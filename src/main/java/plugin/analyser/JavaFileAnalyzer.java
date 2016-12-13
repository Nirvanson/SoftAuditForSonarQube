package plugin.analyser;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.measures.Metric;

import plugin.SoftAuditMetrics;

/**
 * Java File Parser to extract needed measures for SoftAudit Metrics.
 *
 * @author Jan Rucks
 * @version 0.1
 */
public class JavaFileAnalyzer {

    /**
     * FileList for Parsing.
     */
    private final Iterable<File> files;
    private final JavaFileNormalizer normalizer;

    /**
     * The logger.
     */
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Analyzer constructor.
     *
     * @param files - The files to analyze
     */
    public JavaFileAnalyzer(final Iterable<File> files) {
        super();
        this.files = files;
        normalizer = new JavaFileNormalizer();
    }

    /**
     * Start analyzing.
     *
     * @return map with results for measures
     */
    @SuppressWarnings("unchecked")
	public Map<Metric<Integer>, Double> analyze() {
    	log.info("Java analyzer started...");
    	Map<Metric<Integer>, Double> result = new HashMap<Metric<Integer>, Double>();
    	// add all measures from metrics list (metrics with keys like base_xyz)
    	for (Metric<?> metric : new SoftAuditMetrics().getMetrics()) {
    		if (metric.getKey().startsWith("base")) {
    			result.put((Metric<Integer>) metric, 0d);
    		}
    	}
    	// start analyzing each relevant file
    	double sourceFiles = 0;
    	for(File file : files) {
    		List<String> normalizedLines = null;
    		try {
    			normalizedLines = normalizer.prepareFile(file);
    		} catch (IOException e) {
				log.error("File " + file.getName() + " caused IO-Error.", e);
			}
			Map<Metric<Integer>, Double> partialResult = analyzeProjectFile(normalizer.splitToWords(normalizedLines));
			for (Metric<Integer> measure : partialResult.keySet()) {
	    		result.put(measure, result.get(measure) + partialResult.get(measure));
	    	}
			sourceFiles++;
    	}
    	result.put(SoftAuditMetrics.SRC, sourceFiles);
    	result.put(SoftAuditMetrics.OMS, 200d);
        return result;
    }

    /**
     * Analyzes single file.
     * 
     * @param file - the file to analyze
     * @throws IOException 
     */
    private Map<Metric<Integer>, Double> analyzeProjectFile(List<String> words) {
    	Map<Metric<Integer>, Double> partialResult = new HashMap<Metric<Integer>, Double>();
    	// count cases in switches
    	partialResult.put(SoftAuditMetrics.CAS, (double) (Collections.frequency(words, "case") + Collections.frequency(words, "default")));
    	// count classes
    	partialResult.put(SoftAuditMetrics.CLA, (double) Collections.frequency(words, "class"));
    	// count if statements //TODO: add short version
    	partialResult.put(SoftAuditMetrics.IFS, (double) Collections.frequency(words, "if"));
    	// count imports //TODO: same as includes? then different
    	partialResult.put(SoftAuditMetrics.IMP, (double) Collections.frequency(words, "import"));
    	// count interfaces //TODO: check
    	partialResult.put(SoftAuditMetrics.INT, (double) Collections.frequency(words, "inteface"));
    	// count Literals //TODO: different to SoftAudit
    	partialResult.put(SoftAuditMetrics.LIT, (double) (Collections.frequency(words, "\"") + Collections.frequency(words, "'")));
    	// count Loop statements
    	partialResult.put(SoftAuditMetrics.LOP, (double) (Collections.frequency(words, "for") + Collections.frequency(words, "while")));
    	// count return statements
    	partialResult.put(SoftAuditMetrics.RET, (double) Collections.frequency(words, "return"));
    	// count switch statements
    	partialResult.put(SoftAuditMetrics.SWI, (double) Collections.frequency(words, "switch"));
    	return partialResult;
    }
    
    
}
