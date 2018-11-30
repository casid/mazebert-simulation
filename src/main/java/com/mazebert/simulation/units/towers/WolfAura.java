package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.systems.WolfSystem;
import com.mazebert.simulation.units.abilities.Ability;

public strictfp class WolfAura extends Ability<Tower> {

    private final WolfSystem wolfSystem = Sim.context().wolfSystem;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onUnitAdded.add(wolfSystem);
        unit.onUnitRemoved.add(wolfSystem);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onUnitRemoved.remove(wolfSystem);
        unit.onUnitAdded.remove(wolfSystem);
        super.dispose(unit);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Alpha wolf";
    }

    @Override
    public String getDescription() {
        return "The most experienced wolf takes the lead and gains for each tower level in the pack:";
    }

    @Override
    public String getIconFile() {
        return "0076_wolf_howl_512";
    }

    @Override
    public String getLevelBonus() {
        return "+ " + format.percent(WolfSystem.CRIT_CHANCE) + "% crit chance" +
                "+ " + format.percent(WolfSystem.CRIT_DAMAGE) + "% crit damage";
    }
}
