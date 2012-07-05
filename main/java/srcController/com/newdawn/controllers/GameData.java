/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.newdawn.controllers;

import com.newdawn.model.system.StellarSystem;
import javafx.collections.FXCollections;
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

    public ObservableList<StellarSystem> getStellarSystems() {

        return stellarSystems;
    }
}
