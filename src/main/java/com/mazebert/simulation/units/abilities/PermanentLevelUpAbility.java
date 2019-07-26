package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.Balancing;
import com.mazebert.simulation.units.towers.Tower;

public strictfp abstract class PermanentLevelUpAbility extends StackableAbility<Tower> {
    private final int levels;

    public PermanentLevelUpAbility(int levels) {
        this.levels = levels;
    }

    public int getLevels() {
        return levels;
    }

    @Override
    public boolean isPermanent() {
        return false; // the effect is permanent (because xp is transferred when replacing towers), but we must not re-apply the effect!
    }

    @Override
    public void addStack() {
        super.addStack();

        float xp = Balancing.getTowerExperienceForLevel(getUnit().getLevel() + getLevelsConsideringPotionEffeciveness());
        getUnit().setExperience(xp + 1.0f);
    }

    private int getLevelsConsideringPotionEffeciveness() {
        return StrictMath.round(getUnit().getPotionEffectiveness() * levels);
    }

    @Override
    protected void updateStacks() {
        // unused
    }

    @Override
    public String getLevelBonus() {
        return "+ " + levels + " levels up";
    }
}
