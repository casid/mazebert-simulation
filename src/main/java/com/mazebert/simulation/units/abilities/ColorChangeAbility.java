package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.listeners.OnUpdateListener;
import com.mazebert.simulation.plugins.random.RandomPlugin;
import com.mazebert.simulation.units.towers.Tower;
import org.jusecase.inject.Component;

import javax.inject.Inject;

@Component
public class ColorChangeAbility extends Ability<Tower> implements OnUpdateListener {

    @Inject
    private RandomPlugin randomPlugin;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onUpdate.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onUpdate.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onUpdate(float dt) {
        getUnit().setColor(randomPlugin.nextInt());
    }
}
