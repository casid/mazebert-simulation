package com.mazebert.simulation;

import com.mazebert.simulation.units.towers.TowerType;

public strictfp enum Element {
    Nature(1),
    Metropolis(2),
    Darkness(3),
    ;

    private static Element[] LOOKUP;

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

    public static Element forId(int id) {
        return LOOKUP[id];
    }

    public final int id;

    Element(int id) {
        this.id = id;
    }

    public String getName() {
        return name();
    }
}
