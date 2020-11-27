package com.mazebert.simulation.usecases;

import com.mazebert.simulation.Card;
import com.mazebert.simulation.CardType;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.commands.TransferCardCommand;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.plugins.FormatPlugin;
import com.mazebert.simulation.stash.Stash;
import com.mazebert.simulation.units.items.BranchOfYggdrasil;
import com.mazebert.simulation.units.items.WeddingRing;
import com.mazebert.simulation.units.quests.TransferCardQuest;
import com.mazebert.simulation.units.quests.TransferUniqueCardQuest;
import com.mazebert.simulation.units.wizards.Wizard;

@SuppressWarnings({"rawtypes", "unchecked"})
public strictfp class TransferCard extends Usecase<TransferCardCommand> {

    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;
    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final FormatPlugin formatPlugin = Sim.context().formatPlugin;

    public static boolean canTransfer(CardType<?> cardType) {
        Card card = cardType.instance();
        if (card instanceof WeddingRing || card instanceof BranchOfYggdrasil) {
            return false;
        }

        if (card.isInternal()) {
            return false;
        }

        return true;
    }

    @Override
    public void execute(TransferCardCommand command) {
        if (command.playerId == command.toPlayerId) {
            return;
        }

        Wizard fromWizard = unitGateway.getWizard(command.playerId);
        if (fromWizard == null) {
            return;
        }

        Wizard toWizard = unitGateway.getWizard(command.toPlayerId);
        if (toWizard == null) {
            return;
        }

        Stash fromStash = fromWizard.getStash(command.cardCategory);
        if (fromStash == null) {
            return;
        }

        Stash toStash = toWizard.getStash(command.cardCategory);
        if (toStash == null) {
            return;
        }

        if (!canTransfer(command.cardType)) {
            return;
        }

        if (toStash.isUniqueAlreadyDropped(command.cardType)) {
            return;
        }

        Card card = fromStash.transferTo(toStash, command.cardType);
        if (card == null) {
            return;
        }

        if (card.isUniqueDrop()) {
            fromWizard.addQuestProgress(TransferUniqueCardQuest.class);
        } else {
            fromWizard.addQuestProgress(TransferCardQuest.class);
        }

        if (simulationListeners.areNotificationsEnabled()) {
            String notification = formatPlugin.playerName(fromWizard) + " transferred " + formatPlugin.card(command.cardType) + " to " + formatPlugin.playerName(toWizard) + ".";
            unitGateway.forEachWizard(w -> simulationListeners.showNotification(w, notification));
        }
    }
}
