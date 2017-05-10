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
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\grammar\\Java.ast:134
 * @production PreIncExpr : {@link Unary};

 */
public class PreIncExpr extends Unary implements Cloneable {
  /**
   * @aspect DefiniteAssignment
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:95
   */
  public void definiteAssignment() {
    if (getOperand().isVariable()) {
      Variable v = getOperand().varDecl();
      if (v != null && v.isFinal()) {
        error("++ and -- can not be applied to final variable " + v);
      }
    }
  }
  /**
   * @aspect DA
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:529
   */
  protected boolean checkDUeverywhere(Variable v) {
    if (getOperand().isVariable() && getOperand().varDecl() == v) {
      if (!isDAbefore(v)) {
        return false;
      }
    }
    return super.checkDUeverywhere(v);
  }
  /**
   * @aspect TypeCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeCheck.jrag:354
   */
  public void typeCheck() {
    if (!getOperand().isVariable()) {
      error("prefix increment expression only work on variables");
    } else if (!getOperand().type().isNumericType()) {
      error("unary increment only operates on numeric types");
    }
  }
  /**
   * @declaredat ASTNode:1
   */
  public PreIncExpr() {
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
  }
  /**
   * @declaredat ASTNode:13
   */
  public PreIncExpr(Expr p0) {
    setChild(p0, 0);
  }
  /**
   * @apilevel low-level
   * @declaredat ASTNode:19
   */
  protected int numChildren() {
    return 1;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:25
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:31
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    stmtCompatible_reset();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:38
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:44
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:50
   */
  public PreIncExpr clone() throws CloneNotSupportedException {
    PreIncExpr node = (PreIncExpr) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:57
   */
  public PreIncExpr copy() {
    try {
      PreIncExpr node = (PreIncExpr) clone();
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
   * @declaredat ASTNode:76
   */
  @Deprecated
  public PreIncExpr fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:86
   */
  public PreIncExpr treeCopyNoTransform() {
    PreIncExpr tree = (PreIncExpr) copy();
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
   * @declaredat ASTNode:106
   */
  public PreIncExpr treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:113
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node);    
  }
  /**
   * Replaces the Operand child.
   * @param node The new node to replace the Operand child.
   * @apilevel high-level
   */
  public void setOperand(Expr node) {
    setChild(node, 0);
  }
  /**
   * Retrieves the Operand child.
   * @return The current node used as the Operand child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Operand")
  public Expr getOperand() {
    return (Expr) getChild(0);
  }
  /**
   * Retrieves the Operand child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Operand child.
   * @apilevel low-level
   */
  public Expr getOperandNoTransform() {
    return (Expr) getChildNoTransform(0);
  }
  @ASTNodeAnnotation.Attribute
  public String printPreOp() {
    String printPreOp_value = "++";

    return printPreOp_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean modifiedInScope(Variable var) {
    boolean modifiedInScope_Variable_value = getOperand().isVariable(var);

    return modifiedInScope_Variable_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean stmtCompatible_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean stmtCompatible_value;
  /**
   * @apilevel internal
   */
  private void stmtCompatible_reset() {
    stmtCompatible_computed = false;
  }
  @ASTNodeAnnotation.Attribute
  public boolean stmtCompatible() {
    ASTNode$State state = state();
    if (stmtCompatible_computed) {
      return stmtCompatible_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    stmtCompatible_value = true;
    if (isFinal && num == state().boundariesCrossed) {
      stmtCompatible_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return stmtCompatible_value;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:37
   * @apilevel internal
   */
  public boolean Define_isDest(ASTNode caller, ASTNode child) {
    if (caller == getOperandNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:68
      return true;
    }
    else {
      return getParent().Define_isDest(this, caller);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:71
   * @apilevel internal
   */
  public boolean Define_isIncOrDec(ASTNode caller, ASTNode child) {
    if (caller == getOperandNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:77
      return true;
    }
    else {
      return getParent().Define_isIncOrDec(this, caller);
    }
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
