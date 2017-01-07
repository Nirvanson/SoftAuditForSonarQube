package plugin.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.sonar.api.measures.Metric;

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

public class Logger {
	private static Logger logger;
	
	private final String FILE_HEAD = "---------- File: FILENAME ----------";
	private final String STEP_1 = "*** Step 1 - Extract normalized Lines";
	private final String STEP_2 = "*** Step 2 - Transform lines to single string";
	private final String STEP_3 = "*** Step 3 - Extract words from code";
	private final String STEP_4 = "*** Step 4 - Count key words in word list for simple measures";
	private final String STEP_5 = "*** Step 5 - Build basic model of the file from wordlist";
	private final String STEP_6 = "*** Step 6 - Recursivly refine codemodel";
	private final String STEP_7 = "*** Step 7 - Count methods and parameters in model";
	private final String STEP_8 = "*** Step 8 - Expand model with structural statements (ifs, loops, ...)";
	private final String STEP_9 = "*** Step 9 - Split remaining wordlists to statements";
	private final String TIME = "*** Finished at TIME";
	private final String INPUT_ERROR = "Method METHODNAME of Logger recieved invalid input: PARAM";
	
	private final int loglevel;
	private PrintWriter writer;
		
	private Logger() {
		loglevel = 2;
		try {
			writer = new PrintWriter("C:\\Beleg/plugin-log.log", "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Logger getLogger() {
		if(logger == null) 
			logger = new Logger();
		return logger; 
	}
	
	public void printFile(File file) {
		writer.println(FILE_HEAD.replace("FILENAME", file.getName()));
		if (loglevel > 3) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				String fileline;
				while ((fileline = br.readLine()) != null) {
					writer.println(fileline);
				}
				br.close();
			} catch (IOException e) {
				writer.println(INPUT_ERROR.replace("METHODNAME", "printFile").replace("PARAM", file.toString()));
			}
		}
	}
	
	public void printNormalizedLines(List<String> lines) {
		writer.println(STEP_1);
		addTime();
		if (loglevel > 2) {
			for (String line : lines) {
				writer.println(line);
			}
		}
	}
	
	public void printSingleLineCode(String code) {
		writer.println(STEP_2);
		addTime();
		if (loglevel > 2) {
			for (String chunk : code.split("(?<=\\G.{100})")) {
				writer.println(chunk);
			}
		}
	}
	
	public void printWords(List<WordInFile> words) {
		writer.println(STEP_3);
		addTime();
		if (loglevel > 2) {
			String sublist = "";
			for (WordInFile word : words) {
				if (sublist.length()>100) {
					writer.println(sublist);
					sublist = "";
				}
				sublist += word.toString() + " ";
			}
		}
	}
	
	public void printMeasures(String step, Map<Metric<Integer>, Double> measures) {
		switch(step) {
		case "keyword":
			writer.println(STEP_4);
			break;
		case "method":
			writer.println(STEP_7);
			break;
		default:
			writer.println(INPUT_ERROR.replace("METHODNAME", "printMeasures").replace("PARAM", step));
			break;
		}
		addTime();
		if (loglevel > 0) {
			for (Metric<Integer> measure : measures.keySet()) {
				writer.println(measure.getName() + ": " + measures.get(measure));
			}
		}
	}
	
	public void printModel(String step, List<JavaFileContent> contents) {
		int levelToLog = 1;
		switch(step) {
		case "class":
			writer.println(STEP_5);
			levelToLog = 2;
			break;
		case "refined":
			writer.println(STEP_6);
			levelToLog = 2;
			break;
		case "expanded":
			writer.println(STEP_8);
			levelToLog = 2;
			break;
		case "full":
			writer.println(STEP_9);
			break;
		default:
			writer.println(INPUT_ERROR.replace("METHODNAME", "printModel").replace("PARAM", step));
			break;
		}
		addTime();
		if (loglevel > levelToLog) {
			printFileContent(contents, 0);
		}
	}

