package com.sun.javafx.collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.function.Predicate;

/**
 * This class combine multiple predicate into one, with an "and" operation. The
 * difference between this and the result of a call to the
 * {@link Predicate#and(Predicate)} method is that the list of predicate in this
 * class is dynamic and can be changed between two call to {@link #test(Object)}
 * 
 * @author Pierrick Puimean-Chieze
 */
public class DynamicAndPredicate<E> implements Predicate<E> {

	private ObservableList<Predicate<E>> matchers = FXCollections
			.observableArrayList();

	/**
	 * Get the value of matchers
	 * 
	 * @return the value of matchers
	 */
	public ObservableList<Predicate<E>> getMatchers() {
		return matchers;
	}

	@Override
	public boolean test(E e) {
		for (Predicate matcher : matchers) {
			if (!matcher.test(e)) {
				return false;
			}
		}
		return true;
	}
}
