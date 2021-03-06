/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newdawn.gui;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
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
public class PropertyListCellFactory<T> implements
		Callback<ListView<T>, ListCell<T>> {

	EventHandler<MouseEvent> mouseEventHandler;
	private final String propertyName;

	public PropertyListCellFactory(String propertyName,
			EventHandler<MouseEvent> mouseEventHandler) {
		this.propertyName = propertyName;
		this.mouseEventHandler = mouseEventHandler;
	}

	@Override
	//TODO override the update item method of the cell
	public ListCell<T> call(ListView<T> arg0) {
		return new ListCell<T>() {
			@Override
			public void updateItem(T item, boolean empty) {
				super.updateItem(item, empty);
				textProperty().unbind();
				if (item != null) {
					// assumes MyDataType.someProperty() returns a StringProperty:
					textProperty().bind(Bindings.selectString(item, propertyName));
				}
			}
		};
	}
}
