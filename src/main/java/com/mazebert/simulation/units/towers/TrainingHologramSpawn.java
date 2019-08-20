package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.Wave;
import com.mazebert.simulation.WaveOrigin;
import com.mazebert.simulation.listeners.OnDeathListener;
import com.mazebert.simulation.listeners.OnRoundStartedListener;
import com.mazebert.simulation.listeners.OnUnitAddedListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.abilities.FollowPathCreepStaticAbility;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.effects.HologramEffect;

public strictfp class TrainingHologramSpawn extends Ability<Tower> implements OnRoundStartedListener, OnUnitAddedListener, OnDeathListener {
    public static final float XP = 3.0f;
    public static final float XP_PER_LEVEL = 0.1f;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onUnitAdded.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        Sim.context().simulationListeners.onRoundStarted.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onUnitAdded(Unit unit) {
        Sim.context().simulationListeners.onRoundStarted.add(this);
    }

    @Override
    public void onRoundStarted(Wave wave) {
        Creep dummy = new Creep(new FollowPathCreepStaticAbility());
        dummy.addAbility(new HologramEffect());
        dummy.setX(getUnit().getX());
        dummy.setY(getUnit().getY());

        Wave dummyWave = new Wave();
        dummyWave.origin = WaveOrigin.TrainingDummy;
        dummyWave.type = wave.type;
        dummyWave.creepType = wave.creepType;
        dummyWave.armorType = wave.armorType;

        dummy.setWizard(getUnit().getWizard());
        dummy.setWave(dummyWave);
        dummy.setHealth(1);
        dummy.setMaxHealth(1);
        dummy.setGold(0);
        dummy.setArmor(1);
        dummy.setExperience(XP + getUnit().getLevel() * XP_PER_LEVEL);
        dummy.setType(dummyWave.creepType);
        dummy.onDeath.add(this);

        Sim.context().waveSpawner.spawnCreep(dummy, null, 0);
    }

    @Override
    public void onDeath(Creep creep) {
        getUnit().addExperience(creep.getExperience());
        creep.onDeath.remove(this);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Creep hologram";
    }

    @Override
    public String getDescription() {
        return "When starting a wave, a creep hologram with 1 health is summoned. The creep grants " + format.experienceWithSignAndUnit(XP) + " to the hologram and the tower destroying it.";
    }

    @Override
    public String getLevelBonus() {
        return format.experienceWithSignAndUnit(XP_PER_LEVEL) + " per tower level";
    }

    @Override
    public String getIconFile() {
        return "hologram_512";
    }
}
