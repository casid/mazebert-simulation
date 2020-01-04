package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Element;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.systems.WeddingRingSystem;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.items.ItemTest;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.potions.PotionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.units.creeps.CreepBuilder.creep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

class Unicorn_YggdrasilTest extends ItemTest {

    Unicorn unicorn;
    Yggdrasil yggdrasil;
    TestTower tower1;
    TestTower tower2;

    @Override
    protected Tower createTower() {
        unicorn = new Unicorn();
        return unicorn;
    }

    @BeforeEach
    void setUp() {
        unicorn.setLevel(50);

        tower1 = new TestTower();
        tower1.setX(1);
        tower1.setWizard(wizard);
        tower1.setElement(Element.Nature);
        unitGateway.addUnit(tower1);

        tower2 = new TestTower();
        tower2.setX(2);
        tower2.setWizard(wizard);
        tower2.setElement(Element.Nature);
        unitGateway.addUnit(tower2);

        yggdrasil = new Yggdrasil();
        yggdrasil.setX(3);
        yggdrasil.setWizard(wizard);
        unitGateway.addUnit(yggdrasil);

        whenItemIsEquipped(yggdrasil, null, 0);
        whenItemIsEquipped(yggdrasil, null, 1);

        whenItemIsEquipped(tower1, ItemType.BranchOfYggdrasil);
        whenItemIsEquipped(tower2, ItemType.BranchOfYggdrasil);

        randomPluginTrainer.givenFloatAbs(0.99f);
        unitGateway.addUnit(a(creep().withWizard(wizard)));
    }

    @Test
    void v18() {
        version = Sim.vDoL;

        whenPotionIsConsumed(yggdrasil, PotionType.UnicornTears);

        assertThat(yggdrasil.getLevel()).isEqualTo(25);
        assertThat(tower1.getLevel()).isEqualTo(1);
        assertThat(tower2.getLevel()).isEqualTo(1);
    }

    @Test
    void v19() {
        version = Sim.v19;

        whenPotionIsConsumed(yggdrasil, PotionType.UnicornTears);

        assertThat(yggdrasil.getLevel()).isEqualTo(75);
        assertThat(tower1.getLevel()).isEqualTo(25);
        assertThat(tower2.getLevel()).isEqualTo(25);
    }
}