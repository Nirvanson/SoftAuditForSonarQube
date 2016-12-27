package plugin.analyser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import plugin.model.JavaFileContent;
import plugin.model.JavaStatement;
import plugin.model.KeyWord;
import plugin.model.StatementType;
import plugin.model.WordInFile;
import plugin.model.WordList;

public class JavaModelExpander {

	public List<JavaFileContent> addStructuralStatements(List<WordInFile> content) {
		/*int i=0;
		List<Integer> intlist = new ArrayList<Integer>();
		{ }
		do {i++;} while (i<0);
		while (i<0) {i++;}
		for (@Something int j=0; j<i; j++)
			{i++;}
		for (@Something Integer k : intlist) {k++;}
		if (i==0) i++; else if (i>9) {i--;}
		try {xyz();} catch (@Something Exception e) {i--;} finally {i++;}*/
		List<JavaFileContent> result = new ArrayList<JavaFileContent>();
		List<WordInFile> otherContent = new ArrayList<WordInFile>();
		List<KeyWord> keywords = Arrays.asList(KeyWord.DO, KeyWord.WHILE, KeyWord.FOR, KeyWord.IF, KeyWord.TRY, KeyWord.RETURN, KeyWord.OPENBRACE);
		for (int i=0; i<content.size(); i++) {
			WordInFile word = content.get(i);
			if (keywords.contains(word)){
				if (!otherContent.isEmpty()){
					List<WordInFile> wordlist = new ArrayList<WordInFile>();
					wordlist.addAll(otherContent);
					otherContent.clear();
					result.add(new WordList(wordlist));
				}
				if (word.equals(KeyWord.DO)) {
					i = parseDoWhile(content, result, i);
					//TODO
					otherContent.add(word);
				} else if (word.equals(KeyWord.WHILE)) {
					i = parseWhile(content, result, i);
					//TODO
					otherContent.add(word);
				} else if (word.equals(KeyWord.FOR)) {
					i = parseFor(content, result, i);
					//TODO
					otherContent.add(word);
				} else if (word.equals(KeyWord.IF)) {
					i = parseIf(content, result, i);
					//TODO
					otherContent.add(word);
				} else if (word.equals(KeyWord.TRY)) {
					i = parseTry(content, result, i);
					//TODO
					otherContent.add(word);
				} else if (word.equals(KeyWord.RETURN)) {
					i = parseReturn(content, result, i);
					//TODO
					otherContent.add(word);
				} else if (word.equals(KeyWord.OPENBRACE)) {
					i = parseBlock(content, result, i+1);
				} 
			} else {
				otherContent.add(word);
			}
		}
		if (!otherContent.isEmpty()){
			result.add(new WordList(otherContent));
		}
		return result;
	}
	
	private int parseBlock(List<WordInFile> content, List<JavaFileContent> result, int i) {
		int openBraces = 1;
		List<WordInFile> blockcontent = new ArrayList<WordInFile>();
		while (openBraces>0) {
			if (content.get(i).equals(KeyWord.CLOSEBRACE)) {
				openBraces--;
			}
			if (content.get(i).equals(KeyWord.OPENBRACE)) {
				openBraces++;
			} 
			if (openBraces!=0) {
				blockcontent.add(content.get(i));
			}
			i++;
		}
		result.add(new JavaStatement(addStructuralStatements(blockcontent), StatementType.BLOCK));
		return i;
	}

	private int parseReturn(List<WordInFile> content, List<JavaFileContent> result, int i) {
		// TODO Auto-generated method stub
		return i;
	}

	private int parseTry(List<WordInFile> content, List<JavaFileContent> result, int i) {
		// TODO Auto-generated method stub
		return i;
	}

	private int parseIf(List<WordInFile> content, List<JavaFileContent> result, int i) {
		// TODO Auto-generated method stub
		return i;
	}

	private int parseFor(List<WordInFile> content, List<JavaFileContent> result, int i) {
		// TODO Auto-generated method stub
		return i;
	}

	private int parseWhile(List<WordInFile> content, List<JavaFileContent> result, int i) {
		// TODO Auto-generated method stub
		return i;
	}

	private int parseDoWhile(List<WordInFile> content, List<JavaFileContent> result, int i) {
		// TODO Auto-generated method stub
		return i;
	}
}
