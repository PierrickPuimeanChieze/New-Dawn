package com.newdawn.model.mineral;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class MineralDeposit {

    public static enum MineralDepositStatus {

        UNKNOWN, DISCOVERED;
    }
    private final Mineral mineral;
    private int skillLevelToDiscover;
    private IntegerProperty discoveryPointsProperty;


    private long quantity;
    private ObjectProperty<MineralDepositStatus> statusProperty;

    public MineralDeposit(Mineral mineral, int skillLevelToDiscover, int skillLevelToExpect, int quantity) {
        this.skillLevelToDiscover = skillLevelToDiscover;
        this.mineral = mineral;
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

    public Mineral getMineral() {
        return mineral;
    }
    
    public IntegerProperty discoveryPointsProperty() {
        if (discoveryPointsProperty == null) {
            discoveryPointsProperty = new SimpleIntegerProperty(this, "discoveryPoints");
        }
        return discoveryPointsProperty;
    }

    /**
     * Get the value of discoveryPoints
     *
     * @return the value of discoveryPoints
     */
    public int getDiscoveryPoints() {
        return discoveryPointsProperty().getValue();
    }

    /**
     * Set the value of discoveryPoints
     *
     * @param discoveryPoints new value of discoveryPoints
     */
    public void setDiscoveryPoints(int discoveryPoints) {
        this.discoveryPointsProperty().setValue(discoveryPoints);
    }
}
