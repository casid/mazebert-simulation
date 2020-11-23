package com.mazebert.simulation.units.items;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.units.abilities.AuraAbility;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class EldritchChainsawAbility extends AuraAbility<Tower, Creep> {

    public static float damageFactor = 0.4f;
    public static float damageFactorPerLevel = 0.04f;

    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    public EldritchChainsawAbility(EldritchChainsaw chainsaw) {
        super(CardCategory.Tower, Creep.class, 1);
        setOrigin(chainsaw);
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);

        if (simulationListeners.areNotificationsEnabled()) {
            simulationListeners.soundNotification("sounds/chainsaw.mp3");
        }
    }

    @Override
    protected void onAuraEntered(Creep unit) {
        unit.addAbility(new EldritchChainsawEffect(getUnit(), damageFactor, damageFactorPerLevel));
    }

    @Override
    protected void onAuraLeft(Creep unit) {
        unit.removeAbility(EldritchChainsawEffect.class, getUnit());
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Groovy";
    }

    @Override
    public String getDescription() {
        return "Creeps in 1 range are cut with " + format.percent(damageFactor) + "% tower damage per second.";
    }

    @Override
    public String getLevelBonus() {
        return format.percentWithSignAndUnit(damageFactorPerLevel) + " cut damage per tower level.";
    }
}
