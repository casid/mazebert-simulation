package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.WaveType;
import com.mazebert.simulation.stash.StashEntry;
import com.mazebert.simulation.systems.LootSystem;
import com.mazebert.simulation.units.abilities.AuraAbility;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.potions.Potion;
import com.mazebert.simulation.units.potions.PotionType;
import com.mazebert.simulation.units.potions.UnicornTears;
import com.mazebert.simulation.units.wizards.Wizard;
import com.mazebert.simulation.usecases.SellTower;

public strictfp class UnicornDeathAbility extends AuraAbility<Tower, Creep> {

    private final LootSystem lootSystem = Sim.context().lootSystem;

    public UnicornDeathAbility() {
        super(CardCategory.Tower, Creep.class, 1);
    }

    @Override
    protected void onAuraEntered(Creep unit) {
        WaveType waveType = unit.getWave().type;
        if (waveType == WaveType.Challenge || waveType == WaveType.Horseman || waveType == WaveType.Air) {
            return;
        }

        if (getUnit().isNegativeAbilityTriggered(0.25f)) {
            Wizard wizard = getUnit().getWizard();
            lootSystem.addToStash(wizard, unit, wizard.potionStash, PotionType.UnicornTears);

            StashEntry<Potion> entry = wizard.potionStash.get(PotionType.UnicornTears);
            ((UnicornTears)entry.getCard()).setLevels(getUnit().getLevel() / 2);

            new SellTower().execute(wizard, getUnit());
        }
    }

    @Override
    protected void onAuraLeft(Creep unit) {
        // unused
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Horn hunt";
    }

    @Override
    public String getDescription() {
        return "Non-challenge ground creeps entering her range have a 25% chance to hunt her down, leaving " + format.card(PotionType.UnicornTears) + " behind.";
    }

    @Override
    public String getIconFile() {
        return "9009_WisdomPotion";
    }
}
