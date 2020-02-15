package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class UncommonDamage extends Potion {
    public UncommonDamage() {
        super(new UncommonDamageAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2013)
        );
    }

    @Override
    public String getIcon() {
        return "9002_DamagePotion";
    }

    @Override
    public String getName() {
        return "Potion of Strength";
    }

    @Override
    public String getDescription() {
        return "Violence is the last refuge of the incompetent. Cheers!";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
    }

    @Override
    public int getItemLevel() {
        return 8;
    }

    @Override
    public String getSinceVersion() {
        return "0.3";
    }
}
