/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newdawn.model.system;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author puimeapi
 */
public class Planet extends OrbitalBody {

    public Planet(String name, PlanetaryClass planetaryClass, Orbit orbit, long diameter) {
        this(name, planetaryClass, orbit, diameter, 0.0, null);
    }
    public Planet(String name, PlanetaryClass planetaryClass, Orbit orbit, long diameter, double delta) {
        this(name, planetaryClass, orbit, diameter, delta, null);
    }
    public Planet(String name, PlanetaryClass planetaryClass, Orbit orbit, long diameter, double delta, Long orbitalPeriod) {
        super(orbit, delta, diameter, orbitalPeriod);
        this.planetaryClass = planetaryClass;
        setName(name);
    }



    public List<Satellite> getSatellites() {
        return satellites;
    }

    /**
     * List possible planetary classes
     */
    public static enum PlanetaryClass {

        TERRESTRIAL,
        CHUNK,
        GAS_GIANT,
        SUPERJOVIAN
    }
    private PlanetaryClass planetaryClass;
    private List<Satellite> satellites = new ArrayList<Satellite>();
    
}
