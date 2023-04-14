package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.listeners.OnDamageListener;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class TheRipperBloodThirst extends Ability<Tower> implements OnDamageListener {
    private static final float bonusPerCrit = 0.0005f;

    private int amount;

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

    @Override
    public void onDamage(Object origin, Creep target, double damage, int multicrits) {
        if (multicrits > 0 && isOriginalDamage(origin)) {
            getUnit().addCritDamage(bonusPerCrit);
            getUnit().addAddedRelativeBaseDamage(bonusPerCrit);
            ++amount;
        }
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Bloodthirst!";
    }

    @Override
    public String getDescription() {
        return "Upon critting The Ripper gains " + format.percent(bonusPerCrit) + "% damage / crit damage permanently.";
    }

    @Override
    public String getIconFile() {
        return "0022_bloodyweapon_512";
    }
}
