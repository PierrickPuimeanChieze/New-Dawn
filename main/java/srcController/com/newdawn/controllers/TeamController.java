package com.newdawn.controllers;

import com.newdawn.model.personnel.Official;
import com.newdawn.model.personnel.team.FieldTeam;
import com.newdawn.model.personnel.team.GeologicalTeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
@Component
public class TeamController {

    public static enum FieldTeamType {

        GEOLOGICAL
    }
    @Autowired
    private GameData gameData;

    public FieldTeam createTeamWithLeader(Official teamLeader, FieldTeamType fieldTeamType) {
        assert teamLeader.getAssignment() == null;
        FieldTeam toReturn;
        switch (fieldTeamType) {
            case GEOLOGICAL:
                toReturn = new GeologicalTeam();

                break;
            default:
                throw new AssertionError();
        }


        toReturn.setLeader(teamLeader);
        toReturn.
                setName(teamLeader.getName() + " ' " + fieldTeamType.toString().
                toLowerCase() + " team");
        toReturn.setLocalization(teamLeader.getLocalization());
        gameData.getGeologicalTeams().add((GeologicalTeam) toReturn);
        return toReturn;
    }
}
