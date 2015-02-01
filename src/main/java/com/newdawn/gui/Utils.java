package com.newdawn.gui;

import com.newdawn.gui.map.system.Constants;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.awt.geom.Point2D;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 
 * @author Pierrick Puimean-Chieze
 */
public class Utils {

	private static final Log LOG = LogFactory.getLog(Utils.class);

	public static Object getUserData(Object source) {
		try {
			Method getUserDataMethod = source.getClass().getMethod(
					"getUserData");
			return getUserDataMethod.invoke(source);
		} catch (NoSuchMethodException ex) {
			return null;
		} catch (IllegalAccessException | InvocationTargetException
				| SecurityException ex) {
			LOG.error(null, ex);
			throw new RuntimeException(ex);
		}
	}

	public static String formatKeyEvent(KeyEvent event) {
		String name = "keyPressed on " + event.getSource() + ". Key code :"
				+ event.getCode() + ". character :" + event.getCharacter()
				+ ". Mod :";
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

    public static Point2D.Double convertCoordinateFromSpaceToScreen(Point2D.Double position, double zoomLevel ) {
        final double registeredPositionX = position.getX();
        final double registeredPositionY = position.getY();

        double positionX = (registeredPositionX / Constants.FIXED_QUOTIENT)
                * zoomLevel;
        double positionY = (registeredPositionY / Constants.FIXED_QUOTIENT)
                * zoomLevel;

        return new Point2D.Double(positionX, positionY*-1);
    }

    public static Point2D.Double convertCoordinateFromScreenToSpace(Point2D.Double position, double zoomLevel ) {
        final double registeredPositionX = position.getX();
        final double registeredPositionY = position.getY()*-1;

        double positionX = (registeredPositionX / zoomLevel)
                * Constants.FIXED_QUOTIENT;
        double positionY = (registeredPositionY / zoomLevel)
                * Constants.FIXED_QUOTIENT;

        return new Point2D.Double(positionX, positionY);
    }
}
