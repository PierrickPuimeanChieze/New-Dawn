package com.newdawn.gui.map.system;

import com.newdawn.controllers.GameData;
import com.newdawn.gui.PropertyListCellFactory;
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
import javafx.scene.control.Cell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
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
        }
        systemViewerTabPane.getSelectionModel().select(openedTab);
    }
}
