package com.newdawn.controllers;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class PersonnelAssignmentException extends RuntimeException {

    public PersonnelAssignmentException(String message, Object... format) {
        super(String.format(message, format));
    }
}
