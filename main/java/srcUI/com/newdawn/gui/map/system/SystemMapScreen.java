package com.newdawn.gui.map.system;

import com.newdawn.controllers.GameData;
import com.newdawn.gui.PropertyListCellFactory;
import com.newdawn.gui.Utils;
import com.newdawn.model.system.StellarSystem;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class SystemMapScreen implements Initializable {

    private Map<StellarSystem, Tab> openedTabs = new HashMap<>();
    @FXML
    private ListView<StellarSystem> stellarSystemListView;
    @FXML
    private TabPane systemViewerTabPane;
    private static Log LOG = LogFactory.getLog(SystemMapScreen.class);

    @FXML
    public void handleClickOnStellarSystemsListView(MouseEvent event) {

        if (event.getClickCount() >= 2) {

            StellarSystem clickedSystem = stellarSystemListView.
                    getSelectionModel().getSelectedItem();
            openOrSelectSystemViewer(clickedSystem);
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        BeanFactory factory = viewerfx.ViewerFX.getCurrentApplication().
                getSprintContainer();
        GameData gameData = factory.getBean(GameData.class);
        Bindings.bindContent(stellarSystemListView.getItems(), gameData.
                getStellarSystems());
        stellarSystemListView.setCellFactory(new PropertyListCellFactory<StellarSystem>("name", null));
    }

    private void openOrSelectSystemViewer(final StellarSystem clickedSystem) {
        Tab openedTab = openedTabs.get(clickedSystem);
        if (openedTab == null) {
            openedTab = new Tab(clickedSystem.getName());
            final SystemViewer systemViewer = new SystemViewer(clickedSystem);
//            openedTab.setClosable(true);
            openedTab.setContent(systemViewer);
            openedTab.setOnClosed(new EventHandler<Event>() {

                @Override
                public void handle(Event event) {
                    openedTabs.remove(clickedSystem);
                }
            });
            systemViewerTabPane.getTabs().add(openedTab);
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
            systemViewer.setOnKeyPressed(new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent arg0) {
                    testPressed(arg0);
                }
            });

        }
        systemViewerTabPane.getSelectionModel().select(openedTab);
    }

    @FXML
    public void testPressed(KeyEvent event) {
        LOG.debug(Utils.formatKeyEvent(event));
        Tab selectedItem = systemViewerTabPane.getSelectionModel().
                getSelectedItem();
        if (selectedItem != null) {
            SystemViewer selectedViewer = (SystemViewer) selectedItem.getContent();
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
                    selectedViewer.setZoomLevel(selectedViewer.getZoomLevel() + increment);
                    break;
                case MINUS:
                case SUBTRACT:
                    selectedViewer.setZoomLevel(selectedViewer.getZoomLevel() - increment);
                    break;

                default:
                    break;
            }

        }


    }
}
