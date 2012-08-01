package com.newdawn.gui;

import javafx.util.Callback;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class SpringFXControllerFactory implements Callback<Class<?>, Object> {

    private ApplicationContext applicationContext;

    public SpringFXControllerFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object call(Class<?> arg0) {
        return applicationContext.getBean(arg0);
    }
}
