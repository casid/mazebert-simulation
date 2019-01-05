package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Element;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.listeners.OnLevelChangedListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class LightbladeAcademySwordAbility extends Ability<Tower> implements OnLevelChangedListener {
    private static final int REQUIRED_LEVEL = 50;
    private static final float DAMAGE_LEVEL_BONUS = 0.15f;

    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    private float damageBonus;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);

        playBladeSound("lightblade-on.mp3");
        getUnit().onLevelChanged.add(this);
        addBonus();
    }

    @Override
    protected void dispose(Tower unit) {
        playBladeSound("lightblade-off.mp3");
        getUnit().onLevelChanged.remove(this);
        removeBouns();

        super.dispose(unit);
    }

    private void playBladeSound(String sound) {
        if (simulationListeners.areNotificationsEnabled() && getUnit().getLevel() >= REQUIRED_LEVEL) {
            simulationListeners.soundNotification("sounds/" + sound);
        }
    }

    @Override
    public void onLevelChanged(Unit unit, int oldLevel, int newLevel) {
        removeBouns();
        addBonus();
    }

    private void removeBouns() {
        if (damageBonus > 0.0f) {
            getUnit().addAddedRelativeBaseDamage(-damageBonus);
            damageBonus = 0;
        }
    }

    private void addBonus() {
        if (getUnit().getLevel() >= REQUIRED_LEVEL) {
            damageBonus = getUnit().getLevel() * DAMAGE_LEVEL_BONUS;
            getUnit().addAddedRelativeBaseDamage(damageBonus);
        } else {
            damageBonus = 0.0f;
        }
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Master of the blade";
    }

    @Override
    public String getDescription() {
        return format.percentWithSignAndUnit(DAMAGE_LEVEL_BONUS) + " damage per tower level.\n(Requires tower level " + REQUIRED_LEVEL + ")";
    }

    public String getBladeIcon() {
        if (getUnit() == null || getUnit().getLevel() < REQUIRED_LEVEL) {
            return "lightblade_512";
        } else {
            Element element = getUnit().getElement();
            if (element == Element.Darkness) {
                return "lightblade_red_512";
            } else if (element == Element.Nature) {
                return "lightblade_green_512";
            }

            return "lightblade_blue_512";
        }
    }
}