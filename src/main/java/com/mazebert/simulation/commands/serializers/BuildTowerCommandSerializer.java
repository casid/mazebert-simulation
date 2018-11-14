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
        writer.writeInt12(object.towerType.id);
        writer.writeInt12(object.x);
        writer.writeInt12(object.y);
    }

    @Override
    public void deserialize(BitReader reader, BuildTowerCommand object) {
        object.towerType = TowerType.forId(reader.readInt12());
        object.x = reader.readInt12();
        object.y = reader.readInt12();
    }
}
