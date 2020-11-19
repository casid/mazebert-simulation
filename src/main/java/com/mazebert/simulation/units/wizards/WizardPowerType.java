package com.mazebert.simulation.units.wizards;

import com.mazebert.simulation.Sim;

public strictfp enum WizardPowerType {
    BerserkPower(1, BerserkPower.class),
    ButcherPower(2, ButcherPower.class),
    ChallengePower(3, ChallengePower.class),
    CollectorPower(4, CollectorPower.class),
    DeckMasterPower(5, DeckMasterPower.class),
    ElderPower(6, ElderPower.class),
    GiantPower(7, GiantPower.class),
    NinjaPower(8, NinjaPower.class),
    ProtectorPower(9, ProtectorPower.class),
    SeekerPower(10, SeekerPower.class),
    StashMasterPower(11, StashMasterPower.class),
    TimeMasterPower(12, TimeMasterPower.class),
    TricksterPower(13, TricksterPower.class),
    BerPower(14, BerPower.class),
    FalPower(15, FalPower.class),
    VexPower(16, VexPower.class),
    WizardExperiencePower(17, WizardExperiencePower.class),
    CultistPower(18, CultistPower.class),
    ;

    private static final WizardPowerType[] LOOKUP;
    private static final WizardPowerType[] STANDARD     = {BerserkPower, ButcherPower, ChallengePower, CollectorPower, DeckMasterPower, ElderPower, GiantPower, NinjaPower, ProtectorPower, SeekerPower, StashMasterPower, TimeMasterPower, TricksterPower, BerPower, FalPower, VexPower, WizardExperiencePower};
    private static final WizardPowerType[] ROC_SEASON   = {BerserkPower, ButcherPower, ChallengePower, CollectorPower, DeckMasterPower, ElderPower, GiantPower, NinjaPower, ProtectorPower, SeekerPower, StashMasterPower, TimeMasterPower, TricksterPower, BerPower, FalPower, VexPower, WizardExperiencePower, CultistPower};

    static {
        int maxId = 0;
        for (WizardPowerType type : WizardPowerType.values()) {
            maxId = StrictMath.max(maxId, type.id);
        }
        LOOKUP = new WizardPowerType[maxId + 1];
        for (WizardPowerType type : WizardPowerType.values()) {
            LOOKUP[type.id] = type;
        }
    }

    public final int id;
    public final Class<? extends WizardPower> powerClass;

    WizardPowerType(int id, Class<? extends WizardPower> powerClass) {
        this.id = id;
        this.powerClass = powerClass;
    }

    public static WizardPowerType[] getValues() {
        if (Sim.isRoCSeasonContent()) {
            return ROC_SEASON;
        }

        return STANDARD;
    }

    public static WizardPowerType forId(int id) {
        if (id < 0 || id >= LOOKUP.length) {
            return null;
        }
        return LOOKUP[id];
    }

    public static WizardPowerType forClass(Class<? extends WizardPower> powerClass) {
        for (WizardPowerType powerType : values()) {
            if (powerType.powerClass == powerClass) {
                return powerType;
            }
        }
        return null;
    }

    public WizardPower create() {
        try {
            return powerClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
