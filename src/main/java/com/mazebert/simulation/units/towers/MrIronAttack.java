package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.AttackAbility;

public strictfp class MrIronAttack extends AttackAbility {
    public MrIronAttack() {
        super(2, true);
    }

    @Override
    protected boolean onCooldownReached() {
        MrIronConstruct construct = getUnit().getAbility(MrIronConstruct.class);
        if (construct.isReady()) {
            return super.onCooldownReached();
        }
        return false;
    }
}