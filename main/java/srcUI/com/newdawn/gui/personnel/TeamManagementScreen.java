package com.newdawn.gui.personnel;

import com.newdawn.controllers.GameData;
import com.newdawn.gui.PropertyOrToStringTreeCellFactory;
import com.newdawn.model.personnel.PersonnelMember;
import com.newdawn.model.personnel.Skill;
import com.newdawn.model.personnel.SkillLevel;
import com.newdawn.model.personnel.team.GeologicalTeam;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.MapBinding;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class TeamManagementScreen implements Initializable {

    @FXML //  fx:id="assignmentTextField"
    private TextField assignmentTextField; // Value injected by FXMLLoader
    @FXML //  fx:id="effectiveTeamSkilLevelTextField"
    private TextField effectiveTeamSkilLevelTextField; // Value injected by FXMLLoader
    @FXML //  fx:id="leaderLeadershipSkillLevelTextField"
    private TextField leaderLeadershipSkillLevelTextField; // Value injected by FXMLLoader
    @FXML //  fx:id="leaderNameTextField"
    private TextField leaderNameTextField; // Value injected by FXMLLoader
    @FXML //  fx:id="leaderTeamSkillLevelTextField"
    private TextField leaderTeamSkillLevelTextField; // Value injected by FXMLLoader
    @FXML //  fx:id="locationTextFiedl"
    private TextField locationTextFiedl; // Value injected by FXMLLoader
    @FXML //  fx:id="membersTableView"
    private TableView<PersonnelMember> membersTableView; // Value injected by FXMLLoader
    @FXML //  fx:id="nameColumn"
    private TableColumn<PersonnelMember, String> nameColumn; // Value injected by FXMLLoader
    @FXML //  fx:id="skillColumn"
    private TableColumn<PersonnelMember, Number> skillColumn; // Value injected by FXMLLoader
    @FXML //  fx:id="teamCumulativeSkillLevelLabel"
    private Label teamCumulativeSkillLevelLabel; // Value injected by FXMLLoader
    @FXML //  fx:id="teamInformationTitledPane"
    private TitledPane teamInformationTitledPane; // Value injected by FXMLLoader
    @FXML //  fx:id="teamsTreeView"
    private TreeView teamsTreeView; // Value injected by FXMLLoader
    private TreeItem rootTreeItem;
    private TreeItem geologicalTeamsTreeItem;
    @Autowired
    private GameData gameData;
    @Resource(name = "leadership")
    private Skill leadershipSkill;
    @Resource(name = "geology")
    private Skill geologySkill;
    // Handler for Button[Button[id=null, styleClass=button]] onAction

    public void launchTeamAssignmentChange(ActionEvent event) {
        // handle the event here
    }

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert assignmentTextField != null : "fx:id=\"assignmentTextField\" was not injected: check your FXML file 'TeamManagementScreen.fxml'.";
        assert effectiveTeamSkilLevelTextField != null : "fx:id=\"effectiveTeamSkilLevelTextField\" was not injected: check your FXML file 'TeamManagementScreen.fxml'.";
        assert leaderLeadershipSkillLevelTextField != null : "fx:id=\"leaderLeadershipSkillLevelTextField\" was not injected: check your FXML file 'TeamManagementScreen.fxml'.";
        assert leaderNameTextField != null : "fx:id=\"leaderNameTextField\" was not injected: check your FXML file 'TeamManagementScreen.fxml'.";
        assert leaderTeamSkillLevelTextField != null : "fx:id=\"leaderTeamSkillLevelTextField\" was not injected: check your FXML file 'TeamManagementScreen.fxml'.";
        assert locationTextFiedl != null : "fx:id=\"locationTextFiedl\" was not injected: check your FXML file 'TeamManagementScreen.fxml'.";
        assert membersTableView != null : "fx:id=\"membersTableView\" was not injected: check your FXML file 'TeamManagementScreen.fxml'.";
        assert nameColumn != null : "fx:id=\"nameColumn\" was not injected: check your FXML file 'TeamManagementScreen.fxml'.";
        assert skillColumn != null : "fx:id=\"skillColumn\" as not injected: check your FXML file 'TeamManagementScreen.fxml'.";
        assert teamCumulativeSkillLevelLabel != null : "fx:id=\"teamCumulativeSkillLevelLabel\" was not injected: check your FXML file 'TeamManagementScreen.fxml'.";
        assert teamInformationTitledPane != null : "fx:id=\"teamInformationTitledPane\" was not injected: check your FXML file 'TeamManagementScreen.fxml'.";
        assert teamsTreeView != null : "fx:id=\"teamsTreeView\" was not injected: check your FXML file 'TeamManagementScreen.fxml'.";
        // initialize your logic here: all @FXML variables will have been injected
        final ObjectBinding<Object> selectedTeamBinding = Bindings.
                select(teamsTreeView.getSelectionModel().
                selectedItemProperty(), "value");
        final ObjectBinding<Skill> selectedTeamSkillBinding = Bindings.
                select(selectedTeamBinding, "teamSkill");
        //<editor-fold defaultstate="collapsed" desc="Initialize geologicalTeamsTreeItem">
        geologicalTeamsTreeItem = new TreeItem<>("Geological Teams");

        for (GeologicalTeam sourceItem : gameData.getGeologicalTeams()) {
            geologicalTeamsTreeItem.getChildren().
                    add(new TreeItem<>(sourceItem));
        }
        TreeItemListContentBinding<GeologicalTeam> geologicalTeamsBinding = new TreeItemListContentBinding<>(geologicalTeamsTreeItem.
                getChildren());
        gameData.getGeologicalTeams().addListener(geologicalTeamsBinding);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Initialize teamsTreeView and its items">
        rootTreeItem = new TreeItem<>();
        rootTreeItem.getChildren().add(geologicalTeamsTreeItem);
        teamsTreeView.setRoot(rootTreeItem);
        teamsTreeView.
                setCellFactory(new PropertyOrToStringTreeCellFactory("name", null, String.class));
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Initializing the team information Field">
        teamInformationTitledPane.textProperty().
                bind(Bindings.
                selectString(selectedTeamBinding, "name").
                concat(" Informations"));
        //TODO made the binding for the assignment
//        assignmentTextField.textProperty().bind((Bindings.
//                selectString(teamsTreeView.getSelectionModel().
//                selectedItemProperty(), "value", "assig");
        locationTextFiedl.textProperty().bind(StringExpression.
                stringExpression(Bindings.
                selectString(selectedTeamBinding, "localization", "localizationName")));
        effectiveTeamSkilLevelTextField.textProperty().bind(StringExpression.
                stringExpression(Bindings.
                selectLong(selectedTeamBinding, "cumulatedSkillLevel")));
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Initialize Leader information">
        final ObjectBinding<Object> leaderBinding = Bindings.
                select(selectedTeamBinding, "leader");
        leaderNameTextField.textProperty().bind(Bindings.
                selectString(leaderBinding, "name"));


        MapBinding<Skill, SkillLevel> leaderSkillLevelsBinding = new MapBinding<Skill, SkillLevel>() {
            final ObjectBinding<ObservableMap<Skill, SkillLevel>> skillLevelsBinding = Bindings.
                    select(leaderBinding, "skillLevels");

            {
                bind(skillLevelsBinding);
            }

            @Override
            protected ObservableMap<Skill, SkillLevel> computeValue() {
                return skillLevelsBinding.get();
            }
        };
//        MapExpression.mapExpression(test);
        final IntegerBinding leaderLeadershipSkillLevelValueBinding = Bindings.
                selectInteger(Bindings.
                valueAt(leaderSkillLevelsBinding, leadershipSkill), "level");
        final IntegerBinding leaderTeamSkillLevelValueBinding = Bindings.
                selectInteger(Bindings.
                valueAt(leaderSkillLevelsBinding, selectedTeamSkillBinding), "level");
//        final IntegerBinding leaderTeamSkillLevelValueBinding = Bindings.
//                selectInteger(Bindings.
//                valueAt(leaderSkillLevelsBinding, selectedTeamBinding.), "level");
        leaderLeadershipSkillLevelTextField.textProperty().
                bind(StringExpression.
                stringExpression(leaderLeadershipSkillLevelValueBinding));
        leaderTeamSkillLevelTextField.textProperty().bind(StringExpression.
                stringExpression(leaderTeamSkillLevelValueBinding));

        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Initialize Team Members Information">
        final ObjectBinding<ObservableList<PersonnelMember>> selectedTeamMembersBinding = Bindings.
                select(selectedTeamBinding, "teamMembers");
        membersTableView.itemsProperty().bind(selectedTeamMembersBinding);
        nameColumn.
                setCellValueFactory(new PropertyValueFactory<PersonnelMember, String>("name"));
        skillColumn.textProperty().bind(Bindings.
                selectString(selectedTeamSkillBinding, "name"));
        skillColumn.
                setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PersonnelMember, Number>, ObservableValue<Number>>() {
                    @Override
                    public ObservableValue<Number> call(CellDataFeatures<PersonnelMember, Number> param) {
                        PersonnelMember member = param.getValue();
                        final IntegerBinding skillLevelValueBinding = Bindings.
                                selectInteger(Bindings.valueAt(member.
                                skillLevelsProperty(), selectedTeamSkillBinding.get()), "level");
                        IntegerProperty toReturn = new SimpleIntegerProperty();
                        toReturn.bind(skillLevelValueBinding);
                        return toReturn;
                    }
                });
        //</editor-fold>
    }
}
