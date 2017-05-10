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
 * @ast enum
 * @aspect SyntacticClassification
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\SyntacticClassification.jrag:45
 */
/**
   * The classified name type of a parsed name or expression.
   *
   * Method Name is not in this enum because it never occurs in the ASTs
   * produced by our parser: the parser builds the correct node since it has a
   * different structure.
   */
  enum NameType {
    NOT_CLASSIFIED,
    PACKAGE_NAME,
    TYPE_NAME,
    PACKAGE_OR_TYPE_NAME,
    AMBIGUOUS_NAME,
    EXPRESSION_NAME
  }
