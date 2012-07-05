/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.newdawn.controllers;

import com.newdawn.model.ships.Squadron;
import com.newdawn.model.ships.orders.Order;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class OrderController {

    public final void updateOrders(Squadron taskGroup) {
        Order order = taskGroup.getCurrentOrder();

        if (order == null) {
            startNextOrder(taskGroup);
        } else if (order.isOrderAccomplished()) {
            order.finalizeOrder();
            startNextOrder(taskGroup);
        }
    }

    /**
     * This method start the next order of the order queue of a task group.
     * If this queue does not contains any order, the task group is set on stand
     * by
     */
    private void startNextOrder(Squadron taskGroup) {
        Order currentOrder = taskGroup.getQueuedOrders().poll();
        taskGroup.setCurrentOrder(currentOrder);
        if (currentOrder != null) {
            currentOrder.applyOrder();
        } else {
            setWaitingForOrder(taskGroup);
        }
    }

    private void setWaitingForOrder(Squadron taskGroup) {
        taskGroup.setDestination(null);
    }

    void startExecutionOrder(Squadron taskGroup) {
        Order currentOrder = taskGroup.getCurrentOrder();

        if (currentOrder != null) {
            currentOrder.applyOrder();
        } else {
            startNextOrder(taskGroup);
        }
    }
}
