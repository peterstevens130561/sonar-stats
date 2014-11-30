package org.pstevens.sonar.stats.batch;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.TimeMachine;
import org.sonar.api.batch.TimeMachineQuery;
import org.sonar.api.config.Settings;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Project;

public class StatsSensor implements PostJob{

  private static final Logger LOG = LoggerFactory.getLogger(StatsSensor.class);

  private Settings settings;
  private TimeMachine timeMachine;

  public StatsSensor(Settings settings,TimeMachine timeMachine) {
    this.settings = settings;
    this.timeMachine=timeMachine;
  }

  public boolean shouldExecuteOnProject(Project project) {
	  String mode=settings.getString(StatsPlugin.MODE);
	  return !"skip".equals(mode);
  }

  public void executeOn(Project project, SensorContext sensorContext) {
	  try {
		execute(project,sensorContext);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
  public void execute(Project project, SensorContext sensorContext) throws IOException, ParseException {
	  LOG.info("Executing StatisticsSensor");
	  String property=settings.getString(StatsPlugin.QUERYDEFINITION_PROPERTY);
	  if(StringUtils.isEmpty(property)) return;
	  TimeMachineQuery query = new TimeMachineQuery(project);
	  List<Metric> metrics = new ArrayList<Metric>();
	  metrics.add(CoreMetrics.BRANCH_COVERAGE);
	  metrics.add(CoreMetrics.LINE_COVERAGE);
	  metrics.add(CoreMetrics.TESTS);
	  metrics.add(CoreMetrics.TEST_SUCCESS_DENSITY);
	  Date from = null;
	  from = parseDate("13-may-2014");

	query.setMetrics(metrics).setFrom(from).setToCurrentAnalysis(true);
	  List<Measure> measures = timeMachine.getMeasures(query);
	  File file = new File(property);
	  FileWriter writer=null;
	  writer = new FileWriter(file);
	  StringBuffer sb = new StringBuffer(10240);
	  sb.append("date\t\tproject\tmetric\tvalue\n");
	  for(Measure measure:measures) {
		  Date date = measure.getDate();
		  String metric = measure.getMetricKey();
		  double value = measure.getValue();
		  sb.append(date).append("\t").append(project.getName()).append("\t").append(metric).append("\t").append(value).append("\n");
	  }
	  writer.append(sb.toString());
	  writer.close();
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
