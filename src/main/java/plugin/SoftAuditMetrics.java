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

	// Sample Metric TODO: remove as soon as own metrics are measured
	
    /**
     * The project name (as configured in the IDE).
     */
    public static final Metric<String> IDE_PRO_NAME =
        new Metric.Builder(
            "ide_pro_name",
            "Project name in IDE",
            Metric.ValueType.STRING)
            .setDescription("The project name (as configured in the IDE)")
            .setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL)
            .create();

    //SoftAudit Measures TODO: Add computed metrics

    public static final Metric<Integer> PREDICATE_COUNT = new Metric.Builder("predicate_count", "Predicate Count", Metric.ValueType.INT)
			.setDescription("Number of predicates.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	public static final Metric<Integer> RESULT_COUNT = new Metric.Builder("result_count", "Result Count", Metric.ValueType.INT)
			.setDescription("Number of results.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	public static final Metric<Integer> ARGUMENT_COUNT = new Metric.Builder("argument_count", "Argument Count", Metric.ValueType.INT)
			.setDescription("Number of arguments.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	public static final Metric<Integer> PARAMETER_COUNT = new Metric.Builder("parameter_count", "Parameter Count", Metric.ValueType.INT)
			.setDescription("Number of parameters.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	public static final Metric<Integer> STATEMENT_COUNT = new Metric.Builder("statement_count", "Statement Count", Metric.ValueType.INT)
			.setDescription("Number of statements.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	public static final Metric<Integer> REFERENCE_COUNT = new Metric.Builder("reference_count", "Reference Count", Metric.ValueType.INT)
			.setDescription("Number of references.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	public static final Metric<Integer> VARIABLE_COUNT = new Metric.Builder("variable_count", "Variable Count", Metric.ValueType.INT)
			.setDescription("Number of variables.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	public static final Metric<Integer> FILE_COUNT = new Metric.Builder("file_count", "File Count", Metric.ValueType.INT)
			.setDescription("Number of files.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	public static final Metric<Integer> DATABASE_COUNT = new Metric.Builder("database_count", "Database Count", Metric.ValueType.INT)
			.setDescription("Number of databases.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	public static final Metric<Integer> FILE_ACCESS_COUNT = new Metric.Builder("file_access_count", "File access Count", Metric.ValueType.INT)
			.setDescription("Number of file accesses.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	public static final Metric<Integer> DATABASE_ACCESS_COUNT = new Metric.Builder("database_access_count", "Database access Count", Metric.ValueType.INT)
			.setDescription("Number of database accesses.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	public static final Metric<Integer> FOREIGN_FUNCTION_CALL_COUNT = new Metric.Builder("foreign_function_call_count", "Foreign function call Count", Metric.ValueType.INT)
			.setDescription("Number of foreign function calls.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	public static final Metric<Integer> FUNCTION_CALL_COUNT = new Metric.Builder("function_call_count", "Function call Count", Metric.ValueType.INT)
			.setDescription("Number of function calls.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	public static final Metric<Integer> BRANCH_COUNT = new Metric.Builder("branch_count", "Branch Count", Metric.ValueType.INT)
			.setDescription("Number of branches.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	public static final Metric<Integer> IF_COUNT = new Metric.Builder("if_count", "If Count", Metric.ValueType.INT)
			.setDescription("Number of If statements.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	public static final Metric<Integer> SWITCH_COUNT = new Metric.Builder("switch_count", "Switch Count", Metric.ValueType.INT)
			.setDescription("Number of Switch statements.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	public static final Metric<Integer> LOOP_COUNT = new Metric.Builder("loop_count", "Loop Count", Metric.ValueType.INT)
			.setDescription("Number of Loop statements.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	public static final Metric<Integer> RETURN_COUNT = new Metric.Builder("return_count", "Return Count", Metric.ValueType.INT)
			.setDescription("Number of Return statements.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	public static final Metric<Integer> GOTO_COUNT = new Metric.Builder("goto_count", "GoTo Count", Metric.ValueType.INT)
			.setDescription("Number of GoTo statements.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	public static final Metric<Integer> EXCEPTION_COUNT = new Metric.Builder("exception_count", "Exception Count", Metric.ValueType.INT)
			.setDescription("Number of Exception handlers.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	public static final Metric<Integer> CONSTANT_COUNT = new Metric.Builder("constant_count", "Constant Count", Metric.ValueType.INT)
			.setDescription("Number of Constants.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	public static final Metric<Integer> DEFINE_COUNT = new Metric.Builder("define_count", "Define Count", Metric.ValueType.INT)
			.setDescription("Number of Defines.") //TODO: What is it??
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	public static final Metric<Integer> STATEMENT_TYPE_COUNT = new Metric.Builder("statement_type_count", "Statement type Count", Metric.ValueType.INT)
			.setDescription("Number of statement types.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	public static final Metric<Integer> DATA_TYPE_COUNT = new Metric.Builder("data_type_count", "Data type Count", Metric.ValueType.INT)
			.setDescription("Number of data types.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	public static final Metric<Integer> OPERAND_COUNT = new Metric.Builder("operand_count", "Operand Count", Metric.ValueType.INT)
			.setDescription("Number of operands.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	public static final Metric<Integer> CLASS_COUNT = new Metric.Builder("class_count", "Class Count", Metric.ValueType.INT)
			.setDescription("Number of classes.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	public static final Metric<Integer> METHOD_COUNT = new Metric.Builder("method_count", "Method Count", Metric.ValueType.INT)
			.setDescription("Number of methods.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	public static final Metric<Integer> PROCEDURE_COUNT = new Metric.Builder("procedure_count", "Procedure Count", Metric.ValueType.INT)
			.setDescription("Number of procedures.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	public static final Metric<Integer> INCLUDE_COUNT = new Metric.Builder("include_count", "Include Count", Metric.ValueType.INT)
			.setDescription("Number of includes.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	public static final Metric<Integer> MODULE_COUNT = new Metric.Builder("module_count", "Module Count", Metric.ValueType.INT)
			.setDescription("Number of modules.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	public static final Metric<Integer> MACRO_REFERENCE_COUNT = new Metric.Builder("macro_reference_count", "Macro reference Count", Metric.ValueType.INT)
			.setDescription("Number of macro references.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	public static final Metric<Integer> INPUT_COUNT = new Metric.Builder("input_count", "Input Count", Metric.ValueType.INT)
			.setDescription("Number of inputs.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	public static final Metric<Integer> OUTPUT_COUNT = new Metric.Builder("output_count", "Output Count", Metric.ValueType.INT)
			.setDescription("Number of outputs.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	public static final Metric<Integer> REPORT_COUNT = new Metric.Builder("report_count", "Report Count", Metric.ValueType.INT)
			.setDescription("Number of reports.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	public static final Metric<Integer> PANEL_COUNT = new Metric.Builder("panel_count", "Panel Count", Metric.ValueType.INT)
			.setDescription("Number of panels.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	public static final Metric<Integer> REUSE_PROCEDURE_AND_METHOD_COUNT = new Metric.Builder("reuse_procedure_and_method_count", "Reuse procedure and method Count", Metric.ValueType.INT)
			.setDescription("Number of reuses of procedures and methods.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	public static final Metric<Integer> COMMENT_LINE_COUNT = new Metric.Builder("comment_line_count", "Comment line Count", Metric.ValueType.INT)
			.setDescription("Number of comment lines.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	public static final Metric<Integer> LINE_COUNT = new Metric.Builder("line_count", "Line Count", Metric.ValueType.INT)
			.setDescription("Number of lines.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	public static final Metric<Integer> CONVERTABLE_STATEMENTS_COUNT = new Metric.Builder("convertable_statements_count", "Convertable statements Count", Metric.ValueType.INT)
			.setDescription("Number of convertable statements.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	public static final Metric<Integer> CONVERTABLE_DATA_COUNT = new Metric.Builder("convertable_data_count", "Convertable data Count", Metric.ValueType.INT)
			.setDescription("Number of convertable data (variables and constants).")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	public static final Metric<Integer> LITERAL_COUNT = new Metric.Builder("literal_count", "Literal Count", Metric.ValueType.INT)
			.setDescription("Number of literals.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	public static final Metric<Integer> MAJOR_DEFICIENCY_COUNT = new Metric.Builder("major_deficiency_count", "Major deficiency Count", Metric.ValueType.INT)
			.setDescription("Number of major deficiancies.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	public static final Metric<Integer> MEDIUM_DEFICIENCY_COUNT = new Metric.Builder("medium_deficiency_count", "Medium deficiency Count", Metric.ValueType.INT)
			.setDescription("Number of medium deficiancies.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	public static final Metric<Integer> MINOR_DEFICIENCY_COUNT = new Metric.Builder("minor_deficiency_count", "Minor deficiency Count", Metric.ValueType.INT)
			.setDescription("Number of minor deficiancies.")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();	
	public static final Metric<Integer> MAX_MODULE_SIZE = new Metric.Builder("max_module_size", "Maximum module size", Metric.ValueType.INT)
			.setDescription("Maximum Module size.")
			.setDirection(Metric.DIRECTION_NONE)
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
        return Arrays.asList(
        	// Sample Metrics from IDE-Metadata TODO: remove as soon as own metrics are measured
            IDE_PRO_NAME, 
            // softaudit measures
            PREDICATE_COUNT, RESULT_COUNT, ARGUMENT_COUNT, PARAMETER_COUNT, STATEMENT_COUNT, REFERENCE_COUNT, VARIABLE_COUNT,
			FILE_COUNT, DATABASE_COUNT, FILE_ACCESS_COUNT, DATABASE_ACCESS_COUNT, FOREIGN_FUNCTION_CALL_COUNT, FUNCTION_CALL_COUNT,
			BRANCH_COUNT, IF_COUNT, SWITCH_COUNT, LOOP_COUNT, RETURN_COUNT, GOTO_COUNT, EXCEPTION_COUNT, CONSTANT_COUNT, 
			DEFINE_COUNT, STATEMENT_TYPE_COUNT, DATA_TYPE_COUNT, OPERAND_COUNT, CLASS_COUNT, METHOD_COUNT, PROCEDURE_COUNT, 
			INCLUDE_COUNT, MODULE_COUNT, MACRO_REFERENCE_COUNT, INPUT_COUNT, OUTPUT_COUNT, REPORT_COUNT, PANEL_COUNT, 
			REUSE_PROCEDURE_AND_METHOD_COUNT, COMMENT_LINE_COUNT, LINE_COUNT, CONVERTABLE_STATEMENTS_COUNT, CONVERTABLE_DATA_COUNT, 
			LITERAL_COUNT, MAJOR_DEFICIENCY_COUNT, MEDIUM_DEFICIENCY_COUNT, MINOR_DEFICIENCY_COUNT, MAX_MODULE_SIZE);
    }
}
