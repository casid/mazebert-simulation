package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.systems.WeddingRingSystem;
import com.mazebert.simulation.units.abilities.ActiveAbilityType;
import com.mazebert.simulation.units.items.ItemTest;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.potions.PotionType;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FoxTest extends ItemTest {

    Tower fox;

    @BeforeEach
    void setUp() {
        wizard.addGold(10000);
    }

    @Override
    protected Tower createTower() {
        return fox = new Fox();
    }

    @Test
    void foxEatsRabbit() {
        fox.setExperience(1000);
        Tower rabbit = whenTowerIsBuilt(wizard, TowerType.Rabbit, 1, 0);
        rabbit.setExperience(5000);

        whenAbilityIsActivated();

        assertThat(rabbit.isDisposed()).isTrue();
        assertThat(fox.getExperience()).isEqualTo(6000);
        assertThat(fox.getCritChance()).isEqualTo(0.09f);

        CustomTowerBonus bonus = new CustomTowerBonus();
        fox.populateCustomTowerBonus(bonus);
        assertThat(bonus.title).isEqualTo("Rabbits eaten:");
        assertThat(bonus.value).isEqualTo("1");
    }

    @Test
    void rabbitCannotBeEatenTwice() {
        Tower otherFox = whenTowerIsBuilt(wizard, TowerType.Fox, 1, 1);
        Tower rabbit = whenTowerIsBuilt(wizard, TowerType.Rabbit, 1, 0);
        rabbit.setExperience(5000);

        whenAbilityIsActivated();

        assertThat(rabbit.isDisposed()).isTrue();
        assertThat(fox.getExperience()).isEqualTo(5000);
        assertThat(otherFox.getExperience()).isEqualTo(0);
    }

    @Test
    void doesNotEatRabbitsOfOtherWizard() {
        Wizard otherWizard = new Wizard();
        otherWizard.playerId = 2;
        otherWizard.addGold(10000);
        unitGateway.addUnit(otherWizard);

        Tower rabbit = whenTowerIsBuilt(otherWizard, TowerType.Rabbit, 1, 0);

        whenAbilityIsActivated();

        assertThat(rabbit.isDisposed()).isFalse();
    }

    @Test
    void rabbitPotionsAreConsumed() {
        Tower rabbit = whenTowerIsBuilt(wizard, TowerType.Rabbit, 1, 0);
        whenPotionIsConsumed(rabbit, PotionType.CommonDamage);
        whenPotionIsConsumed(rabbit, PotionType.CommonDamage);
        whenPotionIsConsumed(rabbit, PotionType.CommonDamage);
        whenPotionIsConsumed(rabbit, PotionType.CommonDamage);
        whenPotionIsConsumed(rabbit, PotionType.CommonSpeed);
        whenPotionIsConsumed(rabbit, PotionType.CommonSpeed);
        whenPotionIsConsumed(rabbit, PotionType.CommonSpeed);
        whenPotionIsConsumed(rabbit, PotionType.CommonSpeed);

        whenAbilityIsActivated();

        assertThat(rabbit.isDisposed()).isTrue();
        assertThat(fox.getAddedRelativeBaseDamage()).isEqualTo(0.2016f);
        assertThat(fox.getAttackSpeedAdd()).isEqualTo(0.1608f);
    }

    @Test
    void rabbitPotionsHalvedWhenReplaced_noRabbitEaten() {
        whenPotionIsConsumed(fox, PotionType.CommonDamage);
        whenPotionIsConsumed(fox, PotionType.CommonDamage);

        Tower adventurer = whenTowerIsReplaced(fox, TowerType.Adventurer);

        assertThat(adventurer.getAddedRelativeBaseDamage()).isEqualTo(0.1008f);
    }

    @Test
    void rabbitPotionsHalvedWhenReplaced() {
        Tower rabbit = whenTowerIsBuilt(wizard, TowerType.Rabbit, 1, 0);
        whenPotionIsConsumed(rabbit, PotionType.CommonDamage);
        whenPotionIsConsumed(rabbit, PotionType.CommonDamage);
        whenPotionIsConsumed(rabbit, PotionType.CommonDamage);
        whenPotionIsConsumed(rabbit, PotionType.CommonDamage);
        whenAbilityIsActivated();

        Tower adventurer = whenTowerIsReplaced(fox, TowerType.Adventurer);

        assertThat(adventurer.getAddedRelativeBaseDamage()).isEqualTo(0.1008f);
    }

    @Test
    void rabbitPotionsHalvedWhenReplaced_foxPotionsKept() {
        Tower rabbit = whenTowerIsBuilt(wizard, TowerType.Rabbit, 1, 0);
        whenPotionIsConsumed(fox, PotionType.CommonDamage);
        whenPotionIsConsumed(fox, PotionType.CommonDamage);
        whenPotionIsConsumed(rabbit, PotionType.CommonDamage);
        whenPotionIsConsumed(rabbit, PotionType.CommonDamage);
        whenAbilityIsActivated();

        Tower adventurer = whenTowerIsReplaced(fox, TowerType.Adventurer);

        assertThat(adventurer.getAddedRelativeBaseDamage()).isEqualTo(0.1512f);
    }

    void whenAbilityIsActivated() {
        whenAbilityIsActivated(fox, ActiveAbilityType.FoxHunt);
    }

    @Nested
    class WeddingRings {
        Tower rabbit;

        @BeforeEach
        void setUp() {
            weddingRingSystem = new WeddingRingSystem();

            rabbit = whenTowerIsBuilt(wizard, TowerType.Rabbit, 1, 0);

            whenItemIsEquipped(fox, ItemType.WeddingRing1);
            whenItemIsEquipped(rabbit, ItemType.WeddingRing2);

            simulationListeners.onUpdate.dispatch(WeddingRingSystem.SECONDS_FOR_MARRIAGE);
        }

        @Test
        void rabbitPotionsHalvedWhenReplaced_notForUniquePotions() {
            whenPotionIsConsumed(fox, PotionType.Tears);
            whenAbilityIsActivated();

            Tower adventurer = whenTowerIsReplaced(fox, TowerType.Adventurer);

            assertThat(adventurer.getMulticrit()).isEqualTo(3); // Tears give +1 each, +1 initial mc
        }

        @Test
        void rabbitPotionsHalvedWhenReplaced_cardDust() {
            whenPotionIsConsumed(fox, PotionType.CardDustCrit);
            whenPotionIsConsumed(fox, PotionType.CardDustCrit);
            whenPotionIsConsumed(fox, PotionType.CardDustCrit);
            whenPotionIsConsumed(fox, PotionType.CardDustCrit);
            whenAbilityIsActivated(); // Rabbit is eaten here
            assertThat(fox.getMulticrit()).isEqualTo(9);

            Tower adventurer = whenTowerIsReplaced(fox, TowerType.Adventurer);

            assertThat(adventurer.getMulticrit()).isEqualTo(7); // +4 from drinking itself, +2 from married & eaten rabbit, +1 initial mc
        }
    }
}