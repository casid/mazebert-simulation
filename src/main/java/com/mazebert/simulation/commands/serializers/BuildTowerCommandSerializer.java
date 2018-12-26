package com.mazebert.simulation.commands.serializers;

import com.mazebert.simulation.commands.BuildTowerCommand;
import com.mazebert.simulation.units.towers.TowerType;
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
        EnumSerializer.writeTowerType(writer, object.towerType);
        writer.writeInt8(object.x);
        writer.writeInt8(object.y);
    }

    @Override
    public void deserialize(BitReader reader, BuildTowerCommand object) {
        object.towerType = EnumSerializer.readTowerType(reader);
        object.x = reader.readInt8();
        object.y = reader.readInt8();
    }
}
