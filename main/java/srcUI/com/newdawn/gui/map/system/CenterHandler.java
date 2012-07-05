/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.newdawn.gui.map.system;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class CenterHandler implements EventHandler<MouseEvent> {

    private final SystemViewer systemViewer;
    private final CelestialBodyComponent celestialBody;

    CenterHandler(SystemViewer systemViewer, CelestialBodyComponent celestialBody) {
        this.systemViewer = systemViewer;
        this.celestialBody = celestialBody;
    }

    @Override
    public void handle(MouseEvent event) {
        this.systemViewer.centerTo(this.celestialBody.getCelestialBodyCircle().
                getCenterX(), this.celestialBody.getCelestialBodyCircle().
                getCenterY());
    }
}
