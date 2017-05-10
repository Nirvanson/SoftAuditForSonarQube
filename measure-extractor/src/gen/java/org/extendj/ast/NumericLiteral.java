/* This file was generated with JastAdd2 (http://jastadd.org) version 2.2.2 */
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
 * A NumericLiteral is a raw literal, produced by the parser.
 * NumericLiterals are rewritten to the best matching concrete
 * numeric literal kind, or IllegalLiteral.
 * @ast node
 * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\grammar\\Literals.ast:18
 * @production NumericLiteral : {@link Literal};

 */
public class NumericLiteral extends Literal implements Cloneable {
  /**
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\Literals.jrag:351
   */
  public static final int DECIMAL = 0;
  /**
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\Literals.jrag:352
   */
  public static final int HEXADECIMAL = 1;
  /**
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\Literals.jrag:353
   */
  public static final int OCTAL = 2;
  /**
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\Literals.jrag:354
   */
  public static final int BINARY = 3;
  /**
   * The trimmed digits.
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\Literals.jrag:418
   */
  protected String digits = "";
  /**
   * Sets the trimmed digits of this literal.
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\Literals.jrag:423
   */
  public void setDigits(String digits) {
    this.digits = digits;
  }
  /**
   * The literal kind tells which kind of literal it is;
   * either a DECIMAL, HEXADECIMAL, OCTAL or BINARY literal.
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\Literals.jrag:437
   */
  protected int kind = NumericLiteral.DECIMAL;
  /**
   * Sets the literal kind.
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\Literals.jrag:442
   */
  public void setKind(int kind) {
    this.kind = kind;
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
  /** @apilevel low-level 
   * @declaredat ASTNode:19
   */
  protected int numChildren() {
    return 0;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:25
   */
  public boolean mayHaveRewrite() {
    return true;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:29
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    rewrittenNode_reset();
    type_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:35
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:39
   */
  public NumericLiteral clone() throws CloneNotSupportedException {
    NumericLiteral node = (NumericLiteral) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:44
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
   * @declaredat ASTNode:63
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
   * @declaredat ASTNode:73
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
   * @declaredat ASTNode:93
   */
  public NumericLiteral treeCopy() {
    NumericLiteral tree = (NumericLiteral) copy();
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        ASTNode child = (ASTNode) getChild(i);
        if (child != null) {
          child = child.treeCopy();
          tree.setChild(child, i);
        }
      }
    }
    return tree;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:107
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenString_LITERAL == ((NumericLiteral) node).tokenString_LITERAL);    
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
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\Literals.jrag:232
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Java7Literals", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\Literals.jrag:232")
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
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\Literals.jrag:251
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Java7Literals", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\Literals.jrag:251")
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
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\Literals.jrag:276
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Java7Literals", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\Literals.jrag:276")
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
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\Literals.jrag:302
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Java7Literals", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\Literals.jrag:302")
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
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\Literals.jrag:322
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Java7Literals", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\Literals.jrag:322")
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
  /**
   * Utility attribute for literal rewriting.
   * Any of the NumericLiteral subclasses have already
   * been rewritten and/or parsed, and should not be
   * rewritten again.
   * 
   * @return true if this literal is a "raw", not-yet-parsed NumericLiteral
   * @attribute syn
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\Literals.jrag:364
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Java7Literals", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\Literals.jrag:364")
  public boolean needsRewrite() {
    boolean needsRewrite_value = true;
    return needsRewrite_value;
  }
  /**
   * @attribute syn
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\Literals.jrag:407
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Java7Literals", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\Literals.jrag:407")
  public boolean isNegative() {
    boolean isNegative_value = getLITERAL().charAt(0) == '-';
    return isNegative_value;
  }
  /**
   * Get the trimmed digits of this literal, excluding
   * underscore, prefix and suffix.
   * @attribute syn
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\Literals.jrag:413
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Java7Literals", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\Literals.jrag:413")
  public String getDigits() {
    String getDigits_value = digits;
    return getDigits_value;
  }
  /**
   * The literal kind tells which kind of literal it is;
   * either a DECIMAL, HEXADECIMAL, OCTAL or BINARY literal.
   * @attribute syn
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\Literals.jrag:431
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Java7Literals", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\Literals.jrag:431")
  public int getKind() {
    int getKind_value = kind;
    return getKind_value;
  }
  /**
   * Get the radix of this literal.
   * @return 16 (hex), 10 (decimal), 8 (octal) or 2 (binary)
   * @attribute syn
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\Literals.jrag:450
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Java7Literals", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\Literals.jrag:450")
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
  /**
   * @return true if the literal is a decimal literal
   * @attribute syn
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\Literals.jrag:467
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Java7Literals", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\Literals.jrag:467")
  public boolean isDecimal() {
    boolean isDecimal_value = kind == DECIMAL;
    return isDecimal_value;
  }
  /**
   * @return true if the literal is a hexadecimal literal
   * @attribute syn
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\Literals.jrag:472
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Java7Literals", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\Literals.jrag:472")
  public boolean isHex() {
    boolean isHex_value = kind == HEXADECIMAL;
    return isHex_value;
  }
  /**
   * @return true if the literal is an octal literal
   * @attribute syn
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\Literals.jrag:477
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Java7Literals", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\Literals.jrag:477")
  public boolean isOctal() {
    boolean isOctal_value = kind == OCTAL;
    return isOctal_value;
  }
  /**
   * @return true if the literal is a binary literal
   * @attribute syn
   * @aspect Java7Literals
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\Literals.jrag:482
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Java7Literals", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\Literals.jrag:482")
  public boolean isBinary() {
    boolean isBinary_value = kind == BINARY;
    return isBinary_value;
  }
  /** @apilevel internal */
  private void type_reset() {
    type_computed = null;
    type_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle type_computed = null;

  /** @apilevel internal */
  protected TypeDecl type_value;

  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:296
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeAnalysis", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:296")
  public TypeDecl type() {
    ASTNode$State state = state();
    if (type_computed == ASTNode$State.NON_CYCLE || type_computed == state().cycle()) {
      return type_value;
    }
    type_value = unknownType();
    if (state().inCircle()) {
      type_computed = state().cycle();
    
    } else {
      type_computed = ASTNode$State.NON_CYCLE;
    
    }
    return type_value;
  }
  /** @apilevel internal */
  public ASTNode rewriteTo() {
    // Declared at C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\Literals.jrag:397
    if (needsRewrite()) {
      return rewriteRule0();
    }
    return super.rewriteTo();
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\Literals.jrag:397
   * @apilevel internal
   */
  private Literal rewriteRule0() {
{
      NumericLiteralParser parser = new NumericLiteralParser(getLITERAL());
      Literal literal = parser.parse();
      literal.setStart(LITERALstart);
      literal.setEnd(LITERALend);
      return literal;
    }  }
  /** @apilevel internal */
  public boolean canRewrite() {
    // Declared at C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\Literals.jrag:397
    if (needsRewrite()) {
      return true;
    }
    return false;
  }
  /** @apilevel internal */
  private void rewrittenNode_reset() {
    rewrittenNode_computed = false;
    rewrittenNode_initialized = false;
    rewrittenNode_value = null;
    rewrittenNode_cycle = null;
  }
/** @apilevel internal */
protected ASTNode$State.Cycle rewrittenNode_cycle = null;
  /** @apilevel internal */
  protected boolean rewrittenNode_computed = false;

  /** @apilevel internal */
  protected ASTNode rewrittenNode_value;
  /** @apilevel internal */
  protected boolean rewrittenNode_initialized = false;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="", declaredAt=":0")
  public ASTNode rewrittenNode() {
    if (rewrittenNode_computed) {
      return rewrittenNode_value;
    }
    ASTNode$State state = state();
    if (!rewrittenNode_initialized) {
      rewrittenNode_initialized = true;
      rewrittenNode_value = this;
      if (rewrittenNode_value != null) {
        rewrittenNode_value.setParent(getParent());
      }
    }
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      do {
        rewrittenNode_cycle = state.nextCycle();
        ASTNode new_rewrittenNode_value = rewrittenNode_value.rewriteTo();
        if (new_rewrittenNode_value != rewrittenNode_value || new_rewrittenNode_value.canRewrite()) {
          state.setChangeInCycle();
        }
        rewrittenNode_value = new_rewrittenNode_value;
        if (rewrittenNode_value != null) {
          rewrittenNode_value.setParent(getParent());
        }
      } while (state.testAndClearChangeInCycle());
      rewrittenNode_computed = true;

      state.leaveCircle();
    } else if (rewrittenNode_cycle != state.cycle()) {
      rewrittenNode_cycle = state.cycle();
      ASTNode new_rewrittenNode_value = rewrittenNode_value.rewriteTo();
      if (new_rewrittenNode_value != rewrittenNode_value || new_rewrittenNode_value.canRewrite()) {
        state.setChangeInCycle();
      }
      rewrittenNode_value = new_rewrittenNode_value;
      if (rewrittenNode_value != null) {
        rewrittenNode_value.setParent(getParent());
      }
    } else {
    }
    return rewrittenNode_value;
  }
}
