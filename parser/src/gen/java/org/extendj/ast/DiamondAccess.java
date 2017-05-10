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
 * Type access for a generic class with an empty type parameter list.
 * @ast node
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\grammar\\Diamond.ast:4
 * @production DiamondAccess : {@link Access} ::= <span class="component">TypeAccess:{@link Access}</span>;

 */
public class DiamondAccess extends Access implements Cloneable {
  /**
   * @aspect Java7PrettyPrint
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\PrettyPrint.jadd:35
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print(getTypeAccess());
    out.print("<>");
  }
  /**
   * @aspect Diamond
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Diamond.jrag:94
   */
  protected static SimpleSet mostSpecific(SimpleSet maxSpecific, MethodDecl decl) {
    if (maxSpecific.isEmpty()) {
      maxSpecific = maxSpecific.add(decl);
    } else {
      MethodDecl other = (MethodDecl) maxSpecific.iterator().next();
      if (decl.moreSpecificThan(other)) {
        maxSpecific = decl;
      } else if (!other.moreSpecificThan(decl)) {
        maxSpecific = maxSpecific.add(decl);
      }
    }
    return maxSpecific;
  }
  /**
   * Choose a constructor for the diamond operator using placeholder
   * methods.
   * @aspect Diamond
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Diamond.jrag:112
   */
  protected SimpleSet chooseConstructor() {
    ClassInstanceExpr instanceExpr = getClassInstanceExpr();
    TypeDecl type = getTypeAccess().type();

    assert instanceExpr != null;
    assert type instanceof ParClassDecl;

    GenericClassDecl genericType = (GenericClassDecl) ((ParClassDecl) type).genericDecl();

    List<StandInMethodDecl> placeholderMethods = genericType.getStandInMethodList();

    SimpleSet maxSpecific = SimpleSet.emptySet;
    Collection<MethodDecl> potentiallyApplicable = potentiallyApplicable(placeholderMethods);
    for (MethodDecl candidate : potentiallyApplicable) {
      if (applicableBySubtyping(instanceExpr, candidate)
          || applicableByMethodInvocationConversion(instanceExpr, candidate)
          || applicableByVariableArity(instanceExpr, candidate)) {
        maxSpecific = mostSpecific(maxSpecific, candidate);
      }
    }
    return maxSpecific;
  }
  /**
   * Select potentially applicable method declarations
   * from a set of candidates.
   * Type inference is applied to the (potentially) applicable candidates.
   * @aspect Diamond
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Diamond.jrag:238
   */
  protected Collection<MethodDecl> potentiallyApplicable(
      List<StandInMethodDecl> candidates) {
    Collection<MethodDecl> potentiallyApplicable = new LinkedList<MethodDecl>();
    for (GenericMethodDecl candidate : candidates) {
      if (potentiallyApplicable(candidate)) {
        MethodDecl decl = candidate.lookupParMethodDecl(
            inferTypeArguments(
                candidate.type(),
                candidate.getParameterList(),
                getClassInstanceExpr().getArgList(),
                candidate.getTypeParameterList()));
        potentiallyApplicable.add(decl);
      }
    }
    return potentiallyApplicable;
  }
  /**
   * Test if a method is applicable for this diamond access.
   * @param candidate candidate method
   * @return false if the candidate method is not applicable.
   * @aspect Diamond
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Diamond.jrag:260
   */
  protected boolean potentiallyApplicable(
      GenericMethodDecl candidate) {
    if (candidate.isVariableArity() && !(getClassInstanceExpr().arity() >= candidate.arity()-1)) {
      return false;
    }
    if (!candidate.isVariableArity() && !(getClassInstanceExpr().arity() == candidate.arity())) {
      return false;
    }

    java.util.List<TypeDecl> typeArgs = inferTypeArguments(
        candidate.type(),
        candidate.getParameterList(),
        getClassInstanceExpr().getArgList(),
        candidate.getTypeParameterList());
    Parameterization par = new SimpleParameterization(candidate.getTypeParameterList(), typeArgs);
    if (typeArgs.size() != 0) {
      if (candidate.getNumTypeParameter() != typeArgs.size()) {
        return false;
      }
      for (int i = 0; i < candidate.getNumTypeParameter(); i++) {
        if (!typeArgs.get(i).withinBounds(candidate.original().getTypeParameter(i), par)) {
          return false;
        }
      }
    }
    return true;
  }
  /**
   * @return true if the method is applicable by subtyping
   * @aspect Diamond
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Diamond.jrag:291
   */
  protected boolean applicableBySubtyping(ClassInstanceExpr expr, MethodDecl method) {
    if (method.getNumParameter() != expr.getNumArg()) {
      return false;
    }
    for (int i = 0; i < method.getNumParameter(); i++) {
      if (!expr.getArg(i).type().instanceOf(method.getParameter(i).type())) {
        return false;
      }
    }
    return true;
  }
  /**
   * @return true if the method is applicable by method invocation conversion
   * @aspect Diamond
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Diamond.jrag:306
   */
  protected boolean applicableByMethodInvocationConversion(
      ClassInstanceExpr expr, MethodDecl method) {
    if (method.getNumParameter() != expr.getNumArg()) {
      return false;
    }
    for (int i = 0; i < method.getNumParameter(); i++) {
      if (!expr.getArg(i).type().methodInvocationConversionTo(
          method.getParameter(i).type())) {
        return false;
      }
    }
    return true;
  }
  /**
   * @return true if the method is applicable by variable arity
   * @aspect Diamond
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Diamond.jrag:323
   */
  protected boolean applicableByVariableArity(
      ClassInstanceExpr expr, MethodDecl method) {
    for (int i = 0; i < method.getNumParameter() - 1; i++) {
      if (!expr.getArg(i).type().methodInvocationConversionTo(
          method.getParameter(i).type())) {
        return false;
      }
    }
    for (int i = method.getNumParameter() - 1; i < expr.getNumArg(); i++) {
      if (!expr.getArg(i).type().methodInvocationConversionTo(
          method.lastParameter().type().componentType())) {
        return false;
      }
    }
    return true;
  }
  /**
   * Checks if this diamond access is legal.
   * The diamond access is not legal if it either is part of an inner class
   * declaration, if it is used to access a non-generic type, or if it is
   * part of a call to a generic constructor with explicit type arguments.
   * @aspect Diamond
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Diamond.jrag:435
   */
  public void typeCheck() {
    if (isAnonymousDecl()) {
      error("the diamond operator can not be used with anonymous classes");
    }
    if (isExplicitGenericConstructorAccess()) {
      error("the diamond operator may not be used with generic "
          + "constructors with explicit type parameters");
    }
    if (getClassInstanceExpr() == null) {
      error("the diamond operator can only be used in class instance expressions");
    }
    if (!(getTypeAccess().type() instanceof ParClassDecl)) {
      error("the diamond operator can only be used to instantiate generic classes");
    }
  }
  /**
   * @declaredat ASTNode:1
   */
  public DiamondAccess() {
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
  public DiamondAccess(Access p0) {
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
    type_reset();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:38
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:44
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:50
   */
  public DiamondAccess clone() throws CloneNotSupportedException {
    DiamondAccess node = (DiamondAccess) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:57
   */
  public DiamondAccess copy() {
    try {
      DiamondAccess node = (DiamondAccess) clone();
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
   * @declaredat ASTNode:76
   */
  @Deprecated
  public DiamondAccess fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:86
   */
  public DiamondAccess treeCopyNoTransform() {
    DiamondAccess tree = (DiamondAccess) copy();
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
   * @declaredat ASTNode:106
   */
  public DiamondAccess treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:113
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
      TypeDecl accessType = getTypeAccess().type();
  
      if (isAnonymousDecl()) {
        return accessType;
      }
  
      if (getClassInstanceExpr() == null) {
        // It is an error if the DiamondAccess does not occurr
        // within a class instance creation expression, but this
        // error is handled in typeCheck.
        return accessType;
      }
  
      if (!(accessType instanceof ParClassDecl)) {
        // It is an error if the TypeDecl of a DiamondAccess is not
        // a generic type, but this error is handled in typeCheck.
        return accessType;
      }
  
      SimpleSet maxSpecific = chooseConstructor();
  
      if (maxSpecific.isEmpty()) {
        return getTypeAccess().type();
      }
  
      MethodDecl constructor = (MethodDecl) maxSpecific.iterator().next();
      return constructor.type();
    }
  @ASTNodeAnnotation.Attribute
  public boolean isDiamond() {
    boolean isDiamond_value = true;

    return isDiamond_value;
  }
  /**
   * @attribute inh
   * @aspect Diamond
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Diamond.jrag:90
   */
  @ASTNodeAnnotation.Attribute
  public ClassInstanceExpr getClassInstanceExpr() {
    ClassInstanceExpr getClassInstanceExpr_value = getParent().Define_getClassInstanceExpr(this, null);

    return getClassInstanceExpr_value;
  }
  /**
   * @return true if this access is part of an anonymous class declaration
   * @attribute inh
   * @aspect Diamond
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Diamond.jrag:401
   */
  @ASTNodeAnnotation.Attribute
  public boolean isAnonymousDecl() {
    boolean isAnonymousDecl_value = getParent().Define_isAnonymousDecl(this, null);

    return isAnonymousDecl_value;
  }
  /**
   * @return true if the Access is part of a generic constructor invocation
   * with explicit type arguments
   * @attribute inh
   * @aspect Diamond
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Diamond.jrag:417
   */
  @ASTNodeAnnotation.Attribute
  public boolean isExplicitGenericConstructorAccess() {
    boolean isExplicitGenericConstructorAccess_value = getParent().Define_isExplicitGenericConstructorAccess(this, null);

    return isExplicitGenericConstructorAccess_value;
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
