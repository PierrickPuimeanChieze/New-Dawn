/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.newdawn.model.ships;

import com.newdawn.model.ships.orders.factory.OrderFactory;
import com.newdawn.model.system.SpaceObject;
import com.newdawn.model.system.StellarSystem;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class Ship implements SpaceObject {

    private double maxSpeed;
    private Squadron taskGroup;
    private StringProperty nameProperty;

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
     * @param name new value of name
     */
    public void setName(String name) {
        nameProperty().setValue(name);
    }

    public StringProperty nameProperty() {
        if (nameProperty == null) {
            nameProperty = new SimpleStringProperty(this, "name");
        }
        return nameProperty;
    }

    public Squadron getTaskGroup() {
        return taskGroup;
    }

    public void setTaskGroup(Squadron taskGroup) {
        this.taskGroup = taskGroup;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    /**
     * Get the value of destination
     *
     * @return the value of destination
     */
    public SpaceObject getDestination() {
        return taskGroup.getDestination();
    }

    /**
     * Get the value of speed
     *
     * @return the value of speed
     */
    public double getSpeed() {
        return taskGroup.getSpeed();
    }

    public StellarSystem getStellarSystem() {
        return taskGroup.getStellarSystem();
    }

    @Override
    public double getPositionX() {
        return taskGroup.getPositionX();
    }

    @Override
    public double getPositionY() {
        return taskGroup.getPositionY();
    }

    @Override
    public ObservableList<OrderFactory> getOrderFactories() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
