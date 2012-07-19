package com.newdawn.gui;

import com.newdawn.controllers.ColonyController;
import com.newdawn.controllers.GameData;
import com.newdawn.controllers.MainController;
import com.newdawn.model.colony.Colony;
import com.newdawn.model.personnel.NavalOfficer;
import com.newdawn.model.personnel.Scientist;
import com.newdawn.model.personnel.Skill;
import com.newdawn.model.personnel.SkillLevel;
import com.newdawn.model.system.Planet;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import viewerfx.ViewerFX;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class MainScreen implements Initializable {

    @Autowired
    private MainController mainController;
    @Autowired
    private ColonyController colonyController;
    @Autowired
    private GameData gameData;
    @FXML
    private Button fiveSecButton;
    @FXML
    private Button thirtySecButton;
    @FXML
    private Button fiveMinButton;
    @FXML
    private Button twentyMinButton;
    @FXML
    private Button oneHourButton;
    @FXML
    private Button threeHourButton;
    @FXML
    private Button eightHourButton;
    @FXML
    private Button oneDayButton;
    @FXML
    private Button fiveDayButton;
    @FXML
    private Button thirtyDayButton;
    @FXML
    private MenuItem economicScreenMenuItem;
    @FXML
    private MenuItem systemMapScreenMenuItem;
    @FXML
    private MenuItem fleetManagementScreenMenuItem;
    @FXML
    private MenuItem personnelManagementScreenMenuItem;
    @FXML
    private TabPane screensTabPane;
    //TODO See to move them to the fxml
    @FXML
    private Tab economicScreenTab;
    @FXML
    private Tab systemMapScreenTab;
    @FXML
    private Tab personnelManagementScreenTab;
    @FXML
    private Tab fleetManagementScreenTab;
    //TODO use include in MainScreen.fxml
    private Node economicScreen;
    private AnchorPane systemMapScreen;
    private Node personnelManagementScreen;
    private AnchorPane fleetManagementScreen;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        fleetManagementScreenTab.setContent(getFleetManagementScreen());
        systemMapScreenTab.setContent(getSystemMapScreen());
        economicScreenTab.setContent(getEconomicScreen());
        personnelManagementScreenTab.setContent(getPersonnelManagementScreen());

        economicScreenMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.F2));
        economicScreenMenuItem.setUserData(economicScreenTab);

        systemMapScreenMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.F3));
        systemMapScreenMenuItem.setUserData(systemMapScreenTab);

        personnelManagementScreenMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.F4));
        personnelManagementScreenMenuItem.setUserData(personnelManagementScreenTab);

        fleetManagementScreenMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.F12));
        fleetManagementScreenMenuItem.setUserData(fleetManagementScreenTab);

        screensTabPane.getTabs().removeAll(systemMapScreenTab, fleetManagementScreenTab, economicScreenTab);
        fiveSecButton.setUserData(5);
        thirtySecButton.setUserData(30);
        fiveMinButton.setUserData(5 * 60);
        twentyMinButton.setUserData(20 * 60);
        oneHourButton.setUserData(3600);
        threeHourButton.setUserData(3 * 3600);
        eightHourButton.setUserData(8 * 3600);
        oneDayButton.setUserData(24 * 3600);
        fiveDayButton.setUserData(5 * 24 * 3600);
        thirtyDayButton.setUserData(30 * 24 * 3600);

//        screensTabPane.setStyle("-fx-background-color:#000000");
    }

    public AnchorPane getSystemMapScreen() {
        if (systemMapScreen == null) {
            try {
                FXMLLoader test = new FXMLLoader();
                test.setControllerFactory(new SpringFXControllerFactory(ViewerFX.
                        getCurrentApplication().getSprintContainer()));
                systemMapScreen = (AnchorPane) test.load(getClass().
                        getResourceAsStream("/com/newdawn/gui/map/system/SystemMapScreen.fxml"));
            } catch (IOException ex) {
                Logger.getLogger(ViewerFX.class.getName()).
                        log(Level.SEVERE, null, ex);
                throw new RuntimeException(ex);
            }
        }
        return systemMapScreen;
    }

    public AnchorPane getFleetManagementScreen() {
        if (fleetManagementScreen == null) {
            try {
                FXMLLoader test = new FXMLLoader();
                test.setControllerFactory(
                        new SpringFXControllerFactory(ViewerFX.
                        getCurrentApplication().getSprintContainer()));
                fleetManagementScreen = (AnchorPane) test.load(getClass().
                        getResourceAsStream("/com/newdawn/gui/fleet/FleetManagementScreen.fxml"));

            } catch (IOException ex) {
                Logger.getLogger(ViewerFX.class.getName()).
                        log(Level.SEVERE, null, ex);
                throw new RuntimeException(ex);
            }
        }
        return fleetManagementScreen;
    }

    @FXML
    public void showScreenTabAction(ActionEvent event) {
        Object data = Utils.getUserData(event.getSource());
        if (data != null && data instanceof Tab) {
            Tab tabToShow = (Tab) data;
            if (screensTabPane.getTabs().contains(tabToShow)) {
                screensTabPane.getSelectionModel().select(tabToShow);
            } else {
                screensTabPane.getTabs().add(tabToShow);
            }
        }
    }

    @FXML
    public void pushDurationButton(ActionEvent event) {
        Object data = Utils.getUserData(event.getSource());
        if (data != null && data instanceof Integer) {
            Integer duration = (Integer) data;
            mainController.runIncrements(duration);
        }
    }

    private Node getEconomicScreen() {

        if (economicScreen == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/newdawn/gui/economic/EconomicScreen.fxml"));
            loader.setControllerFactory(
                    new SpringFXControllerFactory(ViewerFX.getCurrentApplication().
                    getSprintContainer()));
            try {
                economicScreen = (Node) loader.load();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        return economicScreen;
    }

    public Node getPersonnelManagementScreen() {
        if (personnelManagementScreen == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/newdawn/gui/personnel/PersonnelManagementScreen.fxml"));
            loader.setControllerFactory(
                    new SpringFXControllerFactory(ViewerFX.getCurrentApplication().
                    getSprintContainer()));
            try {
                personnelManagementScreen = (Node) loader.load();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        return personnelManagementScreen;
    }

    @FXML
    public void launchTest(ActionEvent event) {
        Skill leadSkill = ViewerFX.getCurrentApplication().getSprintContainer().getBean("leadership", Skill.class);
        Skill geoSkill = ViewerFX.getCurrentApplication().getSprintContainer().getBean("geology", Skill.class);
        SkillLevel skillLevel = new SkillLevel(leadSkill);
        skillLevel.setLevel(75);
        gameData.getPersonnelMembers().get(0).skillLevelsProperty().put(leadSkill, skillLevel);

        Colony test = new Colony();
        test.setPopulation(100_000_000);
        test.setPopulationGrowRate(1);
        test.setWealthProduction(500);
        test.setName("Test");
        Planet planet = gameData.getStellarSystems().get(0).getPlanets().get(0);
        colonyController.updateSystemWithColony(planet, test);

        Scientist testScientist1 = new Scientist();
        testScientist1.setName("testScientist1");
        testScientist1.setLocalization(test);
        Scientist testScientist2 = new Scientist();
        testScientist2.setName("testScientist2");
        testScientist2.setLocalization(test);

        NavalOfficer navalOfficer1 = new NavalOfficer();
        navalOfficer1.setName("navalOfficer1");
        navalOfficer1.setLocalization(test);

//        gameData.getPersonnelMembers().addAll(testScientist1, testScientist2, navalOfficer1);
    }
}
