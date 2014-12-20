package com.newdawn.model.personnel.ranks;

import javafx.beans.property.ReadOnlyStringProperty;

/**
 * 
 * @author Pierrick Puimean-Chieze
 */
public interface Rank {

	ReadOnlyStringProperty designationProperty();

	String getDesignation();
}
