package plugin.model;

import java.util.List;

public class JavaClass extends JavaFileContent {
	private final String name;
	private final List<WordInFile> modifiers;
	private final KeyWord type;
	private List<String> extending;
	private List<String> implementing;

	public JavaClass(String name, List<WordInFile> content, KeyWord type, List<WordInFile> modifiers, List<String> extending,
			List<String> implementing) {
		super(content);
		this.name = name;
		this.type = type;
		this.modifiers = modifiers;
		this.extending = extending;
		this.implementing = implementing;
	}

	public List<WordInFile> getModifiers() {
		return modifiers;
	}

	public KeyWord getType() {
		return type;
	}

	public List<String> getExtending() {
		return extending;
	}

	public List<String> getImplementing() {
		return implementing;
	}

	public String getName() {
		return name;
	}
}
