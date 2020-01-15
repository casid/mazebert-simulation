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
        return "Whenever Yggdrasil drinks a potion, all branch carriers receive the effect of that potion, too.";
    }

    @Override
    public String getIconFile() {
        return "9002_DamagePotion";
    }
}
