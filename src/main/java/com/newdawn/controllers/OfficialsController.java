package com.newdawn.controllers;

import com.newdawn.model.personnel.*;
import com.newdawn.model.personnel.ranks.NavalRank;
import com.newdawn.model.personnel.team.FieldTeam;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Pierrick Puimean-Chieze
 */
@Component
public class OfficialsController {

	@Autowired
	private Skill[] skills;
	@Autowired
	private GameData gameData;
	@Autowired
	private TeamController teamController;

	private ObservableMap<Skill, SkillLevel> createInitialSkillsLevel() {
		ObservableMap<Skill, SkillLevel> toReturn = FXCollections
				.observableHashMap();
		for (Skill skill : skills) {
			SkillLevel skillLevel = new SkillLevel(skill);
			toReturn.put(skill, skillLevel);
		}
		return toReturn;
	}

	public NavalOfficer createNewNavalOfficer(String name,
			PersonnelLocalisation localization) {
		NavalOfficer created = new NavalOfficer();
		created.setName(name);
		created.setLocalization(localization);
		created.setRank(NavalRank.A0);
		created.setSkillLevels(createInitialSkillsLevel());
		return created;
	}

	public Scientist createNewScientist(String name,
			PersonnelLocalisation localization) {
		Scientist created = new Scientist();
		created.setName(name);
		created.setLocalization(localization);
		// created.setRank(NavalRank.A0);
		created.setSkillLevels(createInitialSkillsLevel());
		return created;

	}

	public Official getOfficialByName(String name) {
		for (Official official : gameData.getOfficials()) {
			if (official.getName().equals(name)) {
				return official;
			}
		}
		return null;
	}

	public void assignOfficialTo(Official officialToAssign,
			PersonnelAssignment assignment) {
		if (assignment instanceof FieldTeam) {
			FieldTeam fieldTeam = (FieldTeam) assignment;
			teamController.addMemberToTeam(officialToAssign, fieldTeam);
			
		}
	}
}
