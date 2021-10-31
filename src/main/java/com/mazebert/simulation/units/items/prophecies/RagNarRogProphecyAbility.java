package com.mazebert.simulation.units.items.prophecies;

import com.mazebert.simulation.Element;

public strictfp class RagNarRogProphecyAbility extends ProphecyAbility {

    @Override
    public String getDescription() {
        return format.prophecyDescription("All rulers of the four worlds\n" +
                format.norseWorld(Element.Light) + "\n" +
                format.norseWorld(Element.Nature) + "\n" +
                format.norseWorld(Element.Metropolis) + "\n" +
                format.norseWorld(Element.Darkness) + "\n" +
                "connected through the world tree.\n" +
                "A Challenge will start Rag nar Rog. You must defeat Naglfar or the world ends."
        );
    }

}
