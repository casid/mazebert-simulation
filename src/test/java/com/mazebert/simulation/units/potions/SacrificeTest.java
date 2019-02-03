package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.units.items.ItemTest;
import com.mazebert.simulation.units.towers.Tower;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class SacrificeTest extends ItemTest {
    @Test
    void level1() {
        tower.setLevel(1);
        whenPotionIsConsumed(PotionType.Sacrifice);
        thenHealthIs(1.01f);
    }

    @Test
    void level10() {
        tower.setLevel(10);
        whenPotionIsConsumed(PotionType.Sacrifice);
        thenHealthIs(1.1f);
    }

    @Test
    void towerIsRemoved() {
        whenPotionIsConsumed(PotionType.Sacrifice);
        assertThat(unitGateway.getAmount(Tower.class)).isEqualTo(0);
    }

    private void thenHealthIs(float expected) {
        assertThat(wizard.health).isEqualTo(expected);
        assertThat(gameGateway.getGame().health).isEqualTo(expected);
    }
}