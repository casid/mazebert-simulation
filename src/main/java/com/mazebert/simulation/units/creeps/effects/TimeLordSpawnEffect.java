package com.mazebert.simulation.units.creeps.effects;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.WaveSpawner;
import com.mazebert.simulation.listeners.OnUpdateListener;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class TimeLordSpawnEffect extends Ability<Creep> implements OnUpdateListener {
    public static final float TOGGLE_INTERVAL = 16.0f;
    public static final float INITIAL_INTERVAL_PASSED = 10.0f;

    private final WaveSpawner waveSpawner = Sim.context().waveSpawner;

    private float secondsPassed = INITIAL_INTERVAL_PASSED;

    @Override
    protected void initialize(Creep unit) {
        super.initialize(unit);
        unit.onUpdate.add(this);
    }

    @Override
    protected void dispose(Creep unit) {
        unit.onUpdate.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onUpdate(float dt) {
        secondsPassed += dt;
        if (secondsPassed >= TOGGLE_INTERVAL) {
            secondsPassed -= TOGGLE_INTERVAL;

            // We us the stun animation for summoning creeps
            StunEffect stunEffect = getUnit().addAbilityStack(StunEffect.class);
            stunEffect.setDuration(2.0f);

            waveSpawner.spawnTimeLordUnderlingsWave(getUnit());
        }
    }
}
