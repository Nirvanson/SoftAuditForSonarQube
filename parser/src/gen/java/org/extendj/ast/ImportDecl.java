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
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\grammar\\Java.ast:9
 * @production ImportDecl : {@link ASTNode} ::= <span class="component">{@link Access}</span>;

 */
public abstract class ImportDecl extends ASTNode<ASTNode> implements Cloneable {
  /**
   * @declaredat ASTNode:1
   */
  public ImportDecl() {
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
  public ImportDecl(Access p0) {
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
    importedFields_String_reset();
    importedMethods_String_reset();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:41
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:47
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:53
   */
  public ImportDecl clone() throws CloneNotSupportedException {
    ImportDecl node = (ImportDecl) super.clone();
    return node;
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @deprecated Please use treeCopy or treeCopyNoTransform instead
   * @declaredat ASTNode:64
   */
  @Deprecated
  public abstract ImportDecl fullCopy();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:72
   */
  public abstract ImportDecl treeCopyNoTransform();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The subtree of this node is traversed to trigger rewrites before copy.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:80
   */
  public abstract ImportDecl treeCopy();
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
    SimpleSet importedTypes_String_value = SimpleSet.emptySet;
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
    importedTypes_value = SimpleSet.emptySet;
    if (isFinal && num == state().boundariesCrossed) {
      importedTypes_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return importedTypes_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isOnDemand() {
    boolean isOnDemand_value = false;

    return isOnDemand_value;
  }
  /**
   * @attribute syn
   * @aspect Names
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\QualifiedNames.jrag:60
   */
  @ASTNodeAnnotation.Attribute
  public String typeName() {
    {
        Access a = getAccess().lastAccess();
        String name = a.isTypeAccess() ? ((TypeAccess) a).nameWithPackage() : "";
        while (a.hasPrevExpr() && a.prevExpr() instanceof Access) {
          Access pred = (Access) a.prevExpr();
          if (pred.isTypeAccess()) {
            name = ((TypeAccess) pred).nameWithPackage() + "." + name;
          }
          a = pred;
        }
        return name;
      }
  }
  /**
   * @apilevel internal
   */
  protected java.util.Map importedFields_String_values;
  /**
   * @apilevel internal
   */
  private void importedFields_String_reset() {
    importedFields_String_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public SimpleSet importedFields(String name) {
    Object _parameters = name;
    if (importedFields_String_values == null) importedFields_String_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (importedFields_String_values.containsKey(_parameters)) {
      return (SimpleSet) importedFields_String_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    SimpleSet importedFields_String_value = SimpleSet.emptySet;
    if (isFinal && num == state().boundariesCrossed) {
      importedFields_String_values.put(_parameters, importedFields_String_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return importedFields_String_value;
  }
  /**
   * @apilevel internal
   */
  protected java.util.Map importedMethods_String_values;
  /**
   * @apilevel internal
   */
  private void importedMethods_String_reset() {
    importedMethods_String_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public Collection importedMethods(String name) {
    Object _parameters = name;
    if (importedMethods_String_values == null) importedMethods_String_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (importedMethods_String_values.containsKey(_parameters)) {
      return (Collection) importedMethods_String_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    Collection importedMethods_String_value = Collections.EMPTY_LIST;
    if (isFinal && num == state().boundariesCrossed) {
      importedMethods_String_values.put(_parameters, importedMethods_String_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return importedMethods_String_value;
  }
  /**
   * @attribute inh
   * @aspect TypeScopePropagation
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:464
   */
  @ASTNodeAnnotation.Attribute
  public String packageName() {
    String packageName_value = getParent().Define_packageName(this, null);

    return packageName_value;
  }
  /**
   * @attribute inh
   * @aspect NameCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\NameCheck.jrag:52
   */
  @ASTNodeAnnotation.Attribute
  public SimpleSet allImportedTypes(String name) {
    SimpleSet allImportedTypes_String_value = getParent().Define_allImportedTypes(this, null, name);

    return allImportedTypes_String_value;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:37
   * @apilevel internal
   */
  public boolean Define_isDest(ASTNode caller, ASTNode child) {
    if (caller == getAccessNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:45
      return false;
    }
    else {
      return getParent().Define_isDest(this, caller);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:47
   * @apilevel internal
   */
  public boolean Define_isSource(ASTNode caller, ASTNode child) {
    if (caller == getAccessNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:54
      return true;
    }
    else {
      return getParent().Define_isSource(this, caller);
    }
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
