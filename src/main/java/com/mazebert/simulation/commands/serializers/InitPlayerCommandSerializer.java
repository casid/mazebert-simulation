package com.mazebert.simulation.commands.serializers;

import com.mazebert.simulation.commands.InitPlayerCommand;
import com.mazebert.simulation.units.quests.QuestData;
import com.mazebert.simulation.units.wizards.WizardPower;
import org.jusecase.bitpack.BitReader;
import org.jusecase.bitpack.BitSerializer;
import org.jusecase.bitpack.BitWriter;

public strictfp class InitPlayerCommandSerializer implements BitSerializer<InitPlayerCommand> {

    private static final int PLAYER_NAME_BITS = 6;

    private final EnumSerializer enumSerializer;

    public InitPlayerCommandSerializer(EnumSerializer enumSerializer) {
        this.enumSerializer = enumSerializer;
    }

    @Override
    public InitPlayerCommand createObject() {
        return new InitPlayerCommand();
    }

    @Override
    public void serialize(BitWriter writer, InitPlayerCommand object) {
        writer.writeLong(object.ladderPlayerId);
        writer.writeStringNullable(PLAYER_NAME_BITS, object.playerName);
        writer.writeLong(object.experience);

        enumSerializer.writeHeroType(writer, object.heroType);

        enumSerializer.writeHeroTypes(writer, object.foilHeroes);
        enumSerializer.writeTowerTypes(writer, object.foilTowers);
        enumSerializer.writeItemTypes(writer, object.foilItems);
        enumSerializer.writePotionTypes(writer, object.foilPotions);

        writer.writeObjectsWithSameType(enumSerializer.getWizardPowerBits() + 1, object.powers);

        enumSerializer.writeElements(writer, object.elements);

        writer.writeObjectsWithSameType(getQuestBits(), object.quests);
    }

    @Override
    public void deserialize(BitReader reader, InitPlayerCommand object) {
        object.ladderPlayerId = reader.readLong();
        object.playerName = reader.readStringNullable(PLAYER_NAME_BITS);
        object.experience = reader.readLong();

        object.heroType = enumSerializer.readHeroType(reader);

        object.foilHeroes = enumSerializer.readHeroTypes(reader);
        object.foilTowers = enumSerializer.readTowerTypes(reader);
        object.foilItems = enumSerializer.readItemTypes(reader);
        object.foilPotions = enumSerializer.readPotionTypes(reader);

        object.powers = reader.readObjectsWithSameTypeAsList(enumSerializer.getWizardPowerBits() + 1, WizardPower.class);

        object.elements = enumSerializer.readElements(reader);

        object.quests = reader.readObjectsWithSameTypeAsList(getQuestBits(), QuestData.class);
    }

    private int getQuestBits() {
        return enumSerializer.getQuestBits() + 1;
    }
}
