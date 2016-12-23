package plugin.model;

import java.util.List;

public class JavaMethod extends JavaFileContent{
	private final String name;
	private final List<String> parameters;
	
	public JavaMethod(String name, List<String> parameters, List<?> body) {
		super(body);
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
