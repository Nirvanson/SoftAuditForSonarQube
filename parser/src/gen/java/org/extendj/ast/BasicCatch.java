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
 * A catch clause that can catch a single exception type.
 * @ast node
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\grammar\\MultiCatch.ast:9
 * @production BasicCatch : {@link CatchClause} ::= <span class="component">Parameter:{@link ParameterDeclaration}</span> <span class="component">{@link Block}</span>;

 */
public class BasicCatch extends CatchClause implements Cloneable {
  /**
   * @aspect Java4PrettyPrint
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\PrettyPrint.jadd:35
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print("catch (");
    out.print(getParameter());
    out.print(") ");
    out.print(getBlock());
  }
  /**
   * @aspect TypeCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeCheck.jrag:431
   */
  public void typeCheck() {
    if (!getParameter().type().instanceOf(typeThrowable())) {
      error("*** The catch variable must extend Throwable");
    }
  }
  /**
   * @aspect PreciseRethrow
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\PreciseRethrow.jrag:286
   */
  void checkUnreachableStmt() {
    if (!getBlock().reachable() && reportUnreachable()) {
      errorf("the exception %s is not thrown in the body of the try statement",
          getParameter().type().fullName());
    }
  }
  /**
   * @declaredat ASTNode:1
   */
  public BasicCatch() {
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
  public BasicCatch(ParameterDeclaration p0, Block p1) {
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
    parameterDeclaration_String_reset();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:39
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:45
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:51
   */
  public BasicCatch clone() throws CloneNotSupportedException {
    BasicCatch node = (BasicCatch) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:58
   */
  public BasicCatch copy() {
    try {
      BasicCatch node = (BasicCatch) clone();
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
   * @declaredat ASTNode:77
   */
  @Deprecated
  public BasicCatch fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:87
   */
  public BasicCatch treeCopyNoTransform() {
    BasicCatch tree = (BasicCatch) copy();
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
   * @declaredat ASTNode:107
   */
  public BasicCatch treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:114
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node);    
  }
  /**
   * Replaces the Parameter child.
   * @param node The new node to replace the Parameter child.
   * @apilevel high-level
   */
  public void setParameter(ParameterDeclaration node) {
    setChild(node, 0);
  }
  /**
   * Retrieves the Parameter child.
   * @return The current node used as the Parameter child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Parameter")
  public ParameterDeclaration getParameter() {
    return (ParameterDeclaration) getChild(0);
  }
  /**
   * Retrieves the Parameter child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Parameter child.
   * @apilevel low-level
   */
  public ParameterDeclaration getParameterNoTransform() {
    return (ParameterDeclaration) getChildNoTransform(0);
  }
  /**
   * Replaces the Block child.
   * @param node The new node to replace the Block child.
   * @apilevel high-level
   */
  public void setBlock(Block node) {
    setChild(node, 1);
  }
  /**
   * Retrieves the Block child.
   * @return The current node used as the Block child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Block")
  public Block getBlock() {
    return (Block) getChild(1);
  }
  /**
   * Retrieves the Block child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Block child.
   * @apilevel low-level
   */
  public Block getBlockNoTransform() {
    return (Block) getChildNoTransform(1);
  }
  @ASTNodeAnnotation.Attribute
  public boolean handles(TypeDecl exceptionType) {
    boolean handles_TypeDecl_value = !getParameter().type().isUnknown()
          && exceptionType.instanceOf(getParameter().type());

    return handles_TypeDecl_value;
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
    SimpleSet parameterDeclaration_String_value = getParameter().name().equals(name)
          ? getParameter()
          : SimpleSet.emptySet;
    if (isFinal && num == state().boundariesCrossed) {
      parameterDeclaration_String_values.put(_parameters, parameterDeclaration_String_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return parameterDeclaration_String_value;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\LookupVariable.jrag:30
   * @apilevel internal
   */
  public SimpleSet Define_lookupVariable(ASTNode caller, ASTNode child, String name) {
    if (caller == getParameterNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupVariable.jrag:143
      return parameterDeclaration(name);
    }
    else {
      return super.Define_lookupVariable(caller, child, name);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\NameCheck.jrag:30
   * @apilevel internal
   */
  public VariableScope Define_outerScope(ASTNode caller, ASTNode child) {
    if (caller == getParameterNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\NameCheck.jrag:366
      return this;
    }
    else {
      return getParent().Define_outerScope(this, caller);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\SyntacticClassification.jrag:36
   * @apilevel internal
   */
  public NameType Define_nameType(ASTNode caller, ASTNode child) {
    if (caller == getParameterNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\SyntacticClassification.jrag:113
      return NameType.TYPE_NAME;
    }
    else {
      return getParent().Define_nameType(this, caller);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\UnreachableStatements.jrag:52
   * @apilevel internal
   */
  public boolean Define_reachable(ASTNode caller, ASTNode child) {
    if (caller == getBlockNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\UnreachableStatements.jrag:180
      return reachableCatchClause(getParameter().type());
    }
    else {
      return getParent().Define_reachable(this, caller);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\MultiCatch.jrag:44
   * @apilevel internal
   */
  public boolean Define_isMethodParameter(ASTNode caller, ASTNode child) {
    if (caller == getParameterNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\VariableDeclaration.jrag:85
      return false;
    }
    else {
      return getParent().Define_isMethodParameter(this, caller);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\MultiCatch.jrag:45
   * @apilevel internal
   */
  public boolean Define_isConstructorParameter(ASTNode caller, ASTNode child) {
    if (caller == getParameterNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\VariableDeclaration.jrag:86
      return false;
    }
    else {
      return getParent().Define_isConstructorParameter(this, caller);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\MultiCatch.jrag:46
   * @apilevel internal
   */
  public boolean Define_isExceptionHandlerParameter(ASTNode caller, ASTNode child) {
    if (caller == getParameterNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\VariableDeclaration.jrag:87
      return true;
    }
    else {
      return getParent().Define_isExceptionHandlerParameter(this, caller);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\VariableArityParameters.jrag:48
   * @apilevel internal
   */
  public boolean Define_variableArityValid(ASTNode caller, ASTNode child) {
    if (caller == getParameterNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\VariableArityParameters.jrag:45
      return false;
    }
    else {
      return getParent().Define_variableArityValid(this, caller);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\EffectivelyFinal.jrag:30
   * @apilevel internal
   */
  public boolean Define_inhModifiedInScope(ASTNode caller, ASTNode child, Variable var) {
    if (caller == getParameterNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\PreciseRethrow.jrag:81
      return getBlock().modifiedInScope(var);
    }
    else {
      return getParent().Define_inhModifiedInScope(this, caller, var);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\PreciseRethrow.jrag:206
   * @apilevel internal
   */
  public boolean Define_isCatchParam(ASTNode caller, ASTNode child) {
    if (caller == getParameterNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\PreciseRethrow.jrag:210
      return true;
    }
    else {
      return getParent().Define_isCatchParam(this, caller);
    }
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
