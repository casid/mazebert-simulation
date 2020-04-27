package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.listeners.OnAttackListener;
import com.mazebert.simulation.plugins.random.RandomPlugin;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class SatelliteCosts extends Ability<Tower> implements OnAttackListener {
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;
    private final RandomPlugin randomPlugin = Sim.context().randomPlugin;

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
        Tower tower = getUnit();
        long costs = -StrictMath.round((0.7 - 0.002 * tower.getLevel()) * (tower.getMinBaseDamage() + randomPlugin.getFloatAbs() * (tower.getMaxBaseDamage() - tower.getMinBaseDamage())));
        if (simulationListeners.areNotificationsEnabled()) {
            simulationListeners.showNotification(tower, format.goldGain(costs), 0xffff00);
        }
        tower.getWizard().addGold(costs);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Sparkling diamonds";
    }

    @Override
    public String getDescription() {
        return "Each attack costs 70% of the Satellite's base damage in " + getCurrency().pluralLowercase + ".";
    }

    @Override
    public String getIconFile() {
        return "gem_512";
    }

    @Override
    public String getLevelBonus() {
        return "-0.2% " + getCurrency().singularLowercase + " cost per level.";
    }
}
