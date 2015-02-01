/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.newdawn.gui.map.system;

import com.newdawn.model.system.Orbit;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * 
 * @author Pierrick Puimean-Chieze
 */
public class OrbitComponent extends Circle {

	private Orbit toShow;
	private boolean alwaysVisible = true;

	private final ChangeListener<Number> updaterListener = new ChangeListener<Number>() {
		@Override
		public void changed(ObservableValue<? extends Number> arg0,
				Number arg1, Number arg2) {
			update();
		}
	};



	public OrbitComponent(Orbit toShow) {
		this.toShow = toShow;
		setFill(null);
		setStroke(Color.BLACK);
		setStrokeWidth(0.5);

		toShow.getRef().positionXProperty().addListener(updaterListener);
		toShow.getRef().positionYProperty().addListener(updaterListener);
        update();
	}

	// TODO remplacer cette methode par des binding
	public void update() {
		final double registeredCenterX = toShow.getRef().getPositionX();
		final double registeredCenterY = toShow.getRef().getPositionY();
		final double registeredRadius = toShow.getRadius();
		double centerX = (registeredCenterX / Constants.FIXED_QUOTIENT)
				;
		double centerY = (registeredCenterY / Constants.FIXED_QUOTIENT)
				* -1;
		double radius = (registeredRadius / Constants.FIXED_QUOTIENT)
				;
		setCenterX(centerX);
		setCenterY(centerY);
		setRadius(radius);
//		setVisible(radius >= Constants.MINIMUM_RADIUS || alwaysVisible);
	}

	void setAlwaysVisible(boolean b) {
		this.alwaysVisible = b;
	}

	public boolean isAlwaysVisible() {
		return alwaysVisible;
	}
}
