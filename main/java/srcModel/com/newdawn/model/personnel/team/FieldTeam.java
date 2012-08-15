package com.newdawn.model.personnel.team;

import com.newdawn.controllers.TeamController;
import com.newdawn.model.personnel.PersonnelLocalisation;
import com.newdawn.model.personnel.Official;
import com.newdawn.model.personnel.Skill;
import com.newdawn.model.personnel.SkillLevel;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.LongBinding;
import javafx.beans.binding.MapBinding;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyLongProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableMap;
import viewerfx.ViewerFX;

/**
 * 
 * @author Pierrick Puimean-Chieze
 */
public abstract class FieldTeam extends Team {

	// TODO add an assignment property
	private int internalCounter = 0;
	private StringProperty nameProperty;
	private ObjectProperty<PersonnelLocalisation> localizationProperty;
	private LongProperty cumulatedSkillLevelProperty;
	private LongBinding cumulatedSkillLevelBinding;
	private ReadOnlyObjectProperty<Skill> teamSkillProperty;
	private ObjectProperty<PersonnelLocalisation> assignementProperty = new SimpleObjectProperty<>(
			this, "assignement");
	private StringProperty visualNameProperty;

	public ReadOnlyObjectProperty<PersonnelLocalisation> assignementProperty() {
		return assignementProperty;
	}

	/**
	 * Get the value of assignementProperty
	 * 
	 * @return the value of assignementProperty
	 */
	public PersonnelLocalisation getAssignementProperty() {
		return assignementProperty().getValue();
	}

	/**
	 * Set the value of assignementProperty
	 * 
	 * @param assignementProperty
	 *            new value of assignementProperty
	 */
	public void setAssignementProperty(PersonnelLocalisation assignementProperty) {
		this.assignementProperty.setValue(assignementProperty);
	}

	public FieldTeam() {
	}

	public ReadOnlyLongProperty cumulatedSkillLevelProperty() {
		if (cumulatedSkillLevelProperty == null) {
			cumulatedSkillLevelProperty = new SimpleLongProperty(this,
					"cumulatedSkillLevel");
			cumulatedSkillLevelProperty.bind(getCumulatedSkillLevelBinding());
		}
		return cumulatedSkillLevelProperty;
	}

	public LongBinding getCumulatedSkillLevelBinding() {
		if (cumulatedSkillLevelBinding == null) {
			cumulatedSkillLevelBinding = new CumulatedSkillLevelBinding();
		}
		return cumulatedSkillLevelBinding;
	}

	public StringProperty nameProperty() {
		if (nameProperty == null) {
			nameProperty = new SimpleStringProperty(this, "name");
		}
		return nameProperty;
	}

	/**
	 * Get the value of name
	 * 
	 * @return the value of name
	 */
	public String getName() {
		return nameProperty().getValue();
	}

	/**
	 * Set the value of name
	 * 
	 * @param name
	 *            new value of name
	 */
	public void setName(String name) {
		this.nameProperty().setValue(name);
	}

	public ObjectProperty<PersonnelLocalisation> localizationProperty() {
		if (localizationProperty == null) {
			localizationProperty = new SimpleObjectProperty<>(this,
					"localization");
		}
		return localizationProperty;
	}

	/**
	 * Get the value of localization
	 * 
	 * @return the value of localization
	 */
	public PersonnelLocalisation getLocalization() {
		return localizationProperty().getValue();
	}

	/**
	 * Set the value of localization
	 * 
	 * @param localization
	 *            new value of localization
	 */
	public void setLocalization(PersonnelLocalisation localization) {
		this.localizationProperty().setValue(localization);
	}

	private int calculateMaxSize() {
		Skill leadershipSkill = ViewerFX.getCurrentApplication()
				.getSprintContainer().getBean("leadership", Skill.class);
		int leaderLeadershipLevel = getLeader().getSkillLevels()
				.get(leadershipSkill).getLevel();
		int additionalTeamMemberSize = (int) (leaderLeadershipLevel / 12.5);
		return 2 + additionalTeamMemberSize;
	}

	@Override
	public String[] validateAddition(Official teamMember) {
		int maxSize = calculateMaxSize();
		if (getTeamMembers().size() >= maxSize) {
			return new String[] { "Max size reached" };
		}
		return new String[0];
	}

	public int getInternalCounter() {
		return internalCounter;
	}

	public void setInternalCounter(int internalCounter) {
		this.internalCounter = internalCounter;
	}

	public long getCumulatedSkillLevel() {
		return cumulatedSkillLevelProperty().get();

	}

	public abstract String getTeamSkillName();

	public Skill getTeamSkill() {
		return teamSkillProperty().get();
	}

	public ReadOnlyObjectProperty<Skill> teamSkillProperty() {
		if (teamSkillProperty == null) {
			Skill teamSkill = ViewerFX.getCurrentApplication()
					.getSprintContainer()
					.getBean(getTeamSkillName(), Skill.class);
			teamSkillProperty = new SimpleObjectProperty<>(this, "teamSkill",
					teamSkill);
		}
		return teamSkillProperty;
	}

