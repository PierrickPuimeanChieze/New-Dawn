package com.newdawn.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.newdawn.controllers.TeamController.FieldTeamType;
import com.newdawn.model.personnel.team.TeamAssignment;
import com.newdawn.model.system.StellarSystem;

@Resource
public class AssignmentController {

	@Resource
	private GameData gameData;
	public List<TeamAssignment> getPossibleTeamAssignments(StellarSystem system, FieldTeamType fieldTeamType) {
		throw new NotImplementedException(); 
	}
	
	public Map<StellarSystem, List<TeamAssignment>> getAllPossibleTeamAssignments(FieldTeamType fieldTeamType) {
		Map<StellarSystem, List<TeamAssignment>> toReturn = new HashMap<>();
		for (StellarSystem system : gameData.getStellarSystems()) {
			List<TeamAssignment> assignments = getPossibleTeamAssignments(system, fieldTeamType);
			if (!assignments.isEmpty()) {
				toReturn.put(system, assignments);
			}
		}
		return toReturn;
	}
}
