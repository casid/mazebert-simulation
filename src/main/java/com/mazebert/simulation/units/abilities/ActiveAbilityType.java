package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.units.towers.PubParty;

public enum ActiveAbilityType {
    PubParty(1, PubParty.class),
    ;

    public final int id;
    public final Class<? extends ActiveAbility> abilityClass;

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

    ActiveAbilityType(int id, Class<? extends ActiveAbility> abilityClass) {
        this.id = id;
        this.abilityClass = abilityClass;
    }

    public static ActiveAbilityType forId(int id) {
        return LOOKUP[id];
    }

    public static ActiveAbilityType forAbility(ActiveAbility ability) {
        Class<? extends ActiveAbility> abilityClass = ability.getClass();
        for (ActiveAbilityType abilityType : values()) {
            if (abilityType.abilityClass == abilityClass) {
                return abilityType;
            }
        }
        return null;
    }
}
