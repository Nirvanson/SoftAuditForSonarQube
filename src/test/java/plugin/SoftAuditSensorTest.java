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
    }
   
    @Test
    public void testSingleFileAnalyse() {
        File input = new File(getClass().getResource("/testdata/CourseManagementController.java").getPath());
        SoftAuditSensor sensor = new SoftAuditSensor(null);
        
        // do analyze, check logfile manually
        sensor.doAnalyse(Arrays.asList(input));
    }
    
    @Test
    @Ignore
    public void testSelfScan() {
        List<File> input = new ArrayList<File>();
        for (File file : new File(getClass().getResource("../java/").getPath()).listFiles()) {
            if (!file.isDirectory() && file.getName().contains(".java")) {
                input.add(file);
            }
        }
        SoftAuditSensor sensor = new SoftAuditSensor(null);
        
        // do analyze, check logfile manually
        sensor.doAnalyse(input);
    }
}
