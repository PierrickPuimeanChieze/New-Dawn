package com.newdawn.model.personnel;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public abstract class Team implements Assignment {

    protected abstract String[] validateAddition(PersonnelMember teamMember);
    private ObjectProperty<PersonnelMember> leaderProperty;
    private ListProperty<PersonnelMember> teamMembersProperty;
    
    //TODO ajouter une verification sur la liste
    public ListProperty teamMembersProperty() {
        if (teamMembersProperty == null) {
            final ObservableList<PersonnelMember> teamMembers = FXCollections.observableArrayList();
            teamMembersProperty = new SimpleListProperty<>(this, "teamMembers", teamMembers);
        }
        return teamMembersProperty;
    }
    public ObservableList<PersonnelMember> getTeamMembers() {
        return teamMembersProperty().getValue();
    }

    public void addTeamMember(PersonnelMember teamMember) {
        String[] validateAdditionErrors = validateAddition(teamMember);
        if (validateAdditionErrors != null) {
            teamMembersProperty().add(teamMember);
        } else {
            //TODO :Change the exception type
            StringBuilder message = new StringBuilder();
            for (String string : validateAdditionErrors) {
                message.append(string);
                message.append('\n');
            }
            throw new IllegalStateException(message.toString());
        }
    }

    public boolean removeTeamMember(PersonnelMember teamMember) {
        return teamMembersProperty().remove(teamMember);
    }

    public ObjectProperty<PersonnelMember> leaderProperty() {
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
    public PersonnelMember getLeader() {
        return leaderProperty().getValue();
    }

    /**
     * Set the value of leader
     *
     * @param leader new value of leader
     */
    public void setLeader(PersonnelMember leader) {
        this.leaderProperty().setValue(leader);
    }
}
