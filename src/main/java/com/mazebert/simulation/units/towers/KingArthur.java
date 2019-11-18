package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.AttackSoundAbility;
import com.mazebert.simulation.units.abilities.InstantDamageAbility;

public strictfp class KingArthur extends Tower {

    public KingArthur() {
        setBaseCooldown(4.0f);
        setBaseRange(2.0f);
        setAttackType(AttackType.Fal);
        setStrength(0.8f);
        setDamageSpread(0.4f);
        setGender(Gender.Male);
        setElement(Element.Light);

        addAbility(new AttackAbility());
        addAbility(new AttackSoundAbility("sounds/abyss_king_attack.mp3"));
        addAbility(new InstantDamageAbility());
        addAbility(new KingArthurExcalibur());
        addAbility(new GuardAura(getBaseRange(), 3));
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.61f;
    }

    @Override
    public String getName() {
        return "King Arthur";
    }

    @Override
    public String getDescription() {
        return "I'm a guardian of the light, I'll defeat the core of all evil!";
    }

    @Override
    public int getItemLevel() {
        return 80;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Rare;
    }

    @Override
    public String getSinceVersion() {
        return "2.0";
    }

    @Override
    public String getModelId() {
        return "king_arthur";
    }

    @Override
    public String getAuthor() {
        return "SnoreFox";
    }
}
