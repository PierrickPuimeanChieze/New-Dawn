package com.newdawn.controllers.utils;

import com.newdawn.model.ships.Squadron;
import com.newdawn.model.ships.orders.MoveOrder;
import com.newdawn.model.ships.orders.Order;
import com.newdawn.model.system.SpaceObject;
import com.newdawn.model.system.StellarSystem;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class ShipUtils {

    public static StellarSystem calculateContextualStellarSystem(Squadron squadron) {
        LinkedList<Order> orders = squadron.getQueuedOrders();
        
        Order lastOrder = orders.isEmpty() ? null : orders.getLast();
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
    
    private static List<Order> getAvailableOrders(Squadron squadron, SpaceObject object) {
        List<Order> toReturn = new ArrayList<>();
        return toReturn;
    }
}
