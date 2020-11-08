package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.WaveType;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.stash.StashEntry;
import com.mazebert.simulation.systems.DamageSystem;
import com.mazebert.simulation.systems.LootSystem;
import com.mazebert.simulation.units.abilities.AuraAbility;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.items.HelmOfHadesInvisibleAbility;
import com.mazebert.simulation.units.potions.Potion;
import com.mazebert.simulation.units.potions.PotionType;
import com.mazebert.simulation.units.potions.UnicornTears;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class UnicornImpaleAbility extends AuraAbility<Tower, Creep> {

    private final LootSystem lootSystem = Sim.context().lootSystem;
    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final DamageSystem damageSystem = Sim.context().damageSystem;

    public UnicornImpaleAbility() {
        super(CardCategory.Tower, Creep.class, 1);
    }

    @Override
    protected void onAuraEntered(Creep creep) {
        if (creep.isAir()) {
            return;
        }

        if (creep.getWave().type == WaveType.TimeLord) {
            return;
        }

        if (creep.getWizard() != null && creep.getWizard() != getUnit().getWizard()) {
            return;
        }

        damageSystem.dealDamage(this, getUnit(), creep, 0.2 * creep.getMaxHealth(), 0, false);

        if (isUnicornKilled(creep)) {
            Wizard wizard = getUnit().getWizard();
            lootSystem.addToStash(wizard, creep, wizard.potionStash, PotionType.UnicornTears);

            StashEntry<Potion> entry = wizard.potionStash.get(PotionType.UnicornTears);
            ((UnicornTears) entry.getCard()).setLevels(getUnit().getLevel() / 2);

            unitGateway.destroyTower(getUnit());
        }
    }

    private boolean isUnicornKilled(Creep creep) {
        if (creep.isDead()) {
            return false;
        }

        WaveType waveType = creep.getWave().type;
        if (waveType == WaveType.Challenge || waveType == WaveType.MassChallenge) {
            return false;
        }

        if (Sim.context().version < Sim.vDoLEnd && getUnit().hasAbility(HelmOfHadesInvisibleAbility.class)) {
            return false;
        }

        return getUnit().isNegativeAbilityTriggered(0.25f);
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
        return "Impale";
    }

    @Override
    public String getDescription() {
        return "Ground creeps entering her range lose 20% health. Survivors (except challenges) have a 25% chance to kill her, leaving behind " + format.card(PotionType.UnicornTears) + ".";
    }

    @Override
    public String getIconFile() {
        return "9009_WisdomPotion";
    }
}
