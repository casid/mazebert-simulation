package com.mazebert.simulation.units.items;

import com.mazebert.simulation.listeners.OnKillListener;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class SeelenreisserAbility extends Ability<Tower> implements OnKillListener {
    private static final float damagePerKill = 0.01f;
    private static final float damagePerBossKill = 0.1f;
    private static final int multicrit = -1;
    private static final float critChance = -0.2f;

    private float totalDamage;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.addAddedRelativeBaseDamage(totalDamage);
        unit.addMulticrit(multicrit);
        unit.addCritChance(critChance);
        unit.onKill.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.addAddedRelativeBaseDamage(-totalDamage);
        unit.addMulticrit(-multicrit);
        unit.addCritChance(-critChance);
        unit.onKill.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onKill(Creep target) {
        if (target.isBoss()) {
            increaseTotalDamage(damagePerBossKill);
        } else {
            increaseTotalDamage(damagePerKill);
        }
    }

    private void increaseTotalDamage(float amount) {
        totalDamage += amount;
        getUnit().addAddedRelativeBaseDamage(amount);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Soul Steal";
    }

    @Override
    public String getLevelBonus() {
        return "+" + format.percent(totalDamage) + "% damage (itembound).\n" +
                format.percentWithSignAndUnit(critChance) + " crit chance.\n" +
                multicrit + " multicrit.\n" +
                format.percentWithSignAndUnit(damagePerKill) + " damage per kill (" + format.percent(damagePerBossKill) +  "% for bosses)";
    }
}
