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
    private final float damageLevelBonus;

    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    private float damageBonus;

    public LightbladeAcademySwordAbility() {
        if (Sim.context().version >= Sim.vDoLEnd) {
            damageLevelBonus = 0.1f;
        } else {
            damageLevelBonus = 0.15f;
        }
    }

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
            damageBonus = getUnit().getLevel() * damageLevelBonus;
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
        return "Master of the Blade";
    }

    @Override
    public String getLevelBonus() {
        return format.percentWithSignAndUnit(damageLevelBonus) + " damage per level.\n(Requires level " + REQUIRED_LEVEL + ")";
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
