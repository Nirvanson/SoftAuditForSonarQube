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
		List<JavaFileContent> result = new ArrayList<JavaFileContent>();
		List<WordInFile> otherContent = new ArrayList<WordInFile>();
		List<KeyWord> keywords = Arrays.asList(KeyWord.SWITCH, KeyWord.DO, KeyWord.WHILE, KeyWord.FOR, KeyWord.IF, KeyWord.TRY, KeyWord.RETURN, KeyWord.OPENBRACE);
		
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
				} else if (word.equals(KeyWord.DO)) {
					i = parseDoWhile(content, result, i);
				} else if (word.equals(KeyWord.WHILE)) {
					i = parseWhile(content, result, i);
				} else if (word.equals(KeyWord.FOR)) {
					i = parseFor(content, result, i);
				} else if (word.equals(KeyWord.IF)) {
					i = parseIf(content, result, i);
				} else if (word.equals(KeyWord.TRY)) {
					i = parseTry(content, result, i);
				} else if (word.equals(KeyWord.RETURN)) {
					i = parseReturn(content, result, i);
				} else if (word.equals(KeyWord.OPENBRACE)) {
					i = parseAnonymousBlock(content, result, i);
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
		// skip "switch("
		i += 2;
		JavaStatement switchStatement = new JavaStatement(null, StatementType.SWITCH);
		// parse switchvalue as condition
		List<WordInFile> condition = new ArrayList<WordInFile>();
		int openParanthesis = 1;
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
		switchStatement.setCondition(condition);
		// skip "{"
		i++;
		// parse content
		List<JavaFileContent> switchContent = new ArrayList<JavaFileContent>();
		int openBraces = 1;
		List<WordInFile> casecontent = new ArrayList<WordInFile>();
		List<WordInFile> casecondition = new ArrayList<WordInFile>();
		JavaStatement currentCase = new JavaStatement(null, StatementType.CASE);
		if (content.get(i).equals(KeyWord.CLOSEBRACE)) {
			openBraces--;
		} else if (content.get(i).equals(KeyWord.OPENBRACE)) {
			openBraces++;
		}
		while (openBraces>0) {
			// accept only cases in switch braces, to avoid to detect cases of inner switches...
			while (openBraces==1 && (content.get(i).equals(KeyWord.CASE) || content.get(i).equals(KeyWord.DEFAULT))) {
				if (!casecontent.isEmpty()) {
					// add previous case to switch
					List<WordInFile> onecase = new ArrayList<WordInFile>();
					onecase.addAll(casecontent);
					casecontent.clear();
					currentCase.setContent(addStructuralStatements(onecase));
					switchContent.add(currentCase);
					currentCase = new JavaStatement(null, StatementType.CASE);
				}
				// skip "case" or "default"
				i++;
				// parse case value and set as condition
				while (!content.get(i).equals(KeyWord.DOUBLEDOT)) {
					casecondition.add(content.get(i));
					i++;
				}
				if (casecondition.isEmpty()) {
					// add "default" if no casecondition found
					casecondition.add(content.get(i-1));
				}
				List<WordInFile> onecondition = new ArrayList<WordInFile>();
				onecondition.addAll(casecondition);
				casecondition.clear();
				currentCase.setCondition(onecondition);
				// skip ":"
				i++;
			}
			if (content.get(i).equals(KeyWord.CLOSEBRACE)) {
				openBraces--;
			} else if (content.get(i).equals(KeyWord.OPENBRACE)) {
				openBraces++;
			}
			if (openBraces>0) {
				casecontent.add(content.get(i));
			}
			i++;
		}
		// finish switch, add last case to switch
		List<WordInFile> onecase = new ArrayList<WordInFile>();
		onecase.addAll(casecontent);
		casecontent.clear();
		currentCase.setContent(addStructuralStatements(onecase));
		switchContent.add(currentCase);
		switchStatement.setContent(switchContent);
		result.add(switchStatement);
		return i;
	}

	private int parseAnonymousBlock(List<WordInFile> content, List<JavaFileContent> result, int i) {
		// skip "{"
		i++;
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
		// skip "try{"
		i += 2;
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
		// skip "if("
		i += 2;
		JavaStatement ifStatement = new JavaStatement(null, StatementType.IF);
		// parse condition
		List<WordInFile> condition = new ArrayList<WordInFile>();
		int openParanthesis = 1;
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
		// skip "for("
		i += 2;
		JavaStatement forStatement = new JavaStatement(null, StatementType.FOR);
		// parse declaration
		List<WordInFile> declaration = new ArrayList<WordInFile>();
		int openParanthesis = 1;
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
		// skip "while("
		i += 2;
		JavaStatement whileStatement = new JavaStatement(null, StatementType.WHILE);
		// parse condition
		List<WordInFile> condition = new ArrayList<WordInFile>();
		int openParanthesis = 1;
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
		whileStatement.setCondition(condition);
		// parse content
		List<JavaFileContent> whileContent = new ArrayList<JavaFileContent>();
		// content is single structural statement - parse directly
		if (content.get(i).equals(KeyWord.IF)) {
			i = parseIf(content, whileContent, i) + 1;
			whileStatement.setContent(whileContent);
		} else if (content.get(i).equals(KeyWord.TRY)) {
			i = parseTry(content, whileContent, i) + 1;
			whileStatement.setContent(whileContent);
		} else if (content.get(i).equals(KeyWord.SWITCH)) {
			i = parseSwitch(content, whileContent, i) + 1;
			whileStatement.setContent(whileContent);
		} else if (content.get(i).equals(KeyWord.FOR)) {
			i = parseFor(content, whileContent, i) + 1;
			whileStatement.setContent(whileContent);
		} else if (content.get(i).equals(KeyWord.WHILE)) {
			i = parseWhile(content, whileContent, i) + 1;
			whileStatement.setContent(whileContent);
		} else if (content.get(i).equals(KeyWord.DO)) {
			i = parseIf(content, whileContent, i) + 1;
			whileStatement.setContent(whileContent);
		} else {
			List<WordInFile> whileblock = new ArrayList<WordInFile>();
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
						whileblock.add(content.get(i));
					}
					i++;
				}
			} else {
			// single (non structural) statement - parse till next semicolon
				do {
					whileblock.add(content.get(i));
					i++;
				} while(!content.get(i-1).equals(KeyWord.SEMICOLON));
			}
			whileStatement.setContent(addStructuralStatements(whileblock));
		}
		result.add(whileStatement);
		return i-1;
	}

	private int parseDoWhile(List<WordInFile> content, List<JavaFileContent> result, int i) {
		// skip "do"
		i++;
		JavaStatement whileStatement = new JavaStatement(null, StatementType.WHILE);
		// parse content
		List<JavaFileContent> whileContent = new ArrayList<JavaFileContent>();
		// content is single structural statement - parse directly
		if (content.get(i).equals(KeyWord.IF)) {
			i = parseIf(content, whileContent, i) + 1;
			whileStatement.setContent(whileContent);
		} else if (content.get(i).equals(KeyWord.TRY)) {
			i = parseTry(content, whileContent, i) + 1;
			whileStatement.setContent(whileContent);
		} else if (content.get(i).equals(KeyWord.SWITCH)) {
			i = parseSwitch(content, whileContent, i) + 1;
			whileStatement.setContent(whileContent);
		} else if (content.get(i).equals(KeyWord.FOR)) {
			i = parseFor(content, whileContent, i) + 1;
			whileStatement.setContent(whileContent);
		} else if (content.get(i).equals(KeyWord.WHILE)) {
			i = parseWhile(content, whileContent, i) + 1;
			whileStatement.setContent(whileContent);
		} else if (content.get(i).equals(KeyWord.DO)) {
			i = parseIf(content, whileContent, i) + 1;
			whileStatement.setContent(whileContent);
		} else {
			List<WordInFile> whileblock = new ArrayList<WordInFile>();
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
						whileblock.add(content.get(i));
					}
					i++;
				}
			} else {
				// single (non structural) statement - parse till next semicolon
				do {
					whileblock.add(content.get(i));
					i++;
				} while(!content.get(i-1).equals(KeyWord.SEMICOLON));
			}
			whileStatement.setContent(addStructuralStatements(whileblock));
		}
		// skip "while("
		i += 2;
		// parse condition
		List<WordInFile> condition = new ArrayList<WordInFile>();
		int openParanthesis = 1;
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
		whileStatement.setCondition(condition);
		result.add(whileStatement);
		return i;
	}
}
