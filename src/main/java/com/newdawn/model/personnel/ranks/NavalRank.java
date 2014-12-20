package com.newdawn.model.personnel.ranks;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 
 * @author Pierrick Puimean-Chieze
 */
public enum NavalRank implements Rank {
	// TODO internationalize

	A0("Lieutenant Commander"), A1("Commander"), A2("Captain"), A3(
			"Rear admiral (Green)"), A4("Rear admiral (Red)"), A5(
			"Vice Admiral"), A6("Admiral"), A7("Fleet Admiral");
	private StringProperty designationProperty = new SimpleStringProperty(
			"designation");

	private NavalRank(String designation) {
		this.designationProperty.set(designation);
	}

	@Override
	public String getDesignation() {
		return designationProperty.get();
	}

	public ReadOnlyStringProperty designationProperty() {
		return designationProperty;
	}
}
