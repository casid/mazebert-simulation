package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.ActiveAbility;
import com.mazebert.simulation.units.items.Item;

public class MrIronConstruct extends ActiveAbility {

    @Override
    public float getCooldown() {
        return 90;
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

        switch (item.getRarity()) {
            case Unique:
            case Legendary:
                return false;
        }

        // TODO set items

        return true;
    }
}
