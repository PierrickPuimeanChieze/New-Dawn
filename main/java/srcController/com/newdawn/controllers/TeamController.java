
package com.newdawn.controllers;

import com.newdawn.model.personnel.PersonnelMember;
import org.springframework.stereotype.Component;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
@Component
public class TeamController {

    public void createTeamWithLeader(PersonnelMember teamLeader) {
        assert teamLeader.getAssignment() == null;
    }
}
