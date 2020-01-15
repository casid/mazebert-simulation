package com.mazebert.simulation;

import com.mazebert.simulation.hash.Hash;
import com.mazebert.simulation.hash.Hashable;

public strictfp enum Element implements Hashable {
    Unknown(0, 0x868686, "None"),
    Nature(1, 0x71864c, "Jotunheim"),
    Metropolis(2, 0x868686, "Midgard"),
    Darkness(3, 0x444444, "Helheim"),
    Light(4, 0xffffff, "Asgard"),
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
    public final String norseWorld;

    Element(int id, int color, String norseWorld) {
        this.id = id;
        this.color = color;
        this.norseWorld = norseWorld;
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
