package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.listeners.OnAttackListener;
import com.mazebert.simulation.systems.ExperienceSystem;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class ScientistExperience extends Ability<Tower> implements OnAttackListener {
    private static final float BONUS = 0.4f;
    private static final float BONUS_PER_LEVEL = 0.02f;

    private final ExperienceSystem experienceSystem = Sim.context().experienceSystem;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onAttack.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onAttack.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onAttack(Creep target) {
        experienceSystem.grantExperience(getUnit(), getUnit().getLevel() * BONUS_PER_LEVEL + BONUS);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Field Study";
    }

    @Override
    public String getDescription() {
        return "Everytime the Scientist attacks a creep he makes a note and his potions become more effective. He gains " + BONUS + " experience.";
    }

    @Override
    public String getIconFile() {
        return "magical_reagent_1_512";
    }

    @Override
    public String getLevelBonus() {
        return "+ " + BONUS_PER_LEVEL + " experience per tower level";
    }
}
