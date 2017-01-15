package plugin;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import plugin.model.components.JavaClass;
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
    /** Logger for detailed file-log. */
    private Logger log;

    /**
     * Constructor for test-runs.
     *
     * @param fileSystem the project file system
     */
    public SoftAuditSensor(String loggername) {
        this.fileSystem = null;
        log = Logger.getLogger(loggername);
    }
    
    /**
     * Constructor that sets the file system object for the
     * project being analysed.
     *
     * @param fileSystem the project file system
     */
    public SoftAuditSensor(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
        log = Logger.getLogger(null);
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
        // start analyzing each relevant file
        double sourceFiles = 0;
        for (File file : files) {
            System.out.println(file);
            try {
                // try parsing file
                log.printFile(file);
                List<String> normalizedLines = null;
                normalizedLines = FileNormalizer.prepareFile(file);
                log.printNormalizedLines(normalizedLines);
                String singleLineCode = FileNormalizer.convertToSingleString(normalizedLines);
                log.printSingleLineCode(singleLineCode);
                List<WordInFile> wordList = FileNormalizer.createJavaWordList(singleLineCode);
                log.printWords(wordList);
                Map<Metric<Integer>, Double> keyWordMeasures = ModelAnalyser.countKeyWords(wordList);
                log.printMeasures("keyword", keyWordMeasures);
                for (Metric<Integer> measure : keyWordMeasures.keySet()) {
                    result.put(measure, result.get(measure) + keyWordMeasures.get(measure));
                }
                List<JavaFileContent> contents = ModelBuilder.parseClassStructure(wordList);
                log.printModel("class", contents);
                for (JavaFileContent content : contents) {
                    if (content instanceof JavaClass) {
                        content.setContent(ModelBuilder.parseClassContent(content));
                    }
                }
                log.printModel("refined", contents);
                Map<Metric<Integer>, Double> methodMeasures = ModelAnalyser.countMethods(contents);
                log.printMeasures("method", methodMeasures);
                for (Metric<Integer> measure : methodMeasures.keySet()) {
                    result.put(measure, result.get(measure) + methodMeasures.get(measure));
                }
                for (JavaFileContent content : contents) {
                    if (content instanceof JavaClass) {
                        content.setContent(ModelStructureExpander.parseStructuralStatements(content.getContent()));
                    }
                }
                log.printModel("expanded", contents);
                contents = ModelDetailExpander.parseRemainingWordListsToStatements(contents);
                log.printModel("statement", contents);
                ModelDetailExpander.parseDeclarationsAndCalls(contents);
                Set<String> declaredVariables = new HashSet<String>();
                declaredVariables = ModelAnalyser.collectDeclaredVariables(contents);
                ModelDetailExpander.parseReferences(contents, declaredVariables);
                ModelDetailExpander.parseAssignments(contents);
                ModelDetailExpander.parseRemainingStatementTypes(contents);
                log.printModel("declarations", contents);
                // Map<Metric<Integer>, Double> varMeasures = new HashMap<Metric<Integer>, Double>();
                // varMeasures.put(SoftAuditMetrics.VAR, (double) declaredVariables.size());
                // log.printMeasures("variables", varMeasures);
                sourceFiles++;
            } catch (ParsingException e) {
                e.printStackTrace();
            }
        }
        log.close();
        result.put(SoftAuditMetrics.SRC, sourceFiles);
        result.put(SoftAuditMetrics.OMS, 200d);
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
