package com.newdawn.model.mineral;

import javafx.beans.property.*;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class MineralDeposit {

    public static enum MineralDepositStatus {

        UNKNOWN, DISCOVERED;
    }
    private MineralModel mineralModel;
    private int skillLevelToDiscover;
    private LongProperty discoveryPointsProperty;
    private long quantity;
    private ObjectProperty<MineralDepositStatus> statusProperty;

    public MineralDeposit(int skillLevelToDiscover, int quantity) {
        this.skillLevelToDiscover = skillLevelToDiscover;
        this.quantity = quantity;
    }

    
    public ObjectProperty<MineralDepositStatus> statusProperty() {
        if (statusProperty == null) {
            statusProperty = new SimpleObjectProperty<>(this, "status");
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

    public Integer getSkillLevelToDiscover() {
        return skillLevelToDiscover;
    }

    public MineralModel getMineralModel() {
        return mineralModel;
    }

    void setMineralModel(MineralModel mineralModel) {
        this.mineralModel = mineralModel;
    }

    
    
    public LongProperty discoveryPointsProperty() {
        if (discoveryPointsProperty == null) {
            discoveryPointsProperty = new SimpleLongProperty(this, "discoveryPoints");
        }
        return discoveryPointsProperty;
    }

    /**
     * Get the value of discoveryPoints
     *
     * @return the value of discoveryPoints
     */
    public long getDiscoveryPoints() {
        return discoveryPointsProperty().getValue();
    }

    /**
     * Set the value of discoveryPoints
     *
     * @param discoveryPoints new value of discoveryPoints
     */
    public void setDiscoveryPoints(long discoveryPoints) {
        this.discoveryPointsProperty().setValue(discoveryPoints);
    }
}
