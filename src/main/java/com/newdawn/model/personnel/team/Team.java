package com.newdawn.model.personnel.team;

import com.newdawn.model.personnel.Official;
import com.newdawn.model.personnel.PersonnelAssignment;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * 
 * @author Pierrick Puimean-Chieze
 */
public abstract class Team implements PersonnelAssignment {

	public abstract String[] validateAddition(Official teamMember);

	private ObjectProperty<Official> leaderProperty;
	private ListProperty<Official> teamMembersProperty;

	// TODO ajouter une verification sur la liste
	public ReadOnlyListProperty teamMembersProperty() {
		if (teamMembersProperty == null) {
			final ObservableList<Official> teamMembers = FXCollections
					.observableArrayList();
			teamMembersProperty = new SimpleListProperty<>(this, "teamMembers",
					teamMembers);
		}
		return teamMembersProperty;
	}

	public ObservableList<Official> getTeamMembers() {
		return teamMembersProperty().getValue();
	}

	public ObjectProperty<Official> leaderProperty() {
		if (leaderProperty == null) {
			leaderProperty = new SimpleObjectProperty<>(this, "leader");
		}
		return leaderProperty;
	}

	/**
	 * Get the value of leader
	 * 
	 * @return the value of leader
	 */
	public Official getLeader() {
		return leaderProperty().getValue();
	}

	/**
	 * Set the value of leader
	 * 
	 * @param leader
	 *            new value of leader
	 */
	public void setLeader(Official leader) {
		this.leaderProperty().setValue(leader);
	}
}
