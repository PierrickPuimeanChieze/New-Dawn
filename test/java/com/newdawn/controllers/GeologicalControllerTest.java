/**
 * 
 */
package com.newdawn.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import javafx.beans.property.ReadOnlyStringProperty;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.newdawn.controllers.TeamController.FieldTeamType;
import com.newdawn.model.mineral.MinerallyExploitableBody;
import com.newdawn.model.mineral.MinerallyExploitableBodyModel;
import com.newdawn.model.personnel.NavalOfficer;
import com.newdawn.model.personnel.Skill;
import com.newdawn.model.personnel.SkillLevel;
import com.newdawn.model.personnel.team.GeologicalTeam;

/**
 * @author teocali
 * 
 */
public class GeologicalControllerTest {

	private static ClassPathXmlApplicationContext springContainer;
	private static OfficialsController officialsController;
	private static TeamController teamController;
	private static GeologicalTeam geologicalTeam;
	private static MinerallyExploitableBody testBody;
	private static GeologicalController geologicalController;
	private static Config config;
	private static SkillLevel teamLeaderGeologySkillLevel;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		springContainer = new ClassPathXmlApplicationContext(
				"/spring/newdawn.xml");
		officialsController = springContainer
				.getBean(OfficialsController.class);
		teamController = springContainer.getBean(TeamController.class);
		testBody = new MinerallyExploitableBody() {

			private MinerallyExploitableBodyModel model;

			@Override
			public ReadOnlyStringProperty visualNameProperty() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void setMinerallyExploitableBodyModel(
					MinerallyExploitableBodyModel minerallyExploitableBodyModel) {
				this.model = minerallyExploitableBodyModel;

			}

			@Override
			public MinerallyExploitableBodyModel getMinerallyExploitableBodyModel() {
				// TODO Auto-generated method stub
				return this.model;
			}
		};
		NavalOfficer teamLeader;
		teamLeader = officialsController.createNewNavalOfficer("Team Leader",
				testBody);
		geologicalTeam = (GeologicalTeam) teamController.createTeamWithLeader(
				teamLeader, FieldTeamType.GEOLOGICAL);
		teamController.assignTeamToAssignment(geologicalTeam, testBody);
		geologicalController = springContainer
				.getBean(GeologicalController.class);
		config = springContainer.getBean(Config.class);
		Skill geologySkill = springContainer.getBean("geology", Skill.class);
		teamLeaderGeologySkillLevel = new SkillLevel(geologySkill);

		teamLeader.getSkillLevels().put(geologySkill,
				teamLeaderGeologySkillLevel);
	}

	@Before
	public void setUp() {
		geologicalTeam.setInternalCounter(0);
		teamLeaderGeologySkillLevel.setLevel(0);
	}

	/**
	 * Test method for
	 * {@link com.newdawn.controllers.GeologicalController#runProspection(com.newdawn.model.personnel.team.GeologicalTeam, int)}
	 * .
	 */
	@Test
	public final void testRunProspectionInitialLevelNotEnoughLevel() {
		MinerallyExploitableBodyModel bodyModel = new MinerallyExploitableBodyModel(
				50, 150, 150);
		bodyModel = new MinerallyExploitableBodyModel(50, 150, 150);
		testBody.setMinerallyExploitableBodyModel(bodyModel);
		teamLeaderGeologySkillLevel.setLevel(0);
		assertThat(geologicalTeam.getCumulatedSkillLevel(), is(equalTo(0L)));
		geologicalController.runProspection(geologicalTeam,
				config.getMaxValueForTeamInternalTimeCounter() + 1);
		assertThat(geologicalTeam.getInternalCounter(), is(equalTo(0)));
		assertThat(bodyModel.getInitialDiscoveryPoints(), is(equalTo(0L)));
	}

	@Test
	
	public final void testRunProspectionInitialLevelFilling() {
		MinerallyExploitableBodyModel bodyModel = new MinerallyExploitableBodyModel(
				50, 150, 150);
		testBody.setMinerallyExploitableBodyModel(bodyModel);

		teamLeaderGeologySkillLevel.setLevel(22);
		assertThat(geologicalTeam.getCumulatedSkillLevel(), is(equalTo(22L)));
		geologicalController.runProspection(geologicalTeam,
				config.getMaxValueForTeamInternalTimeCounter() + 1);
		assertThat(geologicalTeam.getInternalCounter(), is(equalTo(1)));
		assertThat(bodyModel.getInitialDiscoveryPoints(), is(equalTo(22L)));

	}

	@Test
	public final void testRunProspectionInitialLevelFinalization() {
		MinerallyExploitableBodyModel bodyModel = new MinerallyExploitableBodyModel(
				30, 150, 150);
		testBody.setMinerallyExploitableBodyModel(bodyModel);

		teamLeaderGeologySkillLevel.setLevel(16);
		assertThat(geologicalTeam.getCumulatedSkillLevel(), is(equalTo(16L)));
		geologicalController.runProspection(geologicalTeam,
				config.getMaxValueForTeamInternalTimeCounter() * 2 + 1);
		assertThat(geologicalTeam.getInternalCounter(), is(equalTo(1)));
		assertThat(bodyModel.isInitialDiscovered(), is(true));
//		assertThat(bodyModel.getInitialDiscoveryPoints(), is(equalTo(32L)));
	}

	@Test
	public final void testRunProspectionIntermediateLevelNotEnoughLevel() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testRunProspectionIntermediateLevelFilling() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testRunProspectionIntermediateLevelFinalization() {
		fail("Not yet implemented"); // TODO
	}

}
