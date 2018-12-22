package com.mazebert.simulation.units.items;

import com.mazebert.simulation.listeners.OnGenderChangedListener;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.ItemChanceWithLevelBonusAbility;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class HandbagAbility extends ItemChanceWithLevelBonusAbility implements OnGenderChangedListener {
    public HandbagAbility() {
        super(0.3f, 0.001f);
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onGenderChanged.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onGenderChanged.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onGenderChanged(Unit unit) {
        updateBonus();
    }

    @Override
    protected float calculateBonus() {
        if (getUnit().getGender() == Gender.Female) {
            return super.calculateBonus();
        } else {
            return 0.0f;
        }
    }

    @Override
    public String getTitle() {
        return "Shopping Queen";
    }

    @Override
    public String getDescription() {
        return "If the carrier of this bag is female, her item chance is increased by " + format.percent(bonus) + "%. For male towers this bag has no effect, except that it makes them look stupid.";
    }

    @Override
    public String getLevelBonus() {
        return format.percentWithSignAndUnit(bonusPerLevel) + " item chance per level.";
    }
}
