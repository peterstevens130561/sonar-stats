package org.pstevens.sonar.stats.test.batch;

import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.pstevens.sonar.stats.batch.StatsSensor;
import org.sonar.api.batch.PostJob;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.TimeMachine;
import org.sonar.api.config.Settings;
import org.sonar.api.resources.Project;

public class StatsSensorTest {
	private Settings settings;
	private TimeMachine timeMachine = mock(TimeMachine.class);
	private Project project = mock(Project.class);
	private SensorContext context = mock(SensorContext.class);
	
	
	private PostJob classUnderTest;
	
	@Before
	public void before() {
		settings = new Settings();
		classUnderTest = new StatsSensor(settings,timeMachine);
	}
	@Test
	public void analyse() {
		settings.appendProperty("sonar.stats.definition", null);
		classUnderTest.executeOn(project, context);
	}
}
