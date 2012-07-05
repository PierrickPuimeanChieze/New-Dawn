/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newdawn.model.system;

import com.newdawn.model.mineral.MinerallyExploitableBody;
import com.newdawn.model.mineral.MinerallyExploitableBodyModel;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class Asteroid extends OrbitalBody implements MinerallyExploitableBody {

    private MinerallyExploitableBodyModel minerallyExploitableBodyModel = new MinerallyExploitableBodyModel();

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
}
