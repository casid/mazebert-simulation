package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.listeners.OnAttackListener;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AttackAbilityTest extends SimTest implements OnAttackListener {

    Tower tower;
    Creep creep;

    List<Creep> attackedUnits = new ArrayList<>();

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();

        tower = new TestTower();
        tower.setBaseCooldown(1.0f);
        tower.setBaseRange(1.0f);
        tower.addAbility(new AttackAbility());
        tower.onAttack.add(this);

        creep = new Creep();
        unitGateway.addUnit(creep);
    }

    @Test
    void attack_tooSoon() {
        tower.simulate(0.1f);
        thenNoUnitIsAttacked();
    }

    @Test
    void attack_creepInRange() {
        whenTowerAttacks();
        thenUnitIsAttacked(creep);
    }

    @Test
    void attack_creepInRange_exactly_right() {
        creep.setX(tower.getBaseRange());
        whenTowerAttacks();
        thenUnitIsAttacked(creep);
    }

    @Test
    void attack_creepInRange_exactly_left() {
        creep.setX(-tower.getBaseRange());
        whenTowerAttacks();
        thenUnitIsAttacked(creep);
    }

    @Test
    void attack_creepInRange_exactly_rightTop() {
        creep.setX(tower.getBaseRange());
        creep.setY(tower.getBaseRange());

        whenTowerAttacks();

        thenUnitIsAttacked(creep);
    }

    @Test
    void attack_creepInRange_exactly_leftBottom() {
        creep.setX(-tower.getBaseRange());
        creep.setY(-tower.getBaseRange());

        whenTowerAttacks();

        thenUnitIsAttacked(creep);
    }

    @Test
    void attack_creepOutOfRange_right() {
        creep.setX(1.1f);
        whenTowerAttacks();
        thenNoUnitIsAttacked();
    }

    @Test
    void attack_creepOutOfRange_left() {
        creep.setX(-1.1f);
        whenTowerAttacks();
        thenNoUnitIsAttacked();
    }

    @Test
    void attack_creepOutOfRange_top() {
        creep.setY(1.1f);
        whenTowerAttacks();
        thenNoUnitIsAttacked();
    }

    @Test
    void attack_creepOutOfRange_bottom() {
        creep.setY(-1.1f);
        whenTowerAttacks();
        thenNoUnitIsAttacked();
    }

    @Test
    void attack_noCreeps() {
        unitGateway.removeUnit(creep);
        whenTowerAttacks();
        thenNoUnitIsAttacked();
    }

    @Test
    void attack_sticky() {
        // first creep is out of range
        creep.setX(100.0f);

        // add another one that is in range
        Creep anotherCreep = new Creep();
        unitGateway.addUnit(anotherCreep);

        whenTowerAttacks();

        // first creep comes in range
        creep.setX(0.5f);

        whenTowerAttacks();

        thenUnitsAreAttacked(anotherCreep, anotherCreep);
    }

    @Test
    void attack_sticky_changesWhenOutOfRange() {
        Creep anotherCreep = new Creep();
        unitGateway.addUnit(anotherCreep);

        whenTowerAttacks();
        creep.setX(100.0f);
        whenTowerAttacks();

        thenUnitsAreAttacked(creep, anotherCreep);
    }

    @Test
    void attack_sticky_changesWhenDead() {
        Creep anotherCreep = new Creep();
        unitGateway.addUnit(anotherCreep);

        whenTowerAttacks();
        creep.setHealth(0);
        whenTowerAttacks();

        thenUnitsAreAttacked(creep, anotherCreep);
    }

    @Test
    void attack_cooldownIsSpared() {
        creep.setX(1000);
        tower.simulate(1.0f);
        thenNoUnitIsAttacked();

        creep.setX(1);
        tower.simulate(0.1f);
        thenUnitIsAttacked(creep);
    }

    @Override
    public void onAttack(Creep target) {
        attackedUnits.add(target);
    }

    private void whenTowerAttacks() {
        tower.simulate(1.0f);
    }

    private void thenNoUnitIsAttacked() {
        assertThat(attackedUnits).isEmpty();
    }

    private void thenUnitIsAttacked(Creep unit) {
        assertThat(attackedUnits).contains(unit);
    }

    private void thenUnitsAreAttacked(Creep... units) {
        assertThat(attackedUnits).containsExactly(units);
    }
}