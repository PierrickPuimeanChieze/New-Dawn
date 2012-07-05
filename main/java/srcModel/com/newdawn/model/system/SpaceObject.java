/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.newdawn.model.system;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public interface SpaceObject {

    public double getPositionX();

    public double getPositionY();

    public String getName();
    
    public StellarSystem getStellarSystem();
}
