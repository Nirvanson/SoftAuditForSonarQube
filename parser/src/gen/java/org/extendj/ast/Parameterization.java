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
 * @aspect LookupParTypeDecl
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\Generics.jrag:932
 */
 interface Parameterization {

     
    boolean isRawType();

     
    TypeDecl substitute(TypeVariable typeVariable);


  /**
   * A raw type parameterization. Performs no substitutions.
   */
   public static final Parameterization RAW = new Parameterization() {
    @Override
    public boolean isRawType() {
      return true;
    }
    @Override
    public TypeDecl substitute(TypeVariable typeVariable) {
      return typeVariable;
    }
  };
}
