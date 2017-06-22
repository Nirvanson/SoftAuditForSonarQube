package org.extendj;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.extendj.ast.CompilationUnit;
import org.extendj.ast.Problem;

public class MeasureExtractor extends JavaChecker {
	
	/**
	 * Run Measure-Extractor standalone with filenames as input to extract relevant 
	 * nodes from these files and print out the number of collected nodes for each 
	 * category.
	 * 
	 * @param args - names of the files to measure
	 */
	public static void main(String args[]) {
		MeasureExtractor extractor = new MeasureExtractor();
		Map<String, Collection<String>> resultMap = extractor.extractNodes(args);
	    for (String key: resultMap.keySet()) {
	    	System.out.println("Found " + resultMap.get(key).size() + " " + key);
	    }
	}
	
	/**
	 * Run Measure-Extractor with filenames as input to extract relevant nodes from 
	 * these files and return them as HashMap.
	 * 
	 * @param fileNames - names of the files to measure
	 * @returns collected nodes as HashMap
	 */
	public Map<String, Collection<String>> extractNodes(String[] fileNames) {
  		this.run(fileNames);
  		Map<String, Collection<String>> resultMap = new HashMap<String, Collection<String>>();
  		
  		// BRA - Branches (2 for each loop, if and try; 1 for each case, catch and return)
  		resultMap.put("Branches", this.program.extractedBranches());
  		// CLA - Class declarations
  		resultMap.put("Class-Declarations", this.program.extractedClassDeclarations());
  		// DTY - Data types (HashSet)
  		resultMap.put("Data-Types", this.program.extractedDataTypes());
  		// FMC - Foreign method calls (not standard java and not in measured program)
  		resultMap.put("Foreign-Method-Calls", this.program.extractedForeignMethodCalls());
  		// GVA - Global variable declarations (fields)
  		resultMap.put("Global-Variables", this.program.extractedGlobalVariables());
  		// IFS - If-statements (and Try-statements)
  		resultMap.put("If-Statements", this.program.extractedIfStatements());
  		// IMP - Imports (including package declaration)
  		resultMap.put("Imports", this.program.extractedImports());
  		// INT - Interface declarations
  		resultMap.put("Interface-Declarations", this.program.extractedInterfaceDeclarations());
  		// LOS - Loop-statements (DoWhile, While, For, EnhancedFor)
  		resultMap.put("Loop-Statements", this.program.extractedLoopStatements());
  		// MEC - Method calls
  		resultMap.put("Method-Calls", this.program.extractedMethodCalls());
  		// MPA - Method parameters
  		resultMap.put("Method-Parameters", this.program.extractedParameters());
  		// NPR - Non-predicate-references
  		resultMap.put("Non-Predicate-References", this.program.extractedNonPredicates());
  		// NUL - Numeric literals
  		resultMap.put("Numeric-Literals", this.program.extractedNumericLiterals());
  		// PRE - Predicates (variable references in loop/if/switch-conditions)
  		resultMap.put("Predicates", this.program.extractedPredicates());
  		// PRM - Private method declarations
  		resultMap.put("Private-Method-Declarations", this.program.extractedPrivateMethodDeclarations());
  		// PUM - Non private method declarations (public / protected)
  		resultMap.put("Public-Method-Declarations", this.program.extractedPublicMethodDeclarations());
  		// RES - Return statements
  		resultMap.put("Return-Statements", this.program.extractedReturnStatements());
  		// RUM - Reusable methods
  		resultMap.put("Reusable-Methods", this.program.extractedReusableMethods());
  		// SOF - Scanned source-files
  		resultMap.put("Source-Files", this.program.extractedSourceFiles());
  		// STA - Statements
  		resultMap.put("Statements", this.program.extractedStatements());
  		// STL - String literals
  		resultMap.put("String-Literals", this.program.extractedStringLiterals());
  		// STY - Statement types (HashSet)
  		resultMap.put("Statement-Types", this.program.extractedStatementTypes());
  		// SWC - Switch-cases
  		resultMap.put("Switch-Cases", this.program.extractedSwitchCases());
  		// SWS - Switch-statements
  		resultMap.put("Switch-Statements", this.program.extractedSwitchStatements());
  		// VAR - Variable declarations (fields and local variables)
  		resultMap.put("Variables", this.program.extractedVariables());
  		// VRE - Variable references
  		resultMap.put("Variable-References", this.program.extractedVariableReferences());
  		// VUS - Vulnerable Statements
  		resultMap.put("Vulnerable-Statements", this.program.extractedVulnerableStatements());
  		return resultMap;
  	}

  	@Override
  	protected void processErrors(Collection<Problem> errors, CompilationUnit unit) {
  		// Do nothing. The overwritten implementation produces a lot of error-messages
  		// because the used frameworks aren't measured with the source-files, so extendj 
  		// can't find the used method implementations. These are irrelevant in this context.
  	}
}
