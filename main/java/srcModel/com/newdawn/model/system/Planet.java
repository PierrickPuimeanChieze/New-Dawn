/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newdawn.model.system;

import com.newdawn.model.mineral.MinerallyExploitableBody;
import com.newdawn.model.mineral.MinerallyExploitableBodyModel;

import java.util.ArrayList;
import java.util.List;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 
 * @author Pierrick Puimean-Chieze
 */
public class Planet extends OrbitalBody implements MinerallyExploitableBody {
	private ReadOnlyStringProperty visualNameProperty;

	public Planet(String name, PlanetaryClass planetaryClass, Orbit orbit,
			long diameter) {
		this(name, planetaryClass, orbit, diameter, 0.0, null);
	}

	public Planet(String name, PlanetaryClass planetaryClass, Orbit orbit,
			long diameter, double delta) {
		this(name, planetaryClass, orbit, diameter, delta, null);
	}

	public Planet(String name, PlanetaryClass planetaryClass, Orbit orbit,
			long diameter, double delta, Long orbitalPeriod) {
		super(orbit, delta, diameter, orbitalPeriod);
		this.planetaryClass = planetaryClass;
		setName(name);
	}

	public List<Satellite> getSatellites() {
		return satellites;
	}

	/**
	 * List possible planetary classes
	 */
	public static enum PlanetaryClass {

		TERRESTRIAL, CHUNK, GAS_GIANT, SUPERJOVIAN
	}

	private PlanetaryClass planetaryClass;
	private List<Satellite> satellites = new ArrayList<>();

	@Override
	public ReadOnlyStringProperty visualNameProperty() {
		if (visualNameProperty == null) {
			visualNameProperty = new SimpleStringProperty(this, "visualName",
					getName());
		}

		return visualNameProperty;
	}

	public String getVisualName() {
		return visualNameProperty().getValue();
	}

	@Override
	public MinerallyExploitableBodyModel getMinerallyExploitableBodyModel() {
		throw new NotImplementedException();
	}

	@Override
	public void setMinerallyExploitableBodyModel(
			MinerallyExploitableBodyModel minerallyExploitableBodyModel) {
		throw new NotImplementedException();

	}
}
