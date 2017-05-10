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
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:203
 */
 interface GenericTypeDecl {

     
    TypeDecl original();

     
    int getNumTypeParameter();

     
    TypeVariable getTypeParameter(int index);

     
    List<TypeVariable> getTypeParameterList();

     
    public String fullName();

     
    public String typeName();
public TypeDecl makeGeneric(Signatures.ClassSignature s);

public SimpleSet addTypeVariables(SimpleSet c, String name);

public List createArgumentList(Collection<TypeDecl> args);

  /**
   * @attribute syn
   * @aspect Generics
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:204
   */
  @ASTNodeAnnotation.Attribute
  public boolean isGenericType();
  /**
   * @attribute syn
   * @aspect Generics
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:209
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl rawType();
  /** Transforms the parameter and calls the lookupParTypeDecl attribute for ArrayList arguments. 
   * @attribute syn
   * @aspect LookupParTypeDecl
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:871
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl lookupParTypeDecl(ParTypeAccess p);
  /**
   * @attribute syn
   * @aspect LookupParTypeDecl
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:879
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl lookupParTypeDecl(Collection<TypeDecl> typeArgs);
}
