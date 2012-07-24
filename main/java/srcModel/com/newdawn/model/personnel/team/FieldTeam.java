package com.newdawn.model.personnel.team;

import com.newdawn.model.personnel.PersonnelLocalisation;
import com.newdawn.model.personnel.PersonnelMember;
import com.newdawn.model.personnel.Skill;
import com.newdawn.model.personnel.Team;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.springframework.beans.BeansException;
import viewerfx.ViewerFX;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public abstract class FieldTeam extends Team {

    private int internalCounter = 0;
    private StringProperty nameProperty;
    private ObjectProperty<PersonnelLocalisation> localizationProperty;

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

    public ObjectProperty<PersonnelLocalisation> localizationProperty() {
        if (localizationProperty == null) {
            localizationProperty = new SimpleObjectProperty<>(this, "localization");
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
     * @param localization new value of localization
     */
    public void setLocalization(PersonnelLocalisation localization) {
        this.localizationProperty().setValue(localization);
    }

    private int calculateMaxSize() {
        Skill leadershipSkill = ViewerFX.getCurrentApplication().
                getSprintContainer().getBean("leadership", Skill.class);
        int leaderLeadershipLevel = getLeader().getSkillLevels().get(leadershipSkill).
                getLevel();
        int additionalTeamMemberSize = (int) (leaderLeadershipLevel / 12.5);
        return 2 + additionalTeamMemberSize;
    }

    @Override
    protected String[] validateAddition(PersonnelMember teamMember) {
        int maxSize = calculateMaxSize();
        if (getTeamMembers().size() >= maxSize) {
            return new String[]{"Max size reached"};
        }
        return null;
    }

    public int getInternalCounter() {
        return internalCounter;
    }

    public void setInternalCounter(int internalCounter) {
        this.internalCounter = internalCounter;
    }

    public long getCumulatedSkillLevel() {
        Skill teamSkill = getTeamSkill();
        Skill leadershipSkill = ViewerFX.getCurrentApplication().
                getSprintContainer().getBean("leadership", Skill.class);
        double leaderLeadershipSkillLevel = getLeader().getSkillLevels().get(leadershipSkill).
                getLevel();
        double leaderTeamSkillLevel = getLeader().getSkillLevels().get(teamSkill).
                getLevel();
        double cumulatedSkillLevel = 0.0;
        for (PersonnelMember teamMember : getTeamMembers()) {
            cumulatedSkillLevel += teamMember.getSkillLevels().get(teamSkill).
                    getLevel();
        }
        cumulatedSkillLevel += ((leaderTeamSkillLevel / 100.0) * leaderLeadershipSkillLevel) * getTeamMembers().
                size();
        cumulatedSkillLevel += leaderTeamSkillLevel / getTeamMembers().size();
        return Math.round(cumulatedSkillLevel);

    }

    protected abstract Skill getTeamSkill() throws BeansException;
}