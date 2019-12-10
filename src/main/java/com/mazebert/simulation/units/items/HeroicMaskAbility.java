package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class HeroicMaskAbility extends Ability<Tower> {
    public static final float BONUS = 0.05f;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.addExperienceModifier(BONUS);
        unit.addAddedRelativeBaseDamage(BONUS);
        unit.addCritChance(BONUS);
        unit.addLuck(BONUS);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.addLuck(-BONUS);
        unit.addCritChance(-BONUS);
        unit.addAddedRelativeBaseDamage(-BONUS);
        unit.addExperienceModifier(-BONUS);
        super.dispose(unit);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Hero Kit";
    }

    @Override
    public String getDescription() {
        return format.percentWithSignAndUnit(BONUS) + " experience\n" +
                format.percentWithSignAndUnit(BONUS) + " damage\n" +
                format.percentWithSignAndUnit(BONUS) + " crit chance\n" +
                format.percentWithSignAndUnit(BONUS) + " luck";
    }
}
