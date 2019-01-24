package com.mazebert.simulation.commands.serializers;

import com.mazebert.simulation.commands.InitPlayerCommand;
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

    @BeforeEach
    void setUp() {
        protocol.register(new InitPlayerCommandSerializer());
        protocol.register(new WizardPowerSerializer());
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
        assertThat(actual.powers.size()).isEqualTo(expected.powers.size());
        for (int i = 0; i < actual.powers.size(); ++i) {
            assertThat(actual.powers.get(i)).isEqualToComparingFieldByFieldRecursively(expected.powers.get(i));
        }
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
        assertThat(actual.powers.get(0)).isEqualToComparingFieldByFieldRecursively(deckMasterPower);
    }

    private void whenBufferIsFlushedAndRead() {
        writer.flush();
        writer.getBuffer().rewind();
        reader = new BufferBitReader(protocol, writer.getBuffer());
    }
}