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
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\grammar\\Java.ast:193
 * @production Block : {@link Stmt} ::= <span class="component">{@link Stmt}*</span>;

 */
public class Block extends Stmt implements Cloneable, VariableScope {
  /**
   * @aspect Java4PrettyPrint
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\PrettyPrint.jadd:455
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print("{");
    out.println();
    out.indent(1);
    out.join(getStmtList(), new PrettyPrinter.Joiner() {
      @Override
      public void printSeparator(PrettyPrinter out) {
        out.println();
      }
    });
    if (!out.isNewLine()) {
      out.println();
    }
    out.print("}");
  }
  /**
   * @declaredat ASTNode:1
   */
  public Block() {
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
  public Block(List<Stmt> p0) {
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
    checkReturnDA_Variable_reset();
    isDAafter_Variable_reset();
    checkReturnDU_Variable_reset();
    isDUafter_Variable_reset();
    localVariableDeclaration_String_reset();
    canCompleteNormally_reset();
    lookupType_String_reset();
    lookupVariable_String_reset();
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
  public Block clone() throws CloneNotSupportedException {
    Block node = (Block) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:65
   */
  public Block copy() {
    try {
      Block node = (Block) clone();
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
  public Block fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:94
   */
  public Block treeCopyNoTransform() {
    Block tree = (Block) copy();
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
   * @declaredat ASTNode:114
   */
  public Block treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:121
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node);    
  }
  /**
   * Replaces the Stmt list.
   * @param list The new list node to be used as the Stmt list.
   * @apilevel high-level
   */
  public void setStmtList(List<Stmt> list) {
    setChild(list, 0);
  }
  /**
   * Retrieves the number of children in the Stmt list.
   * @return Number of children in the Stmt list.
   * @apilevel high-level
   */
  public int getNumStmt() {
    return getStmtList().getNumChild();
  }
  /**
   * Retrieves the number of children in the Stmt list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the Stmt list.
   * @apilevel low-level
   */
  public int getNumStmtNoTransform() {
    return getStmtListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the Stmt list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the Stmt list.
   * @apilevel high-level
   */
  public Stmt getStmt(int i) {
    return (Stmt) getStmtList().getChild(i);
  }
  /**
   * Check whether the Stmt list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasStmt() {
    return getStmtList().getNumChild() != 0;
  }
  /**
   * Append an element to the Stmt list.
   * @param node The element to append to the Stmt list.
   * @apilevel high-level
   */
  public void addStmt(Stmt node) {
    List<Stmt> list = (parent == null) ? getStmtListNoTransform() : getStmtList();
    list.addChild(node);
  }
  /**
   * @apilevel low-level
   */
  public void addStmtNoTransform(Stmt node) {
    List<Stmt> list = getStmtListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the Stmt list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setStmt(Stmt node, int i) {
    List<Stmt> list = getStmtList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the Stmt list.
   * @return The node representing the Stmt list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="Stmt")
  public List<Stmt> getStmtList() {
    List<Stmt> list = (List<Stmt>) getChild(0);
    return list;
  }
  /**
   * Retrieves the Stmt list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the Stmt list.
   * @apilevel low-level
   */
  public List<Stmt> getStmtListNoTransform() {
    return (List<Stmt>) getChildNoTransform(0);
  }
  /**
   * Retrieves the Stmt list.
   * @return The node representing the Stmt list.
   * @apilevel high-level
   */
  public List<Stmt> getStmts() {
    return getStmtList();
  }
  /**
   * Retrieves the Stmt list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the Stmt list.
   * @apilevel low-level
   */
  public List<Stmt> getStmtsNoTransform() {
    return getStmtListNoTransform();
  }
  @ASTNodeAnnotation.Attribute
  public boolean declaredBeforeUse(VariableDeclaration decl, int indexUse) {
    boolean declaredBeforeUse_VariableDeclaration_int_value = decl.blockIndex() < indexUse;

    return declaredBeforeUse_VariableDeclaration_int_value;
  }
  /**
   * @apilevel internal
   */
  protected java.util.Map checkReturnDA_Variable_values;
  /**
   * @apilevel internal
   */
  private void checkReturnDA_Variable_reset() {
    checkReturnDA_Variable_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean checkReturnDA(Variable v) {
    Object _parameters = v;
    if (checkReturnDA_Variable_values == null) checkReturnDA_Variable_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (checkReturnDA_Variable_values.containsKey(_parameters)) {
      return (Boolean) checkReturnDA_Variable_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean checkReturnDA_Variable_value = checkReturnDA_compute(v);
    if (isFinal && num == state().boundariesCrossed) {
      checkReturnDA_Variable_values.put(_parameters, checkReturnDA_Variable_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return checkReturnDA_Variable_value;
  }
  /**
   * @apilevel internal
   */
  private boolean checkReturnDA_compute(Variable v) {
      HashSet set = new HashSet();
      collectBranches(set);
      for (Iterator iter = set.iterator(); iter.hasNext(); ) {
        Object o = iter.next();
        if (o instanceof ReturnStmt) {
          ReturnStmt stmt = (ReturnStmt) o;
          if (!stmt.isDAafterReachedFinallyBlocks(v)) {
            return false;
          }
        }
      }
      return true;
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
    boolean isDAafter_Variable_value = getNumStmt() == 0 ? isDAbefore(v) : getStmt(getNumStmt()-1).isDAafter(v);
    if (isFinal && num == state().boundariesCrossed) {
      isDAafter_Variable_values.put(_parameters, isDAafter_Variable_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return isDAafter_Variable_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isDUeverywhere(Variable v) {
    boolean isDUeverywhere_Variable_value = isDUbefore(v) && checkDUeverywhere(v);

    return isDUeverywhere_Variable_value;
  }
  /**
   * @apilevel internal
   */
  protected java.util.Map checkReturnDU_Variable_values;
  /**
   * @apilevel internal
   */
  private void checkReturnDU_Variable_reset() {
    checkReturnDU_Variable_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean checkReturnDU(Variable v) {
    Object _parameters = v;
    if (checkReturnDU_Variable_values == null) checkReturnDU_Variable_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (checkReturnDU_Variable_values.containsKey(_parameters)) {
      return (Boolean) checkReturnDU_Variable_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean checkReturnDU_Variable_value = checkReturnDU_compute(v);
    if (isFinal && num == state().boundariesCrossed) {
      checkReturnDU_Variable_values.put(_parameters, checkReturnDU_Variable_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return checkReturnDU_Variable_value;
  }
  /**
   * @apilevel internal
   */
  private boolean checkReturnDU_compute(Variable v) {
      HashSet set = new HashSet();
      collectBranches(set);
      for (Iterator iter = set.iterator(); iter.hasNext(); ) {
        Object o = iter.next();
        if (o instanceof ReturnStmt) {
          ReturnStmt stmt = (ReturnStmt) o;
          if (!stmt.isDUafterReachedFinallyBlocks(v)) {
            return false;
          }
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
    boolean isDUafter_Variable_value = getNumStmt() == 0 ? isDUbefore(v) : getStmt(getNumStmt()-1).isDUafter(v);
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
      for (Stmt stmt: getStmtList()) {
        VariableDeclaration decl = stmt.variableDeclaration(name);
        if (decl != null) {
          return decl;
        }
      }
      return null;
    }
  @ASTNodeAnnotation.Attribute
  public boolean hasStmts() {
    boolean hasStmts_value = getNumStmt() > 0;

    return hasStmts_value;
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
    canCompleteNormally_value = getNumStmt() == 0
          ? reachable()
          : getStmt(getNumStmt() - 1).canCompleteNormally();
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
        for (Stmt stmt : getStmtList()) {
          if (stmt.modifiedInScope(var)) {
            return true;
          }
        }
        return false;
      }
  }
  /**
   * @attribute inh
   * @aspect TypeScopePropagation
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:340
   */
  @ASTNodeAnnotation.Attribute
  public SimpleSet lookupType(String name) {
    Object _parameters = name;
    if (lookupType_String_values == null) lookupType_String_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (lookupType_String_values.containsKey(_parameters)) {
      return (SimpleSet) lookupType_String_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    SimpleSet lookupType_String_value = getParent().Define_lookupType(this, null, name);
    if (isFinal && num == state().boundariesCrossed) {
      lookupType_String_values.put(_parameters, lookupType_String_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return lookupType_String_value;
  }
  /**
   * @apilevel internal
   */
  protected java.util.Map lookupType_String_values;
  /**
   * @apilevel internal
   */
  private void lookupType_String_reset() {
    lookupType_String_values = null;
  }
  /**
   * @attribute inh
   * @aspect VariableScope
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupVariable.jrag:38
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
   * @aspect NameCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\NameCheck.jrag:525
   */
  @ASTNodeAnnotation.Attribute
  public SimpleSet otherLocalClassDecls(String name) {
    SimpleSet otherLocalClassDecls_String_value = getParent().Define_otherLocalClassDecls(this, null, name);

    return otherLocalClassDecls_String_value;
  }
  /**
   * @attribute inh
   * @aspect UnreachableStatements
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\UnreachableStatements.jrag:52
   */
  @ASTNodeAnnotation.Attribute
  public boolean reachable() {
    boolean reachable_value = getParent().Define_reachable(this, null);

    return reachable_value;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DeclareBeforeUse.jrag:34
   * @apilevel internal
   */
  public int Define_blockIndex(ASTNode caller, ASTNode child) {
    if (caller == getStmtListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DeclareBeforeUse.jrag:36
      int index = caller.getIndexOfChild(child);
      return index;
    }
    else {
      return getParent().Define_blockIndex(this, caller);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:71
   * @apilevel internal
   */
  public boolean Define_isIncOrDec(ASTNode caller, ASTNode child) {
    if (caller == getStmtListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:74
      int childIndex = caller.getIndexOfChild(child);
      return false;
    }
    else {
      return getParent().Define_isIncOrDec(this, caller);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:255
   * @apilevel internal
   */
  public boolean Define_isDAbefore(ASTNode caller, ASTNode child, Variable v) {
    if (caller == getStmtListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:486
      int index = caller.getIndexOfChild(child);
      return index == 0 ? isDAbefore(v) : getStmt(index - 1).isDAafter(v);
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
    if (caller == getStmtListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:986
      int index = caller.getIndexOfChild(child);
      return index == 0 ? isDUbefore(v) : getStmt(index - 1).isDUafter(v);
    }
    else {
      return getParent().Define_isDUbefore(this, caller, v);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\GenericMethods.jrag:197
   * @apilevel internal
   */
  public SimpleSet Define_lookupType(ASTNode caller, ASTNode child, String name) {
    if (caller == getStmtListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:500
      int index = caller.getIndexOfChild(child);
      {
          SimpleSet c = SimpleSet.emptySet;
          for (int i = index; i >= 0 && !(getStmt(i) instanceof Case); i--) {
            if (getStmt(i) instanceof LocalClassDeclStmt) {
              TypeDecl t = ((LocalClassDeclStmt) getStmt(i)).getClassDecl();
              if (t.name().equals(name)) {
                c = c.add(t);
              }
            }
          }
          if (!c.isEmpty()) {
            return c;
          }
          return lookupType(name);
        }
    }
    else {
      return getParent().Define_lookupType(this, caller, name);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\LookupVariable.jrag:30
   * @apilevel internal
   */
  public SimpleSet Define_lookupVariable(ASTNode caller, ASTNode child, String name) {
    if (caller == getStmtListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupVariable.jrag:125
      int index = caller.getIndexOfChild(child);
      {
          VariableDeclaration v = localVariableDeclaration(name);
          // declare before use and shadowing
          if (v != null && declaredBeforeUse(v, index)) {
            return v;
          }
          return lookupVariable(name);
        }
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
    if (caller == getStmtListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\NameCheck.jrag:367
      int childIndex = caller.getIndexOfChild(child);
      return this;
    }
    else {
      return getParent().Define_outerScope(this, caller);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\NameCheck.jrag:524
   * @apilevel internal
   */
  public SimpleSet Define_otherLocalClassDecls(ASTNode caller, ASTNode child, String name) {
    if (caller == getStmtListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\NameCheck.jrag:529
      int index = caller.getIndexOfChild(child);
      {
          SimpleSet local = SimpleSet.emptySet;
          for (int i = index-1; i >= 0 && !(getStmt(i) instanceof Case); --i) {
            if (getStmt(i) instanceof LocalClassDeclStmt) {
              TypeDecl t = ((LocalClassDeclStmt) getStmt(i)).getClassDecl();
              if (t.name().equals(name)) {
                local = local.add(t);
              }
            }
          }
          if (!local.isEmpty()) {
            return local;
          } else {
            return otherLocalClassDecls(name);
          }
        }
    }
    else {
      return getParent().Define_otherLocalClassDecls(this, caller, name);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\SyntacticClassification.jrag:36
   * @apilevel internal
   */
  public NameType Define_nameType(ASTNode caller, ASTNode child) {
    if (caller == getStmtListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\SyntacticClassification.jrag:138
      int childIndex = caller.getIndexOfChild(child);
      return NameType.EXPRESSION_NAME;
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
    if (caller == getStmtListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\UnreachableStatements.jrag:72
      int index = caller.getIndexOfChild(child);
      return index == 0
            ? reachable()
            : getStmt(index-1).canCompleteNormally();
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
    if (caller == getStmtListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\UnreachableStatements.jrag:210
      int i = caller.getIndexOfChild(child);
      return i == 0 ? reachable() : getStmt(i-1).reachable();
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
    if (caller == getStmtListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\EffectivelyFinal.jrag:51
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
    return super.rewriteTo();
  }
}
