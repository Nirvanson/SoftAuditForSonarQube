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
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\grammar\\Lambda.ast:11
 * @production LambdaBody : {@link ASTNode};

 */
public abstract class LambdaBody extends ASTNode<ASTNode> implements Cloneable {
  /**
   * @declaredat ASTNode:1
   */
  public LambdaBody() {
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
   * @apilevel low-level
   * @declaredat ASTNode:15
   */
  protected int numChildren() {
    return 0;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:21
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:27
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    enclosingLambda_reset();
    lookupVariable_String_reset();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:35
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:41
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:47
   */
  public LambdaBody clone() throws CloneNotSupportedException {
    LambdaBody node = (LambdaBody) super.clone();
    return node;
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @deprecated Please use treeCopy or treeCopyNoTransform instead
   * @declaredat ASTNode:58
   */
  @Deprecated
  public abstract LambdaBody fullCopy();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:66
   */
  public abstract LambdaBody treeCopyNoTransform();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The subtree of this node is traversed to trigger rewrites before copy.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:74
   */
  public abstract LambdaBody treeCopy();
  /**
   * @attribute syn
   * @aspect PreciseRethrow
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\EffectivelyFinal.jrag:43
   */
  @ASTNodeAnnotation.Attribute
  public abstract boolean modifiedInScope(Variable var);
  /**
   * @attribute syn
   * @aspect LambdaBody
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\LambdaBody.jrag:29
   */
  @ASTNodeAnnotation.Attribute
  public abstract boolean isBlockBody();
  /**
   * @attribute syn
   * @aspect LambdaBody
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\LambdaBody.jrag:30
   */
  @ASTNodeAnnotation.Attribute
  public abstract boolean isExprBody();
  /**
   * @attribute syn
   * @aspect LambdaExpr
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\LambdaExpr.jrag:71
   */
  @ASTNodeAnnotation.Attribute
  public abstract boolean congruentTo(FunctionDescriptor f);
  /**
   * @attribute inh
   * @aspect EnclosingLambda
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\EnclosingLambda.jrag:29
   */
  @ASTNodeAnnotation.Attribute
  public LambdaExpr enclosingLambda() {
    ASTNode$State state = state();
    if (enclosingLambda_computed) {
      return enclosingLambda_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    enclosingLambda_value = getParent().Define_enclosingLambda(this, null);
    if (isFinal && num == state().boundariesCrossed) {
      enclosingLambda_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return enclosingLambda_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean enclosingLambda_computed = false;
  /**
   * @apilevel internal
   */
  protected LambdaExpr enclosingLambda_value;
  /**
   * @apilevel internal
   */
  private void enclosingLambda_reset() {
    enclosingLambda_computed = false;
    enclosingLambda_value = null;
  }
  /**
   * @attribute inh
   * @aspect VariableScope
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\LookupVariable.jrag:30
   */
  @ASTNodeAnnotation.Attribute
  public SimpleSet lookupVariable(String name) {
    Object _parameters = name;
    if (lookupVariable_String_values == null) lookupVariable_String_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (lookupVariable_String_values.containsKey(_parameters)) {
      return (SimpleSet) lookupVariable_String_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    SimpleSet lookupVariable_String_value = getParent().Define_lookupVariable(this, null, name);
    if (isFinal && num == state().boundariesCrossed) {
      lookupVariable_String_values.put(_parameters, lookupVariable_String_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return lookupVariable_String_value;
  }
  /**
   * @apilevel internal
   */
  protected java.util.Map lookupVariable_String_values;
  /**
   * @apilevel internal
   */
  private void lookupVariable_String_reset() {
    lookupVariable_String_values = null;
  }
  /**
   * @attribute inh
   * @aspect TypeCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TypeCheck.jrag:31
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl unknownType() {
    TypeDecl unknownType_value = getParent().Define_unknownType(this, null);

    return unknownType_value;
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
