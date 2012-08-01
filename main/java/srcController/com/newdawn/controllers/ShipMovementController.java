/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.newdawn.controllers;

import com.newdawn.controllers.utils.ShipUtils;
import com.newdawn.model.ships.Squadron;

import javafx.geometry.Point2D;
import org.springframework.stereotype.Component;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
@Component
public class ShipMovementController {

    void moveTaskGroup(long second, Squadron taskGroup) {
        if (taskGroup.getDestination() != null) {
            double traveledDistance = taskGroup.getSpeed() * second;
            Point2D taskGroupPosition = new Point2D(taskGroup.getPositionX(), taskGroup.
                    getPositionY());
            Point2D destinationPosition = new Point2D(taskGroup.getDestination().
                    getPositionX(), taskGroup.getDestination().getPositionY());
            double destinationDistance = taskGroupPosition.
                    distance(destinationPosition);
            if (destinationDistance < traveledDistance) {
                taskGroup.
                        setPositionX(taskGroup.getDestination().getPositionX());
                taskGroup.
                        setPositionY(taskGroup.getDestination().getPositionY());
            } else {
                Point2D newPositionForShip = ShipUtils.
                        calculateIntermediateCoordinate(taskGroupPosition, destinationPosition, traveledDistance);


                taskGroup.setPositionX(newPositionForShip.getX());
                taskGroup.setPositionY(newPositionForShip.getY());
            }
        }

    }
}
