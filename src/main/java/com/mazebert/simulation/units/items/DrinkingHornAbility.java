package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.listeners.OnAbilityRemovedListener;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class DrinkingHornAbility extends Ability<Tower> implements OnAbilityRemovedListener {
    private final float potionBonus;
    private final float luckBonus = 0.1f;
    private final float missChance = 0.05f;

    public DrinkingHornAbility() {
        if (Sim.context().version >= Sim.vRoC) {
            potionBonus = 0.2f;
        } else {
            potionBonus = 0.1f;
        }
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.addPotionEffectiveness(potionBonus);
        unit.addLuck(luckBonus);
        unit.addChanceToMiss(missChance);

        unit.onAbilityRemoved.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onAbilityRemoved.remove(this);

        unit.addPotionEffectiveness(-potionBonus);
        unit.addLuck(-luckBonus);
        unit.addChanceToMiss(-missChance);
        super.dispose(unit);
    }

    @Override
    public void onAbilityRemoved(Ability ability) {
        Tower tower = getUnit();
        if (Sim.context().version >= Sim.v23 && tower.isMarkedForDisposal()) {
            // This tower is about to be disposed, items will be transferred automatically later on.
            // This fixes a dupe exploit of Viking Horn: https://mazebert.com/forum/bugs/roordahuizum-duplicate--id1431
            return;
        }

        if (!tower.isViking()) {
            tower.removeItem(ItemType.DrinkingHorn);
            if (Sim.context().version >= Sim.vRoC) {
                tower.getWizard().itemStash.add(ItemType.DrinkingHorn);
            }
        }
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Drink with the Gods";
    }

    @Override
    public String getLevelBonus() {
        return format.percentWithSignAndUnit(potionBonus) + " effect of consumed potions.\n" + format.percentWithSignAndUnit(luckBonus) + " luck.\n" + format.percentWithSignAndUnit(missChance) + " chance to miss attacks.";
    }
}
