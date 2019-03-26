package com.mazebert.simulation;

import com.mazebert.simulation.replay.StreamReplayReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;
import static org.jusecase.Builders.set;

public class BackwardCompatiblityTester {

    private static final Set<String> acknowledgedDsyncs = a(set(
            "6d22fa0a-723d-408f-a687-c2dc687ac8b2-32597.mbg",
            "ebdbea64-ab7f-4302-ab68-60ace587bd01-34707.mbg"
    ));

    private static final Path gamesDirectory = Paths.get("src/test/resources/games");

    private ConcurrentMap<Path, Exception> errors = new ConcurrentHashMap<>();

    @AfterEach
    void tearDown() {
        for (Map.Entry<Path, Exception> entry : errors.entrySet()) {
            Exception exception = entry.getValue();
            if (exception instanceof IOException) {
                System.err.println("Failed to load game " + entry.getKey() + " " + exception.getMessage());
            } else {
                System.err.println("Failed to verify game " + entry.getKey() + " " + exception.getMessage());
            }
        }
        assertThat(errors.size()).isEqualTo(0);
    }

    @Test
    void checkAll() throws IOException {
        List<Path> files = Files.walk(gamesDirectory, 1).collect(Collectors.toList());
        System.out.println("Validating " + files.size() + " games");

        files.parallelStream().forEach(file -> {
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
        } catch (Exception e) {
            errors.put(file, e);
        }
    }
}
