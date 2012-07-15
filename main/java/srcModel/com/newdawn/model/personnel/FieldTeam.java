package com.newdawn.model.personnel;

import org.springframework.beans.BeansException;
import viewerfx.ViewerFX;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public abstract class FieldTeam extends Team {

    private int internalCounter = 0;

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
