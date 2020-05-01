package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Balancing;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class UnicornTearsAbility extends Ability<Tower> {

    private int levels;

    public void setLevels(int levels) {
        this.levels = levels;
    }

    public int getLevels() {
        return levels;
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);

        float xp = Balancing.getTowerExperienceForLevel(getUnit().getLevel() + getLevelsConsideringPotionEffeciveness());
        getUnit().setExperience(xp + 1.0f);
    }

    private int getLevelsConsideringPotionEffeciveness() {
        return StrictMath.round(getUnit().getPotionEffectiveness() * levels);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Pepperidge Farm Remembers";
    }

    @Override
    public String getDescription() {
        return "Close your eyes, as you taste all the colors of the rainbow.";
    }

    @Override
    public String getLevelBonus() {
        if (levels == 1) {
            return "+" + levels + " level.";
        }
        return "+" + levels + " levels.";
    }
}
