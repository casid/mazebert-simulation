package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.projectiles.ProjectileGateway;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.items.BabySword;
import com.mazebert.simulation.units.items.ItemTest;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.items.Lightbringer;
import com.mazebert.simulation.units.quests.OnlyLightQuest;
import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.units.creeps.CreepBuilder.creep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

class LuciferTest extends ItemTest {

    Lucifer lucifer;

    @Override
    protected Tower createTower() {
        projectileGateway = new ProjectileGateway();
        return lucifer = new Lucifer();
    }

    @Test
    void startsWithSword() {
        assertThat(lucifer.getItem(0)).isInstanceOf(Lightbringer.class);
    }

    @Test
    void swordRemoved_luciferIsReplacedWithDarkLucifer() {
        whenItemIsEquipped(null);

        assertThat(unitGateway.hasUnit(lucifer)).isFalse();
        assertThat(getLuciferFallen()).isNotNull();
    }

    @Test
    void swordRemoved_remainingItemsAreKept() {
        whenItemIsEquipped(ItemType.BabySword, 1);

        whenItemIsEquipped(null);

        assertThat(getLuciferFallen().getItem(1)).isInstanceOf(BabySword.class);
    }

    @Test
    void swordRemoved_lightbringerIsReplacedByDarkSwords() {
        whenItemIsEquipped(null);

        assertThat(wizard.itemStash.get(ItemType.Lightbringer)).isNull();
        // TODO
    }

    @Test
    void luciferIsSold() {
        whenTowerIsSold();

        assertThat(unitGateway.hasUnit(lucifer)).isFalse();
        assertThat(getLuciferFallen()).isNull();
        assertThat(wizard.itemStash.get(ItemType.Lightbringer)).isNull();
    }

    @Test
    void luciferIsReplaced() {
        whenTowerIsReplaced(lucifer, TowerType.Dandelion);

        assertThat(unitGateway.hasUnit(lucifer)).isFalse();
        assertThat(getLuciferFallen()).isNull();
        assertThat(wizard.itemStash.get(ItemType.Lightbringer)).isNull();
    }

    @Test
    void inventorySizeIsIncreased_nothingHappens() {
        whenItemIsEquipped(ItemType.LightbladeAcademyDrone, 1);
        whenItemIsEquipped(ItemType.LightbladeAcademySword, 2);

        assertThat(unitGateway.hasUnit(lucifer)).isTrue();
    }

    @Test
    void lightbringerHeals() {
        Creep creep = a(creep());
        unitGateway.addUnit(creep);

        whenTowerAttacks();

        double healthAfterAttack = creep.getHealth();
        creep.simulate(1.0f);
        double healthAfterHeal = creep.getHealth();
        assertThat(healthAfterHeal).isGreaterThan(healthAfterAttack);
    }

    @Test
    void lightbringerHeals_notMoreThanFullHealth() {
        Creep creep = a(creep());
        unitGateway.addUnit(creep);

        whenTowerAttacks();

        creep.setHealth(99.0f); // Creep healed by some other ability
        creep.simulate(1.0f);
        assertThat(creep.getHealth()).isEqualTo(100); // Not more than full health
    }

    @Test
    void damagedCreepsRestInPiece() {
        Creep creep = a(creep());
        unitGateway.addUnit(creep);

        whenTowerAttacks();

        assertThat(creep.isRestsInPiece()).isTrue();
    }

    @Test
    void onlyLightQuestFails() {
        wizard.addAbility(new OnlyLightQuest());

        whenItemIsEquipped(null);

        assertThat(wizard.hasAbility(OnlyLightQuest.class)).isFalse();
    }

    @Override
    protected void whenTowerAttacks() {
        super.whenTowerAttacks();
        projectileGateway.simulate(0.5f);
        projectileGateway.simulate(0.5f);
    }

    private LuciferFallen getLuciferFallen() {
        return unitGateway.findUnit(LuciferFallen.class, 1);
    }
}