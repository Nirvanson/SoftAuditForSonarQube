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
 * A catch parameter with disjunct exception type.
 * @ast node
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\grammar\\MultiCatch.ast:19
 * @production CatchParameterDeclaration : {@link ASTNode} ::= <span class="component">{@link Modifiers}</span> <span class="component">TypeAccess:{@link Access}*</span> <span class="component">&lt;ID:String&gt;</span>;

 */
public class CatchParameterDeclaration extends ASTNode<ASTNode> implements Cloneable, Variable, SimpleSet, Iterator {
  /**
   * @aspect Java7PrettyPrint
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\PrettyPrint.jadd:39
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print(getModifiers());
    out.join(getTypeAccessList(), new PrettyPrinter.Joiner() {
      @Override
      public void printSeparator(PrettyPrinter out) {
        out.print(" | ");
      }
    });
    out.print(" ");
    out.print(getID());
  }
  /**
   * @aspect MultiCatch
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\MultiCatch.jrag:85
   */
  public SimpleSet add(Object o) {
    return new SimpleSetImpl().add(this).add(o);
  }
  /**
   * @aspect MultiCatch
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\MultiCatch.jrag:89
   */
  public boolean isSingleton() { return true; }
  /**
   * @aspect MultiCatch
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\MultiCatch.jrag:90
   */
  public boolean isSingleton(Object o) { return contains(o); }
  /**
   * @aspect MultiCatch
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\MultiCatch.jrag:93
   */
  private CatchParameterDeclaration iterElem;
  /**
   * @aspect MultiCatch
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\MultiCatch.jrag:94
   */
  public Iterator iterator() { iterElem = this; return this; }
  /**
   * @aspect MultiCatch
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\MultiCatch.jrag:95
   */
  public boolean hasNext() { return iterElem != null; }
  /**
   * @aspect MultiCatch
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\MultiCatch.jrag:96
   */
  public Object next() { Object o = iterElem; iterElem = null; return o; }
  /**
   * @aspect MultiCatch
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\MultiCatch.jrag:97
   */
  public void remove() { throw new UnsupportedOperationException(); }
  /**
   * Type checking.
   * The types given in a disjunction type may not be
   * subtypes of each other.
   * @aspect MultiCatch
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\MultiCatch.jrag:131
   */
  public void typeCheck() {
    boolean pass = true;
    for (int i = 0; i < getNumTypeAccess(); ++i) {
      for (int j = 0; j < getNumTypeAccess(); ++j) {
        if (i == j) {
          continue;
        }
        TypeDecl t1 = getTypeAccess(i).type();
        TypeDecl t2 = getTypeAccess(j).type();
        if (t2.instanceOf(t1)) {
          errorf("%s is a subclass of %s, which is not allowed in multi-catch type alternatives",
              t2.fullName(), t1.fullName());
          pass = false;
        }
      }
    }
  }
  /**
   * @declaredat ASTNode:1
   */
  public CatchParameterDeclaration() {
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
  public CatchParameterDeclaration(Modifiers p0, List<Access> p1, String p2) {
    setChild(p0, 0);
    setChild(p1, 1);
    setID(p2);
  }
  /**
   * @declaredat ASTNode:19
   */
  public CatchParameterDeclaration(Modifiers p0, List<Access> p1, beaver.Symbol p2) {
    setChild(p0, 0);
    setChild(p1, 1);
    setID(p2);
  }
  /**
   * @apilevel low-level
   * @declaredat ASTNode:27
   */
  protected int numChildren() {
    return 2;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:33
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:39
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    sourceVariableDecl_reset();
    throwTypes_reset();
    isEffectivelyFinal_reset();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:48
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:54
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:60
   */
  public CatchParameterDeclaration clone() throws CloneNotSupportedException {
    CatchParameterDeclaration node = (CatchParameterDeclaration) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:67
   */
  public CatchParameterDeclaration copy() {
    try {
      CatchParameterDeclaration node = (CatchParameterDeclaration) clone();
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
   * @declaredat ASTNode:86
   */
  @Deprecated
  public CatchParameterDeclaration fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:96
   */
  public CatchParameterDeclaration treeCopyNoTransform() {
    CatchParameterDeclaration tree = (CatchParameterDeclaration) copy();
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
   * @declaredat ASTNode:116
   */
  public CatchParameterDeclaration treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:123
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenString_ID == ((CatchParameterDeclaration)node).tokenString_ID);    
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
   * Replaces the TypeAccess list.
   * @param list The new list node to be used as the TypeAccess list.
   * @apilevel high-level
   */
  public void setTypeAccessList(List<Access> list) {
    setChild(list, 1);
  }
  /**
   * Retrieves the number of children in the TypeAccess list.
   * @return Number of children in the TypeAccess list.
   * @apilevel high-level
   */
  public int getNumTypeAccess() {
    return getTypeAccessList().getNumChild();
  }
  /**
   * Retrieves the number of children in the TypeAccess list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the TypeAccess list.
   * @apilevel low-level
   */
  public int getNumTypeAccessNoTransform() {
    return getTypeAccessListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the TypeAccess list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the TypeAccess list.
   * @apilevel high-level
   */
  public Access getTypeAccess(int i) {
    return (Access) getTypeAccessList().getChild(i);
  }
  /**
   * Check whether the TypeAccess list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasTypeAccess() {
    return getTypeAccessList().getNumChild() != 0;
  }
  /**
   * Append an element to the TypeAccess list.
   * @param node The element to append to the TypeAccess list.
   * @apilevel high-level
   */
  public void addTypeAccess(Access node) {
    List<Access> list = (parent == null) ? getTypeAccessListNoTransform() : getTypeAccessList();
    list.addChild(node);
  }
  /**
   * @apilevel low-level
   */
  public void addTypeAccessNoTransform(Access node) {
    List<Access> list = getTypeAccessListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the TypeAccess list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setTypeAccess(Access node, int i) {
    List<Access> list = getTypeAccessList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the TypeAccess list.
   * @return The node representing the TypeAccess list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="TypeAccess")
  public List<Access> getTypeAccessList() {
    List<Access> list = (List<Access>) getChild(1);
    return list;
  }
  /**
   * Retrieves the TypeAccess list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the TypeAccess list.
   * @apilevel low-level
   */
  public List<Access> getTypeAccessListNoTransform() {
    return (List<Access>) getChildNoTransform(1);
  }
  /**
   * Retrieves the TypeAccess list.
   * @return The node representing the TypeAccess list.
   * @apilevel high-level
   */
  public List<Access> getTypeAccesss() {
    return getTypeAccessList();
  }
  /**
   * Retrieves the TypeAccess list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the TypeAccess list.
   * @apilevel low-level
   */
  public List<Access> getTypeAccesssNoTransform() {
    return getTypeAccessListNoTransform();
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
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\NameCheck.jrag:71
   */
   
  public void nameCheck() {
    SimpleSet decls = outerScope().lookupVariable(name());
    for (Iterator iter = decls.iterator(); iter.hasNext(); ) {
      Variable var = (Variable) iter.next();
      if (var instanceof VariableDeclaration) {
        VariableDeclaration decl = (VariableDeclaration) var;
        if (decl.enclosingBodyDecl() == enclosingBodyDecl()) {
          errorf("duplicate declaration of catch parameter %s", name());
        }
      } else if (var instanceof ParameterDeclaration) {
        ParameterDeclaration decl = (ParameterDeclaration) var;
        if (decl.enclosingBodyDecl() == enclosingBodyDecl()) {
          errorf("duplicate declaration of catch parameter %s", name());
        }
      } else if (var instanceof InferredParameterDeclaration) {
        InferredParameterDeclaration decl = (InferredParameterDeclaration) var;
        if (decl.enclosingBodyDecl() == enclosingBodyDecl()) {
          errorf("duplicate declaration of catch parameter %s", name());
        }
      } else if (var instanceof CatchParameterDeclaration) {
        CatchParameterDeclaration decl = (CatchParameterDeclaration) var;
        if (decl.enclosingBodyDecl() == enclosingBodyDecl()) {
          errorf("duplicate declaration of catch parameter %s", name());
        }
      }
    }

    // 8.4.1
    if (!lookupVariable(name()).contains(this)) {
      errorf("duplicate declaration of catch parameter %s", name());
    }
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
    boolean isFinal_value = true;

    return isFinal_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isVolatile() {
    boolean isVolatile_value = getModifiers().isVolatile();

    return isVolatile_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isBlank() {
    boolean isBlank_value = false;

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
   * @aspect MultiCatch
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\MultiCatch.jrag:64
   */
  @ASTNodeAnnotation.Attribute
  public Expr getInit() {
    {
        throw new UnsupportedOperationException();
      }
  }
  /**
   * @attribute syn
   * @aspect MultiCatch
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\MultiCatch.jrag:67
   */
  @ASTNodeAnnotation.Attribute
  public Constant constant() {
    {
        throw new UnsupportedOperationException();
      }
  }
  @ASTNodeAnnotation.Attribute
  public boolean isSynthetic() {
    boolean isSynthetic_value = getModifiers().isSynthetic();

    return isSynthetic_value;
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
  /**
   * A catch parameter declared with a disjunction type has the
   * effective type lub(t1, t2, ...)
   * 
   * @see "JLSv3 &sect;15.12.2.7"
   * @attribute syn
   * @aspect MultiCatch
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\MultiCatch.jrag:175
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl type() {
    {
        ArrayList<TypeDecl> list = new ArrayList<TypeDecl>();
        for (int i = 0; i < getNumTypeAccess(); i++)
          list.add(getTypeAccess(i).type());
        return lookupLUBType(list).lub();
      }
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
    throwTypes_value = catchClause().caughtExceptions();
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
  protected boolean isEffectivelyFinal_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean isEffectivelyFinal_value;
  /**
   * @apilevel internal
   */
  private void isEffectivelyFinal_reset() {
    isEffectivelyFinal_computed = false;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isEffectivelyFinal() {
    ASTNode$State state = state();
    if (isEffectivelyFinal_computed) {
      return isEffectivelyFinal_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    isEffectivelyFinal_value = true;
    if (isFinal && num == state().boundariesCrossed) {
      isEffectivelyFinal_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return isEffectivelyFinal_value;
  }
  /**
   * Inherit the lookupVariable attribute.
   * @attribute inh
   * @aspect MultiCatch
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\MultiCatch.jrag:36
   */
  @ASTNodeAnnotation.Attribute
  public SimpleSet lookupVariable(String name) {
    SimpleSet lookupVariable_String_value = getParent().Define_lookupVariable(this, null, name);

    return lookupVariable_String_value;
  }
  /**
   * @attribute inh
   * @aspect MultiCatch
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\MultiCatch.jrag:44
   */
  @ASTNodeAnnotation.Attribute
  public boolean isMethodParameter() {
    boolean isMethodParameter_value = getParent().Define_isMethodParameter(this, null);

    return isMethodParameter_value;
  }
  /**
   * @attribute inh
   * @aspect MultiCatch
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\MultiCatch.jrag:45
   */
  @ASTNodeAnnotation.Attribute
  public boolean isConstructorParameter() {
    boolean isConstructorParameter_value = getParent().Define_isConstructorParameter(this, null);

    return isConstructorParameter_value;
  }
  /**
   * @attribute inh
   * @aspect MultiCatch
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\MultiCatch.jrag:46
   */
  @ASTNodeAnnotation.Attribute
  public boolean isExceptionHandlerParameter() {
    boolean isExceptionHandlerParameter_value = getParent().Define_isExceptionHandlerParameter(this, null);

    return isExceptionHandlerParameter_value;
  }
  /**
   * @attribute inh
   * @aspect MultiCatch
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\MultiCatch.jrag:71
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl hostType() {
    TypeDecl hostType_value = getParent().Define_hostType(this, null);

    return hostType_value;
  }
  /**
   * @attribute inh
   * @aspect MultiCatch
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\MultiCatch.jrag:167
   */
  @ASTNodeAnnotation.Attribute
  public LUBType lookupLUBType(Collection bounds) {
    LUBType lookupLUBType_Collection_value = getParent().Define_lookupLUBType(this, null, bounds);

    return lookupLUBType_Collection_value;
  }
  /**
   * @attribute inh
   * @aspect MultiCatch
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\MultiCatch.jrag:182
   */
  @ASTNodeAnnotation.Attribute
  public VariableScope outerScope() {
    VariableScope outerScope_value = getParent().Define_outerScope(this, null);

    return outerScope_value;
  }
  /**
   * @attribute inh
   * @aspect MultiCatch
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\MultiCatch.jrag:183
   */
  @ASTNodeAnnotation.Attribute
  public BodyDecl enclosingBodyDecl() {
    BodyDecl enclosingBodyDecl_value = getParent().Define_enclosingBodyDecl(this, null);

    return enclosingBodyDecl_value;
  }
  /**
   * @attribute inh
   * @aspect PreciseRethrow
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\PreciseRethrow.jrag:213
   */
  @ASTNodeAnnotation.Attribute
  public CatchClause catchClause() {
    CatchClause catchClause_value = getParent().Define_catchClause(this, null);

    return catchClause_value;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\SyntacticClassification.jrag:36
   * @apilevel internal
   */
  public NameType Define_nameType(ASTNode caller, ASTNode child) {
    if (caller == getTypeAccessListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\MultiCatch.jrag:113
      int i = caller.getIndexOfChild(child);
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
