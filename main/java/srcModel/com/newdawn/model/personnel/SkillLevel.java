package com.newdawn.model.personnel;

import javafx.beans.property.*;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class SkillLevel {

    private ObjectProperty<Skill> skillProperty = new SimpleObjectProperty<>(this, "skill");
    //TODO replace by a short property
    private IntegerProperty levelProperty;
    //TODO replace by a short property
    private IntegerProperty xpReserveProperty;

    public SkillLevel(Skill skill) {
        skillProperty.setValue(skill);
    }

    public Skill getSkill() {
        return skillProperty.get();
    }

    public ReadOnlyObjectProperty<Skill> skillProperty() {
        return skillProperty;
    }

    public IntegerProperty levelProperty() {
        if (levelProperty == null) {
            levelProperty = new SimpleIntegerProperty(this, "level", 0);
        }
        return levelProperty;
    }

    /**
     * Get the value of level
     *
     * @return the value of level
     */
    public int getLevel() {
        return levelProperty().getValue();
    }

    /**
     * Set the value of level
     *
     * @param level new value of level
     */
    public void setLevel(int level) {
        this.levelProperty().setValue(level);
    }

    public IntegerProperty xpReserveProperty() {
        if (xpReserveProperty == null) {
            xpReserveProperty = new SimpleIntegerProperty(this, "xpReserve");
        }
        return xpReserveProperty;
    }

    /**
     * Get the value of xpReserve
     *
     * @return the value of xpReserve
     */
    public int getXpReserve() {
        return xpReserveProperty().getValue();
    }

    /**
     * Set the value of xpReserve
     *
     * @param xpReserve new value of xpReserve
     */
    public void setXpReserve(int xpReserve) {
        this.xpReserveProperty().setValue(xpReserve);
    }
}
