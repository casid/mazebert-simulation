package com.mazebert.simulation.commands.serializers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.commands.InitPlayerCommand;
import com.mazebert.simulation.units.quests.QuestData;
import com.mazebert.simulation.units.quests.QuestType;
import com.mazebert.simulation.units.towers.TowerType;
import com.mazebert.simulation.units.wizards.DeckMasterPower;
import com.mazebert.simulation.units.wizards.WizardPower;
import com.mazebert.simulation.units.wizards.WizardPowerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.jusecase.bitpack.AbstractBitProtocol;
import org.jusecase.bitpack.BitProtocol;
import org.jusecase.bitpack.buffer.BufferBitReader;
import org.jusecase.bitpack.buffer.BufferBitWriter;

import java.nio.ByteBuffer;

import static org.assertj.core.api.Assertions.assertThat;

public class InitPlayerCommandSerializerTest {
    BitProtocol protocol = new AbstractBitProtocol();
    BufferBitWriter writer = new BufferBitWriter(protocol, ByteBuffer.allocateDirect(256));
    BufferBitReader reader;

    EnumSerializer enumSerializer = new EnumSerializer(Sim.version);

    @BeforeEach
    void setUp() {
        protocol.register(new InitPlayerCommandSerializer(enumSerializer));
        protocol.register(new WizardPowerSerializer(enumSerializer));
        protocol.register(new QuestDataSerializer(enumSerializer));
    }

    @Test
    void allPowersCanBeWrittenAndRead() {
        InitPlayerCommand expected = new InitPlayerCommand();
        for (WizardPowerType value : WizardPowerType.values()) {
            WizardPower wizardPower = value.create();
            wizardPower.setSkillLevel(wizardPower.getMaxSkillLevel());
            expected.powers.add(wizardPower);
        }
        writer.writeObjectNonNull(expected);

        whenBufferIsFlushedAndRead();

        InitPlayerCommand actual = reader.readObjectNonNull(InitPlayerCommand.class);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void deckMasterPower() {
        InitPlayerCommand expected = new InitPlayerCommand();
        DeckMasterPower deckMasterPower = new DeckMasterPower();
        deckMasterPower.setSelectedTower(TowerType.Huli);
        expected.powers.add(deckMasterPower);
        writer.writeObjectNonNull(expected);

        whenBufferIsFlushedAndRead();

        InitPlayerCommand actual = reader.readObjectNonNull(InitPlayerCommand.class);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void quests() {
        InitPlayerCommand expected = new InitPlayerCommand();
        QuestData questData = new QuestData();
        questData.type = QuestType.KillChallengesQuest;
        questData.currentAmount = 3;
        expected.quests.add(questData);
        writer.writeObjectNonNull(expected);

        whenBufferIsFlushedAndRead();

        InitPlayerCommand actual = reader.readObjectNonNull(InitPlayerCommand.class);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void quests_collectGold() {
        InitPlayerCommand expected = new InitPlayerCommand();
        QuestData questData = new QuestData();
        questData.type = QuestType.CollectGoldQuest;
        questData.currentAmount = 987000;
        expected.quests.add(questData);
        writer.writeObjectNonNull(expected);

        whenBufferIsFlushedAndRead();

        InitPlayerCommand actual = reader.readObjectNonNull(InitPlayerCommand.class);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    private void whenBufferIsFlushedAndRead() {
        writer.flush();
        writer.getBuffer().rewind();
        reader = new BufferBitReader(protocol, writer.getBuffer());
    }
}