package org.pstevens.sonar.stats.batch;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.pstevens.sonar.stats.StatsPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.TimeMachine;
import org.sonar.api.batch.TimeMachineQuery;
import org.sonar.api.config.Settings;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Project;

public class StatsSensor implements Sensor {

  private static final Logger LOG = LoggerFactory.getLogger(StatsSensor.class);

  private Settings settings;
  private TimeMachine timeMachine;

  public StatsSensor(Settings settings,TimeMachine timeMachine) {
    this.settings = settings;
    this.timeMachine=timeMachine;
  }

  public boolean shouldExecuteOnProject(Project project) {
    return true;
  }

  public void analyse(Project project, SensorContext sensorContext) {
	  String property=settings.getString(StatsPlugin.QUERYDEFINITION_PROPERTY);
	  if(StringUtils.isEmpty(property)) return;
	  Project prj = new Project("API");
	  TimeMachineQuery query = new TimeMachineQuery(prj);
	  List<Metric> metrics = new ArrayList<Metric>();
	  metrics.add(CoreMetrics.BLOCKER_VIOLATIONS);
	  query.setMetrics(metrics).setOnlyLastAnalysis(true);
	  timeMachine.getMeasures(query);
	  
  }

  @Override
  public String toString() {
    return getClass().getSimpleName();
  }

}
