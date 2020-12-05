package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.projectiles.ChainViewType;
import com.mazebert.simulation.units.abilities.ChainAbility;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class MjoelnirChainAbility extends ChainAbility {
    private static final int CREEPS = 3;

    private int attacks;
    private float damage = 0.1f;

    public MjoelnirChainAbility() {
        super(ChainViewType.Lightning, CREEPS - 1);
        if (Sim.context().version >= Sim.vDoL) {
            damage = 0.24f;
        }
    }

    @Override
    protected void chain(Creep target, double damage) {
        if (++attacks >= 3) {
            attacks = 0;
            super.chain(target, damage * this.damage);
        }
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Mjoelnir's Wrath";
    }

    @Override
    public String getDescription() {
        return "Every third hit " +
                "chain lightning jumps to " + CREEPS + " creeps and deals " +
                format.percent(damage) + "% of the carrier's damage.";
    }
}