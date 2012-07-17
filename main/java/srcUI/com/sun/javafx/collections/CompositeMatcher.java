package com.sun.javafx.collections;

import com.sun.javafx.collections.transformation.Matcher;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class CompositeMatcher<E> implements Matcher<E> {

    private List<Matcher<E>> matchers = new ArrayList<>();

    /**
     * Get the value of matchers
     *
     * @return the value of matchers
     */
    public List<Matcher<E>> getMatchers() {
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
