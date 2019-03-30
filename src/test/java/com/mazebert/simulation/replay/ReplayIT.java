package com.mazebert.simulation.replay;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.commands.NextWaveCommand;
import com.mazebert.simulation.replay.data.ReplayFrame;
import com.mazebert.simulation.replay.data.ReplayHeader;
import com.mazebert.simulation.replay.data.ReplayTurn;
import io.github.glytching.junit.extension.folder.TemporaryFolder;
import io.github.glytching.junit.extension.folder.TemporaryFolderExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

import static java.nio.file.StandardOpenOption.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(TemporaryFolderExtension.class)
public class ReplayIT {

    File replay;

    @BeforeEach
    void setUp(TemporaryFolder temporaryFolder) throws IOException {
        replay = temporaryFolder.createFile("replay");
    }

    @Test
    void roundtrip() throws IOException {
        ReplayHeader header = new ReplayHeader();
        header.version = Sim.version;
        header.playerCount = 2;

        ReplayFrame turn = new ReplayFrame();
        turn.turnNumber = 42;
        turn.hash = 1241932452;
        turn.playerTurns = new ReplayTurn[1];
        turn.playerTurns[0] = new ReplayTurn();
        turn.playerTurns[0].commands = new ArrayList<>();
        turn.playerTurns[0].commands.add(new NextWaveCommand());

        try (ReplayWriter replayWriter = new ReplayWriter(Files.newOutputStream(replay.toPath(), CREATE, WRITE, TRUNCATE_EXISTING), header.version)) {
            replayWriter.writeHeader(header);
            replayWriter.writeFrame(turn);
        }

        try (StreamReplayReader replayReader = new StreamReplayReader(Files.newInputStream(replay.toPath(), StandardOpenOption.READ), header.version)) {
            ReplayHeader readHeader = replayReader.readHeader();
            assertThat(readHeader).isEqualToComparingFieldByFieldRecursively(header);

            ReplayFrame readFrame = replayReader.readFrame();
            assertThat(readFrame).isEqualToComparingFieldByFieldRecursively(turn);

            ReplayFrame frame2 = replayReader.readFrame();
            assertThat(frame2).isNull();
        }
    }
}