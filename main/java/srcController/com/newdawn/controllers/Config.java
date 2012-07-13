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
    private String initDefaultDeltaValue = "π";
    //TODO remove the table storage
    private int[] popRepartition = {5, 75, 20};

    /**
     * This field store the period of the population growth.
     */
    private int populationGrowPeriod = 24*3600*30;
    /**
     * This field store the multiplicator to apply to the colonies' grow rate for getting the annual grow rate
     * //TODO try to link this field to populationGrowPeriod
     */
    private int populationGrowRateAnnualMultiplicator = 12;
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

    public int getPopulationGrowPeriod() {
        return populationGrowPeriod;
    }

    public int getPopulationGrowRateAnnualMultiplicator() {
        return populationGrowRateAnnualMultiplicator;
    }
    
    
}
