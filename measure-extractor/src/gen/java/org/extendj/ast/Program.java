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
 * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\grammar\\Java.ast:1
 * @production Program : {@link ASTNode} ::= <span class="component">{@link CompilationUnit}*</span>;

 */
public class Program extends ASTNode<ASTNode> implements Cloneable {
  /**
   * Returns a robust iterator that can be iterated while the colleciton is updated.
   * @aspect LibraryCompilationUnits
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LibCompilationUnits.jadd:35
   */
  public Iterator<CompilationUnit> libraryCompilationUnitIterator() {
    return libraryCompilationUnitSet.iterator();
  }
  /**
   * @aspect AddOptionsToProgram
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Options.jadd:34
   */
  public Options options = new Options();
  /**
   * @aspect AddOptionsToProgram
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Options.jadd:36
   */
  public Options options() {
    return options;
  }
  /**
   * @aspect ClassPath
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ClassPath.jrag:56
   */
  protected BytecodeReader bytecodeReader = defaultBytecodeReader();
  /**
   * @aspect ClassPath
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ClassPath.jrag:58
   */
  public void initBytecodeReader(BytecodeReader r) {
    bytecodeReader = r;
  }
  /**
   * @aspect ClassPath
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ClassPath.jrag:62
   */
  public static BytecodeReader defaultBytecodeReader() {
    return new BytecodeReader() {
      @Override
      public CompilationUnit read(InputStream is, String fullName, Program p)
          throws FileNotFoundException, IOException {
        return new BytecodeParser(is, fullName).parse(null, null, p);
      }
    };
  }
  /**
   * @aspect ClassPath
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ClassPath.jrag:72
   */
  protected JavaParser javaParser = defaultJavaParser();
  /**
   * @aspect ClassPath
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ClassPath.jrag:74
   */
  public void initJavaParser(JavaParser p) {
    javaParser = p;
  }
  /**
   * @aspect ClassPath
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ClassPath.jrag:78
   */
  public static JavaParser defaultJavaParser() {
    return new JavaParser() {
      @Override
      public CompilationUnit parse(InputStream is, String fileName)
          throws IOException, beaver.Parser.Exception {
        return new org.extendj.parser.JavaParser().parse(is, fileName);
      }
    };
  }
  /**
   * Parse the source file and add the compilation unit to the list of
   * compilation units in the program.
   * 
   * @param fileName file name of the source file
   * @return The CompilationUnit representing the source file,
   * or <code>null</code> if the source file could not be parsed
   * @aspect ClassPath
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ClassPath.jrag:119
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
   * Creates an iterator to iterate over compilation units parsed from source files.
   * @aspect ClassPath
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ClassPath.jrag:132
   */
  public Iterator<CompilationUnit> compilationUnitIterator() {
    return new Iterator<CompilationUnit>() {
      int index = 0;

      @Override
      public boolean hasNext() {
        return index < getNumCompilationUnit();
      }

      @Override
      public CompilationUnit next() {
        if (getNumCompilationUnit() == index) {
          throw new java.util.NoSuchElementException();
        }
        return getCompilationUnit(index++);
      }

      @Override
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
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ClassPath.jrag:161
   */
  public InputStream getInputStream(String name) {
    return classPath.getInputStream(name);
  }
  /**
   * @aspect ClassPath
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ClassPath.jrag:165
   */
  private final ClassPath classPath = new ClassPath(this);
  /**
   * @return <code>true</code> if there is a package with the given name on
   * the classpath
   * @aspect ClassPath
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ClassPath.jrag:431
   */
  public boolean isPackage(String packageName) {
    return classPath.isPackage(packageName);
  }
  /**
   * Add a path part to the library class path.
   * @aspect ClassPath
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ClassPath.jrag:454
   */
  public void addClassPath(PathPart pathPart) {
    classPath.addClassPath(pathPart);
  }
  /**
   * Add a path part to the user class path.
   * @aspect ClassPath
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ClassPath.jrag:461
   */
  public void addSourcePath(PathPart pathPart) {
    classPath.addSourcePath(pathPart);
  }
  /**
   * @aspect FrontendMain
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\FrontendMain.jrag:37
   */
  public long javaParseTime;
  /**
   * @aspect FrontendMain
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\FrontendMain.jrag:38
   */
  public long bytecodeParseTime;
  /**
   * @aspect FrontendMain
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\FrontendMain.jrag:39
   */
  public long codeGenTime;
  /**
   * @aspect FrontendMain
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\FrontendMain.jrag:40
   */
  public long errorCheckTime;
  /**
   * @aspect FrontendMain
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\FrontendMain.jrag:41
   */
  public int numJavaFiles;
  /**
   * @aspect FrontendMain
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\FrontendMain.jrag:42
   */
  public int numClassFiles;
  /**
   * Reset the profile statistics.
   * @aspect FrontendMain
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\FrontendMain.jrag:47
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
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\FrontendMain.jrag:56
   */
  public void printStatistics(PrintStream out) {
    out.println("javaParseTime: " + javaParseTime);
    out.println("numJavaFiles: " + numJavaFiles);
    out.println("bytecodeParseTime: " + bytecodeParseTime);
    out.println("numClassFiles: " + numClassFiles);
    out.println("errorCheckTime: " + errorCheckTime);
    out.println("codeGenTime: " + codeGenTime);
  }
  /**
   * @aspect LookupFullyQualifiedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:133
   */
  public int classFileReadTime;
  /**
   * Extra cache for source type lookups. This cache is important in order to
   * make all source types shadow library types with matching names, even when
   * the source type lives in a compilation unit with a different simple name.
   * @aspect LookupFullyQualifiedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:162
   */
  private final Map<String, TypeDecl> sourceTypeMap = new HashMap<String, TypeDecl>();
  /**
   * @aspect LookupFullyQualifiedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:164
   */
  private boolean sourceTypeMapInitialized = false;
  /**
   * Lookup a type among source classes.
   * 
   * <p>Invoking this method may cause more than just the specified type to be
   * loaded, for example if there exists other types in the same source file,
   * the additional types are also loaded and cached for the next lookup.
   * 
   * <p>This method is not an attribute due to the necessary side-effects
   * caused by loading and caching of extra types.
   * @aspect LookupFullyQualifiedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:176
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
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:198
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
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:215
   */
  private final Map<String, TypeDecl> libraryTypeMap = new HashMap<String, TypeDecl>();
  /**
   * @aspect LookupFullyQualifiedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:217
   */
  private final Set<CompilationUnit> libraryCompilationUnitSet =
      new RobustSet<CompilationUnit>(new HashSet<CompilationUnit>());
  /**
   * @aspect LookupFullyQualifiedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:220
   */
  private boolean libraryTypeMapInitialized = false;
  /**
   * Lookup a type among library classes. The lookup includes Jar and source files.
   * 
   * <p>Invoking this method may cause more than just the specified type to be loaded, for
   * example if there exists other types in the same source file, the additional
   * types are also loaded and cached for the next lookup.
   * 
   * <p>This method is not an attribute due to the necessary side-effects caused
   * by loading and caching of extra types.
   * @aspect LookupFullyQualifiedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:232
   */
  protected TypeDecl lookupLibraryType(String packageName, String typeName) {
    String fullName = packageName.isEmpty() ? typeName : packageName + "." + typeName;

    if (!libraryTypeMapInitialized) {
      initializeLibraryTypeMap();
      libraryTypeMapInitialized = true;
    }

    if (libraryTypeMap.containsKey(fullName)) {
      return libraryTypeMap.get(fullName);
    }

    // Lookup the type in the library class path.
    CompilationUnit libraryUnit = getLibCompilationUnit(fullName);

    // Store the compilation unit in a set for later introspection of loaded compilation units.
    libraryCompilationUnitSet.add(libraryUnit);

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
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:269
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
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\PrettyPrintUtil.jrag:77
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
  }
  /**
   * Initializes the child array to the correct size.
   * Initializes List and Opt nta children.
   * @apilevel internal
   * @ast method
   * @declaredat ASTNode:10
   */
  public void init$Children() {
    children = new ASTNode[1];
    setChild(new List(), 0);
  }
  /**
   * @declaredat ASTNode:14
   */
  public Program(List<CompilationUnit> p0) {
    setChild(p0, 0);
  }
  /** @apilevel low-level 
   * @declaredat ASTNode:18
   */
  protected int numChildren() {
    return 1;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:24
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:28
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
  /** @apilevel internal 
   * @declaredat ASTNode:55
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
    Program_extractedBranches_computed = null;
    Program_extractedBranches_value = null;
    Program_extractedClassDeclarations_computed = null;
    Program_extractedClassDeclarations_value = null;
    Program_extractedDataTypes_computed = null;
    Program_extractedDataTypes_value = null;
    Program_extractedForeignMethodCalls_computed = null;
    Program_extractedForeignMethodCalls_value = null;
    Program_extractedGlobalVariables_computed = null;
    Program_extractedGlobalVariables_value = null;
    Program_extractedIfStatements_computed = null;
    Program_extractedIfStatements_value = null;
    Program_extractedImports_computed = null;
    Program_extractedImports_value = null;
    Program_extractedInterfaceDeclarations_computed = null;
    Program_extractedInterfaceDeclarations_value = null;
    Program_extractedLoopStatements_computed = null;
    Program_extractedLoopStatements_value = null;
    Program_extractedMethodCalls_computed = null;
    Program_extractedMethodCalls_value = null;
    Program_extractedParameters_computed = null;
    Program_extractedParameters_value = null;
    Program_extractedNonPredicates_computed = null;
    Program_extractedNonPredicates_value = null;
    Program_extractedNumericLiterals_computed = null;
    Program_extractedNumericLiterals_value = null;
    Program_extractedPredicates_computed = null;
    Program_extractedPredicates_value = null;
    Program_extractedPrivateMethodDeclarations_computed = null;
    Program_extractedPrivateMethodDeclarations_value = null;
    Program_extractedPublicMethodDeclarations_computed = null;
    Program_extractedPublicMethodDeclarations_value = null;
    Program_extractedReturnStatements_computed = null;
    Program_extractedReturnStatements_value = null;
    Program_extractedReusableMethods_computed = null;
    Program_extractedReusableMethods_value = null;
    Program_extractedSourceFiles_computed = null;
    Program_extractedSourceFiles_value = null;
    Program_extractedStatements_computed = null;
    Program_extractedStatements_value = null;
    Program_extractedStringLiterals_computed = null;
    Program_extractedStringLiterals_value = null;
    Program_extractedStatementTypes_computed = null;
    Program_extractedStatementTypes_value = null;
    Program_extractedSwitchCases_computed = null;
    Program_extractedSwitchCases_value = null;
    Program_extractedSwitchStatements_computed = null;
    Program_extractedSwitchStatements_value = null;
    Program_extractedVariables_computed = null;
    Program_extractedVariables_value = null;
    Program_extractedVariableReferences_computed = null;
    Program_extractedVariableReferences_value = null;
    Program_extractedVulnerableStatements_computed = null;
    Program_extractedVulnerableStatements_value = null;
    contributorMap_Program_extractedBranches = null;
    contributorMap_Program_extractedClassDeclarations = null;
    contributorMap_Program_extractedDataTypes = null;
    contributorMap_Program_extractedForeignMethodCalls = null;
    contributorMap_Program_extractedGlobalVariables = null;
    contributorMap_Program_extractedIfStatements = null;
    contributorMap_Program_extractedImports = null;
    contributorMap_Program_extractedInterfaceDeclarations = null;
    contributorMap_Program_extractedLoopStatements = null;
    contributorMap_Program_extractedMethodCalls = null;
    contributorMap_Program_extractedParameters = null;
    contributorMap_Program_extractedNonPredicates = null;
    contributorMap_Program_extractedNumericLiterals = null;
    contributorMap_Program_extractedPredicates = null;
    contributorMap_Program_extractedPrivateMethodDeclarations = null;
    contributorMap_Program_extractedPublicMethodDeclarations = null;
    contributorMap_Program_extractedReturnStatements = null;
    contributorMap_Program_extractedReusableMethods = null;
    contributorMap_Program_extractedSourceFiles = null;
    contributorMap_Program_extractedStatements = null;
    contributorMap_Program_extractedStringLiterals = null;
    contributorMap_Program_extractedStatementTypes = null;
    contributorMap_Program_extractedSwitchCases = null;
    contributorMap_Program_extractedSwitchStatements = null;
    contributorMap_Program_extractedVariables = null;
    contributorMap_Program_extractedVariableReferences = null;
    contributorMap_Program_extractedVulnerableStatements = null;
    contributorMap_BlockLambdaBody_lambdaReturns = null;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:141
   */
  public Program clone() throws CloneNotSupportedException {
    Program node = (Program) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:146
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
   * @declaredat ASTNode:165
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
   * @declaredat ASTNode:175
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
   * @declaredat ASTNode:195
   */
  public Program treeCopy() {
    Program tree = (Program) copy();
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        ASTNode child = (ASTNode) getChild(i);
        if (child != null) {
          child = child.treeCopy();
          tree.setChild(child, i);
        }
      }
    }
    return tree;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:209
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
  /** @apilevel low-level 
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
   * @return the element at index {@code i} in the CompilationUnit list without
   * triggering rewrites.
   */
  public CompilationUnit getCompilationUnitNoTransform(int i) {
    return (CompilationUnit) getCompilationUnitListNoTransform().getChildNoTransform(i);
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
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:3
   */
  protected java.util.Map<ASTNode, java.util.Set<ASTNode>> contributorMap_Program_extractedBranches = null;

  protected void survey_Program_extractedBranches() {
    if (contributorMap_Program_extractedBranches == null) {
      contributorMap_Program_extractedBranches = new java.util.IdentityHashMap<ASTNode, java.util.Set<ASTNode>>();
      collect_contributors_Program_extractedBranches(this, contributorMap_Program_extractedBranches);
    }
  }

  /**
   * @aspect <NoAspect>
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:21
   */
  protected java.util.Map<ASTNode, java.util.Set<ASTNode>> contributorMap_Program_extractedClassDeclarations = null;

  protected void survey_Program_extractedClassDeclarations() {
    if (contributorMap_Program_extractedClassDeclarations == null) {
      contributorMap_Program_extractedClassDeclarations = new java.util.IdentityHashMap<ASTNode, java.util.Set<ASTNode>>();
      collect_contributors_Program_extractedClassDeclarations(this, contributorMap_Program_extractedClassDeclarations);
    }
  }

  /**
   * @aspect <NoAspect>
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:26
   */
  protected java.util.Map<ASTNode, java.util.Set<ASTNode>> contributorMap_Program_extractedDataTypes = null;

  protected void survey_Program_extractedDataTypes() {
    if (contributorMap_Program_extractedDataTypes == null) {
      contributorMap_Program_extractedDataTypes = new java.util.IdentityHashMap<ASTNode, java.util.Set<ASTNode>>();
      collect_contributors_Program_extractedDataTypes(this, contributorMap_Program_extractedDataTypes);
    }
  }

  /**
   * @aspect <NoAspect>
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:31
   */
  protected java.util.Map<ASTNode, java.util.Set<ASTNode>> contributorMap_Program_extractedForeignMethodCalls = null;

  protected void survey_Program_extractedForeignMethodCalls() {
    if (contributorMap_Program_extractedForeignMethodCalls == null) {
      contributorMap_Program_extractedForeignMethodCalls = new java.util.IdentityHashMap<ASTNode, java.util.Set<ASTNode>>();
      collect_contributors_Program_extractedForeignMethodCalls(this, contributorMap_Program_extractedForeignMethodCalls);
    }
  }

  /**
   * @aspect <NoAspect>
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:35
   */
  protected java.util.Map<ASTNode, java.util.Set<ASTNode>> contributorMap_Program_extractedGlobalVariables = null;

  protected void survey_Program_extractedGlobalVariables() {
    if (contributorMap_Program_extractedGlobalVariables == null) {
      contributorMap_Program_extractedGlobalVariables = new java.util.IdentityHashMap<ASTNode, java.util.Set<ASTNode>>();
      collect_contributors_Program_extractedGlobalVariables(this, contributorMap_Program_extractedGlobalVariables);
    }
  }

  /**
   * @aspect <NoAspect>
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:39
   */
  protected java.util.Map<ASTNode, java.util.Set<ASTNode>> contributorMap_Program_extractedIfStatements = null;

  protected void survey_Program_extractedIfStatements() {
    if (contributorMap_Program_extractedIfStatements == null) {
      contributorMap_Program_extractedIfStatements = new java.util.IdentityHashMap<ASTNode, java.util.Set<ASTNode>>();
      collect_contributors_Program_extractedIfStatements(this, contributorMap_Program_extractedIfStatements);
    }
  }

  /**
   * @aspect <NoAspect>
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:44
   */
  protected java.util.Map<ASTNode, java.util.Set<ASTNode>> contributorMap_Program_extractedImports = null;

  protected void survey_Program_extractedImports() {
    if (contributorMap_Program_extractedImports == null) {
      contributorMap_Program_extractedImports = new java.util.IdentityHashMap<ASTNode, java.util.Set<ASTNode>>();
      collect_contributors_Program_extractedImports(this, contributorMap_Program_extractedImports);
    }
  }

  /**
   * @aspect <NoAspect>
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:49
   */
  protected java.util.Map<ASTNode, java.util.Set<ASTNode>> contributorMap_Program_extractedInterfaceDeclarations = null;

  protected void survey_Program_extractedInterfaceDeclarations() {
    if (contributorMap_Program_extractedInterfaceDeclarations == null) {
      contributorMap_Program_extractedInterfaceDeclarations = new java.util.IdentityHashMap<ASTNode, java.util.Set<ASTNode>>();
      collect_contributors_Program_extractedInterfaceDeclarations(this, contributorMap_Program_extractedInterfaceDeclarations);
    }
  }

  /**
   * @aspect <NoAspect>
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:53
   */
  protected java.util.Map<ASTNode, java.util.Set<ASTNode>> contributorMap_Program_extractedLoopStatements = null;

  protected void survey_Program_extractedLoopStatements() {
    if (contributorMap_Program_extractedLoopStatements == null) {
      contributorMap_Program_extractedLoopStatements = new java.util.IdentityHashMap<ASTNode, java.util.Set<ASTNode>>();
      collect_contributors_Program_extractedLoopStatements(this, contributorMap_Program_extractedLoopStatements);
    }
  }

  /**
   * @aspect <NoAspect>
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:60
   */
  protected java.util.Map<ASTNode, java.util.Set<ASTNode>> contributorMap_Program_extractedMethodCalls = null;

  protected void survey_Program_extractedMethodCalls() {
    if (contributorMap_Program_extractedMethodCalls == null) {
      contributorMap_Program_extractedMethodCalls = new java.util.IdentityHashMap<ASTNode, java.util.Set<ASTNode>>();
      collect_contributors_Program_extractedMethodCalls(this, contributorMap_Program_extractedMethodCalls);
    }
  }

  /**
   * @aspect <NoAspect>
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:64
   */
  protected java.util.Map<ASTNode, java.util.Set<ASTNode>> contributorMap_Program_extractedParameters = null;

  protected void survey_Program_extractedParameters() {
    if (contributorMap_Program_extractedParameters == null) {
      contributorMap_Program_extractedParameters = new java.util.IdentityHashMap<ASTNode, java.util.Set<ASTNode>>();
      collect_contributors_Program_extractedParameters(this, contributorMap_Program_extractedParameters);
    }
  }

  /**
   * @aspect <NoAspect>
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:68
   */
  protected java.util.Map<ASTNode, java.util.Set<ASTNode>> contributorMap_Program_extractedNonPredicates = null;

  protected void survey_Program_extractedNonPredicates() {
    if (contributorMap_Program_extractedNonPredicates == null) {
      contributorMap_Program_extractedNonPredicates = new java.util.IdentityHashMap<ASTNode, java.util.Set<ASTNode>>();
      collect_contributors_Program_extractedNonPredicates(this, contributorMap_Program_extractedNonPredicates);
    }
  }

  /**
   * @aspect <NoAspect>
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:72
   */
  protected java.util.Map<ASTNode, java.util.Set<ASTNode>> contributorMap_Program_extractedNumericLiterals = null;

  protected void survey_Program_extractedNumericLiterals() {
    if (contributorMap_Program_extractedNumericLiterals == null) {
      contributorMap_Program_extractedNumericLiterals = new java.util.IdentityHashMap<ASTNode, java.util.Set<ASTNode>>();
      collect_contributors_Program_extractedNumericLiterals(this, contributorMap_Program_extractedNumericLiterals);
    }
  }

  /**
   * @aspect <NoAspect>
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:76
   */
  protected java.util.Map<ASTNode, java.util.Set<ASTNode>> contributorMap_Program_extractedPredicates = null;

  protected void survey_Program_extractedPredicates() {
    if (contributorMap_Program_extractedPredicates == null) {
      contributorMap_Program_extractedPredicates = new java.util.IdentityHashMap<ASTNode, java.util.Set<ASTNode>>();
      collect_contributors_Program_extractedPredicates(this, contributorMap_Program_extractedPredicates);
    }
  }

  /**
   * @aspect <NoAspect>
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:80
   */
  protected java.util.Map<ASTNode, java.util.Set<ASTNode>> contributorMap_Program_extractedPrivateMethodDeclarations = null;

  protected void survey_Program_extractedPrivateMethodDeclarations() {
    if (contributorMap_Program_extractedPrivateMethodDeclarations == null) {
      contributorMap_Program_extractedPrivateMethodDeclarations = new java.util.IdentityHashMap<ASTNode, java.util.Set<ASTNode>>();
      collect_contributors_Program_extractedPrivateMethodDeclarations(this, contributorMap_Program_extractedPrivateMethodDeclarations);
    }
  }

  /**
   * @aspect <NoAspect>
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:84
   */
  protected java.util.Map<ASTNode, java.util.Set<ASTNode>> contributorMap_Program_extractedPublicMethodDeclarations = null;

  protected void survey_Program_extractedPublicMethodDeclarations() {
    if (contributorMap_Program_extractedPublicMethodDeclarations == null) {
      contributorMap_Program_extractedPublicMethodDeclarations = new java.util.IdentityHashMap<ASTNode, java.util.Set<ASTNode>>();
      collect_contributors_Program_extractedPublicMethodDeclarations(this, contributorMap_Program_extractedPublicMethodDeclarations);
    }
  }

  /**
   * @aspect <NoAspect>
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:88
   */
  protected java.util.Map<ASTNode, java.util.Set<ASTNode>> contributorMap_Program_extractedReturnStatements = null;

  protected void survey_Program_extractedReturnStatements() {
    if (contributorMap_Program_extractedReturnStatements == null) {
      contributorMap_Program_extractedReturnStatements = new java.util.IdentityHashMap<ASTNode, java.util.Set<ASTNode>>();
      collect_contributors_Program_extractedReturnStatements(this, contributorMap_Program_extractedReturnStatements);
    }
  }

  /**
   * @aspect <NoAspect>
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:92
   */
  protected java.util.Map<ASTNode, java.util.Set<ASTNode>> contributorMap_Program_extractedReusableMethods = null;

  protected void survey_Program_extractedReusableMethods() {
    if (contributorMap_Program_extractedReusableMethods == null) {
      contributorMap_Program_extractedReusableMethods = new java.util.IdentityHashMap<ASTNode, java.util.Set<ASTNode>>();
      collect_contributors_Program_extractedReusableMethods(this, contributorMap_Program_extractedReusableMethods);
    }
  }

  /**
   * @aspect <NoAspect>
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:96
   */
  protected java.util.Map<ASTNode, java.util.Set<ASTNode>> contributorMap_Program_extractedSourceFiles = null;

  protected void survey_Program_extractedSourceFiles() {
    if (contributorMap_Program_extractedSourceFiles == null) {
      contributorMap_Program_extractedSourceFiles = new java.util.IdentityHashMap<ASTNode, java.util.Set<ASTNode>>();
      collect_contributors_Program_extractedSourceFiles(this, contributorMap_Program_extractedSourceFiles);
    }
  }

  /**
   * @aspect <NoAspect>
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:100
   */
  protected java.util.Map<ASTNode, java.util.Set<ASTNode>> contributorMap_Program_extractedStatements = null;

  protected void survey_Program_extractedStatements() {
    if (contributorMap_Program_extractedStatements == null) {
      contributorMap_Program_extractedStatements = new java.util.IdentityHashMap<ASTNode, java.util.Set<ASTNode>>();
      collect_contributors_Program_extractedStatements(this, contributorMap_Program_extractedStatements);
    }
  }

  /**
   * @aspect <NoAspect>
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:104
   */
  protected java.util.Map<ASTNode, java.util.Set<ASTNode>> contributorMap_Program_extractedStringLiterals = null;

  protected void survey_Program_extractedStringLiterals() {
    if (contributorMap_Program_extractedStringLiterals == null) {
      contributorMap_Program_extractedStringLiterals = new java.util.IdentityHashMap<ASTNode, java.util.Set<ASTNode>>();
      collect_contributors_Program_extractedStringLiterals(this, contributorMap_Program_extractedStringLiterals);
    }
  }

  /**
   * @aspect <NoAspect>
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:108
   */
  protected java.util.Map<ASTNode, java.util.Set<ASTNode>> contributorMap_Program_extractedStatementTypes = null;

  protected void survey_Program_extractedStatementTypes() {
    if (contributorMap_Program_extractedStatementTypes == null) {
      contributorMap_Program_extractedStatementTypes = new java.util.IdentityHashMap<ASTNode, java.util.Set<ASTNode>>();
      collect_contributors_Program_extractedStatementTypes(this, contributorMap_Program_extractedStatementTypes);
    }
  }

  /**
   * @aspect <NoAspect>
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:138
   */
  protected java.util.Map<ASTNode, java.util.Set<ASTNode>> contributorMap_Program_extractedSwitchCases = null;

  protected void survey_Program_extractedSwitchCases() {
    if (contributorMap_Program_extractedSwitchCases == null) {
      contributorMap_Program_extractedSwitchCases = new java.util.IdentityHashMap<ASTNode, java.util.Set<ASTNode>>();
      collect_contributors_Program_extractedSwitchCases(this, contributorMap_Program_extractedSwitchCases);
    }
  }

  /**
   * @aspect <NoAspect>
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:142
   */
  protected java.util.Map<ASTNode, java.util.Set<ASTNode>> contributorMap_Program_extractedSwitchStatements = null;

  protected void survey_Program_extractedSwitchStatements() {
    if (contributorMap_Program_extractedSwitchStatements == null) {
      contributorMap_Program_extractedSwitchStatements = new java.util.IdentityHashMap<ASTNode, java.util.Set<ASTNode>>();
      collect_contributors_Program_extractedSwitchStatements(this, contributorMap_Program_extractedSwitchStatements);
    }
  }

  /**
   * @aspect <NoAspect>
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:146
   */
  protected java.util.Map<ASTNode, java.util.Set<ASTNode>> contributorMap_Program_extractedVariables = null;

  protected void survey_Program_extractedVariables() {
    if (contributorMap_Program_extractedVariables == null) {
      contributorMap_Program_extractedVariables = new java.util.IdentityHashMap<ASTNode, java.util.Set<ASTNode>>();
      collect_contributors_Program_extractedVariables(this, contributorMap_Program_extractedVariables);
    }
  }

  /**
   * @aspect <NoAspect>
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:151
   */
  protected java.util.Map<ASTNode, java.util.Set<ASTNode>> contributorMap_Program_extractedVariableReferences = null;

  protected void survey_Program_extractedVariableReferences() {
    if (contributorMap_Program_extractedVariableReferences == null) {
      contributorMap_Program_extractedVariableReferences = new java.util.IdentityHashMap<ASTNode, java.util.Set<ASTNode>>();
      collect_contributors_Program_extractedVariableReferences(this, contributorMap_Program_extractedVariableReferences);
    }
  }

  /**
   * @aspect <NoAspect>
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:155
   */
  protected java.util.Map<ASTNode, java.util.Set<ASTNode>> contributorMap_Program_extractedVulnerableStatements = null;

  protected void survey_Program_extractedVulnerableStatements() {
    if (contributorMap_Program_extractedVulnerableStatements == null) {
      contributorMap_Program_extractedVulnerableStatements = new java.util.IdentityHashMap<ASTNode, java.util.Set<ASTNode>>();
      collect_contributors_Program_extractedVulnerableStatements(this, contributorMap_Program_extractedVulnerableStatements);
    }
  }

  /**
   * @aspect <NoAspect>
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\LambdaBody.jrag:47
   */
  protected java.util.Map<ASTNode, java.util.Set<ASTNode>> contributorMap_BlockLambdaBody_lambdaReturns = null;

  protected void survey_BlockLambdaBody_lambdaReturns() {
    if (contributorMap_BlockLambdaBody_lambdaReturns == null) {
      contributorMap_BlockLambdaBody_lambdaReturns = new java.util.IdentityHashMap<ASTNode, java.util.Set<ASTNode>>();
      collect_contributors_BlockLambdaBody_lambdaReturns(this, contributorMap_BlockLambdaBody_lambdaReturns);
    }
  }

  /** @apilevel internal */
  private void getCompilationUnit_String_reset() {
    getCompilationUnit_String_computed = new java.util.HashMap(4);
    getCompilationUnit_String_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map getCompilationUnit_String_values;
  /** @apilevel internal */
  protected java.util.Map getCompilationUnit_String_computed;
  /**
   * Load a compilation unit from disk, selecting a class file
   * if one exists that is not older than a corresponding source
   * file, otherwise the source file is selected.
   * 
   * <p>This method is called by the LibCompilationUnit NTA.  We rely on the
   * result of this method being cached because it will return a newly parsed
   * compilation unit each time it is called.
   * 
   * @return the loaded compilation unit, or the empty compilation unit if no compilation unit was
   * found.
   * @attribute syn
   * @aspect ClassPath
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ClassPath.jrag:424
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ClassPath", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ClassPath.jrag:424")
  public CompilationUnit getCompilationUnit(String typeName) {
    Object _parameters = typeName;
    if (getCompilationUnit_String_computed == null) getCompilationUnit_String_computed = new java.util.HashMap(4);
    if (getCompilationUnit_String_values == null) getCompilationUnit_String_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (getCompilationUnit_String_values.containsKey(_parameters) && getCompilationUnit_String_computed != null
        && getCompilationUnit_String_computed.containsKey(_parameters)
        && (getCompilationUnit_String_computed.get(_parameters) == ASTNode$State.NON_CYCLE || getCompilationUnit_String_computed.get(_parameters) == state().cycle())) {
      return (CompilationUnit) getCompilationUnit_String_values.get(_parameters);
    }
    CompilationUnit getCompilationUnit_String_value = classPath.getCompilationUnit(typeName, emptyCompilationUnit());
    if (state().inCircle()) {
      getCompilationUnit_String_values.put(_parameters, getCompilationUnit_String_value);
      getCompilationUnit_String_computed.put(_parameters, state().cycle());
    
    } else {
      getCompilationUnit_String_values.put(_parameters, getCompilationUnit_String_value);
      getCompilationUnit_String_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return getCompilationUnit_String_value;
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
   * @attribute syn
   * @aspect SpecialClasses
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:37
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:37")
  public TypeDecl typeObject() {
    ASTNode$State state = state();
    if (typeObject_computed == ASTNode$State.NON_CYCLE || typeObject_computed == state().cycle()) {
      return typeObject_value;
    }
    typeObject_value = lookupType("java.lang", "Object");
    if (state().inCircle()) {
      typeObject_computed = state().cycle();
    
    } else {
      typeObject_computed = ASTNode$State.NON_CYCLE;
    
    }
    return typeObject_value;
  }
  /** @apilevel internal */
  private void typeCloneable_reset() {
    typeCloneable_computed = null;
    typeCloneable_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle typeCloneable_computed = null;

  /** @apilevel internal */
  protected TypeDecl typeCloneable_value;

  /**
   * @attribute syn
   * @aspect SpecialClasses
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:38
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:38")
  public TypeDecl typeCloneable() {
    ASTNode$State state = state();
    if (typeCloneable_computed == ASTNode$State.NON_CYCLE || typeCloneable_computed == state().cycle()) {
      return typeCloneable_value;
    }
    typeCloneable_value = lookupType("java.lang", "Cloneable");
    if (state().inCircle()) {
      typeCloneable_computed = state().cycle();
    
    } else {
      typeCloneable_computed = ASTNode$State.NON_CYCLE;
    
    }
    return typeCloneable_value;
  }
  /** @apilevel internal */
  private void typeSerializable_reset() {
    typeSerializable_computed = null;
    typeSerializable_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle typeSerializable_computed = null;

  /** @apilevel internal */
  protected TypeDecl typeSerializable_value;

  /**
   * @attribute syn
   * @aspect SpecialClasses
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:39
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:39")
  public TypeDecl typeSerializable() {
    ASTNode$State state = state();
    if (typeSerializable_computed == ASTNode$State.NON_CYCLE || typeSerializable_computed == state().cycle()) {
      return typeSerializable_value;
    }
    typeSerializable_value = lookupType("java.io", "Serializable");
    if (state().inCircle()) {
      typeSerializable_computed = state().cycle();
    
    } else {
      typeSerializable_computed = ASTNode$State.NON_CYCLE;
    
    }
    return typeSerializable_value;
  }
  /** @apilevel internal */
  private void typeBoolean_reset() {
    typeBoolean_computed = null;
    typeBoolean_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle typeBoolean_computed = null;

  /** @apilevel internal */
  protected TypeDecl typeBoolean_value;

  /**
   * @attribute syn
   * @aspect SpecialClasses
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:45
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:45")
  public TypeDecl typeBoolean() {
    ASTNode$State state = state();
    if (typeBoolean_computed == ASTNode$State.NON_CYCLE || typeBoolean_computed == state().cycle()) {
      return typeBoolean_value;
    }
    typeBoolean_value = getPrimitiveCompilationUnit().typeBoolean();
    if (state().inCircle()) {
      typeBoolean_computed = state().cycle();
    
    } else {
      typeBoolean_computed = ASTNode$State.NON_CYCLE;
    
    }
    return typeBoolean_value;
  }
  /** @apilevel internal */
  private void typeByte_reset() {
    typeByte_computed = null;
    typeByte_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle typeByte_computed = null;

  /** @apilevel internal */
  protected TypeDecl typeByte_value;

  /**
   * @attribute syn
   * @aspect SpecialClasses
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:46
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:46")
  public TypeDecl typeByte() {
    ASTNode$State state = state();
    if (typeByte_computed == ASTNode$State.NON_CYCLE || typeByte_computed == state().cycle()) {
      return typeByte_value;
    }
    typeByte_value = getPrimitiveCompilationUnit().typeByte();
    if (state().inCircle()) {
      typeByte_computed = state().cycle();
    
    } else {
      typeByte_computed = ASTNode$State.NON_CYCLE;
    
    }
    return typeByte_value;
  }
  /** @apilevel internal */
  private void typeShort_reset() {
    typeShort_computed = null;
    typeShort_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle typeShort_computed = null;

  /** @apilevel internal */
  protected TypeDecl typeShort_value;

  /**
   * @attribute syn
   * @aspect SpecialClasses
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:47
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:47")
  public TypeDecl typeShort() {
    ASTNode$State state = state();
    if (typeShort_computed == ASTNode$State.NON_CYCLE || typeShort_computed == state().cycle()) {
      return typeShort_value;
    }
    typeShort_value = getPrimitiveCompilationUnit().typeShort();
    if (state().inCircle()) {
      typeShort_computed = state().cycle();
    
    } else {
      typeShort_computed = ASTNode$State.NON_CYCLE;
    
    }
    return typeShort_value;
  }
  /** @apilevel internal */
  private void typeChar_reset() {
    typeChar_computed = null;
    typeChar_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle typeChar_computed = null;

  /** @apilevel internal */
  protected TypeDecl typeChar_value;

  /**
   * @attribute syn
   * @aspect SpecialClasses
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:48
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:48")
  public TypeDecl typeChar() {
    ASTNode$State state = state();
    if (typeChar_computed == ASTNode$State.NON_CYCLE || typeChar_computed == state().cycle()) {
      return typeChar_value;
    }
    typeChar_value = getPrimitiveCompilationUnit().typeChar();
    if (state().inCircle()) {
      typeChar_computed = state().cycle();
    
    } else {
      typeChar_computed = ASTNode$State.NON_CYCLE;
    
    }
    return typeChar_value;
  }
  /** @apilevel internal */
  private void typeInt_reset() {
    typeInt_computed = null;
    typeInt_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle typeInt_computed = null;

  /** @apilevel internal */
  protected TypeDecl typeInt_value;

  /**
   * @attribute syn
   * @aspect SpecialClasses
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:49
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:49")
  public TypeDecl typeInt() {
    ASTNode$State state = state();
    if (typeInt_computed == ASTNode$State.NON_CYCLE || typeInt_computed == state().cycle()) {
      return typeInt_value;
    }
    typeInt_value = getPrimitiveCompilationUnit().typeInt();
    if (state().inCircle()) {
      typeInt_computed = state().cycle();
    
    } else {
      typeInt_computed = ASTNode$State.NON_CYCLE;
    
    }
    return typeInt_value;
  }
  /** @apilevel internal */
  private void typeLong_reset() {
    typeLong_computed = null;
    typeLong_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle typeLong_computed = null;

  /** @apilevel internal */
  protected TypeDecl typeLong_value;

  /**
   * @attribute syn
   * @aspect SpecialClasses
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:50
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:50")
  public TypeDecl typeLong() {
    ASTNode$State state = state();
    if (typeLong_computed == ASTNode$State.NON_CYCLE || typeLong_computed == state().cycle()) {
      return typeLong_value;
    }
    typeLong_value = getPrimitiveCompilationUnit().typeLong();
    if (state().inCircle()) {
      typeLong_computed = state().cycle();
    
    } else {
      typeLong_computed = ASTNode$State.NON_CYCLE;
    
    }
    return typeLong_value;
  }
  /** @apilevel internal */
  private void typeFloat_reset() {
    typeFloat_computed = null;
    typeFloat_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle typeFloat_computed = null;

  /** @apilevel internal */
  protected TypeDecl typeFloat_value;

  /**
   * @attribute syn
   * @aspect SpecialClasses
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:51
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:51")
  public TypeDecl typeFloat() {
    ASTNode$State state = state();
    if (typeFloat_computed == ASTNode$State.NON_CYCLE || typeFloat_computed == state().cycle()) {
      return typeFloat_value;
    }
    typeFloat_value = getPrimitiveCompilationUnit().typeFloat();
    if (state().inCircle()) {
      typeFloat_computed = state().cycle();
    
    } else {
      typeFloat_computed = ASTNode$State.NON_CYCLE;
    
    }
    return typeFloat_value;
  }
  /** @apilevel internal */
  private void typeDouble_reset() {
    typeDouble_computed = null;
    typeDouble_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle typeDouble_computed = null;

  /** @apilevel internal */
  protected TypeDecl typeDouble_value;

  /**
   * @attribute syn
   * @aspect SpecialClasses
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:52
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:52")
  public TypeDecl typeDouble() {
    ASTNode$State state = state();
    if (typeDouble_computed == ASTNode$State.NON_CYCLE || typeDouble_computed == state().cycle()) {
      return typeDouble_value;
    }
    typeDouble_value = getPrimitiveCompilationUnit().typeDouble();
    if (state().inCircle()) {
      typeDouble_computed = state().cycle();
    
    } else {
      typeDouble_computed = ASTNode$State.NON_CYCLE;
    
    }
    return typeDouble_value;
  }
  /** @apilevel internal */
  private void typeString_reset() {
    typeString_computed = null;
    typeString_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle typeString_computed = null;

  /** @apilevel internal */
  protected TypeDecl typeString_value;

  /**
   * @attribute syn
   * @aspect SpecialClasses
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:53
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:53")
  public TypeDecl typeString() {
    ASTNode$State state = state();
    if (typeString_computed == ASTNode$State.NON_CYCLE || typeString_computed == state().cycle()) {
      return typeString_value;
    }
    typeString_value = lookupType("java.lang", "String");
    if (state().inCircle()) {
      typeString_computed = state().cycle();
    
    } else {
      typeString_computed = ASTNode$State.NON_CYCLE;
    
    }
    return typeString_value;
  }
  /** @apilevel internal */
  private void typeVoid_reset() {
    typeVoid_computed = null;
    typeVoid_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle typeVoid_computed = null;

  /** @apilevel internal */
  protected TypeDecl typeVoid_value;

  /**
   * @attribute syn
   * @aspect SpecialClasses
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:65
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:65")
  public TypeDecl typeVoid() {
    ASTNode$State state = state();
    if (typeVoid_computed == ASTNode$State.NON_CYCLE || typeVoid_computed == state().cycle()) {
      return typeVoid_value;
    }
    typeVoid_value = getPrimitiveCompilationUnit().typeVoid();
    if (state().inCircle()) {
      typeVoid_computed = state().cycle();
    
    } else {
      typeVoid_computed = ASTNode$State.NON_CYCLE;
    
    }
    return typeVoid_value;
  }
  /** @apilevel internal */
  private void typeNull_reset() {
    typeNull_computed = null;
    typeNull_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle typeNull_computed = null;

  /** @apilevel internal */
  protected TypeDecl typeNull_value;

  /**
   * @attribute syn
   * @aspect SpecialClasses
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:68
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:68")
  public TypeDecl typeNull() {
    ASTNode$State state = state();
    if (typeNull_computed == ASTNode$State.NON_CYCLE || typeNull_computed == state().cycle()) {
      return typeNull_value;
    }
    typeNull_value = getPrimitiveCompilationUnit().typeNull();
    if (state().inCircle()) {
      typeNull_computed = state().cycle();
    
    } else {
      typeNull_computed = ASTNode$State.NON_CYCLE;
    
    }
    return typeNull_value;
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
   * @attribute syn
   * @aspect SpecialClasses
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:71
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:71")
  public TypeDecl unknownType() {
    ASTNode$State state = state();
    if (unknownType_computed == ASTNode$State.NON_CYCLE || unknownType_computed == state().cycle()) {
      return unknownType_value;
    }
    unknownType_value = getPrimitiveCompilationUnit().unknownType();
    if (state().inCircle()) {
      unknownType_computed = state().cycle();
    
    } else {
      unknownType_computed = ASTNode$State.NON_CYCLE;
    
    }
    return unknownType_value;
  }
  /** @apilevel internal */
  private void hasPackage_String_reset() {
    hasPackage_String_computed = new java.util.HashMap(4);
    hasPackage_String_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map hasPackage_String_values;
  /** @apilevel internal */
  protected java.util.Map hasPackage_String_computed;
  /**
   * @attribute syn
   * @aspect LookupFullyQualifiedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:101
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="LookupFullyQualifiedTypes", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:101")
  public boolean hasPackage(String packageName) {
    Object _parameters = packageName;
    if (hasPackage_String_computed == null) hasPackage_String_computed = new java.util.HashMap(4);
    if (hasPackage_String_values == null) hasPackage_String_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (hasPackage_String_values.containsKey(_parameters) && hasPackage_String_computed != null
        && hasPackage_String_computed.containsKey(_parameters)
        && (hasPackage_String_computed.get(_parameters) == ASTNode$State.NON_CYCLE || hasPackage_String_computed.get(_parameters) == state().cycle())) {
      return (Boolean) hasPackage_String_values.get(_parameters);
    }
    boolean hasPackage_String_value = isPackage(packageName);
    if (state().inCircle()) {
      hasPackage_String_values.put(_parameters, hasPackage_String_value);
      hasPackage_String_computed.put(_parameters, state().cycle());
    
    } else {
      hasPackage_String_values.put(_parameters, hasPackage_String_value);
      hasPackage_String_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return hasPackage_String_value;
  }
  /** @apilevel internal */
  private void lookupType_String_String_reset() {
    lookupType_String_String_computed = new java.util.HashMap(4);
    lookupType_String_String_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map lookupType_String_String_values;
  /** @apilevel internal */
  protected java.util.Map lookupType_String_String_computed;
  /**
   * Checks from-source compilation units for the given type.
   * If no matching compilation unit is found the library compliation units
   * will be searched.
   * @attribute syn
   * @aspect LookupFullyQualifiedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:146
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="LookupFullyQualifiedTypes", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:146")
  public TypeDecl lookupType(String packageName, String typeName) {
    java.util.List _parameters = new java.util.ArrayList(2);
    _parameters.add(packageName);
    _parameters.add(typeName);
    if (lookupType_String_String_computed == null) lookupType_String_String_computed = new java.util.HashMap(4);
    if (lookupType_String_String_values == null) lookupType_String_String_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (lookupType_String_String_values.containsKey(_parameters) && lookupType_String_String_computed != null
        && lookupType_String_String_computed.containsKey(_parameters)
        && (lookupType_String_String_computed.get(_parameters) == ASTNode$State.NON_CYCLE || lookupType_String_String_computed.get(_parameters) == state().cycle())) {
      return (TypeDecl) lookupType_String_String_values.get(_parameters);
    }
    TypeDecl lookupType_String_String_value = lookupType_compute(packageName, typeName);
    if (state().inCircle()) {
      lookupType_String_String_values.put(_parameters, lookupType_String_String_value);
      lookupType_String_String_computed.put(_parameters, state().cycle());
    
    } else {
      lookupType_String_String_values.put(_parameters, lookupType_String_String_value);
      lookupType_String_String_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return lookupType_String_String_value;
  }
  /** @apilevel internal */
  private TypeDecl lookupType_compute(String packageName, String typeName) {
      // Look for a matching source type.
      TypeDecl sourceType = lookupSourceType(packageName, typeName);
      if (!sourceType.isUnknown()) {
        return sourceType;
      }
  
      // Look for a matching library type.
      return lookupLibraryType(packageName, typeName);
    }
  /** @apilevel internal */
  private void getLibCompilationUnit_String_reset() {
    getLibCompilationUnit_String_values = null;
    getLibCompilationUnit_String_list = null;
  }
  /** @apilevel internal */
  protected List getLibCompilationUnit_String_list;
  /** @apilevel internal */
  protected java.util.Map getLibCompilationUnit_String_values;

  /**
   * This attribute is used to cache library compilation units, by storing the compilation units in
   * a parameterized NTA.
   * @attribute syn
   * @aspect LookupFullyQualifiedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:288
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="LookupFullyQualifiedTypes", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:288")
  public CompilationUnit getLibCompilationUnit(String typeName) {
    Object _parameters = typeName;
    if (getLibCompilationUnit_String_values == null) getLibCompilationUnit_String_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (getLibCompilationUnit_String_values.containsKey(_parameters)) {
      return (CompilationUnit) getLibCompilationUnit_String_values.get(_parameters);
    }
    state().enterLazyAttribute();
    CompilationUnit getLibCompilationUnit_String_value = getCompilationUnit(typeName);
    if (getLibCompilationUnit_String_list == null) {
      getLibCompilationUnit_String_list = new List();
      getLibCompilationUnit_String_list.setParent(this);
    }
    getLibCompilationUnit_String_list.add(getLibCompilationUnit_String_value);
    if (getLibCompilationUnit_String_value != null) {
      getLibCompilationUnit_String_value = (CompilationUnit) getLibCompilationUnit_String_list.getChild(getLibCompilationUnit_String_list.numChildren - 1);
    }
    getLibCompilationUnit_String_values.put(_parameters, getLibCompilationUnit_String_value);
    state().leaveLazyAttribute();
    return getLibCompilationUnit_String_value;
  }
  /** @apilevel internal */
  private void emptyCompilationUnit_reset() {
    emptyCompilationUnit_computed = false;
    
    emptyCompilationUnit_value = null;
  }
  /** @apilevel internal */
  protected boolean emptyCompilationUnit_computed = false;

  /** @apilevel internal */
  protected CompilationUnit emptyCompilationUnit_value;

  /**
   * @attribute syn
   * @aspect LookupFullyQualifiedTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:291
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="LookupFullyQualifiedTypes", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:291")
  public CompilationUnit emptyCompilationUnit() {
    ASTNode$State state = state();
    if (emptyCompilationUnit_computed) {
      return emptyCompilationUnit_value;
    }
    state().enterLazyAttribute();
    emptyCompilationUnit_value = new CompilationUnit();
    emptyCompilationUnit_value.setParent(this);
    emptyCompilationUnit_computed = true;
    state().leaveLazyAttribute();
    return emptyCompilationUnit_value;
  }
  /** @apilevel internal */
  private void getPrimitiveCompilationUnit_reset() {
    getPrimitiveCompilationUnit_computed = false;
    
    getPrimitiveCompilationUnit_value = null;
  }
  /** @apilevel internal */
  protected boolean getPrimitiveCompilationUnit_computed = false;

  /** @apilevel internal */
  protected PrimitiveCompilationUnit getPrimitiveCompilationUnit_value;

  /** Creates a compilation unit with primitive types. 
   * @attribute syn
   * @aspect PrimitiveTypes
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\PrimitiveTypes.jrag:155
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="PrimitiveTypes", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\PrimitiveTypes.jrag:155")
  public PrimitiveCompilationUnit getPrimitiveCompilationUnit() {
    ASTNode$State state = state();
    if (getPrimitiveCompilationUnit_computed) {
      return getPrimitiveCompilationUnit_value;
    }
    state().enterLazyAttribute();
    getPrimitiveCompilationUnit_value = getPrimitiveCompilationUnit_compute();
    getPrimitiveCompilationUnit_value.setParent(this);
    getPrimitiveCompilationUnit_computed = true;
    state().leaveLazyAttribute();
    return getPrimitiveCompilationUnit_value;
  }
  /** @apilevel internal */
  private PrimitiveCompilationUnit getPrimitiveCompilationUnit_compute() {
      PrimitiveCompilationUnit u = new PrimitiveCompilationUnit();
      u.setPackageDecl(PRIMITIVE_PACKAGE_NAME);
      return u;
    }
  /** @apilevel internal */
  private void unknownConstructor_reset() {
    unknownConstructor_computed = null;
    unknownConstructor_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle unknownConstructor_computed = null;

  /** @apilevel internal */
  protected ConstructorDecl unknownConstructor_value;

  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:264
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeAnalysis", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:264")
  public ConstructorDecl unknownConstructor() {
    ASTNode$State state = state();
    if (unknownConstructor_computed == ASTNode$State.NON_CYCLE || unknownConstructor_computed == state().cycle()) {
      return unknownConstructor_value;
    }
    unknownConstructor_value = unknownType().constructors().iterator().next();
    if (state().inCircle()) {
      unknownConstructor_computed = state().cycle();
    
    } else {
      unknownConstructor_computed = ASTNode$State.NON_CYCLE;
    
    }
    return unknownConstructor_value;
  }
  /** @apilevel internal */
  private void wildcards_reset() {
    wildcards_computed = false;
    
    wildcards_value = null;
  }
  /** @apilevel internal */
  protected boolean wildcards_computed = false;

  /** @apilevel internal */
  protected WildcardsCompilationUnit wildcards_value;

  /**
   * @attribute syn
   * @aspect LookupParTypeDecl
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:1569
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="LookupParTypeDecl", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:1569")
  public WildcardsCompilationUnit wildcards() {
    ASTNode$State state = state();
    if (wildcards_computed) {
      return wildcards_value;
    }
    state().enterLazyAttribute();
    wildcards_value = new WildcardsCompilationUnit(
              "wildcards",
              new List(),
              new List());
    wildcards_value.setParent(this);
    wildcards_computed = true;
    state().leaveLazyAttribute();
    return wildcards_value;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Options.jadd:40
   * @apilevel internal
   */
  public Program Define_program(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return this;
  }
  protected boolean canDefine_program(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\AnonymousClasses.jrag:33
   * @apilevel internal
   */
  public TypeDecl Define_superType(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return null;
  }
  protected boolean canDefine_superType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\AnonymousClasses.jrag:39
   * @apilevel internal
   */
  public ConstructorDecl Define_constructorDecl(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return unknownConstructor();
  }
  protected boolean canDefine_constructorDecl(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Arrays.jrag:56
   * @apilevel internal
   */
  public TypeDecl Define_componentType(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return unknownType();
  }
  protected boolean canDefine_componentType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\BranchTarget.jrag:255
   * @apilevel internal
   */
  public LabeledStmt Define_lookupLabel(ASTNode _callerNode, ASTNode _childNode, String name) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return null;
  }
  protected boolean canDefine_lookupLabel(ASTNode _callerNode, ASTNode _childNode, String name) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ClassPath.jrag:105
   * @apilevel internal
   */
  public CompilationUnit Define_compilationUnit(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return null;
  }
  protected boolean canDefine_compilationUnit(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DeclareBeforeUse.jrag:35
   * @apilevel internal
   */
  public int Define_blockIndex(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return -1;
  }
  protected boolean canDefine_blockIndex(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DeclareBeforeUse.jrag:58
   * @apilevel internal
   */
  public boolean Define_declaredBefore(ASTNode _callerNode, ASTNode _childNode, Variable decl) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
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
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:66
   * @apilevel internal
   */
  public boolean Define_isIncOrDec(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  protected boolean canDefine_isIncOrDec(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:256
   * @apilevel internal
   */
  public boolean Define_assignedBefore(ASTNode _callerNode, ASTNode _childNode, Variable v) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  protected boolean canDefine_assignedBefore(ASTNode _callerNode, ASTNode _childNode, Variable v) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:891
   * @apilevel internal
   */
  public boolean Define_unassignedBefore(ASTNode _callerNode, ASTNode _childNode, Variable v) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return true;
  }
  protected boolean canDefine_unassignedBefore(ASTNode _callerNode, ASTNode _childNode, Variable v) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ExceptionHandling.jrag:47
   * @apilevel internal
   */
  public TypeDecl Define_typeException(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return lookupType("java.lang", "Exception");
  }
  protected boolean canDefine_typeException(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\TryWithResources.jrag:142
   * @apilevel internal
   */
  public TypeDecl Define_typeRuntimeException(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return lookupType("java.lang", "RuntimeException");
  }
  protected boolean canDefine_typeRuntimeException(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\TryWithResources.jrag:140
   * @apilevel internal
   */
  public TypeDecl Define_typeError(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return lookupType("java.lang", "Error");
  }
  protected boolean canDefine_typeError(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\ExceptionHandling.jrag:56
   * @apilevel internal
   */
  public TypeDecl Define_typeNullPointerException(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return lookupType("java.lang", "NullPointerException");
  }
  protected boolean canDefine_typeNullPointerException(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:93
   * @apilevel internal
   */
  public TypeDecl Define_typeThrowable(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return lookupType("java.lang", "Throwable");
  }
  protected boolean canDefine_typeThrowable(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\TryWithResources.jrag:115
   * @apilevel internal
   */
  public boolean Define_handlesException(ASTNode _callerNode, ASTNode _childNode, TypeDecl exceptionType) {
    int childIndex = this.getIndexOfChild(_callerNode);
    {
        throw new Error("Operation handlesException not supported");
      }
  }
  protected boolean canDefine_handlesException(ASTNode _callerNode, ASTNode _childNode, TypeDecl exceptionType) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupConstructor.jrag:35
   * @apilevel internal
   */
  public Collection<ConstructorDecl> Define_lookupConstructor(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return Collections.emptyList();
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
    return Collections.emptyList();
  }
  protected boolean canDefine_lookupSuperConstructor(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupMethod.jrag:42
   * @apilevel internal
   */
  public Expr Define_nestedScope(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    {
        throw new UnsupportedOperationException();
      }
  }
  protected boolean canDefine_nestedScope(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupMethod.jrag:52
   * @apilevel internal
   */
  public Collection<MethodDecl> Define_lookupMethod(ASTNode _callerNode, ASTNode _childNode, String name) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return Collections.EMPTY_LIST;
  }
  protected boolean canDefine_lookupMethod(ASTNode _callerNode, ASTNode _childNode, String name) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:1158
   * @apilevel internal
   */
  public TypeDecl Define_typeObject(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return typeObject();
  }
  protected boolean canDefine_typeObject(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:152
   * @apilevel internal
   */
  public TypeDecl Define_typeCloneable(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return typeCloneable();
  }
  protected boolean canDefine_typeCloneable(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:151
   * @apilevel internal
   */
  public TypeDecl Define_typeSerializable(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return typeSerializable();
  }
  protected boolean canDefine_typeSerializable(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:74
   * @apilevel internal
   */
  public TypeDecl Define_typeBoolean(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return typeBoolean();
  }
  protected boolean canDefine_typeBoolean(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:75
   * @apilevel internal
   */
  public TypeDecl Define_typeByte(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return typeByte();
  }
  protected boolean canDefine_typeByte(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:76
   * @apilevel internal
   */
  public TypeDecl Define_typeShort(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return typeShort();
  }
  protected boolean canDefine_typeShort(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:77
   * @apilevel internal
   */
  public TypeDecl Define_typeChar(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return typeChar();
  }
  protected boolean canDefine_typeChar(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:86
   * @apilevel internal
   */
  public TypeDecl Define_typeInt(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return typeInt();
  }
  protected boolean canDefine_typeInt(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:88
   * @apilevel internal
   */
  public TypeDecl Define_typeLong(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return typeLong();
  }
  protected boolean canDefine_typeLong(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:80
   * @apilevel internal
   */
  public TypeDecl Define_typeFloat(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return typeFloat();
  }
  protected boolean canDefine_typeFloat(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:81
   * @apilevel internal
   */
  public TypeDecl Define_typeDouble(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return typeDouble();
  }
  protected boolean canDefine_typeDouble(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Enums.jrag:541
   * @apilevel internal
   */
  public TypeDecl Define_typeString(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return typeString();
  }
  protected boolean canDefine_typeString(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:83
   * @apilevel internal
   */
  public TypeDecl Define_typeVoid(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return typeVoid();
  }
  protected boolean canDefine_typeVoid(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:1168
   * @apilevel internal
   */
  public TypeDecl Define_typeNull(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return typeNull();
  }
  protected boolean canDefine_typeNull(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\TypeCheck.jrag:32
   * @apilevel internal
   */
  public TypeDecl Define_unknownType(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return unknownType();
  }
  protected boolean canDefine_unknownType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupType.jrag:113
   * @apilevel internal
   */
  public boolean Define_hasPackage(ASTNode _callerNode, ASTNode _childNode, String packageName) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return hasPackage(packageName);
  }
  protected boolean canDefine_hasPackage(ASTNode _callerNode, ASTNode _childNode, String packageName) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\TryWithResources.jrag:40
   * @apilevel internal
   */
  public TypeDecl Define_lookupType(ASTNode _callerNode, ASTNode _childNode, String packageName, String typeName) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return lookupType(packageName, typeName);
  }
  protected boolean canDefine_lookupType(ASTNode _callerNode, ASTNode _childNode, String packageName, String typeName) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericMethods.jrag:225
   * @apilevel internal
   */
  public SimpleSet<TypeDecl> Define_lookupType(ASTNode _callerNode, ASTNode _childNode, String name) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return emptySet();
  }
  protected boolean canDefine_lookupType(ASTNode _callerNode, ASTNode _childNode, String name) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\LookupVariable.jrag:30
   * @apilevel internal
   */
  public SimpleSet<Variable> Define_lookupVariable(ASTNode _callerNode, ASTNode _childNode, String name) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return emptySet();
  }
  protected boolean canDefine_lookupVariable(ASTNode _callerNode, ASTNode _childNode, String name) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:433
   * @apilevel internal
   */
  public boolean Define_mayBePublic(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  protected boolean canDefine_mayBePublic(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:435
   * @apilevel internal
   */
  public boolean Define_mayBeProtected(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  protected boolean canDefine_mayBeProtected(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:434
   * @apilevel internal
   */
  public boolean Define_mayBePrivate(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  protected boolean canDefine_mayBePrivate(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:436
   * @apilevel internal
   */
  public boolean Define_mayBeStatic(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  protected boolean canDefine_mayBeStatic(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:437
   * @apilevel internal
   */
  public boolean Define_mayBeFinal(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  protected boolean canDefine_mayBeFinal(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:438
   * @apilevel internal
   */
  public boolean Define_mayBeAbstract(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  protected boolean canDefine_mayBeAbstract(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:439
   * @apilevel internal
   */
  public boolean Define_mayBeVolatile(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  protected boolean canDefine_mayBeVolatile(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:440
   * @apilevel internal
   */
  public boolean Define_mayBeTransient(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  protected boolean canDefine_mayBeTransient(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:441
   * @apilevel internal
   */
  public boolean Define_mayBeStrictfp(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  protected boolean canDefine_mayBeStrictfp(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:442
   * @apilevel internal
   */
  public boolean Define_mayBeSynchronized(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  protected boolean canDefine_mayBeSynchronized(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\Modifiers.jrag:443
   * @apilevel internal
   */
  public boolean Define_mayBeNative(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  protected boolean canDefine_mayBeNative(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\NameCheck.jrag:356
   * @apilevel internal
   */
  public ASTNode Define_enclosingBlock(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return null;
  }
  protected boolean canDefine_enclosingBlock(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\NameCheck.jrag:31
   * @apilevel internal
   */
  public VariableScope Define_outerScope(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    {
        throw new UnsupportedOperationException("outerScope() not defined");
      }
  }
  protected boolean canDefine_outerScope(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\NameCheck.jrag:504
   * @apilevel internal
   */
  public boolean Define_insideLoop(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  protected boolean canDefine_insideLoop(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\NameCheck.jrag:512
   * @apilevel internal
   */
  public boolean Define_insideSwitch(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  protected boolean canDefine_insideSwitch(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\NameCheck.jrag:569
   * @apilevel internal
   */
  public Case Define_bind(ASTNode _callerNode, ASTNode _childNode, Case c) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return null;
  }
  protected boolean canDefine_bind(ASTNode _callerNode, ASTNode _childNode, Case c) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\SyntacticClassification.jrag:36
   * @apilevel internal
   */
  public NameType Define_nameType(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return NameType.NOT_CLASSIFIED;
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
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\LookupVariable.jrag:355
   * @apilevel internal
   */
  public Variable Define_unknownField(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return unknownType().findSingleVariable("unknown");
  }
  protected boolean canDefine_unknownField(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\MethodReference.jrag:30
   * @apilevel internal
   */
  public MethodDecl Define_unknownMethod(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    {
        for (MethodDecl m : unknownType().memberMethods("unknown")) {
          return m;
        }
        throw new Error("Could not find method unknown in type Unknown");
      }
  }
  protected boolean canDefine_unknownMethod(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\ConstructorReference.jrag:29
   * @apilevel internal
   */
  public ConstructorDecl Define_unknownConstructor(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return unknownConstructor();
  }
  protected boolean canDefine_unknownConstructor(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Annotations.jrag:713
   * @apilevel internal
   */
  public TypeDecl Define_declType(ASTNode _callerNode, ASTNode _childNode) {
    int i = this.getIndexOfChild(_callerNode);
    return null;
  }
  protected boolean canDefine_declType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\NameCheck.jrag:30
   * @apilevel internal
   */
  public BodyDecl Define_enclosingBodyDecl(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return null;
  }
  protected boolean canDefine_enclosingBodyDecl(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeAnalysis.jrag:588
   * @apilevel internal
   */
  public boolean Define_isMemberType(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  protected boolean canDefine_isMemberType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\MultiCatch.jrag:76
   * @apilevel internal
   */
  public TypeDecl Define_hostType(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return null;
  }
  protected boolean canDefine_hostType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeCheck.jrag:482
   * @apilevel internal
   */
  public TypeDecl Define_switchType(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return unknownType();
  }
  protected boolean canDefine_switchType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeCheck.jrag:534
   * @apilevel internal
   */
  public TypeDecl Define_returnType(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return typeVoid();
  }
  protected boolean canDefine_returnType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeCheck.jrag:667
   * @apilevel internal
   */
  public TypeDecl Define_enclosingInstance(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return null;
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
    {
        throw new Error("Needs extra equation for methodHost()");
      }
  }
  protected boolean canDefine_methodHost(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:188
   * @apilevel internal
   */
  public boolean Define_inExplicitConstructorInvocation(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  protected boolean canDefine_inExplicitConstructorInvocation(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:196
   * @apilevel internal
   */
  public TypeDecl Define_enclosingExplicitConstructorHostType(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return null;
  }
  protected boolean canDefine_enclosingExplicitConstructorHostType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:207
   * @apilevel internal
   */
  public boolean Define_inStaticContext(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
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
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\MultiCatch.jrag:44
   * @apilevel internal
   */
  public boolean Define_isMethodParameter(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  protected boolean canDefine_isMethodParameter(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\MultiCatch.jrag:45
   * @apilevel internal
   */
  public boolean Define_isConstructorParameter(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  protected boolean canDefine_isConstructorParameter(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\MultiCatch.jrag:46
   * @apilevel internal
   */
  public boolean Define_isExceptionHandlerParameter(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  protected boolean canDefine_isExceptionHandlerParameter(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Annotations.jrag:131
   * @apilevel internal
   */
  public boolean Define_mayUseAnnotationTarget(ASTNode _callerNode, ASTNode _childNode, String name) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  protected boolean canDefine_mayUseAnnotationTarget(ASTNode _callerNode, ASTNode _childNode, String name) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Annotations.jrag:278
   * @apilevel internal
   */
  public ElementValue Define_lookupElementTypeValue(ASTNode _callerNode, ASTNode _childNode, String name) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return null;
  }
  protected boolean canDefine_lookupElementTypeValue(ASTNode _callerNode, ASTNode _childNode, String name) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\SuppressWarnings.jrag:37
   * @apilevel internal
   */
  public boolean Define_withinSuppressWarnings(ASTNode _callerNode, ASTNode _childNode, String annot) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  protected boolean canDefine_withinSuppressWarnings(ASTNode _callerNode, ASTNode _childNode, String annot) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Annotations.jrag:536
   * @apilevel internal
   */
  public boolean Define_withinDeprecatedAnnotation(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  protected boolean canDefine_withinDeprecatedAnnotation(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Annotations.jrag:604
   * @apilevel internal
   */
  public Annotation Define_lookupAnnotation(ASTNode _callerNode, ASTNode _childNode, TypeDecl typeDecl) {
    int i = this.getIndexOfChild(_callerNode);
    return null;
  }
  protected boolean canDefine_lookupAnnotation(ASTNode _callerNode, ASTNode _childNode, TypeDecl typeDecl) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Annotations.jrag:648
   * @apilevel internal
   */
  public TypeDecl Define_enclosingAnnotationDecl(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return unknownType();
  }
  protected boolean canDefine_enclosingAnnotationDecl(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericMethodsInference.jrag:65
   * @apilevel internal
   */
  public TypeDecl Define_assignConvertedType(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return typeNull();
  }
  protected boolean canDefine_assignConvertedType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:341
   * @apilevel internal
   */
  public boolean Define_inExtendsOrImplements(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  protected boolean canDefine_inExtendsOrImplements(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:1249
   * @apilevel internal
   */
  public FieldDecl Define_fieldDecl(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return null;
  }
  protected boolean canDefine_fieldDecl(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:1583
   * @apilevel internal
   */
  public TypeDecl Define_typeWildcard(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return wildcards().typeWildcard();
  }
  protected boolean canDefine_typeWildcard(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:1582
   * @apilevel internal
   */
  public TypeDecl Define_lookupWildcardExtends(ASTNode _callerNode, ASTNode _childNode, TypeDecl typeDecl) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return wildcards().lookupWildcardExtends(typeDecl);
  }
  protected boolean canDefine_lookupWildcardExtends(ASTNode _callerNode, ASTNode _childNode, TypeDecl typeDecl) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:1581
   * @apilevel internal
   */
  public TypeDecl Define_lookupWildcardSuper(ASTNode _callerNode, ASTNode _childNode, TypeDecl typeDecl) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return wildcards().lookupWildcardSuper(typeDecl);
  }
  protected boolean canDefine_lookupWildcardSuper(ASTNode _callerNode, ASTNode _childNode, TypeDecl typeDecl) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\MultiCatch.jrag:210
   * @apilevel internal
   */
  public LUBType Define_lookupLUBType(ASTNode _callerNode, ASTNode _childNode, Collection<TypeDecl> bounds) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return wildcards().lookupLUBType(bounds);
  }
  protected boolean canDefine_lookupLUBType(ASTNode _callerNode, ASTNode _childNode, Collection<TypeDecl> bounds) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\Generics.jrag:1683
   * @apilevel internal
   */
  public GLBType Define_lookupGLBType(ASTNode _callerNode, ASTNode _childNode, Collection<TypeDecl> bounds) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return wildcards().lookupGLBType(bounds);
  }
  protected boolean canDefine_lookupGLBType(ASTNode _callerNode, ASTNode _childNode, Collection<TypeDecl> bounds) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\GenericsParTypeDecl.jrag:74
   * @apilevel internal
   */
  public TypeDecl Define_genericDecl(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return null;
  }
  protected boolean canDefine_genericDecl(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java5\\frontend\\VariableArityParameters.jrag:46
   * @apilevel internal
   */
  public boolean Define_variableArityValid(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  protected boolean canDefine_variableArityValid(ASTNode _callerNode, ASTNode _childNode) {
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
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\Diamond.jrag:409
   * @apilevel internal
   */
  public boolean Define_isAnonymousDecl(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  protected boolean canDefine_isAnonymousDecl(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\Diamond.jrag:425
   * @apilevel internal
   */
  public boolean Define_isExplicitGenericConstructorAccess(ASTNode _callerNode, ASTNode _childNode) {
    int i = this.getIndexOfChild(_callerNode);
    return false;
  }
  protected boolean canDefine_isExplicitGenericConstructorAccess(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\PreciseRethrow.jrag:202
   * @apilevel internal
   */
  public boolean Define_isCatchParam(ASTNode _callerNode, ASTNode _childNode) {
    int i = this.getIndexOfChild(_callerNode);
    return false;
  }
  protected boolean canDefine_isCatchParam(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\PreciseRethrow.jrag:209
   * @apilevel internal
   */
  public CatchClause Define_catchClause(ASTNode _callerNode, ASTNode _childNode) {
    int i = this.getIndexOfChild(_callerNode);
    {
        throw new IllegalStateException("Could not find parent " + "catch clause");
      }
  }
  protected boolean canDefine_catchClause(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java7\\frontend\\TryWithResources.jrag:198
   * @apilevel internal
   */
  public boolean Define_resourcePreviouslyDeclared(ASTNode _callerNode, ASTNode _childNode, String name) {
    int i = this.getIndexOfChild(_callerNode);
    return false;
  }
  protected boolean canDefine_resourcePreviouslyDeclared(ASTNode _callerNode, ASTNode _childNode, String name) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java8\\frontend\\TargetType.jrag:30
   * @apilevel internal
   */
  public TypeDecl Define_targetType(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return typeNull();
  }
  protected boolean canDefine_targetType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\AdditionalNodeCharacteristics.jrag:3
   * @apilevel internal
   */
  public boolean Define_isPredicate(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getCompilationUnitListNoTransform()) {
      // @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\AdditionalNodeCharacteristics.jrag:4
      int i = _callerNode.getIndexOfChild(_childNode);
      return false;
    }
    else {
      return getParent().Define_isPredicate(this, _callerNode);
    }
  }
  protected boolean canDefine_isPredicate(ASTNode _callerNode, ASTNode _childNode) {
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
  /**
   * @attribute coll
   * @aspect NodeCollector
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:3
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.COLL)
  @ASTNodeAnnotation.Source(aspect="NodeCollector", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:3")
  public java.util.Collection<String> extractedBranches() {
    ASTNode$State state = state();
    if (Program_extractedBranches_computed == ASTNode$State.NON_CYCLE || Program_extractedBranches_computed == state().cycle()) {
      return Program_extractedBranches_value;
    }
    Program_extractedBranches_value = extractedBranches_compute();
    if (state().inCircle()) {
      Program_extractedBranches_computed = state().cycle();
    
    } else {
      Program_extractedBranches_computed = ASTNode$State.NON_CYCLE;
    
    }
    return Program_extractedBranches_value;
  }
  /** @apilevel internal */
  private java.util.Collection<String> extractedBranches_compute() {
    ASTNode node = this;
    while (node != null && !(node instanceof Program)) {
      node = node.getParent();
    }
    Program root = (Program) node;
    root.survey_Program_extractedBranches();
    java.util.Collection<String> _computedValue = new java.util.ArrayList<String>();
    if (root.contributorMap_Program_extractedBranches.containsKey(this)) {
      for (ASTNode contributor : root.contributorMap_Program_extractedBranches.get(this)) {
        contributor.contributeTo_Program_extractedBranches(_computedValue);
      }
    }
    return _computedValue;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle Program_extractedBranches_computed = null;

  /** @apilevel internal */
  protected java.util.Collection<String> Program_extractedBranches_value;

  /**
   * @attribute coll
   * @aspect NodeCollector
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:21
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.COLL)
  @ASTNodeAnnotation.Source(aspect="NodeCollector", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:21")
  public java.util.Collection<String> extractedClassDeclarations() {
    ASTNode$State state = state();
    if (Program_extractedClassDeclarations_computed == ASTNode$State.NON_CYCLE || Program_extractedClassDeclarations_computed == state().cycle()) {
      return Program_extractedClassDeclarations_value;
    }
    Program_extractedClassDeclarations_value = extractedClassDeclarations_compute();
    if (state().inCircle()) {
      Program_extractedClassDeclarations_computed = state().cycle();
    
    } else {
      Program_extractedClassDeclarations_computed = ASTNode$State.NON_CYCLE;
    
    }
    return Program_extractedClassDeclarations_value;
  }
  /** @apilevel internal */
  private java.util.Collection<String> extractedClassDeclarations_compute() {
    ASTNode node = this;
    while (node != null && !(node instanceof Program)) {
      node = node.getParent();
    }
    Program root = (Program) node;
    root.survey_Program_extractedClassDeclarations();
    java.util.Collection<String> _computedValue = new java.util.ArrayList<String>();
    if (root.contributorMap_Program_extractedClassDeclarations.containsKey(this)) {
      for (ASTNode contributor : root.contributorMap_Program_extractedClassDeclarations.get(this)) {
        contributor.contributeTo_Program_extractedClassDeclarations(_computedValue);
      }
    }
    return _computedValue;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle Program_extractedClassDeclarations_computed = null;

  /** @apilevel internal */
  protected java.util.Collection<String> Program_extractedClassDeclarations_value;

  /**
   * @attribute coll
   * @aspect NodeCollector
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:26
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.COLL)
  @ASTNodeAnnotation.Source(aspect="NodeCollector", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:26")
  public java.util.Collection<String> extractedDataTypes() {
    ASTNode$State state = state();
    if (Program_extractedDataTypes_computed == ASTNode$State.NON_CYCLE || Program_extractedDataTypes_computed == state().cycle()) {
      return Program_extractedDataTypes_value;
    }
    Program_extractedDataTypes_value = extractedDataTypes_compute();
    if (state().inCircle()) {
      Program_extractedDataTypes_computed = state().cycle();
    
    } else {
      Program_extractedDataTypes_computed = ASTNode$State.NON_CYCLE;
    
    }
    return Program_extractedDataTypes_value;
  }
  /** @apilevel internal */
  private java.util.Collection<String> extractedDataTypes_compute() {
    ASTNode node = this;
    while (node != null && !(node instanceof Program)) {
      node = node.getParent();
    }
    Program root = (Program) node;
    root.survey_Program_extractedDataTypes();
    java.util.Collection<String> _computedValue = new java.util.HashSet<String>();
    if (root.contributorMap_Program_extractedDataTypes.containsKey(this)) {
      for (ASTNode contributor : root.contributorMap_Program_extractedDataTypes.get(this)) {
        contributor.contributeTo_Program_extractedDataTypes(_computedValue);
      }
    }
    return _computedValue;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle Program_extractedDataTypes_computed = null;

  /** @apilevel internal */
  protected java.util.Collection<String> Program_extractedDataTypes_value;

  /**
   * @attribute coll
   * @aspect NodeCollector
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:31
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.COLL)
  @ASTNodeAnnotation.Source(aspect="NodeCollector", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:31")
  public java.util.Collection<String> extractedForeignMethodCalls() {
    ASTNode$State state = state();
    if (Program_extractedForeignMethodCalls_computed == ASTNode$State.NON_CYCLE || Program_extractedForeignMethodCalls_computed == state().cycle()) {
      return Program_extractedForeignMethodCalls_value;
    }
    Program_extractedForeignMethodCalls_value = extractedForeignMethodCalls_compute();
    if (state().inCircle()) {
      Program_extractedForeignMethodCalls_computed = state().cycle();
    
    } else {
      Program_extractedForeignMethodCalls_computed = ASTNode$State.NON_CYCLE;
    
    }
    return Program_extractedForeignMethodCalls_value;
  }
  /** @apilevel internal */
  private java.util.Collection<String> extractedForeignMethodCalls_compute() {
    ASTNode node = this;
    while (node != null && !(node instanceof Program)) {
      node = node.getParent();
    }
    Program root = (Program) node;
    root.survey_Program_extractedForeignMethodCalls();
    java.util.Collection<String> _computedValue = new java.util.ArrayList<String>();
    if (root.contributorMap_Program_extractedForeignMethodCalls.containsKey(this)) {
      for (ASTNode contributor : root.contributorMap_Program_extractedForeignMethodCalls.get(this)) {
        contributor.contributeTo_Program_extractedForeignMethodCalls(_computedValue);
      }
    }
    return _computedValue;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle Program_extractedForeignMethodCalls_computed = null;

  /** @apilevel internal */
  protected java.util.Collection<String> Program_extractedForeignMethodCalls_value;

  /**
   * @attribute coll
   * @aspect NodeCollector
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:35
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.COLL)
  @ASTNodeAnnotation.Source(aspect="NodeCollector", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:35")
  public java.util.Collection<String> extractedGlobalVariables() {
    ASTNode$State state = state();
    if (Program_extractedGlobalVariables_computed == ASTNode$State.NON_CYCLE || Program_extractedGlobalVariables_computed == state().cycle()) {
      return Program_extractedGlobalVariables_value;
    }
    Program_extractedGlobalVariables_value = extractedGlobalVariables_compute();
    if (state().inCircle()) {
      Program_extractedGlobalVariables_computed = state().cycle();
    
    } else {
      Program_extractedGlobalVariables_computed = ASTNode$State.NON_CYCLE;
    
    }
    return Program_extractedGlobalVariables_value;
  }
  /** @apilevel internal */
  private java.util.Collection<String> extractedGlobalVariables_compute() {
    ASTNode node = this;
    while (node != null && !(node instanceof Program)) {
      node = node.getParent();
    }
    Program root = (Program) node;
    root.survey_Program_extractedGlobalVariables();
    java.util.Collection<String> _computedValue = new java.util.ArrayList<String>();
    if (root.contributorMap_Program_extractedGlobalVariables.containsKey(this)) {
      for (ASTNode contributor : root.contributorMap_Program_extractedGlobalVariables.get(this)) {
        contributor.contributeTo_Program_extractedGlobalVariables(_computedValue);
      }
    }
    return _computedValue;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle Program_extractedGlobalVariables_computed = null;

  /** @apilevel internal */
  protected java.util.Collection<String> Program_extractedGlobalVariables_value;

  /**
   * @attribute coll
   * @aspect NodeCollector
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:39
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.COLL)
  @ASTNodeAnnotation.Source(aspect="NodeCollector", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:39")
  public java.util.Collection<String> extractedIfStatements() {
    ASTNode$State state = state();
    if (Program_extractedIfStatements_computed == ASTNode$State.NON_CYCLE || Program_extractedIfStatements_computed == state().cycle()) {
      return Program_extractedIfStatements_value;
    }
    Program_extractedIfStatements_value = extractedIfStatements_compute();
    if (state().inCircle()) {
      Program_extractedIfStatements_computed = state().cycle();
    
    } else {
      Program_extractedIfStatements_computed = ASTNode$State.NON_CYCLE;
    
    }
    return Program_extractedIfStatements_value;
  }
  /** @apilevel internal */
  private java.util.Collection<String> extractedIfStatements_compute() {
    ASTNode node = this;
    while (node != null && !(node instanceof Program)) {
      node = node.getParent();
    }
    Program root = (Program) node;
    root.survey_Program_extractedIfStatements();
    java.util.Collection<String> _computedValue = new java.util.ArrayList<String>();
    if (root.contributorMap_Program_extractedIfStatements.containsKey(this)) {
      for (ASTNode contributor : root.contributorMap_Program_extractedIfStatements.get(this)) {
        contributor.contributeTo_Program_extractedIfStatements(_computedValue);
      }
    }
    return _computedValue;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle Program_extractedIfStatements_computed = null;

  /** @apilevel internal */
  protected java.util.Collection<String> Program_extractedIfStatements_value;

  /**
   * @attribute coll
   * @aspect NodeCollector
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:44
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.COLL)
  @ASTNodeAnnotation.Source(aspect="NodeCollector", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:44")
  public java.util.Collection<String> extractedImports() {
    ASTNode$State state = state();
    if (Program_extractedImports_computed == ASTNode$State.NON_CYCLE || Program_extractedImports_computed == state().cycle()) {
      return Program_extractedImports_value;
    }
    Program_extractedImports_value = extractedImports_compute();
    if (state().inCircle()) {
      Program_extractedImports_computed = state().cycle();
    
    } else {
      Program_extractedImports_computed = ASTNode$State.NON_CYCLE;
    
    }
    return Program_extractedImports_value;
  }
  /** @apilevel internal */
  private java.util.Collection<String> extractedImports_compute() {
    ASTNode node = this;
    while (node != null && !(node instanceof Program)) {
      node = node.getParent();
    }
    Program root = (Program) node;
    root.survey_Program_extractedImports();
    java.util.Collection<String> _computedValue = new java.util.ArrayList<String>();
    if (root.contributorMap_Program_extractedImports.containsKey(this)) {
      for (ASTNode contributor : root.contributorMap_Program_extractedImports.get(this)) {
        contributor.contributeTo_Program_extractedImports(_computedValue);
      }
    }
    return _computedValue;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle Program_extractedImports_computed = null;

  /** @apilevel internal */
  protected java.util.Collection<String> Program_extractedImports_value;

  /**
   * @attribute coll
   * @aspect NodeCollector
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:49
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.COLL)
  @ASTNodeAnnotation.Source(aspect="NodeCollector", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:49")
  public java.util.Collection<String> extractedInterfaceDeclarations() {
    ASTNode$State state = state();
    if (Program_extractedInterfaceDeclarations_computed == ASTNode$State.NON_CYCLE || Program_extractedInterfaceDeclarations_computed == state().cycle()) {
      return Program_extractedInterfaceDeclarations_value;
    }
    Program_extractedInterfaceDeclarations_value = extractedInterfaceDeclarations_compute();
    if (state().inCircle()) {
      Program_extractedInterfaceDeclarations_computed = state().cycle();
    
    } else {
      Program_extractedInterfaceDeclarations_computed = ASTNode$State.NON_CYCLE;
    
    }
    return Program_extractedInterfaceDeclarations_value;
  }
  /** @apilevel internal */
  private java.util.Collection<String> extractedInterfaceDeclarations_compute() {
    ASTNode node = this;
    while (node != null && !(node instanceof Program)) {
      node = node.getParent();
    }
    Program root = (Program) node;
    root.survey_Program_extractedInterfaceDeclarations();
    java.util.Collection<String> _computedValue = new java.util.ArrayList<String>();
    if (root.contributorMap_Program_extractedInterfaceDeclarations.containsKey(this)) {
      for (ASTNode contributor : root.contributorMap_Program_extractedInterfaceDeclarations.get(this)) {
        contributor.contributeTo_Program_extractedInterfaceDeclarations(_computedValue);
      }
    }
    return _computedValue;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle Program_extractedInterfaceDeclarations_computed = null;

  /** @apilevel internal */
  protected java.util.Collection<String> Program_extractedInterfaceDeclarations_value;

  /**
   * @attribute coll
   * @aspect NodeCollector
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:53
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.COLL)
  @ASTNodeAnnotation.Source(aspect="NodeCollector", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:53")
  public java.util.Collection<String> extractedLoopStatements() {
    ASTNode$State state = state();
    if (Program_extractedLoopStatements_computed == ASTNode$State.NON_CYCLE || Program_extractedLoopStatements_computed == state().cycle()) {
      return Program_extractedLoopStatements_value;
    }
    Program_extractedLoopStatements_value = extractedLoopStatements_compute();
    if (state().inCircle()) {
      Program_extractedLoopStatements_computed = state().cycle();
    
    } else {
      Program_extractedLoopStatements_computed = ASTNode$State.NON_CYCLE;
    
    }
    return Program_extractedLoopStatements_value;
  }
  /** @apilevel internal */
  private java.util.Collection<String> extractedLoopStatements_compute() {
    ASTNode node = this;
    while (node != null && !(node instanceof Program)) {
      node = node.getParent();
    }
    Program root = (Program) node;
    root.survey_Program_extractedLoopStatements();
    java.util.Collection<String> _computedValue = new java.util.ArrayList<String>();
    if (root.contributorMap_Program_extractedLoopStatements.containsKey(this)) {
      for (ASTNode contributor : root.contributorMap_Program_extractedLoopStatements.get(this)) {
        contributor.contributeTo_Program_extractedLoopStatements(_computedValue);
      }
    }
    return _computedValue;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle Program_extractedLoopStatements_computed = null;

  /** @apilevel internal */
  protected java.util.Collection<String> Program_extractedLoopStatements_value;

  /**
   * @attribute coll
   * @aspect NodeCollector
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:60
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.COLL)
  @ASTNodeAnnotation.Source(aspect="NodeCollector", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:60")
  public java.util.Collection<String> extractedMethodCalls() {
    ASTNode$State state = state();
    if (Program_extractedMethodCalls_computed == ASTNode$State.NON_CYCLE || Program_extractedMethodCalls_computed == state().cycle()) {
      return Program_extractedMethodCalls_value;
    }
    Program_extractedMethodCalls_value = extractedMethodCalls_compute();
    if (state().inCircle()) {
      Program_extractedMethodCalls_computed = state().cycle();
    
    } else {
      Program_extractedMethodCalls_computed = ASTNode$State.NON_CYCLE;
    
    }
    return Program_extractedMethodCalls_value;
  }
  /** @apilevel internal */
  private java.util.Collection<String> extractedMethodCalls_compute() {
    ASTNode node = this;
    while (node != null && !(node instanceof Program)) {
      node = node.getParent();
    }
    Program root = (Program) node;
    root.survey_Program_extractedMethodCalls();
    java.util.Collection<String> _computedValue = new java.util.ArrayList<String>();
    if (root.contributorMap_Program_extractedMethodCalls.containsKey(this)) {
      for (ASTNode contributor : root.contributorMap_Program_extractedMethodCalls.get(this)) {
        contributor.contributeTo_Program_extractedMethodCalls(_computedValue);
      }
    }
    return _computedValue;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle Program_extractedMethodCalls_computed = null;

  /** @apilevel internal */
  protected java.util.Collection<String> Program_extractedMethodCalls_value;

  /**
   * @attribute coll
   * @aspect NodeCollector
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:64
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.COLL)
  @ASTNodeAnnotation.Source(aspect="NodeCollector", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:64")
  public java.util.Collection<String> extractedParameters() {
    ASTNode$State state = state();
    if (Program_extractedParameters_computed == ASTNode$State.NON_CYCLE || Program_extractedParameters_computed == state().cycle()) {
      return Program_extractedParameters_value;
    }
    Program_extractedParameters_value = extractedParameters_compute();
    if (state().inCircle()) {
      Program_extractedParameters_computed = state().cycle();
    
    } else {
      Program_extractedParameters_computed = ASTNode$State.NON_CYCLE;
    
    }
    return Program_extractedParameters_value;
  }
  /** @apilevel internal */
  private java.util.Collection<String> extractedParameters_compute() {
    ASTNode node = this;
    while (node != null && !(node instanceof Program)) {
      node = node.getParent();
    }
    Program root = (Program) node;
    root.survey_Program_extractedParameters();
    java.util.Collection<String> _computedValue = new java.util.ArrayList<String>();
    if (root.contributorMap_Program_extractedParameters.containsKey(this)) {
      for (ASTNode contributor : root.contributorMap_Program_extractedParameters.get(this)) {
        contributor.contributeTo_Program_extractedParameters(_computedValue);
      }
    }
    return _computedValue;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle Program_extractedParameters_computed = null;

  /** @apilevel internal */
  protected java.util.Collection<String> Program_extractedParameters_value;

  /**
   * @attribute coll
   * @aspect NodeCollector
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:68
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.COLL)
  @ASTNodeAnnotation.Source(aspect="NodeCollector", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:68")
  public java.util.Collection<String> extractedNonPredicates() {
    ASTNode$State state = state();
    if (Program_extractedNonPredicates_computed == ASTNode$State.NON_CYCLE || Program_extractedNonPredicates_computed == state().cycle()) {
      return Program_extractedNonPredicates_value;
    }
    Program_extractedNonPredicates_value = extractedNonPredicates_compute();
    if (state().inCircle()) {
      Program_extractedNonPredicates_computed = state().cycle();
    
    } else {
      Program_extractedNonPredicates_computed = ASTNode$State.NON_CYCLE;
    
    }
    return Program_extractedNonPredicates_value;
  }
  /** @apilevel internal */
  private java.util.Collection<String> extractedNonPredicates_compute() {
    ASTNode node = this;
    while (node != null && !(node instanceof Program)) {
      node = node.getParent();
    }
    Program root = (Program) node;
    root.survey_Program_extractedNonPredicates();
    java.util.Collection<String> _computedValue = new java.util.ArrayList<String>();
    if (root.contributorMap_Program_extractedNonPredicates.containsKey(this)) {
      for (ASTNode contributor : root.contributorMap_Program_extractedNonPredicates.get(this)) {
        contributor.contributeTo_Program_extractedNonPredicates(_computedValue);
      }
    }
    return _computedValue;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle Program_extractedNonPredicates_computed = null;

  /** @apilevel internal */
  protected java.util.Collection<String> Program_extractedNonPredicates_value;

  /**
   * @attribute coll
   * @aspect NodeCollector
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:72
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.COLL)
  @ASTNodeAnnotation.Source(aspect="NodeCollector", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:72")
  public java.util.Collection<String> extractedNumericLiterals() {
    ASTNode$State state = state();
    if (Program_extractedNumericLiterals_computed == ASTNode$State.NON_CYCLE || Program_extractedNumericLiterals_computed == state().cycle()) {
      return Program_extractedNumericLiterals_value;
    }
    Program_extractedNumericLiterals_value = extractedNumericLiterals_compute();
    if (state().inCircle()) {
      Program_extractedNumericLiterals_computed = state().cycle();
    
    } else {
      Program_extractedNumericLiterals_computed = ASTNode$State.NON_CYCLE;
    
    }
    return Program_extractedNumericLiterals_value;
  }
  /** @apilevel internal */
  private java.util.Collection<String> extractedNumericLiterals_compute() {
    ASTNode node = this;
    while (node != null && !(node instanceof Program)) {
      node = node.getParent();
    }
    Program root = (Program) node;
    root.survey_Program_extractedNumericLiterals();
    java.util.Collection<String> _computedValue = new java.util.ArrayList<String>();
    if (root.contributorMap_Program_extractedNumericLiterals.containsKey(this)) {
      for (ASTNode contributor : root.contributorMap_Program_extractedNumericLiterals.get(this)) {
        contributor.contributeTo_Program_extractedNumericLiterals(_computedValue);
      }
    }
    return _computedValue;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle Program_extractedNumericLiterals_computed = null;

  /** @apilevel internal */
  protected java.util.Collection<String> Program_extractedNumericLiterals_value;

  /**
   * @attribute coll
   * @aspect NodeCollector
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:76
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.COLL)
  @ASTNodeAnnotation.Source(aspect="NodeCollector", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:76")
  public java.util.Collection<String> extractedPredicates() {
    ASTNode$State state = state();
    if (Program_extractedPredicates_computed == ASTNode$State.NON_CYCLE || Program_extractedPredicates_computed == state().cycle()) {
      return Program_extractedPredicates_value;
    }
    Program_extractedPredicates_value = extractedPredicates_compute();
    if (state().inCircle()) {
      Program_extractedPredicates_computed = state().cycle();
    
    } else {
      Program_extractedPredicates_computed = ASTNode$State.NON_CYCLE;
    
    }
    return Program_extractedPredicates_value;
  }
  /** @apilevel internal */
  private java.util.Collection<String> extractedPredicates_compute() {
    ASTNode node = this;
    while (node != null && !(node instanceof Program)) {
      node = node.getParent();
    }
    Program root = (Program) node;
    root.survey_Program_extractedPredicates();
    java.util.Collection<String> _computedValue = new java.util.ArrayList<String>();
    if (root.contributorMap_Program_extractedPredicates.containsKey(this)) {
      for (ASTNode contributor : root.contributorMap_Program_extractedPredicates.get(this)) {
        contributor.contributeTo_Program_extractedPredicates(_computedValue);
      }
    }
    return _computedValue;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle Program_extractedPredicates_computed = null;

  /** @apilevel internal */
  protected java.util.Collection<String> Program_extractedPredicates_value;

  /**
   * @attribute coll
   * @aspect NodeCollector
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:80
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.COLL)
  @ASTNodeAnnotation.Source(aspect="NodeCollector", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:80")
  public java.util.Collection<String> extractedPrivateMethodDeclarations() {
    ASTNode$State state = state();
    if (Program_extractedPrivateMethodDeclarations_computed == ASTNode$State.NON_CYCLE || Program_extractedPrivateMethodDeclarations_computed == state().cycle()) {
      return Program_extractedPrivateMethodDeclarations_value;
    }
    Program_extractedPrivateMethodDeclarations_value = extractedPrivateMethodDeclarations_compute();
    if (state().inCircle()) {
      Program_extractedPrivateMethodDeclarations_computed = state().cycle();
    
    } else {
      Program_extractedPrivateMethodDeclarations_computed = ASTNode$State.NON_CYCLE;
    
    }
    return Program_extractedPrivateMethodDeclarations_value;
  }
  /** @apilevel internal */
  private java.util.Collection<String> extractedPrivateMethodDeclarations_compute() {
    ASTNode node = this;
    while (node != null && !(node instanceof Program)) {
      node = node.getParent();
    }
    Program root = (Program) node;
    root.survey_Program_extractedPrivateMethodDeclarations();
    java.util.Collection<String> _computedValue = new java.util.ArrayList<String>();
    if (root.contributorMap_Program_extractedPrivateMethodDeclarations.containsKey(this)) {
      for (ASTNode contributor : root.contributorMap_Program_extractedPrivateMethodDeclarations.get(this)) {
        contributor.contributeTo_Program_extractedPrivateMethodDeclarations(_computedValue);
      }
    }
    return _computedValue;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle Program_extractedPrivateMethodDeclarations_computed = null;

  /** @apilevel internal */
  protected java.util.Collection<String> Program_extractedPrivateMethodDeclarations_value;

  /**
   * @attribute coll
   * @aspect NodeCollector
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:84
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.COLL)
  @ASTNodeAnnotation.Source(aspect="NodeCollector", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:84")
  public java.util.Collection<String> extractedPublicMethodDeclarations() {
    ASTNode$State state = state();
    if (Program_extractedPublicMethodDeclarations_computed == ASTNode$State.NON_CYCLE || Program_extractedPublicMethodDeclarations_computed == state().cycle()) {
      return Program_extractedPublicMethodDeclarations_value;
    }
    Program_extractedPublicMethodDeclarations_value = extractedPublicMethodDeclarations_compute();
    if (state().inCircle()) {
      Program_extractedPublicMethodDeclarations_computed = state().cycle();
    
    } else {
      Program_extractedPublicMethodDeclarations_computed = ASTNode$State.NON_CYCLE;
    
    }
    return Program_extractedPublicMethodDeclarations_value;
  }
  /** @apilevel internal */
  private java.util.Collection<String> extractedPublicMethodDeclarations_compute() {
    ASTNode node = this;
    while (node != null && !(node instanceof Program)) {
      node = node.getParent();
    }
    Program root = (Program) node;
    root.survey_Program_extractedPublicMethodDeclarations();
    java.util.Collection<String> _computedValue = new java.util.ArrayList<String>();
    if (root.contributorMap_Program_extractedPublicMethodDeclarations.containsKey(this)) {
      for (ASTNode contributor : root.contributorMap_Program_extractedPublicMethodDeclarations.get(this)) {
        contributor.contributeTo_Program_extractedPublicMethodDeclarations(_computedValue);
      }
    }
    return _computedValue;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle Program_extractedPublicMethodDeclarations_computed = null;

  /** @apilevel internal */
  protected java.util.Collection<String> Program_extractedPublicMethodDeclarations_value;

  /**
   * @attribute coll
   * @aspect NodeCollector
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:88
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.COLL)
  @ASTNodeAnnotation.Source(aspect="NodeCollector", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:88")
  public java.util.Collection<String> extractedReturnStatements() {
    ASTNode$State state = state();
    if (Program_extractedReturnStatements_computed == ASTNode$State.NON_CYCLE || Program_extractedReturnStatements_computed == state().cycle()) {
      return Program_extractedReturnStatements_value;
    }
    Program_extractedReturnStatements_value = extractedReturnStatements_compute();
    if (state().inCircle()) {
      Program_extractedReturnStatements_computed = state().cycle();
    
    } else {
      Program_extractedReturnStatements_computed = ASTNode$State.NON_CYCLE;
    
    }
    return Program_extractedReturnStatements_value;
  }
  /** @apilevel internal */
  private java.util.Collection<String> extractedReturnStatements_compute() {
    ASTNode node = this;
    while (node != null && !(node instanceof Program)) {
      node = node.getParent();
    }
    Program root = (Program) node;
    root.survey_Program_extractedReturnStatements();
    java.util.Collection<String> _computedValue = new java.util.ArrayList<String>();
    if (root.contributorMap_Program_extractedReturnStatements.containsKey(this)) {
      for (ASTNode contributor : root.contributorMap_Program_extractedReturnStatements.get(this)) {
        contributor.contributeTo_Program_extractedReturnStatements(_computedValue);
      }
    }
    return _computedValue;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle Program_extractedReturnStatements_computed = null;

  /** @apilevel internal */
  protected java.util.Collection<String> Program_extractedReturnStatements_value;

  /**
   * @attribute coll
   * @aspect NodeCollector
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:92
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.COLL)
  @ASTNodeAnnotation.Source(aspect="NodeCollector", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:92")
  public java.util.Collection<String> extractedReusableMethods() {
    ASTNode$State state = state();
    if (Program_extractedReusableMethods_computed == ASTNode$State.NON_CYCLE || Program_extractedReusableMethods_computed == state().cycle()) {
      return Program_extractedReusableMethods_value;
    }
    Program_extractedReusableMethods_value = extractedReusableMethods_compute();
    if (state().inCircle()) {
      Program_extractedReusableMethods_computed = state().cycle();
    
    } else {
      Program_extractedReusableMethods_computed = ASTNode$State.NON_CYCLE;
    
    }
    return Program_extractedReusableMethods_value;
  }
  /** @apilevel internal */
  private java.util.Collection<String> extractedReusableMethods_compute() {
    ASTNode node = this;
    while (node != null && !(node instanceof Program)) {
      node = node.getParent();
    }
    Program root = (Program) node;
    root.survey_Program_extractedReusableMethods();
    java.util.Collection<String> _computedValue = new java.util.ArrayList<String>();
    if (root.contributorMap_Program_extractedReusableMethods.containsKey(this)) {
      for (ASTNode contributor : root.contributorMap_Program_extractedReusableMethods.get(this)) {
        contributor.contributeTo_Program_extractedReusableMethods(_computedValue);
      }
    }
    return _computedValue;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle Program_extractedReusableMethods_computed = null;

  /** @apilevel internal */
  protected java.util.Collection<String> Program_extractedReusableMethods_value;

  /**
   * @attribute coll
   * @aspect NodeCollector
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:96
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.COLL)
  @ASTNodeAnnotation.Source(aspect="NodeCollector", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:96")
  public java.util.Collection<String> extractedSourceFiles() {
    ASTNode$State state = state();
    if (Program_extractedSourceFiles_computed == ASTNode$State.NON_CYCLE || Program_extractedSourceFiles_computed == state().cycle()) {
      return Program_extractedSourceFiles_value;
    }
    Program_extractedSourceFiles_value = extractedSourceFiles_compute();
    if (state().inCircle()) {
      Program_extractedSourceFiles_computed = state().cycle();
    
    } else {
      Program_extractedSourceFiles_computed = ASTNode$State.NON_CYCLE;
    
    }
    return Program_extractedSourceFiles_value;
  }
  /** @apilevel internal */
  private java.util.Collection<String> extractedSourceFiles_compute() {
    ASTNode node = this;
    while (node != null && !(node instanceof Program)) {
      node = node.getParent();
    }
    Program root = (Program) node;
    root.survey_Program_extractedSourceFiles();
    java.util.Collection<String> _computedValue = new java.util.ArrayList<String>();
    if (root.contributorMap_Program_extractedSourceFiles.containsKey(this)) {
      for (ASTNode contributor : root.contributorMap_Program_extractedSourceFiles.get(this)) {
        contributor.contributeTo_Program_extractedSourceFiles(_computedValue);
      }
    }
    return _computedValue;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle Program_extractedSourceFiles_computed = null;

  /** @apilevel internal */
  protected java.util.Collection<String> Program_extractedSourceFiles_value;

  /**
   * @attribute coll
   * @aspect NodeCollector
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:100
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.COLL)
  @ASTNodeAnnotation.Source(aspect="NodeCollector", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:100")
  public java.util.Collection<String> extractedStatements() {
    ASTNode$State state = state();
    if (Program_extractedStatements_computed == ASTNode$State.NON_CYCLE || Program_extractedStatements_computed == state().cycle()) {
      return Program_extractedStatements_value;
    }
    Program_extractedStatements_value = extractedStatements_compute();
    if (state().inCircle()) {
      Program_extractedStatements_computed = state().cycle();
    
    } else {
      Program_extractedStatements_computed = ASTNode$State.NON_CYCLE;
    
    }
    return Program_extractedStatements_value;
  }
  /** @apilevel internal */
  private java.util.Collection<String> extractedStatements_compute() {
    ASTNode node = this;
    while (node != null && !(node instanceof Program)) {
      node = node.getParent();
    }
    Program root = (Program) node;
    root.survey_Program_extractedStatements();
    java.util.Collection<String> _computedValue = new java.util.ArrayList<String>();
    if (root.contributorMap_Program_extractedStatements.containsKey(this)) {
      for (ASTNode contributor : root.contributorMap_Program_extractedStatements.get(this)) {
        contributor.contributeTo_Program_extractedStatements(_computedValue);
      }
    }
    return _computedValue;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle Program_extractedStatements_computed = null;

  /** @apilevel internal */
  protected java.util.Collection<String> Program_extractedStatements_value;

  /**
   * @attribute coll
   * @aspect NodeCollector
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:104
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.COLL)
  @ASTNodeAnnotation.Source(aspect="NodeCollector", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:104")
  public java.util.Collection<String> extractedStringLiterals() {
    ASTNode$State state = state();
    if (Program_extractedStringLiterals_computed == ASTNode$State.NON_CYCLE || Program_extractedStringLiterals_computed == state().cycle()) {
      return Program_extractedStringLiterals_value;
    }
    Program_extractedStringLiterals_value = extractedStringLiterals_compute();
    if (state().inCircle()) {
      Program_extractedStringLiterals_computed = state().cycle();
    
    } else {
      Program_extractedStringLiterals_computed = ASTNode$State.NON_CYCLE;
    
    }
    return Program_extractedStringLiterals_value;
  }
  /** @apilevel internal */
  private java.util.Collection<String> extractedStringLiterals_compute() {
    ASTNode node = this;
    while (node != null && !(node instanceof Program)) {
      node = node.getParent();
    }
    Program root = (Program) node;
    root.survey_Program_extractedStringLiterals();
    java.util.Collection<String> _computedValue = new java.util.ArrayList<String>();
    if (root.contributorMap_Program_extractedStringLiterals.containsKey(this)) {
      for (ASTNode contributor : root.contributorMap_Program_extractedStringLiterals.get(this)) {
        contributor.contributeTo_Program_extractedStringLiterals(_computedValue);
      }
    }
    return _computedValue;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle Program_extractedStringLiterals_computed = null;

  /** @apilevel internal */
  protected java.util.Collection<String> Program_extractedStringLiterals_value;

  /**
   * @attribute coll
   * @aspect NodeCollector
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:108
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.COLL)
  @ASTNodeAnnotation.Source(aspect="NodeCollector", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:108")
  public java.util.Collection<String> extractedStatementTypes() {
    ASTNode$State state = state();
    if (Program_extractedStatementTypes_computed == ASTNode$State.NON_CYCLE || Program_extractedStatementTypes_computed == state().cycle()) {
      return Program_extractedStatementTypes_value;
    }
    Program_extractedStatementTypes_value = extractedStatementTypes_compute();
    if (state().inCircle()) {
      Program_extractedStatementTypes_computed = state().cycle();
    
    } else {
      Program_extractedStatementTypes_computed = ASTNode$State.NON_CYCLE;
    
    }
    return Program_extractedStatementTypes_value;
  }
  /** @apilevel internal */
  private java.util.Collection<String> extractedStatementTypes_compute() {
    ASTNode node = this;
    while (node != null && !(node instanceof Program)) {
      node = node.getParent();
    }
    Program root = (Program) node;
    root.survey_Program_extractedStatementTypes();
    java.util.Collection<String> _computedValue = new java.util.HashSet<String>();
    if (root.contributorMap_Program_extractedStatementTypes.containsKey(this)) {
      for (ASTNode contributor : root.contributorMap_Program_extractedStatementTypes.get(this)) {
        contributor.contributeTo_Program_extractedStatementTypes(_computedValue);
      }
    }
    return _computedValue;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle Program_extractedStatementTypes_computed = null;

  /** @apilevel internal */
  protected java.util.Collection<String> Program_extractedStatementTypes_value;

  /**
   * @attribute coll
   * @aspect NodeCollector
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:138
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.COLL)
  @ASTNodeAnnotation.Source(aspect="NodeCollector", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:138")
  public java.util.Collection<String> extractedSwitchCases() {
    ASTNode$State state = state();
    if (Program_extractedSwitchCases_computed == ASTNode$State.NON_CYCLE || Program_extractedSwitchCases_computed == state().cycle()) {
      return Program_extractedSwitchCases_value;
    }
    Program_extractedSwitchCases_value = extractedSwitchCases_compute();
    if (state().inCircle()) {
      Program_extractedSwitchCases_computed = state().cycle();
    
    } else {
      Program_extractedSwitchCases_computed = ASTNode$State.NON_CYCLE;
    
    }
    return Program_extractedSwitchCases_value;
  }
  /** @apilevel internal */
  private java.util.Collection<String> extractedSwitchCases_compute() {
    ASTNode node = this;
    while (node != null && !(node instanceof Program)) {
      node = node.getParent();
    }
    Program root = (Program) node;
    root.survey_Program_extractedSwitchCases();
    java.util.Collection<String> _computedValue = new java.util.ArrayList<String>();
    if (root.contributorMap_Program_extractedSwitchCases.containsKey(this)) {
      for (ASTNode contributor : root.contributorMap_Program_extractedSwitchCases.get(this)) {
        contributor.contributeTo_Program_extractedSwitchCases(_computedValue);
      }
    }
    return _computedValue;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle Program_extractedSwitchCases_computed = null;

  /** @apilevel internal */
  protected java.util.Collection<String> Program_extractedSwitchCases_value;

  /**
   * @attribute coll
   * @aspect NodeCollector
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:142
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.COLL)
  @ASTNodeAnnotation.Source(aspect="NodeCollector", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:142")
  public java.util.Collection<String> extractedSwitchStatements() {
    ASTNode$State state = state();
    if (Program_extractedSwitchStatements_computed == ASTNode$State.NON_CYCLE || Program_extractedSwitchStatements_computed == state().cycle()) {
      return Program_extractedSwitchStatements_value;
    }
    Program_extractedSwitchStatements_value = extractedSwitchStatements_compute();
    if (state().inCircle()) {
      Program_extractedSwitchStatements_computed = state().cycle();
    
    } else {
      Program_extractedSwitchStatements_computed = ASTNode$State.NON_CYCLE;
    
    }
    return Program_extractedSwitchStatements_value;
  }
  /** @apilevel internal */
  private java.util.Collection<String> extractedSwitchStatements_compute() {
    ASTNode node = this;
    while (node != null && !(node instanceof Program)) {
      node = node.getParent();
    }
    Program root = (Program) node;
    root.survey_Program_extractedSwitchStatements();
    java.util.Collection<String> _computedValue = new java.util.ArrayList<String>();
    if (root.contributorMap_Program_extractedSwitchStatements.containsKey(this)) {
      for (ASTNode contributor : root.contributorMap_Program_extractedSwitchStatements.get(this)) {
        contributor.contributeTo_Program_extractedSwitchStatements(_computedValue);
      }
    }
    return _computedValue;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle Program_extractedSwitchStatements_computed = null;

  /** @apilevel internal */
  protected java.util.Collection<String> Program_extractedSwitchStatements_value;

  /**
   * @attribute coll
   * @aspect NodeCollector
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:146
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.COLL)
  @ASTNodeAnnotation.Source(aspect="NodeCollector", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:146")
  public java.util.Collection<String> extractedVariables() {
    ASTNode$State state = state();
    if (Program_extractedVariables_computed == ASTNode$State.NON_CYCLE || Program_extractedVariables_computed == state().cycle()) {
      return Program_extractedVariables_value;
    }
    Program_extractedVariables_value = extractedVariables_compute();
    if (state().inCircle()) {
      Program_extractedVariables_computed = state().cycle();
    
    } else {
      Program_extractedVariables_computed = ASTNode$State.NON_CYCLE;
    
    }
    return Program_extractedVariables_value;
  }
  /** @apilevel internal */
  private java.util.Collection<String> extractedVariables_compute() {
    ASTNode node = this;
    while (node != null && !(node instanceof Program)) {
      node = node.getParent();
    }
    Program root = (Program) node;
    root.survey_Program_extractedVariables();
    java.util.Collection<String> _computedValue = new java.util.ArrayList<String>();
    if (root.contributorMap_Program_extractedVariables.containsKey(this)) {
      for (ASTNode contributor : root.contributorMap_Program_extractedVariables.get(this)) {
        contributor.contributeTo_Program_extractedVariables(_computedValue);
      }
    }
    return _computedValue;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle Program_extractedVariables_computed = null;

  /** @apilevel internal */
  protected java.util.Collection<String> Program_extractedVariables_value;

  /**
   * @attribute coll
   * @aspect NodeCollector
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:151
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.COLL)
  @ASTNodeAnnotation.Source(aspect="NodeCollector", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:151")
  public java.util.Collection<String> extractedVariableReferences() {
    ASTNode$State state = state();
    if (Program_extractedVariableReferences_computed == ASTNode$State.NON_CYCLE || Program_extractedVariableReferences_computed == state().cycle()) {
      return Program_extractedVariableReferences_value;
    }
    Program_extractedVariableReferences_value = extractedVariableReferences_compute();
    if (state().inCircle()) {
      Program_extractedVariableReferences_computed = state().cycle();
    
    } else {
      Program_extractedVariableReferences_computed = ASTNode$State.NON_CYCLE;
    
    }
    return Program_extractedVariableReferences_value;
  }
  /** @apilevel internal */
  private java.util.Collection<String> extractedVariableReferences_compute() {
    ASTNode node = this;
    while (node != null && !(node instanceof Program)) {
      node = node.getParent();
    }
    Program root = (Program) node;
    root.survey_Program_extractedVariableReferences();
    java.util.Collection<String> _computedValue = new java.util.ArrayList<String>();
    if (root.contributorMap_Program_extractedVariableReferences.containsKey(this)) {
      for (ASTNode contributor : root.contributorMap_Program_extractedVariableReferences.get(this)) {
        contributor.contributeTo_Program_extractedVariableReferences(_computedValue);
      }
    }
    return _computedValue;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle Program_extractedVariableReferences_computed = null;

  /** @apilevel internal */
  protected java.util.Collection<String> Program_extractedVariableReferences_value;

  /**
   * @attribute coll
   * @aspect NodeCollector
   * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:155
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.COLL)
  @ASTNodeAnnotation.Source(aspect="NodeCollector", declaredAt="C:\\Develop\\Diplom\\git_repo\\measure-extractor\\src\\jastadd\\NodeCollector.jrag:155")
  public java.util.Collection<String> extractedVulnerableStatements() {
    ASTNode$State state = state();
    if (Program_extractedVulnerableStatements_computed == ASTNode$State.NON_CYCLE || Program_extractedVulnerableStatements_computed == state().cycle()) {
      return Program_extractedVulnerableStatements_value;
    }
    Program_extractedVulnerableStatements_value = extractedVulnerableStatements_compute();
    if (state().inCircle()) {
      Program_extractedVulnerableStatements_computed = state().cycle();
    
    } else {
      Program_extractedVulnerableStatements_computed = ASTNode$State.NON_CYCLE;
    
    }
    return Program_extractedVulnerableStatements_value;
  }
  /** @apilevel internal */
  private java.util.Collection<String> extractedVulnerableStatements_compute() {
    ASTNode node = this;
    while (node != null && !(node instanceof Program)) {
      node = node.getParent();
    }
    Program root = (Program) node;
    root.survey_Program_extractedVulnerableStatements();
    java.util.Collection<String> _computedValue = new java.util.ArrayList<String>();
    if (root.contributorMap_Program_extractedVulnerableStatements.containsKey(this)) {
      for (ASTNode contributor : root.contributorMap_Program_extractedVulnerableStatements.get(this)) {
        contributor.contributeTo_Program_extractedVulnerableStatements(_computedValue);
      }
    }
    return _computedValue;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle Program_extractedVulnerableStatements_computed = null;

  /** @apilevel internal */
  protected java.util.Collection<String> Program_extractedVulnerableStatements_value;

}
