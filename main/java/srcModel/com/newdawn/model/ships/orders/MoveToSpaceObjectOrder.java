/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.newdawn.model.ships.orders;

import com.newdawn.model.ships.Squadron;
import com.newdawn.model.system.SpaceObject;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class MoveToSpaceObjectOrder extends MoveOrder {

    public MoveToSpaceObjectOrder(SpaceObject destination, Squadron taskGroup) {
        super(destination, taskGroup);
    }

    @Override
    public void applyOrder() {
        getTaskGroup().setDestination(getDestination());
    }

    @Override
    public String getShortDescription() {
        return "Move To: " + getDestination().getName();
    }

    @Override
    public String getLongDescription() {
        return "Following this order, the task group " + getTaskGroup().getName() + " will try to move to " + getDestination().
                getName();
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
