package plugin.model;

import java.util.List;

public class WordList extends JavaFileContent {
	private List<WordInFile> wordlist;
	
	public WordList(List<WordInFile> wordlist) {
		super(null);
		this.setWordlist(wordlist);
	}

	public List<WordInFile> getWordlist() {
		return wordlist;
	}

	public void setWordlist(List<WordInFile> wordlist) {
		this.wordlist = wordlist;
	}
}