	private void printFileContent(List<JavaFileContent> contents, int level) {
		if (contents!=null)
		for (JavaFileContent content : contents) {
			if (content instanceof JavaClass) {
				JavaClass theClass = (JavaClass) content;
				String classline = addTabs(level) + theClass.getType().toString().toUpperCase() + " with name: " + theClass.getName();
				if (!theClass.getExtending().isEmpty()) {
					classline += " extending: " + theClass.getExtending();
				}
				if (!theClass.getImplementing().isEmpty()) {
					classline += " implementing: " + theClass.getImplementing();
				}
				classline += " and Body:";
				writer.println(classline);
				printFileContent(theClass.getContent(), level+1);
			} else if (content instanceof JavaMethod) {
				JavaMethod theMethod = (JavaMethod) content;
				String methodline = addTabs(level) + "METHOD";
				if (theMethod.getContent()==null) {
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
				if (theMethod.getContent()!=null) {
					methodline += " and ";
					if (theMethod.getContent().isEmpty()) {
						methodline += "empty Body";
					} else {
						methodline += "Body:";
					}
				}
				writer.println(methodline);
				if (theMethod.getContent()!=null && !theMethod.getContent().isEmpty()) {
					printFileContent(theMethod.getContent(), level+1);
				}
			} else if (content instanceof JavaControlStatement) {
				JavaControlStatement statement = (JavaControlStatement) content;
				String header = addTabs(level) + statement.getType() + " - ControlStatement ";
				if (statement.getType().equals(StatementType.SWITCH)) {
					String condition = "";
					for (WordInFile conditionWord : statement.getCondition()) {
						condition += conditionWord + " ";
					}
					header += "over value of : (" + condition + ") and cases:";
					writer.println(header);
					printFileContent(statement.getContent(), level+1);
				} else if (statement.getType().equals(StatementType.CASE)) {
					String condition = "";
					for (WordInFile conditionWord : statement.getCondition()) {
						condition += conditionWord + " ";
					}
					header += "for case: (" + condition + ") and content:";
					writer.println(header);
					printFileContent(statement.getContent(), level+1);
				} else if (statement.getType().equals(StatementType.BLOCK)) {
					header += "(anonymous block):";
					writer.println(header);
					printFileContent(statement.getContent(), level+1);
				} else if (statement.getType().equals(StatementType.TRY)) {
					if (statement.getResources()!=null) {
						header += "with resources:";
						writer.println(header);
						printFileContent(statement.getResources(), level+1);
						writer.println(addTabs(level) + "And try-block:");
					} else {
						header += "with try-block:";
						writer.println(header);
					}
					printFileContent(statement.getContent(), level+1);
					for (List<WordInFile> exception : statement.getCatchedExceptions().keySet()) {
						String condition = "";
						for (WordInFile conditionWord : exception) {
							condition += conditionWord + " ";
						}
						writer.println(addTabs(level) + "CATCH - (" + condition + ") And catch-block:");
						printFileContent(statement.getCatchedExceptions().get(exception), level+1);
					}
					if (statement.getOthercontent()!=null) {
						writer.println(addTabs(level) + "FINALLY - block:");
						printFileContent(statement.getOthercontent(), level+1);
					}
				} else if (statement.getType().equals(StatementType.IF)) {
					String condition = "";
					for (WordInFile conditionWord : statement.getCondition()) {
						condition += conditionWord + " ";
					}
					header += "Checking condition: (" + condition + ") with if-block:";
					writer.println(header);
					printFileContent(statement.getContent(), level+1);
					if (statement.getOthercontent()!=null) {
						writer.println(addTabs(level) + "ELSE - block:");
						printFileContent(statement.getOthercontent(), level+1);
					}
				} else if (statement.getType().equals(StatementType.WHILE)) {
					String condition = "";
					for (WordInFile conditionWord : statement.getCondition()) {
						condition += conditionWord + " ";
					}
					header += "Checking condition: (" + condition + ") with content:";
					writer.println(header);
					printFileContent(statement.getContent(), level+1);
				} else if (statement.getType().equals(StatementType.FOR)) {
					String condition = "";
					for (WordInFile conditionWord : statement.getCondition()) {
						condition += conditionWord + " ";
					}
					if (statement.getInitialization()!=null) {
						String init = "";
						for (WordInFile conditionWord : statement.getInitialization()) {
							init += conditionWord + " ";
						}
						String inc = "";
						for (WordInFile conditionWord : statement.getIncrement()) {
							inc += conditionWord + " ";
						}
						header += "With initialization: (" + init + ") termination: (" + condition + ") increment: (" + inc + ") and content:";
					} else {
						header += "Enhanced version scanning: (" + condition + ") with content:";
					}
					writer.println(header);
					printFileContent(statement.getContent(), level+1);
				} else if (statement.getType().equals(StatementType.SYNCHRONIZED)) {
					String condition = "";
					for (WordInFile conditionWord : statement.getCondition()) {
						condition += conditionWord + " ";
					}
					header += "Over lock: (" + condition + ") with content:";
					writer.println(header);
					printFileContent(statement.getContent(), level+1);
				} else {
					writer.println(header + ":" + statement.getStatementText());
				} 
			} else if (content instanceof JavaStatementWithAnonymousClass) {
				JavaStatementWithAnonymousClass statement = (JavaStatementWithAnonymousClass) content;
				String classline = addTabs(level) + statement.getType() + " - Statement with anonymous class: '";
				for (WordInFile word : statement.getStatementBeforeClass()) {
					classline += word + " ";
				}
				classline += "' of Type: " + statement.getClassType() + " with content: ";
				writer.println(classline);
				printFileContent(statement.getContent(), level+1);
				classline = addTabs(level) + "Ending with: ";
				for (WordInFile word : statement.getStatementAfterClass()) {
					classline += word + " ";
				}
				writer.println(classline);
			} else if (content instanceof JavaStatement) {
				JavaStatement statement = (JavaStatement) content;
				String header = addTabs(level) + statement.getType() + " - Statement ";
				if (statement.getStatementText()!=null && !statement.getStatementText().isEmpty()) {
					writer.println(header + ":" + statement.getStatementText());
				} else {
					writer.println(header);
				}
			} else if (content instanceof JavaEnumValues){
				JavaEnumValues values = (JavaEnumValues) content;
				writer.println(addTabs(level) + "Enum-Values: ");
				for (List<WordInFile> value : values.getValues()) {
					writer.println(addTabs(level+1) + value);
				}
			} else if (content instanceof WordList){
				WordList wordlist = (WordList) content;
				writer.println(addTabs(level) + "Wordlist with length: " + wordlist.getWordlist().size() + " and content: " + wordlist.getWordlist());
			} else {
				writer.println(INPUT_ERROR.replace("METHODNAME", "printFileContent").replace("PARAM", content.toString()));
			}
		}
	}

	public void close() {
		writer.close();
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

	private void addTime() {
		writer.println(TIME.replaceAll("TIME", (new SimpleDateFormat("HH:mm:ss:SSS")).format(new Date())));		
	}
}
