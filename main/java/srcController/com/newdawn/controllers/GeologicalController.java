package com.newdawn.controllers;

import com.newdawn.model.mineral.MineralDeposit;
import com.newdawn.model.mineral.MinerallyExploitableBody;
import com.newdawn.model.mineral.MinerallyExploitableBodyModel;
import com.newdawn.model.personnel.GeologicalTeam;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
@Component
public class GeologicalController {

    @Autowired
    private Config config;

    public void putGeologicalTeamOnMinerallyExploitableBody(MinerallyExploitableBody body, GeologicalTeam team) {
        if (team.getBodyProspected() != null) {
            MinerallyExploitableBodyModel oldModel = team.getBodyProspected().
                    getMinerallyExploitableBodyModel();
            oldModel.setGeologicalTeam(null);
        }
        if (body != null) {
            MinerallyExploitableBodyModel newModel = body.
                    getMinerallyExploitableBodyModel();
            newModel.setGeologicalTeam(team);
        }
        team.setBodyProspected(body);
    }

    public void runProspection(GeologicalTeam team, int increment) {
        MinerallyExploitableBodyModel bodyModel = team.getBodyProspected().
                getMinerallyExploitableBodyModel();
        if (bodyModel.isFinalized()) {
            //TODO EVENT
            return;
        }

        int newCounter = team.getInternalCounter() + increment;
        if (newCounter >= config.getMaxValueForTeamInternalTimeCounter()) {
            team.setInternalCounter(0);
            long teamCumulatedSkill = team.getCumulatedSkillLevel();
            if (!bodyModel.isInitialDiscovered()) {
                long newInitialDiscoveryPoints = bodyModel.
                        getInitialDiscoveryPoints() + teamCumulatedSkill;
                if (newInitialDiscoveryPoints >= bodyModel.
                        getPointsMaxForInitialDiscovery()) {
                    bodyModel.discoverInitialQuantities();
                    //TODO EVENT
                } else {
                    bodyModel.setInitialDiscoveryPoints(newInitialDiscoveryPoints);
                }

            } else if (bodyModel.getFinalisationSkill() <= teamCumulatedSkill) {
                long newFinalisationPoints = bodyModel.getFinalizationPoints() + teamCumulatedSkill - bodyModel.
                        getFinalisationSkill();
                if (newFinalisationPoints >= bodyModel.
                        getPointNeededForFinalization()) {
                    //TODO EVENT
                    bodyModel.finalizeProspection();
                } else {
                    bodyModel.setFinalizationPoints(newFinalisationPoints);
                }
            } else {
                List<MineralDeposit> mostAccessiblesDeposit = bodyModel.
                        getMostAccessiblesDeposit();
                assert mostAccessiblesDeposit.size() > 0;

                MineralDeposit inspectedDeposit = null;
                //we get the mineral Deposit already inspected;
                for (MineralDeposit mineralDeposit : mostAccessiblesDeposit) {
                    if (inspectedDeposit == null || mineralDeposit.
                            getDiscoveryPoints() > inspectedDeposit.
                            getDiscoveryPoints()) {
                        inspectedDeposit = mineralDeposit;
                    }
                }
                //If the team has a skill level too low to make a prospection
                if (inspectedDeposit.getSkillLevelToDiscover() >= teamCumulatedSkill) {
                    //TODO EVENT
                } else {
                    long newDiscoveryPoints = inspectedDeposit.
                            getDiscoveryPoints() + teamCumulatedSkill - inspectedDeposit.
                            getSkillLevelToDiscover();
                    if (newDiscoveryPoints >= config.
                            getPointsToDiscoverGeologicalDeposit()) {
                        inspectedDeposit.getMineralModel().discoverDeposit(inspectedDeposit);
                    } else {
                        inspectedDeposit.setDiscoveryPoints(newDiscoveryPoints);
                    }
                }

            }
        } else {
            team.setInternalCounter(newCounter);
        }
    }
}
