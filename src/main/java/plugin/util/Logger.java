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

import plugin.model.JavaClass;
import plugin.model.JavaFileContent;
import plugin.model.JavaMethod;
import plugin.model.JavaStatement;
import plugin.model.WordInFile;
import plugin.model.WordList;

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
	private final String TIME = "*** Finished at TIME";
	private final String INPUT_ERROR = "Method METHODNAME of Logger recieved invalid input: PARAM";
	
	private final int loglevel;
	private PrintWriter writer;
		
	private Logger() {
		loglevel = 2;
		try {
			writer = new PrintWriter("D:\\plugin-log.log", "UTF-8");
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
		switch(step) {
		case "class":
			writer.println(STEP_5);
			break;
		case "refined":
			writer.println(STEP_6);
			break;
		default:
			writer.println(INPUT_ERROR.replace("METHODNAME", "printModel").replace("PARAM", step));
			break;
		}
		addTime();
		if (loglevel > 1) {
			printFileContent(contents, 0);
		}
	}

	private void printFileContent(List<JavaFileContent> contents, int level) {
		for (JavaFileContent content : contents) {
			if (content instanceof JavaClass) {
				JavaClass theClass = (JavaClass) content;
				writer.println(addTabs(level) + "Class with name: " + theClass.getName() + " extending: "
						+ theClass.getExtending() + " implementing: "
						+ theClass.getImplementing() + " and Body:");
				printFileContent(theClass.getContent(), level+1);
			} else if (content instanceof JavaMethod) {
				JavaMethod theMethod = (JavaMethod) content;
				writer.println(addTabs(level) + "Method with name: " + theMethod.getName()
						+ " and Parameters: " + theMethod.getParameters());
				printFileContent(theMethod.getContent(), level+1);
			} else if (content instanceof JavaStatement) {
				JavaStatement statement = (JavaStatement) content;
				writer.println(addTabs(level) + "Statement of type: " + statement.getType());
			} else if (content instanceof WordList){
				WordList wordlist = (WordList) content;
				writer.println(addTabs(level) + "Wordlist with length: " + wordlist.getWordlist().size());
				writer.println(addTabs(level) + wordlist.getWordlist());
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
