package jnru;

import org.sonar.api.Plugin;
import jnru.measures.ComputeSizeAverage;
import jnru.measures.ComputeSizeRating;
import jnru.measures.ExampleMetrics;
import jnru.measures.SetSizeOnFilesSensor;
import jnru.rules.CreateIssuesOnJavaFilesSensor;
import jnru.rules.JavaRulesDefinition;
import jnru.settings.ExampleProperties;
import jnru.settings.SayHelloFromScanner;
import jnru.web.ExampleFooter;
import jnru.web.ExampleWidget;

/**
 * This class is the entry point for all extensions. It is referenced in pom.xml.
 */
public class SoftAuditPlugin implements Plugin {

  @Override
  public void define(Context context) {

    // tutorial on measures
    context
      .addExtensions(ExampleMetrics.class, SetSizeOnFilesSensor.class, ComputeSizeAverage.class, ComputeSizeRating.class);

    // tutorial on rules
    context.addExtensions(JavaRulesDefinition.class, CreateIssuesOnJavaFilesSensor.class);

    // tutorial on settings
    context
      .addExtensions(ExampleProperties.definitions())
      .addExtension(SayHelloFromScanner.class);

    // tutorial on web extensions
    context.addExtensions(ExampleFooter.class, ExampleWidget.class);
  }
}
