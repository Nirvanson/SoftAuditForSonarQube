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

/**
 * Prints all steps of parsing, analyzing and calculating to log-file.
 *
 * @author Jan Rucks
 * @version 1.0
 */
public class SoftAuditLogger {
    /** The singleton instance. */
    private static SoftAuditLogger logger;

    /** Headline for step 1: printing source-file-content. */
    private final String FILE = "--- FILENAME --- Analyzed Filecontent:";
    /** Headline for step 2: printing extracted word-list. */
    private final String WORDLIST = "--- FILENAME --- Extracted wordlist:";
    /** Headline for step 3: printing generated file-model. */
    private final String FILEMODEL = "--- FILENAME --- Generated Model:";
    /** Headline for step 4: printing measured values for file. */
    private final String FILEMEASURES = "--- FILENAME --- Measured Values:";
    /** Headline for step 5: printing accumulated measures on project-level. */
    private final String MEASURES = "--- Project-level - Measured values:";
    /** Headline for step 6: printing accumulated measures on project-level. */
    private final String SONARMEASURES = "--- Project-level - Measures provided by SonarQube:";
    /** Headline for step 7: printing calculated metrics on project level. */
    private final String METRICS = "--- Project-level - Calculated Metrics:";

    /** The log-level that determines which steps are printed. */
    private final int loglevel;
    /** The writer. */
    private PrintWriter writer;

    /**
     * The Constructor, initializing writer.
     * 
     * @param filename - full name of the log-file with path
     * @param loglevel
     * @throws IOException - if file creation fails
     */
    private SoftAuditLogger(String filename, int loglevel) throws IOException {
        this.loglevel = loglevel;
        writer = new PrintWriter(new FileWriter(filename, true));
    }

    /**
     * Get singleton instance, used for initialization.
     * 
     * @param filename - full name of the log-file with path
     * @param loglevel
     * @throws IOException - if file creation fails
     */
    public static SoftAuditLogger getLogger(String filename, int loglevel) throws IOException {
        if (logger == null) {
            logger = new SoftAuditLogger(filename, loglevel);
        }
        return logger;
    }

    /**
     * Get singleton instance, used after initialization.
     * 
     * @param filename - full name of the log-file with path
     * @param loglevel
     * @throws IOException - if file creation fails
     */
    public static SoftAuditLogger getLogger() throws IOException {
        if (logger == null) {
            throw new IOException("Logger is not initialized!");
        }
        return logger;
    }

    /**
     * Print steps 1 - 4 for single source file.
     * 
     * @param parsedFile - parsing result of file
     * @param measures - retrieved measures
     * @throws IOException - if file access fails
     */
    public void printAnalysedFile(AnalyzeTriple<File, List<WordInFile>, List<JavaFileContent>> parsedFile,
            Map<Metric<?>, Double> measures) throws IOException {
        String filename = parsedFile.getFile().getName();
        printFile(filename, parsedFile.getFile());
        printWords(filename, parsedFile.getWordList());
        printModel(filename, parsedFile.getModel());
        printFileMeasures(filename, measures);
    }

    /**
     * Print step 5 on project level.
     * 
     * @param measures - accumulated measures
     */
    public void printCumulatedMeasures(Map<Metric<?>, Double> measures) {
        if (loglevel > 0) {
            writer.println(MEASURES);
            for (Metric<?> metric : measures.keySet()) {
                writer.println(metric.getName() + ": " + measures.get(metric));
            }
        }
    }
    
    /**
     * Print step 6 on project level.
     * 
     * @param measures - measures provided by sonarqube
     */
    public void printSonarMeasures(Map<Metric<?>, Double> measures) {
        if (loglevel > 0) {
            writer.println(SONARMEASURES);
            for (Metric<?> metric : measures.keySet()) {
                writer.println(metric.getName() + ": " + measures.get(metric));
            }
        }
    }

    /**
     * Print step 7 on project level.
     * 
     * @param metrics - calculated metrics
     */
    public void printMetrics(Map<Metric<?>, Double> metrics) {
        if (loglevel > 0) {
            writer.println(METRICS);
            for (Metric<?> metric : metrics.keySet()) {
                writer.println(metric.getName() + ": " + metrics.get(metric));
            }
        }
    }

    /**
     * Close writer and set singleton instance to null.
     */
    public void close() {
        if (logger != null) {
            writer.close();
        }
        logger = null;
    }

    /**
     * Print step 1 - print source-file to log.
     * 
     * @param filename - the file name for header
     * @param file - the file to print
     * @throws IOException - if file access fails
     */
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

    /**
     * Print step 2 - print word-list to log.
     * 
     * @param filename - the file name for header
     * @param words - the the extracted word-list
     */
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

    /**
     * Print step 3 - print generated file-model to log.
     * 
     * @param filename - the file name for header
     * @param contents - the file-model
     */
    private void printModel(String filename, List<JavaFileContent> contents) {
        if (loglevel > 2) {
            writer.println(FILEMODEL.replace("FILENAME", filename));
            printFileContent(contents, 0);
        }
    }

    /**
     * Print step 4 - print measured values of file to log.
     * 
     * @param filename - the file name for header
     * @param measures - the the extracted measures
     */
    private void printFileMeasures(String filename, Map<Metric<?>, Double> measures) {
        if (loglevel > 1) {
            writer.println(FILEMEASURES.replace("FILENAME", filename));
            for (Metric<?> metric : measures.keySet()) {
                writer.println(metric.getName() + ": " + measures.get(metric));
            }
        }
    }

    /**
     * Recursive printing of file-model-component-lists.
     * 
     * @param contents - the list of model-components
     * @param level - recursion-level for indent
     */
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

    /**
     * Add indent to a String depending on recursion-level.
     * 
     * @param inputlevel - recursion-level for indent
     * @return String with tabs
     */
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
