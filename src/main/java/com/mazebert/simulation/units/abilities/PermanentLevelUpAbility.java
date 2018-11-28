package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.Balancing;
import com.mazebert.simulation.units.towers.Tower;

public strictfp abstract class PermanentLevelUpAbility extends Ability<Tower> {
    private final int levels;

    public PermanentLevelUpAbility(int levels) {
        this.levels = levels;
    }

    @Override
    public boolean isPermanent() {
        return false; // the effect is permanent (because xp is transferred when replacing towers), but we must not re-apply the effect!
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);

        float xp = Balancing.getTowerExperienceForLevel(unit.getLevel() + levels);
        unit.setExperience(xp + 1.0f);
    }

    @Override
    public String getLevelBonus() {
        return "+ " + levels + " levels up";
    }
}
