package com.newdawn.gui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class Utils {

    private static final Log LOG = LogFactory.getLog(Utils.class);

    public static Object getUserData(Object source) {
        try {
            Method getUserDataMethod = source.getClass().getMethod("getUserData");
            return getUserDataMethod.invoke(source);
        } catch (NoSuchMethodException ex) {
            return null;
        } catch (IllegalAccessException | InvocationTargetException | SecurityException ex) {
            LOG.error(null, ex);
            throw new RuntimeException(ex);
        }
    }
    public static String formatKeyEvent(KeyEvent event) {
        String name = "keyPressed on " + event.getSource() + ". Key code :" + event.
                getCode() + ". character :" + event.getCharacter() + ". Mod :";
        if (event.isAltDown()) {
            name += " ALT";
        }
        if (event.isControlDown()) {
            name += " CTRL";
        }
        if (event.isShiftDown()) {
            name += " SHIFT";
        }
        if (event.isMetaDown()) {
            name += " META";
        }
        name += ".";
        if (event.getSource() instanceof Node) {
            Node node = (Node) event.getSource();
            if (node.isFocused()) {
                name += " focused.";
            }
        }
        name += " target : " + event.getTarget();
        return name;
    }
}
