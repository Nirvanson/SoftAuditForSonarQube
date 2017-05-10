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
 * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\grammar\\ConstructorReference.ast:3
 * @production ClassReference : {@link ConstructorReference} ::= <span class="component">TypeArgument:{@link Access}*</span>;

 */
public class ClassReference extends ConstructorReference implements Cloneable {
  /**
   * @aspect Java8PrettyPrint
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\PrettyPrint.jadd:42
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print(getTypeAccess());
    out.print("::");
    if (hasTypeArgument()) {
      out.print("<");
      out.join(getTypeArguments(), new PrettyPrinter.Joiner() {
        @Override
        public void printSeparator(PrettyPrinter out) {
          out.print(", ");
        }
      });
      out.print(">");
    }
    out.print("new");
  }
  /**
   * @declaredat ASTNode:1
   */
  public ClassReference() {
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
  public ClassReference(Access p0, List<Access> p1) {
    setChild(p0, 0);
    setChild(p1, 1);
  }
  /** @apilevel low-level 
   * @declaredat ASTNode:19
   */
  protected int numChildren() {
    return 2;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:25
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:29
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    targetConstructor_FunctionDescriptor_reset();
    syntheticInstanceExpr_FunctionDescriptor_reset();
    congruentTo_FunctionDescriptor_reset();
    potentiallyApplicableConstructors_FunctionDescriptor_reset();
    exactCompileTimeDeclaration_reset();
    isExact_reset();
    potentiallyCompatible_TypeDecl_BodyDecl_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:40
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:44
   */
  public ClassReference clone() throws CloneNotSupportedException {
    ClassReference node = (ClassReference) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:49
   */
  public ClassReference copy() {
    try {
      ClassReference node = (ClassReference) clone();
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
   * @declaredat ASTNode:68
   */
  @Deprecated
  public ClassReference fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:78
   */
  public ClassReference treeCopyNoTransform() {
    ClassReference tree = (ClassReference) copy();
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
   * @declaredat ASTNode:98
   */
  public ClassReference treeCopy() {
    ClassReference tree = (ClassReference) copy();
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
   * @declaredat ASTNode:112
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
  /** @apilevel low-level 
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
   * @return the element at index {@code i} in the TypeArgument list without
   * triggering rewrites.
   */
  public Access getTypeArgumentNoTransform(int i) {
    return (Access) getTypeArgumentListNoTransform().getChildNoTransform(i);
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
  /** @apilevel internal */
  private void targetConstructor_FunctionDescriptor_reset() {
    targetConstructor_FunctionDescriptor_computed = new java.util.HashMap(4);
    targetConstructor_FunctionDescriptor_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map targetConstructor_FunctionDescriptor_values;
  /** @apilevel internal */
  protected java.util.Map targetConstructor_FunctionDescriptor_computed;
  /**
   * @attribute syn
   * @aspect ConstructorReference
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\ConstructorReference.jrag:31
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstructorReference", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\ConstructorReference.jrag:31")
  public ConstructorDecl targetConstructor(FunctionDescriptor f) {
    Object _parameters = f;
    if (targetConstructor_FunctionDescriptor_computed == null) targetConstructor_FunctionDescriptor_computed = new java.util.HashMap(4);
    if (targetConstructor_FunctionDescriptor_values == null) targetConstructor_FunctionDescriptor_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (targetConstructor_FunctionDescriptor_values.containsKey(_parameters) && targetConstructor_FunctionDescriptor_computed != null
        && targetConstructor_FunctionDescriptor_computed.containsKey(_parameters)
        && (targetConstructor_FunctionDescriptor_computed.get(_parameters) == ASTNode$State.NON_CYCLE || targetConstructor_FunctionDescriptor_computed.get(_parameters) == state().cycle())) {
      return (ConstructorDecl) targetConstructor_FunctionDescriptor_values.get(_parameters);
    }
    ConstructorDecl targetConstructor_FunctionDescriptor_value = syntheticInstanceExpr(f).decl();
    if (state().inCircle()) {
      targetConstructor_FunctionDescriptor_values.put(_parameters, targetConstructor_FunctionDescriptor_value);
      targetConstructor_FunctionDescriptor_computed.put(_parameters, state().cycle());
    
    } else {
      targetConstructor_FunctionDescriptor_values.put(_parameters, targetConstructor_FunctionDescriptor_value);
      targetConstructor_FunctionDescriptor_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return targetConstructor_FunctionDescriptor_value;
  }
  /** @apilevel internal */
  private void syntheticInstanceExpr_FunctionDescriptor_reset() {
    syntheticInstanceExpr_FunctionDescriptor_values = null;
    syntheticInstanceExpr_FunctionDescriptor_list = null;
  }
  /** @apilevel internal */
  protected List syntheticInstanceExpr_FunctionDescriptor_list;
  /** @apilevel internal */
  protected java.util.Map syntheticInstanceExpr_FunctionDescriptor_values;

  /**
   * @attribute syn
   * @aspect ConstructorReference
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\ConstructorReference.jrag:34
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="ConstructorReference", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\ConstructorReference.jrag:34")
  public ClassInstanceExpr syntheticInstanceExpr(FunctionDescriptor f) {
    Object _parameters = f;
    if (syntheticInstanceExpr_FunctionDescriptor_values == null) syntheticInstanceExpr_FunctionDescriptor_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (syntheticInstanceExpr_FunctionDescriptor_values.containsKey(_parameters)) {
      return (ClassInstanceExpr) syntheticInstanceExpr_FunctionDescriptor_values.get(_parameters);
    }
    state().enterLazyAttribute();
    ClassInstanceExpr syntheticInstanceExpr_FunctionDescriptor_value = syntheticInstanceExpr_compute(f);
    if (syntheticInstanceExpr_FunctionDescriptor_list == null) {
      syntheticInstanceExpr_FunctionDescriptor_list = new List();
      syntheticInstanceExpr_FunctionDescriptor_list.setParent(this);
    }
    syntheticInstanceExpr_FunctionDescriptor_list.add(syntheticInstanceExpr_FunctionDescriptor_value);
    if (syntheticInstanceExpr_FunctionDescriptor_value != null) {
      syntheticInstanceExpr_FunctionDescriptor_value = (ClassInstanceExpr) syntheticInstanceExpr_FunctionDescriptor_list.getChild(syntheticInstanceExpr_FunctionDescriptor_list.numChildren - 1);
    }
    syntheticInstanceExpr_FunctionDescriptor_values.put(_parameters, syntheticInstanceExpr_FunctionDescriptor_value);
    state().leaveLazyAttribute();
    return syntheticInstanceExpr_FunctionDescriptor_value;
  }
  /** @apilevel internal */
  private ClassInstanceExpr syntheticInstanceExpr_compute(FunctionDescriptor f) {
      List<Expr> arguments = new List<Expr>();
      for (int i = 0; i < f.method.getNumParameter(); i++) {
        TypeDecl argumentType = f.method.getParameter(i).type();
        arguments.add(new SyntheticTypeAccess(argumentType));
      }
  
      ClassInstanceExpr instanceExpr = null;
      if (hasTypeArgument()) {
        instanceExpr = new ParConstructorReferenceAccess(
            (Access) getTypeAccess().treeCopyNoTransform(), arguments,
            new Opt(), (List<Access>) getTypeArgumentList().treeCopyNoTransform(), f);
      } else {
        // Must check for raw reference type, and in that case infer using diamond (JLS 15.13.1)
        boolean checkDiamond = true;
        if (getTypeAccess().type().hostType() != null && !getTypeAccess().type().isStatic()
            && getTypeAccess().type().hostType().isRawType()) {
          checkDiamond = false;
        }
        if (getTypeAccess().type().isRawType() && checkDiamond) {
          DiamondAccess diamond = new DiamondAccess((Access) getTypeAccess().treeCopyNoTransform());
          instanceExpr = new ConstructorReferenceAccess(diamond, arguments, f);
        } else {
          instanceExpr = new ConstructorReferenceAccess(
              (Access) getTypeAccess().treeCopyNoTransform(), arguments, f);
        }
      }
      return instanceExpr;
    }
  /** @apilevel internal */
  private void congruentTo_FunctionDescriptor_reset() {
    congruentTo_FunctionDescriptor_computed = new java.util.HashMap(4);
    congruentTo_FunctionDescriptor_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map congruentTo_FunctionDescriptor_values;
  /** @apilevel internal */
  protected java.util.Map congruentTo_FunctionDescriptor_computed;
  /**
   * @attribute syn
   * @aspect ConstructorReference
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\ConstructorReference.jrag:68
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstructorReference", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\ConstructorReference.jrag:68")
  public boolean congruentTo(FunctionDescriptor f) {
    Object _parameters = f;
    if (congruentTo_FunctionDescriptor_computed == null) congruentTo_FunctionDescriptor_computed = new java.util.HashMap(4);
    if (congruentTo_FunctionDescriptor_values == null) congruentTo_FunctionDescriptor_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (congruentTo_FunctionDescriptor_values.containsKey(_parameters) && congruentTo_FunctionDescriptor_computed != null
        && congruentTo_FunctionDescriptor_computed.containsKey(_parameters)
        && (congruentTo_FunctionDescriptor_computed.get(_parameters) == ASTNode$State.NON_CYCLE || congruentTo_FunctionDescriptor_computed.get(_parameters) == state().cycle())) {
      return (Boolean) congruentTo_FunctionDescriptor_values.get(_parameters);
    }
    boolean congruentTo_FunctionDescriptor_value = congruentTo_compute(f);
    if (state().inCircle()) {
      congruentTo_FunctionDescriptor_values.put(_parameters, congruentTo_FunctionDescriptor_value);
      congruentTo_FunctionDescriptor_computed.put(_parameters, state().cycle());
    
    } else {
      congruentTo_FunctionDescriptor_values.put(_parameters, congruentTo_FunctionDescriptor_value);
      congruentTo_FunctionDescriptor_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return congruentTo_FunctionDescriptor_value;
  }
  /** @apilevel internal */
  private boolean congruentTo_compute(FunctionDescriptor f) {
      ConstructorDecl decl = targetConstructor(f);
      if (unknownConstructor() == decl) {
        return false;
      }
      if (!f.method.type().isVoid()) {
        TypeDecl returnType = syntheticInstanceExpr(f).type();
        if (!returnType.assignConversionTo(f.method.type(), null)) {
          return false;
        }
      }
      return true;
    }
  /** @apilevel internal */
  private void potentiallyApplicableConstructors_FunctionDescriptor_reset() {
    potentiallyApplicableConstructors_FunctionDescriptor_computed = new java.util.HashMap(4);
    potentiallyApplicableConstructors_FunctionDescriptor_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map potentiallyApplicableConstructors_FunctionDescriptor_values;
  /** @apilevel internal */
  protected java.util.Map potentiallyApplicableConstructors_FunctionDescriptor_computed;
  /**
   * @attribute syn
   * @aspect ConstructorReference
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\ConstructorReference.jrag:100
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstructorReference", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\ConstructorReference.jrag:100")
  public ArrayList<ConstructorDecl> potentiallyApplicableConstructors(FunctionDescriptor f) {
    Object _parameters = f;
    if (potentiallyApplicableConstructors_FunctionDescriptor_computed == null) potentiallyApplicableConstructors_FunctionDescriptor_computed = new java.util.HashMap(4);
    if (potentiallyApplicableConstructors_FunctionDescriptor_values == null) potentiallyApplicableConstructors_FunctionDescriptor_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (potentiallyApplicableConstructors_FunctionDescriptor_values.containsKey(_parameters) && potentiallyApplicableConstructors_FunctionDescriptor_computed != null
        && potentiallyApplicableConstructors_FunctionDescriptor_computed.containsKey(_parameters)
        && (potentiallyApplicableConstructors_FunctionDescriptor_computed.get(_parameters) == ASTNode$State.NON_CYCLE || potentiallyApplicableConstructors_FunctionDescriptor_computed.get(_parameters) == state().cycle())) {
      return (ArrayList<ConstructorDecl>) potentiallyApplicableConstructors_FunctionDescriptor_values.get(_parameters);
    }
    ArrayList<ConstructorDecl> potentiallyApplicableConstructors_FunctionDescriptor_value = potentiallyApplicableConstructors_compute(f);
    if (state().inCircle()) {
      potentiallyApplicableConstructors_FunctionDescriptor_values.put(_parameters, potentiallyApplicableConstructors_FunctionDescriptor_value);
      potentiallyApplicableConstructors_FunctionDescriptor_computed.put(_parameters, state().cycle());
    
    } else {
      potentiallyApplicableConstructors_FunctionDescriptor_values.put(_parameters, potentiallyApplicableConstructors_FunctionDescriptor_value);
      potentiallyApplicableConstructors_FunctionDescriptor_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return potentiallyApplicableConstructors_FunctionDescriptor_value;
  }
  /** @apilevel internal */
  private ArrayList<ConstructorDecl> potentiallyApplicableConstructors_compute(FunctionDescriptor f) {
      TypeDecl classType = syntheticInstanceExpr(f).type();
      Collection<ConstructorDecl> col = classType.constructors();
      ArrayList<ConstructorDecl> applicable = new ArrayList<ConstructorDecl>();
      for (ConstructorDecl decl : col) {
        if (!decl.accessibleFrom(hostType())) {
          continue;
        }
        if (!(decl.arity() == f.method.arity())) {
          continue;
        }
        if (hasTypeArgument()) {
          if (!decl.isGeneric()) {
            continue;
          }
          GenericConstructorDecl genDecl = decl.genericDecl();
          if (!(getNumTypeArgument() == genDecl.getNumTypeParameter())) {
            continue;
          }
        }
        applicable.add(decl);
      }
      return applicable;
    }
  /** @apilevel internal */
  private void exactCompileTimeDeclaration_reset() {
    exactCompileTimeDeclaration_computed = null;
    exactCompileTimeDeclaration_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle exactCompileTimeDeclaration_computed = null;

  /** @apilevel internal */
  protected ConstructorDecl exactCompileTimeDeclaration_value;

  /**
   * @attribute syn
   * @aspect ConstructorReference
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\ConstructorReference.jrag:129
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstructorReference", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\ConstructorReference.jrag:129")
  public ConstructorDecl exactCompileTimeDeclaration() {
    ASTNode$State state = state();
    if (exactCompileTimeDeclaration_computed == ASTNode$State.NON_CYCLE || exactCompileTimeDeclaration_computed == state().cycle()) {
      return exactCompileTimeDeclaration_value;
    }
    exactCompileTimeDeclaration_value = exactCompileTimeDeclaration_compute();
    if (state().inCircle()) {
      exactCompileTimeDeclaration_computed = state().cycle();
    
    } else {
      exactCompileTimeDeclaration_computed = ASTNode$State.NON_CYCLE;
    
    }
    return exactCompileTimeDeclaration_value;
  }
  /** @apilevel internal */
  private ConstructorDecl exactCompileTimeDeclaration_compute() {
      if (getTypeAccess().type().isRawType()) {
        if (getTypeAccess().type().hostType() == null || getTypeAccess().type().isStatic()
            || getTypeAccess().type().hostType().isRawType()) {
          return unknownConstructor();
        }
      }
      TypeDecl classType = getTypeAccess().type();
      Collection<ConstructorDecl> col = classType.constructors();
      ArrayList<ConstructorDecl> applicable = new ArrayList<ConstructorDecl>();
      int foundCompatible = 0;
      ConstructorDecl latestDecl = null;
  
      for (ConstructorDecl decl : col) {
        if (decl.accessibleFrom(hostType())) {
          foundCompatible++;
          latestDecl = decl;
        }
      }
      if (foundCompatible != 1) {
        return unknownConstructor();
      }
      if (latestDecl.isVariableArity()) {
        return unknownConstructor();
      }
      if (latestDecl.isGeneric()) {
        GenericConstructorDecl genericDecl = latestDecl.genericDecl();
        if (getNumTypeArgument() == genericDecl.getNumTypeParameter()) {
          return latestDecl;
        } else {
          return unknownConstructor();
        }
      }
      return latestDecl;
    }
  /** @apilevel internal */
  private void isExact_reset() {
    isExact_computed = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle isExact_computed = null;

  /** @apilevel internal */
  protected boolean isExact_value;

  /**
   * @attribute syn
   * @aspect ConstructorReference
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\ConstructorReference.jrag:127
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstructorReference", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\ConstructorReference.jrag:127")
  public boolean isExact() {
    ASTNode$State state = state();
    if (isExact_computed == ASTNode$State.NON_CYCLE || isExact_computed == state().cycle()) {
      return isExact_value;
    }
    isExact_value = exactCompileTimeDeclaration() != unknownConstructor();
    if (state().inCircle()) {
      isExact_computed = state().cycle();
    
    } else {
      isExact_computed = ASTNode$State.NON_CYCLE;
    
    }
    return isExact_value;
  }
  /** @apilevel internal */
  private void potentiallyCompatible_TypeDecl_BodyDecl_reset() {
    potentiallyCompatible_TypeDecl_BodyDecl_computed = new java.util.HashMap(4);
    potentiallyCompatible_TypeDecl_BodyDecl_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map potentiallyCompatible_TypeDecl_BodyDecl_values;
  /** @apilevel internal */
  protected java.util.Map potentiallyCompatible_TypeDecl_BodyDecl_computed;
  /**
   * @attribute syn
   * @aspect MethodSignature18
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\MethodSignature.jrag:465
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MethodSignature18", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\MethodSignature.jrag:465")
  public boolean potentiallyCompatible(TypeDecl type, BodyDecl candidateDecl) {
    java.util.List _parameters = new java.util.ArrayList(2);
    _parameters.add(type);
    _parameters.add(candidateDecl);
    if (potentiallyCompatible_TypeDecl_BodyDecl_computed == null) potentiallyCompatible_TypeDecl_BodyDecl_computed = new java.util.HashMap(4);
    if (potentiallyCompatible_TypeDecl_BodyDecl_values == null) potentiallyCompatible_TypeDecl_BodyDecl_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (potentiallyCompatible_TypeDecl_BodyDecl_values.containsKey(_parameters) && potentiallyCompatible_TypeDecl_BodyDecl_computed != null
        && potentiallyCompatible_TypeDecl_BodyDecl_computed.containsKey(_parameters)
        && (potentiallyCompatible_TypeDecl_BodyDecl_computed.get(_parameters) == ASTNode$State.NON_CYCLE || potentiallyCompatible_TypeDecl_BodyDecl_computed.get(_parameters) == state().cycle())) {
      return (Boolean) potentiallyCompatible_TypeDecl_BodyDecl_values.get(_parameters);
    }
    boolean potentiallyCompatible_TypeDecl_BodyDecl_value = potentiallyCompatible_compute(type, candidateDecl);
    if (state().inCircle()) {
      potentiallyCompatible_TypeDecl_BodyDecl_values.put(_parameters, potentiallyCompatible_TypeDecl_BodyDecl_value);
      potentiallyCompatible_TypeDecl_BodyDecl_computed.put(_parameters, state().cycle());
    
    } else {
      potentiallyCompatible_TypeDecl_BodyDecl_values.put(_parameters, potentiallyCompatible_TypeDecl_BodyDecl_value);
      potentiallyCompatible_TypeDecl_BodyDecl_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return potentiallyCompatible_TypeDecl_BodyDecl_value;
  }
  /** @apilevel internal */
  private boolean potentiallyCompatible_compute(TypeDecl type, BodyDecl candidateDecl) {
      if (super.potentiallyCompatible(type, candidateDecl) && type.isTypeVariable()) {
        return true;
      } else if (!super.potentiallyCompatible(type, candidateDecl)) {
        return false;
      }
  
      InterfaceDecl iDecl = (InterfaceDecl) type;
      FunctionDescriptor f = iDecl.functionDescriptor();
  
      boolean foundMethod = false;
      for (ConstructorDecl decl : potentiallyApplicableConstructors(f)) {
        if (f.method.arity() == decl.arity()) {
          foundMethod = true;
          break;
        }
      }
      return foundMethod;
    }
  /**
   * @attribute syn
   * @aspect Java8NameCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\NameCheck.jrag:522
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Java8NameCheck", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\NameCheck.jrag:522")
  public Collection<Problem> nameProblems() {
    {
        for (int i = 0; i < getNumTypeArgument(); i++) {
          if (getTypeArgument(i) instanceof AbstractWildcard) {
            return Collections.singletonList(
                error("Wildcard not allowed in constructor reference type argument lists"));
          }
        }
        return Collections.emptyList();
      }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\SyntacticClassification.jrag:36
   * @apilevel internal
   */
  public NameType Define_nameType(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getTypeArgumentListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\ConstructorReference.jrag:65
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return NameType.TYPE_NAME;
    }
    else {
      return super.Define_nameType(_callerNode, _childNode);
    }
  }
  protected boolean canDefine_nameType(ASTNode _callerNode, ASTNode _childNode) {
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
    // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\NameCheck.jrag:520
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
  protected void contributeTo_CompilationUnit_problems(LinkedList<Problem> collection) {
    super.contributeTo_CompilationUnit_problems(collection);
    for (Problem value : nameProblems()) {
      collection.add(value);
    }
  }
}
