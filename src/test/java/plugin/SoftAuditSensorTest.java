package plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.sonar.api.measures.Metric;

import plugin.SoftAuditSensor;
import plugin.analyser.MetricCalculator;
import plugin.util.Logger;

public class SoftAuditSensorTest {
	
    public SoftAuditSensorTest() {
        super();
        (new File("target/logs")).mkdirs();
    }
   
    @Test
    public void testSingleFileAnalyse() throws InterruptedException {
        String filename = "Day";
        File input = new File("src/main/resources/testdata/" + filename + ".java");
        SoftAuditSensor sensor = new SoftAuditSensor(filename);
        
        // do analyze, check logfile manually
        Map<Metric<?>, Double> measures = sensor.doAnalyse(Arrays.asList(input));
        // calculate metrics
        Map<Metric<?>, Double> metrics = MetricCalculator.calculate(measures);
        Logger.getLogger(null).printMetrics(metrics);
        Logger.getLogger(null).close();
    }
    
    @Test
    public void testSelfScan() throws InterruptedException {
        List<File> input = new ArrayList<File>();
        listf("src/main/java", input);
        SoftAuditSensor sensor = new SoftAuditSensor("SelfScan");
        
        // do analyze, check logfile manually
        Map<Metric<?>, Double> measures = sensor.doAnalyse(input);
        // calculate metrics
        Map<Metric<?>, Double> metrics = MetricCalculator.calculate(measures);
        Logger.getLogger(null).printMetrics(metrics);
        Logger.getLogger(null).close();
    }
    
    public void listf(String directoryName, List<File> files) {
        File directory = new File(directoryName);

        // get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isFile()) {
                files.add(file);
            } else if (file.isDirectory()) {
                listf(file.getAbsolutePath(), files);
            }
        }
    }
}
