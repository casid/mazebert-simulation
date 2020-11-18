package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.towers.Tower;

public strictfp abstract class EldritchItemAbility extends StackableAbility<Tower> {

    private final float chance;
    private float currentBonus = 0.0f;

    public EldritchItemAbility(float chance) {
        this.chance = chance;
    }

    @Override
    protected void updateStacks() {
        Sim.context().waveGateway.addCultistChance(-currentBonus);
        currentBonus = chance * getStackCount() * getUnit().getEldritchCardModifier();
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
        return format.colored(format.percentWithSignAndUnit(chance) + " chance to spawn eldritch cultists.", 0x038174);
    }
}
