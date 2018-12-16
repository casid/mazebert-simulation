package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.listeners.OnDamageListener;
import com.mazebert.simulation.plugins.random.RandomPlugin;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.creeps.Creep;

public class NoviceWizardSpell extends Ability<Tower> implements OnDamageListener {
    private static final float warpSeconds = 3.0f;
    private static final float spellChance = 0.05f;
    private static final float spellChanceLevelBonus = 0.0025f;
    private static final float backfireChance = 0.25f;

    private final RandomPlugin randomPlugin = Sim.context().randomPlugin;

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
        if (getUnit().isAbilityTriggered(spellChance + getUnit().getLevel() * spellChanceLevelBonus)) {
            if (randomPlugin.getFloatAbs() < 0.5f) {
                if (getUnit().isNegativeAbilityTriggered(backfireChance)) {
                    target.warpInTime(warpSeconds);
                } else {
                    target.warpInTime(-warpSeconds);
                }
            } else {
                // TODO banish
            }
        }
    }
}
