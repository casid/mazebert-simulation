package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.WaveSpawner;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class JackInTheBoxAbility extends HeroTowerBuffAbility {
    public static final float CHANCE = 0.33f;

    private final WaveSpawner waveSpawner = Sim.context().waveSpawner;

    @Override
    protected void buffTower(Tower tower) {
        if (tower.getWizard().ownsFoilCard(tower.getType()) && tower.isAbilityTriggered(CHANCE)) {
            waveSpawner.spawnTreasureGoblins(tower.getWizard(), 1);
        }
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Goblin out of hat";
    }

    @Override
    public String getDescription() {
        return 	"Whenever a golden tower is built, there is a " + format.percent(CHANCE) + "% chance jack summons a treasure goblin.";
    }
}
