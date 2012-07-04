package com.newdawn.gui.fleet;

import com.newdawn.controllers.GameData;
import com.newdawn.gui.map.system.PropertyListCellFactory;
import com.newdawn.model.ships.Ship;
import com.newdawn.model.ships.Squadron;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import viewerfx.ViewerFX;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class FleetManagementScreen implements Initializable {

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

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        final GameData gameData = ViewerFX.getCurrentApplication().
                getSprintContainer().getBean(GameData.class);
//        systemListView = new ListView<>(gameData.getStellarSystems());
        systemListView.setItems(gameData.getStellarSystems());
        systemListView.setCellFactory(new PropertyListCellFactory<StellarSystem>("name", null));
        systemListView.setPrefHeight(50);

        final ObjectBinding<ObservableList<Squadron>> select = Bindings.select(systemListView.
                getSelectionModel().selectedItemProperty(), "squadrons");
        squadronListView.itemsProperty().bind(select);

        squadronListView.setCellFactory(new PropertyListCellFactory<Squadron>("name", null));
        squadronListView.getSelectionModel().selectedItemProperty().
                addListener(new ChangeListener<Object>() {
//
            @Override
            public void changed(ObservableValue<? extends Object> property, Object oldValue, Object newValue) {
//                    getSquadronShipListTableView().getItems().clear();
                if (newValue != null) {
                    Squadron squadron = (Squadron) newValue;
                    shipsTableView.setItems(squadron.getShips());
                } else {
                    final ObservableList<Ship> emptyObservableList = FXCollections.
                            emptyObservableList();
                    shipsTableView.setItems(emptyObservableList);
                }
            }
        });

        squadronSpeedSelectionComponent.squadronProperty().bind(squadronListView.
                getSelectionModel().selectedItemProperty());


        shipNameColumn.setCellValueFactory(new PropertyValueFactory<Ship, String>("name"));
    }
}
