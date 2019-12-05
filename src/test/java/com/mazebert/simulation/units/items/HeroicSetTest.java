package com.mazebert.simulation.units.items;

import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.units.items.HeroicMaskAbility.BONUS;
import static org.assertj.core.api.Assertions.assertThat;

public strictfp class HeroicSetTest extends ItemTest {
    @Test
    void cape() {
        whenItemIsEquipped(ItemType.HeroicCape);
        assertThat(tower.getDamageAgainstBosses()).isGreaterThan(1);
    }

    @Test
    void cape_removed() {
        whenItemIsEquipped(ItemType.HeroicCape);
        whenItemIsEquipped(null);
        assertThat(tower.getDamageAgainstBosses()).isEqualTo(1);
    }

    @Test
    void mask() {
        whenItemIsEquipped(ItemType.HeroicMask);
        assertThat(tower.getExperienceModifier()).isEqualTo(1 + BONUS);
        assertThat(tower.getAddedRelativeBaseDamage()).isEqualTo(BONUS);
        assertThat(tower.getCritChance()).isEqualTo(0.05f + BONUS);
        assertThat(tower.getLuck()).isEqualTo(1 + BONUS);
    }

    @Test
    void mask_removed() {
        whenItemIsEquipped(ItemType.HeroicMask);
        whenItemIsEquipped(null);
        assertThat(tower.getExperienceModifier()).isEqualTo(1 + BONUS - BONUS);
        assertThat(tower.getAddedRelativeBaseDamage()).isEqualTo(BONUS - BONUS);
        assertThat(tower.getCritChance()).isEqualTo(0.05f + BONUS - BONUS);
        assertThat(tower.getLuck()).isEqualTo(1 + BONUS - BONUS);
    }

    @Test
    void set() {
        whenItemIsEquipped(ItemType.HeroicMask, 0);
        whenItemIsEquipped(ItemType.HeroicCape, 1);

        assertThat(tower.getAttackSpeedAdd()).isEqualTo(HeroicSetAbility.BONUS);
    }

    @Test
    void set_removed() {
        whenItemIsEquipped(ItemType.HeroicMask, 0);
        whenItemIsEquipped(ItemType.HeroicCape, 1);
        whenItemIsEquipped(null, 1);

        assertThat(tower.getAttackSpeedAdd()).isEqualTo(HeroicSetAbility.BONUS - HeroicSetAbility.BONUS);
    }
}