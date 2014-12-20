/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newdawn.model.system;

import com.newdawn.model.mineral.MinerallyExploitableBody;
import com.newdawn.model.mineral.MinerallyExploitableBodyModel;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * 
 * @author Pierrick Puimean-Chieze
 */
public class Asteroid extends OrbitalBody implements MinerallyExploitableBody {

	private MinerallyExploitableBodyModel minerallyExploitableBodyModel;
	private ReadOnlyStringProperty visualNameProperty = null;

	public Asteroid(Orbit orbit, double diameter) {
		super(orbit, diameter);
	}

	@Override
	public MinerallyExploitableBodyModel getMinerallyExploitableBodyModel() {
		return minerallyExploitableBodyModel;
	}

	@Override
	public void setMinerallyExploitableBodyModel(
			MinerallyExploitableBodyModel minerallyExploitableBodyModel) {
		this.minerallyExploitableBodyModel = minerallyExploitableBodyModel;
	}

	@Override
	public ReadOnlyStringProperty visualNameProperty() {
		if (visualNameProperty == null) {
			visualNameProperty = new SimpleStringProperty(this, "visualName",
					getName());
		}

		return visualNameProperty ;
	}

	public String getVisualName() {
		return visualNameProperty().getValue();
	}
}
