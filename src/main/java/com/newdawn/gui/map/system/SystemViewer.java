/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.newdawn.gui.map.system;

import com.newdawn.gui.Utils;
import com.newdawn.model.ships.Squadron;
import com.newdawn.model.system.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

/**
 * This component is designed to allow the user to visualize and interract with a system using a map
 *
 * @author Pierrick Puimean-Chieze
 */
public class SystemViewer extends ScrollPane {

	private static final Log LOG = LogFactory.getLog(SystemViewer.class);
	private Group global = new Group();
	private Group components = new Group();
	// private double zoomLevel = 1;
	private GridPane grid;
	private DoubleProperty zoomLevelProperty;
    private static EventHandler<MouseEvent> LOG_EVENT_HANDLER = new EventHandler<MouseEvent>() {
    @Override
    public void handle(MouseEvent event) {
        System.out.println(event.getSource());
    }
};
	public DoubleProperty zoomLevelProperty() {
		if (zoomLevelProperty == null) {
			zoomLevelProperty = new SimpleDoubleProperty(this, "zoomLevel", 1.0);
		}
		return zoomLevelProperty;
	}

	// private EventHandler<MouseEvent> dragManager;
	// private Set<Orbit> registeredOrbit = new HashSet<Orbit>();
	public SystemViewer() {
		this.setPannable(true);
		this.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0,
                                Boolean arg1, Boolean arg2) {
                LOG.debug(arg2 ? "Focused" : "Not Focused");
            }
        });
		grid = new GridPane();
        grid.setOpacity(0.0);
        grid.setOnMouseClicked(LOG_EVENT_HANDLER);
        grid.setMouseTransparent(true);
        grid.setHgap(0);
        grid.setVgap(0);
        grid.setPadding(new Insets(0,0,0,0));
        for (int x =0;x<10;x++) {
            for (int y = 0; y < 10; y++) {
                Rectangle rectangle = new Rectangle();
                rectangle.setFill(Color.RED);
                rectangle.setStroke(Color.BLACK);
                rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Point2D.Double actualCenter = new Point2D.Double(rectangle.getX()+rectangle.getWidth()/2, rectangle.getY()+rectangle.getHeight()/2);
                        Point2D.Double spaceCenter = Utils.convertCoordinateFromScreenToSpace(actualCenter, getZoomLevel());
                        setZoomLevel(getZoomLevel() * 10);
                        Point2D.Double newCenter = Utils.convertCoordinateFromSpaceToScreen(spaceCenter, getZoomLevel());
                        SystemViewer.this.centerTo(newCenter);
                    }
                });
                grid.add(rectangle, y,x);
            }
        }

        global.getChildren().add(components);
        global.getChildren().add(grid);

		this.setContent(global);
		zoomLevelProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0,
					Number arg1, Number arg2) {
				updateChildren();
			}
		});
		this.boundsInLocalProperty().addListener(new ChangeListener<Bounds>() {
			@Override
			public void changed(ObservableValue<? extends Bounds> arg0,
					Bounds arg1, Bounds arg2) {
				updateBackground();
			}
		});
		components.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				System.out.println("X:" + arg0.getX() + ";scene :"
						+ arg0.getSceneX() + ", screen:" + arg0.getScreenX());
			}
		});
	}

    /**
     * This constructor initialize a component for a given instance of {@link com.newdawn.model.system.StellarSystem}
     *
     * @param stellarSystem the stellar system that will be visualized in this component
     */
    public SystemViewer(StellarSystem stellarSystem) {
		this();


        //For each star
		for (Star star : stellarSystem.getStars()) {
            //we create the visual component for it
            //TODO use an external factory
			final CelestialBodyComponent starComponent = CelestialBodyComponentFactory
					.buildComponentForBody(star);
            //We add the listener for this component
			starComponent.setOnMouseClicked(new CenterHandler(this,
					starComponent));

            starComponent.setOnMouseClicked(LOG_EVENT_HANDLER);
            //And we add it to the overall component group
			components.getChildren().add(starComponent);

		}

        //We do the same for each planets
		for (Planet planet : stellarSystem.getPlanets()) {
            //TODO use an external factory
			final CelestialBodyComponent planetComponent = CelestialBodyComponentFactory
					.buildComponentForBody(planet);
			planetComponent.setOnMouseClicked(new CenterHandler(this,
					planetComponent));
			components.getChildren().add(planetComponent);
			OrbitComponent planetOrbitComponent = new OrbitComponent(
					planet.getOrbit());
			components.getChildren().add(planetOrbitComponent);
			planetComponent.setLinkedOrbitComponent(planetOrbitComponent);
			// registeredOrbit.add(planet.getOrbit());

            //And for each satellite of it
            //TODO use an external factory
			for (Satellite satellite : planet.getSatellites()) {
				final CelestialBodyComponent moonComponent = CelestialBodyComponentFactory
						.buildComponentForBody(satellite);
				components.getChildren().add(moonComponent);
				OrbitComponent moonOrbitComponent = new OrbitComponent(
						satellite.getOrbit());
				moonOrbitComponent.setAlwaysVisible(false);
				components.getChildren().add(moonOrbitComponent);
				moonComponent.setLinkedOrbitComponent(moonOrbitComponent);
			}
		}

        //For each Squadron
        //TODO use an external factory
		for (Squadron taskGroup : stellarSystem.getSquadrons()) {
			components.getChildren().add(new SquadronComponent(taskGroup));
		}

        //And for each asteroid
        //TODO use an external factory
		for (Asteroid asteroid : stellarSystem.getAsteroids()) {
			final CelestialBodyComponent asteroidComponent = CelestialBodyComponentFactory
					.buildComponentForBody(asteroid);
			asteroidComponent.setOnMouseClicked(new CenterHandler(this,
					asteroidComponent));
			components.getChildren().add(asteroidComponent);
			OrbitComponent planetOrbitComponent = new OrbitComponent(
					asteroid.getOrbit());
			components.getChildren().add(planetOrbitComponent);
			asteroidComponent.setLinkedOrbitComponent(planetOrbitComponent);
			asteroidComponent.getCelestialBodyCircle().setFill(Color.YELLOWGREEN);
			// registeredOrbit.add(planet.getOrbit());


		}

		updateChildren();
		updateBackground();

	}

	private void updateBackground() {
		// double HBuffer = getViewportBounds().getWidth();
		// double VBuffer = getViewportBounds().getHeight();

        double totalWidth = components.getBoundsInLocal().getWidth();
        double totalHeight = components.getBoundsInLocal().getHeight();
        grid.setTranslateX(totalWidth/2*-1);
        grid.setTranslateY(totalHeight/2*-1);
        double gridCaseSize = Math.max(totalHeight, totalWidth) / 10;

        for (Node node : grid.getChildren()) {
            Rectangle gridCase = (Rectangle) node;
            gridCase.setHeight(gridCaseSize);
            gridCase.setWidth(gridCaseSize);
        }
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
	 * @param zoomLevel
	 *            new value of zoomLevel
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
	 * @param positionX
	 *            the x of the wanted position
	 * @param positionY
	 *            the y of the wanted position
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
			center.x = (((getTranslateX() - (getScene().getWidth() / 2.0)) / zoomLevel) * Constants.FIXED_QUOTIENT)
					/ -1;
			center.y = (((getTranslateY() - (getScene().getHeight() / 2.0)) / zoomLevel) * Constants.FIXED_QUOTIENT);
		}
		return center;
	}

    public void toggleZoomMode() {
        if (this.grid.isMouseTransparent()) {
            this.grid.setMouseTransparent(false);
            this.grid.setOpacity(0.5);
        } else {
            this.grid.setMouseTransparent(true);
            this.grid.setOpacity(0.0);
        }
    }
}
