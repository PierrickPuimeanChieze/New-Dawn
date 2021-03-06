package com.newdawn.controllers;

import com.newdawn.model.mineral.MinerallyExploitableBody;
import com.newdawn.model.personnel.Official;
import com.newdawn.model.personnel.team.FieldTeam;
import com.newdawn.model.personnel.team.GeologicalTeam;
import com.newdawn.model.personnel.team.Team;
import com.newdawn.model.personnel.team.TeamAssignment;
import javafx.beans.property.ReadOnlyListProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Pierrick Puimean-Chieze
 */
@Component
public class TeamController {

	@Autowired
	private ApplicationContext applicationContext;
	//TODO remove fieldTeamType, eventually; use Team class instead
	private FieldTeam createFieldTeam(FieldTeamType fieldTeamType)
			throws AssertionError {
		FieldTeam toReturn;
		switch (fieldTeamType) {
		case GEOLOGICAL:
			toReturn = applicationContext.getBean(GeologicalTeam.class);

			break;
		default:
			throw new AssertionError();
		}
		return toReturn;
	}

	public static enum FieldTeamType {

		GEOLOGICAL
	}

	@Autowired
	private GameData gameData;

	public FieldTeam createTeamWithLeader(Official teamLeader,
			FieldTeamType fieldTeamType) {
		if (teamLeader.getAssignment() != null) {
			throw new PersonnelAssignmentException(
					"The leader %s for the new team is already assigned somewhere else",
					teamLeader.getName());
		}
		FieldTeam toReturn = createFieldTeam(fieldTeamType);

		toReturn.setLeader(teamLeader);
		toReturn.setName(teamLeader.getName() + " ' "
				+ fieldTeamType.toString().toLowerCase() + " team");
		toReturn.setLocalization(teamLeader.getLocalization());
		teamLeader.localizationProperty().bind(toReturn.localizationProperty());
		teamLeader.setAssignment(toReturn);
		gameData.getGeologicalTeams().add((GeologicalTeam) toReturn);
		return toReturn;
	}

	public void addMemberToTeam(Official newMember, FieldTeam team) {
		if (newMember.getAssignment() != null) {
			throw new PersonnelAssignmentException(
					"The new member %s for the team %s is already assigned somewhere else",
					newMember.getName(), team.getName());
		}
		final String[] validateAdditionError = team.validateAddition(newMember);
		if (validateAdditionError.length > 0) {
			// TODO :Change the exception type
			StringBuilder message = new StringBuilder(
					"The member %s cannot be added to the team %s. Errors :");
			for (String string : validateAdditionError) {
				message.append(string);
				message.append('\n');
			}
			throw new PersonnelAssignmentException(message.toString(),
					newMember.getName(), team.getName());
		} else {
			team.getTeamMembers().add(newMember);
			newMember.localizationProperty().bind(team.localizationProperty());
			newMember.setAssignment(team);
		}
	}

	public boolean promotingTeamMemberToLeader(FieldTeam team, Official member) {
		final String[] validateAdditionError = canPromoteTeamMember(member,
				team);
		if (validateAdditionError.length > 0) {
			StringBuilder message = new StringBuilder(
					"The member %s cannot be promoted to leader for the team %s. Errors :");
			for (String string : validateAdditionError) {
				message.append(string);
				message.append('\n');
			}

			throw new PersonnelAssignmentException(message.toString(),
					member.getName(), team.getName());
		}

		if (removeTeamMember(team, member)) {
			team.getTeamMembers().add(team.getLeader());
			team.setLeader(member);
			return true;
		} else {
			return false;
		}
	}

	public String[] canPromoteTeamMember(Official wantedLeader, FieldTeam team) {
		FieldTeam testTeam = createWorkingClone(team);
		Official oldLeader = testTeam.getLeader();
		if (!removeTeamMember(testTeam, wantedLeader)) {
			throw new PersonnelAssignmentException(
					"the member %s is not a member of a the team %s",
					wantedLeader.getName(), team.getName());
		}
		testTeam.setLeader(wantedLeader);
		return testTeam.validateAddition(oldLeader);
	}

	public boolean removeTeamMember(Team team, Official teamMember) {
		ReadOnlyListProperty teamMembersProperty = team.teamMembersProperty();
		final boolean toReturn = teamMembersProperty.remove(teamMember);
		if (toReturn) {
			teamMember.setAssignment(null);
			teamMember.localizationProperty().unbind();
		}
		return toReturn;
	}

	private FieldTeam createWorkingClone(FieldTeam team) {
		FieldTeam toReturn = createFieldTeam(team.getType());
		toReturn.setLeader(team.getLeader());
		toReturn.getTeamMembers().addAll(team.getTeamMembers());
		return toReturn;
	}

	public void setLeaderForTeam(Official wantedLeader, FieldTeam team) {
		if (wantedLeader.getAssignment() != null) {
			throw new PersonnelAssignmentException(
					"The leader %s has already an assignment",
					wantedLeader.getName());
		}
		if (team.getLeader() != null) {
			throw new PersonnelAssignmentException(
					"The team %s has already a leader", team.getName());

		}
		team.setLeader(wantedLeader);
		wantedLeader.setAssignment(team);
	}

	public void assignTeamToAssignment(FieldTeam selectedTeam,
			TeamAssignment assignment) {
		switch (selectedTeam.getType()) {
		case GEOLOGICAL:
			assert assignment instanceof MinerallyExploitableBody;
			assert selectedTeam instanceof GeologicalTeam;
			assignGeologicalTeamToMinerallyExploitableBody((GeologicalTeam)selectedTeam, (MinerallyExploitableBody)assignment);
			break;
		default:
			//TODO handle the other type
			throw new AssertionError();
		}
		//TODO supprimer et mettre en place le transport
		selectedTeam.setLocalization(selectedTeam.getAssignment());
		
	}

	public void deleteTeam(Team teamToDelete)
	{
		
	}
	private void assignGeologicalTeamToMinerallyExploitableBody(
			GeologicalTeam selectedTeam, MinerallyExploitableBody assignment) {
		selectedTeam.setAssignement(assignment);
		selectedTeam.setBodyProspected(assignment);
	}
}
