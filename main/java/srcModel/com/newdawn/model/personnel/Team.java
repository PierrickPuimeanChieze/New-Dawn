package com.newdawn.model.personnel;

import java.util.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public abstract class Team {

    protected abstract String[] validateAddition(PersonnelMember teamMember);
    private ObjectProperty<PersonnelMember> leaderProperty;
    private ObservableList<PersonnelMember> teamMembers = FXCollections.
            observableArrayList();

    public ObservableList<PersonnelMember> getTeamMembers() {
        return FXCollections.unmodifiableObservableList(teamMembers);
    }

    public void addTeamMember(PersonnelMember teamMember) {
        String[] validateAdditionErrors = validateAddition(teamMember);
        if (validateAdditionErrors != null) {
            teamMembers.add(teamMember);
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
        return teamMembers.remove(teamMember);
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
