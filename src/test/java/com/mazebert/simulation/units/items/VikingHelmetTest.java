package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.potions.MeadAbility;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public strictfp class VikingHelmetTest extends ItemTest {

    @Test
    void mead() {
        tower.addAbility(new MeadAbility());

        whenItemIsEquipped(ItemType.VikingHelmet);

        Assertions.assertThat(tower.getAddedRelativeBaseDamage()).isEqualTo(0.5f + MeadAbility.damageBonus);
    }

    @Test
    void mead_helmetDropped() {
        tower.addAbility(new MeadAbility());

        whenItemIsEquipped(ItemType.VikingHelmet);
        whenItemIsEquipped(null);

        Assertions.assertThat(tower.getAddedRelativeBaseDamage()).isEqualTo(-1.4901161E-8f);
    }
}