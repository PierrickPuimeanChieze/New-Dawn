package com.newdawn.model.personnel.team;

import com.newdawn.model.personnel.Official;
import com.newdawn.model.personnel.PersonnelAssignment;
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
public abstract class Team  implements PersonnelAssignment{

    protected abstract String[] validateAddition(Official teamMember);
    private ObjectProperty<Official> leaderProperty;
    private ListProperty<Official> teamMembersProperty;

    //TODO ajouter une verification sur la liste
    public ListProperty teamMembersProperty() {
        if (teamMembersProperty == null) {
            final ObservableList<Official> teamMembers = FXCollections.
                    observableArrayList();
            teamMembersProperty = new SimpleListProperty<>(this, "teamMembers", teamMembers);
        }
        return teamMembersProperty;
    }

    public ObservableList<Official> getTeamMembers() {
        return teamMembersProperty().getValue();
    }

    public void addTeamMember(Official teamMember) {
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

    public boolean removeTeamMember(Official teamMember) {
        return teamMembersProperty().remove(teamMember);
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
     * @param leader new value of leader
     */
    public void setLeader(Official leader) {
        this.leaderProperty().setValue(leader);
    }
}
