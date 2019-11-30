package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.abilities.StackableAbility;
import com.mazebert.simulation.units.towers.GuardAura;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class GuardLanceAbility extends StackableAbility<Tower> {
    private static final int ABSOLUTE_DAMAGE = 4;
    private static final float RELATIVE_DAMAGE = 0.2f;

    private int currentAbsoluteDamage;
    private float currentRelativeDamage;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        updateAuras();
    }

    @Override
    protected void dispose(Tower unit) {
        super.dispose(unit);
        updateAuras();
    }

    private void updateAuras() {
        Sim.context().unitGateway.forEachTower(t -> {
            GuardAura guardAura = t.getAbility(GuardAura.class);
            if (guardAura != null) {
                guardAura.updateAuraTargets();
            }
        });
    }

    @Override
    protected void updateStacks() {
        getUnit().addAddedAbsoluteBaseDamage(-currentAbsoluteDamage);
        currentAbsoluteDamage = ABSOLUTE_DAMAGE * getStackCount();
        getUnit().addAddedAbsoluteBaseDamage(currentAbsoluteDamage);

        getUnit().addAddedRelativeBaseDamage(-currentRelativeDamage);
        currentRelativeDamage = RELATIVE_DAMAGE * getStackCount();
        getUnit().addAddedRelativeBaseDamage(currentRelativeDamage);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Guard wannabe";
    }

    @Override
    public String getDescription() {
        return "The carrier becomes a guard.";
    }

    @Override
    public String getLevelBonus() {
        return "+" + ABSOLUTE_DAMAGE + " base damage\n" + format.percentWithSignAndUnit(RELATIVE_DAMAGE) + " damage";
    }
}
