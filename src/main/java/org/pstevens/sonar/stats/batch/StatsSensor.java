package org.pstevens.sonar.stats.batch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.pstevens.sonar.stats.StatsPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.PostJob;
import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.TimeMachine;
import org.sonar.api.batch.TimeMachineQuery;
import org.sonar.api.config.Settings;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Project;
import org.sonar.api.utils.SonarException;

public class StatsSensor implements PostJob{

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

  public void executeOn(Project project, SensorContext sensorContext) {
	  String property=settings.getString(StatsPlugin.QUERYDEFINITION_PROPERTY);
	  if(StringUtils.isEmpty(property)) return;
	  TimeMachineQuery query = new TimeMachineQuery(project);
	  List<Metric> metrics = new ArrayList<Metric>();
	  metrics.add(CoreMetrics.BRANCH_COVERAGE);
	  metrics.add(CoreMetrics.LINE_COVERAGE);
	  Date from = null;
	try {
		from = parseDate("13-may-2014");
	} catch (ParseException e) {
		throw new SonarException(e);
	}
	query.setMetrics(metrics).setFrom(from).setToCurrentAnalysis(true);
	  List<Measure> measures = timeMachine.getMeasures(query);
	  
  }

  public Date parseDate(String date) throws ParseException {
	  SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
	  Date result = formatter.parse(date);
	  return result;
  }
  @Override
  public String toString() {
    return getClass().getSimpleName();
  }



}
