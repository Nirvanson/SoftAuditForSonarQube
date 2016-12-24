package plugin.model;

import java.util.List;

public class JavaStatement extends JavaFileContent {
	private final StatementType type;
	
	public JavaStatement(List<JavaFileContent> content, StatementType type) {
		super(content);
		this.type = type;
	}

	public StatementType getType() {
		return type;
	}
}
