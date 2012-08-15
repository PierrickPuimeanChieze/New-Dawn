package com.newdawn.model.ships.orders;

import com.newdawn.model.ships.Squadron;
import com.newdawn.model.system.SpaceObject;

/**
 * 
 * @author Pierrick Puimean-Chieze
 */
public abstract class MoveOrder extends Order {

	private SpaceObject destination;

	public SpaceObject getDestination() {
		return destination;
	}

	public MoveOrder(SpaceObject destination, Squadron taskGroup) {
		super(taskGroup);
		this.destination = destination;
	}
}
