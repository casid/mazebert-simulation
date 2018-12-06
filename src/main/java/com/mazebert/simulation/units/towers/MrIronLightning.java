package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.projectiles.ChainViewType;
import com.mazebert.simulation.units.abilities.InstantDamageAbility;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class MrIronLightning extends InstantDamageAbility {
    private static final Creep[] EMPTY = new Creep[0];

    @Override
    public void onAttack(Creep target) {
        super.onAttack(target);
        getUnit().onChain.dispatch(ChainViewType.Lightning, target, EMPTY, 0);
    }
}
