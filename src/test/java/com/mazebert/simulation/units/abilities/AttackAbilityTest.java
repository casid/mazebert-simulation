package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.listeners.OnAttackListener;
import com.mazebert.simulation.units.towers.Tower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.jusecase.inject.ComponentTest;
import org.jusecase.inject.Trainer;

import static org.assertj.core.api.Assertions.assertThat;

public class AttackAbilityTest implements ComponentTest, OnAttackListener {

    @Trainer
    UnitGateway unitGateway;

    Tower tower;
    Creep creep;

    Unit attackedUnit;

    @BeforeEach
    void setUp() {
        tower = new Tower();
        tower.setCooldown(1.0f);
        tower.addAbility(new AttackAbility());
        tower.onAttack.add(this);

        creep = new Creep();
        unitGateway.addUnit(creep);
    }

    @Test
    void attack_tooSoon() {
        tower.simulate(0.1f);
        assertThat(attackedUnit).isNull();
    }

    @Test
    void attack_creepInRange() {
        tower.simulate(1.0f);
        assertThat(attackedUnit).isSameAs(creep);
    }

    @Test
    void attack_noCreeps() {
        unitGateway.removeUnit(creep);
        tower.simulate(1.0f);
        assertThat(attackedUnit).isNull();
    }

    @Override
    public void onAttack(Unit unit) {
        attackedUnit = unit;
    }
}