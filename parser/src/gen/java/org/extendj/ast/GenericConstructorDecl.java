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
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\grammar\\GenericMethods.ast:4
 * @production GenericConstructorDecl : {@link ConstructorDecl} ::= <span class="component">TypeParameter:{@link TypeVariable}*</span>;

 */
public class GenericConstructorDecl extends ConstructorDecl implements Cloneable {
  /**
   * @aspect Java5PrettyPrint
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\PrettyPrint.jadd:168
   */
  public void prettyPrint(PrettyPrinter out) {
    if (!isImplicitConstructor()) {
      if (hasDocComment()) {
        out.print(docComment());
      }
      if (!out.isNewLine()) {
        out.println();
      }
      out.print(getModifiers());
      out.print("<");
      out.join(getTypeParameterList(), new PrettyPrinter.Joiner() {
        @Override
        public void printSeparator(PrettyPrinter out) {
          out.print(", ");
        }
      });
      out.print("> ");
      out.print(getID());
      out.print("(");
      out.join(getParameterList(), new PrettyPrinter.Joiner() {
        @Override
        public void printSeparator(PrettyPrinter out) {
          out.print(", ");
        }
      });
      out.print(")");
      if (hasExceptions()) {
        out.print(" throws ");
        out.join(getExceptionList(), new PrettyPrinter.Joiner() {
          @Override
          public void printSeparator(PrettyPrinter out) {
            out.print(", ");
          }
        });
      }
      out.print(" {");
      out.println();
      out.indent(1);
      out.print(getParsedConstructorInvocation());
      if (!out.isNewLine()) {
        out.println();
      }
      out.println();
      out.indent(1);
      out.join(blockStmts(), new PrettyPrinter.Joiner() {
        @Override
        public void printSeparator(PrettyPrinter out) {
          out.println();
        }
      });
      if (!out.isNewLine()) {
        out.println();
      }
      out.print("}");
    }
  }
  /**
   * @aspect GenericMethods
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\GenericMethods.jrag:89
   */
  public ParConstructorDecl newParConstructorDecl(Collection<TypeDecl> typeArguments) {
    ParConstructorDecl constructorDecl = typeArguments.isEmpty()
        ? new RawConstructorDecl()
        : new ParConstructorDecl();

    // Adding a link to GenericConstructorDecl to be used during substitution
    // instead of the not yet existing parent link.
    constructorDecl.setGenericConstructorDecl(this);

    List<Access> list = new List<Access>();
    if (typeArguments.isEmpty()) {
      GenericConstructorDecl original = original();
      for (int i = 0; i < original.getNumTypeParameter(); i++) {
        list.add(original.getTypeParameter(i).erasure().createBoundAccess());
      }
    } else {
      for (TypeDecl arg : typeArguments) {
        list.add(arg.createBoundAccess());
      }
    }
    constructorDecl.setTypeArgumentList(list);
    constructorDecl.setModifiers((Modifiers) getModifiers().treeCopyNoTransform());
    constructorDecl.setID(getID());
    constructorDecl.setParameterList(getParameterList().substitute(constructorDecl));
    constructorDecl.setExceptionList(getExceptionList().substitute(constructorDecl));
    return constructorDecl;
  }
  /**
   * @aspect LookupParTypeDecl
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:1386
   */
  public GenericConstructorDecl original;
  /**
   * @declaredat ASTNode:1
   */
  public GenericConstructorDecl() {
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
    children = new ASTNode[7];
    setChild(new List(), 1);
    setChild(new List(), 2);
    setChild(new Opt(), 3);
    setChild(new List(), 5);
  }
  /**
   * @declaredat ASTNode:17
   */
  public GenericConstructorDecl(Modifiers p0, String p1, List<ParameterDeclaration> p2, List<Access> p3, Opt<Stmt> p4, Block p5, List<TypeVariable> p6) {
    setChild(p0, 0);
    setID(p1);
    setChild(p2, 1);
    setChild(p3, 2);
    setChild(p4, 3);
    setChild(p5, 4);
    setChild(p6, 5);
  }
  /**
   * @declaredat ASTNode:26
   */
  public GenericConstructorDecl(Modifiers p0, beaver.Symbol p1, List<ParameterDeclaration> p2, List<Access> p3, Opt<Stmt> p4, Block p5, List<TypeVariable> p6) {
    setChild(p0, 0);
    setID(p1);
    setChild(p2, 1);
    setChild(p3, 2);
    setChild(p4, 3);
    setChild(p5, 4);
    setChild(p6, 5);
  }
  /**
   * @apilevel low-level
   * @declaredat ASTNode:38
   */
  protected int numChildren() {
    return 6;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:44
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:50
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    lookupParConstructorDecl_Collection_TypeDecl__reset();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:57
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:63
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:69
   */
  public GenericConstructorDecl clone() throws CloneNotSupportedException {
    GenericConstructorDecl node = (GenericConstructorDecl) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:76
   */
  public GenericConstructorDecl copy() {
    try {
      GenericConstructorDecl node = (GenericConstructorDecl) clone();
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
   * @declaredat ASTNode:95
   */
  @Deprecated
  public GenericConstructorDecl fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:105
   */
  public GenericConstructorDecl treeCopyNoTransform() {
    GenericConstructorDecl tree = (GenericConstructorDecl) copy();
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        switch (i) {
        case 6:
          tree.children[i] = null;
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
   * @declaredat ASTNode:130
   */
  public GenericConstructorDecl treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:137
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenString_ID == ((GenericConstructorDecl)node).tokenString_ID);    
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
   * Replaces the Parameter list.
   * @param list The new list node to be used as the Parameter list.
   * @apilevel high-level
   */
  public void setParameterList(List<ParameterDeclaration> list) {
    setChild(list, 1);
  }
  /**
   * Retrieves the number of children in the Parameter list.
   * @return Number of children in the Parameter list.
   * @apilevel high-level
   */
  public int getNumParameter() {
    return getParameterList().getNumChild();
  }
  /**
   * Retrieves the number of children in the Parameter list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the Parameter list.
   * @apilevel low-level
   */
  public int getNumParameterNoTransform() {
    return getParameterListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the Parameter list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the Parameter list.
   * @apilevel high-level
   */
  public ParameterDeclaration getParameter(int i) {
    return (ParameterDeclaration) getParameterList().getChild(i);
  }
  /**
   * Check whether the Parameter list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasParameter() {
    return getParameterList().getNumChild() != 0;
  }
  /**
   * Append an element to the Parameter list.
   * @param node The element to append to the Parameter list.
   * @apilevel high-level
   */
  public void addParameter(ParameterDeclaration node) {
    List<ParameterDeclaration> list = (parent == null) ? getParameterListNoTransform() : getParameterList();
    list.addChild(node);
  }
  /**
   * @apilevel low-level
   */
  public void addParameterNoTransform(ParameterDeclaration node) {
    List<ParameterDeclaration> list = getParameterListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the Parameter list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setParameter(ParameterDeclaration node, int i) {
    List<ParameterDeclaration> list = getParameterList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the Parameter list.
   * @return The node representing the Parameter list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="Parameter")
  public List<ParameterDeclaration> getParameterList() {
    List<ParameterDeclaration> list = (List<ParameterDeclaration>) getChild(1);
    return list;
  }
  /**
   * Retrieves the Parameter list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the Parameter list.
   * @apilevel low-level
   */
  public List<ParameterDeclaration> getParameterListNoTransform() {
    return (List<ParameterDeclaration>) getChildNoTransform(1);
  }
  /**
   * Retrieves the Parameter list.
   * @return The node representing the Parameter list.
   * @apilevel high-level
   */
  public List<ParameterDeclaration> getParameters() {
    return getParameterList();
  }
  /**
   * Retrieves the Parameter list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the Parameter list.
   * @apilevel low-level
   */
  public List<ParameterDeclaration> getParametersNoTransform() {
    return getParameterListNoTransform();
  }
  /**
   * Replaces the Exception list.
   * @param list The new list node to be used as the Exception list.
   * @apilevel high-level
   */
  public void setExceptionList(List<Access> list) {
    setChild(list, 2);
  }
  /**
   * Retrieves the number of children in the Exception list.
   * @return Number of children in the Exception list.
   * @apilevel high-level
   */
  public int getNumException() {
    return getExceptionList().getNumChild();
  }
  /**
   * Retrieves the number of children in the Exception list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the Exception list.
   * @apilevel low-level
   */
  public int getNumExceptionNoTransform() {
    return getExceptionListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the Exception list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the Exception list.
   * @apilevel high-level
   */
  public Access getException(int i) {
    return (Access) getExceptionList().getChild(i);
  }
  /**
   * Check whether the Exception list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasException() {
    return getExceptionList().getNumChild() != 0;
  }
  /**
   * Append an element to the Exception list.
   * @param node The element to append to the Exception list.
   * @apilevel high-level
   */
  public void addException(Access node) {
    List<Access> list = (parent == null) ? getExceptionListNoTransform() : getExceptionList();
    list.addChild(node);
  }
  /**
   * @apilevel low-level
   */
  public void addExceptionNoTransform(Access node) {
    List<Access> list = getExceptionListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the Exception list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setException(Access node, int i) {
    List<Access> list = getExceptionList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the Exception list.
   * @return The node representing the Exception list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="Exception")
  public List<Access> getExceptionList() {
    List<Access> list = (List<Access>) getChild(2);
    return list;
  }
  /**
   * Retrieves the Exception list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the Exception list.
   * @apilevel low-level
   */
  public List<Access> getExceptionListNoTransform() {
    return (List<Access>) getChildNoTransform(2);
  }
  /**
   * Retrieves the Exception list.
   * @return The node representing the Exception list.
   * @apilevel high-level
   */
  public List<Access> getExceptions() {
    return getExceptionList();
  }
  /**
   * Retrieves the Exception list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the Exception list.
   * @apilevel low-level
   */
  public List<Access> getExceptionsNoTransform() {
    return getExceptionListNoTransform();
  }
  /**
   * Replaces the optional node for the ParsedConstructorInvocation child. This is the <code>Opt</code>
   * node containing the child ParsedConstructorInvocation, not the actual child!
   * @param opt The new node to be used as the optional node for the ParsedConstructorInvocation child.
   * @apilevel low-level
   */
  public void setParsedConstructorInvocationOpt(Opt<Stmt> opt) {
    setChild(opt, 3);
  }
  /**
   * Replaces the (optional) ParsedConstructorInvocation child.
   * @param node The new node to be used as the ParsedConstructorInvocation child.
   * @apilevel high-level
   */
  public void setParsedConstructorInvocation(Stmt node) {
    getParsedConstructorInvocationOpt().setChild(node, 0);
  }
  /**
   * Check whether the optional ParsedConstructorInvocation child exists.
   * @return {@code true} if the optional ParsedConstructorInvocation child exists, {@code false} if it does not.
   * @apilevel high-level
   */
  public boolean hasParsedConstructorInvocation() {
    return getParsedConstructorInvocationOpt().getNumChild() != 0;
  }
  /**
   * Retrieves the (optional) ParsedConstructorInvocation child.
   * @return The ParsedConstructorInvocation child, if it exists. Returns {@code null} otherwise.
   * @apilevel low-level
   */
  public Stmt getParsedConstructorInvocation() {
    return (Stmt) getParsedConstructorInvocationOpt().getChild(0);
  }
  /**
   * Retrieves the optional node for the ParsedConstructorInvocation child. This is the <code>Opt</code> node containing the child ParsedConstructorInvocation, not the actual child!
   * @return The optional node for child the ParsedConstructorInvocation child.
   * @apilevel low-level
   */
  @ASTNodeAnnotation.OptChild(name="ParsedConstructorInvocation")
  public Opt<Stmt> getParsedConstructorInvocationOpt() {
    return (Opt<Stmt>) getChild(3);
  }
  /**
   * Retrieves the optional node for child ParsedConstructorInvocation. This is the <code>Opt</code> node containing the child ParsedConstructorInvocation, not the actual child!
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The optional node for child ParsedConstructorInvocation.
   * @apilevel low-level
   */
  public Opt<Stmt> getParsedConstructorInvocationOptNoTransform() {
    return (Opt<Stmt>) getChildNoTransform(3);
  }
  /**
   * Replaces the Block child.
   * @param node The new node to replace the Block child.
   * @apilevel high-level
   */
  public void setBlock(Block node) {
    setChild(node, 4);
  }
  /**
   * Retrieves the Block child.
   * @return The current node used as the Block child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Block")
  public Block getBlock() {
    return (Block) getChild(4);
  }
  /**
   * Retrieves the Block child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Block child.
   * @apilevel low-level
   */
  public Block getBlockNoTransform() {
    return (Block) getChildNoTransform(4);
  }
  /**
   * Replaces the TypeParameter list.
   * @param list The new list node to be used as the TypeParameter list.
   * @apilevel high-level
   */
  public void setTypeParameterList(List<TypeVariable> list) {
    setChild(list, 5);
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
    List<TypeVariable> list = (List<TypeVariable>) getChild(5);
    return list;
  }
  /**
   * Retrieves the TypeParameter list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the TypeParameter list.
   * @apilevel low-level
   */
  public List<TypeVariable> getTypeParameterListNoTransform() {
    return (List<TypeVariable>) getChildNoTransform(5);
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
   * Retrieves the ImplicitConstructorInvocation child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the ImplicitConstructorInvocation child.
   * @apilevel low-level
   */
  public Stmt getImplicitConstructorInvocationNoTransform() {
    return (Stmt) getChildNoTransform(6);
  }
  /**
   * Retrieves the child position of the optional child ImplicitConstructorInvocation.
   * @return The the child position of the optional child ImplicitConstructorInvocation.
   * @apilevel low-level
   */
  protected int getImplicitConstructorInvocationChildPosition() {
    return 6;
  }
  /**
   * @apilevel internal
   */
  protected List lookupParConstructorDecl_Collection_TypeDecl__list;
  /**
   * @apilevel internal
   */
  protected java.util.Map lookupParConstructorDecl_Collection_TypeDecl__values;
  /**
   * @apilevel internal
   */
  private void lookupParConstructorDecl_Collection_TypeDecl__reset() {
    lookupParConstructorDecl_Collection_TypeDecl__values = null;
    lookupParConstructorDecl_Collection_TypeDecl__list = null;
  }
  @ASTNodeAnnotation.Attribute
  public ParConstructorDecl lookupParConstructorDecl(Collection<TypeDecl> typeArguments) {
    Object _parameters = typeArguments;
    if (lookupParConstructorDecl_Collection_TypeDecl__values == null) lookupParConstructorDecl_Collection_TypeDecl__values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (lookupParConstructorDecl_Collection_TypeDecl__values.containsKey(_parameters)) {
      return (ParConstructorDecl) lookupParConstructorDecl_Collection_TypeDecl__values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    ParConstructorDecl lookupParConstructorDecl_Collection_TypeDecl__value = newParConstructorDecl(typeArguments);
    if (lookupParConstructorDecl_Collection_TypeDecl__list == null) {
      lookupParConstructorDecl_Collection_TypeDecl__list = new List();
      lookupParConstructorDecl_Collection_TypeDecl__list.is$Final = true;
      lookupParConstructorDecl_Collection_TypeDecl__list.setParent(this);
    }
    lookupParConstructorDecl_Collection_TypeDecl__list.add(lookupParConstructorDecl_Collection_TypeDecl__value);
    if (lookupParConstructorDecl_Collection_TypeDecl__value != null) {
      lookupParConstructorDecl_Collection_TypeDecl__value = (ParConstructorDecl) lookupParConstructorDecl_Collection_TypeDecl__list.getChild(lookupParConstructorDecl_Collection_TypeDecl__list.numChildren-1);
      lookupParConstructorDecl_Collection_TypeDecl__value.is$Final = true;
    }
    if (true) {
      lookupParConstructorDecl_Collection_TypeDecl__values.put(_parameters, lookupParConstructorDecl_Collection_TypeDecl__value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return lookupParConstructorDecl_Collection_TypeDecl__value;
  }
  /**
   * @attribute syn
   * @aspect GenericMethodsNameAnalysis
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\GenericMethods.jrag:198
   */
  @ASTNodeAnnotation.Attribute
  public SimpleSet localLookupType(String name) {
    {
        for (int i = 0; i < getNumTypeParameter(); i++) {
          if (original().getTypeParameter(i).name().equals(name)) {
            return SimpleSet.emptySet.add(original().getTypeParameter(i));
          }
        }
        return SimpleSet.emptySet;
      }
  }
  @ASTNodeAnnotation.Attribute
  public GenericConstructorDecl original() {
    GenericConstructorDecl original_value = original != null ? original : this;

    return original_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isGeneric() {
    boolean isGeneric_value = true;

    return isGeneric_value;
  }
  @ASTNodeAnnotation.Attribute
  public GenericConstructorDecl genericDecl() {
    GenericConstructorDecl genericDecl_value = this;

    return genericDecl_value;
  }
  @ASTNodeAnnotation.Attribute
  public List<TypeVariable> typeParameters() {
    List<TypeVariable> typeParameters_value = getTypeParameterList();

    return typeParameters_value;
  }
  /**
   * @attribute inh
   * @aspect GenericMethodsNameAnalysis
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\GenericMethods.jrag:197
   */
  @ASTNodeAnnotation.Attribute
  public SimpleSet lookupType(String name) {
    SimpleSet lookupType_String_value = getParent().Define_lookupType(this, null, name);

    return lookupType_String_value;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\SyntacticClassification.jrag:36
   * @apilevel internal
   */
  public NameType Define_nameType(ASTNode caller, ASTNode child) {
    if (caller == getTypeParameterListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\GenericMethods.jrag:195
      int childIndex = caller.getIndexOfChild(child);
      return NameType.TYPE_NAME;
    }
    else {
      return super.Define_nameType(caller, child);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\GenericMethods.jrag:197
   * @apilevel internal
   */
  public SimpleSet Define_lookupType(ASTNode caller, ASTNode child, String name) {
    int childIndex = this.getIndexOfChild(caller);
    return localLookupType(name).isEmpty() ? lookupType(name) : localLookupType(name);
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TypeVariablePositions.jrag:29
   * @apilevel internal
   */
  public int Define_typeVarPosition(ASTNode caller, ASTNode child) {
    if (caller == getTypeParameterListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TypeVariablePositions.jrag:40
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
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TypeVariablePositions.jrag:41
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
