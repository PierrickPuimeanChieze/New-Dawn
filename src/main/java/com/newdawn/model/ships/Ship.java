/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.newdawn.model.ships;

import com.newdawn.model.personnel.PersonnelLocalisation;
import com.newdawn.model.ships.orders.factory.OrderFactory;
import com.newdawn.model.system.SpaceObject;
import com.newdawn.model.system.StellarSystem;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 * 
 * @author Pierrick Puimean-Chieze
 */
public class Ship implements SpaceObject, PersonnelLocalisation {

	private double maxSpeed;
	private Squadron squadron;
	private StringProperty nameProperty;
	private StringProperty localizationNameProperty;

	/**
	 * Get the value of name
	 * 
	 * @return the value of name
	 */
	@Override
	public String getName() {
		return nameProperty().getValue();
	}

	/**
	 * Set the value of name
	 * 
	 * @param name
	 *            new value of name
	 */
	public void setName(String name) {
		nameProperty().setValue(name);
	}

	public StringProperty nameProperty() {
		if (nameProperty == null) {
			nameProperty = new SimpleStringProperty(this, "name");
		}
		return nameProperty;
	}

	public Squadron getSquadron() {
		return squadron;
	}

	public void setSquadron(Squadron squadron) {
		this.squadron = squadron;
	}

	public double getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	/**
	 * Get the value of destination
	 * 
	 * @return the value of destination
	 */
	public SpaceObject getDestination() {
		return squadron.getDestination();
	}

	/**
	 * Get the value of speed
	 * 
	 * @return the value of speed
	 */
	public double getSpeed() {
		return squadron.getSpeed();
	}

	public StellarSystem getStellarSystem() {
		return squadron.getStellarSystem();
	}

	@Override
	public double getPositionX() {
		return squadron.getPositionX();
	}

	@Override
	public double getPositionY() {
		return squadron.getPositionY();
	}

	@Override
	public ObservableList<OrderFactory> getOrderFactories() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ReadOnlyStringProperty visualNameProperty() {
		if (localizationNameProperty == null) {
			localizationNameProperty = new SimpleStringProperty();
			// TODO use a configurable String for the Ship, before the name,
			// like HMS, RMS, and so on.
			localizationNameProperty.bind(Bindings.concat("Ship ",
					nameProperty()));
		}
		return localizationNameProperty;
	}
}
