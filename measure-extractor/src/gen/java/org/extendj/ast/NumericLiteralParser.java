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
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.IOException;
import java.util.Set;
import beaver.*;
import org.jastadd.util.*;
import java.util.zip.*;
import java.io.*;
import org.jastadd.util.PrettyPrintable;
import org.jastadd.util.PrettyPrinter;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
/**
 * @ast class
 * @aspect Java7Literals
 * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\Literals.jrag:516
 */
public class NumericLiteralParser extends java.lang.Object {
  
    private final String literal;

  
    private String digits = "";

  
    private StringBuilder buf = new StringBuilder();

  
    private int idx = 0;

  
    private int kind;

  
    private boolean whole;

   // Have whole part?
    private boolean fraction;

   // Have fraction part?
    private boolean exponentSpecifier;

   // Have exponent specifier?
    private boolean exponent;

   // Have exponent part?
    private boolean floating;

   // Is floating point?
    private boolean isFloat;

  
    private boolean isLong;

  

    public NumericLiteralParser(String literal) {
      this.literal = literal;
    }

  

    /**
     * @return a name describing the literal, used in parsing error messages.
     */
    private String name() {
      String name;
      switch (kind) {
        case NumericLiteral.DECIMAL:
          name = "decimal";
          break;
        case NumericLiteral.HEXADECIMAL:
          name = "hexadecimal";
          break;
        case NumericLiteral.OCTAL:
          name = "octal";
          break;
        case NumericLiteral.BINARY:
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
     */
    private void pushChar() {
      buf.append(literal.charAt(idx++));
    }

  

    /**
     * Skip ahead n chracters in the literal.
     */
    private void skip(int n) {
      idx += n;
    }

  

    /**
     * @return true if there exists at least n more characters
     * in the literal
     */
    private boolean have(int n) {
      return literal.length() >= idx + n;
    }

  

    /**
     * Look at the n'th next character.
     */
    private char peek(int n) {
      return literal.charAt(idx + n);
    }

  

    /**
     * @return true if the character c is a decimal digit
     */
    private static final boolean isDecimalDigit(char c) {
      return c == '_' || c >= '0' && c <= '9';
    }

  

    /**
     * @return true if the character c is a hexadecimal digit
     */
    private static final boolean isHexadecimalDigit(char c) {
      return c == '_' || c >= '0' && c <= '9' || c >= 'a' && c <= 'f' || c >= 'A' && c <= 'F';
    }

  

    /**
     * @return true if the character c is a binary digit
     */
    private static final boolean isBinaryDigit(char c) {
      return c == '_' || c == '0' || c == '1';
    }

  

    /**
     * @return true if the character c is an underscore
     */
    private static final boolean isUnderscore(char c) {
      return c == '_';
    }

  

    /**
     * Parse a literal. If there is a syntax error in the literal,
     * an IllegalLiteral will be returned.
     */
    public Literal parse() {
      if (idx != 0 || buf.length() != 0) {
        throw new IllegalStateException("Already parsed literal.");
      }
      if (literal.length() == 0) {
        throw new IllegalStateException("Can't parse empty literal.");
      }
      kind = classifyLiteral();
      if (!floating) {
        return parseDigits();
      } else {
        return parseFractionPart();
      }
    }

  

    /**
     * Classify the literal.
     *
     * @return either DECIMAL, HEXADECIMAL or BINARY
     */
    private int classifyLiteral() {
      if (peek(0) == '.') {
        floating = true;
        return NumericLiteral.DECIMAL;
      } else if (peek(0) == '0') {
        if (!have(2)) {
          // This is the only 1-length string that starts with 0!
          return NumericLiteral.DECIMAL;
        } else if (peek(1) == 'x' || peek(1) == 'X') {
          skip(2);
          return NumericLiteral.HEXADECIMAL;
        } else if (peek(1) == 'b' || peek(1) == 'B') {
          skip(2);
          return NumericLiteral.BINARY;
        } else {
          return NumericLiteral.DECIMAL;
        }
      } else {
        return NumericLiteral.DECIMAL;
      }
    }

  

    /**
     * If the current character is an underscore, the previous and next
     * characters need to be valid digits or underscores.
     *
     * @return true if the underscore is misplaced
     */
    private boolean misplacedUnderscore() {
      // first and last characters are never allowed to be an underscore
      if (idx == 0 || idx + 1 == literal.length()) {
        return true;
      }

      switch (kind) {
        case NumericLiteral.DECIMAL:
          return !(isDecimalDigit(peek(-1)) && isDecimalDigit(peek(1)));
        case NumericLiteral.HEXADECIMAL:
          return !(isHexadecimalDigit(peek(-1)) && isHexadecimalDigit(peek(1)));
        case NumericLiteral.BINARY:
          return !(isBinaryDigit(peek(-1)) && isBinaryDigit(peek(1)));
      }
      throw new IllegalStateException("Unexpected literal kind");
    }

  

    /**
     * Report an illegal digit.
     */
    private Literal syntaxError(String msg) {
      return new IllegalLiteral(String.format("in %s literal \"%s\": %s", name(), literal, msg));
    }

  

    private Literal unexpectedCharacter(char c) {
      return syntaxError("unexpected character '" + c + "'; not a valid digit");
    }

  

    /**
     * Returns a string of only the lower case digits of the
     * parsed numeric literal.
     */
    private String getLiteralString() {
      return buf.toString().toLowerCase();
    }

  

    /**
     * Parse and build an IntegerLiteral, LongLiteral, FloatingPointLiteral or
     * DoubleLiteral. Returns an IllegalLiteral if the numeric literal can not
     * be parsed.
     *
     * <p>Note: does not perform bounds checks.
     *
     * @return a concrete literal on success, or an IllegalLiteral if there is a syntax error
     */
    private Literal buildLiteral() {
      NumericLiteral result;
      digits = buf.toString().toLowerCase();

      if (!floating) {
        if (!whole) {
          return syntaxError("at least one digit is required");
        }

        // Check if the literal is octal, and if so report illegal digits.
        if (kind == NumericLiteral.DECIMAL) {
          if (digits.charAt(0) == '0') {
            kind = NumericLiteral.OCTAL;
            for (int idx = 1; idx < digits.length(); ++idx) {
              char c = digits.charAt(idx);
              if (c < '0' || c > '7') {
                return unexpectedCharacter(c);
              }
            }
          }
        }

        if (isLong) {
          result = new LongLiteral(literal);
        } else {
          result = new IntegerLiteral(literal);
        }
      } else {
        if (kind == NumericLiteral.HEXADECIMAL && !exponent) {
          return syntaxError("exponent is missing");
        }

        if (exponentSpecifier && !exponent) {
          return syntaxError("expected exponent after exponent specifier");
        }

        if (!(whole || fraction)) {
          return syntaxError("at least one digit is required in "
              + "either the whole or fraction part");
        }

        if (kind == NumericLiteral.HEXADECIMAL) {
          digits = "0x" + digits;// Digits parsed with Float or Double.
        }

        if (isFloat) {
          result = new FloatingPointLiteral(literal);
        } else {
          result = new DoubleLiteral(literal);
        }
      }
      result.setDigits(digits);
      result.setKind(kind);
      return result;
    }

  

    private Literal parseDigits() {
      while (have(1)) { // Continue while there is at least one more character/digit.
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
            if (kind != NumericLiteral.DECIMAL && kind != NumericLiteral.HEXADECIMAL) {
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
            if (kind == NumericLiteral.BINARY) {
              return unexpectedCharacter(c);
            }
            isFloat = true;
          case 'd':
          case 'D':
            if (kind == NumericLiteral.BINARY) {
              return unexpectedCharacter(c);
            }
            if (kind != NumericLiteral.HEXADECIMAL) {
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
          case NumericLiteral.DECIMAL:
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
          case NumericLiteral.HEXADECIMAL:
            if (c == 'p' || c == 'P') {
              return parseExponentPart();
            }

            if (!isHexadecimalDigit(c)) {
              return unexpectedCharacter(c);
            }
            whole = true;
            pushChar();
            continue;
          case NumericLiteral.BINARY:
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

        if (kind == NumericLiteral.DECIMAL) {
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


}
