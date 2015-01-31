package com.newdawn.gui;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Pierrick Puimean-Chieze
 */
public class PropertyOrToStringTreeCellFactory<T> implements
		Callback<TreeView<T>, TreeCell<T>> {

	private List<Class<?>> toStringClasses = new ArrayList<>();;
	EventHandler<MouseEvent> mouseEventHandler;
	private final String propertyName;

	public PropertyOrToStringTreeCellFactory(String propertyName,
			EventHandler<MouseEvent> mouseEventHandler,
			Class<?>... toStringClasses) {
		this.propertyName = propertyName;
		this.mouseEventHandler = mouseEventHandler;
		CollectionUtils.addAll(this.toStringClasses, toStringClasses);
	}

	@Override
	public TreeCell<T> call(TreeView<T> arg0) {

		final TreeCell<T> toReturn = new TreeCell<T>() {
			@Override
			protected void updateItem(T item, boolean empty) {
				super.updateItem(item, empty);
				textProperty().unbind();
				if (item != null) {
					if (CollectionUtils.countMatches(toStringClasses, (Class class1) -> class1.isAssignableFrom(item.getClass()))>0) {
						setText(item.toString());
					} else {
						textProperty().bind(Bindings.selectString(item, propertyName));
					}
				}
			}
		};

		return toReturn;
	}
}
