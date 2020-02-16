package com.mazebert.simulation.units.items;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public strictfp class HeroicSetTest extends ItemTest {
    private static final float MASK_BONUS = 0.1f;
    private static final float SET_BONUS = 0.4f;

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
        assertThat(tower.getExperienceModifier()).isEqualTo(1 + MASK_BONUS);
        assertThat(tower.getAddedRelativeBaseDamage()).isEqualTo(MASK_BONUS);
        assertThat(tower.getCritChance()).isEqualTo(0.05f + MASK_BONUS);
        assertThat(tower.getLuck()).isEqualTo(1 + MASK_BONUS);
    }

    @Test
    void mask_removed() {
        whenItemIsEquipped(ItemType.HeroicMask);
        whenItemIsEquipped(null);
        assertThat(tower.getExperienceModifier()).isEqualTo(1 + MASK_BONUS - MASK_BONUS);
        assertThat(tower.getAddedRelativeBaseDamage()).isEqualTo(MASK_BONUS - MASK_BONUS);
        assertThat(tower.getCritChance()).isEqualTo(0.05f + MASK_BONUS - MASK_BONUS);
        assertThat(tower.getLuck()).isEqualTo(1 + MASK_BONUS - MASK_BONUS);
    }

    @Test
    void set() {
        whenItemIsEquipped(ItemType.HeroicMask, 0);
        whenItemIsEquipped(ItemType.HeroicCape, 1);

        assertThat(tower.getAttackSpeedAdd()).isEqualTo(SET_BONUS);
    }

    @Test
    void set_removed() {
        whenItemIsEquipped(ItemType.HeroicMask, 0);
        whenItemIsEquipped(ItemType.HeroicCape, 1);
        whenItemIsEquipped(null, 1);

        assertThat(tower.getAttackSpeedAdd()).isEqualTo(SET_BONUS - SET_BONUS);
    }
}