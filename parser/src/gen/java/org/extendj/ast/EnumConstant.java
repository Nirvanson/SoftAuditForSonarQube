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
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\grammar\\Enums.ast:3
 * @production EnumConstant : {@link FieldDeclaration} ::= <span class="component">{@link Modifiers}</span> <span class="component">&lt;ID:String&gt;</span> <span class="component">Arg:{@link Expr}*</span> <span class="component">[Init:{@link Expr}]</span> <span class="component">TypeAccess:{@link Access}</span>;

 */
public class EnumConstant extends FieldDeclaration implements Cloneable {
  /**
   * @aspect Enums
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Enums.jrag:229
   */
  public EnumConstant(Modifiers mods, String name, List<Expr> args, List<BodyDecl> bds) {
    this(mods, name, args, new Opt<Expr>(new EnumInstanceExpr(createOptAnonymousDecl(bds))));
  }
  /**
   * @aspect Enums
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Enums.jrag:270
   */
  private static Opt<TypeDecl> createOptAnonymousDecl(List<BodyDecl> bds) {
    if (bds.getNumChildNoTransform() == 0) {
      return new Opt<TypeDecl>();
    }
    return new Opt<TypeDecl>(
      new AnonymousDecl(
        new Modifiers(),
        "Anonymous",
        bds
      )
    );
  }
  /**
   * @aspect Enums
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Enums.jrag:284
   */
  public int getNumBodyDecl() {
    int cnt = 0;
    ClassInstanceExpr init = (ClassInstanceExpr) getInit();
    if (!init.hasTypeDecl()) {
      return 0;
    }
    for (BodyDecl bd : init.getTypeDecl().getBodyDecls()) {
      if (!(bd instanceof ConstructorDecl)) {
        ++cnt;
      }
    }
    return cnt;
  }
  /**
   * @aspect Enums
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Enums.jrag:298
   */
  public BodyDecl getBodyDecl(int i) {
    ClassInstanceExpr init = (ClassInstanceExpr) getInit();
    if (init.hasTypeDecl()) {
      for (BodyDecl bd : init.getTypeDecl().getBodyDecls()) {
        if (!(bd instanceof ConstructorDecl)) {
          if (i-- == 0) {
            return bd;
          }
        }
      }
    }
    throw new ArrayIndexOutOfBoundsException(i);
  }
  /**
   * @aspect Enums
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Enums.jrag:635
   */
  public void prettyPrint(PrettyPrinter out) {
    out.println();
    out.print(getModifiers());
    out.print(getID());
    out.print("(");
    if (getNumArg() > 0) {
      out.join(getArgList(), new PrettyPrinter.Joiner() {
        @Override
        public void printSeparator(PrettyPrinter out) {
          out.print(", ");
        }
      });
    }
    out.print(")");
    if (getNumBodyDecl() > 0) {
      out.print(" {");
      for (int i=0; i < getNumBodyDecl(); i++) {
        BodyDecl d = getBodyDecl(i);
        out.print(d);
      }
      out.print("}");
    }
    out.print(",");
  }
  /**
   * @declaredat ASTNode:1
   */
  public EnumConstant() {
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
    children = new ASTNode[4];
    setChild(new List(), 1);
    setChild(new Opt(), 2);
  }
  /**
   * @declaredat ASTNode:15
   */
  public EnumConstant(Modifiers p0, String p1, List<Expr> p2, Opt<Expr> p3) {
    setChild(p0, 0);
    setID(p1);
    setChild(p2, 1);
    setChild(p3, 2);
  }
  /**
   * @declaredat ASTNode:21
   */
  public EnumConstant(Modifiers p0, beaver.Symbol p1, List<Expr> p2, Opt<Expr> p3) {
    setChild(p0, 0);
    setID(p1);
    setChild(p2, 1);
    setChild(p3, 2);
  }
  /**
   * @apilevel low-level
   * @declaredat ASTNode:30
   */
  protected int numChildren() {
    return 3;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:36
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:42
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    getTypeAccess_reset();
    localMethodsSignatureMap_reset();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:50
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:56
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:62
   */
  public EnumConstant clone() throws CloneNotSupportedException {
    EnumConstant node = (EnumConstant) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:69
   */
  public EnumConstant copy() {
    try {
      EnumConstant node = (EnumConstant) clone();
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
   * @declaredat ASTNode:88
   */
  @Deprecated
  public EnumConstant fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:98
   */
  public EnumConstant treeCopyNoTransform() {
    EnumConstant tree = (EnumConstant) copy();
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        switch (i) {
        case 3:
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
   * @declaredat ASTNode:123
   */
  public EnumConstant treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:130
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenString_ID == ((EnumConstant)node).tokenString_ID);    
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
   * Replaces the lexeme ID.
   * @param value The new value for the lexeme ID.
   * @apilevel high-level
   */
  public void setID(String value) {
    tokenString_ID = value;
  }
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
    setChild(list, 1);
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
    List<Expr> list = (List<Expr>) getChild(1);
    return list;
  }
  /**
   * Retrieves the Arg list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the Arg list.
   * @apilevel low-level
   */
  public List<Expr> getArgListNoTransform() {
    return (List<Expr>) getChildNoTransform(1);
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
   * Replaces the optional node for the Init child. This is the <code>Opt</code>
   * node containing the child Init, not the actual child!
   * @param opt The new node to be used as the optional node for the Init child.
   * @apilevel low-level
   */
  public void setInitOpt(Opt<Expr> opt) {
    setChild(opt, 2);
  }
  /**
   * Replaces the (optional) Init child.
   * @param node The new node to be used as the Init child.
   * @apilevel high-level
   */
  public void setInit(Expr node) {
    getInitOpt().setChild(node, 0);
  }
  /**
   * Check whether the optional Init child exists.
   * @return {@code true} if the optional Init child exists, {@code false} if it does not.
   * @apilevel high-level
   */
  public boolean hasInit() {
    return getInitOpt().getNumChild() != 0;
  }
  /**
   * Retrieves the (optional) Init child.
   * @return The Init child, if it exists. Returns {@code null} otherwise.
   * @apilevel low-level
   */
  public Expr getInit() {
    return (Expr) getInitOpt().getChild(0);
  }
  /**
   * Retrieves the optional node for the Init child. This is the <code>Opt</code> node containing the child Init, not the actual child!
   * @return The optional node for child the Init child.
   * @apilevel low-level
   */
  @ASTNodeAnnotation.OptChild(name="Init")
  public Opt<Expr> getInitOpt() {
    return (Opt<Expr>) getChild(2);
  }
  /**
   * Retrieves the optional node for child Init. This is the <code>Opt</code> node containing the child Init, not the actual child!
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The optional node for child Init.
   * @apilevel low-level
   */
  public Opt<Expr> getInitOptNoTransform() {
    return (Opt<Expr>) getChildNoTransform(2);
  }
  /**
   * This method should not be called. This method throws an exception due to
   * the corresponding child being an NTA shadowing a non-NTA child.
   * @param node
   * @apilevel internal
   */
  public void setTypeAccess(Access node) {
    throw new Error("Can not replace NTA child TypeAccess in EnumConstant!");
  }
  /**
   * Retrieves the TypeAccess child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the TypeAccess child.
   * @apilevel low-level
   */
  public Access getTypeAccessNoTransform() {
    return (Access) getChildNoTransform(3);
  }
  /**
   * Retrieves the child position of the optional child TypeAccess.
   * @return The the child position of the optional child TypeAccess.
   * @apilevel low-level
   */
  protected int getTypeAccessChildPosition() {
    return 3;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isEnumConstant() {
    boolean isEnumConstant_value = true;

    return isEnumConstant_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isPublic() {
    boolean isPublic_value = true;

    return isPublic_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isStatic() {
    boolean isStatic_value = true;

    return isStatic_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isFinal() {
    boolean isFinal_value = true;

    return isFinal_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean getTypeAccess_computed = false;
  /**
   * @apilevel internal
   */
  protected Access getTypeAccess_value;
  /**
   * @apilevel internal
   */
  private void getTypeAccess_reset() {
    getTypeAccess_computed = false;
    getTypeAccess_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public Access getTypeAccess() {
    ASTNode$State state = state();
    if (getTypeAccess_computed) {
      return (Access) getChild(getTypeAccessChildPosition());
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    getTypeAccess_value = getTypeAccess_compute();
    setChild(getTypeAccess_value, getTypeAccessChildPosition());
    if (isFinal && num == state().boundariesCrossed) {
      getTypeAccess_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    Access node = (Access) this.getChild(getTypeAccessChildPosition());
    return node;
  }
  /**
   * @apilevel internal
   */
  private Access getTypeAccess_compute() {
      return hostType().createQualifiedAccess();
    }
  /**
   * @attribute syn
   * @aspect Enums
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Enums.jrag:737
   */
  @ASTNodeAnnotation.Attribute
  public SimpleSet localMethodsSignature(String signature) {
    {
        SimpleSet set = localMethodsSignatureMap().get(signature);
        if (set != null) {
          return set;
        }
        return SimpleSet.emptySet;
      }
  }
  /**
   * @apilevel internal
   */
  protected boolean localMethodsSignatureMap_computed = false;
  /**
   * @apilevel internal
   */
  protected Map<String,SimpleSet> localMethodsSignatureMap_value;
  /**
   * @apilevel internal
   */
  private void localMethodsSignatureMap_reset() {
    localMethodsSignatureMap_computed = false;
    localMethodsSignatureMap_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public Map<String,SimpleSet> localMethodsSignatureMap() {
    ASTNode$State state = state();
    if (localMethodsSignatureMap_computed) {
      return localMethodsSignatureMap_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    localMethodsSignatureMap_value = localMethodsSignatureMap_compute();
    if (isFinal && num == state().boundariesCrossed) {
      localMethodsSignatureMap_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return localMethodsSignatureMap_value;
  }
  /**
   * @apilevel internal
   */
  private Map<String,SimpleSet> localMethodsSignatureMap_compute() {
      HashMap map = new HashMap(getNumBodyDecl());
      for (int i = 0; i < getNumBodyDecl(); i++) {
        if (getBodyDecl(i) instanceof MethodDecl) {
          MethodDecl decl = (MethodDecl) getBodyDecl(i);
          putSimpleSetElement(map, decl.signature(), decl);
        }
      }
      return map;
    }
  /**
   * @attribute syn
   * @aspect Enums
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Enums.jrag:757
   */
  @ASTNodeAnnotation.Attribute
  public boolean implementsMethod(MethodDecl method) {
    {
        SimpleSet set = (SimpleSet) localMethodsSignature(method.signature());
        if (set.size() == 1) {
          MethodDecl n = (MethodDecl) set.iterator().next();
          if (!n.isAbstract()) {
            return true;
          }
        }
        return false;
      }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\SyntacticClassification.jrag:36
   * @apilevel internal
   */
  public NameType Define_nameType(ASTNode caller, ASTNode child) {
    if (caller == getTypeAccessNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Enums.jrag:564
      return NameType.TYPE_NAME;
    }
    else {
      return super.Define_nameType(caller, child);
    }
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
