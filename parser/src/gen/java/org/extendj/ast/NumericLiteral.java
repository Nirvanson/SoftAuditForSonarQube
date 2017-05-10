/* This file was generated with JastAdd2 (http://jastadd.org) version 2.1.13 */
package org.extendj.ast;

import java.util.ArrayList;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.io.File;
import java.util.Set;
import beaver.*;
import org.jastadd.util.*;
import java.util.zip.*;
import java.io.*;
import org.jastadd.util.PrettyPrintable;
import org.jastadd.util.PrettyPrinter;
import java.io.FileNotFoundException;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
/**
 * A NumericLiteral is a raw literal, produced by the parser.
 * NumericLiterals are rewritten to the best matching concrete
 * numeric literal kind, or IllegalLiteral.
 * @ast node
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\grammar\\Literals.ast:18
 * @production NumericLiteral : {@link Literal};

 */
public class NumericLiteral extends Literal implements Cloneable {
  /**
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Literals.jrag:360
   */
  public static final int DECIMAL = 0;
  /**
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Literals.jrag:361
   */
  public static final int HEXADECIMAL = 1;
  /**
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Literals.jrag:362
   */
  public static final int OCTAL = 2;
  /**
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Literals.jrag:363
   */
  public static final int BINARY = 3;
  /**
   * The trimmed digits.
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Literals.jrag:421
   */
  protected String digits = "";
  /**
   * Sets the trimmed digits of this literal.
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Literals.jrag:426
   */
  public void setDigits(String digits) {
    this.digits = digits;
  }
  /**
   * The literal kind tells which kind of literal it is;
   * either a DECIMAL, HEXADECIMAL, OCTAL or BINARY literal.
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Literals.jrag:440
   */
  protected int kind = NumericLiteral.DECIMAL;
  /**
   * Sets the literal kind.
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Literals.jrag:445
   */
  public void setKind(int kind) {
    this.kind = kind;
  }
  /**
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Literals.jrag:523
   */
  
    private StringBuffer buf = new StringBuffer();
  /**
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Literals.jrag:524
   */
  
    private int idx = 0;
  /**
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Literals.jrag:525
   */
  
    private boolean whole;
  /**
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Literals.jrag:526
   */
  // have whole part?
    private boolean fraction;
  /**
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Literals.jrag:527
   */
  // have fraction part?
    private boolean exponentSpecifier;
  /**
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Literals.jrag:528
   */
  // have exponent specifier?
    private boolean exponent;
  /**
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Literals.jrag:529
   */
  // have exponent part?
    private boolean floating;
  /**
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Literals.jrag:530
   */
  // is floating point?
    private boolean isFloat;
  /**
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Literals.jrag:531
   */
  
    private boolean isLong;
  /**
   * @return a readable name to describe this literal.
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Literals.jrag:536
   */
  

    /**
     * @return a readable name to describe this literal.
     */
    private String name() {
      String name;
      switch (kind) {
        case DECIMAL:
          name = "decimal";
          break;
        case HEXADECIMAL:
          name = "hexadecimal";
          break;
        case OCTAL:
          name = "octal";
          break;
        case BINARY:
        default:
          name = "binary";
          break;
      }
      if (floating) {
        return name + " floating point";
      } else {
        return name;
      }
    }
  /**
   * The next character in the literal is a significant character;
   * push it onto the buffer.
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Literals.jrag:564
   */
  

    /**
     * The next character in the literal is a significant character;
     * push it onto the buffer.
     */
    private void pushChar() {
      buf.append(getLITERAL().charAt(idx++));
    }
  /**
   * Skip ahead n chracters in the literal.
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Literals.jrag:571
   */
  

    /**
     * Skip ahead n chracters in the literal.
     */
    private void skip(int n) {
      idx += n;
    }
  /**
   * @return true if there exists at least n more characters
   * in the literal
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Literals.jrag:579
   */
  

    /**
     * @return true if there exists at least n more characters
     * in the literal
     */
    private boolean have(int n) {
      return getLITERAL().length() >= idx + n;
    }
  /**
   * Look at the n'th next character.
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Literals.jrag:586
   */
  

    /**
     * Look at the n'th next character.
     */
    private char peek(int n) {
      return getLITERAL().charAt(idx + n);
    }
  /**
   * @return true if the character c is a decimal digit
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Literals.jrag:593
   */
  

