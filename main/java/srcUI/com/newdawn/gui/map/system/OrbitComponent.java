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
    private DoubleProperty zoomLevelProperty;
    private final ChangeListener<Number> updaterListener = new ChangeListener<Number>() {

        @Override
        public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
            update();
        }
    };

    public DoubleProperty zoomLevelProperty() {
        if (zoomLevelProperty == null) {
            zoomLevelProperty = new SimpleDoubleProperty(this, "zoomLevel");
        }
        return zoomLevelProperty;
    }

    public double getZoomLevel() {
        return zoomLevelProperty().getValue();
    }

    public void setZoomLevel(double zoomLevel) {
        zoomLevelProperty().setValue(zoomLevel);
    }

    public OrbitComponent(Orbit toShow) {
        this.toShow = toShow;
        setFill(null);
        setStroke(Color.BLACK);
        setStrokeWidth(0.5);

        zoomLevelProperty().addListener(updaterListener);
        toShow.getRef().positionXProperty().addListener(updaterListener);
        toShow.getRef().positionYProperty().addListener(updaterListener);
        setZoomLevel(1);
    }

    //TODO remplacer cette methode par des binding
    public void update() {
        double zoomLevel = getZoomLevel();
        final double registeredCenterX = toShow.getRef().getPositionX();
        final double registeredCenterY = toShow.getRef().getPositionY();
        final double registeredRadius = toShow.getRadius();
        double centerX = (registeredCenterX / Constants.FIXED_QUOTIENT) * zoomLevel;
        double centerY = (registeredCenterY / Constants.FIXED_QUOTIENT) * zoomLevel * -1;
        double radius = (registeredRadius / Constants.FIXED_QUOTIENT) * zoomLevel;
        setCenterX(centerX);
        setCenterY(centerY);
        setRadius(radius);
        setVisible(radius >= Constants.MINIMUM_RADIUS || alwaysVisible);
    }

    void setAlwaysVisible(boolean b) {
        this.alwaysVisible = b;
    }

    public boolean isAlwaysVisible() {
        return alwaysVisible;
    }
}
