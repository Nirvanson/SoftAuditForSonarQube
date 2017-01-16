package plugin.analyser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.sonar.api.measures.Metric;

import plugin.SoftAuditMetrics;
import plugin.model.JavaFileContent;
import plugin.model.KeyWord;
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
    private double insecureStatements;

    public ModelAnalyser() {
        usedStatementTypes = new HashSet<StatementType>();
        usedDataTypes = new HashSet<List<WordInFile>>();
        scannedSourceFiles = 0.000;
        // TODO: properties file ?
        optimalModuleSize = 200.000;
    }

    public Map<Metric<?>, Double> doFileModelAnalysis(List<JavaFileContent> fileModel) throws AnalyzeException {
        scannedSourceFiles++;
        insecureStatements = 0.0;
        declaredMethods = collectDeclaredMethods(fileModel);
        Map<Metric<?>, Double> result = analyzeContentList(fileModel);
        result.put(SoftAuditMetrics.SST, result.get(SoftAuditMetrics.STM) - insecureStatements);
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

    private Map<Metric<?>, Double> analyzeContentList(List<JavaFileContent> contentlist) throws AnalyzeException {
        Map<Metric<?>, Double> result = new HashMap<Metric<?>, Double>();
        if (contentlist == null || contentlist.isEmpty()) {
            // ignore empty contents
            return result;
        }
        for (JavaFileContent content : contentlist) {
            if (content instanceof JavaClass) {
                JavaClass theClass = (JavaClass) content;
                // check for Serializable / Clonable implementers and non-final derived classes
                if ((theClass.getImplementing() != null && (theClass.getImplementing().contains("Serializable")
                        || theClass.getImplementing().contains("Clonable")))
                        || (theClass.getExtending() != null && !theClass.getExtending().isEmpty()
                                && (theClass.getModifiers() == null
                                        || !theClass.getModifiers().contains(KeyWord.FINAL)))) {
                    insecureStatements++;
                }
                // check for public classvariables
                for (JavaFileContent classcontent : theClass.getContent()) {
                    if (classcontent instanceof JavaStatement
                            && ((JavaStatement) classcontent).getDeclaredVariables() != null
                            && !((JavaStatement) classcontent).getDeclaredVariables().isEmpty() ) {
                        for (WordInFile wordInStatement : ((JavaStatement) classcontent).getStatementText()) {
                            if (wordInStatement.equals(KeyWord.PUBLIC)) {
                                insecureStatements++;
                                break;
                            }
                        }
                    }
                }
                // add header-resulting measures
                countFinding(result, SoftAuditMetrics.STM, 2.0);
                switch (theClass.getType()) {
                case CLASS:
                    usedStatementTypes.add(StatementType.CLASSDECLARATION);
                    countFinding(result, SoftAuditMetrics.CLA, 1.0);
                    break;
                case INTERFACE:
                case ANNOTATIONINTERFACE:
                    usedStatementTypes.add(StatementType.INTERFACEDECLARATION);
                    countFinding(result, SoftAuditMetrics.INT, 1.0);
                    break;
                case ENUM:
                    usedStatementTypes.add(StatementType.ENUMDECLARATION);
                    if (theClass.getContent().size() > 1) {
                        countFinding(result, SoftAuditMetrics.CLA, 1.0);
                    }
                    break;
                default:
                    throw new AnalyzeException("Unknown JavaClassDeclaration: " + theClass.getType());
                }
                // include content-scan
                includeContentScan(result, analyzeContentList(theClass.getContent()));
            } else if (content instanceof JavaMethod) {
                JavaMethod theMethod = (JavaMethod) content;
                // count header-resulting measures
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
                // check for FFC hits. if 0 add as RUM
                Map<Metric<?>, Double> contentScan = analyzeContentList(theMethod.getContent());
                if (!contentScan.containsKey(SoftAuditMetrics.FFC) || contentScan.get(SoftAuditMetrics.FFC) == 0.0) {
                    countFinding(result, SoftAuditMetrics.RUM, 1.0);
                }
                // include scan-result from method-content
                includeContentScan(result, contentScan);
            } else if (content instanceof JavaEnumValues) {
                // count as 1 statement, each value as literal, add statementtype
                countFinding(result, SoftAuditMetrics.STM, 1.0);
                countFinding(result, SoftAuditMetrics.LIT, ((JavaEnumValues) content).getValues().size());
                usedStatementTypes.add(StatementType.ENUMVALUES);
            } else if (content instanceof JavaStatementWithAnonymousClass) {
                JavaStatementWithAnonymousClass theStatement = (JavaStatementWithAnonymousClass) content;
                // include content-scan of anonymous class 
                includeContentScan(result, analyzeContentList(theStatement.getContent()));
                // count as 3 statements statement itself and begin / ending of anonymous class
                countFinding(result, SoftAuditMetrics.STM, 3.0);
                // count it as class
                countFinding(result, SoftAuditMetrics.CLA, 1.0);
                // add possible statement-measures
                usedStatementTypes.add(theStatement.getType());
                for (JavaVariable variable : theStatement.getDeclaredVariables()) {
                    usedDataTypes.add(variable.getType());
                }
                countFinding(result, SoftAuditMetrics.VAR, theStatement.getDeclaredVariables().size());
                countFinding(result, SoftAuditMetrics.REF,
                        theStatement.getDeclaredVariables().size() + theStatement.getReferencedVariables().size());
                countFinding(result, SoftAuditMetrics.FUC, theStatement.getCalledMethods().size());
                for (WordInFile function : theStatement.getCalledMethods()) {
                    if (!declaredMethods.contains(function.getWord())) {
                        countFinding(result, SoftAuditMetrics.FFC, 1.0);
                    }
                }
                if (theStatement.getType().equals(StatementType.ASSIGNMENT)) {
                    boolean assignFound = false;
                    countFinding(result, SoftAuditMetrics.RES, 1.0);
                    for (WordInFile word : theStatement.getStatementText()) {
                        if (word.getKey().equals(KeyWord.ASSIGNMENT)) {
                            assignFound = true;
                        }
                        if (word.getKey().equals(KeyWord.VARIDENT) && assignFound) {
                            countFinding(result, SoftAuditMetrics.ARG, 1.0);
                        }
                    }
                }
            } else if (content instanceof JavaControlStatement) {
                // TODO
            } else if (content instanceof JavaStatement) {
                // TODO
            } else {
                // ignore unparsed stuff
            }
        }
        return result;
    }

    private Set<String> collectDeclaredMethods(List<JavaFileContent> contentlist) {
        Set<String> result = new HashSet<String>();
        if (contentlist == null || contentlist.isEmpty()) {
            return result;
        }
        for (JavaFileContent content : contentlist) {
            if (content instanceof JavaClass || content instanceof JavaStatementWithAnonymousClass) {
                result.addAll(collectDeclaredMethods(content.getContent()));
            } else if (content instanceof JavaMethod) {
                result.addAll(collectDeclaredMethods(content.getContent()));
                if (((JavaMethod) content).getReturntype().isEmpty() && result.contains(((JavaMethod) content).getName())) {
                    // duplicated constructor detected
                    insecureStatements++;
                } else {
                    result.add(((JavaMethod) content).getName());
                }
            }
        }
        return result;
    }

    private void includeContentScan(Map<Metric<?>, Double> resultmap, Map<Metric<?>, Double> partialResult) {
        for (Metric<?> measureToCount : partialResult.keySet()) {
            if (resultmap.containsKey(measureToCount)) {
                resultmap.put(measureToCount, resultmap.get(measureToCount) + partialResult.get(measureToCount));
            } else {
                resultmap.put(measureToCount, partialResult.get(measureToCount));
            }
        }
    }

    private void countFinding(Map<Metric<?>, Double> resultmap, Metric<?> measureToCount, double findings) {
        if (resultmap.containsKey(measureToCount)) {
            resultmap.put(measureToCount, resultmap.get(measureToCount) + findings);
        } else {
            resultmap.put(measureToCount, findings);
        }
    }
}
