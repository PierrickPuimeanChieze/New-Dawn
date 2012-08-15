/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.newdawn.model.ships;

import com.newdawn.controllers.utils.ShipUtils;
import com.newdawn.model.ships.orders.Order;
import com.newdawn.model.ships.orders.factory.MoveToSpaceObjectOrderFactory;
import com.newdawn.model.ships.orders.factory.OrderFactory;
import com.newdawn.model.system.SpaceObject;
import com.newdawn.model.system.StellarSystem;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Pierrick Puimean-Chieze
 */
public class Squadron implements SpaceObject {
	// private List<Ship> ships = new ArrayList<Ship>();

	private Log LOG = LogFactory.getLog(Squadron.class);
	// TODO see to remove the property
	private ObjectProperty<ObservableList<Ship>> shipsProperty;
	private DoubleProperty positionXProperty;
	private DoubleProperty positionYProperty;
	private ObjectProperty<SpaceObject> destinationProperty;
	private ObjectProperty<StellarSystem> stellarSystemProperty;
	private ObjectProperty<StellarSystem> contextualStellarSystemProperty;
	private DoubleProperty speedProperty;
	private StringProperty nameProperty;
	private ReadOnlyListProperty<Order> plottedOrdersProperty;
	private ObservableList<OrderFactory> orderFactories = FXCollections
			.observableArrayList();

	public Squadron() {
		orderFactories.add(new MoveToSpaceObjectOrderFactory(this));
	}

	public ReadOnlyObjectProperty<StellarSystem> contextualStellarSystemProperty() {
		if (contextualStellarSystemProperty == null) {
			contextualStellarSystemProperty = new SimpleObjectProperty<>(this,
					"contextualStellarSystem");
			ObjectBinding<StellarSystem> binding = new ObjectBinding<StellarSystem>() {
				{
					bind(getPlottedOrders());
				}

				@Override
				protected StellarSystem computeValue() {
					return ShipUtils
							.calculateContextualStellarSystem(Squadron.this);
				}
			};
			contextualStellarSystemProperty.bind(binding);
		}
		return contextualStellarSystemProperty;
	}

	public StellarSystem getContextualStellarSystem() {
		return contextualStellarSystemProperty().getValue();
	}

	public ObjectProperty<StellarSystem> stellarSystemProperty() {
		if (stellarSystemProperty == null) {
			stellarSystemProperty = new SimpleObjectProperty<>(this,
					"stellarSystem");
		}
		return stellarSystemProperty;
	}

	public ReadOnlyListProperty<Order> plottedOrdersProperty() {
		if (plottedOrdersProperty == null) {
			ObservableList<Order> observableArrayList = FXCollections
					.observableArrayList();
			plottedOrdersProperty = new SimpleListProperty<>(this,
					"plottedOrders", observableArrayList);
			plottedOrdersProperty.addListener(new ListChangeListener<Order>() {
				@Override
				public void onChanged(Change<? extends Order> change) {
					if (getCurrentOrder() != null
							&& !getCurrentOrder().isApplied()) {
						getCurrentOrder().applyOrder();
					} else if (getCurrentOrder() == null) {
						LOG.trace("no more order for squadron " + getName());
						setDestination(null);
					}
				}
			});
		}
		return plottedOrdersProperty;
	}

	/**
	 * Get the value of currentOrder
	 * 
	 * @return the value of currentOrder
	 */
	public Order getCurrentOrder() {
		return getPlottedOrders().size() > 0 ? getPlottedOrders().get(0) : null;

	}

	// /**
	// * Set the value of currentOrder
	// *
	// * @param currentOrder new value of currentOrder
	// */
	// public void setCurrentOrder(Order currentOrder) {
	// this.currentOrder = currentOrder;
	// }
	public ObservableList<Order> getPlottedOrders() {
		return plottedOrdersProperty().getValue();
	}

	/**
	 * Get the value of name
	 * 
	 * @return the value of name
	 */
	@Override
	public String getName() {
		return nameProperty().getValue();
	}

