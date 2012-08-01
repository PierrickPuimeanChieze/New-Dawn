/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.newdawn.gui.map.system;

import com.newdawn.controllers.utils.ShipUtils;
import com.newdawn.model.ships.Squadron;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

/**
 *
 * @author Pierrick Puimean-Chieze
 *
 */
// TODO : ajouter les informations textuelles (no, vitesse) 
// TODO : Essayer de mettre en place des bindings, plutot que des listeners, pour la mise a jour
// TODO : documentation
public class SquadronComponent extends Group {

    private DoubleProperty zoomLevelProperty;
    final private Squadron squadronToShow;
    private Line verticalCrossBar = new Line();
    private Line horizontalCrossBar = new Line();
    private Line directionalLine = new Line();
    //Will be replaced by an arrow head when those will be available
    private Circle directionalLineEnd = new Circle();
    private Text speedText = new Text();
    private static final double DIRECTIONAL_LINE_LENGTH = 15;
    private ChangeListener updaterListener = new ChangeListener() {
        @Override
        public void changed(ObservableValue property, Object oldValue, Object newValue) {
            update();

        }
    };

    public SquadronComponent(Squadron squadronToShow) {
        this.squadronToShow = squadronToShow;

        this.getChildren().
                addAll(verticalCrossBar, horizontalCrossBar, directionalLine, directionalLineEnd, speedText);
        this.setZoomLevel(1);
        this.zoomLevelProperty().addListener(updaterListener);
        this.squadronToShow.positionXProperty().addListener(updaterListener);
        this.squadronToShow.positionYProperty().addListener(updaterListener);
        this.squadronToShow.destinationProperty().addListener(updaterListener);
        directionalLine.visibleProperty().bind(Bindings.
                isNotNull(squadronToShow.
                destinationProperty()));
        directionalLineEnd.visibleProperty().bind(Bindings.
                isNotNull(squadronToShow.
                destinationProperty()));
        speedText.visibleProperty().bind(Bindings.isNotNull(squadronToShow.
                destinationProperty()));
        speedText.textProperty().bind(new StringBinding() {
            {
                bind(SquadronComponent.this.squadronToShow.speedProperty());
            }

            @Override
            protected String computeValue() {
                Double speedMS = SquadronComponent.this.squadronToShow.
                        getSpeed();
                long speedKMS = Math.round(speedMS / 1000);
                String speedStr = Long.toString(speedKMS) + " km/s";
                return speedStr;
            }
        });


//        directionalLine.endXProperty().bind(new DoubleBinding() {
//
//            {
//                bind(zoomLevelProperty(), SquadronComponent.this.squadronToShow.
//                        positionXProperty(), SquadronComponent.this.squadronToShow.
//                        positionYProperty(), SquadronComponent.this.squadronToShow.
//                        destinationProperty());
//            }
//
//            @Override
//            protected double computeValue() {
//                double zoomLevel = getZoomLevel();
//                final double centerX = SquadronComponent.this.squadronToShow.getPositionX();
//                final double centerY = SquadronComponent.this.squadronToShow.getPositionY();
//
//                double centerXCalculated = centerX / Constants.FIXED_QUOTIENT * zoomLevel;
//                double centerYCalculated = centerY / Constants.FIXED_QUOTIENT * zoomLevel;
//
//                //TODO replace this calculation by the use of the ShipMovementController.calculateIntermediateCoordinate
//                double li = DIRECTIONAL_LINE_LENGTH;
//                final double destinationX = SquadronComponent.this.squadronToShow.
//                        getDestination().
//                        getPositionX();
//                final double destinationY = SquadronComponent.this.squadronToShow.
//                        getDestination().
//                        getPositionY();
//
//                final double destinationXCalculated = destinationX / Constants.FIXED_QUOTIENT * zoomLevel;
//                final double destinationYCalculated = destinationY / Constants.FIXED_QUOTIENT * zoomLevel;
//
//                double lc = Math.sqrt((destinationXCalculated - centerXCalculated) * (destinationXCalculated - centerXCalculated) + (destinationYCalculated - centerYCalculated) * (destinationYCalculated - centerYCalculated));
//                double endOfLineX = (li * (destinationXCalculated - centerXCalculated)) / lc + centerXCalculated;
//
//                return endOfLineX;
//            }
//        });
    }

    public void update() {
        double zoomLevel = getZoomLevel();
        final double centerX = squadronToShow.getPositionX();
        final double centerY = squadronToShow.getPositionY();
        double centerXCalculated = centerX / Constants.FIXED_QUOTIENT * zoomLevel;
        double centerYCalculated = centerY / Constants.FIXED_QUOTIENT * zoomLevel;

        horizontalCrossBar.setStartX(centerXCalculated - 5);
        horizontalCrossBar.setEndX(centerXCalculated + 5);
        horizontalCrossBar.setStartY(centerYCalculated);
        horizontalCrossBar.setEndY(centerYCalculated);

        verticalCrossBar.setStartX(centerXCalculated);
        verticalCrossBar.setEndX(centerXCalculated);
        verticalCrossBar.setStartY(centerYCalculated - 5);
        verticalCrossBar.setEndY(centerYCalculated + 5);

        //If Ship is moving, we calculate the arrow
        if (squadronToShow.getDestination() != null) {
            //TODO replace this calculation by the use of the ShipMovementController.calculateIntermediateCoordinate
            double li = DIRECTIONAL_LINE_LENGTH;
            final double destinationX = this.squadronToShow.getDestination().
                    getPositionX();
            final double destinationY = this.squadronToShow.getDestination().
                    getPositionY();

            final double destinationXCalculated = destinationX / Constants.FIXED_QUOTIENT * zoomLevel;
            final double destinationYCalculated = destinationY / Constants.FIXED_QUOTIENT * zoomLevel;

            double lc = Math.
                    sqrt((destinationXCalculated - centerXCalculated) * (destinationXCalculated - centerXCalculated) + (destinationYCalculated - centerYCalculated) * (destinationYCalculated - centerYCalculated));
            Point2D intermediateCoordinate = ShipUtils.
                    calculateIntermediateCoordinate(new Point2D(centerXCalculated, centerYCalculated), new Point2D(destinationXCalculated, destinationYCalculated), li);

            directionalLine.setStartX(centerXCalculated);
            directionalLine.setStartY(centerYCalculated);
            directionalLine.setEndX(intermediateCoordinate.getX());
            directionalLine.setEndY(intermediateCoordinate.getY());

            //End of the directionalLine
            directionalLineEnd.setRadius(3);
            directionalLineEnd.setCenterX(intermediateCoordinate.getX());
            directionalLineEnd.setCenterY(intermediateCoordinate.getY());

            //Speed Text
            speedText.setX(intermediateCoordinate.getX() + 5);
            if (intermediateCoordinate.getY() > centerYCalculated) {
                speedText.setY(intermediateCoordinate.getY() + 5);
            } else {
                speedText.setY(intermediateCoordinate.getY() - 5);
            }
        }
    }

    public DoubleProperty zoomLevelProperty() {
        if (zoomLevelProperty == null) {
            zoomLevelProperty = new SimpleDoubleProperty(this, "zoomLevel", 1);
        }
        return zoomLevelProperty;
    }

    public double getZoomLevel() {
        return zoomLevelProperty().getValue();
    }

    public void setZoomLevel(double zoomLevel) {
        zoomLevelProperty().setValue(zoomLevel);
    }
}
