package plugin.analyser;

import java.io.File;
import java.io.IOException;
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
    	try {
			List<String> result = normalizer.splitToWords(normalizer.prepareFile(input));
			for (String line: result) {
				System.out.println(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
