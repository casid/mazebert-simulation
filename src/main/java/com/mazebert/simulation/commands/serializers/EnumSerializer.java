package com.mazebert.simulation.commands.serializers;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.CardType;
import com.mazebert.simulation.units.abilities.ActiveAbilityType;
import com.mazebert.simulation.units.heroes.HeroType;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.potions.PotionType;
import com.mazebert.simulation.units.towers.TowerType;
import org.jusecase.bitpack.BitReader;
import org.jusecase.bitpack.BitWriter;

public strictfp class EnumSerializer {
    public static TowerType readTowerType(BitReader reader) {
        return TowerType.forId(reader.readUnsignedInt6());
    }

    public static void writeTowerType(BitWriter writer, TowerType type) {
        writer.writeUnsignedInt6(getCardTypeId(type));
    }

    public static PotionType readPotionType(BitReader reader) {
        return PotionType.forId(reader.readUnsignedInt5());
    }

    public static void writePotionType(BitWriter writer, PotionType type) {
        writer.writeUnsignedInt5(getCardTypeId(type));
    }

    public static ItemType readItemType(BitReader reader) {
        return ItemType.forId(reader.readUnsignedInt7());
    }

    public static void writeItemType(BitWriter writer, ItemType type) {
        writer.writeUnsignedInt7(getCardTypeId(type));
    }

    public static HeroType readHeroType(BitReader reader) {
        return HeroType.forId(reader.readUnsignedInt4());
    }

    public static void writeHeroType(BitWriter writer, HeroType type) {
        writer.writeUnsignedInt4(getCardTypeId(type));
    }

    public static ActiveAbilityType readActiveAbilityType(BitReader reader) {
        return ActiveAbilityType.forId(reader.readUnsignedInt2());
    }

    public static void writeActiveAbilityType(BitWriter writer, ActiveAbilityType type) {
        writer.writeUnsignedInt2(type.id);
    }

    public static CardCategory readTowerPotionOrItemCardCategory(BitReader reader) {
        return CardCategory.forId(reader.readUnsignedInt2());
    }

    public static void writeTowerPotionOrItemCardCategory(BitWriter writer, CardCategory category) {
        writer.writeUnsignedInt2(category.id);
    }

    public static CardType readCardType(BitReader reader, CardCategory category) {
        if (category == CardCategory.Tower) {
            return readTowerType(reader);
        }

        if (category == CardCategory.Potion) {
            return readPotionType(reader);
        }

        if (category == CardCategory.Item) {
            return readItemType(reader);
        }

        return null;
    }

    public static void writeCardType(BitWriter writer, CardCategory category, CardType type) {
        if (category == CardCategory.Tower) {
            writeTowerType(writer, (TowerType) type);
        }

        if (category == CardCategory.Potion) {
            writePotionType(writer, (PotionType) type);
        }

        if (category == CardCategory.Item) {
            writeItemType(writer, (ItemType) type);
        }
    }

    private static int getCardTypeId(CardType type) {
        if (type == null) {
            return 0;
        } else {
            return type.id();
        }
    }
}
