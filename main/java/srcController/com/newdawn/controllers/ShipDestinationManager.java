package com.newdawn.controllers;

import com.newdawn.model.ships.Squadron;
import com.newdawn.model.ships.orders.MoveOrder;
import com.newdawn.model.ships.orders.Order;
import com.newdawn.model.system.SpaceObject;
import com.newdawn.model.system.StellarSystem;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class ShipDestinationManager {

    public StellarSystem calculateContextualStellarSystem(Squadron squadron) {
        Order lastOrder = squadron.getQueuedOrders().getLast();
        return calculateContextualStellarSystem(squadron, lastOrder);
    }

    private StellarSystem calculateContextualStellarSystem(Squadron squadron, Order order) {
        StellarSystem contextualSystem = squadron.getStellarSystem();
        if (order instanceof MoveOrder) {
            MoveOrder moveOrder = (MoveOrder) order;
            contextualSystem = moveOrder.getDestination().getStellarSystem();
        }
        //TODO Add the treatment of other type of order
        return contextualSystem;
    }
}
