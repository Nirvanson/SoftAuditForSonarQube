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
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\grammar\\Java.ast:69
 * @production BodyDecl : {@link ASTNode};

 */
public abstract class BodyDecl extends ASTNode<ASTNode> implements Cloneable {
  /**
   * @aspect DocumentationComments
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DocumentationComments.jadd:36
   */
  public String docComment = "";
  /**
   * @aspect LookupParTypeDecl
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:1361
   */
  public BodyDecl substitutedBodyDecl(Parameterization parTypeDecl) {
    throw new Error("Operation substitutedBodyDecl not supported for " + getClass().getName());
  }
  /**
   * We must report illegal uses of the SafeVarargs annotation.
   * It is only allowed on variable arity method and constructor declarations.
   * @aspect SafeVarargs
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\SafeVarargs.jrag:108
   */
  public void checkWarnings() {
    if (hasIllegalAnnotationSafeVarargs()) {
      error("@SafeVarargs is only allowed for variable arity method and constructor declarations");
    }
  }
  /**
   * @declaredat ASTNode:1
   */
  public BodyDecl() {
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
    isDAafter_Variable_reset();
    isDAbefore_Variable_reset();
    isDUafter_Variable_reset();
    isDUbefore_Variable_reset();
    typeThrowable_reset();
    lookupVariable_String_reset();
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
  public BodyDecl clone() throws CloneNotSupportedException {
    BodyDecl node = (BodyDecl) super.clone();
    return node;
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @deprecated Please use treeCopy or treeCopyNoTransform instead
   * @declaredat ASTNode:62
   */
  @Deprecated
  public abstract BodyDecl fullCopy();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:70
   */
  public abstract BodyDecl treeCopyNoTransform();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The subtree of this node is traversed to trigger rewrites before copy.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:78
   */
  public abstract BodyDecl treeCopy();
  @ASTNodeAnnotation.Attribute
  public String docComment() {
    String docComment_value = docComment;

    return docComment_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean hasDocComment() {
    boolean hasDocComment_value = !docComment.isEmpty();

    return hasDocComment_value;
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
  protected java.util.Map isDAbefore_Variable_values;
  /**
   * @apilevel internal
   */
  private void isDAbefore_Variable_reset() {
    isDAbefore_Variable_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isDAbefore(Variable v) {
    Object _parameters = v;
    if (isDAbefore_Variable_values == null) isDAbefore_Variable_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (isDAbefore_Variable_values.containsKey(_parameters)) {
      return (Boolean) isDAbefore_Variable_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean isDAbefore_Variable_value = isDAbefore(v, this);
    if (isFinal && num == state().boundariesCrossed) {
      isDAbefore_Variable_values.put(_parameters, isDAbefore_Variable_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return isDAbefore_Variable_value;
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
  protected java.util.Map isDUbefore_Variable_values;
  /**
   * @apilevel internal
   */
  private void isDUbefore_Variable_reset() {
    isDUbefore_Variable_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isDUbefore(Variable v) {
    Object _parameters = v;
    if (isDUbefore_Variable_values == null) isDUbefore_Variable_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (isDUbefore_Variable_values.containsKey(_parameters)) {
      return (Boolean) isDUbefore_Variable_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean isDUbefore_Variable_value = isDUbefore(v, this);
    if (isFinal && num == state().boundariesCrossed) {
      isDUbefore_Variable_values.put(_parameters, isDUbefore_Variable_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return isDUbefore_Variable_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean declaresType(String name) {
    boolean declaresType_String_value = false;

    return declaresType_String_value;
  }
  @ASTNodeAnnotation.Attribute
  public TypeDecl type(String name) {
    TypeDecl type_String_value = null;

    return type_String_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isVoid() {
    boolean isVoid_value = false;

    return isVoid_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean hasAnnotationSuppressWarnings(String annot) {
    boolean hasAnnotationSuppressWarnings_String_value = false;

    return hasAnnotationSuppressWarnings_String_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isDeprecated() {
    boolean isDeprecated_value = false;

    return isDeprecated_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isEnumConstant() {
    boolean isEnumConstant_value = false;

    return isEnumConstant_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean visibleTypeParameters() {
    boolean visibleTypeParameters_value = true;

    return visibleTypeParameters_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isGeneric() {
    boolean isGeneric_value = false;

    return isGeneric_value;
  }
  /**
   * Note: isGeneric must be called first to check if this declaration is generic.
   * Otherwise this attribute will throw an error!
   * @return type parameters for this declaration.
   * @attribute syn
   * @aspect MethodSignature15
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\MethodSignature.jrag:408
   */
  @ASTNodeAnnotation.Attribute
  public List<TypeVariable> typeParameters() {
    {
        throw new Error("can not evaulate type parameters for non-generic declaration");
      }
  }
  @ASTNodeAnnotation.Attribute
  public boolean hasAnnotationSafeVarargs() {
    boolean hasAnnotationSafeVarargs_value = false;

    return hasAnnotationSafeVarargs_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean hasIllegalAnnotationSafeVarargs() {
    boolean hasIllegalAnnotationSafeVarargs_value = hasAnnotationSafeVarargs();

    return hasIllegalAnnotationSafeVarargs_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean modifiedInScope(Variable var) {
    boolean modifiedInScope_Variable_value = false;

    return modifiedInScope_Variable_value;
  }
  /**
   * @attribute inh
   * @aspect DA
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:272
   */
  @ASTNodeAnnotation.Attribute
  public boolean isDAbefore(Variable v, BodyDecl b) {
    boolean isDAbefore_Variable_BodyDecl_value = getParent().Define_isDAbefore(this, null, v, b);

    return isDAbefore_Variable_BodyDecl_value;
  }
  /**
   * @attribute inh
   * @aspect DU
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:804
   */
  @ASTNodeAnnotation.Attribute
  public boolean isDUbefore(Variable v, BodyDecl b) {
    boolean isDUbefore_Variable_BodyDecl_value = getParent().Define_isDUbefore(this, null, v, b);

    return isDUbefore_Variable_BodyDecl_value;
  }
  /**
   * @attribute inh
   * @aspect ExceptionHandling
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ExceptionHandling.jrag:49
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl typeThrowable() {
    ASTNode$State state = state();
    if (typeThrowable_computed) {
      return typeThrowable_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    typeThrowable_value = getParent().Define_typeThrowable(this, null);
    if (isFinal && num == state().boundariesCrossed) {
      typeThrowable_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return typeThrowable_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean typeThrowable_computed = false;
  /**
   * @apilevel internal
   */
  protected TypeDecl typeThrowable_value;
  /**
   * @apilevel internal
   */
  private void typeThrowable_reset() {
    typeThrowable_computed = false;
    typeThrowable_value = null;
  }
  /**
   * @attribute inh
   * @aspect LookupMethod
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupMethod.jrag:47
   */
  @ASTNodeAnnotation.Attribute
  public Collection lookupMethod(String name) {
    Collection lookupMethod_String_value = getParent().Define_lookupMethod(this, null, name);

    return lookupMethod_String_value;
  }
  /**
   * @attribute inh
   * @aspect LookupFullyQualifiedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:131
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl lookupType(String packageName, String typeName) {
    TypeDecl lookupType_String_String_value = getParent().Define_lookupType(this, null, packageName, typeName);

    return lookupType_String_String_value;
  }
  /**
   * @attribute inh
   * @aspect TypeScopePropagation
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:338
   */
  @ASTNodeAnnotation.Attribute
  public SimpleSet lookupType(String name) {
    SimpleSet lookupType_String_value = getParent().Define_lookupType(this, null, name);

    return lookupType_String_value;
  }
  /**
   * @attribute inh
   * @aspect VariableScope
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupVariable.jrag:36
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
   * @aspect NestedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeAnalysis.jrag:621
   */
  @ASTNodeAnnotation.Attribute
  public String hostPackage() {
    String hostPackage_value = getParent().Define_hostPackage(this, null);

    return hostPackage_value;
  }
  /**
   * @attribute inh
   * @aspect NestedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeAnalysis.jrag:637
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl hostType() {
    TypeDecl hostType_value = getParent().Define_hostType(this, null);

    return hostType_value;
  }
  /**
   * @attribute inh
   * @aspect Annotations
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Annotations.jrag:341
   */
  @ASTNodeAnnotation.Attribute
  public boolean withinSuppressWarnings(String annot) {
    boolean withinSuppressWarnings_String_value = getParent().Define_withinSuppressWarnings(this, null, annot);

    return withinSuppressWarnings_String_value;
  }
  /**
   * @attribute inh
   * @aspect Annotations
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Annotations.jrag:449
   */
  @ASTNodeAnnotation.Attribute
  public boolean withinDeprecatedAnnotation() {
    boolean withinDeprecatedAnnotation_value = getParent().Define_withinDeprecatedAnnotation(this, null);

    return withinDeprecatedAnnotation_value;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\BranchTarget.jrag:227
   * @apilevel internal
   */
  public Stmt Define_branchTarget(ASTNode caller, ASTNode child, Stmt branch) {
    int childIndex = this.getIndexOfChild(caller);
    {
        throw new Error("Found no branch targets for " + branch.getClass().getName());
      }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\BranchTarget.jrag:262
   * @apilevel internal
   */
  public FinallyHost Define_enclosingFinally(ASTNode caller, ASTNode child, Stmt branch) {
    int childIndex = this.getIndexOfChild(caller);
    return null;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:255
   * @apilevel internal
   */
  public boolean Define_isDAbefore(ASTNode caller, ASTNode child, Variable v) {
    int childIndex = this.getIndexOfChild(caller);
    return isDAbefore(v, this);
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:779
   * @apilevel internal
   */
  public boolean Define_isDUbefore(ASTNode caller, ASTNode child, Variable v) {
    int childIndex = this.getIndexOfChild(caller);
    return isDUbefore(v, this);
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\NameCheck.jrag:524
   * @apilevel internal
   */
  public SimpleSet Define_otherLocalClassDecls(ASTNode caller, ASTNode child, String name) {
    int childIndex = this.getIndexOfChild(caller);
    return SimpleSet.emptySet;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\NameCheck.jrag:29
   * @apilevel internal
   */
  public BodyDecl Define_enclosingBodyDecl(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return this;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\SuppressWarnings.jrag:38
   * @apilevel internal
   */
  public boolean Define_withinSuppressWarnings(ASTNode caller, ASTNode child, String annot) {
    int childIndex = this.getIndexOfChild(caller);
    return hasAnnotationSuppressWarnings(annot) || hasAnnotationSuppressWarnings(annot)
          || withinSuppressWarnings(annot);
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Annotations.jrag:449
   * @apilevel internal
   */
  public boolean Define_withinDeprecatedAnnotation(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return isDeprecated() || isDeprecated() || withinDeprecatedAnnotation();
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\TryWithResources.jrag:183
   * @apilevel internal
   */
  public boolean Define_resourcePreviouslyDeclared(ASTNode caller, ASTNode child, String name) {
    int i = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
