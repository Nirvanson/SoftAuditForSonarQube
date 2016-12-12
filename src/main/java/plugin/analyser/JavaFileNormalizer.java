package plugin.analyser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaFileNormalizer {
	private static final String LITERAL = "literal_string";
	/**
     * The logger.
     */
    private final Logger log = LoggerFactory.getLogger(this.getClass());
	/**
     * Prepares File for parsing. remove comments, empty lines, leading spaces....
     * 
     * @param file - the file to analyze
     * @throws IOException 
     */
    public List<String> prepareFile(File file) throws IOException{
    	// Step 1 remove empty lines and single line comments
    	List<String> step1 = new ArrayList<String>();
    	BufferedReader br = new BufferedReader(new FileReader(file));
    	String line;
    	while ((line = br.readLine()) != null) {
    		log.info("full line: " + line);
    		// only check non empty lines
    		String linewithoutspaces = line.trim();
    		if (!linewithoutspaces.isEmpty() && !linewithoutspaces.startsWith("//")) {
    			step1.add(linewithoutspaces);
    		}
    	}
    	br.close();
    	return step1;
    }
}
