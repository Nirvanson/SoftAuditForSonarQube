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
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\grammar\\Java.ast:28
 * @production PackageAccess : {@link Access} ::= <span class="component">&lt;Package:String&gt;</span>;

 */
public class PackageAccess extends Access implements Cloneable {
  /**
   * @aspect Java4PrettyPrint
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\PrettyPrint.jadd:139
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print(getPackage());
  }
  /**
   * @aspect NameCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\NameCheck.jrag:82
   */
  public void nameCheck() {
    if (!hasPackage(packageName())) {
      errorf("package %s not found", packageName());
    }
  }
  /**
   * @aspect NodeConstructors
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\NodeConstructors.jrag:39
   */
  public PackageAccess(String name, int start, int end) {
    this(name);
    this.start = this.Packagestart = start;
    this.end = this.Packageend = end;
  }
  /**
   * @declaredat ASTNode:1
   */
  public PackageAccess() {
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
  public PackageAccess(String p0) {
    setPackage(p0);
  }
  /**
   * @declaredat ASTNode:15
   */
  public PackageAccess(beaver.Symbol p0) {
    setPackage(p0);
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
  public PackageAccess clone() throws CloneNotSupportedException {
    PackageAccess node = (PackageAccess) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:58
   */
  public PackageAccess copy() {
    try {
      PackageAccess node = (PackageAccess) clone();
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
  public PackageAccess fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:87
   */
  public PackageAccess treeCopyNoTransform() {
    PackageAccess tree = (PackageAccess) copy();
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
  public PackageAccess treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:114
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenString_Package == ((PackageAccess)node).tokenString_Package);    
  }
  /**
   * Replaces the lexeme Package.
   * @param value The new value for the lexeme Package.
   * @apilevel high-level
   */
  public void setPackage(String value) {
    tokenString_Package = value;
  }
  /**
   * @apilevel internal
   */
  protected String tokenString_Package;
  /**
   */
  public int Packagestart;
  /**
   */
  public int Packageend;
  /**
   * JastAdd-internal setter for lexeme Package using the Beaver parser.
   * @param symbol Symbol containing the new value for the lexeme Package
   * @apilevel internal
   */
  public void setPackage(beaver.Symbol symbol) {
    if (symbol.value != null && !(symbol.value instanceof String))
    throw new UnsupportedOperationException("setPackage is only valid for String lexemes");
    tokenString_Package = (String)symbol.value;
    Packagestart = symbol.getStart();
    Packageend = symbol.getEnd();
  }
  /**
   * Retrieves the value for the lexeme Package.
   * @return The value for the lexeme Package.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Token(name="Package")
  public String getPackage() {
    return tokenString_Package != null ? tokenString_Package : "";
  }
  @ASTNodeAnnotation.Attribute
  public boolean hasQualifiedPackage(String packageName) {
    boolean hasQualifiedPackage_String_value = hasPackage(packageName() + "." + packageName);

    return hasQualifiedPackage_String_value;
  }
  /**
   * @attribute syn
   * @aspect TypeScopePropagation
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:553
   */
  @ASTNodeAnnotation.Attribute
  public SimpleSet qualifiedLookupType(String name) {
    {
        SimpleSet c = SimpleSet.emptySet;
        TypeDecl typeDecl = lookupType(packageName(), name);
        if (!typeDecl.isUnknown()) {
          if (hostType() != null && typeDecl.accessibleFrom(hostType())) {
            c = c.add(typeDecl);
          } else if (hostType() == null && typeDecl.accessibleFromPackage(hostPackage())) {
            c = c.add(typeDecl);
          }
        }
        return c;
      }
  }
  @ASTNodeAnnotation.Attribute
  public SimpleSet qualifiedLookupVariable(String name) {
    SimpleSet qualifiedLookupVariable_String_value = SimpleSet.emptySet;

    return qualifiedLookupVariable_String_value;
  }
  @ASTNodeAnnotation.Attribute
  public String name() {
    String name_value = getPackage();

    return name_value;
  }
  /**
   * @attribute syn
   * @aspect Names
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\QualifiedNames.jrag:44
   */
  @ASTNodeAnnotation.Attribute
  public String packageName() {
    {
        StringBuilder sb = new StringBuilder();
        if (hasPrevExpr()) {
          sb.append(prevExpr().packageName());
          sb.append(".");
        }
        sb.append(getPackage());
        return sb.toString();
      }
  }
  @ASTNodeAnnotation.Attribute
  public boolean isPackageAccess() {
    boolean isPackageAccess_value = true;

    return isPackageAccess_value;
  }
  @ASTNodeAnnotation.Attribute
  public NameType predNameType() {
    NameType predNameType_value = NameType.PACKAGE_NAME;

    return predNameType_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isUnknown() {
    boolean isUnknown_value = !hasPackage(packageName());

    return isUnknown_value;
  }
  /**
   * @attribute inh
   * @aspect NameCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\NameCheck.jrag:308
   */
  @ASTNodeAnnotation.Attribute
  public boolean hasPackage(String packageName) {
    boolean hasPackage_String_value = getParent().Define_hasPackage(this, null, packageName);

    return hasPackage_String_value;
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
