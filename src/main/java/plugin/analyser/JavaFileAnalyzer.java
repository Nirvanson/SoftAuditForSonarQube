package plugin.analyser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.measures.Metric;

import plugin.SoftAuditMetrics;
import plugin.model.JavaClassContent;
import plugin.model.JavaMethod;
import plugin.model.JavaWord;
import plugin.model.KeyWord;

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
    	PrintWriter writer = null;
    	try {
			writer = new PrintWriter("D:\\plugin-log.log", "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		} 
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
    		writer.println("---------- File: " + file.getName() + " ----------");
    		try {
    			BufferedReader br = new BufferedReader(new FileReader(file));
    	    	String fileline;
    	    	while ((fileline = br.readLine()) != null) {
    	    		//writer.println(fileline);
    	    	}
    	    	br.close();
    		} catch (IOException e) {
    			log.error("File " + file.getName() + " caused IO-Error.", e);
    		}
    		List<String> normalizedLines = null;
    		try {
    			normalizedLines = normalizer.prepareFile(file);
    		} catch (IOException e) {
				log.error("File " + file.getName() + " caused IO-Error.", e);
			}
    		writer.println("*** Step 1 - normalized Lines:");
    		for (String line : normalizedLines) {
    			//writer.println(line);
    		}
    		writer.println("*** Step 2 - Code as single string:");
			String singleCodeString = normalizer.convertToSingleString(normalizedLines);
			//writer.println(singleCodeString);
			writer.println("*** Step 3 - Java Words");
			List<JavaWord> wordList = normalizer.createJavaWordList(singleCodeString);
			for (JavaWord word : wordList) {
    			//writer.println(word);
    		}
			writer.println("*** Step 4 - Count key words");
			Map<Metric<Integer>, Double> keyWordMeasures = countKeyWords(wordList);
			for (Metric<Integer> measure : keyWordMeasures.keySet()) {
				writer.println(measure.getName() + ": " + keyWordMeasures.get(measure));
	    		result.put(measure, result.get(measure) + keyWordMeasures.get(measure));
	    	}
			writer.println("*** Step 5 - Reduce word list");
			List<JavaWord> reducedWordList = normalizer.reduceWordList(wordList);
			for (JavaWord word : reducedWordList) {
    			//writer.println(word);
    		}
			writer.println("*** Step 6 - Extract Methods");
			List<JavaClassContent> contents = normalizer.splitToMethods(reducedWordList);
			for (JavaClassContent content : contents) {
				if (content instanceof JavaMethod) {
					writer.println("Method with name: " + ((JavaMethod) content).getName() + " and Parameters: " + ((JavaMethod) content).getParameters());
				} else {
					writer.println("Wordlist with length: " + content.getContent().size());
				}
				writer.println(content.getContent());
			}
			writer.println("*** Step 7 - Count methods and parameters");
			//TODO
			sourceFiles++;
    	}
    	writer.close();
    	result.put(SoftAuditMetrics.SRC, sourceFiles);
    	result.put(SoftAuditMetrics.OMS, 200d);
        return result;
    }

    /**
     * Do keyword-count-analysis for file.
     * 
     * @param words - the words of the file to analyze
     * @returns result-map
     */
    private Map<Metric<Integer>, Double> countKeyWords(List<JavaWord> words) {
    	Map<Metric<Integer>, Double> partialResult = new HashMap<Metric<Integer>, Double>();
    	// count cases in switches
    	partialResult.put(SoftAuditMetrics.CAS, countKey(words, KeyWord.CASE) + countKey(words, KeyWord.DEFAULT));
    	// count classes
    	partialResult.put(SoftAuditMetrics.CLA, countKey(words, KeyWord.CLASS));
    	// count if statements 
    	partialResult.put(SoftAuditMetrics.IFS, countKey(words, KeyWord.IF) + countKey(words, KeyWord.TRY));
    	// count imports 
    	partialResult.put(SoftAuditMetrics.IMP, countKey(words, KeyWord.IMPORT));
    	// count interfaces 
    	partialResult.put(SoftAuditMetrics.INT, countKey(words, KeyWord.INTERFACE));
    	// count Literals
    	partialResult.put(SoftAuditMetrics.LIT, countKey(words, KeyWord.STRINGLITERAL));
    	// count Loop statements
    	partialResult.put(SoftAuditMetrics.LOP, countKey(words, KeyWord.FOR) + countKey(words, KeyWord.WHILE));
    	// count return statements
    	partialResult.put(SoftAuditMetrics.RET, countKey(words, KeyWord.RETURN));
    	// count switch statements
    	partialResult.put(SoftAuditMetrics.SWI, countKey(words, KeyWord.SWITCH));
    	// count statements
    	partialResult.put(SoftAuditMetrics.STM, countKey(words, KeyWord.OPENBRACE) + countKey(words, KeyWord.SEMICOLON));
    	return partialResult;
    }
    
    private double countKey(List<JavaWord> words, KeyWord key) {
    	return (double) Collections.frequency(words, new JavaWord(null, key));
    }
}
