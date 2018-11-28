package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.abilities.Ability;

public abstract strictfp class CardDust extends Potion {
    public CardDust(Ability... abilities) {
        super(abilities);
    }

    @Override
    public String getName() {
        return "Card Dust";
    }

    @Override
    public String getDescription() {
        return "This magical dust was extracted from very powerful cards.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public String getIcon() {
        return "card_dust_512";
    }

    @Override
    public boolean isDropable() {
        return false;
    }

    @Override
    public String getSinceVersion() {
        return "1.3";
    }

    @Override
    public int getItemLevel() {
        return 1;
    }
}
