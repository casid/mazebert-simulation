package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.abilities.AuraAbility;

public strictfp class AbyssKingAura extends AuraAbility<AbyssKing, Tower> {

    public static final float damagePerUndead = 0.01f;
    public static final float critDamagePerUndead = 0.01f;
    public static final float critChancePerUndead = 0.001f;

    private int armySize;

    public AbyssKingAura() {
        super(CardCategory.Tower, Tower.class, 10000);
    }

    @Override
    protected boolean isQualifiedForAura(Tower unit) {
        return unit.getElement() == Element.Darkness;
    }

    @Override
    protected void onAuraEntered(Tower unit) {
        AbyssKingAuraEffect ability = new AbyssKingAuraEffect(getUnit());
        unit.addAbility(ability);

        if (Sim.context().version >= Sim.v19) {
            ability.update(armySize);
        }
    }

    @Override
    protected void onAuraLeft(Tower unit) {
        unit.removeAbility(AbyssKingAuraEffect.class, getUnit());
    }

    public void update(int armySize) {
        this.armySize = armySize;
        forEach(this::update);
    }

    private void update(Tower tower) {
        AbyssKingAuraEffect ability = tower.getAbility(AbyssKingAuraEffect.class, getUnit());
        if (ability != null) {
            ability.update(armySize);
        }
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "King of Darkness";
    }

    @Override
    public String getDescription() {
        return "For every Undead in his army, all Darkness towers get:";
    }

    @Override
    public String getLevelBonus() {
        return
                format.percentWithSignAndUnit(damagePerUndead) + " damage\n" +
                format.percentWithSignAndUnit(critDamagePerUndead) + " crit damage\n" +
                format.percentWithSignAndUnit(critChancePerUndead) + " crit chance";
    }

    @Override
    public String getIconFile() {
        return "0041_purpledebuff_512";
    }
}
