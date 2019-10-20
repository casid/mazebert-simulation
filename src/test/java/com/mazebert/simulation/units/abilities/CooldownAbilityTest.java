package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.towers.Tower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class CooldownAbilityTest {
    boolean active;
    int actionCount;

    Tower unit;
    MyCooldownAbility cooldownAbility;

    @BeforeEach
    void setUp() {
        unit = new TestTower();
        unit.setBaseCooldown(1.0f);

        cooldownAbility = new MyCooldownAbility();
        unit.addAbility(cooldownAbility);
    }

    @Test
    void inactive() {
        active = false;
        unit.simulate(1.0f);
        assertThat(actionCount).isEqualTo(0);
    }

    @Test
    void inactive_becomesActive() {
        active = false;
        unit.simulate(0.5f);
        unit.simulate(0.5f);
        active = true;
        unit.simulate(0.5f);
        assertThat(actionCount).isEqualTo(1);
    }

    @Test
    void active() {
        active = true;
        unit.simulate(1.0f);
        assertThat(actionCount).isEqualTo(1);
    }

    @Test
    void active_twoTimesInOneSimStep() {
        active = true;
        unit.simulate(2.0f);
        assertThat(actionCount).isEqualTo(2);
    }

    @Test
    void active_fourTimesInOneSimStep() {
        active = true;
        unit.simulate(4.9f);
        assertThat(actionCount).isEqualTo(4);
    }

    @Test
    void active_smallSteps() {
        active = true;

        unit.simulate(0.33f);
        assertThat(actionCount).isEqualTo(0);
        unit.simulate(0.33f);
        assertThat(actionCount).isEqualTo(0);
        unit.simulate(0.32f);
        assertThat(actionCount).isEqualTo(0);

        unit.simulate(0.02f);
        assertThat(actionCount).isEqualTo(1);
    }

    @Test
    void active_cooldownChanges() {
        active = true;

        unit.simulate(0.5f);
        unit.setBaseCooldown(2.0f);
        unit.simulate(0.5f);
        unit.simulate(0.5f);
        unit.simulate(0.5f);

        assertThat(actionCount).isEqualTo(1);
    }

    @Test
    void active_dispose() {
        active = true;
        cooldownAbility.dispose();

        unit.simulate(1.0f);

        assertThat(actionCount).isEqualTo(0);
        assertThat(cooldownAbility.getUnit()).isNull();
    }

    @Test
    void dispose_alreadyDisposed() {
        cooldownAbility.dispose();
        Throwable throwable = catchThrowable(() -> cooldownAbility.dispose());
        assertThat(throwable).isInstanceOf(IllegalStateException.class).hasMessage("This ability is already disposed");
    }

    @Test
    void init_alreadyInitialized() {
        Throwable throwable = catchThrowable(() -> cooldownAbility.init(new TestTower()));
        assertThat(throwable).isInstanceOf(IllegalStateException.class).hasMessage("This ability is already owned by " + unit);
    }

    private class MyCooldownAbility extends CooldownUnitAbility<Tower> {

        @Override
        protected boolean onCooldownReached() {
            if (active) {
                actionCount++;
            }
            return active;
        }
    }
}