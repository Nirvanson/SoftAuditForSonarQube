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
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\grammar\\Java.ast:4
 * @production CompilationUnit : {@link ASTNode} ::= <span class="component">&lt;PackageDecl:String&gt;</span> <span class="component">{@link ImportDecl}*</span> <span class="component">{@link TypeDecl}*</span>;

 */
public class CompilationUnit extends ASTNode<ASTNode> implements Cloneable {
  /**
   * @aspect Java4PrettyPrint
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\PrettyPrint.jadd:302
   */
  public void prettyPrint(PrettyPrinter out) {
    if (hasPackageDecl()) {
      out.print("package ");
      out.print(getPackageDecl());
      out.print(";");
      out.println();
    }
    if (!out.isNewLine()) {
      out.println();
    }
    out.print(getImportDeclList());
    if (!out.isNewLine()) {
      out.println();
    }
    out.println();
    out.join(getTypeDecls(), new PrettyPrinter.Joiner() {
      @Override
      public void printSeparator(PrettyPrinter out) {
        out.println();
        out.println();
      }
    });
  }
  /**
   * @aspect ClassPath
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ClassPath.jrag:399
   */
  private ClassSource classSource = ClassSource.NONE;
  /**
   * @aspect ClassPath
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ClassPath.jrag:400
   */
  private boolean fromSource = false;
  /**
   * @aspect ClassPath
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ClassPath.jrag:402
   */
  public void setClassSource(ClassSource source) {
    this.classSource = source;
  }
  /**
   * @aspect ClassPath
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ClassPath.jrag:405
   */
  public ClassSource getClassSource() {
    return classSource;
  }
  /**
   * @aspect ClassPath
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ClassPath.jrag:408
   */
  public void setFromSource(boolean value) {
    this.fromSource = value;
  }
  /**
   * @aspect ErrorCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ErrorCheck.jrag:88
   */
  protected Collection<Problem> errors = new LinkedList<Problem>();
  /**
   * @aspect ErrorCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ErrorCheck.jrag:89
   */
  protected Collection<Problem> warnings = new LinkedList<Problem>();
  /**
   * @aspect ErrorCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ErrorCheck.jrag:91
   */
  public Collection parseErrors() { return parseErrors; }
  /**
   * @aspect ErrorCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ErrorCheck.jrag:92
   */
  public void addParseError(Problem msg) { parseErrors.add(msg); }
  /**
   * @aspect ErrorCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ErrorCheck.jrag:93
   */
  protected Collection parseErrors = new ArrayList();
  /**
   * @return collection of semantic errors
   * @aspect ErrorCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ErrorCheck.jrag:261
   */
  public Collection<Problem> errors() {
    return errors;
  }
  /**
   * @return collection of semantic warnings
   * @aspect ErrorCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ErrorCheck.jrag:268
   */
  public Collection<Problem> warnings() {
    return warnings;
  }
  /**
   * @aspect NameCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\NameCheck.jrag:63
   */
  public void refined_NameCheck_CompilationUnit_nameCheck() {
    for (int i = 0; i < getNumImportDecl(); i++) {
      ImportDecl decl = getImportDecl(i);
      if (!decl.isOnDemand()) {
        Iterator importIter = decl.importedTypes().iterator();
        while (importIter.hasNext()) {
          TypeDecl importedType = (TypeDecl) importIter.next();
          Iterator iter = localLookupType(importedType.name()).iterator();
          while (iter.hasNext()) {
            TypeDecl local = (TypeDecl) iter.next();
            if (local != importedType) {
              errorf("imported type %s conflicts with visible type", decl.typeName());
            }
          }
        }
      }
    }
  }
  /**
   * @aspect ExtensionBase
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\src\\jastadd\\ExtensionBase.jrag:3
   */
  public void process() {
    System.out.println(pathName() + " contained no errors");
  }
  /**
   * @declaredat ASTNode:1
   */
  public CompilationUnit() {
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
    setChild(new List(), 0);
    setChild(new List(), 1);
  }
  /**
   * @declaredat ASTNode:15
   */
  public CompilationUnit(String p0, List<ImportDecl> p1, List<TypeDecl> p2) {
    setPackageDecl(p0);
    setChild(p1, 0);
    setChild(p2, 1);
  }
  /**
   * @declaredat ASTNode:20
   */
  public CompilationUnit(beaver.Symbol p0, List<ImportDecl> p1, List<TypeDecl> p2) {
    setPackageDecl(p0);
    setChild(p1, 0);
    setChild(p2, 1);
  }
  /**
   * @apilevel low-level
   * @declaredat ASTNode:28
   */
  protected int numChildren() {
    return 2;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:34
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:40
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    packageName_reset();
    lookupType_String_reset();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:48
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:54
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:60
   */
  public CompilationUnit clone() throws CloneNotSupportedException {
    CompilationUnit node = (CompilationUnit) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:67
   */
  public CompilationUnit copy() {
    try {
      CompilationUnit node = (CompilationUnit) clone();
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
   * @declaredat ASTNode:86
   */
  @Deprecated
  public CompilationUnit fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:96
   */
  public CompilationUnit treeCopyNoTransform() {
    CompilationUnit tree = (CompilationUnit) copy();
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
   * @declaredat ASTNode:116
   */
  public CompilationUnit treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:123
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenString_PackageDecl == ((CompilationUnit)node).tokenString_PackageDecl);    
  }
  /**
   * Replaces the lexeme PackageDecl.
   * @param value The new value for the lexeme PackageDecl.
   * @apilevel high-level
   */
  public void setPackageDecl(String value) {
    tokenString_PackageDecl = value;
  }
  /**
   * @apilevel internal
   */
  protected String tokenString_PackageDecl;
  /**
   */
  public int PackageDeclstart;
  /**
   */
  public int PackageDeclend;
  /**
   * JastAdd-internal setter for lexeme PackageDecl using the Beaver parser.
   * @param symbol Symbol containing the new value for the lexeme PackageDecl
   * @apilevel internal
   */
  public void setPackageDecl(beaver.Symbol symbol) {
    if (symbol.value != null && !(symbol.value instanceof String))
    throw new UnsupportedOperationException("setPackageDecl is only valid for String lexemes");
    tokenString_PackageDecl = (String)symbol.value;
    PackageDeclstart = symbol.getStart();
    PackageDeclend = symbol.getEnd();
  }
  /**
   * Retrieves the value for the lexeme PackageDecl.
   * @return The value for the lexeme PackageDecl.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Token(name="PackageDecl")
  public String getPackageDecl() {
    return tokenString_PackageDecl != null ? tokenString_PackageDecl : "";
  }
  /**
   * Replaces the ImportDecl list.
   * @param list The new list node to be used as the ImportDecl list.
   * @apilevel high-level
   */
  public void setImportDeclList(List<ImportDecl> list) {
    setChild(list, 0);
  }
  /**
   * Retrieves the number of children in the ImportDecl list.
   * @return Number of children in the ImportDecl list.
   * @apilevel high-level
   */
  public int getNumImportDecl() {
    return getImportDeclList().getNumChild();
  }
  /**
   * Retrieves the number of children in the ImportDecl list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the ImportDecl list.
   * @apilevel low-level
   */
  public int getNumImportDeclNoTransform() {
    return getImportDeclListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the ImportDecl list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the ImportDecl list.
   * @apilevel high-level
   */
  public ImportDecl getImportDecl(int i) {
    return (ImportDecl) getImportDeclList().getChild(i);
  }
  /**
   * Check whether the ImportDecl list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasImportDecl() {
    return getImportDeclList().getNumChild() != 0;
  }
  /**
   * Append an element to the ImportDecl list.
   * @param node The element to append to the ImportDecl list.
   * @apilevel high-level
   */
  public void addImportDecl(ImportDecl node) {
    List<ImportDecl> list = (parent == null) ? getImportDeclListNoTransform() : getImportDeclList();
    list.addChild(node);
  }
  /**
   * @apilevel low-level
   */
  public void addImportDeclNoTransform(ImportDecl node) {
    List<ImportDecl> list = getImportDeclListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the ImportDecl list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setImportDecl(ImportDecl node, int i) {
    List<ImportDecl> list = getImportDeclList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the ImportDecl list.
   * @return The node representing the ImportDecl list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="ImportDecl")
  public List<ImportDecl> getImportDeclList() {
    List<ImportDecl> list = (List<ImportDecl>) getChild(0);
    return list;
  }
  /**
   * Retrieves the ImportDecl list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the ImportDecl list.
   * @apilevel low-level
   */
  public List<ImportDecl> getImportDeclListNoTransform() {
    return (List<ImportDecl>) getChildNoTransform(0);
  }
  /**
   * Retrieves the ImportDecl list.
   * @return The node representing the ImportDecl list.
   * @apilevel high-level
   */
  public List<ImportDecl> getImportDecls() {
    return getImportDeclList();
  }
  /**
   * Retrieves the ImportDecl list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the ImportDecl list.
   * @apilevel low-level
   */
  public List<ImportDecl> getImportDeclsNoTransform() {
    return getImportDeclListNoTransform();
  }
  /**
   * Replaces the TypeDecl list.
   * @param list The new list node to be used as the TypeDecl list.
   * @apilevel high-level
   */
  public void setTypeDeclList(List<TypeDecl> list) {
    setChild(list, 1);
  }
  /**
   * Retrieves the number of children in the TypeDecl list.
   * @return Number of children in the TypeDecl list.
   * @apilevel high-level
   */
  public int getNumTypeDecl() {
    return getTypeDeclList().getNumChild();
  }
  /**
   * Retrieves the number of children in the TypeDecl list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the TypeDecl list.
   * @apilevel low-level
   */
  public int getNumTypeDeclNoTransform() {
    return getTypeDeclListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the TypeDecl list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the TypeDecl list.
   * @apilevel high-level
   */
  public TypeDecl getTypeDecl(int i) {
    return (TypeDecl) getTypeDeclList().getChild(i);
  }
  /**
   * Check whether the TypeDecl list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasTypeDecl() {
    return getTypeDeclList().getNumChild() != 0;
  }
  /**
   * Append an element to the TypeDecl list.
   * @param node The element to append to the TypeDecl list.
   * @apilevel high-level
   */
  public void addTypeDecl(TypeDecl node) {
    List<TypeDecl> list = (parent == null) ? getTypeDeclListNoTransform() : getTypeDeclList();
    list.addChild(node);
  }
  /**
   * @apilevel low-level
   */
  public void addTypeDeclNoTransform(TypeDecl node) {
    List<TypeDecl> list = getTypeDeclListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the TypeDecl list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setTypeDecl(TypeDecl node, int i) {
    List<TypeDecl> list = getTypeDeclList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the TypeDecl list.
   * @return The node representing the TypeDecl list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="TypeDecl")
  public List<TypeDecl> getTypeDeclList() {
    List<TypeDecl> list = (List<TypeDecl>) getChild(1);
    return list;
  }
  /**
   * Retrieves the TypeDecl list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the TypeDecl list.
   * @apilevel low-level
   */
  public List<TypeDecl> getTypeDeclListNoTransform() {
    return (List<TypeDecl>) getChildNoTransform(1);
  }
  /**
   * Retrieves the TypeDecl list.
   * @return The node representing the TypeDecl list.
   * @apilevel high-level
   */
  public List<TypeDecl> getTypeDecls() {
    return getTypeDeclList();
  }
  /**
   * Retrieves the TypeDecl list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the TypeDecl list.
   * @apilevel low-level
   */
  public List<TypeDecl> getTypeDeclsNoTransform() {
    return getTypeDeclListNoTransform();
  }
  /**
   * @aspect StaticImports
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\StaticImports.jrag:273
   */
    public void nameCheck() {
    refined_NameCheck_CompilationUnit_nameCheck();
    for (int i = 0; i < getNumImportDecl(); i++) {
      if (getImportDecl(i) instanceof SingleStaticImportDecl) {
        SingleStaticImportDecl decl = (SingleStaticImportDecl) getImportDecl(i);
        String name = decl.name();
        if (!decl.importedTypes(name).isEmpty()) {
          TypeDecl type = (TypeDecl) decl.importedTypes(name).iterator().next();
          if (localLookupType(name).contains(type)) {
            decl.errorf("the imported name %s.%s is already declared in this compilation unit",
                packageName(), name);
          }
        }
      }
    }
  }
  /**
   * @aspect TypeScopePropagation
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:347
   */
  private SimpleSet refined_TypeScopePropagation_CompilationUnit_Child_lookupType_String(String name)
{
    // Locally declared types in the compilation unit.
    SimpleSet set = localLookupType(name);
    if (!set.isEmpty()) {
      return set;
    }

    // Imported types.
    set = importedTypes(name);
    if (!set.isEmpty()) {
      return set;
    }

    // Types in the same package.
    TypeDecl result = lookupType(packageName(), name);
    if (result.accessibleFromPackage(packageName())) {
      return result;
    }

    // Types imported on demand.
    set = importedTypesOnDemand(name);
    if (!set.isEmpty()) {
      return set;
    }

    // Include primitive types.
    result = lookupType(PRIMITIVE_PACKAGE_NAME, name);
    if (!result.isUnknown()) {
      return result;
    }

    // 7.5.5 Automatic Imports
    result = lookupType("java.lang", name);
    if (result.accessibleFromPackage(packageName())) {
      return result;
    }
    return lookupType(name);
  }
  @ASTNodeAnnotation.Attribute
  public String relativeName() {
    String relativeName_value = getClassSource().relativeName();

    return relativeName_value;
  }
  @ASTNodeAnnotation.Attribute
  public String pathName() {
    String pathName_value = getClassSource().pathName();

    return pathName_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean fromSource() {
    boolean fromSource_value = fromSource;

    return fromSource_value;
  }
  /** Searches for a type with the given simple name in this compilation unit. 
   * @attribute syn
   * @aspect TypeScopePropagation
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:387
   */
  @ASTNodeAnnotation.Attribute
  public SimpleSet localLookupType(String name) {
    {
        for (int i = 0; i < getNumTypeDecl(); i++) {
          if (getTypeDecl(i).name().equals(name)) {
            return SimpleSet.emptySet.add(getTypeDecl(i));
          }
        }
        return SimpleSet.emptySet;
      }
  }
  /**
   * @attribute syn
   * @aspect TypeScopePropagation
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:396
   */
  @ASTNodeAnnotation.Attribute
  public SimpleSet importedTypes(String name) {
    {
        SimpleSet set = SimpleSet.emptySet;
        for (int i = 0; i < getNumImportDecl(); i++) {
          if (!getImportDecl(i).isOnDemand()) {
            for (Iterator iter = getImportDecl(i).importedTypes(name).iterator(); iter.hasNext(); ) {
              set = set.add(iter.next());
            }
          }
        }
        return set;
      }
  }
  /**
   * @attribute syn
   * @aspect TypeScopePropagation
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:408
   */
  @ASTNodeAnnotation.Attribute
  public SimpleSet importedTypesOnDemand(String name) {
    {
        SimpleSet set = SimpleSet.emptySet;
        for (int i = 0; i < getNumImportDecl(); i++) {
          if (getImportDecl(i).isOnDemand()) {
            for (Iterator iter = getImportDecl(i).importedTypes(name).iterator(); iter.hasNext(); ) {
              set = set.add(iter.next());
            }
          }
        }
        return set;
      }
  }
  @ASTNodeAnnotation.Attribute
  public boolean hasPackageDecl() {
    boolean hasPackageDecl_value = !getPackageDecl().isEmpty();

    return hasPackageDecl_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean packageName_computed = false;
  /**
   * @apilevel internal
   */
  protected String packageName_value;
  /**
   * @apilevel internal
   */
  private void packageName_reset() {
    packageName_computed = false;
    packageName_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public String packageName() {
    ASTNode$State state = state();
    if (packageName_computed) {
      return packageName_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    packageName_value = packageName_compute();
    if (isFinal && num == state().boundariesCrossed) {
      packageName_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return packageName_value;
  }
  /**
   * @apilevel internal
   */
  private String packageName_compute() {return getPackageDecl();}
  /**
   * @attribute syn
   * @aspect StaticImports
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\StaticImports.jrag:186
   */
  @ASTNodeAnnotation.Attribute
  public SimpleSet importedFields(String name) {
    {
        SimpleSet set = SimpleSet.emptySet;
        for (int i = 0; i < getNumImportDecl(); i++) {
          if (!getImportDecl(i).isOnDemand()) {
            for (Iterator iter = getImportDecl(i).importedFields(name).iterator(); iter.hasNext(); ) {
              set = set.add(iter.next());
            }
          }
        }
        return set;
      }
  }
  /**
   * @attribute syn
   * @aspect StaticImports
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\StaticImports.jrag:198
   */
  @ASTNodeAnnotation.Attribute
  public SimpleSet importedFieldsOnDemand(String name) {
    {
        SimpleSet set = SimpleSet.emptySet;
        for (int i = 0; i < getNumImportDecl(); i++) {
          if (getImportDecl(i).isOnDemand()) {
            for (Iterator iter = getImportDecl(i).importedFields(name).iterator(); iter.hasNext(); ) {
              set = set.add(iter.next());
            }
          }
        }
        return set;
      }
  }
  /**
   * @attribute syn
   * @aspect StaticImports
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\StaticImports.jrag:228
   */
  @ASTNodeAnnotation.Attribute
  public Collection importedMethods(String name) {
    {
        Collection list = new ArrayList();
        for (int i = 0; i < getNumImportDecl(); i++) {
          if (!getImportDecl(i).isOnDemand()) {
            list.addAll(getImportDecl(i).importedMethods(name));
          }
        }
        return list;
      }
  }
  /**
   * @attribute syn
   * @aspect StaticImports
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\StaticImports.jrag:238
   */
  @ASTNodeAnnotation.Attribute
  public Collection importedMethodsOnDemand(String name) {
    {
        Collection list = new ArrayList();
        for (int i = 0; i < getNumImportDecl(); i++) {
          if (getImportDecl(i).isOnDemand()) {
            list.addAll(getImportDecl(i).importedMethods(name));
          }
        }
        return list;
      }
  }
  /**
   * @attribute inh
   * @aspect LookupFullyQualifiedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:133
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl lookupType(String packageName, String typeName) {
    TypeDecl lookupType_String_String_value = getParent().Define_lookupType(this, null, packageName, typeName);

    return lookupType_String_String_value;
  }
  /**
   * @attribute inh
   * @aspect TypeScopePropagation
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:336
   */
  @ASTNodeAnnotation.Attribute
  public SimpleSet lookupType(String name) {
    Object _parameters = name;
    if (lookupType_String_values == null) lookupType_String_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (lookupType_String_values.containsKey(_parameters)) {
      return (SimpleSet) lookupType_String_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    SimpleSet lookupType_String_value = getParent().Define_lookupType(this, null, name);
    if (isFinal && num == state().boundariesCrossed) {
      lookupType_String_values.put(_parameters, lookupType_String_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return lookupType_String_value;
  }
  /**
   * @apilevel internal
   */
  protected java.util.Map lookupType_String_values;
  /**
   * @apilevel internal
   */
  private void lookupType_String_reset() {
    lookupType_String_values = null;
  }
  /**
   * @attribute inh
   * @aspect StaticImports
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\StaticImports.jrag:184
   */
  @ASTNodeAnnotation.Attribute
  public SimpleSet lookupVariable(String name) {
    SimpleSet lookupVariable_String_value = getParent().Define_lookupVariable(this, null, name);

    return lookupVariable_String_value;
  }
  /**
   * @attribute inh
   * @aspect StaticImports
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\StaticImports.jrag:226
   */
  @ASTNodeAnnotation.Attribute
  public Collection lookupMethod(String name) {
    Collection lookupMethod_String_value = getParent().Define_lookupMethod(this, null, name);

    return lookupMethod_String_value;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ClassPath.jrag:79
   * @apilevel internal
   */
  public CompilationUnit Define_compilationUnit(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return this;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:71
   * @apilevel internal
   */
  public boolean Define_isIncOrDec(ASTNode caller, ASTNode child) {
    if (caller == getTypeDeclListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:73
      int childIndex = caller.getIndexOfChild(child);
      return false;
    }
    else {
      return getParent().Define_isIncOrDec(this, caller);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\TryWithResources.jrag:113
   * @apilevel internal
   */
  public boolean Define_handlesException(ASTNode caller, ASTNode child, TypeDecl exceptionType) {
    if (caller == getImportDeclListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\StaticImports.jrag:292
      int childIndex = caller.getIndexOfChild(child);
      return false;
    }
    else if (caller == getTypeDeclListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ExceptionHandling.jrag:177
      int childIndex = caller.getIndexOfChild(child);
      return false;
    }
    else {
      return getParent().Define_handlesException(this, caller, exceptionType);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\GenericMethods.jrag:197
   * @apilevel internal
   */
  public SimpleSet Define_lookupType(ASTNode caller, ASTNode child, String name) {
    if (caller == getImportDeclListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:470
      int childIndex = caller.getIndexOfChild(child);
      return lookupType(name);
    }
    else {
      int childIndex = this.getIndexOfChild(caller);
      {
          SimpleSet result = SimpleSet.emptySet;
          for (Iterator iter = refined_TypeScopePropagation_CompilationUnit_Child_lookupType_String(name).iterator(); iter.hasNext(); ) {
            TypeDecl typeDecl = (TypeDecl) iter.next();
            if (typeDecl instanceof ParTypeDecl) {
              result = result.add(((ParTypeDecl) typeDecl).genericDecl());
            } else {
              result = result.add(typeDecl);
            }
          }
          return result;
        }
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\NameCheck.jrag:52
   * @apilevel internal
   */
  public SimpleSet Define_allImportedTypes(ASTNode caller, ASTNode child, String name) {
    if (caller == getImportDeclListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\NameCheck.jrag:53
      int childIndex = caller.getIndexOfChild(child);
      return importedTypes(name);
    }
    else {
      return getParent().Define_allImportedTypes(this, caller, name);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\QualifiedNames.jrag:103
   * @apilevel internal
   */
  public String Define_packageName(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return packageName();
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\SyntacticClassification.jrag:36
   * @apilevel internal
   */
  public NameType Define_nameType(ASTNode caller, ASTNode child) {
    if (caller == getImportDeclListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\SyntacticClassification.jrag:96
      int childIndex = caller.getIndexOfChild(child);
      return NameType.PACKAGE_NAME;
    }
    else {
      return getParent().Define_nameType(this, caller);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeAnalysis.jrag:544
   * @apilevel internal
   */
  public TypeDecl Define_enclosingType(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return null;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeAnalysis.jrag:569
   * @apilevel internal
   */
  public boolean Define_isNestedType(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeAnalysis.jrag:577
   * @apilevel internal
   */
  public boolean Define_isMemberType(ASTNode caller, ASTNode child) {
    if (caller == getTypeDeclListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeAnalysis.jrag:580
      int childIndex = caller.getIndexOfChild(child);
      return false;
    }
    else {
      return getParent().Define_isMemberType(this, caller);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeAnalysis.jrag:591
   * @apilevel internal
   */
  public boolean Define_isLocalClass(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeAnalysis.jrag:622
   * @apilevel internal
   */
  public String Define_hostPackage(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return packageName();
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\MultiCatch.jrag:71
   * @apilevel internal
   */
  public TypeDecl Define_hostType(ASTNode caller, ASTNode child) {
    if (caller == getImportDeclListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeAnalysis.jrag:635
      int childIndex = caller.getIndexOfChild(child);
      return null;
    }
    else {
      return getParent().Define_hostType(this, caller);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\LookupVariable.jrag:30
   * @apilevel internal
   */
  public SimpleSet Define_lookupVariable(ASTNode caller, ASTNode child, String name) {
    if (caller == getTypeDeclListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\StaticImports.jrag:172
      int childIndex = caller.getIndexOfChild(child);
      {
          SimpleSet set = importedFields(name);
          if (!set.isEmpty()) {
            return set;
          }
          set = importedFieldsOnDemand(name);
          if (!set.isEmpty()) {
            return set;
          }
          return lookupVariable(name);
        }
    }
    else {
      return getParent().Define_lookupVariable(this, caller, name);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupMethod.jrag:46
   * @apilevel internal
   */
  public Collection Define_lookupMethod(ASTNode caller, ASTNode child, String name) {
    if (caller == getTypeDeclListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\StaticImports.jrag:214
      int childIndex = caller.getIndexOfChild(child);
      {
          Collection list = importedMethods(name);
          if (!list.isEmpty()) {
            return list;
          }
          list = importedMethodsOnDemand(name);
          if (!list.isEmpty()) {
            return list;
          }
          return lookupMethod(name);
        }
    }
    else {
      return getParent().Define_lookupMethod(this, caller, name);
    }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\EnclosingLambda.jrag:37
   * @apilevel internal
   */
  public LambdaExpr Define_enclosingLambda(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return null;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:196
   * @apilevel internal
   */
  public boolean Define_assignmentContext(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:197
   * @apilevel internal
   */
  public boolean Define_invocationContext(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:198
   * @apilevel internal
   */
  public boolean Define_castContext(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:199
   * @apilevel internal
   */
  public boolean Define_stringContext(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:200
   * @apilevel internal
   */
  public boolean Define_numericContext(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TypeVariablePositions.jrag:29
   * @apilevel internal
   */
  public int Define_typeVarPosition(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return -1;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TypeVariablePositions.jrag:32
   * @apilevel internal
   */
  public boolean Define_typeVarInMethod(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TypeVariablePositions.jrag:30
   * @apilevel internal
   */
  public int Define_genericMethodLevel(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return 0;
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
