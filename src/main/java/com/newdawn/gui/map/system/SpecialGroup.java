package com.newdawn.gui.map.system;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Circle;

/**
 * Created by Pierrick on 01/02/2015.
 */
public class SpecialGroup extends Group {

    public SpecialGroup() {
        super();
        this.scaleXProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double newScale  = newValue.doubleValue();
                for (Node node : getChildren()) {
                    if (node instanceof CelestialBodyComponent) {
                        CelestialBodyComponent celestialBodyComponent = (CelestialBodyComponent) node;
                        double minimalRadius = celestialBodyComponent.getMinimalRadius();
                        double celestialBodyRadius = celestialBodyComponent.getCelestialBodyRadius();
                        double visibleRadius = celestialBodyRadius*newScale;
                        Circle circle = celestialBodyComponent.getCelestialBodyCircle();
                        if (visibleRadius<minimalRadius) {
                            circle.setRadius(minimalRadius/newScale);
                        } else {
                            circle.setRadius(celestialBodyRadius);
                        }
                    }

                    if (node instanceof OrbitComponent) {
                        OrbitComponent orbitComponent = (OrbitComponent) node;
                        double minimalOrbitStroke = 0.5;
                        orbitComponent.setStrokeWidth(0.5/newScale);
                    }
                }
            }
        });
    }
}