	private class CumulatedSkillLevelBinding extends LongBinding implements
			ListChangeListener<Official> {

		private Map<Official, IntegerBinding> memberTeamSkillLevelBindings = new HashMap<>();

		public CumulatedSkillLevelBinding() {
			Skill leadershipSkill = ViewerFX.getCurrentApplication()
					.getSprintContainer().getBean("leadership", Skill.class);

			// <editor-fold defaultstate="collapsed"
			// desc="Binding for the change of leader, or change of skill for the leader">
			MapBinding<Skill, SkillLevel> leaderSkillLevelsBinding = new SkillLevelBindings(
					leaderProperty());
			final IntegerBinding leaderLeadershipSkillLevelValueBinding = Bindings
					.selectInteger(Bindings.valueAt(leaderSkillLevelsBinding,
							leadershipSkill), "level");
			final IntegerBinding leaderTeamSkillLevelValueBinding = Bindings
					.selectInteger(Bindings.valueAt(leaderSkillLevelsBinding,
							teamSkillProperty()), "level");

			bind(leaderLeadershipSkillLevelValueBinding,
					leaderTeamSkillLevelValueBinding);
			// </editor-fold>
			for (Official member : getTeamMembers()) {
				final IntegerBinding memberTeamSkillLevelBinding = Bindings
						.selectInteger(Bindings.valueAt(
								member.skillLevelsProperty(), getTeamSkill()),
								"level");
				bind(memberTeamSkillLevelBinding);
				memberTeamSkillLevelBindings.put(member,
						memberTeamSkillLevelBinding);

			}
			getTeamMembers().addListener(this);
		}

		// TODO try to "bind" the binding
		@Override
		protected long computeValue() {
			Skill teamSkill = getTeamSkill();
			Skill leadershipSkill = ViewerFX.getCurrentApplication()
					.getSprintContainer().getBean("leadership", Skill.class);
			final SkillLevel leaderLeadershipSkillLevel = getLeader()
					.getSkillLevels().get(leadershipSkill);
			double leaderLeadershipSkillLevelValue = leaderLeadershipSkillLevel == null ? 0
					: leaderLeadershipSkillLevel.getLevel();
			final SkillLevel leaderTeamSkillLevel = getLeader()
					.getSkillLevels().get(teamSkill);
			double leaderTeamSkillLevelValue = leaderTeamSkillLevel == null ? 0
					: leaderTeamSkillLevel.getLevel();
			double cumulatedSkillLevel = 0.0;
			for (Official teamMember : getTeamMembers()) {
				cumulatedSkillLevel += teamMember.getSkillLevels()
						.get(teamSkill).getLevel();
			}
			cumulatedSkillLevel += ((leaderTeamSkillLevelValue / 100.0) * leaderLeadershipSkillLevelValue)
					* getTeamMembers().size();
			cumulatedSkillLevel += leaderTeamSkillLevelValue
					/ (getTeamMembers().size() + 1);
			return Math.round(cumulatedSkillLevel);
		}

		@Override
		public void onChanged(Change<? extends Official> change) {
			while (change.next()) {
				if (change.wasAdded()) {
					for (Official member : change.getAddedSubList()) {
						final IntegerBinding memberTeamSkillLevelBinding = Bindings
								.selectInteger(Bindings.valueAt(
										member.skillLevelsProperty(),
										getTeamSkill()), "level");
						bind(memberTeamSkillLevelBinding);
						memberTeamSkillLevelBindings.put(member,
								memberTeamSkillLevelBinding);
					}
				}
				if (change.wasRemoved()) {
					for (Official member : change.getRemoved()) {
						final IntegerBinding memberTeamSkillLevelBinding = memberTeamSkillLevelBindings
								.remove(member);
						unbind(memberTeamSkillLevelBinding);
					}
				}
			}
			invalidate();
		}

		private class SkillLevelBindings extends MapBinding<Skill, SkillLevel> {

			final ObjectBinding<ObservableMap<Skill, SkillLevel>> skillLevelsBinding;

			public SkillLevelBindings(
					ObjectProperty<Official> personnelToObserve) {
				skillLevelsBinding = Bindings.select(personnelToObserve,
						"skillLevels");
				bind(skillLevelsBinding);
			}

			@Override
			protected ObservableMap<Skill, SkillLevel> computeValue() {
				return skillLevelsBinding.get();
			}
		}
	}

	@Override
	public ReadOnlyStringProperty visualNameProperty() {
		if (visualNameProperty == null) {
			visualNameProperty = new SimpleStringProperty(this, "visualName");
			visualNameProperty.bind(nameProperty());
		}
		return visualNameProperty;
	}

	public abstract TeamController.FieldTeamType getType();
}
