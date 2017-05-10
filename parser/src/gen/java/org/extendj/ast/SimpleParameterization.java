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
 * @ast class
 * @aspect MethodSignature15
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\MethodSignature.jrag:254
 */
 class SimpleParameterization extends java.lang.Object implements Parameterization {
  
    Map<TypeVariable, TypeDecl> typeMap = new HashMap<TypeVariable, TypeDecl>();

  
    public SimpleParameterization(Iterable<TypeVariable> typeParams, Iterable<TypeDecl> typeArgs) {
      Iterator<TypeVariable> param = typeParams.iterator();
      Iterator<TypeDecl> arg = typeArgs.iterator();
      while (param.hasNext() && arg.hasNext()) {
        typeMap.put(param.next(), arg.next());
      }
    }

  

    @Override
    public boolean isRawType() {
      return false;
    }

  

    @Override
    public TypeDecl substitute(TypeVariable typeVariable) {
      if (typeMap.containsKey(typeVariable)) {
        return typeMap.get(typeVariable);
      }
      return typeVariable;
    }


}
