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
 * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\grammar\\Java.ast:202
 * @production ConstCase : {@link Case} ::= <span class="component">Value:{@link Expr}</span>;

 */
public class ConstCase extends Case implements Cloneable {
  /**
   * @aspect Java4PrettyPrint
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\PrettyPrint.jadd:253
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print("case ");
    out.print(getValue());
    out.print(":");
  }
  /**
   * @declaredat ASTNode:1
   */
  public ConstCase() {
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
  public ConstCase(Expr p0) {
    setChild(p0, 0);
  }
  /** @apilevel low-level 
   * @declaredat ASTNode:17
   */
  protected int numChildren() {
    return 1;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:23
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:27
   */
  public void flushAttrCache() {
    super.flushAttrCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:31
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:35
   */
  public ConstCase clone() throws CloneNotSupportedException {
    ConstCase node = (ConstCase) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:40
   */
  public ConstCase copy() {
    try {
      ConstCase node = (ConstCase) clone();
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
   * @declaredat ASTNode:59
   */
  @Deprecated
  public ConstCase fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:69
   */
  public ConstCase treeCopyNoTransform() {
    ConstCase tree = (ConstCase) copy();
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
   * @declaredat ASTNode:89
   */
  public ConstCase treeCopy() {
    ConstCase tree = (ConstCase) copy();
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
   * @declaredat ASTNode:103
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node);    
  }
  /**
   * Replaces the Value child.
   * @param node The new node to replace the Value child.
   * @apilevel high-level
   */
  public void setValue(Expr node) {
    setChild(node, 0);
  }
  /**
   * Retrieves the Value child.
   * @return The current node used as the Value child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Value")
  public Expr getValue() {
    return (Expr) getChild(0);
  }
  /**
   * Retrieves the Value child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Value child.
   * @apilevel low-level
   */
  public Expr getValueNoTransform() {
    return (Expr) getChildNoTransform(0);
  }
  /**
   * @aspect NameCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\NameCheck.jrag:588
   */
  private boolean refined_NameCheck_ConstCase_constValue_Case(Case c)
{
    if (!(c instanceof ConstCase) || !getValue().isConstant()) {
      return false;
    }
    if (!getValue().type().assignableToInt()
        || !((ConstCase) c).getValue().type().assignableToInt()) {
      return false;
    }
    return getValue().constant().intValue() == ((ConstCase) c).getValue().constant().intValue();
  }
  /**
   * @aspect Enums
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Enums.jrag:671
   */
  private boolean refined_Enums_ConstCase_constValue_Case(Case c)
{
    if (switchType().isEnumDecl()) {
      if (!(c instanceof ConstCase) || !getValue().isConstant()) {
        return false;
      }
      return getValue().varDecl() == ((ConstCase) c).getValue().varDecl();
    } else {
      return refined_NameCheck_ConstCase_constValue_Case(c);
    }
  }
  /**
   * @attribute syn
   * @aspect NameCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\NameCheck.jrag:555
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="NameCheck", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\NameCheck.jrag:555")
  public Collection<Problem> nameProblems() {
    {
        if (getValue().isConstant() && bind(this) != this) {
          return Collections.singletonList(errorf(
              "constant expression %s is multiply declared in two case statements",
              getValue().prettyPrint()));
        }
        return Collections.emptyList();
      }
  }
  /**
   * @attribute syn
   * @aspect NameCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\NameCheck.jrag:586
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="NameCheck", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\NameCheck.jrag:586")
  public boolean constValue(Case c) {
    {
        if (isDefaultCase() || c.isDefaultCase()) {
          return isDefaultCase() && c.isDefaultCase();
        }
    
        Expr myValue = getValue();
        Expr otherValue = ((ConstCase) c).getValue();
        TypeDecl myType = myValue.type();
        TypeDecl otherType = otherValue.type();
        if (myType.isString() || otherType.isString()) {
          if (!myType.isString() || !otherType.isString()) {
            return false;
          }
          if (!myValue.isConstant() || !otherValue.isConstant()) {
            return false;
          }
          return myValue.constant().stringValue().equals(otherValue.constant().stringValue());
        }
    
        return refined_Enums_ConstCase_constValue_Case(c);
      }
  }
  /**
   * @attribute syn
   * @aspect TypeCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeCheck.jrag:470
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeCheck", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeCheck.jrag:470")
  public Collection<Problem> typeProblems() {
    {
        Collection<Problem> problems = new LinkedList<Problem>();
        boolean isEnumConstant = getValue().isEnumConstant();
        TypeDecl switchType = switchType();
        TypeDecl type = getValue().type();
        if (switchType.isEnumDecl() && !isEnumConstant) {
          problems.add(error("Unqualified enumeration constant required"));
        }
        if (!type.assignConversionTo(switchType, getValue())) {
          problems.add(errorf("Case label has incompatible type %s, expected type compatible with %s",
              switchType.name(), type.name()));
        }
        if (!getValue().isConstant() && !getValue().type().isUnknown() && !isEnumConstant) {
          problems.add(error("Case label must have constant expression"));
        }
        return problems;
      }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\LookupVariable.jrag:30
   * @apilevel internal
   */
  public SimpleSet<Variable> Define_lookupVariable(ASTNode _callerNode, ASTNode _childNode, String name) {
    if (getValueNoTransform() != null && _callerNode == getValue()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Enums.jrag:643
      return switchType().isEnumDecl()
            ? switchType().memberFields(name)
            : lookupVariable(name);
    }
    else {
      return getParent().Define_lookupVariable(this, _callerNode, name);
    }
  }
  protected boolean canDefine_lookupVariable(ASTNode _callerNode, ASTNode _childNode, String name) {
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
    // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\NameCheck.jrag:553
    {
      java.util.Set<ASTNode> contributors = _map.get(_root);
      if (contributors == null) {
        contributors = new java.util.LinkedHashSet<ASTNode>();
        _map.put((ASTNode) _root, contributors);
      }
      contributors.add(this);
    }
    // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeCheck.jrag:468
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
  protected void collect_contributors_Program_extractedStatementTypes(Program _root, java.util.Map<ASTNode, java.util.Set<ASTNode>> _map) {
    // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:121
    {
      java.util.Set<ASTNode> contributors = _map.get(_root);
      if (contributors == null) {
        contributors = new java.util.LinkedHashSet<ASTNode>();
        _map.put((ASTNode) _root, contributors);
      }
      contributors.add(this);
    }
    super.collect_contributors_Program_extractedStatementTypes(_root, _map);
  }
  protected void contributeTo_CompilationUnit_problems(LinkedList<Problem> collection) {
    super.contributeTo_CompilationUnit_problems(collection);
    for (Problem value : nameProblems()) {
      collection.add(value);
    }
    for (Problem value : typeProblems()) {
      collection.add(value);
    }
  }
  protected void contributeTo_Program_extractedStatementTypes(java.util.Collection<String> collection) {
    super.contributeTo_Program_extractedStatementTypes(collection);
    collection.add(("STY;Const-Case-Statement"));
  }
}
