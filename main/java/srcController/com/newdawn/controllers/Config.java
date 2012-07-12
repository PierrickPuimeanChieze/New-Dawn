/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newdawn.controllers;

import org.springframework.stereotype.Component;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
@Component
public class Config {

    private int subPulse = 5;
    private String initDefaultDeltaValue;
    private int[] popRepartition = {5, 75, 20};

    /**
     * Get the value of initDefaultDeltaValue
     *
     * @return the value of initDefaultDeltaValue
     */
    public String getInitDefaultDeltaValue() {
        return initDefaultDeltaValue;
    }

    /**
     * Set the value of initDefaultDeltaValue
     *
     * @param initDefaultDeltaValue new value of initDefaultDeltaValue
     */
    public void setInitDefaultDeltaValue(String initDefaultDeltaValue) {
        this.initDefaultDeltaValue = initDefaultDeltaValue;
    }

    public int getSubPulse() {
        return subPulse;
    }

    public int getAgriculturePopulationPart() {
        return popRepartition[0];
    }

    public int getServicesPopulationPart() {
        return popRepartition[1];
    }

    public int getIndustriesPopulationPart() {
        return popRepartition[2];
    }
}
