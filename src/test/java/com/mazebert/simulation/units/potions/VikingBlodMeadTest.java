package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.items.ItemTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VikingBlodMeadTest extends ItemTest {
    @Test
    void extraGoreForClient() {
        whenPotionIsConsumed(PotionType.VikingBlodMead);

        Creep creep = new Creep();
        tower.kill(creep);

        assertThat(creep.isExtraGore()).isTrue();
    }

    @Test
    void extraGoreForClient_notWithoutPotion() {
        Creep creep = new Creep();
        tower.kill(creep);

        assertThat(creep.isExtraGore()).isFalse();
    }
}