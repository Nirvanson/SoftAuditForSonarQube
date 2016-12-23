package plugin.model;

public class WordInFile {
	private final String word;
	private final KeyWord key;
	
	public WordInFile(String word, KeyWord key) {
		this.word = word;
		this.key = key;
	}
	
	@Override
	public String toString() {
		if (word == null) {
			return this.key.toString();
		}
		return this.getWord();
	}
	
	@Override
	public boolean equals(Object o) {
		if ( o instanceof WordInFile) {
			WordInFile test = (WordInFile) o;
			if ((test.getWord()==null && test.getKey().equals(this.key)) 
					|| (test.getWord()!=null && test.getWord().equals(this.word) && test.getKey().equals(this.key))) {
				return true;
			}
		} else if(o instanceof KeyWord ) {
			KeyWord test = (KeyWord) o;
			if (test.equals(this.key)) {
				return true;
			}
		}
		return false;
	}

	public String getWord() {
		return this.word;
	}

	public KeyWord getKey() {
		return this.key;
	}
}
