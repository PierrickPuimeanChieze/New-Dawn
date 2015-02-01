package com.newdawn.gui.map.system;

import javafx.scene.shape.Circle;

/**
 * Created by Pierrick on 01/02/2015.
 */
public class SpecialCircle extends Circle {

    public boolean altered = false;

    public double initialRadius;
    public SpecialCircle(double centerX, double centerY, double radius) {
        super(centerX, centerY, radius);
        initialRadius = radius;
    }


}
