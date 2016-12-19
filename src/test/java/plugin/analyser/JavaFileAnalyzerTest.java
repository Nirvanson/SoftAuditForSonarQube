package plugin.analyser;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Arrays;
import java.util.Map;

import org.junit.Test;
import org.sonar.api.measures.Metric;

import plugin.SoftAuditMetrics;

public class JavaFileAnalyzerTest {

    public JavaFileAnalyzerTest() {
        super();
    }

    @Test
    public void testFileNormalization() {
    	File input = new File(getClass().getResource("/testdata/TestClass.txt").getPath());
    	JavaFileAnalyzer analyzer = new JavaFileAnalyzer(Arrays.asList(input));
    	
    	// do analyze
    	Map<Metric<Integer>, Double> result = analyzer.analyze();
    	assertEquals("Wrong if count", 8d, result.get(SoftAuditMetrics.IFS), 0.1);
    	assertEquals("Wrong method count", 5d, result.get(SoftAuditMetrics.MET), 0.1);
    	System.out.println(result.get(SoftAuditMetrics.STM));
    }
}
