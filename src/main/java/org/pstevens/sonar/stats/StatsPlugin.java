package org.pstevens.sonar.stats;

import org.pstevens.sonar.stats.batch.StatsSensor;
import org.sonar.api.Properties;
import org.sonar.api.Property;
import org.sonar.api.SonarPlugin;

import java.util.Arrays;
import java.util.List;

/**
 * This class is the entry point for all extensions
 */
@Properties({
	@Property(
		    key = StatsPlugin.MODE,
		    name = "Mode",
		    description = "Mode, set to skip to disable",
		    global=true,project=true
		    ),
  @Property(
    key = StatsPlugin.QUERYDEFINITION_PROPERTY,
    name = "Definition for queries",
    description = "Definition for queries",
    global=false
    )})
public final class StatsPlugin extends SonarPlugin {

  public static final String QUERYDEFINITION_PROPERTY= "sonar.stats.definition";
public static final String MODE = "sonar.stats.mode";

  // This is where you're going to declare all your Sonar extensions
  @SuppressWarnings({ "unchecked", "rawtypes" })
public List getExtensions() {
    return Arrays.asList(
    		StatsSensor.class
);
  }
}
