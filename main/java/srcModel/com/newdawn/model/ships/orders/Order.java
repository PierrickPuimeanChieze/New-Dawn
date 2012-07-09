/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.newdawn.model.ships.orders;

import com.newdawn.model.ships.Squadron;
import javafx.beans.property.ReadOnlyStringProperty;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public abstract class Order {

    private final Squadron taskGroup;

    protected Order(Squadron taskGroup) {
        this.taskGroup = taskGroup;
    }

    public Squadron getTaskGroup() {
        return taskGroup;
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
}
