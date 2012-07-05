/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.newdawn.gui.map.system;

import com.newdawn.model.system.CelestialBody;
import com.newdawn.model.system.Satellite;
import com.newdawn.model.system.Planet;
import com.newdawn.model.system.Star;
import javafx.scene.paint.Color;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class CelestialBodyComponentFactory {

    public static CelestialBodyComponent buildComponentForBody(CelestialBody body) {
        CelestialBodyComponent toReturn = new CelestialBodyComponent(body);
        if (body instanceof Star) {
            toReturn.getCelestialBodyCircle().setFill(Color.RED);
        }
        if (body instanceof Planet) {

            toReturn.getCelestialBodyCircle().setFill(Color.BLUE);

        }
        if (body instanceof Satellite) {
            toReturn.getCelestialBodyCircle().setFill(Color.GREEN);
        }
        return toReturn;
    }
}
