package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.ActiveAbility;

public strictfp class ThorHoldBack extends ActiveAbility {

    private final ThorAttack thorAttack;

    public ThorHoldBack(ThorAttack thorAttack) {
        this.thorAttack = thorAttack;
    }

    @Override
    public float getReadyProgress() {
        return 1.0f;
    }

    @Override
    public float getReadyProgressForDisplay() {
        return thorAttack.getProgress();
    }

    @Override
    public void activate() {
        thorAttack.toggleHoldBack();
    }

    @SuppressWarnings("unused") // Used by app client
    public boolean isHoldBack() {
        return thorAttack.isHoldBack();
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Holding Back";
    }

    @Override
    public String getDescription() {
        return "Stop attacking until reactivated.";
    }

    @Override
    public String getIconFile() {
        return "0006_handarmor_512";
    }
}
