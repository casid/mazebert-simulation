package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.abilities.AttackAbility;
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
}