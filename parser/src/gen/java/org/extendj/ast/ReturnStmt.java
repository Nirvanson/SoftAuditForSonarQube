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
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\grammar\\Java.ast:210
 * @production ReturnStmt : {@link Stmt} ::= <span class="component">[Result:{@link Expr}]</span> <span class="component">[Finally:{@link Block}]</span>;

 */
public class ReturnStmt extends Stmt implements Cloneable {
  /**
   * @aspect Java4PrettyPrint
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\PrettyPrint.jadd:219
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print("return");
    if (hasResult()) {
      out.print(" ");
      out.print(getResult());
    }
    out.print(";");
  }
  /**
   * @aspect BranchTarget
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\BranchTarget.jrag:104
   */
  public void collectBranches(Collection<Stmt> c) {
    c.add(this);
  }
  /**
   * @aspect NodeConstructors
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\NodeConstructors.jrag:73
   */
  public ReturnStmt(Expr expr) {
    this(new Opt(expr));
  }
  /**
   * @declaredat ASTNode:1
   */
  public ReturnStmt() {
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
    setChild(new Opt(), 0);
    setChild(new Opt(), 1);
  }
  /**
   * @declaredat ASTNode:15
   */
  public ReturnStmt(Opt<Expr> p0) {
    setChild(p0, 0);
  }
  /**
   * @apilevel low-level
   * @declaredat ASTNode:21
   */
  protected int numChildren() {
    return 1;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:27
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:33
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    isDAafter_Variable_reset();
    isDUafterReachedFinallyBlocks_Variable_reset();
    isDAafterReachedFinallyBlocks_Variable_reset();
    isDUafter_Variable_reset();
    getFinallyOpt_reset();
    canCompleteNormally_reset();
    enclosingLambda_reset();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:46
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:52
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:58
   */
  public ReturnStmt clone() throws CloneNotSupportedException {
    ReturnStmt node = (ReturnStmt) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:65
   */
  public ReturnStmt copy() {
    try {
      ReturnStmt node = (ReturnStmt) clone();
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
   * @declaredat ASTNode:84
   */
  @Deprecated
  public ReturnStmt fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:94
   */
  public ReturnStmt treeCopyNoTransform() {
    ReturnStmt tree = (ReturnStmt) copy();
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        switch (i) {
        case 1:
          tree.children[i] = new Opt();
          continue;
        }
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
   * @declaredat ASTNode:119
   */
  public ReturnStmt treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:126
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node);    
  }
  /**
   * Replaces the optional node for the Result child. This is the <code>Opt</code>
   * node containing the child Result, not the actual child!
   * @param opt The new node to be used as the optional node for the Result child.
   * @apilevel low-level
   */
  public void setResultOpt(Opt<Expr> opt) {
    setChild(opt, 0);
  }
  /**
   * Replaces the (optional) Result child.
   * @param node The new node to be used as the Result child.
   * @apilevel high-level
   */
  public void setResult(Expr node) {
    getResultOpt().setChild(node, 0);
  }
  /**
   * Check whether the optional Result child exists.
   * @return {@code true} if the optional Result child exists, {@code false} if it does not.
   * @apilevel high-level
   */
  public boolean hasResult() {
    return getResultOpt().getNumChild() != 0;
  }
  /**
   * Retrieves the (optional) Result child.
   * @return The Result child, if it exists. Returns {@code null} otherwise.
   * @apilevel low-level
   */
  public Expr getResult() {
    return (Expr) getResultOpt().getChild(0);
  }
  /**
   * Retrieves the optional node for the Result child. This is the <code>Opt</code> node containing the child Result, not the actual child!
   * @return The optional node for child the Result child.
   * @apilevel low-level
   */
  @ASTNodeAnnotation.OptChild(name="Result")
  public Opt<Expr> getResultOpt() {
    return (Opt<Expr>) getChild(0);
  }
  /**
   * Retrieves the optional node for child Result. This is the <code>Opt</code> node containing the child Result, not the actual child!
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The optional node for child Result.
   * @apilevel low-level
   */
  public Opt<Expr> getResultOptNoTransform() {
    return (Opt<Expr>) getChildNoTransform(0);
  }
  /**
   * Replaces the (optional) Finally child.
   * @param node The new node to be used as the Finally child.
   * @apilevel high-level
   */
  public void setFinally(Block node) {
    getFinallyOpt().setChild(node, 0);
  }
  /**
   * Check whether the optional Finally child exists.
   * @return {@code true} if the optional Finally child exists, {@code false} if it does not.
   * @apilevel high-level
   */
  public boolean hasFinally() {
    return getFinallyOpt().getNumChild() != 0;
  }
  /**
   * Retrieves the (optional) Finally child.
   * @return The Finally child, if it exists. Returns {@code null} otherwise.
   * @apilevel low-level
   */
  public Block getFinally() {
    return (Block) getFinallyOpt().getChild(0);
  }
  /**
   * Retrieves the optional node for child Finally. This is the <code>Opt</code> node containing the child Finally, not the actual child!
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The optional node for child Finally.
   * @apilevel low-level
   */
  public Opt<Block> getFinallyOptNoTransform() {
    return (Opt<Block>) getChildNoTransform(1);
  }
  /**
   * Retrieves the child position of the optional child Finally.
   * @return The the child position of the optional child Finally.
   * @apilevel low-level
   */
  protected int getFinallyOptChildPosition() {
    return 1;
  }
  /**
   * @aspect TypeCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TypeCheck.jrag:196
   */
   
  public void typeCheck() {
    if (enclosingLambda() == null || enclosingLambda().hostType() != hostType()) {
      if (hasResult() && !returnType().isVoid()) {
        if (!getResult().type().assignConversionTo(returnType(), getResult())) {
          errorf("return value must be an instance of %s which %s is not",
              returnType().typeName(), getResult().type().typeName());
        }
      }
      // 8.4.5 8.8.5
      if (returnType().isVoid() && hasResult()) {
        error("return stmt may not have an expression in void methods");
      }
      // 8.4.5
      if (!returnType().isVoid() && !hasResult()) {
        error("return stmt must have an expression in non void methods");
      }
      if (enclosingBodyDecl() instanceof InstanceInitializer
          || enclosingBodyDecl() instanceof StaticInitializer) {
        error("Initializers may not return");
      }
    } else {
      if (hasResult() && !returnType().isVoid() && !(getResult() instanceof LambdaExpr)) {
        if (!getResult().type().assignConversionTo(returnType(), getResult())) {
          errorf("return value must be an instance of %s which %s is not",
              returnType().typeName(), getResult().type().typeName());
        }
      }
    }
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
    boolean isDAafter_Variable_value = true;
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
  protected java.util.Map isDUafterReachedFinallyBlocks_Variable_values;
  /**
   * @apilevel internal
   */
  private void isDUafterReachedFinallyBlocks_Variable_reset() {
    isDUafterReachedFinallyBlocks_Variable_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isDUafterReachedFinallyBlocks(Variable v) {
    Object _parameters = v;
    if (isDUafterReachedFinallyBlocks_Variable_values == null) isDUafterReachedFinallyBlocks_Variable_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (isDUafterReachedFinallyBlocks_Variable_values.containsKey(_parameters)) {
      return (Boolean) isDUafterReachedFinallyBlocks_Variable_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean isDUafterReachedFinallyBlocks_Variable_value = isDUafterReachedFinallyBlocks_compute(v);
    if (isFinal && num == state().boundariesCrossed) {
      isDUafterReachedFinallyBlocks_Variable_values.put(_parameters, isDUafterReachedFinallyBlocks_Variable_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return isDUafterReachedFinallyBlocks_Variable_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isDUafterReachedFinallyBlocks_compute(Variable v) {
      Iterator<FinallyHost> iter = finallyIterator();
      if (!isDUbefore(v) && !iter.hasNext()) {
        return false;
      }
      while (iter.hasNext()) {
        FinallyHost f = iter.next();
        if (!f.isDUafterFinally(v)) {
          return false;
        }
      }
      return true;
    }
  /**
   * @apilevel internal
   */
  protected java.util.Map isDAafterReachedFinallyBlocks_Variable_values;
  /**
   * @apilevel internal
   */
  private void isDAafterReachedFinallyBlocks_Variable_reset() {
    isDAafterReachedFinallyBlocks_Variable_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isDAafterReachedFinallyBlocks(Variable v) {
    Object _parameters = v;
    if (isDAafterReachedFinallyBlocks_Variable_values == null) isDAafterReachedFinallyBlocks_Variable_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (isDAafterReachedFinallyBlocks_Variable_values.containsKey(_parameters)) {
      return (Boolean) isDAafterReachedFinallyBlocks_Variable_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean isDAafterReachedFinallyBlocks_Variable_value = isDAafterReachedFinallyBlocks_compute(v);
    if (isFinal && num == state().boundariesCrossed) {
      isDAafterReachedFinallyBlocks_Variable_values.put(_parameters, isDAafterReachedFinallyBlocks_Variable_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return isDAafterReachedFinallyBlocks_Variable_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isDAafterReachedFinallyBlocks_compute(Variable v) {
      if (hasResult() ? getResult().isDAafter(v) : isDAbefore(v)) {
        return true;
      }
      Iterator<FinallyHost> iter = finallyIterator();
      if (!iter.hasNext()) {
        return false;
      }
      while (iter.hasNext()) {
        FinallyHost f = iter.next();
        if (!f.isDAafterFinally(v)) {
          return false;
        }
      }
      return true;
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
    boolean isDUafter_Variable_value = true;
    if (isFinal && num == state().boundariesCrossed) {
      isDUafter_Variable_values.put(_parameters, isDUafter_Variable_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return isDUafter_Variable_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean getFinallyOpt_computed = false;
  /**
   * @apilevel internal
   */
  protected Opt<Block> getFinallyOpt_value;
  /**
   * @apilevel internal
   */
  private void getFinallyOpt_reset() {
    getFinallyOpt_computed = false;
    getFinallyOpt_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public Opt<Block> getFinallyOpt() {
    ASTNode$State state = state();
    if (getFinallyOpt_computed) {
      return (Opt<Block>) getChild(getFinallyOptChildPosition());
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    getFinallyOpt_value = getFinallyOpt_compute();
    setChild(getFinallyOpt_value, getFinallyOptChildPosition());
    if (isFinal && num == state().boundariesCrossed) {
      getFinallyOpt_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    Opt<Block> node = (Opt<Block>) this.getChild(getFinallyOptChildPosition());
    return node;
  }
  /**
   * @apilevel internal
   */
  private Opt<Block> getFinallyOpt_compute() {
      return branchFinallyOpt();
    }
  /**
   * @apilevel internal
   */
  protected boolean canCompleteNormally_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean canCompleteNormally_value;
  /**
   * @apilevel internal
   */
  private void canCompleteNormally_reset() {
    canCompleteNormally_computed = false;
  }
  @ASTNodeAnnotation.Attribute
  public boolean canCompleteNormally() {
    ASTNode$State state = state();
    if (canCompleteNormally_computed) {
      return canCompleteNormally_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    canCompleteNormally_value = false;
    if (isFinal && num == state().boundariesCrossed) {
      canCompleteNormally_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return canCompleteNormally_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean modifiedInScope(Variable var) {
    boolean modifiedInScope_Variable_value = false;

    return modifiedInScope_Variable_value;
  }
  /**
   * @attribute inh
   * @aspect TypeCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeCheck.jrag:472
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl returnType() {
    TypeDecl returnType_value = getParent().Define_returnType(this, null);

    return returnType_value;
  }
  /**
   * @attribute inh
   * @aspect EnclosingLambda
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\EnclosingLambda.jrag:33
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
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:255
   * @apilevel internal
   */
  public boolean Define_isDAbefore(ASTNode caller, ASTNode child, Variable v) {
    if (caller == getResultOptNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:734
      return isDAbefore(v);
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
    if (caller == getResultOptNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:1353
      return isDUbefore(v);
    }
    else {
      return getParent().Define_isDUbefore(this, caller, v);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\GenericMethodsInference.jrag:58
   * @apilevel internal
   */
  public TypeDecl Define_assignConvertedType(ASTNode caller, ASTNode child) {
    if (caller == getResultOptNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\GenericMethodsInference.jrag:63
      return returnType();
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
    if (caller == getResultOptNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:41
      return returnType();
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
    if (caller == getResultOptNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:355
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
    if (caller == getResultOptNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:356
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
    if (caller == getResultOptNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:357
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
    if (caller == getResultOptNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:358
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
    if (caller == getResultOptNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:359
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
  protected void collect_contributors_BlockLambdaBody_lambdaReturns() {
  /**
   * @attribute coll
   * @aspect ReturnCompatible
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\LambdaBody.jrag:52
   */
    if ((enclosingLambda() != null) && (enclosingLambda().hostType() == hostType())) {
      {
        BlockLambdaBody ref = (BlockLambdaBody) ((BlockLambdaBody)enclosingLambda().getLambdaBody());
        if (ref != null) {
          ref.BlockLambdaBody_lambdaReturns_contributors().add(this);
        }
      }
    }
    super.collect_contributors_BlockLambdaBody_lambdaReturns();
  }
  protected void contributeTo_BlockLambdaBody_BlockLambdaBody_lambdaReturns(ArrayList<ReturnStmt> collection) {
    super.contributeTo_BlockLambdaBody_BlockLambdaBody_lambdaReturns(collection);
    if ((enclosingLambda() != null) && (enclosingLambda().hostType() == hostType()))
      collection.add(this);
  }

}
