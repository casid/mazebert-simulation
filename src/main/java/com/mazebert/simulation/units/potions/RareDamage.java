package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class RareDamage extends Potion {
    public RareDamage() {
        super(new RareDamageAbility());
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
        return "Great Potion of Strength";
    }

    @Override
    public String getDescription() {
        return "Violence is the last refuge of the incompetent. Cheers!";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Rare;
    }

    @Override
    public int getItemLevel() {
        return 24;
    }

    @Override
    public String getSinceVersion() {
        return "0.3";
    }
}
