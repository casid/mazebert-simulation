package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.listeners.OnUpdateListener;
import com.mazebert.simulation.plugins.random.RandomPlugin;
import com.mazebert.simulation.units.towers.Tower;
import org.jusecase.inject.Component;

import javax.inject.Inject;

@Component
public class ColorChangeAbility extends CooldownAbility<Tower> {

    @Inject
    private RandomPlugin randomPlugin;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.setColor(0xffffffff);
    }

    @Override
    boolean onCooldownReached() {
        getUnit().setColor(randomPlugin.nextInt());
        return true;
    }
}