    /**
     * @return true if the character c is a decimal digit
     */
    private static final boolean isDecimalDigit(char c) {
      return c == '_' || c >= '0' && c <= '9';
    }
  /**
   * @return true if the character c is a hexadecimal digit
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Literals.jrag:600
   */
  

    /**
     * @return true if the character c is a hexadecimal digit
     */
    private static final boolean isHexadecimalDigit(char c) {
      return c == '_' || c >= '0' && c <= '9' || c >= 'a' && c <= 'f' || c >= 'A' && c <= 'F';
    }
  /**
   * @return true if the character c is a binary digit
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Literals.jrag:607
   */
  

    /**
     * @return true if the character c is a binary digit
     */
    private static final boolean isBinaryDigit(char c) {
      return c == '_' || c == '0' || c == '1';
    }
  /**
   * @return true if the character c is an underscore
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Literals.jrag:614
   */
  

    /**
     * @return true if the character c is an underscore
     */
    private static final boolean isUnderscore(char c) {
      return c == '_';
    }
  /**
   * Parse a literal. If there is a syntax error in the literal,
   * an IllegalLiteral will be returned.
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Literals.jrag:622
   */
  

    /**
     * Parse a literal. If there is a syntax error in the literal,
     * an IllegalLiteral will be returned.
     */
    public Literal parse() {
      if (getLITERAL().length() == 0) {
        throw new IllegalStateException("Empty NumericLiteral");
      }

      kind = classifyLiteral();

      Literal literal;
      if (!floating) {
        literal = parseDigits();
      } else {
        literal = parseFractionPart();
      }
      literal.setStart(LITERALstart);
      literal.setEnd(LITERALend);
      return literal;
    }
  /**
   * Classify the literal.
   * 
   * @return either DECIMAL, HEXADECIMAL or BINARY
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Literals.jrag:645
   */
  

    /**
     * Classify the literal.
     *
     * @return either DECIMAL, HEXADECIMAL or BINARY
     */
    private int classifyLiteral() {
      if (peek(0) == '.') {
        floating = true;
        return DECIMAL;
      } else if (peek(0) == '0') {
        if (!have(2)) {
          // the only 1-length string that starts with 0 (obvious!)
          return DECIMAL;
        } else if (peek(1) == 'x' || peek(1) == 'X') {
          skip(2);
          return HEXADECIMAL;
        } else if (peek(1) == 'b' || peek(1) == 'B') {
          skip(2);
          return BINARY;
        } else {
          return DECIMAL;
        }
      } else {
        return DECIMAL;
      }
    }
  /**
   * If the current character is an underscore, the previous and next
   * characters need to be valid digits or underscores.
   * 
   * @return true if the underscore is misplaced
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Literals.jrag:673
   */
  

    /**
     * If the current character is an underscore, the previous and next
     * characters need to be valid digits or underscores.
     *
     * @return true if the underscore is misplaced
     */
    private boolean misplacedUnderscore() {
      // first and last characters are never allowed to be an underscore
      if (idx == 0 || idx+1 == getLITERAL().length()) {
        return true;
      }

      switch (kind) {
        case DECIMAL:
          return !(isDecimalDigit(peek(-1)) && isDecimalDigit(peek(1)));
        case HEXADECIMAL:
          return !(isHexadecimalDigit(peek(-1)) && isHexadecimalDigit(peek(1)));
        case BINARY:
          return !(isBinaryDigit(peek(-1)) && isBinaryDigit(peek(1)));
      }
      throw new IllegalStateException("Unexpected literal kind");
    }
  /**
   * Report an illegal digit.
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Literals.jrag:693
   */
  

    /**
     * Report an illegal digit.
     */
    private Literal syntaxError(String msg) {
      return new IllegalLiteral(
          String.format("in %s literal \"%s\": %s", name(), getLITERAL(), msg));
    }
  /**
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Literals.jrag:698
   */
  

    private Literal unexpectedCharacter(char c) {
      return syntaxError("unexpected character '" + c + "'; not a valid digit");
    }
  /**
   * Returns a string of only the lower case digits of the
   * parsed numeric literal.
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Literals.jrag:706
   */
  

