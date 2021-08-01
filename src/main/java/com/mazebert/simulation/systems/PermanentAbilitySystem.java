package com.mazebert.simulation.systems;

import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.abilities.StackableAbility;
import com.mazebert.simulation.units.towers.Tower;

import java.util.ArrayList;
import java.util.List;

public strictfp class PermanentAbilitySystem {
    public static void transferAll(Tower oldTower, Tower newTower) {
        List<Ability> permanentAbilities = new ArrayList<>();
        oldTower.forEachAbility(ability -> {
            if (ability.isPermanent()) {
                permanentAbilities.add(ability);
            }
        });

        for (Ability permanentAbility : permanentAbilities) {
            if (permanentAbility instanceof StackableAbility) {
                do {
                    transferPermanentAbility(oldTower, newTower, permanentAbility);
                } while (permanentAbility.getUnit() != null);
            } else {
                transferPermanentAbility(oldTower, newTower, permanentAbility);
            }
        }
    }

    private static void transferPermanentAbility(Tower oldTower, Tower newTower, Ability permanentAbility) {
        oldTower.removeAbility(permanentAbility);
        newTower.addAbility(permanentAbility);
    }
}
