package com.sun.javafx.collections;

import com.sun.javafx.collections.transformation.Matcher;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * 
 * @author Pierrick Puimean-Chieze
 */
public class CompositeMatcher<E> implements Matcher<E> {

	private ObservableList<Matcher<E>> matchers = FXCollections
			.observableArrayList();

	/**
	 * Get the value of matchers
	 * 
	 * @return the value of matchers
	 */
	public ObservableList<Matcher<E>> getMatchers() {
		return matchers;
	}

	@Override
	public boolean matches(E e) {
		for (Matcher matcher : matchers) {
			if (!matcher.matches(e)) {
				return false;
			}
		}
		return true;
	}
}
