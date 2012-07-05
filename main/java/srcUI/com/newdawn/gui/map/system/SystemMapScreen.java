/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newdawn.gui.map.system;

import com.newdawn.controllers.GameData;
import com.newdawn.model.system.StellarSystem;
import java.util.HashMap;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.springframework.beans.factory.BeanFactory;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class SystemMapScreen extends HBox {

    private ListView<StellarSystem> availableSystemListView;
    private TabPane systemViewers;
    private Map<StellarSystem, Tab> openedTabs = new HashMap<>();
    private EventHandler<MouseEvent> listMouseHandler;
    private EventHandler<KeyEvent> keyboardHandler;
    private HBox debugCenterToBox;
    private TextField debugCenterToXTextField;
    private TextField debugCenterToYTextField;
    private Button debugCenterToValidButton;

    public SystemMapScreen() {
        initComponents();

    }

    private void initComponents() {
        HBox.setHgrow(getSystemViewers(), Priority.ALWAYS);
        getChildren().addAll(getDebugCenterToBox(), getAvailableSystemListView(), getSystemViewers());
        setOnKeyPressed(getKeyboardHandler());
    }

    public ListView<StellarSystem> getAvailableSystemListView() {
        if (availableSystemListView == null) {
            BeanFactory factory = viewerfx.ViewerFX.getCurrentApplication().
                    getSprintContainer();
            GameData gameData = factory.getBean(GameData.class);
            availableSystemListView = new ListView<>(gameData.getStellarSystems());
            availableSystemListView.setCellFactory(new PropertyListCellFactory<StellarSystem>("name", getListMouseHandler()));
        }
        return availableSystemListView;
    }

    public TabPane getSystemViewers() {
        if (systemViewers == null) {
            systemViewers = new TabPane();

        }
        return systemViewers;
    }

    private EventHandler<MouseEvent> getListMouseHandler() {
        if (listMouseHandler == null) {
            listMouseHandler = new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    if (event.getClickCount() >= 2) {

                        if (event.getSource() instanceof Cell) {
                            Cell cell = (Cell) event.getSource();
                            if (cell.getItem() instanceof StellarSystem) {
                                StellarSystem clickedSystem = (StellarSystem) cell.
                                        getItem();
                                openOrSelectSystemViewe(clickedSystem);
                            }
                        }
                    }
                }
            };
        }
        return listMouseHandler;
    }

    private void openOrSelectSystemViewe(final StellarSystem clickedSystem) {
        Tab openedTab = openedTabs.get(clickedSystem);
        if (openedTab == null) {
            openedTab = new Tab(clickedSystem.getName());
            final SystemViewer systemViewer = new SystemViewer(clickedSystem);
            openedTab.setContent(systemViewer);
            openedTab.setOnClosed(new EventHandler<Event>() {

                @Override
                public void handle(Event event) {
                    openedTabs.remove(clickedSystem);
                }
            });
            getSystemViewers().getTabs().add(openedTab);
            openedTabs.put(clickedSystem, openedTab);
            systemViewer.updateChildren();
            openedTab.setOnSelectionChanged(new EventHandler<Event>() {

                @Override
                public void handle(Event arg0) {
                    Tab tab = (Tab) arg0.getSource();
                    if (tab.isSelected()) {
                        systemViewer.requestFocus();
                    }
                }
            });
        }
        getSystemViewers().getSelectionModel().select(openedTab);
    }

    public EventHandler<KeyEvent> getKeyboardHandler() {
        if (keyboardHandler == null) {
            keyboardHandler = new EventHandler<KeyEvent>() {

                public void handle(KeyEvent event) {
                    SystemViewer selectedViewer = (SystemViewer) systemViewers.
                            getSelectionModel().getSelectedItem().getContent();
                    double increment = 0.01;
                    if (event.isControlDown()) {
                        increment = 0.1;
                    }


                    switch (event.getCode()) {
//                    case DOWN:
//                        components.setTranslateY(getTranslateY() - 10);
//                        break;
//                    case UP:
//                        components.setTranslateY(getTranslateY() + 10);
//                        break;
//                    case RIGHT:
//                        components.setTranslateX(getTranslateX() - 10);
//                        break;
                        case LEFT:
                            selectedViewer.centerTo(0, 0);
                            break;
                        case PLUS:
                        case ADD:
                            selectedViewer.setZoomLevel(selectedViewer.
                                    getZoomLevel() + increment);
                            break;
                        case MINUS:
                        case SUBTRACT:
                            selectedViewer.setZoomLevel(selectedViewer.
                                    getZoomLevel() - increment);
                            break;

                        default:
                            break;
                    }


                }
            };
        }
        return keyboardHandler;
    }

    public HBox getDebugCenterToBox() {
        if (debugCenterToBox == null) {
            debugCenterToBox = new HBox();
            debugCenterToBox.getChildren().addAll(getDebugCenterToXTextField(), getDebugCenterToYTextField(), getDebugCenterToValidButton());
        }
        return debugCenterToBox;
    }

    public Button getDebugCenterToValidButton() {
        if (debugCenterToValidButton == null) {
            debugCenterToValidButton = new Button();
            debugCenterToValidButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    SystemViewer selectedViewer = (SystemViewer) systemViewers.
                            getSelectionModel().getSelectedItem().getContent();
                    double x = Double.parseDouble(getDebugCenterToXTextField().
                            getText());
                    double y = Double.parseDouble(getDebugCenterToYTextField().
                            getText());

                    selectedViewer.centerTo(x, y);
                }
            });
        }
        return debugCenterToValidButton;
    }

    public TextField getDebugCenterToXTextField() {
        if (debugCenterToXTextField == null) {
            debugCenterToXTextField = new TextField();
        }
        return debugCenterToXTextField;
    }

    public TextField getDebugCenterToYTextField() {
        if (debugCenterToYTextField == null) {
            debugCenterToYTextField = new TextField();
        }
        return debugCenterToYTextField;
    }
}
