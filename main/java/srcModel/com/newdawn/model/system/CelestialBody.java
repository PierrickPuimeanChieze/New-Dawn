/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newdawn.model.system;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * Abstract class for any celestial body.
 *
 * @author Pierrick Puimean-Chieze
 */
public abstract class CelestialBody implements SpaceObject {

    protected PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private long diameter;
    private StringProperty nameProperty;
    private StellarSystem stellarSystem;
    private DoubleProperty positionXProperty;
    private DoubleProperty positionYProperty;

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

    public CelestialBody(StellarSystem stellarSystem, long diameter) {
        this.stellarSystem = stellarSystem;
        this.diameter = diameter;
    }

    public CelestialBody(long diameter) {
        this.diameter = diameter;
    }

    public synchronized void removePropertyChangeListener(String string, PropertyChangeListener pl) {
        propertyChangeSupport.removePropertyChangeListener(string, pl);
    }

    public synchronized void removePropertyChangeListener(PropertyChangeListener pl) {
        propertyChangeSupport.removePropertyChangeListener(pl);
    }

    public synchronized boolean hasListeners(String string) {
        return propertyChangeSupport.hasListeners(string);
    }

    public synchronized PropertyChangeListener[] getPropertyChangeListeners(String string) {
        return propertyChangeSupport.getPropertyChangeListeners(string);
    }

    public synchronized PropertyChangeListener[] getPropertyChangeListeners() {
        return propertyChangeSupport.getPropertyChangeListeners();
    }

    public synchronized void addPropertyChangeListener(String string, PropertyChangeListener pl) {
        propertyChangeSupport.addPropertyChangeListener(string, pl);
    }

    public synchronized void addPropertyChangeListener(PropertyChangeListener pl) {
        propertyChangeSupport.addPropertyChangeListener(pl);
    }

    protected PropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }

    public long getDiameter() {
        return diameter;
    }

    public StellarSystem getStellarSystem() {
        return stellarSystem;
    }

    public void setStellarSystem(StellarSystem stellarSystem) {
        this.stellarSystem = stellarSystem;
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
}
