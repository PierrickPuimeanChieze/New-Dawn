/**
 * Sample Skeleton for "TeamAssignmentDialog.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

package com.newdawn.gui.personnel;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

import javax.annotation.Resource;

import com.newdawn.controllers.AssignmentController;
import com.newdawn.gui.PropertyOrToStringTreeCellFactory;
import com.newdawn.model.personnel.team.FieldTeam;
import com.newdawn.model.personnel.team.TeamAssignment;
import com.newdawn.model.system.StellarSystem;

public class TeamAssignmentDialog implements Initializable {

	@FXML
	private AnchorPane dialogRoot;
	@FXML
	// fx:id="availableAssignmentsTreeView"
	private TreeView<Object> availableAssignmentsTreeView; // Value injected by

	@FXML
	// fx:id="okButton"
	private Button okButton;

	private TreeItem<Object> root;
	@Resource
	private AssignmentController assignmentController;
	private FieldTeam team;

	// Handler for Button[Button[id=null, styleClass=button]] onAction
	public void cancelButtonAction(ActionEvent event) {
//		dialogRoot.getP
	}

	// Handler for Button[Button[id=null, styleClass=button]] onAction
	public void okButtonAction(ActionEvent event) {
		// Bindings.
	}

	@Override
	// This method is called by the FXMLLoader when initialization is complete
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		assert availableAssignmentsTreeView != null : "fx:id=\"availableAssignmentsTreeView\" was not injected: check your FXML file 'TeamAssignmentDialog.fxml'.";
		assert okButton != null;
		root = new TreeItem<>();
		availableAssignmentsTreeView.setRoot(root);
		availableAssignmentsTreeView.setShowRoot(false);
		BooleanBinding selectedItemIsAssignmentBinding = new BooleanBinding() {

			{
				bind(availableAssignmentsTreeView.getSelectionModel()
						.selectedItemProperty());
			}

			@Override
			protected boolean computeValue() {

				TreeItem<Object> selectedItem = availableAssignmentsTreeView
						.getSelectionModel().getSelectedItem();

				return !(selectedItem != null
						&& selectedItem.getValue() != null && selectedItem
						.getValue() instanceof TeamAssignment);
			}
		};
		okButton.disableProperty().bind(selectedItemIsAssignmentBinding);
		
		availableAssignmentsTreeView.setCellFactory(new PropertyOrToStringTreeCellFactory<Object>("visualName", null, String.class));
		
	}

	public void setTeam(FieldTeam team) {
		this.team = team;
		Map<StellarSystem, Map<String, List<? extends TeamAssignment>>> possibleAssignements = assignmentController
				.getAllPossibleTeamAssignments(team.getType());
		root.getChildren().clear();
		for (Entry<StellarSystem, Map<String, List<? extends TeamAssignment>>> systemEntry : possibleAssignements
				.entrySet()) {
			TreeItem<Object> stellarSystemTreeItem = new TreeItem<Object>(
					systemEntry.getKey().getName());
			for (Entry<String, List<? extends TeamAssignment>> assignmentCategoryEntry : systemEntry
					.getValue().entrySet()) {
				TreeItem<Object> categoryTreeItem = new TreeItem<Object>(
						assignmentCategoryEntry.getKey());
				for (TeamAssignment teamAssignment : assignmentCategoryEntry
						.getValue()) {
					categoryTreeItem.getChildren().add(
							new TreeItem<Object>(teamAssignment));
				}
				stellarSystemTreeItem.getChildren().add(categoryTreeItem);
			}
			root.getChildren().add(stellarSystemTreeItem);
		}
	}

}
