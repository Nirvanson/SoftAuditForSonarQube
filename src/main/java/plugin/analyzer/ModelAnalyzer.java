package plugin.analyzer;

import java.util.Arrays;
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
import plugin.model.WordType;
import plugin.model.components.JavaClass;
import plugin.model.components.JavaControlStatement;
import plugin.model.components.JavaEnumValues;
import plugin.model.components.JavaMethod;
import plugin.model.components.JavaStatement;
import plugin.model.components.JavaStatementWithAnonymousClass;
import plugin.model.components.JavaVariable;
import plugin.util.AnalyzeException;

/**
 * Java File Parser to extract needed measures for SoftAudit Metrics.
 *
 * @author Jan Rucks (jan.rucks@gmx.de)
 * @version 0.3
 */
public class ModelAnalyzer {
    // variables for full analysis
    private Set<StatementType> usedControlStatementTypes;
    private double otherStatementTypes;
    private Set<String> usedDataTypes;
    private double scannedSourceFiles;
    private Set<String> declaredMethods;
    private Set<String> getterAndSetter;

    public ModelAnalyzer() {
        usedControlStatementTypes = new HashSet<StatementType>();
        otherStatementTypes = 0.0;
        usedDataTypes = new HashSet<String>();
        scannedSourceFiles = 0.0;
        declaredMethods = new HashSet<String>();
        getterAndSetter = new HashSet<String>();
    }

    public Map<Metric<?>, Double> doFileModelAnalysis(List<JavaFileContent> fileModel, List<WordInFile> wordList)
            throws AnalyzeException {
        scannedSourceFiles++;
        Map<Metric<?>, Double> result = analyzeContentList(fileModel);
        includeContentScan(result, countLiteralsAndConstants(wordList));
        return result;
    }

    private Map<Metric<?>, Double> countLiteralsAndConstants(List<WordInFile> wordList) {
        double constants = 0.0;
        double literals = 0.0;
        for (WordInFile word : wordList) {
            if (word.equals(KeyWord.STRINGLITERAL)) {
                literals++;
            } else if (word.equals(KeyWord.CONSTANT)) {
                constants++;
            }
        }
        Map<Metric<?>, Double> result = new HashMap<Metric<?>, Double>();
        result.put(SoftAuditMetrics.LIT, literals);
        result.put(SoftAuditMetrics.CON, constants);
        return result;
    }

    public double getNumberOfStatementTypes() {
        return otherStatementTypes + usedControlStatementTypes.size();
    }

    public double getNumberOfDataTypes() {
        return (double) usedDataTypes.size();
    }

