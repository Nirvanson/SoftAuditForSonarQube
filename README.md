Plugin for SonarQube with Code-Quality-Index and Sneed-Metrics for SWP

Installation/Build (Windows):

Basic Installation for local SQ-Server:
* Download + Unpack SonarQube 5.1.2 (SQ-Directory)
* Download + Unpack SonarScanner 2.6.1 (Scanner-Directory)
* Download SonarJava-Plugin 3.7.1 and copy to SQ-Directory/extensions/plugins
* Clone git-Repository (Git-Directory)

Build current plugin-version 1.0 with measure-extractor 0.1:
* Navigate with command-line in Git-Directory/measure-extractor
* run "gradle jar"
* run "mvn org.apache.maven.plugins:maven-install-plugin:2.3.1:install-file -Dfile={Git-Directory}/measure-extractor/measure-extractor.jar -DgroupId=org.extendj -DartifactId=measure-extractor -Dversion=0.1 -Dpackaging=jar"
* navigate to Git-Directory/plugin
* run "mvn clean install"
* copy Git-Directory/plugin/target/sonar-softaudit-plugin-1.0.jar to SQ-Directory/extensions/plugins

Run SonarQube for measuring the plugin:
* Start SonarQube with SQ-Directory\bin\windows-x86-64\StartSonar.bat (keep command-line open)
* Navigate with new command-line in Git-Directory/plugin
* run "{Scanner-Directory}/bin/sonar-scanner.bat"
* open localhost:9000 in browser
* Open plugin-mesurement
* One-Time: Login with admin/admin, configure widgets: select "SoftAudit for SonarQube"-Widget, go back to dashboard
* see results

Re-deploy:
* Shut down SQ with Ctrl+c in SQ-Command-Line
* Build and copy new Plugin-Version
* Start SonarQube again
* Run Scanner
