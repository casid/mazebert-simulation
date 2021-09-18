package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.projectiles.ChainViewType;
import com.mazebert.simulation.units.abilities.ChainAbility;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Thor;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class MjoelnirChainAbility extends ChainAbility {
    private static final int CREEPS = 3;

    private int attacks;
    private float damage;
    private final float initialDamage;

    private int numberOfAttacks;
    private final int initialNumberOfAttacks;

    public MjoelnirChainAbility() {
        super(ChainViewType.Lightning, CREEPS - 1);
        if (Sim.context().version >= Sim.vRnR) {
            damage = 0.16f;
        } else if (Sim.context().version >= Sim.vDoL) {
            damage = 0.24f;
        } else {
            damage = 0.1f;
        }

        if (Sim.context().version >= Sim.vRnR) {
            numberOfAttacks = 2;
        } else {
            numberOfAttacks = 3;
        }

        initialDamage = damage;
        initialNumberOfAttacks = numberOfAttacks;
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);

        if (unit instanceof Thor) {
            damage *= 2.0f;
            setMaxChains(2 * CREEPS - 1);
            numberOfAttacks = 1;
        }
    }

    @Override
    protected void dispose(Tower unit) {
        if (unit instanceof Thor) {
            damage = initialDamage;
            setMaxChains(CREEPS - 1);
            numberOfAttacks = initialNumberOfAttacks;
        }

        super.dispose(unit);
    }

    @Override
    protected void chain(Creep target, double damage) {
        if (++attacks >= numberOfAttacks) {
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
        String numberOfAttacksWording;
        if (initialNumberOfAttacks == 3) {
            numberOfAttacksWording = "third";
        } else {
            numberOfAttacksWording = "second";
        }

        return "Every " + numberOfAttacksWording + " hit " +
                "chain lightning jumps to " + (getMaxChains() + 1) + " creeps and deals " +
                format.percent(damage) + "% of the carrier's damage.";
    }
}