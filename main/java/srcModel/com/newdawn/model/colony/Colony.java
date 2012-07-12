package com.newdawn.model.colony;

import com.newdawn.controllers.Config;
import com.newdawn.model.system.Planet;
import javafx.beans.binding.LongBinding;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import viewerfx.ViewerFX;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
class PercentBinding extends LongBinding {

    private final ObservableValue<Number> observableValue;
    private final int percent;

    public PercentBinding(ObservableValue<Number> observableValue, int percent) {
        this.observableValue = observableValue;
        this.percent = percent;
        super.bind(observableValue);
    }

    @Override
    protected long computeValue() {
        return Math.round((observableValue.getValue().longValue() / 100L) * percent);
    }
}

public class Colony {

    private final Config config;
    private ObjectProperty<Planet> colonizedPlanetProperty;
    private LongProperty populationProperty;
    private LongProperty agriculturePopulationProperty;
    private LongProperty servicesPopulationProperty;
    private LongProperty industryPopulationProperty;
    private StringProperty nameProperty;

    public StringProperty nameProperty() {
        if (nameProperty == null) {
            nameProperty = new SimpleStringProperty(this, "name");
        }
        return nameProperty;
    }

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return nameProperty().getValue();
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName(String name) {
        this.nameProperty().setValue(name);
    }


    public ReadOnlyLongProperty industryPopulationProperty() {
        if (industryPopulationProperty == null) {
            industryPopulationProperty = new SimpleLongProperty(this, "industryPopulation");
            industryPopulationProperty.bind(new PercentBinding(populationProperty(), config.
                    getIndustriesPopulationPart()));
        }
        return industryPopulationProperty;
    }

    /**
     * Get the value of industryPopulationProperty
     *
     * @return the value of industryPopulationProperty
     */
    public long getIndustryPopulation() {
        return industryPopulationProperty().getValue();
    }

    public ReadOnlyLongProperty servicesPopulationProperty() {
        if (servicesPopulationProperty == null) {
            servicesPopulationProperty = new SimpleLongProperty(this, "servicesPopulation");
            servicesPopulationProperty.bind(new PercentBinding(populationProperty(), config.
                    getServicesPopulationPart()));
        }
        return servicesPopulationProperty;
    }

    public ReadOnlyLongProperty agriculturePopulationProperty() {
        if (agriculturePopulationProperty == null) {
            agriculturePopulationProperty = new SimpleLongProperty(this, "agriculturePopulation");
            agriculturePopulationProperty.bind(new PercentBinding(populationProperty(), config.
                    getAgriculturePopulationPart()));
        }
        return agriculturePopulationProperty;
    }

    public Colony() {
        this.config = ViewerFX.getCurrentApplication().getSprintContainer().
                getBean(Config.class);
    }

    /**
     * Get the value of servicesPopulation
     *
     * @return the value of servicesPopulation
     */
    public long getServicesPopulation() {
        return servicesPopulationProperty().getValue();
    }

    /**
     * Get the value of populationForAgriculture
     *
     * @return the value of populationForAgriculture
     */
    public long getAgriculturePopulation() {
        return agriculturePopulationProperty().getValue();
    }
    /**
     * grow rate of the population, monthly
     */
    private FloatProperty populationGrowRateProperty;

    public Colony(Planet colonizedPlanet) {
        this();
        setColonizedPlanet(colonizedPlanet);
    }

    public FloatProperty populationGrowRateProperty() {
        //TODO use a binding for calculate the population grow rate
        if (populationGrowRateProperty == null) {
            populationGrowRateProperty = new SimpleFloatProperty(this, "populationGrowRate");
        }
        return populationGrowRateProperty;
    }

    public LongProperty populationProperty() {
        if (populationProperty == null) {
            populationProperty = new SimpleLongProperty(this, "population", 0);
        }
        return populationProperty;
    }

    public ObjectProperty<Planet> colonizedPlanetProperty() {
        if (colonizedPlanetProperty == null) {
            colonizedPlanetProperty = new SimpleObjectProperty<>(this, "colonizedPlanet");
        }
        return colonizedPlanetProperty;
    }

    /**
     * Get the value of populationGrowRate
     *
     * @return the value of populationGrowRate
     */
    public float getPopulationGrowRate() {
        return populationGrowRateProperty().getValue();
    }

    /**
     * Set the value of populationGrowRate
     *
     * @param populationGrowRate new value of populationGrowRate
     */
    public void setPopulationGrowRate(float populationGrowRate) {
        this.populationGrowRateProperty().setValue(populationGrowRate);
    }

    /**
     * Get the value of colonizedPlanet
     *
     * @return the value of colonizedPlanet
     */
    public Planet getColonizedPlanet() {
        return colonizedPlanetProperty().getValue();
    }

    /**
     * Set the value of colonizedPlanet
     *
     * @param colonizedPlanet new value of colonizedPlanet
     */
    final public void setColonizedPlanet(Planet colonizedPlanet) {
        this.colonizedPlanetProperty().setValue(colonizedPlanet);
    }

    /**
     * Get the value of population
     *
     * @return the value of population
     */
    public long getPopulation() {
        return populationProperty().getValue();
    }

    /**
     * Set the value of population
     *
     * @param population new value of population
     */
    public void setPopulation(long population) {
        this.populationProperty().setValue(population);
    }
}
