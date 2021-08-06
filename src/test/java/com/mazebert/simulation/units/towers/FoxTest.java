package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Wave;
import com.mazebert.simulation.units.items.ItemTest;
import com.mazebert.simulation.units.potions.PotionType;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
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

        whenNextRoundIsStarted();

        assertThat(rabbit.isDisposed()).isTrue();
        assertThat(fox.getExperience()).isEqualTo(6000);
        assertThat(fox.getCritChance()).isEqualTo(0.09f);

        CustomTowerBonus bonus = new CustomTowerBonus();
        fox.populateCustomTowerBonus(bonus);
        assertThat(bonus.title).isEqualTo("Rabbits eaten:");
        assertThat(bonus.value).isEqualTo("1");
    }

    @Test
    void foxEatsRabbit_chanceNotMet() {
        Tower rabbit = whenTowerIsBuilt(wizard, TowerType.Rabbit, 1, 0);
        randomPluginTrainer.givenFloatAbs(0.2f);

        whenNextRoundIsStarted();

        assertThat(rabbit.isDisposed()).isFalse();
    }

    @Test
    void rabbitCannotBeEatenTwice() {
        Tower otherFox = whenTowerIsBuilt(wizard, TowerType.Fox, 1, 1);
        Tower rabbit = whenTowerIsBuilt(wizard, TowerType.Rabbit, 1, 0);
        rabbit.setExperience(5000);

        whenNextRoundIsStarted();

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

        whenNextRoundIsStarted();

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

        whenNextRoundIsStarted();

        assertThat(rabbit.isDisposed()).isTrue();
        assertThat(fox.getAddedRelativeBaseDamage()).isEqualTo(0.2016f);
        assertThat(fox.getAttackSpeedAdd()).isEqualTo(0.1608f);
    }

    void whenNextRoundIsStarted() {
        simulationListeners.onRoundStarted.dispatch(new Wave());
    }
}