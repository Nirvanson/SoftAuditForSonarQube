package jnru;

import org.sonar.api.Plugin;
import jnru.hooks.DisplayIssuesInScanner;
import jnru.hooks.DisplayQualityGateStatus;
import jnru.languages.FooLanguage;
import jnru.languages.FooQualityProfile;
import jnru.measures.ComputeSizeAverage;
import jnru.measures.ComputeSizeRating;
import jnru.measures.ExampleMetrics;
import jnru.measures.SetSizeOnFilesSensor;
import jnru.rules.CreateIssuesOnJavaFilesSensor;
import jnru.rules.FooLintIssuesLoaderSensor;
import jnru.rules.FooLintRulesDefinition;
import jnru.rules.JavaRulesDefinition;
import jnru.settings.ExampleProperties;
import jnru.settings.SayHelloFromScanner;
import jnru.web.ExampleFooter;
import jnru.web.ExampleWidget;

/**
 * This class is the entry point for all extensions. It is referenced in pom.xml.
 */
public class ExamplePlugin implements Plugin {

  @Override
  public void define(Context context) {
    // tutorial on hooks
    // http://docs.sonarqube.org/display/DEV/Adding+Hooks
    context.addExtensions(DisplayIssuesInScanner.class, DisplayQualityGateStatus.class);

    // tutorial on languages
    context.addExtensions(FooLanguage.class, FooQualityProfile.class);

    // tutorial on measures
    context
      .addExtensions(ExampleMetrics.class, SetSizeOnFilesSensor.class, ComputeSizeAverage.class, ComputeSizeRating.class);

    // tutorial on rules
    context.addExtensions(JavaRulesDefinition.class, CreateIssuesOnJavaFilesSensor.class);
    context.addExtensions(FooLintRulesDefinition.class, FooLintIssuesLoaderSensor.class);

    // tutorial on settings
    context
      .addExtensions(ExampleProperties.definitions())
      .addExtension(SayHelloFromScanner.class);

    // tutorial on web extensions
    context.addExtensions(ExampleFooter.class, ExampleWidget.class);
  }
}
