package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class EldritchItemAbility extends StackableAbility<Tower> {

    private static final float CHANCE = 0.01f;

    private float currentBonus = 0.0f;

    @Override
    protected void updateStacks() {
        Sim.context().waveGateway.addCultistChance(-currentBonus);
        currentBonus = CHANCE * getStackCount() * getUnit().getEldritchCardModifier();
        Sim.context().waveGateway.addCultistChance(currentBonus);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return format.colored("Rising Horror", 0x038174);
    }

    @Override
    public String getDescription() {
        return format.colored(format.percentWithSignAndUnit(CHANCE) + " chance to spawn eldritch cultists.", 0x038174);
    }
}
