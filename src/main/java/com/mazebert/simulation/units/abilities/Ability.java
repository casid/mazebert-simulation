package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.plugins.FormatPlugin;
import com.mazebert.simulation.units.Currency;
import com.mazebert.simulation.units.Unit;

public abstract strictfp class Ability<U extends Unit> {

    protected final FormatPlugin format = Sim.context().formatPlugin;

    private Object origin;
    private U unit;

    public final void init(U unit) {
        if (this.unit != null) {
            throw new IllegalStateException("This ability is already owned by " + this.unit);
        }
        initialize(unit);
    }

    public final void dispose() {
        if (this.unit == null) {
            throw new IllegalStateException("This ability is already disposed");
        }
        dispose(unit);
    }

    protected void initialize(U unit) {
        this.unit = unit;
    }

    protected void dispose(U unit) {
        this.unit = null;
    }

    public boolean isPermanent() {
        return false;
    }

    public void setOrigin(Object origin) {
        this.origin = origin;
    }

    public Object getOrigin() {
        return origin;
    }

    public U getUnit() {
        return unit;
    }

    public boolean isVisibleToUser() {
        return false;
    }

    public String getTitle() {
        return "";
    }

    public String getDescription() {
        return "";
    }

    public String getIconFile() {
        return "";
    }

    public String getLevelBonus() {
        return "";
    }

    protected Currency getCurrency() {
        if (getUnit().getWizard() != null) {
            return getUnit().getWizard().currency;
        }
        return Currency.Gold;
    }
}
