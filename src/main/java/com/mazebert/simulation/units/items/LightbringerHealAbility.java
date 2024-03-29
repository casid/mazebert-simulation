package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.listeners.OnLevelChangedListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.PoisonAbility;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class LightbringerHealAbility extends PoisonAbility implements OnLevelChangedListener {
    private static final float HEALING_AMOUNT = 0.5f;
    private static final int MULTICRIT = 1;

    private final int baseDamagePerLevel = Sim.context().version >= Sim.vDoLEnd ? 11 : 6;

    private int currentBonus;

    public LightbringerHealAbility() {
        super(5.0f);
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.addMulticrit(MULTICRIT);
        updateBonus();
        unit.onLevelChanged.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onLevelChanged.remove(this);
        removeBonus();
        unit.addMulticrit(-MULTICRIT);
        super.dispose(unit);
    }

    @Override
    protected double calculatePoisonDamage(Creep target, double damage, int multicrits) {
        return -HEALING_AMOUNT * damage;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public void onLevelChanged(Unit unit, int oldLevel, int newLevel) {
        updateBonus();
    }

    private void updateBonus() {
        removeBonus();

        currentBonus = calculateBonus();
        getUnit().addAddedAbsoluteBaseDamage(currentBonus);
    }

    private int calculateBonus() {
        return getUnit().getLevel() * baseDamagePerLevel;
    }

    private void removeBonus() {
        getUnit().addAddedAbsoluteBaseDamage(-currentBonus);
        currentBonus = 0;
    }

    @Override
    public String getTitle() {
        return "Destruction and Redemption";
    }

    @Override
    public String getDescription() {
        return "Damaged creeps are healed by " + format.percent(HEALING_AMOUNT) + "% of damage dealt over " + format.seconds(getDuration()) + ".";
    }

    @Override
    public String getLevelBonus() {
        return "+" + baseDamagePerLevel + " base damage per level\n" + "+" + MULTICRIT + " multicrit";
    }
}
