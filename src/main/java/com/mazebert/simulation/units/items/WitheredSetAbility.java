package com.mazebert.simulation.units.items;

import com.mazebert.simulation.listeners.OnDamageListener;
import com.mazebert.simulation.units.creeps.Creep;

import java.util.EnumSet;
import java.util.Set;

public strictfp class WitheredSetAbility extends ItemSetAbility implements OnDamageListener {
    public WitheredSetAbility() {
        super(EnumSet.of(ItemType.WitheredCactus, ItemType.WitheredToadstool, ItemType.WitheredBandages));
    }

    @Override
    protected void updateSetBonus(Set<ItemType> items, int oldAmount, int newAmount) {
        if (newAmount >= 2 && oldAmount < 2) {
            getUnit().onDamage.add(this);
        }

        if (newAmount < 2 && oldAmount >= 2) {
            getUnit().onDamage.remove(this);
        }
    }

    @Override
    public void onDamage(Object origin, Creep target, double damage, int multicrits) {
        if (isOriginalDamage(origin)) {
            if (getUnit().isImmobilizeAbilityTriggered(0.02f, target)) {
                target.warpInTime(-1.0f);
            }

            if (getAmount() >= 3) {
                WitheredSetEffect effect = target.addAbilityStack(WitheredSetEffect.class);
                effect.setDuration(6.0f);
            }
        }
    }

    @Override
    public String getTitle() {
        return "Withered";
    }

    @Override
    public String getDescription() {
        return "Attacks have a 2% chance to send the creep 1s back in time. (2 set items)\nEvery attack adds a 3% damage amplification for 6 seconds up to 75%. (3 set items)";
    }
}
