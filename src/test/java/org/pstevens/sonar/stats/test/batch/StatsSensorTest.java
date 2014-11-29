package org.pstevens.sonar.stats.test.batch;

import org.junit.Before;
import org.junit.Test;
import org.pstevens.sonar.stats.batch.StatsSensor;
import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.TimeMachine;
import org.sonar.api.config.Settings;
import org.sonar.api.resources.Project;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyString;

public class StatsSensorTest {
	private Settings settings;
	private TimeMachine timeMachine = mock(TimeMachine.class);
	private Project project = mock(Project.class);
	private SensorContext context = mock(SensorContext.class);
	
	
	private Sensor classUnderTest;
	
	@Before
	public void before() {
		settings = new Settings();
		classUnderTest = new StatsSensor(settings,timeMachine);
	}
	@Test
	public void shouldExecute_True() {
		assertTrue(classUnderTest.shouldExecuteOnProject(null));
	}
	
	@Test
	public void analyse() {
		settings.appendProperty("sonar.stats.definition", null);
		classUnderTest.analyse(project, context);
	}
}
