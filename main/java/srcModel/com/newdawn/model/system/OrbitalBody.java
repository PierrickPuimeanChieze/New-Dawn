/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.newdawn.model.system;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * 
 * @author Pierrick Puimean-Chieze
 */
public class OrbitalBody extends CelestialBody {

	private Orbit orbit;
	private DoubleProperty deltaProperty;
	private Long orbitalPeriod;

	public OrbitalBody(Orbit orbit, long diameter) {
		this(orbit, 0.0, diameter);
	}

	public OrbitalBody(Orbit orbit, double delta, long diameter) {
		this(orbit, delta, diameter, null);
	}

	public OrbitalBody(Orbit orbit, double delta, long diameter,
			Long orbitalPeriod) {
		super(orbit.getRef().getStellarSystem(), diameter);
		this.orbit = orbit;
		this.orbitalPeriod = orbitalPeriod;
		positionXProperty().bind(new DoubleBinding() {
			{
				super.bind(deltaProperty());
			}

			@Override
			protected double computeValue() {
				final double positionX = getOrbit().getRef().getPositionX()
						+ getOrbit().getRadius() * Math.cos(getDelta());
				return positionX;
			}
		});

		positionYProperty().bind(new DoubleBinding() {
			{
				super.bind(deltaProperty());
			}

			@Override
			protected double computeValue() {
				final double positionY = getOrbit().getRef().getPositionY()
						+ getOrbit().getRadius() * Math.sin(getDelta());
				return positionY;
			}
		});
		setDelta(delta);
	}

	public DoubleProperty deltaProperty() {
		if (deltaProperty == null) {
			deltaProperty = new SimpleDoubleProperty(this, "delta", 0.0);
		}
		return deltaProperty;
	}

	public Orbit getOrbit() {
		return orbit;
	}

	public Long getOrbitalPeriod() {
		return orbitalPeriod;
	}

	public void setOrbitalPeriod(Long orbitalPeriod) {
		this.orbitalPeriod = orbitalPeriod;
	}

	public double getOrbitalSpeed() {
		if (orbitalPeriod == null) {
			return 0;
		}
		return (Math.PI * 2) / orbitalPeriod;
	}

	public double getDelta() {
		return deltaProperty().getValue();
	}

	public void setDelta(double delta) {
		deltaProperty().setValue(delta);
	}
}
