package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.units.abilities.InstantDamageAbility;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class LokiDamage extends InstantDamageAbility {

    private static final int MAX_STABS = 1024;
    private static final float NOTICE_CHANCE = 0.9f;
    private static final int MULTILUCK = 1;

    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.addMultiluck(MULTILUCK);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.addMultiluck(-MULTILUCK);
        super.dispose(unit);
    }

    @Override
    public void onAttack(Creep target) {
        Tower tower = getUnit();

        double damage = damageSystem.dealDamage(this, tower, target);
        if (damage <= 0) {
            return;
        }

        int additionalStabs = 0;
        for (int i = 0; i <= MAX_STABS; ++i) {
            if (tower.isNegativeAbilityTriggered(NOTICE_CHANCE)) {
                break;
            } else {
                ++additionalStabs;
            }
        }

        if (additionalStabs > 0) {
            damageSystem.dealDamage(this, tower, target, damage * additionalStabs, 0, false);

            if (simulationListeners.areNotificationsEnabled()) {
                simulationListeners.showNotification(tower, (additionalStabs + 1) + "x", 0xca26f9);
            }
        }
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Unseen Strike";
    }

    @Override
    public String getDescription() {
        return "Unseen Strike repeats until the creep notices. Sadly, there's a " + format.percent(NOTICE_CHANCE) + "% chance creeps notice being stabbed in the stomach.";
    }

    @Override
    public String getLevelBonus() {
        return "+" + MULTILUCK + " multiluck";
    }

    @Override
    public String getIconFile() {
        return "0028_hide_512";
    }
}
