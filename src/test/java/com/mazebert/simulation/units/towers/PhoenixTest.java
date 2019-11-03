package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.commands.ActivateAbilityCommand;
import com.mazebert.simulation.units.abilities.ActiveAbilityType;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.items.ItemTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.units.creeps.CreepBuilder.creep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

public strictfp class PhoenixTest extends ItemTest {

    Phoenix phoenix;

    @BeforeEach
    void setUp() {
        wizard.gold = PhoenixRebirth.GOLD_COST;
        wizard.towerStash.add(TowerType.Phoenix);
    }

    @Override
    protected Tower createTower() {
        phoenix = new Phoenix();
        return phoenix;
    }

    @Test
    void creepBurns() {
        Creep creep = a(creep());
        creep.setX(2);
        unitGateway.addUnit(creep);

        creep.simulate(0.1f);
        assertThat(creep.getHealth()).isEqualTo(98.69999998062849);
        creep.simulate(0.1f);
        assertThat(creep.getHealth()).isEqualTo(97.39999996125698);
    }

    @Test
    void creepsBurn() {
        Creep creep1 = a(creep());
        creep1.setX(+2);
        unitGateway.addUnit(creep1);

        Creep creep2 = a(creep());
        creep2.setX(-2);
        unitGateway.addUnit(creep2);

        creep1.simulate(0.1f);
        creep2.simulate(0.1f);
        assertThat(creep1.getHealth()).isEqualTo(98.69999998062849);
        assertThat(creep2.getHealth()).isEqualTo(98.69999998062849);
        creep1.simulate(0.1f);
        creep2.simulate(0.1f);
        assertThat(creep1.getHealth()).isEqualTo(97.39999996125698);
        assertThat(creep2.getHealth()).isEqualTo(97.39999996125698);
    }

    @Test
    void creepsCanBeKilled() {
        Creep creep = a(creep());
        creep.setX(2);
        unitGateway.addUnit(creep);

        creep.simulate(100.0f);
        assertThat(creep.isDead()).isTrue();
    }

    @Test
    void creepLeavesAura() {
        Creep creep = a(creep());
        creep.setX(2);
        unitGateway.addUnit(creep);

        creep.simulate(0.1f);
        creep.setX(4);
        phoenix.simulate(0.1f);
        creep.simulate(0.1f);
        creep.simulate(0.1f);
        creep.simulate(0.1f);
        assertThat(creep.getHealth()).isEqualTo(98.69999998062849);
    }

    @Test
    void rebirth() {
        whenAbilityIsActivated();

        assertThat(phoenix.getAddedAbsoluteBaseDamage()).isEqualTo(PhoenixRebirth.DAMAGE_GAIN);
        assertThat(wizard.gold).isEqualTo(0);
        assertThat(wizard.towerStash.size()).isEqualTo(0);
    }

    @Test
    void rebirth_multipleTimes() {
        wizard.gold += PhoenixRebirth.GOLD_COST;
        wizard.towerStash.add(TowerType.Phoenix);

        whenAbilityIsActivated();
        tower.simulate(PhoenixRebirth.REBIRTH_TIME);
        whenAbilityIsActivated();

        assertThat(phoenix.getAddedAbsoluteBaseDamage()).isEqualTo(2 * PhoenixRebirth.DAMAGE_GAIN);
        assertThat(wizard.gold).isEqualTo(0);
        assertThat(wizard.towerStash.size()).isEqualTo(0);
    }

    @Test
    void rebirth_notEnoughGold() {
        wizard.gold = 0;
        whenAbilityIsActivated();
        assertThat(phoenix.getAddedAbsoluteBaseDamage()).isEqualTo(0);
    }

    @Test
    void rebirth_noPhoenixCard() {
        wizard.towerStash.remove(TowerType.Phoenix);
        whenAbilityIsActivated();
        assertThat(phoenix.getAddedAbsoluteBaseDamage()).isEqualTo(0);
    }

    @Test
    void rebirth_notAliveYet() {
        wizard.gold += PhoenixRebirth.GOLD_COST;
        wizard.towerStash.add(TowerType.Phoenix);

        whenAbilityIsActivated();

        assertThat(phoenix.getAbility(PhoenixRebirth.class).isReady()).isFalse();
    }

    @Test
    void rebirth_noDamage() {
        Creep creep = a(creep());
        unitGateway.addUnit(creep);

        whenAbilityIsActivated();
        creep.simulate(0.1f);
        creep.simulate(0.1f);
        creep.simulate(0.1f);
        assertThat(creep.getHealth()).isEqualTo(100);

        phoenix.simulate(PhoenixRebirth.REBIRTH_TIME);
        creep.simulate(0.1f);
        assertThat(creep.getHealth()).isEqualTo(98.69999998062849);
    }

    private void whenAbilityIsActivated() {
        whenAbilityIsActivated(phoenix, ActiveAbilityType.PhoenixRebirth);
    }
}