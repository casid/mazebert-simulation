package com.mazebert.simulation;

import com.mazebert.simulation.errors.DsyncException;
import com.mazebert.simulation.replay.StreamReplayReader;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.LongAdder;
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
            "0dd320b2-cbed-4533-b265-8eddea8dc005-35049.mbg",
            "08e5765a-b727-4adb-a749-c16aa5c41d28-27392.mbg",
            "10b4c4d6-6c18-4cdb-8da5-c8967aca5438-13732.mbg"
    ));

    private static final Path gamesDirectory = Paths.get("games");

    private final ConcurrentMap<Path, Exception> errors = new ConcurrentHashMap<>();

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

    @Test
    void check_18() throws IOException {
        checkGames(Sim.vDoL);
    }

    @Test
    void check_19() throws IOException {
        checkGames(Sim.v19);
    }

    @Test
    void check_20() throws IOException {
        checkGames(Sim.vCorona);
    }

    @Test
    void check_21() throws IOException {
        checkGames(Sim.vDoLEnd);
    }

    @Test
    void check_22() throws IOException {
        checkGames(Sim.vRoC);
    }

    @Test
    void check_23() throws IOException {
        checkGames(Sim.v23);
    }

    @Test
    void check_24() throws IOException {
        checkGames(Sim.v24);
    }

    @Test
    void corruptEndOfFile() {
        int version = Sim.vDoLEnd;
        checkGame(gamesDirectory.resolve("corrupt-eof.mbg"), version, null);
    }

    @Test
    void noOutOfMemoryIfNoPathCouldBeFound() {
        int version = 22;
        checkGame(gamesDirectory.resolve("no-oom-pathfinding.mbg"), version, null);
    }

    @Disabled
    @Test
    void checkOne() {
        int version = Sim.v10;
        checkGame(gamesDirectory.resolve(version + "/0dd320b2-cbed-4533-b265-8eddea8dc005-35049.mbg"), version, null);
    }

    private void checkGames(int version) throws IOException {
        Path directory = gamesDirectory.resolve("" + version);

        List<Path> files = Files.walk(directory, 1).filter(p -> p.toString().endsWith(".mbg")).collect(Collectors.toList());
        int total = files.size();
        System.out.println("Validating " + total + " games (version " + version + ")");
        LongAdder counter = new LongAdder();

        GameValidationGateway gameValidationGateway = new GameValidationGateway(directory);

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (Path file : files) {
            executorService.submit(() -> {
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

                    checkGame(file, version, gameValidationGateway);
                } finally {
                    counter.add(1);
                    System.out.println(counter + "/" + total + "");
                }
            });
        }

        try {
            executorService.shutdown();
            boolean success = executorService.awaitTermination(1, TimeUnit.HOURS);
            assertThat(success).isTrue();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }

        if (gameValidationGateway.needsToBePopulated) {
            gameValidationGateway.writeToDisk();
        }
    }

    private void checkGame(Path file, int version, GameValidationGateway gameValidationGateway) {
        try (StreamReplayReader replayReader = new StreamReplayReader(new BufferedInputStream(Files.newInputStream(file, StandardOpenOption.READ)), version)) {
            new SimulationValidator().validate(version, replayReader, null, context -> {
                if (gameValidationGateway == null) {
                    return;
                }

                String game = file.getFileName().toString();

                Wizard wizard = context.unitGateway.getWizard(1);
                String experience = "" + wizard.experience;

                if (gameValidationGateway.needsToBePopulated) {
                    gameValidationGateway.setExperience(game, experience);
                } else {
                    String expectedExperience = gameValidationGateway.getExperience(game);
                    if (!expectedExperience.equals(experience)) {
                        throw new DsyncException("XP after validation is different! Expected " + expectedExperience + ", got " + experience, context.turnGateway.getCurrentTurnNumber());
                    }
                }
            });

        } catch (Exception e) {
            System.err.println("Arghs! " + e.getMessage());
            errors.put(file, e);
        }
    }

    private static class GameValidationGateway {
        private final Path propertyFile;
        private final Properties properties = new Properties();
        private final boolean needsToBePopulated;

        public GameValidationGateway(Path directory) {
            propertyFile = directory.resolve("xp.properties");

            if (Files.exists(propertyFile)) {
                try (Reader reader = Files.newBufferedReader(propertyFile)) {
                    properties.load(reader);
                } catch (IOException e) {
                    throw new UncheckedIOException(propertyFile + " could not be read!", e);
                }
                needsToBePopulated = false;
            } else {
                System.out.println(propertyFile + " does not exist, will create it with this run.");
                needsToBePopulated = true;
            }
        }

        public String getExperience(String game) {
            String value = (String)properties.get(game);
            if (value == null) {
                throw new IllegalStateException("No xp stored for game " + game);
            }

            return value;
        }

        public void setExperience(String game, String experience) {
            synchronized (properties) {
                properties.setProperty(game, experience);
            }
        }

        public void writeToDisk() {
            System.out.print("Writing " + propertyFile + " ... ");

            try (Writer writer = Files.newBufferedWriter(propertyFile)) {
                properties.store(writer, null);
                System.out.println("done.");
            } catch (IOException e) {
                throw new UncheckedIOException(propertyFile + " could not be written!", e);
            }
        }
    }
}
