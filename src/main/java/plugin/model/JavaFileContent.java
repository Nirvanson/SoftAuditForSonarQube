package plugin.model;

import java.util.List;

public abstract class JavaFileContent {
	private List<JavaFileContent> content;
	
	public JavaFileContent(List<JavaFileContent> content) {
		this.content = content;
	}
	
	public List<JavaFileContent> getContent() {
		return this.content;
	}
	
	public void setContent(List<JavaFileContent> content) {
		this.content = content;
	}
}
