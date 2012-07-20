package com.newdawn.gui.personnel;

import com.newdawn.model.personnel.PersonnelMember;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 *
 * @author Pierrick Puimean-Chieze
 */

public class PersonnelDetailsPane
        implements Initializable {
    
    @FXML //  fx:id="assignementTextField"
    private TextField assignementTextField; // Value injected by FXMLLoader
    @FXML //  fx:id="designationTextField"
    private TextField designationTextField; // Value injected by FXMLLoader
    @FXML //  fx:id="locationTestField"
    private TextField locationTestField; // Value injected by FXMLLoader

    private ObjectProperty<PersonnelMember> personnelMemberProperty;

    public ObjectProperty<PersonnelMember> personnelMemberProperty() {
        if (personnelMemberProperty == null) {
            personnelMemberProperty = new SimpleObjectProperty<>(this, "personnelMember");
        }
        return personnelMemberProperty;
    }

    /**
     * Get the value of personnelMember
     *
     * @return the value of personnelMember
     */
    public PersonnelMember getPersonnelMember() {
        return personnelMemberProperty().getValue();
    }

    /**
     * Set the value of personnelMember
     *
     * @param personnelMember new value of personnelMember
     */
    public void setPersonnelMember(PersonnelMember personnelMember) {
        this.personnelMemberProperty().setValue(personnelMember);
    }


    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert assignementTextField != null : "fx:id=\"assignementTextField\" was not injected: check your FXML file 'PersonnelDetailsPane.fxml'.";
        assert designationTextField != null : "fx:id=\"designationTextField\" was not injected: check your FXML file 'PersonnelDetailsPane.fxml'.";
        assert locationTestField != null : "fx:id=\"locationTestField\" was not injected: check your FXML file 'PersonnelDetailsPane.fxml'.";
        // initialize your logic here: all @FXML variables will have been injected
        final StringExpression concat = Bindings.selectString(personnelMemberProperty(), "rank", "designation").concat(" ").concat(Bindings.selectString(personnelMemberProperty(), "name"));
        designationTextField.textProperty().bind(concat);

    }
}
