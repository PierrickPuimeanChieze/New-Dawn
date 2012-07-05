/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newdawn.gui.fleet;

import com.newdawn.controllers.GameData;
import com.newdawn.gui.map.system.PropertyListCellFactory;
import com.newdawn.model.ships.Ship;
import com.newdawn.model.ships.Squadron;
import com.newdawn.model.system.StellarSystem;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import viewerfx.ViewerFX;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class FleetManagementScreenOld extends GridPane {

    private TitledPane squadronSelectionPane;
    private GridPane squadronSelectionLayout;
    private Label systemSelectionListViewLabel;
    private ListView<StellarSystem> systemSelectionListView;
    private Label squadronSelectionListViewLabel;
    private ListView<Squadron> squadronSelectionListView;
    private TitledPane squadronShipListPane;
    private TableView<Ship> squadronShipListTableView;
    private SpeedSelectionComponent squadronSpeedSelectionPane;

    public FleetManagementScreenOld() {
        initComponents();
    }

    private void initComponents() {
        ColumnConstraints column0 = new ColumnConstraints();
        ColumnConstraints column1 = new ColumnConstraints();
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setHgrow(Priority.ALWAYS);
        this.getColumnConstraints().addAll(column0, column1, column2);
        this.add(getSquadronSelectionPane(), 0, 0);
        this.add(getSquadronSpeedSelectionPane(), 1, 0);
        this.add(getSquadronShipListPane(), 2, 0);
    }

    public TitledPane getSquadronSelectionPane() {
        if (squadronSelectionPane == null) {
            squadronSelectionPane = new TitledPane("Squadron Selection", getSquadronSelectionLayout());
            squadronSelectionPane.setCollapsible(false);
        }
        return squadronSelectionPane;
    }

    private GridPane getSquadronSelectionLayout() {
        if (squadronSelectionLayout == null) {
            squadronSelectionLayout = new GridPane();
            squadronSelectionLayout.add(getSystemSelectionListViewLabel(), 0, 0);
            squadronSelectionLayout.add(getSystemSelectionListView(), 0, 1);
            squadronSelectionLayout.add(getSquadronSelectionListViewLabel(), 1, 0);
            squadronSelectionLayout.add(getSquadronSelectionListView(), 1, 1);
        }
        return squadronSelectionLayout;
    }

    public ListView<StellarSystem> getSystemSelectionListView() {
        if (systemSelectionListView == null) {

            final GameData gameData = ViewerFX.getCurrentApplication().
                    getSprintContainer().getBean(GameData.class);
            systemSelectionListView = new ListView<>(gameData.getStellarSystems());
            systemSelectionListView.setCellFactory(new PropertyListCellFactory<StellarSystem>("name", null));
            systemSelectionListView.setPrefHeight(50);

        }
        return systemSelectionListView;
    }

    public ListView<Squadron> getSquadronSelectionListView() {
        if (squadronSelectionListView == null) {
            squadronSelectionListView = new ListView<>();
            final ObjectBinding<ObservableList<Squadron>> select = Bindings.
                    select(getSystemSelectionListView().getSelectionModel().
                    selectedItemProperty(), "squadrons");
            squadronSelectionListView.itemsProperty().bind(select);
            squadronSelectionListView.setPrefHeight(50);
            squadronSelectionListView.setCellFactory(new PropertyListCellFactory<Squadron>("name", null));

            squadronSelectionListView.getSelectionModel().selectedItemProperty().
                    addListener(new ChangeListener<Object>() {

                @Override
                public void changed(ObservableValue<? extends Object> property, Object oldValue, Object newValue) {
//                    getSquadronShipListTableView().getItems().clear();
                    if (newValue != null) {
                        Squadron squadron = (Squadron) newValue;
                        getSquadronShipListTableView().setItems(squadron.
                                getShips());
                    } else {
                        final ObservableList<Ship> emptyObservableList = FXCollections.
                                emptyObservableList();
                        getSquadronShipListTableView().setItems(emptyObservableList);
                    }
                }
            });
        }


        return squadronSelectionListView;
    }

    public Label getSquadronSelectionListViewLabel() {
        if (squadronSelectionListViewLabel == null) {
            squadronSelectionListViewLabel = new Label("Squadron");
        }
        return squadronSelectionListViewLabel;
    }

    public Label getSystemSelectionListViewLabel() {
        if (systemSelectionListViewLabel == null) {
            systemSelectionListViewLabel = new Label("System");
        }
        return systemSelectionListViewLabel;
    }

    public TitledPane getSquadronShipListPane() {
        if (squadronShipListPane == null) {
            squadronShipListPane = new TitledPane("Ships in squadron", getSquadronShipListTableView());
            squadronShipListPane.setCollapsible(false);
        }
        return squadronShipListPane;
    }

    public TableView<Ship> getSquadronShipListTableView() {
        if (squadronShipListTableView == null) {
            squadronShipListTableView = new TableView<>();
//            final ObjectBinding<ObservableList<Ship>> select = Bindings.select(getSquadronSelectionListView().getSelectionModel().selectedItemProperty(), "ships");
//
//            squadronShipListTableView.itemsProperty().bind(select);

            TableColumn<Ship, String> nameColumn = new TableColumn<>("name");
            nameColumn.setCellValueFactory(new PropertyValueFactory<Ship, String>("name"));
            squadronShipListTableView.getColumns().add(nameColumn);
        }
        return squadronShipListTableView;
    }

    public SpeedSelectionComponent getSquadronSpeedSelectionPane() {
        if (squadronSpeedSelectionPane == null) {
            squadronSpeedSelectionPane = new SpeedSelectionComponent();
            squadronSpeedSelectionPane.squadronProperty().bind(getSquadronSelectionListView().
                    getSelectionModel().selectedItemProperty());
        }
        return squadronSpeedSelectionPane;
    }
}
