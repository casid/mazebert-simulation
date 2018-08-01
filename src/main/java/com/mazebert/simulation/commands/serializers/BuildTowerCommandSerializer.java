package com.mazebert.simulation.commands.serializers;

import com.mazebert.simulation.commands.BuildTowerCommand;
import org.jusecase.bitpack.BitReader;
import org.jusecase.bitpack.BitSerializer;
import org.jusecase.bitpack.BitWriter;

public strictfp class BuildTowerCommandSerializer implements BitSerializer<BuildTowerCommand> {

    @Override
    public BuildTowerCommand createObject() {
        return new BuildTowerCommand();
    }

    @Override
    public void serialize(BitWriter writer, BuildTowerCommand object) {
        writer.writeStringNonNull(object.tower);
    }

    @Override
    public void deserialize(BitReader reader, BuildTowerCommand object) {
        object.tower = reader.readStringNonNull();
    }
}
