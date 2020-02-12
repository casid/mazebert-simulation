package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;

public strictfp class HeroicMask extends Item {

    public HeroicMask() {
        super(new HeroicMaskAbility(), new HeroicSetAbility());
    }

    @Override
    public String getName() {
        return "Heroic Mask";
    }

    @Override
    public String getDescription() {
        return "Put it on - it's all it takes to look bad-ass.";
    }

    @Override
    public String getIcon() {
        return "hero_mask";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
    }

    @Override
    public int getItemLevel() {
        return 17;
    }

    @Override
    public String getSinceVersion() {
        return "2.0";
    }

    @Override
    public Element getElement() {
        return Element.Light;
    }
}
