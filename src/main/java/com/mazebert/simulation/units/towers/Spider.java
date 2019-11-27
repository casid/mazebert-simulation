package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.projectiles.ProjectileViewType;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.AttackSoundAbility;
import com.mazebert.simulation.units.abilities.ProjectileDamageAbility;

public strictfp class Spider extends Tower {

    public Spider() {
        setBaseCooldown(2.0f);
        setBaseRange(4.0f);
        setAttackType(AttackType.Fal);
        setStrength(0.6f);
        setDamageSpread(0.4f);
        setGender(Gender.Female);
        setElement(Element.Darkness);

        addAbility(new AttackAbility());
        addAbility(new AttackSoundAbility("sounds/web-spit.mp3"));
        addAbility(new ProjectileDamageAbility(ProjectileViewType.Web, 11.8f));
        if (Sim.isDoLSeasonContent()) {
            addAbility(new SpiderWebDoL());
        } else {
            addAbility(new SpiderWeb());
        }
    }

    @Override
    public String getName() {
        return "Small Spider";
    }

    @Override
    public String getDescription() {
        return "When I'm grown up, I will eat my mate. Nom, nom!";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Common;
    }

    @Override
    public int getItemLevel() {
        return 1;
    }

    @Override
    public String getSinceVersion() {
        return "1.0";
    }

    @Override
    public String getModelId() {
        return "spider";
    }

    @Override
    public String getAuthor() {
        return "Vigi";
    }

    @Override
    protected float getGoldCostFactor() {
        return 0.8f;
    }

    @Override
    public int getImageOffsetOnCardY() {
        return 22;
    }
}
