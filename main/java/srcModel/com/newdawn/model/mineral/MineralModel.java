package com.newdawn.model.mineral;

import java.util.List;
import static com.newdawn.model.mineral.MineralDeposit.MineralDepositStatus;
import com.sun.javafx.collections.transformation.SortedList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * 
 * @author Pierrick Puimean-Chieze
 */
public class MineralModel {

	private final Mineral mineral;
	private long initialQuantity;
	private boolean initialDiscovered = false;
	private boolean finalized = false;
	private ObservableList<MineralDeposit> undiscoveredDeposits = FXCollections
			.observableArrayList();
	private ObservableList<MineralDeposit> discoveredDeposits = FXCollections
			.observableArrayList();
	private LongProperty extractedProperty;
	private LongProperty discoveredProperty;
	private LongProperty remainingProperty;

	public MineralModel(Mineral mineral, long initialQuantity,
			List<MineralDeposit> deposits) {
		this.mineral = mineral;
		this.initialQuantity = initialQuantity;
		for (MineralDeposit mineralDeposit : deposits) {
			assert mineralDeposit.getStatus() == MineralDepositStatus.UNKNOWN : "Not all of the deposit of a newly created MineralModel are UNKNOWN";
			assert mineralDeposit.getMineralModel() == null;
			mineralDeposit.setMineralModel(this);
		}

		this.undiscoveredDeposits.addAll(deposits);
		Collections.sort(undiscoveredDeposits,
				new Comparator<MineralDeposit>() {
					@Override
					public int compare(MineralDeposit arg0, MineralDeposit arg1) {
						return arg0.getSkillLevelToDiscover().compareTo(
								arg1.getSkillLevelToDiscover());
					}
				});
	}

	public Mineral getMineral() {
		return mineral;
	}

	public LongProperty remainingProperty() {
		if (remainingProperty == null) {
			remainingProperty = new SimpleLongProperty(this, "remaining", 0);
			remainingProperty.bind(discoveredProperty().subtract(
					extractedProperty()));
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
	 * @param discovered
	 *            new value of discovered
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
	 * @param extracted
	 *            new value of extracted
	 */
	private void setExtracted(long extracted) {
		this.extractedProperty().setValue(extracted);
	}

	public List<MineralDeposit> getDiscoveredDeposits() {
		return undiscoveredDeposits;
	}

	public List<MineralDeposit> getUnknownDeposits() {
		return discoveredDeposits;
	}

	public long getInitialQuantity() {
		return initialQuantity;
	}

	public boolean isFinalized() {
		return finalized;
	}

	// TODO try to use a binding
	private void updateTotalDiscoveredQuantity() {
		if (initialDiscovered) {
			long total = initialQuantity;
			for (MineralDeposit level : getDiscoveredDeposits()) {
				total += level.getQuantity();
			}
			setDiscovered(total);
		} else {
			setDiscovered(0);
		}
	}

	public void addExtracted(long extractedQuantity) {
		setExtracted(getExtracted() + extractedQuantity);
	}

	public void discoverInitial() {
		initialDiscovered = true;
		updateTotalDiscoveredQuantity();
	}

	public void discoverDeposit(MineralDeposit mineralDeposit) {
		assert initialDiscovered;
		assert mineralDeposit.getStatus() == MineralDepositStatus.UNKNOWN;
		// Normally, we can't discover another deposit than the first available
		assert getUnknownDeposits().indexOf(mineralDeposit) == 0;
		mineralDeposit.setStatus(MineralDepositStatus.DISCOVERED);
		getUnknownDeposits().remove(mineralDeposit);
		getDiscoveredDeposits().add(mineralDeposit);
		updateTotalDiscoveredQuantity();
	}

	void finalizeProspection() {
		finalized = true;
	}
}
