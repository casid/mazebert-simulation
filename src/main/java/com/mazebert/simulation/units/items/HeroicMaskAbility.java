package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class HeroicMaskAbility extends Ability<Tower> {
    private final float bonus;

    public HeroicMaskAbility() {
        if (Sim.context().version >= Sim.v20) {
            bonus = 0.1f;
        } else {
            bonus = 0.05f;
        }
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.addExperienceModifier(bonus);
        unit.addAddedRelativeBaseDamage(bonus);
        unit.addCritChance(bonus);
        unit.addLuck(bonus);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.addLuck(-bonus);
        unit.addCritChance(-bonus);
        unit.addAddedRelativeBaseDamage(-bonus);
        unit.addExperienceModifier(-bonus);
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
        return format.percentWithSignAndUnit(bonus) + " experience\n" +
                format.percentWithSignAndUnit(bonus) + " damage\n" +
                format.percentWithSignAndUnit(bonus) + " crit chance\n" +
                format.percentWithSignAndUnit(bonus) + " luck";
    }
}