    /**
     * Returns a string of only the lower case digits of the
     * parsed numeric literal.
     */
    private String getLiteralString() {
      return buf.toString().toLowerCase();
    }
  /**
   * Parse and build an IntegerLiteral, LongLiteral,
   * FloatingPointLiteral or DoubleLiteral. Returns an
   * IllegalLiteral if the numeric literal can not be
   * parsed.
   * 
   * Note: does not perform bounds checks.
   * 
   * @return a concrete literal on success, or an IllegalLiteral if there is a syntax error
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Literals.jrag:720
   */
  

    /**
     * Parse and build an IntegerLiteral, LongLiteral,
     * FloatingPointLiteral or DoubleLiteral. Returns an
     * IllegalLiteral if the numeric literal can not be
     * parsed.
     *
     * Note: does not perform bounds checks.
     *
     * @return a concrete literal on success, or an IllegalLiteral if there is a syntax error
     */
    private Literal buildLiteral() {
      NumericLiteral literal;
      setDigits(buf.toString().toLowerCase());

      if (!floating) {
        if (!whole) {
          return syntaxError("at least one digit is required");
        }

        // check if the literal is octal, and if so report illegal digits
        if (kind == DECIMAL) {
          if (digits.charAt(0) == '0') {
            kind = OCTAL;
            for (int idx = 1; idx < digits.length(); ++idx) {
              char c = digits.charAt(idx);
              if (c < '0' || c > '7') {
                return unexpectedCharacter(c);
              }
            }
          }
        }

        if (isLong) {
          literal = new LongLiteral(getLITERAL());
        } else {
          literal = new IntegerLiteral(getLITERAL());
        }
      } else {
        if (kind == HEXADECIMAL && !exponent) {
          return syntaxError("exponent is missing");
        }

        if (exponentSpecifier && !exponent) {
          return syntaxError("expected exponent after exponent specifier");
        }

        if (!(whole || fraction)) {
          return syntaxError("at least one digit is required in "
              + "either the whole or fraction part");
        }

        if (kind == HEXADECIMAL) {
          digits = "0x"+digits;// digits parsed with Float or Double
        }

        if (isFloat) {
          literal = new FloatingPointLiteral(getLITERAL());
        } else {
          literal = new DoubleLiteral(getLITERAL());
        }
      }

      literal.setDigits(getDigits());
      literal.setKind(getKind());
      return literal;
    }
  /**
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Literals.jrag:777
   */
  

    private Literal parseDigits() {
      // While we have at least one more character/digit
      while (have(1)) {
        char c = peek(0);
        switch (c) {
          case '_':
            if (misplacedUnderscore()) {
              return syntaxError("misplaced underscore - underscores may only "
                  + "be used within sequences of digits");
            }
            skip(1);
            continue;
          case '.':
            if (kind != DECIMAL && kind != HEXADECIMAL) {
              return unexpectedCharacter(c);
            }
            return parseFractionPart();
          case 'l':
          case 'L':
            if (have(2)) {
              return syntaxError("extra digits/characters after suffix " + c);
            }
            isLong = true;
            skip(1);
            continue;
          case 'f':
          case 'F':
            if (kind == BINARY) {
              return unexpectedCharacter(c);
            }
            isFloat = true;
          case 'd':
          case 'D':
            if (kind == BINARY) {
              return unexpectedCharacter(c);
            }
            if (kind != HEXADECIMAL) {
              if (have(2)) {
                return syntaxError("extra digits/characters after type suffix " + c);
              }
              floating = true;
              skip(1);
            } else {
              whole = true;
              pushChar();
            }
            continue;
        }

        switch (kind) {
          case DECIMAL:
            if (c == 'e' || c == 'E') {
              return parseExponentPart();

            } else if (c == 'f' || c == 'F') {
              if (have(2)) {
                return syntaxError("extra digits/characters after type suffix " + c);
              }
              floating = true;
              isFloat = true;
              skip(1);
            } else if (c == 'd' || c == 'D') {
              if (have(2)) {
                return syntaxError("extra digits/characters after type suffix " + c);
              }
              floating = true;
              skip(1);
            } else {
              if (!isDecimalDigit(c)) {
                return unexpectedCharacter(c);
              }
              whole = true;
              pushChar();
            }
            continue;
          case HEXADECIMAL:
            if (c == 'p' || c == 'P') {
              return parseExponentPart();
            }

            if (!isHexadecimalDigit(c)) {
              return unexpectedCharacter(c);
            }
            whole = true;
            pushChar();
            continue;
          case BINARY:
            if (!isBinaryDigit(c)) {
              return unexpectedCharacter(c);
            }
            whole = true;
            pushChar();
            continue;
        }
      }

      return buildLiteral();
    }
  /**
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Literals.jrag:876
   */
  

