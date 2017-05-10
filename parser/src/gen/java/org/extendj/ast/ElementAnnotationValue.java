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
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\grammar\\Annotations.ast:12
 * @production ElementAnnotationValue : {@link ElementValue} ::= <span class="component">{@link Annotation}</span>;

 */
public class ElementAnnotationValue extends ElementValue implements Cloneable {
  /**
   * @aspect Java5PrettyPrint
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\PrettyPrint.jadd:81
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print(getAnnotation());
  }
  /**
   * @declaredat ASTNode:1
   */
  public ElementAnnotationValue() {
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
  public ElementAnnotationValue(Annotation p0) {
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
  public ElementAnnotationValue clone() throws CloneNotSupportedException {
    ElementAnnotationValue node = (ElementAnnotationValue) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:56
   */
  public ElementAnnotationValue copy() {
    try {
      ElementAnnotationValue node = (ElementAnnotationValue) clone();
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
  public ElementAnnotationValue fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:85
   */
  public ElementAnnotationValue treeCopyNoTransform() {
    ElementAnnotationValue tree = (ElementAnnotationValue) copy();
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
  public ElementAnnotationValue treeCopy() {
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
   * Replaces the Annotation child.
   * @param node The new node to replace the Annotation child.
   * @apilevel high-level
   */
  public void setAnnotation(Annotation node) {
    setChild(node, 0);
  }
  /**
   * Retrieves the Annotation child.
   * @return The current node used as the Annotation child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Annotation")
  public Annotation getAnnotation() {
    return (Annotation) getChild(0);
  }
  /**
   * Retrieves the Annotation child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Annotation child.
   * @apilevel low-level
   */
  public Annotation getAnnotationNoTransform() {
    return (Annotation) getChildNoTransform(0);
  }
  /**
   * @attribute syn
   * @aspect Annotations
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Annotations.jrag:558
   */
  @ASTNodeAnnotation.Attribute
  public boolean commensurateWithTypeDecl(TypeDecl type) {
    {
        return type() == type;
      }
  }
  @ASTNodeAnnotation.Attribute
  public TypeDecl type() {
    TypeDecl type_value = getAnnotation().type();

    return type_value;
  }
  /**
   * @attribute inh
   * @aspect Annotations
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Annotations.jrag:504
   */
  @ASTNodeAnnotation.Attribute
  public Annotation lookupAnnotation(TypeDecl typeDecl) {
    Annotation lookupAnnotation_TypeDecl_value = getParent().Define_lookupAnnotation(this, null, typeDecl);

    return lookupAnnotation_TypeDecl_value;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Annotations.jrag:96
   * @apilevel internal
   */
  public boolean Define_mayUseAnnotationTarget(ASTNode caller, ASTNode child, String name) {
    if (caller == getAnnotationNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Annotations.jrag:122
      return true;
    }
    else {
      return getParent().Define_mayUseAnnotationTarget(this, caller, name);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Annotations.jrag:504
   * @apilevel internal
   */
  public Annotation Define_lookupAnnotation(ASTNode caller, ASTNode child, TypeDecl typeDecl) {
    if (caller == getAnnotationNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Annotations.jrag:508
      return getAnnotation().type() == typeDecl ? getAnnotation() : lookupAnnotation(typeDecl);
    }
    else {
      return getParent().Define_lookupAnnotation(this, caller, typeDecl);
    }
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
