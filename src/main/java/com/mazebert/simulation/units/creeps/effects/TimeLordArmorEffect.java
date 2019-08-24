package com.mazebert.simulation.units.creeps.effects;

import com.mazebert.simulation.ArmorType;
import com.mazebert.simulation.listeners.OnUpdateListener;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class TimeLordArmorEffect extends Ability<Creep> implements OnUpdateListener {
    public static final float TOGGLE_INTERVAL = 2.0f;

    private final ArmorType[] armorTypes = {ArmorType.Zod, ArmorType.Ber, ArmorType.Fal, ArmorType.Vex};

    private int currentArmorTypeIndex;
    private float secondsPassed;

    @Override
    protected void initialize(Creep unit) {
        super.initialize(unit);
        unit.onUpdate.add(this);
    }

    @Override
    protected void dispose(Creep unit) {
        unit.onUpdate.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onUpdate(float dt) {
        secondsPassed += dt;
        if (secondsPassed >= TOGGLE_INTERVAL) {
            secondsPassed -= TOGGLE_INTERVAL;

            if (++currentArmorTypeIndex >= armorTypes.length) {
                currentArmorTypeIndex = 0;
            }

            getUnit().getWave().armorType = armorTypes[currentArmorTypeIndex];
        }
    }
}
