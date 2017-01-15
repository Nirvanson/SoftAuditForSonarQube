package plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import plugin.SoftAuditSensor;

public class SoftAuditSensorTest {
	
    public SoftAuditSensorTest() {
        super();
        (new File("target/logs")).mkdirs();
    }
   
    @Test
    public void testSingleFileAnalyse() throws InterruptedException {
        String filename = "ParsingHorror";
        File input = new File("src/test/java/testdata/" + filename + ".java");
        SoftAuditSensor sensor = new SoftAuditSensor(filename);
        
        // do analyze, check logfile manually
        sensor.doAnalyse(Arrays.asList(input));
    }
    
    @Test
    @Ignore
    public void testSelfScan() throws InterruptedException {
        List<File> input = new ArrayList<File>();
        listf("src/main/java", input);
        SoftAuditSensor sensor = new SoftAuditSensor("SelfScan");
        
        // do analyze, check logfile manually
        sensor.doAnalyse(input);
    }
    
    public void listf(String directoryName, List<File> files) {
        File directory = new File(directoryName);

        // get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isFile()) {
                files.add(file);
            } else if (file.isDirectory()) {
                listf(file.getAbsolutePath(), files);
            }
        }
    }
}
