package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.potions.CardDust;
import com.mazebert.simulation.units.towers.Dandelion;
import com.mazebert.simulation.units.towers.Tower;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ToiletPaperTest extends ItemTest {

    @Override
    protected Tower createTower() {
        return new Dandelion();
    }

    @Test
    void targetsNotIncreased() {
        whenItemIsEquipped(ItemType.ToiletPaper);
        assertThat(tower.getAbility(AttackAbility.class).getTargets()).isEqualTo(1);
    }

    @Test
    void transmuted() {
        randomPluginTrainer.givenFloatAbs(0.0f, 0.9f, 0.0f);
        wizard.itemStash.add(ItemType.ToiletPaper);
        wizard.foilItems.add(ItemType.UnluckyPants);

        whenCardIsTransmuted(ItemType.ToiletPaper);

        assertThat(wizard.itemStash.size()).isEqualTo(3);
        assertThat(wizard.itemStash.get(0).cardType.instance().getRarity()).isEqualTo(Rarity.Unique);
        assertThat(wizard.itemStash.get(1).cardType.instance().getRarity()).isEqualTo(Rarity.Legendary);
        assertThat(wizard.itemStash.get(2).cardType.instance().getRarity()).isEqualTo(Rarity.Unique);
    }

    @Test
    void transmuted_allUniquesAndLegendariesAlreadyDropped() {
        randomPluginTrainer.givenFloatAbs(0.0f, 0.3f, 0.6f);
        givenAllUniqueItemsAlreadyDroppedExcept(ItemType.ToiletPaper);
        wizard.itemStash.add(ItemType.ToiletPaper);

        whenCardIsTransmuted(ItemType.ToiletPaper);

        assertThat(wizard.itemStash.size()).isEqualTo(0);
        assertThat(wizard.potionStash.size()).isEqualTo(3);
        assertThat(wizard.potionStash.get(0).cardType.instance()).isInstanceOf(CardDust.class);
        assertThat(wizard.potionStash.get(1).cardType.instance()).isInstanceOf(CardDust.class);
        assertThat(wizard.potionStash.get(2).cardType.instance()).isInstanceOf(CardDust.class);
    }

    @Test
    void transmuted_allUniquesAndLegendariesAlreadyDropped_exceptOne() {
        randomPluginTrainer.givenFloatAbs(0.0f, 0.3f, 0.6f);
        givenAllUniqueItemsAlreadyDroppedExcept(ItemType.ToiletPaper, ItemType.DungeonDoor);
        wizard.itemStash.add(ItemType.ToiletPaper);

        whenCardIsTransmuted(ItemType.ToiletPaper);

        assertThat(wizard.itemStash.size()).isEqualTo(1);
        assertThat(wizard.itemStash.get(0).cardType).isEqualTo(ItemType.DungeonDoor);
        assertThat(wizard.potionStash.size()).isEqualTo(2);
    }

    @Test
    void transmuted_allUniquesAndLegendariesAlreadyDropped_exceptWeddingRings() {
        givenAllUniqueItemsAlreadyDroppedExcept(ItemType.ToiletPaper, ItemType.WeddingRing1, ItemType.WeddingRing2);
        wizard.itemStash.add(ItemType.ToiletPaper);

        whenCardIsTransmuted(ItemType.ToiletPaper);

        assertThat(wizard.itemStash.size()).isEqualTo(2);
        assertThat(wizard.itemStash.get(0).cardType).isEqualTo(ItemType.WeddingRing1);
        assertThat(wizard.itemStash.get(1).cardType).isEqualTo(ItemType.WeddingRing2);
    }

    @Test
    void transmuted_weddingRingsDropInPairs() {
        givenAllUniqueItemsAlreadyDroppedExcept(ItemType.ToiletPaper, ItemType.Excalibur, ItemType.DungeonDoor, ItemType.WeddingRing1, ItemType.WeddingRing2);
        wizard.itemStash.add(ItemType.ToiletPaper);

        whenCardIsTransmuted(ItemType.ToiletPaper);

        assertThat(wizard.itemStash.size()).isEqualTo(3);
        assertThat(wizard.itemStash.get(0).cardType).isEqualTo(ItemType.WeddingRing1);
        assertThat(wizard.itemStash.get(1).cardType).isEqualTo(ItemType.WeddingRing2);
        assertThat(wizard.itemStash.get(2).cardType).isEqualTo(ItemType.DungeonDoor);
    }

    @Test
    void transmuted_weddingRingsDropInPairs_weddingRing1IsLastDrop() {
        randomPluginTrainer.givenFloatAbs(0.9f, 0.0f, 0.0f);
        givenAllUniqueItemsAlreadyDroppedExcept(ItemType.ToiletPaper, ItemType.Excalibur, ItemType.DungeonDoor, ItemType.WeddingRing1, ItemType.WeddingRing2);
        wizard.itemStash.add(ItemType.ToiletPaper);

        whenCardIsTransmuted(ItemType.ToiletPaper);

        assertThat(wizard.itemStash.size()).isEqualTo(4);
        assertThat(wizard.itemStash.get(0).cardType).isEqualTo(ItemType.WeddingRing2);
        assertThat(wizard.itemStash.get(1).cardType).isEqualTo(ItemType.WeddingRing1);
        assertThat(wizard.itemStash.get(2).cardType).isEqualTo(ItemType.DungeonDoor);
        assertThat(wizard.itemStash.get(3).cardType).isEqualTo(ItemType.Excalibur);
    }

    @Test
    void transmuted_weddingRingsDropInPairs_weddingRing2IsLastDrop() {
        randomPluginTrainer.givenFloatAbs(0.9f, 0.0f, 0.9f);
        givenAllUniqueItemsAlreadyDroppedExcept(ItemType.ToiletPaper, ItemType.Excalibur, ItemType.DungeonDoor, ItemType.WeddingRing1, ItemType.WeddingRing2);
        wizard.itemStash.add(ItemType.ToiletPaper);

        whenCardIsTransmuted(ItemType.ToiletPaper);

        assertThat(wizard.itemStash.size()).isEqualTo(4);
        assertThat(wizard.itemStash.get(0).cardType).isEqualTo(ItemType.WeddingRing1);
        assertThat(wizard.itemStash.get(1).cardType).isEqualTo(ItemType.WeddingRing2);
        assertThat(wizard.itemStash.get(2).cardType).isEqualTo(ItemType.DungeonDoor);
        assertThat(wizard.itemStash.get(3).cardType).isEqualTo(ItemType.Excalibur);
    }
}