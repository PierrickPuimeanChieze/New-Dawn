/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.newdawn.model.ships.orders;

import com.newdawn.model.ships.Squadron;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public abstract class Order {

    private final Squadron taskGroup;

    protected Order(Squadron taskGroup) {
        this.taskGroup = taskGroup;
    }

    public Squadron getTaskGroup() {
        return taskGroup;
    }

    public abstract void applyOrder();

    public abstract String getShortDescription();

    public abstract String getLongDescription();
    
    public abstract boolean isOrderAccomplished();

    public abstract void finalizeOrder();
}
