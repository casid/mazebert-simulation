package com.mazebert.simulation;

import com.mazebert.simulation.listeners.*;
import com.mazebert.simulation.replay.StreamReplayReader;
import com.mazebert.simulation.replay.data.ReplayHeader;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.creeps.Creep;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

@Execution(ExecutionMode.SAME_THREAD)
public class HitpointBalancingTester {

    private static final Path gamesDirectory = Paths.get("games");

    private final Map<String, double[]> validatedGames = new ConcurrentHashMap<>();

    @Test
    void oneGame() {
        checkGame(Paths.get("games", "19", "b676082b-e3d2-4b8f-882d-37ddb28a569d-36503.mbg"), Sim.v19);
        storeData("hitpoint-balancing-19-oneGame.csv");
    }

    @Test
    void multiplayer2() throws IOException {
        checkGames(18, 2, 0);
        checkGames(19, 2, 0);
        storeData("hitpoint-balancing-mp2.csv");
    }

    @Test
    void multiplayer2_reference() {
        addHitpointsReference(20, 2);
        storeData("hitpoint-balancing-mp2-reference.csv");
    }

    @Test
    void multiplayer3_reference() {
        addHitpointsReference(20, 3);
        storeData("hitpoint-balancing-mp3-reference.csv");
    }

    @Test
    void multiplayer4_reference() {
        addHitpointsReference(20, 4);
        storeData("hitpoint-balancing-mp4-reference.csv");
    }

    @Test
    void singleplayer() throws IOException {
        checkGames(19, 1, 1000);
        storeData("hitpoint-balancing-sp.csv");
    }

    @Test
    void singleplayer_reference() {
        addHitpointsReference(20, 1);
        storeData("hitpoint-balancing-sp-reference.csv");
    }

    @SuppressWarnings("SameParameterValue")
    private void addHitpointsReference(int version, int playerCount) {
        for (Difficulty difficulty : Difficulty.values()) {
            double[] hitpoints = new double[500];
            for (int i = 0; i < hitpoints.length; i++) {
                hitpoints[i] = Balancing.getTotalCreepHitpoints(version, i + 1, difficulty, playerCount);
                if (version < Sim.vDoLEnd) {
                    hitpoints[i] *= playerCount; // More creeps where spawned back in the days
                }
            }
            validatedGames.put("mp-reference-" + difficulty, hitpoints);
        }
    }

    private void storeData(String filename) {
        try (OutputStreamWriter writer = new OutputStreamWriter(Files.newOutputStream(Paths.get(filename), StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING))) {
            List<Map.Entry<String, double[]>> entries = validatedGames.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toList());
            for (Map.Entry<String, double[]> entry : entries) {
                writer.write(entry.getKey());
                for (double damage : entry.getValue()) {
                    writer.write("," + damage);
                }
                writer.write('\n');
            }
            writer.flush();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @SuppressWarnings("SameParameterValue")
    private void checkGames(int version, int playerCount, int limit) throws IOException {
        List<Path> files = Files.walk(gamesDirectory.resolve("" + version), 1).collect(Collectors.toList());
        int total = files.size();
        System.out.println("Validating " + total + " games (version " + version + ")");
        LongAdder counter = new LongAdder();

        files.parallelStream().forEach(file -> {
            try {
                if (limit > 0 && counter.sum() > limit) {
                    return;
                }

                if (Files.isDirectory(file)) {
                    return;
                }

                String fileName = file.getFileName().toString();
                if (fileName.startsWith(".")) {
                    return;
                }

                ReplayHeader replayHeader = readHeader(file, version);
                if (replayHeader == null) {
                    return;
                }

                if (replayHeader.playerCount != playerCount) {
                    return;
                }

                checkGame(file, version);
            } finally {
                counter.add(1);
                System.out.println(counter + "/" + total + "");
            }
        });
    }

    private ReplayHeader readHeader(Path file, int version) {
        try (StreamReplayReader replayReader = new StreamReplayReader(new BufferedInputStream(Files.newInputStream(file, StandardOpenOption.READ)), version)) {
            return replayReader.readHeader();
        } catch (IOException e) {
            return null;
        }
    }

    private void checkGame(Path file, int version) {
        String fileName = file.getFileName().toString();

        String uuidString = fileName.substring(0, 36);
        if (validatedGames.containsKey(uuidString)) {
            return;
        }

        double[] damagePerRound = new double[500];
        validatedGames.put(uuidString, damagePerRound);

        new CheckGame(file, version, damagePerRound).simulate(1);
    }

    private static class AbortedException extends RuntimeException {
    }

    private static class NextRoundException extends RuntimeException {
    }

    private static class TrackDamageAbility extends Ability<Creep> implements OnDamageListener {
        private final int index;
        private final double[] damagePerRound;

        public TrackDamageAbility(int index, double[] damagePerRound) {
            this.index = index;
            this.damagePerRound = damagePerRound;
        }

        @Override
        protected void initialize(Creep unit) {
            super.initialize(unit);
            unit.onDamage.add(this);
        }

        @Override
        protected void dispose(Creep unit) {
            unit.onDamage.remove(this);
            super.dispose(unit);
        }

        @Override
        public void onDamage(Object origin, Creep target, double damage, int multicrits) {
            damagePerRound[index] += damage;
        }
    }

    static class CheckGame implements OnGameStartedListener, OnUnitAddedListener, OnWaveFinishedListener, OnGameWonListener {
        private final Path file;
        private final int version;
        private final double[] damagePerRound;

        private int round;
        private boolean roundReached;

        CheckGame(Path path, int version, double[] damagePerRound) {
            this.file = path;
            this.version = version;
            this.damagePerRound = damagePerRound;
        }

        private void simulate(int round) {
            this.round = round;
            this.roundReached = false;

            try (StreamReplayReader replayReader = new StreamReplayReader(new BufferedInputStream(Files.newInputStream(file, StandardOpenOption.READ)), version)) {
                new SimulationValidator().validate(version, replayReader, this::beforeValidation, null);
            } catch (NextRoundException e) {
                // We need to simulate the next round
                simulate(round + 1);
            } catch (AbortedException e) {
                // This game is no longer interesting for us
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void beforeValidation(Context context) {
            context.simulationListeners.onGameStarted.add(this);
        }

        @Override
        public void onGameStarted() {
            Sim.context().simulationListeners.onGameStarted.remove(this);

            Sim.context().simulationListeners.onUnitAdded.add(this);
            Sim.context().simulationListeners.onWaveFinished.add(this);
            Sim.context().simulationListeners.onGameWon.add(this);

            Sim.context().simulation.setHashCheckDisabled(true);

        }

        @Override
        public void onUnitAdded(Unit unit) {
            if (unit instanceof Creep) {
                Creep creep = (Creep) unit;

                if (creep.getWave().round == round) {
                    roundReached = true;
                    creep.setImmortal(true);
                    creep.addAbility(new TrackDamageAbility(round - 1, damagePerRound));
                } else if (creep.getWave().round > round || roundReached) {
                    creep.setHealth(0); // Instantly kill that creep, in order to not mess with our calculation
                }
            }
        }

        @Override
        public void onWaveFinished(Wave wave) {
            if (wave.round == round) {
                throw new NextRoundException();
            }
        }

        @Override
        public void onGameWon() {
            throw new AbortedException();
        }
    }
}
