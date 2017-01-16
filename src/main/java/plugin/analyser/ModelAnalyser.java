package plugin.analyser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sonar.api.measures.Metric;

import plugin.model.JavaFileContent;
import plugin.model.StatementType;
import plugin.model.WordInFile;
import plugin.util.Logger;

/**
 * Java File Parser to extract needed measures for SoftAudit Metrics.
 *
 * @author Jan Rucks (jan.rucks@gmx.de)
 * @version 0.3
 */
public class ModelAnalyser {
	private List<StatementType> usedStatementTypes;
	private List<List<WordInFile>> usedDataTypes;
	private double scannedSourceFiles;
	private final double optimalModuleSize;

	public ModelAnalyser() {
		usedStatementTypes = new ArrayList<StatementType>();
		usedDataTypes = new ArrayList<List<WordInFile>>();
		scannedSourceFiles = 0.000;
		// TODO: properties file ?
		optimalModuleSize = 200.000;
	}

	public Map<Metric<?>, Double> doFileModelAnalysis(List<JavaFileContent> fileModel, int reachedParsingLevel) {
		scannedSourceFiles++;
		Map<Metric<?>, Double> result = new HashMap<Metric<?>, Double>();
		// TODO: scan model recoursivly to measure all the shit
		Logger.getLogger(null).printFileMeasures(result);
		return result;
	}

	public double getNumberOfStatementTypes() {
		return (double) usedStatementTypes.size();
	}
	
	public double getNumberOfDataTypes() {
		return (double) usedDataTypes.size();
	}
	
	public List<StatementType> getUsedStatementTypes() {
		return usedStatementTypes;
	}

	public void setUsedStatementTypes(List<StatementType> usedStatementTypes) {
		this.usedStatementTypes = usedStatementTypes;
	}

	public List<List<WordInFile>> getUsedDataTypes() {
		return usedDataTypes;
	}

	public void setUsedDataTypes(List<List<WordInFile>> usedDataTypes) {
		this.usedDataTypes = usedDataTypes;
	}

	public double getScannedSourceFiles() {
		return scannedSourceFiles;
	}

	public void setScannedSourceFiles(double scannedSourceFiles) {
		this.scannedSourceFiles = scannedSourceFiles;
	}

	public double getOptimalModuleSize() {
		return optimalModuleSize;
	}
}
