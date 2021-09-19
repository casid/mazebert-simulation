package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.abilities.AuraAbility;

public strictfp class ManitouAura extends AuraAbility<Tower, Tower> {
    private static final int BONUS = 2;

    public ManitouAura() {
        super(CardCategory.Tower, Tower.class, Sim.context().version >= Sim.vDoLEnd ? 0 : 3);
    }

    @Override
    protected void onAuraEntered(Tower unit) {
        unit.addMulticrit(+calculateBonus(unit));
    }

    @Override
    protected void onAuraLeft(Tower unit) {
        unit.addMulticrit(-calculateBonus(unit));
    }

    private int calculateBonus(Tower tower) {
        if (Sim.context().version >= Sim.vRoCEnd && tower.getElement() == Element.Nature) {
            return 2 * BONUS;
        }
        return BONUS;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Buffalo Totem";
    }

    @Override
    public String getDescription() {
        return "Towers are blessed with the strength of the buffalo.";
    }

    @Override
    public String getIconFile() {
        return "0017_tribalnecklace_512";
    }

    @Override
    public String getLevelBonus() {
        if (Sim.context().version >= Sim.vRoCEnd) {
            return "+" + BONUS + " multicrit for towers in range. Buff is doubled for " + format.element(Element.Nature) + " towers.";
        } else {
            return "+" + BONUS + " multicrit for towers in range.";
        }
    }
}
