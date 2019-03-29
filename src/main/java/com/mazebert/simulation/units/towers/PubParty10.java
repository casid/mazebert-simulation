package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.systems.PubSystem;
import com.mazebert.simulation.units.abilities.CooldownActiveAbility;

public strictfp class PubParty10 extends CooldownActiveAbility {
    private final PubSystem pubSystem = Sim.context().pubSystem;

    @Override
    public float getCooldown() {
        return PubSystem.COOLDOWN_TIME;
    }

    @Override
    public void activate() {
        pubSystem.activate();
        startCooldown();
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
                " your Irish Pubs can call out St Paddy's Day. All towers on the map dress green and gain " +
                format.percentWithSignAndUnit(PubSystem.DAMAGE_BONUS_PER_PUB) + " damage per Irish Pub for " +
                format.seconds(PubSystem.PARTY_TIME) + ".";
    }

    @Override
    public String getIconFile() {
        return "cup_512";
    }
}