package com.newdawn.gui;

import com.newdawn.controllers.ColonyController;
import com.newdawn.controllers.GameData;
import com.newdawn.controllers.MainController;
import com.newdawn.controllers.OfficialsController;
import com.newdawn.model.colony.Colony;
import com.newdawn.model.personnel.NavalOfficer;
import com.newdawn.model.personnel.PersonnelMember;
import com.newdawn.model.personnel.Scientist;
import com.newdawn.model.personnel.Skill;
import com.newdawn.model.personnel.SkillLevel;
import com.newdawn.model.system.Planet;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.MapProperty;
import javafx.collections.ObservableList;
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
    private OfficialsController officialsFactory;
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
    private MenuItem teamManagementScreenMenuItem;
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
    @FXML
    private Tab teamManagementScreenTab;
    //TODO use include in MainScreen.fxml
    private Node economicScreen;
    private AnchorPane systemMapScreen;
    private Node personnelManagementScreen;
    private Node teamManagementScreen;
    private AnchorPane fleetManagementScreen;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        fleetManagementScreenTab.setContent(getFleetManagementScreen());
        systemMapScreenTab.setContent(getSystemMapScreen());
        economicScreenTab.setContent(getEconomicScreen());
        personnelManagementScreenTab.setContent(getPersonnelManagementScreen());
        teamManagementScreenTab.setContent(getTeamManagementScreen());
        economicScreenMenuItem.
                setAccelerator(new KeyCodeCombination(KeyCode.F2));
        economicScreenMenuItem.setUserData(economicScreenTab);

        systemMapScreenMenuItem.
                setAccelerator(new KeyCodeCombination(KeyCode.F3));
        systemMapScreenMenuItem.setUserData(systemMapScreenTab);

        personnelManagementScreenMenuItem.
                setAccelerator(new KeyCodeCombination(KeyCode.F4));
        personnelManagementScreenMenuItem.
                setUserData(personnelManagementScreenTab);

        teamManagementScreenMenuItem.
                setAccelerator(new KeyCodeCombination(KeyCode.F5));
        teamManagementScreenMenuItem.setUserData(teamManagementScreenTab);

        fleetManagementScreenMenuItem.
                setAccelerator(new KeyCodeCombination(KeyCode.F12));
        fleetManagementScreenMenuItem.setUserData(fleetManagementScreenTab);

        screensTabPane.getTabs().
                removeAll(systemMapScreenTab, fleetManagementScreenTab, economicScreenTab);
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

    private AnchorPane getSystemMapScreen() {
        if (systemMapScreen == null) {
            try {
                FXMLLoader test = new FXMLLoader();
                test.
                        setControllerFactory(new SpringFXControllerFactory(ViewerFX.
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

    private AnchorPane getFleetManagementScreen() {
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
            FXMLLoader loader = new FXMLLoader(getClass().
                    getResource("/com/newdawn/gui/economic/EconomicScreen.fxml"));
            loader.setControllerFactory(
                    new SpringFXControllerFactory(ViewerFX.
                    getCurrentApplication().
                    getSprintContainer()));
            try {
                economicScreen = (Node) loader.load();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        return economicScreen;
    }

    private Node getPersonnelManagementScreen() {
        if (personnelManagementScreen == null) {
            FXMLLoader loader = new FXMLLoader(getClass().
                    getResource("/com/newdawn/gui/personnel/PersonnelManagementScreen.fxml"));
            loader.setControllerFactory(
                    new SpringFXControllerFactory(ViewerFX.
                    getCurrentApplication().
                    getSprintContainer()));
            try {
                personnelManagementScreen = (Node) loader.load();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        return personnelManagementScreen;
    }

    private Node getTeamManagementScreen() {
        if (teamManagementScreen == null) {
            FXMLLoader loader = new FXMLLoader(getClass().
                    getResource("/com/newdawn/gui/personnel/TeamManagementScreen.fxml"));
            loader.setControllerFactory(
                    new SpringFXControllerFactory(ViewerFX.
                    getCurrentApplication().
                    getSprintContainer()));
            try {
                teamManagementScreen = (Node) loader.load();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        return teamManagementScreen;
    }

    @FXML
    public void launchTest(ActionEvent event) {
        Skill leadSkill = ViewerFX.getCurrentApplication().getSprintContainer().
                getBean("leadership", Skill.class);
        Skill geoSkill = ViewerFX.getCurrentApplication().getSprintContainer().
                getBean("geology", Skill.class);

        final PersonnelMember officials = gameData.getPersonnelMembers().get(0);
        SkillLevel leadSkillLevel = officials.skillLevelsProperty().
                get(leadSkill);
        if (leadSkillLevel == null) {
            leadSkillLevel = new SkillLevel(leadSkill);
            officials.skillLevelsProperty().
                    put(leadSkill, leadSkillLevel);
        }
        leadSkillLevel.setLevel(75);


        SkillLevel geoSkillLevel = officials.skillLevelsProperty().get(geoSkill);

        if (geoSkillLevel == null) {
            geoSkillLevel = new SkillLevel(geoSkill);
            officials.skillLevelsProperty().
                    put(geoSkill, geoSkillLevel);
        }
        geoSkillLevel.setLevel(75);
        final PersonnelMember navalOfficer3 = officialsFactory.getPersonnelMemberByName("navalOfficer3");
        navalOfficer3.skillLevelsProperty().get(geoSkill).setLevel(75);
        Colony test = new Colony();
        test.setPopulation(100_000_000);
        test.setPopulationGrowRate(1);
        test.setWealthProduction(500);
        test.setName("Test");
        Planet planet = gameData.getStellarSystems().get(0).getPlanets().get(0);
        colonyController.updateSystemWithColony(planet, test);

        Scientist testScientist1 = officialsFactory.
                createNewScientist("testScientist1", test);
        Scientist testScientist2 = officialsFactory.
                createNewScientist("testScientist2", test);
        NavalOfficer navalOfficer1 = officialsFactory.
                createNewNavalOfficer("navalOfficer1", test);
//        gameData.getPersonnelMembers().addAll(testScientist1, testScientist2, navalOfficer1);
    }
}
