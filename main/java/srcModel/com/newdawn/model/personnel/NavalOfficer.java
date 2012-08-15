package com.newdawn.model.personnel;

import com.newdawn.model.personnel.ranks.NavalRank;
import com.newdawn.model.personnel.Official;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 
 * @author Pierrick Puimean-Chieze
 */
public class NavalOfficer extends Official {

	public void setRank(NavalRank navalRank) {
		rankProperty.setValue(navalRank);
	}
}
