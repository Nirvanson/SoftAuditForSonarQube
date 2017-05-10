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
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\grammar\\Java.ast:19
 * @production MethodAccess : {@link Access} ::= <span class="component">&lt;ID:String&gt;</span> <span class="component">Arg:{@link Expr}*</span>;

 */
public class MethodAccess extends Access implements Cloneable {
  /**
   * @aspect Java4PrettyPrint
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\PrettyPrint.jadd:626
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
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\AnonymousClasses.jrag:138
   */
  protected void collectExceptions(Collection c, ASTNode target) {
    super.collectExceptions(c, target);
    for (int i = 0; i < decl().getNumException(); i++) {
      c.add(decl().getException(i).type());
    }
  }
  /**
   * @aspect ExceptionHandling
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ExceptionHandling.jrag:90
   */
  public void exceptionHandling() {
    for (Iterator iter = exceptionCollection().iterator(); iter.hasNext(); ) {
      TypeDecl exceptionType = (TypeDecl) iter.next();
      if (exceptionType.isCheckedException() && !handlesException(exceptionType)) {
        errorf("%s.%s invoked in %s may throw uncaught exception %s",
            decl().hostType().fullName(), this.name(),
            hostType().fullName(), exceptionType.fullName());
      }
    }
  }
  /**
   * @aspect ExceptionHandling
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ExceptionHandling.jrag:308
   */
  protected boolean reachedException(TypeDecl catchType) {
    for (Iterator iter = exceptionCollection().iterator(); iter.hasNext(); ) {
      TypeDecl exceptionType = (TypeDecl) iter.next();
      if (catchType.mayCatch(exceptionType)) {
        return true;
      }
    }
    return super.reachedException(catchType);
  }
  /**
   * @aspect LookupMethod
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupMethod.jrag:165
   */
  private static SimpleSet removeInstanceMethods(SimpleSet c) {
    SimpleSet set = SimpleSet.emptySet;
    for (Iterator iter = c.iterator(); iter.hasNext(); ) {
      MethodDecl m = (MethodDecl) iter.next();
      if (m.isStatic()) {
        set = set.add(m);
      }
    }
    return set;
  }
  /**
   * @aspect MethodDecl
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupMethod.jrag:247
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
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\NodeConstructors.jrag:67
   */
  public MethodAccess(String name, List args, int start, int end) {
    this(name, args);
    setStart(start);
    setEnd(end);
  }
  /**
   * @aspect TypeHierarchyCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:44
   */
  public void nameCheck() {
    if (isQualified() && qualifier().isPackageAccess() && !qualifier().isUnknown()) {
      errorf("The method %s can not be qualified by a package name.", decl().fullSignature());
    }
    if (isQualified() && decl().isAbstract() && qualifier().isSuperAccess()) {
      error("may not access abstract methods in superclass");
    }
    if (decls().isEmpty() && (!isQualified() || !qualifier().isUnknown())) {
      StringBuilder sb = new StringBuilder();
      sb.append("no method named " + name());
      sb.append("(");
      for (int i = 0; i < getNumArg(); i++) {
        TypeDecl argType = getArg(i).type();
        if (argType.isVoid()) {
          // error will be reported for the void argument in typeCheck
          // so we return now to avoid confusing double errors
          return;
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
      error(sb.toString());
    }
    if (decls().size() > 1) {
      boolean allAbstract = true;
      for (Iterator iter = decls().iterator(); iter.hasNext() && allAbstract; ) {
         MethodDecl m = (MethodDecl) iter.next();
        if (!m.isAbstract() && !m.hostType().isObject()) {
          allAbstract = false;
        }
      }
      if (!allAbstract && validArgs()) {
        StringBuilder sb = new StringBuilder();
        sb.append("several most specific methods for " + this.prettyPrint() + "\n");
        for (Iterator iter = decls().iterator(); iter.hasNext(); ) {
          MethodDecl m = (MethodDecl) iter.next();
          sb.append("    " + m.fullSignature() + " in " + m.hostType().typeName() + "\n");
        }
        error(sb.toString());
      }

    }
  }
  /**
   * @aspect Annotations
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Annotations.jrag:412
   */
  public void checkModifiers() {
    if (decl().isDeprecated() &&
      !withinDeprecatedAnnotation() &&
      hostType().topLevelType() != decl().hostType().topLevelType() &&
      !withinSuppressWarnings("deprecation"))
        warning(decl().signature() + " in " + decl().hostType().typeName() + " has been deprecated");
  }
  /**
   * @aspect MethodSignature15
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\MethodSignature.jrag:44
   */
  protected SimpleSet potentiallyApplicable(Collection candidates) {
    SimpleSet potentiallyApplicable = SimpleSet.emptySet;
    // select potentially applicable methods
    for (Iterator iter = candidates.iterator(); iter.hasNext(); ) {
      MethodDecl decl = (MethodDecl) iter.next();
      if (potentiallyApplicable(decl) && accessible(decl)) {
        if (decl.isGeneric()) {
          GenericMethodDecl gm = decl.genericDecl();
          decl = gm.lookupParMethodDecl(
              inferTypeArguments(
                  gm.type(),
                  gm.getParameterList(),
                  getArgList(),
                  gm.getTypeParameterList()));
        }
        potentiallyApplicable = potentiallyApplicable.add(decl);
      }
    }
    return potentiallyApplicable;
  }
  /**
   * @aspect MethodSignature15
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\MethodSignature.jrag:65
   */
  protected SimpleSet applicableBySubtyping(SimpleSet potentiallyApplicable) {
    SimpleSet maxSpecific = SimpleSet.emptySet;
    for (Iterator iter = potentiallyApplicable.iterator(); iter.hasNext(); ) {
      MethodDecl decl = (MethodDecl) iter.next();
      if (applicableBySubtyping(decl)) {
        maxSpecific = mostSpecific(maxSpecific, decl);
      }
    }
    return maxSpecific;
  }
  /**
   * @aspect MethodSignature15
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\MethodSignature.jrag:76
   */
  protected SimpleSet applicableByMethodInvocationConversion(
      SimpleSet potentiallyApplicable, SimpleSet maxSpecific) {
    if (maxSpecific.isEmpty()) {
      for (Iterator iter = potentiallyApplicable.iterator(); iter.hasNext(); ) {
        MethodDecl decl = (MethodDecl) iter.next();
        if (applicableByMethodInvocationConversion(decl)) {
          maxSpecific = mostSpecific(maxSpecific, decl);
        }
      }
    }
    return maxSpecific;
  }
  /**
   * @aspect MethodSignature15
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\MethodSignature.jrag:89
   */
  protected SimpleSet applicableVariableArity(SimpleSet potentiallyApplicable,
      SimpleSet maxSpecific) {
    if (maxSpecific.isEmpty()) {
      for (Iterator iter = potentiallyApplicable.iterator(); iter.hasNext(); ) {
        MethodDecl decl = (MethodDecl) iter.next();
        if (decl.isVariableArity() && applicableVariableArity(decl)) {
          maxSpecific = mostSpecific(maxSpecific, decl);
        }
      }
    }
    return maxSpecific;
  }
  /**
   * @aspect SafeVarargs
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\SafeVarargs.jrag:88
   */
  public void checkWarnings() {

    MethodDecl decl = decl();
    if (decl.getNumParameter() == 0 || decl.getNumParameter() > getNumArg()) {
      return;
    }

    ParameterDeclaration param = decl.getParameter(decl.getNumParameter()-1);
    if (!withinSuppressWarnings("unchecked")
        && !decl.hasAnnotationSafeVarargs()
        && param.isVariableArity()
        && !param.type().isReifiable()) {
      warning("unchecked array creation for variable " + "arity parameter of " + decl().name());
    }
  }
  /**
   * @aspect MethodSignature18
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\MethodSignature.jrag:702
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

    int num = getNumArg();
    for (int i = 0; i < num; i++) {
      TypeDecl t1 = i < m1.getNumParameter() - 1
          ? m1.getParameter(i).type()
          : m1.getParameter(m1.getNumParameter()-1).type().componentType();
      TypeDecl t2 = i < m2.getNumParameter() - 1
          ? m2.getParameter(i).type()
          : m2.getParameter(m2.getNumParameter()-1).type().componentType();
      if (!getArg(i).moreSpecificThan(t1, t2)) {
          return false;
      }

    }
    num++;
    if (m2.getNumParameter() == num) {
      TypeDecl t1 = num < m1.getNumParameter() - 1
          ? m1.getParameter(num).type()
          : m1.getParameter(m1.getNumParameter()-1).type().componentType();
      TypeDecl t2 = num < m2.getNumParameter() - 1
          ? m2.getParameter(num).type()
          : m2.getParameter(m2.getNumParameter()-1).type().componentType();
      if (!t1.instanceOf(t2) && !t1.withinBounds(t2, Parameterization.RAW)) {
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
  /**
   * @apilevel low-level
   * @declaredat ASTNode:25
   */
  protected int numChildren() {
    return 1;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:31
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:37
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    computeDAbefore_int_Variable_reset();
    computeDUbefore_int_Variable_reset();
    exceptionCollection_reset();
    decls_reset();
    decl_reset();
    type_reset();
    stmtCompatible_reset();
    isBooleanExpression_reset();
    isNumericExpression_reset();
    isPolyExpression_reset();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:53
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:59
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:65
   */
  public MethodAccess clone() throws CloneNotSupportedException {
    MethodAccess node = (MethodAccess) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:72
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
   * @declaredat ASTNode:91
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
   * @declaredat ASTNode:101
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
   * @declaredat ASTNode:121
   */
  public MethodAccess treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:128
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenString_ID == ((MethodAccess)node).tokenString_ID);    
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
  /**
   * @apilevel low-level
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
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\MethodSignature.jrag:33
   */
    protected SimpleSet maxSpecific(Collection candidates) {
    SimpleSet potentiallyApplicable = potentiallyApplicable(candidates);
    // first phase
    SimpleSet maxSpecific = applicableBySubtyping(potentiallyApplicable);
    // second phase
    maxSpecific = applicableByMethodInvocationConversion(potentiallyApplicable, maxSpecific);
    // third phase
    maxSpecific = applicableVariableArity(potentiallyApplicable, maxSpecific);
    return maxSpecific;
  }
  /**
   * @aspect MethodSignature15
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\MethodSignature.jrag:551
   */
    public void typeCheck() {
    for (int i = 0; i < getNumArg(); ++i) {
      if (getArg(i).type().isVoid()) {
        errorf("expression '%s' has type void and is not a valid method argument",
            getArg(i).prettyPrint());
      }
    }
    if (isQualified() && decl().isAbstract() && qualifier().isSuperAccess()) {
      error("may not access abstract methods in superclass");
    }
    if (!decl().isVariableArity() || invokesVariableArityAsArray()) {
      for (int i = 0; i < decl().getNumParameter(); i++) {
        TypeDecl exprType = getArg(i).type();
        TypeDecl parmType = decl().getParameter(i).type();
        if (!exprType.methodInvocationConversionTo(parmType) &&
            !exprType.isUnknown() && !parmType.isUnknown()) {
          errorf("argument '%s' of type %s is not compatible with the method parameter type %s",
              getArg(i).prettyPrint(), exprType.typeName(), parmType.typeName());
        }
      }
    }
  }
  /**
   * @aspect MethodSignature18
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\MethodSignature.jrag:746
   */
   
  private SimpleSet mostSpecific(SimpleSet maxSpecific, MethodDecl decl) {
    SimpleSet newMax;
    if (maxSpecific.isEmpty()) {
      newMax = maxSpecific.add(decl);
    } else {
      boolean foundStricter = false;
      newMax = SimpleSet.emptySet;
      Iterator<MethodDecl> iter = maxSpecific.iterator();
      while (iter.hasNext()) {
        MethodDecl toCompare = iter.next();
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
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeAnalysis.jrag:310
   */
  private TypeDecl refined_TypeAnalysis_MethodAccess_type()
{ return decl().type(); }
  /**
   * @apilevel internal
   */
  protected java.util.Map computeDAbefore_int_Variable_values;
  /**
   * @apilevel internal
   */
  private void computeDAbefore_int_Variable_reset() {
    computeDAbefore_int_Variable_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean computeDAbefore(int i, Variable v) {
    java.util.List _parameters = new java.util.ArrayList(2);
    _parameters.add(i);
    _parameters.add(v);
    if (computeDAbefore_int_Variable_values == null) computeDAbefore_int_Variable_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (computeDAbefore_int_Variable_values.containsKey(_parameters)) {
      return (Boolean) computeDAbefore_int_Variable_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean computeDAbefore_int_Variable_value = i == 0 ? isDAbefore(v) : getArg(i-1).isDAafter(v);
    if (isFinal && num == state().boundariesCrossed) {
      computeDAbefore_int_Variable_values.put(_parameters, computeDAbefore_int_Variable_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return computeDAbefore_int_Variable_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isDAafter(Variable v) {
    boolean isDAafter_Variable_value = getNumArg() == 0 ? isDAbefore(v) : getArg(getNumArg()-1).isDAafter(v);

    return isDAafter_Variable_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isDAafterTrue(Variable v) {
    boolean isDAafterTrue_Variable_value = isFalse() || (getNumArg() == 0 ? isDAbefore(v) : getArg(getNumArg()-1).isDAafter(v));

    return isDAafterTrue_Variable_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isDAafterFalse(Variable v) {
    boolean isDAafterFalse_Variable_value = isTrue() || (getNumArg() == 0 ? isDAbefore(v) : getArg(getNumArg()-1).isDAafter(v));

    return isDAafterFalse_Variable_value;
  }
  /**
   * @apilevel internal
   */
  protected java.util.Map computeDUbefore_int_Variable_values;
  /**
   * @apilevel internal
   */
  private void computeDUbefore_int_Variable_reset() {
    computeDUbefore_int_Variable_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean computeDUbefore(int i, Variable v) {
    java.util.List _parameters = new java.util.ArrayList(2);
    _parameters.add(i);
    _parameters.add(v);
    if (computeDUbefore_int_Variable_values == null) computeDUbefore_int_Variable_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (computeDUbefore_int_Variable_values.containsKey(_parameters)) {
      return (Boolean) computeDUbefore_int_Variable_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean computeDUbefore_int_Variable_value = i == 0 ? isDUbefore(v) : getArg(i-1).isDUafter(v);
    if (isFinal && num == state().boundariesCrossed) {
      computeDUbefore_int_Variable_values.put(_parameters, computeDUbefore_int_Variable_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return computeDUbefore_int_Variable_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isDUafter(Variable v) {
    boolean isDUafter_Variable_value = getNumArg() == 0 ? isDUbefore(v) : getArg(getNumArg()-1).isDUafter(v);

    return isDUafter_Variable_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isDUafterTrue(Variable v) {
    boolean isDUafterTrue_Variable_value = isFalse() || (getNumArg() == 0 ? isDUbefore(v) : getArg(getNumArg()-1).isDUafter(v));

    return isDUafterTrue_Variable_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isDUafterFalse(Variable v) {
    boolean isDUafterFalse_Variable_value = isTrue() || (getNumArg() == 0 ? isDUbefore(v) : getArg(getNumArg()-1).isDUafter(v));

    return isDUafterFalse_Variable_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean exceptionCollection_computed = false;
  /**
   * @apilevel internal
   */
  protected Collection exceptionCollection_value;
  /**
   * @apilevel internal
   */
  private void exceptionCollection_reset() {
    exceptionCollection_computed = false;
    exceptionCollection_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public Collection exceptionCollection() {
    ASTNode$State state = state();
    if (exceptionCollection_computed) {
      return exceptionCollection_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    exceptionCollection_value = exceptionCollection_compute();
    if (isFinal && num == state().boundariesCrossed) {
      exceptionCollection_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return exceptionCollection_value;
  }
  /**
   * @apilevel internal
   */
  private Collection exceptionCollection_compute() {
      //System.out.println("Computing exceptionCollection for " + name());
      HashSet set = new HashSet();
      Iterator iter = decls().iterator();
      if (!iter.hasNext()) {
        return set;
      }
  
      MethodDecl m = (MethodDecl) iter.next();
      //System.out.println("Processing first found method " + m.signature() + " in " + m.hostType().fullName());
  
      for (int i = 0; i < m.getNumException(); i++) {
        TypeDecl exceptionType = m.getException(i).type();
        set.add(exceptionType);
      }
      while (iter.hasNext()) {
        HashSet first = new HashSet();
        first.addAll(set);
        HashSet second = new HashSet();
        m = (MethodDecl) iter.next();
        //System.out.println("Processing the next method " + m.signature() + " in " + m.hostType().fullName());
        for (int i = 0; i < m.getNumException(); i++) {
          TypeDecl exceptionType = m.getException(i).type();
          second.add(exceptionType);
        }
        set = new HashSet();
        for (Iterator i1 = first.iterator(); i1.hasNext(); ) {
          TypeDecl firstType = (TypeDecl) i1.next();
          for (Iterator i2 = second.iterator(); i2.hasNext(); ) {
            TypeDecl secondType = (TypeDecl) i2.next();
            if (firstType.instanceOf(secondType)) {
              set.add(firstType);
            } else if (secondType.instanceOf(firstType)) {
              set.add(secondType);
            }
          }
        }
      }
      return set;
    }
  /**
   * @attribute syn
   * @aspect LookupMethod
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupMethod.jrag:105
   */
  @ASTNodeAnnotation.Attribute
  public MethodDecl singleCandidateDecl() {
    {
        MethodDecl result = null;
        for (Iterator iter = lookupMethod(name()).iterator(); iter.hasNext(); ) {
          MethodDecl m = (MethodDecl) iter.next();
          if (result == null) {
            result = m;
          } else if (m.getNumParameter() == getNumArg() && result.getNumParameter() != getNumArg()) {
            result = m;
          }
        }
        return result;
      }
  }
  /**
   * @apilevel internal
   */
  protected boolean decls_computed = false;
  /**
   * @apilevel internal
   */
  protected SimpleSet decls_value;
  /**
   * @apilevel internal
   */
  private void decls_reset() {
    decls_computed = false;
    decls_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public SimpleSet decls() {
    ASTNode$State state = state();
    if (decls_computed) {
      return decls_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    decls_value = decls_compute();
    if (isFinal && num == state().boundariesCrossed) {
      decls_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return decls_value;
  }
  /**
   * @apilevel internal
   */
  private SimpleSet decls_compute() {
      SimpleSet maxSpecific = maxSpecific(lookupMethod(name()));
      if (isQualified() ? qualifier().staticContextQualifier() : inStaticContext()) {
        maxSpecific = removeInstanceMethods(maxSpecific);
      }
      return maxSpecific;
    }
  /**
   * @apilevel internal
   */
  protected boolean decl_computed = false;
  /**
   * @apilevel internal
   */
  protected MethodDecl decl_value;
  /**
   * @apilevel internal
   */
  private void decl_reset() {
    decl_computed = false;
    decl_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public MethodDecl decl() {
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
  private MethodDecl decl_compute() {
      SimpleSet decls = decls();
      if (decls.size() == 1) {
        return (MethodDecl) decls.iterator().next();
      }
  
      // 8.4.6.4 - only return the first method in case of multply inherited abstract methods
      boolean allAbstract = true;
      for (Iterator iter = decls.iterator(); iter.hasNext() && allAbstract; ) {
        MethodDecl m = (MethodDecl) iter.next();
        if (!m.isAbstract() && !m.hostType().isObject()) {
          allAbstract = false;
        }
      }
      if (decls.size() > 1 && allAbstract) {
        return (MethodDecl) decls.iterator().next();
      }
      return unknownMethod();
    }
  /**
   * @attribute syn
   * @aspect MethodDecl
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupMethod.jrag:262
   */
  @ASTNodeAnnotation.Attribute
  public boolean accessible(MethodDecl m) {
    {
        if (!isQualified()) {
          return true;
        }
        if (!m.accessibleFrom(hostType())) {
          return false;
        }
        // the method is not accessible if the type is not accessible
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
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\NameCheck.jrag:88
   */
  @ASTNodeAnnotation.Attribute
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
  @ASTNodeAnnotation.Attribute
  public String name() {
    String name_value = getID();

    return name_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isMethodAccess() {
    boolean isMethodAccess_value = true;

    return isMethodAccess_value;
  }
  @ASTNodeAnnotation.Attribute
  public NameType predNameType() {
    NameType predNameType_value = NameType.AMBIGUOUS_NAME;

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
   * @aspect MethodSignature15
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\MethodSignature.jrag:279
   */
  @ASTNodeAnnotation.Attribute
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
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\MethodSignature.jrag:319
   */
  @ASTNodeAnnotation.Attribute
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
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\MethodSignature.jrag:343
   */
  @ASTNodeAnnotation.Attribute
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
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\MethodSignature.jrag:438
   */
  @ASTNodeAnnotation.Attribute
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
          if (!(arity() >= m.arity()-1)) {
            return false;
          }
          for (int i = 0; i < m.arity() - 2; i++) {
            if (!getArg(i).potentiallyCompatible(m.getParameter(i).type(), m)) {
              return false;
            }
          }
          TypeDecl varArgType = m.getParameter(m.arity()-1).type();
          if (arity() == m.arity()) {
            if (!getArg(arity()-1).potentiallyCompatible(varArgType, m)
                && !getArg(arity()-1).potentiallyCompatible(varArgType.componentType(), m)) {
              return false;
            }
          } else if (arity() > m.arity()) {
            for (int i = m.arity()-1; i < arity(); i++) {
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
            Parameterization par = new SimpleParameterization(gm.getTypeParameterList(), typeArguments);
            for (int i = 0; i < gm.getNumTypeParameter(); i++) {
              if (!((TypeDecl) typeArguments.get(i)).withinBounds(gm.original().getTypeParameter(i), par)) {
                return false;
              }
            }
          }
        }
    
        return true;
      }
  }
  @ASTNodeAnnotation.Attribute
  public int arity() {
    int arity_value = getNumArg();

    return arity_value;
  }
  /**
   * @attribute syn
   * @aspect VariableArityParameters
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\VariableArityParameters.jrag:61
   */
  @ASTNodeAnnotation.Attribute
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
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\PreciseRethrow.jrag:149
   */
  @ASTNodeAnnotation.Attribute
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
   * @apilevel internal
   */
  protected boolean stmtCompatible_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean stmtCompatible_value;
  /**
   * @apilevel internal
   */
  private void stmtCompatible_reset() {
    stmtCompatible_computed = false;
  }
  @ASTNodeAnnotation.Attribute
  public boolean stmtCompatible() {
    ASTNode$State state = state();
    if (stmtCompatible_computed) {
      return stmtCompatible_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    stmtCompatible_value = true;
    if (isFinal && num == state().boundariesCrossed) {
      stmtCompatible_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return stmtCompatible_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean isBooleanExpression_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean isBooleanExpression_value;
  /**
   * @apilevel internal
   */
  private void isBooleanExpression_reset() {
    isBooleanExpression_computed = false;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isBooleanExpression() {
    ASTNode$State state = state();
    if (isBooleanExpression_computed) {
      return isBooleanExpression_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    isBooleanExpression_value = isBooleanExpression_compute();
    if (isFinal && num == state().boundariesCrossed) {
      isBooleanExpression_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return isBooleanExpression_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isBooleanExpression_compute() {
      MethodDecl decl = decl();
      if (decl instanceof ParMethodDecl) {
        return ((ParMethodDecl) decl).genericMethodDecl().type().isBoolean();
      } else {
        return decl.type().isBoolean();
      }
    }
  /**
   * @apilevel internal
   */
  protected boolean isNumericExpression_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean isNumericExpression_value;
  /**
   * @apilevel internal
   */
  private void isNumericExpression_reset() {
    isNumericExpression_computed = false;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isNumericExpression() {
    ASTNode$State state = state();
    if (isNumericExpression_computed) {
      return isNumericExpression_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    isNumericExpression_value = isNumericExpression_compute();
    if (isFinal && num == state().boundariesCrossed) {
      isNumericExpression_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return isNumericExpression_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isNumericExpression_compute() {
      MethodDecl decl = decl();
      if (decl instanceof ParMethodDecl) {
        return ((ParMethodDecl) decl).genericMethodDecl().type().isNumericType();
      } else {
        return decl.type().isNumericType();
      }
    }
  /**
   * @apilevel internal
   */
  protected boolean isPolyExpression_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean isPolyExpression_value;
  /**
   * @apilevel internal
   */
  private void isPolyExpression_reset() {
    isPolyExpression_computed = false;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isPolyExpression() {
    ASTNode$State state = state();
    if (isPolyExpression_computed) {
      return isPolyExpression_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    isPolyExpression_value = isPolyExpression_compute();
    if (isFinal && num == state().boundariesCrossed) {
      isPolyExpression_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return isPolyExpression_value;
  }
  /**
   * @apilevel internal
   */
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
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ExceptionHandling.jrag:76
   */
  @ASTNodeAnnotation.Attribute
  public boolean handlesException(TypeDecl exceptionType) {
    boolean handlesException_TypeDecl_value = getParent().Define_handlesException(this, null, exceptionType);

    return handlesException_TypeDecl_value;
  }
  /**
   * @attribute inh
   * @aspect LookupMethod
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupMethod.jrag:37
   */
  @ASTNodeAnnotation.Attribute
  public MethodDecl unknownMethod() {
    MethodDecl unknownMethod_value = getParent().Define_unknownMethod(this, null);

    return unknownMethod_value;
  }
  /**
   * @attribute inh
   * @aspect TypeHierarchyCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:161
   */
  @ASTNodeAnnotation.Attribute
  public boolean inExplicitConstructorInvocation() {
    boolean inExplicitConstructorInvocation_value = getParent().Define_inExplicitConstructorInvocation(this, null);

    return inExplicitConstructorInvocation_value;
  }
  /**
   * @attribute inh
   * @aspect SuppressWarnings
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\SuppressWarnings.jrag:40
   */
  @ASTNodeAnnotation.Attribute
  public boolean withinSuppressWarnings(String annot) {
    boolean withinSuppressWarnings_String_value = getParent().Define_withinSuppressWarnings(this, null, annot);

    return withinSuppressWarnings_String_value;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:255
   * @apilevel internal
   */
  public boolean Define_isDAbefore(ASTNode caller, ASTNode child, Variable v) {
    if (caller == getArgListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:450
      int i = caller.getIndexOfChild(child);
      return computeDAbefore(i, v);
    }
    else {
      return getParent().Define_isDAbefore(this, caller, v);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:779
   * @apilevel internal
   */
  public boolean Define_isDUbefore(ASTNode caller, ASTNode child, Variable v) {
    if (caller == getArgListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:958
      int i = caller.getIndexOfChild(child);
      return computeDUbefore(i, v);
    }
    else {
      return getParent().Define_isDUbefore(this, caller, v);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupMethod.jrag:46
   * @apilevel internal
   */
  public Collection Define_lookupMethod(ASTNode caller, ASTNode child, String name) {
    if (caller == getArgListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupMethod.jrag:50
      int childIndex = caller.getIndexOfChild(child);
      return unqualifiedScope().lookupMethod(name);
    }
    else {
      return getParent().Define_lookupMethod(this, caller, name);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:115
   * @apilevel internal
   */
  public boolean Define_hasPackage(ASTNode caller, ASTNode child, String packageName) {
    if (caller == getArgListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:116
      int childIndex = caller.getIndexOfChild(child);
      return unqualifiedScope().hasPackage(packageName);
    }
    else {
      return getParent().Define_hasPackage(this, caller, packageName);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\GenericMethods.jrag:197
   * @apilevel internal
   */
  public SimpleSet Define_lookupType(ASTNode caller, ASTNode child, String name) {
    if (caller == getArgListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:330
      int childIndex = caller.getIndexOfChild(child);
      return unqualifiedScope().lookupType(name);
    }
    else {
      return getParent().Define_lookupType(this, caller, name);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\LookupVariable.jrag:30
   * @apilevel internal
   */
  public SimpleSet Define_lookupVariable(ASTNode caller, ASTNode child, String name) {
    if (caller == getArgListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupVariable.jrag:218
      int childIndex = caller.getIndexOfChild(child);
      return unqualifiedScope().lookupVariable(name);
    }
    else {
      return getParent().Define_lookupVariable(this, caller, name);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\SyntacticClassification.jrag:36
   * @apilevel internal
   */
  public NameType Define_nameType(ASTNode caller, ASTNode child) {
    if (caller == getArgListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\SyntacticClassification.jrag:143
      int childIndex = caller.getIndexOfChild(child);
      return NameType.EXPRESSION_NAME;
    }
    else {
      return getParent().Define_nameType(this, caller);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:32
   * @apilevel internal
   */
  public String Define_methodHost(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return unqualifiedScope().methodHost();
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\GenericMethodsInference.jrag:58
   * @apilevel internal
   */
  public TypeDecl Define_assignConvertedType(ASTNode caller, ASTNode child) {
    if (caller == getArgListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\GenericMethodsInference.jrag:66
      int childIndex = caller.getIndexOfChild(child);
      return typeObject();
    }
    else {
      return getParent().Define_assignConvertedType(this, caller);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:30
   * @apilevel internal
   */
  public TypeDecl Define_targetType(ASTNode caller, ASTNode child) {
    if (caller == getArgListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:87
      int i = caller.getIndexOfChild(child);
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
      return getParent().Define_targetType(this, caller);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:196
   * @apilevel internal
   */
  public boolean Define_assignmentContext(ASTNode caller, ASTNode child) {
    if (caller == getArgListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:331
      int childIndex = caller.getIndexOfChild(child);
      return false;
    }
    else {
      return getParent().Define_assignmentContext(this, caller);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:197
   * @apilevel internal
   */
  public boolean Define_invocationContext(ASTNode caller, ASTNode child) {
    if (caller == getArgListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:332
      int childIndex = caller.getIndexOfChild(child);
      return true;
    }
    else {
      return getParent().Define_invocationContext(this, caller);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:198
   * @apilevel internal
   */
  public boolean Define_castContext(ASTNode caller, ASTNode child) {
    if (caller == getArgListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:333
      int childIndex = caller.getIndexOfChild(child);
      return false;
    }
    else {
      return getParent().Define_castContext(this, caller);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:199
   * @apilevel internal
   */
  public boolean Define_stringContext(ASTNode caller, ASTNode child) {
    if (caller == getArgListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:334
      int childIndex = caller.getIndexOfChild(child);
      return false;
    }
    else {
      return getParent().Define_stringContext(this, caller);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:200
   * @apilevel internal
   */
  public boolean Define_numericContext(ASTNode caller, ASTNode child) {
    if (caller == getArgListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:335
      int childIndex = caller.getIndexOfChild(child);
      return false;
    }
    else {
      return getParent().Define_numericContext(this, caller);
    }
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
