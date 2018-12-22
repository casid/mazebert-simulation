package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.projectiles.ProjectileGateway;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.creeps.Creep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class HuliTest extends SimTest {

    private Huli huli;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        projectileGateway = new ProjectileGateway();

        huli = new Huli();
        unitGateway.addUnit(huli);
    }

    @Test
    void eat() {
        huli.simulate(1.5f);
        huli.simulate(1.5f); // banana eaten here

        assertThat(huli.getAddedRelativeBaseDamage()).isEqualTo(0.02f);
    }

    @Test
    void eat_more() {
        huli.simulate(1.5f);
        huli.simulate(1.5f); // banana eaten here
        huli.simulate(1.5f); // banana eaten here
        huli.simulate(1.5f); // banana eaten here

        assertThat(huli.getAddedRelativeBaseDamage()).isEqualTo(0.06f);
    }

    @Test
    void eat_notMoreThanMax() {
        huli.simulate(1.5f);
        for (int i = 0; i < 60; ++i) {
            huli.simulate(1.5f); // banana could be eaten here
        }

        assertThat(huli.getAddedRelativeBaseDamage()).isEqualTo(0.9999996f);
    }

    @Test
    void eat_notMoreThanMax_increasedByLevel() {
        huli.setLevel(1);
        huli.simulate(1.5f);
        for (int i = 0; i < 60; ++i) {
            huli.simulate(1.5f); // banana could be eaten here
        }

        assertThat(huli.getAddedRelativeBaseDamage()).isEqualTo(1.0199996f);
    }

    @Test
    void eat_notWhenAttacking() {
        Creep creep = new Creep();
        unitGateway.addUnit(creep);

        huli.simulate(1.5f);
        huli.simulate(1.5f); // no banana eaten, because huli is busy
        huli.simulate(1.5f); // no banana eaten, because huli is busy

        assertThat(huli.getAddedRelativeBaseDamage()).isEqualTo(0);
    }

    @Test
    void eat_bonusText() {
        huli.simulate(1.5f);
        huli.simulate(1.5f); // banana eaten here
        huli.simulate(1.5f); // banana eaten here
        huli.simulate(1.5f); // banana eaten here

        CustomTowerBonus bonus = new CustomTowerBonus();
        huli.populateCustomTowerBonus(bonus);
        assertThat(bonus.title).isEqualTo("Bananas:");
        assertThat(bonus.value).isEqualTo("3");
    }

    @Test
    void mojo_noFemaleTowersAround() {
        assertThat(huli.getCritChance()).isEqualTo(0.05f);
    }

    @Test
    void mojo_noFemaleTowersAround_huliGenderChanged() {
        huli.setGender(Gender.Female);
        assertThat(huli.getCritChance()).isEqualTo(0.05f);
    }

    @Test
    void mojo_oneFemaleTowerAround() {
        unitGateway.addUnit(new HerbWitch());
        assertThat(huli.getCritChance()).isEqualTo(0.09f);
    }

    @Test
    void mojo_oneMaleTowerAround() {
        unitGateway.addUnit(new Viking());
        assertThat(huli.getCritChance()).isEqualTo(0.05f);
    }

    @Test
    void mojo_oneFemaleTowerAround_huliGenderChanged() {
        unitGateway.addUnit(new HerbWitch());
        huli.setGender(Gender.Female);
        assertThat(huli.getCritChance()).isEqualTo(0.050000004f);
    }

    @Test
    void mojo_oneFemaleTowerRemoved() {
        HerbWitch herbWitch = new HerbWitch();
        unitGateway.addUnit(herbWitch);
        unitGateway.removeUnit(herbWitch);

        assertThat(huli.getCritChance()).isEqualTo(0.050000004f);
    }

    @Test
    void mojo_twoFemaleTowersAround() {
        unitGateway.addUnit(new HerbWitch());
        unitGateway.addUnit(new HerbWitch());

        assertThat(huli.getCritChance()).isEqualTo(0.13f);
    }

    @Test
    void mojo_twoFemaleTowersAround_levelUp() {
        unitGateway.addUnit(new HerbWitch());
        unitGateway.addUnit(new HerbWitch());

        huli.setLevel(10);

        assertThat(huli.getCritChance()).isEqualTo(0.13599999f);
    }

    @Test
    void mojo_twoFemaleTowersAround_genderChanged() {
        unitGateway.addUnit(new HerbWitch());
        Tower herby = new HerbWitch();
        unitGateway.addUnit(herby);
        herby.setGender(Gender.Male);

        assertThat(huli.getCritChance()).isEqualTo(0.089999996f);
    }

    @Test
    void mojo_outOfRange() {
        HerbWitch herbWitch = new HerbWitch();
        herbWitch.setX(4);
        unitGateway.addUnit(herbWitch);

        assertThat(huli.getCritChance()).isEqualTo(0.05f);
    }

    @Test
    void mojo_outOfRange_rangeIncreased() {
        HerbWitch herbWitch = new HerbWitch();
        herbWitch.setX(4);
        unitGateway.addUnit(herbWitch);

        huli.addRange(1);

        assertThat(huli.getCritChance()).isEqualTo(0.09f);
    }
}