	/**
	 * Set the value of name
	 * 
	 * @param name
	 *            new value of name
	 */
	public void setName(String name) {
		nameProperty().setValue(name);
	}

	/**
	 * Get the value of stellarSystem
	 * 
	 * @return the value of stellarSystem
	 */
	@Override
	public StellarSystem getStellarSystem() {
		return stellarSystemProperty().getValue();
	}

	/**
	 * Set the value of stellarSystem
	 * 
	 * @param stellarSystem
	 *            new value of stellarSystem
	 */
	public void setStellarSystem(StellarSystem stellarSystem) {
		this.stellarSystemProperty().setValue(stellarSystem);
	}

	public ObjectProperty<SpaceObject> destinationProperty() {
		if (destinationProperty == null) {
			destinationProperty = new SimpleObjectProperty<>(this,
					"destination");
		}
		return destinationProperty;
	}

	/**
	 * Get the value of destination
	 * 
	 * @return the value of destination
	 */
	public SpaceObject getDestination() {
		return destinationProperty().getValue();
	}

	/**
	 * Set the value of destination
	 * 
	 * @param destination
	 *            new value of destination
	 */
	public void setDestination(SpaceObject destination) {
		destinationProperty().setValue(destination);
	}

	public DoubleProperty positionYProperty() {
		if (positionYProperty == null) {
			positionYProperty = new SimpleDoubleProperty(this, "positionY", 0);
		}
		return positionYProperty;
	}

	/**
	 * Get the value of positionY
	 * 
	 * @return the value of positionY
	 */
	@Override
	public double getPositionY() {
		return positionYProperty().getValue();
	}

	/**
	 * Set the value of positionY
	 * 
	 * @param positionY
	 *            new value of positionY
	 */
	public void setPositionY(double positionY) {
		positionYProperty().setValue(positionY);
	}

	public DoubleProperty positionXProperty() {
		if (positionXProperty == null) {
			positionXProperty = new SimpleDoubleProperty(this, "positionX", 0);
		}
		return positionXProperty;
	}

	/**
	 * Get the value of positionX
	 * 
	 * @return the value of positionX
	 */
	@Override
	public double getPositionX() {
		return positionXProperty().getValue();
	}

	/**
	 * Set the value of positionX
	 * 
	 * @param positionX
	 *            new value of positionX
	 */
	public void setPositionX(double positionX) {
		positionXProperty().setValue(positionX);
	}

	/**
	 * Get the value of ships
	 * 
	 * @return the value of ships
	 */
	public ObservableList<Ship> getShips() {
		return shipsProperty().getValue();
	}

	public double getMaxSpeed() {
		double maxSpeed = Double.MAX_VALUE;
		for (Ship ship : getShips()) {
			maxSpeed = Math.min(maxSpeed, ship.getMaxSpeed());

		}
		return maxSpeed;
	}

	public void setSpeed(double speed) {
		speedProperty().setValue(speed);
	}

	public double getSpeed() {
		return speedProperty().getValue();
	}

	public DoubleProperty speedProperty() {
		if (speedProperty == null) {
			speedProperty = new SimpleDoubleProperty(this, "speed", 0);
		}
		return speedProperty;
	}

	public StringProperty nameProperty() {
		if (nameProperty == null) {
			nameProperty = new SimpleStringProperty(this, "name");
		}
		return nameProperty;
	}

	public ObjectProperty<ObservableList<Ship>> shipsProperty() {
		if (shipsProperty == null) {
			ObservableList<Ship> ships = FXCollections.observableArrayList();
			shipsProperty = new SimpleObjectProperty<>(this, "ships", ships);
		}
		return shipsProperty;
	}

	@Override
	public ObservableList<OrderFactory> getOrderFactories() {
		return FXCollections.unmodifiableObservableList(orderFactories);
	}

	@Override
	public String toString() {
		return super.toString() + "[name=" + getName() + "]";
	}
}
