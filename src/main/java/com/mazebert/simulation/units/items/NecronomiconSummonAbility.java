package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.listeners.OnUnitRemovedListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.ActiveAbility;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class NecronomiconSummonAbility extends ActiveAbility implements OnUnitRemovedListener {
    private static final int requiredSouls = 50;
    private int souls = 50;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        Sim.context().simulationListeners.onUnitRemoved.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        Sim.context().simulationListeners.onUnitRemoved.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onUnitRemoved(Unit unit) {
        if (unit instanceof Creep) {
            Creep creep = (Creep)unit;
            if (creep.isEldritch() && creep.isDead()) {
                souls += StrictMath.round(1.0f * getUnit().getEldritchCardModifier());
            }
        }
    }

    @Override
    public float getReadyProgress() {
        if (souls >= requiredSouls) {
            return 1.0f;
        }
        return (float)souls / requiredSouls;
    }

    @Override
    public void activate() {
        souls -= requiredSouls;
        Sim.context().waveSpawner.spawnExtraCultistsWave();
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Klaatu Barada Nikto (Active ability)";
    }

    @Override
    public String getDescription() {
        return "Sacrifice " + requiredSouls + " souls to summon an extra wave of eldritch cultists. Souls: " + souls + ".";
    }

    @Override
    public String getLevelBonus() {
        return "+1 soul whenever a cultist dies.";
    }
}
