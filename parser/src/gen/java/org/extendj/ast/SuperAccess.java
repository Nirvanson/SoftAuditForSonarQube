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
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\grammar\\Java.ast:27
 * @production SuperAccess : {@link Access} ::= <span class="component">&lt;ID:String&gt;</span>;

 */
public class SuperAccess extends Access implements Cloneable {
  /**
   * @aspect Java4PrettyPrint
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\PrettyPrint.jadd:162
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print("super");
  }
  /**
   * @declaredat ASTNode:1
   */
  public SuperAccess() {
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
  public SuperAccess(String p0) {
    setID(p0);
  }
  /**
   * @declaredat ASTNode:15
   */
  public SuperAccess(beaver.Symbol p0) {
    setID(p0);
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
    decl_reset();
    type_reset();
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
  public SuperAccess clone() throws CloneNotSupportedException {
    SuperAccess node = (SuperAccess) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:60
   */
  public SuperAccess copy() {
    try {
      SuperAccess node = (SuperAccess) clone();
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
   * @declaredat ASTNode:79
   */
  @Deprecated
  public SuperAccess fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:89
   */
  public SuperAccess treeCopyNoTransform() {
    SuperAccess tree = (SuperAccess) copy();
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
   * @declaredat ASTNode:109
   */
  public SuperAccess treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:116
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenString_ID == ((SuperAccess)node).tokenString_ID);    
  }
  /**
   * Replaces the lexeme ID.
   * @param value The new value for the lexeme ID.
   * @apilevel high-level
   */
  public void setID(String value) {
    tokenString_ID = value;
  }
  /**
   * @apilevel internal
   */
  protected String tokenString_ID;
  /**
   */
  public int IDstart;
  /**
   */
  public int IDend;
  /**
   * JastAdd-internal setter for lexeme ID using the Beaver parser.
   * @param symbol Symbol containing the new value for the lexeme ID
   * @apilevel internal
   */
  public void setID(beaver.Symbol symbol) {
    if (symbol.value != null && !(symbol.value instanceof String))
    throw new UnsupportedOperationException("setID is only valid for String lexemes");
    tokenString_ID = (String)symbol.value;
    IDstart = symbol.getStart();
    IDend = symbol.getEnd();
  }
  /**
   * Retrieves the value for the lexeme ID.
   * @return The value for the lexeme ID.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Token(name="ID")
  public String getID() {
    return tokenString_ID != null ? tokenString_ID : "";
  }
  /**
   * @aspect Java8NameCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\NameCheck.jrag:371
   */
   
  public void nameCheck() {
    if (isQualified()) {
      if (decl().isInterfaceDecl()) {
        InterfaceDecl decl = (InterfaceDecl) decl();
        if (hostType().isClassDecl()) {
          ClassDecl hostDecl = (ClassDecl) hostType();
          InterfaceDecl found = null;
          for (int i = 0; i < hostDecl.getNumImplements(); i++) {
            if (hostDecl.getImplements(i).type() == decl) {
              found = (InterfaceDecl) hostDecl.getImplements(i).type();
              break;
            }
          }
          if (found == null) {
            // 15.12.1 - fourth bullet
            errorf("Type %s is not a direct superinterface of %s",
                decl().typeName(), hostType().typeName());
            return;
          }
          InterfaceDecl foundRedundant = null;
          for (int i = 0; i < hostDecl.getNumImplements(); i++) {
            if (hostDecl.getImplements(i).type() != found && hostDecl.getImplements(i).type().strictSubtype(found)) {
              foundRedundant = (InterfaceDecl) hostDecl.getImplements(i).type();
              break;
            }
          }
          if (foundRedundant != null) {
            // 15.12.1 - fourth bullet
            errorf("Type %s cannot be used as qualifier, it is extended by implemented interface %s and is redundant",
                decl().typeName(), foundRedundant.typeName());
            return;
          }
          if (hasNextAccess() && nextAccess() instanceof MethodAccess) {
            MethodAccess methodAccess = (MethodAccess) nextAccess();
            if (hostDecl.hasOverridingMethodInSuper(methodAccess.decl())) {
              errorf("Cannot make a super reference to method %s,"
                  + " there is a more specific override",
                  methodAccess.decl().fullSignature());
            }
          }
        } else if (hostType().isInterfaceDecl()) {
          InterfaceDecl hostDecl = (InterfaceDecl) hostType();
          InterfaceDecl found = null;
          for (int i = 0; i < hostDecl.getNumSuperInterface(); i++) {
            if (hostDecl.getSuperInterface(i).type() == decl) {
              found = (InterfaceDecl) hostDecl.getSuperInterface(i).type();
              break;
            }
          }
          if (found == null) {
            // 15.12.1 - fourth bullet
            errorf("Type %s is not a direct superinterface of %s",
                decl().typeName(), hostType().typeName());
            return;
          }
          InterfaceDecl foundRedundant = null;
          for (int i = 0; i < hostDecl.getNumSuperInterface(); i++) {
            if (hostDecl.getSuperInterface(i).type() != found && hostDecl.getSuperInterface(i).type().strictSubtype(found)) {
              foundRedundant = (InterfaceDecl) hostDecl.getSuperInterface(i).type();
              break;
            }
          }
          if (foundRedundant != null) {
            // 15.12.1 - fourth bullet
            errorf("Type %s cannot be used as qualifier, it is extended by"
                + " implemented interface %s and is redundant",
                decl().typeName(), foundRedundant.typeName());
            return;
          }
          if (hasNextAccess() && nextAccess() instanceof MethodAccess) {
            MethodAccess methodAccess = (MethodAccess) nextAccess();
            if (hostDecl.hasOverridingMethodInSuper(methodAccess.decl())) {
              errorf("Cannot make a super reference to method %s,"
                  + " there is a more specific override",
                  methodAccess.decl().fullSignature());
            }
          }
        } else {
          error("Illegal context for super access");
        }

        if (nextAccess() instanceof MethodAccess) {
          if (((MethodAccess) nextAccess()).decl().isStatic()) {
            error("Cannot reference static interface methods with super");
          }
        }

        if (!hostType().strictSubtype(decl())) {
          errorf("Type %s is not a superinterface for %s",
              decl().typeName(), hostType().typeName());
        }
      } else if (!hostType().isInnerTypeOf(decl()) && hostType() != decl()) {
        error("qualified super must name an enclosing type");
      }
      if (inStaticContext()) {
        error("*** Qualified super may not occur in static context");
      }
    }
    // 8.8.5.1
    // JLSv7 8.8.7.1
    TypeDecl constructorHostType = enclosingExplicitConstructorHostType();
    if (constructorHostType != null && (constructorHostType == decl())) {
      error("super may not be accessed in an explicit constructor invocation");
    }
    // 8.4.3.2
    if (inStaticContext()) {
      error("super may not be accessed in a static context");
    }
  }
  /**
   * @aspect TypeScopePropagation
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:328
   */
  private TypeDecl refined_TypeScopePropagation_SuperAccess_decl()
{ return isQualified() ? qualifier().type() : hostType(); }
  @ASTNodeAnnotation.Attribute
  public SimpleSet decls() {
    SimpleSet decls_value = SimpleSet.emptySet;

    return decls_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean decl_computed = false;
  /**
   * @apilevel internal
   */
  protected TypeDecl decl_value;
  /**
   * @apilevel internal
   */
  private void decl_reset() {
    decl_computed = false;
    decl_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public TypeDecl decl() {
    ASTNode$State state = state();
    if (decl_computed) {
      return decl_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    decl_value = decl_compute();
    if (isFinal && num == state().boundariesCrossed) {
      decl_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return decl_value;
  }
  /**
   * @apilevel internal
   */
  private TypeDecl decl_compute() {
      TypeDecl typeDecl;
      if (isQualified()) {
        typeDecl = qualifier().type();
      } else {
        typeDecl = hostType();
        while (typeDecl instanceof LambdaAnonymousDecl) {
          typeDecl = typeDecl.enclosingType();
        }
      }
  
      if (typeDecl instanceof ParTypeDecl) {
        typeDecl = ((ParTypeDecl) typeDecl).genericDecl();
      }
      return typeDecl;
    }
  @ASTNodeAnnotation.Attribute
  public boolean isSuperAccess() {
    boolean isSuperAccess_value = true;

    return isSuperAccess_value;
  }
  @ASTNodeAnnotation.Attribute
  public NameType predNameType() {
    NameType predNameType_value = NameType.TYPE_NAME;

    return predNameType_value;
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
    type_value = type_compute();
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
  private TypeDecl type_compute() {
      TypeDecl typeDecl = decl();
      if (typeDecl.isInterfaceDecl()) {
        if (isQualified() && qualifier().type() == typeDecl) {
          return typeDecl;
        }
      }
      if (!typeDecl.isClassDecl()) {
        return unknownType();
      }
      ClassDecl classDecl = (ClassDecl) typeDecl;
      if (!classDecl.hasSuperclass()) {
        return unknownType();
      }
      return classDecl.superclass();
    }
  /**
   * @attribute inh
   * @aspect TypeHierarchyCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:162
   */
  @ASTNodeAnnotation.Attribute
  public boolean inExplicitConstructorInvocation() {
    boolean inExplicitConstructorInvocation_value = getParent().Define_inExplicitConstructorInvocation(this, null);

    return inExplicitConstructorInvocation_value;
  }
  /**
   * @attribute inh
   * @aspect TypeHierarchyCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:173
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl enclosingExplicitConstructorHostType() {
    TypeDecl enclosingExplicitConstructorHostType_value = getParent().Define_enclosingExplicitConstructorHostType(this, null);

    return enclosingExplicitConstructorHostType_value;
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
