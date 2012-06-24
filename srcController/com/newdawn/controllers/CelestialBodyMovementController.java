/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.newdawn.controllers;

import com.newdawn.model.system.OrbitalBody;
import org.springframework.stereotype.Component;

/**
 *
 * @author Pierrick
 */
@Component
public class CelestialBodyMovementController {
    
    
    public void moveBody(long time, OrbitalBody bodyToMove) {
        final double orbitalSpeed = bodyToMove.getOrbitalSpeed();
        final double originalDelta = bodyToMove.getDelta();
        double newDelta = originalDelta+orbitalSpeed*time;
        final double newDeltaModified = newDelta%(Math.PI*2);
        bodyToMove.setDelta(newDeltaModified);
    }
    
   
    
}
