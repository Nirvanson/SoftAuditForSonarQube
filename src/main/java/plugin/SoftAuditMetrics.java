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

    // SonarQube provided measures

    /**
     * Major Deficiencies. Eventually mapping to SonarQube-RuleSet with Code-Smells metric.
     */
    public static final Metric<Integer> MAD = new Metric.Builder("sonar_mad", "Major Deficiencies",
            Metric.ValueType.INT).setDescription("Number of major deficiencies.").setDirection(Metric.DIRECTION_NONE)
                    .setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Medium Deficiencies. Eventually mapping to SonarQube-RuleSet with Code-Smells metric.
     */
    public static final Metric<Integer> MED = new Metric.Builder("sonar_med", "Medium Deficiencies",
            Metric.ValueType.INT).setDescription("Number of medium deficiencies.").setDirection(Metric.DIRECTION_NONE)
                    .setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Minor Deficiencies. Eventually mapping to SonarQube-RuleSet with Code-Smells metric.
     */
    public static final Metric<Integer> MID = new Metric.Builder("sonar_mid", "Minor Deficiencies",
            Metric.ValueType.INT).setDescription("Number of minor deficiencies.").setDirection(Metric.DIRECTION_NONE)
                    .setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Security Deficiencies. Eventually mapping to SonarQube-RuleSet with Code-Smells metric.
     */
    public static final Metric<Integer> SED = new Metric.Builder("sonar_sed", "Security Deficiencies",
            Metric.ValueType.INT).setDescription("Number of security deficiencies.").setDirection(Metric.DIRECTION_NONE)
                    .setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Number of Secure statements. All Statements that DON'T contain: - Constructor Methods that
     * are duplicated - Return Values not controlled after method invocation - Class Variables
     * should that are declared public - Derived class that is not declared as final - Input
     * Parameter of a Public Method not checked - SQL Statement in Java method - Object compared
     * with a literal - serialzeable Class definition  - cloneable class definition - Casting of a
     * variable - Return Value not checked - Embedded SQL function
     */
    public static final Metric<Integer> SST = new Metric.Builder("sonar_sst", "Secure Statements", Metric.ValueType.INT)
            .setDescription("Number of secure statements.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();

    // Measures

    /**
     * All variables whose values are used to set or change other variables, includes the input
     * parameters.
     */
    public static final Metric<Integer> ARG = new Metric.Builder("base_arg", "Arguments", Metric.ValueType.INT)
            .setDescription("Number of arguments.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Number of flow lines in control flow graph, exits from a control statement. Eventually
     * Mapping to „Conditions to cover“ for Unit Tests in CoreMetrics.
     */
    public static final Metric<Integer> BRA = new Metric.Builder("base_bra", "Branches", Metric.ValueType.INT)
            .setDescription("Number of branches.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Number of Cases in Switch-Statements.
     */
    public static final Metric<Integer> CAS = new Metric.Builder("base_cas", "Cases", Metric.ValueType.INT)
            .setDescription("Number of cases in switches.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Number of Classes. Good mapping to CoreMetrics.
     */
    public static final Metric<Integer> CLA = new Metric.Builder("base_cla", "Classes", Metric.ValueType.INT)
            .setDescription("Number of classes.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Number of numeric values other than 0 or 1 or 2 in the code.
     */
    public static final Metric<Integer> CON = new Metric.Builder("base_con", "Constants", Metric.ValueType.INT)
            .setDescription("Number of Constants.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Number of If-Statements.
     */
    public static final Metric<Integer> IFS = new Metric.Builder("base_ifs", "If-Statements", Metric.ValueType.INT)
            .setDescription("Number of if-statements.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Number of Interfaces.
     */
    public static final Metric<Integer> INT = new Metric.Builder("base_int", "Interfaces", Metric.ValueType.INT)
            .setDescription("Number of interfaces.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Number of embedded Texts in the procedural code.
     */
    public static final Metric<Integer> LIT = new Metric.Builder("base_lit", "Literals", Metric.ValueType.INT)
            .setDescription("Number of literals.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Number of Loop-Statements.
     */
    public static final Metric<Integer> LOP = new Metric.Builder("base_lop", "Loop-Statements", Metric.ValueType.INT)
            .setDescription("Number of loop-statements.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Number of Methods / Procedures / Functions. Good mapping to CoreMetrics.
     */
    public static final Metric<Integer> MET = new Metric.Builder("base_met", "Methods", Metric.ValueType.INT)
            .setDescription("Number of methods.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Number of Methods / Procedures / Functions. Good mapping to CoreMetrics.
     */
    public static final Metric<Integer> PME = new Metric.Builder("base_pme", "Private Methods", Metric.ValueType.INT)
            .setDescription("Number of private methods.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * All variables in a parameter list, i.e. in an interface to a method.
     */
    public static final Metric<Integer> PAR = new Metric.Builder("base_par", "Parameters", Metric.ValueType.INT)
            .setDescription("Number of parameters.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * All conditional operands, i.e. variables in conditional statements like if, switch, for,
     * while, until.
     */
    public static final Metric<Integer> PRE = new Metric.Builder("base_pre", "Predicates", Metric.ValueType.INT)
            .setDescription("Number of predicates.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Sum of all occurrences of variables in statements.
     */
    public static final Metric<Integer> REF = new Metric.Builder("base_ref", "References", Metric.ValueType.INT)
            .setDescription("Number of references.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Number of Return Statements.
     */
    public static final Metric<Integer> RET = new Metric.Builder("base_ret", "Return-Statements", Metric.ValueType.INT)
            .setDescription("Number of return-statements.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Sum of all code lines ending with "{" or ";". Mapping with other definition in CoreMetrics.
     */
    public static final Metric<Integer> STM = new Metric.Builder("base_stm", "Statements", Metric.ValueType.INT)
            .setDescription("Number of statements.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Number Switch Statements.
     */
    public static final Metric<Integer> SWI = new Metric.Builder("base_swi", "Switch-Statements", Metric.ValueType.INT)
            .setDescription("Number of switch statements.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * All data attribute declarations.
     */
    public static final Metric<Integer> VAR = new Metric.Builder("base_var", "Variables", Metric.ValueType.INT)
            .setDescription("Number of variables.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * All data attribute declarations.
     */
    public static final Metric<Integer> GVA = new Metric.Builder("base_gva", "Global Variables", Metric.ValueType.INT)
            .setDescription("Number of global variables.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Number of different variables types declared like int, float, string, long, user defined.
     */
    public static final Metric<Integer> DTY = new Metric.Builder("base_dty", "Data Types", Metric.ValueType.INT)
            .setDescription("Number of data types.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Functions referenced which are outside of the class where the function is referenced.
     */
    public static final Metric<Integer> FFC = new Metric.Builder("base_ffc", "Foreign-Function-Calls",
            Metric.ValueType.INT).setDescription("Number of foreign function calls.")
                    .setDirection(Metric.DIRECTION_NONE).setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL)
                    .create();
    /**
     * Function references / Method calls.
     */
    public static final Metric<Integer> FUC = new Metric.Builder("base_fuc", "Function-Calls", Metric.ValueType.INT)
            .setDescription("Number of function calls.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Number of imports.
     */
    public static final Metric<Integer> IMP = new Metric.Builder("base_imp", "Imports", Metric.ValueType.INT)
            .setDescription("Number of imports.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Methods without a reference to a foreign method or a framework function & without inheriting
     * anything.
     */
    public static final Metric<Integer> RUM = new Metric.Builder("base_rum", "Reusable Methods", Metric.ValueType.INT)
            .setDescription("Number of reusable methods.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Number of Source-Files. Good mapping to CoreMetrics.
     */
    public static final Metric<Integer> SRC = new Metric.Builder("base_src", "Source-Files", Metric.ValueType.INT)
            .setDescription("Number of modules.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Number of different Java statement types like if, for, while, switch, return, break, function
     * call, etc.
     */
    public static final Metric<Integer> STY = new Metric.Builder("base_sty", "Statement-Types", Metric.ValueType.INT)
            .setDescription("Number of statement types.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Optimal Module Size, default 200. Constant... no measurement.
     */
    public static final Metric<Integer> OMS = new Metric.Builder("base_oms", "Optimal Module Size",
            Metric.ValueType.INT).setDescription("Optimal module size.").setDirection(Metric.DIRECTION_NONE)
                    .setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL).create();

    // Metrics

    /**
     * Object Points = (CLA * 4) + (MET * 3) + (INT * 2) + VAR
     */
    public static final Metric<Integer> OBP = new Metric.Builder("size_obp", "Object Points", Metric.ValueType.INT)
            .setDescription("Computed object points.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Data Complexity (Based on Chapin) = ((PRE * 2) + (RES * 1,5) + ARG + (PAR * 0,5)) / (STM +
     * REF)
     */
    public static final Metric<String> DCO = new Metric.Builder("complexity_dco", "Data Complexity",
            Metric.ValueType.STRING).setDescription("Computed data complexity (Chapin).")
                    .setDirection(Metric.DIRECTION_WORST).setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL)
                    .create();
    /**
     * Data Flow Complexity (Based on Elshof) = 1 – ((VAR * 2) / REF)
     */
    public static final Metric<String> DFC = new Metric.Builder("complexity_dfc", "Data Flow Complexity",
            Metric.ValueType.STRING).setDescription("Computed data flow complexity (Elshof).")
                    .setDirection(Metric.DIRECTION_WORST).setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL)
                    .create();
    /**
     * Control Flow Complexity (Based on McCabe) = (BRA – (IFS + SWI + LOP + RET)) / BRA
     */
    public static final Metric<String> CFC = new Metric.Builder("complexity_cfc", "Control Flow Complexity",
            Metric.ValueType.STRING).setDescription("Computed control flow complexity (McCabe).")
                    .setDirection(Metric.DIRECTION_WORST).setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL)
                    .create();
    /**
     * Conditional Complexity (Based on McClure) = (IFS + SWI + CAS + LOP + 1) / (MET * 4)
     */
    public static final Metric<String> COC = new Metric.Builder("complexity_coc", "Conditional Complexity",
            Metric.ValueType.STRING).setDescription("Computed conditional complexity (McClure).")
                    .setDirection(Metric.DIRECTION_WORST).setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL)
                    .create();
    /**
     * Testability = ((1 – ((BRA * 2) / STM)) + (1 – ((PRE * 2) / REF))) / 2
     */
    public static final Metric<String> TST = new Metric.Builder("quality_tst", "Testability", Metric.ValueType.STRING)
            .setDescription("Computed testability.").setDirection(Metric.DIRECTION_BETTER).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Flexibility = 1 – ((LIT + CON) / STM)
     */
    public static final Metric<String> FLE = new Metric.Builder("quality_fle", "Flexibility", Metric.ValueType.STRING)
            .setDescription("Computed flexibility.").setDirection(Metric.DIRECTION_BETTER).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Conformity = 1 – (((SED * 2) + (MAD * 1,5) + MED + (MID * 0,5)) / STM)
     */
    public static final Metric<String> COF = new Metric.Builder("quality_cof", "Conformity", Metric.ValueType.STRING)
            .setDescription("Computed conformity.").setDirection(Metric.DIRECTION_BETTER).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Interface Complexity (Based on Henry) = FFC / FUC
     */
    public static final Metric<String> ICO = new Metric.Builder("complexity_ico", "Interface Complexity",
            Metric.ValueType.STRING).setDescription("Computed interface complexity (Henry).")
                    .setDirection(Metric.DIRECTION_WORST).setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL)
                    .create();
    /**
     * Branching Complexity (Based on Sneed) = ((FFC * 2) + (RET * 2) + FUC) / STM
     */
    public static final Metric<String> BRC = new Metric.Builder("complexity_brc", "Branching Complexity",
            Metric.ValueType.STRING).setDescription("Computed branching complexity (Sneed).")
                    .setDirection(Metric.DIRECTION_WORST).setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL)
                    .create();
    /**
     * Language Complexity Medium Level (Based on Halstead) = ((STY / STM) + (DTY / (VAR + CON))) /
     * 2
     */
    public static final Metric<String> LCM = new Metric.Builder("complexity_lcm", "Language Complexity",
            Metric.ValueType.STRING).setDescription("Computed language complexity (Halstead).")
                    .setDirection(Metric.DIRECTION_WORST).setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL)
                    .create();
    /**
     * Average Complexity Medium Level = (DCO + DFC + CFC + COC + ICO + BRC + LCM) / 7
     */
    public static final Metric<String> ACM = new Metric.Builder("complexity_acm", "Average Complexity",
            Metric.ValueType.STRING).setDescription("Computed average complexity.").setDirection(Metric.DIRECTION_WORST)
                    .setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Modularity = ((((CLA * 4) + (MET * 2)) / ((IMP * 4) + VAR)) + (1 – (FFC / (FUC + MET))) +
     * ((STM / SRC) / OMS)) / 3
     */
    public static final Metric<String> MOD = new Metric.Builder("quality_mod", "Modularity", Metric.ValueType.STRING)
            .setDescription("Computed modularity.").setDirection(Metric.DIRECTION_BETTER).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Reusability = RUM / MET
     */
    public static final Metric<String> REU = new Metric.Builder("quality_reu", "Reuseability", Metric.ValueType.STRING)
            .setDescription("Computed reusability.").setDirection(Metric.DIRECTION_BETTER).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Security = (SST / 1,2) / STM
     */
    public static final Metric<String> SEC = new Metric.Builder("quality_sec", "Security", Metric.ValueType.STRING)
            .setDescription("Computed security.").setDirection(Metric.DIRECTION_BETTER).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Maintainability Medium Level = ((1 – ACM) + ((MOD + TST + REU + SEC + FLE + (COF / 2)) / 6))
     * / 2
     */
    public static final Metric<String> MAM = new Metric.Builder("quality_mam", "Maintainability",
            Metric.ValueType.STRING).setDescription("Computed maintainability.").setDirection(Metric.DIRECTION_BETTER)
                    .setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Average Quality Medium Level = (MOD + TST + REU + SEC + FLE + COF + MAB) / 7
     */
    public static final Metric<String> AQM = new Metric.Builder("quality_aqm", "Average Quality",
            Metric.ValueType.STRING).setDescription("Computed average quality.").setDirection(Metric.DIRECTION_BETTER)
                    .setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL).create();

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
        return Arrays.asList(ARG, BRA, CAS, CLA, CON, IFS, INT, LIT, LOP, MAD, MED, MET, MID, PAR, PRE, REF, RET, SED,
                STM, SWI, VAR, DTY, FFC, FUC, IMP, RUM, SRC, SST, STY, OMS, OBP, DCO, DFC, CFC, COC, ICO, BRC, LCM, ACM,
                TST, FLE, COF, MOD, REU, SEC, MAM, AQM, PME, GVA);

    }
}
