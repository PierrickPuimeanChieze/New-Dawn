/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.newdawn.controllers;

import com.newdawn.model.ships.Squadron;
import java.awt.geom.Point2D;
import org.springframework.stereotype.Component;

/**
 *
 * @author Pierrick
 */
@Component
public class ShipMovementController {

    void moveTaskGroup(long second, Squadron taskGroup) {
        if (taskGroup.getDestination() != null) {
            double traveledDistance = taskGroup.getSpeed() * second;
            double destinationDistance = Point2D.distance(taskGroup.getPositionX(), taskGroup.getPositionY(), taskGroup.getDestination().getPositionX(), taskGroup.getDestination().getPositionY());
            if (destinationDistance < traveledDistance) {
                taskGroup.setPositionX(taskGroup.getDestination().getPositionX());
                taskGroup.setPositionY(taskGroup.getDestination().getPositionY());
                taskGroup.setDestination(null);
            } else {
                Point2D.Double newPositionForShip = calculateIntermediateCoordinate(new Point2D.Double(taskGroup.getPositionX(), taskGroup.getPositionY()), new Point2D.Double(taskGroup.getDestination().getPositionX(), taskGroup.getDestination().getPositionY()), traveledDistance);


                taskGroup.setPositionX(newPositionForShip.x);
                taskGroup.setPositionY(newPositionForShip.y);
            }
        }

    }

    public Point2D.Double calculateIntermediateCoordinate(Point2D.Double origine, Point2D.Double destination, double intermediateDistance) {

        Point2D.Double intermediateCoordinate = new Point2D.Double();

        double lc = Point2D.distance(origine.x, origine.y, destination.x, destination.y);

        intermediateCoordinate.x = (intermediateDistance * (destination.x - origine.x)) / lc + origine.x;
        intermediateCoordinate.y = (intermediateDistance * (destination.y - origine.y)) / lc + origine.y;

        return intermediateCoordinate;
    }
}
