package com.newdawn.controllers;

import com.newdawn.model.ships.Squadron;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class ShipDestinationManager {

    public void getAvailableDestination(Squadron squadron) {
        squadron.getQueuedOrders();
    }
}
