package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Balancing;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.units.items.ItemTest;
import com.mazebert.simulation.units.towers.Beacon;
import com.mazebert.simulation.units.towers.TowerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LeuchtfeuerTest extends ItemTest {

    Beacon beacon;

    @BeforeEach
    void setUp() {
        wizard.addGold(10000);
        beacon = (Beacon)whenTowerNeighbourIsBuilt(tower, TowerType.Beacon, 1, 0);
        tower.setElement(Element.Light);
    }

    @Test
    void onlyForLightTowers() {
        tower.setElement(Element.Nature);
        whenPotionIsConsumed(PotionType.LeuchtFeuer);
        assertThat(tower.getMaxLevel()).isEqualTo(99);
    }


    @Test
    void maxLevelIncreased() {
        whenPotionIsConsumed(PotionType.LeuchtFeuer);
        assertThat(tower.getMaxLevel()).isEqualTo(100);
    }

    @Test
    void maxLevelIncreased_4Potions() {
        whenPotionIsConsumed(PotionType.LeuchtFeuer);
        whenPotionIsConsumed(PotionType.LeuchtFeuer);
        whenPotionIsConsumed(PotionType.LeuchtFeuer);
        whenPotionIsConsumed(PotionType.LeuchtFeuer);

        assertThat(tower.getMaxLevel()).isEqualTo(103);
    }

    @Test
    void replacedWithOtherElementTower() {
        whenPotionIsConsumed(PotionType.LeuchtFeuer);
        whenPotionIsConsumed(PotionType.LeuchtFeuer);
        whenPotionIsConsumed(PotionType.LeuchtFeuer);
        whenPotionIsConsumed(PotionType.LeuchtFeuer);

        tower = whenTowerIsReplaced(tower, TowerType.Rabbit);

        assertThat(tower.getMaxLevel()).isEqualTo(99);
    }

    @Test
    void replacedWithOtherElementTower_thenWithLightTowerAgain() {
        whenPotionIsConsumed(PotionType.LeuchtFeuer);
        whenPotionIsConsumed(PotionType.LeuchtFeuer);
        whenPotionIsConsumed(PotionType.LeuchtFeuer);
        whenPotionIsConsumed(PotionType.LeuchtFeuer);

        tower = whenTowerIsReplaced(tower, TowerType.Rabbit);
        tower = whenTowerIsReplaced(tower, TowerType.Guard);

        assertThat(tower.getMaxLevel()).isEqualTo(103);
    }

    @Test
    void potionEffectiveness() {
        tower.addPotionEffectiveness(-0.6f); // Results in 40% effectiveness

        whenPotionIsConsumed(PotionType.LeuchtFeuer); // +0.4 max level
        whenPotionIsConsumed(PotionType.LeuchtFeuer); // +0.4 max level

        assertThat(tower.getMaxLevel()).isEqualTo(100); // 0.8 is rounded up

        tower.addPotionEffectiveness(+0.6f); // Back to 100% effectiveness

        assertThat(tower.getMaxLevel()).isEqualTo(101);
    }

    @Test
    void beaconOfHopeSold_beforeDrinking() {
        whenTowerIsSold(beacon);

        whenPotionIsConsumed(PotionType.LeuchtFeuer);

        assertThat(tower.getMaxLevel()).isEqualTo(99);
    }

    @Test
    void beaconOfHopeSold_afterDrinking() {
        whenPotionIsConsumed(PotionType.LeuchtFeuer);

        whenTowerIsSold(beacon);

        assertThat(tower.getMaxLevel()).isEqualTo(99);
    }

    @Nested
    class ExistingExperience {
        @BeforeEach
        void setUp() {
            tower.setExperience(Balancing.getTowerExperienceForLevel(112));
        }

        @Test
        void onlyForLightTowers() {
            tower.setElement(Element.Nature);
            whenPotionIsConsumed(PotionType.LeuchtFeuer);
            assertThat(tower.getLevel()).isEqualTo(99);
        }


        @Test
        void levelIncreased() {
            whenPotionIsConsumed(PotionType.LeuchtFeuer);
            assertThat(tower.getLevel()).isEqualTo(100);
        }

        @Test
        void levelIncreased_notMoreThanMaxLevel() {
            for (int i = 0; i < 20; i++) {
                whenPotionIsConsumed(PotionType.LeuchtFeuer);
            }

            assertThat(tower.getLevel()).isEqualTo(112);
        }

        @Test
        void replacedWithOtherElementTower() {
            whenPotionIsConsumed(PotionType.LeuchtFeuer);
            whenPotionIsConsumed(PotionType.LeuchtFeuer);
            whenPotionIsConsumed(PotionType.LeuchtFeuer);
            whenPotionIsConsumed(PotionType.LeuchtFeuer);

            tower = whenTowerIsReplaced(tower, TowerType.Rabbit);

            assertThat(tower.getLevel()).isEqualTo(99);
        }

        @Test
        void replacedWithOtherElementTower_thenWithLightTowerAgain() {
            whenPotionIsConsumed(PotionType.LeuchtFeuer);
            whenPotionIsConsumed(PotionType.LeuchtFeuer);
            whenPotionIsConsumed(PotionType.LeuchtFeuer);
            whenPotionIsConsumed(PotionType.LeuchtFeuer);

            tower = whenTowerIsReplaced(tower, TowerType.Rabbit);
            tower = whenTowerIsReplaced(tower, TowerType.Guard);

            assertThat(tower.getLevel()).isEqualTo(103);
        }

        @Test
        void potionEffectiveness() {
            tower.addPotionEffectiveness(-0.6f); // Results in 40% effectiveness

            whenPotionIsConsumed(PotionType.LeuchtFeuer); // +0.4 max level
            whenPotionIsConsumed(PotionType.LeuchtFeuer); // +0.4 max level

            assertThat(tower.getLevel()).isEqualTo(100); // 0.8 is rounded up

            tower.addPotionEffectiveness(+0.6f); // Back to 100% effectiveness

            assertThat(tower.getLevel()).isEqualTo(101);
        }
    }
}