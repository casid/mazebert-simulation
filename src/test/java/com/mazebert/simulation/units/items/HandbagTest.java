package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.Gender;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class HandbagTest extends ItemTest {
    @Test
    void male() {
        tower.setGender(Gender.Male);
        whenItemIsEquipped(ItemType.Handbag);
        assertThat(tower.getItemChance()).isEqualTo(1.0f);
    }

    @Test
    void female() {
        tower.setGender(Gender.Female);
        whenItemIsEquipped(ItemType.Handbag);
        assertThat(tower.getItemChance()).isEqualTo(1.3f);
    }

    @Test
    void genderChanged() {
        tower.setGender(Gender.Male);
        whenItemIsEquipped(ItemType.Handbag);
        assertThat(tower.getItemChance()).isEqualTo(1.0f);

        tower.setGender(Gender.Female);
        assertThat(tower.getItemChance()).isEqualTo(1.3f);

        tower.setGender(Gender.Male);
        assertThat(tower.getItemChance()).isEqualTo(0.99999994f);
    }
}