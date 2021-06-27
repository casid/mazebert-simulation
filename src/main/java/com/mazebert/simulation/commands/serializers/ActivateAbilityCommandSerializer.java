package com.mazebert.simulation.commands.serializers;

import com.mazebert.simulation.commands.ActivateAbilityCommand;
import org.jusecase.bitpack.BitReader;
import org.jusecase.bitpack.BitSerializer;
import org.jusecase.bitpack.BitWriter;

public strictfp class ActivateAbilityCommandSerializer implements BitSerializer<ActivateAbilityCommand> {

    private final EnumSerializer enumSerializer;

    public ActivateAbilityCommandSerializer(EnumSerializer enumSerializer) {
        this.enumSerializer = enumSerializer;
    }

    @Override
    public ActivateAbilityCommand createObject() {
        return new ActivateAbilityCommand();
    }

    @Override
    public void serialize(BitWriter writer, ActivateAbilityCommand object) {
        enumSerializer.writeActiveAbilityType(writer, object.abilityType);
        writer.writeInt8(object.towerX);
        writer.writeInt8(object.towerY);
    }

    @Override
    public void deserialize(BitReader reader, ActivateAbilityCommand object) {
        object.abilityType = enumSerializer.readActiveAbilityType(reader);
        object.towerX = reader.readInt8();
        object.towerY = reader.readInt8();
    }
}
