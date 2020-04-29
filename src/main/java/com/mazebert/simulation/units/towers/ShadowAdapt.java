package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.ArmorType;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.listeners.OnDamageListener;
import com.mazebert.simulation.plugins.random.RandomPlugin;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class ShadowAdapt extends Ability<Tower> implements OnDamageListener {

    private static final float chance = 0.05f;
    private static final float chanceLevelBonus = 0.0005f;
    private static final float damageIncrease = 0.03f;
    private static final float damageDecrease = -0.01f;

    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;
    private final RandomPlugin randomPlugin = Sim.context().randomPlugin;

    private final float max;

    private float damageAdaptedForVex = 0.0f;
    private float damageAdaptedForFal = 0.0f;
    private float damageAdaptedForBer = 0.0f;

    public ShadowAdapt() {
        if (Sim.context().version >= Sim.vDoLEnd) {
            max = 13.0f;
        } else if (Sim.context().version >= Sim.v12) {
            max = 7.0f;
        } else {
            max = 100.0f;
        }
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onDamage.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onDamage.remove(this);
        super.dispose(unit);
    }

    public float getDamageAdaptedForBer() {
        return damageAdaptedForBer;
    }

    public float getDamageAdaptedForFal() {
        return damageAdaptedForFal;
    }

    public float getDamageAdaptedForVex() {
        return damageAdaptedForVex;
    }

    @Override
    public void onDamage(Object origin, Creep target, double damage, int multicrits) {
        if (getUnit().isAbilityTriggered(chance + chanceLevelBonus * getUnit().getLevel())) {
            ArmorType armorType = target.getWave().armorType;
            adapt(armorType);
        }
    }

    private void adapt(ArmorType armorType) {
        switch (armorType) {
            case Ber:
                if (damageAdaptedForBer < max) {
                    getUnit().addDamageAgainstBer(damageIncrease);
                    getUnit().addDamageAgainstFal(damageDecrease);
                    getUnit().addDamageAgainstVex(damageDecrease);
                    damageAdaptedForBer += damageIncrease;
                    damageAdaptedForFal += damageDecrease;
                    damageAdaptedForVex += damageDecrease;
                    if (simulationListeners.areNotificationsEnabled()) {
                        simulationListeners.showNotification(getUnit(), "Ber!", 0x5d8b4c);
                    }
                }
                break;
            case Fal:
                if (damageAdaptedForFal < max) {
                    getUnit().addDamageAgainstBer(damageDecrease);
                    getUnit().addDamageAgainstFal(damageIncrease);
                    getUnit().addDamageAgainstVex(damageDecrease);
                    damageAdaptedForBer += damageDecrease;
                    damageAdaptedForFal += damageIncrease;
                    damageAdaptedForVex += damageDecrease;
                    if (simulationListeners.areNotificationsEnabled()) {
                        simulationListeners.showNotification(getUnit(), "Fal!", 0x9da9bc);
                    }
                }
                break;
            case Vex:
                if (damageAdaptedForVex < max) {
                    getUnit().addDamageAgainstBer(damageDecrease);
                    getUnit().addDamageAgainstFal(damageDecrease);
                    getUnit().addDamageAgainstVex(damageIncrease);
                    damageAdaptedForBer += damageDecrease;
                    damageAdaptedForFal += damageDecrease;
                    damageAdaptedForVex += damageIncrease;
                    if (simulationListeners.areNotificationsEnabled()) {
                        simulationListeners.showNotification(getUnit(), "Vex!", 0x760983);
                    }
                }
                break;
            case Zod:
                adapt(getRandomArmorType());
                break;
        }
    }

    private ArmorType getRandomArmorType() {
        float roll = randomPlugin.getFloatAbs();
        if (roll < 0.33f) {
            return ArmorType.Ber;
        } else if (roll < 0.66f) {
            return ArmorType.Fal;
        }
        return ArmorType.Vex;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Adapt";
    }

    @Override
    public String getDescription() {
        return "Whenever Shadow damages a creep, there's a " +
                format.percent(chance, 0) + "% chance to raise Shadow's damage against this armor type by " +
                format.percent(damageIncrease, 0) + "%, while lowering the damage against other armor types by " +
                format.percent(-damageDecrease, 0) + "%. " +
                "(Damaging " + format.armorType(ArmorType.Zod) + " armor counts randomly as " + format.armorType(ArmorType.Ber) + ", " + format.armorType(ArmorType.Fal) + " or " + format.armorType(ArmorType.Vex) + ")";
    }

    @Override
    public String getLevelBonus() {
        return "+" + format.percent(chanceLevelBonus, 2) + "% chance per level.\nMaximum adaption: " + format.percent(max) + "%.";
    }

    @Override
    public String getIconFile() {
        return "0041_purpledebuff_512";
    }
}
