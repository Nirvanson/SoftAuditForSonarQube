package plugin.model;

import java.util.List;

public class JavaMethod extends JavaClassContent{
	private final String name;
	private final List<String> parameters;
	
	public JavaMethod(int position, String name, List<String> parameters, List<JavaWord> body) {
		super(position, body);
		this.name = name;
		this.parameters = parameters;
	}

	public List<String> getParameters() {
		return parameters;
	}
	
	public String getName() {
		return name;
	}
}
