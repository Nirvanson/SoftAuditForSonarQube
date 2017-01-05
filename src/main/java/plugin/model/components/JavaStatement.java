package plugin.model.components;

import java.util.ArrayList;
import java.util.List;

import plugin.model.JavaFileContent;
import plugin.model.StatementType;
import plugin.model.WordInFile;

public class JavaStatement extends JavaFileContent {
	
	// the statementtext in single line statments without content
	private List<WordInFile> statementText;
	// Statementtype like return, import, if, ...
	private StatementType type;
	private List<JavaVariable> declaredVariables;
	private List<JavaVariable> referencedVariables;
	private List<WordInFile> calledMethods;

	public JavaStatement(StatementType type) {
		super(null);
		this.type = type;
		this.statementText = null;
		this.declaredVariables = new ArrayList<JavaVariable>();
		this.referencedVariables = new ArrayList<JavaVariable>();
		this.calledMethods = new ArrayList<WordInFile>();
	}

	public List<WordInFile> getStatementText() {
		return statementText;
	}

	public void setStatementText(List<WordInFile> statementText) {
		this.statementText = statementText;
	}

	public StatementType getType() {
		return type;
	}

	public void setStatementType(StatementType type) {
		this.type = type;
	}

	public List<JavaVariable> getDeclaredVariables() {
		return declaredVariables;
	}

	public void setDeclaredVariables(List<JavaVariable> declaredVariables) {
		this.declaredVariables = declaredVariables;
	}

	public List<JavaVariable> getReferencedVariables() {
		return referencedVariables;
	}

	public void setReferencedVariables(List<JavaVariable> referencedVariables) {
		this.referencedVariables = referencedVariables;
	}

	public List<WordInFile> getCalledMethods() {
		return calledMethods;
	}

	public void setCalledMethods(List<WordInFile> calledMethods) {
		this.calledMethods = calledMethods;
	}
}
