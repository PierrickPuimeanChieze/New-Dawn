package com.newdawn.gui.fleet;

import com.newdawn.controllers.GameData;
import com.newdawn.controllers.utils.ShipUtils;
import com.newdawn.gui.PropertyListCellFactory;
import com.newdawn.gui.PropertyOrToStringTreeCellFactory;
import com.newdawn.model.ships.Ship;
import com.newdawn.model.ships.Squadron;
import com.newdawn.model.ships.orders.factory.OrderFactory;
import com.newdawn.model.system.Planet;
import com.newdawn.model.system.Star;
import com.newdawn.model.system.StellarSystem;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import viewerfx.ViewerFX;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class FleetManagementScreen implements Initializable {

    private TreeItem planetRoot = new TreeItem("Planets");
    private TreeItem root = new TreeItem();
    private TreeItem starRoot = new TreeItem("Stars");
    private TreeItem squadronRoot = new TreeItem("Squadron");
    @FXML
    private ListView<StellarSystem> systemListView;
    @FXML
    private ListView<Squadron> squadronListView;
    @FXML
    private TableView<Ship> shipsTableView;
    @FXML
    private SpeedSelectionComponent squadronSpeedSelectionComponent;
    @FXML
    private TableColumn<Ship, String> shipNameColumn;
    @FXML
    private TreeView availableLocationsTreeView;
    @FXML
    private ListView<OrderFactory> availableOrdersListView;
    @FXML
    private ListView plottedOrdersListView;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        final GameData gameData = ViewerFX.getCurrentApplication().
                getSprintContainer().getBean(GameData.class);
        initSystemListView(gameData);
        initSquadronListView();
        initSquadronInformation();
        initShipListView();
        availableLocationsTreeView.setCellFactory(new PropertyOrToStringTreeCellFactory("name", String.class, null));
        root.getChildren().add(starRoot);
        root.getChildren().add(planetRoot);
        root.getChildren().add(squadronRoot);
    }

    private void initShipListView() {
        shipNameColumn.setCellValueFactory(new PropertyValueFactory<Ship, String>("name"));
    }

    private void initSquadronInformation() {
        squadronSpeedSelectionComponent.squadronProperty().bind(squadronListView.
                getSelectionModel().selectedItemProperty());
    }

    private void initSquadronListView() {
        final ObjectBinding<ObservableList<Squadron>> select = Bindings.select(systemListView.
                getSelectionModel().selectedItemProperty(), "squadrons");
        squadronListView.itemsProperty().bind(select);

        squadronListView.setCellFactory(new PropertyListCellFactory<Squadron>("name", null));
        //TODO change for a binding
        squadronListView.getSelectionModel().selectedItemProperty().
                addListener(new ChangeListener<Object>() {

            @Override
            public void changed(ObservableValue<? extends Object> property, Object oldValue, Object newValue) {
                Squadron squadron = (Squadron) newValue;
                updateAvailableLocation(squadron);
                if (newValue != null) {
                    shipsTableView.setItems(squadron.getShips());
                } else {
                    final ObservableList<Ship> emptyObservableList = FXCollections.
                            emptyObservableList();
                    shipsTableView.setItems(emptyObservableList);
                }
            }
        });

    }

    private void initSystemListView(final GameData gameData) {
        //        systemListView = new ListView<>(gameData.getStellarSystems());
        systemListView.setItems(gameData.getStellarSystems());
        systemListView.setCellFactory(new PropertyListCellFactory<StellarSystem>("name", null));
        systemListView.setPrefHeight(50);
    }

    private void updateAvailableLocation(Squadron squadron) {
        if (squadron == null) {

            availableLocationsTreeView.setRoot(null);
        } else {
            StellarSystem stellarSystem = ShipUtils.
                    calculateContextualStellarSystem(squadron);
            planetRoot.getChildren().clear();
            for (Planet planet : stellarSystem.getPlanets()) {
                TreeItem treeItem = new TreeItem(planet);
                planetRoot.getChildren().add(treeItem);
            }
            starRoot.getChildren().clear();
            for (Star star : stellarSystem.getStars()) {
                starRoot.getChildren().add(new TreeItem(star));
            }
            squadronRoot.getChildren().clear();
            for (Squadron squadronChild : stellarSystem.getSquadrons()) {
                if (squadronChild != squadron) {
                    squadronRoot.getChildren().add(new TreeItem(squadronChild));
                }
            }
            availableLocationsTreeView.setRoot(root);
        }
    }
}
