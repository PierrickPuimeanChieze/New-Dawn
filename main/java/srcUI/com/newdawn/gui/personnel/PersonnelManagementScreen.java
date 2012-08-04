package com.newdawn.gui.personnel;

import com.newdawn.controllers.TeamController;
import com.newdawn.model.personnel.NavalOfficer;
import com.newdawn.controllers.GameData;
import com.newdawn.controllers.TeamController.FieldTeamType;
import com.newdawn.gui.PropertyListCellFactory;
import com.newdawn.model.personnel.*;
import com.sun.javafx.collections.CompositeMatcher;
import com.sun.javafx.collections.transformation.Matcher;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.MapChangeListener;
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
    private final MapChangeListener<Skill, SkillLevel> mapChangeListener = new MapChangeListener<Skill, SkillLevel>() {
        @Override
        public void onChanged(MapChangeListener.Change<? extends Skill, ? extends SkillLevel> arg0) {
            updateSkills();
        }
    };
    @Autowired
    private Skill[] skills;
    @Autowired
    private TeamController teamController;
    @Autowired
    private AssignementFilter assignementFilters;
    private ObservableList<Official> officialFilteredList = FXCollections.
            observableArrayList();
    private final CompositeMatcher<Official> compositeMatcher = new CompositeMatcher<>();
    private final CompositeMatcher<Official> skillFiltersMatcher = new CompositeMatcher<>();
    private final Matcher<Official> typeOfficialMatcher = new Matcher<Official>() {
        @Override
        public boolean matches(Official e) {
            return (e instanceof Scientist && scientistFilterCheckBox.
                    isSelected())
                    || (e instanceof NavalOfficer && navalOfficerFilterCheckBox.
                    isSelected())
                    || (e instanceof GroundForceOfficers && groundOfficerFilterCheckBox.
                    isSelected())
                    || (e instanceof CivilianAdministrators && civilianAdministratorCheckBox.
                    isSelected());
        }
    };
    @FXML
    private Map<Skill, TableColumn<Official, Number>> skillFiltersColumn = new HashMap<>();
    @FXML //  fx:id="civilianAdministratorCheckBox"
    private CheckBox civilianAdministratorCheckBox; // Value injected by FXMLLoader
    @FXML //  fx:id="filteredPersonnelTableView"
    private TableView<Official> officialsFilteredTableView; // Value injected by FXMLLoader
    @FXML //  fx:id="groundOfficerFilterCheckBox"
    private CheckBox groundOfficerFilterCheckBox; // Value injected by FXMLLoader
    @FXML //  fx:id="navalOfficerFilterCheckBox"
    private CheckBox navalOfficerFilterCheckBox; // Value injected by FXMLLoader
    @FXML //  fx:id="personnelNameColumn"
    private TableColumn<Official, String> personnelNameColumn; // Value injected by FXMLLoader
    @FXML //  fx:id="personnelRankColumn"
    private TableColumn<Official, String> personnelRankColumn; // Value injected by FXMLLoader
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
    @FXML
    private ListView<SkillLevel> skillsListView;
    //TODO implements the filter
    @FXML
    private ComboBox<AssignementFilter> assignmentsFilterComboBox;
    //TODO correct variable name
    @FXML
    private ListView<PersonnelAssignment> assigmentsListView;
    @FXML //  fx:id="createTeamMenuItem"
    private MenuItem createTeamMenuItem; // Value injected by FXMLLoader

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert civilianAdministratorCheckBox != null : "fx:id=\"civilianAdministratorCheckBox\" was not injected: check your FXML file 'PersonnelManagementScreen.fxml'.";
        assert officialsFilteredTableView != null : "fx:id=\"filteredPersonnelTableView\" was not injected: check your FXML file 'PersonnelManagementScreen.fxml'.";
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
        assert skillsListView != null;
        assert assignmentsFilterComboBox != null;
        assert assigmentsListView != null;
        assert createTeamMenuItem != null : "fx:id=\"createTeamMenuItem\" was not injected: check your FXML file 'PersonnelManagementScreen.fxml'.";
        // initialize your logic here: all @FXML variables will have been injected
        //<editor-fold defaultstate="collapsed" desc="Initialization of the compositeMatcher">
        compositeMatcher.getMatchers().add(typeOfficialMatcher);
        compositeMatcher.getMatchers().add(skillFiltersMatcher);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Initialization of the filteredPersonnelTableView">
        personnelNameColumn.
                setCellValueFactory(new PropertyValueFactory<Official, String>("name"));
        personnelRankColumn.
                setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Official, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Official, String> arg0) {
                final Official official = arg0.getValue();
                final StringExpression concat = Bindings.selectString(official.
                        rankProperty(), "designation");
                return concat;
            }
        });
        Bindings.
                bindContent(officialsFilteredTableView.getItems(), officialFilteredList);
        gameData.getOfficials().addListener(new ListChangeListener<Official>() {
            @Override
            public void onChanged(Change<? extends Official> arg0) {
                updateFilters(null);
            }
        });
        //</editor-fold>

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
        skillFiltersNameColumn.
                setCellValueFactory(new Callback<CellDataFeatures<SkillFilter, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<SkillFilter, String> arg0) {
                return Bindings.
                        selectString(arg0.getValue().skillProperty(), "name");
            }
        });

        skillFilterMinValueColumn.
                setCellValueFactory(new Callback<CellDataFeatures<SkillFilter, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(CellDataFeatures<SkillFilter, Number> arg0) {
                return arg0.getValue().minValueProperty();
            }
        });
        skillFilterMaxValueColumn.
                setCellValueFactory(new Callback<CellDataFeatures<SkillFilter, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(CellDataFeatures<SkillFilter, Number> arg0) {
                return arg0.getValue().maxValueProperty();
            }
        });
        skillFiltersTableView.getItems().
                addListener(new ListChangeListener<SkillFilter>() {
            @Override
            public void onChanged(Change<? extends SkillFilter> arg0) {
                while (arg0.next()) {
                    for (SkillFilter skillFilter : arg0.getAddedSubList()) {
                        final Skill skill = skillFilter.getSkill();
                        TableColumn<Official, Number> skillColumn = new TableColumn<>(skill.
                                getName());
                        skillColumn.
                                setCellValueFactory(new Callback<CellDataFeatures<Official, Number>, ObservableValue<Number>>() {
                            @Override
                            public ObservableValue<Number> call(CellDataFeatures<Official, Number> arg0) {
                                Official official = arg0.getValue();
                                IntegerBinding selectInteger = Bindings.
                                        selectInteger(official.
                                        skillLevelsProperty().valueAt(skill), "level");
                                selectInteger.
                                addListener(new ChangeListener<Number>() {
                            @Override
                            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                                updateFilters(null);
                            }
                        });
                                return selectInteger;
                            }
                        });
                        officialsFilteredTableView.getColumns().add(skillColumn);
                    }

                    for (SkillFilter skillFilter : arg0.getRemoved()) {
                        final Skill skill = skillFilter.getSkill();
                        TableColumn<Official, Number> column = skillFiltersColumn.
                                remove(skill);
                        if (column != null) {
                            officialsFilteredTableView.getColumns().
                                    remove(column);
                        }
                    }
                }
            }
        });
        Bindings.
                bindContent(skillFiltersMatcher.getMatchers(), skillFiltersTableView.
                getItems());
