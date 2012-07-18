package com.newdawn.model.personnel;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class Skill {

    private StringProperty nameProperty = new SimpleStringProperty(this, "name");

    public Skill(String name) {
        nameProperty.set(name);
    }

    public ReadOnlyStringProperty nameProperty() {
        return nameProperty;
    }

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return nameProperty.getValue();
    }
}
