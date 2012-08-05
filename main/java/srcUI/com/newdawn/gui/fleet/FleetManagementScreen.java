package com.newdawn.gui.fleet;

import com.newdawn.controllers.GameData;
import com.newdawn.gui.PropertyListCellFactory;
import com.newdawn.gui.PropertyOrToStringTreeCellFactory;
import com.newdawn.gui.Utils;
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
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class FleetManagementScreen implements Initializable {

    @Autowired
    private GameData gameData;
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
    private ListView<Order> plottedOrdersListView;
    @FXML
    private CheckBox asteroidsFilterCheckBox;
    @FXML
    private CheckBox starsFilterCheckBox;
    @FXML
    private CheckBox planetsFilterCheckBox;
    @FXML
    private CheckBox squadronFilterCheckBox;
    private ObjectProperty<Squadron> squadronProperty;
    private ObjectProperty<StellarSystem> stellarSystemProperty;

    public ObjectProperty<Squadron> squadronProperty() {
        if (squadronProperty == null) {
            squadronProperty = new SimpleObjectProperty<>(this, "squadron");
            squadronProperty.addListener(new ChangeListener<Squadron>() {
                @Override
                public void changed(ObservableValue<? extends Squadron> arg0, Squadron arg1, Squadron arg2) {
                    LOG.debug("squadron changed from " + arg1 + " to " + arg2);
                }
            });
        }
        return squadronProperty;
    }

    private ObjectProperty<StellarSystem> stellarSystemProperty() {
        if (stellarSystemProperty == null) {
            stellarSystemProperty = new SimpleObjectProperty<>(this, "stellarSystem");
            ObjectBinding<StellarSystem> select = Bindings.
                    select(squadronProperty(), "contextualStellarSystem");
            stellarSystemProperty.bind(select);
            stellarSystemProperty.
                    addListener(new ChangeListener<StellarSystem>() {
                @Override
                public void changed(ObservableValue<? extends StellarSystem> arg0, StellarSystem arg1, StellarSystem arg2) {
                    LOG.
                            debug("stellar System changed from" + arg1 + " to " + arg2);
                }
            });
        }
        return stellarSystemProperty;
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
        initSystemListView();
        initSquadronListView();
        initSquadronInformation();
        initShipListView();
        initAvailableLocationsTreeView();
        initAvailableOrders();
        initPlottedOrdersListView();
    }

    private void initPlottedOrdersListView() {
        plottedOrdersListView.
                setCellFactory(new PropertyListCellFactory("shortDescription", null));
        ObjectBinding<ObservableList<Order>> select = Bindings.
                select(squadronProperty(), "plottedOrders");
        plottedOrdersListView.itemsProperty().bind(select);
    }

    private void initAvailableOrders() {
        availableOrdersListView.
                setCellFactory(new PropertyListCellFactory<OrderFactory>("name", null));
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

        availableLocationsTreeView.
                setCellFactory(new PropertyOrToStringTreeCellFactory("name", null, String.class));
        //TODO replace by a custom SelectBinding, one day
        final ObjectBinding<TreeItem> contextualStellarSystemBinding = new ObjectBinding<TreeItem>() {
            final ChangeListener<StellarSystem> contextualStellarSystemListener = new ChangeListener<StellarSystem>() {
                @Override
                public void changed(ObservableValue<? extends StellarSystem> arg0, StellarSystem arg1, StellarSystem arg2) {
                    invalidate();
                }
            };

            {
                squadronProperty().addListener(new ChangeListener<Squadron>() {
                    @Override
                    public void changed(ObservableValue<? extends Squadron> arg0, Squadron arg1, Squadron arg2) {
                        invalidate();
                        if (arg1 != null) {
                            arg1.contextualStellarSystemProperty().
                                    removeListener(contextualStellarSystemListener);
                        }
                        if (arg2 != null) {
                            arg2.contextualStellarSystemProperty().
                                    addListener(contextualStellarSystemListener);
                        }
                    }
                });
            }

            @Override
            protected TreeItem computeValue() {
                if (getSquadron() == null) {
                    return null;
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
                            squadronRoot.getChildren().
                                    add(new TreeItem(squadronChild));
                        }
                    }
                    return root;
                }
            }
        };
        availableLocationsTreeView.rootProperty().
                bind(contextualStellarSystemBinding);
        updateFilters(null);
    }

    //TODO Maybe used a filtered list, here, with binding
    public void updateFilters(ActionEvent event) {
        root.getChildren().clear();
        if (starsFilterCheckBox.isSelected()) {
            root.getChildren().add(starRoot);
        }
        if (planetsFilterCheckBox.isSelected()) {
            root.getChildren().add(planetRoot);
        }
        if (squadronFilterCheckBox.isSelected()) {
            root.getChildren().add(squadronRoot);
        }
    }

    private void initShipListView() {
        ObjectBinding<ObservableList<Ship>> select = Bindings.
                select(squadronProperty(), "ships");
        shipsTableView.itemsProperty().bind(select);
        shipNameColumn.
                setCellValueFactory(new PropertyValueFactory<Ship, String>("name"));
    }

    private void initSquadronInformation() {
        squadronSpeedSelectionComponent.squadronProperty().
                bind(squadronListView.
                getSelectionModel().selectedItemProperty());
    }

    private void initSquadronListView() {
        final ObjectBinding<ObservableList<Squadron>> select = Bindings.
                select(systemListView.
                getSelectionModel().selectedItemProperty(), "squadrons");
        squadronListView.itemsProperty().bind(select);

        squadronListView.
                setCellFactory(new PropertyListCellFactory<Squadron>("name", null));
        squadronProperty().bind(squadronListView.getSelectionModel().
                selectedItemProperty());

    }

    private void initSystemListView() {
        //        systemListView = new ListView<>(gameData.getStellarSystems());
        systemListView.setItems(gameData.getStellarSystems());
        systemListView.
                setCellFactory(new PropertyListCellFactory<StellarSystem>("name", null));
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
            LOG.
                    trace("Added Order " + order.getShortDescription() + " to " + getSquadron().
                    getName());

        }
    }

    public void keyPressedOnPlottedOrdersListView(KeyEvent event) {
        LOG.trace(Utils.formatKeyEvent(event));
        if (event.getCode() == KeyCode.DELETE) {
            Order selectedOrder = plottedOrdersListView.getSelectionModel().
                    getSelectedItem();
            getSquadron().getPlottedOrders().remove(selectedOrder);
        }
    }
}
