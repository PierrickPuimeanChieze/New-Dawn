/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewerfx;

import com.newdawn.controllers.ColonyController;
import com.newdawn.controllers.GameData;
import com.newdawn.controllers.InitialisationController;
import com.newdawn.controllers.OfficialsController;
import com.newdawn.controllers.TeamController;
import com.newdawn.gui.SpringFXControllerFactory;
import com.newdawn.model.colony.Colony;
import com.newdawn.model.personnel.NavalOfficer;
import com.newdawn.model.personnel.Skill;
import com.newdawn.model.personnel.SkillLevel;
import com.newdawn.model.personnel.ranks.NavalRank;
import com.newdawn.model.personnel.team.FieldTeam;
import com.newdawn.model.system.Planet;
import com.newdawn.model.system.StellarSystem;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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

		Colony test2 = springContainer.getBean(Colony.class);
		test2.setPopulation(100_000_000);
		test2.setPopulationGrowRate(1);
		test2.setWealthProduction(500);
		test2.setName("Test");
		Planet planet = solarSystem2.getPlanets().get(1);
		springContainer.getBean(ColonyController.class).updateSystemWithColony(
				planet, test2);

		Skill geologySkill = springContainer.getBean("geology", Skill.class);
		NavalOfficer navalOfficer1 = officialController.createNewNavalOfficer(
				"navalOfficer2", test2);
		navalOfficer1.setRank(NavalRank.A6);
		NavalOfficer navalOfficer3 = officialController.createNewNavalOfficer(
				"navalOfficer3", test2);
		navalOfficer3.setRank(NavalRank.A6);

		SkillLevel geologySkillLevel = navalOfficer1.skillLevelsProperty().get(
				geologySkill);
		geologySkillLevel.setLevel(50);

		gameData.getOfficials().add(navalOfficer1);
		gameData.getOfficials().add(navalOfficer3);
		final FieldTeam fieldTeam = teamController.createTeamWithLeader(
				navalOfficer1, TeamController.FieldTeamType.GEOLOGICAL);
		teamController.addMemberToTeam(navalOfficer3, fieldTeam);
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
