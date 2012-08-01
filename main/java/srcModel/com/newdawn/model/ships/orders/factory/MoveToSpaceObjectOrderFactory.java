package com.newdawn.model.ships.orders.factory;

import com.newdawn.model.ships.Squadron;
import com.newdawn.model.ships.orders.MoveToSpaceObjectOrder;
import com.newdawn.model.ships.orders.Order;
import com.newdawn.model.system.SpaceObject;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class MoveToSpaceObjectOrderFactory implements OrderFactory {

    private SpaceObject destination;
    private ReadOnlyStringProperty nameProperty;

    public ReadOnlyStringProperty nameProperty() {
        if (nameProperty == null) {
            nameProperty = new SimpleStringProperty(this, "name", "Move To");
        }
        return nameProperty;
    }

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return nameProperty().getValue();
    }

    public MoveToSpaceObjectOrderFactory(SpaceObject destination) {
        this.destination = destination;
    }

    @Override
    public Order createOrder(Squadron squadron) {
        return new MoveToSpaceObjectOrder(destination, squadron);
    }
}
