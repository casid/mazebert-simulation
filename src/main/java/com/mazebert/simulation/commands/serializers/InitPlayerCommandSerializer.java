package com.mazebert.simulation.commands.serializers;

import com.mazebert.simulation.commands.InitPlayerCommand;
import com.mazebert.simulation.units.wizards.WizardPower;
import org.jusecase.bitpack.BitReader;
import org.jusecase.bitpack.BitSerializer;
import org.jusecase.bitpack.BitWriter;

public strictfp class InitPlayerCommandSerializer implements BitSerializer<InitPlayerCommand> {

    @Override
    public InitPlayerCommand createObject() {
        return new InitPlayerCommand();
    }

    @Override
    public void serialize(BitWriter writer, InitPlayerCommand object) {
        writer.writeLong(object.ladderPlayerId);
        writer.writeStringNullable(object.playerName);
        writer.writeLong(object.experience);

        EnumSerializer.writeHeroType(writer, object.heroType);

        EnumSerializer.writeHeroTypes(writer, object.foilHeroes);
        EnumSerializer.writeTowerTypes(writer, object.foilTowers);
        EnumSerializer.writeItemTypes(writer, object.foilItems);
        EnumSerializer.writePotionTypes(writer, object.foilPotions);

        writer.writeObjectsWithSameType(object.powers);

        EnumSerializer.writeElements(writer, object.elements);
    }

    @Override
    public void deserialize(BitReader reader, InitPlayerCommand object) {
        object.ladderPlayerId = reader.readLong();
        object.playerName = reader.readStringNullable();
        object.experience = reader.readLong();

        object.heroType = EnumSerializer.readHeroType(reader);

        object.foilHeroes = EnumSerializer.readHeroTypes(reader);
        object.foilTowers = EnumSerializer.readTowerTypes(reader);
        object.foilItems = EnumSerializer.readItemTypes(reader);
        object.foilPotions = EnumSerializer.readPotionTypes(reader);

        object.powers = reader.readObjectsWithSameTypeAsList(WizardPower.class);

        object.elements = EnumSerializer.readElements(reader);
    }
}
