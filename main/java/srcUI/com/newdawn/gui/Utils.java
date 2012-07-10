package com.newdawn.gui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
}
