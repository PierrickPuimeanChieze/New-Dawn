package com.newdawn.gui.economic;

import com.newdawn.controllers.GameData;
import com.newdawn.gui.PropertyOrToStringTreeCellFactory;
import com.newdawn.model.colony.Colony;
import com.newdawn.model.system.StellarSystem;
import com.sun.javafx.binding.SelectBinding;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author Pierrick Puimean-Chieze
 */
public class EconomicScreen implements Initializable {

	@FXML
	// fx:id="coloniesTreeView"
	private TreeView<Object> coloniesTreeView; // Value injected by FXMLLoader
	@Autowired
	private GameData gameData;
	private TreeItem<Object> colonyRoot;
	@FXML
	private TabPane colonyEconomicScreen;
	@FXML
	private ColonyEconomicScreen colonyEconomicScreenController;

	@Override
	// This method is called by the FXMLLoader when initialization is complete
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		assert coloniesTreeView != null : "fx:id=\"coloniesTreeView\" was not injected: check your FXML file 'EconomicScreen.fxml'.";
		assert colonyEconomicScreen != null;
		// initialize your logic here: all @FXML variables will have been
		// injected
		colonyRoot = new TreeItem<Object>("Populated Systems");
		coloniesTreeView.setRoot(colonyRoot);

		coloniesTreeView.setCellFactory(new PropertyOrToStringTreeCellFactory(
				"name", null, String.class));
		// TODO for this, try to use Bind.
		gameData.getPopulatedStellarSystems().addListener(
				new ListChangeListener<StellarSystem>() {
					@Override
					public void onChanged(Change<? extends StellarSystem> arg0) {
						colonyRoot.getChildren().clear();
						updateColonyTree();
					}

				});
		colonyEconomicScreenController.colonyProperty().bind(
				new SelectBinding.AsObject<Colony>(coloniesTreeView.getSelectionModel()
						.selectedItemProperty(), "value") {
					@Override
					protected Colony computeValue() {
						Object value = super.computeValue();
						if (value != null && value instanceof Colony) {
							return (Colony) value;
						}
						return null;
					}
				});
		updateColonyTree();
	}

	private void updateColonyTree() {
		for (StellarSystem populatedSystem : gameData
				.getPopulatedStellarSystems()) {
			TreeItem<Object> systemTreeItem = new TreeItem<Object>(
					populatedSystem);
			colonyRoot.getChildren().add(systemTreeItem);
			for (Colony colony : populatedSystem.getColonies()) {
				TreeItem<Object> colonyTreeItem = new TreeItem<Object>(colony);
				systemTreeItem.getChildren().add(colonyTreeItem);
			}
		}
	}
}
