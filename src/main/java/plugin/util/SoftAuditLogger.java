package plugin.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import org.sonar.api.measures.Metric;

import plugin.model.AnalyzeTriple;
import plugin.model.JavaFileContent;
import plugin.model.StatementType;
import plugin.model.WordInFile;
import plugin.model.WordList;
import plugin.model.components.JavaClass;
import plugin.model.components.JavaControlStatement;
import plugin.model.components.JavaEnumValues;
import plugin.model.components.JavaMethod;
import plugin.model.components.JavaStatement;
import plugin.model.components.JavaStatementWithAnonymousClass;
import plugin.model.components.JavaVariable;

public class SoftAuditLogger {
    private static SoftAuditLogger logger;

    private final String FILE = "--- FILENAME --- Analyzed Filecontent:";
    private final String WORDLIST = "--- FILENAME --- Extracted wordlist:";
    private final String FILEMODEL = "--- FILENAME --- Generated Model:";
    private final String FILEMEASURES = "--- FILENAME --- Measured Values:";
    private final String MEASURES = "--- Project-level - Measured values:";
    private final String METRICS = "--- Project-level - Calculated Metrics:";

    private final int loglevel;
    private PrintWriter writer;

    private SoftAuditLogger(String filename, int loglevel) throws IOException {
        this.loglevel = loglevel;
        writer = new PrintWriter(new FileWriter(filename, true));
    }

    public static SoftAuditLogger getLogger(String filename, int loglevel) throws IOException {
        if (logger == null) {
            logger = new SoftAuditLogger(filename, loglevel);
        }
        return logger;
    }

    public static SoftAuditLogger getLogger() throws IOException {
        if (logger == null) {
            throw new IOException("Logger is not initialized!");
        }
        return logger;
    }

    public void printAnalysedFile(AnalyzeTriple<File, List<WordInFile>, List<JavaFileContent>> parsedFile,
            Map<Metric<?>, Double> measures) throws IOException {
        String filename = parsedFile.getFile().getName();
        printFile(filename, parsedFile.getFile());
        printWords(filename, parsedFile.getWordList());
        printModel(filename, parsedFile.getModel());
        printFileMeasures(filename, measures);
    }

    public void printCumulatedMeasures(Map<Metric<?>, Double> measures) {
        if (loglevel > 0) {
            writer.println(MEASURES);
            for (Metric<?> metric : measures.keySet()) {
                writer.println(metric.getName() + ": " + measures.get(metric));
            }
        }
    }

    public void printMetrics(Map<Metric<?>, Double> measures) {
        if (loglevel > 0) {
            writer.println(METRICS);
            for (Metric<?> metric : measures.keySet()) {
                writer.println(metric.getName() + ": " + measures.get(metric));
            }
        }
    }

    public void close() {
        if (logger!=null) {
            writer.close();
        }
        logger = null;
    }

    private void printFile(String filename, File file) throws IOException {
        if (loglevel > 4) {
            writer.println(FILE.replace("FILENAME", filename));
            BufferedReader br = new BufferedReader(new FileReader(file));
            String fileline;
            while ((fileline = br.readLine()) != null) {
                writer.println(fileline);
            }
            br.close();
        }
    }

    private void printWords(String filename, List<WordInFile> words) {
        if (loglevel > 3) {
            writer.println(WORDLIST.replace("FILENAME", filename));
            String sublist = "";
            for (WordInFile word : words) {
                if (sublist.length() > 100) {
                    writer.println(sublist);
                    sublist = "";
                }
                sublist += word.toString() + " ";
            }
        }
    }

    private void printModel(String filename, List<JavaFileContent> contents) {
        if (loglevel > 2) {
            writer.println(FILEMODEL.replace("FILENAME", filename));
            printFileContent(contents, 0);
        }
    }

    private void printFileMeasures(String filename, Map<Metric<?>, Double> measures) {
        if (loglevel > 1) {
            writer.println(FILEMEASURES.replace("FILENAME", filename));
            for (Metric<?> metric : measures.keySet()) {
                writer.println(metric.getName() + ": " + measures.get(metric));
            }
        }
    }

