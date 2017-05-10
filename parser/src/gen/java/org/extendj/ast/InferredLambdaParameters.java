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
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\grammar\\Lambda.ast:8
 * @production InferredLambdaParameters : {@link LambdaParameters} ::= <span class="component">Parameter:{@link InferredParameterDeclaration}*</span>;

 */
public class InferredLambdaParameters extends LambdaParameters implements Cloneable {
  /**
   * @aspect Java8PrettyPrint
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\PrettyPrint.jadd:54
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print("(");
    out.join(getParameters(), new PrettyPrinter.Joiner() {
      @Override
      public void printSeparator(PrettyPrinter out) {
        out.print(", ");
      }
    });
    out.print(")");
  }
  /**
   * @aspect Java8NameCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\NameCheck.jrag:559
   */
  public void nameCheck() {
    for (int i = 0; i < getNumParameter(); i++) {
      if (getParameter(i).name().equals("_")) {
        // 15.27.1
        error("Underscore is not a valid identifier for a lambda parameter");
        return;
      }
    }
  }
  /**
   * @declaredat ASTNode:1
   */
  public InferredLambdaParameters() {
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
  public InferredLambdaParameters(List<InferredParameterDeclaration> p0) {
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
    numParameters_reset();
    congruentTo_FunctionDescriptor_reset();
    parameterDeclaration_String_reset();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:41
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:47
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:53
   */
  public InferredLambdaParameters clone() throws CloneNotSupportedException {
    InferredLambdaParameters node = (InferredLambdaParameters) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:60
   */
  public InferredLambdaParameters copy() {
    try {
      InferredLambdaParameters node = (InferredLambdaParameters) clone();
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
   * @declaredat ASTNode:79
   */
  @Deprecated
  public InferredLambdaParameters fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:89
   */
  public InferredLambdaParameters treeCopyNoTransform() {
    InferredLambdaParameters tree = (InferredLambdaParameters) copy();
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
   * @declaredat ASTNode:109
   */
  public InferredLambdaParameters treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:116
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node);    
  }
  /**
   * Replaces the Parameter list.
   * @param list The new list node to be used as the Parameter list.
   * @apilevel high-level
   */
  public void setParameterList(List<InferredParameterDeclaration> list) {
    setChild(list, 0);
  }
  /**
   * Retrieves the number of children in the Parameter list.
   * @return Number of children in the Parameter list.
   * @apilevel high-level
   */
  public int getNumParameter() {
    return getParameterList().getNumChild();
  }
  /**
   * Retrieves the number of children in the Parameter list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the Parameter list.
   * @apilevel low-level
   */
  public int getNumParameterNoTransform() {
    return getParameterListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the Parameter list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the Parameter list.
   * @apilevel high-level
   */
  public InferredParameterDeclaration getParameter(int i) {
    return (InferredParameterDeclaration) getParameterList().getChild(i);
  }
  /**
   * Check whether the Parameter list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasParameter() {
    return getParameterList().getNumChild() != 0;
  }
  /**
   * Append an element to the Parameter list.
   * @param node The element to append to the Parameter list.
   * @apilevel high-level
   */
  public void addParameter(InferredParameterDeclaration node) {
    List<InferredParameterDeclaration> list = (parent == null) ? getParameterListNoTransform() : getParameterList();
    list.addChild(node);
  }
  /**
   * @apilevel low-level
   */
  public void addParameterNoTransform(InferredParameterDeclaration node) {
    List<InferredParameterDeclaration> list = getParameterListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the Parameter list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setParameter(InferredParameterDeclaration node, int i) {
    List<InferredParameterDeclaration> list = getParameterList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the Parameter list.
   * @return The node representing the Parameter list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="Parameter")
  public List<InferredParameterDeclaration> getParameterList() {
    List<InferredParameterDeclaration> list = (List<InferredParameterDeclaration>) getChild(0);
    return list;
  }
  /**
   * Retrieves the Parameter list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the Parameter list.
   * @apilevel low-level
   */
  public List<InferredParameterDeclaration> getParameterListNoTransform() {
    return (List<InferredParameterDeclaration>) getChildNoTransform(0);
  }
  /**
   * Retrieves the Parameter list.
   * @return The node representing the Parameter list.
   * @apilevel high-level
   */
  public List<InferredParameterDeclaration> getParameters() {
    return getParameterList();
  }
  /**
   * Retrieves the Parameter list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the Parameter list.
   * @apilevel low-level
   */
  public List<InferredParameterDeclaration> getParametersNoTransform() {
    return getParameterListNoTransform();
  }
  /**
   * @apilevel internal
   */
  protected boolean numParameters_computed = false;
  /**
   * @apilevel internal
   */
  protected int numParameters_value;
  /**
   * @apilevel internal
   */
  private void numParameters_reset() {
    numParameters_computed = false;
  }
  @ASTNodeAnnotation.Attribute
  public int numParameters() {
    ASTNode$State state = state();
    if (numParameters_computed) {
      return numParameters_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    numParameters_value = getNumParameter();
    if (isFinal && num == state().boundariesCrossed) {
      numParameters_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return numParameters_value;
  }
  /**
   * @apilevel internal
   */
  protected java.util.Map congruentTo_FunctionDescriptor_values;
  /**
   * @apilevel internal
   */
  private void congruentTo_FunctionDescriptor_reset() {
    congruentTo_FunctionDescriptor_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean congruentTo(FunctionDescriptor f) {
    Object _parameters = f;
    if (congruentTo_FunctionDescriptor_values == null) congruentTo_FunctionDescriptor_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (congruentTo_FunctionDescriptor_values.containsKey(_parameters)) {
      return (Boolean) congruentTo_FunctionDescriptor_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean congruentTo_FunctionDescriptor_value = numParameters() == f.method.getNumParameter();
    if (isFinal && num == state().boundariesCrossed) {
      congruentTo_FunctionDescriptor_values.put(_parameters, congruentTo_FunctionDescriptor_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return congruentTo_FunctionDescriptor_value;
  }
  /**
   * @apilevel internal
   */
  protected java.util.Map parameterDeclaration_String_values;
  /**
   * @apilevel internal
   */
  private void parameterDeclaration_String_reset() {
    parameterDeclaration_String_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public SimpleSet parameterDeclaration(String name) {
    Object _parameters = name;
    if (parameterDeclaration_String_values == null) parameterDeclaration_String_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (parameterDeclaration_String_values.containsKey(_parameters)) {
      return (SimpleSet) parameterDeclaration_String_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    SimpleSet parameterDeclaration_String_value = parameterDeclaration_compute(name);
    if (isFinal && num == state().boundariesCrossed) {
      parameterDeclaration_String_values.put(_parameters, parameterDeclaration_String_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return parameterDeclaration_String_value;
  }
  /**
   * @apilevel internal
   */
  private SimpleSet parameterDeclaration_compute(String name) {
      for (int i = 0; i < getNumParameter(); i++) {
        if (getParameter(i).name().equals(name)) {
          return (InferredParameterDeclaration) getParameter(i);
        }
      }
      return SimpleSet.emptySet;
    }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\EffectivelyFinal.jrag:30
   * @apilevel internal
   */
  public boolean Define_inhModifiedInScope(ASTNode caller, ASTNode child, Variable var) {
    if (caller == getParameterListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\EffectivelyFinal.jrag:37
      int childIndex = caller.getIndexOfChild(child);
      return inhModifiedInScope(var);
    }
    else {
      return getParent().Define_inhModifiedInScope(this, caller, var);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\LookupVariable.jrag:30
   * @apilevel internal
   */
  public SimpleSet Define_lookupVariable(ASTNode caller, ASTNode child, String name) {
    if (caller == getParameterListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\LookupVariable.jrag:35
      int childIndex = caller.getIndexOfChild(child);
      return parameterDeclaration(name);
    }
    else {
      return getParent().Define_lookupVariable(this, caller, name);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TypeCheck.jrag:453
   * @apilevel internal
   */
  public TypeDecl Define_inferredType(ASTNode caller, ASTNode child) {
    if (caller == getParameterListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TypeCheck.jrag:454
      int i = caller.getIndexOfChild(child);
      {
          if (enclosingLambda().targetInterface() == null) {
            return unknownType();
          }
          InterfaceDecl iDecl = (InterfaceDecl)enclosingLambda().targetInterface();
          if (!iDecl.isFunctional()) {
            return unknownType();
          } else if (iDecl.functionDescriptor().method.getNumParameter() < i + 1) {
            return unknownType();
          } else {
            return iDecl.functionDescriptor().method.getParameter(i).type();
          }
        }
    }
    else {
      return getParent().Define_inferredType(this, caller);
    }
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
