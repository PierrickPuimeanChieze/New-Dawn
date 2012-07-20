package com.newdawn.gui.personnel;

import com.newdawn.model.personnel.NavalOfficer;
import com.newdawn.controllers.GameData;
import com.newdawn.gui.PropertyListCellFactory;
import com.newdawn.model.personnel.*;
import com.sun.javafx.collections.CompositeMatcher;
import com.sun.javafx.collections.transformation.Matcher;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.binding.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
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
    @Autowired
    private Skill[] skills;
    private ObservableList<PersonnelMember> personnelMemberFilteredList = FXCollections.
            observableArrayList();
    private final CompositeMatcher<PersonnelMember> compositeMatcher = new CompositeMatcher<>();
    private final CompositeMatcher<PersonnelMember> skillFiltersMatcher = new CompositeMatcher<>();
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
    @FXML
    private Map<Skill, TableColumn<PersonnelMember, Number>> skillFiltersColumn = new HashMap<>();
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
    @FXML //  fx:id="scientistFilterCheckBox"
    private CheckBox scientistFilterCheckBox; // Value injected by FXMLLoader
    @FXML //  fx:id="skillFilterComboBox"
    private ComboBox<Skill> skillFilterComboBox; // Value injected by FXMLLoader
    @FXML //  fx:id="skillFilterMaxValueColumn"
    private TableColumn<SkillFilter, Number> skillFilterMaxValueColumn; // Value injected by FXMLLoader
    @FXML //  fx:id="skillFilterMaxValueComponent"
    private TextField skillFilterMaxValueComponent; // Value injected by FXMLLoader
    @FXML //  fx:id="skillFilterMinValueColumn"
    private TableColumn<SkillFilter, Number> skillFilterMinValueColumn; // Value injected by FXMLLoader
    @FXML //  fx:id="skillFilterMinValueComponent"
    private TextField skillFilterMinValueComponent; // Value injected by FXMLLoader
    @FXML //  fx:id="skillFiltersNameColumn"
    private TableColumn<SkillFilter, String> skillFiltersNameColumn; // Value injected by FXMLLoader
    //TODO make this table editable
    //TODO Allow the users to delete a filter
    @FXML //  fx:id="skillFiltersTableView"
    private TableView<SkillFilter> skillFiltersTableView; // Value injected by FXMLLoader
    @FXML //  fx:id="detailsPane"
    private AnchorPane detailsPane; // Value injected by FXMLLoader
    @FXML //  fx:id="detailsPane"
    private PersonnelDetailsPane detailsPaneController; // Value injected by FXMLLoader

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert civilianAdministratorCheckBox != null : "fx:id=\"civilianAdministratorCheckBox\" was not injected: check your FXML file 'PersonnelManagementScreen.fxml'.";
        assert filteredPersonnelTableView != null : "fx:id=\"filteredPersonnelTableView\" was not injected: check your FXML file 'PersonnelManagementScreen.fxml'.";
        assert groundOfficerFilterCheckBox != null : "fx:id=\"groundOfficerFilterCheckBox\" was not injected: check your FXML file 'PersonnelManagementScreen.fxml'.";
        assert navalOfficerFilterCheckBox != null : "fx:id=\"navalOfficerFilterCheckBox\" was not injected: check your FXML file 'PersonnelManagementScreen.fxml'.";
        assert personnelNameColumn != null : "fx:id=\"personnelNameColumn\" was not injected: check your FXML file 'PersonnelManagementScreen.fxml'.";
        assert personnelRankColumn != null : "fx:id=\"personnelRankColumn\" was not injected: check your FXML file 'PersonnelManagementScreen.fxml'.";

        assert scientistFilterCheckBox != null : "fx:id=\"scientistFilterCheckBox\" was not injected: check your FXML file 'PersonnelManagementScreen.fxml'.";
        assert skillFilterComboBox != null : "fx:id=\"skillFilterComboBox\" was not injected: check your FXML file 'PersonnelManagementScreen.fxml'.";
        assert skillFilterMaxValueColumn != null : "fx:id=\"skillFilterMaxValueColumn\" was not injected: check your FXML file 'PersonnelManagementScreen.fxml'.";
        assert skillFilterMaxValueComponent != null : "fx:id=\"skillFilterMaxValueComponent\" was not injected: check your FXML file 'PersonnelManagementScreen.fxml'.";
        assert skillFilterMinValueColumn != null : "fx:id=\"skillFilterMinValueColumn\" was not injected: check your FXML file 'PersonnelManagementScreen.fxml'.";
        assert skillFilterMinValueComponent != null : "fx:id=\"skillFilterMinValueComponent\" was not injected: check your FXML file 'PersonnelManagementScreen.fxml'.";
        assert skillFiltersNameColumn != null : "fx:id=\"skillFiltersNameColumn\" was not injected: check your FXML file 'PersonnelManagementScreen.fxml'.";
        assert skillFiltersTableView != null : "fx:id=\"skillFiltersTableView\" was not injected: check your FXML file 'PersonnelManagementScreen.fxml'.";
        // initialize your logic here: all @FXML variables will have been injected

        compositeMatcher.getMatchers().add(typePersonnelMemberMatcher);
        compositeMatcher.getMatchers().add(skillFiltersMatcher);
        personnelNameColumn.setCellValueFactory(new PropertyValueFactory<PersonnelMember, String>("name"));
        personnelRankColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PersonnelMember, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(CellDataFeatures<PersonnelMember, String> arg0) {
                final PersonnelMember personnelMember = arg0.getValue();
                final StringExpression concat = Bindings.selectString(personnelMember.
                        rankProperty(), "designation");
                return concat;
            }
        });
        Bindings.bindContent(filteredPersonnelTableView.getItems(), personnelMemberFilteredList);
        filteredPersonnelTableView.getSelectionModel().selectedItemProperty().
                addListener(new ChangeListener<PersonnelMember>() {

            @Override
            public void changed(ObservableValue<? extends PersonnelMember> arg0, PersonnelMember arg1, PersonnelMember arg2) {

                System.out.println(arg2 == null ? null : arg2.getName());
            }
        });
        gameData.getPersonnelMembers().addListener(new ListChangeListener<PersonnelMember>() {

            @Override
            public void onChanged(Change<? extends PersonnelMember> arg0) {
                updateFilters(null);
            }
        });

        //<editor-fold defaultstate="collapsed" desc="Initialization of the skill filters tab">
        skillFilterComboBox.getItems().addAll(skills);
        PropertyListCellFactory<Skill> propertyListCellFactory = new PropertyListCellFactory<>("name", null);
        skillFilterComboBox.setButtonCell(new ListCell<Skill>() {

            @Override
            protected void updateItem(Skill arg0, boolean arg1) {
                super.updateItem(arg0, arg1);
                if (arg1 || arg0 == null) {
                    this.setText(null);
                } else {
                    this.setText(arg0.getName());
                }
            }
        });
        skillFilterComboBox.setCellFactory(propertyListCellFactory);
        skillFiltersNameColumn.setCellValueFactory(new Callback<CellDataFeatures<SkillFilter, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(CellDataFeatures<SkillFilter, String> arg0) {
                return Bindings.selectString(arg0.getValue().skillProperty(), "name");
            }
        });

        skillFilterMinValueColumn.setCellValueFactory(new Callback<CellDataFeatures<SkillFilter, Number>, ObservableValue<Number>>() {

            @Override
            public ObservableValue<Number> call(CellDataFeatures<SkillFilter, Number> arg0) {
                return arg0.getValue().minValueProperty();
            }
        });
        skillFilterMaxValueColumn.setCellValueFactory(new Callback<CellDataFeatures<SkillFilter, Number>, ObservableValue<Number>>() {

            @Override
            public ObservableValue<Number> call(CellDataFeatures<SkillFilter, Number> arg0) {
                return arg0.getValue().maxValueProperty();
            }
        });
        skillFiltersTableView.getItems().addListener(new ListChangeListener<SkillFilter>() {

            @Override
            public void onChanged(Change<? extends SkillFilter> arg0) {
                while (arg0.next()) {
                    for (SkillFilter skillFilter : arg0.getAddedSubList()) {
                        final Skill skill = skillFilter.getSkill();
                        TableColumn<PersonnelMember, Number> skillColumn = new TableColumn<>(skill.
                                getName());
                        skillColumn.setCellValueFactory(new Callback<CellDataFeatures<PersonnelMember, Number>, ObservableValue<Number>>() {

                            @Override
                            public ObservableValue<Number> call(CellDataFeatures<PersonnelMember, Number> arg0) {
                                PersonnelMember personnelMember = arg0.getValue();
                                IntegerBinding selectInteger = Bindings.
                                        selectInteger(personnelMember.
                                        skillLevelsProperty().valueAt(skill), "level");
                                selectInteger.addListener(new ChangeListener<Number>() {

                                    @Override
                                    public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                                        updateFilters(null);
                                    }
                                });
                                return selectInteger;
                            }
                        });
                        filteredPersonnelTableView.getColumns().add(skillColumn);
                    }

                    for (SkillFilter skillFilter : arg0.getRemoved()) {
                        final Skill skill = skillFilter.getSkill();
                        TableColumn<PersonnelMember, Number> column = skillFiltersColumn.
                                remove(skill);
                        if (column != null) {
                            filteredPersonnelTableView.getColumns().remove(column);
                        }
                    }
                }
            }
        });
        Bindings.bindContent(skillFiltersMatcher.getMatchers(), skillFiltersTableView.
                getItems());

        final ObjectBinding<PersonnelMember> select = Bindings.select(filteredPersonnelTableView.
                selectionModelProperty(), "selectedItem");
        detailsPaneController.personnelMemberProperty().bind(select);
        updateFilters(null);
    }

    //TODO Try to use a filtered List
    public void updateFilters(ActionEvent event) {
        personnelMemberFilteredList.clear();
        for (PersonnelMember personnelMember : gameData.getPersonnelMembers()) {
            if (compositeMatcher.matches(personnelMember)) {
                personnelMemberFilteredList.add(personnelMember);
            }
        }

    }

    public void addSkillFilter(ActionEvent event) {
        final Skill skill = skillFilterComboBox.getSelectionModel().
                getSelectedItem();
        SkillFilter skillFilter = new SkillFilter();
        skillFilter.setSkill(skill);
        skillFilter.setMinValue(Integer.parseInt(skillFilterMinValueComponent.
                getText()));
        skillFilter.setMaxValue(Integer.parseInt(skillFilterMaxValueComponent.
                getText()));
        skillFiltersTableView.getItems().add(skillFilter);
        compositeMatcher.getMatchers().add(skillFilter);
        updateFilters(event);
    }
}
