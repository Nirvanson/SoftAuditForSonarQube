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
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\grammar\\Java.ast:64
 * @production InterfaceDecl : {@link ReferenceType} ::= <span class="component">{@link Modifiers}</span> <span class="component">&lt;ID:String&gt;</span> <span class="component">SuperInterface:{@link Access}*</span> <span class="component">{@link BodyDecl}*</span>;

 */
public class InterfaceDecl extends ReferenceType implements Cloneable {
  /**
   * @aspect Java4PrettyPrint
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\PrettyPrint.jadd:325
   */
  public void prettyPrint(PrettyPrinter out) {
    if (hasDocComment()) {
      out.print(docComment());
    }
    if (!out.isNewLine()) {
      out.println();
    }
    out.print(getModifiers());
    out.print("interface ");
    out.print(getID());
    if (hasSuperInterface()) {
      out.print(" extends ");
      out.join(getSuperInterfaceList(), new PrettyPrinter.Joiner() {
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
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\AccessControl.jrag:197
   */
  public void accessControl() {
    super.accessControl();

    if (!isCircular()) {
      // 9.1.2
      HashSet set = new HashSet();
      for (int i = 0; i < getNumSuperInterface(); i++) {
        TypeDecl decl = getSuperInterface(i).type();

        if (!decl.isInterfaceDecl() && !decl.isUnknown()) {
          errorf("interface %s can not extend non interface type %s", fullName(), decl.fullName());
        }
        if (!decl.isCircular() && !decl.accessibleFrom(this)) {
          errorf("interface %s can not extend non accessible type %s", fullName(), decl.fullName());
        }
        if (set.contains(decl)) {
          errorf("extended interface %s is mentionened multiple times in extends clause",
              decl.fullName());
        }
        set.add(decl);
      }
    }
  }
  /**
   * @aspect Modifiers
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\Modifiers.jrag:132
   */
  public void checkModifiers() {
    super.checkModifiers();
  }
  /**
   * @aspect SuperClasses
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeAnalysis.jrag:718
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
        if (isCircular()) {
          return;
        }
        while (index < getNumSuperInterface()) {
          TypeDecl typeDecl = getSuperInterface(index++).type();
          if (!typeDecl.isCircular() && typeDecl.isInterfaceDecl()) {
            current = typeDecl;
            return;
          }
        }
      }
    };
  }
  /**
   * @aspect Generics
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:273
   */
  public TypeDecl makeGeneric(Signatures.ClassSignature s) {
    if (s.hasFormalTypeParameters()) {
      ASTNode node = getParent();
      int index = node.getIndexOfChild(this);
      node.setChild(
          new GenericInterfaceDecl(
            getModifiersNoTransform(),
            getID(),
            s.hasSuperinterfaceSignature()
                ? s.superinterfaceSignature()
                : getSuperInterfaceListNoTransform(),
            getBodyDeclListNoTransform(),
            s.typeParameters()
          ),
          index
      );
      return (TypeDecl) node.getChildNoTransform(index);
    } else {
      if (s.hasSuperinterfaceSignature()) {
        setSuperInterfaceList(s.superinterfaceSignature());
      }
      return this;
    }
  }
  /**
   * @aspect LookupParTypeDecl
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:1445
   */
  public InterfaceDecl substitutedInterfaceDecl(Parameterization parTypeDecl) {
    return new InterfaceDeclSubstituted(
      (Modifiers) getModifiers().treeCopyNoTransform(),
      getID(),
      getSuperInterfaceList().substitute(parTypeDecl),
      this
    );
  }
  /**
   * @declaredat ASTNode:1
   */
  public InterfaceDecl() {
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
    children = new ASTNode[3];
    setChild(new List(), 1);
    setChild(new List(), 2);
  }
  /**
   * @declaredat ASTNode:15
   */
  public InterfaceDecl(Modifiers p0, String p1, List<Access> p2, List<BodyDecl> p3) {
    setChild(p0, 0);
    setID(p1);
    setChild(p2, 1);
    setChild(p3, 2);
  }
  /**
   * @declaredat ASTNode:21
   */
  public InterfaceDecl(Modifiers p0, beaver.Symbol p1, List<Access> p2, List<BodyDecl> p3) {
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
    methodsSignatureMap_reset();
    ancestorMethods_String_reset();
    memberTypes_String_reset();
    memberFieldsMap_reset();
    memberFields_String_reset();
    isStatic_reset();
    castingConversionTo_TypeDecl_reset();
    instanceOf_TypeDecl_reset();
    isCircular_reset();
    erasedAncestorMethodsMap_reset();
    implementedInterfaces_reset();
    subtype_TypeDecl_reset();
    hasAnnotationFunctionalInterface_reset();
    isFunctionalInterface_reset();
    isFunctional_reset();
    collectAbstractMethods_reset();
    hasFunctionDescriptor_reset();
    functionDescriptor_reset();
    strictSubtype_TypeDecl_reset();
    hasOverridingMethodInSuper_MethodDecl_reset();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:68
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:74
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:80
   */
  public InterfaceDecl clone() throws CloneNotSupportedException {
    InterfaceDecl node = (InterfaceDecl) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:87
   */
  public InterfaceDecl copy() {
    try {
      InterfaceDecl node = (InterfaceDecl) clone();
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
   * @declaredat ASTNode:106
   */
  @Deprecated
  public InterfaceDecl fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:116
   */
  public InterfaceDecl treeCopyNoTransform() {
    InterfaceDecl tree = (InterfaceDecl) copy();
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
   * @declaredat ASTNode:136
   */
  public InterfaceDecl treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:143
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenString_ID == ((InterfaceDecl)node).tokenString_ID);    
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
   * Replaces the SuperInterface list.
   * @param list The new list node to be used as the SuperInterface list.
   * @apilevel high-level
   */
  public void setSuperInterfaceList(List<Access> list) {
    setChild(list, 1);
  }
  /**
   * Retrieves the number of children in the SuperInterface list.
   * @return Number of children in the SuperInterface list.
   * @apilevel high-level
   */
  public int getNumSuperInterface() {
    return getSuperInterfaceList().getNumChild();
  }
  /**
   * Retrieves the number of children in the SuperInterface list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the SuperInterface list.
   * @apilevel low-level
   */
  public int getNumSuperInterfaceNoTransform() {
    return getSuperInterfaceListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the SuperInterface list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the SuperInterface list.
   * @apilevel high-level
   */
  public Access getSuperInterface(int i) {
    return (Access) getSuperInterfaceList().getChild(i);
  }
  /**
   * Check whether the SuperInterface list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasSuperInterface() {
    return getSuperInterfaceList().getNumChild() != 0;
  }
  /**
   * Append an element to the SuperInterface list.
   * @param node The element to append to the SuperInterface list.
   * @apilevel high-level
   */
  public void addSuperInterface(Access node) {
    List<Access> list = (parent == null) ? getSuperInterfaceListNoTransform() : getSuperInterfaceList();
    list.addChild(node);
  }
  /**
   * @apilevel low-level
   */
  public void addSuperInterfaceNoTransform(Access node) {
    List<Access> list = getSuperInterfaceListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the SuperInterface list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setSuperInterface(Access node, int i) {
    List<Access> list = getSuperInterfaceList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the SuperInterface list.
   * @return The node representing the SuperInterface list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="SuperInterface")
  public List<Access> getSuperInterfaceList() {
    List<Access> list = (List<Access>) getChild(1);
    return list;
  }
  /**
   * Retrieves the SuperInterface list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the SuperInterface list.
   * @apilevel low-level
   */
  public List<Access> getSuperInterfaceListNoTransform() {
    return (List<Access>) getChildNoTransform(1);
  }
  /**
   * Retrieves the SuperInterface list.
   * @return The node representing the SuperInterface list.
   * @apilevel high-level
   */
  public List<Access> getSuperInterfaces() {
    return getSuperInterfaceList();
  }
  /**
   * Retrieves the SuperInterface list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the SuperInterface list.
   * @apilevel low-level
   */
  public List<Access> getSuperInterfacesNoTransform() {
    return getSuperInterfaceListNoTransform();
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
   * @aspect Java8NameCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\NameCheck.jrag:339
   */
   
  public void nameCheck() {
    super.nameCheck();

    //9.6.3.8
      if (hasAnnotationFunctionalInterface() && !isFunctional()) {
        errorf("%s is not a functional interface", name());
      }

    if (isCircular()) {
      errorf("circular inheritance dependency in %s", typeName());
    } else {
      for (int i = 0; i < getNumSuperInterface(); i++) {
        TypeDecl typeDecl = getSuperInterface(i).type();
        if (typeDecl.isCircular()) {
          errorf("circular inheritance dependency in %s", typeName());
        }
      }
    }
    for (Iterator<SimpleSet> iter = methodsSignatureMap().values().iterator(); iter.hasNext(); ) {
      SimpleSet set = iter.next();
      if (set.size() > 1) {
        Iterator i2 = set.iterator();
        MethodDecl m = (MethodDecl) i2.next();
        while (i2.hasNext()) {
          MethodDecl n = (MethodDecl) i2.next();
          checkInterfaceMethodDecls(m, n);
        }
      }
    }
  }
  /**
   * @aspect Generics
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:102
   */
  private boolean refined_Generics_InterfaceDecl_castingConversionTo_TypeDecl(TypeDecl type)
{
    TypeDecl S = this;
    TypeDecl T = type;
    if (T.isArrayDecl()) {
      return T.instanceOf(S);
    } else if (T.isReferenceType() && !T.isFinal()) {
      return true;
    } else {
      return T.instanceOf(S);
    }
  }
  @ASTNodeAnnotation.Attribute
  public Collection lookupSuperConstructor() {
    Collection lookupSuperConstructor_value = typeObject().constructors();

    return lookupSuperConstructor_value;
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
      for (Iterator<MethodDecl> iter = interfacesMethodsIterator(); iter.hasNext(); ) {
        MethodDecl m = iter.next();
        if (!m.isStatic() && m.accessibleFrom(this)
            && !localMap.containsKey(m.signature())
            && !hasOverridingMethodInSuper(m)) {
          putSimpleSetElement(map, m.signature(), m);
        }
      }
      for (Iterator<MethodDecl> iter = typeObject().methodsIterator(); iter.hasNext(); ) {
        MethodDecl m = iter.next();
        if (m.isPublic() && !map.containsKey(m.signature())) {
          putSimpleSetElement(map, m.signature(), m);
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
      for (Iterator<TypeDecl> outerIter = interfacesIterator(); outerIter.hasNext(); ) {
        TypeDecl typeDecl = outerIter.next();
        for (Iterator iter = typeDecl.methodsSignature(signature).iterator(); iter.hasNext(); ) {
          MethodDecl m = (MethodDecl) iter.next();
          set = set.add(m);
        }
      }
      if (!interfacesIterator().hasNext()) {
        for (Iterator iter = typeObject().methodsSignature(signature).iterator(); iter.hasNext(); ) {
          MethodDecl m = (MethodDecl) iter.next();
          if (m.isPublic()) {
            set = set.add(m);
          }
        }
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
        TypeDecl typeDecl = outerIter.next();
        for (Iterator iter = typeDecl.memberTypes(name).iterator(); iter.hasNext(); ) {
          TypeDecl decl = (TypeDecl) iter.next();
          if (!decl.isPrivate()) {
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
      for (Iterator<TypeDecl> outerIter = interfacesIterator(); outerIter.hasNext(); ) {
        TypeDecl typeDecl = outerIter.next();
        for (Iterator iter = typeDecl.fieldsIterator(); iter.hasNext(); ) {
          FieldDeclaration f = (FieldDeclaration) iter.next();
          if (f.accessibleFrom(this) && !f.isPrivate() && !localFieldsMap().containsKey(f.name())) {
            putSimpleSetElement(map, f.name(), f);
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
        return fields;
      }
      for (Iterator<TypeDecl> outerIter = interfacesIterator(); outerIter.hasNext(); ) {
        TypeDecl typeDecl = (TypeDecl) outerIter.next();
        for (Iterator iter = typeDecl.memberFields(name).iterator(); iter.hasNext(); ) {
          FieldDeclaration f = (FieldDeclaration) iter.next();
          if (f.accessibleFrom(this) && !f.isPrivate()) {
            fields = fields.add(f);
          }
        }
      }
      return fields;
    }
  @ASTNodeAnnotation.Attribute
  public boolean isAbstract() {
    boolean isAbstract_value = true;

    return isAbstract_value;
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
    isStatic_value = getModifiers().isStatic() || isMemberType();
    if (isFinal && num == state().boundariesCrossed) {
      isStatic_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return isStatic_value;
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
      if (refined_Generics_InterfaceDecl_castingConversionTo_TypeDecl(type)) {
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
  public boolean isInterfaceDecl() {
    boolean isInterfaceDecl_value = true;

    return isInterfaceDecl_value;
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
        for (Iterator<TypeDecl> iter = type.interfacesIterator(); iter.hasNext(); ) {
          TypeDecl typeDecl = (TypeDecl) iter.next();
          if (typeDecl.instanceOf(this)) {
            return true;
          }
        }
        return type.hasSuperclass() && type.superclass().instanceOf(this);
      }
  }
  /**
   * @attribute syn
   * @aspect TypeWideningAndIdentity
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeAnalysis.jrag:478
   */
  @ASTNodeAnnotation.Attribute
  public boolean isSupertypeOfInterfaceDecl(InterfaceDecl type) {
    {
        if (super.isSupertypeOfInterfaceDecl(type)) {
          return true;
        }
        for (Iterator<TypeDecl> iter = type.interfacesIterator(); iter.hasNext(); ) {
          TypeDecl superinterface = (TypeDecl) iter.next();
          if (superinterface.instanceOf(this)) {
            return true;
          }
        }
        return false;
      }
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
        for (Iterator<TypeDecl> iter = type.interfacesIterator(); iter.hasNext(); ) {
          TypeDecl typeDecl = (TypeDecl) iter.next();
          if (typeDecl.instanceOf(this)) {
            return true;
          }
        }
        return false;
      }
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
      for (int i = 0; i < getNumSuperInterface(); i++) {
        Access a = getSuperInterface(i).lastAccess();
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
      Map<String,SimpleSet> map = new HashMap<String,SimpleSet>(localMap);
      for (Iterator<MethodDecl> iter = interfacesMethodsIterator(); iter.hasNext(); ) {
        MethodDecl m = (MethodDecl) iter.next();
        if (m.accessibleFrom(this) && m.erasedMethod() != m) {
          String erasedSignature = m.erasedMethod().signature();
          if (!localMap.containsKey(erasedSignature)) {
            // map erased signature to substituted method
            putSimpleSetElement(map, m.erasedMethod().signature(), m);
          }
        }
      }
      for (Iterator<MethodDecl> iter = typeObject().methodsIterator(); iter.hasNext(); ) {
        MethodDecl m = (MethodDecl) iter.next();
        if (m.isPublic() && m.erasedMethod() != m) {
          String erasedSignature = m.erasedMethod().signature();
          if (!localMap.containsKey(erasedSignature)) {
            // map erased signature to substituted method
            putSimpleSetElement(map, m.erasedMethod().signature(), m);
          }
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
      HashSet<InterfaceDecl> set= new HashSet<InterfaceDecl>();
      set.addAll(typeObject().implementedInterfaces());
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
        new_subtype_TypeDecl_value = type.supertypeInterfaceDecl(this);
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
        boolean $tmp = type.supertypeInterfaceDecl(this);
        state.RESET_CYCLE = false;
      }
      state.IN_CIRCLE = false;
      state.INTERMEDIATE_VALUE = false;
      return new_subtype_TypeDecl_value;
    }
    if (state.CIRCLE_INDEX != _value.visited) {
      _value.visited = state.CIRCLE_INDEX;
      new_subtype_TypeDecl_value = type.supertypeInterfaceDecl(this);
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
  /**
   * @attribute syn
   * @aspect GenericsSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\GenericsSubtype.jrag:448
   */
  @ASTNodeAnnotation.Attribute
  public boolean supertypeClassDecl(ClassDecl type) {
    {
        if (super.supertypeClassDecl(type)) {
          return true;
        }
        for (Iterator<TypeDecl> iter = type.interfacesIterator(); iter.hasNext(); ) {
          TypeDecl typeDecl = iter.next();
          if (typeDecl.subtype(this)) {
            return true;
          }
        }
        return type.hasSuperclass() && type.superclass().subtype(this);
      }
  }
  /**
   * @attribute syn
   * @aspect GenericsSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\GenericsSubtype.jrag:465
   */
  @ASTNodeAnnotation.Attribute
  public boolean supertypeInterfaceDecl(InterfaceDecl type) {
    {
        if (super.supertypeInterfaceDecl(type)) {
          return true;
        }
        for (Iterator<TypeDecl> iter = type.interfacesIterator(); iter.hasNext(); ) {
          TypeDecl superinterface = iter.next();
          if (superinterface.subtype(this)) {
            return true;
          }
        }
        return false;
      }
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
        for (Iterator<TypeDecl> iter = type.interfacesIterator(); iter.hasNext(); ) {
          TypeDecl typeDecl = iter.next();
          if (typeDecl.subtype(this)) {
            return true;
          }
        }
        return false;
      }
  }
  /**
   * @apilevel internal
   */
  protected boolean hasAnnotationFunctionalInterface_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean hasAnnotationFunctionalInterface_value;
  /**
   * @apilevel internal
   */
  private void hasAnnotationFunctionalInterface_reset() {
    hasAnnotationFunctionalInterface_computed = false;
  }
  @ASTNodeAnnotation.Attribute
  public boolean hasAnnotationFunctionalInterface() {
    ASTNode$State state = state();
    if (hasAnnotationFunctionalInterface_computed) {
      return hasAnnotationFunctionalInterface_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    hasAnnotationFunctionalInterface_value = getModifiers().hasAnnotationFunctionalInterface();
    if (isFinal && num == state().boundariesCrossed) {
      hasAnnotationFunctionalInterface_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return hasAnnotationFunctionalInterface_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean isFunctionalInterface_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean isFunctionalInterface_value;
  /**
   * @apilevel internal
   */
  private void isFunctionalInterface_reset() {
    isFunctionalInterface_computed = false;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isFunctionalInterface() {
    ASTNode$State state = state();
    if (isFunctionalInterface_computed) {
      return isFunctionalInterface_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    isFunctionalInterface_value = isFunctional();
    if (isFinal && num == state().boundariesCrossed) {
      isFunctionalInterface_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return isFunctionalInterface_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean isFunctional_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean isFunctional_value;
  /**
   * @apilevel internal
   */
  private void isFunctional_reset() {
    isFunctional_computed = false;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isFunctional() {
    ASTNode$State state = state();
    if (isFunctional_computed) {
      return isFunctional_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    isFunctional_value = isFunctional_compute();
    if (isFinal && num == state().boundariesCrossed) {
      isFunctional_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return isFunctional_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isFunctional_compute() {
      LinkedList<MethodDecl> methods = collectAbstractMethods();
      boolean foundMethod = false;
  
      if (methods.size() == 0) {
        return false;
      } else if (methods.size() == 1) {
        return true;
      } else {
        for (MethodDecl current : methods) {
          foundMethod = true;
          for (MethodDecl inner : methods) {
            if (!current.subsignatureTo(inner) || !current.returnTypeSubstitutableFor(inner)) {
              foundMethod = false;
            }
          }
          if (foundMethod) {
            break;
          }
        }
      }
      return foundMethod;
    }
  /**
   * @apilevel internal
   */
  protected boolean collectAbstractMethods_computed = false;
  /**
   * @apilevel internal
   */
  protected LinkedList<MethodDecl> collectAbstractMethods_value;
  /**
   * @apilevel internal
   */
  private void collectAbstractMethods_reset() {
    collectAbstractMethods_computed = false;
    collectAbstractMethods_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public LinkedList<MethodDecl> collectAbstractMethods() {
    ASTNode$State state = state();
    if (collectAbstractMethods_computed) {
      return collectAbstractMethods_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    collectAbstractMethods_value = collectAbstractMethods_compute();
    if (isFinal && num == state().boundariesCrossed) {
      collectAbstractMethods_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return collectAbstractMethods_value;
  }
  /**
   * @apilevel internal
   */
  private LinkedList<MethodDecl> collectAbstractMethods_compute() {
      LinkedList<MethodDecl> methods = new LinkedList<MethodDecl>();
      Map<String, SimpleSet> map = localMethodsSignatureMap();
      Map<String, SimpleSet> objectMethods = typeObject().methodsSignatureMap();
      MethodDecl inObject;
  
      for (Map.Entry<String,SimpleSet> entry: map.entrySet()) {
        SimpleSet set = entry.getValue();
        MethodDecl m = (MethodDecl) set.iterator().next();
  
        SimpleSet objectSet = objectMethods.get(m.signature());
        if (m.isAbstract()) {
          if (objectSet == null || objectSet.isEmpty()) {
            methods.add(m);
          } else {
            inObject = (MethodDecl) objectSet.iterator().next();
            if (!inObject.isPublic()) {
              methods.add(m);
            }
          }
        }
      }
  
      for (Iterator outerIter = interfacesIterator(); outerIter.hasNext();) {
        TypeDecl typeDecl = (TypeDecl) outerIter.next();
        for (Iterator iter = typeDecl.methodsIterator(); iter.hasNext();) {
          MethodDecl m = (MethodDecl) iter.next();
  
          if (m.isAbstract() && !m.isPrivate() && m.accessibleFrom(this)) {
            SimpleSet objectSet = objectMethods.get(m.signature());
            if (objectSet == null || objectSet.isEmpty()) {
              methods.add(m);
            } else {
              inObject = (MethodDecl) objectSet.iterator().next();
              if (!inObject.isPublic()) {
                methods.add(m);
              }
            }
          }
        }
      }
      return methods;
    }
  /**
   * @apilevel internal
   */
  protected boolean hasFunctionDescriptor_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean hasFunctionDescriptor_value;
  /**
   * @apilevel internal
   */
  private void hasFunctionDescriptor_reset() {
    hasFunctionDescriptor_computed = false;
  }
  @ASTNodeAnnotation.Attribute
  public boolean hasFunctionDescriptor() {
    ASTNode$State state = state();
    if (hasFunctionDescriptor_computed) {
      return hasFunctionDescriptor_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    hasFunctionDescriptor_value = hasFunctionDescriptor_compute();
    if (isFinal && num == state().boundariesCrossed) {
      hasFunctionDescriptor_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return hasFunctionDescriptor_value;
  }
  /**
   * @apilevel internal
   */
  private boolean hasFunctionDescriptor_compute() {
      return functionDescriptor() != null;
    }
  /**
   * @apilevel internal
   */
  protected boolean functionDescriptor_computed = false;
  /**
   * @apilevel internal
   */
  protected FunctionDescriptor functionDescriptor_value;
  /**
   * @apilevel internal
   */
  private void functionDescriptor_reset() {
    functionDescriptor_computed = false;
    functionDescriptor_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public FunctionDescriptor functionDescriptor() {
    ASTNode$State state = state();
    if (functionDescriptor_computed) {
      return functionDescriptor_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    functionDescriptor_value = functionDescriptor_compute();
    if (isFinal && num == state().boundariesCrossed) {
      functionDescriptor_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return functionDescriptor_value;
  }
  /**
   * @apilevel internal
   */
  private FunctionDescriptor functionDescriptor_compute() {
      LinkedList<MethodDecl> methods = collectAbstractMethods();
  
      if (methods.size() == 0) {
        return null;
      } else if (methods.size() == 1) {
        MethodDecl m = methods.getFirst();
        FunctionDescriptor f = new FunctionDescriptor(this);
        f.method = m;
        ArrayList<TypeDecl> throwsList = new ArrayList<TypeDecl>();
        for (Access exception : m.getExceptionList()) {
          throwsList.add(exception.type());
        }
        f.throwsList = throwsList;
        return f;
      } else {
        FunctionDescriptor f = null;
        MethodDecl foundMethod = null;
  
        for (MethodDecl current : methods) {
          foundMethod = current;
          for (MethodDecl inner : methods) {
            if (!current.subsignatureTo(inner) || !current.returnTypeSubstitutableFor(inner)) {
              foundMethod = null;
            }
          }
          if (foundMethod != null) {
            break;
          }
        }
  
        ArrayList<Access> descriptorThrows = new ArrayList<Access>();
        if (foundMethod != null) {
          // Now the throws-list needs to be computed as stated in 9.8
          for (MethodDecl current : methods) {
            for (Access exception : current.getExceptionList()) {
              boolean alreadyInserted = false;
              for (Access found : descriptorThrows) {
                if (found.sameType(exception)) {
                  alreadyInserted = true;
                  break;
                }
              }
              if (alreadyInserted) {
                continue;
              }
  
              boolean foundIncompatibleClause = false;
              // Has to be the subtype to at least one exception in each clause
              if (foundMethod.isGeneric()) {
                for (MethodDecl inner : methods) {
                  if (!inner.subtypeThrowsClause(exception)) {
                    foundIncompatibleClause = true;
                    break;
                  }
                }
              } else {
                for (MethodDecl inner : methods) {
                  if (!inner.subtypeThrowsClauseErased(exception)) {
                    foundIncompatibleClause = true;
                    break;
                  }
                }
              }
  
              if (!foundIncompatibleClause) {
                // Was subtype to one exception in every clause
                descriptorThrows.add(exception);
              }
            }
          }
  
          /* Found a suitable method and finished building throws-list,
          now the descriptor just needs to be put together */
          f = new FunctionDescriptor(this);
          f.method = foundMethod;
          if (descriptorThrows.size() == 0) {
            f.throwsList = new ArrayList<TypeDecl>();
          } else {
            ArrayList<TypeDecl> throwsList = new ArrayList<TypeDecl>();
  
            /* All type variables must be replaced with foundMethods
                type variables if the descriptor is generic */
            if (foundMethod.isGeneric()) {
              GenericMethodDecl foundGeneric = foundMethod.genericDecl();
              for (Access exception : descriptorThrows) {
                if (exception.type() instanceof TypeVariable) {
                  TypeVariable foundVar = (TypeVariable) exception.type();
                  TypeVariable original = foundGeneric.getTypeParameter(foundVar.typeVarPosition());
                  throwsList.add(original);
                } else {
                  throwsList.add(exception.type());
                }
              }
            } else {
              // All throwed types must be erased if the descriptor is not generic.
              for (Access exception : descriptorThrows) {
                throwsList.add(exception.type().erasure());
              }
            }
            f.throwsList = throwsList;
          }
        }
        return f;
      }
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
        new_strictSubtype_TypeDecl_value = type.strictSupertypeInterfaceDecl(this);
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
        boolean $tmp = type.strictSupertypeInterfaceDecl(this);
        state.RESET_CYCLE = false;
      }
      state.IN_CIRCLE = false;
      state.INTERMEDIATE_VALUE = false;
      return new_strictSubtype_TypeDecl_value;
    }
    if (state.CIRCLE_INDEX != _value.visited) {
      _value.visited = state.CIRCLE_INDEX;
      new_strictSubtype_TypeDecl_value = type.strictSupertypeInterfaceDecl(this);
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
  /**
   * @attribute syn
   * @aspect StrictSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\GenericsSubtype.jrag:370
   */
  @ASTNodeAnnotation.Attribute
  public boolean strictSupertypeClassDecl(ClassDecl type) {
    {
        if (super.strictSupertypeClassDecl(type)) {
          return true;
        }
        for (Iterator<TypeDecl> iter = type.interfacesIterator(); iter.hasNext(); ) {
          TypeDecl typeDecl = iter.next();
          if (typeDecl.strictSubtype(this)) {
            return true;
          }
        }
        return type.hasSuperclass() && type.superclass() != null
            && type.superclass().strictSubtype(this);
      }
  }
  /**
   * @attribute syn
   * @aspect StrictSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\GenericsSubtype.jrag:390
   */
  @ASTNodeAnnotation.Attribute
  public boolean strictSupertypeInterfaceDecl(InterfaceDecl type) {
    {
        if (super.strictSupertypeInterfaceDecl(type)) {
          return true;
        }
        for (Iterator<TypeDecl> iter = type.interfacesIterator(); iter.hasNext(); ) {
          TypeDecl superinterface = iter.next();
          if (superinterface.strictSubtype(this)) {
            return true;
          }
        }
        return false;
      }
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
        for (Iterator<TypeDecl> iter = type.interfacesIterator(); iter.hasNext(); ) {
          TypeDecl typeDecl = iter.next();
          if (typeDecl.strictSubtype(this)) {
            return true;
          }
        }
        return false;
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
      for (Iterator<TypeDecl> outerIter = interfacesIterator(); outerIter.hasNext(); ) {
        TypeDecl typeDecl = outerIter.next();
        for (Iterator iter = typeDecl.methodsIterator(); iter.hasNext(); ) {
          MethodDecl superMethod = (MethodDecl) iter.next();
          if (m != superMethod && superMethod.overrides(m)) {
            return true;
          }
        }
      }
      return false;
    }
  /**
   * @attribute inh
   * @aspect TypeConversion
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeAnalysis.jrag:122
   */
  @ASTNodeAnnotation.Attribute
  public MethodDecl unknownMethod() {
    MethodDecl unknownMethod_value = getParent().Define_unknownMethod(this, null);

    return unknownMethod_value;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\SyntacticClassification.jrag:36
   * @apilevel internal
   */
  public NameType Define_nameType(ASTNode caller, ASTNode child) {
    if (caller == getSuperInterfaceListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\SyntacticClassification.jrag:102
      int childIndex = caller.getIndexOfChild(child);
      return NameType.TYPE_NAME;
    }
    else {
      return super.Define_nameType(caller, child);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\MultiCatch.jrag:71
   * @apilevel internal
   */
  public TypeDecl Define_hostType(ASTNode caller, ASTNode child) {
    if (caller == getSuperInterfaceListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeAnalysis.jrag:632
      int childIndex = caller.getIndexOfChild(child);
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
    if (caller == getSuperInterfaceListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Annotations.jrag:351
      int childIndex = caller.getIndexOfChild(child);
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
    if (caller == getSuperInterfaceListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Annotations.jrag:455
      int childIndex = caller.getIndexOfChild(child);
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
    if (caller == getSuperInterfaceListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:337
      int childIndex = caller.getIndexOfChild(child);
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
