package com.newdawn.controllers.utils;

import com.newdawn.model.ships.Squadron;
import com.newdawn.model.ships.orders.MoveOrder;
import com.newdawn.model.ships.orders.Order;
import com.newdawn.model.system.StellarSystem;

import javafx.collections.ObservableList;
import javafx.geometry.Point2D;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class ShipUtils {

    public static StellarSystem calculateContextualStellarSystem(Squadron squadron) {
        ObservableList<Order> orders = squadron.getPlottedOrders();

        Order lastOrder = orders.isEmpty() ? null : orders.get(orders.size() - 1);
        return calculateContextualStellarSystem(squadron, lastOrder);
    }

    private static StellarSystem calculateContextualStellarSystem(Squadron squadron, Order order) {
        StellarSystem contextualSystem = squadron.getStellarSystem();
        if (order == null) {
            return contextualSystem;
        }
        if (order instanceof MoveOrder) {
            MoveOrder moveOrder = (MoveOrder) order;
            contextualSystem = moveOrder.getDestination().getStellarSystem();
        }
        //TODO Add the treatment of other type of order
        return contextualSystem;
    }

    public static Point2D calculateIntermediateCoordinate(Point2D origine, Point2D destination, double intermediateDistance) {

//        Point2D intermediateCoordinate =
//                double lc = Point2D.distance(origine.x, origine.y, destination.x, destination.y);
        double lc = origine.distance(destination);
//        intermediateCoordinate.x =;
//        intermediateCoordinate.y =
        return new Point2D((intermediateDistance * (destination.getX() - origine.
                getY())) / lc + origine.getX(), (intermediateDistance * (destination.
                getY() - origine.getY())) / lc + origine.getY());
//        return intermediateCoordinate;
    }
}
