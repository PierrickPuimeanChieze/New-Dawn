package com.newdawn.model.mineral;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 
 * @author Pierrick Puimean-Chieze
 */
public class Mineral {

	private StringProperty nameProperty;

	public StringProperty nameProperty() {
		if (nameProperty == null) {
			nameProperty = new SimpleStringProperty(this, "name");
		}
		return nameProperty;
	}

	/**
	 * Get the value of name
	 * 
	 * @return the value of name
	 */
	public String getName() {
		return nameProperty().getValue();
	}

	/**
	 * Set the value of name
	 * 
	 * @param name
	 *            new value of name
	 */
	public void setName(String name) {
		this.nameProperty().setValue(name);
	}
}
