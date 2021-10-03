package com.mazebert.simulation.units.items.prophecies;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.units.quests.QuestType;
import com.mazebert.simulation.units.towers.TowerType;

public strictfp abstract class UnlockGodProphecy extends Prophecy {
    // TODO Unique prophecy card
    // Add hidden quests with progress for every norse god
    // - Whenever a tower kills a challenge, it counts as sacrifice to the god of that tower's element
    // - Quest progress is written on the card
    // - On completion of the quest, the god's golden card is obtained!

    // - If a god is already owned by the player, the god card is added to the players hand on the first sacrifice.

    // TODO ensure Thor's Mjoelnir unlock is done properly on server side!


    public UnlockGodProphecy() {
        setAbilities(new UnlockGodProphecyAbility(getType(), getGod(), getQuest()));
    }

    protected abstract TowerType getGod();

    protected abstract QuestType getQuest();

    @Override
    public String getName() {
        return "Sacrifice for " + getGod().name();
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.vRnR, true, 2021)
        );
    }

    @Override
    public int getItemLevel() {
        return 13;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }
}
