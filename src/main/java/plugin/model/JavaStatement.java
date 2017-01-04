package plugin.model;

import java.util.List;

public class JavaStatement extends JavaFileContent {
	
	// the statementtext in single line statments without content
	private List<WordInFile> statementText;
	// Statementtype like return, import, if, ...
	private StatementType type;

	public JavaStatement(StatementType type) {
		super(null);
		this.type = type;
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
}
