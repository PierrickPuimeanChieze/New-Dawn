/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.newdawn.model.system;

/**
 *
 * @author Pierrick
 */
public class Orbit {
    private CelestialBody ref;
    private double radius;
    
    public Orbit(CelestialBody ref, long radius) {
        this.ref = ref;
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public CelestialBody getRef() {
        return ref;
    }
    
    
}
