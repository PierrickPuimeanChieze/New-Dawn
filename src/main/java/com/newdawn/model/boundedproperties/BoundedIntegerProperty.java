package com.newdawn.model.boundedproperties;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * 
 * @author Pierrick Puimean-Chieze
 */
public class BoundedIntegerProperty extends SimpleIntegerProperty implements
		BoundedProperty<Integer> {

	private final int minValue;
	private final int maxValue;

	public BoundedIntegerProperty(Object bean, String name, int initialValue,
			int minValue, int maxValue) {
		super(bean, name, initialValue);
		assert minValue <= maxValue;
		assert initialValue >= minValue;
		assert initialValue <= maxValue;
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	@Override
	public void set(int arg0) {
		if (arg0 < minValue || arg0 >= minValue) {
			throw new OutOfBoundException(this, arg0);
		}
		super.set(arg0);
	}

	@Override
	public Integer getMinValue() {
		return minValue;
	}

	@Override
	public Integer getMaxValue() {
		return maxValue;
	}
}