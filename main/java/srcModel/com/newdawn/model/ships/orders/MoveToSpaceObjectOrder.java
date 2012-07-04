/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.newdawn.model.ships.orders;

import com.newdawn.model.ships.Squadron;
import com.newdawn.model.system.SpaceObject;

/**
 *
 * @author Teocali
 */
public class MoveToSpaceObjectOrder extends Order {

    private SpaceObject destination;

    public MoveToSpaceObjectOrder(SpaceObject destination, Squadron taskGroup) {
        super(taskGroup);
        this.destination = destination;
    }

    @Override
    public void applyOrder() {
        getTaskGroup().setDestination(destination);
    }

    @Override
    public String getShortDescription() {
        return "Move To: " + destination.getName();
    }

    @Override
    public String getLongDescription() {
        return "Following this order, the task group " + getTaskGroup().getName() + " will try to move to " + destination.getName();
    }

    @Override
    public boolean isOrderAccomplished() {
        return destination.getPositionX() == getTaskGroup().getPositionX() && destination.getPositionY() == getTaskGroup().getPositionY();
    }

    @Override
    public void finalizeOrder() {
        
        getTaskGroup().setSpeed(0);
        getTaskGroup().setDestination(null);
    }
}
