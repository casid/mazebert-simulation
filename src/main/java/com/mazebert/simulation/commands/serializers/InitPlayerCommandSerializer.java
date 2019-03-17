package com.mazebert.simulation.commands.serializers;

import com.mazebert.simulation.commands.InitPlayerCommand;
import com.mazebert.simulation.units.quests.QuestData;
import com.mazebert.simulation.units.wizards.WizardPower;
import org.jusecase.bitpack.BitReader;
import org.jusecase.bitpack.BitSerializer;
import org.jusecase.bitpack.BitWriter;

public strictfp class InitPlayerCommandSerializer implements BitSerializer<InitPlayerCommand> {

    private static final int PLAYER_NAME_BITS = 6;
    private static final int WIZARD_POWER_BITS = EnumSerializer.WIZARD_POWER_BITS + 1;
    private static final int QUEST_BITS = EnumSerializer.QUEST_BITS + 1;

    @Override
    public InitPlayerCommand createObject() {
        return new InitPlayerCommand();
    }

    @Override
    public void serialize(BitWriter writer, InitPlayerCommand object) {
        writer.writeLong(object.ladderPlayerId);
        writer.writeStringNullable(PLAYER_NAME_BITS, object.playerName);
        writer.writeLong(object.experience);

        EnumSerializer.writeHeroType(writer, object.heroType);

        EnumSerializer.writeHeroTypes(writer, object.foilHeroes);
        EnumSerializer.writeTowerTypes(writer, object.foilTowers);
        EnumSerializer.writeItemTypes(writer, object.foilItems);
        EnumSerializer.writePotionTypes(writer, object.foilPotions);

        writer.writeObjectsWithSameType(WIZARD_POWER_BITS, object.powers);

        EnumSerializer.writeElements(writer, object.elements);

        writer.writeObjectsWithSameType(QUEST_BITS, object.quests);
    }

    @Override
    public void deserialize(BitReader reader, InitPlayerCommand object) {
        object.ladderPlayerId = reader.readLong();
        object.playerName = reader.readStringNullable(PLAYER_NAME_BITS);
        object.experience = reader.readLong();

        object.heroType = EnumSerializer.readHeroType(reader);

        object.foilHeroes = EnumSerializer.readHeroTypes(reader);
        object.foilTowers = EnumSerializer.readTowerTypes(reader);
        object.foilItems = EnumSerializer.readItemTypes(reader);
        object.foilPotions = EnumSerializer.readPotionTypes(reader);

        object.powers = reader.readObjectsWithSameTypeAsList(WIZARD_POWER_BITS, WizardPower.class);

        object.elements = EnumSerializer.readElements(reader);

        object.quests = reader.readObjectsWithSameTypeAsList(QUEST_BITS, QuestData.class);
    }
}
