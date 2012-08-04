/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newdawn.model.system;

import com.newdawn.model.mineral.MinerallyExploitableBody;
import com.newdawn.model.mineral.MinerallyExploitableBodyModel;
import javafx.beans.property.ReadOnlyStringProperty;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class Asteroid extends OrbitalBody implements MinerallyExploitableBody {

    private MinerallyExploitableBodyModel minerallyExploitableBodyModel;

    public Asteroid(Orbit orbit, long diameter) {
        super(orbit, diameter);
    }

    @Override
    public MinerallyExploitableBodyModel getMinerallyExploitableBodyModel() {
        return minerallyExploitableBodyModel;
    }

    @Override
    public void setMinerallyExploitableBodyModel(MinerallyExploitableBodyModel minerallyExploitableBodyModel) {
        this.minerallyExploitableBodyModel = minerallyExploitableBodyModel;
    }

    @Override
    public ReadOnlyStringProperty visualNameProperty() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
