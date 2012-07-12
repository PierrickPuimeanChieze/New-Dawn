/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.newdawn.controllers;

import com.newdawn.model.colony.Colony;
import com.newdawn.model.system.StellarSystem;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
@Component
public class GameData {

    private ObservableList<StellarSystem> stellarSystems = FXCollections.
            observableArrayList();
    private ObservableList<StellarSystem> populatedStellarSystems = FXCollections.
            observableArrayList();
    private LongProperty wealthProperty;

    public LongProperty wealthProperty() {
        if (wealthProperty == null) {
            wealthProperty = new SimpleLongProperty(this, "wealth", 0);
        }
        return wealthProperty;
    }

    /**
     * Get the value of wealth
     *
     * @return the value of wealth
     */
    public long getWealth() {
        return wealthProperty().getValue();
    }

    /**
     * Set the value of wealth
     *
     * @param wealth new value of wealth
     */
    public void setWealth(long wealth) {
        this.wealthProperty().setValue(wealth);
    }


    {
        stellarSystems.addListener(new ListChangeListener<StellarSystem>() {

            @Override
            public void onChanged(Change<? extends StellarSystem> arg0) {
                while (arg0.next()) {
                    for (final StellarSystem stellarSystem : arg0.
                            getAddedSubList()) {
                        stellarSystem.getColonies().addListener(new ListChangeListener<Colony>() {

                            @Override
                            public void onChanged(Change<? extends Colony> arg0) {
                                if (stellarSystem.getColonies().size() == 1) {
                                    populatedStellarSystems.add(stellarSystem);
                                } else if (stellarSystem.getColonies().size() == 0) {
                                    populatedStellarSystems.remove(stellarSystem);
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    public ObservableList<StellarSystem> getStellarSystems() {
        return stellarSystems;
    }

    public ObservableList<StellarSystem> getPopulatedStellarSystems() {
        return populatedStellarSystems;
    }
    
    
}
