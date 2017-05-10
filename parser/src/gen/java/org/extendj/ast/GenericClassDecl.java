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
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\grammar\\Generics.ast:2
 * @production GenericClassDecl : {@link ClassDecl} ::= <span class="component">{@link Modifiers}</span> <span class="component">&lt;ID:String&gt;</span> <span class="component">[SuperClass:{@link Access}]</span> <span class="component">Implements:{@link Access}*</span> <span class="component">{@link BodyDecl}*</span> <span class="component">TypeParameter:{@link TypeVariable}*</span>;

 */
public class GenericClassDecl extends ClassDecl implements Cloneable, GenericTypeDecl {
  /**
   * @aspect Java5PrettyPrint
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\PrettyPrint.jadd:333
   */
  public void prettyPrint(PrettyPrinter out) {
    if (hasDocComment()) {
      out.print(docComment());
    }
    if (!out.isNewLine()) {
      out.println();
    }
    out.print(getModifiers());
    out.print("class ");
    out.print(getID());
    out.print("<");
    out.join(getTypeParameterList(), new PrettyPrinter.Joiner() {
      @Override
      public void printSeparator(PrettyPrinter out) {
        out.print(", ");
      }
    });
    out.print(">");
    if (hasSuperClass()) {
      out.print(" extends ");
      out.print(getSuperClass());
    }
    if (hasImplements()) {
      out.print(" implements ");
      out.join(getImplementss(), new PrettyPrinter.Joiner() {
        @Override
        public void printSeparator(PrettyPrinter out) {
          out.print(", ");
        }
      });
    }
    out.print(" {");
    out.println();
    out.indent(1);
    out.join(getBodyDecls(), new PrettyPrinter.Joiner() {
      @Override
      public void printSeparator(PrettyPrinter out) {
        out.println();
        out.println();
      }
    });
    if (!out.isNewLine()) {
      out.println();
    }
    out.print("}");
  }
  /**
   * @aspect GenericsTypeCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:640
   */
  public void typeCheck() {
    super.typeCheck();
    if (instanceOf(typeThrowable())) {
      errorf("generic class %s may not directly or indirectly inherit java.lang.Throwable",
          typeName());
    }
  }
  /**
   * @aspect LookupParTypeDecl
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:1434
   */
  public ClassDecl substitutedClassDecl(Parameterization parTypeDecl) {
    return new GenericClassDeclSubstituted(
      (Modifiers) getModifiers().treeCopyNoTransform(),
      getID(),
      hasSuperClass() ? new Opt(getSuperClass().type().substitute(parTypeDecl)) : new Opt(),
      getImplementsList().substitute(parTypeDecl),
      new List(), // delegates TypeParameter lookup to original
      this
    );
  }
  /**
   * @aspect Generics
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:298
   */
  public TypeDecl makeGeneric(Signatures.ClassSignature s) {
    return (TypeDecl) this;
  }
  /**
   * @aspect GenericsNameBinding
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:717
   */
  public SimpleSet addTypeVariables(SimpleSet c, String name) {
    GenericTypeDecl original = (GenericTypeDecl) original();
    for (int i = 0; i < original.getNumTypeParameter(); i++) {
      TypeVariable p = original.getTypeParameter(i);
      if (p.name().equals(name)) {
        c = c.add(p);
      }
    }
    return c;
  }
  /**
   * @aspect LookupParTypeDecl
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:913
   */
  public List createArgumentList(Collection<TypeDecl> args) {
    GenericTypeDecl original = (GenericTypeDecl) original();
    List<Access> list = new List<Access>();
    if (args.isEmpty()) {
      // Change: Don't add any thing to the list.
      // Concern: The previous version seem to add the erasure of the type variable for some reason,
      // maybe this is how the raw type is represented (?), but this doesn't really comply with the
      // claim that raw types don't have any type variables...?
      for (int i = 0; i < original.getNumTypeParameter(); i++) {
        list.add(original.getTypeParameter(i).erasure().createBoundAccess());
      }
    } else {
      for (TypeDecl arg : args) {
        list.add(arg.createBoundAccess());
      }
    }
    return list;
  }
  /**
   * @declaredat ASTNode:1
   */
  public GenericClassDecl() {
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
    children = new ASTNode[6];
    setChild(new Opt(), 1);
    setChild(new List(), 2);
    setChild(new List(), 3);
    setChild(new List(), 4);
    setChild(new Opt(), 5);
  }
  /**
   * @declaredat ASTNode:18
   */
  public GenericClassDecl(Modifiers p0, String p1, Opt<Access> p2, List<Access> p3, List<BodyDecl> p4, List<TypeVariable> p5) {
    setChild(p0, 0);
    setID(p1);
    setChild(p2, 1);
    setChild(p3, 2);
    setChild(p4, 3);
    setChild(p5, 4);
  }
  /**
   * @declaredat ASTNode:26
   */
  public GenericClassDecl(Modifiers p0, beaver.Symbol p1, Opt<Access> p2, List<Access> p3, List<BodyDecl> p4, List<TypeVariable> p5) {
    setChild(p0, 0);
    setID(p1);
    setChild(p2, 1);
    setChild(p3, 2);
    setChild(p4, 3);
    setChild(p5, 4);
  }
  /**
   * @apilevel low-level
   * @declaredat ASTNode:37
   */
  protected int numChildren() {
    return 5;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:43
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:49
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    rawType_reset();
    lookupParTypeDecl_Collection_TypeDecl__reset();
    usesTypeVariable_reset();
    subtype_TypeDecl_reset();
    instanceOf_TypeDecl_reset();
    getStandInMethodList_reset();
    strictSubtype_TypeDecl_reset();
    lookupParTypeDecl_ParTypeAccess_reset();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:63
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:69
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:75
   */
  public GenericClassDecl clone() throws CloneNotSupportedException {
    GenericClassDecl node = (GenericClassDecl) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:82
   */
  public GenericClassDecl copy() {
    try {
      GenericClassDecl node = (GenericClassDecl) clone();
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
   * @declaredat ASTNode:101
   */
  @Deprecated
  public GenericClassDecl fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:111
   */
  public GenericClassDecl treeCopyNoTransform() {
    GenericClassDecl tree = (GenericClassDecl) copy();
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        switch (i) {
        case 5:
          tree.children[i] = new Opt();
          continue;
        }
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
   * @declaredat ASTNode:136
   */
  public GenericClassDecl treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:143
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenString_ID == ((GenericClassDecl)node).tokenString_ID);    
  }
  /**
   * Replaces the Modifiers child.
   * @param node The new node to replace the Modifiers child.
   * @apilevel high-level
   */
  public void setModifiers(Modifiers node) {
    setChild(node, 0);
  }
  /**
   * Retrieves the Modifiers child.
   * @return The current node used as the Modifiers child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Modifiers")
  public Modifiers getModifiers() {
    return (Modifiers) getChild(0);
  }
  /**
   * Retrieves the Modifiers child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Modifiers child.
   * @apilevel low-level
   */
  public Modifiers getModifiersNoTransform() {
    return (Modifiers) getChildNoTransform(0);
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
   * Replaces the optional node for the SuperClass child. This is the <code>Opt</code>
   * node containing the child SuperClass, not the actual child!
   * @param opt The new node to be used as the optional node for the SuperClass child.
   * @apilevel low-level
   */
  public void setSuperClassOpt(Opt<Access> opt) {
    setChild(opt, 1);
  }
  /**
   * Replaces the (optional) SuperClass child.
   * @param node The new node to be used as the SuperClass child.
   * @apilevel high-level
   */
  public void setSuperClass(Access node) {
    getSuperClassOpt().setChild(node, 0);
  }
  /**
   * Check whether the optional SuperClass child exists.
   * @return {@code true} if the optional SuperClass child exists, {@code false} if it does not.
   * @apilevel high-level
   */
  public boolean hasSuperClass() {
    return getSuperClassOpt().getNumChild() != 0;
  }
  /**
   * Retrieves the (optional) SuperClass child.
   * @return The SuperClass child, if it exists. Returns {@code null} otherwise.
   * @apilevel low-level
   */
  public Access getSuperClass() {
    return (Access) getSuperClassOpt().getChild(0);
  }
  /**
   * Retrieves the optional node for the SuperClass child. This is the <code>Opt</code> node containing the child SuperClass, not the actual child!
   * @return The optional node for child the SuperClass child.
   * @apilevel low-level
   */
  @ASTNodeAnnotation.OptChild(name="SuperClass")
  public Opt<Access> getSuperClassOpt() {
    return (Opt<Access>) getChild(1);
  }
  /**
   * Retrieves the optional node for child SuperClass. This is the <code>Opt</code> node containing the child SuperClass, not the actual child!
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The optional node for child SuperClass.
   * @apilevel low-level
   */
  public Opt<Access> getSuperClassOptNoTransform() {
    return (Opt<Access>) getChildNoTransform(1);
  }
  /**
   * Replaces the Implements list.
   * @param list The new list node to be used as the Implements list.
   * @apilevel high-level
   */
  public void setImplementsList(List<Access> list) {
    setChild(list, 2);
  }
  /**
   * Retrieves the number of children in the Implements list.
   * @return Number of children in the Implements list.
   * @apilevel high-level
   */
  public int getNumImplements() {
    return getImplementsList().getNumChild();
  }
  /**
   * Retrieves the number of children in the Implements list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the Implements list.
   * @apilevel low-level
   */
  public int getNumImplementsNoTransform() {
    return getImplementsListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the Implements list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the Implements list.
   * @apilevel high-level
   */
  public Access getImplements(int i) {
    return (Access) getImplementsList().getChild(i);
  }
  /**
   * Check whether the Implements list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasImplements() {
    return getImplementsList().getNumChild() != 0;
  }
  /**
   * Append an element to the Implements list.
   * @param node The element to append to the Implements list.
   * @apilevel high-level
   */
  public void addImplements(Access node) {
    List<Access> list = (parent == null) ? getImplementsListNoTransform() : getImplementsList();
    list.addChild(node);
  }
  /**
   * @apilevel low-level
   */
  public void addImplementsNoTransform(Access node) {
    List<Access> list = getImplementsListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the Implements list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setImplements(Access node, int i) {
    List<Access> list = getImplementsList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the Implements list.
   * @return The node representing the Implements list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="Implements")
  public List<Access> getImplementsList() {
    List<Access> list = (List<Access>) getChild(2);
    return list;
  }
  /**
   * Retrieves the Implements list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the Implements list.
   * @apilevel low-level
   */
  public List<Access> getImplementsListNoTransform() {
    return (List<Access>) getChildNoTransform(2);
  }
  /**
   * Retrieves the Implements list.
   * @return The node representing the Implements list.
   * @apilevel high-level
   */
  public List<Access> getImplementss() {
    return getImplementsList();
  }
  /**
   * Retrieves the Implements list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the Implements list.
   * @apilevel low-level
   */
  public List<Access> getImplementssNoTransform() {
    return getImplementsListNoTransform();
  }
  /**
   * Replaces the BodyDecl list.
   * @param list The new list node to be used as the BodyDecl list.
   * @apilevel high-level
   */
  public void setBodyDeclList(List<BodyDecl> list) {
    setChild(list, 3);
  }
  /**
   * Retrieves the number of children in the BodyDecl list.
   * @return Number of children in the BodyDecl list.
   * @apilevel high-level
   */
  public int getNumBodyDecl() {
    return getBodyDeclList().getNumChild();
  }
  /**
   * Retrieves the number of children in the BodyDecl list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the BodyDecl list.
   * @apilevel low-level
   */
  public int getNumBodyDeclNoTransform() {
    return getBodyDeclListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the BodyDecl list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the BodyDecl list.
   * @apilevel high-level
   */
  public BodyDecl getBodyDecl(int i) {
    return (BodyDecl) getBodyDeclList().getChild(i);
  }
  /**
   * Check whether the BodyDecl list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasBodyDecl() {
    return getBodyDeclList().getNumChild() != 0;
  }
  /**
   * Append an element to the BodyDecl list.
   * @param node The element to append to the BodyDecl list.
   * @apilevel high-level
   */
  public void addBodyDecl(BodyDecl node) {
    List<BodyDecl> list = (parent == null) ? getBodyDeclListNoTransform() : getBodyDeclList();
    list.addChild(node);
  }
  /**
   * @apilevel low-level
   */
  public void addBodyDeclNoTransform(BodyDecl node) {
    List<BodyDecl> list = getBodyDeclListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the BodyDecl list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setBodyDecl(BodyDecl node, int i) {
    List<BodyDecl> list = getBodyDeclList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the BodyDecl list.
   * @return The node representing the BodyDecl list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="BodyDecl")
  public List<BodyDecl> getBodyDeclList() {
    List<BodyDecl> list = (List<BodyDecl>) getChild(3);
    return list;
  }
  /**
   * Retrieves the BodyDecl list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the BodyDecl list.
   * @apilevel low-level
   */
  public List<BodyDecl> getBodyDeclListNoTransform() {
    return (List<BodyDecl>) getChildNoTransform(3);
  }
  /**
   * Retrieves the BodyDecl list.
   * @return The node representing the BodyDecl list.
   * @apilevel high-level
   */
  public List<BodyDecl> getBodyDecls() {
    return getBodyDeclList();
  }
  /**
   * Retrieves the BodyDecl list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the BodyDecl list.
   * @apilevel low-level
   */
  public List<BodyDecl> getBodyDeclsNoTransform() {
    return getBodyDeclListNoTransform();
  }
  /**
   * Replaces the TypeParameter list.
   * @param list The new list node to be used as the TypeParameter list.
   * @apilevel high-level
   */
  public void setTypeParameterList(List<TypeVariable> list) {
    setChild(list, 4);
  }
  /**
   * Retrieves the number of children in the TypeParameter list.
   * @return Number of children in the TypeParameter list.
   * @apilevel high-level
   */
  public int getNumTypeParameter() {
    return getTypeParameterList().getNumChild();
  }
  /**
   * Retrieves the number of children in the TypeParameter list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the TypeParameter list.
   * @apilevel low-level
   */
  public int getNumTypeParameterNoTransform() {
    return getTypeParameterListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the TypeParameter list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the TypeParameter list.
   * @apilevel high-level
   */
  public TypeVariable getTypeParameter(int i) {
    return (TypeVariable) getTypeParameterList().getChild(i);
  }
  /**
   * Check whether the TypeParameter list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasTypeParameter() {
    return getTypeParameterList().getNumChild() != 0;
  }
  /**
   * Append an element to the TypeParameter list.
   * @param node The element to append to the TypeParameter list.
   * @apilevel high-level
   */
  public void addTypeParameter(TypeVariable node) {
    List<TypeVariable> list = (parent == null) ? getTypeParameterListNoTransform() : getTypeParameterList();
    list.addChild(node);
  }
  /**
   * @apilevel low-level
   */
  public void addTypeParameterNoTransform(TypeVariable node) {
    List<TypeVariable> list = getTypeParameterListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the TypeParameter list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setTypeParameter(TypeVariable node, int i) {
    List<TypeVariable> list = getTypeParameterList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the TypeParameter list.
   * @return The node representing the TypeParameter list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="TypeParameter")
  public List<TypeVariable> getTypeParameterList() {
    List<TypeVariable> list = (List<TypeVariable>) getChild(4);
    return list;
  }
  /**
   * Retrieves the TypeParameter list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the TypeParameter list.
   * @apilevel low-level
   */
  public List<TypeVariable> getTypeParameterListNoTransform() {
    return (List<TypeVariable>) getChildNoTransform(4);
  }
  /**
   * Retrieves the TypeParameter list.
   * @return The node representing the TypeParameter list.
   * @apilevel high-level
   */
  public List<TypeVariable> getTypeParameters() {
    return getTypeParameterList();
  }
  /**
   * Retrieves the TypeParameter list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the TypeParameter list.
   * @apilevel low-level
   */
  public List<TypeVariable> getTypeParametersNoTransform() {
    return getTypeParameterListNoTransform();
  }
  /**
   * Replaces the (optional) ImplicitConstructor child.
   * @param node The new node to be used as the ImplicitConstructor child.
   * @apilevel high-level
   */
  public void setImplicitConstructor(ConstructorDecl node) {
    getImplicitConstructorOpt().setChild(node, 0);
  }
  /**
   * Check whether the optional ImplicitConstructor child exists.
   * @return {@code true} if the optional ImplicitConstructor child exists, {@code false} if it does not.
   * @apilevel high-level
   */
  public boolean hasImplicitConstructor() {
    return getImplicitConstructorOpt().getNumChild() != 0;
  }
  /**
   * Retrieves the (optional) ImplicitConstructor child.
   * @return The ImplicitConstructor child, if it exists. Returns {@code null} otherwise.
   * @apilevel low-level
   */
  public ConstructorDecl getImplicitConstructor() {
    return (ConstructorDecl) getImplicitConstructorOpt().getChild(0);
  }
  /**
   * Retrieves the optional node for child ImplicitConstructor. This is the <code>Opt</code> node containing the child ImplicitConstructor, not the actual child!
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The optional node for child ImplicitConstructor.
   * @apilevel low-level
   */
  public Opt<ConstructorDecl> getImplicitConstructorOptNoTransform() {
    return (Opt<ConstructorDecl>) getChildNoTransform(5);
  }
  /**
   * Retrieves the child position of the optional child ImplicitConstructor.
   * @return The the child position of the optional child ImplicitConstructor.
   * @apilevel low-level
   */
  protected int getImplicitConstructorOptChildPosition() {
    return 5;
  }
  /**
   * @apilevel internal
   */
  protected boolean rawType_computed = false;
  /**
   * @apilevel internal
   */
  protected TypeDecl rawType_value;
  /**
   * @apilevel internal
   */
  private void rawType_reset() {
    rawType_computed = false;
    rawType_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public TypeDecl rawType() {
    ASTNode$State state = state();
    if (rawType_computed) {
      return rawType_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    rawType_value = lookupParTypeDecl(Collections.<TypeDecl>emptyList());
    if (isFinal && num == state().boundariesCrossed) {
      rawType_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return rawType_value;
  }
  /**
   * @apilevel internal
   */
  protected List lookupParTypeDecl_Collection_TypeDecl__list;
  /**
   * @apilevel internal
   */
  protected java.util.Map lookupParTypeDecl_Collection_TypeDecl__values;
  /**
   * @apilevel internal
   */
  private void lookupParTypeDecl_Collection_TypeDecl__reset() {
    lookupParTypeDecl_Collection_TypeDecl__values = null;
    lookupParTypeDecl_Collection_TypeDecl__list = null;
  }
  @ASTNodeAnnotation.Attribute
  public TypeDecl lookupParTypeDecl(Collection<TypeDecl> typeArgs) {
    Object _parameters = typeArgs;
    if (lookupParTypeDecl_Collection_TypeDecl__values == null) lookupParTypeDecl_Collection_TypeDecl__values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (lookupParTypeDecl_Collection_TypeDecl__values.containsKey(_parameters)) {
      return (TypeDecl) lookupParTypeDecl_Collection_TypeDecl__values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    TypeDecl lookupParTypeDecl_Collection_TypeDecl__value = lookupParTypeDecl_compute(typeArgs);
    if (lookupParTypeDecl_Collection_TypeDecl__list == null) {
      lookupParTypeDecl_Collection_TypeDecl__list = new List();
      lookupParTypeDecl_Collection_TypeDecl__list.is$Final = true;
      lookupParTypeDecl_Collection_TypeDecl__list.setParent(this);
    }
    lookupParTypeDecl_Collection_TypeDecl__list.add(lookupParTypeDecl_Collection_TypeDecl__value);
    if (lookupParTypeDecl_Collection_TypeDecl__value != null) {
      lookupParTypeDecl_Collection_TypeDecl__value = (TypeDecl) lookupParTypeDecl_Collection_TypeDecl__list.getChild(lookupParTypeDecl_Collection_TypeDecl__list.numChildren-1);
      lookupParTypeDecl_Collection_TypeDecl__value.is$Final = true;
    }
    if (true) {
      lookupParTypeDecl_Collection_TypeDecl__values.put(_parameters, lookupParTypeDecl_Collection_TypeDecl__value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return lookupParTypeDecl_Collection_TypeDecl__value;
  }
  /**
   * @apilevel internal
   */
  private TypeDecl lookupParTypeDecl_compute(Collection<TypeDecl> typeArgs) {
      ParClassDecl typeDecl = typeArgs.size() == 0 ? new RawClassDecl() : new ParClassDecl();
      typeDecl.setModifiers((Modifiers) getModifiers().treeCopyNoTransform());
      typeDecl.setID(getID());
      // ES: trying to only do this for ParClassDecl and then later for RawClassDecl
      if (!(typeDecl instanceof RawClassDecl)) {
        typeDecl.setArgumentList(createArgumentList(typeArgs));
      }
      return typeDecl;
    }
  /**
   * @apilevel internal
   */
  protected int usesTypeVariable_visited = -1;
  /**
   * @apilevel internal
   */
  private void usesTypeVariable_reset() {
    usesTypeVariable_computed = false;
    usesTypeVariable_initialized = false;
    usesTypeVariable_visited = -1;
  }
  /**
   * @apilevel internal
   */
  protected boolean usesTypeVariable_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean usesTypeVariable_initialized = false;
  /**
   * @apilevel internal
   */
  protected boolean usesTypeVariable_value;
  @ASTNodeAnnotation.Attribute
  public boolean usesTypeVariable() {
    if (usesTypeVariable_computed) {
      return usesTypeVariable_value;
    }
    ASTNode$State state = state();
    boolean new_usesTypeVariable_value;
    if (!usesTypeVariable_initialized) {
      usesTypeVariable_initialized = true;
      usesTypeVariable_value = false;
    }
    if (!state.IN_CIRCLE) {
      state.IN_CIRCLE = true;
      int num = state.boundariesCrossed;
      boolean isFinal = this.is$Final();
      do {
        usesTypeVariable_visited = state.CIRCLE_INDEX;
        state.CHANGE = false;
        new_usesTypeVariable_value = true;
        if (new_usesTypeVariable_value != usesTypeVariable_value) {
          state.CHANGE = true;
        }
        usesTypeVariable_value = new_usesTypeVariable_value;
        state.CIRCLE_INDEX++;
      } while (state.CHANGE);
      if (isFinal && num == state().boundariesCrossed) {
        usesTypeVariable_computed = true;
      } else {
        state.RESET_CYCLE = true;
        boolean $tmp = true;
        state.RESET_CYCLE = false;
        usesTypeVariable_computed = false;
        usesTypeVariable_initialized = false;
      }
      state.IN_CIRCLE = false;
      state.INTERMEDIATE_VALUE = false;
      return usesTypeVariable_value;
    }
    if (usesTypeVariable_visited != state.CIRCLE_INDEX) {
      usesTypeVariable_visited = state.CIRCLE_INDEX;
      if (state.RESET_CYCLE) {
        usesTypeVariable_computed = false;
        usesTypeVariable_initialized = false;
        usesTypeVariable_visited = -1;
        return usesTypeVariable_value;
      }
      new_usesTypeVariable_value = true;
      if (new_usesTypeVariable_value != usesTypeVariable_value) {
        state.CHANGE = true;
      }
      usesTypeVariable_value = new_usesTypeVariable_value;
      state.INTERMEDIATE_VALUE = true;
      return usesTypeVariable_value;
    }
    state.INTERMEDIATE_VALUE = true;
    return usesTypeVariable_value;
  }
  /**
   * @apilevel internal
   */
  private void subtype_TypeDecl_reset() {
    subtype_TypeDecl_values = null;
  }
  protected java.util.Map subtype_TypeDecl_values;
  @ASTNodeAnnotation.Attribute
  public boolean subtype(TypeDecl type) {
    Object _parameters = type;
    if (subtype_TypeDecl_values == null) subtype_TypeDecl_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State.CircularValue _value;
    if (subtype_TypeDecl_values.containsKey(_parameters)) {
      Object _o = subtype_TypeDecl_values.get(_parameters);
      if (!(_o instanceof ASTNode$State.CircularValue)) {
        return (Boolean) _o;
      } else {
        _value = (ASTNode$State.CircularValue) _o;
      }
    } else {
      _value = new ASTNode$State.CircularValue();
      subtype_TypeDecl_values.put(_parameters, _value);
      _value.value = true;
    }
    ASTNode$State state = state();
    boolean new_subtype_TypeDecl_value;
    if (!state.IN_CIRCLE) {
      state.IN_CIRCLE = true;
      int num = state.boundariesCrossed;
      boolean isFinal = this.is$Final();
      // TODO: fixme
      // state().CIRCLE_INDEX = 1;
      do {
        _value.visited = state.CIRCLE_INDEX;
        state.CHANGE = false;
        new_subtype_TypeDecl_value = type.supertypeGenericClassDecl(this);
        if (new_subtype_TypeDecl_value != ((Boolean)_value.value)) {
          state.CHANGE = true;
          _value.value = new_subtype_TypeDecl_value;
        }
        state.CIRCLE_INDEX++;
      } while (state.CHANGE);
      if (isFinal && num == state().boundariesCrossed) {
        subtype_TypeDecl_values.put(_parameters, new_subtype_TypeDecl_value);
      } else {
        subtype_TypeDecl_values.remove(_parameters);
        state.RESET_CYCLE = true;
        boolean $tmp = type.supertypeGenericClassDecl(this);
        state.RESET_CYCLE = false;
      }
      state.IN_CIRCLE = false;
      state.INTERMEDIATE_VALUE = false;
      return new_subtype_TypeDecl_value;
    }
    if (state.CIRCLE_INDEX != _value.visited) {
      _value.visited = state.CIRCLE_INDEX;
      new_subtype_TypeDecl_value = type.supertypeGenericClassDecl(this);
      if (state.RESET_CYCLE) {
        subtype_TypeDecl_values.remove(_parameters);
      }
      else if (new_subtype_TypeDecl_value != ((Boolean)_value.value)) {
        state.CHANGE = true;
        _value.value = new_subtype_TypeDecl_value;
      }
      state.INTERMEDIATE_VALUE = true;
      return new_subtype_TypeDecl_value;
    }
    state.INTERMEDIATE_VALUE = true;
    return (Boolean) _value.value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean supertypeParClassDecl(ParClassDecl type) {
    boolean supertypeParClassDecl_ParClassDecl_value = type.genericDecl().original().subtype(this);

    return supertypeParClassDecl_ParClassDecl_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean supertypeParInterfaceDecl(ParInterfaceDecl type) {
    boolean supertypeParInterfaceDecl_ParInterfaceDecl_value = type.genericDecl().original().subtype(this);

    return supertypeParInterfaceDecl_ParInterfaceDecl_value;
  }
  /**
   * @apilevel internal
   */
  protected java.util.Map instanceOf_TypeDecl_values;
  /**
   * @apilevel internal
   */
  private void instanceOf_TypeDecl_reset() {
    instanceOf_TypeDecl_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean instanceOf(TypeDecl type) {
    Object _parameters = type;
    if (instanceOf_TypeDecl_values == null) instanceOf_TypeDecl_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (instanceOf_TypeDecl_values.containsKey(_parameters)) {
      return (Boolean) instanceOf_TypeDecl_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean instanceOf_TypeDecl_value = subtype(type);
    if (isFinal && num == state().boundariesCrossed) {
      instanceOf_TypeDecl_values.put(_parameters, instanceOf_TypeDecl_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return instanceOf_TypeDecl_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isReifiable() {
    boolean isReifiable_value = false;

    return isReifiable_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean getStandInMethodList_computed = false;
  /**
   * @apilevel internal
   */
  protected List<StandInMethodDecl> getStandInMethodList_value;
  /**
   * @apilevel internal
   */
  private void getStandInMethodList_reset() {
    getStandInMethodList_computed = false;
    getStandInMethodList_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public List<StandInMethodDecl> getStandInMethodList() {
    ASTNode$State state = state();
    if (getStandInMethodList_computed) {
      return getStandInMethodList_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    getStandInMethodList_value = getStandInMethodList_compute();
    getStandInMethodList_value.setParent(this);
    getStandInMethodList_value.is$Final = true;
    if (true) {
      getStandInMethodList_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return getStandInMethodList_value;
  }
  /**
   * @apilevel internal
   */
  private List<StandInMethodDecl> getStandInMethodList_compute() {
      List<StandInMethodDecl> placeholderMethods = new List<StandInMethodDecl>();
      List<TypeVariable> typeParams = getTypeParameterList();
      List<TypeVariable> classTypeVars = new List<TypeVariable>();
      List<Access> typeArgs = new List<Access>();
  
      // Copy the list of type parameters.
      int arg = 0;
      for (Iterator iter = typeParams.iterator(); iter.hasNext(); ++arg) {
        String substName = "#"+arg;
        typeArgs.add(new TypeAccess(substName));
  
        TypeVariable typeVar = (TypeVariable) iter.next();
        List<Access> typeBounds = new List<Access>();
        for (Access typeBound : typeVar.getTypeBoundList()) {
          typeBounds.add((Access) typeBound.treeCopyNoTransform());
        }
        classTypeVars.add(new TypeVariable(
            new Modifiers(),
            substName,
            new List<BodyDecl>(),
            typeBounds));
      }
  
      ParTypeAccess returnType = new ParTypeAccess(
          createQualifiedAccess(),
          typeArgs);
  
      for (Iterator iter = constructors().iterator(); iter.hasNext(); ) {
        ConstructorDecl decl = (ConstructorDecl) iter.next();
        if (decl instanceof ConstructorDeclSubstituted) {
          decl = ((ConstructorDeclSubstituted) decl).getOriginal();
        }
  
        // Filter accessible constructors.
        if (!decl.accessibleFrom(hostType())) {
          continue;
        }
  
        Collection<TypeVariable> originalTypeVars = new LinkedList<TypeVariable>();
        List<TypeVariable> typeVars = new List<TypeVariable>();
        for (TypeVariable typeVar : typeParams) {
          originalTypeVars.add(typeVar);
        }
        for (TypeVariable typeVar : classTypeVars) {
          typeVars.add((TypeVariable) typeVar.treeCopyNoTransform());
        }
  
        if (decl.isGeneric()) {
          GenericConstructorDecl genericDecl = decl.genericDecl();
          List<TypeVariable> typeVariables = new List<TypeVariable>();
          for (int i = 0; i < genericDecl.getNumTypeParameter(); ++i) {
            String substName = "#" + (arg+i);
  
            TypeVariable typeVar = genericDecl.getTypeParameter(i);
            originalTypeVars.add(typeVar);
            List<Access> typeBounds = new List<Access>();
            for (Access typeBound : typeVar.getTypeBoundList()) {
              typeBounds.add((Access) typeBound.treeCopyNoTransform());
            }
            typeVars.add(new TypeVariable(
                  new Modifiers(),
                  substName,
                  new List<BodyDecl>(),
                  typeBounds));
          }
        }
  
        List<ParameterDeclaration> substParameters = new List<ParameterDeclaration>();
        for (ParameterDeclaration param : decl.getParameterList()) {
          substParameters.add(param.substituted(originalTypeVars, typeVars));
        }
  
        List<Access> substExceptions = new List<Access>();
        for (Access exception : decl.getExceptionList()) {
          substExceptions.add(exception.substituted(originalTypeVars, typeVars));
        }
  
        StandInMethodDecl placeholderMethod = new StandInMethodDecl(
            (Modifiers) decl.getModifiers().treeCopyNoTransform(),
            (Access) returnType.treeCopyNoTransform(),
            "#" + getID(),
            substParameters,
            substExceptions,
            new Opt(new Block()),
            typeVars);
  
        placeholderMethods.add(placeholderMethod);
      }
      return placeholderMethods;
    }
  /**
   * @apilevel internal
   */
  private void strictSubtype_TypeDecl_reset() {
    strictSubtype_TypeDecl_values = null;
  }
  protected java.util.Map strictSubtype_TypeDecl_values;
  @ASTNodeAnnotation.Attribute
  public boolean strictSubtype(TypeDecl type) {
    Object _parameters = type;
    if (strictSubtype_TypeDecl_values == null) strictSubtype_TypeDecl_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State.CircularValue _value;
    if (strictSubtype_TypeDecl_values.containsKey(_parameters)) {
      Object _o = strictSubtype_TypeDecl_values.get(_parameters);
      if (!(_o instanceof ASTNode$State.CircularValue)) {
        return (Boolean) _o;
      } else {
        _value = (ASTNode$State.CircularValue) _o;
      }
    } else {
      _value = new ASTNode$State.CircularValue();
      strictSubtype_TypeDecl_values.put(_parameters, _value);
      _value.value = true;
    }
    ASTNode$State state = state();
    boolean new_strictSubtype_TypeDecl_value;
    if (!state.IN_CIRCLE) {
      state.IN_CIRCLE = true;
      int num = state.boundariesCrossed;
      boolean isFinal = this.is$Final();
      // TODO: fixme
      // state().CIRCLE_INDEX = 1;
      do {
        _value.visited = state.CIRCLE_INDEX;
        state.CHANGE = false;
        new_strictSubtype_TypeDecl_value = type.strictSupertypeGenericClassDecl(this);
        if (new_strictSubtype_TypeDecl_value != ((Boolean)_value.value)) {
          state.CHANGE = true;
          _value.value = new_strictSubtype_TypeDecl_value;
        }
        state.CIRCLE_INDEX++;
      } while (state.CHANGE);
      if (isFinal && num == state().boundariesCrossed) {
        strictSubtype_TypeDecl_values.put(_parameters, new_strictSubtype_TypeDecl_value);
      } else {
        strictSubtype_TypeDecl_values.remove(_parameters);
        state.RESET_CYCLE = true;
        boolean $tmp = type.strictSupertypeGenericClassDecl(this);
        state.RESET_CYCLE = false;
      }
      state.IN_CIRCLE = false;
      state.INTERMEDIATE_VALUE = false;
      return new_strictSubtype_TypeDecl_value;
    }
    if (state.CIRCLE_INDEX != _value.visited) {
      _value.visited = state.CIRCLE_INDEX;
      new_strictSubtype_TypeDecl_value = type.strictSupertypeGenericClassDecl(this);
      if (state.RESET_CYCLE) {
        strictSubtype_TypeDecl_values.remove(_parameters);
      }
      else if (new_strictSubtype_TypeDecl_value != ((Boolean)_value.value)) {
        state.CHANGE = true;
        _value.value = new_strictSubtype_TypeDecl_value;
      }
      state.INTERMEDIATE_VALUE = true;
      return new_strictSubtype_TypeDecl_value;
    }
    state.INTERMEDIATE_VALUE = true;
    return (Boolean) _value.value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean strictSupertypeParClassDecl(ParClassDecl type) {
    boolean strictSupertypeParClassDecl_ParClassDecl_value = type.genericDecl().original().strictSubtype(this);

    return strictSupertypeParClassDecl_ParClassDecl_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean strictSupertypeParInterfaceDecl(ParInterfaceDecl type) {
    boolean strictSupertypeParInterfaceDecl_ParInterfaceDecl_value = type.genericDecl().original().strictSubtype(this);

    return strictSupertypeParInterfaceDecl_ParInterfaceDecl_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isGenericType() {
    boolean isGenericType_value = true;

    return isGenericType_value;
  }
  /**
   * @apilevel internal
   */
  protected java.util.Map lookupParTypeDecl_ParTypeAccess_values;
  /**
   * @apilevel internal
   */
  private void lookupParTypeDecl_ParTypeAccess_reset() {
    lookupParTypeDecl_ParTypeAccess_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public TypeDecl lookupParTypeDecl(ParTypeAccess p) {
    Object _parameters = p;
    if (lookupParTypeDecl_ParTypeAccess_values == null) lookupParTypeDecl_ParTypeAccess_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (lookupParTypeDecl_ParTypeAccess_values.containsKey(_parameters)) {
      return (TypeDecl) lookupParTypeDecl_ParTypeAccess_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    TypeDecl lookupParTypeDecl_ParTypeAccess_value = lookupParTypeDecl_compute(p);
    if (isFinal && num == state().boundariesCrossed) {
      lookupParTypeDecl_ParTypeAccess_values.put(_parameters, lookupParTypeDecl_ParTypeAccess_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return lookupParTypeDecl_ParTypeAccess_value;
  }
  /**
   * @apilevel internal
   */
  private TypeDecl lookupParTypeDecl_compute(ParTypeAccess p) {
      Collection<TypeDecl> typeArguments = new ArrayList<TypeDecl>();
      for (int i = 0; i < p.getNumTypeArgument(); i++) {
        typeArguments.add(p.getTypeArgument(i).type());
      }
      return lookupParTypeDecl(typeArguments);
    }
  /**
   * @attribute inh
   * @aspect GenericsTypeCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:656
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl typeThrowable() {
    TypeDecl typeThrowable_value = getParent().Define_typeThrowable(this, null);

    return typeThrowable_value;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeAnalysis.jrag:569
   * @apilevel internal
   */
  public boolean Define_isNestedType(ASTNode caller, ASTNode child) {
    if (caller == getTypeParameterListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:709
      int childIndex = caller.getIndexOfChild(child);
      return true;
    }
    else {
      return super.Define_isNestedType(caller, child);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeAnalysis.jrag:544
   * @apilevel internal
   */
  public TypeDecl Define_enclosingType(ASTNode caller, ASTNode child) {
    if (caller == getTypeParameterListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:710
      int childIndex = caller.getIndexOfChild(child);
      return this;
    }
    else {
      return super.Define_enclosingType(caller, child);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\GenericMethods.jrag:197
   * @apilevel internal
   */
  public SimpleSet Define_lookupType(ASTNode caller, ASTNode child, String name) {
    if (caller == getBodyDeclListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:763
      int index = caller.getIndexOfChild(child);
      {
          SimpleSet c = memberTypes(name);
          if (getBodyDecl(index).visibleTypeParameters()) {
            c = addTypeVariables(c, name);
          }
          if (!c.isEmpty()) {
            return c;
          }
          // 8.5.2
          if (isClassDecl() && isStatic() && !isTopLevelType()) {
            for (Iterator iter = lookupType(name).iterator(); iter.hasNext(); ) {
              TypeDecl d = (TypeDecl) iter.next();
              if (d.isStatic() || (d.enclosingType() != null && instanceOf(d.enclosingType()))) {
                c = c.add(d);
              }
            }
          } else {
            c = lookupType(name);
          }
          if (!c.isEmpty()) {
            return c;
          }
          return topLevelType().lookupType(name); // Fix to search imports
          // include type parameters if not static
        }
    }
    else if (caller == getTypeParameterListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:741
      int childIndex = caller.getIndexOfChild(child);
      {
          SimpleSet c = memberTypes(name);
          c = addTypeVariables(c, name);
          if (!c.isEmpty()) {
            return c;
          }
          // 8.5.2
          if (isClassDecl() && isStatic() && !isTopLevelType()) {
            for (Iterator iter = lookupType(name).iterator(); iter.hasNext(); ) {
              TypeDecl d = (TypeDecl) iter.next();
              if (d.isStatic() || (d.enclosingType() != null && instanceOf(d.enclosingType()))) {
                c = c.add(d);
              }
            }
          } else {
            c = lookupType(name);
          }
          if (!c.isEmpty()) {
            return c;
          }
          return topLevelType().lookupType(name); // Fix to search imports
        }
    }
    else if (caller == getImplementsListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:736
      int childIndex = caller.getIndexOfChild(child);
      {
          SimpleSet c = addTypeVariables(SimpleSet.emptySet, name);
          return !c.isEmpty() ? c : lookupType(name);
        }
    }
    else if (caller == getSuperClassOptNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:732
      {
          SimpleSet c = addTypeVariables(SimpleSet.emptySet, name);
          return !c.isEmpty() ? c : lookupType(name);
        }
    }
    else {
      return super.Define_lookupType(caller, child, name);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\GenericsParTypeDecl.jrag:71
   * @apilevel internal
   */
  public TypeDecl Define_genericDecl(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return this;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TypeVariablePositions.jrag:29
   * @apilevel internal
   */
  public int Define_typeVarPosition(ASTNode caller, ASTNode child) {
    if (caller == getTypeParameterListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TypeVariablePositions.jrag:43
      int i = caller.getIndexOfChild(child);
      return i;
    }
    else {
      return getParent().Define_typeVarPosition(this, caller);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TypeVariablePositions.jrag:32
   * @apilevel internal
   */
  public boolean Define_typeVarInMethod(ASTNode caller, ASTNode child) {
    if (caller == getTypeParameterListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TypeVariablePositions.jrag:44
      int childIndex = caller.getIndexOfChild(child);
      return false;
    }
    else {
      return getParent().Define_typeVarInMethod(this, caller);
    }
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
