package com.mazebert.simulation.systems;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.Wave;
import com.mazebert.simulation.WaveType;
import com.mazebert.simulation.gateways.DifficultyGateway;
import com.mazebert.simulation.plugins.FormatPlugin;
import com.mazebert.simulation.plugins.PlayerLevelPlugin;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class ExperienceSystem {
    private static final PlayerLevelPlugin playerLevelPlugin = new PlayerLevelPlugin();

    private final DifficultyGateway difficultyGateway = Sim.context().difficultyGateway;
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    public void grantExperience(Tower tower, float experience) {
        experience *= tower.getExperienceModifier();
        tower.addExperience(experience);
        if (simulationListeners.areNotificationsEnabled()) {
            simulationListeners.showExperienceGainNotification(tower, experience);
        }
    }

    public void grantExperience(Wizard wizard, Wave wave, Creep lastCreep) {
        long experienceForRound = calculateExperienceForCompletingRound(wizard, wave.round);
        long experienceForCreep = calculateExperienceForCreep(wizard, wave, lastCreep);
        grantExperience(wizard, experienceForRound + experienceForCreep);
    }

    public void grantExperience(Wizard wizard, long experience) {
        wizard.experience += experience;
        int level = playerLevelPlugin.getLevelForExperience(wizard.experience);
        if (wizard.level != level) {
            int oldLevel = wizard.level;
            wizard.level = level;
            wizard.onLevelChanged.dispatch(wizard, oldLevel, level);
        }
    }

    private long calculateExperienceForCompletingRound(Wizard wizard, int round) {
        int r0 = round - 1;

        double experienceModifier = getExperienceModifier(wizard);
        double experience = experienceModifier * ((0.12 * round * round + round) - (0.12 * r0 * r0 + r0));
        double maxExperience = experienceModifier * 99;

        if (experience < 1.0) {
            experience = 1;
        } else if (experience > maxExperience) {
            experience = maxExperience;
        }

        return StrictMath.round(experience);
    }

    private long calculateExperienceForCreep(Wizard wizard, Wave wave, Creep lastCreep) {
        if (wave.type != WaveType.Challenge) {
            return 0;
        }

        double progress = 1.0 - lastCreep.getHealth() / lastCreep.getMaxHealth();
        long experience = StrictMath.round(progress * 111 * getExperienceModifier(wizard));

        if (simulationListeners.areNotificationsEnabled()) {
            FormatPlugin format = Sim.context().formatPlugin;
            simulationListeners.showNotification(wizard, format.percent((float) progress) + "% challenge damage! (" + format.experienceGain(experience) + ")");
        }

        return experience;
    }

    private double getExperienceModifier(Wizard wizard) {
        return wizard.experienceModifier * difficultyGateway.getDifficulty().experienceModifier;
    }
}
