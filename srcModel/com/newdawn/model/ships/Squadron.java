/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.newdawn.model.ships;

import com.newdawn.model.ships.orders.Order;
import com.newdawn.model.system.SpaceObject;
import com.newdawn.model.system.StellarSystem;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Teocali
 */
public class Squadron implements SpaceObject{
//    private List<Ship> ships = new ArrayList<Ship>();
    private ObjectProperty<ObservableList<Ship>> shipsProperty;

    private DoubleProperty positionXProperty;
    private DoubleProperty positionYProperty;
    
    private SpaceObject destination;
    private StellarSystem stellarSystem;
    private DoubleProperty speedProperty;
    private StringProperty nameProperty;
    private Queue<Order> queuedOrders = new LinkedList<Order>();
    private Order currentOrder;
    
    /**
     * Get the value of currentOrder
     *
     * @return the value of currentOrder
     */
    public Order getCurrentOrder() {
        return currentOrder;
    }

    /**
     * Set the value of currentOrder
     *
     * @param currentOrder new value of currentOrder
     */
    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }

    public Queue<Order> getQueuedOrders() {
        return queuedOrders;
    }

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return nameProperty().getValue();
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName(String name) {
        nameProperty().setValue(name);
    }

    /**
     * Get the value of stellarSystem
     *
     * @return the value of stellarSystem
     */
    public StellarSystem getStellarSystem() {
        return stellarSystem;
    }

    /**
     * Set the value of stellarSystem
     *
     * @param stellarSystem new value of stellarSystem
     */
    public void setStellarSystem(StellarSystem stellarSystem) {
        this.stellarSystem = stellarSystem;
    }

    /**
     * Get the value of destination
     *
     * @return the value of destination
     */
    public SpaceObject getDestination() {
        return destination;
    }

    /**
     * Set the value of destination
     *
     * @param destination new value of destination
     */
    public void setDestination(SpaceObject destination) {
        this.destination = destination;
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
    public double getPositionY() {
        return positionYProperty().getValue();
    }

    /**
     * Set the value of positionY
     *
     * @param positionY new value of positionY
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
    public double getPositionX() {
        return positionXProperty().getValue();
    }

    /**
     * Set the value of positionX
     *
     * @param positionX new value of positionX
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
            speedProperty=new SimpleDoubleProperty(this, "speed", 0);
        }
        return speedProperty;
    }

    
    public StringProperty nameProperty() {
        if (nameProperty == null) {
            nameProperty=new SimpleStringProperty(this, "name");
        }
        return nameProperty;
    }

    public ObjectProperty<ObservableList<Ship>> shipsProperty() {
        if (shipsProperty == null) {
            ObservableList<Ship> ships = FXCollections.observableArrayList();
            shipsProperty=new SimpleObjectProperty<>(this, "ships", ships);
        }
        return shipsProperty;
    }
    
    
}
