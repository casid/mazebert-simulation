package com.mazebert.simulation;

import com.mazebert.simulation.hash.Hash;
import com.mazebert.simulation.hash.Hashable;

public strictfp enum Element implements Hashable {
    Unknown(0, 0x868686),
    Nature(1, 0x71864c),
    Metropolis(2, 0x868686),
    Darkness(3, 0x444444),
    Light(4, 0xffffff),
    ;

    private static Element[] LOOKUP;
    private static Element[] STANDARD = {Nature, Metropolis, Darkness};
    private static Element[] DAWN_OF_LIGHT = {Nature, Metropolis, Darkness, Light};

    static {
        int maxId = 0;
        for (Element element : Element.values()) {
            maxId = StrictMath.max(maxId, element.id);
        }
        LOOKUP = new Element[maxId + 1];
        for (Element element : Element.values()) {
            LOOKUP[element.id] = element;
        }
    }

    public static Element[] getValues() {
        if (Sim.isDoLSeasonContent()) {
            return DAWN_OF_LIGHT;
        }
        return STANDARD;
    }

    public final int id;
    public final int color;

    Element(int id, int color) {
        this.id = id;
        this.color = color;
    }

    public static Element forId(int id) {
        return LOOKUP[id];
    }

    public String getName() {
        return name();
    }

    @Override
    public void hash(Hash hash) {
        hash.add(id);
    }
}
