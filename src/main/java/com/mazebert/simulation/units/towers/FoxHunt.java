package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.systems.ExperienceSystem;
import com.mazebert.simulation.systems.PermanentAbilitySystem;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.abilities.ActiveAbility;
import com.mazebert.simulation.units.abilities.StackableAbility;
import com.mazebert.simulation.util.IntegerReference;

import java.util.HashMap;
import java.util.Map;

public strictfp class FoxHunt extends ActiveAbility {

    private static final float CRIT_CHANCE_PER_RABBIT = 0.04f;

    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;
    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final ExperienceSystem experienceSystem = Sim.context().experienceSystem;
    private final Map<Class, IntegerReference> rabbitPotions = new HashMap<>();
    private int rabbitsEaten;

    @Override
    public float getReadyProgress() {
        return findRabbitOnField() != null ? 1.0f : 0.0f;
    }

    @Override
    public void activate() {
        huntRabbit();
    }

    private void huntRabbit() {
        Rabbit rabbit = findRabbitOnField();
        if (rabbit != null) {
            eatRabbit(rabbit);
        }
    }

    private Rabbit findRabbitOnField() {
        return unitGateway.findUnit(Rabbit.class, r -> r.getWizard() == getUnit().getWizard());
    }

    private void eatRabbit(Rabbit rabbit) {
        getUnit().addCritChance(CRIT_CHANCE_PER_RABBIT);
        experienceSystem.grantExperience(getUnit(), rabbit.getExperience(), true);

        rememberRabbitPotions(rabbit);

        rabbit.markForDisposal();
        PermanentAbilitySystem.transferAll(rabbit, getUnit());
        unitGateway.destroyTower(rabbit);

        ++rabbitsEaten;
        if (simulationListeners.areNotificationsEnabled()) {
            simulationListeners.showNotification(getUnit(), "Rabbit eaten");
        }
    }

    private void rememberRabbitPotions(Rabbit rabbit) {
        rabbit.forEachAbility(ability -> {
            if (ability.isPermanent()) {
                if (ability instanceof StackableAbility) {
                    rememberRabbitPotion(ability, ((StackableAbility) ability).getStackCount());
                } else {
                    rememberRabbitPotion(ability, 1);
                }
            }
        });
    }

    private void rememberRabbitPotion(Ability ability, int count) {
        IntegerReference amount = rabbitPotions.get(ability.getClass());
        if (amount == null) {
            amount = new IntegerReference(count);
            rabbitPotions.put(ability.getClass(), amount);
        } else {
            amount.value += count;
        }
    }

    public int getRabbitsEaten() {
        return rabbitsEaten;
    }

    public void onFoxReplaced() {
        Map<Class, IntegerReference> rabbitPotionsToRemove = calculateRabbitPotionsToRemove();
        if (rabbitPotionsToRemove.isEmpty()) {
            return;
        }

        removePotions(rabbitPotionsToRemove);
    }

    private Map<Class, IntegerReference> calculateRabbitPotionsToRemove() {
        Map<Class, IntegerReference> potionsToRemove = new HashMap<>();
        for (Map.Entry<Class, IntegerReference> entry : rabbitPotions.entrySet()) {
            int amountToRemove = entry.getValue().value / 2;
            if (amountToRemove > 0) {
                potionsToRemove.put(entry.getKey(), new IntegerReference(amountToRemove));
            }
        }
        return potionsToRemove;
    }

    private void removePotions(Map<Class, IntegerReference> potionsToRemove) {
        getUnit().forEachAbility(ability -> {
            if (ability.isPermanent()) {
                IntegerReference amountToRemove = potionsToRemove.get(ability.getClass());
                if (amountToRemove != null && amountToRemove.value > 0) {
                    if (ability instanceof StackableAbility) {
                        do {
                            getUnit().removeAbility(ability);
                            --amountToRemove.value;
                        } while (amountToRemove.value > 0);
                    } else {
                        getUnit().removeAbility(ability);
                        --amountToRemove.value;
                    }
                }
            }
        });
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Rabbit Hunter";
    }

    @Override
    public String getDescription() {
        return format.card(TowerType.Fox) + " eats a " + format.card(TowerType.Rabbit) + " on the field, carrying over the Rabbit’s experience and potions. Lose 50% of eaten rabbit potions when " + format.card(TowerType.Fox) + " is replaced.";
    }

    @Override
    public String getIconFile() {
        return "fox";
    }

    @Override
    public String getLevelBonus() {
        return format.percentWithSignAndUnit(CRIT_CHANCE_PER_RABBIT) + " crit chance per " + format.card(TowerType.Rabbit) + " eaten";
    }
}
