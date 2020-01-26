package com.mazebert.simulation;

import com.mazebert.simulation.listeners.OnGameStartedListener;
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
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;
import static org.jusecase.Builders.set;

@Execution(ExecutionMode.SAME_THREAD)
public class HitpointBalancingTester implements OnGameStartedListener {

    private static final Path gamesDirectory = Paths.get("games");

    @Test
    void test() throws IOException {
        checkGames(Sim.vDoL);
    }

    private void checkGames(int version) throws IOException {
        List<Path> files = Files.walk(gamesDirectory.resolve("" + version), 1).collect(Collectors.toList());
        int total = files.size();
        System.out.println("Validating " + total + " games (version " + version + ")");
        LongAdder counter = new LongAdder();

        files.parallelStream().forEach(file -> {
            try {
                if (Files.isDirectory(file)) {
                    return;
                }

                String fileName = file.getFileName().toString();
                if (fileName.startsWith(".")) {
                    return;
                }

                checkGame(file, version);
            } finally {
                counter.add(1);
                System.out.println(counter + "/" + total + "");
            }
        });
    }

    private void checkGame(Path file, int version) {
        try (StreamReplayReader replayReader = new StreamReplayReader(new BufferedInputStream(Files.newInputStream(file, StandardOpenOption.READ)), version)) {
            new SimulationValidator().validate(version, replayReader, this::beforeValidation, null);
        } catch (AbortedException e) {
            // This game is not interesting for us
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void beforeValidation(Context context) {
        if (context.playerGateway.getPlayerCount() < 2) {
            throw new AbortedException();
        }

        context.simulationListeners.onGameStarted.add(this);
    }

    @Override
    public void onGameStarted() {
        Sim.context().simulationListeners.onGameStarted.remove(this);

    }

    private static class AbortedException extends RuntimeException {
    }
}
