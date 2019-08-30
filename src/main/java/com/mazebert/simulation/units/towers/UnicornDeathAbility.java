package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.WaveType;
import com.mazebert.simulation.stash.StashEntry;
import com.mazebert.simulation.units.abilities.AuraAbility;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.potions.Potion;
import com.mazebert.simulation.units.potions.PotionType;
import com.mazebert.simulation.units.potions.UnicornTears;
import com.mazebert.simulation.units.wizards.Wizard;
import com.mazebert.simulation.usecases.SellTower;

public strictfp class UnicornDeathAbility extends AuraAbility<Tower, Creep> {

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
            wizard.potionStash.add(PotionType.UnicornTears);
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
        return "25% chance to get hunted down by non-challenge ground creeps entering her range. " + format.card(PotionType.UnicornTears) + " are added to your stash, granting half of the unicorns current level.";
    }

    @Override
    public String getIconFile() {
        return "9009_WisdomPotion";
    }
}
