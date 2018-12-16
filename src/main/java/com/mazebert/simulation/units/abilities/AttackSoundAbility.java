package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.listeners.OnAttackListener;
import com.mazebert.simulation.listeners.OnUpdateListener;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;

public class AttackSoundAbility extends Ability<Tower> implements OnAttackListener, OnUpdateListener {
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    private final String sound;
    private final String group;
    private final float volume;

    private boolean didAttack;

    public AttackSoundAbility(String sound) {
        this(sound, null, 1.0f);
    }

    public AttackSoundAbility(String sound, String group, float volume) {
        this.sound = sound;
        this.group = group;
        this.volume = volume;
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        if (simulationListeners.areNotificationsEnabled()) {
            unit.onAttack.add(this);
            unit.onUpdate.add(this);
        }
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onAttack.remove(this);
        unit.onUpdate.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onAttack(Creep target) {
        didAttack = true;
    }

    @Override
    public void onUpdate(float dt) {
        if (didAttack) {
            didAttack = false;
            simulationListeners.soundNotification(sound, group, volume);
        }
    }
}
