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
import plugin.model.JavaClass;
import plugin.model.JavaFileContent;
import plugin.model.JavaMethod;
import plugin.model.JavaStatement;
import plugin.model.WordInFile;
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
	private final int loglevel;

	/**
	 * The logger.
	 */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * Analyzer constructor.
	 *
	 * @param files
	 *            - The files to analyze
	 */
	public JavaFileAnalyzer(final Iterable<File> files) {
		super();
		this.files = files;
		normalizer = new JavaFileNormalizer();
		loglevel = 2;
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
		for (File file : files) {
			writer.println("---------- File: " + file.getName() + " ----------");
			if (loglevel > 3) {
				try {
					BufferedReader br = new BufferedReader(new FileReader(file));
					String fileline;
					while ((fileline = br.readLine()) != null) {
						writer.println(fileline);
					}
					br.close();
				} catch (IOException e) {
					writer.println("File " + file.getName() + " caused IO-Error: " + e);
				}
			}
			List<String> normalizedLines = null;
			try {
				normalizedLines = normalizer.prepareFile(file);
			} catch (IOException e) {
				log.error("File " + file.getName() + " caused IO-Error.", e);
			}
			writer.println("*** Step 1 - Extract normalized Lines");
			if (loglevel > 2) {
				for (String line : normalizedLines) {
					writer.println(line);
				}
			}
			writer.println("*** Step 2 - Transform lines to single string");
			String singleCodeString = normalizer.convertToSingleString(normalizedLines);
			if (loglevel > 2)
				writer.println(singleCodeString);
			writer.println("*** Step 3 - Extract words from code");
			List<WordInFile> wordList = normalizer.createJavaWordList(singleCodeString);
			if (loglevel > 2) {
				for (WordInFile word : wordList) {
					writer.println(word);
				}
			}
			writer.println("*** Step 4 - Count key words in word list for simple measures");
			Map<Metric<Integer>, Double> keyWordMeasures = countKeyWords(wordList);
			for (Metric<Integer> measure : keyWordMeasures.keySet()) {
				if (loglevel > 0)
					writer.println(measure.getName() + ": " + keyWordMeasures.get(measure));
				result.put(measure, result.get(measure) + keyWordMeasures.get(measure));
			}
			writer.println("*** Step 5 - Build basic model of the file from wordlist");
			List<JavaFileContent> contents = normalizer.parseClassStructure(wordList);
			for (JavaFileContent content : contents) {
				if (content instanceof JavaClass) {
					content.setContent(normalizer.parseMethods((List<WordInFile>) content.getContent()));
				}
			}
			if (loglevel > 1) {
				for (JavaFileContent content : contents) {
					if (content instanceof JavaClass) {
						writer.println("Class with name: " + ((JavaClass) content).getName() + " extending: " + ((JavaClass) content).getExtending() + " implementing: " + ((JavaClass) content).getImplementing() + " and Body:");
						for (JavaFileContent innercontent : (List<JavaFileContent>) content.getContent()) {
							if (innercontent instanceof JavaMethod) {
								writer.println("	Method with name: " + ((JavaMethod) innercontent).getName()
										+ " and Parameters: " + ((JavaMethod) innercontent).getParameters());
								writer.println("	" + innercontent.getContent());
							} else if (innercontent instanceof JavaStatement) {
								writer.println("	Statement of type: " + ((JavaStatement) innercontent).getType());
							} else {
								writer.println("	Wordlist with length: " + innercontent.getContent().size());
								writer.println("	" + innercontent.getContent());
							}
						}
					} else if (content instanceof JavaStatement) {
						writer.println("Statement of type: " + ((JavaStatement) content).getType());
					} else {
						writer.println("Wordlist with length: " + content.getContent().size());
						writer.println(content.getContent());
					}
				}
			}
			writer.println("*** Step 6 - Count methods and parameters in basic model");
			for (JavaFileContent content : contents) {
				if (content instanceof JavaClass) {
					Map<Metric<Integer>, Double> methodMeasures = countMethods(
							(List<JavaFileContent>) content.getContent());
					for (Metric<Integer> measure : methodMeasures.keySet()) {
						if (loglevel > 0)
							writer.println(measure.getName() + ": " + methodMeasures.get(measure));
						result.put(measure, result.get(measure) + methodMeasures.get(measure));
					}
				}
			}
			sourceFiles++;
		}
		writer.close();
		result.put(SoftAuditMetrics.SRC, sourceFiles);
		result.put(SoftAuditMetrics.OMS, 200d);
		return result;
	}

	/**
	 * Count methods and their parameters
	 * 
	 * @param contents
	 *            - the JavaClassContents of the file
	 * @returns result-map
	 */
	private Map<Metric<Integer>, Double> countMethods(List<JavaFileContent> contents) {
		Map<Metric<Integer>, Double> partialResult = new HashMap<Metric<Integer>, Double>();
		double methods = 0;
		double params = 0;
		for (JavaFileContent content : contents) {
			if (content instanceof JavaMethod) {
				methods++;
				params += ((JavaMethod) content).getParameters().size();
			}
		}
		partialResult.put(SoftAuditMetrics.MET, methods);
		partialResult.put(SoftAuditMetrics.PAR, params);
		return partialResult;
	}

	/**
	 * Do keyword-count-analysis for file.
	 * 
	 * @param words
	 *            - the words of the file to analyze
	 * @returns result-map
	 */
	private Map<Metric<Integer>, Double> countKeyWords(List<WordInFile> words) {
		Map<Metric<Integer>, Double> partialResult = new HashMap<Metric<Integer>, Double>();
		// count cases in switches
		partialResult.put(SoftAuditMetrics.CAS, countKey(words, KeyWord.CASE) + countKey(words, KeyWord.DEFAULT));
		// count classes
		partialResult.put(SoftAuditMetrics.CLA, countKey(words, KeyWord.CLASS));
		// count if and try statements
		partialResult.put(SoftAuditMetrics.IFS, countKey(words, KeyWord.IF) + countKey(words, KeyWord.TRY));
		// count includes as imports and package statements
		partialResult.put(SoftAuditMetrics.IMP, countKey(words, KeyWord.IMPORT) + countKey(words, KeyWord.PACKAGE));
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
		// count statement-identifiers. has to be cleaned by removing ";" in for
		// conditions and adding "else if" as 2 statements and if without braces
		partialResult.put(SoftAuditMetrics.STM, countKey(words, KeyWord.OPENBRACE) + countKey(words, KeyWord.SEMICOLON)
				+ countKey(words, KeyWord.CLOSEBRACE));
		return partialResult;
	}

	private double countKey(List<WordInFile> words, KeyWord key) {
		return (double) Collections.frequency(words, new WordInFile(null, key));
	}
}
