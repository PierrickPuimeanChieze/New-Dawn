package com.newdawn.model.personnel.ranks;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public enum ScientistRank implements Rank {

    A0("Scientist");
    private StringProperty designationProperty = new SimpleStringProperty("designation");

    private ScientistRank(String designation) {
        this.designationProperty.set(designation);
    }

    public String getDesignation() {
        return designationProperty.get();
    }

    public ReadOnlyStringProperty designationProperty() {
        return designationProperty;
    }
}
