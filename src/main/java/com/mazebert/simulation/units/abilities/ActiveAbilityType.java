package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.items.BowlingBallRollAbility;
import com.mazebert.simulation.units.towers.KiwiHaka;
import com.mazebert.simulation.units.towers.MrIronConstruct;
import com.mazebert.simulation.units.towers.PubParty;
import com.mazebert.simulation.units.towers.PubParty10;

public strictfp enum ActiveAbilityType {
    PubParty(1, PubParty10.class, PubParty.class),
    MrIronConstruct(2, MrIronConstruct.class),
    KiwiHaka(3, KiwiHaka.class),
    BowlingBallRollAbility(4, BowlingBallRollAbility.class),
    ;

    public final int id;
    public final Class<? extends ActiveAbility>[] abilityClasses;

    private static ActiveAbilityType[] LOOKUP;

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
    ActiveAbilityType(int id, Class<? extends ActiveAbility> ... abilityClasses) {
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

    public Class<? extends ActiveAbility> getAbilityClass() {
        if (this == PubParty) {
            if (Sim.context().version <= 10) {
                return abilityClasses[0];
            } else {
                return abilityClasses[1];
            }
        }
        return abilityClasses[0];
    }
}
