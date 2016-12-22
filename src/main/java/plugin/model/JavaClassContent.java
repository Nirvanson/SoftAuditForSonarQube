package plugin.model;

import java.util.List;

public class JavaClassContent {
	private final int position;
	private List<JavaWord> content;
	
	public JavaClassContent(int position, List<JavaWord> content) {
		this.position = position;
		this.content = content;
	}

	public int getPosition() {
		return this.position;
	}
	
	public List<JavaWord> getContent() {
		return this.content;
	}
	
	public void setContent(List<JavaWord> content) {
		this.content = content;
	}
}