//</editor-fold>

        final ObjectBinding<Official> selectedPersonnel = Bindings.
                select(officialsFilteredTableView.
                selectionModelProperty(), "selectedItem");

        detailsPaneController.officialProperty().bind(selectedPersonnel);
        skillsListView.
                setCellFactory(new Callback<ListView<SkillLevel>, ListCell<SkillLevel>>() {
            @Override
            public ListCell<SkillLevel> call(ListView<SkillLevel> arg0) {
                final ListCell<SkillLevel> toReturn = new ListCell<>();
                final ObjectProperty<SkillLevel> itemProperty = toReturn.
                        itemProperty();

                final StringExpression concat = Bindings.selectString(toReturn.
                        itemProperty(), "skill", "name").
                        concat(": ").concat(Bindings.selectInteger(toReturn.
                        itemProperty(), "level").asString()).
                        concat(" %");

                //TODO try to use a binding.
                concat.addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable arg0) {
                        toReturn.setText(concat.getValueSafe());
                    }
                });
//                toReturn.textProperty().bind(concat);
                return toReturn;
            }
        });
        selectedPersonnel.addListener(new ChangeListener<Official>() {
            @Override
            public void changed(ObservableValue<? extends Official> arg0, Official arg1, Official arg2) {
                updateSkills();
            }
        });

        updateFilters(null);



        //<editor-fold defaultstate="collapsed" desc="Initialize assignements ListView and Filters">

        final ListBinding<PersonnelAssignment> filteredAssignementBinding = new ListBinding<PersonnelAssignment>() {
            final ObjectBinding<ObservableList<PersonnelAssignment>> internalBinding = Bindings.
                select(assignmentsFilterComboBox.getSelectionModel().
                selectedItemProperty(), "assignments");
            {
                bind(internalBinding);
            }
            @Override
            protected ObservableList<PersonnelAssignment> computeValue() {
                return internalBinding.get();
            }
        };
        
        Bindings.bindContent(assigmentsListView.getItems(), filteredAssignementBinding);
        assigmentsListView.
                setCellFactory(new PropertyListCellFactory<PersonnelAssignment>("name", null));

        assignmentsFilterComboBox.getItems().clear();
        assignmentsFilterComboBox.getItems().addAll(assignementFilters);
        assignmentsFilterComboBox.setCellFactory(new PropertyListCellFactory<AssignementFilter>("name", null));

        createTeamMenuItem.disableProperty().bind(Bindings.
                select(officialsFilteredTableView.selectionModelProperty(), "selectedItem").
                isNull());
    }

    //TODO Try to use a filtered List
    public void updateFilters(ActionEvent event) {
        for (Official official : officialFilteredList) {
            official.getSkillLevels().removeListener(mapChangeListener);
        }
        officialFilteredList.clear();
        for (Official official : gameData.getOfficials()) {
            if (compositeMatcher.matches(official)) {
                officialFilteredList.add(official);

                official.getSkillLevels().addListener(mapChangeListener);

            }
        }
        updateSkills();
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

    //TODO try to use a binding
    private void updateSkills() {
        skillsListView.getItems().clear();
        Official selectedMember = officialsFilteredTableView.
                getSelectionModel().getSelectedItem();
        if (selectedMember != null) {
            skillsListView.getItems().addAll(selectedMember.getSkillLevels().
                    values());
            Collections.
            sort(skillsListView.getItems(), new Comparator<SkillLevel>() {
        @Override
        public int compare(SkillLevel arg0, SkillLevel arg1) {
            return arg0.getSkill().getName().compareTo(arg1.getSkill().
                    getName());
        }
    });
        }
    }

    public void createTeam(ActionEvent event) {
        Official selectedPersonnel = officialsFilteredTableView.
                getSelectionModel().getSelectedItem();
        assert selectedPersonnel != null;
        teamController.
                createTeamWithLeader(selectedPersonnel, FieldTeamType.GEOLOGICAL);
    }
}
