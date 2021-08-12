package com.mazebert.simulation.units.items.prophecies;

import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.potions.PotionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CardDustProphecyTest extends ProphecyTest {

    @BeforeEach
    void setUp() {
        randomPluginTrainer.givenFloatAbs(0.9f); // Will roll card dust different from Critical Dust

        wizard.itemStash.add(ItemType.Excalibur);
        wizard.itemStash.add(ItemType.BowlingBall);
    }

    @Test
    void transmuteWithoutProphecy() {
        whenCardIsTransmuted(ItemType.Excalibur);
        whenCardIsTransmuted(ItemType.BowlingBall);

        assertThat(wizard.potionStash.get(0).cardType).isEqualTo(PotionType.CardDustVital);
    }

    @Test
    void transmute() {
        whenItemIsEquipped(ItemType.CardDustProphecy);

        whenCardIsTransmuted(ItemType.Excalibur);
        whenCardIsTransmuted(ItemType.BowlingBall);

        assertThat(wizard.potionStash.get(0).cardType).isEqualTo(PotionType.CardDustCrit);
    }

    @Test
    void onlyWorksOnce() {
        whenItemIsEquipped(ItemType.CardDustProphecy);
        wizard.itemStash.add(ItemType.BranchOfYggdrasilDarkness);
        wizard.itemStash.add(ItemType.BranchOfYggdrasilNature);

        whenCardIsTransmuted(ItemType.Excalibur);
        whenCardIsTransmuted(ItemType.BowlingBall);
        whenCardIsTransmuted(ItemType.BranchOfYggdrasilDarkness);
        whenCardIsTransmuted(ItemType.BranchOfYggdrasilNature);

        assertThat(wizard.potionStash.get(0).cardType).isEqualTo(PotionType.CardDustCrit);
        assertThat(wizard.potionStash.get(1).cardType).isEqualTo(PotionType.CardDustVital);
    }
}