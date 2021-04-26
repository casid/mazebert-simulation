package com.mazebert.simulation;

import com.mazebert.simulation.units.towers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Balancing_TowerExperienceTester extends SimTest {

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
    }

    @Test
    void printExperienceAndDamage() {
        System.out.println(Balancing.getTowerExperienceForLevel(99));
        System.out.println(Balancing.getTowerExperienceForLevel(100));
        System.out.println(Balancing.getTowerExperienceForLevel(101));
        System.out.println(Balancing.getTowerExperienceForLevel(102));
        System.out.println(Balancing.getTowerExperienceForLevel(103));
        System.out.println(Balancing.getTowerExperienceForLevel(120));
        System.out.println(Balancing.getTowerExperienceForLevel(140));
        System.out.println(Balancing.getTowerExperienceForLevel(199));
        System.out.println(Balancing.getTowerExperienceForLevel(999));

        printDamageProgression(new Guard());
        printDamageProgression(new Templar());
        printDamageProgression(new KingArthur());
    }

    private void printDamageProgression(Tower tower) {
        System.out.println("Damage progression of " + tower.getName());

        printDamageAtLevel(tower, 99);
        printDamageAtLevel(tower, 120);
        printDamageAtLevel(tower, 140);
        printDamageAtLevel(tower, 199);
        printDamageAtLevel(tower, 999);
    }

    private void printDamageAtLevel(Tower tower, int level) {
        tower.setLevel(level);
        System.out.println("Level " + level + ": " + tower.getMinBaseDamage() + "-" + tower.getMaxBaseDamage());
    }
}