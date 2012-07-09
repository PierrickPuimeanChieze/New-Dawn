/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.newdawn.model.ships.orders;

import com.newdawn.controllers.utils.ShipUtils;
import com.newdawn.model.ships.Squadron;
import com.newdawn.model.system.SpaceObject;
import java.awt.geom.Point2D;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class MoveToSpaceObjectOrder extends MoveOrder {

    private SimpleStringProperty shortDescriptionProperty;
    private SimpleStringProperty longDescriptionProperty;
    private static Log LOG = LogFactory.getLog(MoveToSpaceObjectOrder.class);

    public MoveToSpaceObjectOrder(SpaceObject destination, Squadron squadron) {
        super(destination, squadron);
    }

    @Override
    public void applyOrder() {
        LOG.trace("order [" + getShortDescription() + "] applied to squadron [" + getTaskGroup().
                getName() + "]");
        getTaskGroup().setDestination(getDestination());
        setApplied(true);
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
        LOG.trace("[" + getShortDescription() + "] order for squadron [" + getTaskGroup().
                getName() + "] finalized");
        getTaskGroup().setSpeed(0);
        getTaskGroup().setDestination(null);
    }

    @Override
    public void executeOrder(long incrementSize) {
        if (getTaskGroup().getDestination() != null) {
            //We calculate the maximum traveled distance during the increment
            double traveledDistance = getTaskGroup().getSpeed() * incrementSize;
            //We calculate the distance to the destination 
            double destinationDistance = Point2D.distance(getTaskGroup().
                    getPositionX(), getTaskGroup().
                    getPositionY(), getTaskGroup().getDestination().getPositionX(), getTaskGroup().
                    getDestination().getPositionY());
            //If the maximum traveled distance is enough to reach the destination
            if (destinationDistance < traveledDistance) {
                //We move the squadron to the destination
                getTaskGroup().setPositionX(getTaskGroup().getDestination().
                        getPositionX());
                getTaskGroup().setPositionY(getTaskGroup().getDestination().
                        getPositionY());
                //We mark the order as finished
                setFinished(true);
                getTaskGroup().setDestination(null);

            } else {
                Point2D.Double newPositionForShip = ShipUtils.
                        calculateIntermediateCoordinate(new Point2D.Double(getTaskGroup().
                        getPositionX(), getTaskGroup().getPositionY()), new Point2D.Double(getTaskGroup().
                        getDestination().getPositionX(), getTaskGroup().
                        getDestination().getPositionY()), traveledDistance);


                getTaskGroup().setPositionX(newPositionForShip.x);
                getTaskGroup().setPositionY(newPositionForShip.y);
            }
        }
    }
}
