package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.InstantDamageAbility;
import com.mazebert.simulation.units.creeps.Creep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.units.creeps.CreepBuilder.creep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

strictfp class UselessMachineTest extends ItemTest {
    @BeforeEach
    void setUp() {
        tower.setLevel(1);
        tower.addAbility(new AttackAbility());
        tower.addAbility(new InstantDamageAbility());
    }

    @Test
    void dealNoDamage() {
        Creep creep = a(creep());
        unitGateway.addUnit(creep);
        damageSystemTrainer.givenRealDamageSystemIsUsed();
        whenItemIsEquipped(ItemType.UselessMachine);

        whenTowerAttacks();

        assertThat(creep.getHealth()).isEqualTo(100.0);
    }

    @Test
    void dealDamageAgainWhenDropped() {
        Creep creep = a(creep());
        unitGateway.addUnit(creep);
        damageSystemTrainer.givenRealDamageSystemIsUsed();
        whenItemIsEquipped(ItemType.UselessMachine);
        whenItemIsEquipped(null);

        whenTowerAttacks();

        assertThat(creep.getHealth()).isEqualTo(72.5);
    }
}