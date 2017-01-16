package plugin;

import java.util.Arrays;
import java.util.List;

import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;

/**
 * SoftAudit Metrics definition.
 *
 * @author Jan Rucks
 * @version 0.1
 */
public class SoftAuditMetrics implements Metrics {

	private static final Integer level = 2;
	
	// Level 1 Measures (Minimum needed for 9 metrics scope of the plugin)
    
    /**
     * All variables whose values are used to set or change other variables, includes the input parameters.
     */
    public static final Metric<Integer> ARG = new Metric.Builder("base_arg", "Arguments", Metric.ValueType.INT)
			.setDescription("Number of arguments.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
    /**
     * Number of flow lines in control flow graph, exits from a control statement.
     * Eventually Mapping to „Conditions to cover“ for Unit Tests in CoreMetrics.
     */
    public static final Metric<Integer> BRA = new Metric.Builder("base_bra", "Branches", Metric.ValueType.INT)
			.setDescription("Number of branches.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
    /**
     * Number of Cases in Switch-Statements.
     */
    public static final Metric<Integer> CAS = new Metric.Builder("base_cas", "Cases", Metric.ValueType.INT)
			.setDescription("Number of cases in switches.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
    /**
     * Number of Classes. Good mapping to CoreMetrics.
     */
    public static final Metric<Integer> CLA = new Metric.Builder("base_cla", "Classes", Metric.ValueType.INT)
			.setDescription("Number of classes.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
    /**
     * Number of numeric values other than 0 or 1 or 2 in the code.
     */
    public static final Metric<Integer> CON = new Metric.Builder("base_con", "Constants", Metric.ValueType.INT)
			.setDescription("Number of Constants.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
    /**
     * Number of If-Statements.
     */
	public static final Metric<Integer> IFS = new Metric.Builder("base_ifs", "If-Statements", Metric.ValueType.INT)
			.setDescription("Number of if-statements.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
     * Number of Interfaces.
     */
	public static final Metric<Integer> INT = new Metric.Builder("base_int", "Interfaces", Metric.ValueType.INT)
			.setDescription("Number of interfaces.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Number of embedded Texts in the procedural code.
	 */
	public static final Metric<Integer> LIT = new Metric.Builder("base_lit", "Literals", Metric.ValueType.INT)
			.setDescription("Number of literals.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
     * Number of Loop-Statements.
     */
	public static final Metric<Integer> LOP = new Metric.Builder("base_lop", "Loop-Statements", Metric.ValueType.INT)
			.setDescription("Number of loop-statements.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Major Deficiencies. Eventually mapping to SonarQube-RuleSet with Code-Smells metric.
	 */
	public static final Metric<Integer> MAD = new Metric.Builder("base_mad", "Major Deficiencies", Metric.ValueType.INT)
			.setDescription("Number of major deficiencies.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Medium Deficiencies. Eventually mapping to SonarQube-RuleSet with Code-Smells metric.
	 */
	public static final Metric<Integer> MED = new Metric.Builder("base_med", "Medium Deficiencies", Metric.ValueType.INT)
			.setDescription("Number of medium deficiencies.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Number of Methods / Procedures / Functions. Good mapping to CoreMetrics.
	 */
	public static final Metric<Integer> MET = new Metric.Builder("base_met", "Methods", Metric.ValueType.INT)
			.setDescription("Number of methods.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Minor Deficiencies. Eventually mapping to SonarQube-RuleSet with Code-Smells metric.
	 */
	public static final Metric<Integer> MID = new Metric.Builder("base_mid", "Minor Deficiencies", Metric.ValueType.INT)
			.setDescription("Number of minor deficiencies.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * All variables in a parameter list, i.e. in an interface to a method.
	 */
	public static final Metric<Integer> PAR = new Metric.Builder("base_par", "Parameters", Metric.ValueType.INT)
			.setDescription("Number of parameters.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * All conditional operands, i.e. variables in conditional statements like if,  switch, for, while, until.
	 */
    public static final Metric<Integer> PRE = new Metric.Builder("base_pre", "Predicates", Metric.ValueType.INT)
			.setDescription("Number of predicates.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
    /**
     * Sum of all occurrences of variables in statements.
     */
    public static final Metric<Integer> REF = new Metric.Builder("base_ref", "References", Metric.ValueType.INT)
			.setDescription("Number of references.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
    /**
     * All variables whose values are set or changed, includes the return values.
     */
	public static final Metric<Integer> RES = new Metric.Builder("base_res", "Results", Metric.ValueType.INT)
			.setDescription("Number of results.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Number of Return Statements.
	 */
	public static final Metric<Integer> RET = new Metric.Builder("base_ret", "Return-Statements", Metric.ValueType.INT)
			.setDescription("Number of return-statements.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Security Deficiencies. Eventually mapping to SonarQube-RuleSet with Code-Smells metric.
	 */
	public static final Metric<Integer> SED = new Metric.Builder("base_sed", "Security Deficiencies", Metric.ValueType.INT)
			.setDescription("Number of security deficiencies.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Sum of all code lines ending with "{" or ";". Mapping with other definition in CoreMetrics.
	 */
	public static final Metric<Integer> STM = new Metric.Builder("base_stm", "Statements", Metric.ValueType.INT)
			.setDescription("Number of statements.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Number Switch Statements.
	 */
	public static final Metric<Integer> SWI = new Metric.Builder("base_swi", "Switch-Statements", Metric.ValueType.INT)
			.setDescription("Number of switch statements.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * All data attribute declarations.
	 */
	public static final Metric<Integer> VAR = new Metric.Builder("base_var", "Variables", Metric.ValueType.INT)
			.setDescription("Number of variables.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	
	// Level 2 Measures (additionally needed for 15 metrics scope of the plugin)
	
	/**
	 * Number of  different variables types declared like int, float, string, long, user defined.
	 */
	public static final Metric<Integer> DTY = new Metric.Builder("base_dty", "Data Types", Metric.ValueType.INT)
			.setDescription("Number of data types.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Functions referenced which are outside of the class where the function is referenced.
	 */
	public static final Metric<Integer> FFC = new Metric.Builder("base_ffc", "Foreign-Function-Calls", Metric.ValueType.INT)
			.setDescription("Number of foreign function calls.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Function references / Method calls.
	 */
	public static final Metric<Integer> FUC = new Metric.Builder("base_fuc", "Function-Calls", Metric.ValueType.INT)
			.setDescription("Number of function calls.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Number of imports.
	 */
	public static final Metric<Integer> IMP = new Metric.Builder("base_imp", "Imports", Metric.ValueType.INT)
			.setDescription("Number of imports.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Methods without a reference to a foreign method or a framework function & without inheriting anything.
	 */
	public static final Metric<Integer> RUM = new Metric.Builder("base_rum", "Reusable Methods", Metric.ValueType.INT)
			.setDescription("Number of reusable methods.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Number of Source-Files. Good mapping to CoreMetrics.
	 */
	public static final Metric<Integer> SRC = new Metric.Builder("base_src", "Source-Files", Metric.ValueType.INT)
			.setDescription("Number of modules.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Number of Secure statements. All Statements that DON'T contain:
	 * - Constructor Methods that are duplicated
	 * - Return Values not controlled after method invocation
	 * - Class Variables should that are declared public
	 * - Derived class that is not declared as final
	 * - Input Parameter of a Public Method not checked
	 * - SQL Statement in Java method
	 * - Object compared with a literal
	 * - serialzeable Class definition 
	 * - cloneable class definition 
	 * - Casting of a variable
	 * - Return Value not checked
	 * - Embedded SQL function
	 */
	public static final Metric<Integer> SST = new Metric.Builder("base_sst", "Secure Statements", Metric.ValueType.INT)
			.setDescription("Number of secure statements.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Number of different Java statement types like if, for, while, switch, return, break, function call, etc.
	 */
	public static final Metric<Integer> STY = new Metric.Builder("base_sty", "Statement-Types", Metric.ValueType.INT)
			.setDescription("Number of statement types.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Optimal Module Size, default 200. Constant... no measurement.
	 */
	public static final Metric<Integer> OMS = new Metric.Builder("base_oms", "Optimal Module Size", Metric.ValueType.INT)
			.setDescription("Optimal module size.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	
	// Level 3 Measures (additionally needed for all (19) metrics scope of the plugin)
	
	/**
	 * Create / Read / Update / Delete statements on DB.
	 */
	public static final Metric<Integer> DBA = new Metric.Builder("base_dba", "Database Accesses", Metric.ValueType.INT)
			.setDescription("Number of database accesses.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Number of Open Database Operations.
	 */
	public static final Metric<Integer> DBS = new Metric.Builder("base_dbs", "Databases", Metric.ValueType.INT)
			.setDescription("Number of databases.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Number of Defines: user defined macros which are expanded before compilation.
	 */
	public static final Metric<Integer> DEF = new Metric.Builder("base_def", "Defines", Metric.ValueType.INT)
			.setDescription("Number of defines.") 
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Number of File Operations – Read Write Delete.
	 */
	public static final Metric<Integer> FIA = new Metric.Builder("base_fia", "File Accesses", Metric.ValueType.INT)
			.setDescription("Number of file accesses.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Number of Open File Operations.
	 */
	public static final Metric<Integer> FIL = new Metric.Builder("base_fil", "Files", Metric.ValueType.INT)
			.setDescription("Number of files.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Number of Inputs.
	 */
	public static final Metric<Integer> INP = new Metric.Builder("base_inp", "Inputs", Metric.ValueType.INT)
			.setDescription("Number of inputs.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Number of Outputs.
	 */
	public static final Metric<Integer> OUT = new Metric.Builder("base_out", "Outputs", Metric.ValueType.INT)
			.setDescription("Number of outputs.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Number of User Interface Windows (HTMLs, XSLs, Paints).
	 */
	public static final Metric<Integer> PAN = new Metric.Builder("base_pan", "Panels", Metric.ValueType.INT)
			.setDescription("Number of panels.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Number of Print functions.
	 */
	public static final Metric<Integer> REP = new Metric.Builder("base_rep", "Reports", Metric.ValueType.INT)
			.setDescription("Number of reports.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * User Framework method calls, method calls outside the measured code (→ framework).
	 */
	public static final Metric<Integer> UFM = new Metric.Builder("base_ufm", "User-Framework-Calls", Metric.ValueType.INT)
			.setDescription("Number user-framework-calls.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Number of Views.
	 */
	public static final Metric<Integer> VIE = new Metric.Builder("base_vie", "Views", Metric.ValueType.INT)
			.setDescription("Number views.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	
	// Level 1 Metrics
	
	/**
	 * Object Points = (CLA * 4) + (MET * 3) + (INT * 2) + VAR
	 */
	public static final Metric<Integer> OBP = new Metric.Builder("size_obp", "Object Points", Metric.ValueType.INT)
			.setDescription("Computed object points.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Data Complexity (Based on Chapin) = ((PRE * 2) + (RES * 1,5) + ARG + (PAR * 0,5)) / (STM + REF)
	 */
	public static final Metric<Integer> DCO = new Metric.Builder("complexity_dco", "Data Complexity", Metric.ValueType.FLOAT)
			.setDescription("Computed data complexity (Chapin).")
			.setDirection(Metric.DIRECTION_WORST)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Data Flow Complexity (Based on Elshof) = 1 – ((VAR * 2) / REF)
	 */
	public static final Metric<Integer> DFC = new Metric.Builder("complexity_dfc", "Data Flow Complexity", Metric.ValueType.FLOAT)
			.setDescription("Computed data flow complexity (Elshof).")
			.setDirection(Metric.DIRECTION_WORST)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Control Flow Complexity (Based on McCabe) = (BRA – (IFS + SWI + LOP + RET)) / BRA
	 */
	public static final Metric<Integer> CFC = new Metric.Builder("complexity_cfc", "Control Flow Complexity", Metric.ValueType.FLOAT)
			.setDescription("Computed control flow complexity (McCabe).")
			.setDirection(Metric.DIRECTION_WORST)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Conditional Complexity (Based on McClure) = (IFS + SWI + CAS + LOP + 1) / (MET * 4)
	 */
	public static final Metric<Integer> COC = new Metric.Builder("complexity_coc", "Conditional Complexity", Metric.ValueType.FLOAT)
			.setDescription("Computed conditional complexity (McClure).")
			.setDirection(Metric.DIRECTION_WORST)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Average Complexity Base Level = (DCO + DFC + CFC + COC) / 4
	 */
	public static final Metric<Integer> ACB = new Metric.Builder("complexity_acb", "Average Complexity", Metric.ValueType.FLOAT)
			.setDescription("Computed average complexity.")
			.setDirection(Metric.DIRECTION_WORST)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Testability = ((1 – ((BRA * 2) / STM)) + (1 – ((PRE * 2) / REF))) / 2
	 */
	public static final Metric<Integer> TST = new Metric.Builder("quality_tst", "Testability", Metric.ValueType.FLOAT)
			.setDescription("Computed testability.")
			.setDirection(Metric.DIRECTION_BETTER)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Flexibility = 1 – ((LIT + CON) / STM)
	 */
	public static final Metric<Integer> FLE = new Metric.Builder("quality_fle", "Flexibility", Metric.ValueType.FLOAT)
			.setDescription("Computed flexibility.")
			.setDirection(Metric.DIRECTION_BETTER)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Conformity = 1 – (((SED * 2) + (MAD * 1,5) + MED + (MID * 0,5)) / STM)
	 */
	public static final Metric<Integer> COF = new Metric.Builder("quality_cof", "Conformity", Metric.ValueType.FLOAT)
			.setDescription("Computed conformity.")
			.setDirection(Metric.DIRECTION_BETTER)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Maintainability Base Level = ((1 – ACB) + ((TST + FLE + (COF / 2)) / 3)) / 2
	 */
	public static final Metric<Integer> MAB = new Metric.Builder("quality_mab", "Maintainability", Metric.ValueType.FLOAT)
			.setDescription("Computed maintainability.")
			.setDirection(Metric.DIRECTION_BETTER)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Average Quality Base Level = (TST + FLE + COF + MAB) / 4
	 */
	public static final Metric<Integer> AQB = new Metric.Builder("quality_aqb", "Average Quality", Metric.ValueType.FLOAT)
			.setDescription("Computed average quality.")
			.setDirection(Metric.DIRECTION_BETTER)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	
	// Level 2 Metrics
	
	/**
	 * Interface Complexity (Based on Henry) = FFC / FUC
	 */
	public static final Metric<Integer> ICO = new Metric.Builder("complexity_ico", "Interface Complexity", Metric.ValueType.FLOAT)
			.setDescription("Computed interface complexity (Henry).")
			.setDirection(Metric.DIRECTION_WORST)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Branching Complexity (Based on Sneed) = ((FFC * 2) + (RET * 2) + FUC) / STM
	 */
	public static final Metric<Integer> BRC = new Metric.Builder("complexity_brc", "Branching Complexity", Metric.ValueType.FLOAT)
			.setDescription("Computed branching complexity (Sneed).")
			.setDirection(Metric.DIRECTION_WORST)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Language Complexity Medium Level (Based on Halstead) = ((STY / STM) + (DTY / (VAR + CON))) / 2
	 */
	public static final Metric<Integer> LCM = new Metric.Builder("complexity_lcm", "Language Complexity", Metric.ValueType.FLOAT)
			.setDescription("Computed language complexity (Halstead).")
			.setDirection(Metric.DIRECTION_WORST)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();	
	/**
	 * Average Complexity Medium Level = (DCO + DFC + CFC + COC + ICO + BRC + LCM) / 7
	 */
	public static final Metric<Integer> ACM = new Metric.Builder("complexity_acm", "Average Complexity", Metric.ValueType.FLOAT)
			.setDescription("Computed average complexity.")
			.setDirection(Metric.DIRECTION_WORST)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Modularity = ((((CLA * 4) + (MET * 2)) / ((IMP * 4) + VAR)) + (1 – (FFC / (FUC + MET))) + ((STM / SRC) / OMS)) / 3
	 */
	public static final Metric<Integer> MOD = new Metric.Builder("quality_mod", "Modularity", Metric.ValueType.FLOAT)
			.setDescription("Computed modularity.")
			.setDirection(Metric.DIRECTION_BETTER)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Reusability = RUM / MET
	 */
	public static final Metric<Integer> REU = new Metric.Builder("quality_reu", "Reuseability", Metric.ValueType.FLOAT)
			.setDescription("Computed reusability.")
			.setDirection(Metric.DIRECTION_BETTER)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Security = (SST / 1,2) / STM
	 */
	public static final Metric<Integer> SEC = new Metric.Builder("quality_sec", "Security", Metric.ValueType.FLOAT)
			.setDescription("Computed security.")
			.setDirection(Metric.DIRECTION_BETTER)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Maintainability Medium Level = ((1 – ACM) + ((MOD + TST + REU + SEC + FLE + (COF / 2)) / 6)) / 2
	 */
	public static final Metric<Integer> MAM = new Metric.Builder("quality_mam", "Maintainability", Metric.ValueType.FLOAT)
			.setDescription("Computed maintainability.")
			.setDirection(Metric.DIRECTION_BETTER)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Average Quality Medium Level = (MOD + TST + REU + SEC + FLE + COF + MAB) / 7
	 */
	public static final Metric<Integer> AQM = new Metric.Builder("quality_aqm", "Average Quality", Metric.ValueType.FLOAT)
			.setDescription("Computed average quality.")
			.setDirection(Metric.DIRECTION_BETTER)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
    
	// Level 3 Metrics
	
	/**
	 * Data Points = VAR + (VIE * 4) + (REP * 4) + (PAN * 4) + (FIL * 4) + (DBS * 4)
	 */
	public static final Metric<Integer> DAP = new Metric.Builder("size_dap", "Data Points", Metric.ValueType.INT)
			.setDescription("Computed data points.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Function Points = (DBS * x) + (FIL * y) + (PAN * z) + (REP * z)
	 */
	public static final Metric<Integer> FUP = new Metric.Builder("size_fup", "Function Points", Metric.ValueType.INT)
			.setDescription("Computed function points.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Data Access Complexity (Based on Card) = 1 – (FIL + DBS) / (FIL + DBS + FIA + DBA)
	 */
	public static final Metric<Integer> DAC = new Metric.Builder("complexity_dac", "Data Access Complexity", Metric.ValueType.FLOAT)
			.setDescription("Computed data access complexity (Card).")
			.setDirection(Metric.DIRECTION_WORST)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();	
	/**
	 * Language Complexity Full Level (Based on Halstead) = ((STY / STM) + (DTY / (VAR + CON + DEF))) / 2
	 */
	public static final Metric<Integer> LCF = new Metric.Builder("complexity_lcf", "Language Complexity", Metric.ValueType.FLOAT)
			.setDescription("Computed language complexity (Halstead).")
			.setDirection(Metric.DIRECTION_WORST)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Average Complexity Full Level = (DCO + DFC + CFC + COC + ICO + BRC + LCM + DAC) / 8
	 */
	public static final Metric<Integer> ACF = new Metric.Builder("complexity_acf", "Average Complexity", Metric.ValueType.FLOAT)
			.setDescription("Computed average complexity.")
			.setDirection(Metric.DIRECTION_WORST)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Portability = 1 – (((UFM * 2) + (INP * 4) + (OUT * 4) + (FIA * 4) + (DBA * 4) + (FIL * 8) + (REP * 8) + (PAN * 8) + (DBS * 8)) / STM)
	 */
	public static final Metric<Integer> POR = new Metric.Builder("quality_por", "Portability", Metric.ValueType.FLOAT)
			.setDescription("Computed portability.")
			.setDirection(Metric.DIRECTION_BETTER)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Maintainability Full Level = ((1 – ACF) + ((MOD + TST + REU + SEC + FLE + POR + (COF / 2)) / 7)) / 2
	 */
	public static final Metric<Integer> MAF = new Metric.Builder("quality_maf", "Maintainability", Metric.ValueType.FLOAT)
			.setDescription("Computed maintainability.")
			.setDirection(Metric.DIRECTION_BETTER)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	/**
	 * Average Quality Full Level = (MOD + TST + REU + SEC + FLE + COF + MAB + POR) / 8
	 */
	public static final Metric<Integer> AQF = new Metric.Builder("quality_aqf", "Average Quality", Metric.ValueType.FLOAT)
			.setDescription("Computed average quality.")
			.setDirection(Metric.DIRECTION_BETTER)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	
	/**
     * Default constructor.
     */
    public SoftAuditMetrics() {
        super();
    }
	
    /**
     * Defines the plugin metrics.
     *
     * @return the list of this plugin metrics
     */
    @SuppressWarnings("rawtypes")
	public List<Metric> getMetrics() {
    	switch(level) {
    	case 1:
    		// Metrics-List with base level metrics
    		return Arrays.asList(
        			ARG, BRA, CAS, CLA, CON, IFS, INT, LIT, LOP, MAD, MED, MET, MID, PAR, PRE, REF, RES,
        			RET, SED, STM, SWI, VAR, OBP, DCO, DFC, CFC, COC, ACB, TST, FLE, COF, MAB, AQB);
    	case 2:
    		// Metrics-list with medium level metrics
    		return Arrays.asList(
        			ARG, BRA, CAS, CLA, CON, IFS, INT, LIT, LOP, MAD, MED, MET, MID, PAR, PRE, REF, RES,
        			RET, SED, STM, SWI, VAR, DTY, FFC, FUC, IMP, RUM, SRC, SST, STY, OMS, OBP, DCO, DFC,
        			CFC, COC, ICO, BRC, LCM, ACM, TST, FLE, COF, MOD, REU, SEC, MAM, AQM);
    	case 3:
    		// Metrics-list with full level metrics
    		return Arrays.asList(
        			ARG, BRA, CAS, CLA, CON, IFS, INT, LIT, LOP, MAD, MED, MET, MID, PAR, PRE, REF, RES,
        			RET, SED, STM, SWI, VAR, DTY, FFC, FUC, IMP, RUM, SRC, SST, STY, OMS, DBA, DBS, DEF,
        			FIA, FIL, INP, OUT, PAN, REP, UFM, VIE, OBP, DAP, FUP, DCO, DFC, CFC, COC, ICO, BRC,
        			DAC, LCF, ACF, TST, FLE, COF, MOD, REU, SEC, POR, MAF, AQF);
    	default:
    			return null;
    	}
    }
}
