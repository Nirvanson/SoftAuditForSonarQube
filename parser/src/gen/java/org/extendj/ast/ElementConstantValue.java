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
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\grammar\\Annotations.ast:11
 * @production ElementConstantValue : {@link ElementValue} ::= <span class="component">{@link Expr}</span>;

 */
public class ElementConstantValue extends ElementValue implements Cloneable {
  /**
   * @aspect Java5PrettyPrint
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\PrettyPrint.jadd:325
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print(getExpr());
  }
  /**
   * @aspect Annotations
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Annotations.jrag:214
   */
  public void nameCheck() {
    if (enclosingAnnotationDecl().fullName().equals("java.lang.annotation.Target")) {
      Variable v = getExpr().varDecl();
      if (v != null && v.hostType().fullName().equals("java.lang.annotation.ElementType")) {
        if (lookupElementTypeValue(v.name()) != this) {
          error("repeated annotation target");
        }
      }
    }
  }
  /**
   * @declaredat ASTNode:1
   */
  public ElementConstantValue() {
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
  public ElementConstantValue(Expr p0) {
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
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:37
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:43
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:49
   */
  public ElementConstantValue clone() throws CloneNotSupportedException {
    ElementConstantValue node = (ElementConstantValue) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:56
   */
  public ElementConstantValue copy() {
    try {
      ElementConstantValue node = (ElementConstantValue) clone();
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
   * @declaredat ASTNode:75
   */
  @Deprecated
  public ElementConstantValue fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:85
   */
  public ElementConstantValue treeCopyNoTransform() {
    ElementConstantValue tree = (ElementConstantValue) copy();
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
   * @declaredat ASTNode:105
   */
  public ElementConstantValue treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:112
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
  /**
   * @attribute syn
   * @aspect Annotations
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Annotations.jrag:80
   */
  @ASTNodeAnnotation.Attribute
  public boolean validTarget(Annotation a) {
    {
        Variable v = getExpr().varDecl();
        if (v == null) {
          return true;
        }
        return v.hostType().fullName().equals("java.lang.annotation.ElementType") && a.mayUseAnnotationTarget(v.name());
      }
  }
  /**
   * @attribute syn
   * @aspect Annotations
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Annotations.jrag:228
   */
  @ASTNodeAnnotation.Attribute
  public ElementValue definesElementTypeValue(String name) {
    {
        Variable v = getExpr().varDecl();
        if (v != null && v.hostType().fullName().equals("java.lang.annotation.ElementType") && v.name().equals(name)) {
          return this;
        }
        return null;
      }
  }
  @ASTNodeAnnotation.Attribute
  public boolean hasValue(String annot) {
    boolean hasValue_String_value = getExpr().type().isString() && getExpr().isConstant()
          && getExpr().constant().stringValue().equals(annot);

    return hasValue_String_value;
  }
  /**
   * @attribute syn
   * @aspect Annotations
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Annotations.jrag:558
   */
  @ASTNodeAnnotation.Attribute
  public boolean commensurateWithTypeDecl(TypeDecl type) {
    {
        Expr v = getExpr();
        if (!v.type().assignConversionTo(type, v)) {
          return false;
        }
        if ((type.isPrimitive() || type.isString()) && !v.isConstant()) {
          return false;
        }
        if (v.type().isNull()) {
          return false;
        }
        if (type.fullName().equals("java.lang.Class") && !v.isClassAccess()) {
          return false;
        }
        if (type.isEnumDecl() && (v.varDecl() == null || !(v.varDecl() instanceof EnumConstant))) {
          return false;
        }
        return true;
      }
  }
  @ASTNodeAnnotation.Attribute
  public TypeDecl type() {
    TypeDecl type_value = getExpr().type();

    return type_value;
  }
  /**
   * @attribute inh
   * @aspect Annotations
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Annotations.jrag:224
   */
  @ASTNodeAnnotation.Attribute
  public ElementValue lookupElementTypeValue(String name) {
    ElementValue lookupElementTypeValue_String_value = getParent().Define_lookupElementTypeValue(this, null, name);

    return lookupElementTypeValue_String_value;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\SyntacticClassification.jrag:36
   * @apilevel internal
   */
  public NameType Define_nameType(ASTNode caller, ASTNode child) {
    if (caller == getExprNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Annotations.jrag:647
      return NameType.EXPRESSION_NAME;
    }
    else {
      return getParent().Define_nameType(this, caller);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:32
   * @apilevel internal
   */
  public String Define_methodHost(ASTNode caller, ASTNode child) {
    if (caller == getExprNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Annotations.jrag:652
      return enclosingAnnotationDecl().typeName();
    }
    else {
      return getParent().Define_methodHost(this, caller);
    }
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
