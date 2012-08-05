package com.newdawn.gui.personnel;

import com.newdawn.model.personnel.PersonnelAssignment;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class AssignementFilter {

    private StringProperty nameProperty;
    private ListProperty<PersonnelAssignment> assignmentsProperty;

    public ListProperty<PersonnelAssignment> assignmentsProperty() {
        if (assignmentsProperty == null) {
            assignmentsProperty = new SimpleListProperty<>(this, "assignments");
        }
        return assignmentsProperty;
    }

    /**
     * Get the value of assignments
     *
     * @return the value of assignments
     */
    public ObservableList<PersonnelAssignment> getAssignments() {
        return assignmentsProperty().getValue();
    }

    /**
     * Set the value of assignments
     *
     * @param assignments new value of assignments
     */
    public void setAssignments(ObservableList<PersonnelAssignment> assignments) {
        this.assignmentsProperty().setValue(assignments);
    }

    public StringProperty nameProperty() {
        if (nameProperty == null) {
            nameProperty = new SimpleStringProperty(this, "name");
        }
        return nameProperty;
    }

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return nameProperty().getValue();
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName(String name) {
        this.nameProperty().setValue(name);
    }
}