    private Literal parseFractionPart() {
      floating = true;

      // Current char is the decimal period.
      pushChar();

      // While we have at least one more character/digit.
      while (have(1)) {
        char c = peek(0);
        switch (c) {
          case '_':
            if (misplacedUnderscore()) {
              return syntaxError("misplaced underscore - underscores may only "
                  + "be used as separators within sequences of valid digits");
            }
            skip(1);
            continue;
          case '.':
            return syntaxError("multiple decimal periods are not allowed");
        }

        if (kind == DECIMAL) {
          if (c == 'e' || c == 'E') {
            return parseExponentPart();

          } else if (c == 'f' || c == 'F') {
            if (have(2)) {
              return syntaxError("extra digits/characters after type suffix " + c);
            }
            floating = true;
            isFloat = true;
            skip(1);
          } else if (c == 'd' || c == 'D') {
            if (have(2)) {
              return syntaxError("extra digits/characters after type suffix " + c);
            }
            floating = true;
            skip(1);
          } else {
            if (!isDecimalDigit(c)) {
              return unexpectedCharacter(c);
            }
            pushChar();
            fraction = true;
          }
        } else { // kind == HEXADECIMAL
          if (c == 'p' || c == 'P') {
            return parseExponentPart();
          }

          if (!isHexadecimalDigit(c)) {
            return unexpectedCharacter(c);
          }
          fraction = true;
          pushChar();
        }
      }

      return buildLiteral();
    }
  /**
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Literals.jrag:937
   */
  

