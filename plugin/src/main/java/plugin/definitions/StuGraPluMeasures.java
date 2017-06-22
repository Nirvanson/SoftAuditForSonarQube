package plugin.definitions;

import java.util.Arrays;
import java.util.List;

import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;

/**
 * PlugIn Measure definition.
 *
 * @author Jan Rucks
 * @version 1.0
 */
public class StuGraPluMeasures implements Metrics {
    /**
     * Number of flow lines in control flow graph, exits from a control statement.
     * +2 for each if, loop and try +1 for each case, return and catch
     */
    public static final Metric<Integer> BRA = new Metric.Builder("bra", "Branches", Metric.ValueType.INT)
            .setDescription("Number of branches.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Number of declared Classes. Including enums, inner and anonymous classes.
     */
    public static final Metric<Integer> CLA = new Metric.Builder("cla", "Class-Declarations", Metric.ValueType.INT)
            .setDescription("Number of classes.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Number of different data types used for variables, parameters and return-types.
     */
    public static final Metric<Integer> DTY = new Metric.Builder("dty", "Data-Types", Metric.ValueType.INT)
            .setDescription("Number of data types.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Method calls of methods that aren't declared in scanned project.
     */
    public static final Metric<Integer> FMC = new Metric.Builder("fmc", "Foreign-Method-Calls",
            Metric.ValueType.INT).setDescription("Number of foreign function calls.")
                    .setDirection(Metric.DIRECTION_NONE).setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL)
                    .create();
    /**
     * Global variable declarations (fields).
     */
    public static final Metric<Integer> GVA = new Metric.Builder("gva", "Global-Variables", Metric.ValueType.INT)
            .setDescription("Number of global variables.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Number of If- and Try-Statements.
     */
    public static final Metric<Integer> IFS = new Metric.Builder("ifs", "If-Statements", Metric.ValueType.INT)
            .setDescription("Number of if-statements.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Number of import and package statements.
     */
    public static final Metric<Integer> IMP = new Metric.Builder("imp", "Imports", Metric.ValueType.INT)
            .setDescription("Number of imports.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Number of declared Interfaces and annotations.
     */
    public static final Metric<Integer> INT = new Metric.Builder("int", "Interface-Declarations",
            Metric.ValueType.INT).setDescription("Number of interfaces.").setDirection(Metric.DIRECTION_NONE)
                    .setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Number of Loop-Statements.
     */
    public static final Metric<Integer> LOS = new Metric.Builder("los", "Loop-Statements", Metric.ValueType.INT)
            .setDescription("Number of loop-statements.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Number of Method calls.
     */
    public static final Metric<Integer> MEC = new Metric.Builder("mec", "Method-Calls", Metric.ValueType.INT)
            .setDescription("Number of method calls.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * All variables in a parameter list, i.e. in an interface to a method.
     */
    public static final Metric<Integer> MPA = new Metric.Builder("mpa", "Method-Parameters", Metric.ValueType.INT)
            .setDescription("Number of parameters.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * All variable references that are not predicates.
     */
    public static final Metric<Integer> NPR = new Metric.Builder("npr", "Non-Predicate-References",
            Metric.ValueType.INT).setDescription("Number of non predicate references.")
                    .setDirection(Metric.DIRECTION_NONE).setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL)
                    .create();
    /**
     * Number of numeric values other than 0 or 1 or 2 in the code.
     */
    public static final Metric<Integer> NUL = new Metric.Builder("nul", "Numeric-Literals", Metric.ValueType.INT)
            .setDescription("Number of Constants.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * All variable references in conditional statements like if, switch, for and while.
     */
    public static final Metric<Integer> PRE = new Metric.Builder("pre", "Predicates", Metric.ValueType.INT)
            .setDescription("Number of predicates.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Number of declared private Methods.
     */
    public static final Metric<Integer> PRM = new Metric.Builder("prm", "Private-Method-Declarations",
            Metric.ValueType.INT).setDescription("Number of private methods.").setDirection(Metric.DIRECTION_NONE)
                    .setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Number of declared non-private Methods.
     */
    public static final Metric<Integer> PUM = new Metric.Builder("pum", "Public-Method-Declarations",
            Metric.ValueType.INT).setDescription("Number of public methods.").setDirection(Metric.DIRECTION_NONE)
                    .setQualitative(false).setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Number of Return Statements.
     */
    public static final Metric<Integer> RES = new Metric.Builder("res", "Return-Statements", Metric.ValueType.INT)
            .setDescription("Number of return-statements.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Methods that don't contain a foreign method call.
     */
    public static final Metric<Integer> RUM = new Metric.Builder("rum", "Reusable-Methods", Metric.ValueType.INT)
            .setDescription("Number of reusable methods.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Number of scanned Source-Files.
     */
    public static final Metric<Integer> SOF = new Metric.Builder("sof", "Source-Files", Metric.ValueType.INT)
            .setDescription("Number of source files.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Number of all Statements.
     */
    public static final Metric<Integer> STA = new Metric.Builder("sta", "Statements", Metric.ValueType.INT)
            .setDescription("Number of statements.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Number of embedded Texts in the procedural code.
     */
    public static final Metric<Integer> STL = new Metric.Builder("stl", "String-Literals", Metric.ValueType.INT)
            .setDescription("Number of string literals.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Number of different Java statement types.
     * All ExtendJ-Statement-Types + each "Expression-Statement" as it's own type.
     */
    public static final Metric<Integer> STY = new Metric.Builder("sty", "Statement-Types", Metric.ValueType.INT)
            .setDescription("Number of statement types.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Number of Cases in Switch-Statements. Including default case.
     */
    public static final Metric<Integer> SWC = new Metric.Builder("swc", "Switch-Cases", Metric.ValueType.INT)
            .setDescription("Number of cases in switches.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Number of Switch Statements.
     */
    public static final Metric<Integer> SWS = new Metric.Builder("sws", "Switch-Statements", Metric.ValueType.INT)
            .setDescription("Number of switch statements.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * All variable declarations.
     */
    public static final Metric<Integer> VAR = new Metric.Builder("var", "Variables", Metric.ValueType.INT)
            .setDescription("Number of variables.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Sum of all occurrences of variables in statements (excluding declarations).
     */
    public static final Metric<Integer> VRE = new Metric.Builder("vre", "Variable-References", Metric.ValueType.INT)
            .setDescription("Number of references.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();
    /**
     * Sum vulnerable statements (duplicated constructors, Casts, non final derived classes, public
     * class variables).
     */
    public static final Metric<Integer> VUS = new Metric.Builder("vus", "Vulnerable-Statements", Metric.ValueType.INT)
            .setDescription("Vulnerable statements.").setDirection(Metric.DIRECTION_NONE).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();

    /**
     * Default constructor.
     */
    public StuGraPluMeasures() {
        super();
    }

    /**
     * Returns the measures of the PlugIn.
     *
     * @return list of measures
     */
    @SuppressWarnings("rawtypes")
    public List<Metric> getMetrics() {
        return Arrays.asList(BRA, CLA, DTY, FMC, GVA, IFS, IMP, INT, LOS, MEC, MPA, NPR, NUL, PRE, PRM, PUM, RES, RUM,
                SOF, STA, STL, STY, SWC, SWS, VAR, VRE, VUS);
    }
}
