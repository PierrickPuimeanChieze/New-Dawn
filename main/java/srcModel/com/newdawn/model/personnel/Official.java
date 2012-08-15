package com.newdawn.model.personnel;

import com.newdawn.model.personnel.team.TeamAssignment;
import com.newdawn.model.personnel.ranks.Rank;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

/**
 * 
 * @author Pierrick Puimean-Chieze
 */
public abstract class Official {

	private StringProperty nameProperty;
	private ObjectProperty<PersonnelLocalisation> localizationProperty;
	private ObjectProperty<PersonnelAssignment> assignmentProperty;
	private MapProperty<Skill, SkillLevel> skillLevelsProperty;
	protected ObjectProperty<Rank> rankProperty = new SimpleObjectProperty<>(
			this, "rank");

	public ReadOnlyObjectProperty<Rank> rankProperty() {
		return rankProperty;
	}

	public Rank getRank() {
		return rankProperty.get();
	}

	public ObjectProperty<PersonnelAssignment> assignmentProperty() {
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
	public PersonnelAssignment getAssignment() {
		return assignmentProperty().getValue();
	}

	/**
	 * Set the value of assignment
	 * 
	 * @param assignement
	 *            new value of assignment
	 */
	public void setAssignment(PersonnelAssignment assignement) {
		this.assignmentProperty().setValue(assignement);
	}

	public ObjectProperty<PersonnelLocalisation> localizationProperty() {
		if (localizationProperty == null) {
			localizationProperty = new SimpleObjectProperty<>(this,
					"localisation");
		}
		return localizationProperty;
	}

	/**
	 * Get the value of localization
	 * 
	 * @return the value of localization
	 */
	public PersonnelLocalisation getLocalization() {
		return localizationProperty().getValue();
	}

	/**
	 * Set the value of localization
	 * 
	 * @param localisation
	 *            new value of localization
	 */
	public void setLocalization(PersonnelLocalisation localization) {
		this.localizationProperty().setValue(localization);
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
	 * @param name
	 *            new value of name
	 */
	public void setName(String name) {
		this.nameProperty().setValue(name);
	}

	public MapProperty<Skill, SkillLevel> skillLevelsProperty() {
		if (skillLevelsProperty == null) {
			ObservableMap<Skill, SkillLevel> observableHashMap = FXCollections
					.observableHashMap();
			skillLevelsProperty = new SimpleMapProperty<>(this, "skillLevels",
					observableHashMap);
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
	 * @param skillLevels
	 *            new value of skillLevels
	 */
	public void setSkillLevels(ObservableMap<Skill, SkillLevel> skillLevels) {
		this.skillLevelsProperty().setValue(skillLevels);
	}
}
