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
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\grammar\\Java.ast:205
 * @production DoStmt : {@link BranchTargetStmt} ::= <span class="component">{@link Stmt}</span> <span class="component">Condition:{@link Expr}</span>;

 */
public class DoStmt extends BranchTargetStmt implements Cloneable {
  /**
   * @aspect Java4PrettyPrint
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\PrettyPrint.jadd:107
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print("do ");
    out.print(getStmt());
    out.print(" while(");
    out.print(getCondition());
    out.print(");");
  }
  /**
   * @aspect TypeCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeCheck.jrag:385
   */
  public void typeCheck() {
    TypeDecl cond = getCondition().type();
    if (!cond.isBoolean()) {
      errorf("the type of \"%s\" is %s which is not boolean",
          getCondition().prettyPrint(), cond.name());
    }
  }
  /**
   * @declaredat ASTNode:1
   */
  public DoStmt() {
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
  public DoStmt(Stmt p0, Expr p1) {
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
    isDAafter_Variable_reset();
    isDUafter_Variable_reset();
    isDUbeforeCondition_Variable_reset();
    canCompleteNormally_reset();
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
  public DoStmt clone() throws CloneNotSupportedException {
    DoStmt node = (DoStmt) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:61
   */
  public DoStmt copy() {
    try {
      DoStmt node = (DoStmt) clone();
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
  public DoStmt fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:90
   */
  public DoStmt treeCopyNoTransform() {
    DoStmt tree = (DoStmt) copy();
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
  public DoStmt treeCopy() {
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
   * Replaces the Stmt child.
   * @param node The new node to replace the Stmt child.
   * @apilevel high-level
   */
  public void setStmt(Stmt node) {
    setChild(node, 0);
  }
  /**
   * Retrieves the Stmt child.
   * @return The current node used as the Stmt child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Stmt")
  public Stmt getStmt() {
    return (Stmt) getChild(0);
  }
  /**
   * Retrieves the Stmt child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Stmt child.
   * @apilevel low-level
   */
  public Stmt getStmtNoTransform() {
    return (Stmt) getChildNoTransform(0);
  }
  /**
   * Replaces the Condition child.
   * @param node The new node to replace the Condition child.
   * @apilevel high-level
   */
  public void setCondition(Expr node) {
    setChild(node, 1);
  }
  /**
   * Retrieves the Condition child.
   * @return The current node used as the Condition child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Condition")
  public Expr getCondition() {
    return (Expr) getChild(1);
  }
  /**
   * Retrieves the Condition child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Condition child.
   * @apilevel low-level
   */
  public Expr getConditionNoTransform() {
    return (Expr) getChildNoTransform(1);
  }
  @ASTNodeAnnotation.Attribute
  public boolean potentialTargetOf(Stmt branch) {
    boolean potentialTargetOf_Stmt_value = branch.canBranchTo(this);

    return potentialTargetOf_Stmt_value;
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
    boolean isDAafter_Variable_value = isDAafter_compute(v);
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
  private boolean isDAafter_compute(Variable v) {
      if (!getCondition().isDAafterFalse(v)) {
        return false;
      }
      for (Iterator iter = targetBreaks().iterator(); iter.hasNext(); ) {
        BreakStmt stmt = (BreakStmt) iter.next();
        if (!stmt.isDAafterReachedFinallyBlocks(v)) {
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
    boolean isDUafter_Variable_value = isDUafter_compute(v);
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
  private boolean isDUafter_compute(Variable v) {
      if (!isDUbeforeCondition(v)) // start a circular evaluation here
        return false;
      if (!getCondition().isDUafterFalse(v)) {
        return false;
      }
      for (Iterator iter = targetBreaks().iterator(); iter.hasNext(); ) {
        BreakStmt stmt = (BreakStmt) iter.next();
        if (!stmt.isDUafterReachedFinallyBlocks(v)) {
          return false;
        }
      }
      return true;
    }
  /**
   * @apilevel internal
   */
  private void isDUbeforeCondition_Variable_reset() {
    isDUbeforeCondition_Variable_values = null;
  }
  protected java.util.Map isDUbeforeCondition_Variable_values;
  @ASTNodeAnnotation.Attribute
  public boolean isDUbeforeCondition(Variable v) {
    Object _parameters = v;
    if (isDUbeforeCondition_Variable_values == null) isDUbeforeCondition_Variable_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State.CircularValue _value;
    if (isDUbeforeCondition_Variable_values.containsKey(_parameters)) {
      Object _o = isDUbeforeCondition_Variable_values.get(_parameters);
      if (!(_o instanceof ASTNode$State.CircularValue)) {
        return (Boolean) _o;
      } else {
        _value = (ASTNode$State.CircularValue) _o;
      }
    } else {
      _value = new ASTNode$State.CircularValue();
      isDUbeforeCondition_Variable_values.put(_parameters, _value);
      _value.value = true;
    }
    ASTNode$State state = state();
    boolean new_isDUbeforeCondition_Variable_value;
    if (!state.IN_CIRCLE) {
      state.IN_CIRCLE = true;
      int num = state.boundariesCrossed;
      boolean isFinal = this.is$Final();
      // TODO: fixme
      // state().CIRCLE_INDEX = 1;
      do {
        _value.visited = state.CIRCLE_INDEX;
        state.CHANGE = false;
        new_isDUbeforeCondition_Variable_value = isDUbeforeCondition_compute(v);
        if (new_isDUbeforeCondition_Variable_value != ((Boolean)_value.value)) {
          state.CHANGE = true;
          _value.value = new_isDUbeforeCondition_Variable_value;
        }
        state.CIRCLE_INDEX++;
      } while (state.CHANGE);
      if (isFinal && num == state().boundariesCrossed) {
        isDUbeforeCondition_Variable_values.put(_parameters, new_isDUbeforeCondition_Variable_value);
      } else {
        isDUbeforeCondition_Variable_values.remove(_parameters);
        state.RESET_CYCLE = true;
        boolean $tmp = isDUbeforeCondition_compute(v);
        state.RESET_CYCLE = false;
      }
      state.IN_CIRCLE = false;
      state.INTERMEDIATE_VALUE = false;
      return new_isDUbeforeCondition_Variable_value;
    }
    if (state.CIRCLE_INDEX != _value.visited) {
      _value.visited = state.CIRCLE_INDEX;
      new_isDUbeforeCondition_Variable_value = isDUbeforeCondition_compute(v);
      if (state.RESET_CYCLE) {
        isDUbeforeCondition_Variable_values.remove(_parameters);
      }
      else if (new_isDUbeforeCondition_Variable_value != ((Boolean)_value.value)) {
        state.CHANGE = true;
        _value.value = new_isDUbeforeCondition_Variable_value;
      }
      state.INTERMEDIATE_VALUE = true;
      return new_isDUbeforeCondition_Variable_value;
    }
    state.INTERMEDIATE_VALUE = true;
    return (Boolean) _value.value;
  }
  /**
   * @apilevel internal
   */
  private boolean isDUbeforeCondition_compute(Variable v) {
      if (!getStmt().isDUafter(v)) {
        return false;
      } else {
        for (Iterator iter = targetContinues().iterator(); iter.hasNext(); ) {
          ContinueStmt stmt = (ContinueStmt) iter.next();
          if (!stmt.isDUafterReachedFinallyBlocks(v)) {
            return false;
          }
        }
      }
      return true;
    }
  @ASTNodeAnnotation.Attribute
  public boolean continueLabel() {
    boolean continueLabel_value = true;

    return continueLabel_value;
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
    canCompleteNormally_value = getStmt().canCompleteNormally() && (!getCondition().isConstant() || !getCondition().isTrue())
          || reachableContinue() && (!getCondition().isConstant() || !getCondition().isTrue())
          || reachableBreak();
    if (isFinal && num == state().boundariesCrossed) {
      canCompleteNormally_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return canCompleteNormally_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean modifiedInScope(Variable var) {
    boolean modifiedInScope_Variable_value = getStmt().modifiedInScope(var);

    return modifiedInScope_Variable_value;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\BranchTarget.jrag:227
   * @apilevel internal
   */
  public Stmt Define_branchTarget(ASTNode caller, ASTNode child, Stmt branch) {
    int childIndex = this.getIndexOfChild(caller);
    return branch.canBranchTo(this) ? this : branchTarget(branch);
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:255
   * @apilevel internal
   */
  public boolean Define_isDAbefore(ASTNode caller, ASTNode child, Variable v) {
    if (caller == getConditionNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:674
      {
          if (!getStmt().isDAafter(v)) {
            return false;
          }
          for (Iterator iter = targetContinues().iterator(); iter.hasNext(); ) {
            ContinueStmt stmt = (ContinueStmt) iter.next();
            if (!stmt.isDAafterReachedFinallyBlocks(v)) {
              return false;
            }
          }
          return true;
        }
    }
    else if (caller == getStmtNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:673
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
    if (caller == getConditionNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:1251
      return isDUbeforeCondition(v);
    }
    else if (caller == getStmtNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:1250
      return isDUbefore(v) && getCondition().isDUafterTrue(v);
    }
    else {
      return getParent().Define_isDUbefore(this, caller, v);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\NameCheck.jrag:441
   * @apilevel internal
   */
  public boolean Define_insideLoop(ASTNode caller, ASTNode child) {
    if (caller == getStmtNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\NameCheck.jrag:447
      return true;
    }
    else {
      return getParent().Define_insideLoop(this, caller);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\UnreachableStatements.jrag:52
   * @apilevel internal
   */
  public boolean Define_reachable(ASTNode caller, ASTNode child) {
    if (caller == getStmtNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\UnreachableStatements.jrag:152
      return reachable();
    }
    else {
      return getParent().Define_reachable(this, caller);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\PreciseRethrow.jrag:283
   * @apilevel internal
   */
  public boolean Define_reportUnreachable(ASTNode caller, ASTNode child) {
    if (caller == getStmtNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\UnreachableStatements.jrag:214
      return reachable();
    }
    else {
      return getParent().Define_reportUnreachable(this, caller);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\EffectivelyFinal.jrag:30
   * @apilevel internal
   */
  public boolean Define_inhModifiedInScope(ASTNode caller, ASTNode child, Variable var) {
    if (caller == getStmtNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\EffectivelyFinal.jrag:55
      return false;
    }
    else {
      return getParent().Define_inhModifiedInScope(this, caller, var);
    }
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
