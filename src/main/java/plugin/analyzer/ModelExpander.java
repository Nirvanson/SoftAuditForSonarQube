package plugin.analyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import plugin.model.JavaFileContent;
import plugin.model.KeyWord;
import plugin.model.StatementType;
import plugin.model.WordInFile;
import plugin.model.WordList;
import plugin.model.components.JavaClass;
import plugin.model.components.JavaControlStatement;
import plugin.model.components.JavaMethod;
import plugin.model.components.JavaStatement;
import plugin.model.components.JavaStatementWithAnonymousClass;

public class ModelExpander {
	
	public List<JavaFileContent> splitRemainingWordListsToStatements(List<JavaFileContent> contents) {
		if (contents == null) {
			return null;
		}
		if (contents.isEmpty()) {
			return contents;
		}
		List<JavaFileContent> result = new ArrayList<JavaFileContent>();
		for (JavaFileContent content : contents) {
			if (content instanceof JavaClass || content instanceof JavaMethod || content instanceof JavaControlStatement
					|| content instanceof JavaStatementWithAnonymousClass) {
				content.setContent(splitRemainingWordListsToStatements(content.getContent()));
			}
			if (content instanceof JavaControlStatement) {
				JavaControlStatement ctrlstm = (JavaControlStatement) content;
				ctrlstm.setOthercontent(splitRemainingWordListsToStatements(ctrlstm.getOthercontent()));
				ctrlstm.setResources(splitRemainingWordListsToStatements(ctrlstm.getResources()));
				if (ctrlstm.getCatchedExceptions()!=null) {
					for (List<WordInFile> exception : ctrlstm.getCatchedExceptions().keySet()) {
						ctrlstm.getCatchedExceptions().put(exception, splitRemainingWordListsToStatements(ctrlstm.getCatchedExceptions().get(exception)));
					}
				}
				result.add(ctrlstm);
			} else if (!(content instanceof WordList)) {
				result.add(content);
			} else {
				result.addAll(splitWordListToStatements(((WordList) content).getWordlist()));
			}
		}
		return result;
	}

	public List<JavaFileContent> parseStructuralStatements(List<JavaFileContent> contentlist) {
		List<JavaFileContent> result = new ArrayList<JavaFileContent>();
		if (!(contentlist == null)) {
			for (JavaFileContent content : contentlist) {
				if (content instanceof JavaMethod && content.getContent()!=null) {
					List<JavaFileContent> newMethodContent = new ArrayList<JavaFileContent>();
					for (JavaFileContent methodcontent : content.getContent()) {
						if (methodcontent instanceof JavaClass || methodcontent instanceof JavaStatementWithAnonymousClass) {
							methodcontent.setContent(parseStructuralStatements(methodcontent.getContent()));
							newMethodContent.add(methodcontent);
						} else if (methodcontent instanceof WordList) {
							newMethodContent.addAll(addStructuralStatements(((WordList) methodcontent).getWordlist()));
						} 
					} 
					content.setContent(newMethodContent);
				} else if (content instanceof JavaClass || content instanceof JavaStatementWithAnonymousClass) {
					content.setContent(parseStructuralStatements(content.getContent()));
				}
				result.add(content);
			}
		}
		return result;
	}
	
	private List<JavaFileContent> splitWordListToStatements(List<WordInFile> wordlist) {
		List<JavaFileContent> result = new ArrayList<JavaFileContent>();
		List<WordInFile> current = new ArrayList<WordInFile>();
		for (WordInFile word : wordlist) {
			current.add(word);
			if (word.equals(KeyWord.SEMICOLON)) {
				List<WordInFile> statement = new ArrayList<WordInFile>();
				statement.addAll(current);
				current.clear();
				JavaStatement newStatement = new JavaStatement(StatementType.UNSPECIFIED);
				newStatement.setStatementText(statement);
				result.add(newStatement);
			}
		}
		return result;
	}
	
