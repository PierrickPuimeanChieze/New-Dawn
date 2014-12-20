package com.newdawn.model.personnel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;

/**
 * 
 * @author Pierrick Puimean-Chieze
 */
public interface PersonnelAssignment {
	public ReadOnlyStringProperty visualNameProperty();
	public ObjectProperty<PersonnelLocalisation> localizationProperty();
}
