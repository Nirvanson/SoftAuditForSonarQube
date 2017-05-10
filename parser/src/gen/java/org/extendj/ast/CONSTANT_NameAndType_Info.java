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
 * @aspect BytecodeCONSTANT
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\BytecodeCONSTANT.jrag:234
 */
 class CONSTANT_NameAndType_Info extends CONSTANT_Info {
  
    public int name_index;

  
    public int descriptor_index;

  

    public CONSTANT_NameAndType_Info(AbstractClassfileParser parser) throws IOException {
      super(parser);
      name_index = p.u2();
      descriptor_index = p.u2();
    }

  

    public String toString() {
      return "NameAndTypeInfo: " + name_index + " " + descriptor_index;
    }


}
