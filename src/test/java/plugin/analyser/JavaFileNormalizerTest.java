package plugin.analyser;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

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
    	for (String line: lines) {
			System.out.println(line);
		}
    	
    	// split to words
    	List<String> words = new ArrayList<String>();
		words = normalizer.splitToWords(lines);
		assertTrue("no words recieved by splitting", !words.isEmpty());
		System.out.println("Wordlist:");
		for (String word: words) {
			System.out.println(word);
		}
		
		// normalized code
		String normalizedCode = null;
		normalizedCode = normalizer.convertToSingleString(lines);
		assertTrue("no normalized code recieved", !(normalizedCode.equals(null) || normalizedCode.isEmpty()));
		System.out.println(normalizedCode);
    }
}
