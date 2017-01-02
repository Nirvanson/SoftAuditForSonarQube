package plugin.analyser;

import java.io.File;
import java.util.Arrays;

import org.junit.Test;

public class JavaFileAnalyzerTest {
	
    public JavaFileAnalyzerTest() {
        super();
    }

    @Test
    public void testFileNormalization() {
    	File input = new File(getClass().getResource("/testdata/StaxParser.java").getPath());
    	JavaFileAnalyzer analyzer = new JavaFileAnalyzer(Arrays.asList(input));
    	
    	// do analyze, check logfile manually
    	analyzer.analyze();
    }
}
