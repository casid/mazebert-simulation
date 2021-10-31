package com.mazebert.simulation.systems;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.Wave;
import com.mazebert.simulation.WaveType;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.quests.DefeatNaglfarFailureQuest;
import com.mazebert.simulation.units.towers.*;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class RagNarRogSystem {

    public static void onProphecyFailed() {
        Sim.context().unitGateway.forEachWizard(w -> {
            if (w.health > 0) {
                w.addHealth(-2 * w.health);
            }

            DefeatNaglfarFailureQuest quest = w.getAbility(DefeatNaglfarFailureQuest.class);
            if (quest != null) {
                quest.addAmount(1);
            }
        });
    }

    public static boolean fulfilProphecy(Wave wave) {
        if (wave.type != WaveType.Challenge) {
            return false;
        }

        ProphecySystem prophecySystem = Sim.context().prophecySystem;
        Wizard wizard = prophecySystem.getProphecyWizard(ItemType.RagNarRogProphecy);
        if (wizard == null) {
            return false;
        }

        UnitGateway unitGateway = Sim.context().unitGateway;

        int playerId = wizard.getPlayerId();

        Yggdrasil yggdrasil = unitGateway.findUnit(Yggdrasil.class, playerId);
        if (yggdrasil == null) {
            return false;
        }

        if (isGodConnectedWithYggdrasil(unitGateway, Thor.class, playerId, ItemType.BranchOfYggdrasilLight) &&
            isGodConnectedWithYggdrasil(unitGateway, Loki.class, playerId, ItemType.BranchOfYggdrasilMetropolis) &&
            isGodConnectedWithYggdrasil(unitGateway, Idun.class, playerId, ItemType.BranchOfYggdrasilNature) &&
            isGodConnectedWithYggdrasil(unitGateway, Hel.class, playerId, ItemType.BranchOfYggdrasilDarkness)) {
            return prophecySystem.fulfillProphecy(ItemType.RagNarRogProphecy);
        }

        return false;
    }

    private static boolean isGodConnectedWithYggdrasil(UnitGateway unitGateway, Class<? extends Tower> godClass, int playerId, ItemType branch) {
        Tower god = unitGateway.findUnit(godClass, playerId);
        if (god == null) {
            return false;
        }

        if (!god.hasItem(branch)) {
            return false;
        }

        return true;
    }
}
