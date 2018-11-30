package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Balancing;
import com.mazebert.simulation.listeners.OnLevelChangedListener;
import com.mazebert.simulation.units.abilities.Ability;

public strictfp class GaneshaLevelAuraEffect extends Ability<Tower> implements OnLevelChangedListener {
    public GaneshaLevelAuraEffect(Tower origin) {
        setOrigin(origin);
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onLevelChanged.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onLevelChanged.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onLevelChanged(Tower tower, int oldLevel, int newLevel) {
        if (tower instanceof Ganesha) {
            return;
        }

        if (newLevel <= oldLevel) {
            return;
        }

        int levels = newLevel - oldLevel;
        float xp = Balancing.getTowerExperienceForLevel(getOriginTower().getLevel() + levels);
        getOriginTower().setExperience(xp + 1.0f);
    }

    private Tower getOriginTower() {
        return (Tower)getOrigin();
    }
}
