package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.towers.Mummy;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.towers.TowerType;

public strictfp class ToiletPaperMummyAbility extends Ability<Tower> {

    private final int targets = 3;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        AttackAbility attackAbility = unit.getAbility(AttackAbility.class);
        if (attackAbility != null && unit instanceof Mummy) {
            attackAbility.setTargets(attackAbility.getTargets() + targets);
        }
    }

    @Override
    protected void dispose(Tower unit) {
        AttackAbility attackAbility = unit.getAbility(AttackAbility.class);
        if (attackAbility != null && unit instanceof Mummy) {
            attackAbility.setTargets(attackAbility.getTargets() - targets);
        }
        super.dispose(unit);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Prepared Mummy";
    }

    @Override
    public String getDescription() {
        return "+" + targets + " additional targets if equipped by " + format.card(TowerType.Mummy);
    }
}
