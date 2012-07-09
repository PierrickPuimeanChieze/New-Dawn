package com.newdawn.gui.fleet;

import com.newdawn.controllers.GameData;
import com.newdawn.gui.PropertyListCellFactory;
import com.newdawn.gui.PropertyOrToStringTreeCellFactory;
import com.newdawn.model.ships.Ship;
import com.newdawn.model.ships.Squadron;
import com.newdawn.model.ships.orders.Order;
import com.newdawn.model.ships.orders.factory.OrderFactory;
import com.newdawn.model.system.Planet;
import com.newdawn.model.system.SpaceObject;
import com.newdawn.model.system.Star;
import com.newdawn.model.system.StellarSystem;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import viewerfx.ViewerFX;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class FleetManagementScreen implements Initializable {

    private static Log LOG = LogFactory.getLog(FleetManagementScreen.class);
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
    private ObjectProperty<Squadron> squadronProperty;

    public ObjectProperty<Squadron> squadronProperty() {
        if (squadronProperty == null) {
            squadronProperty = new SimpleObjectProperty<>(this, "squadron");
        }
        return squadronProperty;
    }

    /**
     * Get the value of squadron
     *
     * @return the value of squadron
     */
    public Squadron getSquadron() {
        return squadronProperty().getValue();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        final GameData gameData = ViewerFX.getCurrentApplication().
                getSprintContainer().getBean(GameData.class);
        initSystemListView(gameData);
        initSquadronListView();
        initSquadronInformation();
        initShipListView();
        initAvailableLocationsTreeView();
        initAvailableOrders();
        initPlottedOrdersListView();
    }

    private void initPlottedOrdersListView() {
        plottedOrdersListView.setCellFactory(new PropertyListCellFactory("shortDescription", null));
        plottedOrdersListView.itemsProperty().bind(Bindings.select(squadronProperty(), "plottedOrders"));
    }

    private void initAvailableOrders() {
        availableOrdersListView.setCellFactory(new PropertyListCellFactory<OrderFactory>("name", null));
        ObjectBinding<ObservableList<OrderFactory>> binding = new ObjectBinding<ObservableList<OrderFactory>>() {

            {
                bind(availableLocationsTreeView.getSelectionModel().
                        selectedItemProperty());
            }

            @Override
            protected ObservableList<OrderFactory> computeValue() {
                TreeItem selectedItem = (TreeItem) availableLocationsTreeView.
                        getSelectionModel().getSelectedItem();
                if (selectedItem != null && selectedItem.getValue() instanceof SpaceObject) {
                    SpaceObject destination = (SpaceObject) selectedItem.
                            getValue();
                    return destination.getOrderFactories();
                } else {
                    return null;
                }
            }
        };
        availableOrdersListView.itemsProperty().bind(binding);
    }

    private void initAvailableLocationsTreeView() {
        availableLocationsTreeView.setCellFactory(new PropertyOrToStringTreeCellFactory("name", String.class, null));
        ObjectBinding<Object> select = Bindings.select(squadronProperty(), "contextualStellarSystem");
        final ObjectBinding<Object> contextualStellarSystemBinding = Bindings.
                select(squadronProperty(), "contextualStellarSystem");

        //TODO replace by a custom selectBinding
        contextualStellarSystemBinding.addListener(new InvalidationListener() {

            @Override
            public void invalidated(Observable arg0) {
                LOG.trace("contextualStellarSystemBinding invalidated");
                if (getSquadron() == null) {
                    availableLocationsTreeView.setRoot(null);
                } else {
                    StellarSystem stellarSystem = getSquadron().
                            getContextualStellarSystem();
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
                        if (squadronChild != getSquadron()) {
                            squadronRoot.getChildren().add(new TreeItem(squadronChild));
                        }
                    }
                    availableLocationsTreeView.setRoot(root);

                }
                contextualStellarSystemBinding.getValue();
            }
        });

        root.getChildren().add(starRoot);
        root.getChildren().add(planetRoot);
        root.getChildren().add(squadronRoot);
    }

    private void initShipListView() {
        ObjectBinding<ObservableList<Ship>> select = Bindings.select(squadronProperty(), "ships");
        shipsTableView.itemsProperty().bind(select);
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
        squadronProperty().bind(squadronListView.getSelectionModel().
                selectedItemProperty());

    }

    private void initSystemListView(final GameData gameData) {
        //        systemListView = new ListView<>(gameData.getStellarSystems());
        systemListView.setItems(gameData.getStellarSystems());
        systemListView.setCellFactory(new PropertyListCellFactory<StellarSystem>("name", null));
        systemListView.setPrefHeight(50);
    }

    @FXML
    public void onAvailableOrderClick(MouseEvent event) {
        if (event.getClickCount() >= 2 && getSquadron() != null) {

            OrderFactory orderFactory = availableOrdersListView.
                    getSelectionModel().
                    getSelectedItem();
            Order order = orderFactory.createOrder(getSquadron());
            getSquadron().getPlottedOrders().add(order);
            LOG.trace("Added Order "+order.getShortDescription()+" to "+getSquadron().getName());

        }
    }
}
