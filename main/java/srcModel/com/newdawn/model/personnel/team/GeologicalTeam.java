package com.newdawn.model.personnel.team;

import com.newdawn.model.mineral.MinerallyExploitableBody;
import com.newdawn.model.personnel.Skill;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.springframework.beans.BeansException;
import viewerfx.ViewerFX;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class GeologicalTeam extends FieldTeam {

    private Skill teamSkill;
    private ObjectProperty<MinerallyExploitableBody> bodyProspectedProperty;

    public GeologicalTeam() {
        teamSkill = ViewerFX.getCurrentApplication().
                getSprintContainer().getBean("geology", Skill.class);
    }

    public ObjectProperty<MinerallyExploitableBody> bodyProspectedProperty() {
        if (bodyProspectedProperty == null) {
            bodyProspectedProperty = new SimpleObjectProperty<>(this, "bodyProspected");
        }
        return bodyProspectedProperty;
    }

    /**
     * Get the value of bodyProspected
     *
     * @return the value of bodyProspected
     */
    public MinerallyExploitableBody getBodyProspected() {
        return bodyProspectedProperty().getValue();
    }

    /**
     * Set the value of bodyProspected
     *
     * @param bodyProspected new value of bodyProspected
     */
    public void setBodyProspected(MinerallyExploitableBody bodyProspected) {
        this.bodyProspectedProperty().setValue(bodyProspected);
    }

    @Override
    protected Skill getTeamSkill() throws BeansException {
        return teamSkill;

    }
}
