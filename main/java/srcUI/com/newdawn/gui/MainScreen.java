package com.newdawn.gui;

import com.newdawn.controllers.ColonyController;
import com.newdawn.controllers.GameData;
import com.newdawn.controllers.MainController;
import com.newdawn.gui.economic.ColonyEconomicScreen;
import com.newdawn.gui.fleet.FleetManagementScreen;
import com.newdawn.model.colony.Colony;
import com.newdawn.model.system.Planet;
import com.sun.javafx.binding.ContentBinding;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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
    private TabPane screensTabPane;
    //TODO See to move them to the fxml
    @FXML
    private Tab economicScreenTab;
    @FXML
    private Tab systemMapScreenTab;
    @FXML
    private Tab fleetManagementScreenTab;
    private AnchorPane systemMapScreen;
    private AnchorPane fleetManagementScreen;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        fleetManagementScreenTab.setContent(getFleetManagementScreen());
        systemMapScreenTab.setContent(getSystemMapScreen());
        economicScreenTab.setContent(getEconomicScreen());

        economicScreenMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.F2));
        economicScreenMenuItem.setUserData(economicScreenTab);

        systemMapScreenMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.F3));
        systemMapScreenMenuItem.setUserData(systemMapScreenTab);

        fleetManagementScreenMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.F12));
        fleetManagementScreenMenuItem.setUserData(fleetManagementScreenTab);

        screensTabPane.getTabs().removeAll(systemMapScreenTab, fleetManagementScreenTab);
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

        Bindings.bindContent(new ArrayList<>(), FXCollections.
                observableArrayList());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/newdawn/gui/economic/EconomicScreen.fxml"));
        loader.setControllerFactory(
                new SpringFXControllerFactory(ViewerFX.getCurrentApplication().
                getSprintContainer()));
        try {
            Node toReturn = (Node) loader.load();
//            ((ColonyEconomicScreen)loader.getController()).setColony(test);
            return toReturn;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @FXML
    public void launchTest(ActionEvent event) {
        Colony test = new Colony();
        test.setPopulation(100_000_000);
        test.setPopulationGrowRate(1);
        test.setName("Test");
        Planet planet = gameData.getStellarSystems().get(0).getPlanets().get(0);
        colonyController.updateSystemWithColony(planet, test);
    }
}
