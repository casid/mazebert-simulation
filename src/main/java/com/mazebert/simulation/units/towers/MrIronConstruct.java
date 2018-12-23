package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.ActiveAbility;
import com.mazebert.simulation.units.items.Item;

public strictfp class MrIronConstruct extends ActiveAbility {

    public static final float COOLDOWN = 90;

    @Override
    public float getCooldown() {
        float attackSpeedModifier = getUnit().getAttackSpeedModifier();
        if (attackSpeedModifier <= 0.0) {
            return COOLDOWN;
        }

        return COOLDOWN / attackSpeedModifier;
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
        return "Construct";
    }

    @Override
    public String getDescription() {
        String legendaryString = "legendary"; // TODO color
        String uniqueString = "unique"; // TODO color
        String setString = "set"; // TODO color
        return "Mr. Iron leaves his battle suit for " + format.seconds(COOLDOWN) + " to improve it. All currently equipped items are removed and integrated in the suit. Cannot integrate " + legendaryString + ", " + uniqueString + " or " + setString + " items.";
    }

    @Override
    public String getIconFile() {
        return "mechanical_leftovers_512";
    }
}
