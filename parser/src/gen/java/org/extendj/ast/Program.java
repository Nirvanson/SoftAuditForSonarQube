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
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\grammar\\Java.ast:1
 * @production Program : {@link ASTNode} ::= <span class="component">{@link CompilationUnit}*</span>;

 */
public class Program extends ASTNode<ASTNode> implements Cloneable {
  /**
   * @aspect LibraryCompilationUnits
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LibCompilationUnits.jadd:32
   */
  public Map<String, CompilationUnit> getLibCompilationUnitValueMap() {

    return getLibCompilationUnit_String_values;
  }
  /**
   * @aspect AddOptionsToProgram
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\Options.jadd:34
   */
  public Options options = new Options();
  /**
   * @aspect AddOptionsToProgram
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\Options.jadd:35
   */
  public Options options() {
    return options;
  }
  /**
   * @aspect ClassPath
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ClassPath.jrag:50
   */
  protected BytecodeReader bytecodeReader;
  /**
   * @aspect ClassPath
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ClassPath.jrag:52
   */
  public void initBytecodeReader(BytecodeReader r) {
    bytecodeReader = r;
  }
  /**
   * @aspect ClassPath
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ClassPath.jrag:56
   */
  protected JavaParser javaParser;
  /**
   * @aspect ClassPath
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ClassPath.jrag:58
   */
  public void initJavaParser(JavaParser p) {
    javaParser = p;
  }
  /**
   * Parse the source file and add the compilation unit to the list of
   * compilation units in the program.
   * 
   * @param fileName file name of the source file
   * @return The CompilationUnit representing the source file,
   * or <code>null</code> if the source file could not be parsed
   * @aspect ClassPath
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ClassPath.jrag:92
   */
  public CompilationUnit addSourceFile(String fileName) throws IOException {
    SourceFilePath pathPart = new SourceFilePath(fileName);
    CompilationUnit cu = pathPart.getCompilationUnit(this, fileName);
    if (cu != emptyCompilationUnit()) {
      classPath.addPackage(cu.packageName());
      addCompilationUnit(cu);
    }
    return cu;
  }
  /**
   * Iterate over all source files and on-demand loaded compilation units.
   * @aspect ClassPath
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ClassPath.jrag:105
   */
  public Iterator<CompilationUnit> compilationUnitIterator() {
    return new Iterator<CompilationUnit>() {
      int index = 0;
      public boolean hasNext() {
        return index < getNumCompilationUnit();
      }
      public CompilationUnit next() {
        if (getNumCompilationUnit() == index) {
          throw new java.util.NoSuchElementException();
        }
        return getCompilationUnit(index++);
      }
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }
  /**
   * Get the input stream for a compilation unit specified using a canonical
   * name. This is used by the bytecode reader to load nested types.
   * @param name The canonical name of the compilation unit.
   * @aspect ClassPath
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ClassPath.jrag:128
   */
  public InputStream getInputStream(String name) {
    return classPath.getInputStream(name);
  }
  /**
   * @aspect ClassPath
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ClassPath.jrag:132
   */
  private final ClassPath classPath = new ClassPath(this);
  /**
   * @return <code>true</code> if there is a package with the given name on
   * the classpath
   * @aspect ClassPath
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ClassPath.jrag:395
   */
  public boolean isPackage(String packageName) {
    return classPath.isPackage(packageName);
  }
  /**
   * Add a path part to the library class path.
   * @aspect ClassPath
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ClassPath.jrag:415
   */
  public void addClassPath(PathPart pathPart) {
    classPath.addClassPath(pathPart);
  }
  /**
   * Add a path part to the user class path.
   * @aspect ClassPath
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ClassPath.jrag:422
   */
  public void addSourcePath(PathPart pathPart) {
    classPath.addSourcePath(pathPart);
  }
  /**
   * @aspect FrontendMain
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\FrontendMain.jrag:37
   */
  public long javaParseTime;
  /**
   * @aspect FrontendMain
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\FrontendMain.jrag:38
   */
  public long bytecodeParseTime;
  /**
   * @aspect FrontendMain
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\FrontendMain.jrag:39
   */
  public long codeGenTime;
  /**
   * @aspect FrontendMain
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\FrontendMain.jrag:40
   */
  public long errorCheckTime;
  /**
   * @aspect FrontendMain
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\FrontendMain.jrag:41
   */
  public int numJavaFiles;
  /**
   * @aspect FrontendMain
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\FrontendMain.jrag:42
   */
  public int numClassFiles;
  /**
   * Reset the profile statistics.
   * @aspect FrontendMain
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\FrontendMain.jrag:47
   */
  public void resetStatistics() {
    javaParseTime = 0;
    bytecodeParseTime = 0;
    codeGenTime = 0;
    errorCheckTime = 0;
    numJavaFiles = 0;
    numClassFiles = 0;
  }
  /**
   * @aspect FrontendMain
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\FrontendMain.jrag:56
   */
  public void printStatistics(PrintStream out) {
    out.println("javaParseTime: " + javaParseTime);
    out.println("numJavaFiles: " + numJavaFiles);
    out.println("bytecodeParseTime: " + javaParseTime);
    out.println("numClassFiles: " + numClassFiles);
    out.println("errorCheckTime: " + errorCheckTime);
    out.println("codeGenTime: " + codeGenTime);
  }
  /**
   * @aspect LookupFullyQualifiedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:135
   */
  public int classFileReadTime;
  /**
   * Extra cache for source type lookups. This cache is important in order to
   * make all source types shadow library types with matching names, even when
   * the source type lives in a compilation unit with a different simple name.
   * @aspect LookupFullyQualifiedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:164
   */
  private final Map<String, TypeDecl> sourceTypeMap = new HashMap<String, TypeDecl>();
  /**
   * @aspect LookupFullyQualifiedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:165
   */
  private boolean sourceTypeMapInitialized = false;
  /**
   * Lookup a type among source classes.
   * <p>
   * Invoking this method may cause more than just the specified type to be loaded, for
   * example if there exists other types in the same source file, the additional
   * types are also loaded and cached for the next lookup.
   * <p>
   * This method is not an attribute due to the necessary side-effects caused
   * by loading and caching of extra types.
   * @aspect LookupFullyQualifiedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:177
   */
  protected TypeDecl lookupSourceType(String packageName, String typeName) {
    String fullName = packageName.equals("") ? typeName : packageName + "." + typeName;

    if (!sourceTypeMapInitialized) {
      initializeSourceTypeMap();
      sourceTypeMapInitialized = true;
    }

    if (sourceTypeMap.containsKey(fullName)) {
      return sourceTypeMap.get(fullName);
    } else {
      sourceTypeMap.put(fullName, unknownType());
    }

    // Look for a matching library type.
    return unknownType();
  }
  /**
   * Initialize source types in the source type map.  This puts all the types provided by
   * Program.addSourceFile() in a map for lookup by Program.lookupSourceType.
   * @aspect LookupFullyQualifiedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:199
   */
  private void initializeSourceTypeMap() {
    // Initialize source type map with the compilation units supplied by Program.addSourceFile.
    for (int i = 0; i < getNumCompilationUnit(); i++) {
      CompilationUnit unit = getCompilationUnit(i);
      for (int j = 0; j < unit.getNumTypeDecl(); j++) {
        TypeDecl type = unit.getTypeDecl(j);
        sourceTypeMap.put(type.fullName(), type);
      }
    }
  }
  /**
   * Extra cache for library type lookups. This cache does not have a big
   * effect on performance due to the caching of the parameterized
   * lookupLibraryType attribute. The cache is needed to be able to track library types
   * that are declared in compilation units with a different simple name than the type itself.
   * @aspect LookupFullyQualifiedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:216
   */
  private final Map<String, TypeDecl> libraryTypeMap = new HashMap<String, TypeDecl>();
  /**
   * @aspect LookupFullyQualifiedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:217
   */
  private boolean libraryTypeMapInitialized = false;
  /**
   * Lookup a type among library classes. The lookup includes Jar and source files.
   * <p>
   * Invoking this method may cause more than just the specified type to be loaded, for
   * example if there exists other types in the same source file, the additional
   * types are also loaded and cached for the next lookup.
   * <p>
   * This method is not an attribute due to the necessary side-effects caused
   * by loading and caching of extra types.
   * @aspect LookupFullyQualifiedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:229
   */
  protected TypeDecl lookupLibraryType(String packageName, String typeName) {
    String fullName = packageName.equals("") ? typeName : packageName + "." + typeName;

    if (!libraryTypeMapInitialized) {
      initializeLibraryTypeMap();
      libraryTypeMapInitialized = true;
    }

    if (libraryTypeMap.containsKey(fullName)) {
      return libraryTypeMap.get(fullName);
    }

    // Lookup the type in the library class path.
    CompilationUnit libraryUnit = getLibCompilationUnit(fullName);

    // Add all types from the compilation unit in the library type map so that we can find them on
    // the next type lookup. If we don't do this lookup might incorrectly miss a type that is not
    // declared in a Java source file with a matching name.
    for (int j = 0; j < libraryUnit.getNumTypeDecl(); j++) {
      TypeDecl type = libraryUnit.getTypeDecl(j);
      if (!libraryTypeMap.containsKey(type.fullName())) {
        libraryTypeMap.put(type.fullName(), type);
      }
    }

    if (libraryTypeMap.containsKey(fullName)) {
      return libraryTypeMap.get(fullName);
    } else {
      libraryTypeMap.put(fullName, unknownType());
      return unknownType();
    }
  }
  /** Initialize primitive types in the library type map.  
   * @aspect LookupFullyQualifiedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:263
   */
  private void initializeLibraryTypeMap() {
      PrimitiveCompilationUnit unit = getPrimitiveCompilationUnit();
      libraryTypeMap.put(PRIMITIVE_PACKAGE_NAME + ".boolean", unit.typeBoolean());
      libraryTypeMap.put(PRIMITIVE_PACKAGE_NAME + ".byte", unit.typeByte());
      libraryTypeMap.put(PRIMITIVE_PACKAGE_NAME + ".short", unit.typeShort());
      libraryTypeMap.put(PRIMITIVE_PACKAGE_NAME + ".char", unit.typeChar());
      libraryTypeMap.put(PRIMITIVE_PACKAGE_NAME + ".int", unit.typeInt());
      libraryTypeMap.put(PRIMITIVE_PACKAGE_NAME + ".long", unit.typeLong());
      libraryTypeMap.put(PRIMITIVE_PACKAGE_NAME + ".float", unit.typeFloat());
      libraryTypeMap.put(PRIMITIVE_PACKAGE_NAME + ".double", unit.typeDouble());
      libraryTypeMap.put(PRIMITIVE_PACKAGE_NAME + ".null", unit.typeNull());
      libraryTypeMap.put(PRIMITIVE_PACKAGE_NAME + ".void", unit.typeVoid());
      libraryTypeMap.put(PRIMITIVE_PACKAGE_NAME + ".Unknown", unit.unknownType());
  }
  /**
   * @aspect PrettyPrintUtil
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\PrettyPrintUtil.jrag:79
   */
  public void prettyPrint(PrettyPrinter out) {
    for (Iterator iter = compilationUnitIterator(); iter.hasNext(); ) {
      CompilationUnit cu = (CompilationUnit) iter.next();
      if (cu.fromSource()) {
        out.print(cu);
      }
    }
  }
  /**
   * @declaredat ASTNode:1
   */
  public Program() {
    super();
    is$Final(true);
  }
  /**
   * Initializes the child array to the correct size.
   * Initializes List and Opt nta children.
   * @apilevel internal
   * @ast method
   * @declaredat ASTNode:11
   */
  public void init$Children() {
    children = new ASTNode[1];
    setChild(new List(), 0);
  }
  /**
   * @declaredat ASTNode:15
   */
  public Program(List<CompilationUnit> p0) {
    setChild(p0, 0);
    is$Final(true);
  }
  /**
   * @apilevel low-level
   * @declaredat ASTNode:22
   */
  protected int numChildren() {
    return 1;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:28
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:34
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    getCompilationUnit_String_reset();
    typeObject_reset();
    typeCloneable_reset();
    typeSerializable_reset();
    typeBoolean_reset();
    typeByte_reset();
    typeShort_reset();
    typeChar_reset();
    typeInt_reset();
    typeLong_reset();
    typeFloat_reset();
    typeDouble_reset();
    typeString_reset();
    typeVoid_reset();
    typeNull_reset();
    unknownType_reset();
    hasPackage_String_reset();
    lookupType_String_String_reset();
    getLibCompilationUnit_String_reset();
    emptyCompilationUnit_reset();
    getPrimitiveCompilationUnit_reset();
    unknownConstructor_reset();
    wildcards_reset();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:63
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
    collect_contributors_BlockLambdaBody_lambdaReturns = false;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:70
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:76
   */
  public Program clone() throws CloneNotSupportedException {
    Program node = (Program) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:83
   */
  public Program copy() {
    try {
      Program node = (Program) clone();
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
   * @declaredat ASTNode:102
   */
  @Deprecated
  public Program fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:112
   */
  public Program treeCopyNoTransform() {
    Program tree = (Program) copy();
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
   * @declaredat ASTNode:132
   */
  public Program treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:139
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node);    
  }
  /**
   * Replaces the CompilationUnit list.
   * @param list The new list node to be used as the CompilationUnit list.
   * @apilevel high-level
   */
  public void setCompilationUnitList(List<CompilationUnit> list) {
    setChild(list, 0);
  }
  /**
   * Retrieves the number of children in the CompilationUnit list.
   * @return Number of children in the CompilationUnit list.
   * @apilevel high-level
   */
  public int getNumCompilationUnit() {
    return getCompilationUnitList().getNumChild();
  }
  /**
   * Retrieves the number of children in the CompilationUnit list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the CompilationUnit list.
   * @apilevel low-level
   */
  public int getNumCompilationUnitNoTransform() {
    return getCompilationUnitListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the CompilationUnit list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the CompilationUnit list.
   * @apilevel high-level
   */
  public CompilationUnit getCompilationUnit(int i) {
    return (CompilationUnit) getCompilationUnitList().getChild(i);
  }
  /**
   * Check whether the CompilationUnit list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasCompilationUnit() {
    return getCompilationUnitList().getNumChild() != 0;
  }
  /**
   * Append an element to the CompilationUnit list.
   * @param node The element to append to the CompilationUnit list.
   * @apilevel high-level
   */
  public void addCompilationUnit(CompilationUnit node) {
    List<CompilationUnit> list = (parent == null) ? getCompilationUnitListNoTransform() : getCompilationUnitList();
    list.addChild(node);
  }
  /**
   * @apilevel low-level
   */
  public void addCompilationUnitNoTransform(CompilationUnit node) {
    List<CompilationUnit> list = getCompilationUnitListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the CompilationUnit list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setCompilationUnit(CompilationUnit node, int i) {
    List<CompilationUnit> list = getCompilationUnitList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the CompilationUnit list.
   * @return The node representing the CompilationUnit list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="CompilationUnit")
  public List<CompilationUnit> getCompilationUnitList() {
    List<CompilationUnit> list = (List<CompilationUnit>) getChild(0);
    return list;
  }
  /**
   * Retrieves the CompilationUnit list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the CompilationUnit list.
   * @apilevel low-level
   */
  public List<CompilationUnit> getCompilationUnitListNoTransform() {
    return (List<CompilationUnit>) getChildNoTransform(0);
  }
  /**
   * Retrieves the CompilationUnit list.
   * @return The node representing the CompilationUnit list.
   * @apilevel high-level
   */
  public List<CompilationUnit> getCompilationUnits() {
    return getCompilationUnitList();
  }
  /**
   * Retrieves the CompilationUnit list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the CompilationUnit list.
   * @apilevel low-level
   */
  public List<CompilationUnit> getCompilationUnitsNoTransform() {
    return getCompilationUnitListNoTransform();
  }
  /**
   * @aspect <NoAspect>
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\LambdaBody.jrag:47
   */
    private boolean collect_contributors_BlockLambdaBody_lambdaReturns = false;
  protected void collect_contributors_BlockLambdaBody_lambdaReturns() {
    if (collect_contributors_BlockLambdaBody_lambdaReturns) return;
    super.collect_contributors_BlockLambdaBody_lambdaReturns();
    collect_contributors_BlockLambdaBody_lambdaReturns = true;
  }

  /**
   * @apilevel internal
   */
  protected java.util.Map getCompilationUnit_String_values;
  /**
   * @apilevel internal
   */
  private void getCompilationUnit_String_reset() {
    getCompilationUnit_String_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public CompilationUnit getCompilationUnit(String typeName) {
    Object _parameters = typeName;
    if (getCompilationUnit_String_values == null) getCompilationUnit_String_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (getCompilationUnit_String_values.containsKey(_parameters)) {
      return (CompilationUnit) getCompilationUnit_String_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    CompilationUnit getCompilationUnit_String_value = classPath.getCompilationUnit(typeName, emptyCompilationUnit());
    if (isFinal && num == state().boundariesCrossed) {
      getCompilationUnit_String_values.put(_parameters, getCompilationUnit_String_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return getCompilationUnit_String_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean typeObject_computed = false;
  /**
   * @apilevel internal
   */
  protected TypeDecl typeObject_value;
  /**
   * @apilevel internal
   */
  private void typeObject_reset() {
    typeObject_computed = false;
    typeObject_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public TypeDecl typeObject() {
    ASTNode$State state = state();
    if (typeObject_computed) {
      return typeObject_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    typeObject_value = lookupType("java.lang", "Object");
    if (isFinal && num == state().boundariesCrossed) {
      typeObject_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return typeObject_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean typeCloneable_computed = false;
  /**
   * @apilevel internal
   */
  protected TypeDecl typeCloneable_value;
  /**
   * @apilevel internal
   */
  private void typeCloneable_reset() {
    typeCloneable_computed = false;
    typeCloneable_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public TypeDecl typeCloneable() {
    ASTNode$State state = state();
    if (typeCloneable_computed) {
      return typeCloneable_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    typeCloneable_value = lookupType("java.lang", "Cloneable");
    if (isFinal && num == state().boundariesCrossed) {
      typeCloneable_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return typeCloneable_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean typeSerializable_computed = false;
  /**
   * @apilevel internal
   */
  protected TypeDecl typeSerializable_value;
  /**
   * @apilevel internal
   */
  private void typeSerializable_reset() {
    typeSerializable_computed = false;
    typeSerializable_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public TypeDecl typeSerializable() {
    ASTNode$State state = state();
    if (typeSerializable_computed) {
      return typeSerializable_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    typeSerializable_value = lookupType("java.io", "Serializable");
    if (isFinal && num == state().boundariesCrossed) {
      typeSerializable_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return typeSerializable_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean typeBoolean_computed = false;
  /**
   * @apilevel internal
   */
  protected TypeDecl typeBoolean_value;
  /**
   * @apilevel internal
   */
  private void typeBoolean_reset() {
    typeBoolean_computed = false;
    typeBoolean_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public TypeDecl typeBoolean() {
    ASTNode$State state = state();
    if (typeBoolean_computed) {
      return typeBoolean_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    typeBoolean_value = getPrimitiveCompilationUnit().typeBoolean();
    if (isFinal && num == state().boundariesCrossed) {
      typeBoolean_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return typeBoolean_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean typeByte_computed = false;
  /**
   * @apilevel internal
   */
  protected TypeDecl typeByte_value;
  /**
   * @apilevel internal
   */
  private void typeByte_reset() {
    typeByte_computed = false;
    typeByte_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public TypeDecl typeByte() {
    ASTNode$State state = state();
    if (typeByte_computed) {
      return typeByte_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    typeByte_value = getPrimitiveCompilationUnit().typeByte();
    if (isFinal && num == state().boundariesCrossed) {
      typeByte_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return typeByte_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean typeShort_computed = false;
  /**
   * @apilevel internal
   */
  protected TypeDecl typeShort_value;
  /**
   * @apilevel internal
   */
  private void typeShort_reset() {
    typeShort_computed = false;
    typeShort_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public TypeDecl typeShort() {
    ASTNode$State state = state();
    if (typeShort_computed) {
      return typeShort_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    typeShort_value = getPrimitiveCompilationUnit().typeShort();
    if (isFinal && num == state().boundariesCrossed) {
      typeShort_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return typeShort_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean typeChar_computed = false;
  /**
   * @apilevel internal
   */
  protected TypeDecl typeChar_value;
  /**
   * @apilevel internal
   */
  private void typeChar_reset() {
    typeChar_computed = false;
    typeChar_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public TypeDecl typeChar() {
    ASTNode$State state = state();
    if (typeChar_computed) {
      return typeChar_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    typeChar_value = getPrimitiveCompilationUnit().typeChar();
    if (isFinal && num == state().boundariesCrossed) {
      typeChar_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return typeChar_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean typeInt_computed = false;
  /**
   * @apilevel internal
   */
  protected TypeDecl typeInt_value;
  /**
   * @apilevel internal
   */
  private void typeInt_reset() {
    typeInt_computed = false;
    typeInt_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public TypeDecl typeInt() {
    ASTNode$State state = state();
    if (typeInt_computed) {
      return typeInt_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    typeInt_value = getPrimitiveCompilationUnit().typeInt();
    if (isFinal && num == state().boundariesCrossed) {
      typeInt_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return typeInt_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean typeLong_computed = false;
  /**
   * @apilevel internal
   */
  protected TypeDecl typeLong_value;
  /**
   * @apilevel internal
   */
  private void typeLong_reset() {
    typeLong_computed = false;
    typeLong_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public TypeDecl typeLong() {
    ASTNode$State state = state();
    if (typeLong_computed) {
      return typeLong_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    typeLong_value = getPrimitiveCompilationUnit().typeLong();
    if (isFinal && num == state().boundariesCrossed) {
      typeLong_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return typeLong_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean typeFloat_computed = false;
  /**
   * @apilevel internal
   */
  protected TypeDecl typeFloat_value;
  /**
   * @apilevel internal
   */
  private void typeFloat_reset() {
    typeFloat_computed = false;
    typeFloat_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public TypeDecl typeFloat() {
    ASTNode$State state = state();
    if (typeFloat_computed) {
      return typeFloat_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    typeFloat_value = getPrimitiveCompilationUnit().typeFloat();
    if (isFinal && num == state().boundariesCrossed) {
      typeFloat_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return typeFloat_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean typeDouble_computed = false;
  /**
   * @apilevel internal
   */
  protected TypeDecl typeDouble_value;
  /**
   * @apilevel internal
   */
  private void typeDouble_reset() {
    typeDouble_computed = false;
    typeDouble_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public TypeDecl typeDouble() {
    ASTNode$State state = state();
    if (typeDouble_computed) {
      return typeDouble_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    typeDouble_value = getPrimitiveCompilationUnit().typeDouble();
    if (isFinal && num == state().boundariesCrossed) {
      typeDouble_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return typeDouble_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean typeString_computed = false;
  /**
   * @apilevel internal
   */
  protected TypeDecl typeString_value;
  /**
   * @apilevel internal
   */
  private void typeString_reset() {
    typeString_computed = false;
    typeString_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public TypeDecl typeString() {
    ASTNode$State state = state();
    if (typeString_computed) {
      return typeString_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    typeString_value = lookupType("java.lang", "String");
    if (isFinal && num == state().boundariesCrossed) {
      typeString_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return typeString_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean typeVoid_computed = false;
  /**
   * @apilevel internal
   */
  protected TypeDecl typeVoid_value;
  /**
   * @apilevel internal
   */
  private void typeVoid_reset() {
    typeVoid_computed = false;
    typeVoid_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public TypeDecl typeVoid() {
    ASTNode$State state = state();
    if (typeVoid_computed) {
      return typeVoid_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    typeVoid_value = getPrimitiveCompilationUnit().typeVoid();
    if (isFinal && num == state().boundariesCrossed) {
      typeVoid_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return typeVoid_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean typeNull_computed = false;
  /**
   * @apilevel internal
   */
  protected TypeDecl typeNull_value;
  /**
   * @apilevel internal
   */
  private void typeNull_reset() {
    typeNull_computed = false;
    typeNull_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public TypeDecl typeNull() {
    ASTNode$State state = state();
    if (typeNull_computed) {
      return typeNull_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    typeNull_value = getPrimitiveCompilationUnit().typeNull();
    if (isFinal && num == state().boundariesCrossed) {
      typeNull_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return typeNull_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean unknownType_computed = false;
  /**
   * @apilevel internal
   */
  protected TypeDecl unknownType_value;
  /**
   * @apilevel internal
   */
  private void unknownType_reset() {
    unknownType_computed = false;
    unknownType_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public TypeDecl unknownType() {
    ASTNode$State state = state();
    if (unknownType_computed) {
      return unknownType_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    unknownType_value = getPrimitiveCompilationUnit().unknownType();
    if (isFinal && num == state().boundariesCrossed) {
      unknownType_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return unknownType_value;
  }
  /**
   * @apilevel internal
   */
  protected java.util.Map hasPackage_String_values;
  /**
   * @apilevel internal
   */
  private void hasPackage_String_reset() {
    hasPackage_String_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean hasPackage(String packageName) {
    Object _parameters = packageName;
    if (hasPackage_String_values == null) hasPackage_String_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (hasPackage_String_values.containsKey(_parameters)) {
      return (Boolean) hasPackage_String_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean hasPackage_String_value = isPackage(packageName);
    if (isFinal && num == state().boundariesCrossed) {
      hasPackage_String_values.put(_parameters, hasPackage_String_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return hasPackage_String_value;
  }
  /**
   * @apilevel internal
   */
  protected java.util.Map lookupType_String_String_values;
  /**
   * @apilevel internal
   */
  private void lookupType_String_String_reset() {
    lookupType_String_String_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public TypeDecl lookupType(String packageName, String typeName) {
    java.util.List _parameters = new java.util.ArrayList(2);
    _parameters.add(packageName);
    _parameters.add(typeName);
    if (lookupType_String_String_values == null) lookupType_String_String_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (lookupType_String_String_values.containsKey(_parameters)) {
      return (TypeDecl) lookupType_String_String_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    TypeDecl lookupType_String_String_value = lookupType_compute(packageName, typeName);
    if (isFinal && num == state().boundariesCrossed) {
      lookupType_String_String_values.put(_parameters, lookupType_String_String_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return lookupType_String_String_value;
  }
  /**
   * @apilevel internal
   */
  private TypeDecl lookupType_compute(String packageName, String typeName) {
      // Look for a matching source type.
      TypeDecl sourceType = lookupSourceType(packageName, typeName);
      if (!sourceType.isUnknown()) {
        return sourceType;
      }
  
      // Look for a matching library type.
      return lookupLibraryType(packageName, typeName);
    }
  /**
   * @apilevel internal
   */
  protected List getLibCompilationUnit_String_list;
  /**
   * @apilevel internal
   */
  protected java.util.Map getLibCompilationUnit_String_values;
  /**
   * @apilevel internal
   */
  private void getLibCompilationUnit_String_reset() {
    getLibCompilationUnit_String_values = null;
    getLibCompilationUnit_String_list = null;
  }
  @ASTNodeAnnotation.Attribute
  public CompilationUnit getLibCompilationUnit(String typeName) {
    Object _parameters = typeName;
    if (getLibCompilationUnit_String_values == null) getLibCompilationUnit_String_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State state = state();
    if (getLibCompilationUnit_String_values.containsKey(_parameters)) {
      return (CompilationUnit) getLibCompilationUnit_String_values.get(_parameters);
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    CompilationUnit getLibCompilationUnit_String_value = getCompilationUnit(typeName);
    if (getLibCompilationUnit_String_list == null) {
      getLibCompilationUnit_String_list = new List();
      getLibCompilationUnit_String_list.is$Final = true;
      getLibCompilationUnit_String_list.setParent(this);
    }
    getLibCompilationUnit_String_list.add(getLibCompilationUnit_String_value);
    if (getLibCompilationUnit_String_value != null) {
      getLibCompilationUnit_String_value = (CompilationUnit) getLibCompilationUnit_String_list.getChild(getLibCompilationUnit_String_list.numChildren-1);
      getLibCompilationUnit_String_value.is$Final = true;
    }
    if (true) {
      getLibCompilationUnit_String_values.put(_parameters, getLibCompilationUnit_String_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return getLibCompilationUnit_String_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean emptyCompilationUnit_computed = false;
  /**
   * @apilevel internal
   */
  protected CompilationUnit emptyCompilationUnit_value;
  /**
   * @apilevel internal
   */
  private void emptyCompilationUnit_reset() {
    emptyCompilationUnit_computed = false;
    emptyCompilationUnit_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public CompilationUnit emptyCompilationUnit() {
    ASTNode$State state = state();
    if (emptyCompilationUnit_computed) {
      return emptyCompilationUnit_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    emptyCompilationUnit_value = new CompilationUnit();
    emptyCompilationUnit_value.setParent(this);
    emptyCompilationUnit_value.is$Final = true;
    if (true) {
      emptyCompilationUnit_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return emptyCompilationUnit_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean getPrimitiveCompilationUnit_computed = false;
  /**
   * @apilevel internal
   */
  protected PrimitiveCompilationUnit getPrimitiveCompilationUnit_value;
  /**
   * @apilevel internal
   */
  private void getPrimitiveCompilationUnit_reset() {
    getPrimitiveCompilationUnit_computed = false;
    getPrimitiveCompilationUnit_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public PrimitiveCompilationUnit getPrimitiveCompilationUnit() {
    ASTNode$State state = state();
    if (getPrimitiveCompilationUnit_computed) {
      return getPrimitiveCompilationUnit_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    getPrimitiveCompilationUnit_value = getPrimitiveCompilationUnit_compute();
    getPrimitiveCompilationUnit_value.setParent(this);
    getPrimitiveCompilationUnit_value.is$Final = true;
    if (true) {
      getPrimitiveCompilationUnit_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return getPrimitiveCompilationUnit_value;
  }
  /**
   * @apilevel internal
   */
  private PrimitiveCompilationUnit getPrimitiveCompilationUnit_compute() {
      PrimitiveCompilationUnit u = new PrimitiveCompilationUnit();
      u.setPackageDecl(PRIMITIVE_PACKAGE_NAME);
      return u;
    }
  /**
   * @apilevel internal
   */
  protected boolean unknownConstructor_computed = false;
  /**
   * @apilevel internal
   */
  protected ConstructorDecl unknownConstructor_value;
  /**
   * @apilevel internal
   */
  private void unknownConstructor_reset() {
    unknownConstructor_computed = false;
    unknownConstructor_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public ConstructorDecl unknownConstructor() {
    ASTNode$State state = state();
    if (unknownConstructor_computed) {
      return unknownConstructor_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    unknownConstructor_value = unknownConstructor_compute();
    if (isFinal && num == state().boundariesCrossed) {
      unknownConstructor_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return unknownConstructor_value;
  }
  /**
   * @apilevel internal
   */
  private ConstructorDecl unknownConstructor_compute() {
      return (ConstructorDecl) unknownType().constructors().iterator().next();
    }
  /**
   * @apilevel internal
   */
  protected boolean wildcards_computed = false;
  /**
   * @apilevel internal
   */
  protected WildcardsCompilationUnit wildcards_value;
  /**
   * @apilevel internal
   */
  private void wildcards_reset() {
    wildcards_computed = false;
    wildcards_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public WildcardsCompilationUnit wildcards() {
    ASTNode$State state = state();
    if (wildcards_computed) {
      return wildcards_value;
    }
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    wildcards_value = wildcards_compute();
    wildcards_value.setParent(this);
    wildcards_value.is$Final = true;
    if (true) {
      wildcards_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return wildcards_value;
  }
  /**
   * @apilevel internal
   */
  private WildcardsCompilationUnit wildcards_compute() {
      return new WildcardsCompilationUnit(
        "wildcards",
        new List(),
        new List()
      );
    }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\Options.jadd:39
   * @apilevel internal
   */
  public Program Define_program(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return this;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\AnonymousClasses.jrag:33
   * @apilevel internal
   */
  public TypeDecl Define_superType(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return null;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\AnonymousClasses.jrag:37
   * @apilevel internal
   */
  public ConstructorDecl Define_constructorDecl(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return unknownConstructor();
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\Arrays.jrag:42
   * @apilevel internal
   */
  public TypeDecl Define_componentType(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return unknownType();
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\BranchTarget.jrag:250
   * @apilevel internal
   */
  public LabeledStmt Define_lookupLabel(ASTNode caller, ASTNode child, String name) {
    int childIndex = this.getIndexOfChild(caller);
    return null;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ClassPath.jrag:79
   * @apilevel internal
   */
  public CompilationUnit Define_compilationUnit(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return null;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DeclareBeforeUse.jrag:34
   * @apilevel internal
   */
  public int Define_blockIndex(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return -1;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:37
   * @apilevel internal
   */
  public boolean Define_isDest(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:47
   * @apilevel internal
   */
  public boolean Define_isSource(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:71
   * @apilevel internal
   */
  public boolean Define_isIncOrDec(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:255
   * @apilevel internal
   */
  public boolean Define_isDAbefore(ASTNode caller, ASTNode child, Variable v) {
    int childIndex = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:779
   * @apilevel internal
   */
  public boolean Define_isDUbefore(ASTNode caller, ASTNode child, Variable v) {
    int childIndex = this.getIndexOfChild(caller);
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ExceptionHandling.jrag:41
   * @apilevel internal
   */
  public TypeDecl Define_typeException(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return lookupType("java.lang", "Exception");
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\TryWithResources.jrag:141
   * @apilevel internal
   */
  public TypeDecl Define_typeRuntimeException(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return lookupType("java.lang", "RuntimeException");
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\TryWithResources.jrag:140
   * @apilevel internal
   */
  public TypeDecl Define_typeError(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return lookupType("java.lang", "Error");
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\ExceptionHandling.jrag:47
   * @apilevel internal
   */
  public TypeDecl Define_typeNullPointerException(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return lookupType("java.lang", "NullPointerException");
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:93
   * @apilevel internal
   */
  public TypeDecl Define_typeThrowable(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return lookupType("java.lang", "Throwable");
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\TryWithResources.jrag:113
   * @apilevel internal
   */
  public boolean Define_handlesException(ASTNode caller, ASTNode child, TypeDecl exceptionType) {
    int childIndex = this.getIndexOfChild(caller);
    {
        throw new Error("Operation handlesException not supported");
      }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupConstructor.jrag:35
   * @apilevel internal
   */
  public Collection Define_lookupConstructor(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return Collections.EMPTY_LIST;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupConstructor.jrag:40
   * @apilevel internal
   */
  public Collection Define_lookupSuperConstructor(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return Collections.EMPTY_LIST;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupMethod.jrag:40
   * @apilevel internal
   */
  public Expr Define_nestedScope(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    { throw new UnsupportedOperationException(); }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupMethod.jrag:46
   * @apilevel internal
   */
  public Collection Define_lookupMethod(ASTNode caller, ASTNode child, String name) {
    int childIndex = this.getIndexOfChild(caller);
    return Collections.EMPTY_LIST;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:1092
   * @apilevel internal
   */
  public TypeDecl Define_typeObject(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return typeObject();
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeAnalysis.jrag:162
   * @apilevel internal
   */
  public TypeDecl Define_typeCloneable(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return typeCloneable();
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeAnalysis.jrag:161
   * @apilevel internal
   */
  public TypeDecl Define_typeSerializable(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return typeSerializable();
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:74
   * @apilevel internal
   */
  public TypeDecl Define_typeBoolean(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return typeBoolean();
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:75
   * @apilevel internal
   */
  public TypeDecl Define_typeByte(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return typeByte();
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:76
   * @apilevel internal
   */
  public TypeDecl Define_typeShort(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return typeShort();
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:77
   * @apilevel internal
   */
  public TypeDecl Define_typeChar(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return typeChar();
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:86
   * @apilevel internal
   */
  public TypeDecl Define_typeInt(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return typeInt();
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:88
   * @apilevel internal
   */
  public TypeDecl Define_typeLong(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return typeLong();
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:80
   * @apilevel internal
   */
  public TypeDecl Define_typeFloat(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return typeFloat();
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:81
   * @apilevel internal
   */
  public TypeDecl Define_typeDouble(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return typeDouble();
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Enums.jrag:466
   * @apilevel internal
   */
  public TypeDecl Define_typeString(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return typeString();
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:83
   * @apilevel internal
   */
  public TypeDecl Define_typeVoid(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return typeVoid();
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:1141
   * @apilevel internal
   */
  public TypeDecl Define_typeNull(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return typeNull();
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TypeCheck.jrag:31
   * @apilevel internal
   */
  public TypeDecl Define_unknownType(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return unknownType();
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupType.jrag:115
   * @apilevel internal
   */
  public boolean Define_hasPackage(ASTNode caller, ASTNode child, String packageName) {
    int childIndex = this.getIndexOfChild(caller);
    return hasPackage(packageName);
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\TryWithResources.jrag:40
   * @apilevel internal
   */
  public TypeDecl Define_lookupType(ASTNode caller, ASTNode child, String packageName, String typeName) {
    int childIndex = this.getIndexOfChild(caller);
    return lookupType(packageName, typeName);
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\GenericMethods.jrag:197
   * @apilevel internal
   */
  public SimpleSet Define_lookupType(ASTNode caller, ASTNode child, String name) {
    int childIndex = this.getIndexOfChild(caller);
    return SimpleSet.emptySet;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\LookupVariable.jrag:30
   * @apilevel internal
   */
  public SimpleSet Define_lookupVariable(ASTNode caller, ASTNode child, String name) {
    int childIndex = this.getIndexOfChild(caller);
    return SimpleSet.emptySet;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\Modifiers.jrag:419
   * @apilevel internal
   */
  public boolean Define_mayBePublic(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\Modifiers.jrag:421
   * @apilevel internal
   */
  public boolean Define_mayBeProtected(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\Modifiers.jrag:420
   * @apilevel internal
   */
  public boolean Define_mayBePrivate(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\Modifiers.jrag:422
   * @apilevel internal
   */
  public boolean Define_mayBeStatic(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\Modifiers.jrag:423
   * @apilevel internal
   */
  public boolean Define_mayBeFinal(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\Modifiers.jrag:424
   * @apilevel internal
   */
  public boolean Define_mayBeAbstract(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\Modifiers.jrag:425
   * @apilevel internal
   */
  public boolean Define_mayBeVolatile(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\Modifiers.jrag:426
   * @apilevel internal
   */
  public boolean Define_mayBeTransient(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\Modifiers.jrag:427
   * @apilevel internal
   */
  public boolean Define_mayBeStrictfp(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\Modifiers.jrag:428
   * @apilevel internal
   */
  public boolean Define_mayBeSynchronized(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\Modifiers.jrag:429
   * @apilevel internal
   */
  public boolean Define_mayBeNative(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\NameCheck.jrag:310
   * @apilevel internal
   */
  public ASTNode Define_enclosingBlock(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return null;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\NameCheck.jrag:30
   * @apilevel internal
   */
  public VariableScope Define_outerScope(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    {
        throw new UnsupportedOperationException("outerScope() not defined");
      }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\NameCheck.jrag:441
   * @apilevel internal
   */
  public boolean Define_insideLoop(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\NameCheck.jrag:449
   * @apilevel internal
   */
  public boolean Define_insideSwitch(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\NameCheck.jrag:496
   * @apilevel internal
   */
  public Case Define_bind(ASTNode caller, ASTNode child, Case c) {
    int childIndex = this.getIndexOfChild(caller);
    return null;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\SyntacticClassification.jrag:36
   * @apilevel internal
   */
  public NameType Define_nameType(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return NameType.NOT_CLASSIFIED;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeAnalysis.jrag:240
   * @apilevel internal
   */
  public boolean Define_isAnonymous(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\LookupVariable.jrag:334
   * @apilevel internal
   */
  public Variable Define_unknownField(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return unknownType().findSingleVariable("unknown");
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\MethodReference.jrag:30
   * @apilevel internal
   */
  public MethodDecl Define_unknownMethod(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    {
        for (Iterator iter = unknownType().memberMethods("unknown").iterator(); iter.hasNext(); ) {
          MethodDecl m = (MethodDecl) iter.next();
          return m;
        }
        throw new Error("Could not find method unknown in type Unknown");
      }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\ConstructorReference.jrag:29
   * @apilevel internal
   */
  public ConstructorDecl Define_unknownConstructor(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return unknownConstructor();
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Annotations.jrag:601
   * @apilevel internal
   */
  public TypeDecl Define_declType(ASTNode caller, ASTNode child) {
    int i = this.getIndexOfChild(caller);
    return null;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\NameCheck.jrag:29
   * @apilevel internal
   */
  public BodyDecl Define_enclosingBodyDecl(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return null;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeAnalysis.jrag:577
   * @apilevel internal
   */
  public boolean Define_isMemberType(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\MultiCatch.jrag:71
   * @apilevel internal
   */
  public TypeDecl Define_hostType(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return null;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeCheck.jrag:420
   * @apilevel internal
   */
  public TypeDecl Define_switchType(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return unknownType();
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeCheck.jrag:472
   * @apilevel internal
   */
  public TypeDecl Define_returnType(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return typeVoid();
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeCheck.jrag:586
   * @apilevel internal
   */
  public TypeDecl Define_enclosingInstance(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return null;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:32
   * @apilevel internal
   */
  public String Define_methodHost(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    {
        throw new Error("Needs extra equation for methodHost()");
      }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:165
   * @apilevel internal
   */
  public boolean Define_inExplicitConstructorInvocation(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:173
   * @apilevel internal
   */
  public TypeDecl Define_enclosingExplicitConstructorHostType(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return null;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:182
   * @apilevel internal
   */
  public boolean Define_inStaticContext(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\PreciseRethrow.jrag:283
   * @apilevel internal
   */
  public boolean Define_reportUnreachable(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\MultiCatch.jrag:44
   * @apilevel internal
   */
  public boolean Define_isMethodParameter(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\MultiCatch.jrag:45
   * @apilevel internal
   */
  public boolean Define_isConstructorParameter(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\MultiCatch.jrag:46
   * @apilevel internal
   */
  public boolean Define_isExceptionHandlerParameter(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Annotations.jrag:96
   * @apilevel internal
   */
  public boolean Define_mayUseAnnotationTarget(ASTNode caller, ASTNode child, String name) {
    int childIndex = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Annotations.jrag:224
   * @apilevel internal
   */
  public ElementValue Define_lookupElementTypeValue(ASTNode caller, ASTNode child, String name) {
    int childIndex = this.getIndexOfChild(caller);
    return null;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\SuppressWarnings.jrag:38
   * @apilevel internal
   */
  public boolean Define_withinSuppressWarnings(ASTNode caller, ASTNode child, String annot) {
    int childIndex = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Annotations.jrag:449
   * @apilevel internal
   */
  public boolean Define_withinDeprecatedAnnotation(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Annotations.jrag:504
   * @apilevel internal
   */
  public Annotation Define_lookupAnnotation(ASTNode caller, ASTNode child, TypeDecl typeDecl) {
    int i = this.getIndexOfChild(caller);
    return null;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Annotations.jrag:544
   * @apilevel internal
   */
  public TypeDecl Define_enclosingAnnotationDecl(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return unknownType();
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\GenericMethodsInference.jrag:58
   * @apilevel internal
   */
  public TypeDecl Define_assignConvertedType(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return typeNull();
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:338
   * @apilevel internal
   */
  public boolean Define_inExtendsOrImplements(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:1480
   * @apilevel internal
   */
  public TypeDecl Define_typeWildcard(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return wildcards().typeWildcard();
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:1479
   * @apilevel internal
   */
  public TypeDecl Define_lookupWildcardExtends(ASTNode caller, ASTNode child, TypeDecl typeDecl) {
    int childIndex = this.getIndexOfChild(caller);
    return wildcards().lookupWildcardExtends(typeDecl);
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:1478
   * @apilevel internal
   */
  public TypeDecl Define_lookupWildcardSuper(ASTNode caller, ASTNode child, TypeDecl typeDecl) {
    int childIndex = this.getIndexOfChild(caller);
    return wildcards().lookupWildcardSuper(typeDecl);
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\MultiCatch.jrag:167
   * @apilevel internal
   */
  public LUBType Define_lookupLUBType(ASTNode caller, ASTNode child, Collection bounds) {
    int childIndex = this.getIndexOfChild(caller);
    return wildcards().lookupLUBType(bounds);
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:1580
   * @apilevel internal
   */
  public GLBType Define_lookupGLBType(ASTNode caller, ASTNode child, ArrayList bounds) {
    int childIndex = this.getIndexOfChild(caller);
    return wildcards().lookupGLBType(bounds);
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\GenericsParTypeDecl.jrag:71
   * @apilevel internal
   */
  public TypeDecl Define_genericDecl(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return null;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\VariableArityParameters.jrag:48
   * @apilevel internal
   */
  public boolean Define_variableArityValid(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Diamond.jrag:90
   * @apilevel internal
   */
  public ClassInstanceExpr Define_getClassInstanceExpr(ASTNode caller, ASTNode child) {
    int i = this.getIndexOfChild(caller);
    return null;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Diamond.jrag:401
   * @apilevel internal
   */
  public boolean Define_isAnonymousDecl(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\Diamond.jrag:417
   * @apilevel internal
   */
  public boolean Define_isExplicitGenericConstructorAccess(ASTNode caller, ASTNode child) {
    int i = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\PreciseRethrow.jrag:206
   * @apilevel internal
   */
  public boolean Define_isCatchParam(ASTNode caller, ASTNode child) {
    int i = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\PreciseRethrow.jrag:213
   * @apilevel internal
   */
  public CatchClause Define_catchClause(ASTNode caller, ASTNode child) {
    int i = this.getIndexOfChild(caller);
    {
        throw new IllegalStateException("Could not find parent " + "catch clause");
      }
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java7\\frontend\\TryWithResources.jrag:183
   * @apilevel internal
   */
  public boolean Define_resourcePreviouslyDeclared(ASTNode caller, ASTNode child, String name) {
    int i = this.getIndexOfChild(caller);
    return false;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\TargetType.jrag:30
   * @apilevel internal
   */
  public TypeDecl Define_targetType(ASTNode caller, ASTNode child) {
    int childIndex = this.getIndexOfChild(caller);
    return typeNull();
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
