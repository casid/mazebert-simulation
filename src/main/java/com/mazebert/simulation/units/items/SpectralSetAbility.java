package com.mazebert.simulation.units.items;

import java.util.EnumSet;

public strictfp class SpectralSetAbility extends ItemSetAbility {
    public SpectralSetAbility() {
        super(EnumSet.of(ItemType.SpectralDaggers, ItemType.SpectralCape));
    }

    @Override
    protected void updateSetBonus(EnumSet<ItemType> items, int oldAmount, int newAmount) {
        if (newAmount == 2) {
            getUnit().getAbility(SpectralDaggersAbility.class).setMaxStacks(SpectralDaggersAbility.maxStacksBase + SpectralDaggersAbility.maxStacksSetBonus);
        }
        if (oldAmount == 2) {
            SpectralDaggersAbility daggersAbility = getUnit().getAbility(SpectralDaggersAbility.class);
            if (daggersAbility != null) {
                daggersAbility.setMaxStacks(SpectralDaggersAbility.maxStacksBase);
            }
        }
    }

    @Override
    public String getTitle() {
        return "Spectral Lord";
    }

    @Override
    public String getDescription() {
        return "+" + SpectralDaggersAbility.maxStacksSetBonus + " stacks for daggers (2 set items)";
    }
}
