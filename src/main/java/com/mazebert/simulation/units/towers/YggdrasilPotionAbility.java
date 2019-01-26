package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.Ability;

public strictfp class YggdrasilPotionAbility extends Ability<Yggdrasil> {

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Connects all worlds";
    }

    @Override
    public String getDescription() {
        return "Whenever Yggdrasil drinks a potion, the carrier of every branch will do the same.";
    }

    @Override
    public String getLevelBonus() {
        return "Limited to 1 branch per tower\nLimited to Nature towers";
    }

    @Override
    public String getIconFile() {
        return "9002_DamagePotion";
    }
}
