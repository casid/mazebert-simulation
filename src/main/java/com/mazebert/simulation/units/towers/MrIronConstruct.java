package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Balancing;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.abilities.CooldownActiveAbility;
import com.mazebert.simulation.units.items.Item;

public strictfp class MrIronConstruct extends CooldownActiveAbility {

    public static final float COOLDOWN = 90;
    public static final float MAX_COOLDOWN = 900;

    private final int version = Sim.context().version;

    @Override
    public float getCooldown() {
        if (version >= Sim.vRnR) {
            return Balancing.calculateCooldown(COOLDOWN, getUnit().getAttackSpeedModifier(), Balancing.MIN_COOLDOWN, MAX_COOLDOWN);
        } else {
            float attackSpeedModifier = getUnit().getAttackSpeedModifier();
            if (attackSpeedModifier <= 0.0) {
                return COOLDOWN;
            }

            return COOLDOWN / attackSpeedModifier;
        }
    }

    @Override
    public void activate() {
        for (int i = 0; i < getUnit().getInventorySize(); ++i) {
            Item item = getUnit().getItem(i);
            if (isPossible(item)) {
                getUnit().setItem(i, null);
                item.forEachAbility(ability -> getUnit().addAbility(ability));
            }
        }
        startCooldown();
    }

    private boolean isPossible(Item item) {
        if (item == null) {
            return false;
        }

        if (item.isSet()) {
            return false;
        }

        switch (item.getRarity()) {
            case Unique:
            case Legendary:
                return false;
        }

        return true;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Armor Upgrade";
    }

    @Override
    public String getDescription() {
        String legendaryString = format.rarity(Rarity.Legendary);
        String uniqueString = format.rarity(Rarity.Unique);
        String setString = "<c=#00ee00>set</c>";
        return "Mr. Iron leaves his armor for " + format.seconds(COOLDOWN) + " to upgrade it, permanently integrating all equipped items. Can't integrate " + legendaryString + ", " + uniqueString + " or " + setString + " items.";
    }

    @Override
    public String getIconFile() {
        return "mechanical_leftovers_512";
    }
}
