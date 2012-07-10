package com.newdawn.gui;

import com.newdawn.controllers.MainController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.AnchorPane;
import viewerfx.ViewerFX;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class MainScreen implements Initializable {

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
    private MenuItem systemMapScreenMenuItem;
    @FXML
    private MenuItem fleetManagementScreenMenuItem;
    @FXML
    private TabPane screensTabPane;
    //TODO See to move them to the fxml
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

        fleetManagementScreenMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.F12));
        fleetManagementScreenMenuItem.setUserData(fleetManagementScreenTab);
        systemMapScreenMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.F3));
        systemMapScreenMenuItem.setUserData(systemMapScreenTab);

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
        if (data != null && data  instanceof Tab) {
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
            MainController mainController = ViewerFX.getCurrentApplication().
                    getSprintContainer().getBean(MainController.class);
            mainController.runIncrements(duration);
        }
    }
}
