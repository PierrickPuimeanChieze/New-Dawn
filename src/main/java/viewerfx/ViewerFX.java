/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewerfx;

import com.newdawn.controllers.*;
import com.newdawn.gui.SpringFXControllerFactory;
import com.newdawn.model.colony.Colony;
import com.newdawn.model.personnel.NavalOfficer;
import com.newdawn.model.personnel.Skill;
import com.newdawn.model.personnel.ranks.NavalRank;
import com.newdawn.model.personnel.team.FieldTeam;
import com.newdawn.model.system.Planet;
import com.newdawn.model.system.StellarSystem;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * 
 * @author Pierrick Puimean-Chieze
 */
public class ViewerFX extends Application {

	private ApplicationContext springContainer;
	private static Log LOG = LogFactory.getLog(ViewerFX.class);

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void init() throws Exception {
		super.init();

		springContainer = new ClassPathXmlApplicationContext(
				"/spring/newdawn.xml");
		final OfficialsController officialController = springContainer
				.getBean(OfficialsController.class);
		final TeamController teamController = springContainer
				.getBean(TeamController.class);
		final InitialisationController initialisationController = springContainer
				.getBean(InitialisationController.class);
		final GameData gameData = springContainer.getBean(GameData.class);
		final ColonyController colonyController = springContainer
				.getBean(ColonyController.class);
		final StellarSystem solarSystem = SollarSystemBuilder
				.getIt(initialisationController);
		final StellarSystem solarSystem2 = SollarSystem2Builder
				.getIt(initialisationController);
		StellarSystem testSystem = initialisationController
				.createSystem(getClass().getResourceAsStream("/testSystem.xml"));
		gameData.getStellarSystems().addAll(solarSystem, solarSystem2,
				testSystem);

		Colony testColony2 = springContainer.getBean(Colony.class);
		testColony2.setPopulation(100_000_000);
		testColony2.setPopulationGrowRate(1);
		testColony2.setWealthProduction(500);
		testColony2.setName("Test");
		Planet planet = solarSystem2.getPlanets().get(1);
		colonyController.updateSystemWithColony(planet, testColony2);

		Skill geologySkill = springContainer.getBean("geology", Skill.class);
		NavalOfficer navalOfficer1 = officialController.createNewNavalOfficer(
				"navalOfficer-geoskill50", testColony2);
		navalOfficer1.setRank(NavalRank.A6);
		NavalOfficer navalOfficer3 = officialController.createNewNavalOfficer(
				"navalOfficer-geoSkill30", testColony2);
		navalOfficer3.setRank(NavalRank.A6);

		//Setting the geology Skill  
		navalOfficer1.skillLevelsProperty().get(
				geologySkill).setLevel(50);
		navalOfficer3.skillLevelsProperty().get(
				geologySkill).setLevel(30);
		gameData.getOfficials().add(navalOfficer1);
		gameData.getOfficials().add(navalOfficer3);
		final FieldTeam fieldTeam = teamController.createTeamWithLeader(
				navalOfficer1, TeamController.FieldTeamType.GEOLOGICAL);
//		teamController.addMemberToTeam(navalOfficer3, fieldTeam);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(
					"/com/newdawn/gui/MainScreen.fxml"));
			loader.setControllerFactory(new SpringFXControllerFactory(
					getSpringContainer()));
			AnchorPane mainScreen;
			mainScreen = (AnchorPane) loader.load();

			final Scene scene = new Scene(mainScreen);
			primaryStage.titleProperty().bind(
					Bindings.format("Date : %s         Total wealth : %d",
							null, getSpringContainer().getBean(GameData.class)
									.wealthProperty()));
			primaryStage.setScene(scene);

			primaryStage.show();
		} catch (IOException ex) {
			LOG.fatal(null, ex);
			throw new RuntimeException(ex);
		}
	}

	private ApplicationContext getSpringContainer() {
		return springContainer;
	}
}
