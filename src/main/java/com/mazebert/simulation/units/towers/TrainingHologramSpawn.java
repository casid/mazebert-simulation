package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.Wave;
import com.mazebert.simulation.WaveOrigin;
import com.mazebert.simulation.listeners.OnUnitAddedListener;
import com.mazebert.simulation.listeners.OnWaveFinishedListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.abilities.FollowPathCreepStaticAbility;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class TrainingHologramSpawn extends Ability<Tower> implements OnWaveFinishedListener, OnUnitAddedListener {
    private static final float XP = 1f;
    private static final float XP_PER_LEVEL = 0.02f;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onUnitAdded.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        Sim.context().simulationListeners.onWaveFinished.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onUnitAdded(Unit unit) {
        Sim.context().simulationListeners.onWaveFinished.add(this);
    }

    @Override
    public void onWaveFinished(Wave wave) {
        Creep dummy = new Creep(new FollowPathCreepStaticAbility());
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

        Sim.context().waveSpawner.spawnCreep(dummy, null, 0);
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
        return "After finishing a wave, a creep hologram with 1 health is summoned. The Training Dummy and the tower who destroys the hologram both gain " + format.experienceWithSignAndUnit(XP);
    }

    @Override
    public String getLevelBonus() {
        return format.experienceWithSignAndUnit(XP_PER_LEVEL) + " per tower level";
    }

    @Override
    public String getIconFile() {
        return "01_magic_rune_circle_512";
    }
}
