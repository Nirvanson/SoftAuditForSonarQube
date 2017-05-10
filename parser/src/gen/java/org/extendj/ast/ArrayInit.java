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
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\grammar\\Java.ast:90
 * @production ArrayInit : {@link Expr} ::= <span class="component">Init:{@link Expr}*</span>;

 */
public class ArrayInit extends Expr implements Cloneable {
  /**
   * @aspect Java4PrettyPrint
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\PrettyPrint.jadd:610
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print("{ ");
    out.join(getInitList(), new PrettyPrinter.Joiner() {
      @Override
      public void printSeparator(PrettyPrinter out) {
        out.print(", ");
      }
    });
    out.print(" }");
  }
  /**
   * @aspect TypeCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeCheck.jrag:169
   */
  public void typeCheck() {
    TypeDecl initializerType = declType().componentType();
    if (initializerType.isUnknown()) {
      error("the dimension of the initializer is larger than the expected dimension");
    }
    for (int i = 0; i < getNumInit(); i++) {
      Expr e = getInit(i);
      if (!e.type().assignConversionTo(initializerType, e)) {
        errorf("the type %s of the initializer is not compatible with %s",
            e.type().name(), initializerType.name());
      }
    }
  }
  /**
   * @declaredat ASTNode:1
   */
  public ArrayInit() {
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
    children = new ASTNode[1];
    setChild(new List(), 0);
  }
  /**
   * @declaredat ASTNode:14
   */
  public ArrayInit(List<Expr> p0) {
    setChild(p0, 0);
  }
  /**
   * @apilevel low-level
   * @declaredat ASTNode:20
   */
  protected int numChildren() {
    return 1;
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
    computeDABefore_int_Variable_reset();
    computeDUbefore_int_Variable_reset();
    type_reset();
    declType_reset();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:42
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:48
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:54
   */
  public ArrayInit clone() throws CloneNotSupportedException {
    ArrayInit node = (ArrayInit) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:61
   */
  public ArrayInit copy() {
    try {
      ArrayInit node = (ArrayInit) clone();
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
   * @declaredat ASTNode:80
   */
  @Deprecated
  public ArrayInit fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:90
   */
  public ArrayInit treeCopyNoTransform() {
    ArrayInit tree = (ArrayInit) copy();
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
   * @declaredat ASTNode:110
   */
  public ArrayInit treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:117
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node);    
  }
  /**
   * Replaces the Init list.
   * @param list The new list node to be used as the Init list.
   * @apilevel high-level
   */
  public void setInitList(List<Expr> list) {
    setChild(list, 0);
  }
  /**
   * Retrieves the number of children in the Init list.
   * @return Number of children in the Init list.
   * @apilevel high-level
   */
  public int getNumInit() {
    return getInitList().getNumChild();
  }
  /**
   * Retrieves the number of children in the Init list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the Init list.
   * @apilevel low-level
   */
  public int getNumInitNoTransform() {
    return getInitListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the Init list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the Init list.
   * @apilevel high-level
   */
  public Expr getInit(int i) {
    return (Expr) getInitList().getChild(i);
  }
  /**
   * Check whether the Init list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasInit() {
    return getInitList().getNumChild() != 0;
  }
  /**
   * Append an element to the Init list.
   * @param node The element to append to the Init list.
   * @apilevel high-level
   */
  public void addInit(Expr node) {
    List<Expr> list = (parent == null) ? getInitListNoTransform() : getInitList();
    list.addChild(node);
  }
  /**
   * @apilevel low-level
   */
  public void addInitNoTransform(Expr node) {
    List<Expr> list = getInitListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the Init list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setInit(Expr node, int i) {
    List<Expr> list = getInitList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the Init list.
   * @return The node representing the Init list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="Init")
  public List<Expr> getInitList() {
    List<Expr> list = (List<Expr>) getChild(0);
    return list;
  }
  /**
   * Retrieves the Init list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the Init list.
   * @apilevel low-level
   */
  public List<Expr> getInitListNoTransform() {
    return (List<Expr>) getChildNoTransform(0);
  }
  /**
   * Retrieves the Init list.
   * @return The node representing the Init list.
   * @apilevel high-level
   */
  public List<Expr> getInits() {
    return getInitList();
  }
  /**
   * Retrieves the Init list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the Init list.
   * @apilevel low-level
   */
  public List<Expr> getInitsNoTransform() {
    return getInitListNoTransform();
  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ConstantExpression.jrag:220
   */
  @ASTNodeAnnotation.Attribute
  public boolean representableIn(TypeDecl t) {
    {
        for (int i = 0; i < getNumInit(); i++) {
          if (!getInit(i).representableIn(t)) {
            return false;
          }
        }
        return true;
      }
  }
  @ASTNodeAnnotation.Attribute
  public boolean isDAafter(Variable v) {
    boolean isDAafter_Variable_value = getNumInit() == 0 ? isDAbefore(v) : getInit(getNumInit()-1).isDAafter(v);

    return isDAafter_Variable_value;
  }
  /**
   * @apilevel internal
   */
  protected java.util.Map computeDABefore_int_Variable_values;
  /**
   * @apilevel internal
   */
  private void computeDABefore_int_Variable_reset() {
    computeDABefore_int_Variable_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean computeDABefore(int childIndex, Variable v) {
    java.util.List _parameters = new java.util.ArrayList(2);
    _parameters.add(childIndex);
    _parameters.add(v);
    if (computeDABefore_int_Variable_values == null) computeDABefore_int_Variable_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (computeDABefore_int_Variable_values.containsKey(_parameters)) {
      return (Boolean) computeDABefore_int_Variable_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean computeDABefore_int_Variable_value = computeDABefore_compute(childIndex, v);
    if (isFinal && num == state().boundariesCrossed) {
      computeDABefore_int_Variable_values.put(_parameters, computeDABefore_int_Variable_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return computeDABefore_int_Variable_value;
  }
  /**
   * @apilevel internal
   */
  private boolean computeDABefore_compute(int childIndex, Variable v) {
      if (childIndex == 0) {
        return isDAbefore(v);
      }
      int index = childIndex-1;
      while (index > 0 && getInit(index).isConstant()) {
        index--;
      }
      return getInit(childIndex-1).isDAafter(v);
    }
  @ASTNodeAnnotation.Attribute
  public boolean isDUafter(Variable v) {
    boolean isDUafter_Variable_value = getNumInit() == 0 ? isDUbefore(v) : getInit(getNumInit()-1).isDUafter(v);

    return isDUafter_Variable_value;
  }
  /**
   * @apilevel internal
   */
  protected java.util.Map computeDUbefore_int_Variable_values;
  /**
   * @apilevel internal
   */
  private void computeDUbefore_int_Variable_reset() {
    computeDUbefore_int_Variable_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean computeDUbefore(int childIndex, Variable v) {
    java.util.List _parameters = new java.util.ArrayList(2);
    _parameters.add(childIndex);
    _parameters.add(v);
    if (computeDUbefore_int_Variable_values == null) computeDUbefore_int_Variable_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (computeDUbefore_int_Variable_values.containsKey(_parameters)) {
      return (Boolean) computeDUbefore_int_Variable_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean computeDUbefore_int_Variable_value = computeDUbefore_compute(childIndex, v);
    if (isFinal && num == state().boundariesCrossed) {
      computeDUbefore_int_Variable_values.put(_parameters, computeDUbefore_int_Variable_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return computeDUbefore_int_Variable_value;
  }
  /**
   * @apilevel internal
   */
  private boolean computeDUbefore_compute(int childIndex, Variable v) {
      if (childIndex == 0) {
        return isDUbefore(v);
      }
      int index = childIndex-1;
      while (index > 0 && getInit(index).isConstant()) {
        index--;
      }
      return getInit(childIndex-1).isDUafter(v);
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
    type_value = declType();
    if (isFinal && num == state().boundariesCrossed) {
      type_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return type_value;
  }
  /**
   * @attribute syn
   * @aspect PreciseRethrow
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\PreciseRethrow.jrag:149
   */
  @ASTNodeAnnotation.Attribute
  public boolean modifiedInScope(Variable var) {
    {
        for (int i = 0; i < getNumInit(); ++i) {
          if (getInit(i).modifiedInScope(var)) {
            return true;
          }
        }
        return false;
      }
  }
  /**
   * @attribute inh
   * @aspect TypeAnalysis
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeAnalysis.jrag:281
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl declType() {
    ASTNode$State state = state();
    if (declType_computed) {
      return declType_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    declType_value = getParent().Define_declType(this, null);
    if (isFinal && num == state().boundariesCrossed) {
      declType_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return declType_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean declType_computed = false;
  /**
   * @apilevel internal
   */
  protected TypeDecl declType_value;
  /**
   * @apilevel internal
   */
  private void declType_reset() {
    declType_computed = false;
    declType_value = null;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:47
   * @apilevel internal
   */
  public boolean Define_isSource(ASTNode caller, ASTNode child) {
    if (caller == getInitListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:64
      int childIndex = caller.getIndexOfChild(child);
      return true;
    }
    else {
      return getParent().Define_isSource(this, caller);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:255
   * @apilevel internal
   */
  public boolean Define_isDAbefore(ASTNode caller, ASTNode child, Variable v) {
    if (caller == getInitListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:560
      int childIndex = caller.getIndexOfChild(child);
      return computeDABefore(childIndex, v);
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
    if (caller == getInitListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:1005
      int childIndex = caller.getIndexOfChild(child);
      return computeDUbefore(childIndex, v);
    }
    else {
      return getParent().Define_isDUbefore(this, caller, v);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Annotations.jrag:601
   * @apilevel internal
   */
  public TypeDecl Define_declType(ASTNode caller, ASTNode child) {
    if (caller == getInitListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeAnalysis.jrag:289
      int childIndex = caller.getIndexOfChild(child);
      return declType().componentType();
    }
    else {
      return getParent().Define_declType(this, caller);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\GenericMethodsInference.jrag:58
   * @apilevel internal
   */
  public TypeDecl Define_assignConvertedType(ASTNode caller, ASTNode child) {
    if (caller == getInitListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\GenericMethodsInference.jrag:62
      int childIndex = caller.getIndexOfChild(child);
      return declType().componentType();
    }
    else {
      return getParent().Define_assignConvertedType(this, caller);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:30
   * @apilevel internal
   */
  public TypeDecl Define_targetType(ASTNode caller, ASTNode child) {
    if (caller == getInitListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:79
      int childIndex = caller.getIndexOfChild(child);
      {
          if (!(targetType() instanceof ArrayDecl)) {
            return targetType();
          } else {
            return ((ArrayDecl) targetType()).componentType();
          }
        }
    }
    else {
      return getParent().Define_targetType(this, caller);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:196
   * @apilevel internal
   */
  public boolean Define_assignmentContext(ASTNode caller, ASTNode child) {
    if (caller == getInitListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:343
      int childIndex = caller.getIndexOfChild(child);
      return true;
    }
    else {
      return getParent().Define_assignmentContext(this, caller);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:197
   * @apilevel internal
   */
  public boolean Define_invocationContext(ASTNode caller, ASTNode child) {
    if (caller == getInitListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:344
      int childIndex = caller.getIndexOfChild(child);
      return false;
    }
    else {
      return getParent().Define_invocationContext(this, caller);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:198
   * @apilevel internal
   */
  public boolean Define_castContext(ASTNode caller, ASTNode child) {
    if (caller == getInitListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:345
      int childIndex = caller.getIndexOfChild(child);
      return false;
    }
    else {
      return getParent().Define_castContext(this, caller);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:199
   * @apilevel internal
   */
  public boolean Define_stringContext(ASTNode caller, ASTNode child) {
    if (caller == getInitListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:346
      int childIndex = caller.getIndexOfChild(child);
      return false;
    }
    else {
      return getParent().Define_stringContext(this, caller);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:200
   * @apilevel internal
   */
  public boolean Define_numericContext(ASTNode caller, ASTNode child) {
    if (caller == getInitListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:347
      int childIndex = caller.getIndexOfChild(child);
      return false;
    }
    else {
      return getParent().Define_numericContext(this, caller);
    }
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
