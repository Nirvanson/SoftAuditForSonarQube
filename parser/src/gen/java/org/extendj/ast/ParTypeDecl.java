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
 * @ast interface
 * @aspect Generics
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:302
 */
 interface ParTypeDecl extends Parameterization {

    //syn String name();
     
    //syn String name();
    int getNumArgument();

     
    Access getArgument(int index);

     
    public String typeName();

     
    SimpleSet localFields(String name);

     
    Map<String,SimpleSet> localMethodsSignatureMap();
public TypeDecl substitute(TypeVariable typeVariable);

public int numTypeParameter();

public TypeVariable typeParameter(int index);

public Access createQualifiedAccess();

 
  public Access substitute(Parameterization parTypeDecl);

  /**
   * @attribute syn
   * @aspect Generics
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:306
   */
  @ASTNodeAnnotation.Attribute
  public boolean isParameterizedType();
  /**
   * @attribute syn
   * @aspect Generics
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:307
   */
  @ASTNodeAnnotation.Attribute
  public boolean isRawType();
  /**
   * @attribute syn
   * @aspect GenericsTypeCheck
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:591
   */
  @ASTNodeAnnotation.Attribute
  public boolean sameArgument(ParTypeDecl decl);
  /**
   * @attribute syn
   * @aspect LookupParTypeDecl
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:816
   */
  @ASTNodeAnnotation.Attribute
  public boolean sameSignature(Access a);
  /**
   * @attribute syn
   * @aspect LookupParTypeDecl
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:858
   */
  @ASTNodeAnnotation.Attribute
  public boolean sameSignature(java.util.List<TypeDecl> list);
  /**
   * @attribute syn
   * @aspect GenericsParTypeDecl
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\GenericsParTypeDecl.jrag:55
   */
  @ASTNodeAnnotation.Attribute
  public String nameWithArgs();
  /**
   * @attribute inh
   * @aspect GenericsParTypeDecl
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\GenericsParTypeDecl.jrag:71
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl genericDecl();
}
