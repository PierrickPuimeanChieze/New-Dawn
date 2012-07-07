package com.newdawn.gui;

import java.math.BigInteger;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javax.swing.SwingWorker;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class PropertyOrToStringTreeCellFactory implements Callback<TreeView, TreeCell> {
    
    private Class<?> toStringClass;
    EventHandler<MouseEvent> mouseEventHandler;
    private final String propertyName;
    
    public PropertyOrToStringTreeCellFactory(String propertyName, Class<?> toStringClass, EventHandler<MouseEvent> mouseEventHandler) {
        this.propertyName = propertyName;
        this.mouseEventHandler = mouseEventHandler;
        this.toStringClass = toStringClass;
    }
    
    @Override
    public TreeCell call(TreeView arg0) {
        
        final TreeCell toReturn = new TreeCell();
        
        toReturn.itemProperty().addListener(new ChangeListener() {
            
            @Override
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                if (arg2 == null) {
                    toReturn.setText("null");
                }
                else if (toStringClass.isAssignableFrom(arg2.getClass())) {
                    toReturn.setText(arg2.toString());
                } else {
                    final StringBinding selectString = Bindings.selectString(arg0, propertyName);
                    toReturn.setText(selectString.get());
                }
            }
        });
        return toReturn;
    }
}
