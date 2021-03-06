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
 * @ast class
 * @aspect DataStructures
 * @declaredat C:\\Develop\\Diplom\\git_repo\\measure-extractor\\extendj\\java4\\frontend\\DataStructures.jrag:287
 */
public class SingleItemIterator<T> extends java.lang.Object implements Iterator<T> {
  
    private boolean done = false;

  
    private final T item;

  

    public SingleItemIterator(T item) {
      this.item = item;
    }

  

    @Override
    public boolean hasNext() {
      return !done;
    }

  

    @Override
    public T next() {
      done = true;
      return item;
    }

  

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }


}
