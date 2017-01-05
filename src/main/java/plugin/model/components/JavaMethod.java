package plugin.model.components;

import java.util.List;

import plugin.model.JavaFileContent;
import plugin.model.WordInFile;

public class JavaMethod extends JavaFileContent{
	private final String name;
	private final List<WordInFile> modifiers;
	private final List<JavaVariable> parameters;
	private final List<WordInFile> returntype;
	
	public JavaMethod(String name, List<WordInFile> returntype, List<WordInFile> modifiers, List<JavaVariable> parameters, List<JavaFileContent> body) {
		super(body);
		this.returntype = returntype;
		this.modifiers = modifiers;
		this.name = name;
		this.parameters = parameters;
	}

	public List<JavaVariable> getParameters() {
		return parameters;
	}
	
	public String getName() {
		return name;
	}

	public List<WordInFile> getModifiers() {
		return modifiers;
	}

	public List<WordInFile> getReturntype() {
		return returntype;
	}
}
