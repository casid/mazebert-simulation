package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.Difficulty;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.listeners.OnUnitAddedListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.Ability;

public strictfp class KvotheAbility extends Ability<Hero> implements OnUnitAddedListener {

    private static final double experienceBonus = 1.0;

    @Override
    protected void initialize(Hero unit) {
        super.initialize(unit);
        unit.onUnitAdded.add(this);
    }

    @Override
    protected void dispose(Hero unit) {
        unit.onUnitAdded.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onUnitAdded(Unit unit) {
        unit.onUnitAdded.remove(this);
        if (Sim.context().difficultyGateway.getDifficulty() == Difficulty.Hard) {
            getUnit().getWizard().experienceModifier += experienceBonus;
        }
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Reward for the Brave";
    }

    @Override
    public String getDescription() {
        return format.percentWithSignAndUnit((float)experienceBonus) + " wizard experience when playing on hard difficulty.";
    }
}
