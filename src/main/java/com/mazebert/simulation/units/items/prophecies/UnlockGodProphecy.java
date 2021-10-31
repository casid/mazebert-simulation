package com.mazebert.simulation.units.items.prophecies;

import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.units.quests.QuestType;
import com.mazebert.simulation.units.towers.TowerType;

public strictfp abstract class UnlockGodProphecy extends Prophecy {

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

    @Override
    public Element getRequiredElement() {
        return getGod().instance().getElement();
    }
}
