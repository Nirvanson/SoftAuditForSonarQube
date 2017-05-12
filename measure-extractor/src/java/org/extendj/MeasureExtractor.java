package org.extendj;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.extendj.ast.ASTNode;
import org.extendj.ast.CompilationUnit;

public class MeasureExtractor extends JavaChecker {
	
	private Map<String, Integer> resultMap;
  
  	public Map<String, Integer> extractMeasures(List<File> files) {
  		resultMap = new HashMap<String, Integer>();
  		
  		String[] fileNames = new String[files.size()];
  		for (int i=0; i<files.size(); i++) {
  			fileNames[i] = files.get(i).getAbsolutePath();
  		}
  		this.run(fileNames);
  		
  		return resultMap;
  	}

  	@Override
  	protected int processCompilationUnit(CompilationUnit unit) throws Error {
  		try {
  			// only scan source-files
  			if (unit.fromSource()) {
  				resultMap = includeSubResult(resultMap, traverseNodes(unit));
  			}
  			return 0;
  		} catch (Exception e) {
  			System.err.println("Exception while scanning sourcefile: " + e);
  			return 1;
  		}
  	}
  	
  	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map<String, Integer> traverseNodes(ASTNode node) {
  		Map<String, Integer> nodeResult = node.countNode();
  		
  		for (int i=0; i<node.getNumChild(); i++) {
  			nodeResult = includeSubResult(nodeResult, traverseNodes(node.getChild(i)));
  		}
  		return nodeResult;
  	}
  	
  	private Map<String, Integer> includeSubResult(Map<String, Integer> fullMap, Map<String, Integer> subMap) {
  		for (String key : subMap.keySet()) {
  			if (fullMap.containsKey(key)) {
  				Integer oldValue = fullMap.get(key);
  				fullMap.put(key, oldValue + subMap.get(key));
  			} else {
  				fullMap.put(key, subMap.get(key));
  			}
  		}
  		return fullMap;
  	}
}
