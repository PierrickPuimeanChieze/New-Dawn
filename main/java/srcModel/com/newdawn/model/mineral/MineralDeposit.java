package com.newdawn.model.mineral;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class MineralDeposit {

    public static enum MineralDepositStatus {

        UNKNOWN, EXPECTED, DISCOVERED;
    }

    public MineralDeposit(int skillLevelToDiscover, int skillLevelToExpect, int quantity) {
        this.skillLevelToDiscover = skillLevelToDiscover;
        this.skillLevelToExpect = skillLevelToExpect;
        this.quantity = quantity;
    }
    
    
    private int skillLevelToDiscover;
    private int skillLevelToExpect;
    private long quantity;

    private ObjectProperty<MineralDepositStatus> statusProperty;

    public ObjectProperty<MineralDepositStatus> statusProperty() {
        if (statusProperty == null) {
            statusProperty = new SimpleObjectProperty<MineralDepositStatus>(this, "status");
        }
        return statusProperty;
    }

    /**
     * Get the value of status
     *
     * @return the value of status
     */
    public MineralDepositStatus getStatus() {
        return statusProperty().getValue();
    }

    /**
     * Set the value of status
     *
     * @param status new value of status
     */
    public void setStatus(MineralDepositStatus status) {
        this.statusProperty.setValue(status);
    }


    public long getQuantity() {
        return quantity;
    }

    public int getSkillLevelToDiscover() {
        return skillLevelToDiscover;
    }

    public int getSkillLevelToExpect() {
        return skillLevelToExpect;
    }

    
    
    
}
