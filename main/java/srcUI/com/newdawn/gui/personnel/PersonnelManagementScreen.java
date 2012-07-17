package com.newdawn.gui.personnel;

import com.newdawn.controllers.GameData;
import com.newdawn.model.personnel.CivilianAdministrators;
import com.newdawn.model.personnel.GroundForceOfficers;
import com.newdawn.model.personnel.NavalOfficer;
import com.newdawn.model.personnel.PersonnelMember;
import com.newdawn.model.personnel.Scientist;
import com.newdawn.model.personnel.Skill;
import com.sun.javafx.collections.CompositeMatcher;
import com.sun.javafx.collections.MyFilteredList;
import com.sun.javafx.collections.transformation.FilterableList;
import com.sun.javafx.collections.transformation.Matcher;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
@Component
public class PersonnelManagementScreen
        implements Initializable {

    @Autowired
    private GameData gameData;
    private MyFilteredList<PersonnelMember> personnelMemberFilteredList;
    private final CompositeMatcher<PersonnelMember> compositeMatcher = new CompositeMatcher<>();
    private final Matcher<PersonnelMember> typePersonnelMemberMatcher = new Matcher<PersonnelMember>() {

        @Override
        public boolean matches(PersonnelMember e) {
            return (e instanceof Scientist && scientistFilterCheckBox.isSelected())
                    || (e instanceof NavalOfficer && navalOfficerFilterCheckBox.
                    isSelected())
                    || (e instanceof GroundForceOfficers && groundOfficerFilterCheckBox.
                    isSelected())
                    || (e instanceof CivilianAdministrators && civilianAdministratorCheckBox.
                    isSelected());
        }
    };
    @FXML //  fx:id="civilianAdministratorCheckBox"
    private CheckBox civilianAdministratorCheckBox; // Value injected by FXMLLoader
    @FXML //  fx:id="filteredPersonnelTableView"
    private TableView<PersonnelMember> filteredPersonnelTableView; // Value injected by FXMLLoader
    @FXML //  fx:id="groundOfficerFilterCheckBox"
    private CheckBox groundOfficerFilterCheckBox; // Value injected by FXMLLoader
    @FXML //  fx:id="navalOfficerFilterCheckBox"
    private CheckBox navalOfficerFilterCheckBox; // Value injected by FXMLLoader
    @FXML //  fx:id="personnelNameColumn"
    private TableColumn<PersonnelMember, String> personnelNameColumn; // Value injected by FXMLLoader
    @FXML //  fx:id="personnelRankColumn"
    private TableColumn<PersonnelMember, String> personnelRankColumn; // Value injected by FXMLLoader
    @FXML //  fx:id="personnelTypeColumn"
    private TableColumn<PersonnelMember, String> personnelTypeColumn; // Value injected by FXMLLoader
    @FXML //  fx:id="scientistFilterCheckBox"
    private CheckBox scientistFilterCheckBox; // Value injected by FXMLLoader
    @FXML //  fx:id="skillFilterComboBox"
    private ComboBox<Skill> skillFilterComboBox; // Value injected by FXMLLoader
    @FXML //  fx:id="skillFilterMaxValueColumn"
    private TableColumn<SkillFilter, Integer> skillFilterMaxValueColumn; // Value injected by FXMLLoader
    @FXML //  fx:id="skillFilterMaxValueComponent"
    private TextField skillFilterMaxValueComponent; // Value injected by FXMLLoader
    @FXML //  fx:id="skillFilterMinValueColumn"
    private TableColumn<SkillFilter, Integer> skillFilterMinValueColumn; // Value injected by FXMLLoader
    @FXML //  fx:id="skillFilterMinValueComponent"
    private TextField skillFilterMinValueComponent; // Value injected by FXMLLoader
    @FXML //  fx:id="skillFiltersNameColumn"
    private TableColumn<SkillFilter, Integer> skillFiltersNameColumn; // Value injected by FXMLLoader
    @FXML //  fx:id="skillFiltersTableView"
    private TableView<SkillFilter> skillFiltersTableView; // Value injected by FXMLLoader

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert civilianAdministratorCheckBox != null : "fx:id=\"civilianAdministratorCheckBox\" was not injected: check your FXML file 'PersonnelManagementScreen.fxml'.";
        assert filteredPersonnelTableView != null : "fx:id=\"filteredPersonnelTableView\" was not injected: check your FXML file 'PersonnelManagementScreen.fxml'.";
        assert groundOfficerFilterCheckBox != null : "fx:id=\"groundOfficerFilterCheckBox\" was not injected: check your FXML file 'PersonnelManagementScreen.fxml'.";
        assert navalOfficerFilterCheckBox != null : "fx:id=\"navalOfficerFilterCheckBox\" was not injected: check your FXML file 'PersonnelManagementScreen.fxml'.";
        assert personnelNameColumn != null : "fx:id=\"personnelNameColumn\" was not injected: check your FXML file 'PersonnelManagementScreen.fxml'.";
        assert personnelRankColumn != null : "fx:id=\"personnelRankColumn\" was not injected: check your FXML file 'PersonnelManagementScreen.fxml'.";
        assert personnelTypeColumn != null : "fx:id=\"personnelTypeColumn\" was not injected: check your FXML file 'PersonnelManagementScreen.fxml'.";
        assert scientistFilterCheckBox != null : "fx:id=\"scientistFilterCheckBox\" was not injected: check your FXML file 'PersonnelManagementScreen.fxml'.";
        assert skillFilterComboBox != null : "fx:id=\"skillFilterComboBox\" was not injected: check your FXML file 'PersonnelManagementScreen.fxml'.";
        assert skillFilterMaxValueColumn != null : "fx:id=\"skillFilterMaxValueColumn\" was not injected: check your FXML file 'PersonnelManagementScreen.fxml'.";
        assert skillFilterMaxValueComponent != null : "fx:id=\"skillFilterMaxValueComponent\" was not injected: check your FXML file 'PersonnelManagementScreen.fxml'.";
        assert skillFilterMinValueColumn != null : "fx:id=\"skillFilterMinValueColumn\" was not injected: check your FXML file 'PersonnelManagementScreen.fxml'.";
        assert skillFilterMinValueComponent != null : "fx:id=\"skillFilterMinValueComponent\" was not injected: check your FXML file 'PersonnelManagementScreen.fxml'.";
        assert skillFiltersNameColumn != null : "fx:id=\"skillFiltersNameColumn\" was not injected: check your FXML file 'PersonnelManagementScreen.fxml'.";
        assert skillFiltersTableView != null : "fx:id=\"skillFiltersTableView\" was not injected: check your FXML file 'PersonnelManagementScreen.fxml'.";

        personnelMemberFilteredList = new MyFilteredList<>(gameData.
                getPersonnelMembers(), compositeMatcher, FilterableList.FilterMode.BATCH);
        compositeMatcher.getMatchers().add(typePersonnelMemberMatcher);
        personnelNameColumn.setCellValueFactory(new PropertyValueFactory<PersonnelMember, String>("name"));
        Bindings.bindContent(filteredPersonnelTableView.getItems(), personnelMemberFilteredList);
        // initialize your logic here: all @FXML variables will have been injected
    }

    public void updateFilters(ActionEvent event) {
        personnelMemberFilteredList.filter();
    }
    
    public void addSkillFilter(ActionEvent event) {
        
    }

}
