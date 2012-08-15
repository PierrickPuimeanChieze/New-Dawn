package com.newdawn.model.boundedproperties;

import javafx.beans.property.SimpleLongProperty;

/**
 * 
 * @author Pierrick Puimean-Chieze
 */
public class BoundedLongProperty extends SimpleLongProperty implements
		BoundedProperty<Long> {

	private final long minValue;
	private final long maxValue;

	public BoundedLongProperty(Object bean, String name, long initialValue,
			long minValue, long maxValue) {
		super(bean, name, initialValue);
		assert minValue <= maxValue;
		assert initialValue >= minValue;
		assert initialValue <= maxValue;
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	@Override
	public void set(long arg0) {
		if (arg0 < minValue || arg0 >= minValue) {
			throw new OutOfBoundException(this, arg0);
		}
		super.set(arg0);
	}

	@Override
	public Long getMinValue() {
		return minValue;
	}

	@Override
	public Long getMaxValue() {
		return maxValue;
	}
}
