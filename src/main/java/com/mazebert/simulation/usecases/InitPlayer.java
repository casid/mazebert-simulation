package com.mazebert.simulation.usecases;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.commands.InitPlayerCommand;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.systems.ExperienceSystem;
import com.mazebert.simulation.systems.GameSystem;
import com.mazebert.simulation.units.heroes.Hero;
import com.mazebert.simulation.units.quests.Quest;
import com.mazebert.simulation.units.quests.QuestData;
import com.mazebert.simulation.units.wizards.Wizard;
import com.mazebert.simulation.units.wizards.WizardPower;

import java.util.List;

public strictfp class InitPlayer extends Usecase<InitPlayerCommand> {

    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;
    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final ExperienceSystem experienceSystem = Sim.context().experienceSystem;
    private final GameSystem gameSystem = Sim.context().gameSystem;

    @Override
    public void execute(InitPlayerCommand command) {
        Wizard wizard = unitGateway.getWizard(command.playerId);

        initWizard(wizard, command);
        addHero(wizard, command);
        addPowers(wizard, command.powers);
        addQuests(wizard, command.quests);

        wizard.foilHeroes = command.foilHeroes;
        wizard.foilTowers = command.foilTowers;
        wizard.foilPotions = command.foilPotions;
        wizard.foilItems = command.foilItems;

        wizard.towerStash.setElements(command.elements);

        gameSystem.rollStartingTowers(wizard);

//        for (TowerType towerType : TowerType.values()) {
//            if (towerType != TowerType.Kiwi) {
//                wizard.towerStash.add(towerType);
//            }
//        }
//        for (ItemType itemType : ItemType.values()) {
//            if (itemType != ItemType.BloodDemonBlade) {
//                wizard.itemStash.add(itemType);
//            }
//        }
//        for (PotionType value : PotionType.values()) {
//            wizard.potionStash.add(value);
//        }

        simulationListeners.onPlayerInitialized.dispatch(wizard.playerId);
    }

    public void initWizard(Wizard wizard, InitPlayerCommand command) {
        if (command.ladderPlayerId > 0) {
            wizard.ladderPlayerId = command.ladderPlayerId;
        }
        if (command.playerName != null && !command.playerName.isEmpty()) {
            wizard.name = command.playerName;
        }
        if (command.experience > 0) {
            wizard.initialExperience = command.experience;
            wizard.experience = command.experience;
            wizard.level = experienceSystem.calculateLevel(wizard);
        }
    }

    private void addHero(Wizard wizard, InitPlayerCommand command) {
        Hero hero = command.heroType.create();
        hero.setWizard(wizard);
        unitGateway.addUnit(hero);
    }

    private void addPowers(Wizard wizard, List<WizardPower> powers) {
        for (WizardPower power : powers) {
            wizard.addAbility(power);
        }
    }

    private void addQuests(Wizard wizard, List<QuestData> quests) {
        for (QuestData data : quests) {
            Quest quest = data.type.create();
            quest.setCurrentAmount(data.currentAmount);
            wizard.addAbility(quest);
        }
    }

}
