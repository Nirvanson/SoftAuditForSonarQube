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
 * @production ASTNode;

 */
public class ASTNode<T extends ASTNode> extends beaver.Symbol implements Cloneable, PrettyPrintable, Iterable<T> {
  /**
   * @aspect DumpTree
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DumpTree.jadd:38
   */
  private String DUMP_TREE_INDENT = "  ";
  /**
   * @aspect DumpTree
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DumpTree.jadd:40
   */
  public String dumpTree() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    dumpTree(new PrintStream(bytes));
    return bytes.toString();
  }
  /**
   * @aspect DumpTree
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DumpTree.jadd:46
   */
  public void dumpTree(PrintStream out) {
    dumpTree(out, "");
    out.flush();
  }
  /**
   * @aspect DumpTree
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DumpTree.jadd:51
   */
  public void dumpTree(PrintStream out, String indent) {
    out.print(indent + getClass().getSimpleName());
    out.println(getTokens());
    String childIndent = indent + DUMP_TREE_INDENT;
    for (int i = 0; i < getNumChild(); ++i) {
      ASTNode child = getChild(i);
      if (child == null)  {
        out.println(childIndent + "null");
      } else {
        child.dumpTree(out, childIndent);
      }
    }
  }
  /**
   * @aspect DumpTree
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DumpTree.jadd:65
   */
  public String getTokens() {
    StringBuilder sb = new StringBuilder();
    Method[] methods = getClass().getMethods();
    for (Method method : getClass().getMethods()) {
      ASTNodeAnnotation.Token token = method.getAnnotation(ASTNodeAnnotation.Token.class);
      if (token != null) {
        try {
          sb.append(" " + token.name() + "=\"" + method.invoke(this) + "\"");
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        }
      }
    }
    return sb.toString();
  }
  /**
   * @aspect DumpTree
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DumpTree.jadd:81
   */
  public String dumpTreeNoRewrite() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    dumpTreeNoRewrite(new PrintStream(bytes));
    return bytes.toString();
  }
  /**
   * @aspect DumpTree
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DumpTree.jadd:87
   */
  public void dumpTreeNoRewrite(PrintStream out) {
    dumpTreeNoRewrite(out, "");
    out.flush();
  }
  /**
   * @aspect DumpTree
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DumpTree.jadd:92
   */
  public void dumpTreeNoRewrite(PrintStream out, String indent) {
    out.print(indent + getClass().getSimpleName());
    out.println(getTokens());
    String childIndent = indent + DUMP_TREE_INDENT;
    for (int i = 0; i < getNumChildNoTransform(); ++i) {
      ASTNode child = getChildNoTransform(i);
      if (child == null)  {
        out.println(childIndent + "null");
      } else {
        child.dumpTreeNoRewrite(out, childIndent);
      }
    }
  }
  /**
   * @aspect StructuredPrettyPrint
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\StructuredPrettyPrint.jadd:32
   */
  public String structuredPrettyPrint() throws IOException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    // First, transform the tree by wrapping all expressions in ParExpr.
    wrapExprs();
    prettyPrint(new PrintStream(out, false, "UTF8"));
    return out.toString().trim();
  }
  /**
   * Hacky way of inserting parens around all expressions.
   * @aspect StructuredPrettyPrint
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\StructuredPrettyPrint.jadd:43
   */
  private void wrapExprs() {
    for (int i = 0; i < getNumChildNoTransform(); ++i) {
      ASTNode child = getChildNoTransform(i);
      if (child instanceof Expr &&
          !(child instanceof ParExpr) &&
          !(child instanceof Access) &&
          !(child instanceof Literal)) {
        child.setParent(null);
        ParExpr parExpr = new ParExpr((Expr) child);
        setChild(parExpr, i);
      }
      child.wrapExprs();
    }
  }
  /**
   * @aspect AccessControl
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\AccessControl.jrag:150
   */
  public void accessControl() {
  }
  /**
   * @aspect AnonymousClasses
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\AnonymousClasses.jrag:123
   */
  protected void collectExceptions(Collection c, ASTNode target) {
    for (int i = 0; i < getNumChild(); i++) {
      getChild(i).collectExceptions(c, target);
    }
  }
  /**
   * @aspect BranchTarget
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\BranchTarget.jrag:90
   */
  public void collectBranches(Collection<Stmt> c) {
    for (int i = 0; i < getNumChild(); i++) {
      getChild(i).collectBranches(c);
    }
  }
  /**
   * @aspect DeclareBeforeUse
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DeclareBeforeUse.jrag:42
   */
  public int varChildIndex(TypeDecl t) {
    ASTNode node = this;
    while (node != null && node.getParent() != null && node.getParent().getParent() != t) {
      node = node.getParent();
    }
    if (node == null) {
      return -1;
    }
    return t.getBodyDeclListNoTransform().getIndexOfChild(node);
  }
  /**
   * @aspect DefiniteAssignment
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:34
   */
  public void definiteAssignment() {
  }
  /**
   * @aspect DA
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:495
   */
  protected boolean checkDUeverywhere(Variable v) {
    for (int i = 0; i < getNumChild(); i++) {
      if (!getChild(i).checkDUeverywhere(v)) {
        return false;
      }
    }
    return true;
  }
  /**
   * @aspect DA
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:627
   */
  protected boolean isDescendantTo(ASTNode node) {
    if (this == node) {
      return true;
    }
    if (getParent() == null) {
      return false;
    }
    return getParent().isDescendantTo(node);
  }
  /**
   * @aspect ErrorCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ErrorCheck.jrag:33
   */
  protected String sourceFile() {
    ASTNode node = this;
    while (node != null && !(node instanceof CompilationUnit)) {
      node = node.getParent();
    }
    if (node == null) {
      return "Unknown file";
    }
    CompilationUnit u = (CompilationUnit) node;
    return u.relativeName();
  }
  /**
   * @aspect ErrorCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ErrorCheck.jrag:57
   */
  public ASTNode setLocation(ASTNode node) {
    setStart(node.getStart());
    setEnd(node.getEnd());
    return this;
  }
  /**
   * @aspect ErrorCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ErrorCheck.jrag:63
   */
  public ASTNode setStart(int i) {
    start = i;
    return this;
  }
  /**
   * @aspect ErrorCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ErrorCheck.jrag:67
   */
  public int start() {
    return start;
  }
  /**
   * @aspect ErrorCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ErrorCheck.jrag:70
   */
  public ASTNode setEnd(int i) {
    end = i;
    return this;
  }
  /**
   * @aspect ErrorCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ErrorCheck.jrag:74
   */
  public int end() {
    return end;
  }
  /**
   * @aspect ErrorCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ErrorCheck.jrag:78
   */
  public String location() {
    return "" + lineNumber();
  }
  /**
   * @aspect ErrorCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ErrorCheck.jrag:81
   */
  public String errorPrefix() {
    return sourceFile() + ":" + location() + ":\n" + "  *** Semantic Error: ";
  }
  /**
   * @aspect ErrorCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ErrorCheck.jrag:84
   */
  public String warningPrefix() {
    return sourceFile() + ":" + location() + ":\n" + "  *** WARNING: ";
  }
  /**
   * @aspect ErrorCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ErrorCheck.jrag:198
   */
  public void errorf(String messagefmt, Object... args) {
    error(String.format(messagefmt, args));
  }
  /**
   * @aspect ErrorCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ErrorCheck.jrag:202
   */
  public void error(String message) {
    ASTNode node = this;
    while (node != null && !(node instanceof CompilationUnit)) {
      node = node.getParent();
    }
    CompilationUnit cu = (CompilationUnit) node;
    if (getNumChild() == 0 && getStart() != 0 && getEnd() != 0) {
      int line = getLine(getStart());
      int column = getColumn(getStart());
      int endLine = getLine(getEnd());
      int endColumn = getColumn(getEnd());
      cu.errors.add(new Problem(sourceFile(), message, line, column, endLine, endColumn,
          Problem.Severity.ERROR, Problem.Kind.SEMANTIC));
    } else {
      cu.errors.add(new Problem(sourceFile(), message, lineNumber(), Problem.Severity.ERROR,
          Problem.Kind.SEMANTIC));
    }
  }
  /**
   * @aspect ErrorCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ErrorCheck.jrag:221
   */
  public void warning(String s) {
    ASTNode node = this;
    while (node != null && !(node instanceof CompilationUnit)) {
      node = node.getParent();
    }
    CompilationUnit cu = (CompilationUnit) node;
    cu.warnings.add(new Problem(sourceFile(), "WARNING: " + s, lineNumber(), Problem.Severity.WARNING));
  }
  /**
   * @aspect ExceptionHandling
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ExceptionHandling.jrag:87
   */
  public void exceptionHandling() {
  }
  /**
   * @aspect ExceptionHandling
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ExceptionHandling.jrag:271
   */
  protected boolean reachedException(TypeDecl type) {
    for (int i = 0; i < getNumChild(); i++) {
      if (getChild(i).reachedException(type)) {
        return true;
      }
    }
    return false;
  }
  /**
   * @aspect LookupMethod
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupMethod.jrag:92
   */
  public static Collection removeInstanceMethods(Collection c) {
    c = new LinkedList(c);
    for (Iterator iter = c.iterator(); iter.hasNext(); ) {
      MethodDecl m = (MethodDecl) iter.next();
      if (!m.isStatic()) {
        iter.remove();
      }
    }
    return c;
  }
  /**
   * Utility method to put a SimpleSet-item in a signature map.
   * @aspect MemberMethods
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupMethod.jrag:511
   */
  protected static void putSimpleSetElement(Map<String,SimpleSet> map, String key, SimpleSet value) {
    SimpleSet set = map.get(key);
    if (set == null) {
      set = SimpleSet.emptySet;
    }
    map.put(key, set.add(value));
  }
  /**
   * @aspect VariableScope
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupVariable.jrag:274
   */
  public SimpleSet removeInstanceVariables(SimpleSet oldSet) {
    SimpleSet newSet = SimpleSet.emptySet;
    for (Iterator iter = oldSet.iterator(); iter.hasNext(); ) {
      Variable v = (Variable) iter.next();
      if (!v.isInstanceVariable()) {
        newSet = newSet.add(v);
      }
    }
    return newSet;
  }
  /**
   * @aspect Modifiers
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\Modifiers.jrag:32
   */
  void checkModifiers() {
  }
  /**
   * @aspect NameCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\NameCheck.jrag:33
   */
  public void nameCheck() {
  }
  /**
   * @aspect NameCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\NameCheck.jrag:36
   */
  public TypeDecl extractSingleType(SimpleSet c) {
    if (c.size() != 1) {
      return null;
    }
    return (TypeDecl) c.iterator().next();
  }
  /**
   * @return a copy of the block as an NTAFinallyBlock
   * @aspect NTAFinally
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\NTAFinally.jrag:35
   */
  protected static NTAFinallyBlock ntaFinallyBlock(FinallyHost origin, Stmt branch, Block block) {
    NTAFinallyBlock ntaBlock = new NTAFinallyBlock(origin);
    ntaBlock.addStmt((Block) block.treeCopyNoTransform());
    /*if (!block.canCompleteNormally()) {
     * // the target block's exit will replace our own exit code
     * // so we can just goto that block!
     * ntaBlock.addGoto(block);
     * } else ...
     */
    if (block.canCompleteNormally()) {
      FinallyHost enclosing = block.enclosingFinally(branch);
      if (enclosing != null) {
        ntaBlock.addStmt(ntaFinallyBlock(enclosing, branch, enclosing.getFinallyBlock()));
      }
    }
    return ntaBlock;
  }
  /**
   * Pretty-print this ASTNode.
   * @return pretty-printed representation of this AST node
   * @aspect PrettyPrintUtil
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\PrettyPrintUtil.jrag:41
   */
  public String prettyPrint() {
    ByteArrayOutputStream buf = new ByteArrayOutputStream();
    prettyPrint(new PrettyPrinter("  ", new PrintStream(buf)));
    return buf.toString();
  }
  /**
   * Pretty print this AST node to the target PrintStream.
   * @param out target for pretty printing
   * @aspect PrettyPrintUtil
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\PrettyPrintUtil.jrag:51
   */
  public void prettyPrint(PrintStream out) {
    prettyPrint(new PrettyPrinter("  ", out));
  }
  /**
   * @return the name of the class implementing this AST node
   * @aspect PrettyPrintUtil
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\PrettyPrintUtil.jrag:58
   */
  public String toString() {
    return getClass().getName();
  }
  /**
   * @aspect PrettyPrintUtil
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\PrettyPrintUtil.jrag:62
   */
  public void prettyPrint(PrettyPrinter out) {
  }
  /**
   * @aspect PrimitiveTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\PrimitiveTypes.jrag:32
   */
  protected static final String PRIMITIVE_PACKAGE_NAME = "@primitive";
  /**
   * @aspect TypeCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeCheck.jrag:33
   */
  public void typeCheck() {
  }
  /**
   * @aspect UnreachableStatements
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\UnreachableStatements.jrag:33
   */
  void checkUnreachableStmt() {
  }
  /**
   * @aspect VariableDeclarationTransformation
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\VariableDeclaration.jrag:154
   */
  public void clearLocations() {
    setStart(0);
    setEnd(0);
    for (int i = 0; i < getNumChildNoTransform(); i++) {
      getChildNoTransform(i).clearLocations();
    }
  }
  /**
   * @aspect Enums
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Enums.jrag:159
   */
  protected void transformEnumConstructors() {
    for (int i = 0; i < getNumChildNoTransform(); i++) {
      ASTNode child = getChildNoTransform(i);
      if (child != null) {
        child.transformEnumConstructors();
      }
    }
  }
  /**
   * @aspect Enums
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Enums.jrag:490
   */
  protected void checkEnum(EnumDecl enumDecl) {
    for (int i = 0; i < getNumChild(); i++) {
      getChild(i).checkEnum(enumDecl);
    }
  }
  /**
   * Checking of the SafeVarargs annotation is only needed for method
   * declarations.
   * @aspect Warnings
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Warnings.jadd:60
   */
  public void checkWarnings() {
	}
  /**
   * @aspect UncheckedConversion
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\UncheckedConversion.jrag:65
   */
  public void checkUncheckedConversion(TypeDecl source, TypeDecl dest) {
    if (source.isUncheckedConversionTo(dest)) {
      warning("unchecked conversion from raw type " + source.typeName()
          + " to generic type " + dest.typeName());
    }
  }
  /**
   * @declaredat ASTNode:1
   */
  public ASTNode() {
    super();
    init$Children();
  }
  /**
   * Initializes the child array to the correct size.
   * Initializes List and Opt nta children.
   * @apilevel internal
   * @ast method
   * @declaredat ASTNode:11
   */
  public void init$Children() {
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:16
   */
  private int childIndex;
  /**
   * @apilevel low-level
   * @declaredat ASTNode:21
   */
  public int getIndexOfChild(ASTNode node) {
    if (node == null) {
      return -1;
    }
    if (node.childIndex < numChildren && node == children[node.childIndex]) {
      return node.childIndex;
    }
    for(int i = 0; children != null && i < children.length; i++) {
      if (children[i] == node) {
        node.childIndex = i;
        return i;
      }
    }
    return -1;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:40
   */
  public static final boolean generatedWithCacheCycle = false;
  /**
   * @apilevel internal
   * @declaredat ASTNode:44
   */
  public static final boolean generatedWithComponentCheck = false;
  /**
   * Parent pointer
   * @apilevel low-level
   * @declaredat ASTNode:50
   */
  protected ASTNode parent;
  /**
   * Child array
   * @apilevel low-level
   * @declaredat ASTNode:56
   */
  protected ASTNode[] children;
  /**
   * @apilevel internal
   * @declaredat ASTNode:62
   */
  private static ASTNode$State state = new ASTNode$State();
  /**
   * @apilevel internal
   * @declaredat ASTNode:67
   */
  public final ASTNode$State state() {
    return state;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:74
   */
  public boolean in$Circle = false;
  /**
   * @apilevel internal
   * @declaredat ASTNode:79
   */
  public boolean in$Circle() {
    return in$Circle;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:86
   */
  public void in$Circle(boolean b) {
    in$Circle = b;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:93
   */
  public boolean is$Final = false;
  /**
   * @apilevel internal
   * @declaredat ASTNode:97
   */
  public boolean is$Final() { return is$Final; }
  /**
   * @apilevel internal
   * @declaredat ASTNode:101
   */
  public void is$Final(boolean b) { is$Final = b; }
  /**
   * @apilevel low-level
   * @declaredat ASTNode:106
   */
  public T getChild(int i) {

    ASTNode node = this.getChildNoTransform(i);
    if (node == null) {
      return null;
    }
    if (node.is$Final()) {
      return (T) node;
    }
    if (!node.mayHaveRewrite()) {
      node.is$Final(this.is$Final());
      return (T) node;
    }
    if (!node.in$Circle()) {
      int rewriteState;
      int num = state().boundariesCrossed;
      do {
        state().push(ASTNode$State.REWRITE_CHANGE);
        ASTNode oldNode = node;
        oldNode.in$Circle(true);
        node = node.rewriteTo();
        if (node != oldNode) {
          this.setChild(node, i);
        }
        oldNode.in$Circle(false);
        rewriteState = state().pop();
      } while(rewriteState == ASTNode$State.REWRITE_CHANGE);
      if (rewriteState == ASTNode$State.REWRITE_NOCHANGE && this.is$Final()) {
        node.is$Final(true);
        state().boundariesCrossed = num;
      } else {
      }
    } else if (this.is$Final() != node.is$Final()) {
      state().boundariesCrossed++;
    } else {
    }
    return (T) node;


  }
  /**
   * @apilevel low-level
   * @declaredat ASTNode:149
   */
  public void addChild(T node) {
    setChild(node, getNumChildNoTransform());
  }
  /**
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @apilevel low-level
   * @declaredat ASTNode:156
   */
  public final T getChildNoTransform(int i) {
    if (children == null) {
      return null;
    }
    T child = (T)children[i];
    return child;
  }
  /**
   * @apilevel low-level
   * @declaredat ASTNode:166
   */
  protected int numChildren;
  /**
   * @apilevel low-level
   * @declaredat ASTNode:171
   */
  protected int numChildren() {
    return numChildren;
  }
  /**
   * @apilevel low-level
   * @declaredat ASTNode:178
   */
  public int getNumChild() {
    return numChildren();
  }
  /**
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @apilevel low-level
   * @declaredat ASTNode:186
   */
  public final int getNumChildNoTransform() {
    return numChildren();
  }
  /**
   * @apilevel low-level
   * @declaredat ASTNode:192
   */
  public void setChild(ASTNode node, int i) {
    if (children == null) {
      children = new ASTNode[(i+1>4 || !(this instanceof List))?i+1:4];
    } else if (i >= children.length) {
      ASTNode c[] = new ASTNode[i << 1];
      System.arraycopy(children, 0, c, 0, children.length);
      children = c;
    }
    children[i] = node;
    if (i >= numChildren) {
      numChildren = i+1;
    }
    if (node != null) {
      node.setParent(this);
      node.childIndex = i;
    }
  }
  /**
   * @apilevel low-level
   * @declaredat ASTNode:212
   */
  public void insertChild(ASTNode node, int i) {
    if (children == null) {
      children = new ASTNode[(i+1>4 || !(this instanceof List))?i+1:4];
      children[i] = node;
    } else {
      ASTNode c[] = new ASTNode[children.length + 1];
      System.arraycopy(children, 0, c, 0, i);
      c[i] = node;
      if (i < children.length) {
        System.arraycopy(children, i, c, i+1, children.length-i);
        for(int j = i+1; j < c.length; ++j) {
          if (c[j] != null) {
            c[j].childIndex = j;
          }
        }
      }
      children = c;
    }
    numChildren++;
    if (node != null) {
      node.setParent(this);
      node.childIndex = i;
    }
  }
  /**
   * @apilevel low-level
   * @declaredat ASTNode:239
   */
  public void removeChild(int i) {
    if (children != null) {
      ASTNode child = (ASTNode) children[i];
      if (child != null) {
        child.parent = null;
        child.childIndex = -1;
      }
      // Adding a check of this instance to make sure its a List, a move of children doesn't make
      // any sense for a node unless its a list. Also, there is a problem if a child of a non-List node is removed
      // and siblings are moved one step to the right, with null at the end.
      if (this instanceof List || this instanceof Opt) {
        System.arraycopy(children, i+1, children, i, children.length-i-1);
        children[children.length-1] = null;
        numChildren--;
        // fix child indices
        for(int j = i; j < numChildren; ++j) {
          if (children[j] != null) {
            child = (ASTNode) children[j];
            child.childIndex = j;
          }
        }
      } else {
        children[i] = null;
      }
    }
  }
  /**
   * @apilevel low-level
   * @declaredat ASTNode:268
   */
  public ASTNode getParent() {
    if (parent != null && ((ASTNode) parent).is$Final() != is$Final()) {
      state().boundariesCrossed++;
    }
    return (ASTNode) parent;
  }
  /**
   * @apilevel low-level
   * @declaredat ASTNode:277
   */
  public void setParent(ASTNode node) {
    parent = node;
  }
  /**
   * @apilevel low-level
   * @declaredat ASTNode:328
   */
  public java.util.Iterator<T> iterator() {
    return new java.util.Iterator<T>() {
      private int counter = 0;
      public boolean hasNext() {
        return counter < getNumChild();
      }
      public T next() {
        if (hasNext())
          return (T)getChild(counter++);
        else
          return null;
      }
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:348
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /**
   * @apilevel low-level
   * @declaredat ASTNode:354
   */
  public void flushTreeCache() {
    flushCache();
    if (children == null) {
      return;
    }
    for (int i = 0; i < children.length; i++) {
      if (children[i] != null && ((ASTNode)children[i]).is$Final) {
        ((ASTNode)children[i]).flushTreeCache();
      }
    }
  }
  /**
   * @apilevel low-level
   * @declaredat ASTNode:368
   */
  public void flushCache() {
    flushAttrAndCollectionCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:374
   */
  public void flushAttrAndCollectionCache() {
    flushAttrCache();
    flushCollectionCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:381
   */
  public void flushAttrCache() {
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:386
   */
  public void flushCollectionCache() {
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:391
   */
  public void flushRewriteCache() {
    in$Circle(false);
    is$Final(false);
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:398
   */
  public ASTNode<T> clone() throws CloneNotSupportedException {
    ASTNode node = (ASTNode) super.clone();
    if (node.is$Final()) {
      node.flushAttrAndCollectionCache();
    }
    node.in$Circle(false);
    // flush rewrites
    node.is$Final(false);
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:411
   */
  public ASTNode<T> copy() {
    try {
      ASTNode node = (ASTNode) clone();
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
   * @declaredat ASTNode:430
   */
  @Deprecated
  public ASTNode<T> fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:440
   */
  public ASTNode<T> treeCopyNoTransform() {
    ASTNode tree = (ASTNode) copy();
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
   * @declaredat ASTNode:460
   */
  public ASTNode<T> treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * Performs a full traversal of the tree using getChild to trigger rewrites
   * @apilevel low-level
   * @declaredat ASTNode:468
   */
  public void doFullTraversal() {
    for (int i = 0; i < getNumChild(); i++) {
      getChild(i).doFullTraversal();
    }
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:476
   */
  protected boolean is$Equal(ASTNode n1, ASTNode n2) {
    if (n1 == null && n2 == null) return true;
    if (n1 == null || n2 == null) return false;
    return n1.is$Equal(n2);
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:484
   */
  protected boolean is$Equal(ASTNode node) {
    if (getClass() != node.getClass()) {
      return false;
    }
    if (numChildren != node.numChildren) {
      return false;
    }
    for (int i = 0; i < numChildren; i++) {
      if (children[i] == null && node.children[i] != null) {
        return false;
      }
      if (!((ASTNode)children[i]).is$Equal(((ASTNode)node.children[i]))) {
        return false;
      }
    }
    return true;
  }
  /**
   * The collectErrors method is refined so that it calls
   * the checkWarnings method on each ASTNode to report
   * unchecked warnings.
   * @aspect Warnings
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Warnings.jadd:42
   */
    public void collectErrors() {
		nameCheck();
		typeCheck();
		accessControl();
		exceptionHandling();
		checkUnreachableStmt();
		definiteAssignment();
		checkModifiers();
		checkWarnings();
		for(int i = 0; i < getNumChild(); i++) {
			getChild(i).collectErrors();
		}
	}
  /**
   * @aspect <NoAspect>
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\LambdaBody.jrag:47
   */
    protected void collect_contributors_BlockLambdaBody_lambdaReturns() {
    for(int i = 0; i < getNumChild(); i++) {
      getChild(i).collect_contributors_BlockLambdaBody_lambdaReturns();
    }
  }
  protected void contributeTo_BlockLambdaBody_BlockLambdaBody_lambdaReturns(ArrayList<ReturnStmt> collection) {
  }

  /**
   * @attribute syn
   * @aspect ErrorCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ErrorCheck.jrag:45
   */
  @ASTNodeAnnotation.Attribute
  public int lineNumber() {
    {
        ASTNode n = this;
        while (n.getParent() != null && n.getStart() == 0) {
          n = n.getParent();
        }
        return getLine(n.getStart());
      }
  }
  /**
   * @attribute syn
   * @aspect LookupParTypeDecl
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:1240
   */
  @ASTNodeAnnotation.Attribute
  public boolean usesTypeVariable() {
    {
        for (int i = 0; i < getNumChild(); i++) {
          if (getChild(i).usesTypeVariable()) {
            return true;
          }
        }
        return false;
      }
  }
  /**
   * @attribute inh
   * @aspect AddOptionsToProgram
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\Options.jadd:39
   */
  @ASTNodeAnnotation.Attribute
  public Program program() {
    Program program_value = getParent().Define_program(this, null);

    return program_value;
  }
  /** @return the enclosing compilation unit. 
   * @attribute inh
   * @aspect ClassPath
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ClassPath.jrag:79
   */
  @ASTNodeAnnotation.Attribute
  public CompilationUnit compilationUnit() {
    CompilationUnit compilationUnit_value = getParent().Define_compilationUnit(this, null);

    return compilationUnit_value;
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    if (state().peek() == ASTNode$State.REWRITE_CHANGE) {
      state().pop();
      state().push(ASTNode$State.REWRITE_NOCHANGE);
    }
    return this;
  }
  /**
   * @apilevel internal
   */
  public Program Define_program(ASTNode caller, ASTNode child) {
    return getParent().Define_program(this, caller);
  }
  /**
   * @apilevel internal
   */
  public TypeDecl Define_superType(ASTNode caller, ASTNode child) {
    return getParent().Define_superType(this, caller);
  }
  /**
   * @apilevel internal
   */
  public ConstructorDecl Define_constructorDecl(ASTNode caller, ASTNode child) {
    return getParent().Define_constructorDecl(this, caller);
  }
  /**
   * @apilevel internal
   */
  public TypeDecl Define_componentType(ASTNode caller, ASTNode child) {
    return getParent().Define_componentType(this, caller);
  }
  /**
   * @apilevel internal
   */
  public LabeledStmt Define_lookupLabel(ASTNode caller, ASTNode child, String name) {
    return getParent().Define_lookupLabel(this, caller, name);
  }
  /**
   * @apilevel internal
   */
  public CompilationUnit Define_compilationUnit(ASTNode caller, ASTNode child) {
    return getParent().Define_compilationUnit(this, caller);
  }
  /**
   * @apilevel internal
   */
  public int Define_blockIndex(ASTNode caller, ASTNode child) {
    return getParent().Define_blockIndex(this, caller);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_isDest(ASTNode caller, ASTNode child) {
    return getParent().Define_isDest(this, caller);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_isSource(ASTNode caller, ASTNode child) {
    return getParent().Define_isSource(this, caller);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_isIncOrDec(ASTNode caller, ASTNode child) {
    return getParent().Define_isIncOrDec(this, caller);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_isDAbefore(ASTNode caller, ASTNode child, Variable v) {
    return getParent().Define_isDAbefore(this, caller, v);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_isDUbefore(ASTNode caller, ASTNode child, Variable v) {
    return getParent().Define_isDUbefore(this, caller, v);
  }
  /**
   * @apilevel internal
   */
  public TypeDecl Define_typeException(ASTNode caller, ASTNode child) {
    return getParent().Define_typeException(this, caller);
  }
  /**
   * @apilevel internal
   */
  public TypeDecl Define_typeRuntimeException(ASTNode caller, ASTNode child) {
    return getParent().Define_typeRuntimeException(this, caller);
  }
  /**
   * @apilevel internal
   */
  public TypeDecl Define_typeError(ASTNode caller, ASTNode child) {
    return getParent().Define_typeError(this, caller);
  }
  /**
   * @apilevel internal
   */
  public TypeDecl Define_typeNullPointerException(ASTNode caller, ASTNode child) {
    return getParent().Define_typeNullPointerException(this, caller);
  }
  /**
   * @apilevel internal
   */
  public TypeDecl Define_typeThrowable(ASTNode caller, ASTNode child) {
    return getParent().Define_typeThrowable(this, caller);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_handlesException(ASTNode caller, ASTNode child, TypeDecl exceptionType) {
    return getParent().Define_handlesException(this, caller, exceptionType);
  }
  /**
   * @apilevel internal
   */
  public Collection Define_lookupConstructor(ASTNode caller, ASTNode child) {
    return getParent().Define_lookupConstructor(this, caller);
  }
  /**
   * @apilevel internal
   */
  public Collection Define_lookupSuperConstructor(ASTNode caller, ASTNode child) {
    return getParent().Define_lookupSuperConstructor(this, caller);
  }
  /**
   * @apilevel internal
   */
  public Expr Define_nestedScope(ASTNode caller, ASTNode child) {
    return getParent().Define_nestedScope(this, caller);
  }
  /**
   * @apilevel internal
   */
  public Collection Define_lookupMethod(ASTNode caller, ASTNode child, String name) {
    return getParent().Define_lookupMethod(this, caller, name);
  }
  /**
   * @apilevel internal
   */
  public TypeDecl Define_typeObject(ASTNode caller, ASTNode child) {
    return getParent().Define_typeObject(this, caller);
  }
  /**
   * @apilevel internal
   */
  public TypeDecl Define_typeCloneable(ASTNode caller, ASTNode child) {
    return getParent().Define_typeCloneable(this, caller);
  }
  /**
   * @apilevel internal
   */
  public TypeDecl Define_typeSerializable(ASTNode caller, ASTNode child) {
    return getParent().Define_typeSerializable(this, caller);
  }
  /**
   * @apilevel internal
   */
  public TypeDecl Define_typeBoolean(ASTNode caller, ASTNode child) {
    return getParent().Define_typeBoolean(this, caller);
  }
  /**
   * @apilevel internal
   */
  public TypeDecl Define_typeByte(ASTNode caller, ASTNode child) {
    return getParent().Define_typeByte(this, caller);
  }
  /**
   * @apilevel internal
   */
  public TypeDecl Define_typeShort(ASTNode caller, ASTNode child) {
    return getParent().Define_typeShort(this, caller);
  }
  /**
   * @apilevel internal
   */
  public TypeDecl Define_typeChar(ASTNode caller, ASTNode child) {
    return getParent().Define_typeChar(this, caller);
  }
  /**
   * @apilevel internal
   */
  public TypeDecl Define_typeInt(ASTNode caller, ASTNode child) {
    return getParent().Define_typeInt(this, caller);
  }
  /**
   * @apilevel internal
   */
  public TypeDecl Define_typeLong(ASTNode caller, ASTNode child) {
    return getParent().Define_typeLong(this, caller);
  }
  /**
   * @apilevel internal
   */
  public TypeDecl Define_typeFloat(ASTNode caller, ASTNode child) {
    return getParent().Define_typeFloat(this, caller);
  }
  /**
   * @apilevel internal
   */
  public TypeDecl Define_typeDouble(ASTNode caller, ASTNode child) {
    return getParent().Define_typeDouble(this, caller);
  }
  /**
   * @apilevel internal
   */
  public TypeDecl Define_typeString(ASTNode caller, ASTNode child) {
    return getParent().Define_typeString(this, caller);
  }
  /**
   * @apilevel internal
   */
  public TypeDecl Define_typeVoid(ASTNode caller, ASTNode child) {
    return getParent().Define_typeVoid(this, caller);
  }
  /**
   * @apilevel internal
   */
  public TypeDecl Define_typeNull(ASTNode caller, ASTNode child) {
    return getParent().Define_typeNull(this, caller);
  }
  /**
   * @apilevel internal
   */
  public TypeDecl Define_unknownType(ASTNode caller, ASTNode child) {
    return getParent().Define_unknownType(this, caller);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_hasPackage(ASTNode caller, ASTNode child, String packageName) {
    return getParent().Define_hasPackage(this, caller, packageName);
  }
  /**
   * @apilevel internal
   */
  public TypeDecl Define_lookupType(ASTNode caller, ASTNode child, String packageName, String typeName) {
    return getParent().Define_lookupType(this, caller, packageName, typeName);
  }
  /**
   * @apilevel internal
   */
  public SimpleSet Define_lookupType(ASTNode caller, ASTNode child, String name) {
    return getParent().Define_lookupType(this, caller, name);
  }
  /**
   * @apilevel internal
   */
  public SimpleSet Define_lookupVariable(ASTNode caller, ASTNode child, String name) {
    return getParent().Define_lookupVariable(this, caller, name);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_mayBePublic(ASTNode caller, ASTNode child) {
    return getParent().Define_mayBePublic(this, caller);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_mayBeProtected(ASTNode caller, ASTNode child) {
    return getParent().Define_mayBeProtected(this, caller);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_mayBePrivate(ASTNode caller, ASTNode child) {
    return getParent().Define_mayBePrivate(this, caller);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_mayBeStatic(ASTNode caller, ASTNode child) {
    return getParent().Define_mayBeStatic(this, caller);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_mayBeFinal(ASTNode caller, ASTNode child) {
    return getParent().Define_mayBeFinal(this, caller);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_mayBeAbstract(ASTNode caller, ASTNode child) {
    return getParent().Define_mayBeAbstract(this, caller);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_mayBeVolatile(ASTNode caller, ASTNode child) {
    return getParent().Define_mayBeVolatile(this, caller);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_mayBeTransient(ASTNode caller, ASTNode child) {
    return getParent().Define_mayBeTransient(this, caller);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_mayBeStrictfp(ASTNode caller, ASTNode child) {
    return getParent().Define_mayBeStrictfp(this, caller);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_mayBeSynchronized(ASTNode caller, ASTNode child) {
    return getParent().Define_mayBeSynchronized(this, caller);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_mayBeNative(ASTNode caller, ASTNode child) {
    return getParent().Define_mayBeNative(this, caller);
  }
  /**
   * @apilevel internal
   */
  public ASTNode Define_enclosingBlock(ASTNode caller, ASTNode child) {
    return getParent().Define_enclosingBlock(this, caller);
  }
  /**
   * @apilevel internal
   */
  public VariableScope Define_outerScope(ASTNode caller, ASTNode child) {
    return getParent().Define_outerScope(this, caller);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_insideLoop(ASTNode caller, ASTNode child) {
    return getParent().Define_insideLoop(this, caller);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_insideSwitch(ASTNode caller, ASTNode child) {
    return getParent().Define_insideSwitch(this, caller);
  }
  /**
   * @apilevel internal
   */
  public Case Define_bind(ASTNode caller, ASTNode child, Case c) {
    return getParent().Define_bind(this, caller, c);
  }
  /**
   * @apilevel internal
   */
  public NameType Define_nameType(ASTNode caller, ASTNode child) {
    return getParent().Define_nameType(this, caller);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_isAnonymous(ASTNode caller, ASTNode child) {
    return getParent().Define_isAnonymous(this, caller);
  }
  /**
   * @apilevel internal
   */
  public Variable Define_unknownField(ASTNode caller, ASTNode child) {
    return getParent().Define_unknownField(this, caller);
  }
  /**
   * @apilevel internal
   */
  public MethodDecl Define_unknownMethod(ASTNode caller, ASTNode child) {
    return getParent().Define_unknownMethod(this, caller);
  }
  /**
   * @apilevel internal
   */
  public ConstructorDecl Define_unknownConstructor(ASTNode caller, ASTNode child) {
    return getParent().Define_unknownConstructor(this, caller);
  }
  /**
   * @apilevel internal
   */
  public TypeDecl Define_declType(ASTNode caller, ASTNode child) {
    return getParent().Define_declType(this, caller);
  }
  /**
   * @apilevel internal
   */
  public BodyDecl Define_enclosingBodyDecl(ASTNode caller, ASTNode child) {
    return getParent().Define_enclosingBodyDecl(this, caller);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_isMemberType(ASTNode caller, ASTNode child) {
    return getParent().Define_isMemberType(this, caller);
  }
  /**
   * @apilevel internal
   */
  public TypeDecl Define_hostType(ASTNode caller, ASTNode child) {
    return getParent().Define_hostType(this, caller);
  }
  /**
   * @apilevel internal
   */
  public TypeDecl Define_switchType(ASTNode caller, ASTNode child) {
    return getParent().Define_switchType(this, caller);
  }
  /**
   * @apilevel internal
   */
  public TypeDecl Define_returnType(ASTNode caller, ASTNode child) {
    return getParent().Define_returnType(this, caller);
  }
  /**
   * @apilevel internal
   */
  public TypeDecl Define_enclosingInstance(ASTNode caller, ASTNode child) {
    return getParent().Define_enclosingInstance(this, caller);
  }
  /**
   * @apilevel internal
   */
  public String Define_methodHost(ASTNode caller, ASTNode child) {
    return getParent().Define_methodHost(this, caller);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_inExplicitConstructorInvocation(ASTNode caller, ASTNode child) {
    return getParent().Define_inExplicitConstructorInvocation(this, caller);
  }
  /**
   * @apilevel internal
   */
  public TypeDecl Define_enclosingExplicitConstructorHostType(ASTNode caller, ASTNode child) {
    return getParent().Define_enclosingExplicitConstructorHostType(this, caller);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_inStaticContext(ASTNode caller, ASTNode child) {
    return getParent().Define_inStaticContext(this, caller);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_reportUnreachable(ASTNode caller, ASTNode child) {
    return getParent().Define_reportUnreachable(this, caller);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_isMethodParameter(ASTNode caller, ASTNode child) {
    return getParent().Define_isMethodParameter(this, caller);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_isConstructorParameter(ASTNode caller, ASTNode child) {
    return getParent().Define_isConstructorParameter(this, caller);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_isExceptionHandlerParameter(ASTNode caller, ASTNode child) {
    return getParent().Define_isExceptionHandlerParameter(this, caller);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_mayUseAnnotationTarget(ASTNode caller, ASTNode child, String name) {
    return getParent().Define_mayUseAnnotationTarget(this, caller, name);
  }
  /**
   * @apilevel internal
   */
  public ElementValue Define_lookupElementTypeValue(ASTNode caller, ASTNode child, String name) {
    return getParent().Define_lookupElementTypeValue(this, caller, name);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_withinSuppressWarnings(ASTNode caller, ASTNode child, String annot) {
    return getParent().Define_withinSuppressWarnings(this, caller, annot);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_withinDeprecatedAnnotation(ASTNode caller, ASTNode child) {
    return getParent().Define_withinDeprecatedAnnotation(this, caller);
  }
  /**
   * @apilevel internal
   */
  public Annotation Define_lookupAnnotation(ASTNode caller, ASTNode child, TypeDecl typeDecl) {
    return getParent().Define_lookupAnnotation(this, caller, typeDecl);
  }
  /**
   * @apilevel internal
   */
  public TypeDecl Define_enclosingAnnotationDecl(ASTNode caller, ASTNode child) {
    return getParent().Define_enclosingAnnotationDecl(this, caller);
  }
  /**
   * @apilevel internal
   */
  public TypeDecl Define_assignConvertedType(ASTNode caller, ASTNode child) {
    return getParent().Define_assignConvertedType(this, caller);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_inExtendsOrImplements(ASTNode caller, ASTNode child) {
    return getParent().Define_inExtendsOrImplements(this, caller);
  }
  /**
   * @apilevel internal
   */
  public TypeDecl Define_typeWildcard(ASTNode caller, ASTNode child) {
    return getParent().Define_typeWildcard(this, caller);
  }
  /**
   * @apilevel internal
   */
  public TypeDecl Define_lookupWildcardExtends(ASTNode caller, ASTNode child, TypeDecl typeDecl) {
    return getParent().Define_lookupWildcardExtends(this, caller, typeDecl);
  }
  /**
   * @apilevel internal
   */
  public TypeDecl Define_lookupWildcardSuper(ASTNode caller, ASTNode child, TypeDecl typeDecl) {
    return getParent().Define_lookupWildcardSuper(this, caller, typeDecl);
  }
  /**
   * @apilevel internal
   */
  public LUBType Define_lookupLUBType(ASTNode caller, ASTNode child, Collection bounds) {
    return getParent().Define_lookupLUBType(this, caller, bounds);
  }
  /**
   * @apilevel internal
   */
  public GLBType Define_lookupGLBType(ASTNode caller, ASTNode child, ArrayList bounds) {
    return getParent().Define_lookupGLBType(this, caller, bounds);
  }
  /**
   * @apilevel internal
   */
  public TypeDecl Define_genericDecl(ASTNode caller, ASTNode child) {
    return getParent().Define_genericDecl(this, caller);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_variableArityValid(ASTNode caller, ASTNode child) {
    return getParent().Define_variableArityValid(this, caller);
  }
  /**
   * @apilevel internal
   */
  public ClassInstanceExpr Define_getClassInstanceExpr(ASTNode caller, ASTNode child) {
    return getParent().Define_getClassInstanceExpr(this, caller);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_isAnonymousDecl(ASTNode caller, ASTNode child) {
    return getParent().Define_isAnonymousDecl(this, caller);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_isExplicitGenericConstructorAccess(ASTNode caller, ASTNode child) {
    return getParent().Define_isExplicitGenericConstructorAccess(this, caller);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_isCatchParam(ASTNode caller, ASTNode child) {
    return getParent().Define_isCatchParam(this, caller);
  }
  /**
   * @apilevel internal
   */
  public CatchClause Define_catchClause(ASTNode caller, ASTNode child) {
    return getParent().Define_catchClause(this, caller);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_resourcePreviouslyDeclared(ASTNode caller, ASTNode child, String name) {
    return getParent().Define_resourcePreviouslyDeclared(this, caller, name);
  }
  /**
   * @apilevel internal
   */
  public TypeDecl Define_targetType(ASTNode caller, ASTNode child) {
    return getParent().Define_targetType(this, caller);
  }
  /**
   * @apilevel internal
   */
  public SimpleSet Define_allImportedTypes(ASTNode caller, ASTNode child, String name) {
    return getParent().Define_allImportedTypes(this, caller, name);
  }
  /**
   * @apilevel internal
   */
  public String Define_packageName(ASTNode caller, ASTNode child) {
    return getParent().Define_packageName(this, caller);
  }
  /**
   * @apilevel internal
   */
  public TypeDecl Define_enclosingType(ASTNode caller, ASTNode child) {
    return getParent().Define_enclosingType(this, caller);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_isNestedType(ASTNode caller, ASTNode child) {
    return getParent().Define_isNestedType(this, caller);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_isLocalClass(ASTNode caller, ASTNode child) {
    return getParent().Define_isLocalClass(this, caller);
  }
  /**
   * @apilevel internal
   */
  public String Define_hostPackage(ASTNode caller, ASTNode child) {
    return getParent().Define_hostPackage(this, caller);
  }
  /**
   * @apilevel internal
   */
  public LambdaExpr Define_enclosingLambda(ASTNode caller, ASTNode child) {
    return getParent().Define_enclosingLambda(this, caller);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_assignmentContext(ASTNode caller, ASTNode child) {
    return getParent().Define_assignmentContext(this, caller);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_invocationContext(ASTNode caller, ASTNode child) {
    return getParent().Define_invocationContext(this, caller);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_castContext(ASTNode caller, ASTNode child) {
    return getParent().Define_castContext(this, caller);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_stringContext(ASTNode caller, ASTNode child) {
    return getParent().Define_stringContext(this, caller);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_numericContext(ASTNode caller, ASTNode child) {
    return getParent().Define_numericContext(this, caller);
  }
  /**
   * @apilevel internal
   */
  public int Define_typeVarPosition(ASTNode caller, ASTNode child) {
    return getParent().Define_typeVarPosition(this, caller);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_typeVarInMethod(ASTNode caller, ASTNode child) {
    return getParent().Define_typeVarInMethod(this, caller);
  }
  /**
   * @apilevel internal
   */
  public int Define_genericMethodLevel(ASTNode caller, ASTNode child) {
    return getParent().Define_genericMethodLevel(this, caller);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_isDAbefore(ASTNode caller, ASTNode child, Variable v, BodyDecl b) {
    return getParent().Define_isDAbefore(this, caller, v, b);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_isDUbefore(ASTNode caller, ASTNode child, Variable v, BodyDecl b) {
    return getParent().Define_isDUbefore(this, caller, v, b);
  }
  /**
   * @apilevel internal
   */
  public Stmt Define_branchTarget(ASTNode caller, ASTNode child, Stmt branch) {
    return getParent().Define_branchTarget(this, caller, branch);
  }
  /**
   * @apilevel internal
   */
  public FinallyHost Define_enclosingFinally(ASTNode caller, ASTNode child, Stmt branch) {
    return getParent().Define_enclosingFinally(this, caller, branch);
  }
  /**
   * @apilevel internal
   */
  public SimpleSet Define_otherLocalClassDecls(ASTNode caller, ASTNode child, String name) {
    return getParent().Define_otherLocalClassDecls(this, caller, name);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_reachable(ASTNode caller, ASTNode child) {
    return getParent().Define_reachable(this, caller);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_inhModifiedInScope(ASTNode caller, ASTNode child, Variable var) {
    return getParent().Define_inhModifiedInScope(this, caller, var);
  }
  /**
   * @apilevel internal
   */
  public boolean Define_reachableCatchClause(ASTNode caller, ASTNode child, TypeDecl exceptionType) {
    return getParent().Define_reachableCatchClause(this, caller, exceptionType);
  }
  /**
   * @apilevel internal
   */
  public Collection<TypeDecl> Define_caughtExceptions(ASTNode caller, ASTNode child) {
    return getParent().Define_caughtExceptions(this, caller);
  }
  /**
   * @apilevel internal
   */
  public TypeDecl Define_inferredType(ASTNode caller, ASTNode child) {
    return getParent().Define_inferredType(this, caller);
  }
}
