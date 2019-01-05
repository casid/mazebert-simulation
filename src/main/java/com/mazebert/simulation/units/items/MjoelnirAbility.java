package com.mazebert.simulation.units.items;

import com.mazebert.simulation.projectiles.ChainViewType;
import com.mazebert.simulation.units.abilities.ChainAbility;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class MjoelnirAbility extends ChainAbility {
    public static final float DAMAGE = 0.1f;
    public static final int CREEPS = 3;

    private int attacks;

    public MjoelnirAbility() {
        super(ChainViewType.Lightning, CREEPS - 1);
    }

    @Override
    protected void chain(Creep target, double damage) {
        if (++attacks >= 3) {
            attacks = 0;
            super.chain(target, damage);
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
        return "Every 3rd attack " +
                "chain lightning hits up to " + CREEPS + " creeps dealing " +
                format.percent(DAMAGE) + "% of the carrier's damage.";
    }
}