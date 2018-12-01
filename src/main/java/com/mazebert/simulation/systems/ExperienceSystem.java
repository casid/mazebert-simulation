package com.mazebert.simulation.systems;

import com.mazebert.simulation.Wave;
import com.mazebert.simulation.WaveType;
import com.mazebert.simulation.gateways.DifficultyGateway;
import com.mazebert.simulation.plugins.PlayerLevelPlugin;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class ExperienceSystem {
    private static final PlayerLevelPlugin playerLevelPlugin = new PlayerLevelPlugin();

    private final DifficultyGateway difficultyGateway;

    public ExperienceSystem(DifficultyGateway difficultyGateway) {
        this.difficultyGateway = difficultyGateway;
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
        return StrictMath.round(progress * 111 * getExperienceModifier(wizard));
    }

    private double getExperienceModifier(Wizard wizard) {
        return wizard.experienceModifier * difficultyGateway.getDifficulty().experienceModifier;
    }
}
