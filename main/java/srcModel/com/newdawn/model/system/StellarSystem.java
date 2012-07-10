/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newdawn.model.system;

import com.newdawn.model.ships.Squadron;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class StellarSystem implements Serializable {

    private StringProperty nameProperty;
    private List<Star> stars = new ArrayList<>();
    private List<Planet> planets = new ArrayList<>();
    private List<Asteroid> asteroids = new ArrayList<>();
    private ObjectProperty<ObservableList<Squadron>> squadronsProperty;

    public List<Star> getStars() {
        return stars;
    }

    public StellarSystem() {
        nameProperty = new SimpleStringProperty(this, "name");
    }

    public List<Planet> getPlanets() {
        return planets;
    }

    public ObservableList<Squadron> getSquadrons() {
        return squadronsProperty().getValue();
    }

    public List<Asteroid> getAsteroids() {
        return asteroids;
    }

    public void setName(String name) {
        nameProperty().setValue(name);
    }

    public String getName() {
        return nameProperty().getValue();
    }

    public StringProperty nameProperty() {
        return nameProperty;
    }

    public ObjectProperty<ObservableList<Squadron>> squadronsProperty() {
        if (squadronsProperty == null) {
            ObservableList<Squadron> squadrons = FXCollections.
                    observableArrayList();
            squadronsProperty = new SimpleObjectProperty<>(this, "squadrons", squadrons);
        }
        return squadronsProperty;
    }

    @Override
    public String toString() {
        return super.toString()+"[name="+getName()+"]";
    }
    
    
}
