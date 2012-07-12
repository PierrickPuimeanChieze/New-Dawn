package com.newdawn.gui.economic;

import com.newdawn.controllers.Config;
import com.newdawn.model.colony.Colony;
import com.sun.javafx.binding.SelectBinding;
import java.net.URL;
import java.util.ResourceBundle;
import static javafx.beans.binding.Bindings.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
class PopulationBinding extends SelectBinding.AsObject {

    public PopulationBinding(ObservableValue ov, String... strings) {
        super(ov, strings);
    }

    @Override
    protected Object computeValue() {

        Long value = ((Long) super.computeValue());
        if (value == null) {
            return null;
        } else if (value >= 1_000_000) {
            double name = (double) value / 1_000_000;
            return String.format("%.2fm", name);
        } else {
            double name = (double) value / 1_000;
            return String.format("%.2fk", name);
        }
    }
}

public class ColonyEconomicScreen implements Initializable {

    @FXML //  fx:id="agriculturePopulationLabel"
    private Label agriculturePopulationLabel; // Value injected by FXMLLoader
    @FXML //  fx:id="annualGrowRateLabel"
    private Label annualGrowRateLabel; // Value injected by FXMLLoader
    @FXML //  fx:id="industryPopulationLabel"
    private Label industryPopulationLabel; // Value injected by FXMLLoader
    @FXML //  fx:id="servicePopulationLabel"
    private Label servicePopulationLabel; // Value injected by FXMLLoader
    @FXML //  fx:id="totalPopulationLabel"
    private Label totalPopulationLabel; // Value injected by FXMLLoader
    private ObjectProperty<Colony> colonyProperty;

    @Autowired
    private Config config;
    public ObjectProperty<Colony> colonyProperty() {
        if (colonyProperty == null) {
            colonyProperty = new SimpleObjectProperty<>(this, "colony");
        }
        return colonyProperty;
    }

    /**
     * Get the value of colony
     *
     * @return the value of colony
     */
    public Colony getColony() {
        return colonyProperty().getValue();
    }

    /**
     * Set the value of colony
     *
     * @param colony new value of colony
     */
    public void setColony(Colony colony) {
        this.colonyProperty().setValue(colony);
    }

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert agriculturePopulationLabel != null : "fx:id=\"agriculturePopulationLabel\" was not injected: check your FXML file 'ColonyEconomicScreen.fxml'.";
        assert annualGrowRateLabel != null : "fx:id=\"annualGrowRateLabel\" was not injected: check your FXML file 'ColonyEconomicScreen.fxml'.";
        assert industryPopulationLabel != null : "fx:id=\"industryPopulationLabel\" was not injected: check your FXML file 'ColonyEconomicScreen.fxml'.";
        assert servicePopulationLabel != null : "fx:id=\"servicePopulationLabel\" was not injected: check your FXML file 'ColonyEconomicScreen.fxml'.";
        assert totalPopulationLabel != null : "fx:id=\"totalPopulationLabel\" was not injected: check your FXML file 'ColonyEconomicScreen.fxml'.";

        // initialize your logic here: all @FXML variables will have been injected

        totalPopulationLabel.textProperty().bind(new PopulationBinding(colonyProperty(), "population"));
        agriculturePopulationLabel.textProperty().bind(new PopulationBinding(colonyProperty(), "agriculturePopulation"));
        servicePopulationLabel.textProperty().bind(new PopulationBinding(colonyProperty(), "servicesPopulation"));
        industryPopulationLabel.textProperty().bind(new PopulationBinding(colonyProperty(), "industryPopulation"));


        annualGrowRateLabel.textProperty().bind(format("%.2s%%", selectFloat(colonyProperty(), "populationGrowRate").
                multiply(config.getPopulationGrowRateAnnualMultiplicator())));
    }
}
