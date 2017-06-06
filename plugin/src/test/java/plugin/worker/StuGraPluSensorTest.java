package plugin.worker;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
        File logfile = new File("./target/logs/" + filename + ".log");
        if (logfile.exists()) {
            logfile.delete();
        }
        StuGraPluSensor sensor = new StuGraPluSensor("./target/logs/" + filename + ".log");

        Map<Metric<?>, Double> measures = sensor
                .extractMeasures(new String[] { "src/test/java/testdata/" + filename + ".java" });

        LogFileWriter.getLogger().printMeasures(measures);
        LogFileWriter.getLogger().close();

        assertEquals("Unexpected measurelist", 26, measures.size());
    }

    @Test
    public void testSelfScan() throws IOException {
        List<File> input = listFiles("src/main/java");
        String[] fileNames = new String[input.size()];
        for (int i = 0; i < input.size(); i++) {
            fileNames[i] = input.get(i).getAbsolutePath();
        }
        File logfile = new File("./target/logs/SelfScan.log");
        if (logfile.exists()) {
            logfile.delete();
        }
        StuGraPluSensor sensor = new StuGraPluSensor("./target/logs/SelfScan.log");

        Map<Metric<?>, Double> measures = sensor.extractMeasures(fileNames);

        LogFileWriter.getLogger().printMeasures(measures);
        LogFileWriter.getLogger().close();

        assertEquals("Unexpected measurelist", 26, measures.size());
    }

    public List<File> listFiles(String directoryName) throws IOException {
        List<File> files = new ArrayList<File>();
        for (File file : (new File(directoryName)).listFiles()) {
            if (file.isFile()) {
                files.add(file);
            } else if (file.isDirectory()) {
                files.addAll(listFiles(file.getAbsolutePath()));
            } else {
                throw new IOException("Unexpected file " + file.getName() + " in directory");
            }
        }
        return files;
    }
}
