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
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\grammar\\Java.ast:101
 * @production Expr : {@link ASTNode};

 */
public abstract class Expr extends ASTNode<ASTNode> implements Cloneable {
  /**
   * @aspect TypeScopePropagation
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:580
   */
  public SimpleSet keepAccessibleTypes(SimpleSet oldSet) {
    SimpleSet newSet = SimpleSet.emptySet;
    TypeDecl hostType = hostType();
    for (Iterator iter = oldSet.iterator(); iter.hasNext(); ) {
      TypeDecl t = (TypeDecl) iter.next();
      if ((hostType != null && t.accessibleFrom(hostType))
          || (hostType == null && t.accessibleFromPackage(hostPackage()))) {
        newSet = newSet.add(t);
      }
    }
    return newSet;
  }
  /**
   * Remove fields that are not accessible when using this Expr as qualifier
   * @return a set containing the accessible fields
   * @aspect VariableScope
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupVariable.jrag:260
   */
  public SimpleSet keepAccessibleFields(SimpleSet oldSet) {
    SimpleSet newSet = SimpleSet.emptySet;
    for (Iterator iter = oldSet.iterator(); iter.hasNext(); ) {
      Variable v = (Variable) iter.next();
      if (v instanceof FieldDeclaration) {
        FieldDeclaration f = (FieldDeclaration) v;
        if (mayAccess(f)) {
          newSet = newSet.add(f);
        }
      }
    }
    return newSet;
  }
  /**
   * @see "JLS $6.6.2.1"
   * @return true if the expression may access the given field
   * @aspect VariableScope
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupVariable.jrag:289
   */
  public boolean mayAccess(FieldDeclaration f) {
    if (f.isPublic()) {
      return true;
    } else if (f.isProtected()) {
      if (f.hostPackage().equals(hostPackage())) {
        return true;
      }
      return hostType().mayAccess(this, f);
    } else if (f.isPrivate()) {
      return f.hostType().topLevelType() == hostType().topLevelType();
    } else {
      return f.hostPackage().equals(hostType().hostPackage());
    }
  }
  /**
   * Creates a qualified expression. This will not be subject to rewriting.
   * @aspect QualifiedNames
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:136
   */
  public Dot qualifiesAccess(Access access) {
    Dot dot = new Dot(this, access);
    dot.setStart(this.getStart());
    dot.setEnd(access.getEnd());
    return dot;
  }
  /**
   * Infer type arguments based on the actual arguments and result assignment type.
   * @aspect GenericMethodsInference
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\GenericMethodsInference.jrag:73
   */
  public Collection<TypeDecl> computeConstraints(
      TypeDecl resultType,
      List<ParameterDeclaration> params,
      List<Expr> args,
      List<TypeVariable> typeParams) {
    Constraints c = new Constraints();
    // Store type parameters.
    for (int i = 0; i < typeParams.getNumChild(); i++) {
      c.addTypeVariable(typeParams.getChild(i));
    }

    // Add initial constraints.
    for (int i = 0; i < args.getNumChild(); i++) {
      TypeDecl A = args.getChild(i).type();
      int index = i >= params.getNumChild() ? params.getNumChild() - 1 : i;
      TypeDecl F = params.getChild(index).type();
      if (params.getChild(index) instanceof VariableArityParameterDeclaration
         && (args.getNumChild() != params.getNumChild() || !A.isArrayDecl())) {
        F = F.componentType();
      }
      c.convertibleTo(A, F);
    }

    if (c.rawAccess) {
      return new ArrayList<TypeDecl>();
    }

    //c.printConstraints();
    //System.err.println("Resolving equality constraints");
    c.resolveEqualityConstraints();
    //c.printConstraints();

    //System.err.println("Resolving supertype constraints");
    c.resolveSupertypeConstraints();
    //c.printConstraints();

    //System.err.println("Resolving unresolved type arguments");
    //c.resolveBounds();
    //c.printConstraints();

    if (c.unresolvedTypeArguments()) {
      TypeDecl S = assignConvertedType();
      if (S.isUnboxedPrimitive()) {
        S = S.boxed();
      }
      TypeDecl R = resultType;
      // TODO: replace all uses of type variables in R with their inferred types
      TypeDecl Rprime = R;
      if (R.isVoid()) {
        R = typeObject();
      }
      c.convertibleFrom(S, R);
      // TODO: additional constraints

      c.resolveEqualityConstraints();
      c.resolveSupertypeConstraints();
      //c.resolveBounds();

      c.resolveSubtypeConstraints();
    }

    return c.typeArguments();
  }
  /**
   * @aspect MethodSignature15
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\MethodSignature.jrag:177
   */
  protected static SimpleSet mostSpecific(SimpleSet maxSpecific, ConstructorDecl decl) {
    if (maxSpecific.isEmpty()) {
      maxSpecific = maxSpecific.add(decl);
    } else {
      ConstructorDecl other = (ConstructorDecl) maxSpecific.iterator().next();
      if (decl.moreSpecificThan(other)) {
        maxSpecific = SimpleSet.emptySet.add(decl);
      } else if (!other.moreSpecificThan(decl)) {
        maxSpecific = maxSpecific.add(decl);
      }
    }
    return maxSpecific;
  }
  /**
   * @aspect MethodSignature18
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\MethodSignature.jrag:992
   */
  protected static boolean moreSpecificThan(ConstructorDecl m1, ConstructorDecl m2, List<Expr> argList) {
    if (m1 instanceof ParConstructorDecl) {
      return m1.moreSpecificThan(m2);
    }
    if (m1.getNumParameter() == 0) {
      return false;
    }
    if (!m1.isVariableArity() && !m2.isVariableArity()) {
      for (int i = 0; i < m1.getNumParameter(); i++) {
        Expr arg = argList.getChild(i);
        if (!arg.moreSpecificThan(m1.getParameter(i).type(), m2.getParameter(i).type())) {
          return false;
        }
      }
      return true;
    }

    int num = argList.getNumChild();
    for (int i = 0; i < num; i++) {
      TypeDecl t1 = i < m1.getNumParameter() - 1 ? m1.getParameter(i).type() : m1.getParameter(m1.getNumParameter()-1).type().componentType();
      TypeDecl t2 = i < m2.getNumParameter() - 1 ? m2.getParameter(i).type() : m2.getParameter(m2.getNumParameter()-1).type().componentType();

      Expr arg = (Expr) argList.getChild(i);
      if (!arg.moreSpecificThan(t1, t2)) {
          return false;
      }
    }
    return true;
  }
  /**
   * @aspect MethodSignature18
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\MethodSignature.jrag:1022
   */
  protected static SimpleSet mostSpecific(SimpleSet maxSpecific, ConstructorDecl decl, List<Expr> argList) {
    SimpleSet newMax;
    if (maxSpecific.isEmpty()) {
      newMax = maxSpecific.add(decl);
    } else {
      boolean foundStricter = false;
      newMax = SimpleSet.emptySet;
      Iterator<ConstructorDecl> iter = maxSpecific.iterator();
      while (iter.hasNext()) {
        ConstructorDecl toCompare = iter.next();
        if (!(moreSpecificThan(decl, toCompare, argList) && !moreSpecificThan(toCompare, decl, argList))) {
          newMax = newMax.add(toCompare);
        }

        if (!moreSpecificThan(decl, toCompare, argList) && moreSpecificThan(toCompare, decl, argList)) {
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
   * @declaredat ASTNode:1
   */
  public Expr() {
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
   * @apilevel low-level
   * @declaredat ASTNode:15
   */
  protected int numChildren() {
    return 0;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:21
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:27
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    inferTypeArguments_TypeDecl_List_ParameterDeclaration__List_Expr__List_TypeVariable__reset();
    stmtCompatible_reset();
    compatibleStrictContext_TypeDecl_reset();
    compatibleLooseContext_TypeDecl_reset();
    pertinentToApplicability_Expr_BodyDecl_int_reset();
    moreSpecificThan_TypeDecl_TypeDecl_reset();
    potentiallyCompatible_TypeDecl_BodyDecl_reset();
    isBooleanExpression_reset();
    isNumericExpression_reset();
    isPolyExpression_reset();
    assignConversionTo_TypeDecl_reset();
    nameType_reset();
    targetType_reset();
    assignmentContext_reset();
    invocationContext_reset();
    castContext_reset();
    stringContext_reset();
    numericContext_reset();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:51
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:57
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:63
   */
  public Expr clone() throws CloneNotSupportedException {
    Expr node = (Expr) super.clone();
    return node;
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @deprecated Please use treeCopy or treeCopyNoTransform instead
   * @declaredat ASTNode:74
   */
  @Deprecated
  public abstract Expr fullCopy();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:82
   */
  public abstract Expr treeCopyNoTransform();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The subtree of this node is traversed to trigger rewrites before copy.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:90
   */
  public abstract Expr treeCopy();
  /**
   * @aspect MethodSignature18
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\MethodSignature.jrag:842
   */
   
  protected SimpleSet chooseConstructor(Collection constructors, List<Expr> argList) {
    SimpleSet potentiallyApplicable = SimpleSet.emptySet;

    // Select potentially applicable constructors.
    for (Iterator iter = constructors.iterator(); iter.hasNext(); ) {
      ConstructorDecl decl = (ConstructorDecl) iter.next();
      if (decl.potentiallyApplicable(argList) && decl.accessibleFrom(hostType())) {
        if (decl.isGeneric()) {
          GenericConstructorDecl gc = decl.genericDecl();
          decl = gc.lookupParConstructorDecl(
              inferTypeArguments(
                  gc.type(),
                  gc.getParameterList(),
                  argList,
                  gc.getTypeParameterList()));
        }
        potentiallyApplicable = potentiallyApplicable.add(decl);
      }
    }

    // First phase.
    SimpleSet maxSpecific = SimpleSet.emptySet;
    for (Iterator iter = potentiallyApplicable.iterator(); iter.hasNext(); ) {
      ConstructorDecl decl = (ConstructorDecl) iter.next();
      if (decl.applicableByStrictInvocation(this, argList)) {
        maxSpecific = mostSpecific(maxSpecific, decl, argList);
      }
    }

    // Second phase.
    if (maxSpecific.isEmpty()) {
      for (Iterator iter = potentiallyApplicable.iterator(); iter.hasNext(); ) {
        ConstructorDecl decl = (ConstructorDecl) iter.next();
        if (decl.applicableByLooseInvocation(this, argList)) {
          maxSpecific = mostSpecific(maxSpecific, decl, argList);
        }
      }
    }

    // Third phase.
    if (maxSpecific.isEmpty()) {
      for (Iterator iter = potentiallyApplicable.iterator(); iter.hasNext(); ) {
        ConstructorDecl decl = (ConstructorDecl) iter.next();
        if (decl.isVariableArity() && decl.applicableByVariableArityInvocation(this, argList)) {
          maxSpecific = mostSpecific(maxSpecific, decl, argList);
        }
      }
    }
    return maxSpecific;
  }
  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeAnalysis.jrag:302
   */
  @ASTNodeAnnotation.Attribute
  public abstract TypeDecl type();
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ConstantExpression.jrag:32
   */
  @ASTNodeAnnotation.Attribute
  public Constant constant() {
    {
        throw new UnsupportedOperationException("ConstantExpression operation constant"
            + " not supported for type " + getClass().getName());
      }
  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ConstantExpression.jrag:220
   */
  @ASTNodeAnnotation.Attribute
  public boolean representableIn(TypeDecl t) {
    {
        if (!type().isByte() && !type().isChar() && !type().isShort() && !type().isInt()) {
          return false;
        }
        if (t.isByte()) {
          return constant().intValue() >= Byte.MIN_VALUE && constant().intValue() <= Byte.MAX_VALUE;
        }
        if (t.isChar()) {
          return constant().intValue() >= Character.MIN_VALUE && constant().intValue() <= Character.MAX_VALUE;
        }
        if (t.isShort()) {
          return constant().intValue() >= Short.MIN_VALUE && constant().intValue() <= Short.MAX_VALUE;
        }
        if (t.isInt()) {
          return constant().intValue() >= Integer.MIN_VALUE && constant().intValue() <= Integer.MAX_VALUE;
        }
        return false;
      }
  }
  @ASTNodeAnnotation.Attribute
  public boolean isConstant() {
    boolean isConstant_value = false;

    return isConstant_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isTrue() {
    boolean isTrue_value = isConstant() && type() instanceof BooleanType && constant().booleanValue();

    return isTrue_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isFalse() {
    boolean isFalse_value = isConstant() && type() instanceof BooleanType && !constant().booleanValue();

    return isFalse_value;
  }
  @ASTNodeAnnotation.Attribute
  public Variable varDecl() {
    Variable varDecl_value = null;

    return varDecl_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isDAafterFalse(Variable v) {
    boolean isDAafterFalse_Variable_value = isTrue() || isDAafter(v);

    return isDAafterFalse_Variable_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isDAafterTrue(Variable v) {
    boolean isDAafterTrue_Variable_value = isFalse() || isDAafter(v);

    return isDAafterTrue_Variable_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isDAafter(Variable v) {
    boolean isDAafter_Variable_value = isDAbefore(v);

    return isDAafter_Variable_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isDUafterFalse(Variable v) {
    boolean isDUafterFalse_Variable_value = isTrue() || isDUafter(v);

    return isDUafterFalse_Variable_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isDUafterTrue(Variable v) {
    boolean isDUafterTrue_Variable_value = isFalse() || isDUafter(v);

    return isDUafterTrue_Variable_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isDUafter(Variable v) {
    boolean isDUafter_Variable_value = isDUbefore(v);

    return isDUafter_Variable_value;
  }
  /**
   * Compute the most specific constructor in a collection.
   * The constructor is invoked with the arguments specified in argList.
   * The curent context (this) is used to evaluate the hostType for accessibility.
   * @attribute syn
   * @aspect ConstructScope
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupConstructor.jrag:55
   */
  @ASTNodeAnnotation.Attribute
  public SimpleSet mostSpecificConstructor(Collection<ConstructorDecl> constructors) {
    {
        SimpleSet maxSpecific = SimpleSet.emptySet;
        for (Iterator iter = constructors.iterator(); iter.hasNext(); ) {
          ConstructorDecl decl = (ConstructorDecl) iter.next();
          if (applicableAndAccessible(decl)) {
            if (maxSpecific.isEmpty()) {
              maxSpecific = maxSpecific.add(decl);
            } else {
              ConstructorDecl other = (ConstructorDecl) maxSpecific.iterator().next();
              if (decl.moreSpecificThan(other)) {
                maxSpecific = SimpleSet.emptySet.add(decl);
              } else if (!other.moreSpecificThan(decl)) {
                maxSpecific = maxSpecific.add(decl);
              }
            }
          }
        }
        return maxSpecific;
      }
  }
  @ASTNodeAnnotation.Attribute
  public boolean applicableAndAccessible(ConstructorDecl decl) {
    boolean applicableAndAccessible_ConstructorDecl_value = false;

    return applicableAndAccessible_ConstructorDecl_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean hasQualifiedPackage(String packageName) {
    boolean hasQualifiedPackage_String_value = false;

    return hasQualifiedPackage_String_value;
  }
  @ASTNodeAnnotation.Attribute
  public SimpleSet qualifiedLookupType(String name) {
    SimpleSet qualifiedLookupType_String_value = keepAccessibleTypes(type().memberTypes(name));

    return qualifiedLookupType_String_value;
  }
  /**
   * @attribute syn
   * @aspect VariableScope
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupVariable.jrag:237
   */
  @ASTNodeAnnotation.Attribute
  public SimpleSet qualifiedLookupVariable(String name) {
    {
        if (type().accessibleFrom(hostType())) {
          return keepAccessibleFields(type().memberFields(name));
        }
        return SimpleSet.emptySet;
      }
  }
  @ASTNodeAnnotation.Attribute
  public boolean isPositive() {
    boolean isPositive_value = false;

    return isPositive_value;
  }
  @ASTNodeAnnotation.Attribute
  public String packageName() {
    String packageName_value = "";

    return packageName_value;
  }
  @ASTNodeAnnotation.Attribute
  public String typeName() {
    String typeName_value = "";

    return typeName_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isTypeAccess() {
    boolean isTypeAccess_value = false;

    return isTypeAccess_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isMethodAccess() {
    boolean isMethodAccess_value = false;

    return isMethodAccess_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isFieldAccess() {
    boolean isFieldAccess_value = false;

    return isFieldAccess_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isSuperAccess() {
    boolean isSuperAccess_value = false;

    return isSuperAccess_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isThisAccess() {
    boolean isThisAccess_value = false;

    return isThisAccess_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isPackageAccess() {
    boolean isPackageAccess_value = false;

    return isPackageAccess_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isArrayAccess() {
    boolean isArrayAccess_value = false;

    return isArrayAccess_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isClassAccess() {
    boolean isClassAccess_value = false;

    return isClassAccess_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isSuperConstructorAccess() {
    boolean isSuperConstructorAccess_value = false;

    return isSuperConstructorAccess_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isLeftChildOfDot() {
    boolean isLeftChildOfDot_value = hasParentDot() && parentDot().getLeft() == this;

    return isLeftChildOfDot_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isRightChildOfDot() {
    boolean isRightChildOfDot_value = hasParentDot() && parentDot().getRight() == this;

    return isRightChildOfDot_value;
  }
  @ASTNodeAnnotation.Attribute
  public AbstractDot parentDot() {
    AbstractDot parentDot_value = getParent() instanceof AbstractDot ?
        (AbstractDot) getParent() : null;

    return parentDot_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean hasParentDot() {
    boolean hasParentDot_value = parentDot() != null;

    return hasParentDot_value;
  }
  @ASTNodeAnnotation.Attribute
  public Access nextAccess() {
    Access nextAccess_value = parentDot().nextAccess();

    return nextAccess_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean hasNextAccess() {
    boolean hasNextAccess_value = isLeftChildOfDot();

    return hasNextAccess_value;
  }
  /**
   * @attribute syn
   * @aspect NestedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeAnalysis.jrag:552
   */
  @ASTNodeAnnotation.Attribute
  public Stmt enclosingStmt() {
    {
        ASTNode node = this;
        while (node != null && !(node instanceof Stmt)) {
          node = node.getParent();
        }
        return (Stmt) node;
      }
  }
  @ASTNodeAnnotation.Attribute
  public boolean isVariable() {
    boolean isVariable_value = false;

    return isVariable_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isUnknown() {
    boolean isUnknown_value = type().isUnknown();

    return isUnknown_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean staticContextQualifier() {
    boolean staticContextQualifier_value = false;

    return staticContextQualifier_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isEnumConstant() {
    boolean isEnumConstant_value = false;

    return isEnumConstant_value;
  }
  /**
   * @apilevel internal
   */
  protected java.util.Map inferTypeArguments_TypeDecl_List_ParameterDeclaration__List_Expr__List_TypeVariable__values;
  /**
   * @apilevel internal
   */
  private void inferTypeArguments_TypeDecl_List_ParameterDeclaration__List_Expr__List_TypeVariable__reset() {
    inferTypeArguments_TypeDecl_List_ParameterDeclaration__List_Expr__List_TypeVariable__values = null;
  }
  @ASTNodeAnnotation.Attribute
  public ArrayList<TypeDecl> inferTypeArguments(TypeDecl resultType, List<ParameterDeclaration> params, List<Expr> args, List<TypeVariable> typeParams) {
    java.util.List _parameters = new java.util.ArrayList(4);
    _parameters.add(resultType);
    _parameters.add(params);
    _parameters.add(args);
    _parameters.add(typeParams);
    if (inferTypeArguments_TypeDecl_List_ParameterDeclaration__List_Expr__List_TypeVariable__values == null) inferTypeArguments_TypeDecl_List_ParameterDeclaration__List_Expr__List_TypeVariable__values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (inferTypeArguments_TypeDecl_List_ParameterDeclaration__List_Expr__List_TypeVariable__values.containsKey(_parameters)) {
      return (ArrayList<TypeDecl>) inferTypeArguments_TypeDecl_List_ParameterDeclaration__List_Expr__List_TypeVariable__values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    ArrayList<TypeDecl> inferTypeArguments_TypeDecl_List_ParameterDeclaration__List_Expr__List_TypeVariable__value = inferTypeArguments_compute(resultType, params, args, typeParams);
    if (isFinal && num == state().boundariesCrossed) {
      inferTypeArguments_TypeDecl_List_ParameterDeclaration__List_Expr__List_TypeVariable__values.put(_parameters, inferTypeArguments_TypeDecl_List_ParameterDeclaration__List_Expr__List_TypeVariable__value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return inferTypeArguments_TypeDecl_List_ParameterDeclaration__List_Expr__List_TypeVariable__value;
  }
  /**
   * @apilevel internal
   */
  private ArrayList<TypeDecl> inferTypeArguments_compute(TypeDecl resultType, List<ParameterDeclaration> params, List<Expr> args, List<TypeVariable> typeParams) {
      ArrayList<TypeDecl> typeArguments = new ArrayList<TypeDecl>();
      Collection<TypeDecl> arguments = computeConstraints(
          resultType,
          params,
          args,
          typeParams);
      if (arguments.isEmpty()) {
        return typeArguments;
      }
      int i = 0;
      for (Iterator<TypeDecl> iter = arguments.iterator(); iter.hasNext(); i++) {
        TypeDecl typeDecl = iter.next();
        if (typeDecl == null) {
          TypeVariable v = typeParams.getChild(i);
          if (v.getNumTypeBound() == 0) {
            typeDecl = typeObject();
          } else if (v.getNumTypeBound() == 1) {
            typeDecl = v.getTypeBound(0).type();
          } else {
            typeDecl = v.lubType();
          }
        }
        typeArguments.add(typeDecl);
      }
      return typeArguments;
    }
  /**
   * @attribute syn
   * @aspect PreciseRethrow
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\PreciseRethrow.jrag:33
   */
  @ASTNodeAnnotation.Attribute
  public Collection<TypeDecl> throwTypes() {
    {
        Collection<TypeDecl> tts = new LinkedList<TypeDecl>();
        tts.add(type());
        return tts;
      }
  }
  @ASTNodeAnnotation.Attribute
  public boolean modifiedInScope(Variable var) {
    boolean modifiedInScope_Variable_value = false;

    return modifiedInScope_Variable_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isVariable(Variable var) {
    boolean isVariable_Variable_value = false;

    return isVariable_Variable_value;
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
    stmtCompatible_value = false;
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
  protected java.util.Map compatibleStrictContext_TypeDecl_values;
  /**
   * @apilevel internal
   */
  private void compatibleStrictContext_TypeDecl_reset() {
    compatibleStrictContext_TypeDecl_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean compatibleStrictContext(TypeDecl type) {
    Object _parameters = type;
    if (compatibleStrictContext_TypeDecl_values == null) compatibleStrictContext_TypeDecl_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (compatibleStrictContext_TypeDecl_values.containsKey(_parameters)) {
      return (Boolean) compatibleStrictContext_TypeDecl_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean compatibleStrictContext_TypeDecl_value = compatibleStrictContext_compute(type);
    if (isFinal && num == state().boundariesCrossed) {
      compatibleStrictContext_TypeDecl_values.put(_parameters, compatibleStrictContext_TypeDecl_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return compatibleStrictContext_TypeDecl_value;
  }
  /**
   * @apilevel internal
   */
  private boolean compatibleStrictContext_compute(TypeDecl type) {
      return type().instanceOf(type)
          || type().withinBounds(type, Parameterization.RAW); // Test subtype of type bounds.
    }
  /**
   * @apilevel internal
   */
  protected java.util.Map compatibleLooseContext_TypeDecl_values;
  /**
   * @apilevel internal
   */
  private void compatibleLooseContext_TypeDecl_reset() {
    compatibleLooseContext_TypeDecl_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean compatibleLooseContext(TypeDecl type) {
    Object _parameters = type;
    if (compatibleLooseContext_TypeDecl_values == null) compatibleLooseContext_TypeDecl_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (compatibleLooseContext_TypeDecl_values.containsKey(_parameters)) {
      return (Boolean) compatibleLooseContext_TypeDecl_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean compatibleLooseContext_TypeDecl_value = type().methodInvocationConversionTo(type)
          || type().boxed().withinBounds(type, Parameterization.RAW);
    if (isFinal && num == state().boundariesCrossed) {
      compatibleLooseContext_TypeDecl_values.put(_parameters, compatibleLooseContext_TypeDecl_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return compatibleLooseContext_TypeDecl_value;
  }
  /**
   * @apilevel internal
   */
  protected java.util.Map pertinentToApplicability_Expr_BodyDecl_int_values;
  /**
   * @apilevel internal
   */
  private void pertinentToApplicability_Expr_BodyDecl_int_reset() {
    pertinentToApplicability_Expr_BodyDecl_int_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean pertinentToApplicability(Expr access, BodyDecl decl, int argIndex) {
    java.util.List _parameters = new java.util.ArrayList(3);
    _parameters.add(access);
    _parameters.add(decl);
    _parameters.add(argIndex);
    if (pertinentToApplicability_Expr_BodyDecl_int_values == null) pertinentToApplicability_Expr_BodyDecl_int_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (pertinentToApplicability_Expr_BodyDecl_int_values.containsKey(_parameters)) {
      return (Boolean) pertinentToApplicability_Expr_BodyDecl_int_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean pertinentToApplicability_Expr_BodyDecl_int_value = true;
    if (isFinal && num == state().boundariesCrossed) {
      pertinentToApplicability_Expr_BodyDecl_int_values.put(_parameters, pertinentToApplicability_Expr_BodyDecl_int_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return pertinentToApplicability_Expr_BodyDecl_int_value;
  }
  /**
   * @apilevel internal
   */
  protected java.util.Map moreSpecificThan_TypeDecl_TypeDecl_values;
  /**
   * @apilevel internal
   */
  private void moreSpecificThan_TypeDecl_TypeDecl_reset() {
    moreSpecificThan_TypeDecl_TypeDecl_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean moreSpecificThan(TypeDecl type1, TypeDecl type2) {
    java.util.List _parameters = new java.util.ArrayList(2);
    _parameters.add(type1);
    _parameters.add(type2);
    if (moreSpecificThan_TypeDecl_TypeDecl_values == null) moreSpecificThan_TypeDecl_TypeDecl_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (moreSpecificThan_TypeDecl_TypeDecl_values.containsKey(_parameters)) {
      return (Boolean) moreSpecificThan_TypeDecl_TypeDecl_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean moreSpecificThan_TypeDecl_TypeDecl_value = type1.instanceOf(type2) || type1.withinBounds(type2, Parameterization.RAW);
    if (isFinal && num == state().boundariesCrossed) {
      moreSpecificThan_TypeDecl_TypeDecl_values.put(_parameters, moreSpecificThan_TypeDecl_TypeDecl_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return moreSpecificThan_TypeDecl_TypeDecl_value;
  }
  /**
   * @apilevel internal
   */
  protected java.util.Map potentiallyCompatible_TypeDecl_BodyDecl_values;
  /**
   * @apilevel internal
   */
  private void potentiallyCompatible_TypeDecl_BodyDecl_reset() {
    potentiallyCompatible_TypeDecl_BodyDecl_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean potentiallyCompatible(TypeDecl type, BodyDecl candidateDecl) {
    java.util.List _parameters = new java.util.ArrayList(2);
    _parameters.add(type);
    _parameters.add(candidateDecl);
    if (potentiallyCompatible_TypeDecl_BodyDecl_values == null) potentiallyCompatible_TypeDecl_BodyDecl_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (potentiallyCompatible_TypeDecl_BodyDecl_values.containsKey(_parameters)) {
      return (Boolean) potentiallyCompatible_TypeDecl_BodyDecl_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean potentiallyCompatible_TypeDecl_BodyDecl_value = true;
    if (isFinal && num == state().boundariesCrossed) {
      potentiallyCompatible_TypeDecl_BodyDecl_values.put(_parameters, potentiallyCompatible_TypeDecl_BodyDecl_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return potentiallyCompatible_TypeDecl_BodyDecl_value;
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
    isBooleanExpression_value = !isPolyExpression() && type().isBoolean();
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
    isNumericExpression_value = !isPolyExpression() && type().isNumericType();
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
    isPolyExpression_value = false;
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
  protected java.util.Map assignConversionTo_TypeDecl_values;
  /**
   * @apilevel internal
   */
  private void assignConversionTo_TypeDecl_reset() {
    assignConversionTo_TypeDecl_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean assignConversionTo(TypeDecl type) {
    Object _parameters = type;
    if (assignConversionTo_TypeDecl_values == null) assignConversionTo_TypeDecl_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (assignConversionTo_TypeDecl_values.containsKey(_parameters)) {
      return (Boolean) assignConversionTo_TypeDecl_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean assignConversionTo_TypeDecl_value = type().assignConversionTo(type, this);
    if (isFinal && num == state().boundariesCrossed) {
      assignConversionTo_TypeDecl_values.put(_parameters, assignConversionTo_TypeDecl_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return assignConversionTo_TypeDecl_value;
  }
  /**
   * @attribute inh
   * @aspect DefiniteAssignment
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:37
   */
  @ASTNodeAnnotation.Attribute
  public boolean isDest() {
    boolean isDest_value = getParent().Define_isDest(this, null);

    return isDest_value;
  }
  /**
   * @attribute inh
   * @aspect DefiniteAssignment
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:47
   */
  @ASTNodeAnnotation.Attribute
  public boolean isSource() {
    boolean isSource_value = getParent().Define_isSource(this, null);

    return isSource_value;
  }
  /**
   * @attribute inh
   * @aspect DefiniteAssignment
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:71
   */
  @ASTNodeAnnotation.Attribute
  public boolean isIncOrDec() {
    boolean isIncOrDec_value = getParent().Define_isIncOrDec(this, null);

    return isIncOrDec_value;
  }
  /**
   * @attribute inh
   * @aspect DA
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:258
   */
  @ASTNodeAnnotation.Attribute
  public boolean isDAbefore(Variable v) {
    boolean isDAbefore_Variable_value = getParent().Define_isDAbefore(this, null, v);

    return isDAbefore_Variable_value;
  }
  /**
   * @attribute inh
   * @aspect DU
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:782
   */
  @ASTNodeAnnotation.Attribute
  public boolean isDUbefore(Variable v) {
    boolean isDUbefore_Variable_value = getParent().Define_isDUbefore(this, null, v);

    return isDUbefore_Variable_value;
  }
  /**
   * @attribute inh
   * @aspect LookupMethod
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupMethod.jrag:45
   */
  @ASTNodeAnnotation.Attribute
  public Collection lookupMethod(String name) {
    Collection lookupMethod_String_value = getParent().Define_lookupMethod(this, null, name);

    return lookupMethod_String_value;
  }
  /**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:74
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl typeBoolean() {
    TypeDecl typeBoolean_value = getParent().Define_typeBoolean(this, null);

    return typeBoolean_value;
  }
  /**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:75
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl typeByte() {
    TypeDecl typeByte_value = getParent().Define_typeByte(this, null);

    return typeByte_value;
  }
  /**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:76
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl typeShort() {
    TypeDecl typeShort_value = getParent().Define_typeShort(this, null);

    return typeShort_value;
  }
  /**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:77
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl typeChar() {
    TypeDecl typeChar_value = getParent().Define_typeChar(this, null);

    return typeChar_value;
  }
  /**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:78
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl typeInt() {
    TypeDecl typeInt_value = getParent().Define_typeInt(this, null);

    return typeInt_value;
  }
  /**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:79
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl typeLong() {
    TypeDecl typeLong_value = getParent().Define_typeLong(this, null);

    return typeLong_value;
  }
  /**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:80
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl typeFloat() {
    TypeDecl typeFloat_value = getParent().Define_typeFloat(this, null);

    return typeFloat_value;
  }
  /**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:81
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl typeDouble() {
    TypeDecl typeDouble_value = getParent().Define_typeDouble(this, null);

    return typeDouble_value;
  }
  /**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:82
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl typeString() {
    TypeDecl typeString_value = getParent().Define_typeString(this, null);

    return typeString_value;
  }
  /**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:83
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl typeVoid() {
    TypeDecl typeVoid_value = getParent().Define_typeVoid(this, null);

    return typeVoid_value;
  }
  /**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:84
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl typeNull() {
    TypeDecl typeNull_value = getParent().Define_typeNull(this, null);

    return typeNull_value;
  }
  /**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:97
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl unknownType() {
    TypeDecl unknownType_value = getParent().Define_unknownType(this, null);

    return unknownType_value;
  }
  /**
   * @attribute inh
   * @aspect LookupFullyQualifiedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:115
   */
  @ASTNodeAnnotation.Attribute
  public boolean hasPackage(String packageName) {
    boolean hasPackage_String_value = getParent().Define_hasPackage(this, null, packageName);

    return hasPackage_String_value;
  }
  /**
   * @attribute inh
   * @aspect LookupFullyQualifiedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:129
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl lookupType(String packageName, String typeName) {
    TypeDecl lookupType_String_String_value = getParent().Define_lookupType(this, null, packageName, typeName);

    return lookupType_String_String_value;
  }
  /**
   * @attribute inh
   * @aspect TypeScopePropagation
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:341
   */
  @ASTNodeAnnotation.Attribute
  public SimpleSet lookupType(String name) {
    SimpleSet lookupType_String_value = getParent().Define_lookupType(this, null, name);

    return lookupType_String_value;
  }
  /**
   * @attribute inh
   * @aspect VariableScope
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupVariable.jrag:40
   */
  @ASTNodeAnnotation.Attribute
  public SimpleSet lookupVariable(String name) {
    SimpleSet lookupVariable_String_value = getParent().Define_lookupVariable(this, null, name);

    return lookupVariable_String_value;
  }
  /**
   * @attribute inh
   * @aspect SyntacticClassification
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\SyntacticClassification.jrag:36
   */
  @ASTNodeAnnotation.Attribute
  public NameType nameType() {
    ASTNode$State state = state();
    if (nameType_computed) {
      return nameType_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    nameType_value = getParent().Define_nameType(this, null);
    if (isFinal && num == state().boundariesCrossed) {
      nameType_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return nameType_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean nameType_computed = false;
  /**
   * @apilevel internal
   */
  protected NameType nameType_value;
  /**
   * @apilevel internal
   */
  private void nameType_reset() {
    nameType_computed = false;
    nameType_value = null;
  }
  /**
   * @attribute inh
   * @aspect NestedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeAnalysis.jrag:560
   */
  @ASTNodeAnnotation.Attribute
  public BodyDecl enclosingBodyDecl() {
    BodyDecl enclosingBodyDecl_value = getParent().Define_enclosingBodyDecl(this, null);

    return enclosingBodyDecl_value;
  }
  /**
   * @attribute inh
   * @aspect NestedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeAnalysis.jrag:622
   */
  @ASTNodeAnnotation.Attribute
  public String hostPackage() {
    String hostPackage_value = getParent().Define_hostPackage(this, null);

    return hostPackage_value;
  }
  /**
   * @attribute inh
   * @aspect NestedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeAnalysis.jrag:638
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl hostType() {
    TypeDecl hostType_value = getParent().Define_hostType(this, null);

    return hostType_value;
  }
  /**
   * @attribute inh
   * @aspect TypeHierarchyCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:32
   */
  @ASTNodeAnnotation.Attribute
  public String methodHost() {
    String methodHost_value = getParent().Define_methodHost(this, null);

    return methodHost_value;
  }
  /**
   * @attribute inh
   * @aspect TypeHierarchyCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:182
   */
  @ASTNodeAnnotation.Attribute
  public boolean inStaticContext() {
    boolean inStaticContext_value = getParent().Define_inStaticContext(this, null);

    return inStaticContext_value;
  }
  /**
   * @attribute inh
   * @aspect GenericMethodsInference
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\GenericMethodsInference.jrag:58
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl assignConvertedType() {
    TypeDecl assignConvertedType_value = getParent().Define_assignConvertedType(this, null);

    return assignConvertedType_value;
  }
  /**
   * @attribute inh
   * @aspect GenericMethodsInference
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\GenericMethodsInference.jrag:68
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl typeObject() {
    TypeDecl typeObject_value = getParent().Define_typeObject(this, null);

    return typeObject_value;
  }
  /**
   * @attribute inh
   * @aspect GenericsTypeAnalysis
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:338
   */
  @ASTNodeAnnotation.Attribute
  public boolean inExtendsOrImplements() {
    boolean inExtendsOrImplements_value = getParent().Define_inExtendsOrImplements(this, null);

    return inExtendsOrImplements_value;
  }
  /**
   * @attribute inh
   * @aspect TargetType
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:30
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl targetType() {
    ASTNode$State state = state();
    if (targetType_computed) {
      return targetType_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    targetType_value = getParent().Define_targetType(this, null);
    if (isFinal && num == state().boundariesCrossed) {
      targetType_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return targetType_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean targetType_computed = false;
  /**
   * @apilevel internal
   */
  protected TypeDecl targetType_value;
  /**
   * @apilevel internal
   */
  private void targetType_reset() {
    targetType_computed = false;
    targetType_value = null;
  }
  /**
   * @attribute inh
   * @aspect Contexts
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:196
   */
  @ASTNodeAnnotation.Attribute
  public boolean assignmentContext() {
    ASTNode$State state = state();
    if (assignmentContext_computed) {
      return assignmentContext_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    assignmentContext_value = getParent().Define_assignmentContext(this, null);
    if (isFinal && num == state().boundariesCrossed) {
      assignmentContext_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return assignmentContext_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean assignmentContext_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean assignmentContext_value;
  /**
   * @apilevel internal
   */
  private void assignmentContext_reset() {
    assignmentContext_computed = false;
  }
  /**
   * @attribute inh
   * @aspect Contexts
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:197
   */
  @ASTNodeAnnotation.Attribute
  public boolean invocationContext() {
    ASTNode$State state = state();
    if (invocationContext_computed) {
      return invocationContext_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    invocationContext_value = getParent().Define_invocationContext(this, null);
    if (isFinal && num == state().boundariesCrossed) {
      invocationContext_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return invocationContext_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean invocationContext_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean invocationContext_value;
  /**
   * @apilevel internal
   */
  private void invocationContext_reset() {
    invocationContext_computed = false;
  }
  /**
   * @attribute inh
   * @aspect Contexts
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:198
   */
  @ASTNodeAnnotation.Attribute
  public boolean castContext() {
    ASTNode$State state = state();
    if (castContext_computed) {
      return castContext_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    castContext_value = getParent().Define_castContext(this, null);
    if (isFinal && num == state().boundariesCrossed) {
      castContext_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return castContext_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean castContext_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean castContext_value;
  /**
   * @apilevel internal
   */
  private void castContext_reset() {
    castContext_computed = false;
  }
  /**
   * @attribute inh
   * @aspect Contexts
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:199
   */
  @ASTNodeAnnotation.Attribute
  public boolean stringContext() {
    ASTNode$State state = state();
    if (stringContext_computed) {
      return stringContext_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    stringContext_value = getParent().Define_stringContext(this, null);
    if (isFinal && num == state().boundariesCrossed) {
      stringContext_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return stringContext_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean stringContext_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean stringContext_value;
  /**
   * @apilevel internal
   */
  private void stringContext_reset() {
    stringContext_computed = false;
  }
  /**
   * @attribute inh
   * @aspect Contexts
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:200
   */
  @ASTNodeAnnotation.Attribute
  public boolean numericContext() {
    ASTNode$State state = state();
    if (numericContext_computed) {
      return numericContext_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    numericContext_value = getParent().Define_numericContext(this, null);
    if (isFinal && num == state().boundariesCrossed) {
      numericContext_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return numericContext_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean numericContext_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean numericContext_value;
  /**
   * @apilevel internal
   */
  private void numericContext_reset() {
    numericContext_computed = false;
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
