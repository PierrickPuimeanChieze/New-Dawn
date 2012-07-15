package com.newdawn.model.boundedproperties;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public interface BoundedProperty<T extends Number> {

    public T getMinValue();

    public T getMaxValue();
}
