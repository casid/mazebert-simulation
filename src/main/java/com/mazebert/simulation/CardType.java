package com.mazebert.simulation;

import com.mazebert.simulation.hash.Hashable;
import com.mazebert.simulation.units.heroes.HeroType;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.potions.PotionType;
import com.mazebert.simulation.units.towers.TowerType;

public interface CardType<T extends Card> extends Hashable {
    int id();
    T instance();
    T create();
    @SuppressWarnings("unused") // Used by ui
    CardCategory category();


    static CardType<?> forId(CardCategory cardCategory, int cardId) {
        switch (cardCategory) {
            case Tower:
                return TowerType.forId(cardId);
            case Item:
                return ItemType.forId(cardId);
            case Potion:
                return PotionType.forId(cardId);
            case Hero:
                return HeroType.forId(cardId);
        }

        throw new IllegalStateException("Unknown card category " + cardCategory);
    }
}
