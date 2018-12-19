package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.Ability;

public strictfp class AbyssKingAuraEffect extends Ability<Tower> {

    private int armySize;

    public AbyssKingAuraEffect(Tower origin) {
        setOrigin(origin);
    }

    @Override
    protected void dispose(Tower unit) {
        getUnit().addAddedRelativeBaseDamage(-armySize * AbyssKingAura.damagePerUndead);
        getUnit().addCritDamage(-armySize * AbyssKingAura.critDamagePerUndead);
        getUnit().addCritChance(-armySize * AbyssKingAura.critChancePerUndead);
        armySize = 0;
        super.dispose(unit);
    }

    public void update(int armySize) {
        int delta = armySize - this.armySize;
        getUnit().addAddedRelativeBaseDamage(delta * AbyssKingAura.damagePerUndead);
        getUnit().addCritDamage(delta * AbyssKingAura.critDamagePerUndead);
        getUnit().addCritChance(delta * AbyssKingAura.critChancePerUndead);
        this.armySize = armySize;
    }
}