    private void printFileContent(List<JavaFileContent> contents, int level) {
        if (contents != null)
            for (JavaFileContent content : contents) {
                if (content instanceof JavaClass) {
                    JavaClass theClass = (JavaClass) content;
                    String classline = addTabs(level) + theClass.getType().toString().toUpperCase() + " with name: "
                            + theClass.getName();
                    if (!theClass.getExtending().isEmpty()) {
                        classline += " extending: " + theClass.getExtending();
                    }
                    if (!theClass.getImplementing().isEmpty()) {
                        classline += " implementing: " + theClass.getImplementing();
                    }
                    classline += " and Body:";
                    writer.println(classline);
                    printFileContent(theClass.getContent(), level + 1);
                } else if (content instanceof JavaMethod) {
                    JavaMethod theMethod = (JavaMethod) content;
                    String methodline = addTabs(level) + "METHOD";
                    if (theMethod.getContent() == null) {
                        methodline += "-interface";
                    }
                    methodline += " with name: " + theMethod.getName();
                    if (!theMethod.getReturntype().isEmpty()) {
                        methodline += ", Returntype: ";
                        for (WordInFile returnword : theMethod.getReturntype()) {
                            methodline += returnword + " ";
                        }
                    }
                    if (!theMethod.getParameters().isEmpty()) {
                        methodline += ", Parameters: ";
                        for (JavaVariable param : theMethod.getParameters()) {
                            for (WordInFile returnword : param.getType()) {
                                methodline += returnword + " ";
                            }
                            methodline += " " + param.getName();
                        }
                    }
                    if (theMethod.getContent() != null) {
                        methodline += " and ";
                        if (theMethod.getContent().isEmpty()) {
                            methodline += "empty Body";
                        } else {
                            methodline += "Body:";
                        }
                    }
                    writer.println(methodline);
                    if (theMethod.getContent() != null && !theMethod.getContent().isEmpty()) {
                        printFileContent(theMethod.getContent(), level + 1);
                    }
                } else if (content instanceof JavaControlStatement) {
                    JavaControlStatement statement = (JavaControlStatement) content;
                    String header = addTabs(level) + statement.getType() + " - ControlStatement / ";
                    if (statement.getDeclaredVariables() != null && !statement.getDeclaredVariables().isEmpty()) {
                        header += "declaring " + statement.getDeclaredVariables().size() + " variables / ";
                    }
                    if (statement.getReferencedVariables() != null && !statement.getReferencedVariables().isEmpty()) {
                        header += "referencing " + statement.getReferencedVariables().size() + " variables / ";
                    }
                    if (statement.getCalledMethods() != null && !statement.getCalledMethods().isEmpty()) {
                        header += "calling " + statement.getCalledMethods().size() + " methods / ";
                    }
                    if (statement.getType().equals(StatementType.SWITCH)) {
                        String condition = "";
                        for (WordInFile conditionWord : statement.getCondition()) {
                            condition += conditionWord + " ";
                        }
                        header += "over value of : (" + condition + ") and cases:";
                        writer.println(header);
                        printFileContent(statement.getContent(), level + 1);
                    } else if (statement.getType().equals(StatementType.CASE)) {
                        String condition = "";
                        for (WordInFile conditionWord : statement.getCondition()) {
                            condition += conditionWord + " ";
                        }
                        header += "for case: (" + condition + ") and content:";
                        writer.println(header);
                        printFileContent(statement.getContent(), level + 1);
                    } else if (statement.getType().equals(StatementType.BLOCK)) {
                        header += "(anonymous block):";
                        writer.println(header);
                        printFileContent(statement.getContent(), level + 1);
                    } else if (statement.getType().equals(StatementType.TRY)) {
                        if (statement.getResources() != null) {
                            header += "with resources:";
                            writer.println(header);
                            printFileContent(statement.getResources(), level + 1);
                            writer.println(addTabs(level) + "And try-block:");
                        } else {
                            header += "with try-block:";
                            writer.println(header);
                        }
                        printFileContent(statement.getContent(), level + 1);
                        for (List<WordInFile> exception : statement.getCatchedExceptions().keySet()) {
                            String condition = "";
                            for (WordInFile conditionWord : exception) {
                                condition += conditionWord + " ";
                            }
                            writer.println(addTabs(level) + "CATCH - (" + condition + ") And catch-block:");
                            printFileContent(statement.getCatchedExceptions().get(exception), level + 1);
                        }
                        if (statement.getOthercontent() != null) {
                            writer.println(addTabs(level) + "FINALLY - block:");
                            printFileContent(statement.getOthercontent(), level + 1);
                        }
                    } else if (statement.getType().equals(StatementType.IF)) {
                        String condition = "";
                        for (WordInFile conditionWord : statement.getCondition()) {
                            condition += conditionWord + " ";
                        }
                        header += "Checking condition: (" + condition + ") with if-block:";
                        writer.println(header);
                        printFileContent(statement.getContent(), level + 1);
                        if (statement.getOthercontent() != null) {
                            writer.println(addTabs(level) + "ELSE - block:");
                            printFileContent(statement.getOthercontent(), level + 1);
                        }
                    } else if (statement.getType().equals(StatementType.WHILE)
                            || statement.getType().equals(StatementType.DOWHILE)) {
                        String condition = "";
                        for (WordInFile conditionWord : statement.getCondition()) {
                            condition += conditionWord + " ";
                        }
                        header += "Checking condition: (" + condition + ") with content:";
                        writer.println(header);
                        printFileContent(statement.getContent(), level + 1);
                    } else if (statement.getType().equals(StatementType.FOR)) {
                        String condition = "";
                        for (WordInFile conditionWord : statement.getCondition()) {
                            condition += conditionWord + " ";
                        }
                        if (statement.getInitialization() != null) {
                            String init = "";
                            for (WordInFile conditionWord : statement.getInitialization()) {
                                init += conditionWord + " ";
                            }
                            String inc = "";
                            for (WordInFile conditionWord : statement.getIncrement()) {
                                inc += conditionWord + " ";
                            }
                            header += "With initialization: (" + init + ") termination: (" + condition
                                    + ") increment: (" + inc + ") and content:";
                        } else {
                            header += "Enhanced version scanning: (" + condition + ") with content:";
                        }
                        writer.println(header);
                        printFileContent(statement.getContent(), level + 1);
                    } else if (statement.getType().equals(StatementType.SYNCHRONIZED)) {
                        String condition = "";
                        for (WordInFile conditionWord : statement.getCondition()) {
                            condition += conditionWord + " ";
                        }
                        header += "Over lock: (" + condition + ") with content:";
                        writer.println(header);
                        printFileContent(statement.getContent(), level + 1);
                    } else {
                        writer.println(header + ":" + statement.getStatementText());
                    }
                } else if (content instanceof JavaStatementWithAnonymousClass) {
                    JavaStatementWithAnonymousClass statement = (JavaStatementWithAnonymousClass) content;
                    String classline = addTabs(level) + statement.getType() + " - Statement with anonymous class";
                    if (statement.getDeclaredVariables() != null && !statement.getDeclaredVariables().isEmpty()) {
                        classline += " / declaring " + statement.getDeclaredVariables().size() + " variables";
                    }
                    if (statement.getReferencedVariables() != null && !statement.getReferencedVariables().isEmpty()) {
                        classline += " / referencing " + statement.getReferencedVariables().size() + " variables";
                    }
                    if (statement.getCalledMethods() != null && !statement.getCalledMethods().isEmpty()) {
                        classline += " / calling " + statement.getCalledMethods().size() + " methods";
                    }
                    classline += ": '";
                    for (WordInFile word : statement.getStatementBeforeClass()) {
                        classline += word + " ";
                    }
                    classline += "' of Type: " + statement.getClassType() + " with content: ";
                    writer.println(classline);
                    printFileContent(statement.getContent(), level + 1);
                    classline = addTabs(level) + "Ending with: ";
                    for (WordInFile word : statement.getStatementAfterClass()) {
                        classline += word + " ";
                    }
                    writer.println(classline);
                } else if (content instanceof JavaStatement) {
                    JavaStatement statement = (JavaStatement) content;
                    if (statement.getType().equals(StatementType.ANNOTATION)) {
                        writer.println(addTabs(level) + statement.getType());
                    } else {
                        String header = addTabs(level) + statement.getType() + " - Statement";
                        if (statement.getDeclaredVariables() != null && !statement.getDeclaredVariables().isEmpty()) {
                            header += " / declaring " + statement.getDeclaredVariables().size() + " variables";
                        }
                        if (statement.getReferencedVariables() != null
                                && !statement.getReferencedVariables().isEmpty()) {
                            header += " / referencing " + statement.getReferencedVariables().size() + " variables";
                        }
                        if (statement.getCalledMethods() != null && !statement.getCalledMethods().isEmpty()) {
                            header += " / calling " + statement.getCalledMethods().size() + " methods";
                        }
                        if (statement.getStatementText() != null && !statement.getStatementText().isEmpty()) {
                            writer.println(header + ":" + statement.getStatementText());
                        } else {
                            writer.println(header);
                        }
                    }
                } else if (content instanceof JavaEnumValues) {
                    JavaEnumValues values = (JavaEnumValues) content;
                    writer.println(addTabs(level) + "Enum-Values: ");
                    for (List<WordInFile> value : values.getValues()) {
                        writer.println(addTabs(level + 1) + value);
                    }
                } else if (content instanceof WordList) {
                    WordList wordlist = (WordList) content;
                    writer.println(addTabs(level) + "Wordlist with length: " + wordlist.getWordlist().size()
                            + " and content: " + wordlist.getWordlist());
                }
            }
    }

    private String addTabs(int inputlevel) {
        int level = inputlevel;
        String tabs = "";
        while (level > 0) {
            tabs += "	";
            level--;
        }
        return tabs;
    }
}
