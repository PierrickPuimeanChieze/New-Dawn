/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.newdawn.gui.map.system;

import com.newdawn.model.ships.Squadron;
import com.newdawn.model.system.Satellite;
import com.newdawn.model.system.Planet;
import com.newdawn.model.system.Star;
import com.newdawn.model.system.StellarSystem;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class SystemViewer extends ScrollPane {

    private Group global = new Group();
    private Group components = new Group();
//    private double zoomLevel = 1;
    private Rectangle background;
    private DoubleProperty zoomLevelProperty;

    public DoubleProperty zoomLevelProperty() {
        if (zoomLevelProperty == null) {
            zoomLevelProperty = new SimpleDoubleProperty(this, "zoomLevel", 1.0);
        }
        return zoomLevelProperty;
    }

//    private EventHandler<MouseEvent> dragManager;
//    private Set<Orbit> registeredOrbit = new HashSet<Orbit>();
    public SystemViewer() {
        this.setPannable(true);
        background = new Rectangle();
        background.setFill(Color.BISQUE);
        global.getChildren().add(background);
        global.getChildren().add(components);
        this.setContent(global);
        zoomLevelProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                updateChildren();
            }
        });
        this.boundsInLocalProperty().addListener(new ChangeListener<Bounds>() {

            @Override
            public void changed(ObservableValue<? extends Bounds> arg0, Bounds arg1, Bounds arg2) {
                updateBackground();
            }
        });
        components.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                System.out.println("X:" + arg0.getX() + ";scene :" + arg0.
                        getSceneX() + ", screen:" + arg0.getScreenX());
            }
        });
    }

    public SystemViewer(StellarSystem stellarSystem) {
        this();

        for (Star star : stellarSystem.getStars()) {
            final CelestialBodyComponent starComponent = CelestialBodyComponentFactory.
                    buildComponentForBody(star);
            starComponent.setOnMouseClicked(new CenterHandler(this, starComponent));
            components.getChildren().add(starComponent);

        }

        for (Planet planet : stellarSystem.getPlanets()) {
            final CelestialBodyComponent planetComponent = CelestialBodyComponentFactory.
                    buildComponentForBody(planet);
            planetComponent.setOnMouseClicked(new CenterHandler(this, planetComponent));
            components.getChildren().add(planetComponent);
            OrbitComponent planetOrbitComponent = new OrbitComponent(planet.
                    getOrbit());
            components.getChildren().add(planetOrbitComponent);
            planetComponent.setLinkedOrbitComponent(planetOrbitComponent);
//            registeredOrbit.add(planet.getOrbit());

            for (Satellite satellite : planet.getSatellites()) {
                final CelestialBodyComponent moonComponent = CelestialBodyComponentFactory.
                        buildComponentForBody(satellite);
                components.getChildren().add(moonComponent);
                OrbitComponent moonOrbitComponent = new OrbitComponent(satellite.
                        getOrbit());
                moonOrbitComponent.setAlwaysVisible(false);
                components.getChildren().add(moonOrbitComponent);
                moonComponent.setLinkedOrbitComponent(moonOrbitComponent);
            }
        }

        for (Squadron taskGroup : stellarSystem.getSquadrons()) {
            components.getChildren().add(new SquadronComponent(taskGroup));
        }


        updateChildren();
        updateBackground();

    }

    private void updateBackground() {
//        double HBuffer = getViewportBounds().getWidth();
//        double VBuffer = getViewportBounds().getHeight();
        double HBuffer = 0;
        double VBuffer = 0;
        background.setWidth(components.getBoundsInLocal().getWidth() + HBuffer * 2);
        background.setHeight(components.getBoundsInLocal().getHeight() + VBuffer * 2);
        background.setX(components.getBoundsInLocal().getMinX() - HBuffer);
        background.setY(components.getBoundsInLocal().getMinY() - VBuffer);
    }

    /**
     * Get the value of zoomLevel
     *
     * @return the value of zoomLevel
     */
    public double getZoomLevel() {
        return zoomLevelProperty().getValue();
    }

    /**
     * Set the value of zoomLevel
     *
     * @param zoomLevel new value of zoomLevel
     */
    public void setZoomLevel(double zoomLevel) {
        zoomLevelProperty().setValue(zoomLevel);
    }

    public void updateChildren() {
        double zoomLevel = getZoomLevel();
        for (Node children : components.getChildren()) {
            if (children instanceof CelestialBodyComponent) {
                CelestialBodyComponent celestialBodyComponent = (CelestialBodyComponent) children;
                celestialBodyComponent.setZoomLevel(zoomLevel);
            }
            if (children instanceof OrbitComponent) {
                OrbitComponent orbitComponent = (OrbitComponent) children;
                orbitComponent.setZoomLevel(zoomLevel);
            }
            if (children instanceof SquadronComponent) {
                SquadronComponent shipComponent = (SquadronComponent) children;
                shipComponent.setZoomLevel(zoomLevel);
            }
        }

        updateBackground();
        double vPart = getVvalue() / (getVmax() - getVmin());
        double hPart = getHvalue() / (getHmax() - getHmin());
        setVmax(components.getBoundsInLocal().getHeight());
        setHmax(components.getBoundsInLocal().getWidth());
        setVvalue(vPart * (getVmax() - getVmin()));
        setHvalue(hPart * (getHmax() - getHmin()));

    }

    private void centerTo(Double center) {
        this.centerTo(center.x, center.y);
    }

    /**
     * This method center the group on a given position, in screen coordinates
     *
     * @param positionX the x of the wanted position
     * @param positionY the y of the wanted position
     */
    public final void centerTo(double positionX, double positionY) {
        final double zoomLevel = getZoomLevel();
        double uiWidth = components.getBoundsInLocal().getWidth();
        double uiHeight = components.getBoundsInLocal().getHeight();

        double positionXToSet = positionX + uiWidth / 2;
        double positionYToSet = positionY + uiHeight / 2;

        setHvalue(positionXToSet);
        setVvalue(positionYToSet);
    }

    /**
     * This method get the center of the group, in astronomical unit
     *
     * @return
     */
    public final Point2D.Double getCenter() {
        double zoomLevel = getZoomLevel();

        Point2D.Double center = null;
        if (getScene() != null) {
            center = new Point2D.Double();
            center.x = (((getTranslateX() - (getScene().getWidth() / 2.0)) / zoomLevel) * Constants.FIXED_QUOTIENT) / -1;
            center.y = (((getTranslateY() - (getScene().getHeight() / 2.0)) / zoomLevel) * Constants.FIXED_QUOTIENT);
        }
        return center;
    }
}
