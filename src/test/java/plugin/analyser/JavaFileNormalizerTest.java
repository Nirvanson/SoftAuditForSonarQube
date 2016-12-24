package plugin.analyser;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import plugin.model.JavaClass;
import plugin.model.JavaFileContent;
import plugin.model.WordInFile;
import plugin.model.WordList;

public class JavaFileNormalizerTest {

    public JavaFileNormalizerTest() {
        super();
    }

    @Test @SuppressWarnings("unchecked")
    public void testFileNormalization() {
    	File input = new File(getClass().getResource("/testdata/TestClass.txt").getPath());
    	JavaFileNormalizer normalizer = new JavaFileNormalizer();
    	
    	// prepare file
    	List<String> lines = new ArrayList<String>();
    	try { 
    		lines = normalizer.prepareFile(input);
    	} catch (IOException e) {
			e.printStackTrace();
		}
    	assertTrue("no lines recieved by preparation", !lines.isEmpty());
    	System.out.println("List of lines with length: " + lines.size());
		
		// normalized code
		String normalizedCode = null;
		normalizedCode = normalizer.convertToSingleString(lines);
		assertTrue("no normalized code recieved", !(normalizedCode.equals(null) || normalizedCode.isEmpty()));
		System.out.println("Code as single string with length: " + normalizedCode.length());
		
		// JavaWords
		List<WordInFile> words = normalizer.createJavaWordList(normalizedCode);
		assertTrue("no words recieved by wordlist creation", !words.isEmpty());
		System.out.println("List of words with length: " + words.size());
		
		// basic structure extraction
		List<JavaFileContent> contents = normalizer.parseClassStructure(words);
		assertTrue("no contents recieved by method extraction", !contents.isEmpty());
		System.out.println("Splittet to Contentparts (basic structure): " + contents.size());
		
		// method extraction
		for (JavaFileContent content : contents) {
			if (content instanceof JavaClass) {
				content.setContent(normalizer.parseMethods(((WordList) content.getContent().get(0)).getWordlist()));
			}
		}
		assertTrue("no contents recieved by method extraction", !contents.isEmpty());
		System.out.println("Splittet to Contentparts (methods and Wordlists outside of Methods): " + contents.size());
    }
}
