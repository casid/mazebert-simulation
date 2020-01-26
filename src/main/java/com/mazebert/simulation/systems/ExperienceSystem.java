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
    public static final int BONUS_ROUND_REWARD_INTERVAL = 30;

    private static final PlayerLevelPlugin playerLevelPlugin = new PlayerLevelPlugin();

    private final DifficultyGateway difficultyGateway = Sim.context().difficultyGateway;
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    public void grantExperience(Tower tower, Creep creep, boolean showNotification) {
        float experience = creep.getExperienceModifier() * creep.getExperience();

        if (Sim.context().version >= Sim.v20) {
            if (experience > 0) {
                creep.getDamageMap().forEachNormalized((t, d) -> {
                    if (d > 0) {
                        grantExperience(t, (float) d * experience, showNotification);
                    }
                });
            }
        } else {
            grantExperience(tower, experience, showNotification);
        }
    }

    public void grantExperience(Tower tower, float experience, boolean showNotification) {
        if (experience > 0) {
            experience *= tower.getExperienceModifier();
        }
        tower.addExperience(experience);
        if (showNotification && simulationListeners.areNotificationsEnabled()) {
            simulationListeners.showExperienceNotification(tower, experience);
        }
    }

    public void grantExperience(Tower tower, float experience) {
        grantExperience(tower, experience, true);
    }

    public void grantExperience(Wizard wizard, Wave wave, Creep lastCreep) {
        long experienceForRound = calculateExperienceForCompletingRound(wizard, wave.round);
        long experienceForCreep = calculateExperienceForCreep(wizard, wave, lastCreep);
        grantExperience(wizard, experienceForRound + experienceForCreep);
    }

    public void grantExperience(Wizard wizard, long experience) {
        wizard.experience += experience;
        int level = calculateLevel(wizard);
        if (wizard.level != level) {
            int oldLevel = wizard.level;
            wizard.level = level;
            wizard.onLevelChanged.dispatch(wizard, oldLevel, level);
        }
    }

    public int calculateLevel(Wizard wizard) {
        return playerLevelPlugin.getLevelForExperience(wizard.experience);
    }

    public void grantBonusRoundExperience(Wizard wizard, int bonusRoundSeconds, boolean notify) {
        double rounds = (double) bonusRoundSeconds / BONUS_ROUND_REWARD_INTERVAL;
        long experience = StrictMath.round(getExperienceModifier(wizard) * 100 * (1 + 2 * rounds / (20 + rounds)));
        grantExperience(wizard, experience);

        if (notify && simulationListeners.areNotificationsEnabled()) {
            FormatPlugin format = Sim.context().formatPlugin;
            simulationListeners.showNotification(wizard, format.experienceWithSignAndUnit(experience) + " survival bonus.");
        }
    }

    public boolean isTimeToGrantBonusRoundExperience(int bonusRoundSeconds) {
        return bonusRoundSeconds % BONUS_ROUND_REWARD_INTERVAL == 0;
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
        long experience = calculateExperience(wizard, progress * 111.0);

        if (simulationListeners.areNotificationsEnabled()) {
            FormatPlugin format = Sim.context().formatPlugin;
            simulationListeners.showNotification(wizard, format.percent((float) progress) + "% challenge damage! (" + format.experienceWithSignAndUnit(experience) + ")");
        }

        return experience;
    }

    private double getExperienceModifier(Wizard wizard) {
        return wizard.experienceModifier * difficultyGateway.getDifficulty().experienceModifier;
    }

    public long calculateExperience(Wizard wizard, double experience) {
        return StrictMath.round(experience * getExperienceModifier(wizard));
    }
}
