package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.listeners.OnLevelChangedListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.AuraAbility;

public strictfp class StonecuttersAura extends AuraAbility<Stonecutters, Tower> implements OnLevelChangedListener {
    public static final int MEMBER_LEVEL_REQUIREMENT = 20;

    private int memberCount;
    private int memberLevel;
    private boolean bonusForAllDamageTypes;

    public StonecuttersAura() {
        super(CardCategory.Tower, Tower.class, 10000);
    }

    @Override
    protected boolean isQualifiedForAura(Tower unit) {
        return unit.getElement() == Element.Metropolis;
    }

    @Override
    protected void onAuraEntered(Tower unit) {
        unit.addAbilityStack(getUnit(), StonecuttersAuraEffect.class);
        updateMemberships();
        unit.onLevelChanged.add(this);
    }

    @Override
    protected void onAuraLeft(Tower unit) {
        unit.onLevelChanged.remove(this);
        updateMemberships();
        unit.removeAbilityStack(getUnit(), StonecuttersAuraEffect.class);
    }

    private void updateMemberships() {
        memberCount = 0;
        memberLevel = 0;
        bonusForAllDamageTypes = getUnit().getLevel() >= 99;
        forEach(this::updateMembership);
        forEach(this::updateMembershipEffect);
    }

    private void updateMembership(Tower tower) {
        if (tower.getLevel() >= MEMBER_LEVEL_REQUIREMENT) {
            ++memberCount;
            memberLevel += tower.getLevel();
        }
    }

    private void updateMembershipEffect(Tower tower) {
        StonecuttersAuraEffect effect = tower.getAbility(StonecuttersAuraEffect.class, getUnit());
        effect.update(memberLevel, bonusForAllDamageTypes);
    }

    public int getMemberCount() {
        return memberCount;
    }

    @Override
    public void onLevelChanged(Unit unit, int oldLevel, int newLevel) {
        updateMemberships();
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Membership Requirement";
    }

    @Override
    public String getDescription() {
        return "Every Metropolis tower level " + MEMBER_LEVEL_REQUIREMENT + " or greater becomes a member.";
    }

    @Override
    public String getIconFile() {
        return "missive_512";
    }
}
