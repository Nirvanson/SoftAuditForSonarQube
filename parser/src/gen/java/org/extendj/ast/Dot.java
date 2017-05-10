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
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\grammar\\Java.ast:16
 * @production Dot : {@link AbstractDot};

 */
public class Dot extends AbstractDot implements Cloneable {
  /**
   * @aspect QualifiedNames
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:125
   */
  public Dot lastDot() {
    Dot node = this;
    while (node.getRightNoTransform() instanceof Dot) {
      node = (Dot) node.getRightNoTransform();
    }
    return node;
  }
  /**
   * @aspect QualifiedNames
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:143
   */
  public Dot qualifiesAccess(Access access) {
    Dot lastDot = lastDot();
    Expr l = lastDot.getRightNoTransform();
    Dot dot = new Dot(lastDot.getRightNoTransform(), access);
    dot.setStart(l.getStart());
    dot.setEnd(access.getEnd());
    lastDot.setRight(dot);
    return this;
  }
  /**
   * @aspect QualifiedNames
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:154
   */
  private Access qualifyTailWith(Access expr) {
    if (getRight/*NoTransform*/() instanceof AbstractDot) {
      AbstractDot dot = (AbstractDot) getRight/*NoTransform*/();
      return expr.qualifiesAccess(dot.getRight/*NoTransform*/());
    }
    return expr;
  }
  /**
   * @aspect QualifiedNames
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:173
   */
  public Access extractLast() {
    return lastDot().getRightNoTransform();
  }
  /**
   * @aspect QualifiedNames
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:177
   */
  public void replaceLast(Access access) {
    lastDot().setRight(access);
  }
  /**
   * @declaredat ASTNode:1
   */
  public Dot() {
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
  public Dot(Expr p0, Access p1) {
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
    return true;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:32
   */
  public void flushAttrCache() {
    super.flushAttrCache();
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
  public Dot clone() throws CloneNotSupportedException {
    Dot node = (Dot) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:57
   */
  public Dot copy() {
    try {
      Dot node = (Dot) clone();
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
  public Dot fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:86
   */
  public Dot treeCopyNoTransform() {
    Dot tree = (Dot) copy();
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
  public Dot treeCopy() {
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
   * Replaces the Left child.
   * @param node The new node to replace the Left child.
   * @apilevel high-level
   */
  public void setLeft(Expr node) {
    setChild(node, 0);
  }
  /**
   * Retrieves the Left child.
   * @return The current node used as the Left child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Left")
  public Expr getLeft() {
    return (Expr) getChild(0);
  }
  /**
   * Retrieves the Left child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Left child.
   * @apilevel low-level
   */
  public Expr getLeftNoTransform() {
    return (Expr) getChildNoTransform(0);
  }
  /**
   * Replaces the Right child.
   * @param node The new node to replace the Right child.
   * @apilevel high-level
   */
  public void setRight(Access node) {
    setChild(node, 1);
  }
  /**
   * Retrieves the Right child.
   * @return The current node used as the Right child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Right")
  public Access getRight() {
    return (Access) getChild(1);
  }
  /**
   * Retrieves the Right child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Right child.
   * @apilevel low-level
   */
  public Access getRightNoTransform() {
    return (Access) getChildNoTransform(1);
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    // Declared at @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:243
    if (leftSide().isPackageAccess() && rightSide().isPackageAccess()) {
      return rewriteRule0();
    }
    // Declared at @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:253
    if (leftSide().isPackageAccess() && !((Access) leftSide()).hasPrevExpr() && rightSide() instanceof TypeAccess) {
      return rewriteRule1();
    }
    return super.rewriteTo();
  }
  /**
   * @declaredat @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:243
   * @apilevel internal
   */
  private Access rewriteRule0() {
{
      PackageAccess left = (PackageAccess) leftSide();
      PackageAccess right = (PackageAccess) rightSide();
      left.setPackage(left.getPackage() + "." + right.getPackage());
      left.setEnd(right.end());
      return qualifyTailWith(left);
    }  }
  /**
   * @declaredat @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:253
   * @apilevel internal
   */
  private Access rewriteRule1() {
{
      PackageAccess left = (PackageAccess) leftSide();
      TypeAccess right = (TypeAccess) rightSide();
      right.setPackage(left.getPackage());
      right.setStart(left.start());
      return qualifyTailWith(right);
    }  }
}