	private List<JavaFileContent> addStructuralStatements(List<WordInFile> content) {
		List<JavaFileContent> result = new ArrayList<JavaFileContent>();
		List<WordInFile> otherContent = new ArrayList<WordInFile>();
		List<KeyWord> keywords = Arrays.asList(KeyWord.SWITCH, KeyWord.DO, KeyWord.WHILE, 
				KeyWord.FOR, KeyWord.IF, KeyWord.TRY, KeyWord.RETURN);
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
				} 
			} else {
				if (word.equals(KeyWord.OPENBRACE) && (i==0 || (!content.get(i-1).equals(KeyWord.CLOSEBRACKET) && !content.get(i-1).equals(KeyWord.ASSERT)))) {
					if (!otherContent.isEmpty()){
						List<WordInFile> wordlist = new ArrayList<WordInFile>();
						wordlist.addAll(otherContent);
						otherContent.clear();
						result.add(new WordList(wordlist));
					} 
					i = parseAnonymousBlock(content, result, i);
				} else {
					otherContent.add(word);
				}
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
		JavaControlStatement switchStatement = new JavaControlStatement(StatementType.SWITCH);
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
		JavaControlStatement currentCase = new JavaControlStatement(StatementType.CASE);
		currentCase.setCondition(new ArrayList<WordInFile>());
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
					List<JavaFileContent> parsedCaseContent = addStructuralStatements(onecase);
					if (parsedCaseContent.size()==1 && parsedCaseContent.get(0) instanceof JavaControlStatement && ((JavaControlStatement) parsedCaseContent.get(0)).getType().equals(StatementType.BLOCK)) {
						// if casecontent is completely in braces dont't count it as anonymous block
						currentCase.setContent(parsedCaseContent.get(0).getContent());
					} else {
						currentCase.setContent(parsedCaseContent);
					}
					// remove last comma
					currentCase.getCondition().remove(currentCase.getCondition().size()-1);
					switchContent.add(currentCase);
					currentCase = new JavaControlStatement(StatementType.CASE);
					currentCase.setCondition(new ArrayList<WordInFile>());
				}
				// skip "case" or "default"
				i++;
				// ignore parantheses around value and parse it
				if (content.get(i).equals(KeyWord.OPENPARANTHESE)) {
					openParanthesis = 1;
					//skip "("
					i++;
					while (openParanthesis>0) {
						if (content.get(i).equals(KeyWord.CLOSPARANTHESE)) {
							openParanthesis--;
						}
						if (content.get(i).equals(KeyWord.OPENPARANTHESE)) {
							openParanthesis++;
						} 
						if (openParanthesis!=0) {
							casecondition.add(content.get(i));
						}
						i++;
					}
				}
				// parse case value and set as condition
				while (!content.get(i).equals(KeyWord.DOUBLEDOT)) {
					casecondition.add(content.get(i));
					i++;
				}
				if (casecondition.isEmpty()) {
					// add "default" if no casecondition found
					casecondition.add(content.get(i-1));
				}
				currentCase.getCondition().addAll(casecondition);
				currentCase.getCondition().add(new WordInFile(null, KeyWord.COMMA));
				casecondition.clear();
				// skip ":"
				i++;
			}
			// check ending of switch or inner braces
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
		List<JavaFileContent> parsedCaseContent = addStructuralStatements(onecase);
		if (parsedCaseContent.size()==1 && parsedCaseContent.get(0) instanceof JavaControlStatement && ((JavaControlStatement) parsedCaseContent.get(0)).getType().equals(StatementType.BLOCK)) {
			// if casecontent is completely in braces dont't count it as anonymous block
			currentCase.setContent(parsedCaseContent.get(0).getContent());
		} else {
			currentCase.setContent(parsedCaseContent);
		}
		// remove last comma
		currentCase.getCondition().remove(currentCase.getCondition().size()-1);
		switchContent.add(currentCase);
		switchStatement.setContent(switchContent);
		result.add(switchStatement);
		return i-1;
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
		JavaControlStatement blockstatement = new JavaControlStatement(StatementType.BLOCK);
		blockstatement.setContent(addStructuralStatements(blockcontent));
		result.add(blockstatement);
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
		JavaControlStatement returnstatement = new JavaControlStatement(StatementType.RETURN);
		returnstatement.setStatementText(returncontent);
		result.add(returnstatement);
		return i;
	}

	private int parseTry(List<WordInFile> content, List<JavaFileContent> result, int i) {
		// skip "try"
		i++;
		List<WordInFile> resources = null;
		if (content.get(i).equals(KeyWord.OPENPARANTHESE)) {
			// try with resource detected
			int openParantheses = 1;
			i++;
			resources = new ArrayList<WordInFile>();
			while (openParantheses>0) {
				if (content.get(i).equals(KeyWord.CLOSPARANTHESE)) {
					openParantheses--;
				}
				if (content.get(i).equals(KeyWord.OPENPARANTHESE)) {
					openParantheses++;
				} 
				if (openParantheses!=0) {
					resources.add(content.get(i));
				}
				i++;
			}
		}
		// skip {
		i++;
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
		JavaControlStatement tryStatement = new JavaControlStatement(StatementType.TRY);
		tryStatement.setContent(addStructuralStatements(tryblock));
		if (resources!=null) {
			tryStatement.setResources(addStructuralStatements(resources));
		}
		// add catched exception as condition (....) if present
		tryStatement.setCatchedExceptions(new HashMap<List<WordInFile>, List<JavaFileContent>>());
		while (content.size()>i && content.get(i).equals(KeyWord.CATCH)) {
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
			tryStatement.getCatchedExceptions().put(exception, addStructuralStatements(catchblock));
		}
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
			tryStatement.setOthercontent(addStructuralStatements(finallyblock));
		}
		result.add(tryStatement);
		return i-1;
	}

	private int parseIf(List<WordInFile> content, List<JavaFileContent> result, int i) {
		// skip "if("
		i += 2;
		JavaControlStatement ifStatement = new JavaControlStatement(StatementType.IF);
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
				ifStatement.setOthercontent(elseContent);
			} else if (content.get(i).equals(KeyWord.TRY)) {
				i = parseTry(content, elseContent, i) + 1;
				ifStatement.setOthercontent(elseContent);
			} else if (content.get(i).equals(KeyWord.SWITCH)) {
				i = parseSwitch(content, elseContent, i) + 1;
				ifStatement.setOthercontent(elseContent);
			} else if (content.get(i).equals(KeyWord.FOR)) {
				i = parseFor(content, elseContent, i) + 1;
				ifStatement.setOthercontent(elseContent);
			} else if (content.get(i).equals(KeyWord.WHILE)) {
				i = parseWhile(content, elseContent, i) + 1;
				ifStatement.setOthercontent(elseContent);
			} else if (content.get(i).equals(KeyWord.DO)) {
				i = parseIf(content, elseContent, i) + 1;
				ifStatement.setOthercontent(elseContent);
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
				ifStatement.setOthercontent(addStructuralStatements(elseblock));
			}
		}
		result.add(ifStatement);
		return i - 1;
	}

	private int parseFor(List<WordInFile> content, List<JavaFileContent> result, int i) {
		// skip "for("
		i += 2;
		JavaControlStatement forStatement = new JavaControlStatement(StatementType.FOR);
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
			if (openParanthesis!=0 && !content.get(i).equals(KeyWord.SEMICOLON)) {
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
		JavaControlStatement whileStatement = new JavaControlStatement(StatementType.WHILE);
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
		JavaControlStatement whileStatement = new JavaControlStatement(StatementType.WHILE);
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
