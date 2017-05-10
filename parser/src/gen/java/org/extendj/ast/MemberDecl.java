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
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\grammar\\Java.ast:74
 * @production MemberDecl : {@link BodyDecl};

 */
public abstract class MemberDecl extends BodyDecl implements Cloneable {
  /**
   * @aspect Modifiers
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\Modifiers.jrag:232
   */
  public void checkModifiers() {
    if (!isSynthetic()) {
      super.checkModifiers();
      if (isStatic() && hostType().isInnerClass() && !isConstant()) {
        error("*** Inner classes may not declare static members, unless they are compile-time constant fields");
      }
    }
  }
  /**
   * @declaredat ASTNode:1
   */
  public MemberDecl() {
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
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:33
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:39
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:45
   */
  public MemberDecl clone() throws CloneNotSupportedException {
    MemberDecl node = (MemberDecl) super.clone();
    return node;
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @deprecated Please use treeCopy or treeCopyNoTransform instead
   * @declaredat ASTNode:56
   */
  @Deprecated
  public abstract MemberDecl fullCopy();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:64
   */
  public abstract MemberDecl treeCopyNoTransform();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The subtree of this node is traversed to trigger rewrites before copy.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:72
   */
  public abstract MemberDecl treeCopy();
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\Modifiers.jrag:263
   */
  @ASTNodeAnnotation.Attribute
  public abstract boolean isStatic();
  @ASTNodeAnnotation.Attribute
  public boolean isConstant() {
    boolean isConstant_value = false;

    return isConstant_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isSynthetic() {
    boolean isSynthetic_value = false;

    return isSynthetic_value;
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
