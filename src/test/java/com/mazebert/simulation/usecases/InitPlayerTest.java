package com.mazebert.simulation.usecases;

import com.mazebert.simulation.Element;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.commands.InitPlayerCommand;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.PlayerGatewayTrainer;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.systems.ExperienceSystem;
import com.mazebert.simulation.systems.GameSystem;
import com.mazebert.simulation.systems.LootSystemTrainer;
import com.mazebert.simulation.units.heroes.HeroType;
import com.mazebert.simulation.units.heroes.LittleFinger;
import com.mazebert.simulation.units.quests.KillChallengesQuest;
import com.mazebert.simulation.units.quests.QuestData;
import com.mazebert.simulation.units.quests.QuestType;
import com.mazebert.simulation.units.towers.TowerType;
import com.mazebert.simulation.units.wizards.DeckMasterPower;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class InitPlayerTest extends UsecaseTest<InitPlayerCommand> {
    RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();

    Wizard wizard;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        playerGateway = new PlayerGatewayTrainer();
        gameGateway = new GameGateway();
        randomPlugin = randomPluginTrainer;

        wizard = new Wizard();
        wizard.playerId = 1;
        unitGateway.addUnit(wizard);

        experienceSystem = new ExperienceSystem();
        lootSystem = new LootSystemTrainer();
        gameSystem = new GameSystem();

        usecase = new InitPlayer();

        request.playerId = wizard.getPlayerId();
        request.heroType = HeroType.Lycaon;
    }

    @Test
    void unknownPlayer() {
        whenRequestIsExecuted();

        assertThat(wizard.ladderPlayerId).isEqualTo(0);
        assertThat(wizard.name).isEqualTo("Unknown Wizard");
        assertThat(wizard.experience).isEqualTo(0);
    }

    @Test
    void knownPlayer() {
        request.ladderPlayerId = 115;
        request.playerName = "Andy";
        request.experience = 3000;

        whenRequestIsExecuted();

        assertThat(wizard.ladderPlayerId).isEqualTo(115);
        assertThat(wizard.name).isEqualTo("Andy");
        assertThat(wizard.experience).isEqualTo(3000);
        assertThat(wizard.level).isEqualTo(9);
    }

    @Test
    void knownPlayer_nullName() {
        request.ladderPlayerId = 115;
        request.playerName = null; // Something broken here

        whenRequestIsExecuted();

        assertThat(wizard.name).isEqualTo("Unknown Wizard");
    }

    @Test
    void littlefinger() {
        request.heroType = HeroType.LittleFinger;

        whenRequestIsExecuted();

        LittleFinger hero = unitGateway.findUnit(LittleFinger.class, wizard.playerId);
        assertThat(hero).isNotNull();
    }

    @Test
    void foilCards() {
        request.foilHeroes = EnumSet.of(HeroType.InnKeeper, HeroType.LoanShark);

        whenRequestIsExecuted();

        assertThat(wizard.foilHeroes).isSameAs(request.foilHeroes);
    }

    @Test
    void deckMasterPower() {
        request.experience = 1000000000L; // Enough xp to use the deck master
        DeckMasterPower deckMasterPower = new DeckMasterPower();
        deckMasterPower.setSkillLevel(deckMasterPower.getMaxSkillLevel());
        deckMasterPower.setSelectedTower(TowerType.Huli);
        request.powers.add(deckMasterPower);

        whenRequestIsExecuted();

        assertThat(wizard.towerStash.get(TowerType.Huli).amount).isEqualTo(1);
    }

    @Test
    void startingTowers() {
        randomPluginTrainer.givenFloatAbs(0.0f);
        request.elements = EnumSet.of(Element.Nature);

        whenRequestIsExecuted();

        Wizard wizard = unitGateway.getWizard(request.playerId);
        assertThat(wizard.towerStash.size()).isGreaterThan(0);
        assertThat(wizard.towerStash.get(0).getCardType()).isEqualTo(TowerType.Beaver);
        assertThat(wizard.towerStash.get(0).getAmount()).isEqualTo(3);
        assertThat(wizard.towerStash.get(1).getCardType()).isEqualTo(TowerType.Frog);
        assertThat(wizard.towerStash.get(1).getAmount()).isEqualTo(1);
    }

    @Test
    void startingTowers_tutorial() {
        gameSystem.initTutorial();

        whenRequestIsExecuted();

        Wizard wizard = unitGateway.getWizard(request.playerId);
        assertThat(wizard.towerStash.size()).isEqualTo(4);
        assertThat(wizard.towerStash.get(0).getCardType()).isEqualTo(TowerType.Beaver);
        assertThat(wizard.towerStash.get(1).getCardType()).isEqualTo(TowerType.Rabbit);
        assertThat(wizard.towerStash.get(2).getCardType()).isEqualTo(TowerType.Hitman);
        assertThat(wizard.towerStash.get(3).getCardType()).isEqualTo(TowerType.Dandelion);
    }

    @Test
    void elements_darknessOnly() {
        randomPluginTrainer.givenFloatAbs(0.0f);
        request.elements = EnumSet.of(Element.Darkness);

        whenRequestIsExecuted();

        Wizard wizard = unitGateway.getWizard(request.playerId);
        assertThat(wizard.towerStash.size()).isGreaterThan(0);
        for (int i = 0; i < wizard.towerStash.size(); ++i) {
            assertThat(wizard.towerStash.get(i).card.getElement()).isEqualTo(Element.Darkness);
        }
    }

    @Test
    void quests() {
        QuestData questData = new QuestData();
        questData.type = QuestType.KillChallengesQuest;
        questData.currentAmount = 6;
        request.quests.add(questData);

        whenRequestIsExecuted();

        Wizard wizard = unitGateway.getWizard(request.playerId);
        KillChallengesQuest quest = wizard.getAbility(KillChallengesQuest.class);
        assertThat(quest).isNotNull();
        assertThat(quest.getCurrentAmount()).isEqualTo(6);
    }
}