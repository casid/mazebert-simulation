package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class KeyOfWisdom extends Item {

    public KeyOfWisdom() {
        super(new KeyOfWisdomAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2013)
        );
    }

    @Override
    public String getName() {
        return "Key of Wisdom";
    }

    @Override
    public String getDescription() {
        return "Unlock your mind. Invite wisdom. Become enlightened.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Rare;
    }

    @Override
    public String getSinceVersion() {
        return "0.5";
    }

    @Override
    public String getIcon() {
        return "0012_key_512";
    }

    @Override
    public int getItemLevel() {
        return 38;
    }

    @Override
    public String getAuthor() {
        return "Alex Nill";
    }
}
