package com.mazebert.simulation.replay;

import com.mazebert.simulation.messages.Turn;
import com.mazebert.simulation.replay.data.ReplayHeader;
import com.mazebert.simulation.replay.data.ReplayTurn;
import io.github.glytching.junit.extension.folder.TemporaryFolder;
import io.github.glytching.junit.extension.folder.TemporaryFolderExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(TemporaryFolderExtension.class)
public class ReplayTest {

    File replay;

    @BeforeEach
    void setUp(TemporaryFolder temporaryFolder) throws IOException {
        replay = temporaryFolder.createFile("replay");
    }

    @Test
    void roundtrip() throws IOException {
        ReplayHeader header = new ReplayHeader();
        header.playerCount = 2;

        ReplayTurn turn = new ReplayTurn();
        turn.playerTurns = new ArrayList<>();
        Turn playerTurn = new Turn();
        playerTurn.playerId = 1;
        playerTurn.hash = 1241932452;
        turn.playerTurns.add(playerTurn);

        try (ReplayWriter replayWriter = new ReplayWriter(replay.toPath())) {
            replayWriter.writeHeader(header);
            replayWriter.writeTurn(turn);
        }

        try (ReplayReader replayReader = new ReplayReader(replay.toPath())) {
            ReplayHeader readHeader = replayReader.readHeader();
            assertThat(readHeader).isEqualToComparingFieldByFieldRecursively(header);

            ReplayTurn readTurn = replayReader.readTurn();
            assertThat(readTurn).isEqualToComparingFieldByFieldRecursively(turn);
        }
    }
}