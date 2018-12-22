package com.mazebert.simulation.units.items;

import com.mazebert.simulation.systems.ExperienceSystem;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.towers.Tower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class KeyOfWisdomTest extends ItemTest {

    @BeforeEach
    void setUp() {
        experienceSystem = new ExperienceSystem();
    }

    @Test
    void onlyThisTower() {
        whenItemIsEquipped(ItemType.KeyOfWisdom);
        tower.simulate(KeyOfWisdomAbility.cooldown);

        assertThat(tower.getExperience()).isEqualTo(KeyOfWisdomAbility.experience);
    }

    @Test
    void anotherTower() {
        randomPluginTrainer.givenFloatAbs(0.6f);
        Tower anotherTower = new TestTower();
        unitGateway.addUnit(anotherTower);

        whenItemIsEquipped(ItemType.KeyOfWisdom);
        tower.simulate(KeyOfWisdomAbility.cooldown);

        assertThat(tower.getExperience()).isEqualTo(0.0f);
        assertThat(anotherTower.getExperience()).isEqualTo(KeyOfWisdomAbility.experience);
    }
}