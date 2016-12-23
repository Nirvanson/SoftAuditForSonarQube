package plugin.model;

import java.util.List;

public class JavaFileContent {
	private List<?> content;
	
	public JavaFileContent(List<?> content) {
		this.content = content;
	}
	
	public List<?> getContent() {
		return this.content;
	}
	
	public void setContent(List<?> content) {
		this.content = content;
	}
}
