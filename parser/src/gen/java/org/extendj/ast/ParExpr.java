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
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\grammar\\Java.ast:128
 * @production ParExpr : {@link PrimaryExpr} ::= <span class="component">{@link Expr}</span>;

 */
public class ParExpr extends PrimaryExpr implements Cloneable {
  /**
   * @aspect Java4PrettyPrint
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\PrettyPrint.jadd:146
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print("(");
    out.print(getExpr());
    out.print(")");
  }
  /**
   * @aspect TypeCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeCheck.jrag:310
   */
  public void typeCheck() {
    if (getExpr().isTypeAccess()) {
      errorf("%s is a type and may not be used in parenthesized expression", getExpr().prettyPrint());
    }
  }
  /**
   * @declaredat ASTNode:1
   */
  public ParExpr() {
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
  public ParExpr(Expr p0) {
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
    type_reset();
    compatibleStrictContext_TypeDecl_reset();
    compatibleLooseContext_TypeDecl_reset();
    pertinentToApplicability_Expr_BodyDecl_int_reset();
    moreSpecificThan_TypeDecl_TypeDecl_reset();
    potentiallyCompatible_TypeDecl_BodyDecl_reset();
    isBooleanExpression_reset();
    isPolyExpression_reset();
    assignConversionTo_TypeDecl_reset();
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
  public ParExpr clone() throws CloneNotSupportedException {
    ParExpr node = (ParExpr) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:65
   */
  public ParExpr copy() {
    try {
      ParExpr node = (ParExpr) clone();
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
  public ParExpr fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:94
   */
  public ParExpr treeCopyNoTransform() {
    ParExpr tree = (ParExpr) copy();
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
  public ParExpr treeCopy() {
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
   * Replaces the Expr child.
   * @param node The new node to replace the Expr child.
   * @apilevel high-level
   */
  public void setExpr(Expr node) {
    setChild(node, 0);
  }
  /**
   * Retrieves the Expr child.
   * @return The current node used as the Expr child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Expr")
  public Expr getExpr() {
    return (Expr) getChild(0);
  }
  /**
   * Retrieves the Expr child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Expr child.
   * @apilevel low-level
   */
  public Expr getExprNoTransform() {
    return (Expr) getChildNoTransform(0);
  }
  @ASTNodeAnnotation.Attribute
  public Constant constant() {
    Constant constant_value = getExpr().constant();

    return constant_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isConstant() {
    boolean isConstant_value = getExpr().isConstant();

    return isConstant_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isTrue() {
    boolean isTrue_value = getExpr().isTrue();

    return isTrue_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isFalse() {
    boolean isFalse_value = getExpr().isFalse();

    return isFalse_value;
  }
  @ASTNodeAnnotation.Attribute
  public Variable varDecl() {
    Variable varDecl_value = getExpr().varDecl();

    return varDecl_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isDAafterTrue(Variable v) {
    boolean isDAafterTrue_Variable_value = isFalse() || getExpr().isDAafterTrue(v);

    return isDAafterTrue_Variable_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isDAafterFalse(Variable v) {
    boolean isDAafterFalse_Variable_value = isTrue() || getExpr().isDAafterFalse(v);

    return isDAafterFalse_Variable_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isDAafter(Variable v) {
    boolean isDAafter_Variable_value = getExpr().isDAafter(v);

    return isDAafter_Variable_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isDUafterTrue(Variable v) {
    boolean isDUafterTrue_Variable_value = getExpr().isDUafterTrue(v);

    return isDUafterTrue_Variable_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isDUafterFalse(Variable v) {
    boolean isDUafterFalse_Variable_value = getExpr().isDUafterFalse(v);

    return isDUafterFalse_Variable_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isDUafter(Variable v) {
    boolean isDUafter_Variable_value = getExpr().isDUafter(v);

    return isDUafter_Variable_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isSuperAccess() {
    boolean isSuperAccess_value = getExpr().isSuperAccess();

    return isSuperAccess_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isThisAccess() {
    boolean isThisAccess_value = getExpr().isThisAccess();

    return isThisAccess_value;
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
    type_value = getExpr().isTypeAccess() ? unknownType() : getExpr().type();
    if (isFinal && num == state().boundariesCrossed) {
      type_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return type_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isVariable() {
    boolean isVariable_value = getExpr().isVariable();

    return isVariable_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean staticContextQualifier() {
    boolean staticContextQualifier_value = getExpr().staticContextQualifier();

    return staticContextQualifier_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean modifiedInScope(Variable var) {
    boolean modifiedInScope_Variable_value = getExpr().modifiedInScope(var);

    return modifiedInScope_Variable_value;
  }
  /**
   * @apilevel internal
   */
  protected java.util.Map compatibleStrictContext_TypeDecl_values;
  /**
   * @apilevel internal
   */
  private void compatibleStrictContext_TypeDecl_reset() {
    compatibleStrictContext_TypeDecl_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean compatibleStrictContext(TypeDecl type) {
    Object _parameters = type;
    if (compatibleStrictContext_TypeDecl_values == null) compatibleStrictContext_TypeDecl_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (compatibleStrictContext_TypeDecl_values.containsKey(_parameters)) {
      return (Boolean) compatibleStrictContext_TypeDecl_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean compatibleStrictContext_TypeDecl_value = getExpr().compatibleStrictContext(type);
    if (isFinal && num == state().boundariesCrossed) {
      compatibleStrictContext_TypeDecl_values.put(_parameters, compatibleStrictContext_TypeDecl_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return compatibleStrictContext_TypeDecl_value;
  }
  /**
   * @apilevel internal
   */
  protected java.util.Map compatibleLooseContext_TypeDecl_values;
  /**
   * @apilevel internal
   */
  private void compatibleLooseContext_TypeDecl_reset() {
    compatibleLooseContext_TypeDecl_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean compatibleLooseContext(TypeDecl type) {
    Object _parameters = type;
    if (compatibleLooseContext_TypeDecl_values == null) compatibleLooseContext_TypeDecl_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (compatibleLooseContext_TypeDecl_values.containsKey(_parameters)) {
      return (Boolean) compatibleLooseContext_TypeDecl_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean compatibleLooseContext_TypeDecl_value = getExpr().compatibleLooseContext(type);
    if (isFinal && num == state().boundariesCrossed) {
      compatibleLooseContext_TypeDecl_values.put(_parameters, compatibleLooseContext_TypeDecl_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return compatibleLooseContext_TypeDecl_value;
  }
  /**
   * @apilevel internal
   */
  protected java.util.Map pertinentToApplicability_Expr_BodyDecl_int_values;
  /**
   * @apilevel internal
   */
  private void pertinentToApplicability_Expr_BodyDecl_int_reset() {
    pertinentToApplicability_Expr_BodyDecl_int_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean pertinentToApplicability(Expr access, BodyDecl decl, int argIndex) {
    java.util.List _parameters = new java.util.ArrayList(3);
    _parameters.add(access);
    _parameters.add(decl);
    _parameters.add(argIndex);
    if (pertinentToApplicability_Expr_BodyDecl_int_values == null) pertinentToApplicability_Expr_BodyDecl_int_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (pertinentToApplicability_Expr_BodyDecl_int_values.containsKey(_parameters)) {
      return (Boolean) pertinentToApplicability_Expr_BodyDecl_int_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean pertinentToApplicability_Expr_BodyDecl_int_value = getExpr().pertinentToApplicability(access, decl, argIndex);
    if (isFinal && num == state().boundariesCrossed) {
      pertinentToApplicability_Expr_BodyDecl_int_values.put(_parameters, pertinentToApplicability_Expr_BodyDecl_int_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return pertinentToApplicability_Expr_BodyDecl_int_value;
  }
  /**
   * @apilevel internal
   */
  protected java.util.Map moreSpecificThan_TypeDecl_TypeDecl_values;
  /**
   * @apilevel internal
   */
  private void moreSpecificThan_TypeDecl_TypeDecl_reset() {
    moreSpecificThan_TypeDecl_TypeDecl_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean moreSpecificThan(TypeDecl type1, TypeDecl type2) {
    java.util.List _parameters = new java.util.ArrayList(2);
    _parameters.add(type1);
    _parameters.add(type2);
    if (moreSpecificThan_TypeDecl_TypeDecl_values == null) moreSpecificThan_TypeDecl_TypeDecl_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (moreSpecificThan_TypeDecl_TypeDecl_values.containsKey(_parameters)) {
      return (Boolean) moreSpecificThan_TypeDecl_TypeDecl_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean moreSpecificThan_TypeDecl_TypeDecl_value = moreSpecificThan_compute(type1, type2);
    if (isFinal && num == state().boundariesCrossed) {
      moreSpecificThan_TypeDecl_TypeDecl_values.put(_parameters, moreSpecificThan_TypeDecl_TypeDecl_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return moreSpecificThan_TypeDecl_TypeDecl_value;
  }
  /**
   * @apilevel internal
   */
  private boolean moreSpecificThan_compute(TypeDecl type1, TypeDecl type2) {
      if (super.moreSpecificThan(type1, type2)) {
        return true;
      }
      return getExpr().moreSpecificThan(type1, type2);
    }
  /**
   * @apilevel internal
   */
  protected java.util.Map potentiallyCompatible_TypeDecl_BodyDecl_values;
  /**
   * @apilevel internal
   */
  private void potentiallyCompatible_TypeDecl_BodyDecl_reset() {
    potentiallyCompatible_TypeDecl_BodyDecl_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean potentiallyCompatible(TypeDecl type, BodyDecl candidateDecl) {
    java.util.List _parameters = new java.util.ArrayList(2);
    _parameters.add(type);
    _parameters.add(candidateDecl);
    if (potentiallyCompatible_TypeDecl_BodyDecl_values == null) potentiallyCompatible_TypeDecl_BodyDecl_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (potentiallyCompatible_TypeDecl_BodyDecl_values.containsKey(_parameters)) {
      return (Boolean) potentiallyCompatible_TypeDecl_BodyDecl_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean potentiallyCompatible_TypeDecl_BodyDecl_value = getExpr().potentiallyCompatible(type, candidateDecl);
    if (isFinal && num == state().boundariesCrossed) {
      potentiallyCompatible_TypeDecl_BodyDecl_values.put(_parameters, potentiallyCompatible_TypeDecl_BodyDecl_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return potentiallyCompatible_TypeDecl_BodyDecl_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean isBooleanExpression_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean isBooleanExpression_value;
  /**
   * @apilevel internal
   */
  private void isBooleanExpression_reset() {
    isBooleanExpression_computed = false;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isBooleanExpression() {
    ASTNode$State state = state();
    if (isBooleanExpression_computed) {
      return isBooleanExpression_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    isBooleanExpression_value = getExpr().isBooleanExpression();
    if (isFinal && num == state().boundariesCrossed) {
      isBooleanExpression_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return isBooleanExpression_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean isPolyExpression_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean isPolyExpression_value;
  /**
   * @apilevel internal
   */
  private void isPolyExpression_reset() {
    isPolyExpression_computed = false;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isPolyExpression() {
    ASTNode$State state = state();
    if (isPolyExpression_computed) {
      return isPolyExpression_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    isPolyExpression_value = getExpr().isPolyExpression();
    if (isFinal && num == state().boundariesCrossed) {
      isPolyExpression_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return isPolyExpression_value;
  }
  /**
   * @apilevel internal
   */
  protected java.util.Map assignConversionTo_TypeDecl_values;
  /**
   * @apilevel internal
   */
  private void assignConversionTo_TypeDecl_reset() {
    assignConversionTo_TypeDecl_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean assignConversionTo(TypeDecl type) {
    Object _parameters = type;
    if (assignConversionTo_TypeDecl_values == null) assignConversionTo_TypeDecl_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (assignConversionTo_TypeDecl_values.containsKey(_parameters)) {
      return (Boolean) assignConversionTo_TypeDecl_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean assignConversionTo_TypeDecl_value = getExpr().assignConversionTo(type);
    if (isFinal && num == state().boundariesCrossed) {
      assignConversionTo_TypeDecl_values.put(_parameters, assignConversionTo_TypeDecl_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return assignConversionTo_TypeDecl_value;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:30
   * @apilevel internal
   */
  public TypeDecl Define_targetType(ASTNode caller, ASTNode child) {
    if (caller == getExprNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:37
      return targetType();
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
    if (caller == getExprNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:301
      return assignmentContext();
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
    if (caller == getExprNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:302
      return invocationContext();
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
    if (caller == getExprNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:303
      return castContext();
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
    if (caller == getExprNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:304
      return stringContext();
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
    if (caller == getExprNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:305
      return numericContext();
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
