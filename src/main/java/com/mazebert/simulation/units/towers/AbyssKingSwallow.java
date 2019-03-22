package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.listeners.OnRangeChangedListener;
import com.mazebert.simulation.units.abilities.AuraAbility;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class AbyssKingSwallow extends AuraAbility<AbyssKing, Creep> implements OnRangeChangedListener {

    public static final float chance = 0.15f;
    public static final float chancePerLevel = 0.001f;

    public AbyssKingSwallow() {
        super(CardCategory.Tower, Creep.class);
    }

    @Override
    protected void initialize(AbyssKing unit) {
        super.initialize(unit);
    }

    @Override
    protected void dispose(AbyssKing unit) {
        super.dispose(unit);
    }

    @Override
    protected void onAuraEntered(Creep unit) {
        unit.addAbilityStack(getUnit(), AbyssKingSwallowEffect.class);
    }

    @Override
    protected void onAuraLeft(Creep unit) {
        unit.removeAbilityStack(getUnit(), AbyssKingSwallowEffect.class);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Arny of Darkness";
    }

    @Override
    public String getDescription() {
        return "Creeps killed in range have a " + format.percent(chance) + "% chance to join his Undead Army.";
    }

    @Override
    public String getLevelBonus() {
        return format.percentWithSignAndUnit(chancePerLevel) + " chance per level";
    }

    @Override
    public String getIconFile() {
        return "0029_undead_512";
    }
}
