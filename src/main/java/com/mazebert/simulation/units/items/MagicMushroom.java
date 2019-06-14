package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.abilities.Ability;

public strictfp class MagicMushroom extends Item {

    private static Ability[] getAbilities() {
        if (Sim.context().version >= Sim.v17) {
            return new Ability[]{new MagicMushroomAbility(), new MagicMushroomDamageAbility()};
        } else {
            return new Ability[]{new MagicMushroomAbility()};
        }
    }

    public MagicMushroom() {
        super(getAbilities());
    }

    @Override
    public String getName() {
        return "Magic Mushroom";
    }

    @Override
    public String getDescription() {
        return "This mushroom has the infinite potential to make you high.";
    }

    @Override
    public String getIcon() {
        return "mushroom_512";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
    }

    @Override
    public String getAuthor() {
        return "Daniel Gerhardt";
    }

    @Override
    public int getItemLevel() {
        return 16;
    }

    @Override
    public String getSinceVersion() {
        return "0.4";
    }
}
