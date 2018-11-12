package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.units.Unit;

public abstract strictfp class Ability<U extends Unit> {

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
}
