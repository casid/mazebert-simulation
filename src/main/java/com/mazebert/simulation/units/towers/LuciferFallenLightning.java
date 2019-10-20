package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.projectiles.ChainViewType;
import com.mazebert.simulation.units.abilities.ChainAbility;

public strictfp class LuciferFallenLightning extends ChainAbility {
    public LuciferFallenLightning() {
        super(ChainViewType.Lightning, 6);
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
        return "The electricity of this chair is so strong, that it jumps to 1 other creep on the map.";
    }

    @Override
    public String getLevelBonus() {
        return "+1 jump every 14 levels";
    }
}
