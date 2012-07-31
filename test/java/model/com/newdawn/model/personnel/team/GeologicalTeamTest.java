package com.newdawn.model.personnel.team;

import com.newdawn.controllers.OfficialsController;
import com.newdawn.model.personnel.NavalOfficer;
import com.newdawn.model.personnel.Skill;
import com.newdawn.model.personnel.SkillLevel;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import viewerfx.ViewerFX;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class GeologicalTeamTest {

    private static ApplicationContext context;
    private static OfficialsController officialsController;
    private static Skill geologySkill;
    private static Skill leadershipSkill;

    @BeforeClass
    public static void initialize() throws Exception {
        final ViewerFX viewerFX = new viewerfx.ViewerFX();
        viewerFX.init();
        context = viewerFX.getSprintContainer();
        officialsController = context.getBean(OfficialsController.class);
        geologySkill = context.getBean("geology", Skill.class);
        leadershipSkill = context.getBean("leadership", Skill.class);
    }

    @Test
    public void testSettingLeaderAlone() {
        GeologicalTeam geologicalTeam = new GeologicalTeam();
        final NavalOfficer testLeader = officialsController.
                createNewNavalOfficer("Test Naval Officer", null);
        testLeader.skillLevelsProperty().get(geologySkill).setLevel(50);
        geologicalTeam.setLeader(testLeader);
        assertThat(geologicalTeam.cumulatedSkillLevelProperty().getValue(), is(equalTo(50L)));
    }

    @Test
    public void testModifyingLeaderAlone() {
        GeologicalTeam geologicalTeam = new GeologicalTeam();
        final NavalOfficer testLeader = officialsController.
                createNewNavalOfficer("Test Naval Officer", null);
        final SkillLevel geologySkillLevel = testLeader.skillLevelsProperty().
                get(geologySkill);
        geologySkillLevel.setLevel(50);
        geologicalTeam.setLeader(testLeader);
        assertThat(geologicalTeam.cumulatedSkillLevelProperty().getValue(), is(equalTo(50L)));
        geologySkillLevel.setLevel(75);
        assertThat(geologicalTeam.cumulatedSkillLevelProperty().getValue(), is(equalTo(75L)));
    }

    @Test
    public void testModifyingLeader() {
        GeologicalTeam geologicalTeam = new GeologicalTeam();
        final NavalOfficer testLeader = officialsController.
                createNewNavalOfficer("Test Naval Officer Leader", null);
        final NavalOfficer testMember = officialsController.
                createNewNavalOfficer("Test Naval Officer Member", null);
        final SkillLevel leaderGeoSkillLevel = testLeader.skillLevelsProperty().
                get(geologySkill);
        final SkillLevel leaderLeadSkillLevel = testLeader.skillLevelsProperty().
                get(leadershipSkill);
        final SkillLevel memberGeoSkillLevel = testMember.skillLevelsProperty().
                get(geologySkill);
        leaderGeoSkillLevel.setLevel(40);
        leaderLeadSkillLevel.setLevel(50);
        memberGeoSkillLevel.setLevel(50);
        geologicalTeam.setLeader(testLeader);
        geologicalTeam.addTeamMember(testMember);
        assertThat(geologicalTeam.cumulatedSkillLevelProperty().getValue(), is(equalTo(90L)));

        leaderLeadSkillLevel.setLevel(20);
        assertThat(geologicalTeam.cumulatedSkillLevelProperty().getValue(), is(equalTo(78L)));
        leaderGeoSkillLevel.setLevel(20);
        assertThat(geologicalTeam.cumulatedSkillLevelProperty().getValue(), is(equalTo(64L)));
    }

    @Test
    public void testModifyingMember() {
        GeologicalTeam geologicalTeam = new GeologicalTeam();
        final NavalOfficer testLeader = officialsController.
                createNewNavalOfficer("Test Naval Officer Leader", null);
        final NavalOfficer testMember = officialsController.
                createNewNavalOfficer("Test Naval Officer Member", null);
        final SkillLevel leaderGeoSkillLevel = testLeader.skillLevelsProperty().
                get(geologySkill);
        final SkillLevel leaderLeadSkillLevel = testLeader.skillLevelsProperty().
                get(leadershipSkill);
        final SkillLevel memberGeoSkillLevel = testMember.skillLevelsProperty().
                get(geologySkill);
        leaderGeoSkillLevel.setLevel(40);
        leaderLeadSkillLevel.setLevel(50);
        memberGeoSkillLevel.setLevel(50);
        geologicalTeam.setLeader(testLeader);
        geologicalTeam.addTeamMember(testMember);
        assertThat(geologicalTeam.cumulatedSkillLevelProperty().getValue(), is(equalTo(90L)));

        memberGeoSkillLevel.setLevel(60);
        assertThat(geologicalTeam.cumulatedSkillLevelProperty().getValue(), is(equalTo(100L)));
    }

    @Test
    public void testAddingMember() {
        GeologicalTeam geologicalTeam = new GeologicalTeam();
        final NavalOfficer testLeader = officialsController.
                createNewNavalOfficer("Test Naval Officer Leader", null);
        final NavalOfficer testMember = officialsController.
                createNewNavalOfficer("Test Naval Officer Member", null);
        final SkillLevel leaderGeoSkillLevel = testLeader.skillLevelsProperty().
                get(geologySkill);
        final SkillLevel leaderLeadSkillLevel = testLeader.skillLevelsProperty().
                get(leadershipSkill);
        final SkillLevel memberGeoSkillLevel = testMember.skillLevelsProperty().
                get(geologySkill);
        leaderGeoSkillLevel.setLevel(30);
        leaderLeadSkillLevel.setLevel(50);
        memberGeoSkillLevel.setLevel(50);
        geologicalTeam.setLeader(testLeader);
        geologicalTeam.addTeamMember(testMember);
        assertThat(geologicalTeam.cumulatedSkillLevelProperty().getValue(), is(equalTo(80L)));

        final NavalOfficer newMember = officialsController.
                createNewNavalOfficer("Test Naval Officer New Member", null);
        final SkillLevel newMemberGeoSkillLevel = newMember.
                skillLevelsProperty().
                get(geologySkill);

        newMemberGeoSkillLevel.setLevel(60);
        geologicalTeam.addTeamMember(newMember);
        assertThat(geologicalTeam.cumulatedSkillLevelProperty().getValue(), is(equalTo(150L)));
    }

    @Test
    public void testRemovingMember() {
        GeologicalTeam geologicalTeam = new GeologicalTeam();
        final NavalOfficer testLeader = officialsController.
                createNewNavalOfficer("Test Naval Officer Leader", null);
        final NavalOfficer testMember = officialsController.
                createNewNavalOfficer("Test Naval Officer Member", null);
        final SkillLevel leaderGeoSkillLevel = testLeader.skillLevelsProperty().
                get(geologySkill);
        final SkillLevel leaderLeadSkillLevel = testLeader.skillLevelsProperty().
                get(leadershipSkill);
        final SkillLevel memberGeoSkillLevel = testMember.skillLevelsProperty().
                get(geologySkill);
        final NavalOfficer secondMember = officialsController.
                createNewNavalOfficer("Test Naval Officer Member 2", null);
        final SkillLevel secondMemberGeoSkillLevel = secondMember.
                skillLevelsProperty().
                get(geologySkill);

        leaderGeoSkillLevel.setLevel(30);
        leaderLeadSkillLevel.setLevel(50);
        memberGeoSkillLevel.setLevel(50);
        secondMemberGeoSkillLevel.setLevel(60);
        geologicalTeam.setLeader(testLeader);
        geologicalTeam.addTeamMember(testMember);
        geologicalTeam.addTeamMember(secondMember);

        assertThat(geologicalTeam.cumulatedSkillLevelProperty().getValue(), is(equalTo(150L)));
        geologicalTeam.removeTeamMember(secondMember);
        assertThat(geologicalTeam.cumulatedSkillLevelProperty().getValue(), is(equalTo(80L)));
    }

    @Test
    public void testPromotingLeader() {
        GeologicalTeam geologicalTeam = new GeologicalTeam();
        final NavalOfficer testLeader = officialsController.
                createNewNavalOfficer("Test Naval Officer Leader", null);
        final NavalOfficer testMember = officialsController.
                createNewNavalOfficer("Test Naval Officer Member", null);
        final SkillLevel leaderGeoSkillLevel = testLeader.skillLevelsProperty().
                get(geologySkill);
        final SkillLevel leaderLeadSkillLevel = testLeader.skillLevelsProperty().
                get(leadershipSkill);
        final SkillLevel memberGeoSkillLevel = testMember.skillLevelsProperty().
                get(geologySkill);
        final NavalOfficer secondMember = officialsController.
                createNewNavalOfficer("Test Naval Officer Member 2", null);
        final SkillLevel secondMemberGeoSkillLevel = secondMember.
                skillLevelsProperty().
                get(geologySkill);
        final SkillLevel secondMemberLeadSkillLevel = secondMember.
                skillLevelsProperty().
                get(leadershipSkill);

        leaderGeoSkillLevel.setLevel(30);
        leaderLeadSkillLevel.setLevel(50);
        memberGeoSkillLevel.setLevel(50);
        secondMemberGeoSkillLevel.setLevel(60);
        secondMemberLeadSkillLevel.setLevel(10);
        geologicalTeam.setLeader(testLeader);
        geologicalTeam.addTeamMember(testMember);
        geologicalTeam.addTeamMember(secondMember);

        assertThat(geologicalTeam.cumulatedSkillLevelProperty().getValue(), is(equalTo(150L)));
        assertThat(geologicalTeam.promotingTeamMemberToLeader(secondMember), is(true));
        assertThat(geologicalTeam.cumulatedSkillLevelProperty().getValue(), is(equalTo(112L)));
    }
}
