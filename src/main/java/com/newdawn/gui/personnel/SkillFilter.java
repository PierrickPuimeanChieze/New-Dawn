package com.newdawn.gui.personnel;

import com.newdawn.model.personnel.Official;
import com.newdawn.model.personnel.Skill;
import com.newdawn.model.personnel.SkillLevel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.function.Predicate;

/**
 * This predicate is used to test an official on his skills (presence, min value
 * and max value)
 * 
 * @author Pierrick Puimean-Chieze
 */
public class SkillFilter implements Predicate<Official> {

	private ObjectProperty<Skill> skillProperty;
	private ObjectProperty<Integer> minValueProperty;
	private ObjectProperty<Integer> maxValueProperty;

	public ObjectProperty<Integer> maxValueProperty() {
		if (maxValueProperty == null) {
			maxValueProperty = new SimpleObjectProperty<Integer>(this,
					"maxValue", null);
		}
		return maxValueProperty;
	}

	/**
	 * Get the value of maxValue
	 * 
	 * @return the value of maxValue
	 */
	public Integer getMaxValue() {
		return maxValueProperty().getValue();
	}

	/**
	 * Set the value of maxValue
	 * 
	 * @param maxValue
	 *            new value of maxValue
	 */
	public void setMaxValue(Integer maxValue) {
		this.maxValueProperty().setValue(maxValue);
	}

	public ObjectProperty<Integer> minValueProperty() {
		if (minValueProperty == null) {
			minValueProperty = new SimpleObjectProperty<Integer>(this,
					"minValue", null);
		}
		return minValueProperty;
	}

	/**
	 * Get the value of minValue
	 * 
	 * @return the value of minValue
	 */
	public Integer getMinValue() {
		return minValueProperty().getValue();
	}

	/**
	 * Set the value of minValue
	 * 
	 * @param minValue
	 *            new value of minValue
	 */
	public void setMinValue(Integer minValue) {
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
		// We checked the skill against each limit of the filter.
		// If One limit is null, the test for this limit return true, whatever
		// the value of the skill
		boolean minValueChecked = getMinValue() == null
				|| level >= getMinValue();
		boolean maxValueChecked = getMaxValue() == null
				|| level <= getMaxValue();

		return minValueChecked && maxValueChecked;
	}
}
