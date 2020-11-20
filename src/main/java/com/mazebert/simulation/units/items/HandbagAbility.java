package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.listeners.OnGenderChangedListener;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.ItemChanceWithLevelBonusAbility;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class HandbagAbility extends ItemChanceWithLevelBonusAbility implements OnGenderChangedListener {

    private static float getChancePerLevel() {
        if (Sim.context().version >= Sim.vRoC) {
            return 0.003f;
        }
        return 0.001f;
    }

    public HandbagAbility() {
        super(0.3f, getChancePerLevel());
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onGenderChanged.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onGenderChanged.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onGenderChanged(Unit unit) {
        updateBonus();
    }

    @Override
    protected float calculateBonus() {
        if (getUnit().getGender() == Gender.Female) {
            return super.calculateBonus();
        } else {
            return 0.0f;
        }
    }

    @Override
    public String getTitle() {
        return "Shopping Queen";
    }

    @Override
    public String getDescription() {
        return "Only works on female towers. Male towers get no bonus (beyond looking silly).";
    }
}
