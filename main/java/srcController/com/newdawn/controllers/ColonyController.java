package com.newdawn.controllers;

import com.newdawn.model.colony.Colony;
import com.newdawn.model.system.Planet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Pierrick Puimean-Chieze
 */
@Component
public class ColonyController {

	@Autowired
	private GameData gameData;
	@Autowired
	private Config config;

	public void calculateColonyPopulationAndWealth(Colony colony, long subPulse) {
		long counter = colony.getPopulationGrowCounter() + subPulse;
		int period = config.getPopulationGrowPeriod();
		if (counter >= period) {
			gameData.setWealth(gameData.getWealth()
					+ colony.getWealthProduction());
			double population = colony.getPopulation();
			double populationGrowRate = colony.getPopulationGrowRate();
			population += population / 100 * populationGrowRate;
			colony.setPopulation(Math.round(population));
			counter -= period;
		}
		colony.setPopulationGrowCounter(counter);
	}

	public void updateSystemWithColony(Planet planet, Colony colony) {
		colony.setColonizedPlanet(planet);
		planet.getStellarSystem().getColonies().add(colony);
	}

	public void destroyColony(Colony colony) {
		Planet planet = colony.getColonizedPlanet();
		colony.setColonizedPlanet(null);
		planet.getStellarSystem().getColonies().remove(colony);
	}
}
