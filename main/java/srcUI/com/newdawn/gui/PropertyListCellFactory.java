/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newdawn.gui;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

//TODO correction of the javadoc
/**
 *
 * This factory create ListCell for a list view. The text of the created
 * listcelle will be binded to a named property of the element for the list
 * cell. Additionaly, a specific mousevent handler will be set for the
 * mouseclicked event of the created list cell
 *
 * @author Pierrick Puimean-Chieze
 */
public class PropertyListCellFactory<T> implements Callback<ListView<T>, ListCell<T>> {

    EventHandler<MouseEvent> mouseEventHandler;
    private final String propertyName;

    public PropertyListCellFactory(String propertyName, EventHandler<MouseEvent> mouseEventHandler) {
        this.propertyName = propertyName;
        this.mouseEventHandler = mouseEventHandler;
    }

    @Override
    public ListCell<T> call(ListView<T> arg0) {
        ListCell<T> toReturn = new ListCell<>();
        final StringBinding selectString = Bindings.selectString(toReturn.
                itemProperty(), propertyName);
        toReturn.textProperty().bind(selectString);
        toReturn.setOnMouseClicked(mouseEventHandler);
        return toReturn;
    }
}
