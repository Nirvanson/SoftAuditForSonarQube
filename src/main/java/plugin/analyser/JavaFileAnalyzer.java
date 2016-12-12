package plugin.analyser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
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
			try {
				Map<Metric<Integer>, Double> partialResult = analyzeProjectFile(file);
				for (Metric<Integer> measure : partialResult.keySet()) {
	    			result.put(measure, result.get(measure) + partialResult.get(measure));
	    		}
			} catch (IOException e) {
				log.error("File " + file.getName() + " caused IO-Error.", e);
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
    private Map<Metric<Integer>, Double> analyzeProjectFile(File file) throws IOException{
    	log.info("Analyzing file: " + file.getName());
    	File normalizedFile = prepareFile(file);
    	Map<Metric<Integer>, Double> partialResult = new HashMap<Metric<Integer>, Double>();
    	BufferedReader br = new BufferedReader(new FileReader(normalizedFile));
    	String line;
    	double returncount = 0;
    	while ((line = br.readLine()) != null) {
    	   // process the line.
    	   if (line.contains("return")) {
    		   returncount++;
    	   }
    	}
    	br.close();
    	partialResult.put(SoftAuditMetrics.RET, returncount);
    	return partialResult;
    }
    
    /**
     * Prepares File for parsing. remove comments, empty lines, leading spaces....
     * 
     * @param file - the file to analyze
     * @throws IOException 
     */
    private File prepareFile(File file) throws IOException{
    	File result = new File("C:\\normalized_" + file.getName());
    	BufferedWriter bw = new BufferedWriter(new FileWriter(result));
    	BufferedReader br = new BufferedReader(new FileReader(file));
    	String line;
    	boolean multiLineComment = false;
    	while ((line = br.readLine()) != null) {
    		log.info("full line: " + line);
    		// only check non empty lines
    		String linewithoutspaces = line.trim();
    		if (!linewithoutspaces.isEmpty()) {
    			log.info("without spaces: " + linewithoutspaces);
    			//remove well formed (full line) comments
    			if (multiLineComment) {
    				if (linewithoutspaces.contains("*/")) {
    					multiLineComment = false;
    				}
    			} else {
    				if (linewithoutspaces.contains("/*") && !linewithoutspaces.contains("*/")) {
    					multiLineComment = true;
    				}
    				if (!linewithoutspaces.startsWith("/*") && !linewithoutspaces.startsWith("//")) {
    					bw.write(linewithoutspaces);
    					log.info("Added line: " + linewithoutspaces);
    				}
    			}
    		}
    	}
    	br.close();
    	bw.close();
    	return result;
    }
}
