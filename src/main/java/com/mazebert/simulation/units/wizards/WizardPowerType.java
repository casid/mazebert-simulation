package com.mazebert.simulation.units.wizards;

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
    ;

    private static WizardPowerType[] LOOKUP;

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
