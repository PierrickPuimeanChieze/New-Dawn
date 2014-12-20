/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newdawn.model.system;

import com.newdawn.model.mineral.MinerallyExploitableBody;
import com.newdawn.model.mineral.MinerallyExploitableBodyModel;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 
 * @author Pierrick Puimean-Chieze
 */
public class Satellite extends OrbitalBody implements MinerallyExploitableBody {

	private StringProperty visualNameProperty;
	private MinerallyExploitableBodyModel minerallyExploitableBodyModel;

	public Satellite(String name, Orbit orbit, double delta,
			Long orbitalPeriod, long diameter) {
		super(orbit, delta, diameter, orbitalPeriod);
		setName(name);
	}

	public Satellite(String name, Orbit orbit, double delta, long diameter) {
		super(orbit, delta, diameter);
		setName(name);
	}

	public Satellite(String name, Orbit orbit, long diameter) {
		super(orbit, diameter);
		setName(name);
	}

	@Override
	public ReadOnlyStringProperty visualNameProperty() {
		if (visualNameProperty == null) {
			visualNameProperty = new SimpleStringProperty(this, "visualName");
			visualNameProperty.bind(nameProperty());
		}

		return visualNameProperty;
	}

	@Override
	public MinerallyExploitableBodyModel getMinerallyExploitableBodyModel() {
		return minerallyExploitableBodyModel;
	}

	@Override
	public void setMinerallyExploitableBodyModel(
			MinerallyExploitableBodyModel minerallyExploitableBodyModel) {
		this.minerallyExploitableBodyModel = minerallyExploitableBodyModel;

	}
}
