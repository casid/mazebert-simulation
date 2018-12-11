package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.Ability;

public class MuliBooze extends Ability<Tower> {
    public static final float CHANCE = 0.1f;
    public static final int GOLD = 20;

    public static final float CRIT_CHANCE_ADD = 0.001f;
    public static final float CRIT_DAMAGE_ADD = 0.005f;

    public static final float HANGOVER_DURATION = 2.0f;

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Banana liquor";
    }

    @Override
    public String getDescription() {
        return format.percent(CHANCE) + "% chance to spend " + format.gold(GOLD, getCurrency()) + " on liquor when he's not busy:";
    }

    @Override
    public String getLevelBonus() {
        return format.percentWithSignAndUnit(CRIT_CHANCE_ADD) + " crit chance\n" +
                format.percentWithSignAndUnit(CRIT_DAMAGE_ADD) + " crit damage\n" +
                format.seconds(HANGOVER_DURATION) + "hangover on next attack";
    }

    @Override
    public String getIconFile() {
        return "cup_512";
    }
}
