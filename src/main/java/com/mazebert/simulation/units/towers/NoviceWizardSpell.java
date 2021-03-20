package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.listeners.OnDamageListener;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.abilities.ProjectileDamageAbility;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class NoviceWizardSpell extends Ability<Tower> implements OnDamageListener {
    public static final float warpSeconds = 3.0f;
    public static final float banishmentSeconds = 3.0f;
    public static final float banishmentAmplifier = 0.5f;
    public static final float spellChance = 0.05f;
    public static final float spellChanceLevelBonus = 0.0025f;
    public static final float backfireChance = 0.25f;

    private static final int goodColor = 0x009900;
    private static final int badColor = 0x990000;

    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onDamage.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onDamage.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onDamage(Object origin, Creep target, double damage, int multicrits) {
        if (isSpellCastPossible(origin)) {
            if (getUnit().isImmobilizeAbilityTriggeredIgnoringLuck(0.5f, target)) {
                castWarpSpell(target);
            } else {
                castBanishSpell(target);
            }
        }
    }

    private boolean isSpellCastPossible(Object origin) {
        return isSpellCastPossibleForOrigin(origin) && getUnit().isAbilityTriggered(spellChance + getUnit().getLevel() * spellChanceLevelBonus);
    }

    private boolean isSpellCastPossibleForOrigin(Object origin) {
        if (Sim.context().version >= Sim.vRoCEnd) {
            return isOriginalDamage(origin);
        } else {
            return origin instanceof ProjectileDamageAbility;
        }
    }

    private void castWarpSpell(Creep target) {
        if (getUnit().isNegativeAbilityTriggered(backfireChance)) {
            target.warpInTime(warpSeconds);
            if (simulationListeners.areNotificationsEnabled()) {
                simulationListeners.showNotification(target, "Time Warp", badColor);
                simulationListeners.soundNotification("sounds/warp-forward.mp3", null, 0.25f);
            }
        } else {
            target.warpInTime(-warpSeconds);
            if (simulationListeners.areNotificationsEnabled()) {
                simulationListeners.showNotification(target, "Time Warp", goodColor);
                simulationListeners.soundNotification("sounds/warp-back.mp3", null, 0.25f);
            }
        }
    }

    private void castBanishSpell(Creep target) {
        if (getUnit().isNegativeAbilityTriggered(backfireChance)) {
            NoviceWizardBanishBadEffect effect = target.addAbilityStack(NoviceWizardBanishBadEffect.class);
            effect.setDuration(banishmentSeconds);
            if (simulationListeners.areNotificationsEnabled()) {
                simulationListeners.showNotification(target, "Banishment", badColor);
                simulationListeners.soundNotification("sounds/banishment-bad.mp3");
            }
        } else {
            NoviceWizardBanishGoodEffect effect = target.addAbilityStack(NoviceWizardBanishGoodEffect.class);
            effect.setDuration(banishmentSeconds);
            if (simulationListeners.areNotificationsEnabled()) {
                simulationListeners.showNotification(target, "Banishment", goodColor);
                simulationListeners.soundNotification("sounds/banishment-good.mp3");
            }
        }
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Book of Spells";
    }

    @Override
    public String getDescription() {
        return format.percent(spellChance) + "% chance to cast a spell from his book upon attack, but each spell has a " + format.percent(backfireChance) + "% chance to backfire.\n" +
                "<c=#8000d9>Time Warp</c> moves the creep " + format.seconds(warpSeconds) + " into the <c=#009900>past</c>/<c=#990000>future</c>.\n" +
                "<c=#8000d9>Banishment</c> causes the creep to receive " + format.percent(banishmentAmplifier) + "% <c=#009900>more</c>/<c=#990000>less</c> damage for " + format.seconds(banishmentSeconds) + ".";
    }

    @Override
    public String getIconFile() {
        return "0014_magicbook_512";
    }

    @Override
    public String getLevelBonus() {
        return format.percentWithSignAndUnit(spellChanceLevelBonus) + " cast chance per level.";
    }
}
