package com.newdawn.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.newdawn.controllers.TeamController.FieldTeamType;
import com.newdawn.model.personnel.team.TeamAssignment;
import com.newdawn.model.system.StellarSystem;

@Component
public class AssignmentController {

	@Resource
	private GameData gameData;
	public Map<String, List<? extends TeamAssignment>> getPossibleTeamAssignments(StellarSystem system, FieldTeamType fieldTeamType) {
		switch (fieldTeamType) {
		case GEOLOGICAL:
			return getMinerralyExploitableBody(system);

		default:
			throw new NotImplementedException();
		} 
	}
	
	private Map<String, List<? extends TeamAssignment>> getMinerralyExploitableBody(
			StellarSystem system) {
		//TODO add the satellites
		//TODO add the comets
		//TODO internationalize
		Map<String, List<? extends TeamAssignment>> toReturn = new HashMap<>();
		toReturn.put("Asteroids",system.getAsteroids());
		toReturn.put("Planets",system.getPlanets());
		return toReturn;
	}

	public Map<StellarSystem, Map<String, List<? extends TeamAssignment>>> getAllPossibleTeamAssignments(FieldTeamType fieldTeamType) {
		Map<StellarSystem, Map<String, List<? extends TeamAssignment>>> toReturn = new HashMap<>();
		for (StellarSystem system : gameData.getStellarSystems()) {
			Map<String, List<? extends TeamAssignment>> assignments = getPossibleTeamAssignments(system, fieldTeamType);
			if (!assignments.isEmpty()) {
				toReturn.put(system, assignments);
			}
		}
		return toReturn;
	}
}
