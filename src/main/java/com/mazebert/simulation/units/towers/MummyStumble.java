package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.listeners.OnAttackListener;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.effects.StunEffect;

public strictfp class MummyStumble extends Ability<Tower> implements OnAttackListener {

    private static final float chance = 0.01f;
    private static final float chanceLevelBonus = 0.001f;
    private static final float bossStunDuration = 0.5f;

    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onAttack.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onAttack.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onAttack(Creep target) {
        if (getUnit().isAbilityTriggered(chance + getUnit().getLevel() * chanceLevelBonus)) {
            if (target.getWave().type.isBoss()) {
                StunEffect stunEffect = target.addAbilityStack(StunEffect.class);
                stunEffect.setDuration(bossStunDuration);
            } else {
                getUnit().kill(target);
                if (simulationListeners.areNotificationsEnabled()) {
                    simulationListeners.showNotification(target, "DBTP!", 0x333333);
                }
            }
        }
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Death by toilet paper";
    }

    @Override
    public String getDescription() {
        return format.percent(chance) + "% chance on attack to instantly kill a creep.\nBosses can't be killed, but stumble for " + format.seconds(bossStunDuration) + ".";
    }

    @Override
    public String getIconFile() {
        return "0021_cloth_512";
    }
}
