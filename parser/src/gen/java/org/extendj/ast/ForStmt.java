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
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\grammar\\Java.ast:206
 * @production ForStmt : {@link BranchTargetStmt} ::= <span class="component">InitStmt:{@link Stmt}*</span> <span class="component">[Condition:{@link Expr}]</span> <span class="component">UpdateStmt:{@link Stmt}*</span> <span class="component">{@link Stmt}</span>;

 */
public class ForStmt extends BranchTargetStmt implements Cloneable, VariableScope {
  /**
   * Manually implemented because it is too complex for a template.
   * @aspect PrettyPrintUtil
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\PrettyPrintUtil.jrag:91
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print("for (");
    if (getNumInitStmt() > 0) {
      if (getInitStmt(0) instanceof VarDeclStmt) {
        VarDeclStmt var = (VarDeclStmt) getInitStmt(0);
        int minDimension = Integer.MAX_VALUE;
        for (VariableDeclaration decl : var.getSingleDeclList()) {
          minDimension = Math.min(minDimension, decl.type().dimension());
        }

        // Print type.
        out.print(var.getModifiers());
        out.print(var.type().elementType().typeName());
        for (int i = minDimension; i > 0; i--) {
          out.print("[]");
        }

        // Print individual declarations.
        for (int i = 0; i < var.getNumSingleDecl(); ++i) {
          if (i != 0) {
            out.print(",");
          }
          VariableDeclaration decl = var.getSingleDecl(i);
          out.print(" " + decl.name());
          for (int j = decl.type().dimension() - minDimension; j > 0; j -= 1) {
            out.print("[]");
          }
          if (decl.hasInit()) {
            out.print(" = ");
            out.print(decl.getInit());
          }
        }
      } else if (getInitStmt(0) instanceof ExprStmt) {
        ExprStmt stmt = (ExprStmt) getInitStmt(0);
        out.print(stmt.getExpr());
        for (int i = 1; i < getNumInitStmt(); i++) {
          out.print(", ");
          stmt = (ExprStmt)getInitStmt(i);
          out.print(stmt.getExpr());
        }
      } else {
        throw new Error("Unexpected initializer in for loop: " + getInitStmt(0));
      }
    }

    out.print("; ");
    if (hasCondition()) {
      out.print(getCondition());
    }
    out.print("; ");

    // Print update statements.
    for (int i = 0; i < getNumUpdateStmt(); i++) {
      if (i > 0) {
        out.print(", ");
      }
      ExprStmt update = (ExprStmt) getUpdateStmt(i);
      out.print(update.getExpr());
    }

    out.print(") ");
    if (getStmt() instanceof Block) {
      out.print(getStmt());
    } else {
      out.print("{");
      out.println();
      out.indent(1);
      out.print(getStmt());
      out.println();
      out.print("}");
    }
  }
  /**
   * @aspect TypeCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeCheck.jrag:392
   */
  public void typeCheck() {
    if (hasCondition()) {
      TypeDecl cond = getCondition().type();
      if (!cond.isBoolean()) {
        errorf("the type of \"%s\" is %s which is not boolean",
            getCondition().prettyPrint(), cond.name());
      }
    }
  }
  /**
   * @declaredat ASTNode:1
   */
  public ForStmt() {
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
    children = new ASTNode[4];
    setChild(new List(), 0);
    setChild(new Opt(), 1);
    setChild(new List(), 2);
  }
  /**
   * @declaredat ASTNode:16
   */
  public ForStmt(List<Stmt> p0, Opt<Expr> p1, List<Stmt> p2, Stmt p3) {
    setChild(p0, 0);
    setChild(p1, 1);
    setChild(p2, 2);
    setChild(p3, 3);
  }
  /**
   * @apilevel low-level
   * @declaredat ASTNode:25
   */
  protected int numChildren() {
    return 4;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:31
   */
  public boolean mayHaveRewrite() {
    return true;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:37
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    isDAafter_Variable_reset();
    isDUafter_Variable_reset();
    isDUbeforeCondition_Variable_reset();
    localLookup_String_reset();
    localVariableDeclaration_String_reset();
    canCompleteNormally_reset();
    lookupVariable_String_reset();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:50
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:56
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:62
   */
  public ForStmt clone() throws CloneNotSupportedException {
    ForStmt node = (ForStmt) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:69
   */
  public ForStmt copy() {
    try {
      ForStmt node = (ForStmt) clone();
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
   * @declaredat ASTNode:88
   */
  @Deprecated
  public ForStmt fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:98
   */
  public ForStmt treeCopyNoTransform() {
    ForStmt tree = (ForStmt) copy();
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
   * @declaredat ASTNode:118
   */
  public ForStmt treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:125
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node);    
  }
  /**
   * Replaces the InitStmt list.
   * @param list The new list node to be used as the InitStmt list.
   * @apilevel high-level
   */
  public void setInitStmtList(List<Stmt> list) {
    setChild(list, 0);
  }
  /**
   * Retrieves the number of children in the InitStmt list.
   * @return Number of children in the InitStmt list.
   * @apilevel high-level
   */
  public int getNumInitStmt() {
    return getInitStmtList().getNumChild();
  }
  /**
   * Retrieves the number of children in the InitStmt list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the InitStmt list.
   * @apilevel low-level
   */
  public int getNumInitStmtNoTransform() {
    return getInitStmtListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the InitStmt list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the InitStmt list.
   * @apilevel high-level
   */
  public Stmt getInitStmt(int i) {
    return (Stmt) getInitStmtList().getChild(i);
  }
  /**
   * Check whether the InitStmt list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasInitStmt() {
    return getInitStmtList().getNumChild() != 0;
  }
  /**
   * Append an element to the InitStmt list.
   * @param node The element to append to the InitStmt list.
   * @apilevel high-level
   */
  public void addInitStmt(Stmt node) {
    List<Stmt> list = (parent == null) ? getInitStmtListNoTransform() : getInitStmtList();
    list.addChild(node);
  }
  /**
   * @apilevel low-level
   */
  public void addInitStmtNoTransform(Stmt node) {
    List<Stmt> list = getInitStmtListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the InitStmt list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setInitStmt(Stmt node, int i) {
    List<Stmt> list = getInitStmtList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the InitStmt list.
   * @return The node representing the InitStmt list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="InitStmt")
  public List<Stmt> getInitStmtList() {
    List<Stmt> list = (List<Stmt>) getChild(0);
    return list;
  }
  /**
   * Retrieves the InitStmt list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the InitStmt list.
   * @apilevel low-level
   */
  public List<Stmt> getInitStmtListNoTransform() {
    return (List<Stmt>) getChildNoTransform(0);
  }
  /**
   * Retrieves the InitStmt list.
   * @return The node representing the InitStmt list.
   * @apilevel high-level
   */
  public List<Stmt> getInitStmts() {
    return getInitStmtList();
  }
  /**
   * Retrieves the InitStmt list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the InitStmt list.
   * @apilevel low-level
   */
  public List<Stmt> getInitStmtsNoTransform() {
    return getInitStmtListNoTransform();
  }
  /**
   * Replaces the optional node for the Condition child. This is the <code>Opt</code>
   * node containing the child Condition, not the actual child!
   * @param opt The new node to be used as the optional node for the Condition child.
   * @apilevel low-level
   */
  public void setConditionOpt(Opt<Expr> opt) {
    setChild(opt, 1);
  }
  /**
   * Replaces the (optional) Condition child.
   * @param node The new node to be used as the Condition child.
   * @apilevel high-level
   */
  public void setCondition(Expr node) {
    getConditionOpt().setChild(node, 0);
  }
  /**
   * Check whether the optional Condition child exists.
   * @return {@code true} if the optional Condition child exists, {@code false} if it does not.
   * @apilevel high-level
   */
  public boolean hasCondition() {
    return getConditionOpt().getNumChild() != 0;
  }
  /**
   * Retrieves the (optional) Condition child.
   * @return The Condition child, if it exists. Returns {@code null} otherwise.
   * @apilevel low-level
   */
  public Expr getCondition() {
    return (Expr) getConditionOpt().getChild(0);
  }
  /**
   * Retrieves the optional node for the Condition child. This is the <code>Opt</code> node containing the child Condition, not the actual child!
   * @return The optional node for child the Condition child.
   * @apilevel low-level
   */
  @ASTNodeAnnotation.OptChild(name="Condition")
  public Opt<Expr> getConditionOpt() {
    return (Opt<Expr>) getChild(1);
  }
  /**
   * Retrieves the optional node for child Condition. This is the <code>Opt</code> node containing the child Condition, not the actual child!
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The optional node for child Condition.
   * @apilevel low-level
   */
  public Opt<Expr> getConditionOptNoTransform() {
    return (Opt<Expr>) getChildNoTransform(1);
  }
  /**
   * Replaces the UpdateStmt list.
   * @param list The new list node to be used as the UpdateStmt list.
   * @apilevel high-level
   */
  public void setUpdateStmtList(List<Stmt> list) {
    setChild(list, 2);
  }
  /**
   * Retrieves the number of children in the UpdateStmt list.
   * @return Number of children in the UpdateStmt list.
   * @apilevel high-level
   */
  public int getNumUpdateStmt() {
    return getUpdateStmtList().getNumChild();
  }
  /**
   * Retrieves the number of children in the UpdateStmt list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the UpdateStmt list.
   * @apilevel low-level
   */
  public int getNumUpdateStmtNoTransform() {
    return getUpdateStmtListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the UpdateStmt list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the UpdateStmt list.
   * @apilevel high-level
   */
  public Stmt getUpdateStmt(int i) {
    return (Stmt) getUpdateStmtList().getChild(i);
  }
  /**
   * Check whether the UpdateStmt list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasUpdateStmt() {
    return getUpdateStmtList().getNumChild() != 0;
  }
  /**
   * Append an element to the UpdateStmt list.
   * @param node The element to append to the UpdateStmt list.
   * @apilevel high-level
   */
  public void addUpdateStmt(Stmt node) {
    List<Stmt> list = (parent == null) ? getUpdateStmtListNoTransform() : getUpdateStmtList();
    list.addChild(node);
  }
  /**
   * @apilevel low-level
   */
  public void addUpdateStmtNoTransform(Stmt node) {
    List<Stmt> list = getUpdateStmtListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the UpdateStmt list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setUpdateStmt(Stmt node, int i) {
    List<Stmt> list = getUpdateStmtList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the UpdateStmt list.
   * @return The node representing the UpdateStmt list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="UpdateStmt")
  public List<Stmt> getUpdateStmtList() {
    List<Stmt> list = (List<Stmt>) getChild(2);
    return list;
  }
  /**
   * Retrieves the UpdateStmt list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the UpdateStmt list.
   * @apilevel low-level
   */
  public List<Stmt> getUpdateStmtListNoTransform() {
    return (List<Stmt>) getChildNoTransform(2);
  }
  /**
   * Retrieves the UpdateStmt list.
   * @return The node representing the UpdateStmt list.
   * @apilevel high-level
   */
  public List<Stmt> getUpdateStmts() {
    return getUpdateStmtList();
  }
  /**
   * Retrieves the UpdateStmt list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the UpdateStmt list.
   * @apilevel low-level
   */
  public List<Stmt> getUpdateStmtsNoTransform() {
    return getUpdateStmtListNoTransform();
  }
  /**
   * Replaces the Stmt child.
   * @param node The new node to replace the Stmt child.
   * @apilevel high-level
   */
  public void setStmt(Stmt node) {
    setChild(node, 3);
  }
  /**
   * Retrieves the Stmt child.
   * @return The current node used as the Stmt child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Stmt")
  public Stmt getStmt() {
    return (Stmt) getChild(3);
  }
  /**
   * Retrieves the Stmt child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Stmt child.
   * @apilevel low-level
   */
  public Stmt getStmtNoTransform() {
    return (Stmt) getChildNoTransform(3);
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
      if (!(!hasCondition() || getCondition().isDAafterFalse(v))) {
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
  @ASTNodeAnnotation.Attribute
  public boolean isDAafterInitialization(Variable v) {
    boolean isDAafterInitialization_Variable_value = getNumInitStmt() == 0 ? isDAbefore(v) : getInitStmt(getNumInitStmt()-1).isDAafter(v);

    return isDAafterInitialization_Variable_value;
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
      if (!isDUbeforeCondition(v)) {
        // start a circular evaluation here
        return false;
      }
      if (!(!hasCondition() || getCondition().isDUafterFalse(v))) {
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
  @ASTNodeAnnotation.Attribute
  public boolean isDUafterInit(Variable v) {
    boolean isDUafterInit_Variable_value = getNumInitStmt() == 0 ? isDUbefore(v) : getInitStmt(getNumInitStmt()-1).isDUafter(v);

    return isDUafterInit_Variable_value;
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
      if (!isDUafterInit(v)) {
        return false;
      } else if (!isDUafterUpdate(v)) {
        return false;
      }
      return true;
    }
  /**
   * @attribute syn
   * @aspect DU
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:1302
   */
  @ASTNodeAnnotation.Attribute
  public boolean isDUafterUpdate(Variable v) {
    {
        if (!isDUbeforeCondition(v)) // start a circular evaluation here
          return false;
        if (getNumUpdateStmt() > 0) {
          return getUpdateStmt(getNumUpdateStmt()-1).isDUafter(v);
        }
        if (!getStmt().isDUafter(v)) {
          return false;
        }
        for (Iterator iter = targetContinues().iterator(); iter.hasNext(); ) {
          ContinueStmt stmt = (ContinueStmt) iter.next();
          if (!stmt.isDUafterReachedFinallyBlocks(v)) {
            return false;
          }
        }
        return true;
      }
  }
  /**
   * @apilevel internal
   */
  protected java.util.Map localLookup_String_values;
  /**
   * @apilevel internal
   */
  private void localLookup_String_reset() {
    localLookup_String_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public SimpleSet localLookup(String name) {
    Object _parameters = name;
    if (localLookup_String_values == null) localLookup_String_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (localLookup_String_values.containsKey(_parameters)) {
      return (SimpleSet) localLookup_String_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    SimpleSet localLookup_String_value = localLookup_compute(name);
    if (isFinal && num == state().boundariesCrossed) {
      localLookup_String_values.put(_parameters, localLookup_String_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return localLookup_String_value;
  }
  /**
   * @apilevel internal
   */
  private SimpleSet localLookup_compute(String name) {
      VariableDeclaration v = localVariableDeclaration(name);
      if (v != null) {
        return v;
      }
      return lookupVariable(name);
    }
  /**
   * @apilevel internal
   */
  protected java.util.Map localVariableDeclaration_String_values;
  /**
   * @apilevel internal
   */
  private void localVariableDeclaration_String_reset() {
    localVariableDeclaration_String_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public VariableDeclaration localVariableDeclaration(String name) {
    Object _parameters = name;
    if (localVariableDeclaration_String_values == null) localVariableDeclaration_String_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (localVariableDeclaration_String_values.containsKey(_parameters)) {
      return (VariableDeclaration) localVariableDeclaration_String_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    VariableDeclaration localVariableDeclaration_String_value = localVariableDeclaration_compute(name);
    if (isFinal && num == state().boundariesCrossed) {
      localVariableDeclaration_String_values.put(_parameters, localVariableDeclaration_String_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return localVariableDeclaration_String_value;
  }
  /**
   * @apilevel internal
   */
  private VariableDeclaration localVariableDeclaration_compute(String name) {
      for (Stmt stmt: getInitStmtList()) {
        VariableDeclaration decl = stmt.variableDeclaration(name);
        if (decl != null) {
          return decl;
        }
      }
      return null;
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
    canCompleteNormally_value = reachable() && hasCondition()
          && (!getCondition().isConstant() || !getCondition().isTrue()) || reachableBreak();
    if (isFinal && num == state().boundariesCrossed) {
      canCompleteNormally_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return canCompleteNormally_value;
  }
  /**
   * @attribute syn
   * @aspect PreciseRethrow
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\PreciseRethrow.jrag:84
   */
  @ASTNodeAnnotation.Attribute
  public boolean modifiedInScope(Variable var) {
    {
        for (Stmt stmt : getInitStmtList()) {
          if (stmt.modifiedInScope(var)) {
            return true;
          }
        }
        for (Stmt stmt : getUpdateStmtList()) {
          if (stmt.modifiedInScope(var)) {
            return true;
          }
        }
        return getStmt().modifiedInScope(var);
      }
  }
  /**
   * @attribute inh
   * @aspect VariableScope
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupVariable.jrag:39
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
    if (caller == getUpdateStmtListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:716
      int childIndex = caller.getIndexOfChild(child);
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
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:706
      {
          if (hasCondition() && getCondition().isDAafterTrue(v)) {
            return true;
          }
          if (!hasCondition() && isDAafterInitialization(v)) {
            return true;
          }
          return false;
        }
    }
    else if (caller == getConditionOptNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:704
      return isDAafterInitialization(v);
    }
    else if (caller == getInitStmtListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:701
      int i = caller.getIndexOfChild(child);
      return i == 0 ? isDAbefore(v) : getInitStmt(i-1).isDAafter(v);
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
    if (caller == getUpdateStmtListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:1321
      int i = caller.getIndexOfChild(child);
      {
          if (!isDUbeforeCondition(v)) // start a circular evaluation here
            return false;
          if (i == 0) {
            if (!getStmt().isDUafter(v)) {
              return false;
            }
            for (Iterator iter = targetContinues().iterator(); iter.hasNext(); ) {
              ContinueStmt stmt = (ContinueStmt) iter.next();
              if (!stmt.isDUafterReachedFinallyBlocks(v)) {
                return false;
              }
            }
            return true;
          } else {
            return getUpdateStmt(i-1).isDUafter(v);
          }
        }
    }
    else if (caller == getStmtNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:1299
      return isDUbeforeCondition(v) && (hasCondition() ?
          getCondition().isDUafterTrue(v) : isDUafterInit(v));
    }
    else if (caller == getConditionOptNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:1286
      return isDUbeforeCondition(v);
    }
    else if (caller == getInitStmtListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:1284
      int childIndex = caller.getIndexOfChild(child);
      return childIndex == 0 ? isDUbefore(v) : getInitStmt(childIndex-1).isDUafter(v);
    }
    else {
      return getParent().Define_isDUbefore(this, caller, v);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\LookupVariable.jrag:30
   * @apilevel internal
   */
  public SimpleSet Define_lookupVariable(ASTNode caller, ASTNode child, String name) {
    if (caller == getStmtNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupVariable.jrag:150
      return localLookup(name);
    }
    else if (caller == getUpdateStmtListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupVariable.jrag:149
      int childIndex = caller.getIndexOfChild(child);
      return localLookup(name);
    }
    else if (caller == getConditionOptNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupVariable.jrag:148
      return localLookup(name);
    }
    else if (caller == getInitStmtListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupVariable.jrag:147
      int childIndex = caller.getIndexOfChild(child);
      return localLookup(name);
    }
    else {
      return getParent().Define_lookupVariable(this, caller, name);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\NameCheck.jrag:30
   * @apilevel internal
   */
  public VariableScope Define_outerScope(ASTNode caller, ASTNode child) {
    if (caller == getStmtNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\NameCheck.jrag:370
      return this;
    }
    else if (caller == getInitStmtListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\NameCheck.jrag:369
      int childIndex = caller.getIndexOfChild(child);
      return this;
    }
    else {
      return getParent().Define_outerScope(this, caller);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\NameCheck.jrag:441
   * @apilevel internal
   */
  public boolean Define_insideLoop(ASTNode caller, ASTNode child) {
    if (caller == getStmtNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\NameCheck.jrag:445
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
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\UnreachableStatements.jrag:158
      return reachable()
            && (!hasCondition() || (!getCondition().isConstant() || !getCondition().isFalse()));
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
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\UnreachableStatements.jrag:213
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
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\EffectivelyFinal.jrag:58
      return false;
    }
    else if (caller == getUpdateStmtListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\EffectivelyFinal.jrag:57
      int childIndex = caller.getIndexOfChild(child);
      return modifiedInScope(var);
    }
    else if (caller == getInitStmtListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\EffectivelyFinal.jrag:56
      int childIndex = caller.getIndexOfChild(child);
      return modifiedInScope(var);
    }
    else {
      return getParent().Define_inhModifiedInScope(this, caller, var);
    }
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    // Declared at @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:1341
    if (!hasCondition()) {
      return rewriteRule0();
    }
    return super.rewriteTo();
  }
  /**
   * @declaredat @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:1341
   * @apilevel internal
   */
  private ForStmt rewriteRule0() {
{
      setCondition(new BooleanLiteral("true"));
      return this;
    }  }
}
