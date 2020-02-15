package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.projectiles.ProjectileViewType;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.AttackSoundAbility;
import com.mazebert.simulation.units.abilities.ProjectileDamageAbility;

public strictfp class AbyssKing extends Tower {
    private final AbyssKingAura aura;
    private int armySize;

    public AbyssKing() {
        setBaseCooldown(2.4f);
        setBaseRange(1.0f);
        setAttackType(AttackType.Fal);
        setStrength(0.8f);
        setDamageSpread(0.14f);
        setGender(Gender.Male);
        setElement(Element.Darkness);

        addAbility(new AttackAbility());
        addAbility(new AttackSoundAbility("sounds/abyss_king_attack.mp3"));
        addAbility(new ProjectileDamageAbility(ProjectileViewType.BlueSpell, 18));
        addAbility(new AbyssKingSwallow());
        addAbility(aura = new AbyssKingAura());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2014)
        );
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.3f;
    }

    @Override
    public String getName() {
        return "Abyss King";
    }

    @Override
    public String getDescription() {
        return "Commander of the Undead.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public int getItemLevel() {
        return 81;
    }

    @Override
    public String getSinceVersion() {
        return "1.0";
    }

    @Override
    public String getAuthor() {
        return "ManuWins";
    }

    @Override
    public String getModelId() {
        return "abyss_king";
    }

    @Override
    public int getImageOffsetOnCardX() {
        return -20;
    }

    @Override
    public int getImageOffsetOnCardY() {
        return -20;
    }

    @Override
    public void populateCustomTowerBonus(CustomTowerBonus bonus) {
        bonus.value = "" + armySize;
        bonus.title = "Army:";
    }

    public int getArmySize() {
        return armySize;
    }

    public void addCreepToArmy() {
        ++armySize;
        aura.update(armySize);
    }
}
