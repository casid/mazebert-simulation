package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.towers.Mummy;
import com.mazebert.simulation.units.towers.Tower;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ToiletPaper_MummyTest extends ItemTest {

    @Override
    protected Tower createTower() {
        return new Mummy();
    }

    @Test
    void targetsIncreased() {
        whenItemIsEquipped(ItemType.ToiletPaper);
        assertThat(tower.getAbility(AttackAbility.class).getTargets()).isEqualTo(4);
    }
}