package com.mazebert.simulation;

public strictfp enum Element {
    Nature(1, 0x71864c),
    Metropolis(2, 0x868686),
    Darkness(3, 0x444444),
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
}
