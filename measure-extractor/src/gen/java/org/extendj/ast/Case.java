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
 * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\grammar\\Java.ast:201
 * @production Case : {@link Stmt};

 */
public abstract class Case extends Stmt implements Cloneable {
  /**
   * @declaredat ASTNode:1
   */
  public Case() {
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
  /** @apilevel low-level 
   * @declaredat ASTNode:13
   */
  protected int numChildren() {
    return 0;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:19
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:23
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    assignedBefore_Variable_reset();
    assignedAfter_Variable_reset();
    unassignedAfter_Variable_reset();
    bind_Case_reset();
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
  public Case clone() throws CloneNotSupportedException {
    Case node = (Case) super.clone();
    return node;
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @deprecated Please use treeCopy or treeCopyNoTransform instead
   * @declaredat ASTNode:46
   */
  @Deprecated
  public abstract Case fullCopy();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:54
   */
  public abstract Case treeCopyNoTransform();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The subtree of this node is traversed to trigger rewrites before copy.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:62
   */
  public abstract Case treeCopy();
  /**
   * @attribute syn
   * @aspect NameCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\NameCheck.jrag:586
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="NameCheck", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\NameCheck.jrag:586")
  public abstract boolean constValue(Case c);
  /** @apilevel internal */
  private void assignedBefore_Variable_reset() {
    assignedBefore_Variable_values = null;
  }
  protected java.util.Map assignedBefore_Variable_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="DefiniteAssignment", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:735")
  public boolean assignedBefore(Variable v) {
    Object _parameters = v;
    if (assignedBefore_Variable_values == null) assignedBefore_Variable_values = new java.util.HashMap(4);
    ASTNode$State.CircularValue _value;
    if (assignedBefore_Variable_values.containsKey(_parameters)) {
      Object _cache = assignedBefore_Variable_values.get(_parameters);
      if (!(_cache instanceof ASTNode$State.CircularValue)) {
        return (Boolean) _cache;
      } else {
        _value = (ASTNode$State.CircularValue) _cache;
      }
    } else {
      _value = new ASTNode$State.CircularValue();
      assignedBefore_Variable_values.put(_parameters, _value);
      _value.value = true;
    }
    ASTNode$State state = state();
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      boolean new_assignedBefore_Variable_value;
      do {
        _value.cycle = state.nextCycle();
        new_assignedBefore_Variable_value = getParent().getParent() instanceof Block
              && ((Block) getParent().getParent()).assignedBefore(v)
              && super.assignedBefore(v);
        if (new_assignedBefore_Variable_value != ((Boolean)_value.value)) {
          state.setChangeInCycle();
          _value.value = new_assignedBefore_Variable_value;
        }
      } while (state.testAndClearChangeInCycle());
      assignedBefore_Variable_values.put(_parameters, new_assignedBefore_Variable_value);

      state.leaveCircle();
      return new_assignedBefore_Variable_value;
    } else if (_value.cycle != state.cycle()) {
      _value.cycle = state.cycle();
      boolean new_assignedBefore_Variable_value = getParent().getParent() instanceof Block
            && ((Block) getParent().getParent()).assignedBefore(v)
            && super.assignedBefore(v);
      if (new_assignedBefore_Variable_value != ((Boolean)_value.value)) {
        state.setChangeInCycle();
        _value.value = new_assignedBefore_Variable_value;
      }
      return new_assignedBefore_Variable_value;
    } else {
      return (Boolean) _value.value;
    }
  }
  /** @apilevel internal */
  private void assignedAfter_Variable_reset() {
    assignedAfter_Variable_values = null;
  }
  protected java.util.Map assignedAfter_Variable_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="DefiniteAssignment", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:264")
  public boolean assignedAfter(Variable v) {
    Object _parameters = v;
    if (assignedAfter_Variable_values == null) assignedAfter_Variable_values = new java.util.HashMap(4);
    ASTNode$State.CircularValue _value;
    if (assignedAfter_Variable_values.containsKey(_parameters)) {
      Object _cache = assignedAfter_Variable_values.get(_parameters);
      if (!(_cache instanceof ASTNode$State.CircularValue)) {
        return (Boolean) _cache;
      } else {
        _value = (ASTNode$State.CircularValue) _cache;
      }
    } else {
      _value = new ASTNode$State.CircularValue();
      assignedAfter_Variable_values.put(_parameters, _value);
      _value.value = true;
    }
    ASTNode$State state = state();
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      boolean new_assignedAfter_Variable_value;
      do {
        _value.cycle = state.nextCycle();
        new_assignedAfter_Variable_value = assignedBefore(v);
        if (new_assignedAfter_Variable_value != ((Boolean)_value.value)) {
          state.setChangeInCycle();
          _value.value = new_assignedAfter_Variable_value;
        }
      } while (state.testAndClearChangeInCycle());
      assignedAfter_Variable_values.put(_parameters, new_assignedAfter_Variable_value);

      state.leaveCircle();
      return new_assignedAfter_Variable_value;
    } else if (_value.cycle != state.cycle()) {
      _value.cycle = state.cycle();
      boolean new_assignedAfter_Variable_value = assignedBefore(v);
      if (new_assignedAfter_Variable_value != ((Boolean)_value.value)) {
        state.setChangeInCycle();
        _value.value = new_assignedAfter_Variable_value;
      }
      return new_assignedAfter_Variable_value;
    } else {
      return (Boolean) _value.value;
    }
  }
  /**
   * @attribute syn
   * @aspect DefiniteUnassignment
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:1393
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="DefiniteUnassignment", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:1393")
  public boolean unassignedBefore(Variable v) {
    boolean unassignedBefore_Variable_value = getParent().getParent() instanceof Block
          && ((Block) getParent().getParent()).unassignedBefore(v)
          && super.unassignedBefore(v);
    return unassignedBefore_Variable_value;
  }
  /** @apilevel internal */
  private void unassignedAfter_Variable_reset() {
    unassignedAfter_Variable_values = null;
  }
  protected java.util.Map unassignedAfter_Variable_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="DefiniteUnassignment", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:899")
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
        new_unassignedAfter_Variable_value = unassignedBefore(v);
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
      boolean new_unassignedAfter_Variable_value = unassignedBefore(v);
      if (new_unassignedAfter_Variable_value != ((Boolean)_value.value)) {
        state.setChangeInCycle();
        _value.value = new_unassignedAfter_Variable_value;
      }
      return new_unassignedAfter_Variable_value;
    } else {
      return (Boolean) _value.value;
    }
  }
  /**
   * @attribute syn
   * @aspect UnreachableStatements
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\UnreachableStatements.jrag:127
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="UnreachableStatements", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\UnreachableStatements.jrag:127")
  public boolean reachable() {
    boolean reachable_value = getParent().getParent() instanceof Block && ((Block) getParent().getParent()).reachable();
    return reachable_value;
  }
  /**
   * @attribute syn
   * @aspect PreciseRethrow
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\PreciseRethrow.jrag:78
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="PreciseRethrow", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\PreciseRethrow.jrag:78")
  public boolean modifiedInScope(Variable var) {
    boolean modifiedInScope_Variable_value = false;
    return modifiedInScope_Variable_value;
  }
  /**
   * @attribute syn
   * @aspect StringsInSwitch
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\StringsInSwitch.jrag:61
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StringsInSwitch", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\StringsInSwitch.jrag:61")
  public boolean isDefaultCase() {
    boolean isDefaultCase_value = false;
    return isDefaultCase_value;
  }
  /**
   * @attribute inh
   * @aspect NameCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\NameCheck.jrag:569
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="NameCheck", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\NameCheck.jrag:569")
  public Case bind(Case c) {
    Object _parameters = c;
    if (bind_Case_computed == null) bind_Case_computed = new java.util.HashMap(4);
    if (bind_Case_values == null) bind_Case_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (bind_Case_values.containsKey(_parameters) && bind_Case_computed != null
        && bind_Case_computed.containsKey(_parameters)
        && (bind_Case_computed.get(_parameters) == ASTNode$State.NON_CYCLE || bind_Case_computed.get(_parameters) == state().cycle())) {
      return (Case) bind_Case_values.get(_parameters);
    }
    Case bind_Case_value = getParent().Define_bind(this, null, c);
    if (state().inCircle()) {
      bind_Case_values.put(_parameters, bind_Case_value);
      bind_Case_computed.put(_parameters, state().cycle());
    
    } else {
      bind_Case_values.put(_parameters, bind_Case_value);
      bind_Case_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return bind_Case_value;
  }
  /** @apilevel internal */
  private void bind_Case_reset() {
    bind_Case_computed = new java.util.HashMap(4);
    bind_Case_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map bind_Case_values;
  /** @apilevel internal */
  protected java.util.Map bind_Case_computed;
  /**
   * @attribute inh
   * @aspect TypeCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeCheck.jrag:482
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="TypeCheck", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeCheck.jrag:482")
  public TypeDecl switchType() {
    TypeDecl switchType_value = getParent().Define_switchType(this, null);
    return switchType_value;
  }
  /** @apilevel internal */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
  /** @apilevel internal */
  public boolean canRewrite() {
    return false;
  }
  protected void collect_contributors_Program_extractedBranches(Program _root, java.util.Map<ASTNode, java.util.Set<ASTNode>> _map) {
    // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:18
    {
      java.util.Set<ASTNode> contributors = _map.get(_root);
      if (contributors == null) {
        contributors = new java.util.LinkedHashSet<ASTNode>();
        _map.put((ASTNode) _root, contributors);
      }
      contributors.add(this);
    }
    super.collect_contributors_Program_extractedBranches(_root, _map);
  }
  protected void collect_contributors_Program_extractedStatementTypes(Program _root, java.util.Map<ASTNode, java.util.Set<ASTNode>> _map) {
    // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:123
    if ((!(this instanceof ConstCase || this instanceof DefaultCase))) {
      {
        java.util.Set<ASTNode> contributors = _map.get(_root);
        if (contributors == null) {
          contributors = new java.util.LinkedHashSet<ASTNode>();
          _map.put((ASTNode) _root, contributors);
        }
        contributors.add(this);
      }
    }
    super.collect_contributors_Program_extractedStatementTypes(_root, _map);
  }
  protected void collect_contributors_Program_extractedSwitchCases(Program _root, java.util.Map<ASTNode, java.util.Set<ASTNode>> _map) {
    // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:139
    {
      java.util.Set<ASTNode> contributors = _map.get(_root);
      if (contributors == null) {
        contributors = new java.util.LinkedHashSet<ASTNode>();
        _map.put((ASTNode) _root, contributors);
      }
      contributors.add(this);
    }
    super.collect_contributors_Program_extractedSwitchCases(_root, _map);
  }
  protected void contributeTo_Program_extractedBranches(java.util.Collection<String> collection) {
    super.contributeTo_Program_extractedBranches(collection);
    collection.add(("BRA;" + this.compilationUnit().pathName() + ";" + this.location() + ";Case-Branch"));
  }
  protected void contributeTo_Program_extractedStatementTypes(java.util.Collection<String> collection) {
    super.contributeTo_Program_extractedStatementTypes(collection);
    if ((!(this instanceof ConstCase || this instanceof DefaultCase))) {
      collection.add(("STY;Case-Statement"));
    }
  }
  protected void contributeTo_Program_extractedSwitchCases(java.util.Collection<String> collection) {
    super.contributeTo_Program_extractedSwitchCases(collection);
    collection.add(("SWC;" + this.compilationUnit().pathName() + ";" + this.location() + ";"));
  }
}
