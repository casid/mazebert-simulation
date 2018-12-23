package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.InstantDamageAbility;
import com.mazebert.simulation.units.creeps.Creep;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.units.creeps.CreepBuilder.creep;
import static org.jusecase.Builders.a;

strictfp class FrozenTest extends ItemTest {
    @BeforeEach
    void setUp() {
        tower.addAbility(new AttackAbility());
        tower.addAbility(new InstantDamageAbility());
    }

    @Test
    void twoItems() {
        Creep creep = a(creep());
        unitGateway.addUnit(creep);

        whenItemIsEquipped(ItemType.FrozenWater, 0);
        whenItemIsEquipped(ItemType.FrozenHeart, 1);

        tower.simulate(tower.getCooldown());

        Assertions.assertThat(creep.getSpeedModifier()).isEqualTo(0.9f);
    }

    @Test
    void twoItems_oneDropped() {
        Creep creep = a(creep());
        unitGateway.addUnit(creep);

        whenItemIsEquipped(ItemType.FrozenWater, 0);
        whenItemIsEquipped(ItemType.FrozenHeart, 1);
        whenItemIsEquipped(null, 0);

        tower.simulate(tower.getCooldown());

        Assertions.assertThat(creep.getSpeedModifier()).isEqualTo(1.0f);
    }
}