package plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import plugin.SoftAuditSensor;

public class SoftAuditSensorTest {
	
    public SoftAuditSensorTest() {
        super();
        (new File("target/logs")).mkdirs();
    }
   
    @Test
    public void testSingleFileAnalyse(){
        String filename = "BilanzController";
        File input = new File("src/main/resources/testdata/" + filename + ".java");
        SoftAuditSensor sensor = new SoftAuditSensor("./target/logs/" + filename + ".log");
        
        // do analyze, check logfile manually
        sensor.doAnalyze(Arrays.asList(input), 200);
    }
    
    @Test
    public void testSelfScan(){
        List<File> input = new ArrayList<File>();
        listf("src/main/java", input);
        SoftAuditSensor sensor = new SoftAuditSensor("./target/logs/SelfScan.log");
        
        // do analyze, check logfile manually
        sensor.doAnalyze(input, 200);
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
