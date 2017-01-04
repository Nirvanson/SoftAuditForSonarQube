package plugin.model;

import java.util.List;

public class JavaStatementWithAnonymousClass extends JavaStatement {
	private final List<WordInFile> statementBeforeClass;
	private List<WordInFile> statementAfterClass;
	private final List<WordInFile> classType;

	public JavaStatementWithAnonymousClass(List<WordInFile> statementBeforeClass, List<WordInFile> classType) {
		super(StatementType.UNSPECIFIED);
		this.statementBeforeClass = statementBeforeClass;
		this.classType = classType;
	}

	public List<WordInFile> getStatementBeforeClass() {
		return statementBeforeClass;
	}

	public List<WordInFile> getStatementAfterClass() {
		return statementAfterClass;
	}

	public void setStatementAfterClass(List<WordInFile> statementAfterClass) {
		this.statementAfterClass = statementAfterClass;
	}

	public List<WordInFile> getClassType() {
		return classType;
	}
}
