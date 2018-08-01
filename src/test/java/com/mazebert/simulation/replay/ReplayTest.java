package com.mazebert.simulation.replay;

import com.mazebert.simulation.replay.data.ReplayHeader;
import com.mazebert.simulation.replay.data.ReplayTurn;
import io.github.glytching.junit.extension.folder.TemporaryFolder;
import io.github.glytching.junit.extension.folder.TemporaryFolderExtension;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(TemporaryFolderExtension.class)
public class ReplayTest {

    ReplayWriter replayWriter;
    ReplayReader replayReader;

    @BeforeEach
    void setUp(TemporaryFolder temporaryFolder) throws IOException {
        File replay = temporaryFolder.createFile("replay");
        replayWriter = new ReplayWriter(replay.toPath());
        replayReader = new ReplayReader(replay.toPath());
    }

    @Test
    void roundtrip() {
        ReplayHeader header = new ReplayHeader();
        replayWriter.writeHeader(header);

        ReplayTurn turn = new ReplayTurn();
        turn.playerTurns = new ArrayList<>();
        replayWriter.writeTurn(turn);

        replayWriter.close();

        ReplayHeader readHeader = replayReader.readHeader();
        assertThat(readHeader).isEqualToComparingFieldByFieldRecursively(header);

        ReplayTurn readTurn = replayReader.readTurn();
        assertThat(readTurn).isEqualToComparingFieldByFieldRecursively(turn);

        replayReader.close();
    }
}