/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newdawn.model.system;

/**
 *
 * @author puimeapi
 */
public class Satellite extends OrbitalBody{

    public Satellite(String name, Orbit orbit, double delta, Long orbitalPeriod, long diameter) {
        super(orbit, delta, diameter, orbitalPeriod);
        setName(name);
    }

    public Satellite(String name, Orbit orbit, double delta, long diameter) {
        super(orbit, delta, diameter);
        setName(name);
    }

    public Satellite(String name, Orbit orbit, long diameter) {
        super(orbit, diameter);
        setName(name);
    }
}
