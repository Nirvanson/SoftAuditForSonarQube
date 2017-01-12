package plugin.analyser;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import plugin.analyzer.FileNormalizer;
import plugin.model.WordInFile;
import plugin.util.ParsingException;

public class JavaFileNormalizerTest {

    public JavaFileNormalizerTest() {
        super();
    }

    @Test
    public void testFileNormalization() {
        File input = new File(getClass().getResource("/testdata/TestClass.txt").getPath());

        // prepare file
        List<String> lines = new ArrayList<String>();
        try {
            lines = FileNormalizer.prepareFile(input);

            assertTrue("no lines recieved by preparation", !lines.isEmpty());
            System.out.println("List of lines with length: " + lines.size());

            // normalized code
            String normalizedCode = null;
            normalizedCode = FileNormalizer.convertToSingleString(lines);
            assertTrue("no normalized code recieved", !(normalizedCode.equals(null) || normalizedCode.isEmpty()));
            System.out.println("Code as single string with length: " + normalizedCode.length());

            // JavaWords
            List<WordInFile> words = FileNormalizer.createJavaWordList(normalizedCode);
            assertTrue("no words recieved by wordlist creation", !words.isEmpty());
            System.out.println("List of words with length: " + words.size());
        } catch (ParsingException e) {
            e.printStackTrace();
        }
    }
}
