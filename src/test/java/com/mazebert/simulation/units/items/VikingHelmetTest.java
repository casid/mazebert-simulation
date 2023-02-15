package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.potions.MeadAbility;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public strictfp class VikingHelmetTest extends ItemTest {

    @Test
    void mead() {
        tower.addAbility(new MeadAbility());

        whenItemIsEquipped(ItemType.VikingHelmet);

        assertThat(tower.getAddedRelativeBaseDamage()).isEqualTo(0.5f + MeadAbility.damageBonus);
    }

    @Test
    void mead_helmetDropped() {
        tower.addAbility(new MeadAbility());

        whenItemIsEquipped(ItemType.VikingHelmet);
        whenItemIsEquipped(null);

        assertThat(tower.getAddedRelativeBaseDamage()).isEqualTo(-1.4901161E-8f);
    }
}