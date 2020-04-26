package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.systems.PubSystem;
import com.mazebert.simulation.units.abilities.ActiveAbility;

public strictfp class PubParty extends ActiveAbility {
    private final PubSystem pubSystem = Sim.context().pubSystem;

    @Override
    public float getReadyProgress() {
        return pubSystem.getReadyProgress();
    }

    @Override
    public void activate() {
        pubSystem.activate();
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "St Paddy's Day";
    }

    @Override
    public String getDescription() {
        return "Every " + format.seconds(PubSystem.COOLDOWN_TIME) +
                " your Irish Pubs can call out St Paddy's Day. All towers on the map dress in green and gain " +
                format.percentWithSignAndUnit(PubSystem.DAMAGE_BONUS_PER_PUB) + " damage per Irish Pub for " +
                format.seconds(PubSystem.PARTY_TIME) + ".";
    }

    @Override
    public String getIconFile() {
        return "cup_512";
    }
}
