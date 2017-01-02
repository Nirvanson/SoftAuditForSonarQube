package plugin.model;

import java.util.List;

public class JavaEnumValues extends JavaFileContent {
	private List<List<WordInFile>> values;
	
	public JavaEnumValues(List<List<WordInFile>> values) {
		super(null);
		this.setValues(values);
	}

	public List<List<WordInFile>> getValues() {
		return values;
	}

	public void setValues(List<List<WordInFile>> values) {
		this.values = values;
	}
}
