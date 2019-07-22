package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.Context;
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

    public final boolean isDisposed() {
        return this.unit == null;
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

    public final U getUnit() {
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
        return null;
    }

    protected Currency getCurrency() {
        return Context.currency;
    }

    protected boolean isOriginalDamage(Object origin) {
        if (origin instanceof DamageAbility) {
            return ((DamageAbility)origin).isOriginalDamage();
        }
        return false;
    }
}
