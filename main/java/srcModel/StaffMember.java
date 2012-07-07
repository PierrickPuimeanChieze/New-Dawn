
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.ObservableMap;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class StaffMember {
    private String name;
    private MapProperty<Skill, IntegerProperty> skillLevelsProperty;

    public MapProperty<Skill, IntegerProperty> skillLevelsProperty() {
        if (skillLevelsProperty == null) {
            skillLevelsProperty = new SimpleMapProperty<>(this, "skillLevels");
        }
        return skillLevelsProperty;
    }

    /**
     * Get the value of skillLevels
     *
     * @return the value of skillLevels
     */
    public ObservableMap<Skill, IntegerProperty> getSkillLevels() {
        return skillLevelsProperty().getValue();
    }

    /**
     * Set the value of skillLevels
     *
     * @param skillLevels new value of skillLevels
     */
    public void setSkillLevels(ObservableMap<Skill, IntegerProperty> skillLevels) {
        this.skillLevelsProperty().setValue(skillLevels);
    }

}
