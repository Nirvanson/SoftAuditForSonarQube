package jnru.measures;

import java.util.Map;

import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.measures.Metric;

/**
 * Scanner feeds raw measures on files but must not aggregate values to directories and project.
 * This class emulates loading of file measures from a 3rd-party analyser.
 */
public class SoftAuditSensor implements Sensor {
	
	@Override
	public void describe(SensorDescriptor descriptor) {
		descriptor.name("Measure needed values for SoftAudit Metrics.");
	}

	@Override
	public void execute(SensorContext context) {
		FileSystem fs = context.fileSystem();
		Iterable<InputFile> files = fs.inputFiles(fs.predicates().hasType(InputFile.Type.MAIN));
		for (InputFile file : files) {
			// parse a java file to retrieve relevant measures
			Map<Metric<Integer>, Integer> measures = FileParser.measure(file);
			for (Metric<Integer> sonarMetric : measures.keySet()) {
				context.<Integer>newMeasure()
        			.forMetric(sonarMetric)
        			.on(file)
        			.withValue(measures.get(sonarMetric)) 
        			.save();
			}
		}
	}
}
