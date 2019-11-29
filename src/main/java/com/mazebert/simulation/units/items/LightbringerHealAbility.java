package com.mazebert.simulation.units.items;

import com.mazebert.simulation.listeners.OnLevelChangedListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.PoisonAbility;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class LightbringerHealAbility extends PoisonAbility implements OnLevelChangedListener {
    private static final float HEALING_AMOUNT = 0.5f;
    private static final int MULTICRIT = 1;
    private static final int BASE_DAMAGE_PER_LEVEL = 6;

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
        return getUnit().getLevel() * BASE_DAMAGE_PER_LEVEL;
    }

    private void removeBonus() {
        getUnit().addAddedAbsoluteBaseDamage(-currentBonus);
        currentBonus = 0;
    }

    @Override
    public String getTitle() {
        return "Smite and Heal";
    }

    @Override
    public String getDescription() {
        return format.percent(HEALING_AMOUNT) + "% damage is healed over " + format.seconds(getDuration()) + ".";
    }

    @Override
    public String getLevelBonus() {
        return "+" + BASE_DAMAGE_PER_LEVEL + " base damage per level\n" + "+" + MULTICRIT + " multicrit";
    }
}
