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
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\grammar\\Java.ast:10
 * @production SingleTypeImportDecl : {@link ImportDecl};

 */
public class SingleTypeImportDecl extends ImportDecl implements Cloneable {
  /**
   * @aspect Java4PrettyPrint
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\PrettyPrint.jadd:255
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print("import ");
    out.print(getAccess());
    out.print(";");
    out.println();
  }
  /**
   * @aspect NameCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\NameCheck.jrag:43
   */
  public void nameCheck() {
    if (!getAccess().type().typeName().equals(typeName()) && !getAccess().type().isUnknown()) {
      errorf("Single-type import %s is not the canonical name of type %s",
          typeName(), getAccess().type().typeName());
    } else if (allImportedTypes(getAccess().type().name()).size() > 1) {
      errorf("%s is imported multiple times", getAccess().type().name());
    }
  }
  /**
   * @declaredat ASTNode:1
   */
  public SingleTypeImportDecl() {
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
  public SingleTypeImportDecl(Access p0) {
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
    importedTypes_String_reset();
    importedTypes_reset();
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
  public SingleTypeImportDecl clone() throws CloneNotSupportedException {
    SingleTypeImportDecl node = (SingleTypeImportDecl) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:58
   */
  public SingleTypeImportDecl copy() {
    try {
      SingleTypeImportDecl node = (SingleTypeImportDecl) clone();
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
   * @declaredat ASTNode:77
   */
  @Deprecated
  public SingleTypeImportDecl fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:87
   */
  public SingleTypeImportDecl treeCopyNoTransform() {
    SingleTypeImportDecl tree = (SingleTypeImportDecl) copy();
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
   * @declaredat ASTNode:107
   */
  public SingleTypeImportDecl treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:114
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node);    
  }
  /**
   * Replaces the Access child.
   * @param node The new node to replace the Access child.
   * @apilevel high-level
   */
  public void setAccess(Access node) {
    setChild(node, 0);
  }
  /**
   * Retrieves the Access child.
   * @return The current node used as the Access child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Access")
  public Access getAccess() {
    return (Access) getChild(0);
  }
  /**
   * Retrieves the Access child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Access child.
   * @apilevel low-level
   */
  public Access getAccessNoTransform() {
    return (Access) getChildNoTransform(0);
  }
  /**
   * @apilevel internal
   */
  protected java.util.Map importedTypes_String_values;
  /**
   * @apilevel internal
   */
  private void importedTypes_String_reset() {
    importedTypes_String_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public SimpleSet importedTypes(String name) {
    Object _parameters = name;
    if (importedTypes_String_values == null) importedTypes_String_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (importedTypes_String_values.containsKey(_parameters)) {
      return (SimpleSet) importedTypes_String_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    SimpleSet importedTypes_String_value = importedTypes_compute(name);
    if (isFinal && num == state().boundariesCrossed) {
      importedTypes_String_values.put(_parameters, importedTypes_String_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return importedTypes_String_value;
  }
  /**
   * @apilevel internal
   */
  private SimpleSet importedTypes_compute(String name) {
      SimpleSet set = SimpleSet.emptySet;
      TypeDecl type = getAccess().type();
      if (type.name().equals(name)) {
        set = set.add(type);
      }
      return set;
    }
  /**
   * @apilevel internal
   */
  protected boolean importedTypes_computed = false;
  /**
   * @apilevel internal
   */
  protected SimpleSet importedTypes_value;
  /**
   * @apilevel internal
   */
  private void importedTypes_reset() {
    importedTypes_computed = false;
    importedTypes_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public SimpleSet importedTypes() {
    ASTNode$State state = state();
    if (importedTypes_computed) {
      return importedTypes_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    importedTypes_value = SimpleSet.emptySet.add(getAccess().type());
    if (isFinal && num == state().boundariesCrossed) {
      importedTypes_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return importedTypes_value;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\SyntacticClassification.jrag:36
   * @apilevel internal
   */
  public NameType Define_nameType(ASTNode caller, ASTNode child) {
    if (caller == getAccessNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\SyntacticClassification.jrag:99
      return NameType.TYPE_NAME;
    }
    else {
      return getParent().Define_nameType(this, caller);
    }
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
