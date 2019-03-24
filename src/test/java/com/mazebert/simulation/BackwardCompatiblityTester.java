package com.mazebert.simulation;

import com.mazebert.simulation.replay.StreamReplayReader;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Set;

import static org.jusecase.Builders.a;
import static org.jusecase.Builders.set;

public class BackwardCompatiblityTester {

    private static final Set<String> acknowledgedDsyncs = a(set(
            "6d22fa0a-723d-408f-a687-c2dc687ac8b2-32597.mbg",
            "ebdbea64-ab7f-4302-ab68-60ace587bd01-34707.mbg"
    ));

    private static final Path gamesDirectory = Paths.get("src/test/resources/games");

    private SoftAssertions s;

    @BeforeEach
    void setUp() {
        s = new SoftAssertions();
    }

    @AfterEach
    void tearDown() {
        s.assertAll();
    }

    @Test
    void checkAll() throws IOException {
        Files.walk(gamesDirectory, 1).forEach(file -> {
            if (Files.isDirectory(file)) {
                return;
            }

            if (acknowledgedDsyncs.contains(file.getFileName().toString())) {
                return;
            }

            checkGame(file);
        });
    }

    @Test
    void checkOne() {
        checkGame(gamesDirectory.resolve("ebdbea64-ab7f-4302-ab68-60ace587bd01-34707.mbg"));
    }

    private void checkGame(Path file) {
        try (StreamReplayReader replayReader = new StreamReplayReader(new BufferedInputStream(Files.newInputStream(file, StandardOpenOption.READ)))) {
            new SimulationValidator().validate(replayReader, null, null);
        } catch (IOException e) {
            s.assertThat(true).describedAs("Failed to load game " + file + " " + e.getMessage()).isFalse();
        } catch (Exception e) {
            s.assertThat(true).describedAs("Failed to verify game " + file + " " + e.getMessage()).isFalse();
        }
    }
}
