/**
 * 
 */
package com.newdawn.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Map;

import javafx.beans.property.ReadOnlyStringProperty;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.newdawn.controllers.TeamController.FieldTeamType;
import com.newdawn.model.mineral.Mineral;
import com.newdawn.model.mineral.MineralDeposit;
import com.newdawn.model.mineral.MineralModel;
import com.newdawn.model.mineral.MinerallyExploitableBody;
import com.newdawn.model.mineral.MinerallyExploitableBodyModel;
import com.newdawn.model.mineral.MineralDeposit.MineralDepositStatus;
import com.newdawn.model.personnel.NavalOfficer;
import com.newdawn.model.personnel.Skill;
import com.newdawn.model.personnel.SkillLevel;
import com.newdawn.model.personnel.team.GeologicalTeam;
import com.sun.org.apache.xpath.internal.operations.NotEquals;

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
	private static Mineral mineral_1;

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
		mineral_1 = springContainer.getBean("mineral_1", Mineral.class);
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
		// teamLeaderGeologySkillLevel.setLevel(0);
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
				50, 150, 150, new MineralModel(mineral_1, 15));
		testBody.setMinerallyExploitableBodyModel(bodyModel);

		teamLeaderGeologySkillLevel.setLevel(22);
		assertThat(geologicalTeam.getCumulatedSkillLevel(), is(equalTo(22L)));
		geologicalController.runProspection(geologicalTeam,
				config.getMaxValueForTeamInternalTimeCounter() + 1);
		assertThat(geologicalTeam.getInternalCounter(), is(equalTo(1)));
		assertThat(bodyModel.getInitialDiscoveryPoints(), is(equalTo(22L)));
		assertThat(bodyModel.getMineralModel(mineral_1).getDiscovered(),
				is(equalTo(0L)));
	}

	@Test
	public final void testRunProspectionInitialLevelFinalization() {
		MinerallyExploitableBodyModel bodyModel = new MinerallyExploitableBodyModel(
				30, 150, 150, new MineralModel(mineral_1, 15));
		testBody.setMinerallyExploitableBodyModel(bodyModel);

		teamLeaderGeologySkillLevel.setLevel(16);
		assertThat(geologicalTeam.getCumulatedSkillLevel(), is(equalTo(16L)));
		geologicalController.runProspection(geologicalTeam,
				config.getMaxValueForTeamInternalTimeCounter() * 2 + 1);
		assertThat(geologicalTeam.getInternalCounter(), is(equalTo(1)));
		assertThat(bodyModel.isInitialDiscovered(), is(true));
		assertThat(bodyModel.getMineralModel(mineral_1).getDiscovered(),
				is(equalTo(15L)));
	}

	@Test
	public final void testRunProspectionIntermediateLevelNotEnoughLevel() {
		MineralDeposit testDeposit = new MineralDeposit(17, 50, 150);
		MineralModel mineral_1Model = new MineralModel(mineral_1, 15,
				testDeposit);

		MinerallyExploitableBodyModel bodyModel = new MinerallyExploitableBodyModel(
				30, 150, 150, mineral_1Model);
		testBody.setMinerallyExploitableBodyModel(bodyModel);

		teamLeaderGeologySkillLevel.setLevel(16);
		bodyModel.discoverInitialQuantities();
		assertThat(geologicalTeam.getCumulatedSkillLevel(), is(equalTo(16L)));
		geologicalController.runProspection(geologicalTeam,
				config.getMaxValueForTeamInternalTimeCounter() * 2 + 1);
		assertThat(geologicalTeam.getInternalCounter(), is(equalTo(1)));
		MineralModel mineralModel = bodyModel.getMineralModel(mineral_1);
		assertThat(mineralModel.getDiscovered(), is(equalTo(15L)));
		assertThat(testDeposit.getDiscoveryPoints(), is(equalTo(0L)));
	}

	@Test
	public final void testRunProspectionIntermediateLevelFilling() {
		MineralDeposit testDeposit = new MineralDeposit(17, 50, 150);
		MineralModel mineral_1Model = new MineralModel(mineral_1, 15,
				testDeposit);

		MinerallyExploitableBodyModel bodyModel = new MinerallyExploitableBodyModel(
				30, 150, 150, mineral_1Model);
		testBody.setMinerallyExploitableBodyModel(bodyModel);

		teamLeaderGeologySkillLevel.setLevel(18);
		bodyModel.discoverInitialQuantities();
		assertThat(geologicalTeam.getCumulatedSkillLevel(), is(equalTo(18L)));
		geologicalController.runProspection(geologicalTeam,
				config.getMaxValueForTeamInternalTimeCounter() * 2 + 1);
		assertThat(geologicalTeam.getInternalCounter(), is(equalTo(1)));
		MineralModel mineralModel = bodyModel.getMineralModel(mineral_1);
		assertThat(mineralModel.getDiscovered(), is(equalTo(15L)));
		assertThat(testDeposit.getDiscoveryPoints(), is(equalTo(2L)));
	}

	@Test
	public final void testRunProspectionIntermediateLevelFinalization() {
		MineralDeposit testDeposit = new MineralDeposit(17, 20, 50);
		MineralModel mineral_1Model = new MineralModel(mineral_1, 15,
				testDeposit);

		MinerallyExploitableBodyModel bodyModel = new MinerallyExploitableBodyModel(
				30, 150, 150, mineral_1Model);
		testBody.setMinerallyExploitableBodyModel(bodyModel);

		teamLeaderGeologySkillLevel.setLevel(27);
		bodyModel.discoverInitialQuantities();
		assertThat(geologicalTeam.getCumulatedSkillLevel(), is(equalTo(27L)));
		geologicalController.runProspection(geologicalTeam,
				config.getMaxValueForTeamInternalTimeCounter() * 2 + 1);
		assertThat(geologicalTeam.getInternalCounter(), is(equalTo(1)));
		MineralModel mineralModel = bodyModel.getMineralModel(mineral_1);
		assertThat(testDeposit.getStatus(), is(MineralDepositStatus.DISCOVERED));
		assertThat(mineralModel.getDiscovered(), is(equalTo(65L)));
	}

}
