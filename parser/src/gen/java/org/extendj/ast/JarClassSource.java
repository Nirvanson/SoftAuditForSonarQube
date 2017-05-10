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
 * @aspect PathPart
 * @declaredat C:\\Develop\\Diplom\\git_repo\\parser\\extendj\\java4\\frontend\\PathPart.jadd:258
 */
public class JarClassSource extends BytecodeClassSource {
  
    private final ZipFile jar;

  
    private final ZipEntry entry;

  
    private final String jarPath;

  

    public JarClassSource(PathPart sourcePath, ZipFile jar, ZipEntry entry,
        String jarPath) {
      super(sourcePath);
      this.jar = jar;
      this.entry = entry;
      this.jarPath = jarPath;
    }

  

    public String jarFilePath() {
      return entry.getName();
    }

  

    @Override
    public long lastModified() {
      return entry.getTime();
    }

  

    @Override
    public InputStream openInputStream() throws IOException {
      return jar.getInputStream(entry);
    }

  

    @Override
    public String pathName() {
      return jarPath;
    }

  

    @Override
    public String relativeName() {
      return entry.getName();
    }

  

    @Override
    public String sourceName() {
      return pathName() + ":" + relativeName();
    }


}
