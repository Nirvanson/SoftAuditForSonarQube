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
 * @ast node
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\grammar\\Java.ast:147
 * @production Binary : {@link Expr} ::= <span class="component">LeftOperand:{@link Expr}</span> <span class="component">RightOperand:{@link Expr}</span>;

 */
public abstract class Binary extends Expr implements Cloneable {
  /**
   * @aspect Java4PrettyPrint
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\PrettyPrint.jadd:187
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print(getLeftOperand());
    out.print(" ");
    out.print(printOp());
    out.print(" ");
    out.print(getRightOperand());
  }
  /**
   * @declaredat ASTNode:1
   */
  public Binary() {
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
    children = new ASTNode[2];
  }
  /**
   * @declaredat ASTNode:13
   */
  public Binary(Expr p0, Expr p1) {
    setChild(p0, 0);
    setChild(p1, 1);
  }
  /**
   * @apilevel low-level
   * @declaredat ASTNode:20
   */
  protected int numChildren() {
    return 2;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:26
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:32
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    isConstant_reset();
    isDAafterTrue_Variable_reset();
    isDAafterFalse_Variable_reset();
    isDAafter_Variable_reset();
    isDUafter_Variable_reset();
    isDUbefore_Variable_reset();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:44
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:50
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:56
   */
  public Binary clone() throws CloneNotSupportedException {
    Binary node = (Binary) super.clone();
    return node;
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @deprecated Please use treeCopy or treeCopyNoTransform instead
   * @declaredat ASTNode:67
   */
  @Deprecated
  public abstract Binary fullCopy();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:75
   */
  public abstract Binary treeCopyNoTransform();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The subtree of this node is traversed to trigger rewrites before copy.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:83
   */
  public abstract Binary treeCopy();
  /**
   * Replaces the LeftOperand child.
   * @param node The new node to replace the LeftOperand child.
   * @apilevel high-level
   */
  public void setLeftOperand(Expr node) {
    setChild(node, 0);
  }
  /**
   * Retrieves the LeftOperand child.
   * @return The current node used as the LeftOperand child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="LeftOperand")
  public Expr getLeftOperand() {
    return (Expr) getChild(0);
  }
  /**
   * Retrieves the LeftOperand child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the LeftOperand child.
   * @apilevel low-level
   */
  public Expr getLeftOperandNoTransform() {
    return (Expr) getChildNoTransform(0);
  }
  /**
   * Replaces the RightOperand child.
   * @param node The new node to replace the RightOperand child.
   * @apilevel high-level
   */
  public void setRightOperand(Expr node) {
    setChild(node, 1);
  }
  /**
   * Retrieves the RightOperand child.
   * @return The current node used as the RightOperand child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="RightOperand")
  public Expr getRightOperand() {
    return (Expr) getChild(1);
  }
  /**
   * Retrieves the RightOperand child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the RightOperand child.
   * @apilevel low-level
   */
  public Expr getRightOperandNoTransform() {
    return (Expr) getChildNoTransform(1);
  }
  /**
   * @aspect ConstantExpression
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ConstantExpression.jrag:298
   */
  private TypeDecl refined_ConstantExpression_Binary_binaryNumericPromotedType()
{
    TypeDecl leftType = left().type();
    TypeDecl rightType = right().type();
    if (leftType.isString()) {
      return leftType;
    }
    if (rightType.isString()) {
      return rightType;
    }
    if (leftType.isNumericType() && rightType.isNumericType()) {
      return leftType.binaryNumericPromotion(rightType);
    }
    if (leftType.isBoolean() && rightType.isBoolean()) {
      return leftType;
    }
    return unknownType();
  }
  /** The operator string used for pretty printing this expression. 
   * @attribute syn
   * @aspect PrettyPrintUtil
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\PrettyPrintUtil.jrag:270
   */
  @ASTNodeAnnotation.Attribute
  public abstract String printOp();
  /**
   * @apilevel internal
   */
  protected int isConstant_visited = -1;
  /**
   * @apilevel internal
   */
  private void isConstant_reset() {
    isConstant_computed = false;
    isConstant_initialized = false;
    isConstant_visited = -1;
  }
  /**
   * @apilevel internal
   */
  protected boolean isConstant_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean isConstant_initialized = false;
  /**
   * @apilevel internal
   */
  protected boolean isConstant_value;
  @ASTNodeAnnotation.Attribute
  public boolean isConstant() {
    if (isConstant_computed) {
      return isConstant_value;
    }
    ASTNode$State state = state();
    boolean new_isConstant_value;
    if (!isConstant_initialized) {
      isConstant_initialized = true;
      isConstant_value = false;
    }
    if (!state.IN_CIRCLE) {
      state.IN_CIRCLE = true;
      int num = state.boundariesCrossed;
      boolean isFinal = this.is$Final();
      do {
        isConstant_visited = state.CIRCLE_INDEX;
        state.CHANGE = false;
        new_isConstant_value = getLeftOperand().isConstant() && getRightOperand().isConstant();
        if (new_isConstant_value != isConstant_value) {
          state.CHANGE = true;
        }
        isConstant_value = new_isConstant_value;
        state.CIRCLE_INDEX++;
      } while (state.CHANGE);
      if (isFinal && num == state().boundariesCrossed) {
        isConstant_computed = true;
      } else {
        state.RESET_CYCLE = true;
        boolean $tmp = getLeftOperand().isConstant() && getRightOperand().isConstant();
        state.RESET_CYCLE = false;
        isConstant_computed = false;
        isConstant_initialized = false;
      }
      state.IN_CIRCLE = false;
      state.INTERMEDIATE_VALUE = false;
      return isConstant_value;
    }
    if (isConstant_visited != state.CIRCLE_INDEX) {
      isConstant_visited = state.CIRCLE_INDEX;
      if (state.RESET_CYCLE) {
        isConstant_computed = false;
        isConstant_initialized = false;
        isConstant_visited = -1;
        return isConstant_value;
      }
      new_isConstant_value = getLeftOperand().isConstant() && getRightOperand().isConstant();
      if (new_isConstant_value != isConstant_value) {
        state.CHANGE = true;
      }
      isConstant_value = new_isConstant_value;
      state.INTERMEDIATE_VALUE = true;
      return isConstant_value;
    }
    state.INTERMEDIATE_VALUE = true;
    return isConstant_value;
  }
  @ASTNodeAnnotation.Attribute
  public Expr left() {
    Expr left_value = getLeftOperand();

    return left_value;
  }
  @ASTNodeAnnotation.Attribute
  public Expr right() {
    Expr right_value = getRightOperand();

    return right_value;
  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ConstantExpression.jrag:298
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl binaryNumericPromotedType() {
    {
        TypeDecl leftType = left().type();
        TypeDecl rightType = right().type();
        if (leftType.isBoolean() && rightType.isBoolean()) {
          return leftType.isReferenceType() ? leftType.unboxed() : leftType;
        }
        return refined_ConstantExpression_Binary_binaryNumericPromotedType();
      }
  }
  /**
   * @apilevel internal
   */
  protected java.util.Map isDAafterTrue_Variable_values;
  /**
   * @apilevel internal
   */
  private void isDAafterTrue_Variable_reset() {
    isDAafterTrue_Variable_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isDAafterTrue(Variable v) {
    Object _parameters = v;
    if (isDAafterTrue_Variable_values == null) isDAafterTrue_Variable_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (isDAafterTrue_Variable_values.containsKey(_parameters)) {
      return (Boolean) isDAafterTrue_Variable_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean isDAafterTrue_Variable_value = isFalse() || getRightOperand().isDAafter(v);
    if (isFinal && num == state().boundariesCrossed) {
      isDAafterTrue_Variable_values.put(_parameters, isDAafterTrue_Variable_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return isDAafterTrue_Variable_value;
  }
  /**
   * @apilevel internal
   */
  protected java.util.Map isDAafterFalse_Variable_values;
  /**
   * @apilevel internal
   */
  private void isDAafterFalse_Variable_reset() {
    isDAafterFalse_Variable_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isDAafterFalse(Variable v) {
    Object _parameters = v;
    if (isDAafterFalse_Variable_values == null) isDAafterFalse_Variable_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (isDAafterFalse_Variable_values.containsKey(_parameters)) {
      return (Boolean) isDAafterFalse_Variable_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean isDAafterFalse_Variable_value = isTrue() || getRightOperand().isDAafter(v);
    if (isFinal && num == state().boundariesCrossed) {
      isDAafterFalse_Variable_values.put(_parameters, isDAafterFalse_Variable_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return isDAafterFalse_Variable_value;
  }
  /**
   * @apilevel internal
   */
  protected java.util.Map isDAafter_Variable_values;
  /**
   * @apilevel internal
   */
  private void isDAafter_Variable_reset() {
    isDAafter_Variable_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isDAafter(Variable v) {
    Object _parameters = v;
    if (isDAafter_Variable_values == null) isDAafter_Variable_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (isDAafter_Variable_values.containsKey(_parameters)) {
      return (Boolean) isDAafter_Variable_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean isDAafter_Variable_value = getRightOperand().isDAafter(v);
    if (isFinal && num == state().boundariesCrossed) {
      isDAafter_Variable_values.put(_parameters, isDAafter_Variable_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return isDAafter_Variable_value;
  }
  /**
   * @apilevel internal
   */
  protected java.util.Map isDUafter_Variable_values;
  /**
   * @apilevel internal
   */
  private void isDUafter_Variable_reset() {
    isDUafter_Variable_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isDUafter(Variable v) {
    Object _parameters = v;
    if (isDUafter_Variable_values == null) isDUafter_Variable_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (isDUafter_Variable_values.containsKey(_parameters)) {
      return (Boolean) isDUafter_Variable_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean isDUafter_Variable_value = getRightOperand().isDUafter(v);
    if (isFinal && num == state().boundariesCrossed) {
      isDUafter_Variable_values.put(_parameters, isDUafter_Variable_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return isDUafter_Variable_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean modifiedInScope(Variable var) {
    boolean modifiedInScope_Variable_value = getLeftOperand().modifiedInScope(var) || getRightOperand().modifiedInScope(var);

    return modifiedInScope_Variable_value;
  }
  /**
   * @attribute inh
   * @aspect DU
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:785
   */
  @ASTNodeAnnotation.Attribute
  public boolean isDUbefore(Variable v) {
    Object _parameters = v;
    if (isDUbefore_Variable_values == null) isDUbefore_Variable_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (isDUbefore_Variable_values.containsKey(_parameters)) {
      return (Boolean) isDUbefore_Variable_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean isDUbefore_Variable_value = getParent().Define_isDUbefore(this, null, v);
    if (isFinal && num == state().boundariesCrossed) {
      isDUbefore_Variable_values.put(_parameters, isDUbefore_Variable_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return isDUbefore_Variable_value;
  }
  /**
   * @apilevel internal
   */
  protected java.util.Map isDUbefore_Variable_values;
  /**
   * @apilevel internal
   */
  private void isDUbefore_Variable_reset() {
    isDUbefore_Variable_values = null;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:255
   * @apilevel internal
   */
  public boolean Define_isDAbefore(ASTNode caller, ASTNode child, Variable v) {
    if (caller == getRightOperandNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:446
      return getLeftOperand().isDAafter(v);
    }
    else {
      return getParent().Define_isDAbefore(this, caller, v);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:779
   * @apilevel internal
   */
  public boolean Define_isDUbefore(ASTNode caller, ASTNode child, Variable v) {
    if (caller == getRightOperandNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:951
      return getLeftOperand().isDUafter(v);
    }
    else {
      return getParent().Define_isDUbefore(this, caller, v);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:196
   * @apilevel internal
   */
  public boolean Define_assignmentContext(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:197
   * @apilevel internal
   */
  public boolean Define_invocationContext(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:198
   * @apilevel internal
   */
  public boolean Define_castContext(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:199
   * @apilevel internal
   */
  public boolean Define_stringContext(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:200
   * @apilevel internal
   */
  public boolean Define_numericContext(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
