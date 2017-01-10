package plugin.analyser;

import java.io.File;
import java.util.Arrays;

import org.junit.Test;

import plugin.analyzer.FileAnalyzer;

public class JavaFileAnalyzerTest {
	
    public JavaFileAnalyzerTest() {
        super();
    }
    
    @Test
    public void testFileNormalization() {
    	File input = new File(getClass().getResource("/testdata/CourseManagementController.java").getPath());
    	FileAnalyzer analyzer = new FileAnalyzer(Arrays.asList(input));
    	
    	// do analyze, check logfile manually
    	analyzer.analyze();
    }
}
