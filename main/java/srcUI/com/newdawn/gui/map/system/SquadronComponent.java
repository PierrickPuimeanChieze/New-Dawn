/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.newdawn.gui.map.system;

import com.newdawn.model.ships.Squadron;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

/**
 *
 * * @author Pierrick Puimean-Chieze
 *
 */
// TODO : ajouter les informations textuelles (no, vitesse) 
// TODO : Essayer de mettre en place des bindings, plutot que des listeners, pour la mise a jour
// TODO : documentation
public class SquadronComponent extends Group {

    private DoubleProperty zoomLevelProperty;
    private Squadron taskGroupToShow;
    private Line verticalCrossBar = new Line();
    private Line horizontalCrossBar = new Line();
    private Line directionalLine = new Line();
    //Will be replaced by an arrow head when those will be available
    private Circle directionalLineEnd = new Circle();
    private Text speedText = new Text();
    private static final double DIRECTIONAL_LINE_LENGTH = 15;
    private ChangeListener<Number> updaterListener = new ChangeListener<Number>() {

        @Override
        public void changed(ObservableValue<? extends Number> property, Number oldValue, Number newValue) {
            update();
        }
    };

    public SquadronComponent(Squadron taskGroupToShow) {
        this.taskGroupToShow = taskGroupToShow;

        this.getChildren().addAll(verticalCrossBar, horizontalCrossBar, directionalLine, directionalLineEnd, speedText);
        this.setZoomLevel(1);
        this.zoomLevelProperty().addListener(updaterListener);
        this.taskGroupToShow.positionXProperty().addListener(updaterListener);
        this.taskGroupToShow.positionYProperty().addListener(updaterListener);

        speedText.textProperty().bind(new StringBinding() {

            {
                bind(SquadronComponent.this.taskGroupToShow.speedProperty());
            }
            @Override
            protected String computeValue() {
                Double speedMS = SquadronComponent.this.taskGroupToShow.getSpeed();
                long speedKMS = Math.round(speedMS/1000);
                String speedStr = Long.toString(speedKMS)+" km/s";
                return speedStr;
            }
        });
    }

    public void update() {
        double zoomLevel = getZoomLevel();
        final double centerX = taskGroupToShow.getPositionX();
        final double centerY = taskGroupToShow.getPositionY();
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

        //If Ship is moving, we show the arrow
        if (taskGroupToShow.getDestination() != null) {
            directionalLine.setVisible(true);
            directionalLineEnd.setVisible(true);
            speedText.setVisible(true);
            //TODO replace this calculation by the use of the ShipMovementController.calculateIntermediateCoordinate
            double li = DIRECTIONAL_LINE_LENGTH;
            final double destinationX = this.taskGroupToShow.getDestination().getPositionX();
            final double destinationY = this.taskGroupToShow.getDestination().getPositionY();

            final double destinationXCalculated = destinationX / Constants.FIXED_QUOTIENT * zoomLevel;
            final double destinationYCalculated = destinationY / Constants.FIXED_QUOTIENT * zoomLevel;

            double lc = Math.sqrt((destinationXCalculated - centerXCalculated) * (destinationXCalculated - centerXCalculated) + (destinationYCalculated - centerYCalculated) * (destinationYCalculated - centerYCalculated));
            double endOfLineX = (li * (destinationXCalculated - centerXCalculated)) / lc + centerXCalculated;
            double endOfLineY = (li * (destinationYCalculated - centerYCalculated)) / lc + centerYCalculated;

            directionalLine.setStartX(centerXCalculated);
            directionalLine.setStartY(centerYCalculated);
            directionalLine.setEndX(endOfLineX);
            directionalLine.setEndY(endOfLineY);

            //End of the directionalLine
            directionalLineEnd.setRadius(3);
            directionalLineEnd.setCenterX(endOfLineX);
            directionalLineEnd.setCenterY(endOfLineY);

            //Speed Text
            speedText.setX(endOfLineX + 5);
            if (endOfLineY > centerYCalculated) {
                speedText.setY(endOfLineY + 5);
            } else {
                speedText.setY(endOfLineY - 5);
            }

        } else {
//            getChildren().removeAll(directionalLine, directionalLineEnd, speedText);
            directionalLine.setVisible(false);
            directionalLineEnd.setVisible(false);
            speedText.setVisible(false);

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
