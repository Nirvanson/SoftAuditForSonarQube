package plugin.analyser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.sonar.api.measures.Metric;

import plugin.SoftAuditMetrics;
import plugin.model.JavaFileContent;
import plugin.model.StatementType;
import plugin.model.WordInFile;
import plugin.model.components.JavaClass;
import plugin.model.components.JavaControlStatement;
import plugin.model.components.JavaEnumValues;
import plugin.model.components.JavaMethod;
import plugin.model.components.JavaStatement;
import plugin.model.components.JavaStatementWithAnonymousClass;
import plugin.model.components.JavaVariable;
import plugin.util.AnalyzeException;
import plugin.util.Logger;

/**
 * Java File Parser to extract needed measures for SoftAudit Metrics.
 *
 * @author Jan Rucks (jan.rucks@gmx.de)
 * @version 0.3
 */
public class ModelAnalyser {
	// variables for full analysis
	private Set<StatementType> usedStatementTypes;
	private Set<List<WordInFile>> usedDataTypes;
	private double scannedSourceFiles;
	private final double optimalModuleSize;
	// variables for single file analysis
	private Set<String> declaredMethods;

	public ModelAnalyser() {
		usedStatementTypes = new HashSet<StatementType>();
		usedDataTypes = new HashSet<List<WordInFile>>();
		scannedSourceFiles = 0.000;
		// TODO: properties file ?
		optimalModuleSize = 200.000;
	}

	public Map<Metric<?>, Double> doFileModelAnalysis(List<JavaFileContent> fileModel, int reachedParsingLevel) throws AnalyzeException {
		scannedSourceFiles++;
		declaredMethods = collectDeclaredMethods(fileModel);
		Map<Metric<?>, Double> result = analyzeContentList(fileModel, reachedParsingLevel);
		Logger.getLogger(null).printFileMeasures(result);
		return result;
	}

	public double getNumberOfStatementTypes() {
		return (double) usedStatementTypes.size();
	}
	
	public double getNumberOfDataTypes() {
		return (double) usedDataTypes.size();
	}

	public double getScannedSourceFiles() {
		return scannedSourceFiles;
	}

	public double getOptimalModuleSize() {
		return optimalModuleSize;
	}
	
	private Map<Metric<?>, Double> analyzeContentList(List<JavaFileContent> contentlist, int reachedParsingLevel) throws AnalyzeException {
		Map<Metric<?>, Double> result = new HashMap<Metric<?>, Double>();
		if (contentlist==null || contentlist.isEmpty()) {
			// ignore empty contents
			return result;
		}
		for (JavaFileContent content : contentlist) {
			if (content instanceof JavaClass) {
				JavaClass theClass = (JavaClass) content;
				countFinding(result, SoftAuditMetrics.STM, 2.0);
				includeContentScan(result, analyzeContentList(theClass.getContent(), reachedParsingLevel));
				switch(theClass.getType()) {
				case CLASS:
					usedStatementTypes.add(StatementType.CLASSDECLARATION);
					countFinding(result, SoftAuditMetrics.CLA, 1.0);
					break;
				case INTERFACE: case ANNOTATIONINTERFACE:
					usedStatementTypes.add(StatementType.INTERFACEDECLARATION);
					countFinding(result, SoftAuditMetrics.INT, 1.0);
					break;
				case ENUM:
					usedStatementTypes.add(StatementType.ENUMDECLARATION);
					if (theClass.getContent().size()>1) {
						countFinding(result, SoftAuditMetrics.CLA, 1.0);
					}
					break;
				default:
					throw new AnalyzeException("Unknown JavaClassDeclaration: " + theClass.getType());
				}
			} else if (content instanceof JavaMethod) {
				JavaMethod theMethod = (JavaMethod) content;
				countFinding(result, SoftAuditMetrics.STM, 2.0);
				countFinding(result, SoftAuditMetrics.MET, 1.0);
				usedStatementTypes.add(StatementType.METHODDECLARATION);
				usedDataTypes.add(theMethod.getReturntype());
				countFinding(result, SoftAuditMetrics.PAR, theMethod.getParameters().size());
				countFinding(result, SoftAuditMetrics.VAR, theMethod.getParameters().size());
				countFinding(result, SoftAuditMetrics.REF, theMethod.getParameters().size());
				for (JavaVariable parameter : theMethod.getParameters()) {
					usedDataTypes.add(parameter.getType());
				}
				includeContentScan(result, analyzeContentList(theMethod.getContent(), reachedParsingLevel));
			} else if (content instanceof JavaEnumValues) {
				//TODO
			} else if (content instanceof JavaStatementWithAnonymousClass) {
				//TODO
			} else if (content instanceof JavaControlStatement) {
				//TODO
			} else if (content instanceof JavaStatement) {
				//TODO
			} else {
				// ignore unparsed stuff
			}
		}
		return result;
	}
	
	private Set<String> collectDeclaredMethods(List<JavaFileContent> contentlist) {
		Set<String> result = new HashSet<String>();
		if (contentlist==null || contentlist.isEmpty()) {
			return result;
		}
		for (JavaFileContent content : contentlist) {
			if (content instanceof JavaClass || content instanceof JavaStatementWithAnonymousClass) {
				result.addAll(collectDeclaredMethods(content.getContent()));
			} else if (content instanceof JavaMethod) {
				result.addAll(collectDeclaredMethods(content.getContent()));
				result.add(((JavaMethod) content).getName());
			}
		}
		return result;
	}
	
	private void includeContentScan(Map<Metric<?>, Double> resultmap, Map<Metric<?>, Double> partialResult) {
		for (Metric<?> measureToCount : partialResult.keySet()) {
			if (resultmap.containsKey(measureToCount)) {
				resultmap.put(measureToCount, resultmap.get(measureToCount) + partialResult.get(measureToCount));
			} else {
				resultmap.put(measureToCount,  partialResult.get(measureToCount));
			}
    	}
	}
	
	private void countFinding(Map<Metric<?>, Double> resultmap, Metric<?> measureToCount, double findings) {
		if (resultmap.containsKey(measureToCount)) {
			resultmap.put(measureToCount, resultmap.get(measureToCount) + findings);
		} else {
			resultmap.put(measureToCount,  findings);
		}
	}
}
