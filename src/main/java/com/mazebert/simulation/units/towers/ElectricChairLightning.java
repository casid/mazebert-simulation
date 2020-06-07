package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.listeners.OnLevelChangedListener;
import com.mazebert.simulation.projectiles.ChainViewType;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.ChainAbility;

public strictfp class ElectricChairLightning extends ChainAbility implements OnLevelChangedListener {
    private final int levelsForOneChain;
    private final int initialChains;

    public ElectricChairLightning(int initialChains) {
        super(ChainViewType.Lightning, initialChains);
        this.initialChains = initialChains;
        levelsForOneChain = 14;
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
    public void onLevelChanged(Unit unit, int oldLevel, int newLevel) {
        setMaxChains(initialChains + (newLevel / levelsForOneChain));
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Electric Death";
    }

    @Override
    public String getIconFile() {
        return "0068_lightning_512";
    }

    @Override
    public String getDescription() {
        if (initialChains == 1) {
            return "The electricity of this chair is so strong that it jumps to 1 other creep on the map.";
        } else {
            return "The electricity of this chair is so strong that it jumps to " + initialChains + " other creeps on the map.";
        }
    }

    @Override
    public String getLevelBonus() {
        return "+1 jump every " + levelsForOneChain + " levels.";
    }
}
