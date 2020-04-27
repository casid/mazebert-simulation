package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.WaveType;
import com.mazebert.simulation.listeners.OnAttackListener;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.quests.KnusperHexeQuest;

public strictfp class KnusperHexeEat extends Ability<KnusperHexe> implements OnAttackListener {
    private static final float chance = 0.2f;
    private static final float chancePerLevel = 0.002f;

    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    private int creepsEaten;

    @Override
    protected void initialize(KnusperHexe unit) {
        super.initialize(unit);
        unit.onAttack.add(this);
    }

    @Override
    protected void dispose(KnusperHexe unit) {
        unit.onAttack.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onAttack(Creep target) {
        if (target.getWave().type == WaveType.Mass && getUnit().isAbilityTriggered(chance + getUnit().getLevel() * chancePerLevel)) {
            getUnit().kill(target);

            ++creepsEaten;
            getUnit().onChildEaten.dispatch(target);
            getUnit().getWizard().addQuestProgress(KnusperHexeQuest.class);

            if (simulationListeners.areNotificationsEnabled()) {
                simulationListeners.showNotification(getUnit(), "Child eaten", 0x444444);
            }
        }
    }

    public int getCreepsEaten() {
        return creepsEaten;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Knusper";
    }

    @Override
    public String getDescription() {
        return "Each time Knusperhexe attacks a mass creep, there's a " + format.percent(chance, 0) + "% chance that she kidnaps, cooks and eats it.";
    }

    @Override
    public String getLevelBonus() {
        return "+" + format.percent(chancePerLevel) + "% chance per level.";
    }

    @Override
    public String getIconFile() {
        return "cauldron_512";
    }
}
