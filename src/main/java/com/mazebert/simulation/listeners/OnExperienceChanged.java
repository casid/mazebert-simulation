package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.Unit;

public strictfp class OnExperienceChanged extends ExposedSignal<OnExperienceChangedListener> {

    public void dispatch(Unit unit, float oldExperience, float newExperience) {
        for (OnExperienceChangedListener listener : this) {
            listener.onExperienceChanged(unit, oldExperience, newExperience);
        }

        if (isExposed()) {
            dispatchExposed(l -> l.onExperienceChanged(unit, oldExperience, newExperience));
        }
    }
}
