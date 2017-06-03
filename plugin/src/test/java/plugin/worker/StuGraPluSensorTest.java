package plugin.worker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.sonar.api.measures.Metric;

public class StuGraPluSensorTest {

    public StuGraPluSensorTest() {
        super();
        (new File("target/logs")).mkdirs();
    }

    @Test
    public void singleFileMeasureExtractionTest() throws IOException {
        String filename = "ParsingHorror";
        File input = new File("src/test/java/testdata/" + filename + ".java");
        File logfile = new File("./target/logs/" + filename + ".log");
        if (logfile.exists()) {
            logfile.delete();
        }
        StuGraPluSensor sensor = new StuGraPluSensor("./target/logs/" + filename + ".log");

        Map<Metric<?>, Double> measures = sensor.extractMeasures(Arrays.asList(input));

        LogFileWriter.getLogger().printMeasures(measures);
        LogFileWriter.getLogger().close();
    }

    @Test
    public void testSelfScan() throws IOException {
        List<File> input = new ArrayList<File>();
        listf("src/main/java", input);
        File logfile = new File("./target/logs/SelfScan.log");
        if (logfile.exists()) {
            logfile.delete();
        }
        StuGraPluSensor sensor = new StuGraPluSensor("./target/logs/SelfScan.log");

        Map<Metric<?>, Double> measures = sensor.extractMeasures(input);

        LogFileWriter.getLogger().printMeasures(measures);
        LogFileWriter.getLogger().close();
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
