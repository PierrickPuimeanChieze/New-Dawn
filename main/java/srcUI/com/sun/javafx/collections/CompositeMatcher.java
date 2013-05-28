package com.sun.javafx.collections;

import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * 
 * @author Pierrick Puimean-Chieze
 */
public class CompositeMatcher<E> implements Predicate<E> {

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
