package com.newdawn.gui.personnel;

import java.util.function.Predicate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import com.newdawn.model.personnel.Official;
import com.newdawn.model.personnel.Skill;
import com.newdawn.model.personnel.SkillLevel;

/**
 * 
 * @author Pierrick Puimean-Chieze
 */
public class SkillFilter implements Predicate<Official> {

	private ObjectProperty<Skill> skillProperty;
	private IntegerProperty minValueProperty;
	private IntegerProperty maxValueProperty;

	public IntegerProperty maxValueProperty() {
		if (maxValueProperty == null) {
			maxValueProperty = new SimpleIntegerProperty(this, "maxValue");
		}
		return maxValueProperty;
	}

	/**
	 * Get the value of maxValue
	 * 
	 * @return the value of maxValue
	 */
	public int getMaxValue() {
		return maxValueProperty().getValue();
	}

	/**
	 * Set the value of maxValue
	 * 
	 * @param maxValue
	 *            new value of maxValue
	 */
	public void setMaxValue(int maxValue) {
		this.maxValueProperty().setValue(maxValue);
	}

	public IntegerProperty minValueProperty() {
		if (minValueProperty == null) {
			minValueProperty = new SimpleIntegerProperty(this, "minValue");
		}
		return minValueProperty;
	}

	/**
	 * Get the value of minValue
	 * 
	 * @return the value of minValue
	 */
	public int getMinValue() {
		return minValueProperty().getValue();
	}

	/**
	 * Set the value of minValue
	 * 
	 * @param minValue
	 *            new value of minValue
	 */
	public void setMinValue(int minValue) {
		this.minValueProperty().setValue(minValue);
	}

	public ObjectProperty<Skill> skillProperty() {
		if (skillProperty == null) {
			skillProperty = new SimpleObjectProperty<>(this, "skill");
		}
		return skillProperty;
	}

	/**
	 * Get the value of skill
	 * 
	 * @return the value of skill
	 */
	public Skill getSkill() {
		return skillProperty().getValue();
	}

	/**
	 * Set the value of skill
	 * 
	 * @param skill
	 *            new value of skill
	 */
	public void setSkill(Skill skill) {
		this.skillProperty().setValue(skill);
	}

	@Override
	public boolean test(Official e) {
		final SkillLevel skillLevel = e.skillLevelsProperty().get(getSkill());
		final int level;
		if (skillLevel == null) {
			level = 0;
		} else {
			level = skillLevel.getLevel();
		}

		return level >= getMinValue() && level <= getMaxValue();
	}
}
