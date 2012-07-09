/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.newdawn.model.ships.orders;

import com.newdawn.model.ships.Squadron;
import com.newdawn.model.system.SpaceObject;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class MoveToSpaceObjectOrder extends MoveOrder {

    private SimpleStringProperty shortDescriptionProperty;
    private SimpleStringProperty longDescriptionProperty;

    public MoveToSpaceObjectOrder(SpaceObject destination, Squadron taskGroup) {
        super(destination, taskGroup);
    }

    @Override
    public void applyOrder() {
        getTaskGroup().setDestination(getDestination());
    }

    @Override
    public ReadOnlyStringProperty shortDescriptionProperty() {
        if (shortDescriptionProperty == null) {
            shortDescriptionProperty = new SimpleStringProperty("Move To: " + getDestination().
                    getName());
        }
        return shortDescriptionProperty;
    }

    @Override
    public ReadOnlyStringProperty longDescriptionProperty() {
        if (longDescriptionProperty == null) {
            longDescriptionProperty = new SimpleStringProperty("Following this order, the task group " + getTaskGroup().
                    getName() + " will try to move to " + getDestination().
                    getName());
        }
        return longDescriptionProperty;
    }

    @Override
    public boolean isOrderAccomplished() {
        return getDestination().getPositionX() == getTaskGroup().getPositionX() && getDestination().
                getPositionY() == getTaskGroup().getPositionY();
    }

    @Override
    public void finalizeOrder() {

        getTaskGroup().setSpeed(0);
        getTaskGroup().setDestination(null);
    }
}
