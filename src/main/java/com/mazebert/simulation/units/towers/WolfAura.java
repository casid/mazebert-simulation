package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.systems.WolfSystem;
import com.mazebert.simulation.units.abilities.Ability;

public strictfp class WolfAura extends Ability<Tower> {


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
        return "+ " + formatPlugin.percent(WolfSystem.CRIT_CHANCE) + "% crit chance" +
                "+ " + formatPlugin.percent(WolfSystem.CRIT_DAMAGE) + "% crit damage";
    }
}
