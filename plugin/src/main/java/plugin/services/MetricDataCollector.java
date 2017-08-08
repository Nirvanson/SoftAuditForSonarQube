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
import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.sonar.api.config.Settings;
import org.sonar.api.measures.Metric;

/**
 * Collecting found issues for code-quality-index-data-base.
 *
 * @author Jan Rucks
 * @version 1.0
 */
public class MetricDataCollector {
    private Connection connection;

    /**
     * Constructor, initializes db.
     * 
     * @param properties - for database parameter
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws FileNotFoundException
     */
    public MetricDataCollector(Settings properties) throws ClassNotFoundException, SQLException, FileNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(properties.getString("metricdburl"),
                properties.getString("dbuser"), properties.getString("dbpw"));
        DatabaseMetaData meta = connection.getMetaData();
        ResultSet res = meta.getTables(null, null, "metrics", new String[] { "TABLE" });
        if (!res.next()) {
            ScriptRunner sr = new ScriptRunner(connection);
            Reader reader = new BufferedReader(new InputStreamReader(
                    MetricDataCollector.class.getResourceAsStream("/sql/metric-database-initialization.sql")));
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
    public void saveValues(String projectName, String tag, Map<Metric<?>, Double> values) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(new SQL().DELETE_FROM("`metrics`")
                .WHERE("`project_name`=\"" + projectName + "\"")
                .WHERE("`tag`=\"" + tag + "\"").toString());
        SQL query = new SQL().INSERT_INTO("`metrics`")
                .VALUES("`project_name`", "\"" + projectName + "\"")
                .VALUES("`tag`", "\"" + tag + "\"");

        for (Metric<?> metric : values.keySet()) {
            query = query.VALUES("`" + metric.getKey() + "`", values.get(metric).toString());
        }
        statement.executeUpdate(query.toString());
    }
}