    private Literal parseExponentPart() {
      floating = true;

      // Current char is the exponent specifier char.
      pushChar();

      exponentSpecifier = true;

      // Exponent sign.
      if (have(1) && (peek(0) == '+' || peek(0) == '-')) {
        pushChar();
      }

      // While we have at least one more character/digit.
      while (have(1)) {
        char c = peek(0);
        switch (c) {
          case '_':
            if (misplacedUnderscore()) {
              return syntaxError("misplaced underscore - underscores may only "
                  + "be used as separators within sequences of valid digits");
            }
            skip(1);
            continue;
          case '-':
          case '+':
            return syntaxError("exponent sign character is only allowed as "
                + "the first character of the exponent part of a "
                + "floating point literal");
          case '.':
            return syntaxError("multiple decimal periods are not allowed");
          case 'p':
          case 'P':
          case 'e':
          case 'E':
            return syntaxError("multiple exponent specifiers are not allowed");
          case 'f':
          case 'F':
            isFloat = true;
          case 'd':
          case 'D':
            if (have(2)) {
              return syntaxError("extra digits/characters after type suffix " + c);
            }
            skip(1);
            continue;
        }

        // Exponent is a signed integer.
        if (!isDecimalDigit(c)) {
          return unexpectedCharacter(c);
        }
        pushChar();
        exponent = true;
      }

      return buildLiteral();
    }
  /**
   * @declaredat ASTNode:1
   */
  public NumericLiteral() {
    super();
  }
  /**
   * Initializes the child array to the correct size.
   * Initializes List and Opt nta children.
   * @apilevel internal
   * @ast method
   * @declaredat ASTNode:10
   */
  public void init$Children() {
  }
  /**
   * @declaredat ASTNode:12
   */
  public NumericLiteral(String p0) {
    setLITERAL(p0);
  }
  /**
   * @declaredat ASTNode:15
   */
  public NumericLiteral(beaver.Symbol p0) {
    setLITERAL(p0);
  }
  /**
   * @apilevel low-level
   * @declaredat ASTNode:21
   */
  protected int numChildren() {
    return 0;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:27
   */
  public boolean mayHaveRewrite() {
    return true;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:33
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    type_reset();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:40
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:46
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:52
   */
  public NumericLiteral clone() throws CloneNotSupportedException {
    NumericLiteral node = (NumericLiteral) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:59
   */
  public NumericLiteral copy() {
    try {
      NumericLiteral node = (NumericLiteral) clone();
      node.parent = null;
      if (children != null) {
        node.children = (ASTNode[]) children.clone();
      }
      return node;
    } catch (CloneNotSupportedException e) {
      throw new Error("Error: clone not supported for " + getClass().getName());
    }
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @deprecated Please use treeCopy or treeCopyNoTransform instead
   * @declaredat ASTNode:78
   */
  @Deprecated
  public NumericLiteral fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:88
   */
  public NumericLiteral treeCopyNoTransform() {
    NumericLiteral tree = (NumericLiteral) copy();
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        ASTNode child = (ASTNode) children[i];
        if (child != null) {
          child = child.treeCopyNoTransform();
          tree.setChild(child, i);
        }
      }
    }
    return tree;
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The subtree of this node is traversed to trigger rewrites before copy.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:108
   */
  public NumericLiteral treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:115
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenString_LITERAL == ((NumericLiteral)node).tokenString_LITERAL);    
  }
  /**
   * Replaces the lexeme LITERAL.
   * @param value The new value for the lexeme LITERAL.
   * @apilevel high-level
   */
  public void setLITERAL(String value) {
    tokenString_LITERAL = value;
  }
  /**
   * JastAdd-internal setter for lexeme LITERAL using the Beaver parser.
   * @param symbol Symbol containing the new value for the lexeme LITERAL
   * @apilevel internal
   */
  public void setLITERAL(beaver.Symbol symbol) {
    if (symbol.value != null && !(symbol.value instanceof String))
    throw new UnsupportedOperationException("setLITERAL is only valid for String lexemes");
    tokenString_LITERAL = (String)symbol.value;
    LITERALstart = symbol.getStart();
    LITERALend = symbol.getEnd();
  }
  /**
   * Retrieves the value for the lexeme LITERAL.
   * @return The value for the lexeme LITERAL.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Token(name="LITERAL")
  public String getLITERAL() {
    return tokenString_LITERAL != null ? tokenString_LITERAL : "";
  }
  /**
   * This is a refactored version of Literal.parseLong which supports
   * binary literals. This version of parseLong is implemented as an
   * attribute rather than a static method. Perhaps some slight
   * performance boost could be gained from keeping it static, but with
   * the loss of declarative- and ReRAGness.
   * 
   * There exists only a parseLong, and not a parseInteger. Parsing
   * of regular integer literals works the same, but with stricter
   * bounds requirements on the resulting parsed value.
   * @attribute syn
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Literals.jrag:241
   */
  @ASTNodeAnnotation.Attribute
  public long parseLong() {
    {
        switch (getKind()) {
          case HEXADECIMAL:
            return parseLongHexadecimal();
          case OCTAL:
            return parseLongOctal();
          case BINARY:
            return parseLongBinary();
          case DECIMAL:
          default:
            return parseLongDecimal();
        }
      }
  }
  /**
   * Parse a hexadecimal long literal.
   * 
   * @throws NumberFormatException if the literal is too large.
   * @attribute syn
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Literals.jrag:260
   */
  @ASTNodeAnnotation.Attribute
  public long parseLongHexadecimal() {
    {
        long val = 0;
        if (digits.length() > 16) {
          for (int i = 0; i < digits.length()-16; i++)
            if (digits.charAt(i) != '0') {
              throw new NumberFormatException("");
            }
        }
        for (int i = 0; i < digits.length(); i++) {
          int c = digits.charAt(i);
          if (c >= 'a' && c <= 'f') {
            c = c - 'a' + 10;
          } else {
            c = c - '0';
          }
          val = val * 16 + c;
        }
        return val;
      }
  }
  /**
   * Parse an octal long literal.
   * 
   * @throws NumberFormatException if the literal is too large.
   * @attribute syn
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Literals.jrag:285
   */
  @ASTNodeAnnotation.Attribute
  public long parseLongOctal() {
    {
        long val = 0;
        if (digits.length() > 21) {
          for (int i = 0; i < digits.length() - 21; i++)
            if (i == digits.length() - 21 - 1) {
              if (digits.charAt(i) != '0' && digits.charAt(i) != '1') {
                throw new NumberFormatException("");
              }
            } else {
              if (digits.charAt(i) != '0') {
                throw new NumberFormatException("");
              }
            }
        }
        for (int i = 0; i < digits.length(); i++) {
          int c = digits.charAt(i) - '0';
          val = val * 8 + c;
        }
        return val;
      }
  }
  /**
   * Parse a binary long literal.
   * 
   * @throws NumberFormatException if the literal is too large.
   * @attribute syn
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Literals.jrag:311
   */
  @ASTNodeAnnotation.Attribute
  public long parseLongBinary() {
    {
        long val = 0;
        if (digits.length() > 64) {
          for (int i = 0; i < digits.length()-64; i++)
            if (digits.charAt(i) != '0') {
              throw new NumberFormatException("");
            }
        }
        for (int i = 0; i < digits.length(); ++i) {
          if (digits.charAt(i) == '1') {
            val |= 1L << (digits.length()-i-1);
          }
        }
        return val;
      }
  }
  /**
   * Parse a decimal long literal.
   * @throws NumberFormatException if the literal is too large.
   * @attribute syn
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Literals.jrag:331
   */
  @ASTNodeAnnotation.Attribute
  public long parseLongDecimal() {
    {
        long val = 0;
        long prev = 0;
        for (int i = 0; i < digits.length(); i++) {
          prev = val;
          int c = digits.charAt(i);
          if (c >= '0' && c <= '9') {
            c = c - '0';
          } else {
            throw new NumberFormatException("unexpected digit: " + c);
          }
          val = val * 10 + c;
          if (val < prev) {
            boolean negMinValue = i == (digits.length()-1) &&
              isNegative() && val == Long.MIN_VALUE;
            if (!negMinValue) {
              throw new NumberFormatException("");
            }
          }
        }
        if (val == Long.MIN_VALUE) {
          return val;
        }
        if (val < 0) {
          throw new NumberFormatException("");
        }
        return isNegative() ? -val : val;
      }
  }
  @ASTNodeAnnotation.Attribute
  public boolean needsRewrite() {
    boolean needsRewrite_value = true;

    return needsRewrite_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isNegative() {
    boolean isNegative_value = getLITERAL().charAt(0) == '-';

    return isNegative_value;
  }
  @ASTNodeAnnotation.Attribute
  public String getDigits() {
    String getDigits_value = digits;

    return getDigits_value;
  }
  @ASTNodeAnnotation.Attribute
  public int getKind() {
    int getKind_value = kind;

    return getKind_value;
  }
  /**
   * Get the radix of this literal.
   * @return 16 (hex), 10 (decimal), 8 (octal) or 2 (binary)
   * @attribute syn
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Literals.jrag:453
   */
  @ASTNodeAnnotation.Attribute
  public int getRadix() {
    {
        switch (kind) {
          case HEXADECIMAL:
            return 16;
          case OCTAL:
            return 8;
          case BINARY:
            return 2;
          case DECIMAL:
          default:
            return 10;
        }
      }
  }
  @ASTNodeAnnotation.Attribute
  public boolean isDecimal() {
    boolean isDecimal_value = kind == DECIMAL;

    return isDecimal_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isHex() {
    boolean isHex_value = kind == HEXADECIMAL;

    return isHex_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isOctal() {
    boolean isOctal_value = kind == OCTAL;

    return isOctal_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isBinary() {
    boolean isBinary_value = kind == BINARY;

    return isBinary_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean type_computed = false;
  /**
   * @apilevel internal
   */
  protected TypeDecl type_value;
  /**
   * @apilevel internal
   */
  private void type_reset() {
    type_computed = false;
    type_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public TypeDecl type() {
    ASTNode$State state = state();
    if (type_computed) {
      return type_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    type_value = unknownType();
    if (isFinal && num == state().boundariesCrossed) {
      type_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return type_value;
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    // Declared at @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Literals.jrag:404
    if (needsRewrite()) {
      return rewriteRule0();
    }
    return super.rewriteTo();
  }
  /**
   * @declaredat @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Literals.jrag:404
   * @apilevel internal
   */
  private Literal rewriteRule0() {
{
      return parse();
    }  }
}
