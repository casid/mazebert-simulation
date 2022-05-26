package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.*;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.listeners.OnDeathListener;
import com.mazebert.simulation.listeners.OnRoundStartedListener;
import com.mazebert.simulation.listeners.OnUnitAddedListener;
import com.mazebert.simulation.systems.ExperienceSystem;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.abilities.FollowPathCreepStaticAbility;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.effects.HologramEffect;

public strictfp class TrainingHologramSpawn extends Ability<Tower> implements OnRoundStartedListener, OnUnitAddedListener, OnDeathListener {
    public static final float XP = 5.0f;

    private final float xpPerLevel = Sim.context().version >= Sim.vDoLEnd ? 0.5f : 0.2f;

    private final ExperienceSystem experienceSystem = Sim.context().experienceSystem;
    private final UnitGateway unitGateway = Sim.context().unitGateway;

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
        if (wave.type == WaveType.TimeLord) {
            return;
        }

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
        dummy.setMaxHealth(1);
        dummy.setGold(0);
        dummy.setArmor(1);
        dummy.setExperience(XP + getUnit().getLevel() * xpPerLevel);
        dummy.setType(dummyWave.creepType);
        dummy.setDealsDamage(false);
        dummy.onDeath.add(this);

        Sim.context().waveSpawner.spawnCreep(dummy, null, 0);
    }

    @Override
    public void onDeath(Creep creep) {
        Tower hologram = getUnit();
        if (hologram != null) {
            experienceSystem.grantExperience(hologram, creep.getExperience());

            if (Sim.context().version >= Sim.vRnREnd) {
                unitGateway.forEachTower(tower -> grantExperienceToGuard(creep, tower));
            }

            creep.onDeath.remove(this);
        }
    }

    private void grantExperienceToGuard(Creep creep, Tower tower) {
        if (tower instanceof Guard) {
            experienceSystem.grantExperience(tower, creep.getExperience());
        }
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
        String bonus = format.experienceWithSignAndUnit(xpPerLevel) + " per tower level.";
        if (Sim.context().version >= Sim.vRnREnd) {
            bonus += "\nAll " + format.card(TowerType.Guard) + " towers gain XP from creep holograms.";
        }
        return bonus;
    }

    @Override
    public String getIconFile() {
        return "hologram_512";
    }
}
