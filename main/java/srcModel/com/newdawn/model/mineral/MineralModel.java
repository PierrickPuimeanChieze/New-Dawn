package com.newdawn.model.mineral;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.newdawn.model.mineral.MineralDeposit.MineralDepositStatus;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class MineralModel {

    private Map<MineralDepositStatus, List<MineralDeposit>> levelByStatus = new HashMap<>();
    private LongProperty extractedProperty;
    private LongProperty discoveredProperty;
    private LongProperty remainingProperty;

    
    public LongProperty remainingProperty() {
        if (remainingProperty == null) {
            remainingProperty = new SimpleLongProperty(this, "remaining", 0);
            remainingProperty.bind(discoveredProperty().subtract(extractedProperty()));
        }
        return remainingProperty;
    }

    /**
     * Get the value of remaining
     *
     * @return the value of remaining
     */
    public long getRemaining() {
        return remainingProperty().getValue();
    }

    /**
     * Set the value of remaining
     *
     * @param remaining new value of remaining
     */
    private void setRemaining(long remaining) {
        this.remainingProperty().setValue(remaining);
    }


    public LongProperty discoveredProperty() {
        if (discoveredProperty == null) {
            discoveredProperty = new SimpleLongProperty(this, "discovered", 0);
        }
        return discoveredProperty;
    }

    /**
     * Get the value of discovered
     *
     * @return the value of discovered
     */
    public long getDiscovered() {
        return discoveredProperty().getValue();
    }

    /**
     * Set the value of discovered
     *
     * @param discovered new value of discovered
     */
    public void setDiscovered(long discovered) {
        this.discoveredProperty().setValue(discovered);
    }


    public LongProperty extractedProperty() {
        if (extractedProperty == null) {
            extractedProperty = new SimpleLongProperty(this, "extracted", 0);
        }
        return extractedProperty;
    }

    
    /**
     * Get the value of extracted
     *
     * @return the value of extracted
     */
    public long getExtracted() {
        return extractedProperty().getValue();
    }

    /**
     * Set the value of extracted
     *
     * @param extracted new value of extracted
     */
    private void setExtracted(long extracted) {
        this.extractedProperty().setValue(extracted);
    }


    public List<MineralDeposit> getDiscoveredLevels() {
        return levelByStatus.get(MineralDepositStatus.DISCOVERED);
    }

    public List<MineralDeposit> getExpectedLevels() {
        return levelByStatus.get(MineralDepositStatus.EXPECTED);
    }
 
    public List<MineralDeposit> getUnknownLevels() {
        return levelByStatus.get(MineralDepositStatus.UNKNOWN);
    }

    private void updateTotalDiscoveredQuantity() {
        long total = 0;
        for (MineralDeposit level : getDiscoveredLevels()) {
            total += level.getQuantity();
        }
        setDiscovered(total);
    }
    

    public void addExtracted(long extractedQuantity) {
        setExtracted(getExtracted()+extractedQuantity);
    }

    public void discoverDeposit(MineralDeposit mineralDeposit) {
        assert mineralDeposit.getStatus() == MineralDepositStatus.EXPECTED;
        assert getExpectedLevels().contains(mineralDeposit);
        mineralDeposit.setStatus(MineralDepositStatus.DISCOVERED);
        getExpectedLevels().remove(mineralDeposit);
        getDiscoveredLevels().add(mineralDeposit);
        updateTotalDiscoveredQuantity();
    }

}
