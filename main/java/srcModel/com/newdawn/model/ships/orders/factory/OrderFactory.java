package com.newdawn.model.ships.orders.factory;

import com.newdawn.model.ships.Squadron;
import com.newdawn.model.ships.orders.Order;

/**
 * 
 * @author Pierrick Puimean-Chieze
 */
public interface OrderFactory {

	public String getName();

	public Order createOrder(Squadron squadron);
}
