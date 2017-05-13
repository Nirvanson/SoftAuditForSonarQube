package org.extendj;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.extendj.ast.CompilationUnit;
import org.extendj.ast.Problem;

public class MeasureExtractor extends JavaChecker {
	
	public static void main(String args[]) {
		MeasureExtractor extractor = new MeasureExtractor();
		Map<String, Integer> resultMap = extractor.extractMeasures(args);
	    for (String key: resultMap.keySet()) {
	    	System.out.println("Found " + resultMap.get(key) + " " + key);
	    }
	}
	
	public Map<String, Integer> extractMeasures(String[] fileNames) {
  		this.run(fileNames);
  		
  		Map<String, Integer> resultMap = new HashMap<String, Integer>();
  		//TODO resultMap.put("Arguments", this.program.extracted);
  		//TODO resultMap.put("Branches", this.program.extracted);
  		resultMap.put("Cases", this.program.extractedSwitchCases().size());
  		resultMap.put("Classes", this.program.extractedClassDeclarations().size());
  		resultMap.put("Constants", this.program.extractedNumericLiterals().size());
  		resultMap.put("If-Statements", this.program.extractedIfStatements().size());
  		resultMap.put("Interfaces", this.program.extractedInterfaceDeclarations().size());
  		resultMap.put("Literals", this.program.extractedStringLiterals().size());
  		resultMap.put("Loop-Statements", this.program.extractedLoopStatements().size());
  		resultMap.put("Methods", this.program.extractedMethodDeclarations().size());
  		resultMap.put("Private Methods", this.program.extractedPrivateMethodDeclarations().size());
  		resultMap.put("Parameters", this.program.extractedParameters().size());
  		//TODO resultMap.put("Predicates", this.program.extracted);
  		//TODO resultMap.put("References", this.program.extracted);
  		resultMap.put("Return-Statements", this.program.extractedReturnStatements().size());
  		resultMap.put("Statements", this.program.extractedStatements().size());
  		resultMap.put("Switch-Statements", this.program.extractedSwitchStatements().size());
  		//TODO resultMap.put("Variables", this.program.extracted);
  		//TODO resultMap.put("Global Variables", this.program.extracted);
  		//TODO resultMap.put("Data Types", this.program.extracted);
  		//TODO resultMap.put("Foreign-Function-Calls", this.program.extracted);
  		//TODO resultMap.put("Function-Calls", this.program.extracted);
  		resultMap.put("Imports", this.program.extractedImports().size());
  		//TODO resultMap.put("Reusable Methods", this.program.extracted);
  		resultMap.put("Source-Files", this.program.extractedSourceFiles().size());
  		//TODO resultMap.put("Statement-Types", this.program.extracted);
  		return resultMap;
  	}

  	@Override
  	protected void processErrors(Collection<Problem> errors, CompilationUnit unit) {
        System.out.println(errors.size() + " errors found in compilation-unit " + unit.relativeName() + ". Might be because of missing dependency library check.");
      }
}
