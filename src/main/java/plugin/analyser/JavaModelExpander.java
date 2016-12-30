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
				} else if (word.equals(KeyWord.IF)) {
					i = parseIf(content, result, i);
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
		return i-1;
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
		return i;
	}

	private int parseTry(List<WordInFile> content, List<JavaFileContent> result, int i) {
		// add everything until brace is closed to tryblock
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
		return i-1;
	}

	private int parseIf(List<WordInFile> content, List<JavaFileContent> result, int i) {
		JavaStatement ifStatement = new JavaStatement(null, StatementType.IF);
		// parse condition
		List<WordInFile> condition = new ArrayList<WordInFile>();
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
				condition.add(content.get(i));
			}
			i++;
		}
		ifStatement.setCondition(condition);
		// parse if-block
		List<JavaFileContent> ifContent = new ArrayList<JavaFileContent>();
		// content is single structural statement - parse directly
		if (content.get(i).equals(KeyWord.IF)) {
			i = parseIf(content, ifContent, i) + 1;
			ifStatement.setContent(ifContent);
		} else if (content.get(i).equals(KeyWord.TRY)) {
			i = parseTry(content, ifContent, i) + 1;
			ifStatement.setContent(ifContent);
		} else if (content.get(i).equals(KeyWord.SWITCH)) {
			i = parseSwitch(content, ifContent, i) + 1;
			ifStatement.setContent(ifContent);
		} else if (content.get(i).equals(KeyWord.FOR)) {
			i = parseFor(content, ifContent, i) + 1;
			ifStatement.setContent(ifContent);
		} else if (content.get(i).equals(KeyWord.WHILE)) {
			i = parseWhile(content, ifContent, i) + 1;
			ifStatement.setContent(ifContent);
		} else if (content.get(i).equals(KeyWord.DO)) {
			i = parseIf(content, ifContent, i) + 1;
			ifStatement.setContent(ifContent);
		} else {
			List<WordInFile> ifblock = new ArrayList<WordInFile>();
			if (content.get(i).equals(KeyWord.OPENBRACE)) {
				// in braces - parse block
				int openBraces = 1;
				i++;
				while (openBraces>0) {
					if (content.get(i).equals(KeyWord.CLOSEBRACE)) {
						openBraces--;
					}
					if (content.get(i).equals(KeyWord.OPENBRACE)) {
						openBraces++;
					} 
					if (openBraces!=0) {
						ifblock.add(content.get(i));
					}
					i++;
				}
			} else {
			// single (non structural) statement - parse till next semicolon
				do {
					ifblock.add(content.get(i));
					i++;
				} while(!content.get(i-1).equals(KeyWord.SEMICOLON));
			}
			ifStatement.setContent(addStructuralStatements(ifblock));
		}
		//check for else block
		if (content.size()>i && content.get(i).equals(KeyWord.ELSE)) {
			i++;
			List<JavaFileContent> elseContent = new ArrayList<JavaFileContent>();
			// content is single structural statement - parse directly
			if (content.get(i).equals(KeyWord.IF)) {
				i = parseIf(content, elseContent, i) + 1;
				ifStatement.setElsecontent(elseContent);
			} else if (content.get(i).equals(KeyWord.TRY)) {
				i = parseTry(content, elseContent, i) + 1;
				ifStatement.setElsecontent(elseContent);
			} else if (content.get(i).equals(KeyWord.SWITCH)) {
				i = parseSwitch(content, elseContent, i) + 1;
				ifStatement.setElsecontent(elseContent);
			} else if (content.get(i).equals(KeyWord.FOR)) {
				i = parseFor(content, elseContent, i) + 1;
				ifStatement.setElsecontent(elseContent);
			} else if (content.get(i).equals(KeyWord.WHILE)) {
				i = parseWhile(content, elseContent, i) + 1;
				ifStatement.setElsecontent(elseContent);
			} else if (content.get(i).equals(KeyWord.DO)) {
				i = parseIf(content, elseContent, i) + 1;
				ifStatement.setElsecontent(elseContent);
			} else {
				List<WordInFile> elseblock = new ArrayList<WordInFile>();
				if (content.get(i).equals(KeyWord.OPENBRACE)) {
					// in braces - parse block
					i++;
					int openBraces = 1;
					while (openBraces>0) {
						if (content.get(i).equals(KeyWord.CLOSEBRACE)) {
							openBraces--;
						}
						if (content.get(i).equals(KeyWord.OPENBRACE)) {
							openBraces++;
						} 
						if (openBraces!=0) {
							elseblock.add(content.get(i));
						}
						i++;
					}
				} else {
					// single (non structural) statement - parse till next semicolon 
					do {
						elseblock.add(content.get(i));
						i++;
					} while(!content.get(i-1).equals(KeyWord.SEMICOLON));
				}
				ifStatement.setElsecontent(addStructuralStatements(elseblock));
			}
		}
		result.add(ifStatement);
		return i - 1;
	}

	private int parseFor(List<WordInFile> content, List<JavaFileContent> result, int i) {
		JavaStatement forStatement = new JavaStatement(null, StatementType.FOR);
		// parse declaration
		List<WordInFile> declaration = new ArrayList<WordInFile>();
		int openParanthesis = 1;
		i++;
		i++;
		int state = 0;
		while (openParanthesis>0) {
			if (content.get(i).equals(KeyWord.CLOSPARANTHESE)) {
				openParanthesis--;
			}
			if (content.get(i).equals(KeyWord.OPENPARANTHESE)) {
				openParanthesis++;
			} 
			if (openParanthesis!=0) {
				declaration.add(content.get(i));
			}
			if (content.get(i).equals(KeyWord.SEMICOLON)) {
				List<WordInFile> temp = new ArrayList<WordInFile>();
				temp.addAll(declaration);
				declaration.clear();
				state++;
				if (state==1) {
					forStatement.setInitialization(temp);
				} else {
					forStatement.setCondition(temp);
				}
			}
			i++;
		}
		if (state==0) {
			forStatement.setCondition(declaration);
		} else {
			forStatement.setIncrement(declaration);
		}
		// parse content
		List<JavaFileContent> forContent = new ArrayList<JavaFileContent>();
		// content is single structural statement - parse directly
		if (content.get(i).equals(KeyWord.IF)) {
			i = parseIf(content, forContent, i) + 1;
			forStatement.setContent(forContent);
		} else if (content.get(i).equals(KeyWord.TRY)) {
			i = parseTry(content, forContent, i) + 1;
			forStatement.setContent(forContent);
		} else if (content.get(i).equals(KeyWord.SWITCH)) {
			i = parseSwitch(content, forContent, i) + 1;
			forStatement.setContent(forContent);
		} else if (content.get(i).equals(KeyWord.FOR)) {
			i = parseFor(content, forContent, i) + 1;
			forStatement.setContent(forContent);
		} else if (content.get(i).equals(KeyWord.WHILE)) {
			i = parseWhile(content, forContent, i) + 1;
			forStatement.setContent(forContent);
		} else if (content.get(i).equals(KeyWord.DO)) {
			i = parseIf(content, forContent, i) + 1;
			forStatement.setContent(forContent);
		} else {
			List<WordInFile> forblock = new ArrayList<WordInFile>();
			if (content.get(i).equals(KeyWord.OPENBRACE)) {
				// in braces - parse block
				int openBraces = 1;
				i++;
				while (openBraces>0) {
					System.out.println(content.get(i));
					if (content.get(i).equals(KeyWord.CLOSEBRACE)) {
						openBraces--;
					}
					if (content.get(i).equals(KeyWord.OPENBRACE)) {
						openBraces++;
					} 
					if (openBraces!=0) {
						forblock.add(content.get(i));
					}
					i++;
				}
			} else {
			// single (non structural) statement - parse till next semicolon
				do {
					forblock.add(content.get(i));
					i++;
				} while(!content.get(i-1).equals(KeyWord.SEMICOLON));
			}
			forStatement.setContent(addStructuralStatements(forblock));
		}
		result.add(forStatement);
		return i - 1;
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
