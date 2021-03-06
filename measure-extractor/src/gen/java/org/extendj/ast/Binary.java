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
 * @ast node
 * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\grammar\\Java.ast:149
 * @production Binary : {@link Expr} ::= <span class="component">LeftOperand:{@link Expr}</span> <span class="component">RightOperand:{@link Expr}</span>;

 */
public abstract class Binary extends Expr implements Cloneable {
  /**
   * @aspect Java4PrettyPrint
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\PrettyPrint.jadd:104
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
  /** @apilevel low-level 
   * @declaredat ASTNode:18
   */
  protected int numChildren() {
    return 2;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:24
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:28
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    isConstant_reset();
    assignedAfterTrue_Variable_reset();
    assignedAfterFalse_Variable_reset();
    unassignedAfter_Variable_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:36
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:40
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
   * @declaredat ASTNode:51
   */
  @Deprecated
  public abstract Binary fullCopy();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:59
   */
  public abstract Binary treeCopyNoTransform();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The subtree of this node is traversed to trigger rewrites before copy.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:67
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
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:461
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
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\PrettyPrintUtil.jrag:242
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="PrettyPrintUtil", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\PrettyPrintUtil.jrag:242")
  public abstract String printOp();
/** @apilevel internal */
protected ASTNode$State.Cycle isConstant_cycle = null;
  /** @apilevel internal */
  private void isConstant_reset() {
    isConstant_computed = false;
    isConstant_initialized = false;
    isConstant_cycle = null;
  }
  /** @apilevel internal */
  protected boolean isConstant_computed = false;

  /** @apilevel internal */
  protected boolean isConstant_value;
  /** @apilevel internal */
  protected boolean isConstant_initialized = false;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="ConstantExpression", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:401")
  public boolean isConstant() {
    if (isConstant_computed) {
      return isConstant_value;
    }
    ASTNode$State state = state();
    if (!isConstant_initialized) {
      isConstant_initialized = true;
      isConstant_value = false;
    }
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      do {
        isConstant_cycle = state.nextCycle();
        boolean new_isConstant_value = getLeftOperand().isConstant() && getRightOperand().isConstant();
        if (new_isConstant_value != isConstant_value) {
          state.setChangeInCycle();
        }
        isConstant_value = new_isConstant_value;
      } while (state.testAndClearChangeInCycle());
      isConstant_computed = true;

      state.leaveCircle();
    } else if (isConstant_cycle != state.cycle()) {
      isConstant_cycle = state.cycle();
      boolean new_isConstant_value = getLeftOperand().isConstant() && getRightOperand().isConstant();
      if (new_isConstant_value != isConstant_value) {
        state.setChangeInCycle();
      }
      isConstant_value = new_isConstant_value;
    } else {
    }
    return isConstant_value;
  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:457
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstantExpression", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:457")
  public Expr left() {
    Expr left_value = getLeftOperand();
    return left_value;
  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:459
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstantExpression", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:459")
  public Expr right() {
    Expr right_value = getRightOperand();
    return right_value;
  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:461
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstantExpression", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:461")
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
  /** @apilevel internal */
  private void assignedAfterTrue_Variable_reset() {
    assignedAfterTrue_Variable_values = null;
  }
  protected java.util.Map assignedAfterTrue_Variable_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="DefiniteAssignment", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:491")
  public boolean assignedAfterTrue(Variable v) {
    Object _parameters = v;
    if (assignedAfterTrue_Variable_values == null) assignedAfterTrue_Variable_values = new java.util.HashMap(4);
    ASTNode$State.CircularValue _value;
    if (assignedAfterTrue_Variable_values.containsKey(_parameters)) {
      Object _cache = assignedAfterTrue_Variable_values.get(_parameters);
      if (!(_cache instanceof ASTNode$State.CircularValue)) {
        return (Boolean) _cache;
      } else {
        _value = (ASTNode$State.CircularValue) _cache;
      }
    } else {
      _value = new ASTNode$State.CircularValue();
      assignedAfterTrue_Variable_values.put(_parameters, _value);
      _value.value = true;
    }
    ASTNode$State state = state();
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      boolean new_assignedAfterTrue_Variable_value;
      do {
        _value.cycle = state.nextCycle();
        new_assignedAfterTrue_Variable_value = isFalse() || getRightOperand().assignedAfter(v);
        if (new_assignedAfterTrue_Variable_value != ((Boolean)_value.value)) {
          state.setChangeInCycle();
          _value.value = new_assignedAfterTrue_Variable_value;
        }
      } while (state.testAndClearChangeInCycle());
      assignedAfterTrue_Variable_values.put(_parameters, new_assignedAfterTrue_Variable_value);

      state.leaveCircle();
      return new_assignedAfterTrue_Variable_value;
    } else if (_value.cycle != state.cycle()) {
      _value.cycle = state.cycle();
      boolean new_assignedAfterTrue_Variable_value = isFalse() || getRightOperand().assignedAfter(v);
      if (new_assignedAfterTrue_Variable_value != ((Boolean)_value.value)) {
        state.setChangeInCycle();
        _value.value = new_assignedAfterTrue_Variable_value;
      }
      return new_assignedAfterTrue_Variable_value;
    } else {
      return (Boolean) _value.value;
    }
  }
  /** @apilevel internal */
  private void assignedAfterFalse_Variable_reset() {
    assignedAfterFalse_Variable_values = null;
  }
  protected java.util.Map assignedAfterFalse_Variable_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="DefiniteAssignment", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:494")
  public boolean assignedAfterFalse(Variable v) {
    Object _parameters = v;
    if (assignedAfterFalse_Variable_values == null) assignedAfterFalse_Variable_values = new java.util.HashMap(4);
    ASTNode$State.CircularValue _value;
    if (assignedAfterFalse_Variable_values.containsKey(_parameters)) {
      Object _cache = assignedAfterFalse_Variable_values.get(_parameters);
      if (!(_cache instanceof ASTNode$State.CircularValue)) {
        return (Boolean) _cache;
      } else {
        _value = (ASTNode$State.CircularValue) _cache;
      }
    } else {
      _value = new ASTNode$State.CircularValue();
      assignedAfterFalse_Variable_values.put(_parameters, _value);
      _value.value = true;
    }
    ASTNode$State state = state();
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      boolean new_assignedAfterFalse_Variable_value;
      do {
        _value.cycle = state.nextCycle();
        new_assignedAfterFalse_Variable_value = isTrue() || getRightOperand().assignedAfter(v);
        if (new_assignedAfterFalse_Variable_value != ((Boolean)_value.value)) {
          state.setChangeInCycle();
          _value.value = new_assignedAfterFalse_Variable_value;
        }
      } while (state.testAndClearChangeInCycle());
      assignedAfterFalse_Variable_values.put(_parameters, new_assignedAfterFalse_Variable_value);

      state.leaveCircle();
      return new_assignedAfterFalse_Variable_value;
    } else if (_value.cycle != state.cycle()) {
      _value.cycle = state.cycle();
      boolean new_assignedAfterFalse_Variable_value = isTrue() || getRightOperand().assignedAfter(v);
      if (new_assignedAfterFalse_Variable_value != ((Boolean)_value.value)) {
        state.setChangeInCycle();
        _value.value = new_assignedAfterFalse_Variable_value;
      }
      return new_assignedAfterFalse_Variable_value;
    } else {
      return (Boolean) _value.value;
    }
  }
  /**
   * @attribute syn
   * @aspect DefiniteAssignment
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:268
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="DefiniteAssignment", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:268")
  public boolean assignedAfter(Variable v) {
    boolean assignedAfter_Variable_value = getRightOperand().assignedAfter(v);
    return assignedAfter_Variable_value;
  }
  /** @apilevel internal */
  private void unassignedAfter_Variable_reset() {
    unassignedAfter_Variable_values = null;
  }
  protected java.util.Map unassignedAfter_Variable_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="DefiniteUnassignment", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:903")
  public boolean unassignedAfter(Variable v) {
    Object _parameters = v;
    if (unassignedAfter_Variable_values == null) unassignedAfter_Variable_values = new java.util.HashMap(4);
    ASTNode$State.CircularValue _value;
    if (unassignedAfter_Variable_values.containsKey(_parameters)) {
      Object _cache = unassignedAfter_Variable_values.get(_parameters);
      if (!(_cache instanceof ASTNode$State.CircularValue)) {
        return (Boolean) _cache;
      } else {
        _value = (ASTNode$State.CircularValue) _cache;
      }
    } else {
      _value = new ASTNode$State.CircularValue();
      unassignedAfter_Variable_values.put(_parameters, _value);
      _value.value = true;
    }
    ASTNode$State state = state();
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      boolean new_unassignedAfter_Variable_value;
      do {
        _value.cycle = state.nextCycle();
        new_unassignedAfter_Variable_value = getRightOperand().unassignedAfter(v);
        if (new_unassignedAfter_Variable_value != ((Boolean)_value.value)) {
          state.setChangeInCycle();
          _value.value = new_unassignedAfter_Variable_value;
        }
      } while (state.testAndClearChangeInCycle());
      unassignedAfter_Variable_values.put(_parameters, new_unassignedAfter_Variable_value);

      state.leaveCircle();
      return new_unassignedAfter_Variable_value;
    } else if (_value.cycle != state.cycle()) {
      _value.cycle = state.cycle();
      boolean new_unassignedAfter_Variable_value = getRightOperand().unassignedAfter(v);
      if (new_unassignedAfter_Variable_value != ((Boolean)_value.value)) {
        state.setChangeInCycle();
        _value.value = new_unassignedAfter_Variable_value;
      }
      return new_unassignedAfter_Variable_value;
    } else {
      return (Boolean) _value.value;
    }
  }
  /**
   * @attribute syn
   * @aspect PreciseRethrow
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\PreciseRethrow.jrag:145
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="PreciseRethrow", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\PreciseRethrow.jrag:145")
  public boolean modifiedInScope(Variable var) {
    boolean modifiedInScope_Variable_value = getLeftOperand().modifiedInScope(var) || getRightOperand().modifiedInScope(var);
    return modifiedInScope_Variable_value;
  }
  /**
   * @attribute inh
   * @aspect DefiniteUnassignment
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:905
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="DefiniteUnassignment", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:905")
  public boolean unassignedBefore(Variable v) {
    boolean unassignedBefore_Variable_value = getParent().Define_unassignedBefore(this, null, v);
    return unassignedBefore_Variable_value;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:256
   * @apilevel internal
   */
  public boolean Define_assignedBefore(ASTNode _callerNode, ASTNode _childNode, Variable v) {
    if (getRightOperandNoTransform() != null && _callerNode == getRightOperand()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:499
      return getLeftOperand().assignedAfter(v);
    }
    else {
      return getParent().Define_assignedBefore(this, _callerNode, v);
    }
  }
  protected boolean canDefine_assignedBefore(ASTNode _callerNode, ASTNode _childNode, Variable v) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:891
   * @apilevel internal
   */
  public boolean Define_unassignedBefore(ASTNode _callerNode, ASTNode _childNode, Variable v) {
    if (getRightOperandNoTransform() != null && _callerNode == getRightOperand()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:1112
      return getLeftOperand().unassignedAfter(v);
    }
    else {
      return getParent().Define_unassignedBefore(this, _callerNode, v);
    }
  }
  protected boolean canDefine_unassignedBefore(ASTNode _callerNode, ASTNode _childNode, Variable v) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\TargetType.jrag:195
   * @apilevel internal
   */
  public boolean Define_assignmentContext(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  protected boolean canDefine_assignmentContext(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\TargetType.jrag:196
   * @apilevel internal
   */
  public boolean Define_invocationContext(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  protected boolean canDefine_invocationContext(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\TargetType.jrag:197
   * @apilevel internal
   */
  public boolean Define_castContext(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  protected boolean canDefine_castContext(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\TargetType.jrag:198
   * @apilevel internal
   */
  public boolean Define_stringContext(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  protected boolean canDefine_stringContext(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\TargetType.jrag:199
   * @apilevel internal
   */
  public boolean Define_numericContext(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  protected boolean canDefine_numericContext(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /** @apilevel internal */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
  /** @apilevel internal */
  public boolean canRewrite() {
    return false;
  }
}
