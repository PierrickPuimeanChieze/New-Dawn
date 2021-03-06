/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.newdawn.gui.map.system;

import com.newdawn.gui.Utils;
import com.newdawn.model.system.CelestialBody;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * 
 * @author Pierrick Puimean-Chieze
 */
// TODO : ajouter les informations textuelles (nom, ...)
// TODO : Essayer de mettre en place des bindings, plutot que des listeners,
// pour la mise a jour
// TODO : documentation
public class CelestialBodyComponent extends Group {

	private final Circle celestialBodyCircle;
	private final CelestialBody body;
	private DoubleProperty minimalRadiusProperty;
	public static final String PNAME_MINIMAL_RADIUS = "minimalRadius";

	public OrbitComponent linkedOrbitComponent;
	private Text nameText;
	private ChangeListener<Number> updaterListener = new ChangeListener<Number>() {
		@Override
		public void changed(ObservableValue<? extends Number> property,
				Number oldValue, Number newValue) {
			update();
		}
	};

	public CelestialBodyComponent(CelestialBody body) {
		super();
		this.body = body;
		this.celestialBodyCircle = new Circle(5);

		nameText = new Text();
		nameText.textProperty().bind(body.nameProperty());

		body.positionXProperty().addListener(updaterListener);
		body.positionYProperty().addListener(updaterListener);
		getChildren().addAll(celestialBodyCircle);
        update();
		// .addL
	}

	public DoubleProperty minimalRadiusProperty() {
		if (minimalRadiusProperty == null) {
			minimalRadiusProperty = new SimpleDoubleProperty(this,
					"minimalRadiusProperty", 5);
		}
		return minimalRadiusProperty;
	}

	/**
	 * Get the value of minimalRadius
	 *
	 * @return the value of minimalRadius
	 */
	public double getMinimalRadius() {
		return minimalRadiusProperty().getValue();
	}

	/**
	 * Set the value of minimalRadius
	 *
	 * @param minimalRadius
	 *            new value of minimalRadius
	 */
	public void setMinimalRadius(double minimalRadius) {
		minimalRadiusProperty().setValue(minimalRadius);
	}



	public void update() {
        final double registeredPositionX = body.getPositionX()/Constants.FIXED_QUOTIENT;
        final double registeredPositionY = body.getPositionY()/Constants.FIXED_QUOTIENT*-1;

        celestialBodyCircle.setCenterX(registeredPositionX);
        celestialBodyCircle.setCenterY(registeredPositionY);
        double radiusToSet = getCelestialBodyRadius();

        celestialBodyCircle.setRadius(radiusToSet);

        // Mettre en place un binding
//        nameText.setX(celestialBodyCircle.getCenterX()
//                - celestialBodyCircle.getRadius() - 5);
//        nameText.setY(celestialBodyCircle.getCenterY()
//                - celestialBodyCircle.getRadius() - 5);

//        if (linkedOrbitComponent != null) {
//
//            // TODO supprimer le test, le remplacer par une seule ligne de code
//            if (!linkedOrbitComponent.isAlwaysVisible()
//                    && !linkedOrbitComponent.isVisible()) {
//                setVisible(false);
//            } else {
//                setVisible(true);
//            }
//        }


	}

	void setLinkedOrbitComponent(OrbitComponent linkedOrbitComponent) {
		this.linkedOrbitComponent = linkedOrbitComponent;
	}

	public Circle getCelestialBodyCircle() {
		return celestialBodyCircle;
	}

    public double getCelestialBodyRadius() {
        return (body.getDiameter() / 2.0 / Constants.FIXED_QUOTIENT);
    }

    public String getBodyName() {
        return body.getName();
    }
}
