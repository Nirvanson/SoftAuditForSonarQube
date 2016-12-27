package plugin.analyser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sonar.api.measures.Metric;

import plugin.SoftAuditMetrics;
import plugin.model.JavaClass;
import plugin.model.JavaFileContent;
import plugin.model.JavaMethod;
import plugin.model.WordInFile;
import plugin.model.WordList;
import plugin.util.Logger;
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
	private Logger log;
	private final JavaFileNormalizer normalizer;
	private final JavaBaseModelBuilder builder;
	private final JavaModelExpander expander;

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
		builder = new JavaBaseModelBuilder();
		expander = new JavaModelExpander();
		log = Logger.getLogger();
	}

	/**
	 * Start analyzing.
	 *
	 * @return map with results for measures
	 */
	@SuppressWarnings("unchecked")
	public Map<Metric<Integer>, Double> analyze() {

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
			log.printFile(file);
			List<String> normalizedLines = null;
			try {
				normalizedLines = normalizer.prepareFile(file);
			} catch (IOException e) {
			}
			log.printNormalizedLines(normalizedLines);
			String singleLineCode = normalizer.convertToSingleString(normalizedLines);
			log.printSingleLineCode(singleLineCode);
			List<WordInFile> wordList = normalizer.createJavaWordList(singleLineCode);
			log.printWords(wordList);
			Map<Metric<Integer>, Double> keyWordMeasures = countKeyWords(wordList);
			log.printMeasures("keyword", keyWordMeasures);
			for (Metric<Integer> measure : keyWordMeasures.keySet()) {
				result.put(measure, result.get(measure) + keyWordMeasures.get(measure));
			}
			List<JavaFileContent> contents = builder.parseClassStructure(wordList);
			log.printModel("class", contents);
			for (JavaFileContent content : contents) {
				if (content instanceof JavaClass) {
					content.setContent(parseClassContent(content));
				}
			}
			log.printModel("refined", contents);
			Map<Metric<Integer>, Double> methodMeasures = countMethods(contents);
			log.printMeasures("method", methodMeasures);
			for (Metric<Integer> measure : methodMeasures.keySet()) {
				result.put(measure, result.get(measure) + methodMeasures.get(measure));
			}
			for (JavaFileContent content : contents) {
				if (content instanceof JavaClass) {
					content.setContent(parseStructuralStatements(content.getContent()));
				}
			}
			log.printModel("expanded", contents);
			sourceFiles++;
		}
		log.close();
		result.put(SoftAuditMetrics.SRC, sourceFiles);
		result.put(SoftAuditMetrics.OMS, 200d);
		return result;
	}

	private List<JavaFileContent> parseStructuralStatements(List<JavaFileContent> contentlist) {
		List<JavaFileContent> result = new ArrayList<JavaFileContent>();
		if (!(contentlist == null)) {
			for (JavaFileContent content : contentlist) {
				if (content instanceof JavaMethod) {
					List<JavaFileContent> newMethodContent = new ArrayList<JavaFileContent>();
					for (JavaFileContent methodcontent : content.getContent()) {
						if (methodcontent instanceof JavaClass) {
							newMethodContent.addAll(parseStructuralStatements(methodcontent.getContent()));
						} else if (methodcontent instanceof WordList) {
							newMethodContent.addAll(expander.addStructuralStatements(((WordList) methodcontent).getWordlist()));
						} 
					} 
					content.setContent(newMethodContent);
				} else if (content instanceof JavaClass) {
					content.setContent(parseStructuralStatements(content.getContent()));
				}
				result.add(content);
			}
		}
		return result;
	}
	
	private List<JavaFileContent> parseClassContent(JavaFileContent parent) {
		List<JavaFileContent> result = new ArrayList<JavaFileContent>();
		List<JavaFileContent> contentlist = parent.getContent();
		KeyWord parenttype = null;
		if (parent instanceof JavaClass) {
			parenttype = ((JavaClass) parent).getType();
		}
		if (!(contentlist == null)) {
			for (JavaFileContent content : contentlist) {
				if (content instanceof WordList) {
					for (JavaFileContent classcontent : builder
							.parseMethodsAndClasses(((WordList) content).getWordlist(), parenttype)) {
						if (classcontent instanceof JavaClass || classcontent instanceof JavaMethod) {
							classcontent.setContent(parseClassContent(classcontent));
						}
						result.add(classcontent);
					}
				}
			}
		}
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
		Map<Metric<Integer>, Double> result = new HashMap<Metric<Integer>, Double>();
		result.put(SoftAuditMetrics.MET, 0d);
		result.put(SoftAuditMetrics.PAR, 0d);
		double methods = 0;
		double params = 0;
		for (JavaFileContent content : contents) {
			if (content instanceof JavaMethod) {
				methods++;
				params += ((JavaMethod) content).getParameters().size();
			} else if (content instanceof JavaClass) {
				Map<Metric<Integer>, Double> innerResult = countMethods(content.getContent());
				result.put(SoftAuditMetrics.MET,
						result.get(SoftAuditMetrics.MET) + innerResult.get(SoftAuditMetrics.MET));
				result.put(SoftAuditMetrics.PAR,
						result.get(SoftAuditMetrics.PAR) + innerResult.get(SoftAuditMetrics.PAR));
			}
		}
		result.put(SoftAuditMetrics.MET, result.get(SoftAuditMetrics.MET) + methods);
		result.put(SoftAuditMetrics.PAR, result.get(SoftAuditMetrics.PAR) + params);
		return result;
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
