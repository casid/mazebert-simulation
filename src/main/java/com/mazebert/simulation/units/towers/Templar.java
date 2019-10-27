package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.AttackSoundAbility;
import com.mazebert.simulation.units.abilities.InstantDamageAbility;
import com.mazebert.simulation.units.abilities.RandomGenderAbility;

public strictfp class Templar extends Tower {

    public Templar() {
        setBaseCooldown(3.0f);
        setBaseRange(2.0f);
        setAttackType(AttackType.Fal);
        setStrength(0.7f);
        setDamageSpread(0.25f);
        setGender(Gender.Unknown);
        setElement(Element.Light);

        addAbility(new TemplarAttack());
        addAbility(new AttackSoundAbility("sounds/abyss_king_attack.mp3"));
        addAbility(new InstantDamageAbility()); // Required for crit bonus to work!
        addAbility(new RandomGenderAbility());
        addAbility(new GuardAura(getBaseRange()));
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.15f;
    }

    @Override
    public String getName() {
        return "Templar";
    }

    @Override
    public String getDescription() {
        return "Give me guidance, Almighty!";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
    }

    @Override
    public int getItemLevel() {
        return 1;
    }

    @Override
    public String getSinceVersion() {
        return "2.0";
    }

    @Override
    public String getModelId() {
        return "templar";
    }

    @Override
    public String getAuthor() {
        return "shimakura";
    }
}