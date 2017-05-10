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
 * A parameter declaration as used in either method parameter lists
 * or as a catch clause parameter.
 * @ast node
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\grammar\\Java.ast:88
 * @production ParameterDeclaration : {@link ASTNode} ::= <span class="component">{@link Modifiers}</span> <span class="component">TypeAccess:{@link Access}</span> <span class="component">&lt;ID:String&gt;</span>;

 */
public class ParameterDeclaration extends ASTNode<ASTNode> implements Cloneable, SimpleSet, Iterator, Variable {
  /**
   * @aspect Java4PrettyPrint
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\PrettyPrint.jadd:413
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print(getModifiers());
    out.print(getTypeAccess());
    out.print(" ");
    out.print(getID());
  }
  /**
   * @aspect DataStructures
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DataStructures.jrag:141
   */
  public SimpleSet add(Object o) {
    return new SimpleSetImpl().add(this).add(o);
  }
  /**
   * @aspect DataStructures
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DataStructures.jrag:145
   */
  public boolean isSingleton() { return true; }
  /**
   * @aspect DataStructures
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DataStructures.jrag:146
   */
  public boolean isSingleton(Object o) { return contains(o); }
  /**
   * @aspect DataStructures
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DataStructures.jrag:149
   */
  private ParameterDeclaration iterElem;
  /**
   * @aspect DataStructures
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DataStructures.jrag:150
   */
  public Iterator iterator() { iterElem = this; return this; }
  /**
   * @aspect DataStructures
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DataStructures.jrag:151
   */
  public boolean hasNext() { return iterElem != null; }
  /**
   * @aspect DataStructures
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DataStructures.jrag:152
   */
  public Object next() { Object o = iterElem; iterElem = null; return o; }
  /**
   * @aspect DataStructures
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DataStructures.jrag:153
   */
  public void remove() { throw new UnsupportedOperationException(); }
  /**
   * @aspect NodeConstructors
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\NodeConstructors.jrag:32
   */
  public ParameterDeclaration(Access type, String name) {
    this(new Modifiers(new List()), type, name);
  }
  /**
   * @aspect NodeConstructors
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\NodeConstructors.jrag:35
   */
  public ParameterDeclaration(TypeDecl type, String name) {
    this(new Modifiers(new List()), type.createQualifiedAccess(), name);
  }
  /**
   * @declaredat ASTNode:1
   */
  public ParameterDeclaration() {
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
  }
  /**
   * @declaredat ASTNode:13
   */
  public ParameterDeclaration(Modifiers p0, Access p1, String p2) {
    setChild(p0, 0);
    setChild(p1, 1);
    setID(p2);
  }
  /**
   * @declaredat ASTNode:18
   */
  public ParameterDeclaration(Modifiers p0, Access p1, beaver.Symbol p2) {
    setChild(p0, 0);
    setChild(p1, 1);
    setID(p2);
  }
  /**
   * @apilevel low-level
   * @declaredat ASTNode:26
   */
  protected int numChildren() {
    return 2;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:32
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:38
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    type_reset();
    sourceVariableDecl_reset();
    throwTypes_reset();
    inferredReferenceAccess_TypeAccess_reset();
    enclosingLambda_reset();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:49
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:55
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:61
   */
  public ParameterDeclaration clone() throws CloneNotSupportedException {
    ParameterDeclaration node = (ParameterDeclaration) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:68
   */
  public ParameterDeclaration copy() {
    try {
      ParameterDeclaration node = (ParameterDeclaration) clone();
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
   * @declaredat ASTNode:87
   */
  @Deprecated
  public ParameterDeclaration fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:97
   */
  public ParameterDeclaration treeCopyNoTransform() {
    ParameterDeclaration tree = (ParameterDeclaration) copy();
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
   * @declaredat ASTNode:117
   */
  public ParameterDeclaration treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:124
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenString_ID == ((ParameterDeclaration)node).tokenString_ID);    
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
   * Replaces the TypeAccess child.
   * @param node The new node to replace the TypeAccess child.
   * @apilevel high-level
   */
  public void setTypeAccess(Access node) {
    setChild(node, 1);
  }
  /**
   * Retrieves the TypeAccess child.
   * @return The current node used as the TypeAccess child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="TypeAccess")
  public Access getTypeAccess() {
    return (Access) getChild(1);
  }
  /**
   * Retrieves the TypeAccess child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the TypeAccess child.
   * @apilevel low-level
   */
  public Access getTypeAccessNoTransform() {
    return (Access) getChildNoTransform(1);
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
   * @aspect MultiCatch
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\MultiCatch.jrag:216
   */
   
  public void refined_MultiCatch_ParameterDeclaration_nameCheck() {
    SimpleSet decls = outerScope().lookupVariable(name());
    for (Iterator iter = decls.iterator(); iter.hasNext(); ) {
      Variable var = (Variable) iter.next();
      if (var instanceof VariableDeclaration) {
        VariableDeclaration decl = (VariableDeclaration) var;
        if (decl.enclosingBodyDecl() == enclosingBodyDecl()) {
          errorf("duplicate declaration of parameter %s", name());
        }
      } else if (var instanceof ParameterDeclaration) {
        ParameterDeclaration decl = (ParameterDeclaration) var;
        if (decl.enclosingBodyDecl() == enclosingBodyDecl()) {
          errorf("duplicate declaration of parameter %s", name());
        }
      } else if (var instanceof CatchParameterDeclaration) {
        CatchParameterDeclaration decl = (CatchParameterDeclaration) var;
        if (decl.enclosingBodyDecl() == enclosingBodyDecl()) {
          errorf("duplicate declaration of parameter %s", name());
        }
      }
    }

    // 8.4.1
    if (!lookupVariable(name()).contains(this)) {
      errorf("duplicate declaration of parameter %s", name());
    }
  }
  /**
   * @aspect Java8NameCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\NameCheck.jrag:105
   */
   
  public void nameCheck() {
    SimpleSet decls = outerScope().lookupVariable(name());
    for (Iterator iter = decls.iterator(); iter.hasNext(); ) {
      Variable var = (Variable) iter.next();
      if (var instanceof VariableDeclaration) {
        VariableDeclaration decl = (VariableDeclaration) var;
        if (decl.enclosingBodyDecl() == enclosingBodyDecl()) {
          errorf("duplicate declaration of parameter %s", name());
        }
      } else if (var instanceof ParameterDeclaration) {
        ParameterDeclaration decl = (ParameterDeclaration) var;
        if (decl.enclosingBodyDecl() == enclosingBodyDecl()) {
          errorf("duplicate declaration of parameter %s", name());
        }
      } else if (var instanceof InferredParameterDeclaration) {
        InferredParameterDeclaration decl = (InferredParameterDeclaration) var;
        if (decl.enclosingBodyDecl() == enclosingBodyDecl()) {
          errorf("duplicate declaration of parameter %s", name());
        }
      } else if (var instanceof CatchParameterDeclaration) {
        CatchParameterDeclaration decl = (CatchParameterDeclaration) var;
        if (decl.enclosingBodyDecl() == enclosingBodyDecl()) {
          errorf("duplicate declaration of parameter %s", name());
        }
      }
    }

    // 8.4.1
    if (!lookupVariable(name()).contains(this)) {
      errorf("duplicate declaration of parameter %s", name());
    }
  }
  @ASTNodeAnnotation.Attribute
  public int size() {
    int size_value = 1;

    return size_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isEmpty() {
    boolean isEmpty_value = false;

    return isEmpty_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean contains(Object o) {
    boolean contains_Object_value = this == o;

    return contains_Object_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isSynthetic() {
    boolean isSynthetic_value = getModifiers().isSynthetic();

    return isSynthetic_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean hasModifiers() {
    boolean hasModifiers_value = getModifiers().getNumModifier() > 0;

    return hasModifiers_value;
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
    type_value = getTypeAccess().type();
    if (isFinal && num == state().boundariesCrossed) {
      type_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return type_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isParameter() {
    boolean isParameter_value = true;

    return isParameter_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isClassVariable() {
    boolean isClassVariable_value = false;

    return isClassVariable_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isInstanceVariable() {
    boolean isInstanceVariable_value = false;

    return isInstanceVariable_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isLocalVariable() {
    boolean isLocalVariable_value = false;

    return isLocalVariable_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isFinal() {
    boolean isFinal_value = getModifiers().isFinal();

    return isFinal_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isVolatile() {
    boolean isVolatile_value = getModifiers().isVolatile();

    return isVolatile_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isBlank() {
    boolean isBlank_value = true;

    return isBlank_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isStatic() {
    boolean isStatic_value = false;

    return isStatic_value;
  }
  @ASTNodeAnnotation.Attribute
  public String name() {
    String name_value = getID();

    return name_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean hasInit() {
    boolean hasInit_value = false;

    return hasInit_value;
  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\VariableDeclaration.jrag:102
   */
  @ASTNodeAnnotation.Attribute
  public Expr getInit() {
    { throw new UnsupportedOperationException(); }
  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\VariableDeclaration.jrag:103
   */
  @ASTNodeAnnotation.Attribute
  public Constant constant() {
    { throw new UnsupportedOperationException(); }
  }
  /**
   * @apilevel internal
   */
  protected boolean sourceVariableDecl_computed = false;
  /**
   * @apilevel internal
   */
  protected Variable sourceVariableDecl_value;
  /**
   * @apilevel internal
   */
  private void sourceVariableDecl_reset() {
    sourceVariableDecl_computed = false;
    sourceVariableDecl_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public Variable sourceVariableDecl() {
    ASTNode$State state = state();
    if (sourceVariableDecl_computed) {
      return sourceVariableDecl_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    sourceVariableDecl_value = this;
    if (isFinal && num == state().boundariesCrossed) {
      sourceVariableDecl_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return sourceVariableDecl_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isVariableArity() {
    boolean isVariableArity_value = false;

    return isVariableArity_value;
  }
  @ASTNodeAnnotation.Attribute
  public ParameterDeclaration substituted(Collection<TypeVariable> original, List<TypeVariable> substitution) {
    ParameterDeclaration substituted_Collection_TypeVariable__List_TypeVariable__value = new ParameterDeclaration(
            (Modifiers) getModifiers().treeCopyNoTransform(),
            getTypeAccess().substituted(original, substitution),
            getID());

    return substituted_Collection_TypeVariable__List_TypeVariable__value;
  }
  /**
   * @apilevel internal
   */
  protected boolean throwTypes_computed = false;
  /**
   * @apilevel internal
   */
  protected Collection<TypeDecl> throwTypes_value;
  /**
   * @apilevel internal
   */
  private void throwTypes_reset() {
    throwTypes_computed = false;
    throwTypes_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public Collection<TypeDecl> throwTypes() {
    ASTNode$State state = state();
    if (throwTypes_computed) {
      return throwTypes_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    throwTypes_value = throwTypes_compute();
    if (isFinal && num == state().boundariesCrossed) {
      throwTypes_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return throwTypes_value;
  }
  /**
   * @apilevel internal
   */
  private Collection<TypeDecl> throwTypes_compute() {
      if (isCatchParam() && isEffectivelyFinal()) {
        // The catch parameter must be final to refine the throw type.
        return catchClause().caughtExceptions();
      } else {
        return Collections.singleton(type());
      }
    }
  @ASTNodeAnnotation.Attribute
  public boolean isEffectivelyFinal() {
    boolean isEffectivelyFinal_value = isFinal() || !inhModifiedInScope(this);

    return isEffectivelyFinal_value;
  }
  /**
   * @apilevel internal
   */
  protected List inferredReferenceAccess_TypeAccess_list;
  /**
   * @apilevel internal
   */
  protected java.util.Map inferredReferenceAccess_TypeAccess_values;
  /**
   * @apilevel internal
   */
  private void inferredReferenceAccess_TypeAccess_reset() {
    inferredReferenceAccess_TypeAccess_values = null;
    inferredReferenceAccess_TypeAccess_list = null;
  }
  @ASTNodeAnnotation.Attribute
  public ParTypeAccess inferredReferenceAccess(TypeAccess typeAccess) {
    Object _parameters = typeAccess;
    if (inferredReferenceAccess_TypeAccess_values == null) inferredReferenceAccess_TypeAccess_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (inferredReferenceAccess_TypeAccess_values.containsKey(_parameters)) {
      return (ParTypeAccess) inferredReferenceAccess_TypeAccess_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    ParTypeAccess inferredReferenceAccess_TypeAccess_value = inferredReferenceAccess_compute(typeAccess);
    if (inferredReferenceAccess_TypeAccess_list == null) {
      inferredReferenceAccess_TypeAccess_list = new List();
      inferredReferenceAccess_TypeAccess_list.is$Final = true;
      inferredReferenceAccess_TypeAccess_list.setParent(this);
    }
    inferredReferenceAccess_TypeAccess_list.add(inferredReferenceAccess_TypeAccess_value);
    if (inferredReferenceAccess_TypeAccess_value != null) {
      inferredReferenceAccess_TypeAccess_value = (ParTypeAccess) inferredReferenceAccess_TypeAccess_list.getChild(inferredReferenceAccess_TypeAccess_list.numChildren-1);
      inferredReferenceAccess_TypeAccess_value.is$Final = true;
    }
    if (true) {
      inferredReferenceAccess_TypeAccess_values.put(_parameters, inferredReferenceAccess_TypeAccess_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return inferredReferenceAccess_TypeAccess_value;
  }
  /**
   * @apilevel internal
   */
  private ParTypeAccess inferredReferenceAccess_compute(TypeAccess typeAccess) {
      if (!(getTypeAccess() instanceof ParTypeAccess)) {
        return new ParTypeAccess((TypeAccess) typeAccess.treeCopyNoTransform(), new List<Access>());
      }
      ParTypeAccess parTypeAccess = (ParTypeAccess)getTypeAccess();
      return new ParTypeAccess((TypeAccess) typeAccess.treeCopyNoTransform(),
          (List<Access>) parTypeAccess.getTypeArgumentList().treeCopyNoTransform());
    }
  /**
   * @attribute inh
   * @aspect VariableScope
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupVariable.jrag:43
   */
  @ASTNodeAnnotation.Attribute
  public SimpleSet lookupVariable(String name) {
    SimpleSet lookupVariable_String_value = getParent().Define_lookupVariable(this, null, name);

    return lookupVariable_String_value;
  }
  /**
   * @attribute inh
   * @aspect NameCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\NameCheck.jrag:364
   */
  @ASTNodeAnnotation.Attribute
  public VariableScope outerScope() {
    VariableScope outerScope_value = getParent().Define_outerScope(this, null);

    return outerScope_value;
  }
  /**
   * @attribute inh
   * @aspect NameCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\NameCheck.jrag:429
   */
  @ASTNodeAnnotation.Attribute
  public BodyDecl enclosingBodyDecl() {
    BodyDecl enclosingBodyDecl_value = getParent().Define_enclosingBodyDecl(this, null);

    return enclosingBodyDecl_value;
  }
  /**
   * @attribute inh
   * @aspect NestedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeAnalysis.jrag:641
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl hostType() {
    TypeDecl hostType_value = getParent().Define_hostType(this, null);

    return hostType_value;
  }
  /**
   * @attribute inh
   * @aspect Variables
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\VariableDeclaration.jrag:73
   */
  @ASTNodeAnnotation.Attribute
  public boolean isMethodParameter() {
    boolean isMethodParameter_value = getParent().Define_isMethodParameter(this, null);

    return isMethodParameter_value;
  }
  /**
   * @attribute inh
   * @aspect Variables
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\VariableDeclaration.jrag:74
   */
  @ASTNodeAnnotation.Attribute
  public boolean isConstructorParameter() {
    boolean isConstructorParameter_value = getParent().Define_isConstructorParameter(this, null);

    return isConstructorParameter_value;
  }
  /**
   * @attribute inh
   * @aspect Variables
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\VariableDeclaration.jrag:75
   */
  @ASTNodeAnnotation.Attribute
  public boolean isExceptionHandlerParameter() {
    boolean isExceptionHandlerParameter_value = getParent().Define_isExceptionHandlerParameter(this, null);

    return isExceptionHandlerParameter_value;
  }
  /**
   * @return true if the variable var is modified in the local scope
   * @attribute inh
   * @aspect PreciseRethrow
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\PreciseRethrow.jrag:76
   */
  @ASTNodeAnnotation.Attribute
  public boolean inhModifiedInScope(Variable var) {
    boolean inhModifiedInScope_Variable_value = getParent().Define_inhModifiedInScope(this, null, var);

    return inhModifiedInScope_Variable_value;
  }
  /**
   * @return true if this is the parameter declaration of a catch clause
   * @attribute inh
   * @aspect PreciseRethrow
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\PreciseRethrow.jrag:206
   */
  @ASTNodeAnnotation.Attribute
  public boolean isCatchParam() {
    boolean isCatchParam_value = getParent().Define_isCatchParam(this, null);

    return isCatchParam_value;
  }
  /**
   * @attribute inh
   * @aspect PreciseRethrow
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\PreciseRethrow.jrag:212
   */
  @ASTNodeAnnotation.Attribute
  public CatchClause catchClause() {
    CatchClause catchClause_value = getParent().Define_catchClause(this, null);

    return catchClause_value;
  }
  /**
   * @attribute inh
   * @aspect EnclosingLambda
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\EnclosingLambda.jrag:34
   */
  @ASTNodeAnnotation.Attribute
  public LambdaExpr enclosingLambda() {
    ASTNode$State state = state();
    if (enclosingLambda_computed) {
      return enclosingLambda_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    enclosingLambda_value = getParent().Define_enclosingLambda(this, null);
    if (isFinal && num == state().boundariesCrossed) {
      enclosingLambda_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return enclosingLambda_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean enclosingLambda_computed = false;
  /**
   * @apilevel internal
   */
  protected LambdaExpr enclosingLambda_value;
  /**
   * @apilevel internal
   */
  private void enclosingLambda_reset() {
    enclosingLambda_computed = false;
    enclosingLambda_value = null;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\Modifiers.jrag:423
   * @apilevel internal
   */
  public boolean Define_mayBeFinal(ASTNode caller, ASTNode child) {
    if (caller == getModifiersNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\Modifiers.jrag:329
      return true;
    }
    else {
      return getParent().Define_mayBeFinal(this, caller);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Annotations.jrag:96
   * @apilevel internal
   */
  public boolean Define_mayUseAnnotationTarget(ASTNode caller, ASTNode child, String name) {
    if (caller == getModifiersNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Annotations.jrag:110
      return name.equals("PARAMETER");
    }
    else {
      return getParent().Define_mayUseAnnotationTarget(this, caller, name);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\SyntacticClassification.jrag:36
   * @apilevel internal
   */
  public NameType Define_nameType(ASTNode caller, ASTNode child) {
    if (caller == getTypeAccessNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Enums.jrag:102
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
