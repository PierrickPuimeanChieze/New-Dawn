/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewerfx;

import com.newdawn.controllers.GameData;
import com.newdawn.controllers.InitialisationController;
import com.newdawn.controllers.MainController;
import com.newdawn.gui.fleet.FleetManagementScreenOld;
import com.newdawn.gui.map.system.SystemMapScreen;
import com.newdawn.model.system.StellarSystem;
import java.io.File;



import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * * @author Pierrick Puimean-Chieze
 */
public class ViewerFX extends Application {

    private static ViewerFX currentApplication;
    private ApplicationContext sprintContainer;
    private Menu fileMenu;
    private Menu screenMenu;
    private MenuItem systemMapScreenMenuItem;
    private MenuItem fleetManagementScreenMenuItem;
    private MainController mainController;
    private TabPane screenTabPane;
    private SystemMapScreen systemMapScreen;
    private Tab systemMapScreenTab;
    private HBox fleetManagementScreen;
    private Tab fleetManagementScreenTab;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        launch(args);
    }
    private MenuBar menuBar;
    private ToolBar runToolBar;
    private Button fiveSecRunButton;
    private Button thirtySecRunButton;
    private Button fiveMinRunButton;
    private Button twentyMinRunButton;
    private Button oneHourRunButton;
    private Button threeHoursRunButton;
    private Button eightHourRunButton;
    private Button oneDayRunButton;
    private Button fiveDaysRunButton;
    private Button thirtyDaysRunButton;

    @Override
    public void init() throws Exception {
        super.init();

        ViewerFX.currentApplication = this;

        sprintContainer = new ClassPathXmlApplicationContext("/spring/newdawn.xml");
        this.mainController = sprintContainer.getBean(MainController.class);

        InitialisationController initialisationController = sprintContainer.
                getBean(InitialisationController.class);
        final StellarSystem solarSystem = SollarSystemBuilder.getIt(initialisationController);
        final StellarSystem solarSystem2 = SollarSystem2Builder.getIt(initialisationController);
        StellarSystem testSystem = initialisationController.createSystem(getClass().
                getResourceAsStream("/testSystem.xml"));
        GameData gameData = sprintContainer.getBean(GameData.class);
        gameData.getStellarSystems().addAll(solarSystem, solarSystem2, testSystem);
    }

    @Override
    public void start(Stage primaryStage) {

//        final SystemViewer systemViewer = new SystemViewer(mainController.getGameData().getStellarSystems().get(0));


        VBox vBox = new VBox();

        VBox.setVgrow(getScreenTabPane(), Priority.ALWAYS);
        vBox.getChildren().add(getMenuBar());
        vBox.getChildren().add(getRunToolBar());
        vBox.getChildren().add(getScreenTabPane());
        final Scene scene = new Scene(vBox);



        primaryStage.setScene(scene);
        primaryStage.show();
//        systemViewer.updateChildren();
    }

    public MenuBar getMenuBar() {
        if (menuBar == null) {
            menuBar = new MenuBar();
            menuBar.getMenus().addAll(getFileMenu(), getScreenMenu());
        }
        return menuBar;
    }

    public Menu getFileMenu() {
        if (fileMenu == null) {
            fileMenu = new Menu("File");
        }
        return fileMenu;
    }

    public Menu getScreenMenu() {
        if (screenMenu == null) {
            screenMenu = new Menu("Screens");
            screenMenu.getItems().addAll(getSystemMapScreenMenuItem(), getFleetManagementScreenMenuItem());
        }
        return screenMenu;
    }

    public MenuItem getSystemMapScreenMenuItem() {
        if (systemMapScreenMenuItem == null) {
            systemMapScreenMenuItem = new MenuItem("System Map");
            systemMapScreenMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.F3));
            systemMapScreenMenuItem.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    if (getScreenTabPane().getTabs().contains(getSystemMapScreenTab())) {
                        getScreenTabPane().getSelectionModel().select(getSystemMapScreenTab());
                    } else {
                        getScreenTabPane().getTabs().add(getSystemMapScreenTab());
                    }
                }
            });
        }
        return systemMapScreenMenuItem;
    }

    public MenuItem getFleetManagementScreenMenuItem() {
        if (fleetManagementScreenMenuItem == null) {
            fleetManagementScreenMenuItem = new MenuItem("Fleet Management");
            fleetManagementScreenMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.F12));
            fleetManagementScreenMenuItem.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    if (getScreenTabPane().getTabs().contains(getFleetManagementScreenTab())) {
                        getScreenTabPane().getSelectionModel().select(getFleetManagementScreenTab());
                    } else {
                        getScreenTabPane().getTabs().add(getFleetManagementScreenTab());
                    }
                }
            });
        }
        return fleetManagementScreenMenuItem;
    }

    public static ViewerFX getCurrentApplication() {
        return currentApplication;
    }

    public BeanFactory getSprintContainer() {
        return sprintContainer;
    }

    public TabPane getScreenTabPane() {
        if (screenTabPane == null) {
            screenTabPane = new TabPane();
        }
        return screenTabPane;
    }

    public SystemMapScreen getSystemMapScreen() {
        if (systemMapScreen == null) {
            systemMapScreen = new SystemMapScreen();
        }
        return systemMapScreen;
    }

    public Tab getSystemMapScreenTab() {
        if (systemMapScreenTab == null) {
            systemMapScreenTab = new Tab("System Screen");
            systemMapScreenTab.setContent(getSystemMapScreen());
        }
        return systemMapScreenTab;
    }

    public HBox getFleetManagementScreen() {
        if (fleetManagementScreen == null) {
            try {
                FXMLLoader test = new FXMLLoader();

//                fleetManagementScreen = new FleetManagementScreenOld();
                fleetManagementScreen = (HBox) test.load(getClass().
                        getResourceAsStream("/com/newdawn/gui/fleet/FleetManagementScreen.fxml"));
            } catch (IOException ex) {
                Logger.getLogger(ViewerFX.class.getName()).
                        log(Level.SEVERE, null, ex);
                throw new RuntimeException(ex);
            }
        }
        return fleetManagementScreen;
    }

    public Tab getFleetManagementScreenTab() {
        if (fleetManagementScreenTab == null) {
            fleetManagementScreenTab = new Tab("Fleet Management");
            fleetManagementScreenTab.setContent(getFleetManagementScreen());
        }
        return fleetManagementScreenTab;
    }

    public ToolBar getRunToolBar() {
        if (runToolBar == null) {
            runToolBar = new ToolBar(getFiveSecRunButton(), getThirtySecRunButton(), getFiveMinRunButton(), getTwentyMinRunButton(), getOneHourRunButton(), getThreeHoursRunButton(), getEightHourRunButton(), getOneDayRunButton(), getFiveDaysRunButton(), getThirtyDaysRunButton());
        }
        return runToolBar;
    }

    public Button getFiveSecRunButton() {
        if (fiveSecRunButton == null) {
            fiveSecRunButton = new Button("5s");
            fiveSecRunButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    mainController.runIncrements(5);
                }
            });

        }
        return fiveSecRunButton;
    }

    public Button getThirtySecRunButton() {
        if (thirtySecRunButton == null) {
            thirtySecRunButton = new Button("30s");
            thirtySecRunButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    mainController.runIncrements(30);
                }
            });
        }
        return thirtySecRunButton;
    }

    public Button getFiveMinRunButton() {
        if (fiveMinRunButton == null) {
            fiveMinRunButton = new Button("5m");
            fiveMinRunButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    mainController.runIncrements(300);
                }
            });
        }
        return fiveMinRunButton;
    }

    public Button getTwentyMinRunButton() {
        if (twentyMinRunButton == null) {
            twentyMinRunButton = new Button("20m");
            twentyMinRunButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    mainController.runIncrements(1200);
                }
            });
        }
        return twentyMinRunButton;
    }

    public Button getOneHourRunButton() {
        if (oneHourRunButton == null) {
            oneHourRunButton = new Button("1h");
            oneHourRunButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    mainController.runIncrements(3600);
                }
            });
        }
        return oneHourRunButton;
    }

    public Button getThreeHoursRunButton() {
        if (threeHoursRunButton == null) {
            threeHoursRunButton = new Button("3h");
            threeHoursRunButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    mainController.runIncrements(10800);
                }
            });
        }
        return threeHoursRunButton;
    }

    public Button getEightHourRunButton() {
        if (eightHourRunButton == null) {
            eightHourRunButton = new Button("8h");
            eightHourRunButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    mainController.runIncrements(28800);
                }
            });
        }
        return eightHourRunButton;
    }

    public Button getOneDayRunButton() {
        if (oneDayRunButton == null) {
            oneDayRunButton = new Button("1d");
            oneDayRunButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    mainController.runIncrements(86400);
                }
            });
        }
        return oneDayRunButton;
    }

    public Button getFiveDaysRunButton() {
        if (fiveDaysRunButton == null) {
            fiveDaysRunButton = new Button("5d");
            fiveDaysRunButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    mainController.runIncrements(432000);
                }
            });
        }
        return fiveDaysRunButton;
    }

    public Button getThirtyDaysRunButton() {
        if (thirtyDaysRunButton == null) {
            thirtyDaysRunButton = new Button("30d");
            thirtyDaysRunButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    mainController.runIncrements(2592000);
                }
            });
        }
        return thirtyDaysRunButton;
    }
}
