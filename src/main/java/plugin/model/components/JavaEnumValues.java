package plugin.model.components;

import java.util.List;

import plugin.model.JavaFileContent;
import plugin.model.WordInFile;

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
