package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.items.BowlingBallRollAbility;
import com.mazebert.simulation.units.items.NecronomiconSummonAbility;
import com.mazebert.simulation.units.items.ScepterOfTimeAbility;
import com.mazebert.simulation.units.towers.*;

public strictfp enum ActiveAbilityType {
    PubParty(1, PubParty10.class, PubParty.class),
    MrIronConstruct(2, MrIronConstruct.class),
    KiwiHaka(3, KiwiHaka.class),
    BowlingBallRollAbility(4, BowlingBallRollAbility.class),
    PhoenixRebirth(5, PhoenixRebirth.class),
    ScepterOfTimeToggle(6, ScepterOfTimeAbility.class),
    NecronomiconSummon(7, NecronomiconSummonAbility.class),
    ;

    public final int id;
    public final Class<? extends ActiveAbility>[] abilityClasses;

    private static final ActiveAbilityType[] LOOKUP;

    static {
        int maxId = 0;
        for (ActiveAbilityType type : ActiveAbilityType.values()) {
            maxId = StrictMath.max(maxId, type.id);
        }
        LOOKUP = new ActiveAbilityType[maxId + 1];
        for (ActiveAbilityType type : ActiveAbilityType.values()) {
            LOOKUP[type.id] = type;
        }
    }

    @SafeVarargs
    ActiveAbilityType(int id, Class<? extends ActiveAbility>... abilityClasses) {
        this.id = id;
        this.abilityClasses = abilityClasses;
    }

    public static ActiveAbilityType forId(int id) {
        return LOOKUP[id];
    }

    @SuppressWarnings("unused") // by client
    public static ActiveAbilityType forAbility(ActiveAbility ability) {
        Class<? extends ActiveAbility> abilityClass = ability.getClass();
        for (ActiveAbilityType abilityType : values()) {
            for (Class<? extends ActiveAbility> typeClass : abilityType.abilityClasses) {
                if (typeClass == abilityClass) {
                    return abilityType;
                }
            }
        }
        return null;
    }

    public Class<? extends ActiveAbility> getAbilityClass(int version) {
        if (this == PubParty) {
            if (version > Sim.v10) {
                return abilityClasses[1];
            } else {
                return abilityClasses[0];
            }
        }
        return abilityClasses[0];
    }
}
