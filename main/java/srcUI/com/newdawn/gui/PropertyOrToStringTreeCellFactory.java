package com.newdawn.gui;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 * 
 * @author Pierrick Puimean-Chieze
 */
public class PropertyOrToStringTreeCellFactory<T> implements
		Callback<TreeView<T>, TreeCell<T>> {

	private Class<?>[] toStringClasses;
	EventHandler<MouseEvent> mouseEventHandler;
	private final String propertyName;

	public PropertyOrToStringTreeCellFactory(String propertyName,
			EventHandler<MouseEvent> mouseEventHandler,
			Class<?>... toStringClasses) {
		this.propertyName = propertyName;
		this.mouseEventHandler = mouseEventHandler;
		this.toStringClasses = toStringClasses;
	}

	@Override
	public TreeCell<T> call(TreeView<T> arg0) {

		final TreeCell<T> toReturn = new TreeCell<T>();
		toReturn.textProperty().bind(
				Bindings.selectString(toReturn.itemProperty(), propertyName));

		// TODO replace by updateItem
		toReturn.itemProperty().addListener(new ChangeListener<T>() {
			@Override
			public void changed(ObservableValue<? extends T> arg0, T arg1, T arg2) {
				if (arg2 == null) {
					toReturn.textProperty().unbind();
					toReturn.setText(null);
					return;
				}
				for (Class<?> class1 : toStringClasses) {
					if (class1.isAssignableFrom(arg2.getClass())) {
						toReturn.textProperty().unbind();
						toReturn.setText(arg2.toString());
						return;
					}
				}
				if (!toReturn.textProperty().isBound()) {
					toReturn.textProperty().bind(
							Bindings.selectString(toReturn.itemProperty(),
									propertyName));
				}
			}
		});
		return toReturn;
	}
}
