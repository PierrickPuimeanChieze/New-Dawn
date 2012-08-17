package com.newdawn.model.personnel.team;

import com.newdawn.controllers.TeamController.FieldTeamType;
import com.newdawn.model.mineral.MinerallyExploitableBody;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * 
 * @author Pierrick Puimean-Chieze
 */
public class GeologicalTeam extends FieldTeam {

	private ObjectProperty<MinerallyExploitableBody> bodyProspectedProperty;

	public ObjectProperty<MinerallyExploitableBody> bodyProspectedProperty() {
		if (bodyProspectedProperty == null) {
			bodyProspectedProperty = new SimpleObjectProperty<>(this,
					"bodyProspected");
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
	 * @param bodyProspected
	 *            new value of bodyProspected
	 */
	public void setBodyProspected(MinerallyExploitableBody bodyProspected) {
		this.bodyProspectedProperty().setValue(bodyProspected);
	}

	@Override
	final public String getTeamSkillName() {
		return "geology";
	}

	public void setAssignement(
			MinerallyExploitableBody assignment) {
		super.setAssignment(assignment);
	}

	@Override
	public FieldTeamType getType() {
		return FieldTeamType.GEOLOGICAL;
	}
}
