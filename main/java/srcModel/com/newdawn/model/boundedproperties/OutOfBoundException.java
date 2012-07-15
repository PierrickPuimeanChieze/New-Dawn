package com.newdawn.model.boundedproperties;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
class OutOfBoundException extends RuntimeException {

    private final Number culprit;
    private final BoundedProperty property;

    OutOfBoundException(BoundedProperty property, Number culprit) {
        this.property = property;
        this.culprit = culprit;
    }

    @Override
    public String getMessage() {
        return "Bound Broke on property "+property+" by value "+culprit;
    }
    
    
}
