package org.extendj;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.extendj.ast.CompilationUnit;
import org.extendj.ast.Problem;

public class MeasureExtractor extends JavaChecker {
	
	public static void main(String args[]) {
		MeasureExtractor extractor = new MeasureExtractor();
		Map<String, Collection<String>> resultMap = extractor.extractNodes(args);
	    for (String key: resultMap.keySet()) {
	    	System.out.println("Found " + resultMap.get(key).size() + " " + key);
	    }
	}
	
	public Map<String, Collection<String>> extractNodes(String[] fileNames) {
  		this.run(fileNames);
  		Map<String, Collection<String>> resultMap = new HashMap<String, Collection<String>>();
  		//TODO resultMap.put("Arguments", this.program.extracted);
  		resultMap.put("Branches", this.program.extractedBranches());
  		resultMap.put("Switch-Cases", this.program.extractedSwitchCases());
  		resultMap.put("Class-Declarations", this.program.extractedClassDeclarations());
  		resultMap.put("Numeric-Literals", this.program.extractedNumericLiterals());
  		resultMap.put("If-Statements", this.program.extractedIfStatements());
  		resultMap.put("Interface-Declarations", this.program.extractedInterfaceDeclarations());
  		resultMap.put("String-Literals", this.program.extractedStringLiterals());
  		resultMap.put("Loop-Statements", this.program.extractedLoopStatements());
  		resultMap.put("Public-Method-Declarations", this.program.extractedPublicMethodDeclarations());
  		resultMap.put("Private-Method-Declarations", this.program.extractedPrivateMethodDeclarations());
  		resultMap.put("Method-Parameters", this.program.extractedParameters());
  		//TODO resultMap.put("Predicates", this.program.extracted);
  		resultMap.put("Variable-References", this.program.extractedVariableReferences());
  		resultMap.put("Return-Statements", this.program.extractedReturnStatements());
  		resultMap.put("Statements", this.program.extractedStatements());
  		resultMap.put("Switch-Statements", this.program.extractedSwitchStatements());
  		resultMap.put("Variables", this.program.extractedVariables());
  		resultMap.put("Global-Variables", this.program.extractedGlobalVariables());
  		resultMap.put("Data-Types", this.program.extractedDataTypes());
  		resultMap.put("Foreign-Method-Calls", this.program.extractedForeignMethodCalls());
  		resultMap.put("Method-Calls", this.program.extractedMethodCalls());
  		resultMap.put("Imports", this.program.extractedImports());
  		//TODO resultMap.put("Reusable Methods", this.program.extracted);
  		resultMap.put("Source-Files", this.program.extractedSourceFiles());
  		resultMap.put("Statement-Types", this.program.extractedStatementTypes());
  		return resultMap;
  	}

  	@Override
  	protected void processErrors(Collection<Problem> errors, CompilationUnit unit) {
        System.out.println(errors.size() + " errors found in compilation-unit " + unit.relativeName() + ". Might be because of missing dependency library check.");
      }
}
