package plugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Project;

import plugin.analyser.FileNormalizer;
import plugin.analyser.ModelAnalyser;
import plugin.analyser.ModelBuilder;
import plugin.analyser.ModelDetailExpander;
import plugin.analyser.ModelStructureExpander;
import plugin.model.JavaFileContent;
import plugin.model.WordInFile;
import plugin.util.Logger;
import plugin.util.ParsingException;

/**
 * Analyses project-files in search for relevant information.
 *
 * @author Jan Rucks
 * @version 0.1
 */
public class SoftAuditSensor implements Sensor {

    /** The file system object for the project being analysed. */
    private final FileSystem fileSystem;

    /**
     * Constructor for test-runs.
     *
     * @param fileSystem the project file system
     */
    public SoftAuditSensor(String loggername) {
        this.fileSystem = null;
        Logger.getLogger(loggername);
    }
    
    /**
     * Constructor that sets the file system object for the
     * project being analysed.
     *
     * @param fileSystem the project file system
     */
    public SoftAuditSensor(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
        Logger.getLogger(null);
    }
    
    /**
     * Determines whether the sensor should run or not for the given project.
     *
     * @param project - the project being analysed
     * @return true if java used in project
     */
    public boolean shouldExecuteOnProject(Project project) {
        if (fileSystem.languages().contains("java")) {
        	return true;
        }
        return false;
    }

    /**
     * Do analysation.
     *
     * @param project       - the project being analysed
     * @param sensorContext - the sensor context
     */
    public void analyse(Project project, SensorContext sensorContext) {
    	// get measures from files
        Map<Metric<?>, Double> measures = doAnalyse(fileSystem.files(fileSystem.predicates().hasLanguage("java")));
        // save measures
        for (Metric<?> metric: measures.keySet()) {
    		sensorContext.saveMeasure(new Measure<Integer>(metric, measures.get(metric)));
    	}
        // compute and save metrics
        sensorContext.saveMeasure(new Measure<Float>(SoftAuditMetrics.COC, (measures.get(SoftAuditMetrics.IFS) + 
        		measures.get(SoftAuditMetrics.LOP) + measures.get(SoftAuditMetrics.SWI) + measures.get(SoftAuditMetrics.CAS)) / (measures.get(SoftAuditMetrics.MET) * 4)));
    }

    /**
     * Start analyzing.
     *
     * @return map with results for measures
     */
    public Map<Metric<?>, Double> doAnalyse(Iterable<File> files) {

        Map<Metric<?>, Double> result = new HashMap<Metric<?>, Double>();
        // add all measures from metrics list (metrics with keys like base_xyz)
        for (Metric<?> metric : new SoftAuditMetrics().getMetrics()) {
            if (metric.getKey().startsWith("base")) {
                result.put(metric, 0d);
            }
        }
        ModelAnalyser analyser = new ModelAnalyser();
        for (File file : files) {
        	// try parsing file
        	List<JavaFileContent> fileModel = null;
        	int reachedParsingLevel = 0;
            try {
            	// step 0 - read file
                Logger.getLogger(null).printFile(file);
                reachedParsingLevel++;
            } catch (IOException exceptionInStepZero) {
            	// file not readable, skip file completely
            	exceptionInStepZero.printStackTrace();
            	continue;
            }
            List<WordInFile> wordList = null;
            try {
                // step 1 - do file normalization
                wordList = FileNormalizer.doFileNormalization(file);
                reachedParsingLevel++;
            } catch (ParsingException exeptionInStepOne) {
            	// file normalization failed. skip file completely
            	exeptionInStepOne.printStackTrace();
            	continue;
            }
            try {
                // step 2 - build basic model
                fileModel = ModelBuilder.parseBasicModel(wordList);
                reachedParsingLevel++;
            } catch (ParsingException exeptionInStepTwo) {
            	// Building basic model failed. skip file completely
            	exeptionInStepTwo.printStackTrace();
            	continue;
            }
            try {
                // step 3 - refine model by statement structure
                fileModel = ModelStructureExpander.parseStatementStructure(fileModel);
                reachedParsingLevel++;
            } catch (ParsingException exeptionInStepThree) {
            	// Refining Model with structural statements failed. skip detail parsing and do basic analysis
            	exeptionInStepThree.printStackTrace();
            }
            if (reachedParsingLevel==4) {
            	try {
            		// step 4 - parse model details
            		fileModel = ModelDetailExpander.parseModelDetails(fileModel);
            		reachedParsingLevel++;
            	} catch (ParsingException exeptionInStepFour) {
                	// Refining Model with details failed. Do medium analysis
                	exeptionInStepFour.printStackTrace();
                }
            }
            // if at least a basic model could be parsed analyze model for available measures
            if (fileModel!=null) {
            	Map<Metric<?>, Double> partialResult = analyser.doFileModelAnalysis(fileModel, reachedParsingLevel);
            	for (Metric<?> metric : partialResult.keySet()) {
            		result.put(metric, result.get(metric) + partialResult.get(metric));
            	}
            }
        }
        Logger.getLogger(null).close();
        // put non-additive measures to resultmap
        result.put(SoftAuditMetrics.SRC, analyser.getScannedSourceFiles());
        result.put(SoftAuditMetrics.OMS, analyser.getOptimalModuleSize());
        result.put(SoftAuditMetrics.DTY, analyser.getNumberOfDataTypes());
        result.put(SoftAuditMetrics.STY, analyser.getNumberOfStatementTypes());
        return result;
    }
    
    /**
     * Returns the name of the sensor as it will be used in logs during analysis.
     *
     * @return the name of the sensor
     */
    public String toString() {
        return "SoftAuditSensor";
    }
}
