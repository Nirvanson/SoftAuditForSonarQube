package plugin.analyser;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import plugin.model.JavaClassContent;
import plugin.model.JavaWord;

public class JavaFileNormalizerTest {

    public JavaFileNormalizerTest() {
        super();
    }

    @Test
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
		List<JavaWord> words = normalizer.createJavaWordList(normalizedCode);
		assertTrue("no words recieved by wordlist creation", !words.isEmpty());
		System.out.println("List of words with length: " + words.size());
		
		// Reduced JavaWords
		List<JavaWord> reducedwords = normalizer.reduceWordList(words);
		assertTrue("no words recieved by wordlist reduction", !reducedwords.isEmpty());
		System.out.println("Reduced list of words with length: " + reducedwords.size());
		
		// Method extraction
		List<JavaClassContent> contents = normalizer.splitToMethods(reducedwords);
		assertTrue("no contents recieved by method extraction", !contents.isEmpty());
		System.out.println("Splittet to Contentparts (methods and Wordlists outside of Methods): " + contents.size());
    }
}
