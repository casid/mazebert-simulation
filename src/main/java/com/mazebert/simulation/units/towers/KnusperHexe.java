package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.listeners.OnKill;
import com.mazebert.simulation.projectiles.ProjectileViewType;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.ProjectileDamageAbility;

public strictfp class KnusperHexe extends Tower {

    public final OnKill onChildEaten = new OnKill();

    private final KnusperHexeEat eat;

    public KnusperHexe() {
        setBaseCooldown(2.4f);
        setBaseRange(3.0f);
        setAttackType(AttackType.Fal);
        setStrength(0.7f);
        setDamageSpread(0.6f);
        setGender(Gender.Female);
        setElement(Element.Darkness);

        addAbility(new AttackAbility());
        addAbility(new ProjectileDamageAbility(ProjectileViewType.FrogSpit, 11.8f));
        addAbility(eat = new KnusperHexeEat());
        addAbility(new KnusperHexeAura());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.vDoLEnd, false, 2020, "Aura range is always the same as Knusperhexe's range."),
                new ChangelogEntry(Sim.v10, false, 2013)
        );
    }

    @Override
    protected float getGoldCostFactor() {
        return 0.81f;
    }

    @Override
    public String getName() {
        if (getGender() == Gender.Female) {
            return "Knusperhexe";
        } else {
            return "Knusperhexer";
        }
    }

    @Override
    public String getDescription() {
        return "Nibble, nibble, gnaw.";
    }

    @Override
    public int getItemLevel() {
        return 72;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public String getSinceVersion() {
        return "0.6";
    }

    @Override
    public String getModelId() {
        return "the_witch";
    }

    @Override
    public String getAuthor() {
        return "Karo";
    }

    @Override
    public int getImageOffsetOnCardY() {
        return 8;
    }

    @Override
    public void populateCustomTowerBonus(CustomTowerBonus bonus) {
        bonus.title = "Eaten:";
        bonus.value = "" + getCreepsEaten();
    }

    public int getCreepsEaten() {
        return eat.getCreepsEaten();
    }
}
