/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.newdawn.model.ships.orders;

import com.newdawn.model.ships.Squadron;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public abstract class Order {

    private final Squadron squadron;
    private BooleanProperty finishedProperty = new SimpleBooleanProperty(this, "finished", false);
    private BooleanProperty appliedProperty = new SimpleBooleanProperty(this, "applied", false);

    public ReadOnlyBooleanProperty appliedProperty() {
        return appliedProperty;
    }

    /**
     * Get the value of applied
     *
     * @return the value of applied
     */
    public boolean isApplied() {
        return appliedProperty().getValue();
    }

    /**
     * Set the value of applied
     *
     * @param applied new value of applied
     */
    protected void setApplied(boolean applied) {
        this.appliedProperty.setValue(applied);
    }

    public ReadOnlyBooleanProperty finishedProperty() {
        return finishedProperty;
    }

    /**
     * Get the value of finished
     *
     * @return the value of finished
     */
    public boolean isFinished() {
        return finishedProperty().getValue();
    }

    /**
     * Set the value of finished
     *
     * @param finished new value of finished
     */
    protected void setFinished(boolean finished) {
        this.finishedProperty.setValue(finished);
    }

    protected Order(Squadron squadron) {
        this.squadron = squadron;
    }

    public Squadron getSquadron() {
        return squadron;
    }

    public abstract ReadOnlyStringProperty shortDescriptionProperty();

    public abstract ReadOnlyStringProperty longDescriptionProperty();

    public abstract void applyOrder();

    public String getShortDescription() {
        return shortDescriptionProperty().getValue();
    }

    public String getLongDescription() {
        return longDescriptionProperty().getValue();
    }

    public abstract boolean isOrderAccomplished();

    public abstract void finalizeOrder();

    public abstract void executeOrder(long incrementSize);
}
