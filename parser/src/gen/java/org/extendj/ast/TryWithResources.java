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
 * The JSR 334 try with resources statement.
 * @ast node
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\grammar\\TryWithResources.ast:4
 * @production TryWithResources : {@link TryStmt} ::= <span class="component">Resource:{@link ResourceDeclaration}*</span> <span class="component">{@link Block}</span> <span class="component">{@link CatchClause}*</span> <span class="component">[Finally:{@link Block}]</span>;

 */
public class TryWithResources extends TryStmt implements Cloneable, VariableScope {
  /**
   * @aspect Java7PrettyPrint
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\PrettyPrint.jadd:50
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print("try (");
    out.join(getResourceList());
    out.print(") ");
    out.print(getBlock());
    if (!out.isNewLine()) {
      out.println();
    }
    if (hasCatchClause()) {
      out.print(" ");
      out.join(getCatchClauseList(), new PrettyPrinter.Joiner() {
        @Override
        public void printSeparator(PrettyPrinter out) {
          out.print(" ");
        }
      });
    }
    if (!out.isNewLine()) {
      out.println();
    }
    if (hasFinally()) {
      out.print(" finally ");
      out.print(getFinally());
    }
  }
  /**
   * Exception error checks.
   * @aspect TryWithResources
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\TryWithResources.jrag:63
   */
  public void exceptionHandling() {

    // Check exception handling of exceptions on auto closing of resource
    for (ResourceDeclaration resource : getResourceList()) {
      MethodDecl close = lookupClose(resource);
      if (close == null) {
        continue;
      }
      for (Access exception : close.getExceptionList()) {
        TypeDecl exceptionType = exception.type();
        if (!twrHandlesException(exceptionType)) {
          error("automatic closing of resource " + resource.name()
              + " may raise the uncaught exception " + exceptionType.fullName() + "; "
              + "it must be caught or declared as being thrown");
        }
      }
    }
  }
  /**
   * Returns true if the try-with-resources statement can throw
   * an exception of type (or a subtype of) catchType.
   * @aspect TryWithResources
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\TryWithResources.jrag:222
   */
  protected boolean reachedException(TypeDecl catchType) {
    boolean found = false;
    // found is true if the exception type is caught by a catch clause
    for (int i = 0; i < getNumCatchClause() && !found; i++) {
      if (getCatchClause(i).handles(catchType)) {
        found = true;
      }
    }
    // If an exception is thrown in the block and the exception is not caught and
    // either there is no finally block or the finally block can complete normally.
    if (!found && (!hasNonEmptyFinally() || getFinally().canCompleteNormally()) ) {
      if (catchableException(catchType)) {
        return true;
      }
    }
    // Even if the exception is caught by the catch clauses they may
    // throw new exceptions.
    for (int i = 0; i < getNumCatchClause(); i++) {
      if (getCatchClause(i).reachedException(catchType)) {
        return true;
      }
    }
    return hasNonEmptyFinally() && getFinally().reachedException(catchType);
  }
  /**
   * @declaredat ASTNode:1
   */
  public TryWithResources() {
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
    children = new ASTNode[5];
    setChild(new List(), 0);
    setChild(new List(), 2);
    setChild(new Opt(), 3);
  }
  /**
   * @declaredat ASTNode:16
   */
  public TryWithResources(List<ResourceDeclaration> p0, Block p1, List<CatchClause> p2, Opt<Block> p3) {
    setChild(p0, 0);
    setChild(p1, 1);
    setChild(p2, 2);
    setChild(p3, 3);
  }
  /**
   * @apilevel low-level
   * @declaredat ASTNode:25
   */
  protected int numChildren() {
    return 4;
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
    localLookup_String_reset();
    localVariableDeclaration_String_reset();
    isDAafter_Variable_reset();
    catchableException_TypeDecl_reset();
    handlesException_TypeDecl_reset();
    typeError_reset();
    typeRuntimeException_reset();
    lookupVariable_String_reset();
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
  public TryWithResources clone() throws CloneNotSupportedException {
    TryWithResources node = (TryWithResources) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:70
   */
  public TryWithResources copy() {
    try {
      TryWithResources node = (TryWithResources) clone();
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
   * @declaredat ASTNode:89
   */
  @Deprecated
  public TryWithResources fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:99
   */
  public TryWithResources treeCopyNoTransform() {
    TryWithResources tree = (TryWithResources) copy();
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        switch (i) {
        case 4:
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
   * @declaredat ASTNode:124
   */
  public TryWithResources treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:131
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node);    
  }
  /**
   * Replaces the Resource list.
   * @param list The new list node to be used as the Resource list.
   * @apilevel high-level
   */
  public void setResourceList(List<ResourceDeclaration> list) {
    setChild(list, 0);
  }
  /**
   * Retrieves the number of children in the Resource list.
   * @return Number of children in the Resource list.
   * @apilevel high-level
   */
  public int getNumResource() {
    return getResourceList().getNumChild();
  }
  /**
   * Retrieves the number of children in the Resource list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the Resource list.
   * @apilevel low-level
   */
  public int getNumResourceNoTransform() {
    return getResourceListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the Resource list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the Resource list.
   * @apilevel high-level
   */
  public ResourceDeclaration getResource(int i) {
    return (ResourceDeclaration) getResourceList().getChild(i);
  }
  /**
   * Check whether the Resource list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasResource() {
    return getResourceList().getNumChild() != 0;
  }
  /**
   * Append an element to the Resource list.
   * @param node The element to append to the Resource list.
   * @apilevel high-level
   */
  public void addResource(ResourceDeclaration node) {
    List<ResourceDeclaration> list = (parent == null) ? getResourceListNoTransform() : getResourceList();
    list.addChild(node);
  }
  /**
   * @apilevel low-level
   */
  public void addResourceNoTransform(ResourceDeclaration node) {
    List<ResourceDeclaration> list = getResourceListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the Resource list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setResource(ResourceDeclaration node, int i) {
    List<ResourceDeclaration> list = getResourceList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the Resource list.
   * @return The node representing the Resource list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="Resource")
  public List<ResourceDeclaration> getResourceList() {
    List<ResourceDeclaration> list = (List<ResourceDeclaration>) getChild(0);
    return list;
  }
  /**
   * Retrieves the Resource list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the Resource list.
   * @apilevel low-level
   */
  public List<ResourceDeclaration> getResourceListNoTransform() {
    return (List<ResourceDeclaration>) getChildNoTransform(0);
  }
  /**
   * Retrieves the Resource list.
   * @return The node representing the Resource list.
   * @apilevel high-level
   */
  public List<ResourceDeclaration> getResources() {
    return getResourceList();
  }
  /**
   * Retrieves the Resource list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the Resource list.
   * @apilevel low-level
   */
  public List<ResourceDeclaration> getResourcesNoTransform() {
    return getResourceListNoTransform();
  }
  /**
   * Replaces the Block child.
   * @param node The new node to replace the Block child.
   * @apilevel high-level
   */
  public void setBlock(Block node) {
    setChild(node, 1);
  }
  /**
   * Retrieves the Block child.
   * @return The current node used as the Block child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Block")
  public Block getBlock() {
    return (Block) getChild(1);
  }
  /**
   * Retrieves the Block child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Block child.
   * @apilevel low-level
   */
  public Block getBlockNoTransform() {
    return (Block) getChildNoTransform(1);
  }
  /**
   * Replaces the CatchClause list.
   * @param list The new list node to be used as the CatchClause list.
   * @apilevel high-level
   */
  public void setCatchClauseList(List<CatchClause> list) {
    setChild(list, 2);
  }
  /**
   * Retrieves the number of children in the CatchClause list.
   * @return Number of children in the CatchClause list.
   * @apilevel high-level
   */
  public int getNumCatchClause() {
    return getCatchClauseList().getNumChild();
  }
  /**
   * Retrieves the number of children in the CatchClause list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the CatchClause list.
   * @apilevel low-level
   */
  public int getNumCatchClauseNoTransform() {
    return getCatchClauseListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the CatchClause list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the CatchClause list.
   * @apilevel high-level
   */
  public CatchClause getCatchClause(int i) {
    return (CatchClause) getCatchClauseList().getChild(i);
  }
  /**
   * Check whether the CatchClause list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasCatchClause() {
    return getCatchClauseList().getNumChild() != 0;
  }
  /**
   * Append an element to the CatchClause list.
   * @param node The element to append to the CatchClause list.
   * @apilevel high-level
   */
  public void addCatchClause(CatchClause node) {
    List<CatchClause> list = (parent == null) ? getCatchClauseListNoTransform() : getCatchClauseList();
    list.addChild(node);
  }
  /**
   * @apilevel low-level
   */
  public void addCatchClauseNoTransform(CatchClause node) {
    List<CatchClause> list = getCatchClauseListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the CatchClause list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setCatchClause(CatchClause node, int i) {
    List<CatchClause> list = getCatchClauseList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the CatchClause list.
   * @return The node representing the CatchClause list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="CatchClause")
  public List<CatchClause> getCatchClauseList() {
    List<CatchClause> list = (List<CatchClause>) getChild(2);
    return list;
  }
  /**
   * Retrieves the CatchClause list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the CatchClause list.
   * @apilevel low-level
   */
  public List<CatchClause> getCatchClauseListNoTransform() {
    return (List<CatchClause>) getChildNoTransform(2);
  }
  /**
   * Retrieves the CatchClause list.
   * @return The node representing the CatchClause list.
   * @apilevel high-level
   */
  public List<CatchClause> getCatchClauses() {
    return getCatchClauseList();
  }
  /**
   * Retrieves the CatchClause list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the CatchClause list.
   * @apilevel low-level
   */
  public List<CatchClause> getCatchClausesNoTransform() {
    return getCatchClauseListNoTransform();
  }
  /**
   * Replaces the optional node for the Finally child. This is the <code>Opt</code>
   * node containing the child Finally, not the actual child!
   * @param opt The new node to be used as the optional node for the Finally child.
   * @apilevel low-level
   */
  public void setFinallyOpt(Opt<Block> opt) {
    setChild(opt, 3);
  }
  /**
   * Replaces the (optional) Finally child.
   * @param node The new node to be used as the Finally child.
   * @apilevel high-level
   */
  public void setFinally(Block node) {
    getFinallyOpt().setChild(node, 0);
  }
  /**
   * Check whether the optional Finally child exists.
   * @return {@code true} if the optional Finally child exists, {@code false} if it does not.
   * @apilevel high-level
   */
  public boolean hasFinally() {
    return getFinallyOpt().getNumChild() != 0;
  }
  /**
   * Retrieves the (optional) Finally child.
   * @return The Finally child, if it exists. Returns {@code null} otherwise.
   * @apilevel low-level
   */
  public Block getFinally() {
    return (Block) getFinallyOpt().getChild(0);
  }
  /**
   * Retrieves the optional node for the Finally child. This is the <code>Opt</code> node containing the child Finally, not the actual child!
   * @return The optional node for child the Finally child.
   * @apilevel low-level
   */
  @ASTNodeAnnotation.OptChild(name="Finally")
  public Opt<Block> getFinallyOpt() {
    return (Opt<Block>) getChild(3);
  }
  /**
   * Retrieves the optional node for child Finally. This is the <code>Opt</code> node containing the child Finally, not the actual child!
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The optional node for child Finally.
   * @apilevel low-level
   */
  public Opt<Block> getFinallyOptNoTransform() {
    return (Opt<Block>) getChildNoTransform(3);
  }
  /**
   * Retrieves the ExceptionHandler child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the ExceptionHandler child.
   * @apilevel low-level
   */
  public Block getExceptionHandlerNoTransform() {
    return (Block) getChildNoTransform(4);
  }
  /**
   * Retrieves the child position of the optional child ExceptionHandler.
   * @return The the child position of the optional child ExceptionHandler.
   * @apilevel low-level
   */
  protected int getExceptionHandlerChildPosition() {
    return 4;
  }
  /**
   * This attribute computes whether or not the TWR statement
   * has a catch clause which handles the exception.
   * @attribute syn
   * @aspect TryWithResources
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\TryWithResources.jrag:86
   */
  @ASTNodeAnnotation.Attribute
  public boolean catchHandlesException(TypeDecl exceptionType) {
    {
        for (int i = 0; i < getNumCatchClause(); i++) {
          if (getCatchClause(i).handles(exceptionType)) {
            return true;
          }
        }
        return false;
      }
  }
  /**
   * Returns true if exceptions of type exceptionType are handled
   * in the try-with-resources statement or any containing statement
   * within the directly enclosing method or initializer block.
   * @attribute syn
   * @aspect TryWithResources
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\TryWithResources.jrag:100
   */
  @ASTNodeAnnotation.Attribute
  public boolean twrHandlesException(TypeDecl exceptionType) {
    {
        if (catchHandlesException(exceptionType)) {
          return true;
        }
        if (hasNonEmptyFinally() && !getFinally().canCompleteNormally()) {
          return true;
        }
        return handlesException(exceptionType);
      }
  }
  /**
   * Lookup the close method declaration for the resource which is being used.
   * @attribute syn
   * @aspect TryWithResources
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\TryWithResources.jrag:125
   */
  @ASTNodeAnnotation.Attribute
  public MethodDecl lookupClose(ResourceDeclaration resource) {
    {
        TypeDecl resourceType = resource.getTypeAccess().type();
        for (MethodDecl method : (Collection<MethodDecl>) resourceType.memberMethods("close")) {
          if (method.getNumParameter() == 0) {
            return method;
          }
        }
        return null;
        /* We can't throw a runtime exception here. If there is no close method it
         * likely means that the resource type is not a subtype of java.lang.AutoCloseable
         * and type checking will report this error.
         */
        //throw new RuntimeException("close() not found for resource type "+resourceType.fullName());
      }
  }
  /**
   * @apilevel internal
   */
  protected java.util.Map localLookup_String_values;
  /**
   * @apilevel internal
   */
  private void localLookup_String_reset() {
    localLookup_String_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public SimpleSet localLookup(String name) {
    Object _parameters = name;
    if (localLookup_String_values == null) localLookup_String_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (localLookup_String_values.containsKey(_parameters)) {
      return (SimpleSet) localLookup_String_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    SimpleSet localLookup_String_value = localLookup_compute(name);
    if (isFinal && num == state().boundariesCrossed) {
      localLookup_String_values.put(_parameters, localLookup_String_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return localLookup_String_value;
  }
  /**
   * @apilevel internal
   */
  private SimpleSet localLookup_compute(String name) {
      VariableDeclaration v = localVariableDeclaration(name);
      if (v != null) {
        return v;
      }
      return lookupVariable(name);
    }
  /**
   * @apilevel internal
   */
  protected java.util.Map localVariableDeclaration_String_values;
  /**
   * @apilevel internal
   */
  private void localVariableDeclaration_String_reset() {
    localVariableDeclaration_String_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public VariableDeclaration localVariableDeclaration(String name) {
    Object _parameters = name;
    if (localVariableDeclaration_String_values == null) localVariableDeclaration_String_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (localVariableDeclaration_String_values.containsKey(_parameters)) {
      return (VariableDeclaration) localVariableDeclaration_String_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    VariableDeclaration localVariableDeclaration_String_value = localVariableDeclaration_compute(name);
    if (isFinal && num == state().boundariesCrossed) {
      localVariableDeclaration_String_values.put(_parameters, localVariableDeclaration_String_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return localVariableDeclaration_String_value;
  }
  /**
   * @apilevel internal
   */
  private VariableDeclaration localVariableDeclaration_compute(String name) {
      for (ResourceDeclaration resource : getResourceList()) {
        if (resource.declaresVariable(name)) {
          return resource;
        }
      }
      return null;
    }
  /**
   * @apilevel internal
   */
  protected java.util.Map isDAafter_Variable_values;
  /**
   * @apilevel internal
   */
  private void isDAafter_Variable_reset() {
    isDAafter_Variable_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isDAafter(Variable v) {
    Object _parameters = v;
    if (isDAafter_Variable_values == null) isDAafter_Variable_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (isDAafter_Variable_values.containsKey(_parameters)) {
      return (Boolean) isDAafter_Variable_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean isDAafter_Variable_value = getBlock().isDAafter(v);
    if (isFinal && num == state().boundariesCrossed) {
      isDAafter_Variable_values.put(_parameters, isDAafter_Variable_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return isDAafter_Variable_value;
  }
  /**
   * True if the automatic closing of resources in this try-with-resources statement
   * may throw an exception of type catchType.
   * @attribute syn
   * @aspect TryWithResources
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\TryWithResources.jrag:251
   */
  @ASTNodeAnnotation.Attribute
  public boolean resourceClosingException(TypeDecl catchType) {
    {
        for (ResourceDeclaration resource : getResourceList()) {
          MethodDecl close = lookupClose(resource);
          if (close == null) {
            continue;
          }
          for (Access exception : close.getExceptionList()) {
            TypeDecl exceptionType = exception.type();
            if (catchType.mayCatch(exception.type())) {
              return true;
            }
          }
        }
        return false;
      }
  }
  /**
   * True if the resource initialization of this try-with-resources statement
   * may throw an exception of type catchType.
   * @attribute syn
   * @aspect TryWithResources
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\TryWithResources.jrag:271
   */
  @ASTNodeAnnotation.Attribute
  public boolean resourceInitializationException(TypeDecl catchType) {
    {
        for (ResourceDeclaration resource : getResourceList()) {
          if (resource.reachedException(catchType)) {
            return true;
          }
        }
        return false;
      }
  }
  /**
   * @apilevel internal
   */
  protected java.util.Map catchableException_TypeDecl_values;
  /**
   * @apilevel internal
   */
  private void catchableException_TypeDecl_reset() {
    catchableException_TypeDecl_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean catchableException(TypeDecl type) {
    Object _parameters = type;
    if (catchableException_TypeDecl_values == null) catchableException_TypeDecl_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (catchableException_TypeDecl_values.containsKey(_parameters)) {
      return (Boolean) catchableException_TypeDecl_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean catchableException_TypeDecl_value = getBlock().reachedException(type)
          || resourceClosingException(type)
          || resourceInitializationException(type);
    if (isFinal && num == state().boundariesCrossed) {
      catchableException_TypeDecl_values.put(_parameters, catchableException_TypeDecl_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return catchableException_TypeDecl_value;
  }
  /**
   * Inherit the handlesException attribute from methoddecl.
   * @attribute inh
   * @aspect TryWithResources
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\TryWithResources.jrag:113
   */
  @ASTNodeAnnotation.Attribute
  public boolean handlesException(TypeDecl exceptionType) {
    Object _parameters = exceptionType;
    if (handlesException_TypeDecl_values == null) handlesException_TypeDecl_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (handlesException_TypeDecl_values.containsKey(_parameters)) {
      return (Boolean) handlesException_TypeDecl_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean handlesException_TypeDecl_value = getParent().Define_handlesException(this, null, exceptionType);
    if (isFinal && num == state().boundariesCrossed) {
      handlesException_TypeDecl_values.put(_parameters, handlesException_TypeDecl_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return handlesException_TypeDecl_value;
  }
  /**
   * @apilevel internal
   */
  protected java.util.Map handlesException_TypeDecl_values;
  /**
   * @apilevel internal
   */
  private void handlesException_TypeDecl_reset() {
    handlesException_TypeDecl_values = null;
  }
  /**
   * @attribute inh
   * @aspect TryWithResources
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\TryWithResources.jrag:140
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl typeError() {
    ASTNode$State state = state();
    if (typeError_computed) {
      return typeError_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    typeError_value = getParent().Define_typeError(this, null);
    if (isFinal && num == state().boundariesCrossed) {
      typeError_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return typeError_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean typeError_computed = false;
  /**
   * @apilevel internal
   */
  protected TypeDecl typeError_value;
  /**
   * @apilevel internal
   */
  private void typeError_reset() {
    typeError_computed = false;
    typeError_value = null;
  }
  /**
   * @attribute inh
   * @aspect TryWithResources
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\TryWithResources.jrag:141
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl typeRuntimeException() {
    ASTNode$State state = state();
    if (typeRuntimeException_computed) {
      return typeRuntimeException_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    typeRuntimeException_value = getParent().Define_typeRuntimeException(this, null);
    if (isFinal && num == state().boundariesCrossed) {
      typeRuntimeException_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return typeRuntimeException_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean typeRuntimeException_computed = false;
  /**
   * @apilevel internal
   */
  protected TypeDecl typeRuntimeException_value;
  /**
   * @apilevel internal
   */
  private void typeRuntimeException_reset() {
    typeRuntimeException_computed = false;
    typeRuntimeException_value = null;
  }
  /**
   * @attribute inh
   * @aspect TryWithResources
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\TryWithResources.jrag:179
   */
  @ASTNodeAnnotation.Attribute
  public SimpleSet lookupVariable(String name) {
    Object _parameters = name;
    if (lookupVariable_String_values == null) lookupVariable_String_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (lookupVariable_String_values.containsKey(_parameters)) {
      return (SimpleSet) lookupVariable_String_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    SimpleSet lookupVariable_String_value = getParent().Define_lookupVariable(this, null, name);
    if (isFinal && num == state().boundariesCrossed) {
      lookupVariable_String_values.put(_parameters, lookupVariable_String_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return lookupVariable_String_value;
  }
  /**
   * @apilevel internal
   */
  protected java.util.Map lookupVariable_String_values;
  /**
   * @apilevel internal
   */
  private void lookupVariable_String_reset() {
    lookupVariable_String_values = null;
  }
  /**
   * @attribute inh
   * @aspect TryWithResources
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\TryWithResources.jrag:183
   */
  @ASTNodeAnnotation.Attribute
  public boolean resourcePreviouslyDeclared(String name) {
    boolean resourcePreviouslyDeclared_String_value = getParent().Define_resourcePreviouslyDeclared(this, null, name);

    return resourcePreviouslyDeclared_String_value;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\TryWithResources.jrag:113
   * @apilevel internal
   */
  public boolean Define_handlesException(ASTNode caller, ASTNode child, TypeDecl exceptionType) {
    if (caller == getBlockNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\TryWithResources.jrag:118
      return twrHandlesException(exceptionType);
    }
    else if (caller == getResourceListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\TryWithResources.jrag:115
      int i = caller.getIndexOfChild(child);
      return twrHandlesException(exceptionType);
    }
    else {
      return super.Define_handlesException(caller, child, exceptionType);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\UnreachableStatements.jrag:185
   * @apilevel internal
   */
  public boolean Define_reachableCatchClause(ASTNode caller, ASTNode child, TypeDecl exceptionType) {
    if (caller == getCatchClauseListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\TryWithResources.jrag:143
      int childIndex = caller.getIndexOfChild(child);
      {
          for (int i = 0; i < childIndex; i++) {
            if (getCatchClause(i).handles(exceptionType)) {
              return false;
            }
          }
          if (catchableException(exceptionType)) {
            return true;
          }
          if (exceptionType.mayCatch(typeError()) || exceptionType.mayCatch(typeRuntimeException())) {
            return true;
          }
          return false;
        }
    }
    else {
      return super.Define_reachableCatchClause(caller, child, exceptionType);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\LookupVariable.jrag:30
   * @apilevel internal
   */
  public SimpleSet Define_lookupVariable(ASTNode caller, ASTNode child, String name) {
    if (caller == getBlockNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\TryWithResources.jrag:161
      return localLookup(name);
    }
    else {
      return getParent().Define_lookupVariable(this, caller, name);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\NameCheck.jrag:30
   * @apilevel internal
   */
  public VariableScope Define_outerScope(ASTNode caller, ASTNode child) {
    if (caller == getResourceListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\TryWithResources.jrag:180
      int i = caller.getIndexOfChild(child);
      return this;
    }
    else {
      return getParent().Define_outerScope(this, caller);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\TryWithResources.jrag:183
   * @apilevel internal
   */
  public boolean Define_resourcePreviouslyDeclared(ASTNode caller, ASTNode child, String name) {
    if (caller == getResourceListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\TryWithResources.jrag:184
      int index = caller.getIndexOfChild(child);
      {
          for (int i = 0; i < index; ++i) {
            if (getResource(i).name().equals(name)) {
              return true;
            }
          }
          return false;
        }
    }
    else {
      return getParent().Define_resourcePreviouslyDeclared(this, caller, name);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:255
   * @apilevel internal
   */
  public boolean Define_isDAbefore(ASTNode caller, ASTNode child, Variable v) {
    if (caller == getBlockNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\TryWithResources.jrag:213
      return getNumResource() == 0
            ? isDAbefore(v)
            : getResource(getNumResource() - 1).isDAafter(v);
    }
    else if (caller == getResourceListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\TryWithResources.jrag:210
      int index = caller.getIndexOfChild(child);
      return index == 0 ? isDAbefore(v) : getResource(index - 1).isDAafter(v);
    }
    else {
      return super.Define_isDAbefore(caller, child, v);
    }
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
