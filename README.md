Plugin for SonarQube wich provides some measures and metrics from SoftAudit. Detailed information following

Installation-guide:

Install SonarQube 5.1.2 (Only compatibel with this version!)
Copy the jar (from release-folder) to [SonarQubeDirectory]/extensions/plugins or create your own version by building after your code changes with "mvn clean install"
Add plugin-related properties to scanner or scanned project (without them no full file-logging is possible)
Run Sonarqube and scanner as usual and add widget to dashboard.
