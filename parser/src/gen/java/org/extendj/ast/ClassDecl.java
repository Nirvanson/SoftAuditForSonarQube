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
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\grammar\\Java.ast:63
 * @production ClassDecl : {@link ReferenceType} ::= <span class="component">{@link Modifiers}</span> <span class="component">&lt;ID:String&gt;</span> <span class="component">[SuperClass:{@link Access}]</span> <span class="component">Implements:{@link Access}*</span> <span class="component">{@link BodyDecl}*</span> <span class="component">[ImplicitConstructor:{@link ConstructorDecl}]</span>;

 */
public class ClassDecl extends ReferenceType implements Cloneable {
  /**
   * @aspect Java4PrettyPrint
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\PrettyPrint.jadd:362
   */
  public void prettyPrint(PrettyPrinter out) {
    if (hasDocComment()) {
      out.print(docComment());
    }
    if (!out.isNewLine()) {
      out.println();
    }
    out.print(getModifiers());
    out.print("class ");
    out.print(getID());
    if (hasSuperClass()) {
      out.print(" extends ");
      out.print(getSuperClass());
    }
    if (hasImplements()) {
      out.print(" implements ");
      out.join(getImplementss(), new PrettyPrinter.Joiner() {
        @Override
        public void printSeparator(PrettyPrinter out) {
          out.print(", ");
        }
      });
    }
    out.print(" {");
    out.println();
    out.indent(1);
    out.join(getBodyDecls(), new PrettyPrinter.Joiner() {
      @Override
      public void printSeparator(PrettyPrinter out) {
        out.println();
        out.println();
      }
    });
    if (!out.isNewLine()) {
      out.println();
    }
    out.print("}");
  }
  /**
   * @aspect AccessControl
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\AccessControl.jrag:175
   */
  public void accessControl() {
    super.accessControl();

    // 8.1.1.2 final Classes
    TypeDecl typeDecl = superclass();
    if (!typeDecl.isUnknown() && !typeDecl.accessibleFromExtend(this)) {
      errorf("class %s may not extend non accessible type %s", fullName(), typeDecl.fullName());
    }

    if (hasSuperclass() && !superclass().accessibleFrom(this)) {
      errorf("a superclass must be accessible which %s is not", superclass().name());
    }

    // 8.1.4
    for (int i = 0; i < getNumImplements(); i++) {
      TypeDecl decl = getImplements(i).type();
      if (!decl.isCircular() && !decl.accessibleFrom(this)) {
        errorf("class %s can not implement non accessible type %s", fullName(), decl.fullName());
      }
    }
  }
  /**
   * @aspect ErrorCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ErrorCheck.jrag:243
   */
  public void collectErrors() {
    super.collectErrors();
    if (hasImplicitConstructor()) {
      getImplicitConstructor().collectErrors();
    }
  }
  /**
   * @aspect Modifiers
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\Modifiers.jrag:122
   */
  public void checkModifiers() {
    super.checkModifiers();
    // 8.1.1.2 final Classes
    TypeDecl typeDecl = superclass();
    if (!typeDecl.isUnknown() && typeDecl.isFinal()) {
      errorf("class %s may not extend final class %s", fullName(), typeDecl.fullName());
    }

  }
  /**
   * @aspect SuperClasses
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeAnalysis.jrag:647
   */
  public boolean hasSuperclass() {
    return !isObject();
  }
  /**
   * @aspect SuperClasses
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeAnalysis.jrag:651
   */
  public TypeDecl superclass() {
    if (isObject()) {
      return unknownType();
    } else if (hasSuperClass() && !isCircular() && getSuperClass().type().isClassDecl()) {
      return getSuperClass().type();
    } else {
      return typeObject();
    }
  }
  /**
   * @aspect SuperClasses
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeAnalysis.jrag:688
   */
  public Iterator<TypeDecl> interfacesIterator() {
    return new Iterator<TypeDecl>() {
      public boolean hasNext() {
        computeNextCurrent();
        return current != null;
      }
      public TypeDecl next() {
        return current;
      }
      public void remove() {
        throw new UnsupportedOperationException();
      }
      private int index = 0;
      private TypeDecl current = null;
      private void computeNextCurrent() {
        current = null;
        if (isObject() || isCircular()) {
          return;
        }
        while (index < getNumImplements()) {
          TypeDecl typeDecl = getImplements(index++).type();
          if (!typeDecl.isCircular() && typeDecl.isInterfaceDecl()) {
            current = typeDecl;
            return;
          }
        }
      }
    };
  }
  /**
   * @aspect TypeHierarchyCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:321
   */
  public void refined_TypeHierarchyCheck_ClassDecl_typeCheck() {
    super.typeCheck();

    // check if methods from superclass are incompatible with interface methods
    for (Iterator<MethodDecl> iter = interfacesMethodsIterator(); iter.hasNext(); ) {
      MethodDecl decl = iter.next();
      SimpleSet set = localMethodsSignature(decl.signature());
      Iterator i2 = set.iterator();
      boolean overridden = false;
      while (i2.hasNext()) {
        MethodDecl m = (MethodDecl) i2.next();
        // regardless of overriding
        // 8.4.6.3
        if (m.overrideCandidate(decl)) {
          overridden = true;
          if (!m.mayOverride(decl)) {
            errorf("the return type of method %s in %s does not match the return type of"
                + " method %s in %s and may thus not be overridden",
                m.fullSignature(), m.hostType().typeName(), decl.fullSignature(),
                decl.hostType().typeName());
          }
        }
      }
      if (!overridden) {
        set = ancestorMethods(decl.signature());
        for (i2 = set.iterator(); i2.hasNext(); ) {
          MethodDecl m = (MethodDecl) i2.next();
          if (!m.isAbstract() && m.overrideCandidate(decl) &&
              !m.mayOverride(decl)) {
            errorf("the return type of method %s in %s does not match the return type of"
                + " method %s in %s and may thus not be overridden",
                m.fullSignature(), m.hostType().typeName(), decl.fullSignature(),
                decl.hostType().typeName());
          }
        }
      }
    }
  }
  /**
   * @aspect TypeHierarchyCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:362
   */
  public void refined_TypeHierarchyCheck_ClassDecl_nameCheck() {
    super.nameCheck();
    if (hasSuperClass() && !getSuperClass().type().isClassDecl()) {
      errorf("a class may only inherit another class, which %s is not",
          getSuperClass().type().typeName());
    }
    if (isObject() && hasSuperClass()) {
      error("class Object may not have a superclass");
    }
    if (isObject() && getNumImplements() != 0) {
      error("class Object may not implement an interface");
    }

    // 8.1.3
    if (isCircular()) {
      errorf("circular inheritance dependency in %s", typeName());
    }

    // 8.1.4
    HashSet set = new HashSet();
    for (int i = 0; i < getNumImplements(); i++) {
      TypeDecl decl = getImplements(i).type();
      if (!decl.isInterfaceDecl() && !decl.isUnknown()) {
        errorf("type %s can not implement the non-interface type %s", fullName(), decl.fullName());
      }
      if (set.contains(decl)) {
        errorf("type %s is mentionened multiple times in implements clause", decl.fullName());
      }
      set.add(decl);
    }

    for (Iterator iter = interfacesMethodsIterator(); iter.hasNext(); ) {
      MethodDecl m = (MethodDecl) iter.next();
      if (localMethodsSignature(m.signature()).isEmpty()) {
        SimpleSet s = superclass().methodsSignature(m.signature());
        for (Iterator i2 = s.iterator(); i2.hasNext(); ) {
          MethodDecl n = (MethodDecl) i2.next();
          if (n.accessibleFrom(this)) {
            interfaceMethodCompatibleWithInherited(m, n);
          }
        }
        if (s.isEmpty()) {
          for (Iterator i2 = interfacesMethodsSignature(m.signature()).iterator(); i2.hasNext(); ) {
            MethodDecl n = (MethodDecl) i2.next();
            // TODO don't report error twice
            checkAbstractMethodDecls(m, n);
          }
        }
      }
    }
  }
  /**
   * Check compatibility of interface method and superclass method.
   * @param m interface method
   * @param n superclass method
   * @aspect TypeHierarchyCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:419
   */
  private void interfaceMethodCompatibleWithInherited(MethodDecl m, MethodDecl n) {
    if (n.isAbstract()) {
      checkAbstractMethodDecls(m, n);
    }
    if (n.isStatic()) {
      error("Xa static method may not hide an instance method");
    }
    if (!n.isAbstract() && !n.isPublic()) {
      errorf("Xoverriding access modifier error for %s in %s and %s",
          m.fullSignature(), m.hostType().typeName(), n.hostType().typeName());
    }
    if (!n.mayOverride(m) && !m.mayOverride(m)) {
      errorf("Xthe return type of method %s in %s does not match"
          + " the return type of method %s in %s and may thus not be overridden",
          m.fullSignature(), m.hostType().typeName(), n.fullSignature(), n.hostType().typeName());
    }

    if (!n.isAbstract()) {
      // n implements and overrides method m in the interface
      // may not throw more checked exceptions
      for (Access e: n.getExceptionList()) {
        if (e.type().isCheckedException()) {
          boolean found = false;
          for (Access declException: m.getExceptionList()) {
            if (e.type().instanceOf(declException.type())) {
              found = true;
              break;
            }
          }
          if (!found) {
            errorf("%s in %s may not throw more checked exceptions than overridden method %s in %s",
                n.fullSignature(), n.hostType().typeName(), m.fullSignature(),
                m.hostType().typeName());
          }
        }
      }
    }
  }
  /**
   * @aspect Generics
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:231
   */
  public TypeDecl makeGeneric(Signatures.ClassSignature s) {
    // NOTE: we are overwriting List- and Opt- children here using setSuperClassOpt
    // and setImplementsList. This is dangerous since those children are in some
    // cases NTAs, and we should not use set methods to try to overwrite NTA values.
    // However, we have to do this here in order to not trigger rewrites that in
    // turn need to access certain (inherited) lookup attributes, and we are reasonably
    // sure that we are in fact not overwriting NTA children. We exclude EnumDecl here
    // because its [SuperClass] and Implements* are in fact NTAs.
    // /Jesper 2015-01-22
    if (this instanceof EnumDecl) {
      return this; // Enum superclass and superinterfaces are NTAs.
    }
    if (s.hasFormalTypeParameters()) {
      ASTNode node = getParent();
      int index = node.getIndexOfChild(this);
      node.setChild(
          new GenericClassDecl(
            getModifiersNoTransform(),
            getID(),
            s.hasSuperclassSignature()
                ? new Opt(s.superclassSignature())
                : getSuperClassOptNoTransform(),
            s.hasSuperinterfaceSignature()
                ? s.superinterfaceSignature()
                : getImplementsListNoTransform(),
            getBodyDeclListNoTransform(),
            s.typeParameters()
          ),
          index
      );
      return (TypeDecl) node.getChildNoTransform(index);
    } else {
      if (s.hasSuperclassSignature()) {
        setSuperClassOpt(new Opt(s.superclassSignature()));
      }
      if (s.hasSuperinterfaceSignature()) {
        setImplementsList(s.superinterfaceSignature());
      }
      return this;
    }
  }
  /**
   * @aspect LookupParTypeDecl
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:1424
   */
  public ClassDecl substitutedClassDecl(Parameterization parTypeDecl) {
    return new ClassDeclSubstituted(
      (Modifiers) getModifiers().treeCopyNoTransform(),
      getID(),
      hasSuperClass() ? new Opt(getSuperClass().type().substitute(parTypeDecl)) : new Opt(),
      getImplementsList().substitute(parTypeDecl),
      this
    );
  }
  /**
   * @declaredat ASTNode:1
   */
  public ClassDecl() {
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
    setChild(new Opt(), 1);
    setChild(new List(), 2);
    setChild(new List(), 3);
    setChild(new Opt(), 4);
  }
  /**
   * @declaredat ASTNode:17
   */
  public ClassDecl(Modifiers p0, String p1, Opt<Access> p2, List<Access> p3, List<BodyDecl> p4) {
    setChild(p0, 0);
    setID(p1);
    setChild(p2, 1);
    setChild(p3, 2);
    setChild(p4, 3);
  }
  /**
   * @declaredat ASTNode:24
   */
  public ClassDecl(Modifiers p0, beaver.Symbol p1, Opt<Access> p2, List<Access> p3, List<BodyDecl> p4) {
    setChild(p0, 0);
    setID(p1);
    setChild(p2, 1);
    setChild(p3, 2);
    setChild(p4, 3);
  }
  /**
   * @apilevel low-level
   * @declaredat ASTNode:34
   */
  protected int numChildren() {
    return 4;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:40
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:46
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    constructors_reset();
    getImplicitConstructorOpt_reset();
    methodsSignatureMap_reset();
    ancestorMethods_String_reset();
    memberTypes_String_reset();
    memberFieldsMap_reset();
    memberFields_String_reset();
    unimplementedMethods_reset();
    hasAbstract_reset();
    castingConversionTo_TypeDecl_reset();
    isString_reset();
    isObject_reset();
    instanceOf_TypeDecl_reset();
    isCircular_reset();
    erasedAncestorMethodsMap_reset();
    implementedInterfaces_reset();
    subtype_TypeDecl_reset();
    strictSubtype_TypeDecl_reset();
    hasOverridingMethodInSuper_MethodDecl_reset();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:71
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:77
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:83
   */
  public ClassDecl clone() throws CloneNotSupportedException {
    ClassDecl node = (ClassDecl) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:90
   */
  public ClassDecl copy() {
    try {
      ClassDecl node = (ClassDecl) clone();
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
   * @declaredat ASTNode:109
   */
  @Deprecated
  public ClassDecl fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:119
   */
  public ClassDecl treeCopyNoTransform() {
    ClassDecl tree = (ClassDecl) copy();
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        switch (i) {
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
   * @declaredat ASTNode:144
   */
  public ClassDecl treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:151
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenString_ID == ((ClassDecl)node).tokenString_ID);    
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
   * Replaces the optional node for the SuperClass child. This is the <code>Opt</code>
   * node containing the child SuperClass, not the actual child!
   * @param opt The new node to be used as the optional node for the SuperClass child.
   * @apilevel low-level
   */
  public void setSuperClassOpt(Opt<Access> opt) {
    setChild(opt, 1);
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
   * Retrieves the optional node for the SuperClass child. This is the <code>Opt</code> node containing the child SuperClass, not the actual child!
   * @return The optional node for child the SuperClass child.
   * @apilevel low-level
   */
  @ASTNodeAnnotation.OptChild(name="SuperClass")
  public Opt<Access> getSuperClassOpt() {
    return (Opt<Access>) getChild(1);
  }
  /**
   * Retrieves the optional node for child SuperClass. This is the <code>Opt</code> node containing the child SuperClass, not the actual child!
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The optional node for child SuperClass.
   * @apilevel low-level
   */
  public Opt<Access> getSuperClassOptNoTransform() {
    return (Opt<Access>) getChildNoTransform(1);
  }
  /**
   * Replaces the Implements list.
   * @param list The new list node to be used as the Implements list.
   * @apilevel high-level
   */
  public void setImplementsList(List<Access> list) {
    setChild(list, 2);
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
    List<Access> list = (List<Access>) getChild(2);
    return list;
  }
  /**
   * Retrieves the Implements list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the Implements list.
   * @apilevel low-level
   */
  public List<Access> getImplementsListNoTransform() {
    return (List<Access>) getChildNoTransform(2);
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
    setChild(list, 3);
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
    List<BodyDecl> list = (List<BodyDecl>) getChild(3);
    return list;
  }
  /**
   * Retrieves the BodyDecl list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the BodyDecl list.
   * @apilevel low-level
   */
  public List<BodyDecl> getBodyDeclListNoTransform() {
    return (List<BodyDecl>) getChildNoTransform(3);
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
    return (Opt<ConstructorDecl>) getChildNoTransform(4);
  }
  /**
   * Retrieves the child position of the optional child ImplicitConstructor.
   * @return The the child position of the optional child ImplicitConstructor.
   * @apilevel low-level
   */
  protected int getImplicitConstructorOptChildPosition() {
    return 4;
  }
  /**
   * @aspect GenericsTypeCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:563
   */
   
  public void typeCheck() {
    refined_TypeHierarchyCheck_ClassDecl_typeCheck();

    if (hasSuperclass()) {
      // JLS SE7 8.4.8.4
      // check for duplicate methods inherited from parameterized supertype
      if (superclass().isParameterizedType()) {
        Map<String,SimpleSet> localMap = localMethodsSignatureMap();
        Map<String,SimpleSet> methodMap = superclass().localMethodsSignatureMap();
        for (Map.Entry<String,SimpleSet> entry: methodMap.entrySet()) {
          String signature = entry.getKey();
          if (!localMap.containsKey(signature)) {
            // not locally overridden
            SimpleSet set = entry.getValue();
            Iterator iter = set.iterator();
            iter.next();
            while (iter.hasNext()) {
              MethodDecl m = (MethodDecl) iter.next();
              errorf("method with signature %s is multiply declared when inherited from %s",
                  signature, superclass().typeName());
            }
          }
        }
      }
    }
  }
  /**
   * @aspect Java8NameCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\NameCheck.jrag:311
   */
   
  public void nameCheck() {
    refined_TypeHierarchyCheck_ClassDecl_nameCheck();
    for (Iterator<SimpleSet> iter = methodsSignatureMap().values().iterator(); iter.hasNext(); ) {
      SimpleSet set = iter.next();
      if (set.size() > 1) {
        Iterator i2 = set.iterator();
        boolean foundClassAbstract = false;
        MethodDecl foundNonAbstract = null;
        while (i2.hasNext()) {
          MethodDecl m = (MethodDecl) i2.next();
          if (!m.isAbstract()) {
            foundNonAbstract = m;
          } else {
            if (m.hostType().isClassDecl() && m.hostType() != this) {
              foundClassAbstract = true;
            }
          }
        }
        // 8.4.8.1
        if (foundNonAbstract != null && !foundClassAbstract) {
          errorf("Method %s is multiply declared in %s",
              foundNonAbstract.fullSignature(), typeName());
        }
      }
    }
  }
  /**
   * @aspect TypeConversion
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeAnalysis.jrag:112
   */
  private boolean refined_TypeConversion_ClassDecl_castingConversionTo_TypeDecl(TypeDecl type)
{
    if (type.isArrayDecl()) {
      return isObject();
    } else if (type.isClassDecl()) {
      return this == type || instanceOf(type) || type.instanceOf(this);
    } else if (type.isInterfaceDecl()) {
      return !isFinal() || instanceOf(type);
    } else return super.castingConversionTo(type);
  }
  /**
   * @aspect Generics
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:81
   */
  private boolean refined_Generics_ClassDecl_castingConversionTo_TypeDecl(TypeDecl type)
{
    TypeDecl S = this;
    TypeDecl T = type;
    if (T instanceof TypeVariable) {
      TypeVariable t = (TypeVariable) T;
      if (t.getNumTypeBound() == 0) {
        return true;
      }
      for (int i = 0; i < t.getNumTypeBound(); i++) {
        if (castingConversionTo(t.getTypeBound(i).type())) {
          return true;
        }
      }
      return false;
    }
    if (T.isClassDecl() && (S.erasure() != S || T.erasure() != T)) {
        return S.erasure().castingConversionTo(T.erasure());
    }
    return refined_TypeConversion_ClassDecl_castingConversionTo_TypeDecl(type);
  }
  @ASTNodeAnnotation.Attribute
  public Constant cast(Constant c) {
    Constant cast_Constant_value = Constant.create(c.stringValue());

    return cast_Constant_value;
  }
  @ASTNodeAnnotation.Attribute
  public Constant add(Constant c1, Constant c2) {
    Constant add_Constant_Constant_value = Constant.create(c1.stringValue() + c2.stringValue());

    return add_Constant_Constant_value;
  }
  @ASTNodeAnnotation.Attribute
  public Constant questionColon(Constant cond, Constant c1, Constant c2) {
    Constant questionColon_Constant_Constant_Constant_value = Constant.create(cond.booleanValue() ? c1.stringValue() : c2.stringValue());

    return questionColon_Constant_Constant_Constant_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean eqIsTrue(Expr left, Expr right) {
    boolean eqIsTrue_Expr_Expr_value = isString() && left.constant().stringValue().equals(right.constant().stringValue());

    return eqIsTrue_Expr_Expr_value;
  }
  @ASTNodeAnnotation.Attribute
  public int lineNumber() {
    int lineNumber_value = getLine(IDstart);

    return lineNumber_value;
  }
  @ASTNodeAnnotation.Attribute
  public Collection lookupSuperConstructor() {
    Collection lookupSuperConstructor_value = hasSuperclass() ? superclass().constructors() : Collections.EMPTY_LIST;

    return lookupSuperConstructor_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean constructors_computed = false;
  /**
   * @apilevel internal
   */
  protected Collection<ConstructorDecl> constructors_value;
  /**
   * @apilevel internal
   */
  private void constructors_reset() {
    constructors_computed = false;
    constructors_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public Collection<ConstructorDecl> constructors() {
    ASTNode$State state = state();
    if (constructors_computed) {
      return constructors_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    constructors_value = constructors_compute();
    if (isFinal && num == state().boundariesCrossed) {
      constructors_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return constructors_value;
  }
  /**
   * @apilevel internal
   */
  private Collection<ConstructorDecl> constructors_compute() {
      Collection<ConstructorDecl> c = super.constructors();
      if (hasImplicitConstructor()) {
        c.add(getImplicitConstructor());
      }
      return c;
    }
  @ASTNodeAnnotation.Attribute
  public boolean needsImplicitConstructor() {
    boolean needsImplicitConstructor_value = compilationUnit().fromSource() && !hasExplicitConstructor();

    return needsImplicitConstructor_value;
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
        Modifiers m = new Modifiers();
        if (isPublic()) {
          m.addModifier(new Modifier("public"));
        } else if (isProtected()) {
          m.addModifier(new Modifier("protected"));
        } else if (isPrivate()) {
          m.addModifier(new Modifier("private"));
        }
        ConstructorDecl constructor = new ConstructorDecl(
            m,
            name(),
            new List(),
            new List(),
            new Opt(),
            new Block()
        );
        constructor.setParsedConstructorInvocation(
          new ExprStmt(
            new SuperConstructorAccess("super", new List())
          )
        );
        constructor.setImplicitConstructor();
        return new Opt<ConstructorDecl>(constructor);
      } else {
        return new Opt<ConstructorDecl>();
      }
    }
  /**
   * @attribute syn
   * @aspect ImplicitConstructor
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupConstructor.jrag:323
   */
  @ASTNodeAnnotation.Attribute
  public boolean hasExplicitConstructor() {
    {
        for (int i = 0; i < getNumBodyDecl(); i++) {
          if (getBodyDecl(i) instanceof ConstructorDecl) {
            return true;
          }
        }
        return false;
      }
  }
  /**
   * @attribute syn
   * @aspect MemberMethods
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupMethod.jrag:406
   */
  @ASTNodeAnnotation.Attribute
  public SimpleSet interfacesMethodsSignature(String signature) {
    {
        SimpleSet set = interfacesMethodsSignatureMap().get(signature);
        if (set != null) {
          return set;
        } else {
          return SimpleSet.emptySet;
        }
      }
  }
  /**
   * @apilevel internal
   */
  protected boolean methodsSignatureMap_computed = false;
  /**
   * @apilevel internal
   */
  protected Map<String,SimpleSet> methodsSignatureMap_value;
  /**
   * @apilevel internal
   */
  private void methodsSignatureMap_reset() {
    methodsSignatureMap_computed = false;
    methodsSignatureMap_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public Map<String,SimpleSet> methodsSignatureMap() {
    ASTNode$State state = state();
    if (methodsSignatureMap_computed) {
      return methodsSignatureMap_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    methodsSignatureMap_value = methodsSignatureMap_compute();
    if (isFinal && num == state().boundariesCrossed) {
      methodsSignatureMap_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return methodsSignatureMap_value;
  }
  /**
   * @apilevel internal
   */
  private Map<String,SimpleSet> methodsSignatureMap_compute() {
      Map<String,SimpleSet> localMap = localMethodsSignatureMap();
      Map<String,SimpleSet> map = new HashMap<String,SimpleSet>(localMap);
      HashMap fromSuperClass = new HashMap();
      if (hasSuperclass()) {
        for (Iterator<MethodDecl> iter = superclass().methodsIterator(); iter.hasNext(); ) {
          MethodDecl m = iter.next();
          if (!m.isPrivate() && m.accessibleFrom(this) && !localMap.containsKey(m.signature())) {
            putSimpleSetElement(map, m.signature(), m);
            if (!m.isAbstract()) {
              putSimpleSetElement(fromSuperClass, m.signature(), m);
            }
          }
        }
      }
      for (Iterator<MethodDecl> iter = interfacesMethodsIterator(); iter.hasNext(); ) {
        MethodDecl m = iter.next();
        if (!m.isStatic()
            && m.accessibleFrom(this) && !localMap.containsKey(m.signature())
            && !hasOverridingMethodInSuper(m)) {
          if (!fromSuperClass.containsKey(m.signature())) {
            putSimpleSetElement(map, m.signature(), m);
          }
        }
      }
      return map;
    }
  /**
   * @apilevel internal
   */
  protected java.util.Map ancestorMethods_String_values;
  /**
   * @apilevel internal
   */
  private void ancestorMethods_String_reset() {
    ancestorMethods_String_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public SimpleSet ancestorMethods(String signature) {
    Object _parameters = signature;
    if (ancestorMethods_String_values == null) ancestorMethods_String_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (ancestorMethods_String_values.containsKey(_parameters)) {
      return (SimpleSet) ancestorMethods_String_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    SimpleSet ancestorMethods_String_value = ancestorMethods_compute(signature);
    if (isFinal && num == state().boundariesCrossed) {
      ancestorMethods_String_values.put(_parameters, ancestorMethods_String_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return ancestorMethods_String_value;
  }
  /**
   * @apilevel internal
   */
  private SimpleSet ancestorMethods_compute(String signature) {
      SimpleSet set = SimpleSet.emptySet;
      if (hasSuperclass()) {
        for (Iterator iter = superclass().localMethodsSignature(signature).iterator(); iter.hasNext(); ) {
          MethodDecl m = (MethodDecl) iter.next();
          if (!m.isPrivate()) {
            set = set.add(m);
          }
        }
      }
      // always add interface methods to the ancestorMethods set so that their
      // access modifiers are checked against local overriding methods
      for (Iterator iter = interfacesMethodsSignature(signature).iterator(); iter.hasNext(); ) {
        MethodDecl m = (MethodDecl) iter.next();
        set = set.add(m);
      }
      if (!hasSuperclass()) {
        return set;
      }
      if (set.size() == 1) {
        MethodDecl m = (MethodDecl) set.iterator().next();
        if (!m.isAbstract()) {
          boolean done = true;
          for (Iterator iter = superclass().ancestorMethods(signature).iterator(); iter.hasNext(); ) {
            MethodDecl n = (MethodDecl) iter.next();
            if (n.isPrivate() || !n.accessibleFrom(m.hostType())) {
              done = false;
            }
          }
          if (done) {
            return set;
          }
        }
      }
      for (Iterator iter = superclass().ancestorMethods(signature).iterator(); iter.hasNext(); ) {
        MethodDecl m = (MethodDecl) iter.next();
        set = set.add(m);
      }
      return set;
    }
  /**
   * @apilevel internal
   */
  protected java.util.Map memberTypes_String_values;
  /**
   * @apilevel internal
   */
  private void memberTypes_String_reset() {
    memberTypes_String_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public SimpleSet memberTypes(String name) {
    Object _parameters = name;
    if (memberTypes_String_values == null) memberTypes_String_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (memberTypes_String_values.containsKey(_parameters)) {
      return (SimpleSet) memberTypes_String_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    SimpleSet memberTypes_String_value = memberTypes_compute(name);
    if (isFinal && num == state().boundariesCrossed) {
      memberTypes_String_values.put(_parameters, memberTypes_String_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return memberTypes_String_value;
  }
  /**
   * @apilevel internal
   */
  private SimpleSet memberTypes_compute(String name) {
      SimpleSet set = localTypeDecls(name);
      if (!set.isEmpty()) {
        return set;
      }
      for (Iterator<TypeDecl> outerIter = interfacesIterator(); outerIter.hasNext(); ) {
        TypeDecl type = outerIter.next();
        for (Iterator iter = type.memberTypes(name).iterator(); iter.hasNext(); ) {
          TypeDecl decl = (TypeDecl) iter.next();
          if (!decl.isPrivate() && decl.accessibleFrom(this)) {
            set = set.add(decl);
          }
        }
      }
      if (hasSuperclass()) {
        for (Iterator iter = superclass().memberTypes(name).iterator(); iter.hasNext(); ) {
          TypeDecl decl = (TypeDecl) iter.next();
          if (!decl.isPrivate() && decl.accessibleFrom(this)) {
            set = set.add(decl);
          }
        }
      }
      return set;
    }
  /**
   * @apilevel internal
   */
  protected boolean memberFieldsMap_computed = false;
  /**
   * @apilevel internal
   */
  protected HashMap memberFieldsMap_value;
  /**
   * @apilevel internal
   */
  private void memberFieldsMap_reset() {
    memberFieldsMap_computed = false;
    memberFieldsMap_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public HashMap memberFieldsMap() {
    ASTNode$State state = state();
    if (memberFieldsMap_computed) {
      return memberFieldsMap_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    memberFieldsMap_value = memberFieldsMap_compute();
    if (isFinal && num == state().boundariesCrossed) {
      memberFieldsMap_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return memberFieldsMap_value;
  }
  /**
   * @apilevel internal
   */
  private HashMap memberFieldsMap_compute() {
      HashMap map = new HashMap(localFieldsMap());
      if (hasSuperclass()) {
        for (Iterator iter = superclass().fieldsIterator(); iter.hasNext(); ) {
          FieldDeclaration decl = (FieldDeclaration) iter.next();
          if (!decl.isPrivate() && decl.accessibleFrom(this) && !localFieldsMap().containsKey(decl.name())) {
            putSimpleSetElement(map, decl.name(), decl);
          }
        }
      }
      for (Iterator<TypeDecl> outerIter = interfacesIterator(); outerIter.hasNext(); ) {
        TypeDecl type = outerIter.next();
        for (Iterator iter = type.fieldsIterator(); iter.hasNext(); ) {
          FieldDeclaration decl = (FieldDeclaration) iter.next();
          if (!decl.isPrivate() && decl.accessibleFrom(this) && !localFieldsMap().containsKey(decl.name())) {
            putSimpleSetElement(map, decl.name(), decl);
          }
        }
      }
      return map;
    }
  /**
   * @apilevel internal
   */
  protected java.util.Map memberFields_String_values;
  /**
   * @apilevel internal
   */
  private void memberFields_String_reset() {
    memberFields_String_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public SimpleSet memberFields(String name) {
    Object _parameters = name;
    if (memberFields_String_values == null) memberFields_String_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (memberFields_String_values.containsKey(_parameters)) {
      return (SimpleSet) memberFields_String_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    SimpleSet memberFields_String_value = memberFields_compute(name);
    if (isFinal && num == state().boundariesCrossed) {
      memberFields_String_values.put(_parameters, memberFields_String_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return memberFields_String_value;
  }
  /**
   * @apilevel internal
   */
  private SimpleSet memberFields_compute(String name) {
      SimpleSet fields = localFields(name);
      if (!fields.isEmpty()) {
        return fields; // this causes hiding of fields in superclass and interfaces
      }
      if (hasSuperclass()) {
        for (Iterator iter = superclass().memberFields(name).iterator(); iter.hasNext(); ) {
          FieldDeclaration decl = (FieldDeclaration) iter.next();
          if (!decl.isPrivate() && decl.accessibleFrom(this)) {
            fields = fields.add(decl);
          }
        }
      }
      for (Iterator<TypeDecl> outerIter = interfacesIterator(); outerIter.hasNext(); ) {
        TypeDecl type = outerIter.next();
        for (Iterator iter = type.memberFields(name).iterator(); iter.hasNext(); ) {
          FieldDeclaration decl = (FieldDeclaration) iter.next();
          if (!decl.isPrivate() && decl.accessibleFrom(this)) {
            fields = fields.add(decl);
          }
        }
      }
      return fields;
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
      Collection c = new ArrayList();
      for (Iterator iter = interfacesMethodsIterator(); iter.hasNext(); ) {
        MethodDecl m = (MethodDecl) iter.next();
        boolean implemented = false;
        SimpleSet set = (SimpleSet) localMethodsSignature(m.signature());
        if (set.size() == 1) {
          MethodDecl n = (MethodDecl) set.iterator().next();
          if (!n.isAbstract()) {
            implemented = true;
          }
        }
        if (!implemented) {
          set = ancestorMethods(m.signature());
          for (Iterator i2 = set.iterator(); i2.hasNext(); ) {
            MethodDecl n = (MethodDecl) i2.next();
            if (!n.isAbstract() && n.isPublic()) {
              implemented = true;
              break;
            }
          }
        }
        if (!implemented) {
          c.add(m);
        }
      }
  
      if (hasSuperclass()) {
        for (Iterator iter = superclass().unimplementedMethods().iterator(); iter.hasNext(); ) {
          MethodDecl m = (MethodDecl) iter.next();
          SimpleSet set = (SimpleSet) localMethodsSignature(m.signature());
          if (set.size() == 1) {
            MethodDecl n = (MethodDecl) set.iterator().next();
            if (n.isAbstract() || !n.overrides(m)) {
              c.add(m);
            }
          } else {
            c.add(m);
          }
        }
      }
  
      for (Iterator iter = localMethodsIterator(); iter.hasNext(); ) {
        MethodDecl m = (MethodDecl) iter.next();
        if (m.isAbstract()) {
          c.add(m);
        }
      }
      return c;
    }
  /**
   * @apilevel internal
   */
  protected boolean hasAbstract_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean hasAbstract_value;
  /**
   * @apilevel internal
   */
  private void hasAbstract_reset() {
    hasAbstract_computed = false;
  }
  @ASTNodeAnnotation.Attribute
  public boolean hasAbstract() {
    ASTNode$State state = state();
    if (hasAbstract_computed) {
      return hasAbstract_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    hasAbstract_value = !unimplementedMethods().isEmpty();
    if (isFinal && num == state().boundariesCrossed) {
      hasAbstract_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return hasAbstract_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean hasModifiers() {
    boolean hasModifiers_value = getModifiers().getNumModifier() > 0;

    return hasModifiers_value;
  }
  /**
   * @apilevel internal
   */
  protected java.util.Map castingConversionTo_TypeDecl_values;
  /**
   * @apilevel internal
   */
  private void castingConversionTo_TypeDecl_reset() {
    castingConversionTo_TypeDecl_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean castingConversionTo(TypeDecl type) {
    Object _parameters = type;
    if (castingConversionTo_TypeDecl_values == null) castingConversionTo_TypeDecl_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (castingConversionTo_TypeDecl_values.containsKey(_parameters)) {
      return (Boolean) castingConversionTo_TypeDecl_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean castingConversionTo_TypeDecl_value = castingConversionTo_compute(type);
    if (isFinal && num == state().boundariesCrossed) {
      castingConversionTo_TypeDecl_values.put(_parameters, castingConversionTo_TypeDecl_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return castingConversionTo_TypeDecl_value;
  }
  /**
   * @apilevel internal
   */
  private boolean castingConversionTo_compute(TypeDecl type) {
      if (refined_Generics_ClassDecl_castingConversionTo_TypeDecl(type)) {
        return true;
      }
      boolean canUnboxThis = !unboxed().isUnknown();
      boolean canUnboxType = !type.unboxed().isUnknown();
      if (canUnboxThis && !canUnboxType) {
        return unboxed().wideningConversionTo(type);
      }
      return false;
      /*
      else if (unboxingConversionTo(type)) {
        return true;
      }
      return false;
      */
    }
  @ASTNodeAnnotation.Attribute
  public boolean isClassDecl() {
    boolean isClassDecl_value = true;

    return isClassDecl_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean isString_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean isString_value;
  /**
   * @apilevel internal
   */
  private void isString_reset() {
    isString_computed = false;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isString() {
    ASTNode$State state = state();
    if (isString_computed) {
      return isString_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    isString_value = fullName().equals("java.lang.String");
    if (isFinal && num == state().boundariesCrossed) {
      isString_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return isString_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean isObject_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean isObject_value;
  /**
   * @apilevel internal
   */
  private void isObject_reset() {
    isObject_computed = false;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isObject() {
    ASTNode$State state = state();
    if (isObject_computed) {
      return isObject_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    isObject_value = name().equals("Object") && packageName().equals("java.lang");
    if (isFinal && num == state().boundariesCrossed) {
      isObject_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return isObject_value;
  }
  /**
   * @apilevel internal
   */
  protected java.util.Map instanceOf_TypeDecl_values;
  /**
   * @apilevel internal
   */
  private void instanceOf_TypeDecl_reset() {
    instanceOf_TypeDecl_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean instanceOf(TypeDecl type) {
    Object _parameters = type;
    if (instanceOf_TypeDecl_values == null) instanceOf_TypeDecl_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (instanceOf_TypeDecl_values.containsKey(_parameters)) {
      return (Boolean) instanceOf_TypeDecl_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean instanceOf_TypeDecl_value = instanceOf_compute(type);
    if (isFinal && num == state().boundariesCrossed) {
      instanceOf_TypeDecl_values.put(_parameters, instanceOf_TypeDecl_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return instanceOf_TypeDecl_value;
  }
  /**
   * @apilevel internal
   */
  private boolean instanceOf_compute(TypeDecl type) { return subtype(type); }
  /**
   * @attribute syn
   * @aspect TypeWideningAndIdentity
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeAnalysis.jrag:458
   */
  @ASTNodeAnnotation.Attribute
  public boolean isSupertypeOfClassDecl(ClassDecl type) {
    {
        if (super.isSupertypeOfClassDecl(type)) {
          return true;
        }
        return type.hasSuperclass() && type.superclass().instanceOf(this);
      }
  }
  @ASTNodeAnnotation.Attribute
  public boolean isSupertypeOfInterfaceDecl(InterfaceDecl type) {
    boolean isSupertypeOfInterfaceDecl_InterfaceDecl_value = isObject();

    return isSupertypeOfInterfaceDecl_InterfaceDecl_value;
  }
  /**
   * @attribute syn
   * @aspect TypeWideningAndIdentity
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeAnalysis.jrag:493
   */
  @ASTNodeAnnotation.Attribute
  public boolean isSupertypeOfArrayDecl(ArrayDecl type) {
    {
        if (super.isSupertypeOfArrayDecl(type)) {
          return true;
        }
        return type.hasSuperclass() && type.superclass().instanceOf(this);
      }
  }
  @ASTNodeAnnotation.Attribute
  public boolean isInnerClass() {
    boolean isInnerClass_value = isNestedType() && !isStatic() && enclosingType().isClassDecl();

    return isInnerClass_value;
  }
  /**
   * @apilevel internal
   */
  protected int isCircular_visited = -1;
  /**
   * @apilevel internal
   */
  private void isCircular_reset() {
    isCircular_computed = false;
    isCircular_initialized = false;
    isCircular_visited = -1;
  }
  /**
   * @apilevel internal
   */
  protected boolean isCircular_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean isCircular_initialized = false;
  /**
   * @apilevel internal
   */
  protected boolean isCircular_value;
  @ASTNodeAnnotation.Attribute
  public boolean isCircular() {
    if (isCircular_computed) {
      return isCircular_value;
    }
    ASTNode$State state = state();
    boolean new_isCircular_value;
    if (!isCircular_initialized) {
      isCircular_initialized = true;
      isCircular_value = true;
    }
    if (!state.IN_CIRCLE) {
      state.IN_CIRCLE = true;
      int num = state.boundariesCrossed;
      boolean isFinal = this.is$Final();
      do {
        isCircular_visited = state.CIRCLE_INDEX;
        state.CHANGE = false;
        new_isCircular_value = isCircular_compute();
        if (new_isCircular_value != isCircular_value) {
          state.CHANGE = true;
        }
        isCircular_value = new_isCircular_value;
        state.CIRCLE_INDEX++;
      } while (state.CHANGE);
      if (isFinal && num == state().boundariesCrossed) {
        isCircular_computed = true;
      } else {
        state.RESET_CYCLE = true;
        boolean $tmp = isCircular_compute();
        state.RESET_CYCLE = false;
        isCircular_computed = false;
        isCircular_initialized = false;
      }
      state.IN_CIRCLE = false;
      state.INTERMEDIATE_VALUE = false;
      return isCircular_value;
    }
    if (isCircular_visited != state.CIRCLE_INDEX) {
      isCircular_visited = state.CIRCLE_INDEX;
      if (state.RESET_CYCLE) {
        isCircular_computed = false;
        isCircular_initialized = false;
        isCircular_visited = -1;
        return isCircular_value;
      }
      new_isCircular_value = isCircular_compute();
      if (new_isCircular_value != isCircular_value) {
        state.CHANGE = true;
      }
      isCircular_value = new_isCircular_value;
      state.INTERMEDIATE_VALUE = true;
      return isCircular_value;
    }
    state.INTERMEDIATE_VALUE = true;
    return isCircular_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isCircular_compute() {
      if (hasSuperClass()) {
        Access a = getSuperClass().lastAccess();
        while (a != null) {
          if (a.type().isCircular()) {
            return true;
          }
          a = (a.isQualified() && a.qualifier().isTypeAccess()) ? (Access) a.qualifier() : null;
        }
      }
      for (int i = 0; i < getNumImplements(); i++) {
        Access a = getImplements(i).lastAccess();
        while (a != null) {
          if (a.type().isCircular()) {
            return true;
          }
          a = (a.isQualified() && a.qualifier().isTypeAccess()) ? (Access) a.qualifier() : null;
        }
      }
      return false;
    }
  /**
   * @attribute syn
   * @aspect Annotations
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Annotations.jrag:292
   */
  @ASTNodeAnnotation.Attribute
  public Annotation annotation(TypeDecl typeDecl) {
    {
        Annotation a = super.annotation(typeDecl);
        if (a != null) {
          return a;
        }
        if (hasSuperclass()) {
          // If the queried annotation is itself annotation with @Inherited then
          // delegate the query to the superclass
          if (typeDecl.annotation(lookupType("java.lang.annotation", "Inherited")) != null) {
            return superclass().annotation(typeDecl);
          }
        }
        return null;
      }
  }
  /**
   * @apilevel internal
   */
  protected boolean erasedAncestorMethodsMap_computed = false;
  /**
   * @apilevel internal
   */
  protected Map<String,SimpleSet> erasedAncestorMethodsMap_value;
  /**
   * @apilevel internal
   */
  private void erasedAncestorMethodsMap_reset() {
    erasedAncestorMethodsMap_computed = false;
    erasedAncestorMethodsMap_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public Map<String,SimpleSet> erasedAncestorMethodsMap() {
    ASTNode$State state = state();
    if (erasedAncestorMethodsMap_computed) {
      return erasedAncestorMethodsMap_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    erasedAncestorMethodsMap_value = erasedAncestorMethodsMap_compute();
    if (isFinal && num == state().boundariesCrossed) {
      erasedAncestorMethodsMap_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return erasedAncestorMethodsMap_value;
  }
  /**
   * @apilevel internal
   */
  private Map<String,SimpleSet> erasedAncestorMethodsMap_compute() {
      Map<String,SimpleSet> localMap = localMethodsSignatureMap();
      Map<String,SimpleSet> map = new HashMap<String,SimpleSet>();
      if (hasSuperclass()) {
        for (Iterator<MethodDecl> iter = superclass().localMethodsIterator(); iter.hasNext(); ) {
          MethodDecl m = iter.next();
          if (!m.isPrivate() && m.accessibleFrom(this) && m.erasedMethod() != m) {
            // map erased signature to substituted method
            putSimpleSetElement(map, m.erasedMethod().signature(), m);
          }
        }
        mergeMap(map, superclass().erasedAncestorMethodsMap());
      }
      for (Iterator<MethodDecl> iter = interfacesMethodsIterator(); iter.hasNext(); ) {
        MethodDecl m = iter.next();
        if (m.accessibleFrom(this) && m.erasedMethod() != m) {
          String erasedSignature = m.erasedMethod().signature();
          // map erased signature to substituted method
          putSimpleSetElement(map, erasedSignature, m);
        }
      }
      return map;
    }
  /**
   * @apilevel internal
   */
  protected boolean implementedInterfaces_computed = false;
  /**
   * @apilevel internal
   */
  protected HashSet<InterfaceDecl> implementedInterfaces_value;
  /**
   * @apilevel internal
   */
  private void implementedInterfaces_reset() {
    implementedInterfaces_computed = false;
    implementedInterfaces_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public HashSet<InterfaceDecl> implementedInterfaces() {
    ASTNode$State state = state();
    if (implementedInterfaces_computed) {
      return implementedInterfaces_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    implementedInterfaces_value = implementedInterfaces_compute();
    if (isFinal && num == state().boundariesCrossed) {
      implementedInterfaces_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return implementedInterfaces_value;
  }
  /**
   * @apilevel internal
   */
  private HashSet<InterfaceDecl> implementedInterfaces_compute() {
      HashSet set = new HashSet();
      if (hasSuperclass()) {
        set.addAll(superclass().implementedInterfaces());
      }
      for (Iterator<TypeDecl> iter = interfacesIterator(); iter.hasNext(); ) {
        InterfaceDecl decl = (InterfaceDecl) iter.next();
        set.add(decl);
        set.addAll(decl.implementedInterfaces());
      }
      return set;
    }
  /**
   * @apilevel internal
   */
  private void subtype_TypeDecl_reset() {
    subtype_TypeDecl_values = null;
  }
  protected java.util.Map subtype_TypeDecl_values;
  @ASTNodeAnnotation.Attribute
  public boolean subtype(TypeDecl type) {
    Object _parameters = type;
    if (subtype_TypeDecl_values == null) subtype_TypeDecl_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State.CircularValue _value;
    if (subtype_TypeDecl_values.containsKey(_parameters)) {
      Object _o = subtype_TypeDecl_values.get(_parameters);
      if (!(_o instanceof ASTNode$State.CircularValue)) {
        return (Boolean) _o;
      } else {
        _value = (ASTNode$State.CircularValue) _o;
      }
    } else {
      _value = new ASTNode$State.CircularValue();
      subtype_TypeDecl_values.put(_parameters, _value);
      _value.value = true;
    }
    ASTNode$State state = state();
    boolean new_subtype_TypeDecl_value;
    if (!state.IN_CIRCLE) {
      state.IN_CIRCLE = true;
      int num = state.boundariesCrossed;
      boolean isFinal = this.is$Final();
      // TODO: fixme
      // state().CIRCLE_INDEX = 1;
      do {
        _value.visited = state.CIRCLE_INDEX;
        state.CHANGE = false;
        new_subtype_TypeDecl_value = type.supertypeClassDecl(this);
        if (new_subtype_TypeDecl_value != ((Boolean)_value.value)) {
          state.CHANGE = true;
          _value.value = new_subtype_TypeDecl_value;
        }
        state.CIRCLE_INDEX++;
      } while (state.CHANGE);
      if (isFinal && num == state().boundariesCrossed) {
        subtype_TypeDecl_values.put(_parameters, new_subtype_TypeDecl_value);
      } else {
        subtype_TypeDecl_values.remove(_parameters);
        state.RESET_CYCLE = true;
        boolean $tmp = type.supertypeClassDecl(this);
        state.RESET_CYCLE = false;
      }
      state.IN_CIRCLE = false;
      state.INTERMEDIATE_VALUE = false;
      return new_subtype_TypeDecl_value;
    }
    if (state.CIRCLE_INDEX != _value.visited) {
      _value.visited = state.CIRCLE_INDEX;
      new_subtype_TypeDecl_value = type.supertypeClassDecl(this);
      if (state.RESET_CYCLE) {
        subtype_TypeDecl_values.remove(_parameters);
      }
      else if (new_subtype_TypeDecl_value != ((Boolean)_value.value)) {
        state.CHANGE = true;
        _value.value = new_subtype_TypeDecl_value;
      }
      state.INTERMEDIATE_VALUE = true;
      return new_subtype_TypeDecl_value;
    }
    state.INTERMEDIATE_VALUE = true;
    return (Boolean) _value.value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean supertypeClassDecl(ClassDecl type) {
    boolean supertypeClassDecl_ClassDecl_value = super.supertypeClassDecl(type) || type.hasSuperclass() && type.superclass().subtype(this);

    return supertypeClassDecl_ClassDecl_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean supertypeInterfaceDecl(InterfaceDecl type) {
    boolean supertypeInterfaceDecl_InterfaceDecl_value = isObject();

    return supertypeInterfaceDecl_InterfaceDecl_value;
  }
  /**
   * @attribute syn
   * @aspect GenericsSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\GenericsSubtype.jrag:480
   */
  @ASTNodeAnnotation.Attribute
  public boolean supertypeArrayDecl(ArrayDecl type) {
    {
        if (super.supertypeArrayDecl(type)) {
          return true;
        }
        return type.hasSuperclass() && type.superclass().subtype(this);
      }
  }
  @ASTNodeAnnotation.Attribute
  public boolean isReifiable() {
    boolean isReifiable_value = !isInnerClass() || enclosingType().isReifiable();

    return isReifiable_value;
  }
  /**
   * @apilevel internal
   */
  private void strictSubtype_TypeDecl_reset() {
    strictSubtype_TypeDecl_values = null;
  }
  protected java.util.Map strictSubtype_TypeDecl_values;
  @ASTNodeAnnotation.Attribute
  public boolean strictSubtype(TypeDecl type) {
    Object _parameters = type;
    if (strictSubtype_TypeDecl_values == null) strictSubtype_TypeDecl_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State.CircularValue _value;
    if (strictSubtype_TypeDecl_values.containsKey(_parameters)) {
      Object _o = strictSubtype_TypeDecl_values.get(_parameters);
      if (!(_o instanceof ASTNode$State.CircularValue)) {
        return (Boolean) _o;
      } else {
        _value = (ASTNode$State.CircularValue) _o;
      }
    } else {
      _value = new ASTNode$State.CircularValue();
      strictSubtype_TypeDecl_values.put(_parameters, _value);
      _value.value = true;
    }
    ASTNode$State state = state();
    boolean new_strictSubtype_TypeDecl_value;
    if (!state.IN_CIRCLE) {
      state.IN_CIRCLE = true;
      int num = state.boundariesCrossed;
      boolean isFinal = this.is$Final();
      // TODO: fixme
      // state().CIRCLE_INDEX = 1;
      do {
        _value.visited = state.CIRCLE_INDEX;
        state.CHANGE = false;
        new_strictSubtype_TypeDecl_value = type.strictSupertypeClassDecl(this);
        if (new_strictSubtype_TypeDecl_value != ((Boolean)_value.value)) {
          state.CHANGE = true;
          _value.value = new_strictSubtype_TypeDecl_value;
        }
        state.CIRCLE_INDEX++;
      } while (state.CHANGE);
      if (isFinal && num == state().boundariesCrossed) {
        strictSubtype_TypeDecl_values.put(_parameters, new_strictSubtype_TypeDecl_value);
      } else {
        strictSubtype_TypeDecl_values.remove(_parameters);
        state.RESET_CYCLE = true;
        boolean $tmp = type.strictSupertypeClassDecl(this);
        state.RESET_CYCLE = false;
      }
      state.IN_CIRCLE = false;
      state.INTERMEDIATE_VALUE = false;
      return new_strictSubtype_TypeDecl_value;
    }
    if (state.CIRCLE_INDEX != _value.visited) {
      _value.visited = state.CIRCLE_INDEX;
      new_strictSubtype_TypeDecl_value = type.strictSupertypeClassDecl(this);
      if (state.RESET_CYCLE) {
        strictSubtype_TypeDecl_values.remove(_parameters);
      }
      else if (new_strictSubtype_TypeDecl_value != ((Boolean)_value.value)) {
        state.CHANGE = true;
        _value.value = new_strictSubtype_TypeDecl_value;
      }
      state.INTERMEDIATE_VALUE = true;
      return new_strictSubtype_TypeDecl_value;
    }
    state.INTERMEDIATE_VALUE = true;
    return (Boolean) _value.value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean strictSupertypeClassDecl(ClassDecl type) {
    boolean strictSupertypeClassDecl_ClassDecl_value = super.strictSupertypeClassDecl(type) || type.hasSuperclass()
          && type.superclass() != null && type.superclass().strictSubtype(this);

    return strictSupertypeClassDecl_ClassDecl_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean strictSupertypeInterfaceDecl(InterfaceDecl type) {
    boolean strictSupertypeInterfaceDecl_InterfaceDecl_value = isObject();

    return strictSupertypeInterfaceDecl_InterfaceDecl_value;
  }
  /**
   * @attribute syn
   * @aspect StrictSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\GenericsSubtype.jrag:407
   */
  @ASTNodeAnnotation.Attribute
  public boolean strictSupertypeArrayDecl(ArrayDecl type) {
    {
        if (super.strictSupertypeArrayDecl(type)) {
          return true;
        }
        return type.hasSuperclass() && type.superclass() != null
            && type.superclass().strictSubtype(this);
      }
  }
  /**
   * @apilevel internal
   */
  protected java.util.Map hasOverridingMethodInSuper_MethodDecl_values;
  /**
   * @apilevel internal
   */
  private void hasOverridingMethodInSuper_MethodDecl_reset() {
    hasOverridingMethodInSuper_MethodDecl_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean hasOverridingMethodInSuper(MethodDecl m) {
    Object _parameters = m;
    if (hasOverridingMethodInSuper_MethodDecl_values == null) hasOverridingMethodInSuper_MethodDecl_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (hasOverridingMethodInSuper_MethodDecl_values.containsKey(_parameters)) {
      return (Boolean) hasOverridingMethodInSuper_MethodDecl_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean hasOverridingMethodInSuper_MethodDecl_value = hasOverridingMethodInSuper_compute(m);
    if (isFinal && num == state().boundariesCrossed) {
      hasOverridingMethodInSuper_MethodDecl_values.put(_parameters, hasOverridingMethodInSuper_MethodDecl_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return hasOverridingMethodInSuper_MethodDecl_value;
  }
  /**
   * @apilevel internal
   */
  private boolean hasOverridingMethodInSuper_compute(MethodDecl m) {
      for (Iterator<MethodDecl> outerIter = interfacesMethodsIterator(); outerIter.hasNext(); ) {
        MethodDecl superMethod = outerIter.next();
        if (m != superMethod && superMethod.overrides(m)) {
          return true;
        }
  
      }
      if (hasSuperclass()) {
        for (Iterator iter = superclass().methodsIterator(); iter.hasNext(); ) {
          MethodDecl superMethod = (MethodDecl) iter.next();
          if (m != superMethod && superMethod.overrides(m)) {
            return true;
          }
        }
      }
  
      return false;
    }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\GenericMethods.jrag:197
   * @apilevel internal
   */
  public SimpleSet Define_lookupType(ASTNode caller, ASTNode child, String name) {
    if (caller == getImplicitConstructorOptNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:474
      return localLookupType(name);
    }
    else {
      return super.Define_lookupType(caller, child, name);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\Modifiers.jrag:423
   * @apilevel internal
   */
  public boolean Define_mayBeFinal(ASTNode caller, ASTNode child) {
    if (caller == getModifiersNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\Modifiers.jrag:300
      return true;
    }
    else {
      return super.Define_mayBeFinal(caller, child);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\SyntacticClassification.jrag:36
   * @apilevel internal
   */
  public NameType Define_nameType(ASTNode caller, ASTNode child) {
    if (caller == getImplementsListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\SyntacticClassification.jrag:101
      int childIndex = caller.getIndexOfChild(child);
      return NameType.TYPE_NAME;
    }
    else if (caller == getSuperClassOptNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\SyntacticClassification.jrag:100
      return NameType.TYPE_NAME;
    }
    else {
      return super.Define_nameType(caller, child);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeAnalysis.jrag:544
   * @apilevel internal
   */
  public TypeDecl Define_enclosingType(ASTNode caller, ASTNode child) {
    if (caller == getImplicitConstructorOptNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeAnalysis.jrag:542
      return this;
    }
    else {
      return super.Define_enclosingType(caller, child);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\MultiCatch.jrag:71
   * @apilevel internal
   */
  public TypeDecl Define_hostType(ASTNode caller, ASTNode child) {
    if (caller == getImplementsListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeAnalysis.jrag:631
      int childIndex = caller.getIndexOfChild(child);
      return hostType();
    }
    else if (caller == getSuperClassOptNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeAnalysis.jrag:630
      return hostType();
    }
    else {
      return super.Define_hostType(caller, child);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\SuppressWarnings.jrag:38
   * @apilevel internal
   */
  public boolean Define_withinSuppressWarnings(ASTNode caller, ASTNode child, String annot) {
    if (caller == getImplementsListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Annotations.jrag:349
      int childIndex = caller.getIndexOfChild(child);
      return hasAnnotationSuppressWarnings(annot) || withinSuppressWarnings(annot);
    }
    else if (caller == getSuperClassOptNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Annotations.jrag:347
      return hasAnnotationSuppressWarnings(annot) || withinSuppressWarnings(annot);
    }
    else {
      return getParent().Define_withinSuppressWarnings(this, caller, annot);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Annotations.jrag:449
   * @apilevel internal
   */
  public boolean Define_withinDeprecatedAnnotation(ASTNode caller, ASTNode child) {
    if (caller == getImplementsListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Annotations.jrag:454
      int childIndex = caller.getIndexOfChild(child);
      return isDeprecated() || withinDeprecatedAnnotation();
    }
    else if (caller == getSuperClassOptNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Annotations.jrag:453
      return isDeprecated() || withinDeprecatedAnnotation();
    }
    else {
      return getParent().Define_withinDeprecatedAnnotation(this, caller);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:338
   * @apilevel internal
   */
  public boolean Define_inExtendsOrImplements(ASTNode caller, ASTNode child) {
    if (caller == getImplementsListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:336
      int i = caller.getIndexOfChild(child);
      return true;
    }
    else if (caller == getSuperClassOptNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:335
      return true;
    }
    else {
      return getParent().Define_inExtendsOrImplements(this, caller);
    }
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
