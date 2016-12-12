package plugin.analyser;

import java.io.File;
import java.io.IOException;
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
    	for(File file : files) {
    		List<String> normalizedFile = null;
    		try {
    			normalizedFile = normalizer.prepareFile(file);
    		} catch (IOException e) {
				log.error("File " + file.getName() + " caused IO-Error.", e);
			}
			Map<Metric<Integer>, Double> partialResult = analyzeProjectFile(normalizedFile);
			for (Metric<Integer> measure : partialResult.keySet()) {
	    		result.put(measure, result.get(measure) + partialResult.get(measure));
	    	}
    	}
        return result;
    }

    /**
     * Analyzes single file.
     * 
     * @param file - the file to analyze
     * @throws IOException 
     */
    private Map<Metric<Integer>, Double> analyzeProjectFile(List<String> file) {
    	Map<Metric<Integer>, Double> partialResult = new HashMap<Metric<Integer>, Double>();
    	double returncount = 0;
    	for (String line: file) {
    	   // process the line.
    	   if (line.startsWith("return;") ||
    			   line.startsWith("return ") ||
    			   line.contains(" return ") ||
    			   line.contains(" return;") ||
    			   line.contains(";return ") ||
    			   line.contains(";return;") ||
    			   line.endsWith(" return") ||
    			   line.equals("return")) {
    		   returncount++;
    	   }
    	}
    	partialResult.put(SoftAuditMetrics.RET, returncount);
    	return partialResult;
    }
    
    
}
