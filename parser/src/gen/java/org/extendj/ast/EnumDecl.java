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
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\grammar\\Enums.ast:1
 * @production EnumDecl : {@link ClassDecl} ::= <span class="component">{@link Modifiers}</span> <span class="component">&lt;ID:String&gt;</span> <span class="component">[SuperClass:{@link Access}]</span> <span class="component">Implements:{@link Access}*</span> <span class="component">{@link BodyDecl}*</span>;

 */
public class EnumDecl extends ClassDecl implements Cloneable {
  /**
   * @aspect Enums
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Enums.jrag:68
   */
  public void typeCheck() {
    super.typeCheck();
    for (Iterator iter = memberMethods("finalize").iterator(); iter.hasNext(); ) {
      MethodDecl m = (MethodDecl) iter.next();
      if (m.getNumParameter() == 0 && m.hostType() == this) {
        error("an enum may not declare a finalizer");
      }
    }
    checkEnum(this);
  }
  /**
   * @aspect Enums
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Enums.jrag:105
   */
  private boolean done = false;
  /**
   * @aspect Enums
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Enums.jrag:106
   */
  private boolean done() {
    if (done) {
      return true;
    }
    done = true;
    return false;
  }
  /**
   * @aspect Enums
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Enums.jrag:354
   */
  private void addValues() {
    int numConstants = enumConstants().size();
    List initValues = new List();
    for (Iterator iter = enumConstants().iterator(); iter.hasNext(); ) {
      EnumConstant c = (EnumConstant) iter.next();
      initValues.add(c.createBoundFieldAccess());
    }
    FieldDeclaration values = new FieldDeclaration(
      new Modifiers(new List().add(
        new Modifier("private")).add(
        new Modifier("static")).add(
        new Modifier("final")).add(
        new Modifier("synthetic"))
      ),
      arrayType().createQualifiedAccess(),
      "$VALUES",
      new Opt(
          new ArrayCreationExpr(
            new ArrayTypeWithSizeAccess(
              createQualifiedAccess(),
              Literal.buildIntegerLiteral(enumConstants().size())
            ),
            new Opt(
              new ArrayInit(
                initValues
              )
            )
          )
      )
    );
    addBodyDecl(values);
    // public static final Test[] values() { return (Test[])$VALUES.clone(); }
    addBodyDecl(
      new MethodDecl(
        new Modifiers(new List().add(
          new Modifier("public")).add(
          new Modifier("static")).add(
          new Modifier("final")).add(
          new Modifier("synthetic"))
        ),
        arrayType().createQualifiedAccess(),
        "values",
        new List(),
        new List(),
        new Opt(
          new Block(
            new List().add(
              new ReturnStmt(
                new Opt(
                  new CastExpr(
                    arrayType().createQualifiedAccess(),
                    values.createBoundFieldAccess().qualifiesAccess(
                      new MethodAccess(
                        "clone",
                        new List()
                      )
                    )
                  )
                )
              )
            )
          )
        )
      )
    );
    // public static Test valueOf(String s) { return (Test) java.lang.Enum.valueOf(Test.class, s); }
    addBodyDecl(
      new MethodDecl(
        new Modifiers(new List().add(
          new Modifier("public")).add(
          new Modifier("static")).add(
          new Modifier("synthetic"))
        ),
        createQualifiedAccess(),
        "valueOf",
        new List().add(
          new ParameterDeclaration(
            new Modifiers(new List()),
            typeString().createQualifiedAccess(),
            "s"
          )
        ),
        new List(),
        new Opt(
          new Block(
            new List().add(
              new ReturnStmt(
                new Opt(
                  new CastExpr(
                    createQualifiedAccess(),
                    lookupType("java.lang", "Enum").createQualifiedAccess().qualifiesAccess(
                      new MethodAccess(
                        "valueOf",
                        new List().add(
                          createQualifiedAccess().qualifiesAccess(new ClassAccess())
                        ).add(
                          new VarAccess(
                            "s"
                          )
                        )
                      )
                    )
                  )
                )
              )
            )
          )
        )
      )
    );
  }
  /**
   * @aspect Enums
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Enums.jrag:496
   */
  protected void checkEnum(EnumDecl enumDecl) {
    for (int i = 0; i < getNumBodyDecl(); i++) {
      if (getBodyDecl(i) instanceof ConstructorDecl) {
        getBodyDecl(i).checkEnum(enumDecl);
      } else if (getBodyDecl(i) instanceof InstanceInitializer) {
        getBodyDecl(i).checkEnum(enumDecl);
      } else if (getBodyDecl(i) instanceof FieldDeclaration) {
        FieldDeclaration f = (FieldDeclaration) getBodyDecl(i);
        if (!f.isStatic() && f.hasInit()) {
          f.checkEnum(enumDecl);
        }
      }
    }
  }
  /**
   * @aspect Enums
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Enums.jrag:605
   */
  public void prettyPrint(PrettyPrinter out) {
    if (!docComment.isEmpty()) {
      out.print(docComment);
    }
    out.print(getModifiers());
    out.print("enum " + name());
    if (getNumImplements() > 0) {
      out.print(" implements ");
      out.join(getImplementsList(), new PrettyPrinter.Joiner() {
        @Override
        public void printSeparator(PrettyPrinter out) {
          out.print(", ");
        }
      });
    }
    out.print(" {");
    for (int i = 0; i < getNumBodyDecl(); i++) {
      BodyDecl d = getBodyDecl(i);
      out.print(d);
      if (d instanceof EnumConstant) {
        if (i + 1 < getNumBodyDecl() && !(getBodyDecl(i + 1) instanceof EnumConstant)) {
          out.println();
          out.print(";");
        }
      }
    }
    out.println();
    out.print("}");
  }
  /**
   * Check that the enum does not contain unimplemented abstract methods.
   * @aspect Enums
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Enums.jrag:723
   */
  public void checkModifiers() {
    super.checkModifiers();
    if (!unimplementedMethods().isEmpty()) {
      StringBuilder sb = new StringBuilder();
      sb.append("" + name() + " lacks implementations in one or more "
          + "enum constants for the following methods:\n");
      for (Iterator iter = unimplementedMethods().iterator(); iter.hasNext(); ) {
        MethodDecl m = (MethodDecl) iter.next();
        sb.append("  " + m.signature() + " in " + m.hostType().typeName() + "\n");
      }
      error(sb.toString());
    }
  }
  /**
   * @declaredat ASTNode:1
   */
  public EnumDecl() {
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
    setChild(new List(), 1);
    setChild(new List(), 2);
    setChild(new Opt(), 3);
    setChild(new Opt(), 4);
  }
  /**
   * @declaredat ASTNode:17
   */
  public EnumDecl(Modifiers p0, String p1, List<Access> p2, List<BodyDecl> p3) {
    setChild(p0, 0);
    setID(p1);
    setChild(p2, 1);
    setChild(p3, 2);
  }
  /**
   * @declaredat ASTNode:23
   */
  public EnumDecl(Modifiers p0, beaver.Symbol p1, List<Access> p2, List<BodyDecl> p3) {
    setChild(p0, 0);
    setID(p1);
    setChild(p2, 1);
    setChild(p3, 2);
  }
  /**
   * @apilevel low-level
   * @declaredat ASTNode:32
   */
  protected int numChildren() {
    return 3;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:38
   */
  public boolean mayHaveRewrite() {
    return true;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:44
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    isStatic_reset();
    getSuperClassOpt_reset();
    getImplicitConstructorOpt_reset();
    enumConstants_reset();
    unimplementedMethods_reset();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:55
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:61
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:67
   */
  public EnumDecl clone() throws CloneNotSupportedException {
    EnumDecl node = (EnumDecl) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:74
   */
  public EnumDecl copy() {
    try {
      EnumDecl node = (EnumDecl) clone();
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
   * @declaredat ASTNode:93
   */
  @Deprecated
  public EnumDecl fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:103
   */
  public EnumDecl treeCopyNoTransform() {
    EnumDecl tree = (EnumDecl) copy();
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        switch (i) {
        case 3:
        case 4:
          tree.children[i] = new Opt();
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
   * @declaredat ASTNode:129
   */
  public EnumDecl treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:136
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenString_ID == ((EnumDecl)node).tokenString_ID);    
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
   * Replaces the Implements list.
   * @param list The new list node to be used as the Implements list.
   * @apilevel high-level
   */
  public void setImplementsList(List<Access> list) {
    setChild(list, 1);
  }
  /**
   * Retrieves the number of children in the Implements list.
   * @return Number of children in the Implements list.
   * @apilevel high-level
   */
  public int getNumImplements() {
    return getImplementsList().getNumChild();
  }
  /**
   * Retrieves the number of children in the Implements list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the Implements list.
   * @apilevel low-level
   */
  public int getNumImplementsNoTransform() {
    return getImplementsListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the Implements list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the Implements list.
   * @apilevel high-level
   */
  public Access getImplements(int i) {
    return (Access) getImplementsList().getChild(i);
  }
  /**
   * Check whether the Implements list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasImplements() {
    return getImplementsList().getNumChild() != 0;
  }
  /**
   * Append an element to the Implements list.
   * @param node The element to append to the Implements list.
   * @apilevel high-level
   */
  public void addImplements(Access node) {
    List<Access> list = (parent == null) ? getImplementsListNoTransform() : getImplementsList();
    list.addChild(node);
  }
  /**
   * @apilevel low-level
   */
  public void addImplementsNoTransform(Access node) {
    List<Access> list = getImplementsListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the Implements list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setImplements(Access node, int i) {
    List<Access> list = getImplementsList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the Implements list.
   * @return The node representing the Implements list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="Implements")
  public List<Access> getImplementsList() {
    List<Access> list = (List<Access>) getChild(1);
    return list;
  }
  /**
   * Retrieves the Implements list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the Implements list.
   * @apilevel low-level
   */
  public List<Access> getImplementsListNoTransform() {
    return (List<Access>) getChildNoTransform(1);
  }
  /**
   * Retrieves the Implements list.
   * @return The node representing the Implements list.
   * @apilevel high-level
   */
  public List<Access> getImplementss() {
    return getImplementsList();
  }
  /**
   * Retrieves the Implements list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the Implements list.
   * @apilevel low-level
   */
  public List<Access> getImplementssNoTransform() {
    return getImplementsListNoTransform();
  }
  /**
   * Replaces the BodyDecl list.
   * @param list The new list node to be used as the BodyDecl list.
   * @apilevel high-level
   */
  public void setBodyDeclList(List<BodyDecl> list) {
    setChild(list, 2);
  }
  /**
   * Retrieves the number of children in the BodyDecl list.
   * @return Number of children in the BodyDecl list.
   * @apilevel high-level
   */
  public int getNumBodyDecl() {
    return getBodyDeclList().getNumChild();
  }
  /**
   * Retrieves the number of children in the BodyDecl list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the BodyDecl list.
   * @apilevel low-level
   */
  public int getNumBodyDeclNoTransform() {
    return getBodyDeclListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the BodyDecl list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the BodyDecl list.
   * @apilevel high-level
   */
  public BodyDecl getBodyDecl(int i) {
    return (BodyDecl) getBodyDeclList().getChild(i);
  }
  /**
   * Check whether the BodyDecl list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasBodyDecl() {
    return getBodyDeclList().getNumChild() != 0;
  }
  /**
   * Append an element to the BodyDecl list.
   * @param node The element to append to the BodyDecl list.
   * @apilevel high-level
   */
  public void addBodyDecl(BodyDecl node) {
    List<BodyDecl> list = (parent == null) ? getBodyDeclListNoTransform() : getBodyDeclList();
    list.addChild(node);
  }
  /**
   * @apilevel low-level
   */
  public void addBodyDeclNoTransform(BodyDecl node) {
    List<BodyDecl> list = getBodyDeclListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the BodyDecl list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setBodyDecl(BodyDecl node, int i) {
    List<BodyDecl> list = getBodyDeclList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the BodyDecl list.
   * @return The node representing the BodyDecl list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="BodyDecl")
  public List<BodyDecl> getBodyDeclList() {
    List<BodyDecl> list = (List<BodyDecl>) getChild(2);
    return list;
  }
  /**
   * Retrieves the BodyDecl list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the BodyDecl list.
   * @apilevel low-level
   */
  public List<BodyDecl> getBodyDeclListNoTransform() {
    return (List<BodyDecl>) getChildNoTransform(2);
  }
  /**
   * Retrieves the BodyDecl list.
   * @return The node representing the BodyDecl list.
   * @apilevel high-level
   */
  public List<BodyDecl> getBodyDecls() {
    return getBodyDeclList();
  }
  /**
   * Retrieves the BodyDecl list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the BodyDecl list.
   * @apilevel low-level
   */
  public List<BodyDecl> getBodyDeclsNoTransform() {
    return getBodyDeclListNoTransform();
  }
  /**
   * Replaces the (optional) ImplicitConstructor child.
   * @param node The new node to be used as the ImplicitConstructor child.
   * @apilevel high-level
   */
  public void setImplicitConstructor(ConstructorDecl node) {
    getImplicitConstructorOpt().setChild(node, 0);
  }
  /**
   * Check whether the optional ImplicitConstructor child exists.
   * @return {@code true} if the optional ImplicitConstructor child exists, {@code false} if it does not.
   * @apilevel high-level
   */
  public boolean hasImplicitConstructor() {
    return getImplicitConstructorOpt().getNumChild() != 0;
  }
  /**
   * Retrieves the (optional) ImplicitConstructor child.
   * @return The ImplicitConstructor child, if it exists. Returns {@code null} otherwise.
   * @apilevel low-level
   */
  public ConstructorDecl getImplicitConstructor() {
    return (ConstructorDecl) getImplicitConstructorOpt().getChild(0);
  }
  /**
   * Retrieves the optional node for child ImplicitConstructor. This is the <code>Opt</code> node containing the child ImplicitConstructor, not the actual child!
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The optional node for child ImplicitConstructor.
   * @apilevel low-level
   */
  public Opt<ConstructorDecl> getImplicitConstructorOptNoTransform() {
    return (Opt<ConstructorDecl>) getChildNoTransform(3);
  }
  /**
   * Retrieves the child position of the optional child ImplicitConstructor.
   * @return The the child position of the optional child ImplicitConstructor.
   * @apilevel low-level
   */
  protected int getImplicitConstructorOptChildPosition() {
    return 3;
  }
  /**
   * This method should not be called. This method throws an exception due to
   * the corresponding child being an NTA shadowing a non-NTA child.
   * @param node
   * @apilevel internal
   */
  public void setSuperClassOpt(Opt<Access> node) {
    throw new Error("Can not replace NTA child SuperClassOpt in EnumDecl!");
  }
  /**
   * Replaces the (optional) SuperClass child.
   * @param node The new node to be used as the SuperClass child.
   * @apilevel high-level
   */
  public void setSuperClass(Access node) {
    getSuperClassOpt().setChild(node, 0);
  }
  /**
   * Check whether the optional SuperClass child exists.
   * @return {@code true} if the optional SuperClass child exists, {@code false} if it does not.
   * @apilevel high-level
   */
  public boolean hasSuperClass() {
    return getSuperClassOpt().getNumChild() != 0;
  }
  /**
   * Retrieves the (optional) SuperClass child.
   * @return The SuperClass child, if it exists. Returns {@code null} otherwise.
   * @apilevel low-level
   */
  public Access getSuperClass() {
    return (Access) getSuperClassOpt().getChild(0);
  }
  /**
   * Retrieves the optional node for child SuperClass. This is the <code>Opt</code> node containing the child SuperClass, not the actual child!
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The optional node for child SuperClass.
   * @apilevel low-level
   */
  public Opt<Access> getSuperClassOptNoTransform() {
    return (Opt<Access>) getChildNoTransform(4);
  }
  /**
   * Retrieves the child position of the optional child SuperClass.
   * @return The the child position of the optional child SuperClass.
   * @apilevel low-level
   */
  protected int getSuperClassOptChildPosition() {
    return 4;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isValidAnnotationMethodReturnType() {
    boolean isValidAnnotationMethodReturnType_value = true;

    return isValidAnnotationMethodReturnType_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isEnumDecl() {
    boolean isEnumDecl_value = true;

    return isEnumDecl_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean isStatic_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean isStatic_value;
  /**
   * @apilevel internal
   */
  private void isStatic_reset() {
    isStatic_computed = false;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isStatic() {
    ASTNode$State state = state();
    if (isStatic_computed) {
      return isStatic_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    isStatic_value = isNestedType();
    if (isFinal && num == state().boundariesCrossed) {
      isStatic_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return isStatic_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean getSuperClassOpt_computed = false;
  /**
   * @apilevel internal
   */
  protected Opt getSuperClassOpt_value;
  /**
   * @apilevel internal
   */
  private void getSuperClassOpt_reset() {
    getSuperClassOpt_computed = false;
    getSuperClassOpt_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public Opt getSuperClassOpt() {
    ASTNode$State state = state();
    if (getSuperClassOpt_computed) {
      return (Opt) getChild(getSuperClassOptChildPosition());
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    getSuperClassOpt_value = getSuperClassOpt_compute();
    setChild(getSuperClassOpt_value, getSuperClassOptChildPosition());
    if (isFinal && num == state().boundariesCrossed) {
      getSuperClassOpt_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    Opt node = (Opt) this.getChild(getSuperClassOptChildPosition());
    return node;
  }
  /**
   * @apilevel internal
   */
  private Opt getSuperClassOpt_compute() {
      return new Opt(
        new ParTypeAccess(
          new TypeAccess(
            "java.lang",
            "Enum"
          ),
          new List().add(createQualifiedAccess())
        )
      );
    }
  /**
   * @apilevel internal
   */
  protected boolean getImplicitConstructorOpt_computed = false;
  /**
   * @apilevel internal
   */
  protected Opt<ConstructorDecl> getImplicitConstructorOpt_value;
  /**
   * @apilevel internal
   */
  private void getImplicitConstructorOpt_reset() {
    getImplicitConstructorOpt_computed = false;
    getImplicitConstructorOpt_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public Opt<ConstructorDecl> getImplicitConstructorOpt() {
    ASTNode$State state = state();
    if (getImplicitConstructorOpt_computed) {
      return (Opt<ConstructorDecl>) getChild(getImplicitConstructorOptChildPosition());
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    getImplicitConstructorOpt_value = getImplicitConstructorOpt_compute();
    setChild(getImplicitConstructorOpt_value, getImplicitConstructorOptChildPosition());
    if (isFinal && num == state().boundariesCrossed) {
      getImplicitConstructorOpt_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    Opt<ConstructorDecl> node = (Opt<ConstructorDecl>) this.getChild(getImplicitConstructorOptChildPosition());
    return node;
  }
  /**
   * @apilevel internal
   */
  private Opt<ConstructorDecl> getImplicitConstructorOpt_compute() {
      if (needsImplicitConstructor()) {
        List parameterList = new List();
        parameterList.add(
            new ParameterDeclaration(new TypeAccess("java.lang", "String"), "p0")
        );
        parameterList.add(
            new ParameterDeclaration(new TypeAccess("int"), "p1")
        );
        ConstructorDecl constructor = new ConstructorDecl(
            new Modifiers(new List()
              .add(new Modifier("private"))
              .add(new Modifier("synthetic"))
              ),
            name(),
            parameterList,
            new List(),
            new Opt(
              new ExprStmt(
                new SuperConstructorAccess(
                  "super",
                  new List()
                  .add(new VarAccess("p0"))
                  .add(new VarAccess("p1"))
                  )
                )
              ),
            new Block(new List())
            );
        return new Opt<ConstructorDecl>(constructor);
      } else {
        return new Opt<ConstructorDecl>();
      }
    }
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\Modifiers.jrag:250
   */
  @ASTNodeAnnotation.Attribute
  public boolean isFinal() {
    {
        for (Iterator iter = enumConstants().iterator(); iter.hasNext(); ) {
          EnumConstant c = (EnumConstant) iter.next();
          ClassInstanceExpr e = (ClassInstanceExpr) c.getInit();
          if (e.hasTypeDecl()) {
            return false;
          }
        }
        return true;
      }
  }
  /**
   * @apilevel internal
   */
  protected boolean enumConstants_computed = false;
  /**
   * @apilevel internal
   */
  protected ArrayList enumConstants_value;
  /**
   * @apilevel internal
   */
  private void enumConstants_reset() {
    enumConstants_computed = false;
    enumConstants_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public ArrayList enumConstants() {
    ASTNode$State state = state();
    if (enumConstants_computed) {
      return enumConstants_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    enumConstants_value = enumConstants_compute();
    if (isFinal && num == state().boundariesCrossed) {
      enumConstants_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return enumConstants_value;
  }
  /**
   * @apilevel internal
   */
  private ArrayList enumConstants_compute() {
      ArrayList list = new ArrayList();
      for (int i = 0; i < getNumBodyDecl(); i++) {
        if (getBodyDecl(i).isEnumConstant()) {
          list.add(getBodyDecl(i));
        }
      }
      return list;
    }
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\Modifiers.jrag:245
   */
  @ASTNodeAnnotation.Attribute
  public boolean isAbstract() {
    {
        for (int i = 0; i < getNumBodyDecl(); i++) {
          if (getBodyDecl(i) instanceof MethodDecl) {
            MethodDecl m = (MethodDecl) getBodyDecl(i);
            if (m.isAbstract()) {
              return true;
            }
          }
        }
        return false;
      }
  }
  /**
   * @apilevel internal
   */
  protected boolean unimplementedMethods_computed = false;
  /**
   * @apilevel internal
   */
  protected Collection unimplementedMethods_value;
  /**
   * @apilevel internal
   */
  private void unimplementedMethods_reset() {
    unimplementedMethods_computed = false;
    unimplementedMethods_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public Collection unimplementedMethods() {
    ASTNode$State state = state();
    if (unimplementedMethods_computed) {
      return unimplementedMethods_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    unimplementedMethods_value = unimplementedMethods_compute();
    if (isFinal && num == state().boundariesCrossed) {
      unimplementedMethods_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return unimplementedMethods_value;
  }
  /**
   * @apilevel internal
   */
  private Collection unimplementedMethods_compute() {
      Collection<MethodDecl> methods = new LinkedList<MethodDecl>();
      for (Iterator iter = interfacesMethodsIterator(); iter.hasNext(); ) {
        MethodDecl method = (MethodDecl) iter.next();
        SimpleSet set = (SimpleSet) localMethodsSignature(method.signature());
        if (set.size() == 1) {
          MethodDecl n = (MethodDecl) set.iterator().next();
          if (!n.isAbstract()) {
            continue;
          }
        }
        boolean implemented = false;
        set = (SimpleSet) ancestorMethods(method.signature());
        for (Iterator i2 = set.iterator(); i2.hasNext(); ) {
          MethodDecl n = (MethodDecl) i2.next();
          if (!n.isAbstract()) {
            implemented = true;
            break;
          }
        }
        if (!implemented) {
          methods.add(method);
        }
      }
  
      for (Iterator iter = localMethodsIterator(); iter.hasNext(); ) {
        MethodDecl method = (MethodDecl) iter.next();
        if (method.isAbstract()) {
          methods.add(method);
        }
      }
  
      Collection unimplemented = new ArrayList();
      for (MethodDecl method : methods) {
        if (enumConstants().isEmpty()) {
          unimplemented.add(method);
          continue;
        }
        boolean missing = false;
        for (Iterator iter = enumConstants().iterator(); iter.hasNext(); ) {
          if (!((EnumConstant) iter.next()).implementsMethod(method)) {
            missing = true;
            break;
          }
        }
        if (missing) {
          unimplemented.add(method);
        }
      }
  
      return unimplemented;
    }
  /**
   * @attribute inh
   * @aspect Enums
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Enums.jrag:466
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl typeString() {
    TypeDecl typeString_value = getParent().Define_typeString(this, null);

    return typeString_value;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\Modifiers.jrag:424
   * @apilevel internal
   */
  public boolean Define_mayBeAbstract(ASTNode caller, ASTNode child) {
    if (caller == getModifiersNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Enums.jrag:55
      return false;
    }
    else {
      return super.Define_mayBeAbstract(caller, child);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\Modifiers.jrag:422
   * @apilevel internal
   */
  public boolean Define_mayBeStatic(ASTNode caller, ASTNode child) {
    if (caller == getModifiersNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Enums.jrag:62
      return isNestedType();
    }
    else {
      return super.Define_mayBeStatic(caller, child);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\Modifiers.jrag:423
   * @apilevel internal
   */
  public boolean Define_mayBeFinal(ASTNode caller, ASTNode child) {
    if (caller == getModifiersNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Enums.jrag:335
      return false;
    }
    else {
      return super.Define_mayBeFinal(caller, child);
    }
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    // Declared at @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Enums.jrag:116
    if (!done()) {
      return rewriteRule0();
    }
    return super.rewriteTo();
  }
  /**
   * @declaredat @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Enums.jrag:116
   * @apilevel internal
   */
  private EnumDecl rewriteRule0() {
{
      transformEnumConstructors();
      addValues(); // Add the values() and getValue(String s) methods
      return this;
    }  }
}
