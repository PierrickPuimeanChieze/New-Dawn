package com.newdawn.model.personnel;

import javafx.beans.property.MapProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableMap;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public abstract class PersonnelMember {

    private String name;
    private ObjectProperty<PersonnelLocalisation> localisationProperty;
    private ObjectProperty<Assignment> assignmentProperty;

    public ObjectProperty<Assignment> assignmentProperty() {
        if (assignmentProperty == null) {
            assignmentProperty = new SimpleObjectProperty<>(this, "assignement");
        }
        return assignmentProperty;
    }

    /**
     * Get the value of assignment
     *
     * @return the value of assignment
     */
    public Assignment getAssignment() {
        return assignmentProperty().getValue();
    }

    /**
     * Set the value of assignment
     *
     * @param assignement new value of assignment
     */
    public void setAssignment(Assignment assignement) {
        this.assignmentProperty().setValue(assignement);
    }

    private MapProperty<Skill, SkillLevel> skillLevelsProperty;

    public ObjectProperty<PersonnelLocalisation> localisationProperty() {
        if (localisationProperty == null) {
            localisationProperty = new SimpleObjectProperty<>(this, "localisation");
        }
        return localisationProperty;
    }

    /**
     * Get the value of localisation
     *
     * @return the value of localisation
     */
    public PersonnelLocalisation getLocalisation() {
        return localisationProperty().getValue();
    }

    /**
     * Set the value of localisation
     *
     * @param localisation new value of localisation
     */
    public void setLocalisation(PersonnelLocalisation localisation) {
        this.localisationProperty().setValue(localisation);
    }

    public MapProperty<Skill, SkillLevel> skillLevelsProperty() {
        if (skillLevelsProperty == null) {
            skillLevelsProperty = new SimpleMapProperty<>(this, "skillLevels");
        }
        return skillLevelsProperty;
    }

    /**
     * Get the value of skillLevels
     *
     * @return the value of skillLevels
     */
    public ObservableMap<Skill, SkillLevel> getSkillLevels() {
        return skillLevelsProperty().getValue();
    }

    /**
     * Set the value of skillLevels
     *
     * @param skillLevels new value of skillLevels
     */
    public void setSkillLevels(ObservableMap<Skill, SkillLevel> skillLevels) {
        this.skillLevelsProperty().setValue(skillLevels);
    }
}
