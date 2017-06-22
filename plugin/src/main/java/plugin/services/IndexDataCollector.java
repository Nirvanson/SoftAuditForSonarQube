package plugin.services;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.sonar.api.config.Settings;
import org.sonar.api.issue.Issue;
import org.sonar.api.issue.ProjectIssues;

import plugin.model.IndicatorThreshold;

/**
 * Collecting found issues for code-quality-index-data-base.
 *
 * @author Jan Rucks
 * @version 1.0
 */
public class IndexDataCollector {
    private Connection connection;

    /**
     * Constructor, initializes db.
     * 
     * @param properties - for database parameter
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws FileNotFoundException
     */
    public IndexDataCollector(Settings properties) throws ClassNotFoundException, SQLException, FileNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(properties.getString("indexdburl"),
                properties.getString("indexdbuser"), properties.getString("indexdbpw"));
        DatabaseMetaData meta = connection.getMetaData();
        ResultSet res = meta.getTables(null, null, "indicators", new String[] { "TABLE" });
        if (!res.next()) {
            ScriptRunner sr = new ScriptRunner(connection);
            Reader reader = new BufferedReader(new InputStreamReader(
                    IndexDataCollector.class.getResourceAsStream("/sql/index-database-initialization.sql")));
            sr.runScript(reader);
            connection.commit();
        }
        connection.setAutoCommit(true);
    }

    /**
     * Close data-base-connection.
     * 
     * @throws SQLException
     */
    public void closeConnection() throws SQLException {
        connection.close();
    }

    /**
     * Collect all rule violations and save them in indicator-data-base.
     * 
     * @param issues
     * @param projectName
     * @throws SQLException
     */
    public void collectRuleViolationsForIndex(ProjectIssues issues, String projectName, double linesOfCode)
            throws SQLException {
        int projectId = prepareProjectTableEntries(projectName);
        Map<Integer, Double> collectedViolations = countViolationsGroupedByIndicator(issues, linesOfCode);
        for (Integer key : collectedViolations.keySet()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(
                    new SQL().INSERT_INTO("`violations`")
                            .VALUES("`indicator`, `project`, `count`",
                                    "\"" + key + "\", " + projectId + ", " + collectedViolations.get(key))
                            .toString());
        }
        calculateThresholds();
    }

    /**
     * Count rule violations, norm by loc and group by indicator.
     * 
     * @param issues
     * @param linesOfCode
     * @return map indicatorId / violationcount
     * @throws SQLException
     */
    public Map<Integer, Double> countViolationsGroupedByIndicator(ProjectIssues issues, double linesOfCode)
            throws SQLException {
        Map<Integer, Integer> collectedViolations = initializeIndicatorMap();
        Map<String, Integer> activeRules = initializeRulesMap();
        Iterator<Issue> issueIterator = issues.issues().iterator();
        while (issueIterator.hasNext()) {
            Issue currentIssue = issueIterator.next();
            Integer count = collectedViolations.get(activeRules.get(currentIssue.ruleKey().toString())) + 1;
            collectedViolations.put(activeRules.get(currentIssue.ruleKey().toString()), count);
        }
        Map<Integer, Double> normedViolations = new HashMap<Integer, Double>();
        for (Integer key : collectedViolations.keySet()) {
            normedViolations.put(key, collectedViolations.get(key) * 1000 / linesOfCode);
        }
        return normedViolations;
    }

    public List<IndicatorThreshold> getIndicatorThresholds() throws SQLException {
        List<IndicatorThreshold> thresholds = new ArrayList<IndicatorThreshold>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                new SQL().SELECT("*")
                        .FROM("`indicators`")
                        .toString());
        while (resultSet.next()) {
            IndicatorThreshold entry = new IndicatorThreshold(resultSet.getInt(1), resultSet.getString(3),
                    resultSet.getString(2));
            entry.setThreshold(IndicatorThreshold.THRESHOLD_25, resultSet.getDouble(4));
            entry.setThreshold(IndicatorThreshold.THRESHOLD_50, resultSet.getDouble(5));
            entry.setThreshold(IndicatorThreshold.THRESHOLD_75, resultSet.getDouble(6));
            entry.setThreshold(IndicatorThreshold.THRESHOLD_90, resultSet.getDouble(7));
            entry.setThreshold(IndicatorThreshold.THRESHOLD_100, resultSet.getDouble(8));
            thresholds.add(entry);
        }
        return thresholds;
    }

    private int prepareProjectTableEntries(String projectName) throws SQLException {
        int projectId = 0;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                new SQL().SELECT("id")
                        .FROM("`projects`")
                        .WHERE("`name` = \"" + projectName + "\"")
                        .toString());
        if (resultSet.next()) {
            // delete former data from this project
            projectId = resultSet.getInt(1);
            statement.executeUpdate(
                    new SQL().DELETE_FROM("`violations`")
                            .WHERE("`project` = " + projectId)
                            .toString());
        } else {
            // create project-entry in data base
            statement.executeUpdate(
                    new SQL().INSERT_INTO("`projects`")
                            .VALUES("`name`, `year`", "\"" + projectName + "\", 2017")
                            .toString());
            ResultSet res = statement.executeQuery(
                    new SQL().SELECT("id")
                            .FROM("`projects`")
                            .WHERE("`name` = \"" + projectName + "\"")
                            .toString());
            if (res.next()) {
                projectId = res.getInt(1);
            }
        }
        return projectId;
    }

    private Map<Integer, Integer> initializeIndicatorMap() throws SQLException {
        Map<Integer, Integer> initialMap = new HashMap<Integer, Integer>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                new SQL().SELECT("id")
                        .FROM("`indicators`")
                        .toString());
        while (resultSet.next()) {
            initialMap.put(resultSet.getInt(1), 0);
        }
        return initialMap;
    }

    private Map<String, Integer> initializeRulesMap() throws SQLException {
        Map<String, Integer> ruleMap = new HashMap<String, Integer>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                new SQL().SELECT("`sonar_key`, `indicator`")
                        .FROM("`rules`")
                        .toString());
        while (resultSet.next()) {
            ruleMap.put(resultSet.getString(1), resultSet.getInt(2));
        }
        return ruleMap;
    }

    private void calculateThresholds() throws SQLException {
        Set<Integer> indicators = initializeIndicatorMap().keySet();
        List<Integer> borderElements = calculateBorderElements();
        for (Integer indicatorId : indicators) {
            calculateThresholdForIndicator(indicatorId, borderElements);
        }
    }

    private List<Integer> calculateBorderElements() throws SQLException {
        List<Integer> borderElements = new ArrayList<Integer>();
        Integer total = countProjectsInDatabase();
        borderElements.add((int) Math.ceil((double) total / 4));
        borderElements.add((int) Math.ceil((double) total / 2));
        borderElements.add((int) Math.ceil((double) (total * 3) / 4));
        borderElements.add((int) Math.ceil((double) (total * 9) / 10));
        borderElements.add(total);
        return borderElements;
    }

    private Integer countProjectsInDatabase() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                new SQL().SELECT("COUNT(*)")
                        .FROM("`projects`")
                        .toString());
        if (resultSet.next()) {
            return resultSet.getInt(1);
        } else {
            return 0;
        }
    }

    private void calculateThresholdForIndicator(Integer indicatorId, List<Integer> borderElements) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                new SQL().SELECT("`count`")
                        .FROM("`violations`")
                        .WHERE("`indicator` = " + indicatorId)
                        .ORDER_BY("`count` DESC")
                        .toString());
        for (int i = 1; i <= borderElements.get(4); i++) {
            resultSet.next();
            if (i == borderElements.get(0)) {
                Statement update1 = connection.createStatement();
                update1.executeUpdate(
                        new SQL().UPDATE("`indicators`")
                                .SET("`threshold_25` = " + resultSet.getDouble(1))
                                .WHERE("`id` = " + indicatorId)
                                .toString());
            }
            if (i == borderElements.get(1)) {
                Statement update2 = connection.createStatement();
                update2.executeUpdate(
                        new SQL().UPDATE("`indicators`")
                                .SET("`threshold_50` = " + resultSet.getDouble(1))
                                .WHERE("`id` = " + indicatorId)
                                .toString());
            }
            if (i == borderElements.get(2)) {
                Statement update3 = connection.createStatement();
                update3.executeUpdate(
                        new SQL().UPDATE("`indicators`")
                                .SET("`threshold_75` = " + resultSet.getDouble(1))
                                .WHERE("`id` = " + indicatorId)
                                .toString());
            }
            if (i == borderElements.get(3)) {
                Statement update3 = connection.createStatement();
                update3.executeUpdate(
                        new SQL().UPDATE("`indicators`")
                                .SET("`threshold_90` = " + resultSet.getDouble(1))
                                .WHERE("`id` = " + indicatorId)
                                .toString());
            }
            if (i == borderElements.get(4)) {
                Statement update4 = connection.createStatement();
                update4.executeUpdate(
                        new SQL().UPDATE("`indicators`")
                                .SET("`threshold_100` = " + resultSet.getDouble(1))
                                .WHERE("`id` = " + indicatorId)
                                .toString());
            }
        }
    }
}