    public double getScannedSourceFiles() {
        return scannedSourceFiles;
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
                // add header-resulting measures
                countFinding(result, SoftAuditMetrics.STM, 1.0);
                switch (theClass.getType()) {
                case CLASS:
                    usedControlStatementTypes.add(StatementType.CLASSDECLARATION);
                    countFinding(result, SoftAuditMetrics.CLA, 1.0);
                    break;
                case INTERFACE:
                case ANNOTATIONINTERFACE:
                    usedControlStatementTypes.add(StatementType.INTERFACEDECLARATION);
                    countFinding(result, SoftAuditMetrics.INT, 1.0);
                    break;
                case ENUM:
                    usedControlStatementTypes.add(StatementType.ENUMDECLARATION);
                    countFinding(result, SoftAuditMetrics.INT, 1.0);
                    break;
                default:
                    throw new AnalyzeException("Unknown JavaClassDeclaration: " + theClass.getType());
                }
                for (JavaFileContent classcontent : theClass.getContent()) {
                    if (classcontent instanceof JavaStatement && ((JavaStatement) classcontent).getDeclaredVariables()!=null) {
                        countFinding(result, SoftAuditMetrics.GVA, ((JavaStatement) classcontent).getDeclaredVariables().size());
                    }
                }
                // include content-scan
                includeContentScan(result, analyzeContentList(theClass.getContent()));
            } else if (content instanceof JavaMethod) {
                JavaMethod theMethod = (JavaMethod) content;
                // count header-resulting measures
                countFinding(result, SoftAuditMetrics.STM, 1.0);
                Map<Metric<?>, Double> contentScan = analyzeContentList(theMethod.getContent());
                if (theMethod.getContent()!=null && !(theMethod.getContent().size() == 1 && theMethod.getContent().get(0) instanceof JavaStatement
                        && (((JavaStatement) theMethod.getContent().get(0)).getType().equals(StatementType.RETURN)
                                || ((JavaStatement) theMethod.getContent().get(0)).getType()
                                        .equals(StatementType.ASSIGNMENT)))) {
                    if (theMethod.getModifiers().contains(new WordInFile(null, KeyWord.PRIVATE))) {
                        countFinding(result, SoftAuditMetrics.PME, 1.0);
                    } else {
                        countFinding(result, SoftAuditMetrics.MET, 1.0);
                    }
                    usedControlStatementTypes.add(StatementType.METHODDECLARATION);
                    countFinding(result, SoftAuditMetrics.PAR, theMethod.getParameters().size());
                    // check for FFC hits. if 0 add as RUM
                    if (!contentScan.containsKey(SoftAuditMetrics.FFC) || contentScan.get(SoftAuditMetrics.FFC) == 0.0) {
                        countFinding(result, SoftAuditMetrics.RUM, 1.0);
                    }
                } else {
                	countFinding(result, SoftAuditMetrics.ARG, theMethod.getParameters().size());
                }
                usedDataTypes.add(parseDataType(theMethod.getReturntype()));
                countFinding(result, SoftAuditMetrics.REF, theMethod.getParameters().size());
                for (JavaVariable parameter : theMethod.getParameters()) {
                    usedDataTypes.add(parseDataType(parameter.getType()));
                }
                // include scan-result from method-content
                includeContentScan(result, contentScan);
            } else if (content instanceof JavaEnumValues) {
                // count as 1 statement, each value as literal, add statementtype
                countFinding(result, SoftAuditMetrics.STM, 1.0);
                countFinding(result, SoftAuditMetrics.CON, ((JavaEnumValues) content).getValues().size());
                otherStatementTypes++;
            } else if (content instanceof JavaStatementWithAnonymousClass) {
                JavaStatementWithAnonymousClass theStatement = (JavaStatementWithAnonymousClass) content;
                // include content-scan of anonymous class
                includeContentScan(result, analyzeContentList(theStatement.getContent()));
                // count as 3 statements statement itself and begin / ending of anonymous class
                countFinding(result, SoftAuditMetrics.STM, 2.0);
                // count it as class
                countFinding(result, SoftAuditMetrics.CLA, 1.0);
                // add possible statement-measures
                measureSimpleStatement(theStatement, result);
            } else if (content instanceof JavaControlStatement) {
                JavaControlStatement theStatement = (JavaControlStatement) content;
                switch (theStatement.getType()) {
                case FOR:
                    countFinding(result, SoftAuditMetrics.LOP, 1.0);
                    countFinding(result, SoftAuditMetrics.BRA, 2.0);
                    // include content-scan of for loop
                    includeContentScan(result, analyzeContentList(theStatement.getContent()));
                    // one statement, if content in block additional end statement
                    countFinding(result, SoftAuditMetrics.STM, 1.0);
                    // count loop-declaration stuff
                    for (JavaVariable variable : theStatement.getDeclaredVariables()) {
                        usedDataTypes.add(parseDataType(variable.getType()));
                    }
                    countFinding(result, SoftAuditMetrics.REF, theStatement.getReferencedVariables().size());
                    countFinding(result, SoftAuditMetrics.PRE, theStatement.getReferencedVariables().size());
                    for (WordInFile function : theStatement.getCalledMethods()) {
                        if (!getterAndSetter.contains(function.getWord())) {
                            countFinding(result, SoftAuditMetrics.FUC, 1.0);
                            if (!declaredMethods.contains(function.getWord())) {
                                countFinding(result, SoftAuditMetrics.FFC, 1.0);
                            }
                        }
                    }
                    if (theStatement.getInitialization() != null) {
                        // add statementtype
                        usedControlStatementTypes.add(StatementType.FOR);
                    } else {
                        // add statementtype
                        usedControlStatementTypes.add(StatementType.ENHANCEDFOR);
                    }
                    break;
                case WHILE:
                case DOWHILE:
                    // add statementtype
                    usedControlStatementTypes.add(theStatement.getType());
                    countFinding(result, SoftAuditMetrics.LOP, 1.0);
                    countFinding(result, SoftAuditMetrics.BRA, 2.0);
                    // include content-scan of while / dowhile loop
                    includeContentScan(result, analyzeContentList(theStatement.getContent()));
                    // one statement, if content in block additional end statement
                    countFinding(result, SoftAuditMetrics.STM, 1.0);
                    // count condition stuff
                    countFinding(result, SoftAuditMetrics.REF, theStatement.getReferencedVariables().size());
                    countFinding(result, SoftAuditMetrics.PRE, theStatement.getReferencedVariables().size());
                    for (WordInFile function : theStatement.getCalledMethods()) {
                        if (!getterAndSetter.contains(function.getWord())) {
                            countFinding(result, SoftAuditMetrics.FUC, 1.0);
                            if (!declaredMethods.contains(function.getWord())) {
                                countFinding(result, SoftAuditMetrics.FFC, 1.0);
                            }
                        }
                    }
                    break;
                case IF:
                    // add statementtype
                    usedControlStatementTypes.add(theStatement.getType());
                    countFinding(result, SoftAuditMetrics.IFS, 1.0);
                    countFinding(result, SoftAuditMetrics.BRA, 2.0);
                    // include content-scan of if-block
                    includeContentScan(result, analyzeContentList(theStatement.getContent()));
                    // one statement, if content in block additional end statement
                    countFinding(result, SoftAuditMetrics.STM, 1.0);
                    // count else block if present
                    if (theStatement.getOthercontent() != null && !theStatement.getOthercontent().isEmpty()) {
                        usedControlStatementTypes.add(StatementType.ELSE);
                        includeContentScan(result, analyzeContentList(theStatement.getOthercontent()));
                        // one statement, if content in block additional end statement
                        countFinding(result, SoftAuditMetrics.STM, 1.0);
                    }
                    // count condition stuff
                    countFinding(result, SoftAuditMetrics.REF, theStatement.getReferencedVariables().size());
                    countFinding(result, SoftAuditMetrics.PRE, theStatement.getReferencedVariables().size());
                    for (WordInFile function : theStatement.getCalledMethods()) {
                        if (!getterAndSetter.contains(function.getWord())) {
                            countFinding(result, SoftAuditMetrics.FUC, 1.0);
                            if (!declaredMethods.contains(function.getWord())) {
                                countFinding(result, SoftAuditMetrics.FFC, 1.0);
                            }
                        }
                    }
                    break;
                case TRY:
                    countFinding(result, SoftAuditMetrics.IFS, 1.0);
                    countFinding(result, SoftAuditMetrics.BRA, 2.0);
                    // include content-scan of try-block
                    includeContentScan(result, analyzeContentList(theStatement.getContent()));
                    // two statements,
                    countFinding(result, SoftAuditMetrics.STM, 1.0);
                    // count resourceBlock if present
                    if (theStatement.getResources() != null && !theStatement.getResources().isEmpty()) {
                        includeContentScan(result, analyzeContentList(theStatement.getResources()));
                        usedControlStatementTypes.add(StatementType.TRYWITHRESOURCES);
                    } else {
                        // add statementtype
                        usedControlStatementTypes.add(StatementType.TRY);
                    }
                    // count finally block if present
                    if (theStatement.getOthercontent() != null && !theStatement.getOthercontent().isEmpty()) {
                        usedControlStatementTypes.add(StatementType.FINALLY);
                        includeContentScan(result, analyzeContentList(theStatement.getOthercontent()));
                        // two statements
                        countFinding(result, SoftAuditMetrics.STM, 1.0);
                    }
                    // count catched exceptions
                    if (theStatement.getCatchedExceptions() != null && !theStatement.getCatchedExceptions().isEmpty()) {
                        usedControlStatementTypes.add(StatementType.CATCH);
                        for (List<WordInFile> exception : theStatement.getCatchedExceptions().keySet()) {
                            countFinding(result, SoftAuditMetrics.REF, 1.0);
                            countFinding(result, SoftAuditMetrics.PRE, 1.0);
                            countFinding(result, SoftAuditMetrics.STM, 1.0);
                            countFinding(result, SoftAuditMetrics.BRA, 1.0);
                            includeContentScan(result,
                                    analyzeContentList(theStatement.getCatchedExceptions().get(exception)));
                        }
                    }
                    break;
                case BREAK:
                case CONTINUE:
                case ASSERT:
                    countFinding(result, SoftAuditMetrics.STM, 1.0);
                    usedControlStatementTypes.add(theStatement.getType());
                    break;
                case RETURN:
                    countFinding(result, SoftAuditMetrics.STM, 1.0);
                    usedControlStatementTypes.add(theStatement.getType());
                    countFinding(result, SoftAuditMetrics.BRA, 1.0);
                    countFinding(result, SoftAuditMetrics.RET, 1.0);
                    if (theStatement.getStatementText().size() > 2) {
                        countFinding(result, SoftAuditMetrics.REF, theStatement.getReferencedVariables().size());
                        countFinding(result, SoftAuditMetrics.ARG, theStatement.getReferencedVariables().size());
                        for (WordInFile function : theStatement.getCalledMethods()) {
                            if (!getterAndSetter.contains(function.getWord())) {
                                countFinding(result, SoftAuditMetrics.FUC, 1.0);
                                if (!declaredMethods.contains(function.getWord())) {
                                    countFinding(result, SoftAuditMetrics.FFC, 1.0);
                                }
                            }
                        }
                    }
                    break;
                case THROW:
                    countFinding(result, SoftAuditMetrics.STM, 1.0);
                    otherStatementTypes++;
                    countFinding(result, SoftAuditMetrics.REF, theStatement.getReferencedVariables().size());
                    countFinding(result, SoftAuditMetrics.ARG, theStatement.getReferencedVariables().size());
                    for (WordInFile function : theStatement.getCalledMethods()) {
                        if (!getterAndSetter.contains(function.getWord())) {
                            countFinding(result, SoftAuditMetrics.FUC, 1.0);
                            if (!declaredMethods.contains(function.getWord())) {
                                countFinding(result, SoftAuditMetrics.FFC, 1.0);
                            }
                        }
                    }
                    break;
                case BLOCK:
                    countFinding(result, SoftAuditMetrics.STM, 1.0);
                    usedControlStatementTypes.add(theStatement.getType());
                    includeContentScan(result, analyzeContentList(theStatement.getContent()));
                    break;
                case SYNCHRONIZED:
                case SWITCH:
                    countFinding(result, SoftAuditMetrics.STM, 1.0);
                    countFinding(result, SoftAuditMetrics.SWI, 1.0);
                    usedControlStatementTypes.add(theStatement.getType());
                    includeContentScan(result, analyzeContentList(theStatement.getContent()));
                    countFinding(result, SoftAuditMetrics.REF, theStatement.getReferencedVariables().size());
                    countFinding(result, SoftAuditMetrics.PRE, theStatement.getReferencedVariables().size());
                    for (WordInFile function : theStatement.getCalledMethods()) {
                        if (!getterAndSetter.contains(function.getWord())) {
                            countFinding(result, SoftAuditMetrics.FUC, 1.0);
                            if (!declaredMethods.contains(function.getWord())) {
                                countFinding(result, SoftAuditMetrics.FFC, 1.0);
                            }
                        }
                    }
                    break;
                case CASE:
                    // one statement, if content in block additional end statement
                    double numbercasesinonestatement = 1.0;
                    for (WordInFile word : theStatement.getCondition()) {
                        if (word.getKey().equals(KeyWord.COMMA)) {
                            numbercasesinonestatement++;
                        }
                    }
                    countFinding(result, SoftAuditMetrics.STM, numbercasesinonestatement);
                    countFinding(result, SoftAuditMetrics.CAS, numbercasesinonestatement);
                    countFinding(result, SoftAuditMetrics.BRA, numbercasesinonestatement);
                    usedControlStatementTypes.add(theStatement.getType());
                    includeContentScan(result, analyzeContentList(theStatement.getContent()));
                    break;
                default:
                    throw new AnalyzeException("Unknown JavaControlStatementDeclaration: " + theStatement.getType());
                }
            } else if (content instanceof JavaStatement) {
                if (!((JavaStatement) content).getType().equals(StatementType.ANNOTATION)) {
                    countFinding(result, SoftAuditMetrics.STM, 1.0);
                    measureSimpleStatement((JavaStatement) content, result);
                }
            } else {
                // ignore unparsed stuff
            }
        }
        return result;
    }

    private void measureSimpleStatement(JavaStatement theStatement, Map<Metric<?>, Double> result) {
        if (theStatement.getType().equals(StatementType.IMPORT)
                || theStatement.getType().equals(StatementType.PACKAGE)) {
            countFinding(result, SoftAuditMetrics.IMP, 1.0);
            usedControlStatementTypes.add(theStatement.getType());
        } else {
            otherStatementTypes++;
            if (theStatement.getCalledMethods() != null) {
                for (WordInFile function : theStatement.getCalledMethods()) {
                    if (!getterAndSetter.contains(function.getWord())) {
                        countFinding(result, SoftAuditMetrics.FUC, 1.0);
                        if (!declaredMethods.contains(function.getWord())) {
                            countFinding(result, SoftAuditMetrics.FFC, 1.0);
                        }
                    }
                }
            }
            if (theStatement.getReferencedVariables() != null) {
                countFinding(result, SoftAuditMetrics.REF, theStatement.getReferencedVariables().size());
            }
            if (theStatement.getDeclaredVariables()!=null) {
                countFinding(result, SoftAuditMetrics.VAR, theStatement.getDeclaredVariables().size());
            }
            if (theStatement.getDeclaredVariables() != null && theStatement.getType().equals(StatementType.ASSIGNMENT)
                    || theStatement.getType().equals(StatementType.VARDECLARATION)
                    || theStatement.getType().equals(StatementType.UNSPECIFIED)) {
                for (JavaVariable variable : theStatement.getDeclaredVariables()) {
                    usedDataTypes.add(parseDataType(variable.getType()));
                }
            } 
            countFinding(result, SoftAuditMetrics.ARG, theStatement.getReferencedVariables().size());
        }
    }

    public void collectDeclaredMethods(List<JavaFileContent> contentlist) {
        if (contentlist == null || contentlist.isEmpty()) {
            return;
        }
        for (JavaFileContent content : contentlist) {
            if (content instanceof JavaClass || content instanceof JavaStatementWithAnonymousClass) {
                collectDeclaredMethods(content.getContent());
            } else if (content instanceof JavaMethod) {
                JavaMethod theMethod = (JavaMethod) content;
                collectDeclaredMethods(theMethod.getContent());
                if (theMethod.getContent() != null && theMethod.getContent().size() == 1
                        && theMethod.getContent().get(0) instanceof JavaStatement
                        && (((JavaStatement) theMethod.getContent().get(0)).getType().equals(StatementType.RETURN)
                                || ((JavaStatement) theMethod.getContent().get(0)).getType()
                                        .equals(StatementType.ASSIGNMENT))) {
                    getterAndSetter.add(theMethod.getName());
                } else {
                    declaredMethods.add(theMethod.getName());
                }
            }
        }
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

    private String parseDataType(List<WordInFile> type) {
        String result = "";
        for (WordInFile word : type) {
            if (!word.getKey().getType().equals(WordType.MODIFIER)
                    && !word.getKey().getType().equals(WordType.STATEMENTORMODIFIER)) {
                List<String> modifiers = Arrays.asList("public", "static", "final", "abstract", "native", "protected",
                        "strictfp", "transient", "volatile", "synchronized");
                if (word.getWord() == null) {
                    result += word.getKey();
                } else if (!modifiers.contains(word.getWord())) {
                    result += word.getWord();
                }
            }
        }
        return result;
    }
}
