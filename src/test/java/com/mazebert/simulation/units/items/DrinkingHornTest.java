package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.VikingAbility;
import com.mazebert.simulation.units.potions.PotionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class DrinkingHornTest extends ItemTest {
    @BeforeEach
    void setUp() {
        tower.addAbility(new VikingAbility());
    }

    @Test
    void stats() {
        whenItemIsEquipped(ItemType.DrinkingHorn);

        assertThat(tower.getPotionEffectiveness()).isEqualTo(1.2f);
        assertThat(tower.getLuck()).isEqualTo(1.1f);
        assertThat(tower.getChanceToMiss()).isEqualTo(0.05f);
    }

    @Test
    void stats_removed() {
        whenItemIsEquipped(ItemType.DrinkingHorn);
        whenItemIsEquipped(null);

        assertThat(tower.getPotionEffectiveness()).isEqualTo(1.0f);
        assertThat(tower.getLuck()).isEqualTo(1.0f);
        assertThat(tower.getChanceToMiss()).isEqualTo(0.0f);
    }

    @Test
    void potionEffect() {
        whenItemIsEquipped(ItemType.DrinkingHorn);
        whenPotionIsConsumed(PotionType.CommonDamage);

        assertThat(tower.getAddedRelativeBaseDamage()).isEqualTo(0.060000002f);
    }

    @Test
    void potionEffect_potionDrankBeforeEquipping() {
        whenPotionIsConsumed(PotionType.CommonDamage);
        whenItemIsEquipped(ItemType.DrinkingHorn);

        assertThat(tower.getAddedRelativeBaseDamage()).isEqualTo(0.060000002f);
    }

    @Test
    void noVikingAnymore() {
        whenItemIsEquipped(ItemType.DrinkingHorn);
        tower.removeAbility(VikingAbility.class);
        
        assertThat(tower.getPotionEffectiveness()).isEqualTo(1.0f);
        assertThat(tower.getItem(0)).isNull();
        assertThat(wizard.itemStash.get(ItemType.DrinkingHorn).amount).isEqualTo(1);
    }

    @Test
    void cardDustVital() {
        whenItemIsEquipped(ItemType.DrinkingHorn);
        whenPotionIsConsumed(PotionType.CardDustVital);

        assertThat(wizard.health).isEqualTo(1.24f);
    }

    @Test
    void essenceOfWisdom() {
        whenItemIsEquipped(ItemType.DrinkingHorn);
        whenPotionIsConsumed(PotionType.EssenceOfWisdom);

        assertThat(tower.getLevel()).isEqualTo(12);
    }

    @Test
    void mead() {
        whenItemIsEquipped(ItemType.DrinkingHorn);
        whenPotionIsConsumed(PotionType.Mead);

        assertThat(tower.getCritDamage()).isEqualTo(0.49f);
    }

    @Test
    void nillos() {
        whenItemIsEquipped(ItemType.DrinkingHorn);
        whenPotionIsConsumed(PotionType.Nillos);
        assertThat(tower.getAttackSpeedAdd()).isEqualTo(0.48000002f);
    }

    @Test
    void sacrifice() {
        whenItemIsEquipped(ItemType.DrinkingHorn);
        tower.setLevel(10);
        whenPotionIsConsumed(PotionType.Sacrifice);

        assertThat(wizard.health).isEqualTo(1.12f);
    }

    @Test
    void tears() {
        whenPotionIsConsumed(PotionType.Tears);
        whenItemIsEquipped(ItemType.DrinkingHorn);
        assertThat(tower.getCritDamage()).isEqualTo(0.85f);
        assertThat(tower.getCritChance()).isEqualTo(0.11f);
        assertThat(tower.getAddedRelativeBaseDamage()).isEqualTo(0.3f);
    }
}