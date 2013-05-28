/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newdawn.controllers;

import com.newdawn.model.ships.Squadron;
import com.newdawn.model.system.StellarSystem;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Pierrick Puimean-Chieze
 */
@Component
public class FleetManagementController {

	List<Squadron> getAvailableSquadronInSystem(StellarSystem... systems) {
		List<Squadron> toReturn = new ArrayList<>();

		for (StellarSystem system : systems) {
			toReturn.addAll(system.getSquadrons());
		}
		return toReturn;

	}
}
