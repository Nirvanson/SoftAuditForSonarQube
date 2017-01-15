package plugin.model;

public enum KeyWord {
	ABSTRACT("abstract", WordType.MODIFIER),
	ASSERT("assert", WordType.STATEMENT),
	BOOLEAN("boolean", WordType.DATATYPE),
	BREAK("break", WordType.STATEMENT),
	BYTE("byte", WordType.DATATYPE),
	CASE("case", WordType.STATEMENT),
	CATCH("catch", WordType.STATEMENT),
	CHAR("char", WordType.DATATYPE),
	CLASS("class", WordType.DECLARATOR),
	CONTINUE("continue", WordType.STATEMENT),
	DEFAULT("default", WordType.STATEMENT),
	DO("do", WordType.STATEMENT),
	DOUBLE("double", WordType.DATATYPE),
	ELSE("else", WordType.STATEMENT),
	ENUM("enum", WordType.DECLARATOR),
	EXTENDS("extends", WordType.SPECIFIER),
	FINAL("final", WordType.MODIFIER),
	FINALLY("finally", WordType.STATEMENT),
	FLOAT("float", WordType.DATATYPE),
	FOR("for", WordType.STATEMENT),
	IF("if", WordType.STATEMENT),
	IMPLEMENTS("implements", WordType.SPECIFIER),
	IMPORT("import", WordType.STATEMENT),
	INSTANCEOF("instanceof", WordType.OPERATOR),
	INT("int", WordType.DATATYPE),
	INTERFACE("interface", WordType.DECLARATOR),
	LONG("long", WordType.DATATYPE),
	NATIVE("native", WordType.MODIFIER),
	NEW("new", WordType.OPERATOR),
	PACKAGE("package", WordType.STATEMENT),
	PRIVATE("private", WordType.MODIFIER),
	PROTECTED("protected", WordType.MODIFIER),
	PUBLIC("public", WordType.MODIFIER),
	RETURN("return", WordType.STATEMENT),
	SHORT("short", WordType.DATATYPE),
	STATIC("static", WordType.MODIFIER),
	STRICTFP("strictfp", WordType.MODIFIER),
	SUPER("super", WordType.STATEMENT),
	SWITCH("switch", WordType.STATEMENT),
	SYNCHRONIZED("synchronized", WordType.STATEMENTORMODIFIER),
	THIS("this", WordType.STATEMENTOROBJECT),
	THROW("throw", WordType.STATEMENT),
	THROWS("throws", WordType.SPECIFIER),
	TRANSIENT("transient", WordType.MODIFIER),
	TRY("try", WordType.STATEMENT),
	VOID("void", WordType.DATATYPE),
	VOLATILE("volatile", WordType.MODIFIER),
	WHILE("while", WordType.STATEMENT),
	TRUE("true", WordType.LITERAL),
	FALSE("false", WordType.LITERAL),
	NULL("null", WordType.LITERAL),
	ADD("+", WordType.OPERATOR),
	SUB("-", WordType.OPERATOR),
	MULT("*", WordType.OPERATOR),
	DIV("/", WordType.OPERATOR),
	MOD("%", WordType.OPERATOR),
	COMP("!", WordType.OPERATOR),
	GREATER(">", WordType.OPERATOR),
	LESS("<", WordType.OPERATOR),
	AND("&", WordType.OPERATOR),
	OR("|", WordType.OPERATOR),
	BITCOMP("~", WordType.OPERATOR),
	BITXOR("^", WordType.OPERATOR),
	ASSIGN("=", WordType.OPERATOR),
	STRINGLITERAL("\"", WordType.LITERAL),
	CHARLITERAL("'", WordType.LITERAL),
	OPENPARANTHESE("(", WordType.BREAK),
	CLOSPARANTHESE(")", WordType.BREAK),
	OPENBRACE("{", WordType.BREAK),
	CLOSEBRACE("}", WordType.BREAK),
	OPENBRACKET("[", WordType.BREAK),
	CLOSEBRACKET("]", WordType.BREAK),
	SEMICOLON(";", WordType.BREAK),
	COMMA(",", WordType.BREAK),
	DOT(".", WordType.BREAK),
	ANNOTATION("@", WordType.BREAK),
	ANNOTATIONINTERFACE("@interface", WordType.DECLARATOR),
	QUESTIONMARK("?", WordType.BREAK),
	DOUBLEDOT(":", WordType.BREAK),
	WORD("", WordType.FREEWORD),
	VARIDENT("", WordType.VARIDENT),
	VARTYPE("", WordType.VARTYPE),
	METHODREF("", WordType.METHODREF),
	ASSIGNMENT("", WordType.ASSIGNMENT),
	CONSTANT("", WordType.NUMBER);
	
	private final String word;
	private final WordType type;
	
	KeyWord(String word, WordType type) {
		this.word = word;
		this.type = type;
	}
	
	@Override
	public String toString() {
		return this.word;
	}
	
	public static WordInFile findKeyword(String word) {
		for (KeyWord key : KeyWord.values()) {
			if (key.word.equals(word)) {
				return new WordInFile(null, key);
			}
		}
		return new WordInFile(word, KeyWord.WORD);
	}

	public WordType getType() {
		return type;
	}
}