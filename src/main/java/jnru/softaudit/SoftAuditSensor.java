package jnru.softaudit;

import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.resources.Project;

public class SoftAuditSensor implements Sensor{

	@Override
	public boolean shouldExecuteOnProject(Project arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void analyse(Project arg0, SensorContext arg1) {
		// TODO Auto-generated method stub
		
	}

}
