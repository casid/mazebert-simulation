package com.mazebert.simulation.commands.serializers;

import com.mazebert.simulation.commands.ActivateAbilityCommand;
import com.mazebert.simulation.units.abilities.ActiveAbilityType;
import org.jusecase.bitpack.BitReader;
import org.jusecase.bitpack.BitSerializer;
import org.jusecase.bitpack.BitWriter;

public strictfp class ActivateAbilityCommandSerializer implements BitSerializer<ActivateAbilityCommand> {

    @Override
    public ActivateAbilityCommand createObject() {
        return new ActivateAbilityCommand();
    }

    @Override
    public void serialize(BitWriter writer, ActivateAbilityCommand object) {
        writer.writeInt12(object.abilityType.id);
        writer.writeInt12(object.towerX);
        writer.writeInt12(object.towerY);
    }

    @Override
    public void deserialize(BitReader reader, ActivateAbilityCommand object) {
        object.abilityType = ActiveAbilityType.forId(reader.readInt12());
        object.towerX = reader.readInt12();
        object.towerY = reader.readInt12();
    }
}
