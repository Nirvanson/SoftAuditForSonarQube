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
 * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\grammar\\Java.ast:38
 * @production TypeDecl : {@link ASTNode} ::= <span class="component">{@link Modifiers}</span> <span class="component">&lt;ID:String&gt;</span> <span class="component">{@link BodyDecl}*</span>;

 */
public abstract class TypeDecl extends ASTNode<ASTNode> implements Cloneable, SimpleSet<TypeDecl>, VariableScope {
  /**
   * @aspect DocumentationComments
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DocumentationComments.jadd:38
   */
  public String docComment = "";
  /**
   * @aspect AnonymousClasses
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\AnonymousClasses.jrag:52
   */
  public int anonymousIndex = 0;
  /**
   * @aspect AnonymousClasses
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\AnonymousClasses.jrag:72
   */
  public int nextAnonymousIndex() {
    if (isNestedType()) {
      return enclosingType().nextAnonymousIndex();
    }
    return anonymousIndex++;
  }
  /**
   * @aspect BoundNames
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\BoundNames.jrag:41
   */
  public ConstructorDecl addConstructor(ConstructorDecl c) {
    addBodyDecl(c);
    return (ConstructorDecl) getBodyDecl(getNumBodyDecl()-1);
  }
  /**
   * @aspect BoundNames
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\BoundNames.jrag:46
   */
  public ClassDecl addMemberClass(ClassDecl c) {
    addBodyDecl(new MemberClassDecl(c));
    return ((MemberClassDecl) getBodyDecl(getNumBodyDecl()-1)).getClassDecl();
  }
  /**
   * Adds a new field to this type declaration.
   * It is an error if the new field is not unique.
   * @aspect BoundNames
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\BoundNames.jrag:55
   */
  public FieldDecl addMemberField(FieldDecl f) {
    addBodyDecl(f);
    return (FieldDecl) getBodyDecl(getNumBodyDecl() - 1);
  }
  /**
   * @aspect BoundNames
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\BoundNames.jrag:108
   */
  public TypeAccess createBoundAccess() {
    return new BoundTypeAccess("", name(), this);
  }
  /**
   * @aspect DataStructures
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DataStructures.jrag:412
   */
  @Override
  public int size() {
    return 1;
  }
  /**
   * @aspect DataStructures
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DataStructures.jrag:417
   */
  @Override
  public boolean isEmpty() {
    return false;
  }
  /**
   * @aspect DataStructures
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DataStructures.jrag:422
   */
  @Override
  public SimpleSet<TypeDecl> add(TypeDecl o) {
    return new SimpleSetImpl<TypeDecl>(this, o);
  }
  /**
   * @aspect DataStructures
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DataStructures.jrag:427
   */
  @Override
  public boolean contains(Object o) {
    return this == o;
  }
  /**
   * @aspect DataStructures
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DataStructures.jrag:432
   */
  @Override
  public boolean isSingleton() {
    return true;
  }
  /**
   * @aspect DataStructures
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DataStructures.jrag:437
   */
  @Override
  public boolean isSingleton(TypeDecl o) {
    return contains(o);
  }
  /**
   * @aspect DataStructures
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DataStructures.jrag:442
   */
  @Override
  public TypeDecl singletonValue() {
    return this;
  }
  /**
   * @aspect DataStructures
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DataStructures.jrag:447
   */
  @Override
  public Iterator<TypeDecl> iterator() {
    return new SingleItemIterator(this);
  }
  /**
   * @aspect ConstructorLookup
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupConstructor.jrag:130
   */
  public ConstructorDecl lookupConstructor(ConstructorDecl signature) {
    for (ConstructorDecl decl : constructors()) {
      if (decl.sameSignature(signature)) {
        return decl;
      }
    }
    return null;
  }
  /**
   * @return true if the method access may access the method
   * @aspect MethodDecl
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupMethod.jrag:299
   */
  public boolean mayAccess(MethodAccess access, MethodDecl method) {
    if (instanceOf(method.hostType())
        && access.qualifier().type().instanceOf(this)) {
      return true;
    }

    if (isNestedType()) {
      return enclosingType().mayAccess(access, method);
    } else {
      return false;
    }
  }
  /**
   * @aspect MemberMethods
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupMethod.jrag:357
   */
  protected static void addMethodToMap(MethodDecl method,
      Map<String, Collection<MethodDecl>> map) {
    String name = method.name();
    Collection<MethodDecl> methods = map.get(name);
    if (methods == null) {
      methods = new ArrayList<MethodDecl>(2);
      map.put(name, methods);
    }
    methods.add(method);
  }
  /**
   * Iterate over all local methods in the type.
   * @return method iterator
   * @aspect MemberMethods
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupMethod.jrag:372
   */
  public Iterator<MethodDecl> localMethodsIterator() {
    return new Iterator<MethodDecl>() {
      private Iterator<SimpleSet<MethodDecl>> outer =
          localMethodsSignatureMap().values().iterator();
      private Iterator inner = null;

      @Override
      public boolean hasNext() {
        if ((inner == null || !inner.hasNext()) && outer.hasNext()) {
          inner = outer.next().iterator();
        }
        return inner == null ? false : inner.hasNext();
      }

      @Override
      public MethodDecl next() {
        return (MethodDecl) inner.next();
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }
  /**
   * @return iterator for iterating over all method declarations in implemented
   * interfaces
   * @aspect MemberMethods
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupMethod.jrag:426
   */
  public Iterator<MethodDecl> interfacesMethodsIterator() {
    return new Iterator<MethodDecl>() {
      private Iterator<SimpleSet<MethodDecl>> outer =
          interfacesMethodsSignatureMap().values().iterator();
      private Iterator inner = null;

      @Override
      public boolean hasNext() {
        if ((inner == null || !inner.hasNext()) && outer.hasNext()) {
          inner = outer.next().iterator();
        }
        return inner == null ? false : inner.hasNext();
      }

      @Override
      public MethodDecl next() {
        return (MethodDecl) inner.next();
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }
  /**
   * Iterate over all member methods in the type.
   * @return method iterator
   * @aspect MemberMethods
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupMethod.jrag:485
   */
  public Iterator<MethodDecl> methodsIterator() {
    return new Iterator<MethodDecl>() {
      private Iterator<SimpleSet<MethodDecl>> outer = methodsSignatureMap().values().iterator();
      private Iterator inner = null;

      @Override
      public boolean hasNext() {
        if ((inner == null || !inner.hasNext()) && outer.hasNext()) {
          inner = outer.next().iterator();
        }
        return inner != null ? inner.hasNext() : false;
      }

      @Override
      public MethodDecl next() {
        return (MethodDecl) inner.next();
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }
  /**
   * @aspect MemberMethods
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupMethod.jrag:575
   */
  protected boolean allMethodsAbstract(SimpleSet<MethodDecl> methods) {
    if (methods == null) {
      return true;
    }
    for (MethodDecl method : methods) {
      if (!method.isAbstract()) {
        return false;
      }
    }
    return true;
  }
  /**
   * @return true if the expression may access the field
   * @aspect VariableScope
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupVariable.jrag:331
   */
  public boolean mayAccess(Expr expr, Variable field) {
    if (instanceOf(field.hostType())) {
      if (!field.isInstanceVariable() || expr.isSuperAccess() || expr.type().instanceOf(this)) {
        return true;
      }
    }
    if (isNestedType()) {
      return enclosingType().mayAccess(expr, field);
    } else {
      return false;
    }
  }
  /**
   * @aspect Fields
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupVariable.jrag:449
   */
  public Iterator<Variable> fieldsIterator() {
    return new Iterator<Variable>() {
      private Iterator<SimpleSet<Variable>> outer = memberFieldsMap().values().iterator();

      private Iterator<Variable> inner = null;

      @Override
      public boolean hasNext() {
        if ((inner == null || !inner.hasNext()) && outer.hasNext()) {
          inner = outer.next().iterator();
        }
        return inner != null ? inner.hasNext() : false;
      }

      @Override
      public FieldDeclarator next() {
        return (FieldDeclarator) inner.next();
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }
  /**
   * @aspect CreateQualifiedAccesses
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\QualifiedNames.jrag:116
   */
  public Access createQualifiedAccess() {
    if (isLocalClass() || isAnonymous()) {
      return new TypeAccess(name());
    } else if (!isTopLevelType()) {
      return enclosingType().createQualifiedAccess().qualifiesAccess(new TypeAccess(name()));
    } else {
      return new TypeAccess(packageName(), name());
    }
  }
  /**
   * @aspect TypeAnalysis
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:251
   */
  public Variable findSingleVariable(String name) {
    return memberFields(name).iterator().next();
  }
  /**
   * Error-check two interface method declarations.
   * @aspect TypeHierarchyCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:539
   */
  protected void refined_TypeHierarchyCheck_TypeDecl_checkAbstractMethodDecls(Collection<Problem> problems,
      MethodDecl m1, MethodDecl m2) {
    if (!m1.mayOverride(m2) && !m2.mayOverride(m1)) {
      StringBuilder err = new StringBuilder();
      TypeDecl host1 = m1.hostType();
      TypeDecl host2 = m2.hostType();
      if (host1 != this || host2 != this) {
        err.append("inherited ");
      }
      err.append("method " + m1.fullSignature() + " is multiply declared "
          + "with incompatible return types in " + fullName());
      problems.add(error(err.toString()));
    }
  }
  /**
   * @aspect Generics
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:225
   */
  public TypeDecl makeGeneric(Signatures.ClassSignature s) {
    return this;
  }
  /**
   * Merge the source map into the destination map.
   * @param dest destination map
   * @param src source map
   * @aspect GenericsTypeCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:561
   */
  protected void mergeMap(Map<String, SimpleSet<MethodDecl>> dest,
      Map<String, SimpleSet<MethodDecl>> src) {
    for (Map.Entry<String, SimpleSet<MethodDecl>> entry : src.entrySet()) {
      String signature = entry.getKey();
      for (MethodDecl method : entry.getValue()) {
        putSimpleSetElement(dest, signature, method);
      }
    }
  }
  /**
   * @aspect LookupParTypeDecl
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:1109
   */
  public TypeDecl substitute(TypeVariable typeVariable) {
    if (isTopLevelType()) {
      return typeVariable;
    }
    return enclosingType().substitute(typeVariable);
  }
  /**
   * Builds a list of erased members in a raw type.
   * @aspect LookupParTypeDecl
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:1540
   */
  protected List<BodyDecl> erasedBodyDecls() {
    List<BodyDecl> list = new List<BodyDecl>();
    for (BodyDecl decl : getBodyDeclList()) {
      if (decl.isSubstitutable()) {
        list.add(decl.erasedCopy());
      }
    }
    return list;
  }
  /**
   * Builds a list of substituted body decls in a parameterized type.
   * @aspect LookupParTypeDecl
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:1553
   */
  protected List<BodyDecl> substitutedBodyDecls() {
    List<BodyDecl> list = new List<BodyDecl>();
    for (BodyDecl decl : getBodyDeclList()) {
      if (decl.isSubstitutable()) {
        list.add(decl.signatureCopy());
      }
    }
    return list;
  }
  /**
   * @aspect Java8NameCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\NameCheck.jrag:288
   */
  protected void checkInterfaceMethodDecls(Collection<Problem> problems,
      MethodDecl m1, MethodDecl m2) {
    if (m1.isAbstract() && m2.isAbstract()) {
      checkAbstractMethodDecls(problems, m1, m2);
      return;
    } else {
      TypeDecl host1 = m1.hostType();
      TypeDecl host2 = m2.hostType();
      String inh1 = "";
      String inh2 = "";
      if (host1 != this) {
        inh1 = "inherited ";
      }
      if (host2 != this) {
        inh2 = "inherited ";
      }

      //9.4
      problems.add(errorf("%smethod %s and %smethod %s are multiply declared in %s",
          inh1, m1.fullSignature(), inh2, m2.fullSignature(), fullName()));
    }
  }
  /**
   * @declaredat ASTNode:1
   */
  public TypeDecl() {
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
  public TypeDecl(Modifiers p0, String p1, List<BodyDecl> p2) {
    setChild(p0, 0);
    setID(p1);
    setChild(p2, 1);
  }
  /**
   * @declaredat ASTNode:19
   */
  public TypeDecl(Modifiers p0, beaver.Symbol p1, List<BodyDecl> p2) {
    setChild(p0, 0);
    setID(p1);
    setChild(p2, 1);
  }
  /** @apilevel low-level 
   * @declaredat ASTNode:25
   */
  protected int numChildren() {
    return 2;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:31
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:35
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    accessibleFromPackage_String_reset();
    accessibleFromExtend_TypeDecl_reset();
    accessibleFrom_TypeDecl_reset();
    dimension_reset();
    elementType_reset();
    arrayType_reset();
    isException_reset();
    isCheckedException_reset();
    isUncheckedException_reset();
    mayCatch_TypeDecl_reset();
    constructors_reset();
    unqualifiedLookupMethod_String_reset();
    methodsNameMap_reset();
    localMethodsSignatureMap_reset();
    interfacesMethodsSignatureMap_reset();
    methodsSignatureMap_reset();
    ancestorMethods_String_reset();
    localTypeDecls_String_reset();
    localFields_String_reset();
    localFieldsMap_reset();
    memberFieldsMap_reset();
    memberFields_String_reset();
    hasAbstract_reset();
    unimplementedMethods_reset();
    isPublic_reset();
    isStatic_reset();
    fullName_reset();
    typeName_reset();
    narrowingConversionTo_TypeDecl_reset();
    methodInvocationConversionTo_TypeDecl_reset();
    castingConversionTo_TypeDecl_reset();
    isString_reset();
    isObject_reset();
    instanceOf_TypeDecl_reset();
    superInterfaces_reset();
    isCircular_reset();
    boxed_reset();
    unboxed_reset();
    isIterable_reset();
    iterableElementType_reset();
    firstTypeArgument_reset();
    involvesTypeParameters_reset();
    erasure_reset();
    erasedAncestorMethodsMap_reset();
    implementedInterfaces_reset();
    usesTypeVariable_reset();
    sourceTypeDecl_reset();
    containedIn_TypeDecl_reset();
    sameStructure_TypeDecl_reset();
    subtype_TypeDecl_reset();
    isFunctionalInterface_reset();
    strictContainedIn_TypeDecl_reset();
    strictSubtype_TypeDecl_reset();
    componentType_reset();
    typeException_reset();
    typeRuntimeException_reset();
    typeError_reset();
    lookupMethod_String_reset();
    typeObject_reset();
    lookupType_String_reset();
    lookupVariable_String_reset();
    packageName_reset();
    isAnonymous_reset();
    unknownType_reset();
    inExplicitConstructorInvocation_reset();
    inStaticContext_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:105
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:109
   */
  public TypeDecl clone() throws CloneNotSupportedException {
    TypeDecl node = (TypeDecl) super.clone();
    return node;
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @deprecated Please use treeCopy or treeCopyNoTransform instead
   * @declaredat ASTNode:120
   */
  @Deprecated
  public abstract TypeDecl fullCopy();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:128
   */
  public abstract TypeDecl treeCopyNoTransform();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The subtree of this node is traversed to trigger rewrites before copy.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:136
   */
  public abstract TypeDecl treeCopy();
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
  /** @apilevel internal 
   */
  protected String tokenString_ID;
  /**
   */
  public int IDstart;
  /**
   */
  public int IDend;
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
   * Replaces the BodyDecl list.
   * @param list The new list node to be used as the BodyDecl list.
   * @apilevel high-level
   */
  public void setBodyDeclList(List<BodyDecl> list) {
    setChild(list, 1);
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
  /** @apilevel low-level 
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
    List<BodyDecl> list = (List<BodyDecl>) getChild(1);
    return list;
  }
  /**
   * Retrieves the BodyDecl list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the BodyDecl list.
   * @apilevel low-level
   */
  public List<BodyDecl> getBodyDeclListNoTransform() {
    return (List<BodyDecl>) getChildNoTransform(1);
  }
  /**
   * @return the element at index {@code i} in the BodyDecl list without
   * triggering rewrites.
   */
  public BodyDecl getBodyDeclNoTransform(int i) {
    return (BodyDecl) getBodyDeclListNoTransform().getChildNoTransform(i);
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
   * @aspect TypeCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericMethods.jrag:184
   */
   
  protected void refined_TypeCheck_TypeDecl_checkAbstractMethodDecls(Collection<Problem> problems,
      MethodDecl m1, MethodDecl m2) {
    if (!m1.sameSignature(m2)) {
      problems.add(errorf("method declarations %s and %s in interface %s are incompatible",
          m1.fullSignature(), m2.fullSignature(), fullName()));
    } else {
      refined_TypeHierarchyCheck_TypeDecl_checkAbstractMethodDecls(problems, m1, m2);
    }
  }
  /**
   * @aspect Java8NameCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\NameCheck.jrag:254
   */
   
  protected void checkAbstractMethodDecls(Collection<Problem> problems,
      MethodDecl m1, MethodDecl m2) {
    if (!m1.subsignatureTo(m2) && !m2.subsignatureTo(m1)) {
      TypeDecl host1 = m1.hostType();
      TypeDecl host2 = m2.hostType();
      String inh1 = "";
      String inh2 = "";
      if (host1 != this) {
        inh1 = "inherited ";
      }
      if (host2 != this) {
        inh2 = "inherited ";
      }

      //8.4.8.3
      problems.add(errorf("%smethod %s and %smethod %s are multiply declared with"
          + " the same erasure but not override-equivalent signatures in %s",
          inh1, m1.fullSignature(), inh2, m2.fullSignature(), fullName()));
    }
    // DON'T FORGET TO CHECK THIS, REALLY OK TO CHECK BOTH WAYS???
    if (!m1.returnTypeSubstitutableFor(m2) && !m2.returnTypeSubstitutableFor(m1)) {
      String inh1 = "";
      TypeDecl host1 = m1.hostType();
      TypeDecl host2 = m2.hostType();
      if (host1 != this || host2 != this) {
        inh1 = "inherited ";
      }

      problems.add(errorf("%smethod %s is multiply declared with incompatible return types in %s",
          m1.fullSignature(), fullName()));
    }
  }
  /**
   * @aspect Modifiers
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:90
   */
  private Collection<Problem> refined_Modifiers_TypeDecl_modifierProblems()
{
    Collection<Problem> problems = new LinkedList<Problem>();
    // 8.1.1
    if (isPublic() && !isTopLevelType() && !isMemberType()) {
      problems.add(error("public pertains only to top level types and member types"));
    }

    // 8.1.1
    if ((isProtected() || isPrivate()) && !(isMemberType() && enclosingType().isClassDecl())) {
      problems.add(error("protected and private may only be used on member types within "
          + "a directly enclosing class declaration"));
    }

    // 8.1.1
    if (isStatic() && !isMemberType()) {
      problems.add(error("static pertains only to member types"));
    }


    // 8.4.3.1
    // 8.1.1.1
    if (!isAbstract() && hasAbstract()) {
      StringBuilder sb = new StringBuilder();
      sb.append("" + name() + " is not declared abstract but contains abstract members: \n");
      for (MethodDecl m : unimplementedMethods()) {
        sb.append("  " + m.signature() + " in " + m.hostType().typeName() + "\n");
      }
      problems.add(error(sb.toString()));
    }
    return problems;
  }
  /**
   * @aspect TypeConversion
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:83
   */
  private boolean refined_TypeConversion_TypeDecl_assignConversionTo_TypeDecl_Expr(TypeDecl type, Expr expr)
{
    boolean sourceIsConstant = expr != null ? expr.isConstant() : false;
    if (identityConversionTo(type) || wideningConversionTo(type)) {
      return true;
    }
    return sourceIsConstant
        && (isInt() || isChar() || isShort() || isByte())
        && (type.isByte() || type.isShort() || type.isChar())
        && narrowingConversionTo(type)
        && expr.representableIn(type);
  }
  /**
   * @aspect TypeConversion
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:96
   */
  private boolean refined_TypeConversion_TypeDecl_methodInvocationConversionTo_TypeDecl(TypeDecl type)
{ return identityConversionTo(type) || wideningConversionTo(type); }
  /**
   * @aspect TypeConversion
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:100
   */
  private boolean refined_TypeConversion_TypeDecl_castingConversionTo_TypeDecl(TypeDecl type)
{ return identityConversionTo(type) || wideningConversionTo(type) || narrowingConversionTo(type); }
  /**
   * @aspect TypeHierarchyCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:233
   */
  private Collection<Problem> refined_TypeHierarchyCheck_TypeDecl_typeProblems()
{
    Collection<Problem> problems = new LinkedList<Problem>();
    // 8.4.6.4 & 9.4.1
    for (Iterator iter1 = localMethodsIterator(); iter1.hasNext(); ) {
      MethodDecl m = (MethodDecl) iter1.next();
      ASTNode target = m.hostType() == this ? (ASTNode) m : (ASTNode) this;

      for (MethodDecl decl : ancestorMethods(m.signature())) {
        if (m.overrides(decl)) {
          // 8.4.6.1
          if (!m.isStatic() && decl.isStatic()) {
            problems.add(target.error("an instance method may not override a static method"));
          }

          // Regardless of overriding.
          // 8.4.6.3
          if (!m.mayOverride(decl)) {
            problems.add(target.errorf(
                "the return type of method %s in %s does not match the return type of"
                + " method %s in %s and may thus not be overridden",
                m.fullSignature(), m.hostType().typeName(), decl.fullSignature(),
                decl.hostType().typeName()));
          }

          // Regardless of overriding.
          // 8.4.4
          for (Access e: m.getExceptionList()) {
            if (e.type().isCheckedException()) {
              boolean found = false;
              for (Access declException: decl.getExceptionList()) {
                if (e.type().instanceOf(declException.type())) {
                  found = true;
                  break;
                }
              }
              if (!found) {
                problems.add(target.errorf("%s in %s may not throw more checked exceptions than"
                    + " overridden method %s in %s",
                    m.fullSignature(), m.hostType().typeName(), decl.fullSignature(),
                    decl.hostType().typeName()));
              }
            }
          }

          // 8.4.6.3
          if (decl.isPublic() && !m.isPublic()) {
            problems.add(target.error("overriding access modifier error"));
          }
          // 8.4.6.3
          if (decl.isProtected() && !(m.isPublic() || m.isProtected())) {
            problems.add(target.error("overriding access modifier error"));
          }
          // 8.4.6.3
          if ((!decl.isPrivate() && !decl.isProtected() && !decl.isPublic()) && m.isPrivate()) {
            problems.add(target.error("overriding access modifier error"));
          }
          // regardless of overriding
          if (decl.isFinal()) {
            problems.add(target.errorf("method %s in %s can not override final method %s in %s",
                m.fullSignature(), hostType().typeName(), decl.fullSignature(),
                decl.hostType().typeName()));
          }
        }
        if (m.hides(decl)) {
          // 8.4.6.2
          if (m.isStatic() && !decl.isStatic()) {
            problems.add(target.error("a static method may not hide an instance method"));
          }
          // 8.4.6.3
          if (!m.mayOverride(decl)) {
            problems.add(target.error("can not hide a method with a different return type"));
          }
          // 8.4.4
          for (int i = 0; i < m.getNumException(); i++) {
            Access e = m.getException(i);
            boolean found = false;
            for (int j = 0; !found && j < decl.getNumException(); j++) {
              if (e.type().instanceOf(decl.getException(j).type())) {
                found = true;
              }
            }
            if (!found) {
              problems.add(target.error(
                  "may not throw more checked exceptions than hidden method"));
            }
          }
          // 8.4.6.3
          if (decl.isPublic() && !m.isPublic()) {
            problems.add(target.errorf("hiding access modifier error:"
                + " public method %s in %s is hidden by non public method %s in %s",
                decl.fullSignature(), decl.hostType().typeName(), m.fullSignature(),
                m.hostType().typeName()));
          }
          // 8.4.6.3
          if (decl.isProtected() && !(m.isPublic() || m.isProtected())) {
            problems.add(target.errorf("hiding access modifier error:"
                + " protected method %s in %s is hidden by non (public|protected) method %s in %s",
                decl.fullSignature(), decl.hostType().typeName(), m.fullSignature(),
                m.hostType().typeName()));
          }
          // 8.4.6.3
          if ((!decl.isPrivate() && !decl.isProtected() && !decl.isPublic()) && m.isPrivate()) {
            problems.add(target.errorf("hiding access modifier error:"
                + " default method %s in %s is hidden by private method %s in %s",
                decl.fullSignature(), decl.hostType().typeName(), m.fullSignature(),
                m.hostType().typeName()));
          }
          if (decl.isFinal()) {
            problems.add(target.errorf("method %s in %s can not hide final method %s in %s",
                m.fullSignature(), hostType().typeName(), decl.fullSignature(),
                decl.hostType().typeName()));
          }
        }
      }
    }
    return problems;
  }
  /**
   * @attribute syn
   * @aspect DocumentationComments
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DocumentationComments.jadd:40
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="DocumentationComments", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DocumentationComments.jadd:40")
  public String docComment() {
    String docComment_value = docComment;
    return docComment_value;
  }
  /**
   * @attribute syn
   * @aspect DocumentationComments
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DocumentationComments.jadd:44
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="DocumentationComments", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DocumentationComments.jadd:44")
  public boolean hasDocComment() {
    boolean hasDocComment_value = !docComment.isEmpty();
    return hasDocComment_value;
  }
  /** @apilevel internal */
  private void accessibleFromPackage_String_reset() {
    accessibleFromPackage_String_computed = new java.util.HashMap(4);
    accessibleFromPackage_String_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map accessibleFromPackage_String_values;
  /** @apilevel internal */
  protected java.util.Map accessibleFromPackage_String_computed;
  /**
   * @attribute syn
   * @aspect AccessControl
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\AccessControl.jrag:39
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="AccessControl", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\AccessControl.jrag:39")
  public boolean accessibleFromPackage(String packageName) {
    Object _parameters = packageName;
    if (accessibleFromPackage_String_computed == null) accessibleFromPackage_String_computed = new java.util.HashMap(4);
    if (accessibleFromPackage_String_values == null) accessibleFromPackage_String_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (accessibleFromPackage_String_values.containsKey(_parameters) && accessibleFromPackage_String_computed != null
        && accessibleFromPackage_String_computed.containsKey(_parameters)
        && (accessibleFromPackage_String_computed.get(_parameters) == ASTNode$State.NON_CYCLE || accessibleFromPackage_String_computed.get(_parameters) == state().cycle())) {
      return (Boolean) accessibleFromPackage_String_values.get(_parameters);
    }
    boolean accessibleFromPackage_String_value = !isPrivate() && (isPublic() || hostPackage().equals(packageName));
    if (state().inCircle()) {
      accessibleFromPackage_String_values.put(_parameters, accessibleFromPackage_String_value);
      accessibleFromPackage_String_computed.put(_parameters, state().cycle());
    
    } else {
      accessibleFromPackage_String_values.put(_parameters, accessibleFromPackage_String_value);
      accessibleFromPackage_String_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return accessibleFromPackage_String_value;
  }
  /** @apilevel internal */
  private void accessibleFromExtend_TypeDecl_reset() {
    accessibleFromExtend_TypeDecl_computed = new java.util.HashMap(4);
    accessibleFromExtend_TypeDecl_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map accessibleFromExtend_TypeDecl_values;
  /** @apilevel internal */
  protected java.util.Map accessibleFromExtend_TypeDecl_computed;
  /**
   * @attribute syn
   * @aspect AccessControl
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\AccessControl.jrag:44
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="AccessControl", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\AccessControl.jrag:44")
  public boolean accessibleFromExtend(TypeDecl type) {
    Object _parameters = type;
    if (accessibleFromExtend_TypeDecl_computed == null) accessibleFromExtend_TypeDecl_computed = new java.util.HashMap(4);
    if (accessibleFromExtend_TypeDecl_values == null) accessibleFromExtend_TypeDecl_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (accessibleFromExtend_TypeDecl_values.containsKey(_parameters) && accessibleFromExtend_TypeDecl_computed != null
        && accessibleFromExtend_TypeDecl_computed.containsKey(_parameters)
        && (accessibleFromExtend_TypeDecl_computed.get(_parameters) == ASTNode$State.NON_CYCLE || accessibleFromExtend_TypeDecl_computed.get(_parameters) == state().cycle())) {
      return (Boolean) accessibleFromExtend_TypeDecl_values.get(_parameters);
    }
    boolean accessibleFromExtend_TypeDecl_value = accessibleFromExtend_compute(type);
    if (state().inCircle()) {
      accessibleFromExtend_TypeDecl_values.put(_parameters, accessibleFromExtend_TypeDecl_value);
      accessibleFromExtend_TypeDecl_computed.put(_parameters, state().cycle());
    
    } else {
      accessibleFromExtend_TypeDecl_values.put(_parameters, accessibleFromExtend_TypeDecl_value);
      accessibleFromExtend_TypeDecl_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return accessibleFromExtend_TypeDecl_value;
  }
  /** @apilevel internal */
  private boolean accessibleFromExtend_compute(TypeDecl type) {
      if (type == this) {
        return true;
      }
      if (isInnerType()) {
        if (!enclosingType().accessibleFrom(type)) {
          return false;
        }
      }
      if (isPublic()) {
        return true;
      } else if (isProtected()) {
        // If isProtected == true it implies a nested type.
        if (hostPackage().equals(type.hostPackage())) {
          return true;
        }
        if (type.isNestedType()
            && type.enclosingType().withinBodyThatSubclasses(enclosingType()) != null) {
          return true;
        }
        return false;
      } else if (isPrivate()) {
        return topLevelType() == type.topLevelType();
      } else {
        return hostPackage().equals(type.hostPackage());
      }
    }
  /** @apilevel internal */
  private void accessibleFrom_TypeDecl_reset() {
    accessibleFrom_TypeDecl_computed = new java.util.HashMap(4);
    accessibleFrom_TypeDecl_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map accessibleFrom_TypeDecl_values;
  /** @apilevel internal */
  protected java.util.Map accessibleFrom_TypeDecl_computed;
  /**
   * @attribute syn
   * @aspect AccessControl
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\AccessControl.jrag:72
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="AccessControl", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\AccessControl.jrag:72")
  public boolean accessibleFrom(TypeDecl type) {
    Object _parameters = type;
    if (accessibleFrom_TypeDecl_computed == null) accessibleFrom_TypeDecl_computed = new java.util.HashMap(4);
    if (accessibleFrom_TypeDecl_values == null) accessibleFrom_TypeDecl_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (accessibleFrom_TypeDecl_values.containsKey(_parameters) && accessibleFrom_TypeDecl_computed != null
        && accessibleFrom_TypeDecl_computed.containsKey(_parameters)
        && (accessibleFrom_TypeDecl_computed.get(_parameters) == ASTNode$State.NON_CYCLE || accessibleFrom_TypeDecl_computed.get(_parameters) == state().cycle())) {
      return (Boolean) accessibleFrom_TypeDecl_values.get(_parameters);
    }
    boolean accessibleFrom_TypeDecl_value = accessibleFrom_compute(type);
    if (state().inCircle()) {
      accessibleFrom_TypeDecl_values.put(_parameters, accessibleFrom_TypeDecl_value);
      accessibleFrom_TypeDecl_computed.put(_parameters, state().cycle());
    
    } else {
      accessibleFrom_TypeDecl_values.put(_parameters, accessibleFrom_TypeDecl_value);
      accessibleFrom_TypeDecl_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return accessibleFrom_TypeDecl_value;
  }
  /** @apilevel internal */
  private boolean accessibleFrom_compute(TypeDecl type) {
      if (type == this) {
        return true;
      }
      if (isInnerType()) {
        if (!enclosingType().accessibleFrom(type)) {
          return false;
        }
      }
      if (isPublic()) {
        return true;
      } else if (isProtected()) {
        if (hostPackage().equals(type.hostPackage())) {
          return true;
        }
        if (isMemberType()) {
          TypeDecl typeDecl = type;
          while (typeDecl != null && !typeDecl.instanceOf(enclosingType())) {
            typeDecl = typeDecl.enclosingType();
          }
          if (typeDecl != null) {
            return true;
          }
        }
        return false;
      } else if (isPrivate()) {
        return topLevelType() == type.topLevelType();
      } else {
        return hostPackage().equals(type.hostPackage());
      }
    }
  /** @apilevel internal */
  private void dimension_reset() {
    dimension_computed = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle dimension_computed = null;

  /** @apilevel internal */
  protected int dimension_value;

  /**
   * Dimension of this type, if it is an array type.
   * @return 0 for non-array types, and the array dimension for an array type.
   * @attribute syn
   * @aspect Arrays
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Arrays.jrag:36
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Arrays", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Arrays.jrag:36")
  public int dimension() {
    ASTNode$State state = state();
    if (dimension_computed == ASTNode$State.NON_CYCLE || dimension_computed == state().cycle()) {
      return dimension_value;
    }
    dimension_value = 0;
    if (state().inCircle()) {
      dimension_computed = state().cycle();
    
    } else {
      dimension_computed = ASTNode$State.NON_CYCLE;
    
    }
    return dimension_value;
  }
  /** @apilevel internal */
  private void elementType_reset() {
    elementType_computed = null;
    elementType_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle elementType_computed = null;

  /** @apilevel internal */
  protected TypeDecl elementType_value;

  /**
   * @return the base type that the array is based on. For example, returns int
   * for an int[][] array type.
   * @attribute syn
   * @aspect Arrays
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Arrays.jrag:44
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Arrays", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Arrays.jrag:44")
  public TypeDecl elementType() {
    ASTNode$State state = state();
    if (elementType_computed == ASTNode$State.NON_CYCLE || elementType_computed == state().cycle()) {
      return elementType_value;
    }
    elementType_value = this;
    if (state().inCircle()) {
      elementType_computed = state().cycle();
    
    } else {
      elementType_computed = ASTNode$State.NON_CYCLE;
    
    }
    return elementType_value;
  }
  /** @apilevel internal */
  private void arrayType_reset() {
    arrayType_computed = false;
    
    arrayType_value = null;
  }
  /** @apilevel internal */
  protected boolean arrayType_computed = false;

  /** @apilevel internal */
  protected TypeDecl arrayType_value;

  /**
   * Nonterminal that builds an array type out of this type.
   * @attribute syn
   * @aspect Arrays
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Arrays.jrag:61
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="Arrays", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Arrays.jrag:61")
  public TypeDecl arrayType() {
    ASTNode$State state = state();
    if (arrayType_computed) {
      return arrayType_value;
    }
    state().enterLazyAttribute();
    arrayType_value = arrayType_compute();
    arrayType_value.setParent(this);
    arrayType_computed = true;
    state().leaveLazyAttribute();
    return arrayType_value;
  }
  /** @apilevel internal */
  private TypeDecl arrayType_compute() {
      String name = name() + "[]";
  
      List body = new List();
      FieldDeclarator length = new FieldDeclarator(
          "length",
          new List<Dims>(),
          new Opt<Expr>());
      body.add(
        new FieldDecl(
            new Modifiers(new List<Modifier>(
                new Modifier("public"),
                new Modifier("final"))),
            new PrimitiveTypeAccess("int"),
            new List<FieldDeclarator>(length)
        )
      );
      MethodDecl clone = null;
      TypeDecl typeObject = typeObject();
      for (int i = 0; clone == null && i < typeObject.getNumBodyDecl(); i++) {
        if (typeObject.getBodyDecl(i) instanceof MethodDecl) {
          MethodDecl m = (MethodDecl) typeObject.getBodyDecl(i);
          if (m.name().equals("clone")) {
            clone = m;
          }
        }
      }
      if (clone != null) {
        body.add(
            // We create a substituted method that substitutes the clone method in object
            // this has the following two consequences: the return value will be cast to the
            // expected return type rather than object, and the invoked method will be the
            // method in object rather in the array.
            new MethodDeclSubstituted(
              new Modifiers(new List().add(new Modifier("public"))),
              new ArrayTypeAccess(createQualifiedAccess()),
              "clone",
              new List(),
              new List(),
              new Opt(new Block()),
              typeObject().memberMethods("clone").iterator().next()
            )
        );
      }
      TypeDecl typeDecl = new ArrayDecl(
          new Modifiers(new List().add(new Modifier("public"))),
          name,
          new Opt(typeObject().createQualifiedAccess()),
          new List().add(typeCloneable().createQualifiedAccess())
              .add(typeSerializable().createQualifiedAccess()),
          body
        );
      return typeDecl;
    }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:95
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstantExpression", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:95")
  public Constant cast(Constant c) {
    {
        throw new UnsupportedOperationException("ConstantExpression operation cast"
            + " not supported for type " + getClass().getName());
      }
  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:118
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstantExpression", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:118")
  public Constant plus(Constant c) {
    {
        throw new UnsupportedOperationException("ConstantExpression operation plus"
            + " not supported for type " + getClass().getName());
      }
  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:131
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstantExpression", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:131")
  public Constant minus(Constant c) {
    {
        throw new UnsupportedOperationException("ConstantExpression operation minus"
            + " not supported for type " + getClass().getName());
      }
  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:144
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstantExpression", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:144")
  public Constant bitNot(Constant c) {
    {
        throw new UnsupportedOperationException("ConstantExpression operation bitNot"
            + " not supported for type " + getClass().getName());
      }
  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:153
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstantExpression", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:153")
  public Constant mul(Constant c1, Constant c2) {
    {
        throw new UnsupportedOperationException("ConstantExpression operation mul"
            + " not supported for type " + getClass().getName());
      }
  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:167
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstantExpression", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:167")
  public Constant div(Constant c1, Constant c2) {
    {
        throw new UnsupportedOperationException("ConstantExpression operation div"
            + " not supported for type " + getClass().getName());
      }
  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:181
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstantExpression", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:181")
  public Constant mod(Constant c1, Constant c2) {
    {
        throw new UnsupportedOperationException("ConstantExpression operation mod"
            + " not supported for type " + getClass().getName());
      }
  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:195
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstantExpression", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:195")
  public Constant add(Constant c1, Constant c2) {
    {
        throw new UnsupportedOperationException("ConstantExpression operation add"
            + " not supported for type " + getClass().getName());
      }
  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:212
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstantExpression", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:212")
  public Constant sub(Constant c1, Constant c2) {
    {
        throw new UnsupportedOperationException("ConstantExpression operation sub"
            + " not supported for type " + getClass().getName());
      }
  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:226
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstantExpression", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:226")
  public Constant lshift(Constant c1, Constant c2) {
    {
        throw new UnsupportedOperationException("ConstantExpression operation lshift"
            + " not supported for type " + getClass().getName());
      }
  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:236
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstantExpression", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:236")
  public Constant rshift(Constant c1, Constant c2) {
    {
        throw new UnsupportedOperationException("ConstantExpression operation rshift"
            + " not supported for type " + getClass().getName());
      }
  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:246
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstantExpression", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:246")
  public Constant urshift(Constant c1, Constant c2) {
    {
        throw new UnsupportedOperationException("ConstantExpression operation urshift"
            + " not supported for type " + getClass().getName());
      }
  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:257
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstantExpression", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:257")
  public Constant andBitwise(Constant c1, Constant c2) {
    {
        throw new UnsupportedOperationException("ConstantExpression operation andBitwise"
            + " not supported for type " + getClass().getName());
      }
  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:271
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstantExpression", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:271")
  public Constant xorBitwise(Constant c1, Constant c2) {
    {
        throw new UnsupportedOperationException("ConstantExpression operation xorBitwise"
            + " not supported for type " + getClass().getName());
      }
  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:285
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstantExpression", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:285")
  public Constant orBitwise(Constant c1, Constant c2) {
    {
        throw new UnsupportedOperationException("ConstantExpression operation orBitwise"
            + " not supported for type " + getClass().getName());
      }
  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:299
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstantExpression", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:299")
  public Constant questionColon(Constant cond, Constant c1, Constant c2) {
    {
        throw new UnsupportedOperationException("ConstantExpression operation questionColon"
            + " not supported for type " + getClass().getName());
      }
  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:499
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstantExpression", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:499")
  public boolean eqIsTrue(Expr left, Expr right) {
    {
        System.err.println("Evaluation eqIsTrue for unknown type: " + getClass().getName());
        return false;
      }
  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:522
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstantExpression", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:522")
  public boolean ltIsTrue(Expr left, Expr right) {
    boolean ltIsTrue_Expr_Expr_value = false;
    return ltIsTrue_Expr_Expr_value;
  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:536
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstantExpression", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ConstantExpression.jrag:536")
  public boolean leIsTrue(Expr left, Expr right) {
    boolean leIsTrue_Expr_Expr_value = false;
    return leIsTrue_Expr_Expr_value;
  }
  /** @apilevel internal */
  private void isException_reset() {
    isException_computed = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle isException_computed = null;

  /** @apilevel internal */
  protected boolean isException_value;

  /**
   * @attribute syn
   * @aspect ExceptionHandling
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ExceptionHandling.jrag:63
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ExceptionHandling", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ExceptionHandling.jrag:63")
  public boolean isException() {
    ASTNode$State state = state();
    if (isException_computed == ASTNode$State.NON_CYCLE || isException_computed == state().cycle()) {
      return isException_value;
    }
    isException_value = instanceOf(typeException());
    if (state().inCircle()) {
      isException_computed = state().cycle();
    
    } else {
      isException_computed = ASTNode$State.NON_CYCLE;
    
    }
    return isException_value;
  }
  /** @apilevel internal */
  private void isCheckedException_reset() {
    isCheckedException_computed = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle isCheckedException_computed = null;

  /** @apilevel internal */
  protected boolean isCheckedException_value;

  /**
   * Checked exceptions must be declared thrown or caught in
   * an enclosing try-statement.
   * 
   * <p>Note that this attribute is the opposite of isUncheckedException, i.e.
   * the type is not tested for being a subclass of java.lang.Exception.
   * 
   * @return {@code true} if this type is not a subtype of java.lang.RuntimException
   * or java.lang.Error
   * @attribute syn
   * @aspect ExceptionHandling
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ExceptionHandling.jrag:75
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ExceptionHandling", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ExceptionHandling.jrag:75")
  public boolean isCheckedException() {
    ASTNode$State state = state();
    if (isCheckedException_computed == ASTNode$State.NON_CYCLE || isCheckedException_computed == state().cycle()) {
      return isCheckedException_value;
    }
    isCheckedException_value = !(instanceOf(typeRuntimeException()) || instanceOf(typeError()));
    if (state().inCircle()) {
      isCheckedException_computed = state().cycle();
    
    } else {
      isCheckedException_computed = ASTNode$State.NON_CYCLE;
    
    }
    return isCheckedException_value;
  }
  /** @apilevel internal */
  private void isUncheckedException_reset() {
    isUncheckedException_computed = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle isUncheckedException_computed = null;

  /** @apilevel internal */
  protected boolean isUncheckedException_value;

  /**
   * Unchecked exceptions need not be declared thrown or caught in
   * an enclosing try-statement.
   * 
   * @return {@code true} if this type is a subtype of java.lang.RuntimException
   * or java.lang.Error
   * @attribute syn
   * @aspect ExceptionHandling
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ExceptionHandling.jrag:85
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ExceptionHandling", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ExceptionHandling.jrag:85")
  public boolean isUncheckedException() {
    ASTNode$State state = state();
    if (isUncheckedException_computed == ASTNode$State.NON_CYCLE || isUncheckedException_computed == state().cycle()) {
      return isUncheckedException_value;
    }
    isUncheckedException_value = instanceOf(typeRuntimeException()) || instanceOf(typeError());
    if (state().inCircle()) {
      isUncheckedException_computed = state().cycle();
    
    } else {
      isUncheckedException_computed = ASTNode$State.NON_CYCLE;
    
    }
    return isUncheckedException_value;
  }
  /** @apilevel internal */
  private void mayCatch_TypeDecl_reset() {
    mayCatch_TypeDecl_computed = new java.util.HashMap(4);
    mayCatch_TypeDecl_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map mayCatch_TypeDecl_values;
  /** @apilevel internal */
  protected java.util.Map mayCatch_TypeDecl_computed;
  /**
   * @attribute syn
   * @aspect ExceptionHandling
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ExceptionHandling.jrag:326
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ExceptionHandling", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ExceptionHandling.jrag:326")
  public boolean mayCatch(TypeDecl thrownType) {
    Object _parameters = thrownType;
    if (mayCatch_TypeDecl_computed == null) mayCatch_TypeDecl_computed = new java.util.HashMap(4);
    if (mayCatch_TypeDecl_values == null) mayCatch_TypeDecl_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (mayCatch_TypeDecl_values.containsKey(_parameters) && mayCatch_TypeDecl_computed != null
        && mayCatch_TypeDecl_computed.containsKey(_parameters)
        && (mayCatch_TypeDecl_computed.get(_parameters) == ASTNode$State.NON_CYCLE || mayCatch_TypeDecl_computed.get(_parameters) == state().cycle())) {
      return (Boolean) mayCatch_TypeDecl_values.get(_parameters);
    }
    boolean mayCatch_TypeDecl_value = thrownType.instanceOf(this) || this.instanceOf(thrownType);
    if (state().inCircle()) {
      mayCatch_TypeDecl_values.put(_parameters, mayCatch_TypeDecl_value);
      mayCatch_TypeDecl_computed.put(_parameters, state().cycle());
    
    } else {
      mayCatch_TypeDecl_values.put(_parameters, mayCatch_TypeDecl_value);
      mayCatch_TypeDecl_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return mayCatch_TypeDecl_value;
  }
  /**
   * @attribute syn
   * @aspect ConstructScope
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupConstructor.jrag:47
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstructScope", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupConstructor.jrag:47")
  public Collection<ConstructorDecl> lookupSuperConstructor() {
    Collection<ConstructorDecl> lookupSuperConstructor_value = Collections.emptyList();
    return lookupSuperConstructor_value;
  }
  /** @apilevel internal */
  private void constructors_reset() {
    constructors_computed = null;
    constructors_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle constructors_computed = null;

  /** @apilevel internal */
  protected Collection<ConstructorDecl> constructors_value;

  /**
   * @attribute syn
   * @aspect ConstructorLookup
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupConstructor.jrag:139
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstructorLookup", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupConstructor.jrag:139")
  public Collection<ConstructorDecl> constructors() {
    ASTNode$State state = state();
    if (constructors_computed == ASTNode$State.NON_CYCLE || constructors_computed == state().cycle()) {
      return constructors_value;
    }
    constructors_value = constructors_compute();
    if (state().inCircle()) {
      constructors_computed = state().cycle();
    
    } else {
      constructors_computed = ASTNode$State.NON_CYCLE;
    
    }
    return constructors_value;
  }
  /** @apilevel internal */
  private Collection<ConstructorDecl> constructors_compute() {
      Collection<ConstructorDecl> c = new ArrayList<ConstructorDecl>();
      for (int i = 0; i < getNumBodyDecl(); i++) {
        if (getBodyDecl(i) instanceof ConstructorDecl) {
          c.add((ConstructorDecl) getBodyDecl(i));
        }
      }
      return c;
    }
  /** @apilevel internal */
  private void unqualifiedLookupMethod_String_reset() {
    unqualifiedLookupMethod_String_computed = new java.util.HashMap(4);
    unqualifiedLookupMethod_String_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map unqualifiedLookupMethod_String_values;
  /** @apilevel internal */
  protected java.util.Map unqualifiedLookupMethod_String_computed;
  /**
   * @attribute syn
   * @aspect LookupMethod
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupMethod.jrag:71
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="LookupMethod", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupMethod.jrag:71")
  public Collection<MethodDecl> unqualifiedLookupMethod(String name) {
    Object _parameters = name;
    if (unqualifiedLookupMethod_String_computed == null) unqualifiedLookupMethod_String_computed = new java.util.HashMap(4);
    if (unqualifiedLookupMethod_String_values == null) unqualifiedLookupMethod_String_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (unqualifiedLookupMethod_String_values.containsKey(_parameters) && unqualifiedLookupMethod_String_computed != null
        && unqualifiedLookupMethod_String_computed.containsKey(_parameters)
        && (unqualifiedLookupMethod_String_computed.get(_parameters) == ASTNode$State.NON_CYCLE || unqualifiedLookupMethod_String_computed.get(_parameters) == state().cycle())) {
      return (Collection<MethodDecl>) unqualifiedLookupMethod_String_values.get(_parameters);
    }
    Collection<MethodDecl> unqualifiedLookupMethod_String_value = unqualifiedLookupMethod_compute(name);
    if (state().inCircle()) {
      unqualifiedLookupMethod_String_values.put(_parameters, unqualifiedLookupMethod_String_value);
      unqualifiedLookupMethod_String_computed.put(_parameters, state().cycle());
    
    } else {
      unqualifiedLookupMethod_String_values.put(_parameters, unqualifiedLookupMethod_String_value);
      unqualifiedLookupMethod_String_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return unqualifiedLookupMethod_String_value;
  }
  /** @apilevel internal */
  private Collection<MethodDecl> unqualifiedLookupMethod_compute(String name) {
      Collection<MethodDecl> methods = memberMethods(name);
      if (!methods.isEmpty()) {
        return methods;
      }
      if (isInnerType()) {
        return lookupMethod(name);
      }
      return keepStaticMethods(lookupMethod(name));
    }
  /**
   * @attribute syn
   * @aspect MemberMethods
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupMethod.jrag:332
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MemberMethods", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupMethod.jrag:332")
  public Collection<MethodDecl> memberMethods(String name) {
    {
        Collection<MethodDecl> methods = methodsNameMap().get(name);
        if (methods != null) {
          return methods;
        } else {
          return Collections.emptyList();
        }
      }
  }
  /** @apilevel internal */
  private void methodsNameMap_reset() {
    methodsNameMap_computed = null;
    methodsNameMap_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle methodsNameMap_computed = null;

  /** @apilevel internal */
  protected Map<String, Collection<MethodDecl>> methodsNameMap_value;

  /**
   * @return map from method name to method declarations
   * @attribute syn
   * @aspect MemberMethods
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupMethod.jrag:343
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MemberMethods", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupMethod.jrag:343")
  public Map<String, Collection<MethodDecl>> methodsNameMap() {
    ASTNode$State state = state();
    if (methodsNameMap_computed == ASTNode$State.NON_CYCLE || methodsNameMap_computed == state().cycle()) {
      return methodsNameMap_value;
    }
    methodsNameMap_value = methodsNameMap_compute();
    if (state().inCircle()) {
      methodsNameMap_computed = state().cycle();
    
    } else {
      methodsNameMap_computed = ASTNode$State.NON_CYCLE;
    
    }
    return methodsNameMap_value;
  }
  /** @apilevel internal */
  private Map<String, Collection<MethodDecl>> methodsNameMap_compute() {
      Map<String, Collection<MethodDecl>> map = new HashMap<String, Collection<MethodDecl>>();
      for (Iterator<MethodDecl> iter = methodsIterator(); iter.hasNext(); ) {
        MethodDecl m = iter.next();
        Collection<MethodDecl> methods = map.get(m.name());
        if (methods == null) {
          methods = new ArrayList<MethodDecl>(4);
          map.put(m.name(), methods);
        }
        methods.add(m);
      }
      return map;
    }
  /**
   * @attribute syn
   * @aspect MemberMethods
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupMethod.jrag:398
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MemberMethods", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupMethod.jrag:398")
  public SimpleSet<MethodDecl> localMethodsSignature(String signature) {
    {
        SimpleSet<MethodDecl> result = localMethodsSignatureMap().get(signature);
        if (result != null) {
          return result;
        } else {
          return emptySet();
        }
      }
  }
  /** @apilevel internal */
  private void localMethodsSignatureMap_reset() {
    localMethodsSignatureMap_computed = null;
    localMethodsSignatureMap_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle localMethodsSignatureMap_computed = null;

  /** @apilevel internal */
  protected Map<String, SimpleSet<MethodDecl>> localMethodsSignatureMap_value;

  /**
   * @return a mapping of method signature to method declaration
   * @attribute syn
   * @aspect MemberMethods
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupMethod.jrag:410
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MemberMethods", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupMethod.jrag:410")
  public Map<String, SimpleSet<MethodDecl>> localMethodsSignatureMap() {
    ASTNode$State state = state();
    if (localMethodsSignatureMap_computed == ASTNode$State.NON_CYCLE || localMethodsSignatureMap_computed == state().cycle()) {
      return localMethodsSignatureMap_value;
    }
    localMethodsSignatureMap_value = localMethodsSignatureMap_compute();
    if (state().inCircle()) {
      localMethodsSignatureMap_computed = state().cycle();
    
    } else {
      localMethodsSignatureMap_computed = ASTNode$State.NON_CYCLE;
    
    }
    return localMethodsSignatureMap_value;
  }
  /** @apilevel internal */
  private Map<String, SimpleSet<MethodDecl>> localMethodsSignatureMap_compute() {
      Map<String, SimpleSet<MethodDecl>> map = new HashMap<String, SimpleSet<MethodDecl>>(
          getNumBodyDecl());
      for (int i = 0; i < getNumBodyDecl(); i++) {
        if (getBodyDecl(i) instanceof MethodDecl) {
          MethodDecl decl = (MethodDecl) getBodyDecl(i);
          putSimpleSetElement(map, decl.signature(), decl);
        }
      }
      return map;
    }
  /** @apilevel internal */
  private void interfacesMethodsSignatureMap_reset() {
    interfacesMethodsSignatureMap_computed = null;
    interfacesMethodsSignatureMap_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle interfacesMethodsSignatureMap_computed = null;

  /** @apilevel internal */
  protected Map<String, SimpleSet<MethodDecl>> interfacesMethodsSignatureMap_value;

  /**
   * Map signature to set of method declarations.
   * @attribute syn
   * @aspect MemberMethods
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupMethod.jrag:464
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MemberMethods", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupMethod.jrag:464")
  public Map<String, SimpleSet<MethodDecl>> interfacesMethodsSignatureMap() {
    ASTNode$State state = state();
    if (interfacesMethodsSignatureMap_computed == ASTNode$State.NON_CYCLE || interfacesMethodsSignatureMap_computed == state().cycle()) {
      return interfacesMethodsSignatureMap_value;
    }
    interfacesMethodsSignatureMap_value = interfacesMethodsSignatureMap_compute();
    if (state().inCircle()) {
      interfacesMethodsSignatureMap_computed = state().cycle();
    
    } else {
      interfacesMethodsSignatureMap_computed = ASTNode$State.NON_CYCLE;
    
    }
    return interfacesMethodsSignatureMap_value;
  }
  /** @apilevel internal */
  private Map<String, SimpleSet<MethodDecl>> interfacesMethodsSignatureMap_compute() {
      Map<String, SimpleSet<MethodDecl>> map = new HashMap<String, SimpleSet<MethodDecl>>();
      for (Iterator<TypeDecl> iter = interfacesIterator(); iter.hasNext(); ) {
        InterfaceDecl iface = (InterfaceDecl) iter.next();
        for (Iterator<MethodDecl> i2 = iface.localMethodsIterator(); i2.hasNext(); ) {
          MethodDecl m = i2.next();
          putSimpleSetElement(map, m.signature(), m);
        }
        for (SimpleSet<MethodDecl> set : iface.interfacesMethodsSignatureMap().values()) {
          for (MethodDecl m : set) {
            putSimpleSetElement(map, m.signature(), m);
          }
        }
      }
      return map;
    }
  /**
   * @attribute syn
   * @aspect MemberMethods
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupMethod.jrag:510
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MemberMethods", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupMethod.jrag:510")
  public SimpleSet<MethodDecl> methodsSignature(String signature) {
    {
        SimpleSet<MethodDecl> result = methodsSignatureMap().get(signature);
        if (result != null) {
          return result;
        } else {
          return emptySet();
        }
      }
  }
  /** @apilevel internal */
  private void methodsSignatureMap_reset() {
    methodsSignatureMap_computed = null;
    methodsSignatureMap_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle methodsSignatureMap_computed = null;

  /** @apilevel internal */
  protected Map<String, SimpleSet<MethodDecl>> methodsSignatureMap_value;

  /** Maps signature String to SimpleSet. 
   * @attribute syn
   * @aspect MemberMethods
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupMethod.jrag:520
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MemberMethods", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupMethod.jrag:520")
  public Map<String, SimpleSet<MethodDecl>> methodsSignatureMap() {
    ASTNode$State state = state();
    if (methodsSignatureMap_computed == ASTNode$State.NON_CYCLE || methodsSignatureMap_computed == state().cycle()) {
      return methodsSignatureMap_value;
    }
    methodsSignatureMap_value = localMethodsSignatureMap();
    if (state().inCircle()) {
      methodsSignatureMap_computed = state().cycle();
    
    } else {
      methodsSignatureMap_computed = ASTNode$State.NON_CYCLE;
    
    }
    return methodsSignatureMap_value;
  }
  /** @apilevel internal */
  private void ancestorMethods_String_reset() {
    ancestorMethods_String_computed = new java.util.HashMap(4);
    ancestorMethods_String_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map ancestorMethods_String_values;
  /** @apilevel internal */
  protected java.util.Map ancestorMethods_String_computed;
  /**
   * Finds methods with the same signature declared in ancestors types.  This
   * is used when checking correct overriding, hiding, and implementation of
   * abstract methods.
   * @attribute syn
   * @aspect AncestorMethods
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupMethod.jrag:594
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="AncestorMethods", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupMethod.jrag:594")
  public SimpleSet<MethodDecl> ancestorMethods(String signature) {
    Object _parameters = signature;
    if (ancestorMethods_String_computed == null) ancestorMethods_String_computed = new java.util.HashMap(4);
    if (ancestorMethods_String_values == null) ancestorMethods_String_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (ancestorMethods_String_values.containsKey(_parameters) && ancestorMethods_String_computed != null
        && ancestorMethods_String_computed.containsKey(_parameters)
        && (ancestorMethods_String_computed.get(_parameters) == ASTNode$State.NON_CYCLE || ancestorMethods_String_computed.get(_parameters) == state().cycle())) {
      return (SimpleSet<MethodDecl>) ancestorMethods_String_values.get(_parameters);
    }
    SimpleSet<MethodDecl> ancestorMethods_String_value = emptySet();
    if (state().inCircle()) {
      ancestorMethods_String_values.put(_parameters, ancestorMethods_String_value);
      ancestorMethods_String_computed.put(_parameters, state().cycle());
    
    } else {
      ancestorMethods_String_values.put(_parameters, ancestorMethods_String_value);
      ancestorMethods_String_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return ancestorMethods_String_value;
  }
  /**
   * @return a set containing this type, unless this is the unknown type in
   * which case an empty set is returned
   * @attribute syn
   * @aspect TypeScopePropagation
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:307
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeScopePropagation", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:307")
  public SimpleSet<TypeDecl> asSet() {
    SimpleSet<TypeDecl> asSet_value = this;
    return asSet_value;
  }
  /**
   * @attribute syn
   * @aspect TypeScopePropagation
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:492
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeScopePropagation", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:492")
  public SimpleSet<TypeDecl> localLookupType(String name) {
    {
        SimpleSet<TypeDecl> result = memberTypes(name);
        if (!result.isEmpty()) {
          return result;
        }
        if (name().equals(name)) {
          return this;
        }
    
        result = lookupType(name);
        // 8.5.2
        if (isClassDecl() && isStatic() && !isTopLevelType()) {
          SimpleSet<TypeDecl> newSet = emptySet();
          for (TypeDecl type : result) {
            newSet = newSet.add(type);
          }
          result = newSet;
        }
        return result;
      }
  }
  /**
   * @attribute syn
   * @aspect TypeScopePropagation
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:607
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeScopePropagation", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:607")
  public boolean hasType(String name) {
    boolean hasType_String_value = !memberTypes(name).isEmpty();
    return hasType_String_value;
  }
  /** @apilevel internal */
  private void localTypeDecls_String_reset() {
    localTypeDecls_String_computed = new java.util.HashMap(4);
    localTypeDecls_String_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map localTypeDecls_String_values;
  /** @apilevel internal */
  protected java.util.Map localTypeDecls_String_computed;
  /**
   * @attribute syn
   * @aspect TypeScopePropagation
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:618
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeScopePropagation", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:618")
  public SimpleSet<TypeDecl> localTypeDecls(String name) {
    Object _parameters = name;
    if (localTypeDecls_String_computed == null) localTypeDecls_String_computed = new java.util.HashMap(4);
    if (localTypeDecls_String_values == null) localTypeDecls_String_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (localTypeDecls_String_values.containsKey(_parameters) && localTypeDecls_String_computed != null
        && localTypeDecls_String_computed.containsKey(_parameters)
        && (localTypeDecls_String_computed.get(_parameters) == ASTNode$State.NON_CYCLE || localTypeDecls_String_computed.get(_parameters) == state().cycle())) {
      return (SimpleSet<TypeDecl>) localTypeDecls_String_values.get(_parameters);
    }
    SimpleSet<TypeDecl> localTypeDecls_String_value = localTypeDecls_compute(name);
    if (state().inCircle()) {
      localTypeDecls_String_values.put(_parameters, localTypeDecls_String_value);
      localTypeDecls_String_computed.put(_parameters, state().cycle());
    
    } else {
      localTypeDecls_String_values.put(_parameters, localTypeDecls_String_value);
      localTypeDecls_String_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return localTypeDecls_String_value;
  }
  /** @apilevel internal */
  private SimpleSet<TypeDecl> localTypeDecls_compute(String name) {
      SimpleSet<TypeDecl> result = emptySet();
      for (int i = 0; i < getNumBodyDecl(); i++) {
        if (getBodyDecl(i).declaresType(name)) {
          result = result.add(getBodyDecl(i).type(name));
        }
      }
      return result;
    }
  /**
   * @attribute syn
   * @aspect TypeScopePropagation
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:629
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeScopePropagation", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:629")
  public SimpleSet<TypeDecl> memberTypes(String name) {
    SimpleSet<TypeDecl> memberTypes_String_value = emptySet();
    return memberTypes_String_value;
  }
  /** @apilevel internal */
  private void localFields_String_reset() {
    localFields_String_computed = new java.util.HashMap(4);
    localFields_String_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map localFields_String_values;
  /** @apilevel internal */
  protected java.util.Map localFields_String_computed;
  /**
   * @attribute syn
   * @aspect Fields
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupVariable.jrag:385
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Fields", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupVariable.jrag:385")
  public SimpleSet<Variable> localFields(String name) {
    Object _parameters = name;
    if (localFields_String_computed == null) localFields_String_computed = new java.util.HashMap(4);
    if (localFields_String_values == null) localFields_String_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (localFields_String_values.containsKey(_parameters) && localFields_String_computed != null
        && localFields_String_computed.containsKey(_parameters)
        && (localFields_String_computed.get(_parameters) == ASTNode$State.NON_CYCLE || localFields_String_computed.get(_parameters) == state().cycle())) {
      return (SimpleSet<Variable>) localFields_String_values.get(_parameters);
    }
    SimpleSet<Variable> localFields_String_value = localFieldsMap().containsKey(name)
          ? localFieldsMap().get(name)
          : ASTNode.<Variable>emptySet();
    if (state().inCircle()) {
      localFields_String_values.put(_parameters, localFields_String_value);
      localFields_String_computed.put(_parameters, state().cycle());
    
    } else {
      localFields_String_values.put(_parameters, localFields_String_value);
      localFields_String_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return localFields_String_value;
  }
  /** @apilevel internal */
  private void localFieldsMap_reset() {
    localFieldsMap_computed = null;
    localFieldsMap_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle localFieldsMap_computed = null;

  /** @apilevel internal */
  protected Map<String, SimpleSet<Variable>> localFieldsMap_value;

  /**
   * @attribute syn
   * @aspect Fields
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupVariable.jrag:390
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Fields", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupVariable.jrag:390")
  public Map<String, SimpleSet<Variable>> localFieldsMap() {
    ASTNode$State state = state();
    if (localFieldsMap_computed == ASTNode$State.NON_CYCLE || localFieldsMap_computed == state().cycle()) {
      return localFieldsMap_value;
    }
    localFieldsMap_value = localFieldsMap_compute();
    if (state().inCircle()) {
      localFieldsMap_computed = state().cycle();
    
    } else {
      localFieldsMap_computed = ASTNode$State.NON_CYCLE;
    
    }
    return localFieldsMap_value;
  }
  /** @apilevel internal */
  private Map<String, SimpleSet<Variable>> localFieldsMap_compute() {
      Map<String, SimpleSet<Variable>> map = new HashMap<String, SimpleSet<Variable>>();
      for (BodyDecl decl : getBodyDeclList()) {
        if (decl instanceof FieldDecl) {
          for (FieldDeclarator field : ((FieldDecl) decl).getDeclaratorList()) {
            putSimpleSetElement(map, field.name(), field);
          }
        }
      }
      return map;
    }
  /** @apilevel internal */
  private void memberFieldsMap_reset() {
    memberFieldsMap_computed = null;
    memberFieldsMap_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle memberFieldsMap_computed = null;

  /** @apilevel internal */
  protected Map<String, SimpleSet<Variable>> memberFieldsMap_value;

  /**
   * @attribute syn
   * @aspect Fields
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupVariable.jrag:402
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Fields", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupVariable.jrag:402")
  public Map<String, SimpleSet<Variable>> memberFieldsMap() {
    ASTNode$State state = state();
    if (memberFieldsMap_computed == ASTNode$State.NON_CYCLE || memberFieldsMap_computed == state().cycle()) {
      return memberFieldsMap_value;
    }
    memberFieldsMap_value = localFieldsMap();
    if (state().inCircle()) {
      memberFieldsMap_computed = state().cycle();
    
    } else {
      memberFieldsMap_computed = ASTNode$State.NON_CYCLE;
    
    }
    return memberFieldsMap_value;
  }
  /** @apilevel internal */
  private void memberFields_String_reset() {
    memberFields_String_computed = new java.util.HashMap(4);
    memberFields_String_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map memberFields_String_values;
  /** @apilevel internal */
  protected java.util.Map memberFields_String_computed;
  /**
   * @attribute syn
   * @aspect Fields
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupVariable.jrag:475
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Fields", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupVariable.jrag:475")
  public SimpleSet<Variable> memberFields(String name) {
    Object _parameters = name;
    if (memberFields_String_computed == null) memberFields_String_computed = new java.util.HashMap(4);
    if (memberFields_String_values == null) memberFields_String_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (memberFields_String_values.containsKey(_parameters) && memberFields_String_computed != null
        && memberFields_String_computed.containsKey(_parameters)
        && (memberFields_String_computed.get(_parameters) == ASTNode$State.NON_CYCLE || memberFields_String_computed.get(_parameters) == state().cycle())) {
      return (SimpleSet<Variable>) memberFields_String_values.get(_parameters);
    }
    SimpleSet<Variable> memberFields_String_value = localFields(name);
    if (state().inCircle()) {
      memberFields_String_values.put(_parameters, memberFields_String_value);
      memberFields_String_computed.put(_parameters, state().cycle());
    
    } else {
      memberFields_String_values.put(_parameters, memberFields_String_value);
      memberFields_String_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return memberFields_String_value;
  }
  /** @apilevel internal */
  private void hasAbstract_reset() {
    hasAbstract_computed = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle hasAbstract_computed = null;

  /** @apilevel internal */
  protected boolean hasAbstract_value;

  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:33
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Modifiers", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:33")
  public boolean hasAbstract() {
    ASTNode$State state = state();
    if (hasAbstract_computed == ASTNode$State.NON_CYCLE || hasAbstract_computed == state().cycle()) {
      return hasAbstract_value;
    }
    hasAbstract_value = false;
    if (state().inCircle()) {
      hasAbstract_computed = state().cycle();
    
    } else {
      hasAbstract_computed = ASTNode$State.NON_CYCLE;
    
    }
    return hasAbstract_value;
  }
  /** @apilevel internal */
  private void unimplementedMethods_reset() {
    unimplementedMethods_computed = null;
    unimplementedMethods_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle unimplementedMethods_computed = null;

  /** @apilevel internal */
  protected Collection<MethodDecl> unimplementedMethods_value;

  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:35
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Modifiers", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:35")
  public Collection<MethodDecl> unimplementedMethods() {
    ASTNode$State state = state();
    if (unimplementedMethods_computed == ASTNode$State.NON_CYCLE || unimplementedMethods_computed == state().cycle()) {
      return unimplementedMethods_value;
    }
    unimplementedMethods_value = Collections.emptyList();
    if (state().inCircle()) {
      unimplementedMethods_computed = state().cycle();
    
    } else {
      unimplementedMethods_computed = ASTNode$State.NON_CYCLE;
    
    }
    return unimplementedMethods_value;
  }
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:90
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Modifiers", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:90")
  public Collection<Problem> modifierProblems() {
    {
        Collection<Problem> problems = refined_Modifiers_TypeDecl_modifierProblems();
        if (getModifiers().hasModifier("default")) {
          problems.add(error("the default modifier is only legal for interface method declarations"));
        }
        return problems;
      }
  }
  /** @apilevel internal */
  private void isPublic_reset() {
    isPublic_computed = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle isPublic_computed = null;

  /** @apilevel internal */
  protected boolean isPublic_value;

  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:233
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Modifiers", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:233")
  public boolean isPublic() {
    ASTNode$State state = state();
    if (isPublic_computed == ASTNode$State.NON_CYCLE || isPublic_computed == state().cycle()) {
      return isPublic_value;
    }
    isPublic_value = getModifiers().isPublic() || isMemberType() && enclosingType().isInterfaceDecl();
    if (state().inCircle()) {
      isPublic_computed = state().cycle();
    
    } else {
      isPublic_computed = ASTNode$State.NON_CYCLE;
    
    }
    return isPublic_value;
  }
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:235
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Modifiers", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:235")
  public boolean isPrivate() {
    boolean isPrivate_value = getModifiers().isPrivate();
    return isPrivate_value;
  }
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:236
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Modifiers", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:236")
  public boolean isProtected() {
    boolean isProtected_value = getModifiers().isProtected();
    return isProtected_value;
  }
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:237
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Modifiers", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:237")
  public boolean isAbstract() {
    boolean isAbstract_value = getModifiers().isAbstract();
    return isAbstract_value;
  }
  /** @apilevel internal */
  private void isStatic_reset() {
    isStatic_computed = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle isStatic_computed = null;

  /** @apilevel internal */
  protected boolean isStatic_value;

  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:239
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Modifiers", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:239")
  public boolean isStatic() {
    ASTNode$State state = state();
    if (isStatic_computed == ASTNode$State.NON_CYCLE || isStatic_computed == state().cycle()) {
      return isStatic_value;
    }
    isStatic_value = getModifiers().isStatic() || isMemberType() && enclosingType().isInterfaceDecl();
    if (state().inCircle()) {
      isStatic_computed = state().cycle();
    
    } else {
      isStatic_computed = ASTNode$State.NON_CYCLE;
    
    }
    return isStatic_value;
  }
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:244
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Modifiers", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:244")
  public boolean isFinal() {
    boolean isFinal_value = getModifiers().isFinal();
    return isFinal_value;
  }
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:245
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Modifiers", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:245")
  public boolean isStrictfp() {
    boolean isStrictfp_value = getModifiers().isStrictfp();
    return isStrictfp_value;
  }
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:247
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Modifiers", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:247")
  public boolean isSynthetic() {
    boolean isSynthetic_value = getModifiers().isSynthetic();
    return isSynthetic_value;
  }
  /**
   * @attribute syn
   * @aspect NameCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\NameCheck.jrag:364
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="NameCheck", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\NameCheck.jrag:364")
  public Collection<Problem> nameProblems() {
    {
        Collection<Problem> problems = new LinkedList<Problem>();
        if (isTopLevelType() && lookupType(packageName(), name()) != this) {
          problems.add(errorf("duplicate type %s in package %s", name(), packageName()));
        }
    
        if (!isTopLevelType() && !isAnonymous() && !isLocalClass()
            && extractSingleType(enclosingType().memberTypes(name())) != this) {
          problems.add(errorf("duplicate member type %s in type %s",
              name(), enclosingType().typeName()));
        }
    
        // 14.3
        if (isLocalClass()) {
          TypeDecl typeDecl = extractSingleType(lookupType(name()));
          if (typeDecl != null && typeDecl != this && typeDecl.isLocalClass()
              && enclosingBlock() == typeDecl.enclosingBlock()) {
            problems.add(errorf(
                "local class named %s may not be redeclared as a local class in the same block",
                name()));
          }
        }
    
        if (!packageName().equals("") && hasPackage(fullName())) {
          problems.add(errorf("type name conflicts with a package using the same name: %s", name()));
        }
    
        // 8.1 & 9.1
        if (hasEnclosingTypeDecl(name())) {
          problems.add(error(
              "type may not have the same simple name as an enclosing type declaration"));
        }
        return problems;
      }
  }
  /**
   * @attribute syn
   * @aspect NameCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\NameCheck.jrag:399
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="NameCheck", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\NameCheck.jrag:399")
  public boolean hasEnclosingTypeDecl(String name) {
    {
        TypeDecl enclosingType = enclosingType();
        if (enclosingType != null) {
          return enclosingType.name().equals(name) || enclosingType.hasEnclosingTypeDecl(name);
        }
        return false;
      }
  }
  /**
   * @attribute syn
   * @aspect NameCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\NameCheck.jrag:582
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="NameCheck", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\NameCheck.jrag:582")
  public boolean assignableToInt() {
    boolean assignableToInt_value = false;
    return assignableToInt_value;
  }
  /**
   * @attribute syn
   * @aspect TypeName
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\QualifiedNames.jrag:82
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeName", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\QualifiedNames.jrag:82")
  public String name() {
    String name_value = getID();
    return name_value;
  }
  /** @apilevel internal */
  private void fullName_reset() {
    fullName_computed = null;
    fullName_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle fullName_computed = null;

  /** @apilevel internal */
  protected String fullName_value;

  /**
   * @attribute syn
   * @aspect TypeName
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\QualifiedNames.jrag:84
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeName", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\QualifiedNames.jrag:84")
  public String fullName() {
    ASTNode$State state = state();
    if (fullName_computed == ASTNode$State.NON_CYCLE || fullName_computed == state().cycle()) {
      return fullName_value;
    }
    fullName_value = fullName_compute();
    if (state().inCircle()) {
      fullName_computed = state().cycle();
    
    } else {
      fullName_computed = ASTNode$State.NON_CYCLE;
    
    }
    return fullName_value;
  }
  /** @apilevel internal */
  private String fullName_compute() {
      if (isNestedType()) {
        return enclosingType().fullName() + "." + name();
      }
      String packageName = packageName();
      if (packageName.equals("")) {
        return name();
      }
      return packageName + "." + name();
    }
  /** @apilevel internal */
  private void typeName_reset() {
    typeName_computed = null;
    typeName_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle typeName_computed = null;

  /** @apilevel internal */
  protected String typeName_value;

  /**
   * @attribute syn
   * @aspect TypeName
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\QualifiedNames.jrag:95
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeName", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\QualifiedNames.jrag:95")
  public String typeName() {
    ASTNode$State state = state();
    if (typeName_computed == ASTNode$State.NON_CYCLE || typeName_computed == state().cycle()) {
      return typeName_value;
    }
    typeName_value = typeName_compute();
    if (state().inCircle()) {
      typeName_computed = state().cycle();
    
    } else {
      typeName_computed = ASTNode$State.NON_CYCLE;
    
    }
    return typeName_value;
  }
  /** @apilevel internal */
  private String typeName_compute() {
      if (isNestedType()) {
        return enclosingType().typeName() + "." + name();
      }
      String packageName = packageName();
      if (packageName.equals("") || packageName.equals(PRIMITIVE_PACKAGE_NAME)) {
        return name();
      }
      return packageName + "." + name();
    }
  /**
   * @attribute syn
   * @aspect TypeConversion
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:36
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeConversion", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:36")
  public boolean identityConversionTo(TypeDecl type) {
    boolean identityConversionTo_TypeDecl_value = this == type;
    return identityConversionTo_TypeDecl_value;
  }
  /**
   * @attribute syn
   * @aspect TypeConversion
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:38
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeConversion", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:38")
  public boolean wideningConversionTo(TypeDecl type) {
    boolean wideningConversionTo_TypeDecl_value = instanceOf(type);
    return wideningConversionTo_TypeDecl_value;
  }
  /** @apilevel internal */
  private void narrowingConversionTo_TypeDecl_reset() {
    narrowingConversionTo_TypeDecl_computed = new java.util.HashMap(4);
    narrowingConversionTo_TypeDecl_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map narrowingConversionTo_TypeDecl_values;
  /** @apilevel internal */
  protected java.util.Map narrowingConversionTo_TypeDecl_computed;
  /**
   * @attribute syn
   * @aspect TypeConversion
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:39
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeConversion", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:39")
  public boolean narrowingConversionTo(TypeDecl type) {
    Object _parameters = type;
    if (narrowingConversionTo_TypeDecl_computed == null) narrowingConversionTo_TypeDecl_computed = new java.util.HashMap(4);
    if (narrowingConversionTo_TypeDecl_values == null) narrowingConversionTo_TypeDecl_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (narrowingConversionTo_TypeDecl_values.containsKey(_parameters) && narrowingConversionTo_TypeDecl_computed != null
        && narrowingConversionTo_TypeDecl_computed.containsKey(_parameters)
        && (narrowingConversionTo_TypeDecl_computed.get(_parameters) == ASTNode$State.NON_CYCLE || narrowingConversionTo_TypeDecl_computed.get(_parameters) == state().cycle())) {
      return (Boolean) narrowingConversionTo_TypeDecl_values.get(_parameters);
    }
    boolean narrowingConversionTo_TypeDecl_value = instanceOf(type);
    if (state().inCircle()) {
      narrowingConversionTo_TypeDecl_values.put(_parameters, narrowingConversionTo_TypeDecl_value);
      narrowingConversionTo_TypeDecl_computed.put(_parameters, state().cycle());
    
    } else {
      narrowingConversionTo_TypeDecl_values.put(_parameters, narrowingConversionTo_TypeDecl_value);
      narrowingConversionTo_TypeDecl_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return narrowingConversionTo_TypeDecl_value;
  }
  /**
   * @attribute syn
   * @aspect TypeConversion
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:79
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeConversion", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:79")
  public boolean stringConversion() {
    boolean stringConversion_value = true;
    return stringConversion_value;
  }
  /**
   * @attribute syn
   * @aspect TypeConversion
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:83
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeConversion", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:83")
  public boolean assignConversionTo(TypeDecl type, Expr expr) {
    {
        if (refined_TypeConversion_TypeDecl_assignConversionTo_TypeDecl_Expr(type, expr)) {
          return true;
        }
        boolean canBoxThis = this instanceof PrimitiveType;
        boolean canBoxType = type instanceof PrimitiveType;
        boolean canUnboxThis = !unboxed().isUnknown();
        boolean canUnboxType = !type.unboxed().isUnknown();
        TypeDecl t = !canUnboxThis && canUnboxType ? type.unboxed() : type;
        boolean sourceIsConstant = expr != null ? expr.isConstant() : false;
        if (sourceIsConstant && (isInt() || isChar() || isShort() || isByte()) &&
            (t.isByte() || t.isShort() || t.isChar()) &&
            narrowingConversionTo(t) && expr.representableIn(t))
          return true;
        if (canBoxThis && !canBoxType && boxed().wideningConversionTo(type)) {
          return true;
        } else if (canUnboxThis && !canUnboxType && unboxed().wideningConversionTo(type)) {
          return true;
        }
        return false;
      }
  }
  /** @apilevel internal */
  private void methodInvocationConversionTo_TypeDecl_reset() {
    methodInvocationConversionTo_TypeDecl_computed = new java.util.HashMap(4);
    methodInvocationConversionTo_TypeDecl_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map methodInvocationConversionTo_TypeDecl_values;
  /** @apilevel internal */
  protected java.util.Map methodInvocationConversionTo_TypeDecl_computed;
  /**
   * @attribute syn
   * @aspect TypeConversion
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:96
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeConversion", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:96")
  public boolean methodInvocationConversionTo(TypeDecl type) {
    Object _parameters = type;
    if (methodInvocationConversionTo_TypeDecl_computed == null) methodInvocationConversionTo_TypeDecl_computed = new java.util.HashMap(4);
    if (methodInvocationConversionTo_TypeDecl_values == null) methodInvocationConversionTo_TypeDecl_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (methodInvocationConversionTo_TypeDecl_values.containsKey(_parameters) && methodInvocationConversionTo_TypeDecl_computed != null
        && methodInvocationConversionTo_TypeDecl_computed.containsKey(_parameters)
        && (methodInvocationConversionTo_TypeDecl_computed.get(_parameters) == ASTNode$State.NON_CYCLE || methodInvocationConversionTo_TypeDecl_computed.get(_parameters) == state().cycle())) {
      return (Boolean) methodInvocationConversionTo_TypeDecl_values.get(_parameters);
    }
    boolean methodInvocationConversionTo_TypeDecl_value = methodInvocationConversionTo_compute(type);
    if (state().inCircle()) {
      methodInvocationConversionTo_TypeDecl_values.put(_parameters, methodInvocationConversionTo_TypeDecl_value);
      methodInvocationConversionTo_TypeDecl_computed.put(_parameters, state().cycle());
    
    } else {
      methodInvocationConversionTo_TypeDecl_values.put(_parameters, methodInvocationConversionTo_TypeDecl_value);
      methodInvocationConversionTo_TypeDecl_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return methodInvocationConversionTo_TypeDecl_value;
  }
  /** @apilevel internal */
  private boolean methodInvocationConversionTo_compute(TypeDecl type) {
      if (refined_TypeConversion_TypeDecl_methodInvocationConversionTo_TypeDecl(type)) {
        return true;
      }
      boolean canBoxThis = this instanceof PrimitiveType;
      boolean canBoxType = type instanceof PrimitiveType;
      boolean canUnboxThis = !unboxed().isUnknown();
      boolean canUnboxType = !type.unboxed().isUnknown();
      if (canBoxThis && !canBoxType) {
        return boxed().wideningConversionTo(type);
      } else if (canUnboxThis && !canUnboxType) {
        return unboxed().wideningConversionTo(type);
      }
      return false;
    }
  /** @apilevel internal */
  private void castingConversionTo_TypeDecl_reset() {
    castingConversionTo_TypeDecl_computed = new java.util.HashMap(4);
    castingConversionTo_TypeDecl_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map castingConversionTo_TypeDecl_values;
  /** @apilevel internal */
  protected java.util.Map castingConversionTo_TypeDecl_computed;
  /**
   * @attribute syn
   * @aspect TypeConversion
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:100
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeConversion", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:100")
  public boolean castingConversionTo(TypeDecl type) {
    Object _parameters = type;
    if (castingConversionTo_TypeDecl_computed == null) castingConversionTo_TypeDecl_computed = new java.util.HashMap(4);
    if (castingConversionTo_TypeDecl_values == null) castingConversionTo_TypeDecl_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (castingConversionTo_TypeDecl_values.containsKey(_parameters) && castingConversionTo_TypeDecl_computed != null
        && castingConversionTo_TypeDecl_computed.containsKey(_parameters)
        && (castingConversionTo_TypeDecl_computed.get(_parameters) == ASTNode$State.NON_CYCLE || castingConversionTo_TypeDecl_computed.get(_parameters) == state().cycle())) {
      return (Boolean) castingConversionTo_TypeDecl_values.get(_parameters);
    }
    boolean castingConversionTo_TypeDecl_value = castingConversionTo_compute(type);
    if (state().inCircle()) {
      castingConversionTo_TypeDecl_values.put(_parameters, castingConversionTo_TypeDecl_value);
      castingConversionTo_TypeDecl_computed.put(_parameters, state().cycle());
    
    } else {
      castingConversionTo_TypeDecl_values.put(_parameters, castingConversionTo_TypeDecl_value);
      castingConversionTo_TypeDecl_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return castingConversionTo_TypeDecl_value;
  }
  /** @apilevel internal */
  private boolean castingConversionTo_compute(TypeDecl type) {
      if (refined_TypeConversion_TypeDecl_castingConversionTo_TypeDecl(type)) {
        return true;
      }
      boolean thisIsPrimitive = this instanceof PrimitiveType;
      boolean typeIsPrimitive = type instanceof PrimitiveType;
      if (thisIsPrimitive && !typeIsPrimitive) {
        return boxed().wideningConversionTo(type);
      } else if (!thisIsPrimitive && typeIsPrimitive) {
        return type.boxed().wideningConversionTo(this);
      }
      return false;
    }
  /**
   * @attribute syn
   * @aspect NumericPromotion
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:157
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="NumericPromotion", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:157")
  public TypeDecl unaryNumericPromotion() {
    TypeDecl unaryNumericPromotion_value = this;
    return unaryNumericPromotion_value;
  }
  /**
   * @attribute syn
   * @aspect NumericPromotion
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:166
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="NumericPromotion", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:166")
  public TypeDecl binaryNumericPromotion(TypeDecl type) {
    TypeDecl binaryNumericPromotion_TypeDecl_value = unknownType();
    return binaryNumericPromotion_TypeDecl_value;
  }
  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:178
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeAnalysis", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:178")
  public boolean isReferenceType() {
    boolean isReferenceType_value = false;
    return isReferenceType_value;
  }
  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:182
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeAnalysis", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:182")
  public boolean isPrimitiveType() {
    boolean isPrimitiveType_value = false;
    return isPrimitiveType_value;
  }
  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:187
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeAnalysis", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:187")
  public boolean isNumericType() {
    boolean isNumericType_value = false;
    return isNumericType_value;
  }
  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:191
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeAnalysis", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:191")
  public boolean isIntegralType() {
    boolean isIntegralType_value = false;
    return isIntegralType_value;
  }
  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:195
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeAnalysis", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:195")
  public boolean isBoolean() {
    boolean isBoolean_value = false;
    return isBoolean_value;
  }
  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:199
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeAnalysis", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:199")
  public boolean isByte() {
    boolean isByte_value = false;
    return isByte_value;
  }
  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:201
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeAnalysis", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:201")
  public boolean isChar() {
    boolean isChar_value = false;
    return isChar_value;
  }
  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:203
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeAnalysis", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:203")
  public boolean isShort() {
    boolean isShort_value = false;
    return isShort_value;
  }
  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:205
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeAnalysis", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:205")
  public boolean isInt() {
    boolean isInt_value = false;
    return isInt_value;
  }
  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:209
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeAnalysis", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:209")
  public boolean isFloat() {
    boolean isFloat_value = false;
    return isFloat_value;
  }
  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:211
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeAnalysis", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:211")
  public boolean isLong() {
    boolean isLong_value = false;
    return isLong_value;
  }
  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:213
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeAnalysis", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:213")
  public boolean isDouble() {
    boolean isDouble_value = false;
    return isDouble_value;
  }
  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:216
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeAnalysis", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:216")
  public boolean isVoid() {
    boolean isVoid_value = false;
    return isVoid_value;
  }
  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:219
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeAnalysis", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:219")
  public boolean isNull() {
    boolean isNull_value = false;
    return isNull_value;
  }
  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:223
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeAnalysis", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:223")
  public boolean isClassDecl() {
    boolean isClassDecl_value = false;
    return isClassDecl_value;
  }
  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:227
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeAnalysis", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:227")
  public boolean isInterfaceDecl() {
    boolean isInterfaceDecl_value = false;
    return isInterfaceDecl_value;
  }
  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:229
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeAnalysis", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:229")
  public boolean isArrayDecl() {
    boolean isArrayDecl_value = false;
    return isArrayDecl_value;
  }
  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:237
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeAnalysis", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:237")
  public boolean isPrimitive() {
    boolean isPrimitive_value = false;
    return isPrimitive_value;
  }
  /** @apilevel internal */
  private void isString_reset() {
    isString_computed = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle isString_computed = null;

  /** @apilevel internal */
  protected boolean isString_value;

  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:240
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeAnalysis", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:240")
  public boolean isString() {
    ASTNode$State state = state();
    if (isString_computed == ASTNode$State.NON_CYCLE || isString_computed == state().cycle()) {
      return isString_value;
    }
    isString_value = false;
    if (state().inCircle()) {
      isString_computed = state().cycle();
    
    } else {
      isString_computed = ASTNode$State.NON_CYCLE;
    
    }
    return isString_value;
  }
  /** @apilevel internal */
  private void isObject_reset() {
    isObject_computed = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle isObject_computed = null;

  /** @apilevel internal */
  protected boolean isObject_value;

  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:243
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeAnalysis", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:243")
  public boolean isObject() {
    ASTNode$State state = state();
    if (isObject_computed == ASTNode$State.NON_CYCLE || isObject_computed == state().cycle()) {
      return isObject_value;
    }
    isObject_value = false;
    if (state().inCircle()) {
      isObject_computed = state().cycle();
    
    } else {
      isObject_computed = ASTNode$State.NON_CYCLE;
    
    }
    return isObject_value;
  }
  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:246
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeAnalysis", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:246")
  public boolean isUnknown() {
    boolean isUnknown_value = false;
    return isUnknown_value;
  }
  /** @apilevel internal */
  private void instanceOf_TypeDecl_reset() {
    instanceOf_TypeDecl_computed = new java.util.HashMap(4);
    instanceOf_TypeDecl_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map instanceOf_TypeDecl_values;
  /** @apilevel internal */
  protected java.util.Map instanceOf_TypeDecl_computed;
  /**
   * @attribute syn
   * @aspect TypeWideningAndIdentity
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:443
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeWideningAndIdentity", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:443")
  public boolean instanceOf(TypeDecl type) {
    Object _parameters = type;
    if (instanceOf_TypeDecl_computed == null) instanceOf_TypeDecl_computed = new java.util.HashMap(4);
    if (instanceOf_TypeDecl_values == null) instanceOf_TypeDecl_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (instanceOf_TypeDecl_values.containsKey(_parameters) && instanceOf_TypeDecl_computed != null
        && instanceOf_TypeDecl_computed.containsKey(_parameters)
        && (instanceOf_TypeDecl_computed.get(_parameters) == ASTNode$State.NON_CYCLE || instanceOf_TypeDecl_computed.get(_parameters) == state().cycle())) {
      return (Boolean) instanceOf_TypeDecl_values.get(_parameters);
    }
    boolean instanceOf_TypeDecl_value = instanceOf_compute(type);
    if (state().inCircle()) {
      instanceOf_TypeDecl_values.put(_parameters, instanceOf_TypeDecl_value);
      instanceOf_TypeDecl_computed.put(_parameters, state().cycle());
    
    } else {
      instanceOf_TypeDecl_values.put(_parameters, instanceOf_TypeDecl_value);
      instanceOf_TypeDecl_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return instanceOf_TypeDecl_value;
  }
  /** @apilevel internal */
  private boolean instanceOf_compute(TypeDecl type) {
      return subtype(type);
    }
  /**
   * @attribute syn
   * @aspect TypeWideningAndIdentity
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:459
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeWideningAndIdentity", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:459")
  public boolean isSupertypeOfClassDecl(ClassDecl type) {
    boolean isSupertypeOfClassDecl_ClassDecl_value = type == this;
    return isSupertypeOfClassDecl_ClassDecl_value;
  }
  /**
   * @attribute syn
   * @aspect TypeWideningAndIdentity
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:479
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeWideningAndIdentity", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:479")
  public boolean isSupertypeOfInterfaceDecl(InterfaceDecl type) {
    boolean isSupertypeOfInterfaceDecl_InterfaceDecl_value = type == this;
    return isSupertypeOfInterfaceDecl_InterfaceDecl_value;
  }
  /**
   * @attribute syn
   * @aspect TypeWideningAndIdentity
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:494
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeWideningAndIdentity", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:494")
  public boolean isSupertypeOfArrayDecl(ArrayDecl type) {
    boolean isSupertypeOfArrayDecl_ArrayDecl_value = this == type;
    return isSupertypeOfArrayDecl_ArrayDecl_value;
  }
  /**
   * @attribute syn
   * @aspect TypeWideningAndIdentity
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:520
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeWideningAndIdentity", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:520")
  public boolean isSupertypeOfPrimitiveType(PrimitiveType type) {
    boolean isSupertypeOfPrimitiveType_PrimitiveType_value = type == this;
    return isSupertypeOfPrimitiveType_PrimitiveType_value;
  }
  /**
   * @attribute syn
   * @aspect TypeWideningAndIdentity
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:530
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeWideningAndIdentity", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:530")
  public boolean isSupertypeOfNullType(NullType type) {
    boolean isSupertypeOfNullType_NullType_value = false;
    return isSupertypeOfNullType_NullType_value;
  }
  /**
   * @attribute syn
   * @aspect TypeWideningAndIdentity
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:534
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeWideningAndIdentity", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:534")
  public boolean isSupertypeOfVoidType(VoidType type) {
    boolean isSupertypeOfVoidType_VoidType_value = false;
    return isSupertypeOfVoidType_VoidType_value;
  }
  /**
   * @return {@code true} if this type has an enclosing type which is
   * a subtype of the given type, or equal to the given type.
   * @attribute syn
   * @aspect NestedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:551
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="NestedTypes", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:551")
  public boolean hasEnclosingType(TypeDecl type) {
    boolean hasEnclosingType_TypeDecl_value = isInnerType() && (enclosingType().instanceOf(type)
              || enclosingType().hasEnclosingType(type));
    return hasEnclosingType_TypeDecl_value;
  }
  /**
   * @attribute syn
   * @aspect NestedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:556
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="NestedTypes", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:556")
  public TypeDecl topLevelType() {
    {
        if (isTopLevelType()) {
          return this;
        }
        return enclosingType().topLevelType();
      }
  }
  /**
   * @attribute syn
   * @aspect NestedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:585
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="NestedTypes", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:585")
  public boolean isTopLevelType() {
    boolean isTopLevelType_value = !isNestedType();
    return isTopLevelType_value;
  }
  /**
   * @attribute syn
   * @aspect NestedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:596
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="NestedTypes", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:596")
  public boolean isInnerClass() {
    boolean isInnerClass_value = false;
    return isInnerClass_value;
  }
  /**
   * @attribute syn
   * @aspect NestedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:600
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="NestedTypes", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:600")
  public boolean isInnerType() {
    boolean isInnerType_value = (isLocalClass() || isAnonymous() || (isMemberType() && !isStatic())) && !inStaticContext();
    return isInnerType_value;
  }
  /**
   * @attribute syn
   * @aspect NestedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:603
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="NestedTypes", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:603")
  public boolean isInnerTypeOf(TypeDecl typeDecl) {
    boolean isInnerTypeOf_TypeDecl_value = typeDecl == this || (isInnerType() && enclosingType().isInnerTypeOf(typeDecl));
    return isInnerTypeOf_TypeDecl_value;
  }
  /**
   * @attribute syn
   * @aspect NestedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:614
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="NestedTypes", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:614")
  public TypeDecl withinBodyThatSubclasses(TypeDecl type) {
    {
        if (instanceOf(type)) {
          return this;
        }
        if (!isTopLevelType()) {
          return enclosingType().withinBodyThatSubclasses(type);
        }
        return null;
      }
  }
  /**
   * @attribute syn
   * @aspect NestedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:624
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="NestedTypes", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:624")
  public boolean encloses(TypeDecl type) {
    boolean encloses_TypeDecl_value = type.enclosedBy(this);
    return encloses_TypeDecl_value;
  }
  /**
   * @attribute syn
   * @aspect NestedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:626
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="NestedTypes", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:626")
  public boolean enclosedBy(TypeDecl type) {
    {
        if (this == type) {
          return true;
        }
        if (isTopLevelType()) {
          return false;
        }
        return enclosingType().enclosedBy(type);
      }
  }
  /**
   * @attribute syn
   * @aspect NestedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:643
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="NestedTypes", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:643")
  public TypeDecl hostType() {
    TypeDecl hostType_value = this;
    return hostType_value;
  }
  /**
   * Iterate over interfaces which this type implements.
   * @return interface iterator
   * @attribute syn
   * @aspect SuperClasses
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:689
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="SuperClasses", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:689")
  public Iterator<TypeDecl> interfacesIterator() {
    Iterator<TypeDecl> interfacesIterator_value = superInterfaces().iterator();
    return interfacesIterator_value;
  }
  /** @apilevel internal */
  private void superInterfaces_reset() {
    superInterfaces_computed = null;
    superInterfaces_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle superInterfaces_computed = null;

  /** @apilevel internal */
  protected Collection<TypeDecl> superInterfaces_value;

  /**
   * @return the interfaces directly implemented by this type.
   * @attribute syn
   * @aspect SuperClasses
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:694
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="SuperClasses", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:694")
  public Collection<TypeDecl> superInterfaces() {
    ASTNode$State state = state();
    if (superInterfaces_computed == ASTNode$State.NON_CYCLE || superInterfaces_computed == state().cycle()) {
      return superInterfaces_value;
    }
    superInterfaces_value = Collections.emptyList();
    if (state().inCircle()) {
      superInterfaces_computed = state().cycle();
    
    } else {
      superInterfaces_computed = ASTNode$State.NON_CYCLE;
    
    }
    return superInterfaces_value;
  }
/** @apilevel internal */
protected ASTNode$State.Cycle isCircular_cycle = null;
  /** @apilevel internal */
  private void isCircular_reset() {
    isCircular_computed = false;
    isCircular_initialized = false;
    isCircular_cycle = null;
  }
  /** @apilevel internal */
  protected boolean isCircular_computed = false;

  /** @apilevel internal */
  protected boolean isCircular_value;
  /** @apilevel internal */
  protected boolean isCircular_initialized = false;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="Circularity", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:726")
  public boolean isCircular() {
    if (isCircular_computed) {
      return isCircular_value;
    }
    ASTNode$State state = state();
    if (!isCircular_initialized) {
      isCircular_initialized = true;
      isCircular_value = true;
    }
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      do {
        isCircular_cycle = state.nextCycle();
        boolean new_isCircular_value = false;
        if (new_isCircular_value != isCircular_value) {
          state.setChangeInCycle();
        }
        isCircular_value = new_isCircular_value;
      } while (state.testAndClearChangeInCycle());
      isCircular_computed = true;

      state.leaveCircle();
    } else if (isCircular_cycle != state.cycle()) {
      isCircular_cycle = state.cycle();
      boolean new_isCircular_value = false;
      if (new_isCircular_value != isCircular_value) {
        state.setChangeInCycle();
      }
      isCircular_value = new_isCircular_value;
    } else {
    }
    return isCircular_value;
  }
  /**
   * @attribute syn
   * @aspect TypeHierarchyCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:233
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeHierarchyCheck", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:233")
  public Collection<Problem> typeProblems() {
    {
        Collection<Problem> problems = new LinkedList<Problem>();
        for (Iterator iter1 = localMethodsIterator(); iter1.hasNext(); ) {
          MethodDecl m = (MethodDecl) iter1.next();
          ASTNode target = m.hostType() == this ? (ASTNode) m : (ASTNode) this;
    
          for (MethodDecl decl : ancestorMethods(m.signature())) {
            if (m.overrides(decl)) {
              // 8.4.6.1
              if (!decl.hostType().isInterfaceDecl() && !m.isStatic() && decl.isStatic()) {
                problems.add(target.error("an instance method may not override a static method"));
              }
    
              // Regardless of overriding.
              // 8.4.6.3
              if (!m.mayOverride(decl)) {
                // 9.4.3
                if (m.isDefault() && decl.hostType() == m.type().typeObject() && !decl.isPrivate()) {
                  problems.add(target.error("default methods may not override methods in Object"));
                } else {
                  problems.add(target.errorf(
                      "the return type of method %s in %s does not match the return type of"
                      + " method %s in %s and may thus not be overridden",
                      m.fullSignature(), m.hostType().typeName(), decl.fullSignature(),
                      decl.hostType().typeName()));
                }
              }
    
              // regardless of overriding
              // 8.4.4
              for (Access e: m.getExceptionList()) {
                if (e.type().isCheckedException()) {
                  boolean found = false;
                  for (Access declException: decl.getExceptionList()) {
                    if (e.type().instanceOf(declException.type())) {
                      found = true;
                      break;
                    }
                  }
                  if (!found) {
                    problems.add(target.errorf("%s in %s may not throw more checked exceptions than"
                        + " overridden method %s in %s",
                        m.fullSignature(), m.hostType().typeName(), decl.fullSignature(),
                        decl.hostType().typeName()));
                  }
                }
              }
    
              // 8.4.6.3
              if (decl.isPublic() && !m.isPublic()) {
                problems.add(target.error("overriding access modifier error"));
              }
              // 8.4.6.3
              if (decl.isProtected() && !(m.isPublic() || m.isProtected())) {
                problems.add(target.error("overriding access modifier error"));
              }
              // 8.4.6.3
              if ((!decl.isPrivate() && !decl.isProtected() && !decl.isPublic()) && m.isPrivate()) {
                problems.add(target.error("overriding access modifier error"));
              }
              // regardless of overriding
              if (decl.isFinal()) {
                problems.add(target.errorf("method %s in %s can not override final method %s in %s",
                    m.fullSignature(), hostType().typeName(), decl.fullSignature(),
                    decl.hostType().typeName()));
              }
            }
            if (m.hides(decl)) {
              // 8.4.6.2
              if (m.isStatic() && !decl.isStatic()) {
                problems.add(target.error("a static method may not hide an instance method"));
              }
              // 8.4.6.3
              if (!m.mayOverride(decl)) {
                problems.add(target.error("can not hide a method with a different return type"));
              }
              // 8.4.4
              for (int i = 0; i < m.getNumException(); i++) {
                Access e = m.getException(i);
                boolean found = false;
                for (int j = 0; !found && j < decl.getNumException(); j++) {
                  if (e.type().instanceOf(decl.getException(j).type())) {
                    found = true;
                  }
                }
                if (!found) {
                  problems.add(target.error("may not throw more checked exceptions than hidden method"));
                }
              }
              // 8.4.6.3
              if (decl.isPublic() && !m.isPublic()) {
                problems.add(target.errorf("hiding access modifier: public method %s in %s is hidden by"
                    + " non public method %s in %s",
                    decl.fullSignature(), decl.hostType().typeName(), m.fullSignature(),
                    m.hostType().typeName()));
              }
              // 8.4.6.3
              if (decl.isProtected() && !(m.isPublic() || m.isProtected())) {
                problems.add(target.errorf("hiding access modifier: protected method %s in %s is hidden by"
                    + " non (public|protected) method %s in %s",
                    decl.fullSignature(), decl.hostType().typeName(), m.fullSignature(),
                    m.hostType().typeName()));
              }
              // 8.4.6.3
              if ((!decl.isPrivate() && !decl.isProtected() && !decl.isPublic()) && m.isPrivate()) {
                problems.add(target.errorf("hiding access modifier: default method %s in %s is hidden by"
                    + " private method %s in %s",
                    decl.fullSignature(), decl.hostType().typeName(), m.fullSignature(),
                    m.hostType().typeName()));
              }
              if (decl.isFinal()) {
                problems.add(target.errorf("method %s in %s can not hide final method %s in %s",
                    m.fullSignature(), hostType().typeName(), decl.fullSignature(),
                    decl.hostType().typeName()));
              }
            }
          }
        }
    
        // Different parameterizations of the same generic interface may not be implemented.
        ArrayList<InterfaceDecl> interfaceList = new ArrayList<InterfaceDecl>();
        interfaceList.addAll(implementedInterfaces());
        for (int i = 0; i < interfaceList.size(); i++) {
          InterfaceDecl decl = interfaceList.get(i);
          if (decl instanceof ParInterfaceDecl) {
            ParInterfaceDecl p = (ParInterfaceDecl) decl;
            for (Iterator<InterfaceDecl> i2 = interfaceList.listIterator(i); i2.hasNext(); ) {
              InterfaceDecl decl2 = i2.next();
              if (decl2 instanceof ParInterfaceDecl) {
                ParInterfaceDecl q = (ParInterfaceDecl) decl2;
                if (p != q && p.genericDecl() == q.genericDecl() && !p.sameArguments(q)) {
                  problems.add(errorf("%s cannot be inherited with different type arguments: %s and %s",
                      p.genericDecl().name(), p.typeName(), q.typeName()));
                }
              }
            }
          }
        }
        return problems;
      }
  }
  /**
   * @attribute syn
   * @aspect Annotations
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Annotations.jrag:199
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Annotations", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Annotations.jrag:199")
  public boolean isValidAnnotationMethodReturnType() {
    boolean isValidAnnotationMethodReturnType_value = false;
    return isValidAnnotationMethodReturnType_value;
  }
  /**
   * @attribute syn
   * @aspect Annotations
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Annotations.jrag:347
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Annotations", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Annotations.jrag:347")
  public boolean isType(String packageName, String name) {
    boolean isType_String_String_value = getID().equals(name) && packageName().equals(packageName);
    return isType_String_String_value;
  }
  /**
   * @attribute syn
   * @aspect Annotations
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Annotations.jrag:350
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Annotations", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Annotations.jrag:350")
  public Annotation annotation(TypeDecl typeDecl) {
    Annotation annotation_TypeDecl_value = getModifiers().annotation(typeDecl);
    return annotation_TypeDecl_value;
  }
  /**
   * @attribute syn
   * @aspect Annotations
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Annotations.jrag:425
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Annotations", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Annotations.jrag:425")
  public boolean hasAnnotationSuppressWarnings(String annot) {
    boolean hasAnnotationSuppressWarnings_String_value = getModifiers().hasAnnotationSuppressWarnings(annot);
    return hasAnnotationSuppressWarnings_String_value;
  }
  /**
   * @attribute syn
   * @aspect Annotations
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Annotations.jrag:481
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Annotations", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Annotations.jrag:481")
  public boolean isDeprecated() {
    boolean isDeprecated_value = getModifiers().hasDeprecatedAnnotation();
    return isDeprecated_value;
  }
  /**
   * @attribute syn
   * @aspect Annotations
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Annotations.jrag:663
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Annotations", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Annotations.jrag:663")
  public boolean commensurateWith(ElementValue value) {
    boolean commensurateWith_ElementValue_value = value.commensurateWithTypeDecl(this);
    return commensurateWith_ElementValue_value;
  }
  /**
   * @attribute syn
   * @aspect Annotations
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Annotations.jrag:761
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Annotations", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Annotations.jrag:761")
  public boolean isAnnotationDecl() {
    boolean isAnnotationDecl_value = false;
    return isAnnotationDecl_value;
  }
  /**
   * @attribute syn
   * @aspect AutoBoxing
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\AutoBoxing.jrag:48
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="AutoBoxing", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\AutoBoxing.jrag:48")
  public boolean boxingConversionTo(TypeDecl typeDecl) {
    boolean boxingConversionTo_TypeDecl_value = false;
    return boxingConversionTo_TypeDecl_value;
  }
  /** @apilevel internal */
  private void boxed_reset() {
    boxed_computed = null;
    boxed_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle boxed_computed = null;

  /** @apilevel internal */
  protected TypeDecl boxed_value;

  /** Mapping between Primitive type and corresponding boxed Reference type. 
   * @attribute syn
   * @aspect AutoBoxing
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\AutoBoxing.jrag:53
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="AutoBoxing", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\AutoBoxing.jrag:53")
  public TypeDecl boxed() {
    ASTNode$State state = state();
    if (boxed_computed == ASTNode$State.NON_CYCLE || boxed_computed == state().cycle()) {
      return boxed_value;
    }
    boxed_value = unknownType();
    if (state().inCircle()) {
      boxed_computed = state().cycle();
    
    } else {
      boxed_computed = ASTNode$State.NON_CYCLE;
    
    }
    return boxed_value;
  }
  /**
   * @attribute syn
   * @aspect AutoBoxing
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\AutoBoxing.jrag:73
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="AutoBoxing", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\AutoBoxing.jrag:73")
  public boolean unboxingConversionTo(TypeDecl typeDecl) {
    boolean unboxingConversionTo_TypeDecl_value = false;
    return unboxingConversionTo_TypeDecl_value;
  }
  /** @apilevel internal */
  private void unboxed_reset() {
    unboxed_computed = null;
    unboxed_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle unboxed_computed = null;

  /** @apilevel internal */
  protected TypeDecl unboxed_value;

  /** Mapping between Reference type and corresponding unboxed Primitive type. 
   * @attribute syn
   * @aspect AutoBoxing
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\AutoBoxing.jrag:77
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="AutoBoxing", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\AutoBoxing.jrag:77")
  public TypeDecl unboxed() {
    ASTNode$State state = state();
    if (unboxed_computed == ASTNode$State.NON_CYCLE || unboxed_computed == state().cycle()) {
      return unboxed_value;
    }
    unboxed_value = unknownType();
    if (state().inCircle()) {
      unboxed_computed = state().cycle();
    
    } else {
      unboxed_computed = ASTNode$State.NON_CYCLE;
    
    }
    return unboxed_value;
  }
  /** @apilevel internal */
  private void isIterable_reset() {
    isIterable_computed = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle isIterable_computed = null;

  /** @apilevel internal */
  protected boolean isIterable_value;

  /**
   * True if type is java.lang.Iterable or subtype
   * As long as we use the 1.4 API we check for java.util.Collection instead.
   * @attribute syn
   * @aspect EnhancedFor
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\EnhancedFor.jrag:69
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="EnhancedFor", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\EnhancedFor.jrag:69")
  public boolean isIterable() {
    ASTNode$State state = state();
    if (isIterable_computed == ASTNode$State.NON_CYCLE || isIterable_computed == state().cycle()) {
      return isIterable_value;
    }
    isIterable_value = instanceOf(lookupType("java.lang", "Iterable"));
    if (state().inCircle()) {
      isIterable_computed = state().cycle();
    
    } else {
      isIterable_computed = ASTNode$State.NON_CYCLE;
    
    }
    return isIterable_value;
  }
  /** @apilevel internal */
  private void iterableElementType_reset() {
    iterableElementType_computed = null;
    iterableElementType_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle iterableElementType_computed = null;

  /** @apilevel internal */
  protected TypeDecl iterableElementType_value;

  /**
   * Computes the element type of a type implementing java.lang.Iterable.
   * Returns UnknownType if this type does not implement java.lang.Iterable.
   * @attribute syn
   * @aspect EnhancedFor
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\EnhancedFor.jrag:75
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="EnhancedFor", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\EnhancedFor.jrag:75")
  public TypeDecl iterableElementType() {
    ASTNode$State state = state();
    if (iterableElementType_computed == ASTNode$State.NON_CYCLE || iterableElementType_computed == state().cycle()) {
      return iterableElementType_value;
    }
    iterableElementType_value = iterableElementType_compute();
    if (state().inCircle()) {
      iterableElementType_computed = state().cycle();
    
    } else {
      iterableElementType_computed = ASTNode$State.NON_CYCLE;
    
    }
    return iterableElementType_value;
  }
  /** @apilevel internal */
  private TypeDecl iterableElementType_compute() {
      if (isType("java.lang", "Iterable")) {
        return firstTypeArgument();
      } else {
        return unknownType();
      }
    }
  /** @apilevel internal */
  private void firstTypeArgument_reset() {
    firstTypeArgument_computed = null;
    firstTypeArgument_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle firstTypeArgument_computed = null;

  /** @apilevel internal */
  protected TypeDecl firstTypeArgument_value;

  /**
   * Returns the first type argument of this type, if it is parameterized, otherwise returns
   * java.lang.Object.
   * @attribute syn
   * @aspect EnhancedFor
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\EnhancedFor.jrag:117
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="EnhancedFor", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\EnhancedFor.jrag:117")
  public TypeDecl firstTypeArgument() {
    ASTNode$State state = state();
    if (firstTypeArgument_computed == ASTNode$State.NON_CYCLE || firstTypeArgument_computed == state().cycle()) {
      return firstTypeArgument_value;
    }
    firstTypeArgument_value = typeObject();
    if (state().inCircle()) {
      firstTypeArgument_computed = state().cycle();
    
    } else {
      firstTypeArgument_computed = ASTNode$State.NON_CYCLE;
    
    }
    return firstTypeArgument_value;
  }
  /**
   * @attribute syn
   * @aspect Enums
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Enums.jrag:38
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Enums", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Enums.jrag:38")
  public boolean isEnumDecl() {
    boolean isEnumDecl_value = false;
    return isEnumDecl_value;
  }
  /**
   * @param bound the bounded type variable
   * @return {@code true} if this type is within the bounds of the parameter type
   * @attribute syn
   * @aspect GenericBoundCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericBoundCheck.jrag:40
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericBoundCheck", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericBoundCheck.jrag:40")
  public boolean withinBounds(TypeDecl bound) {
    boolean withinBounds_TypeDecl_value = bound.boundOf(this);
    return withinBounds_TypeDecl_value;
  }
  /**
   * Check if a given type is within the bound of this type, given a specific
   * parameterization of this type.
   * 
   * See JLS SE7 $4.5
   * 
   * @param argument argument type
   * @return {@code true} if the argument type is in the bound of this type
   * @attribute syn
   * @aspect GenericBoundCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericBoundCheck.jrag:61
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericBoundCheck", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericBoundCheck.jrag:61")
  public boolean boundOf(TypeDecl argument) {
    boolean boundOf_TypeDecl_value = !isPrimitive() && argument.subtype(this);
    return boundOf_TypeDecl_value;
  }
  /**
   * @attribute syn
   * @aspect GenericBoundCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericBoundCheck.jrag:73
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericBoundCheck", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericBoundCheck.jrag:73")
  public boolean boundOfWildcard(WildcardType type) {
    boolean boundOfWildcard_WildcardType_value = false;
    return boundOfWildcard_WildcardType_value;
  }
  /**
   * @attribute syn
   * @aspect GenericBoundCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericBoundCheck.jrag:77
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericBoundCheck", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericBoundCheck.jrag:77")
  public boolean boundOfWildcardExtends(WildcardExtendsType type) {
    boolean boundOfWildcardExtends_WildcardExtendsType_value = false;
    return boundOfWildcardExtends_WildcardExtendsType_value;
  }
  /**
   * @attribute syn
   * @aspect GenericBoundCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericBoundCheck.jrag:82
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericBoundCheck", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericBoundCheck.jrag:82")
  public boolean boundOfWildcardSuper(WildcardSuperType type) {
    boolean boundOfWildcardSuper_WildcardSuperType_value = false;
    return boundOfWildcardSuper_WildcardSuperType_value;
  }
  /**
   * @attribute syn
   * @aspect GenericBoundCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericBoundCheck.jrag:87
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericBoundCheck", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericBoundCheck.jrag:87")
  public boolean boundOfArray(ArrayDecl type) {
    boolean boundOfArray_ArrayDecl_value = this == typeObject();
    return boundOfArray_ArrayDecl_value;
  }
  /**
   * @attribute syn
   * @aspect GenericMethodsInference
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericMethodsInference.jrag:35
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericMethodsInference", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericMethodsInference.jrag:35")
  public boolean isUnboxedPrimitive() {
    boolean isUnboxedPrimitive_value = this instanceof PrimitiveType && isPrimitive();
    return isUnboxedPrimitive_value;
  }
/** @apilevel internal */
protected ASTNode$State.Cycle involvesTypeParameters_cycle = null;
  /** @apilevel internal */
  private void involvesTypeParameters_reset() {
    involvesTypeParameters_computed = false;
    involvesTypeParameters_initialized = false;
    involvesTypeParameters_cycle = null;
  }
  /** @apilevel internal */
  protected boolean involvesTypeParameters_computed = false;

  /** @apilevel internal */
  protected boolean involvesTypeParameters_value;
  /** @apilevel internal */
  protected boolean involvesTypeParameters_initialized = false;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="GenericMethodsInference", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericMethodsInference.jrag:37")
  public boolean involvesTypeParameters() {
    if (involvesTypeParameters_computed) {
      return involvesTypeParameters_value;
    }
    ASTNode$State state = state();
    if (!involvesTypeParameters_initialized) {
      involvesTypeParameters_initialized = true;
      involvesTypeParameters_value = false;
    }
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      do {
        involvesTypeParameters_cycle = state.nextCycle();
        boolean new_involvesTypeParameters_value = false;
        if (new_involvesTypeParameters_value != involvesTypeParameters_value) {
          state.setChangeInCycle();
        }
        involvesTypeParameters_value = new_involvesTypeParameters_value;
      } while (state.testAndClearChangeInCycle());
      involvesTypeParameters_computed = true;

      state.leaveCircle();
    } else if (involvesTypeParameters_cycle != state.cycle()) {
      involvesTypeParameters_cycle = state.cycle();
      boolean new_involvesTypeParameters_value = false;
      if (new_involvesTypeParameters_value != involvesTypeParameters_value) {
        state.setChangeInCycle();
      }
      involvesTypeParameters_value = new_involvesTypeParameters_value;
    } else {
    }
    return involvesTypeParameters_value;
  }
  /**
   * @attribute syn
   * @aspect Generics
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:219
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Generics", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:219")
  public boolean isGenericType() {
    boolean isGenericType_value = false;
    return isGenericType_value;
  }
  /**
   * @attribute syn
   * @aspect Generics
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:310
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Generics", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:310")
  public boolean isParameterizedType() {
    boolean isParameterizedType_value = false;
    return isParameterizedType_value;
  }
  /**
   * @attribute syn
   * @aspect Generics
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:318
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Generics", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:318")
  public boolean isRawType() {
    boolean isRawType_value = isNestedType() && enclosingType().isRawType();
    return isRawType_value;
  }
  /** @apilevel internal */
  private void erasure_reset() {
    erasure_computed = null;
    erasure_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle erasure_computed = null;

  /** @apilevel internal */
  protected TypeDecl erasure_value;

  /**
   * @attribute syn
   * @aspect GenericsErasure
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:418
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsErasure", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:418")
  public TypeDecl erasure() {
    ASTNode$State state = state();
    if (erasure_computed == ASTNode$State.NON_CYCLE || erasure_computed == state().cycle()) {
      return erasure_value;
    }
    erasure_value = erasure_compute();
    if (state().inCircle()) {
      erasure_computed = state().cycle();
    
    } else {
      erasure_computed = ASTNode$State.NON_CYCLE;
    
    }
    return erasure_value;
  }
  /** @apilevel internal */
  private TypeDecl erasure_compute() {
      if (isAnonymous() || isLocalClass()) {
        return this;
      }
      if (!isNestedType()) {
        return this;
      }
      return extractSingleType(enclosingType().erasure().memberTypes(name()));
    }
  /** @apilevel internal */
  private void erasedAncestorMethodsMap_reset() {
    erasedAncestorMethodsMap_computed = null;
    erasedAncestorMethodsMap_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle erasedAncestorMethodsMap_computed = null;

  /** @apilevel internal */
  protected Map<String, SimpleSet<MethodDecl>> erasedAncestorMethodsMap_value;

  /**
   * @attribute syn
   * @aspect GenericsTypeCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:503
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsTypeCheck", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:503")
  public Map<String, SimpleSet<MethodDecl>> erasedAncestorMethodsMap() {
    ASTNode$State state = state();
    if (erasedAncestorMethodsMap_computed == ASTNode$State.NON_CYCLE || erasedAncestorMethodsMap_computed == state().cycle()) {
      return erasedAncestorMethodsMap_value;
    }
    erasedAncestorMethodsMap_value = original().localMethodsSignatureMap();
    if (state().inCircle()) {
      erasedAncestorMethodsMap_computed = state().cycle();
    
    } else {
      erasedAncestorMethodsMap_computed = ASTNode$State.NON_CYCLE;
    
    }
    return erasedAncestorMethodsMap_value;
  }
  /** @apilevel internal */
  private void implementedInterfaces_reset() {
    implementedInterfaces_computed = null;
    implementedInterfaces_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle implementedInterfaces_computed = null;

  /** @apilevel internal */
  protected Collection<InterfaceDecl> implementedInterfaces_value;

  /**
   * @attribute syn
   * @aspect GenericsTypeCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:612
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsTypeCheck", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:612")
  public Collection<InterfaceDecl> implementedInterfaces() {
    ASTNode$State state = state();
    if (implementedInterfaces_computed == ASTNode$State.NON_CYCLE || implementedInterfaces_computed == state().cycle()) {
      return implementedInterfaces_value;
    }
    implementedInterfaces_value = Collections.emptySet();
    if (state().inCircle()) {
      implementedInterfaces_computed = state().cycle();
    
    } else {
      implementedInterfaces_computed = ASTNode$State.NON_CYCLE;
    
    }
    return implementedInterfaces_value;
  }
  /**
   * @attribute syn
   * @aspect LookupParTypeDecl
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:801
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="LookupParTypeDecl", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:801")
  public boolean sameSignature(Access a) {
    {
        if (a instanceof ParTypeAccess) {
          return false;
        }
        if (a instanceof AbstractWildcard) {
          return false;
        }
        return this == a.type();
      }
  }
  /**
   * Replaces wildcards in generic type arguments by ? extends with the
   * type bound of the corresponding type parameter.
   * @attribute syn
   * @aspect LookupParTypeDecl
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:1120
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="LookupParTypeDecl", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:1120")
  public TypeDecl expandWildcard(TypeVariable param) {
    TypeDecl expandWildcard_TypeVariable_value = this;
    return expandWildcard_TypeVariable_value;
  }
/** @apilevel internal */
protected ASTNode$State.Cycle usesTypeVariable_cycle = null;
  /** @apilevel internal */
  private void usesTypeVariable_reset() {
    usesTypeVariable_computed = false;
    usesTypeVariable_initialized = false;
    usesTypeVariable_cycle = null;
  }
  /** @apilevel internal */
  protected boolean usesTypeVariable_computed = false;

  /** @apilevel internal */
  protected boolean usesTypeVariable_value;
  /** @apilevel internal */
  protected boolean usesTypeVariable_initialized = false;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="LookupParTypeDecl", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:1189")
  public boolean usesTypeVariable() {
    if (usesTypeVariable_computed) {
      return usesTypeVariable_value;
    }
    ASTNode$State state = state();
    if (!usesTypeVariable_initialized) {
      usesTypeVariable_initialized = true;
      usesTypeVariable_value = false;
    }
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      do {
        usesTypeVariable_cycle = state.nextCycle();
        boolean new_usesTypeVariable_value = isNestedType() && enclosingType().usesTypeVariable();
        if (new_usesTypeVariable_value != usesTypeVariable_value) {
          state.setChangeInCycle();
        }
        usesTypeVariable_value = new_usesTypeVariable_value;
      } while (state.testAndClearChangeInCycle());
      usesTypeVariable_computed = true;

      state.leaveCircle();
    } else if (usesTypeVariable_cycle != state.cycle()) {
      usesTypeVariable_cycle = state.cycle();
      boolean new_usesTypeVariable_value = isNestedType() && enclosingType().usesTypeVariable();
      if (new_usesTypeVariable_value != usesTypeVariable_value) {
        state.setChangeInCycle();
      }
      usesTypeVariable_value = new_usesTypeVariable_value;
    } else {
    }
    return usesTypeVariable_value;
  }
  /**
   * @attribute syn
   * @aspect LookupParTypeDecl
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:1530
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="LookupParTypeDecl", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:1530")
  public TypeDecl original() {
    TypeDecl original_value = this;
    return original_value;
  }
  /**
   * @attribute syn
   * @aspect LookupParTypeDecl
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:1607
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="LookupParTypeDecl", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:1607")
  public TypeDecl asWildcardExtends() {
    TypeDecl asWildcardExtends_value = lookupWildcardExtends(this);
    return asWildcardExtends_value;
  }
  /**
   * @attribute syn
   * @aspect LookupParTypeDecl
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:1621
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="LookupParTypeDecl", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:1621")
  public TypeDecl asWildcardSuper() {
    TypeDecl asWildcardSuper_value = lookupWildcardSuper(this);
    return asWildcardSuper_value;
  }
  /** @apilevel internal */
  private void sourceTypeDecl_reset() {
    sourceTypeDecl_computed = null;
    sourceTypeDecl_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle sourceTypeDecl_computed = null;

  /** @apilevel internal */
  protected TypeDecl sourceTypeDecl_value;

  /**
   * @attribute syn
   * @aspect SourceDeclarations
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:1722
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="SourceDeclarations", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:1722")
  public TypeDecl sourceTypeDecl() {
    ASTNode$State state = state();
    if (sourceTypeDecl_computed == ASTNode$State.NON_CYCLE || sourceTypeDecl_computed == state().cycle()) {
      return sourceTypeDecl_value;
    }
    sourceTypeDecl_value = this;
    if (state().inCircle()) {
      sourceTypeDecl_computed = state().cycle();
    
    } else {
      sourceTypeDecl_computed = ASTNode$State.NON_CYCLE;
    
    }
    return sourceTypeDecl_value;
  }
  /**
   * @attribute syn
   * @aspect GenericsParTypeDecl
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsParTypeDecl.jrag:104
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsParTypeDecl", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsParTypeDecl.jrag:104")
  public boolean isTypeVariable() {
    boolean isTypeVariable_value = false;
    return isTypeVariable_value;
  }
  /**
   * @attribute syn
   * @aspect GenericsSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:37
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:37")
  public boolean supertypeGenericClassDecl(GenericClassDecl type) {
    boolean supertypeGenericClassDecl_GenericClassDecl_value = supertypeClassDecl(type);
    return supertypeGenericClassDecl_GenericClassDecl_value;
  }
  /**
   * @attribute syn
   * @aspect GenericsSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:43
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:43")
  public boolean supertypeGenericInterfaceDecl(GenericInterfaceDecl type) {
    boolean supertypeGenericInterfaceDecl_GenericInterfaceDecl_value = this == type || supertypeInterfaceDecl(type);
    return supertypeGenericInterfaceDecl_GenericInterfaceDecl_value;
  }
  /**
   * @attribute syn
   * @aspect GenericsSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:49
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:49")
  public boolean supertypeRawClassDecl(RawClassDecl type) {
    boolean supertypeRawClassDecl_RawClassDecl_value = supertypeParClassDecl(type);
    return supertypeRawClassDecl_RawClassDecl_value;
  }
  /**
   * @attribute syn
   * @aspect GenericsSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:53
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:53")
  public boolean supertypeRawInterfaceDecl(RawInterfaceDecl type) {
    boolean supertypeRawInterfaceDecl_RawInterfaceDecl_value = supertypeParInterfaceDecl(type);
    return supertypeRawInterfaceDecl_RawInterfaceDecl_value;
  }
  /**
   * @attribute syn
   * @aspect GenericsSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:69
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:69")
  public boolean supertypeWildcard(WildcardType type) {
    boolean supertypeWildcard_WildcardType_value = this == typeObject();
    return supertypeWildcard_WildcardType_value;
  }
  /**
   * @attribute syn
   * @aspect GenericsSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:76
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:76")
  public boolean supertypeWildcardExtends(WildcardExtendsType type) {
    boolean supertypeWildcardExtends_WildcardExtendsType_value = type.extendsType().subtype(this);
    return supertypeWildcardExtends_WildcardExtendsType_value;
  }
  /**
   * @attribute syn
   * @aspect GenericsSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:85
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:85")
  public boolean supertypeWildcardSuper(WildcardSuperType type) {
    boolean supertypeWildcardSuper_WildcardSuperType_value = this == typeObject();
    return supertypeWildcardSuper_WildcardSuperType_value;
  }
  /** @return {@code true} if this type is a wildcard type, possibly with type bounds. 
   * @attribute syn
   * @aspect GenericsSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:120
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:120")
  public boolean isWildcard() {
    boolean isWildcard_value = false;
    return isWildcard_value;
  }
  /** @return {@code true} if this type is the unbounded wildcard type. 
   * @attribute syn
   * @aspect GenericsSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:125
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:125")
  public boolean isUnboundedWildcard() {
    boolean isUnboundedWildcard_value = false;
    return isUnboundedWildcard_value;
  }
  /**
   * @attribute syn
   * @aspect GenericsSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:146
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:146")
  public boolean supertypeParClassDecl(ParClassDecl type) {
    boolean supertypeParClassDecl_ParClassDecl_value = supertypeClassDecl(type);
    return supertypeParClassDecl_ParClassDecl_value;
  }
  /**
   * @attribute syn
   * @aspect GenericsSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:150
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:150")
  public boolean supertypeParInterfaceDecl(ParInterfaceDecl type) {
    boolean supertypeParInterfaceDecl_ParInterfaceDecl_value = supertypeInterfaceDecl(type);
    return supertypeParInterfaceDecl_ParInterfaceDecl_value;
  }
  /** @apilevel internal */
  private void containedIn_TypeDecl_reset() {
    containedIn_TypeDecl_values = null;
  }
  protected java.util.Map containedIn_TypeDecl_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="GenericsSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:162")
  public boolean containedIn(TypeDecl type) {
    Object _parameters = type;
    if (containedIn_TypeDecl_values == null) containedIn_TypeDecl_values = new java.util.HashMap(4);
    ASTNode$State.CircularValue _value;
    if (containedIn_TypeDecl_values.containsKey(_parameters)) {
      Object _cache = containedIn_TypeDecl_values.get(_parameters);
      if (!(_cache instanceof ASTNode$State.CircularValue)) {
        return (Boolean) _cache;
      } else {
        _value = (ASTNode$State.CircularValue) _cache;
      }
    } else {
      _value = new ASTNode$State.CircularValue();
      containedIn_TypeDecl_values.put(_parameters, _value);
      _value.value = true;
    }
    ASTNode$State state = state();
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      boolean new_containedIn_TypeDecl_value;
      do {
        _value.cycle = state.nextCycle();
        new_containedIn_TypeDecl_value = containedIn_compute(type);
        if (new_containedIn_TypeDecl_value != ((Boolean)_value.value)) {
          state.setChangeInCycle();
          _value.value = new_containedIn_TypeDecl_value;
        }
      } while (state.testAndClearChangeInCycle());
      containedIn_TypeDecl_values.put(_parameters, new_containedIn_TypeDecl_value);

      state.leaveCircle();
      return new_containedIn_TypeDecl_value;
    } else if (_value.cycle != state.cycle()) {
      _value.cycle = state.cycle();
      boolean new_containedIn_TypeDecl_value = containedIn_compute(type);
      if (new_containedIn_TypeDecl_value != ((Boolean)_value.value)) {
        state.setChangeInCycle();
        _value.value = new_containedIn_TypeDecl_value;
      }
      return new_containedIn_TypeDecl_value;
    } else {
      return (Boolean) _value.value;
    }
  }
  /** @apilevel internal */
  private boolean containedIn_compute(TypeDecl type) {
      if (type == this || type instanceof WildcardType) {
        return true;
      } else if (type instanceof WildcardExtendsType) {
        return this.subtype(((WildcardExtendsType) type).extendsType());
      } else if (type instanceof WildcardSuperType) {
        return ((WildcardSuperType) type).superType().subtype(this);
      } else if (type instanceof TypeVariable) {
        return subtype(type);
      }
      return sameStructure(type);
    }
  /** @apilevel internal */
  private void sameStructure_TypeDecl_reset() {
    sameStructure_TypeDecl_values = null;
  }
  protected java.util.Map sameStructure_TypeDecl_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="GenericsSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:216")
  public boolean sameStructure(TypeDecl t) {
    Object _parameters = t;
    if (sameStructure_TypeDecl_values == null) sameStructure_TypeDecl_values = new java.util.HashMap(4);
    ASTNode$State.CircularValue _value;
    if (sameStructure_TypeDecl_values.containsKey(_parameters)) {
      Object _cache = sameStructure_TypeDecl_values.get(_parameters);
      if (!(_cache instanceof ASTNode$State.CircularValue)) {
        return (Boolean) _cache;
      } else {
        _value = (ASTNode$State.CircularValue) _cache;
      }
    } else {
      _value = new ASTNode$State.CircularValue();
      sameStructure_TypeDecl_values.put(_parameters, _value);
      _value.value = true;
    }
    ASTNode$State state = state();
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      boolean new_sameStructure_TypeDecl_value;
      do {
        _value.cycle = state.nextCycle();
        new_sameStructure_TypeDecl_value = t == this;
        if (new_sameStructure_TypeDecl_value != ((Boolean)_value.value)) {
          state.setChangeInCycle();
          _value.value = new_sameStructure_TypeDecl_value;
        }
      } while (state.testAndClearChangeInCycle());
      sameStructure_TypeDecl_values.put(_parameters, new_sameStructure_TypeDecl_value);

      state.leaveCircle();
      return new_sameStructure_TypeDecl_value;
    } else if (_value.cycle != state.cycle()) {
      _value.cycle = state.cycle();
      boolean new_sameStructure_TypeDecl_value = t == this;
      if (new_sameStructure_TypeDecl_value != ((Boolean)_value.value)) {
        state.setChangeInCycle();
        _value.value = new_sameStructure_TypeDecl_value;
      }
      return new_sameStructure_TypeDecl_value;
    } else {
      return (Boolean) _value.value;
    }
  }
  /**
   * @attribute syn
   * @aspect GenericsSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:362
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:362")
  public boolean supertypeTypeVariable(TypeVariable type) {
    {
        if (type == this) {
          return true;
        }
        for (int i = 0; i < type.getNumTypeBound(); i++) {
          if (type.getTypeBound(i).type().subtype(this)) {
            return true;
          }
        }
        return false;
      }
  }
  /**
   * @attribute syn
   * @aspect GenericsSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:400
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:400")
  public boolean supertypeLUBType(LUBType type) {
    {
        for (int i = 0; i < type.getNumTypeBound(); i++) {
          if (!type.getTypeBound(i).type().subtype(this)) {
            return false;
          }
        }
        return true;
      }
  }
  /**
   * @attribute syn
   * @aspect GenericsSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:424
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:424")
  public boolean supertypeGLBType(GLBType type) {
    {
        // T1 && .. && Tn <: this, if exists  0 < i <= n Ti <: this
        for (int i = 0; i < type.getNumTypeBound(); i++) {
          if (type.getTypeBound(i).type().subtype(this)) {
            return true;
          }
        }
        return false;
      }
  }
  /** @apilevel internal */
  private void subtype_TypeDecl_reset() {
    subtype_TypeDecl_values = null;
  }
  protected java.util.Map subtype_TypeDecl_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="GenericsSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:490")
  public boolean subtype(TypeDecl type) {
    Object _parameters = type;
    if (subtype_TypeDecl_values == null) subtype_TypeDecl_values = new java.util.HashMap(4);
    ASTNode$State.CircularValue _value;
    if (subtype_TypeDecl_values.containsKey(_parameters)) {
      Object _cache = subtype_TypeDecl_values.get(_parameters);
      if (!(_cache instanceof ASTNode$State.CircularValue)) {
        return (Boolean) _cache;
      } else {
        _value = (ASTNode$State.CircularValue) _cache;
      }
    } else {
      _value = new ASTNode$State.CircularValue();
      subtype_TypeDecl_values.put(_parameters, _value);
      _value.value = true;
    }
    ASTNode$State state = state();
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      boolean new_subtype_TypeDecl_value;
      do {
        _value.cycle = state.nextCycle();
        new_subtype_TypeDecl_value = type == this;
        if (new_subtype_TypeDecl_value != ((Boolean)_value.value)) {
          state.setChangeInCycle();
          _value.value = new_subtype_TypeDecl_value;
        }
      } while (state.testAndClearChangeInCycle());
      subtype_TypeDecl_values.put(_parameters, new_subtype_TypeDecl_value);

      state.leaveCircle();
      return new_subtype_TypeDecl_value;
    } else if (_value.cycle != state.cycle()) {
      _value.cycle = state.cycle();
      boolean new_subtype_TypeDecl_value = type == this;
      if (new_subtype_TypeDecl_value != ((Boolean)_value.value)) {
        state.setChangeInCycle();
        _value.value = new_subtype_TypeDecl_value;
      }
      return new_subtype_TypeDecl_value;
    } else {
      return (Boolean) _value.value;
    }
  }
  /**
   * @attribute syn
   * @aspect GenericsSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:505
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:505")
  public boolean supertypeClassDecl(ClassDecl type) {
    boolean supertypeClassDecl_ClassDecl_value = type == this;
    return supertypeClassDecl_ClassDecl_value;
  }
  /**
   * @attribute syn
   * @aspect GenericsSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:522
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:522")
  public boolean supertypeInterfaceDecl(InterfaceDecl type) {
    boolean supertypeInterfaceDecl_InterfaceDecl_value = type == this;
    return supertypeInterfaceDecl_InterfaceDecl_value;
  }
  /**
   * @attribute syn
   * @aspect GenericsSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:539
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:539")
  public boolean supertypeArrayDecl(ArrayDecl type) {
    boolean supertypeArrayDecl_ArrayDecl_value = this == type;
    return supertypeArrayDecl_ArrayDecl_value;
  }
  /**
   * @attribute syn
   * @aspect GenericsSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:568
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:568")
  public boolean supertypePrimitiveType(PrimitiveType type) {
    boolean supertypePrimitiveType_PrimitiveType_value = type == this;
    return supertypePrimitiveType_PrimitiveType_value;
  }
  /**
   * @attribute syn
   * @aspect GenericsSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:577
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:577")
  public boolean supertypeNullType(NullType type) {
    boolean supertypeNullType_NullType_value = false;
    return supertypeNullType_NullType_value;
  }
  /**
   * @attribute syn
   * @aspect GenericsSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:582
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:582")
  public boolean supertypeVoidType(VoidType type) {
    boolean supertypeVoidType_VoidType_value = false;
    return supertypeVoidType_VoidType_value;
  }
  /**
   * @attribute syn
   * @aspect GenericsSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:592
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:592")
  public boolean supertypeClassDeclSubstituted(ClassDeclSubstituted type) {
    boolean supertypeClassDeclSubstituted_ClassDeclSubstituted_value = type.original() == this || supertypeClassDecl(type);
    return supertypeClassDeclSubstituted_ClassDeclSubstituted_value;
  }
  /**
   * @attribute syn
   * @aspect GenericsSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:605
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:605")
  public boolean supertypeInterfaceDeclSubstituted(InterfaceDeclSubstituted type) {
    boolean supertypeInterfaceDeclSubstituted_InterfaceDeclSubstituted_value = type.original() == this || supertypeInterfaceDecl(type);
    return supertypeInterfaceDeclSubstituted_InterfaceDeclSubstituted_value;
  }
  /**
   * @attribute syn
   * @aspect GenericsSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:618
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:618")
  public boolean supertypeGenericClassDeclSubstituted(GenericClassDeclSubstituted type) {
    boolean supertypeGenericClassDeclSubstituted_GenericClassDeclSubstituted_value = type.original() == this || supertypeGenericClassDecl(type);
    return supertypeGenericClassDeclSubstituted_GenericClassDeclSubstituted_value;
  }
  /**
   * @attribute syn
   * @aspect GenericsSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:632
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsSubtype.jrag:632")
  public boolean supertypeGenericInterfaceDeclSubstituted(GenericInterfaceDeclSubstituted type) {
    boolean supertypeGenericInterfaceDeclSubstituted_GenericInterfaceDeclSubstituted_value = type.original() == this || supertypeGenericInterfaceDecl(type);
    return supertypeGenericInterfaceDeclSubstituted_GenericInterfaceDeclSubstituted_value;
  }
  /**
   * A type is reifiable if it either refers to a non-parameterized type,
   * is a raw type, is a parameterized type with only unbound wildcard
   * parameters or is an array type with a reifiable type parameter.
   * 
   * @see "JLS SE7 &sect;4.7"
   * @attribute syn
   * @aspect ReifiableTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\ReifiableTypes.jrag:39
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ReifiableTypes", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\ReifiableTypes.jrag:39")
  public boolean isReifiable() {
    boolean isReifiable_value = true;
    return isReifiable_value;
  }
  /**
   * @return true if the modifier list includes the SafeVarargs annotation
   * @attribute syn
   * @aspect SafeVarargs
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\SafeVarargs.jrag:36
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="SafeVarargs", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\SafeVarargs.jrag:36")
  public boolean hasAnnotationSafeVarargs() {
    boolean hasAnnotationSafeVarargs_value = getModifiers().hasAnnotationSafeVarargs();
    return hasAnnotationSafeVarargs_value;
  }
  /**
   * An unchecked conversion occurs when converting from a
   * raw type G to a generic type G<T1, ..., Tn>.
   * @attribute syn
   * @aspect UncheckedConversion
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\UncheckedConversion.jrag:75
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="UncheckedConversion", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\UncheckedConversion.jrag:75")
  public boolean isUncheckedConversionTo(TypeDecl dest) {
    boolean isUncheckedConversionTo_TypeDecl_value = (!dest.isRawType()) && this.isRawType();
    return isUncheckedConversionTo_TypeDecl_value;
  }
  /**
   * @attribute syn
   * @aspect PreciseRethrow
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\EffectivelyFinal.jrag:41
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="PreciseRethrow", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\EffectivelyFinal.jrag:41")
  public boolean modifiedInScope(Variable var) {
    {
        for (int i = 0; i < getNumBodyDecl(); i++) {
          BodyDecl body = getBodyDecl(i);
          if (body.modifiedInScope(var)) {
            return true;
          }
        }
        return false;
      }
  }
  /** @apilevel internal */
  private void isFunctionalInterface_reset() {
    isFunctionalInterface_computed = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle isFunctionalInterface_computed = null;

  /** @apilevel internal */
  protected boolean isFunctionalInterface_value;

  /**
   * @attribute syn
   * @aspect FunctionalInterface
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\FunctionalInterface.jrag:30
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="FunctionalInterface", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\FunctionalInterface.jrag:30")
  public boolean isFunctionalInterface() {
    ASTNode$State state = state();
    if (isFunctionalInterface_computed == ASTNode$State.NON_CYCLE || isFunctionalInterface_computed == state().cycle()) {
      return isFunctionalInterface_value;
    }
    isFunctionalInterface_value = false;
    if (state().inCircle()) {
      isFunctionalInterface_computed = state().cycle();
    
    } else {
      isFunctionalInterface_computed = ASTNode$State.NON_CYCLE;
    
    }
    return isFunctionalInterface_value;
  }
  /**
   * @attribute syn
   * @aspect StrictSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\GenericsSubtype.jrag:39
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StrictSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\GenericsSubtype.jrag:39")
  public boolean strictSupertypeGenericClassDecl(GenericClassDecl type) {
    boolean strictSupertypeGenericClassDecl_GenericClassDecl_value = strictSupertypeClassDecl(type);
    return strictSupertypeGenericClassDecl_GenericClassDecl_value;
  }
  /**
   * @attribute syn
   * @aspect StrictSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\GenericsSubtype.jrag:46
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StrictSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\GenericsSubtype.jrag:46")
  public boolean strictSupertypeGenericInterfaceDecl(GenericInterfaceDecl type) {
    boolean strictSupertypeGenericInterfaceDecl_GenericInterfaceDecl_value = this == type || strictSupertypeInterfaceDecl(type);
    return strictSupertypeGenericInterfaceDecl_GenericInterfaceDecl_value;
  }
  /**
   * @attribute syn
   * @aspect StrictSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\GenericsSubtype.jrag:52
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StrictSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\GenericsSubtype.jrag:52")
  public boolean strictSupertypeRawClassDecl(RawClassDecl type) {
    boolean strictSupertypeRawClassDecl_RawClassDecl_value = strictSupertypeParClassDecl(type);
    return strictSupertypeRawClassDecl_RawClassDecl_value;
  }
  /**
   * @attribute syn
   * @aspect StrictSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\GenericsSubtype.jrag:56
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StrictSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\GenericsSubtype.jrag:56")
  public boolean strictSupertypeRawInterfaceDecl(RawInterfaceDecl type) {
    boolean strictSupertypeRawInterfaceDecl_RawInterfaceDecl_value = strictSupertypeParInterfaceDecl(type);
    return strictSupertypeRawInterfaceDecl_RawInterfaceDecl_value;
  }
  /**
   * @attribute syn
   * @aspect StrictSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\GenericsSubtype.jrag:68
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StrictSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\GenericsSubtype.jrag:68")
  public boolean strictSupertypeWildcard(WildcardType type) {
    boolean strictSupertypeWildcard_WildcardType_value = false;
    return strictSupertypeWildcard_WildcardType_value;
  }
  /**
   * @attribute syn
   * @aspect StrictSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\GenericsSubtype.jrag:79
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StrictSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\GenericsSubtype.jrag:79")
  public boolean strictSupertypeWildcardSuper(WildcardSuperType type) {
    boolean strictSupertypeWildcardSuper_WildcardSuperType_value = false;
    return strictSupertypeWildcardSuper_WildcardSuperType_value;
  }
  /**
   * @attribute syn
   * @aspect StrictSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\GenericsSubtype.jrag:149
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StrictSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\GenericsSubtype.jrag:149")
  public boolean strictSupertypeParClassDecl(ParClassDecl type) {
    boolean strictSupertypeParClassDecl_ParClassDecl_value = strictSupertypeClassDecl(type);
    return strictSupertypeParClassDecl_ParClassDecl_value;
  }
  /**
   * @attribute syn
   * @aspect StrictSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\GenericsSubtype.jrag:153
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StrictSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\GenericsSubtype.jrag:153")
  public boolean strictSupertypeParInterfaceDecl(ParInterfaceDecl type) {
    boolean strictSupertypeParInterfaceDecl_ParInterfaceDecl_value = strictSupertypeInterfaceDecl(type);
    return strictSupertypeParInterfaceDecl_ParInterfaceDecl_value;
  }
  /** @apilevel internal */
  private void strictContainedIn_TypeDecl_reset() {
    strictContainedIn_TypeDecl_values = null;
  }
  protected java.util.Map strictContainedIn_TypeDecl_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="StrictSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\GenericsSubtype.jrag:165")
  public boolean strictContainedIn(TypeDecl type) {
    Object _parameters = type;
    if (strictContainedIn_TypeDecl_values == null) strictContainedIn_TypeDecl_values = new java.util.HashMap(4);
    ASTNode$State.CircularValue _value;
    if (strictContainedIn_TypeDecl_values.containsKey(_parameters)) {
      Object _cache = strictContainedIn_TypeDecl_values.get(_parameters);
      if (!(_cache instanceof ASTNode$State.CircularValue)) {
        return (Boolean) _cache;
      } else {
        _value = (ASTNode$State.CircularValue) _cache;
      }
    } else {
      _value = new ASTNode$State.CircularValue();
      strictContainedIn_TypeDecl_values.put(_parameters, _value);
      _value.value = true;
    }
    ASTNode$State state = state();
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      boolean new_strictContainedIn_TypeDecl_value;
      do {
        _value.cycle = state.nextCycle();
        new_strictContainedIn_TypeDecl_value = strictContainedIn_compute(type);
        if (new_strictContainedIn_TypeDecl_value != ((Boolean)_value.value)) {
          state.setChangeInCycle();
          _value.value = new_strictContainedIn_TypeDecl_value;
        }
      } while (state.testAndClearChangeInCycle());
      strictContainedIn_TypeDecl_values.put(_parameters, new_strictContainedIn_TypeDecl_value);

      state.leaveCircle();
      return new_strictContainedIn_TypeDecl_value;
    } else if (_value.cycle != state.cycle()) {
      _value.cycle = state.cycle();
      boolean new_strictContainedIn_TypeDecl_value = strictContainedIn_compute(type);
      if (new_strictContainedIn_TypeDecl_value != ((Boolean)_value.value)) {
        state.setChangeInCycle();
        _value.value = new_strictContainedIn_TypeDecl_value;
      }
      return new_strictContainedIn_TypeDecl_value;
    } else {
      return (Boolean) _value.value;
    }
  }
  /** @apilevel internal */
  private boolean strictContainedIn_compute(TypeDecl type) {
      if (type == this || type instanceof WildcardType) {
        return true;
      } else if (type instanceof WildcardExtendsType) {
        return this.strictSubtype(((WildcardExtendsType) type).extendsType());
      } else if (type instanceof WildcardSuperType) {
        return ((WildcardSuperType) type).superType().strictSubtype(this);
      } else if (type instanceof TypeVariable) {
        return strictSubtype(type);
      }
      return sameStructure(type);
    }
  /**
   * @attribute syn
   * @aspect StrictSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\GenericsSubtype.jrag:281
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StrictSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\GenericsSubtype.jrag:281")
  public boolean strictSupertypeTypeVariable(TypeVariable type) {
    {
        if (type == this) {
          return true;
        }
        for (int i = 0; i < type.getNumTypeBound(); i++) {
          if (type.getTypeBound(i).type().strictSubtype(this)) {
            return true;
          }
        }
        return false;
      }
  }
  /**
   * @attribute syn
   * @aspect StrictSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\GenericsSubtype.jrag:321
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StrictSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\GenericsSubtype.jrag:321")
  public boolean strictSupertypeLUBType(LUBType type) {
    {
        for (int i = 0; i < type.getNumTypeBound(); i++) {
          if (!type.getTypeBound(i).type().strictSubtype(this)) {
            return false;
          }
        }
        return true;
      }
  }
  /**
   * @attribute syn
   * @aspect StrictSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\GenericsSubtype.jrag:342
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StrictSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\GenericsSubtype.jrag:342")
  public boolean strictSupertypeGLBType(GLBType type) {
    {
        // T1 && .. && Tn <: this, if exists  0 < i <= n Ti <: this
        for (int i = 0; i < type.getNumTypeBound(); i++) {
          if (type.getTypeBound(i).type().strictSubtype(this)) {
            return true;
          }
        }
        return false;
      }
  }
  /** @apilevel internal */
  private void strictSubtype_TypeDecl_reset() {
    strictSubtype_TypeDecl_values = null;
  }
  protected java.util.Map strictSubtype_TypeDecl_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="StrictSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\GenericsSubtype.jrag:363")
  public boolean strictSubtype(TypeDecl type) {
    Object _parameters = type;
    if (strictSubtype_TypeDecl_values == null) strictSubtype_TypeDecl_values = new java.util.HashMap(4);
    ASTNode$State.CircularValue _value;
    if (strictSubtype_TypeDecl_values.containsKey(_parameters)) {
      Object _cache = strictSubtype_TypeDecl_values.get(_parameters);
      if (!(_cache instanceof ASTNode$State.CircularValue)) {
        return (Boolean) _cache;
      } else {
        _value = (ASTNode$State.CircularValue) _cache;
      }
    } else {
      _value = new ASTNode$State.CircularValue();
      strictSubtype_TypeDecl_values.put(_parameters, _value);
      _value.value = true;
    }
    ASTNode$State state = state();
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      boolean new_strictSubtype_TypeDecl_value;
      do {
        _value.cycle = state.nextCycle();
        new_strictSubtype_TypeDecl_value = type == this;
        if (new_strictSubtype_TypeDecl_value != ((Boolean)_value.value)) {
          state.setChangeInCycle();
          _value.value = new_strictSubtype_TypeDecl_value;
        }
      } while (state.testAndClearChangeInCycle());
      strictSubtype_TypeDecl_values.put(_parameters, new_strictSubtype_TypeDecl_value);

      state.leaveCircle();
      return new_strictSubtype_TypeDecl_value;
    } else if (_value.cycle != state.cycle()) {
      _value.cycle = state.cycle();
      boolean new_strictSubtype_TypeDecl_value = type == this;
      if (new_strictSubtype_TypeDecl_value != ((Boolean)_value.value)) {
        state.setChangeInCycle();
        _value.value = new_strictSubtype_TypeDecl_value;
      }
      return new_strictSubtype_TypeDecl_value;
    } else {
      return (Boolean) _value.value;
    }
  }
  /**
   * @attribute syn
   * @aspect StrictSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\GenericsSubtype.jrag:378
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StrictSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\GenericsSubtype.jrag:378")
  public boolean strictSupertypeClassDecl(ClassDecl type) {
    boolean strictSupertypeClassDecl_ClassDecl_value = type == this;
    return strictSupertypeClassDecl_ClassDecl_value;
  }
  /**
   * @attribute syn
   * @aspect StrictSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\GenericsSubtype.jrag:398
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StrictSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\GenericsSubtype.jrag:398")
  public boolean strictSupertypeInterfaceDecl(InterfaceDecl type) {
    boolean strictSupertypeInterfaceDecl_InterfaceDecl_value = type == this;
    return strictSupertypeInterfaceDecl_InterfaceDecl_value;
  }
  /**
   * @attribute syn
   * @aspect StrictSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\GenericsSubtype.jrag:415
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StrictSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\GenericsSubtype.jrag:415")
  public boolean strictSupertypeArrayDecl(ArrayDecl type) {
    boolean strictSupertypeArrayDecl_ArrayDecl_value = this == type;
    return strictSupertypeArrayDecl_ArrayDecl_value;
  }
  /**
   * @attribute syn
   * @aspect StrictSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\GenericsSubtype.jrag:445
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StrictSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\GenericsSubtype.jrag:445")
  public boolean strictSupertypePrimitiveType(PrimitiveType type) {
    boolean strictSupertypePrimitiveType_PrimitiveType_value = type == this;
    return strictSupertypePrimitiveType_PrimitiveType_value;
  }
  /**
   * @attribute syn
   * @aspect StrictSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\GenericsSubtype.jrag:454
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StrictSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\GenericsSubtype.jrag:454")
  public boolean strictSupertypeNullType(NullType type) {
    boolean strictSupertypeNullType_NullType_value = false;
    return strictSupertypeNullType_NullType_value;
  }
  /**
   * @attribute syn
   * @aspect StrictSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\GenericsSubtype.jrag:458
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StrictSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\GenericsSubtype.jrag:458")
  public boolean strictSupertypeVoidType(VoidType type) {
    boolean strictSupertypeVoidType_VoidType_value = false;
    return strictSupertypeVoidType_VoidType_value;
  }
  /**
   * @attribute syn
   * @aspect StrictSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\GenericsSubtype.jrag:463
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StrictSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\GenericsSubtype.jrag:463")
  public boolean strictSupertypeClassDeclSubstituted(ClassDeclSubstituted type) {
    boolean strictSupertypeClassDeclSubstituted_ClassDeclSubstituted_value = type.original() == this || strictSupertypeClassDecl(type);
    return strictSupertypeClassDeclSubstituted_ClassDeclSubstituted_value;
  }
  /**
   * @attribute syn
   * @aspect StrictSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\GenericsSubtype.jrag:474
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StrictSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\GenericsSubtype.jrag:474")
  public boolean strictSupertypeInterfaceDeclSubstituted(InterfaceDeclSubstituted type) {
    boolean strictSupertypeInterfaceDeclSubstituted_InterfaceDeclSubstituted_value = type.original() == this || strictSupertypeInterfaceDecl(type);
    return strictSupertypeInterfaceDeclSubstituted_InterfaceDeclSubstituted_value;
  }
  /**
   * @attribute syn
   * @aspect StrictSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\GenericsSubtype.jrag:486
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StrictSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\GenericsSubtype.jrag:486")
  public boolean strictSupertypeGenericClassDeclSubstituted(GenericClassDeclSubstituted type) {
    boolean strictSupertypeGenericClassDeclSubstituted_GenericClassDeclSubstituted_value = type.original() == this || strictSupertypeGenericClassDecl(type);
    return strictSupertypeGenericClassDeclSubstituted_GenericClassDeclSubstituted_value;
  }
  /**
   * @attribute syn
   * @aspect StrictSubtype
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\GenericsSubtype.jrag:500
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StrictSubtype", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\GenericsSubtype.jrag:500")
  public boolean strictSupertypeGenericInterfaceDeclSubstituted(GenericInterfaceDeclSubstituted type) {
    boolean strictSupertypeGenericInterfaceDeclSubstituted_GenericInterfaceDeclSubstituted_value = type.original() == this || strictSupertypeGenericInterfaceDecl(type);
    return strictSupertypeGenericInterfaceDeclSubstituted_GenericInterfaceDeclSubstituted_value;
  }
  /**
   * @attribute inh
   * @aspect Arrays
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Arrays.jrag:56
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="Arrays", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Arrays.jrag:56")
  public TypeDecl componentType() {
    ASTNode$State state = state();
    if (componentType_computed == ASTNode$State.NON_CYCLE || componentType_computed == state().cycle()) {
      return componentType_value;
    }
    componentType_value = getParent().Define_componentType(this, null);
    if (state().inCircle()) {
      componentType_computed = state().cycle();
    
    } else {
      componentType_computed = ASTNode$State.NON_CYCLE;
    
    }
    return componentType_value;
  }
  /** @apilevel internal */
  private void componentType_reset() {
    componentType_computed = null;
    componentType_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle componentType_computed = null;

  /** @apilevel internal */
  protected TypeDecl componentType_value;

  /**
   * @attribute inh
   * @aspect Arrays
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Arrays.jrag:87
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="Arrays", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Arrays.jrag:87")
  public TypeDecl typeCloneable() {
    TypeDecl typeCloneable_value = getParent().Define_typeCloneable(this, null);
    return typeCloneable_value;
  }
  /**
   * @attribute inh
   * @aspect Arrays
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Arrays.jrag:89
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="Arrays", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Arrays.jrag:89")
  public TypeDecl typeSerializable() {
    TypeDecl typeSerializable_value = getParent().Define_typeSerializable(this, null);
    return typeSerializable_value;
  }
  /**
   * @attribute inh
   * @aspect DefiniteAssignment
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:270
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="DefiniteAssignment", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:270")
  public boolean assignedBefore(Variable v) {
    boolean assignedBefore_Variable_value = getParent().Define_assignedBefore(this, null, v);
    return assignedBefore_Variable_value;
  }
  /**
   * @attribute inh
   * @aspect DefiniteUnassignment
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:913
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="DefiniteUnassignment", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:913")
  public boolean unassignedBefore(Variable v) {
    boolean unassignedBefore_Variable_value = getParent().Define_unassignedBefore(this, null, v);
    return unassignedBefore_Variable_value;
  }
  /**
   * @attribute inh
   * @aspect ExceptionHandling
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ExceptionHandling.jrag:47
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="ExceptionHandling", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ExceptionHandling.jrag:47")
  public TypeDecl typeException() {
    ASTNode$State state = state();
    if (typeException_computed == ASTNode$State.NON_CYCLE || typeException_computed == state().cycle()) {
      return typeException_value;
    }
    typeException_value = getParent().Define_typeException(this, null);
    if (state().inCircle()) {
      typeException_computed = state().cycle();
    
    } else {
      typeException_computed = ASTNode$State.NON_CYCLE;
    
    }
    return typeException_value;
  }
  /** @apilevel internal */
  private void typeException_reset() {
    typeException_computed = null;
    typeException_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle typeException_computed = null;

  /** @apilevel internal */
  protected TypeDecl typeException_value;

  /**
   * @attribute inh
   * @aspect ExceptionHandling
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ExceptionHandling.jrag:50
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="ExceptionHandling", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ExceptionHandling.jrag:50")
  public TypeDecl typeRuntimeException() {
    ASTNode$State state = state();
    if (typeRuntimeException_computed == ASTNode$State.NON_CYCLE || typeRuntimeException_computed == state().cycle()) {
      return typeRuntimeException_value;
    }
    typeRuntimeException_value = getParent().Define_typeRuntimeException(this, null);
    if (state().inCircle()) {
      typeRuntimeException_computed = state().cycle();
    
    } else {
      typeRuntimeException_computed = ASTNode$State.NON_CYCLE;
    
    }
    return typeRuntimeException_value;
  }
  /** @apilevel internal */
  private void typeRuntimeException_reset() {
    typeRuntimeException_computed = null;
    typeRuntimeException_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle typeRuntimeException_computed = null;

  /** @apilevel internal */
  protected TypeDecl typeRuntimeException_value;

  /**
   * @attribute inh
   * @aspect ExceptionHandling
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ExceptionHandling.jrag:53
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="ExceptionHandling", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ExceptionHandling.jrag:53")
  public TypeDecl typeError() {
    ASTNode$State state = state();
    if (typeError_computed == ASTNode$State.NON_CYCLE || typeError_computed == state().cycle()) {
      return typeError_value;
    }
    typeError_value = getParent().Define_typeError(this, null);
    if (state().inCircle()) {
      typeError_computed = state().cycle();
    
    } else {
      typeError_computed = ASTNode$State.NON_CYCLE;
    
    }
    return typeError_value;
  }
  /** @apilevel internal */
  private void typeError_reset() {
    typeError_computed = null;
    typeError_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle typeError_computed = null;

  /** @apilevel internal */
  protected TypeDecl typeError_value;

  /**
   * @attribute inh
   * @aspect LookupMethod
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupMethod.jrag:56
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="LookupMethod", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupMethod.jrag:56")
  public Collection<MethodDecl> lookupMethod(String name) {
    Object _parameters = name;
    if (lookupMethod_String_computed == null) lookupMethod_String_computed = new java.util.HashMap(4);
    if (lookupMethod_String_values == null) lookupMethod_String_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (lookupMethod_String_values.containsKey(_parameters) && lookupMethod_String_computed != null
        && lookupMethod_String_computed.containsKey(_parameters)
        && (lookupMethod_String_computed.get(_parameters) == ASTNode$State.NON_CYCLE || lookupMethod_String_computed.get(_parameters) == state().cycle())) {
      return (Collection<MethodDecl>) lookupMethod_String_values.get(_parameters);
    }
    Collection<MethodDecl> lookupMethod_String_value = getParent().Define_lookupMethod(this, null, name);
    if (state().inCircle()) {
      lookupMethod_String_values.put(_parameters, lookupMethod_String_value);
      lookupMethod_String_computed.put(_parameters, state().cycle());
    
    } else {
      lookupMethod_String_values.put(_parameters, lookupMethod_String_value);
      lookupMethod_String_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return lookupMethod_String_value;
  }
  /** @apilevel internal */
  private void lookupMethod_String_reset() {
    lookupMethod_String_computed = new java.util.HashMap(4);
    lookupMethod_String_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map lookupMethod_String_values;
  /** @apilevel internal */
  protected java.util.Map lookupMethod_String_computed;
  /**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:87
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:87")
  public TypeDecl typeInt() {
    TypeDecl typeInt_value = getParent().Define_typeInt(this, null);
    return typeInt_value;
  }
  /**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:90
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:90")
  public TypeDecl typeObject() {
    ASTNode$State state = state();
    if (typeObject_computed == ASTNode$State.NON_CYCLE || typeObject_computed == state().cycle()) {
      return typeObject_value;
    }
    typeObject_value = getParent().Define_typeObject(this, null);
    if (state().inCircle()) {
      typeObject_computed = state().cycle();
    
    } else {
      typeObject_computed = ASTNode$State.NON_CYCLE;
    
    }
    return typeObject_value;
  }
  /** @apilevel internal */
  private void typeObject_reset() {
    typeObject_computed = null;
    typeObject_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle typeObject_computed = null;

  /** @apilevel internal */
  protected TypeDecl typeObject_value;

  /**
   * @attribute inh
   * @aspect LookupFullyQualifiedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:130
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="LookupFullyQualifiedTypes", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:130")
  public TypeDecl lookupType(String packageName, String typeName) {
    TypeDecl lookupType_String_String_value = getParent().Define_lookupType(this, null, packageName, typeName);
    return lookupType_String_String_value;
  }
  /**
   * @attribute inh
   * @aspect TypeScopePropagation
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:351
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="TypeScopePropagation", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:351")
  public SimpleSet<TypeDecl> lookupType(String name) {
    Object _parameters = name;
    if (lookupType_String_computed == null) lookupType_String_computed = new java.util.HashMap(4);
    if (lookupType_String_values == null) lookupType_String_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (lookupType_String_values.containsKey(_parameters) && lookupType_String_computed != null
        && lookupType_String_computed.containsKey(_parameters)
        && (lookupType_String_computed.get(_parameters) == ASTNode$State.NON_CYCLE || lookupType_String_computed.get(_parameters) == state().cycle())) {
      return (SimpleSet<TypeDecl>) lookupType_String_values.get(_parameters);
    }
    SimpleSet<TypeDecl> lookupType_String_value = getParent().Define_lookupType(this, null, name);
    if (state().inCircle()) {
      lookupType_String_values.put(_parameters, lookupType_String_value);
      lookupType_String_computed.put(_parameters, state().cycle());
    
    } else {
      lookupType_String_values.put(_parameters, lookupType_String_value);
      lookupType_String_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return lookupType_String_value;
  }
  /** @apilevel internal */
  private void lookupType_String_reset() {
    lookupType_String_computed = new java.util.HashMap(4);
    lookupType_String_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map lookupType_String_values;
  /** @apilevel internal */
  protected java.util.Map lookupType_String_computed;
  /**
   * Finds the variables in the current scope with the given name.
   * @attribute inh
   * @aspect VariableScope
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupVariable.jrag:38
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="VariableScope", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupVariable.jrag:38")
  public SimpleSet<Variable> lookupVariable(String name) {
    Object _parameters = name;
    if (lookupVariable_String_computed == null) lookupVariable_String_computed = new java.util.HashMap(4);
    if (lookupVariable_String_values == null) lookupVariable_String_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (lookupVariable_String_values.containsKey(_parameters) && lookupVariable_String_computed != null
        && lookupVariable_String_computed.containsKey(_parameters)
        && (lookupVariable_String_computed.get(_parameters) == ASTNode$State.NON_CYCLE || lookupVariable_String_computed.get(_parameters) == state().cycle())) {
      return (SimpleSet<Variable>) lookupVariable_String_values.get(_parameters);
    }
    SimpleSet<Variable> lookupVariable_String_value = getParent().Define_lookupVariable(this, null, name);
    if (state().inCircle()) {
      lookupVariable_String_values.put(_parameters, lookupVariable_String_value);
      lookupVariable_String_computed.put(_parameters, state().cycle());
    
    } else {
      lookupVariable_String_values.put(_parameters, lookupVariable_String_value);
      lookupVariable_String_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return lookupVariable_String_value;
  }
  /** @apilevel internal */
  private void lookupVariable_String_reset() {
    lookupVariable_String_computed = new java.util.HashMap(4);
    lookupVariable_String_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map lookupVariable_String_values;
  /** @apilevel internal */
  protected java.util.Map lookupVariable_String_computed;
  /**
   * @attribute inh
   * @aspect NameCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\NameCheck.jrag:352
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="NameCheck", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\NameCheck.jrag:352")
  public boolean hasPackage(String packageName) {
    boolean hasPackage_String_value = getParent().Define_hasPackage(this, null, packageName);
    return hasPackage_String_value;
  }
  /**
   * @attribute inh
   * @aspect NameCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\NameCheck.jrag:356
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="NameCheck", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\NameCheck.jrag:356")
  public ASTNode enclosingBlock() {
    ASTNode enclosingBlock_value = getParent().Define_enclosingBlock(this, null);
    return enclosingBlock_value;
  }
  /**
   * @attribute inh
   * @aspect TypeName
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\QualifiedNames.jrag:108
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="TypeName", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\QualifiedNames.jrag:108")
  public String packageName() {
    ASTNode$State state = state();
    if (packageName_computed == ASTNode$State.NON_CYCLE || packageName_computed == state().cycle()) {
      return packageName_value;
    }
    packageName_value = getParent().Define_packageName(this, null);
    if (state().inCircle()) {
      packageName_computed = state().cycle();
    
    } else {
      packageName_computed = ASTNode$State.NON_CYCLE;
    
    }
    return packageName_value;
  }
  /** @apilevel internal */
  private void packageName_reset() {
    packageName_computed = null;
    packageName_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle packageName_computed = null;

  /** @apilevel internal */
  protected String packageName_value;

  /**
   * @attribute inh
   * @aspect TypeAnalysis
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:232
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="TypeAnalysis", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:232")
  public boolean isAnonymous() {
    ASTNode$State state = state();
    if (isAnonymous_computed == ASTNode$State.NON_CYCLE || isAnonymous_computed == state().cycle()) {
      return isAnonymous_value;
    }
    isAnonymous_value = getParent().Define_isAnonymous(this, null);
    if (state().inCircle()) {
      isAnonymous_computed = state().cycle();
    
    } else {
      isAnonymous_computed = ASTNode$State.NON_CYCLE;
    
    }
    return isAnonymous_value;
  }
  /** @apilevel internal */
  private void isAnonymous_reset() {
    isAnonymous_computed = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle isAnonymous_computed = null;

  /** @apilevel internal */
  protected boolean isAnonymous_value;

  /**
   * @attribute inh
   * @aspect NestedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:555
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="NestedTypes", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:555")
  public TypeDecl enclosingType() {
    TypeDecl enclosingType_value = getParent().Define_enclosingType(this, null);
    return enclosingType_value;
  }
  /**
   * @attribute inh
   * @aspect NestedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:573
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="NestedTypes", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:573")
  public BodyDecl enclosingBodyDecl() {
    BodyDecl enclosingBodyDecl_value = getParent().Define_enclosingBodyDecl(this, null);
    return enclosingBodyDecl_value;
  }
  /**
   * @attribute inh
   * @aspect NestedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:580
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="NestedTypes", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:580")
  public boolean isNestedType() {
    boolean isNestedType_value = getParent().Define_isNestedType(this, null);
    return isNestedType_value;
  }
  /**
   * @attribute inh
   * @aspect NestedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:588
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="NestedTypes", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:588")
  public boolean isMemberType() {
    boolean isMemberType_value = getParent().Define_isMemberType(this, null);
    return isMemberType_value;
  }
  /**
   * @attribute inh
   * @aspect NestedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:606
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="NestedTypes", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:606")
  public boolean isLocalClass() {
    boolean isLocalClass_value = getParent().Define_isLocalClass(this, null);
    return isLocalClass_value;
  }
  /**
   * @attribute inh
   * @aspect NestedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:638
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="NestedTypes", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:638")
  public String hostPackage() {
    String hostPackage_value = getParent().Define_hostPackage(this, null);
    return hostPackage_value;
  }
  /**
   * @attribute inh
   * @aspect Circularity
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:724
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="Circularity", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:724")
  public TypeDecl unknownType() {
    ASTNode$State state = state();
    if (unknownType_computed == ASTNode$State.NON_CYCLE || unknownType_computed == state().cycle()) {
      return unknownType_value;
    }
    unknownType_value = getParent().Define_unknownType(this, null);
    if (state().inCircle()) {
      unknownType_computed = state().cycle();
    
    } else {
      unknownType_computed = ASTNode$State.NON_CYCLE;
    
    }
    return unknownType_value;
  }
  /** @apilevel internal */
  private void unknownType_reset() {
    unknownType_computed = null;
    unknownType_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle unknownType_computed = null;

  /** @apilevel internal */
  protected TypeDecl unknownType_value;

  /**
   * @attribute inh
   * @aspect TypeCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeCheck.jrag:533
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="TypeCheck", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeCheck.jrag:533")
  public TypeDecl typeVoid() {
    TypeDecl typeVoid_value = getParent().Define_typeVoid(this, null);
    return typeVoid_value;
  }
  /**
   * @attribute inh
   * @aspect TypeCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeCheck.jrag:667
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="TypeCheck", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeCheck.jrag:667")
  public TypeDecl enclosingInstance() {
    TypeDecl enclosingInstance_value = getParent().Define_enclosingInstance(this, null);
    return enclosingInstance_value;
  }
  /**
   * @attribute inh
   * @aspect TypeHierarchyCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:188
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="TypeHierarchyCheck", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:188")
  public boolean inExplicitConstructorInvocation() {
    ASTNode$State state = state();
    if (inExplicitConstructorInvocation_computed == ASTNode$State.NON_CYCLE || inExplicitConstructorInvocation_computed == state().cycle()) {
      return inExplicitConstructorInvocation_value;
    }
    inExplicitConstructorInvocation_value = getParent().Define_inExplicitConstructorInvocation(this, null);
    if (state().inCircle()) {
      inExplicitConstructorInvocation_computed = state().cycle();
    
    } else {
      inExplicitConstructorInvocation_computed = ASTNode$State.NON_CYCLE;
    
    }
    return inExplicitConstructorInvocation_value;
  }
  /** @apilevel internal */
  private void inExplicitConstructorInvocation_reset() {
    inExplicitConstructorInvocation_computed = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle inExplicitConstructorInvocation_computed = null;

  /** @apilevel internal */
  protected boolean inExplicitConstructorInvocation_value;

  /**
   * @attribute inh
   * @aspect TypeHierarchyCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:208
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="TypeHierarchyCheck", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:208")
  public boolean inStaticContext() {
    ASTNode$State state = state();
    if (inStaticContext_computed == ASTNode$State.NON_CYCLE || inStaticContext_computed == state().cycle()) {
      return inStaticContext_value;
    }
    inStaticContext_value = getParent().Define_inStaticContext(this, null);
    if (state().inCircle()) {
      inStaticContext_computed = state().cycle();
    
    } else {
      inStaticContext_computed = ASTNode$State.NON_CYCLE;
    
    }
    return inStaticContext_value;
  }
  /** @apilevel internal */
  private void inStaticContext_reset() {
    inStaticContext_computed = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle inStaticContext_computed = null;

  /** @apilevel internal */
  protected boolean inStaticContext_value;

  /**
   * @attribute inh
   * @aspect Annotations
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Annotations.jrag:423
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="Annotations", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Annotations.jrag:423")
  public boolean withinSuppressWarnings(String annot) {
    boolean withinSuppressWarnings_String_value = getParent().Define_withinSuppressWarnings(this, null, annot);
    return withinSuppressWarnings_String_value;
  }
  /**
   * @attribute inh
   * @aspect Annotations
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Annotations.jrag:550
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="Annotations", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Annotations.jrag:550")
  public boolean withinDeprecatedAnnotation() {
    boolean withinDeprecatedAnnotation_value = getParent().Define_withinDeprecatedAnnotation(this, null);
    return withinDeprecatedAnnotation_value;
  }
  /**
   * @attribute inh
   * @aspect LookupParTypeDecl
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:1591
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="LookupParTypeDecl", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:1591")
  public TypeDecl typeWildcard() {
    TypeDecl typeWildcard_value = getParent().Define_typeWildcard(this, null);
    return typeWildcard_value;
  }
  /**
   * @attribute inh
   * @aspect LookupParTypeDecl
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:1605
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="LookupParTypeDecl", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:1605")
  public TypeDecl lookupWildcardExtends(TypeDecl typeDecl) {
    TypeDecl lookupWildcardExtends_TypeDecl_value = getParent().Define_lookupWildcardExtends(this, null, typeDecl);
    return lookupWildcardExtends_TypeDecl_value;
  }
  /**
   * @attribute inh
   * @aspect LookupParTypeDecl
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:1619
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="LookupParTypeDecl", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:1619")
  public TypeDecl lookupWildcardSuper(TypeDecl typeDecl) {
    TypeDecl lookupWildcardSuper_TypeDecl_value = getParent().Define_lookupWildcardSuper(this, null, typeDecl);
    return lookupWildcardSuper_TypeDecl_value;
  }
  /**
   * @attribute inh
   * @aspect LookupParTypeDecl
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:1642
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="LookupParTypeDecl", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:1642")
  public LUBType lookupLUBType(Collection<TypeDecl> bounds) {
    LUBType lookupLUBType_Collection_TypeDecl__value = getParent().Define_lookupLUBType(this, null, bounds);
    return lookupLUBType_Collection_TypeDecl__value;
  }
  /**
   * @attribute inh
   * @aspect LookupParTypeDecl
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:1683
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="LookupParTypeDecl", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:1683")
  public GLBType lookupGLBType(Collection<TypeDecl> bounds) {
    GLBType lookupGLBType_Collection_TypeDecl__value = getParent().Define_lookupGLBType(this, null, bounds);
    return lookupGLBType_Collection_TypeDecl__value;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Arrays.jrag:56
   * @apilevel internal
   */
  public TypeDecl Define_componentType(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == arrayType_value) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Arrays.jrag:54
      return this;
    }
    else {
      return getParent().Define_componentType(this, _callerNode);
    }
  }
  protected boolean canDefine_componentType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DeclareBeforeUse.jrag:58
   * @apilevel internal
   */
  public boolean Define_declaredBefore(ASTNode _callerNode, ASTNode _childNode, Variable decl) {
    if (_callerNode == getBodyDeclListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DeclareBeforeUse.jrag:71
      int index = _callerNode.getIndexOfChild(_childNode);
      return index == 0 ? declaredBefore(decl) : getBodyDecl(index - 1).declaredIn(decl);
    }
    else {
      return getParent().Define_declaredBefore(this, _callerNode, decl);
    }
  }
  protected boolean canDefine_declaredBefore(ASTNode _callerNode, ASTNode _childNode, Variable decl) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:34
   * @apilevel internal
   */
  public boolean Define_isDest(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  protected boolean canDefine_isDest(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:44
   * @apilevel internal
   */
  public boolean Define_isSource(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return true;
  }
  protected boolean canDefine_isSource(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:280
   * @apilevel internal
   */
  public boolean Define_assignedBefore(ASTNode _callerNode, ASTNode _childNode, Variable v, BodyDecl b) {
    int childIndex = this.getIndexOfChild(_callerNode);
    {
        if (!v.isField()) {
          if (v.hostType() != this) {
            return assignedBefore(v);
          }
          return false;
        }
        if (b instanceof FieldDecl && !((FieldDecl) b).isStatic() && v.isClassVariable()) {
          return true;
        }
        if (b instanceof MethodDecl) {
          return true;
        }
        if (b instanceof MemberTypeDecl && v.isBlank() && v.isFinal() && v.hostType() == this) {
          return true;
        }
        if (v.isField()) {
          if (v.isFinal() && v.hostType() != this && instanceOf(v.hostType())) {
            return true;
          }
          boolean skip = !(b instanceof ConstructorDecl);
          for (int i = getNumBodyDecl() - 1; i >= 0; --i) {
            BodyDecl decl = getBodyDecl(i);
            if (skip && b == decl) {
              skip = false;
            } else if (!skip) {
              if (decl instanceof FieldDecl) {
                FieldDecl f = (FieldDecl) decl;
                if ((v.isClassVariable() && f.isStatic())
                    || (v.isInstanceVariable() && !f.isStatic())) {
                  return f.assignedAfter(v);
                }
              } else if (decl instanceof StaticInitializer && v.isClassVariable()) {
                StaticInitializer si = (StaticInitializer) decl;
                return si.assignedAfter(v);
              } else if (decl instanceof InstanceInitializer && v.isInstanceVariable()) {
                InstanceInitializer ii = (InstanceInitializer) decl;
                return ii.assignedAfter(v);
              }
            }
          }
        }
        return assignedBefore(v);
      }
  }
  protected boolean canDefine_assignedBefore(ASTNode _callerNode, ASTNode _childNode, Variable v, BodyDecl b) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:923
   * @apilevel internal
   */
  public boolean Define_unassignedBefore(ASTNode _callerNode, ASTNode _childNode, Variable v, BodyDecl b) {
    int childIndex = this.getIndexOfChild(_callerNode);
    {
        if (b instanceof MethodDecl || b instanceof MemberTypeDecl) {
          return false;
        }
        if (v.isField()) {
          boolean skip = !(b instanceof ConstructorDecl);
          for (int i = getNumBodyDecl() - 1; i >= 0; --i) {
            BodyDecl decl = getBodyDecl(i);
            if (skip && b == decl) {
              skip = false;
            } else if (!skip) {
              if (decl instanceof FieldDecl) {
                FieldDecl f = (FieldDecl) decl;
                if ((v.isClassVariable() && f.isStatic())
                    || (v.isInstanceVariable() && !f.isStatic())) {
                  return f.unassignedAfter(v);
                }
              } else if (decl instanceof StaticInitializer && v.isClassVariable()) {
                StaticInitializer si = (StaticInitializer) decl;
                return si.unassignedAfter(v);
              } else if (decl instanceof InstanceInitializer && v.isInstanceVariable()) {
                InstanceInitializer ii = (InstanceInitializer) decl;
                return ii.unassignedAfter(v);
              }
            }
          }
        }
        return unassignedBefore(v);
      }
  }
  protected boolean canDefine_unassignedBefore(ASTNode _callerNode, ASTNode _childNode, Variable v, BodyDecl b) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupConstructor.jrag:35
   * @apilevel internal
   */
  public Collection<ConstructorDecl> Define_lookupConstructor(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return constructors();
  }
  protected boolean canDefine_lookupConstructor(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupConstructor.jrag:43
   * @apilevel internal
   */
  public Collection<ConstructorDecl> Define_lookupSuperConstructor(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return lookupSuperConstructor();
  }
  protected boolean canDefine_lookupSuperConstructor(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupMethod.jrag:52
   * @apilevel internal
   */
  public Collection<MethodDecl> Define_lookupMethod(ASTNode _callerNode, ASTNode _childNode, String name) {
    if (_callerNode == getBodyDeclListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupMethod.jrag:69
      int i = _callerNode.getIndexOfChild(_childNode);
      return unqualifiedLookupMethod(name);
    }
    else {
      return getParent().Define_lookupMethod(this, _callerNode, name);
    }
  }
  protected boolean canDefine_lookupMethod(ASTNode _callerNode, ASTNode _childNode, String name) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericMethods.jrag:225
   * @apilevel internal
   */
  public SimpleSet<TypeDecl> Define_lookupType(ASTNode _callerNode, ASTNode _childNode, String name) {
    if (_callerNode == getBodyDeclListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:488
      int index = _callerNode.getIndexOfChild(_childNode);
      return localLookupType(name);
    }
    else {
      return getParent().Define_lookupType(this, _callerNode, name);
    }
  }
  protected boolean canDefine_lookupType(ASTNode _callerNode, ASTNode _childNode, String name) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\LookupVariable.jrag:30
   * @apilevel internal
   */
  public SimpleSet<Variable> Define_lookupVariable(ASTNode _callerNode, ASTNode _childNode, String name) {
    if (_callerNode == getBodyDeclListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupVariable.jrag:51
      int i = _callerNode.getIndexOfChild(_childNode);
      {
          SimpleSet<Variable> list = memberFields(name);
          if (!list.isEmpty()) {
            return list;
          }
          list = lookupVariable(name);
          if (inStaticContext() || isStatic()) {
            list = removeInstanceVariables(list);
          }
          return list;
        }
    }
    else {
      return getParent().Define_lookupVariable(this, _callerNode, name);
    }
  }
  protected boolean canDefine_lookupVariable(ASTNode _callerNode, ASTNode _childNode, String name) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:433
   * @apilevel internal
   */
  public boolean Define_mayBePublic(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getBodyDeclListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:355
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return false;
    }
    else if (getModifiersNoTransform() != null && _callerNode == getModifiers()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:304
      return true;
    }
    else {
      return getParent().Define_mayBePublic(this, _callerNode);
    }
  }
  protected boolean canDefine_mayBePublic(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:435
   * @apilevel internal
   */
  public boolean Define_mayBeProtected(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getBodyDeclListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:356
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return false;
    }
    else if (getModifiersNoTransform() != null && _callerNode == getModifiers()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:305
      return true;
    }
    else {
      return getParent().Define_mayBeProtected(this, _callerNode);
    }
  }
  protected boolean canDefine_mayBeProtected(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:434
   * @apilevel internal
   */
  public boolean Define_mayBePrivate(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getBodyDeclListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:357
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return false;
    }
    else if (getModifiersNoTransform() != null && _callerNode == getModifiers()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:306
      return true;
    }
    else {
      return getParent().Define_mayBePrivate(this, _callerNode);
    }
  }
  protected boolean canDefine_mayBePrivate(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:438
   * @apilevel internal
   */
  public boolean Define_mayBeAbstract(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getBodyDeclListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:360
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return false;
    }
    else if (getModifiersNoTransform() != null && _callerNode == getModifiers()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:307
      return true;
    }
    else {
      return getParent().Define_mayBeAbstract(this, _callerNode);
    }
  }
  protected boolean canDefine_mayBeAbstract(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:436
   * @apilevel internal
   */
  public boolean Define_mayBeStatic(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getBodyDeclListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:358
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return false;
    }
    else if (getModifiersNoTransform() != null && _callerNode == getModifiers()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:308
      return true;
    }
    else {
      return getParent().Define_mayBeStatic(this, _callerNode);
    }
  }
  protected boolean canDefine_mayBeStatic(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:441
   * @apilevel internal
   */
  public boolean Define_mayBeStrictfp(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getBodyDeclListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:363
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return false;
    }
    else if (getModifiersNoTransform() != null && _callerNode == getModifiers()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:309
      return true;
    }
    else {
      return getParent().Define_mayBeStrictfp(this, _callerNode);
    }
  }
  protected boolean canDefine_mayBeStrictfp(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:437
   * @apilevel internal
   */
  public boolean Define_mayBeFinal(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getBodyDeclListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:359
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return false;
    }
    else {
      return getParent().Define_mayBeFinal(this, _callerNode);
    }
  }
  protected boolean canDefine_mayBeFinal(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:439
   * @apilevel internal
   */
  public boolean Define_mayBeVolatile(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getBodyDeclListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:361
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return false;
    }
    else {
      return getParent().Define_mayBeVolatile(this, _callerNode);
    }
  }
  protected boolean canDefine_mayBeVolatile(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:440
   * @apilevel internal
   */
  public boolean Define_mayBeTransient(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getBodyDeclListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:362
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return false;
    }
    else {
      return getParent().Define_mayBeTransient(this, _callerNode);
    }
  }
  protected boolean canDefine_mayBeTransient(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:442
   * @apilevel internal
   */
  public boolean Define_mayBeSynchronized(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getBodyDeclListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:364
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return false;
    }
    else {
      return getParent().Define_mayBeSynchronized(this, _callerNode);
    }
  }
  protected boolean canDefine_mayBeSynchronized(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:443
   * @apilevel internal
   */
  public boolean Define_mayBeNative(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getBodyDeclListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:365
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return false;
    }
    else {
      return getParent().Define_mayBeNative(this, _callerNode);
    }
  }
  protected boolean canDefine_mayBeNative(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\NameCheck.jrag:31
   * @apilevel internal
   */
  public VariableScope Define_outerScope(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return this;
  }
  protected boolean canDefine_outerScope(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\NameCheck.jrag:504
   * @apilevel internal
   */
  public boolean Define_insideLoop(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getBodyDeclListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\NameCheck.jrag:507
      int i = _callerNode.getIndexOfChild(_childNode);
      return false;
    }
    else {
      return getParent().Define_insideLoop(this, _callerNode);
    }
  }
  protected boolean canDefine_insideLoop(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\NameCheck.jrag:512
   * @apilevel internal
   */
  public boolean Define_insideSwitch(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getBodyDeclListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\NameCheck.jrag:514
      int i = _callerNode.getIndexOfChild(_childNode);
      return false;
    }
    else {
      return getParent().Define_insideSwitch(this, _callerNode);
    }
  }
  protected boolean canDefine_insideSwitch(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:78
   * @apilevel internal
   */
  public boolean Define_isLeftChildOfDot(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  protected boolean canDefine_isLeftChildOfDot(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:93
   * @apilevel internal
   */
  public boolean Define_isRightChildOfDot(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  protected boolean canDefine_isRightChildOfDot(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:110
   * @apilevel internal
   */
  public Expr Define_prevExpr(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return prevExprError();
  }
  protected boolean canDefine_prevExpr(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:134
   * @apilevel internal
   */
  public Access Define_nextAccess(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return nextAccessError();
  }
  protected boolean canDefine_nextAccess(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\SyntacticClassification.jrag:36
   * @apilevel internal
   */
  public NameType Define_nameType(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getBodyDeclListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\SyntacticClassification.jrag:137
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return NameType.EXPRESSION_NAME;
    }
    else {
      return getParent().Define_nameType(this, _callerNode);
    }
  }
  protected boolean canDefine_nameType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:232
   * @apilevel internal
   */
  public boolean Define_isAnonymous(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  protected boolean canDefine_isAnonymous(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:555
   * @apilevel internal
   */
  public TypeDecl Define_enclosingType(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getBodyDeclListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:543
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return this;
    }
    else {
      return getParent().Define_enclosingType(this, _callerNode);
    }
  }
  protected boolean canDefine_enclosingType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:580
   * @apilevel internal
   */
  public boolean Define_isNestedType(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getBodyDeclListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:582
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return true;
    }
    else {
      return getParent().Define_isNestedType(this, _callerNode);
    }
  }
  protected boolean canDefine_isNestedType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:606
   * @apilevel internal
   */
  public boolean Define_isLocalClass(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getBodyDeclListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:610
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return false;
    }
    else {
      return getParent().Define_isLocalClass(this, _callerNode);
    }
  }
  protected boolean canDefine_isLocalClass(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\MultiCatch.jrag:76
   * @apilevel internal
   */
  public TypeDecl Define_hostType(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return this;
  }
  protected boolean canDefine_hostType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeCheck.jrag:534
   * @apilevel internal
   */
  public TypeDecl Define_returnType(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getBodyDeclListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeCheck.jrag:535
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return typeVoid();
    }
    else {
      return getParent().Define_returnType(this, _callerNode);
    }
  }
  protected boolean canDefine_returnType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeCheck.jrag:667
   * @apilevel internal
   */
  public TypeDecl Define_enclosingInstance(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getBodyDeclListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeCheck.jrag:673
      int index = _callerNode.getIndexOfChild(_childNode);
      {
          if (getBodyDecl(index) instanceof MemberTypeDecl
              && !((MemberTypeDecl) getBodyDecl(index)).typeDecl().isInnerType()) {
            return null;
          }
          if (getBodyDecl(index) instanceof ConstructorDecl) {
            return enclosingInstance();
          }
          return this;
        }
    }
    else {
      return getParent().Define_enclosingInstance(this, _callerNode);
    }
  }
  protected boolean canDefine_enclosingInstance(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:33
   * @apilevel internal
   */
  public String Define_methodHost(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return typeName();
  }
  protected boolean canDefine_methodHost(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:207
   * @apilevel internal
   */
  public boolean Define_inStaticContext(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return isStatic() || inStaticContext();
  }
  protected boolean canDefine_inStaticContext(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\PreciseRethrow.jrag:280
   * @apilevel internal
   */
  public boolean Define_reportUnreachable(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return true;
  }
  protected boolean canDefine_reportUnreachable(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Annotations.jrag:95
   * @apilevel internal
   */
  public boolean Define_inComplexAnnotation(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  protected boolean canDefine_inComplexAnnotation(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Annotations.jrag:131
   * @apilevel internal
   */
  public boolean Define_mayUseAnnotationTarget(ASTNode _callerNode, ASTNode _childNode, String name) {
    if (getModifiersNoTransform() != null && _callerNode == getModifiers()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Annotations.jrag:138
      return name.equals("TYPE");
    }
    else {
      return getParent().Define_mayUseAnnotationTarget(this, _callerNode, name);
    }
  }
  protected boolean canDefine_mayUseAnnotationTarget(ASTNode _callerNode, ASTNode _childNode, String name) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Enums.jrag:566
   * @apilevel internal
   */
  public boolean Define_inEnumInitializer(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  protected boolean canDefine_inEnumInitializer(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:789
   * @apilevel internal
   */
  public String Define_typeVariableContext(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return fullName();
  }
  protected boolean canDefine_typeVariableContext(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\Diamond.jrag:94
   * @apilevel internal
   */
  public ClassInstanceExpr Define_getClassInstanceExpr(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return null;
  }
  protected boolean canDefine_getClassInstanceExpr(ASTNode _callerNode, ASTNode _childNode) {
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
    // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:88
    {
      java.util.Set<ASTNode> contributors = _map.get(_root);
      if (contributors == null) {
        contributors = new java.util.LinkedHashSet<ASTNode>();
        _map.put((ASTNode) _root, contributors);
      }
      contributors.add(this);
    }
    // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\NameCheck.jrag:362
    {
      java.util.Set<ASTNode> contributors = _map.get(_root);
      if (contributors == null) {
        contributors = new java.util.LinkedHashSet<ASTNode>();
        _map.put((ASTNode) _root, contributors);
      }
      contributors.add(this);
    }
    // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:231
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
    for (Problem value : modifierProblems()) {
      collection.add(value);
    }
    for (Problem value : nameProblems()) {
      collection.add(value);
    }
    for (Problem value : typeProblems()) {
      collection.add(value);
    }
  }
}
