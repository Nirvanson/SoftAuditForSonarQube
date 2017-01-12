package plugin.analyser;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.sonar.api.measures.Metric;

import plugin.SoftAuditMetrics;
import plugin.model.JavaFileContent;
import plugin.model.WordInFile;
import plugin.model.components.JavaClass;
import plugin.model.components.JavaControlStatement;
import plugin.model.components.JavaMethod;
import plugin.model.components.JavaStatement;
import plugin.model.components.JavaStatementWithAnonymousClass;
import plugin.model.components.JavaVariable;
import plugin.util.ParsingException;
import plugin.model.KeyWord;

/**
 * Java File Parser to extract needed measures for SoftAudit Metrics.
 *
 * @author Jan Rucks (jan.rucks@gmx.de)
 * @version 0.3
 */
public class ModelAnalyser {

    public static Set<String> collectDeclaredVariables(List<JavaFileContent> contents) throws ParsingException {
        Set<String> result = new HashSet<String>();
        if (contents == null || contents.isEmpty()) {
            return result;
        }
        for (JavaFileContent content : contents) {
            if (content instanceof JavaClass) {
                result.addAll(collectDeclaredVariables(content.getContent()));
            } else if (content instanceof JavaMethod) {
                result.addAll(collectDeclaredVariables(content.getContent()));
                if (((JavaMethod) content).getParameters() != null) {
                    for (JavaVariable var : ((JavaMethod) content).getParameters()) {
                        result.add(var.getName());
                    }
                }
            } else if (content instanceof JavaStatementWithAnonymousClass) {
                if (((JavaStatement) content).getDeclaredVariables() != null) {
                    for (JavaVariable var : ((JavaStatement) content).getDeclaredVariables()) {
                        result.add(var.getName());
                    }
                }
                result.addAll(collectDeclaredVariables(content.getContent()));
            } else if (content instanceof JavaControlStatement) {
                JavaControlStatement theStatement = (JavaControlStatement) content;
                switch (theStatement.getType()) {
                case SWITCH:
                case WHILE:
                case SYNCHRONIZED:
                case FOR:
                case BLOCK:
                case CASE:
                    if (theStatement.getDeclaredVariables() != null) {
                        for (JavaVariable var : theStatement.getDeclaredVariables()) {
                            result.add(var.getName());
                        }
                    }
                    result.addAll(collectDeclaredVariables(content.getContent()));
                    break;
                case IF:
                    if (theStatement.getDeclaredVariables() != null) {
                        for (JavaVariable var : theStatement.getDeclaredVariables()) {
                            result.add(var.getName());
                        }
                    }
                    result.addAll(collectDeclaredVariables(content.getContent()));
                    if (theStatement.getOthercontent() != null) {
                        result.addAll(collectDeclaredVariables(theStatement.getOthercontent()));
                    }
                    break;
                case TRY:
                    if (theStatement.getDeclaredVariables() != null) {
                        for (JavaVariable var : theStatement.getDeclaredVariables()) {
                            result.add(var.getName());
                        }
                    }
                    result.addAll(collectDeclaredVariables(content.getContent()));
                    if (theStatement.getOthercontent() != null) {
                        result.addAll(collectDeclaredVariables(theStatement.getOthercontent()));
                    }
                    if (theStatement.getResources() != null) {
                        result.addAll(collectDeclaredVariables(theStatement.getResources()));
                    }
                    if (theStatement.getCatchedExceptions() != null) {
                        for (List<WordInFile> exception : theStatement.getCatchedExceptions().keySet()) {
                            result.addAll(collectDeclaredVariables(theStatement.getCatchedExceptions().get(exception)));
                        }
                    }
                    break;
                case RETURN:
                case ASSERT:
                case THROW:
                case BREAK:
                case CONTINUE:
                    if (theStatement.getDeclaredVariables() != null) {
                        for (JavaVariable var : theStatement.getDeclaredVariables()) {
                            result.add(var.getName());
                        }
                    }
                    break;
                default:
                    throw new ParsingException("Unknown Model-Component!");
                }
            } else if (content instanceof JavaStatement) {
                JavaStatement theStatement = (JavaStatement) content;
                if (theStatement.getDeclaredVariables() != null) {
                    for (JavaVariable var : theStatement.getDeclaredVariables()) {
                        result.add(var.getName());
                    }
                }
            }
        }
        return result;
    }

    /**
     * Count methods and their parameters
     * 
     * @param contents - the JavaClassContents of the file
     * @returns result-map
     */
    public static Map<Metric<Integer>, Double> countMethods(List<JavaFileContent> contents) {
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
     * @param words - the words of the file to analyze
     * @returns result-map
     */
    public static Map<Metric<Integer>, Double> countKeyWords(List<WordInFile> words) {
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

    private static double countKey(List<WordInFile> words, KeyWord key) {
        return (double) Collections.frequency(words, new WordInFile(null, key));
    }
}
