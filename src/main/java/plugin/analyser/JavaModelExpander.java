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
		switch(value) {case 1: ... break; default: ... break;}
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
				if (word.equals(KeyWord.SWITCH)) {
					i = parseSwitch(content, result, i);
					//TODO
					otherContent.add(word);
				} else if (word.equals(KeyWord.DO)) {
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
					i = parseTry(content, result, i+2);
				} else if (word.equals(KeyWord.RETURN)) {
					i = parseReturn(content, result, i);
				} else if (word.equals(KeyWord.OPENBRACE)) {
					i = parseAnonymousBlock(content, result, i+1);
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
	
	private int parseSwitch(List<WordInFile> content, List<JavaFileContent> result, int i) {
		// TODO Auto-generated method stub
		return 0;
	}

	private int parseAnonymousBlock(List<WordInFile> content, List<JavaFileContent> result, int i) {
		// add everything until brace is closed to block
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
		// add everything until the next semicolon to returnstatement
		List<WordInFile> returncontent = new ArrayList<WordInFile>();
		while (!content.get(i).equals(KeyWord.SEMICOLON)) {
			returncontent.add(content.get(i));
			i++;
		}
		returncontent.add(content.get(i));
		result.add(new JavaStatement(Arrays.asList(new WordList(returncontent)), StatementType.RETURN));
		i++;
		return i;
	}

	private int parseTry(List<WordInFile> content, List<JavaFileContent> result, int i) {
		// add everything  until brace is closed to tryblock
		List<WordInFile> tryblock = new ArrayList<WordInFile>();
		int openBraces = 1;
		while (openBraces>0) {
			if (content.get(i).equals(KeyWord.CLOSEBRACE)) {
				openBraces--;
			}
			if (content.get(i).equals(KeyWord.OPENBRACE)) {
				openBraces++;
			} 
			if (openBraces!=0) {
				tryblock.add(content.get(i));
			}
			i++;
		}
		JavaStatement tryStatement = new JavaStatement(addStructuralStatements(tryblock), StatementType.TRY);
		// add catched exception as condition (....)
		List<WordInFile> exception = new ArrayList<WordInFile>();
		int openParanthesis = 1;
		i++;
		i++;
		while (openParanthesis>0) {
			if (content.get(i).equals(KeyWord.CLOSPARANTHESE)) {
				openParanthesis--;
			}
			if (content.get(i).equals(KeyWord.OPENPARANTHESE)) {
				openParanthesis++;
			} 
			if (openParanthesis!=0) {
				exception.add(content.get(i));
			}
			i++;
		}
		tryStatement.setCondition(exception);
		// add catchblock
		List<WordInFile> catchblock = new ArrayList<WordInFile>();
		openBraces = 1;
		i++;
		while (openBraces>0) {
			if (content.get(i).equals(KeyWord.CLOSEBRACE)) {
				openBraces--;
			}
			if (content.get(i).equals(KeyWord.OPENBRACE)) {
				openBraces++;
			} 
			if (openBraces!=0) {
				catchblock.add(content.get(i));
			}
			i++;
		}
		tryStatement.setElsecontent(addStructuralStatements(catchblock));
		// check for finally block and add if present
		if (content.size()>i && content.get(i).equals(KeyWord.FINALLY)) {
			i++;
			List<WordInFile> finallyblock = new ArrayList<WordInFile>();
			openBraces = 1;
			i++;
			while (openBraces>0) {
				if (content.get(i).equals(KeyWord.CLOSEBRACE)) {
					openBraces--;
				}
				if (content.get(i).equals(KeyWord.OPENBRACE)) {
					openBraces++;
				} 
				if (openBraces!=0) {
					finallyblock.add(content.get(i));
				}
				i++;
			}
			tryStatement.setFinallycontent(addStructuralStatements(finallyblock));
		}
		result.add(tryStatement);
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
