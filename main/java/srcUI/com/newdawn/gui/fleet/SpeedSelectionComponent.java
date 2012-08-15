/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newdawn.gui.fleet;

import com.newdawn.model.ships.Squadron;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

/**
 * 
 * @author Pierrick Puimean-Chieze
 */
public class SpeedSelectionComponent extends VBox {

	private ObjectProperty<Squadron> squadronProperty;
	private DoubleBinding selectedSpeedBinding;
	private TextField selectedSpeed;
	private TextField maxSpeed;

	public SpeedSelectionComponent() {
		initComponents();
	}

	private void initComponents() {

		this.getChildren().addAll(getSelectedSpeed(), getMaxSpeed());

	}

	public DoubleBinding getSelectedSpeedBinding() {
		if (selectedSpeedBinding == null) {
			selectedSpeedBinding = new DoubleBinding() {
				{
					super.bind(getSelectedSpeed().textProperty());
				}

				@Override
				protected double computeValue() {

					return Double.parseDouble(getSelectedSpeed().textProperty()
							.getValue());
				}

				@Override
				public void dispose() {
					super.dispose();
					super.unbind(getSelectedSpeed().textProperty());

				}
			};
		}
		return selectedSpeedBinding;
	}

	public ObjectProperty<Squadron> squadronProperty() {
		if (squadronProperty == null) {
			squadronProperty = new SimpleObjectProperty<>(this, "squadron");
			squadronProperty.addListener(new ChangeListener<Squadron>() {
				@Override
				public void changed(
						ObservableValue<? extends Squadron> property,
						Squadron oldValue, Squadron newValue) {

					if (oldValue != null) {
						oldValue.speedProperty().unbind();
					}

					if (newValue != null) {
						// getSpeedSelectionSlider().setMax(newValue.getMaxSpeed());
						// getSpeedSelectionSlider().setMajorTickUnit(newValue.getMaxSpeed());

						getMaxSpeed().setText("" + newValue.getMaxSpeed());
						getSelectedSpeed().setText("" + newValue.getSpeed());
						getSelectedSpeed().setDisable(false);

						newValue.speedProperty()
								.bind(getSelectedSpeedBinding());
					} else {
						getMaxSpeed().setText("-");
						getSelectedSpeed().setText("-");
						getSelectedSpeed().setDisable(true);
					}
				}
			});
		}
		return squadronProperty;
	}

	public void setSquadron(Squadron squadron) {
		squadronProperty().setValue(squadron);
	}

	public TextField getMaxSpeed() {
		if (maxSpeed == null) {
			maxSpeed = new TextField();
			maxSpeed.setDisable(true);

		}
		return maxSpeed;
	}

	public TextField getSelectedSpeed() {
		if (selectedSpeed == null) {
			selectedSpeed = new TextField();
		}
		return selectedSpeed;
	}
}
