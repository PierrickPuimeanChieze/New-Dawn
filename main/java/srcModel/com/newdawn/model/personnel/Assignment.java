package com.newdawn.model.personnel;

import javafx.beans.property.StringProperty;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public interface Assignment {

    public StringProperty nameProperty();

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName();

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName(String name);
}
