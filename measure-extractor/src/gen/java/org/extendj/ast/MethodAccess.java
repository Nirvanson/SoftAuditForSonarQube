/* This file was generated with JastAdd2 (http://jastadd.org) version 2.2.2 */
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
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.IOException;
import java.util.Set;
import beaver.*;
import org.jastadd.util.*;
import java.util.zip.*;
import java.io.*;
import org.jastadd.util.PrettyPrintable;
import org.jastadd.util.PrettyPrinter;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
/**
 * @ast node
 * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\grammar\\Java.ast:19
 * @production MethodAccess : {@link Access} ::= <span class="component">&lt;ID:String&gt;</span> <span class="component">Arg:{@link Expr}*</span>;

 */
public class MethodAccess extends Access implements Cloneable {
  /**
   * @aspect Java4PrettyPrint
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\PrettyPrint.jadd:460
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print(getID());
    out.print("(");
    out.join(getArgList(), new PrettyPrinter.Joiner() {
      @Override
      public void printSeparator(PrettyPrinter out) {
        out.print(", ");
      }
    });
    out.print(")");
  }
  /**
   * @aspect AnonymousClasses
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\AnonymousClasses.jrag:138
   */
  protected void collectExceptions(Collection<TypeDecl> exceptions, ASTNode target) {
    super.collectExceptions(exceptions, target);
    for (int i = 0; i < decl().getNumException(); i++) {
      exceptions.add(decl().getException(i).type());
    }
  }
  /**
   * @aspect ExceptionHandling
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ExceptionHandling.jrag:329
   */
  protected boolean reachedException(TypeDecl catchType) {
    for (TypeDecl exceptionType : exceptionCollection()) {
      if (catchType.mayCatch(exceptionType)) {
        return true;
      }
    }
    return super.reachedException(catchType);
  }
  /**
   * @aspect LookupMethod
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupMethod.jrag:180
   */
  private static SimpleSet<MethodDecl> keepStaticMethods(
      SimpleSet<MethodDecl> methods) {
    SimpleSet<MethodDecl> result = emptySet();
    for (MethodDecl method : methods) {
      if (method.isStatic()) {
        result = result.add(method);
      }
    }
    return result;
  }
  /**
   * @aspect MethodDecl
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupMethod.jrag:262
   */
  public boolean applicable(MethodDecl decl) {
    if (getNumArg() != decl.getNumParameter()) {
      return false;
    }
    if (!name().equals(decl.name())) {
      return false;
    }
    for (int i = 0; i < getNumArg(); i++) {
      if (!getArg(i).type().instanceOf(decl.getParameter(i).type())) {
        return false;
      }
    }
    return true;
  }
  /**
   * @aspect NodeConstructors
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\NodeConstructors.jrag:70
   */
  public MethodAccess(String name, List args, int start, int end) {
    this(name, args);
    setStart(start);
    setEnd(end);
  }
  /**
   * @aspect MethodSignature15
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\MethodSignature.jrag:45
   */
  protected SimpleSet<MethodDecl> potentiallyApplicable(
      Collection<MethodDecl> candidates) {
    SimpleSet<MethodDecl> potentiallyApplicable = emptySet();
    // Select potentially applicable methods.
    for (MethodDecl method : candidates) {
      if (potentiallyApplicable(method) && accessible(method)) {
        if (method.isGeneric()) {
          GenericMethodDecl gm = method.genericDecl();
          Collection<TypeDecl> typeArguments = inferTypeArguments(
              method.type(),
              method.getParameterList(),
              getArgList(),
              gm.getTypeParameterList());
          method = gm.lookupParMethodDecl(typeArguments);
        }
        potentiallyApplicable = potentiallyApplicable.add(method);
      }
    }
    return potentiallyApplicable;
  }
  /**
   * @aspect MethodSignature15
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\MethodSignature.jrag:66
   */
  protected SimpleSet<MethodDecl> applicableBySubtyping(
      SimpleSet<MethodDecl> potentiallyApplicable) {
    SimpleSet<MethodDecl> maxSpecific = emptySet();
    for (MethodDecl method : potentiallyApplicable) {
      if (applicableBySubtyping(method)) {
        maxSpecific = mostSpecific(maxSpecific, method);
      }
    }
    return maxSpecific;
  }
  /**
   * @aspect MethodSignature15
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\MethodSignature.jrag:77
   */
  protected SimpleSet<MethodDecl> applicableByMethodInvocationConversion(
      SimpleSet<MethodDecl> potentiallyApplicable, SimpleSet<MethodDecl> maxSpecific) {
    if (maxSpecific.isEmpty()) {
      for (MethodDecl method : potentiallyApplicable) {
        if (applicableByMethodInvocationConversion(method)) {
          maxSpecific = mostSpecific(maxSpecific, method);
        }
      }
    }
    return maxSpecific;
  }
  /**
   * @aspect MethodSignature15
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\MethodSignature.jrag:89
   */
  protected SimpleSet<MethodDecl> applicableVariableArity(
      SimpleSet<MethodDecl> potentiallyApplicable, SimpleSet<MethodDecl> maxSpecific) {
    if (maxSpecific.isEmpty()) {
      for (MethodDecl method : potentiallyApplicable) {
        if (method.isVariableArity() && applicableVariableArity(method)) {
          maxSpecific = mostSpecific(maxSpecific, method);
        }
      }
    }
    return maxSpecific;
  }
  /**
   * @aspect MethodSignature18
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\MethodSignature.jrag:701
   */
  protected boolean moreSpecificThan(MethodDecl m1, MethodDecl m2) {
    if (m1 instanceof ParMethodDecl) {
      return m1.moreSpecificThan(m2);
    }
    if (m1.getNumParameter() == 0) {
      return false;
    }
    if (!m1.isVariableArity() && !m2.isVariableArity()) {
      for (int i = 0; i < m1.getNumParameter(); i++) {
        if (!getArg(i).moreSpecificThan(m1.getParameter(i).type(), m2.getParameter(i).type())) {
          return false;
        }
      }
      return true;
    }

    boolean expandVarargs = m1.isVariableArity() && m2.isVariableArity();

    int num = getNumArg();
    for (int i = 0; i < num; i++) {
      ParameterDeclaration p1 = i < m1.getNumParameter()
          ? m1.getParameter(i)
          : m1.getParameter(m1.getNumParameter() - 1);
      ParameterDeclaration p2 = i < m2.getNumParameter()
          ? m2.getParameter(i)
          : m2.getParameter(m2.getNumParameter() - 1);
      TypeDecl t1 = expandVarargs && p1.isVariableArity() ? p1.type().componentType() : p1.type();
      TypeDecl t2 = expandVarargs && p2.isVariableArity() ? p2.type().componentType() : p2.type();
      if (!getArg(i).moreSpecificThan(t1, t2)) {
          return false;
      }
    }
    num++;
    if (m2.getNumParameter() == num) {
      ParameterDeclaration p1 = num < m1.getNumParameter()
          ? m1.getParameter(num)
          : m1.getParameter(m1.getNumParameter() - 1);
      ParameterDeclaration p2 = num < m2.getNumParameter()
          ? m2.getParameter(num)
          : m2.getParameter(m2.getNumParameter() - 1);
      TypeDecl t1 = expandVarargs && p1.isVariableArity() ? p1.type().componentType() : p1.type();
      TypeDecl t2 = expandVarargs && p2.isVariableArity() ? p2.type().componentType() : p2.type();
      if (!t1.instanceOf(t2) && !t1.withinBounds(t2)) {
        return false;
      }
    }
    return true;
  }
  /**
   * @declaredat ASTNode:1
   */
  public MethodAccess() {
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
    setChild(new List(), 0);
  }
  /**
   * @declaredat ASTNode:14
   */
  public MethodAccess(String p0, List<Expr> p1) {
    setID(p0);
    setChild(p1, 0);
  }
  /**
   * @declaredat ASTNode:18
   */
  public MethodAccess(beaver.Symbol p0, List<Expr> p1) {
    setID(p0);
    setChild(p1, 0);
  }
  /** @apilevel low-level 
   * @declaredat ASTNode:23
   */
  protected int numChildren() {
    return 1;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:29
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:33
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    computeDAbefore_int_Variable_reset();
    computeDUbefore_int_Variable_reset();
    unassignedAfter_Variable_reset();
    unassignedAfterTrue_Variable_reset();
    unassignedAfterFalse_Variable_reset();
    exceptionCollection_reset();
    decls_reset();
    decl_reset();
    type_reset();
    stmtCompatible_reset();
    isBooleanExpression_reset();
    isNumericExpression_reset();
    isPolyExpression_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:50
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:54
   */
  public MethodAccess clone() throws CloneNotSupportedException {
    MethodAccess node = (MethodAccess) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:59
   */
  public MethodAccess copy() {
    try {
      MethodAccess node = (MethodAccess) clone();
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
  public MethodAccess fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:88
   */
  public MethodAccess treeCopyNoTransform() {
    MethodAccess tree = (MethodAccess) copy();
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
  public MethodAccess treeCopy() {
    MethodAccess tree = (MethodAccess) copy();
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        ASTNode child = (ASTNode) getChild(i);
        if (child != null) {
          child = child.treeCopy();
          tree.setChild(child, i);
        }
      }
    }
    return tree;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:122
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenString_ID == ((MethodAccess) node).tokenString_ID);    
  }
  /**
   * Replaces the lexeme ID.
   * @param value The new value for the lexeme ID.
   * @apilevel high-level
   */
  public void setID(String value) {
    tokenString_ID = value;
  }
  /** @apilevel internal 
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
   * Replaces the Arg list.
   * @param list The new list node to be used as the Arg list.
   * @apilevel high-level
   */
  public void setArgList(List<Expr> list) {
    setChild(list, 0);
  }
  /**
   * Retrieves the number of children in the Arg list.
   * @return Number of children in the Arg list.
   * @apilevel high-level
   */
  public int getNumArg() {
    return getArgList().getNumChild();
  }
  /**
   * Retrieves the number of children in the Arg list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the Arg list.
   * @apilevel low-level
   */
  public int getNumArgNoTransform() {
    return getArgListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the Arg list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the Arg list.
   * @apilevel high-level
   */
  public Expr getArg(int i) {
    return (Expr) getArgList().getChild(i);
  }
  /**
   * Check whether the Arg list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasArg() {
    return getArgList().getNumChild() != 0;
  }
  /**
   * Append an element to the Arg list.
   * @param node The element to append to the Arg list.
   * @apilevel high-level
   */
  public void addArg(Expr node) {
    List<Expr> list = (parent == null) ? getArgListNoTransform() : getArgList();
    list.addChild(node);
  }
  /** @apilevel low-level 
   */
  public void addArgNoTransform(Expr node) {
    List<Expr> list = getArgListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the Arg list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setArg(Expr node, int i) {
    List<Expr> list = getArgList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the Arg list.
   * @return The node representing the Arg list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="Arg")
  public List<Expr> getArgList() {
    List<Expr> list = (List<Expr>) getChild(0);
    return list;
  }
  /**
   * Retrieves the Arg list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the Arg list.
   * @apilevel low-level
   */
  public List<Expr> getArgListNoTransform() {
    return (List<Expr>) getChildNoTransform(0);
  }
  /**
   * @return the element at index {@code i} in the Arg list without
   * triggering rewrites.
   */
  public Expr getArgNoTransform(int i) {
    return (Expr) getArgListNoTransform().getChildNoTransform(i);
  }
  /**
   * Retrieves the Arg list.
   * @return The node representing the Arg list.
   * @apilevel high-level
   */
  public List<Expr> getArgs() {
    return getArgList();
  }
  /**
   * Retrieves the Arg list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the Arg list.
   * @apilevel low-level
   */
  public List<Expr> getArgsNoTransform() {
    return getArgListNoTransform();
  }
  /**
   * @aspect MethodSignature15
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\MethodSignature.jrag:33
   */
    protected SimpleSet<MethodDecl> maxSpecific(
      Collection<MethodDecl> candidates) {
    SimpleSet<MethodDecl> potentiallyApplicable = potentiallyApplicable(candidates);
    // First phase.
    SimpleSet<MethodDecl> maxSpecific = applicableBySubtyping(potentiallyApplicable);
    // Second phase.
    maxSpecific = applicableByMethodInvocationConversion(potentiallyApplicable, maxSpecific);
    // Third phase.
    maxSpecific = applicableVariableArity(potentiallyApplicable, maxSpecific);
    return maxSpecific;
  }
  /**
   * @aspect MethodSignature18
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\MethodSignature.jrag:750
   */
   
  private SimpleSet<MethodDecl> mostSpecific(SimpleSet<MethodDecl> maxSpecific,
      MethodDecl decl) {
    SimpleSet<MethodDecl> newMax;
    if (maxSpecific.isEmpty()) {
      newMax = maxSpecific.add(decl);
    } else {
      boolean foundStricter = false;
      newMax = emptySet();
      for (MethodDecl toCompare : maxSpecific) {
        if (!(moreSpecificThan(decl, toCompare) && !moreSpecificThan(toCompare, decl))) {
          newMax = newMax.add(toCompare);
        }
        if (!moreSpecificThan(decl, toCompare) && moreSpecificThan(toCompare, decl)) {
          foundStricter = true;
        }
      }
      if (!foundStricter) {
        newMax = newMax.add(decl);
      }
    }
    return newMax;
  }
  /**
   * @aspect TypeAnalysis
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:307
   */
  private TypeDecl refined_TypeAnalysis_MethodAccess_type()
{ return decl().type(); }
  /** @apilevel internal */
  private void computeDAbefore_int_Variable_reset() {
    computeDAbefore_int_Variable_values = null;
  }
  protected java.util.Map computeDAbefore_int_Variable_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="DefiniteAssignment", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:505")
  public boolean computeDAbefore(int i, Variable v) {
    java.util.List _parameters = new java.util.ArrayList(2);
    _parameters.add(i);
    _parameters.add(v);
    if (computeDAbefore_int_Variable_values == null) computeDAbefore_int_Variable_values = new java.util.HashMap(4);
    ASTNode$State.CircularValue _value;
    if (computeDAbefore_int_Variable_values.containsKey(_parameters)) {
      Object _cache = computeDAbefore_int_Variable_values.get(_parameters);
      if (!(_cache instanceof ASTNode$State.CircularValue)) {
        return (Boolean) _cache;
      } else {
        _value = (ASTNode$State.CircularValue) _cache;
      }
    } else {
      _value = new ASTNode$State.CircularValue();
      computeDAbefore_int_Variable_values.put(_parameters, _value);
      _value.value = true;
    }
    ASTNode$State state = state();
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      boolean new_computeDAbefore_int_Variable_value;
      do {
        _value.cycle = state.nextCycle();
        new_computeDAbefore_int_Variable_value = i == 0 ? assignedBefore(v) : getArg(i-1).assignedAfter(v);
        if (new_computeDAbefore_int_Variable_value != ((Boolean)_value.value)) {
          state.setChangeInCycle();
          _value.value = new_computeDAbefore_int_Variable_value;
        }
      } while (state.testAndClearChangeInCycle());
      computeDAbefore_int_Variable_values.put(_parameters, new_computeDAbefore_int_Variable_value);

      state.leaveCircle();
      return new_computeDAbefore_int_Variable_value;
    } else if (_value.cycle != state.cycle()) {
      _value.cycle = state.cycle();
      boolean new_computeDAbefore_int_Variable_value = i == 0 ? assignedBefore(v) : getArg(i-1).assignedAfter(v);
      if (new_computeDAbefore_int_Variable_value != ((Boolean)_value.value)) {
        state.setChangeInCycle();
        _value.value = new_computeDAbefore_int_Variable_value;
      }
      return new_computeDAbefore_int_Variable_value;
    } else {
      return (Boolean) _value.value;
    }
  }
  /**
   * @attribute syn
   * @aspect DefiniteAssignment
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:268
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="DefiniteAssignment", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:268")
  public boolean assignedAfter(Variable v) {
    boolean assignedAfter_Variable_value = getNumArg() == 0
          ? assignedBefore(v)
          : getArg(getNumArg()-1).assignedAfter(v);
    return assignedAfter_Variable_value;
  }
  /**
   * @attribute syn
   * @aspect DefiniteAssignment
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:378
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="DefiniteAssignment", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:378")
  public boolean assignedAfterTrue(Variable v) {
    boolean assignedAfterTrue_Variable_value = isFalse() || (getNumArg() == 0 ? assignedBefore(v) : getArg(getNumArg()-1).assignedAfter(v));
    return assignedAfterTrue_Variable_value;
  }
  /**
   * @attribute syn
   * @aspect DefiniteAssignment
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:380
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="DefiniteAssignment", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:380")
  public boolean assignedAfterFalse(Variable v) {
    boolean assignedAfterFalse_Variable_value = isTrue() || (getNumArg() == 0 ? assignedBefore(v) : getArg(getNumArg()-1).assignedAfter(v));
    return assignedAfterFalse_Variable_value;
  }
  /** @apilevel internal */
  private void computeDUbefore_int_Variable_reset() {
    computeDUbefore_int_Variable_values = null;
  }
  protected java.util.Map computeDUbefore_int_Variable_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="DefiniteUnassignment", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:1121")
  public boolean computeDUbefore(int i, Variable v) {
    java.util.List _parameters = new java.util.ArrayList(2);
    _parameters.add(i);
    _parameters.add(v);
    if (computeDUbefore_int_Variable_values == null) computeDUbefore_int_Variable_values = new java.util.HashMap(4);
    ASTNode$State.CircularValue _value;
    if (computeDUbefore_int_Variable_values.containsKey(_parameters)) {
      Object _cache = computeDUbefore_int_Variable_values.get(_parameters);
      if (!(_cache instanceof ASTNode$State.CircularValue)) {
        return (Boolean) _cache;
      } else {
        _value = (ASTNode$State.CircularValue) _cache;
      }
    } else {
      _value = new ASTNode$State.CircularValue();
      computeDUbefore_int_Variable_values.put(_parameters, _value);
      _value.value = true;
    }
    ASTNode$State state = state();
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      boolean new_computeDUbefore_int_Variable_value;
      do {
        _value.cycle = state.nextCycle();
        new_computeDUbefore_int_Variable_value = i == 0 ? unassignedBefore(v) : getArg(i-1).unassignedAfter(v);
        if (new_computeDUbefore_int_Variable_value != ((Boolean)_value.value)) {
          state.setChangeInCycle();
          _value.value = new_computeDUbefore_int_Variable_value;
        }
      } while (state.testAndClearChangeInCycle());
      computeDUbefore_int_Variable_values.put(_parameters, new_computeDUbefore_int_Variable_value);

      state.leaveCircle();
      return new_computeDUbefore_int_Variable_value;
    } else if (_value.cycle != state.cycle()) {
      _value.cycle = state.cycle();
      boolean new_computeDUbefore_int_Variable_value = i == 0 ? unassignedBefore(v) : getArg(i-1).unassignedAfter(v);
      if (new_computeDUbefore_int_Variable_value != ((Boolean)_value.value)) {
        state.setChangeInCycle();
        _value.value = new_computeDUbefore_int_Variable_value;
      }
      return new_computeDUbefore_int_Variable_value;
    } else {
      return (Boolean) _value.value;
    }
  }
  /** @apilevel internal */
  private void unassignedAfter_Variable_reset() {
    unassignedAfter_Variable_values = null;
  }
  protected java.util.Map unassignedAfter_Variable_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="DefiniteUnassignment", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:903")
  public boolean unassignedAfter(Variable v) {
    Object _parameters = v;
    if (unassignedAfter_Variable_values == null) unassignedAfter_Variable_values = new java.util.HashMap(4);
    ASTNode$State.CircularValue _value;
    if (unassignedAfter_Variable_values.containsKey(_parameters)) {
      Object _cache = unassignedAfter_Variable_values.get(_parameters);
      if (!(_cache instanceof ASTNode$State.CircularValue)) {
        return (Boolean) _cache;
      } else {
        _value = (ASTNode$State.CircularValue) _cache;
      }
    } else {
      _value = new ASTNode$State.CircularValue();
      unassignedAfter_Variable_values.put(_parameters, _value);
      _value.value = true;
    }
    ASTNode$State state = state();
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      boolean new_unassignedAfter_Variable_value;
      do {
        _value.cycle = state.nextCycle();
        new_unassignedAfter_Variable_value = getNumArg() == 0 ? unassignedBefore(v) : getArg(getNumArg()-1).unassignedAfter(v);
        if (new_unassignedAfter_Variable_value != ((Boolean)_value.value)) {
          state.setChangeInCycle();
          _value.value = new_unassignedAfter_Variable_value;
        }
      } while (state.testAndClearChangeInCycle());
      unassignedAfter_Variable_values.put(_parameters, new_unassignedAfter_Variable_value);

      state.leaveCircle();
      return new_unassignedAfter_Variable_value;
    } else if (_value.cycle != state.cycle()) {
      _value.cycle = state.cycle();
      boolean new_unassignedAfter_Variable_value = getNumArg() == 0 ? unassignedBefore(v) : getArg(getNumArg()-1).unassignedAfter(v);
      if (new_unassignedAfter_Variable_value != ((Boolean)_value.value)) {
        state.setChangeInCycle();
        _value.value = new_unassignedAfter_Variable_value;
      }
      return new_unassignedAfter_Variable_value;
    } else {
      return (Boolean) _value.value;
    }
  }
  /** @apilevel internal */
  private void unassignedAfterTrue_Variable_reset() {
    unassignedAfterTrue_Variable_values = null;
  }
  protected java.util.Map unassignedAfterTrue_Variable_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="DefiniteUnassignment", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:909")
  public boolean unassignedAfterTrue(Variable v) {
    Object _parameters = v;
    if (unassignedAfterTrue_Variable_values == null) unassignedAfterTrue_Variable_values = new java.util.HashMap(4);
    ASTNode$State.CircularValue _value;
    if (unassignedAfterTrue_Variable_values.containsKey(_parameters)) {
      Object _cache = unassignedAfterTrue_Variable_values.get(_parameters);
      if (!(_cache instanceof ASTNode$State.CircularValue)) {
        return (Boolean) _cache;
      } else {
        _value = (ASTNode$State.CircularValue) _cache;
      }
    } else {
      _value = new ASTNode$State.CircularValue();
      unassignedAfterTrue_Variable_values.put(_parameters, _value);
      _value.value = true;
    }
    ASTNode$State state = state();
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      boolean new_unassignedAfterTrue_Variable_value;
      do {
        _value.cycle = state.nextCycle();
        new_unassignedAfterTrue_Variable_value = isFalse()
              || (getNumArg() == 0 ? unassignedBefore(v) : getArg(getNumArg()-1).unassignedAfter(v));
        if (new_unassignedAfterTrue_Variable_value != ((Boolean)_value.value)) {
          state.setChangeInCycle();
          _value.value = new_unassignedAfterTrue_Variable_value;
        }
      } while (state.testAndClearChangeInCycle());
      unassignedAfterTrue_Variable_values.put(_parameters, new_unassignedAfterTrue_Variable_value);

      state.leaveCircle();
      return new_unassignedAfterTrue_Variable_value;
    } else if (_value.cycle != state.cycle()) {
      _value.cycle = state.cycle();
      boolean new_unassignedAfterTrue_Variable_value = isFalse()
            || (getNumArg() == 0 ? unassignedBefore(v) : getArg(getNumArg()-1).unassignedAfter(v));
      if (new_unassignedAfterTrue_Variable_value != ((Boolean)_value.value)) {
        state.setChangeInCycle();
        _value.value = new_unassignedAfterTrue_Variable_value;
      }
      return new_unassignedAfterTrue_Variable_value;
    } else {
      return (Boolean) _value.value;
    }
  }
  /** @apilevel internal */
  private void unassignedAfterFalse_Variable_reset() {
    unassignedAfterFalse_Variable_values = null;
  }
  protected java.util.Map unassignedAfterFalse_Variable_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="DefiniteUnassignment", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:911")
  public boolean unassignedAfterFalse(Variable v) {
    Object _parameters = v;
    if (unassignedAfterFalse_Variable_values == null) unassignedAfterFalse_Variable_values = new java.util.HashMap(4);
    ASTNode$State.CircularValue _value;
    if (unassignedAfterFalse_Variable_values.containsKey(_parameters)) {
      Object _cache = unassignedAfterFalse_Variable_values.get(_parameters);
      if (!(_cache instanceof ASTNode$State.CircularValue)) {
        return (Boolean) _cache;
      } else {
        _value = (ASTNode$State.CircularValue) _cache;
      }
    } else {
      _value = new ASTNode$State.CircularValue();
      unassignedAfterFalse_Variable_values.put(_parameters, _value);
      _value.value = true;
    }
    ASTNode$State state = state();
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      boolean new_unassignedAfterFalse_Variable_value;
      do {
        _value.cycle = state.nextCycle();
        new_unassignedAfterFalse_Variable_value = isTrue()
              || (getNumArg() == 0 ? unassignedBefore(v) : getArg(getNumArg()-1).unassignedAfter(v));
        if (new_unassignedAfterFalse_Variable_value != ((Boolean)_value.value)) {
          state.setChangeInCycle();
          _value.value = new_unassignedAfterFalse_Variable_value;
        }
      } while (state.testAndClearChangeInCycle());
      unassignedAfterFalse_Variable_values.put(_parameters, new_unassignedAfterFalse_Variable_value);

      state.leaveCircle();
      return new_unassignedAfterFalse_Variable_value;
    } else if (_value.cycle != state.cycle()) {
      _value.cycle = state.cycle();
      boolean new_unassignedAfterFalse_Variable_value = isTrue()
            || (getNumArg() == 0 ? unassignedBefore(v) : getArg(getNumArg()-1).unassignedAfter(v));
      if (new_unassignedAfterFalse_Variable_value != ((Boolean)_value.value)) {
        state.setChangeInCycle();
        _value.value = new_unassignedAfterFalse_Variable_value;
      }
      return new_unassignedAfterFalse_Variable_value;
    } else {
      return (Boolean) _value.value;
    }
  }
  /**
   * @attribute syn
   * @aspect ExceptionHandling
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ExceptionHandling.jrag:100
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ExceptionHandling", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ExceptionHandling.jrag:100")
  public Collection<Problem> exceptionHandlingProblems() {
    {
        Collection<Problem> problems = new LinkedList<Problem>();
        for (TypeDecl exceptionType : exceptionCollection()) {
          if (exceptionType.isCheckedException() && !handlesException(exceptionType)) {
            problems.add(errorf("%s.%s invoked in %s may throw uncaught exception %s",
                decl().hostType().fullName(), this.name(),
                hostType().fullName(), exceptionType.fullName()));
          }
        }
        return problems;
      }
  }
  /** @apilevel internal */
  private void exceptionCollection_reset() {
    exceptionCollection_computed = null;
    exceptionCollection_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle exceptionCollection_computed = null;

  /** @apilevel internal */
  protected Collection<TypeDecl> exceptionCollection_value;

  /** @return the exception types possibly thrown by this method access 
   * @attribute syn
   * @aspect ExceptionHandling
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ExceptionHandling.jrag:113
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ExceptionHandling", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ExceptionHandling.jrag:113")
  public Collection<TypeDecl> exceptionCollection() {
    ASTNode$State state = state();
    if (exceptionCollection_computed == ASTNode$State.NON_CYCLE || exceptionCollection_computed == state().cycle()) {
      return exceptionCollection_value;
    }
    exceptionCollection_value = exceptionCollection_compute();
    if (state().inCircle()) {
      exceptionCollection_computed = state().cycle();
    
    } else {
      exceptionCollection_computed = ASTNode$State.NON_CYCLE;
    
    }
    return exceptionCollection_value;
  }
  /** @apilevel internal */
  private Collection<TypeDecl> exceptionCollection_compute() {
      Collection<TypeDecl> exceptions = new HashSet<TypeDecl>();
      Iterator<MethodDecl> iter = decls().iterator();
      if (!iter.hasNext()) {
        return exceptions;
      }
  
      MethodDecl m = iter.next();
  
      for (int i = 0; i < m.getNumException(); i++) {
        TypeDecl exceptionType = m.getException(i).type();
        exceptions.add(exceptionType);
      }
  
      while (iter.hasNext()) {
        Collection<TypeDecl> first = new HashSet<TypeDecl>();
        first.addAll(exceptions);
        Collection<TypeDecl> second = new HashSet<TypeDecl>();
        m = iter.next();
        for (int i = 0; i < m.getNumException(); i++) {
          TypeDecl exceptionType = m.getException(i).type();
          second.add(exceptionType);
        }
        exceptions = new HashSet<TypeDecl>();
        for (TypeDecl firstType : first) {
          for (TypeDecl secondType : second) {
            if (firstType.instanceOf(secondType)) {
              exceptions.add(firstType);
            } else if (secondType.instanceOf(firstType)) {
              exceptions.add(secondType);
            }
          }
        }
      }
      return exceptions;
    }
  /**
   * @attribute syn
   * @aspect LookupMethod
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupMethod.jrag:120
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="LookupMethod", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupMethod.jrag:120")
  public MethodDecl singleCandidateDecl() {
    {
        MethodDecl result = null;
        for (MethodDecl m : lookupMethod(name())) {
          if (result == null) {
            result = m;
          } else if (m.getNumParameter() == getNumArg() && result.getNumParameter() != getNumArg()) {
            result = m;
          }
        }
        return result;
      }
  }
  /** @apilevel internal */
  private void decls_reset() {
    decls_computed = null;
    decls_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle decls_computed = null;

  /** @apilevel internal */
  protected SimpleSet<MethodDecl> decls_value;

  /**
   * @attribute syn
   * @aspect LookupMethod
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupMethod.jrag:151
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="LookupMethod", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupMethod.jrag:151")
  public SimpleSet<MethodDecl> decls() {
    ASTNode$State state = state();
    if (decls_computed == ASTNode$State.NON_CYCLE || decls_computed == state().cycle()) {
      return decls_value;
    }
    decls_value = decls_compute();
    if (state().inCircle()) {
      decls_computed = state().cycle();
    
    } else {
      decls_computed = ASTNode$State.NON_CYCLE;
    
    }
    return decls_value;
  }
  /** @apilevel internal */
  private SimpleSet<MethodDecl> decls_compute() {
      SimpleSet<MethodDecl> maxSpecific = maxSpecific(lookupMethod(name()));
      if (isQualified() ? qualifier().staticContextQualifier() : inStaticContext()) {
        maxSpecific = keepStaticMethods(maxSpecific);
      }
      return maxSpecific;
    }
  /** @apilevel internal */
  private void decl_reset() {
    decl_computed = null;
    decl_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle decl_computed = null;

  /** @apilevel internal */
  protected MethodDecl decl_value;

  /**
   * @attribute syn
   * @aspect LookupMethod
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupMethod.jrag:159
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="LookupMethod", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupMethod.jrag:159")
  public MethodDecl decl() {
    ASTNode$State state = state();
    if (decl_computed == ASTNode$State.NON_CYCLE || decl_computed == state().cycle()) {
      return decl_value;
    }
    decl_value = decl_compute();
    if (state().inCircle()) {
      decl_computed = state().cycle();
    
    } else {
      decl_computed = ASTNode$State.NON_CYCLE;
    
    }
    return decl_value;
  }
  /** @apilevel internal */
  private MethodDecl decl_compute() {
      SimpleSet<MethodDecl> decls = decls();
      if (decls.isSingleton()) {
        return decls.singletonValue();
      }
  
      // Only return the first method in case of multiply inherited abstract methods.
      // See JLS section 8.4.6.4.
      boolean allAbstract = true;
      for (MethodDecl m : decls) {
        if (!m.isAbstract() && !m.hostType().isObject()) {
          allAbstract = false;
          break;
        }
      }
      if (decls.size() > 1 && allAbstract) {
        return decls.iterator().next();
      }
      return unknownMethod();
    }
  /**
   * @attribute syn
   * @aspect MethodDecl
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupMethod.jrag:277
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MethodDecl", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupMethod.jrag:277")
  public boolean accessible(MethodDecl m) {
    {
        if (!isQualified()) {
          return true;
        }
        if (!m.accessibleFrom(hostType())) {
          return false;
        }
        // The method is not accessible if the type is not accessible.
        if (!qualifier().type().accessibleFrom(hostType())) {
          return false;
        }
        // 6.6.2.1 -  include qualifier type for protected access
        if (m.isProtected() && !m.hostPackage().equals(hostPackage())
            && !m.isStatic() && !qualifier().isSuperAccess()) {
          return hostType().mayAccess(this, m);
        }
        return true;
      }
  }
  /**
   * @attribute syn
   * @aspect NameCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\NameCheck.jrag:95
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="NameCheck", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\NameCheck.jrag:95")
  public boolean validArgs() {
    {
        for (int i = 0; i < getNumArg(); i++) {
          if (!getArg(i).isPolyExpression() && getArg(i).type().isUnknown()) {
            return false;
          }
        }
        return true;
      }
  }
  /**
   * @attribute syn
   * @aspect Names
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\QualifiedNames.jrag:36
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Names", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\QualifiedNames.jrag:36")
  public String name() {
    String name_value = getID();
    return name_value;
  }
  /**
   * @attribute syn
   * @aspect AccessTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:39
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="AccessTypes", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:39")
  public boolean isMethodAccess() {
    boolean isMethodAccess_value = true;
    return isMethodAccess_value;
  }
  /**
   * Defines the expected kind of name for the left hand side in a qualified
   * expression.
   * @attribute syn
   * @aspect SyntacticClassification
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\SyntacticClassification.jrag:60
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="SyntacticClassification", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\SyntacticClassification.jrag:60")
  public NameType predNameType() {
    NameType predNameType_value = NameType.AMBIGUOUS_NAME;
    return predNameType_value;
  }
  /** @apilevel internal */
  private void type_reset() {
    type_computed = null;
    type_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle type_computed = null;

  /** @apilevel internal */
  protected TypeDecl type_value;

  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:296
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeAnalysis", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:296")
  public TypeDecl type() {
    ASTNode$State state = state();
    if (type_computed == ASTNode$State.NON_CYCLE || type_computed == state().cycle()) {
      return type_value;
    }
    type_value = type_compute();
    if (state().inCircle()) {
      type_computed = state().cycle();
    
    } else {
      type_computed = ASTNode$State.NON_CYCLE;
    
    }
    return type_value;
  }
  /** @apilevel internal */
  private TypeDecl type_compute() {
      if (getNumArg() == 0 && name().equals("getClass") && decl().hostType().isObject()) {
        TypeDecl typeClass = lookupType("java.lang", "Class");
        if (typeClass instanceof GenericClassDecl) {
          TypeDecl bound = isQualified() ? qualifier().type() : hostType();
          ArrayList<TypeDecl> args = new ArrayList<TypeDecl>();
          args.add(bound.erasure().asWildcardExtends());
          return ((GenericClassDecl) typeClass).lookupParTypeDecl(args);
        }
      }
      // Legacy getClass access using non-generic java.lang.Class.
      return refined_TypeAnalysis_MethodAccess_type();
    }
  /**
   * @attribute syn
   * @aspect TypeCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeCheck.jrag:149
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeCheck", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeCheck.jrag:149")
  public Collection<Problem> typeProblems() {
    {
        Collection<Problem> problems = new LinkedList<Problem>();
        for (int i = 0; i < getNumArg(); ++i) {
          if (getArg(i).type().isVoid()) {
            problems.add(errorf("expression '%s' has type void and is not a valid method argument",
                getArg(i).prettyPrint()));
          }
        }
        if (isQualified() && decl().isAbstract() && qualifier().isSuperAccess()) {
          problems.add(error("may not access abstract methods in superclass"));
        }
        if (!decl().isVariableArity() || invokesVariableArityAsArray()) {
          for (int i = 0; i < decl().getNumParameter(); i++) {
            TypeDecl exprType = getArg(i).type();
            TypeDecl parmType = decl().getParameter(i).type();
            if (!exprType.methodInvocationConversionTo(parmType) &&
                !exprType.isUnknown() && !parmType.isUnknown()) {
              problems.add(errorf("argument '%s' of type %s is not compatible with the method parameter type %s",
                  getArg(i).prettyPrint(), exprType.typeName(), parmType.typeName()));
            }
          }
        }
        return problems;
      }
  }
  /**
   * @attribute syn
   * @aspect TypeHierarchyCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:53
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeHierarchyCheck", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:53")
  public Collection<Problem> typeHierarchyProblems() {
    {
        Collection<Problem> problems = new LinkedList<Problem>();
        if (isQualified() && qualifier().isPackageAccess() && !qualifier().isUnknown()) {
          problems.add(errorf("The method %s can not be qualified by a package name.",
              decl().fullSignature()));
        }
        if (isQualified() && decl().isAbstract() && qualifier().isSuperAccess()) {
          problems.add(error("may not access abstract methods in superclass"));
        }
        if (decls().isEmpty() && (!isQualified() || !qualifier().isUnknown())) {
          StringBuilder sb = new StringBuilder();
          sb.append("no method named " + name());
          sb.append("(");
          for (int i = 0; i < getNumArg(); i++) {
            TypeDecl argType = getArg(i).type();
            if (argType.isVoid()) {
              // Error will be reported for the void argument in typeCheck
              // so we return now to avoid confusing double errors.
              return problems;
            }
            if (i != 0) {
              sb.append(", ");
            }
            sb.append(argType.typeName());
          }
          sb.append(")" + " in " + methodHost() + " matches.");
          if (singleCandidateDecl() != null) {
            sb.append(" However, there is a method " + singleCandidateDecl().fullSignature());
          }
          problems.add(error(sb.toString()));
        }
        if (decls().size() > 1) {
          boolean allAbstract = true;
          for (MethodDecl m : decls()) {
            if (!m.isAbstract() && !m.hostType().isObject()) {
              allAbstract = false;
              break;
            }
          }
          if (!allAbstract && validArgs()) {
            StringBuilder sb = new StringBuilder();
            sb.append("several most specific methods for " + this.prettyPrint() + "\n");
            for (MethodDecl m : decls()) {
              sb.append("    " + m.fullSignature() + " in " + m.hostType().typeName() + "\n");
            }
            problems.add(error(sb.toString()));
          }
        }
        return problems;
      }
  }
  /**
   * @attribute syn
   * @aspect MethodSignature15
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\MethodSignature.jrag:237
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MethodSignature15", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\MethodSignature.jrag:237")
  public boolean applicableBySubtyping(MethodDecl m) {
    {
        if (m.getNumParameter() != getNumArg()) {
          return false;
        }
        for (int i = 0; i < m.getNumParameter(); i++) {
          if (!getArg(i).pertinentToApplicability(this, m, i)) {
            continue;
          } else if (!getArg(i).compatibleStrictContext(m.getParameter(i).type())) {
            return false;
          }
        }
        return true;
      }
  }
  /**
   * @attribute syn
   * @aspect MethodSignature15
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\MethodSignature.jrag:265
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MethodSignature15", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\MethodSignature.jrag:265")
  public boolean applicableByMethodInvocationConversion(MethodDecl m) {
    {
        if (m.getNumParameter() != getNumArg()) {
          return false;
        }
        for (int i = 0; i < m.getNumParameter(); i++) {
          if (!getArg(i).pertinentToApplicability(this, m, i)) {
            continue;
          } else if (!getArg(i).compatibleLooseContext(m.getParameter(i).type())) {
            return false;
          }
        }
        return true;
      }
  }
  /**
   * @attribute syn
   * @aspect MethodSignature15
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\MethodSignature.jrag:290
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MethodSignature15", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\MethodSignature.jrag:290")
  public boolean applicableVariableArity(MethodDecl m) {
    {
        for (int i = 0; i < m.getNumParameter() - 1; i++) {
          if (!getArg(i).pertinentToApplicability(this, m, i)) {
            continue;
          }
          if (!getArg(i).compatibleLooseContext(m.getParameter(i).type())) {
            return false;
          }
        }
        for (int i = m.getNumParameter() - 1; i < getNumArg(); i++) {
          if (!getArg(i).pertinentToApplicability(this, m, i)) {
            continue;
          }
          if (!getArg(i).compatibleLooseContext(m.lastParameter().type().componentType())) {
            return false;
          }
        }
        return true;
      }
  }
  /**
   * A member method is potentially applicable to a method invocation if and
   * only if all of the following are true:
   * <ul>
   * <li>The name of the member is identical to the name of the method in the
   * method invocation.
   * <li>The member is accessible (\u00a76.6) to the class or interface in which
   * the method invocation appears.
   * <li>The arity of the member is lesser or equal to the arity of the
   * method invocation.
   * <li>If the member is a variable arity method with arity n, the arity of
   * the method invocation is greater or equal to n-1.
   * <li>If the member is a fixed arity method with arity n, the arity of the
   * method invocation is equal to n.
   * <li>If the method invocation includes explicit type parameters, and the
   * member is a generic method, then the number of actual type parameters is
   * equal to the number of formal type parameters.
   * </ul>
   * @attribute syn
   * @aspect MethodSignature15
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\MethodSignature.jrag:390
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MethodSignature15", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\MethodSignature.jrag:390")
  public boolean potentiallyApplicable(MethodDecl m) {
    {
        if (!m.name().equals(name())) {
          return false;
        }
        if (!m.accessibleFrom(hostType())) {
          return false;
        }
        if (!m.isVariableArity()) {
          if (arity() != m.arity()) {
            return false;
          }
          for (int i = 0; i < getNumArg(); i++) {
            if (!getArg(i).potentiallyCompatible(m.getParameter(i).type(), m)) {
              return false;
            }
          }
        }
        if (m.isVariableArity()) {
          if (!(arity() >= m.arity() - 1)) {
            return false;
          }
          for (int i = 0; i < m.arity() - 2; i++) {
            if (!getArg(i).potentiallyCompatible(m.getParameter(i).type(), m)) {
              return false;
            }
          }
          TypeDecl varArgType = m.getParameter(m.arity() - 1).type();
          if (arity() == m.arity()) {
            if (!getArg(arity() - 1).potentiallyCompatible(varArgType, m)
                && !getArg(arity() - 1).potentiallyCompatible(varArgType.componentType(), m)) {
              return false;
            }
          } else if (arity() > m.arity()) {
            for (int i = m.arity() - 1; i < arity(); i++) {
              if (!getArg(i).potentiallyCompatible(varArgType.componentType(), m)) {
                return false;
              }
            }
          }
        }
    
        if (m.isGeneric()) {
          GenericMethodDecl gm = m.genericDecl();
          ArrayList<TypeDecl> typeArguments = inferTypeArguments(
              gm.type(),
              gm.getParameterList(),
              getArgList(),
              gm.getTypeParameterList());
          if (!typeArguments.isEmpty()) {
            if (gm.getNumTypeParameter() != typeArguments.size()) {
              return false;
            }
            ParMethodDecl parMethod = gm.lookupParMethodDecl(typeArguments);
            for (int i = 0; i < gm.getNumTypeParameter(); i++) {
              if (!((TypeDecl) typeArguments.get(i)).withinBounds(parMethod.getTypeParameter(i))) {
                return false;
              }
            }
          }
        }
    
        return true;
      }
  }
  /**
   * @attribute syn
   * @aspect MethodSignature15
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\MethodSignature.jrag:426
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MethodSignature15", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\MethodSignature.jrag:426")
  public int arity() {
    int arity_value = getNumArg();
    return arity_value;
  }
  /**
   * @attribute syn
   * @aspect VariableArityParameters
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\VariableArityParameters.jrag:65
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="VariableArityParameters", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\VariableArityParameters.jrag:65")
  public boolean invokesVariableArityAsArray() {
    {
        if (!decl().isVariableArity()) {
          return false;
        }
        if (arity() != decl().arity()) {
          return false;
        }
        return getArg(getNumArg()-1).type().methodInvocationConversionTo(decl().lastParameter().type());
      }
  }
  /**
   * @attribute syn
   * @aspect PreciseRethrow
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\PreciseRethrow.jrag:145
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="PreciseRethrow", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\PreciseRethrow.jrag:145")
  public boolean modifiedInScope(Variable var) {
    {
        for (int i = 0; i < getNumArg(); ++i) {
          if (getArg(i).modifiedInScope(var)) {
            return true;
          }
        }
        return false;
      }
  }
  /**
   * @attribute syn
   * @aspect SafeVarargs
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\SafeVarargs.jrag:89
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="SafeVarargs", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\SafeVarargs.jrag:89")
  public Collection<Problem> uncheckedWarnings() {
    {
        MethodDecl decl = decl();
        if (decl.getNumParameter() == 0 || decl.getNumParameter() > getNumArg()) {
          return Collections.emptyList();
        }
    
        ParameterDeclaration param = decl.getParameter(decl.getNumParameter()-1);
        if (!withinSuppressWarnings("unchecked")
            && !decl.hasAnnotationSafeVarargs()
            && param.isVariableArity()
            && !param.type().isReifiable()) {
          return Collections.singletonList(
              warning("unchecked array creation for variable arity parameter of " + decl().name()));
        }
        return Collections.emptyList();
      }
  }
  /** @apilevel internal */
  private void stmtCompatible_reset() {
    stmtCompatible_computed = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle stmtCompatible_computed = null;

  /** @apilevel internal */
  protected boolean stmtCompatible_value;

  /**
   * @attribute syn
   * @aspect StmtCompatible
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\LambdaExpr.jrag:122
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StmtCompatible", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\LambdaExpr.jrag:122")
  public boolean stmtCompatible() {
    ASTNode$State state = state();
    if (stmtCompatible_computed == ASTNode$State.NON_CYCLE || stmtCompatible_computed == state().cycle()) {
      return stmtCompatible_value;
    }
    stmtCompatible_value = true;
    if (state().inCircle()) {
      stmtCompatible_computed = state().cycle();
    
    } else {
      stmtCompatible_computed = ASTNode$State.NON_CYCLE;
    
    }
    return stmtCompatible_value;
  }
  /** @apilevel internal */
  private void isBooleanExpression_reset() {
    isBooleanExpression_computed = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle isBooleanExpression_computed = null;

  /** @apilevel internal */
  protected boolean isBooleanExpression_value;

  /**
   * @attribute syn
   * @aspect PolyExpressions
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\PolyExpressions.jrag:29
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="PolyExpressions", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\PolyExpressions.jrag:29")
  public boolean isBooleanExpression() {
    ASTNode$State state = state();
    if (isBooleanExpression_computed == ASTNode$State.NON_CYCLE || isBooleanExpression_computed == state().cycle()) {
      return isBooleanExpression_value;
    }
    isBooleanExpression_value = isBooleanExpression_compute();
    if (state().inCircle()) {
      isBooleanExpression_computed = state().cycle();
    
    } else {
      isBooleanExpression_computed = ASTNode$State.NON_CYCLE;
    
    }
    return isBooleanExpression_value;
  }
  /** @apilevel internal */
  private boolean isBooleanExpression_compute() {
      MethodDecl decl = decl();
      if (decl instanceof ParMethodDecl) {
        return ((ParMethodDecl) decl).genericMethodDecl().type().isBoolean();
      } else {
        return decl.type().isBoolean();
      }
    }
  /** @apilevel internal */
  private void isNumericExpression_reset() {
    isNumericExpression_computed = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle isNumericExpression_computed = null;

  /** @apilevel internal */
  protected boolean isNumericExpression_value;

  /**
   * @attribute syn
   * @aspect PolyExpressions
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\PolyExpressions.jrag:60
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="PolyExpressions", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\PolyExpressions.jrag:60")
  public boolean isNumericExpression() {
    ASTNode$State state = state();
    if (isNumericExpression_computed == ASTNode$State.NON_CYCLE || isNumericExpression_computed == state().cycle()) {
      return isNumericExpression_value;
    }
    isNumericExpression_value = isNumericExpression_compute();
    if (state().inCircle()) {
      isNumericExpression_computed = state().cycle();
    
    } else {
      isNumericExpression_computed = ASTNode$State.NON_CYCLE;
    
    }
    return isNumericExpression_value;
  }
  /** @apilevel internal */
  private boolean isNumericExpression_compute() {
      MethodDecl decl = decl();
      if (decl instanceof ParMethodDecl) {
        return ((ParMethodDecl) decl).genericMethodDecl().type().isNumericType();
      } else {
        return decl.type().isNumericType();
      }
    }
  /** @apilevel internal */
  private void isPolyExpression_reset() {
    isPolyExpression_computed = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle isPolyExpression_computed = null;

  /** @apilevel internal */
  protected boolean isPolyExpression_value;

  /**
   * @attribute syn
   * @aspect PolyExpressions
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\PolyExpressions.jrag:86
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="PolyExpressions", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\PolyExpressions.jrag:86")
  public boolean isPolyExpression() {
    ASTNode$State state = state();
    if (isPolyExpression_computed == ASTNode$State.NON_CYCLE || isPolyExpression_computed == state().cycle()) {
      return isPolyExpression_value;
    }
    isPolyExpression_value = isPolyExpression_compute();
    if (state().inCircle()) {
      isPolyExpression_computed = state().cycle();
    
    } else {
      isPolyExpression_computed = ASTNode$State.NON_CYCLE;
    
    }
    return isPolyExpression_value;
  }
  /** @apilevel internal */
  private boolean isPolyExpression_compute() {
      if (!assignmentContext() && !invocationContext()) {
        return false;
      }
      if (!decl().isGeneric()) {
        return false;
      }
      GenericMethodDecl genericDecl = decl().genericDecl();
      return genericDecl.typeVariableInReturn();
    }
  /**
   * @attribute inh
   * @aspect ExceptionHandling
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ExceptionHandling.jrag:88
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="ExceptionHandling", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ExceptionHandling.jrag:88")
  public boolean handlesException(TypeDecl exceptionType) {
    boolean handlesException_TypeDecl_value = getParent().Define_handlesException(this, null, exceptionType);
    return handlesException_TypeDecl_value;
  }
  /**
   * @attribute inh
   * @aspect LookupMethod
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupMethod.jrag:38
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="LookupMethod", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupMethod.jrag:38")
  public MethodDecl unknownMethod() {
    MethodDecl unknownMethod_value = getParent().Define_unknownMethod(this, null);
    return unknownMethod_value;
  }
  /**
   * @attribute inh
   * @aspect TypeHierarchyCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:184
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="TypeHierarchyCheck", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:184")
  public boolean inExplicitConstructorInvocation() {
    boolean inExplicitConstructorInvocation_value = getParent().Define_inExplicitConstructorInvocation(this, null);
    return inExplicitConstructorInvocation_value;
  }
  /**
   * @attribute inh
   * @aspect SuppressWarnings
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\SuppressWarnings.jrag:39
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="SuppressWarnings", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\SuppressWarnings.jrag:39")
  public boolean withinSuppressWarnings(String annot) {
    boolean withinSuppressWarnings_String_value = getParent().Define_withinSuppressWarnings(this, null, annot);
    return withinSuppressWarnings_String_value;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:256
   * @apilevel internal
   */
  public boolean Define_assignedBefore(ASTNode _callerNode, ASTNode _childNode, Variable v) {
    if (_callerNode == getArgListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:503
      int i = _callerNode.getIndexOfChild(_childNode);
      return computeDAbefore(i, v);
    }
    else {
      return getParent().Define_assignedBefore(this, _callerNode, v);
    }
  }
  protected boolean canDefine_assignedBefore(ASTNode _callerNode, ASTNode _childNode, Variable v) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:891
   * @apilevel internal
   */
  public boolean Define_unassignedBefore(ASTNode _callerNode, ASTNode _childNode, Variable v) {
    if (_callerNode == getArgListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:1119
      int i = _callerNode.getIndexOfChild(_childNode);
      return computeDUbefore(i, v);
    }
    else {
      return getParent().Define_unassignedBefore(this, _callerNode, v);
    }
  }
  protected boolean canDefine_unassignedBefore(ASTNode _callerNode, ASTNode _childNode, Variable v) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupMethod.jrag:52
   * @apilevel internal
   */
  public Collection<MethodDecl> Define_lookupMethod(ASTNode _callerNode, ASTNode _childNode, String name) {
    if (_callerNode == getArgListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupMethod.jrag:58
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return unqualifiedScope().lookupMethod(name);
    }
    else {
      return getParent().Define_lookupMethod(this, _callerNode, name);
    }
  }
  protected boolean canDefine_lookupMethod(ASTNode _callerNode, ASTNode _childNode, String name) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:113
   * @apilevel internal
   */
  public boolean Define_hasPackage(ASTNode _callerNode, ASTNode _childNode, String packageName) {
    if (_callerNode == getArgListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:114
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return unqualifiedScope().hasPackage(packageName);
    }
    else {
      return getParent().Define_hasPackage(this, _callerNode, packageName);
    }
  }
  protected boolean canDefine_hasPackage(ASTNode _callerNode, ASTNode _childNode, String packageName) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericMethods.jrag:225
   * @apilevel internal
   */
  public SimpleSet<TypeDecl> Define_lookupType(ASTNode _callerNode, ASTNode _childNode, String name) {
    if (_callerNode == getArgListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:339
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return unqualifiedScope().lookupType(name);
    }
    else {
      return getParent().Define_lookupType(this, _callerNode, name);
    }
  }
  protected boolean canDefine_lookupType(ASTNode _callerNode, ASTNode _childNode, String name) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\LookupVariable.jrag:30
   * @apilevel internal
   */
  public SimpleSet<Variable> Define_lookupVariable(ASTNode _callerNode, ASTNode _childNode, String name) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return unqualifiedScope().lookupVariable(name);
  }
  protected boolean canDefine_lookupVariable(ASTNode _callerNode, ASTNode _childNode, String name) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:93
   * @apilevel internal
   */
  public boolean Define_isRightChildOfDot(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getArgListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:99
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return false;
    }
    else {
      int childIndex = this.getIndexOfChild(_callerNode);
      return isRightChildOfDot();
    }
  }
  protected boolean canDefine_isRightChildOfDot(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:110
   * @apilevel internal
   */
  public Expr Define_prevExpr(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getArgListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:115
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return prevExprError();
    }
    else {
      int childIndex = this.getIndexOfChild(_callerNode);
      return prevExpr();
    }
  }
  protected boolean canDefine_prevExpr(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\SyntacticClassification.jrag:36
   * @apilevel internal
   */
  public NameType Define_nameType(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getArgListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\SyntacticClassification.jrag:139
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return NameType.EXPRESSION_NAME;
    }
    else {
      return getParent().Define_nameType(this, _callerNode);
    }
  }
  protected boolean canDefine_nameType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:33
   * @apilevel internal
   */
  public String Define_methodHost(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return unqualifiedScope().methodHost();
  }
  protected boolean canDefine_methodHost(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericMethodsInference.jrag:65
   * @apilevel internal
   */
  public TypeDecl Define_assignConvertedType(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getArgListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericMethodsInference.jrag:72
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return typeObject();
    }
    else {
      return getParent().Define_assignConvertedType(this, _callerNode);
    }
  }
  protected boolean canDefine_assignConvertedType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\TargetType.jrag:30
   * @apilevel internal
   */
  public TypeDecl Define_targetType(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getArgListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\TargetType.jrag:86
      int i = _callerNode.getIndexOfChild(_childNode);
      {
          MethodDecl decl = decl();
          if (decl.unknownMethod() == decl) {
            return decl.type().unknownType();
          }
      
          if (decl.isVariableArity() && i >= decl.arity() - 1) {
            return decl.getParameter(decl.arity() - 1).type().componentType();
          } else {
            return decl.getParameter(i).type();
          }
        }
    }
    else {
      return getParent().Define_targetType(this, _callerNode);
    }
  }
  protected boolean canDefine_targetType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\TargetType.jrag:195
   * @apilevel internal
   */
  public boolean Define_assignmentContext(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getArgListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\TargetType.jrag:310
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return false;
    }
    else {
      return getParent().Define_assignmentContext(this, _callerNode);
    }
  }
  protected boolean canDefine_assignmentContext(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\TargetType.jrag:196
   * @apilevel internal
   */
  public boolean Define_invocationContext(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getArgListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\TargetType.jrag:311
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return true;
    }
    else {
      return getParent().Define_invocationContext(this, _callerNode);
    }
  }
  protected boolean canDefine_invocationContext(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\TargetType.jrag:197
   * @apilevel internal
   */
  public boolean Define_castContext(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getArgListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\TargetType.jrag:312
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return false;
    }
    else {
      return getParent().Define_castContext(this, _callerNode);
    }
  }
  protected boolean canDefine_castContext(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\TargetType.jrag:198
   * @apilevel internal
   */
  public boolean Define_stringContext(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getArgListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\TargetType.jrag:313
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return false;
    }
    else {
      return getParent().Define_stringContext(this, _callerNode);
    }
  }
  protected boolean canDefine_stringContext(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\TargetType.jrag:199
   * @apilevel internal
   */
  public boolean Define_numericContext(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getArgListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\TargetType.jrag:314
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return false;
    }
    else {
      return getParent().Define_numericContext(this, _callerNode);
    }
  }
  protected boolean canDefine_numericContext(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /** @apilevel internal */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
  /** @apilevel internal */
  public boolean canRewrite() {
    return false;
  }
  protected void collect_contributors_CompilationUnit_problems(CompilationUnit _root, java.util.Map<ASTNode, java.util.Set<ASTNode>> _map) {
    // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ExceptionHandling.jrag:98
    {
      java.util.Set<ASTNode> contributors = _map.get(_root);
      if (contributors == null) {
        contributors = new java.util.LinkedHashSet<ASTNode>();
        _map.put((ASTNode) _root, contributors);
      }
      contributors.add(this);
    }
    // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeCheck.jrag:147
    {
      java.util.Set<ASTNode> contributors = _map.get(_root);
      if (contributors == null) {
        contributors = new java.util.LinkedHashSet<ASTNode>();
        _map.put((ASTNode) _root, contributors);
      }
      contributors.add(this);
    }
    // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:51
    {
      java.util.Set<ASTNode> contributors = _map.get(_root);
      if (contributors == null) {
        contributors = new java.util.LinkedHashSet<ASTNode>();
        _map.put((ASTNode) _root, contributors);
      }
      contributors.add(this);
    }
    // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Annotations.jrag:499
    if (decl().isDeprecated()
              && !withinDeprecatedAnnotation()
              && hostType().topLevelType() != decl().hostType().topLevelType()
              && !withinSuppressWarnings("deprecation")) {
      {
        java.util.Set<ASTNode> contributors = _map.get(_root);
        if (contributors == null) {
          contributors = new java.util.LinkedHashSet<ASTNode>();
          _map.put((ASTNode) _root, contributors);
        }
        contributors.add(this);
      }
    }
    // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\SafeVarargs.jrag:87
    {
      java.util.Set<ASTNode> contributors = _map.get(_root);
      if (contributors == null) {
        contributors = new java.util.LinkedHashSet<ASTNode>();
        _map.put((ASTNode) _root, contributors);
      }
      contributors.add(this);
    }
    super.collect_contributors_CompilationUnit_problems(_root, _map);
  }
  protected void collect_contributors_MethodDecl_containingForeignMethodCalls(MethodDecl _root, java.util.Map<ASTNode, java.util.Set<ASTNode>> _map) {
    // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\AdditionalNodeCharacteristics.jrag:12
    if ((this.decl().name().equals("unknown"))) {
      {
        java.util.Set<ASTNode> contributors = _map.get(_root);
        if (contributors == null) {
          contributors = new java.util.LinkedHashSet<ASTNode>();
          _map.put((ASTNode) _root, contributors);
        }
        contributors.add(this);
      }
    }
    super.collect_contributors_MethodDecl_containingForeignMethodCalls(_root, _map);
  }
  protected void collect_contributors_Program_extractedForeignMethodCalls(Program _root, java.util.Map<ASTNode, java.util.Set<ASTNode>> _map) {
    // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:32
    if ((this.decl().name().equals("unknown"))) {
      {
        java.util.Set<ASTNode> contributors = _map.get(_root);
        if (contributors == null) {
          contributors = new java.util.LinkedHashSet<ASTNode>();
          _map.put((ASTNode) _root, contributors);
        }
        contributors.add(this);
      }
    }
    super.collect_contributors_Program_extractedForeignMethodCalls(_root, _map);
  }
  protected void collect_contributors_Program_extractedMethodCalls(Program _root, java.util.Map<ASTNode, java.util.Set<ASTNode>> _map) {
    // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:61
    {
      java.util.Set<ASTNode> contributors = _map.get(_root);
      if (contributors == null) {
        contributors = new java.util.LinkedHashSet<ASTNode>();
        _map.put((ASTNode) _root, contributors);
      }
      contributors.add(this);
    }
    super.collect_contributors_Program_extractedMethodCalls(_root, _map);
  }
  protected void contributeTo_CompilationUnit_problems(LinkedList<Problem> collection) {
    super.contributeTo_CompilationUnit_problems(collection);
    for (Problem value : exceptionHandlingProblems()) {
      collection.add(value);
    }
    for (Problem value : typeProblems()) {
      collection.add(value);
    }
    for (Problem value : typeHierarchyProblems()) {
      collection.add(value);
    }
    if (decl().isDeprecated()
              && !withinDeprecatedAnnotation()
              && hostType().topLevelType() != decl().hostType().topLevelType()
              && !withinSuppressWarnings("deprecation")) {
      collection.add(warning(decl().signature() + " in " + decl().hostType().typeName() + " has been deprecated"));
    }
    for (Problem value : uncheckedWarnings()) {
      collection.add(value);
    }
  }
  protected void contributeTo_MethodDecl_containingForeignMethodCalls(java.util.Collection<String> collection) {
    super.contributeTo_MethodDecl_containingForeignMethodCalls(collection);
    if ((this.decl().name().equals("unknown"))) {
      collection.add(("Foreign method call at " + this.location()));
    }
  }
  protected void contributeTo_Program_extractedForeignMethodCalls(java.util.Collection<String> collection) {
    super.contributeTo_Program_extractedForeignMethodCalls(collection);
    if ((this.decl().name().equals("unknown"))) {
      collection.add(("FMC;" + this.compilationUnit().pathName() + ";" + this.location() + ";"));
    }
  }
  protected void contributeTo_Program_extractedMethodCalls(java.util.Collection<String> collection) {
    super.contributeTo_Program_extractedMethodCalls(collection);
    collection.add(("MEC;" + this.compilationUnit().pathName() + ";" + this.location() + ";"));
  }
}
