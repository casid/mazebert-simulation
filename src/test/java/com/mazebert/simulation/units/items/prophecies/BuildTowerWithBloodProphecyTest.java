package com.mazebert.simulation.units.items.prophecies;

import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.towers.TowerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BuildTowerWithBloodProphecyTest extends ProphecyTest {

    @BeforeEach
    void setUp() {
        wizard.gold = 100;
    }

    @Test
    void noProphecy() {
        Tower moneyBin = whenTowerIsBuilt(wizard, TowerType.MoneyBin, 1, 1);

        assertThat(moneyBin).isNull();
    }

    @Test
    void prophecy() {
        whenItemIsEquipped(veleda, ItemType.BuildTowerWithBloodProphecy);

        Tower moneyBin = whenTowerIsBuilt(wizard, TowerType.MoneyBin, 1, 1);

        assertThat(wizard.gold).isEqualTo(100);
        assertThat(wizard.health).isEqualTo(0.8f);
        assertThat(moneyBin).isNotNull();
        assertThat(veleda.getItem(0)).isNull(); // Prophecy was used
    }

    @Test
    void veledaReplaced() {
        whenItemIsEquipped(veleda, ItemType.BuildTowerWithBloodProphecy);

        Tower moneyBin = whenTowerIsBuilt(wizard, TowerType.MoneyBin, 0, 0);

        assertThat(wizard.gold).isEqualTo(1156); // returned gold for Veleda
        assertThat(wizard.health).isEqualTo(0.8f);
        assertThat(moneyBin).isNotNull();
        assertThat(wizard.itemStash.size()).isEqualTo(0); // BuildTowerWithBloodProphecy was used, not returned to inventory
    }

    @Test
    void veledaReplaced_otherProphecies() {
        whenItemIsEquipped(veleda, ItemType.BuildTowerWithBloodProphecy, 0);
        whenItemIsEquipped(veleda, ItemType.HungoverChallengeProphecy, 1);

        whenTowerIsBuilt(wizard, TowerType.MoneyBin, 0, 0);

        assertThat(wizard.itemStash.size()).isEqualTo(1); // BuildTowerWithBloodProphecy was used, not returned to inventory
        assertThat(wizard.itemStash.get(0).getCardType()).isEqualTo(ItemType.HungoverChallengeProphecy); // HungoverChallengeProphecy returned to inventory
    }
}