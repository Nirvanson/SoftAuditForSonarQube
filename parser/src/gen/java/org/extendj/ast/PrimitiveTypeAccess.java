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
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\grammar\\Java.ast:23
 * @production PrimitiveTypeAccess : {@link TypeAccess} ::= <span class="component">&lt;Package:String&gt;</span> <span class="component">&lt;ID:String&gt;</span> <span class="component">&lt;Name:String&gt;</span>;

 */
public class PrimitiveTypeAccess extends TypeAccess implements Cloneable {
  /**
   * @declaredat ASTNode:1
   */
  public PrimitiveTypeAccess() {
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
   * @declaredat ASTNode:12
   */
  public PrimitiveTypeAccess(String p0) {
    setName(p0);
  }
  /**
   * @declaredat ASTNode:15
   */
  public PrimitiveTypeAccess(beaver.Symbol p0) {
    setName(p0);
  }
  /**
   * @apilevel low-level
   * @declaredat ASTNode:21
   */
  protected int numChildren() {
    return 0;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:27
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:33
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    decls_reset();
    getPackage_reset();
    getID_reset();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:42
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:48
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:54
   */
  public PrimitiveTypeAccess clone() throws CloneNotSupportedException {
    PrimitiveTypeAccess node = (PrimitiveTypeAccess) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:61
   */
  public PrimitiveTypeAccess copy() {
    try {
      PrimitiveTypeAccess node = (PrimitiveTypeAccess) clone();
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
   * @declaredat ASTNode:80
   */
  @Deprecated
  public PrimitiveTypeAccess fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:90
   */
  public PrimitiveTypeAccess treeCopyNoTransform() {
    PrimitiveTypeAccess tree = (PrimitiveTypeAccess) copy();
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
   * @declaredat ASTNode:110
   */
  public PrimitiveTypeAccess treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:117
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenString_Name == ((PrimitiveTypeAccess)node).tokenString_Name);    
  }
  /**
   * Replaces the lexeme Name.
   * @param value The new value for the lexeme Name.
   * @apilevel high-level
   */
  public void setName(String value) {
    tokenString_Name = value;
  }
  /**
   * @apilevel internal
   */
  protected String tokenString_Name;
  /**
   */
  public int Namestart;
  /**
   */
  public int Nameend;
  /**
   * JastAdd-internal setter for lexeme Name using the Beaver parser.
   * @param symbol Symbol containing the new value for the lexeme Name
   * @apilevel internal
   */
  public void setName(beaver.Symbol symbol) {
    if (symbol.value != null && !(symbol.value instanceof String))
    throw new UnsupportedOperationException("setName is only valid for String lexemes");
    tokenString_Name = (String)symbol.value;
    Namestart = symbol.getStart();
    Nameend = symbol.getEnd();
  }
  /**
   * Retrieves the value for the lexeme Name.
   * @return The value for the lexeme Name.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Token(name="Name")
  public String getName() {
    return tokenString_Name != null ? tokenString_Name : "";
  }
  /**
   * This method should not be called. This method throws an exception due to
   * the corresponding child being an NTA shadowing a non-NTA child.
   * @param node
   * @apilevel internal
   */
  public void setPackage(String node) {
    throw new Error("Can not replace NTA child Package in PrimitiveTypeAccess!");
  }
  /**
   * @apilevel internal
   */
  protected String tokenString_Package;
  /**
   * This method should not be called. This method throws an exception due to
   * the corresponding child being an NTA shadowing a non-NTA child.
   * @param node
   * @apilevel internal
   */
  public void setID(String node) {
    throw new Error("Can not replace NTA child ID in PrimitiveTypeAccess!");
  }
  /**
   * @apilevel internal
   */
  protected String tokenString_ID;
  /**
   * @apilevel internal
   */
  protected boolean decls_computed = false;
  /**
   * @apilevel internal
   */
  protected SimpleSet decls_value;
  /**
   * @apilevel internal
   */
  private void decls_reset() {
    decls_computed = false;
    decls_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public SimpleSet decls() {
    ASTNode$State state = state();
    if (decls_computed) {
      return decls_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    decls_value = lookupType(PRIMITIVE_PACKAGE_NAME, name());
    if (isFinal && num == state().boundariesCrossed) {
      decls_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return decls_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean getPackage_computed = false;
  /**
   * @apilevel internal
   */
  protected String getPackage_value;
  /**
   * @apilevel internal
   */
  private void getPackage_reset() {
    getPackage_computed = false;
    getPackage_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public String getPackage() {
    ASTNode$State state = state();
    if (getPackage_computed) {
      return getPackage_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    getPackage_value = PRIMITIVE_PACKAGE_NAME;
    if (isFinal && num == state().boundariesCrossed) {
      getPackage_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return getPackage_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean getID_computed = false;
  /**
   * @apilevel internal
   */
  protected String getID_value;
  /**
   * @apilevel internal
   */
  private void getID_reset() {
    getID_computed = false;
    getID_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public String getID() {
    ASTNode$State state = state();
    if (getID_computed) {
      return getID_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    getID_value = getName();
    if (isFinal && num == state().boundariesCrossed) {
      getID_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return getID_value;
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
