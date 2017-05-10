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
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\grammar\\MethodReference.ast:9
 * @production SyntheticTypeAccess : {@link Access};

 */
public class SyntheticTypeAccess extends Access implements Cloneable {
  /**
   * @aspect Synthetics
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\MethodReference.jrag:373
   */
  private TypeDecl type;
  /**
   * @aspect Synthetics
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\MethodReference.jrag:374
   */
  public SyntheticTypeAccess(TypeDecl type) {
    this.type = type;
  }
  /**
   * @declaredat ASTNode:1
   */
  public SyntheticTypeAccess() {
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
    type_reset();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:34
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:40
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:46
   */
  public SyntheticTypeAccess clone() throws CloneNotSupportedException {
    SyntheticTypeAccess node = (SyntheticTypeAccess) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:53
   */
  public SyntheticTypeAccess copy() {
    try {
      SyntheticTypeAccess node = (SyntheticTypeAccess) clone();
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
   * @declaredat ASTNode:72
   */
  @Deprecated
  public SyntheticTypeAccess fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:82
   */
  public SyntheticTypeAccess treeCopyNoTransform() {
    SyntheticTypeAccess tree = (SyntheticTypeAccess) copy();
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
   * @declaredat ASTNode:102
   */
  public SyntheticTypeAccess treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:109
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node);    
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
    type_value = type;
    if (isFinal && num == state().boundariesCrossed) {
      type_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return type_value;
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
