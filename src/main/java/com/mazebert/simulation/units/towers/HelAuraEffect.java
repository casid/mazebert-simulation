package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.listeners.OnDeathListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.StackableByOriginAbility;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class HelAuraEffect extends StackableByOriginAbility<Creep> implements OnDeathListener {

    public static float SPEED_MODIFIER = 0.7f;
    public static int CREEPS_PER_ARMOR = 15;

    private int totalReduction;
    private float totalSpeedModifier = 1.0f;

    @Override
    protected void initialize(Creep unit) {
        super.initialize(unit);
        unit.onDeath.add(this);
    }

    @Override
    protected void dispose(Creep unit) {
        unit.onDeath.remove(this);

        unit.addArmor(totalReduction);
        unit.setSpeedModifier(unit.getSpeedModifier() / totalSpeedModifier);

        super.dispose(unit);
    }

    public void applyEffect(int baseReduction) {
        totalReduction += baseReduction;
        getUnit().addArmor(-baseReduction);

        totalSpeedModifier *= SPEED_MODIFIER;
        getUnit().setSpeedModifier(SPEED_MODIFIER * getUnit().getSpeedModifier());
    }

    @Override
    public void onDeath(Creep creep) {
        Unit origin = (Unit) getOrigin();

        HelAura aura = origin.getAbility(HelAura.class);

        aura.increaseHelheimPopulation();
    }
}
