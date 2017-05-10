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
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\grammar\\Generics.ast:16
 * @production ParTypeAccess : {@link Access} ::= <span class="component">TypeAccess:{@link Access}</span> <span class="component">TypeArgument:{@link Access}*</span>;

 */
public class ParTypeAccess extends Access implements Cloneable {
  /**
   * @aspect Java5PrettyPrint
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\PrettyPrint.jadd:102
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print(getTypeAccess());
    out.print("<");
    out.join(getTypeArgumentList(), new PrettyPrinter.Joiner() {
      @Override
      public void printSeparator(PrettyPrinter out) {
        out.print(", ");
      }
    });
    out.print(">");
  }
  /**
   * @aspect GenericsTypeAnalysis
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:415
   */
  public boolean isRaw() {
    return false;
  }
  /**
   * @aspect GenericsTypeCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:668
   */
  public void typeCheck() {
    super.typeCheck();
    if (!genericDecl().isUnknown()) {
      TypeDecl type = type();
      if (!genericDecl().isGenericType()) {
        errorf("%s is not a generic type but used as one in %s",
            genericDecl().typeName(), this.prettyPrint());
      } else if (!type.isRawType() && type.isNestedType() && type.enclosingType().isRawType()) {
        error("Can not access a member type of a raw type as a parameterized type");
      } else {
        GenericTypeDecl decl = (GenericTypeDecl) genericDecl();
        GenericTypeDecl original = (GenericTypeDecl) decl.original();
        if (original.getNumTypeParameter() != getNumTypeArgument()) {
          errorf("%s takes %d type parameters, not %d as used in %s",
              decl.typeName(), original.getNumTypeParameter(), getNumTypeArgument(),
              this.prettyPrint());
        } else {
          for (int i = 0; i < getNumTypeArgument(); i++) {
            if (!getTypeArgument(i).type().withinBounds(original.getTypeParameter(i),
                (Parameterization) type())) {
              errorf("type argument %s is of type %s which is not within"
                  + " the bounds of type parameter %s (%s)",
                  i, getTypeArgument(i).type().typeName(), original.getTypeParameter(i).typeName(),
                  original.getTypeParameter(i).prettyPrint());
            }
          }
        }
      }
    }
  }
  /**
   * @aspect FunctionalInterface
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\FunctionalInterface.jrag:223
   */
  public boolean sameType(ParTypeAccess p) {
    TypeAccess ta1 = (TypeAccess) getTypeAccess();
    TypeAccess ta2 = (TypeAccess) p.getTypeAccess();
    if (!ta1.sameType(ta2)) {
      return false;
    }

    if (getNumTypeArgument() != p.getNumTypeArgument()) {
      return false;
    }

    for (int i = 0; i < getNumTypeArgument(); i++) {
      Access a1 = getTypeArgument(i);
      Access a2 = p.getTypeArgument(i);
      if (!a1.sameType(a2)) {
        return false;
      }
    }

    return true;
  }
  /**
   * @declaredat ASTNode:1
   */
  public ParTypeAccess() {
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
    setChild(new List(), 1);
  }
  /**
   * @declaredat ASTNode:14
   */
  public ParTypeAccess(Access p0, List<Access> p1) {
    setChild(p0, 0);
    setChild(p1, 1);
  }
  /**
   * @apilevel low-level
   * @declaredat ASTNode:21
   */
  protected int numChildren() {
    return 2;
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
    type_reset();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:40
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:46
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:52
   */
  public ParTypeAccess clone() throws CloneNotSupportedException {
    ParTypeAccess node = (ParTypeAccess) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:59
   */
  public ParTypeAccess copy() {
    try {
      ParTypeAccess node = (ParTypeAccess) clone();
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
   * @declaredat ASTNode:78
   */
  @Deprecated
  public ParTypeAccess fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:88
   */
  public ParTypeAccess treeCopyNoTransform() {
    ParTypeAccess tree = (ParTypeAccess) copy();
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
   * @declaredat ASTNode:108
   */
  public ParTypeAccess treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:115
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node);    
  }
  /**
   * Replaces the TypeAccess child.
   * @param node The new node to replace the TypeAccess child.
   * @apilevel high-level
   */
  public void setTypeAccess(Access node) {
    setChild(node, 0);
  }
  /**
   * Retrieves the TypeAccess child.
   * @return The current node used as the TypeAccess child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="TypeAccess")
  public Access getTypeAccess() {
    return (Access) getChild(0);
  }
  /**
   * Retrieves the TypeAccess child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the TypeAccess child.
   * @apilevel low-level
   */
  public Access getTypeAccessNoTransform() {
    return (Access) getChildNoTransform(0);
  }
  /**
   * Replaces the TypeArgument list.
   * @param list The new list node to be used as the TypeArgument list.
   * @apilevel high-level
   */
  public void setTypeArgumentList(List<Access> list) {
    setChild(list, 1);
  }
  /**
   * Retrieves the number of children in the TypeArgument list.
   * @return Number of children in the TypeArgument list.
   * @apilevel high-level
   */
  public int getNumTypeArgument() {
    return getTypeArgumentList().getNumChild();
  }
  /**
   * Retrieves the number of children in the TypeArgument list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the TypeArgument list.
   * @apilevel low-level
   */
  public int getNumTypeArgumentNoTransform() {
    return getTypeArgumentListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the TypeArgument list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the TypeArgument list.
   * @apilevel high-level
   */
  public Access getTypeArgument(int i) {
    return (Access) getTypeArgumentList().getChild(i);
  }
  /**
   * Check whether the TypeArgument list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasTypeArgument() {
    return getTypeArgumentList().getNumChild() != 0;
  }
  /**
   * Append an element to the TypeArgument list.
   * @param node The element to append to the TypeArgument list.
   * @apilevel high-level
   */
  public void addTypeArgument(Access node) {
    List<Access> list = (parent == null) ? getTypeArgumentListNoTransform() : getTypeArgumentList();
    list.addChild(node);
  }
  /**
   * @apilevel low-level
   */
  public void addTypeArgumentNoTransform(Access node) {
    List<Access> list = getTypeArgumentListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the TypeArgument list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setTypeArgument(Access node, int i) {
    List<Access> list = getTypeArgumentList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the TypeArgument list.
   * @return The node representing the TypeArgument list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="TypeArgument")
  public List<Access> getTypeArgumentList() {
    List<Access> list = (List<Access>) getChild(1);
    return list;
  }
  /**
   * Retrieves the TypeArgument list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the TypeArgument list.
   * @apilevel low-level
   */
  public List<Access> getTypeArgumentListNoTransform() {
    return (List<Access>) getChildNoTransform(1);
  }
  /**
   * Retrieves the TypeArgument list.
   * @return The node representing the TypeArgument list.
   * @apilevel high-level
   */
  public List<Access> getTypeArguments() {
    return getTypeArgumentList();
  }
  /**
   * Retrieves the TypeArgument list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the TypeArgument list.
   * @apilevel low-level
   */
  public List<Access> getTypeArgumentsNoTransform() {
    return getTypeArgumentListNoTransform();
  }
  @ASTNodeAnnotation.Attribute
  public Expr unqualifiedScope() {
    Expr unqualifiedScope_value = getParent() instanceof Access
          ? ((Access) getParent()).unqualifiedScope()
          : super.unqualifiedScope();

    return unqualifiedScope_value;
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
      TypeDecl typeDecl = genericDecl();
      if (typeDecl instanceof GenericTypeDecl) {
        // Use signature in lookup for types that are used in extends and implements clauses.
        if (unqualifiedScope().inExtendsOrImplements()) {
          return ((GenericTypeDecl) typeDecl).lookupParTypeDecl(this);
        }
        ArrayList<TypeDecl> args = new ArrayList<TypeDecl>();
        for (int i = 0; i < getNumTypeArgument(); i++) {
          args.add(getTypeArgument(i).type());
        }
        return ((GenericTypeDecl) typeDecl).lookupParTypeDecl(args);
      }
      return typeDecl;
    }
  @ASTNodeAnnotation.Attribute
  public TypeDecl genericDecl() {
    TypeDecl genericDecl_value = getTypeAccess().type();

    return genericDecl_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isTypeAccess() {
    boolean isTypeAccess_value = true;

    return isTypeAccess_value;
  }
  /**
   * Builds a copy of this Access node where all occurrences
   * of type variables in the original type parameter list have been replaced
   * by the substitution type parameters.
   * 
   * @return the substituted Access node
   * @attribute syn
   * @aspect Diamond
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Diamond.jrag:361
   */
  @ASTNodeAnnotation.Attribute
  public Access substituted(Collection<TypeVariable> original, List<TypeVariable> substitution) {
    {
        List<Access> substArgs = new List<Access>();
        for (Access arg : getTypeArgumentList()) {
          substArgs.add(arg.substituted(original, substitution));
        }
        return new ParTypeAccess(getTypeAccess().substituted(original, substitution), substArgs);
      }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\GenericMethods.jrag:197
   * @apilevel internal
   */
  public SimpleSet Define_lookupType(ASTNode caller, ASTNode child, String name) {
    if (caller == getTypeArgumentListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:332
      int childIndex = caller.getIndexOfChild(child);
      return unqualifiedScope().lookupType(name);
    }
    else {
      return getParent().Define_lookupType(this, caller, name);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\SyntacticClassification.jrag:36
   * @apilevel internal
   */
  public NameType Define_nameType(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return NameType.TYPE_NAME;
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
