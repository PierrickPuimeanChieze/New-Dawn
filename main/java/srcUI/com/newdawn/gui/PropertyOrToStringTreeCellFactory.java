package com.newdawn.gui;

import com.sun.javafx.property.PropertyReference;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
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
public class PropertyOrToStringTreeCellFactory implements Callback<TreeView, TreeCell> {

    private Class<?>[] toStringClasses;
    EventHandler<MouseEvent> mouseEventHandler;
    private final String propertyName;

    public PropertyOrToStringTreeCellFactory(String propertyName, EventHandler<MouseEvent> mouseEventHandler, Class<?>... toStringClasses) {
        this.propertyName = propertyName;
        this.mouseEventHandler = mouseEventHandler;
        this.toStringClasses = toStringClasses;
    }

    @Override
    public TreeCell call(TreeView arg0) {

        final TreeCell toReturn = new TreeCell();
        toReturn.textProperty().bind(Bindings.selectString(toReturn.itemProperty(), propertyName));

        toReturn.itemProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                if (arg2 == null) {
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
                    toReturn.textProperty().bind(Bindings.selectString(toReturn.
                            itemProperty(), propertyName));
                }
            }
        });
        return toReturn;
    }
}
