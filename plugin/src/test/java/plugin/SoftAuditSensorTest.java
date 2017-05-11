package plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.extendj.MeasureExtractor;
import org.junit.Test;
import org.sonar.api.measures.Metric;

import plugin.SoftAuditSensor;
import plugin.util.SoftAuditLogger;

public class SoftAuditSensorTest {
	
    public SoftAuditSensorTest() {
        super();
        (new File("target/logs")).mkdirs();
    }
   
    @Test
    public void testMeasureExtractor(){
    	List<File> input = new ArrayList<File>();
        listf("src/main/java", input);
        System.out.println("files in input: " + input.size());
        MeasureExtractor extractor = new MeasureExtractor();
        Map<String, Integer> result = extractor.extractMeasures(input);
        System.out.println("resultset: " + result.keySet());
        System.out.println("files in result: " + result.get("File"));
    }
    
    @Test
    public void testSingleFileAnalyse(){
        String filename = "ParsingHorror";
        File input = new File("src/test/java/testdata/" + filename + ".java");
        File file = new File("./target/logs/" + filename + ".log");
        if (file.exists()){
            file.delete();
        }
        SoftAuditSensor sensor = new SoftAuditSensor("./target/logs/" + filename + ".log");
        
        // do analyze, check logfile manually
        Map<Metric<?>, Double> measures = sensor.doAnalyze(Arrays.asList(input), 200.0);
        try {
            SoftAuditLogger.getLogger().printCumulatedMeasures(measures);
            SoftAuditLogger.getLogger().close();
        } catch (IOException e) {
            
        }
    }
    
    @Test
    public void testSelfScan(){
        List<File> input = new ArrayList<File>();
        listf("src/main/java", input);
        File file = new File("./target/logs/SelfScan.log");
        if (file.exists()){
            file.delete();
        }
        SoftAuditSensor sensor = new SoftAuditSensor("./target/logs/SelfScan.log");
        
        // do analyze, check logfile manually
        Map<Metric<?>, Double> measures = sensor.doAnalyze(input, 200.0);
        try {
            SoftAuditLogger.getLogger().printCumulatedMeasures(measures);
            SoftAuditLogger.getLogger().close();
        } catch (IOException e) {
            
        }
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
