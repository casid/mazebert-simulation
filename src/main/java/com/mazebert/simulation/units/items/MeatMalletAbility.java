package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.abilities.DamageWithLevelBonusAbility;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class MeatMalletAbility extends DamageWithLevelBonusAbility {
    private final float critChanceMalus = 0.25f;
    private float totalCritChanceMalus;

    public MeatMalletAbility() {
        super(2.0f, 0.01f);
    }

    @Override
    protected void updateBonus() {
        super.updateBonus();

        totalCritChanceMalus = getStackCount() * critChanceMalus;
        getUnit().addCritChance(-totalCritChanceMalus);
    }

    @Override
    protected void removeBonus() {
        super.removeBonus();

        getUnit().addCritChance(totalCritChanceMalus);
        totalCritChanceMalus = 0;
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
    }

    @Override
    public String getTitle() {
        return "A good beating";
    }

    @Override
    public String getDescription() {
        return "Damage increased by " + formatPlugin.percent(bonus) + "%\nCrit chance reduced by " + formatPlugin.percent(critChanceMalus) + "%";
    }
}
