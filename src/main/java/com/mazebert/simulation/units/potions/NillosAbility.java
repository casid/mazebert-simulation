package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.towers.Tower;

public class NillosAbility extends Ability<Tower> {
    private static final float luckAdd = 0.2f;
    private static final float attackSpeedAdd = 0.4f;
    private static final float itemChance = 0.2f;
    private static final float itemQuality = 0.2f;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);

        unit.addLuck(luckAdd);
        unit.addAttackSpeed(attackSpeedAdd);
        unit.addItemChance(itemChance);
        unit.addItemQuality(itemQuality);
    }

    @Override
    protected void dispose(Tower unit) {
        super.dispose(unit);

        unit.addLuck(-luckAdd);
        unit.addAttackSpeed(-attackSpeedAdd);
        unit.addItemChance(-itemChance);
        unit.addItemQuality(-itemQuality);
    }

    @Override
    public boolean isPermanent() {
        return true;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Work smarter, not harder";
    }

    @Override
    public String getDescription() {
        return "+ " + formatPlugin.percent(luckAdd) + "% luck\n" +
                "+ " + formatPlugin.percent(attackSpeedAdd) + "% attack speed\n" +
                "+ " + formatPlugin.percent(itemChance) + "% item chance\n" +
                "+ " + formatPlugin.percent(itemQuality) + "% item quality";
    }
}
