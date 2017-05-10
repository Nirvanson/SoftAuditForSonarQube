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
 * @aspect Variable
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java8\\frontend\\Variable.jadd:31
 */
 interface Variable {

     
    public String name();

     
    public TypeDecl type();

     
    public Collection<TypeDecl> throwTypes();

     
    public boolean isParameter();

    // 4.5.3
     
    // 4.5.3
    public boolean isClassVariable();

     
    public boolean isInstanceVariable();

     
    public boolean isMethodParameter();

     
    public boolean isConstructorParameter();

     
    public boolean isExceptionHandlerParameter();

     
    public boolean isLocalVariable();

    // 4.5.4
     
    // 4.5.4
    public boolean isFinal();

     
    public boolean isVolatile();


    // 4.12.4
     

    // 4.12.4
    public boolean isEffectivelyFinal();


     

    public boolean isBlank();

     
    public boolean isStatic();

     
    public boolean isSynthetic();


     

    public TypeDecl hostType();


     

    public Expr getInit();

     
    public boolean hasInit();


     

    public Constant constant();


     

    public Modifiers getModifiers();
  /**
   * @attribute syn
   * @aspect SourceDeclarations
   * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:1637
   */
  @ASTNodeAnnotation.Attribute
  public Variable sourceVariableDecl();
}
