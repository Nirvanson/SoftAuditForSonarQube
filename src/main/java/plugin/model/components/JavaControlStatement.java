package plugin.model.components;

import java.util.List;
import java.util.Map;

import plugin.model.JavaFileContent;
import plugin.model.StatementType;
import plugin.model.WordInFile;

public class JavaControlStatement extends JavaStatement {
	
	// content of JavaFileContent is the block inside ifs, fors, whiles, trys, cases, and blocks
	// the list of cases in switches
	// or the statement itself in one-line statements (import-, package-, return-statements, ...)
	
	// condition in while and if, termination in for, declaration in enhanced for, switchvariable in switch and value of switchvariable in case
	private List<WordInFile> condition;
	// in for loop
	private List<WordInFile> initialization;
	// increment in for
	private List<WordInFile> increment;
	// else block in if, catch block in try
	private List<JavaFileContent> othercontent;
	// resources in try with resources
	private List<JavaFileContent> resources;
	// list of catched Exceptions with catchblock
	private Map<List<WordInFile>, List<JavaFileContent>> catchedExceptions;
	
	public JavaControlStatement(StatementType type) {
		super(type);
		condition = null;
		initialization = null;
		increment = null;
		othercontent = null;
		setCatchedExceptions(null);
		setResources(null);
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

	public List<JavaFileContent> getOthercontent() {
		return othercontent;
	}

	public void setOthercontent(List<JavaFileContent> elsecontent) {
		this.othercontent = elsecontent;
	}

	public List<JavaFileContent> getResources() {
		return resources;
	}

	public void setResources(List<JavaFileContent> resources) {
		this.resources = resources;
	}

	public Map<List<WordInFile>, List<JavaFileContent>> getCatchedExceptions() {
		return catchedExceptions;
	}

	public void setCatchedExceptions(Map<List<WordInFile>, List<JavaFileContent>> catchedExceptions) {
		this.catchedExceptions = catchedExceptions;
	}
}