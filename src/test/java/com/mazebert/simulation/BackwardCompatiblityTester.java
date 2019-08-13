package com.mazebert.simulation;

import com.mazebert.simulation.replay.StreamReplayReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;
import static org.jusecase.Builders.set;

@Execution(ExecutionMode.SAME_THREAD)
public class BackwardCompatiblityTester {

    private static final Set<String> acknowledgedDsyncs = a(set(
            "6d22fa0a-723d-408f-a687-c2dc687ac8b2-32597.mbg",
            "ebdbea64-ab7f-4302-ab68-60ace587bd01-34707.mbg",
            "79a1e02f-3c7a-4d58-8f21-1f2ca049a1d5-6737.mbg",
            "8407598d-5540-4935-9485-41d28b594595-34164.mbg",
            "0dd320b2-cbed-4533-b265-8eddea8dc005-35049.mbg"
    ));

    private static final Path gamesDirectory = Paths.get("games");

    private ConcurrentMap<Path, Exception> errors = new ConcurrentHashMap<>();

    @AfterEach
    void tearDown() {
        for (Map.Entry<Path, Exception> entry : errors.entrySet()) {
            Exception exception = entry.getValue();
            if (exception instanceof IOException) {
                System.err.println("Failed to load game " + entry.getKey() + " " + exception.getMessage());
            } else {
                System.err.println("Failed to verify game " + entry.getKey() + " " + exception.getMessage());
                exception.printStackTrace(System.err);
            }
        }
        assertThat(errors.size()).isEqualTo(0);
    }

    @Test
    void check_10() throws IOException {
        checkGames(Sim.v10);
    }

    @Test
    void check_11() throws IOException {
        checkGames(Sim.v11);
    }

    @Test
    void check_12() throws IOException {
        checkGames(Sim.v12);
    }

    @Test
    void check_13() throws IOException {
        checkGames(Sim.v13);
    }

    @Test
    void check_14() throws IOException {
        checkGames(Sim.v14);
    }

    @Test
    void check_16() throws IOException {
        checkGames(Sim.v16);
    }

    @Test
    void check_17() throws IOException {
        checkGames(Sim.v17);
    }

    @Disabled
    @Test
    void checkOne() {
        int version = Sim.v10;
        checkGame(gamesDirectory.resolve(version + "/0dd320b2-cbed-4533-b265-8eddea8dc005-35049.mbg"), version);
    }

    private void checkGames(int version) throws IOException {
        List<Path> files = Files.walk(gamesDirectory.resolve("" + version), 1).collect(Collectors.toList());
        int total = files.size();
        System.out.println("Validating " + total + " games (version " + version + ")");
        AtomicInteger counter = new AtomicInteger();

        files.parallelStream().forEach(file -> {
            try {
                if (Files.isDirectory(file)) {
                    return;
                }

                String fileName = file.getFileName().toString();
                if (fileName.startsWith(".")) {
                    return;
                }

                if (acknowledgedDsyncs.contains(fileName)) {
                    return;
                }

                checkGame(file, version);
            } finally {
                System.out.println(counter.incrementAndGet() + "/" + total + "");
            }
        });
    }

    private void checkGame(Path file, int version) {
        try (StreamReplayReader replayReader = new StreamReplayReader(new BufferedInputStream(Files.newInputStream(file, StandardOpenOption.READ)), version)) {
            new SimulationValidator().validate(version, replayReader, null, null);
        } catch (Exception e) {
            System.err.println("Arghs! " + e.getMessage());
            errors.put(file, e);
        }
    }
}
