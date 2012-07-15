package com.newdawn.model.personnel;

import org.springframework.beans.BeansException;
import viewerfx.ViewerFX;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class FieldTeam extends Team {

    private int calculateMaxSize() {
        Skill leadershipSkill = ViewerFX.getCurrentApplication().
                getSprintContainer().getBean("leadership", Skill.class);
        int leaderLeadershipLevel = getLeader().getSkillLevels().get(leadershipSkill).
                getLevel();
        int additionalTeamMemberSize = (int) (leaderLeadershipLevel / 12.5);
        return 2 + additionalTeamMemberSize;
    }

    public static enum Type {

        GEOLOGICAL("geology"), INTELLIGENCE("intelligence"), DIPLOMACY("diplomacy");
        private String skillBeanId;

        private Type(String skillBeanId) {
            this.skillBeanId = skillBeanId;
        }

        public String getSkillBeanId() {
            return skillBeanId;
        }
    }
    private Type type;

    public FieldTeam(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @Override
    protected String[] validateAddition(PersonnelMember teamMember) {
        int maxSize = calculateMaxSize();
        if (getTeamMembers().size() >= maxSize) {
            return new String[]{"Max size reached"};
        }
        return null;
    }

    public long getCumulatedSkillLevel() {
        Skill teamSkill = getTeamSkill();
        Skill leadershipSkill = ViewerFX.getCurrentApplication().
                getSprintContainer().getBean("leadership", Skill.class);
        double leaderLeadershipSkillLevel = getLeader().getSkillLevels().get(leadershipSkill).getLevel();
        double leaderTeamSkillLevel = getLeader().getSkillLevels().get(teamSkill).getLevel();
        double cumulatedSkillLevel = 0.0;
        for (PersonnelMember teamMember : getTeamMembers()) {
            cumulatedSkillLevel += teamMember.getSkillLevels().get(teamSkill).
                    getLevel();
        }
        cumulatedSkillLevel += ((leaderTeamSkillLevel/100.0)*leaderLeadershipSkillLevel)*getTeamMembers().size();
        cumulatedSkillLevel += leaderTeamSkillLevel/getTeamMembers().size();
        return  Math.round(cumulatedSkillLevel);

    }

    public Skill getTeamSkill() throws BeansException {
        return ViewerFX.getCurrentApplication().
                getSprintContainer().getBean(getType().getSkillBeanId(), Skill.class);
    }
}
