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
 * @aspect BytecodeDescriptor
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java5\\frontend\\BytecodeDescriptor.jrag:57
 */
 class FieldInfo extends java.lang.Object {
  
    private AbstractClassfileParser p;

  
    String name;

  
    int flags;

  
    private FieldDescriptor fieldDescriptor;

  
    private Attributes.FieldAttributes attributes;

  

    public FieldInfo(AbstractClassfileParser parser) throws IOException {
      p = parser;
      flags = p.u2();
      if (AbstractClassfileParser.VERBOSE) {
        p.print("Flags: " + flags);
      }
      int name_index = p.u2();
      name = ((CONSTANT_Utf8_Info) p.constantPool[name_index]).string();

      fieldDescriptor = new FieldDescriptor(p, name);
      attributes = new Attributes.FieldAttributes(p);
    }

  

    public BodyDecl bodyDecl() {
      FieldDeclaration f;
      if ((flags & Flags.ACC_ENUM) != 0) {
        //EnumConstant : FieldDeclaration ::= Modifiers <ID:String> Arg:Expr* BodyDecl* /TypeAccess:Access/ /[Init:Expr]/;
        f = new EnumConstant(
            AbstractClassfileParser.modifiers(flags),
            name,
            new List(),
            new List()
            );
      } else {
        Signatures.FieldSignature s = attributes.fieldSignature;
        Access type = s != null ? s.fieldTypeAccess() : fieldDescriptor.type();
        f = new FieldDeclaration(
            AbstractClassfileParser.modifiers(flags),
            type,
            name,
            new Opt()
            );
      }
      if (attributes.constantValue() != null) {
        if (fieldDescriptor.isBoolean()) {
          f.setInit(attributes.constantValue().exprAsBoolean());
        } else {
          f.setInit(attributes.constantValue().expr());
        }
      }

      if (attributes.annotations != null) {
        for (Iterator iter = attributes.annotations.iterator(); iter.hasNext(); ) {
          f.getModifiersNoTransform().addModifier((Modifier) iter.next());
        }
      }

      return f;
    }

  

    public boolean isSynthetic() {
      return attributes.isSynthetic();
    }


}
