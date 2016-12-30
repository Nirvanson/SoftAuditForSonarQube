package plugin.model;

import java.util.List;

public class JavaStatement extends JavaFileContent {
	
	// content of JavaFileContent is the block inside ifs, fors, whiles, trys, cases, and blocks
	// the list of cases in switches
	// or the statement itself in one-line statements (import-, package-, return-statements, ...)
	
	// Statementtype like return, import, if, ...
	private final StatementType type;
	// condition in while and if, termination in for, declaration in enhanced for
	private List<WordInFile> condition;
	// in for loop
	private List<WordInFile> initialization;
	// increment in for
	private List<WordInFile> increment;
	// else block in if, catch block in try
	private List<JavaFileContent> elsecontent;
	// finally block in try
	private List<JavaFileContent> finallycontent;
	
	public JavaStatement(List<JavaFileContent> content, StatementType type) {
		super(content);
		this.type = type;
		condition = null;
		initialization = null;
		increment = null;
		elsecontent = null;
		finallycontent = null;
	}

	public StatementType getType() {
		return type;
	}

	public List<WordInFile> getCondition() {
		return condition;
	}

	public void setCondition(List<WordInFile> condition) {
		this.condition = condition;
	}

	public List<WordInFile> getInitialization() {
		return initialization;
	}

	public void setInitialization(List<WordInFile> initialization) {
		this.initialization = initialization;
	}

	public List<WordInFile> getIncrement() {
		return increment;
	}

	public void setIncrement(List<WordInFile> increment) {
		this.increment = increment;
	}

	public List<JavaFileContent> getElsecontent() {
		return elsecontent;
	}

	public void setElsecontent(List<JavaFileContent> elsecontent) {
		this.elsecontent = elsecontent;
	}

	public List<JavaFileContent> getFinallycontent() {
		return finallycontent;
	}

	public void setFinallycontent(List<JavaFileContent> finallycontent) {
		this.finallycontent = finallycontent;
	}
}
