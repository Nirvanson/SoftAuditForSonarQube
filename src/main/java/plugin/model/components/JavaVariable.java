package plugin.model.components;

import java.util.List;

import plugin.model.JavaFileContent;
import plugin.model.WordInFile;

public class JavaVariable extends JavaFileContent{
	private final String name;
	private final List<WordInFile> type;
	private String value;

	public JavaVariable(String name, List<WordInFile> type) {
		super(null);
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public List<WordInFile> getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
