package com.mazebert.simulation.commands.serializers;

import com.mazebert.simulation.*;
import com.mazebert.simulation.maps.MapType;
import com.mazebert.simulation.units.abilities.ActiveAbilityType;
import com.mazebert.simulation.units.heroes.HeroType;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.potions.PotionType;
import com.mazebert.simulation.units.quests.QuestType;
import com.mazebert.simulation.units.towers.TowerType;
import com.mazebert.simulation.units.wizards.WizardPowerType;
import org.jusecase.bitpack.BitReader;
import org.jusecase.bitpack.BitWriter;

import java.util.EnumSet;

public strictfp class EnumSerializer {
    public static final int TOWER_BITS = 6;
    public static final int POTION_BITS = 5;
    public static final int ITEM_BITS = 7;
    public static final int HERO_BITS = 4;
    public static final int ELEMENT_BITS = 3;
    public static final int DIFFICULTY_BITS = 3;
    public static final int MAP_BITS = 3;
    public static final int ACTIVE_ABILITY_BITS = 3;

    public static TowerType readTowerType(BitReader reader) {
        return TowerType.forId(reader.readUnsignedInt(TOWER_BITS));
    }

    public static void writeTowerType(BitWriter writer, TowerType type) {
        writer.writeUnsignedInt(TOWER_BITS, getCardTypeId(type));
    }

    public static PotionType readPotionType(BitReader reader) {
        return PotionType.forId(reader.readUnsignedInt(POTION_BITS));
    }

    public static void writePotionType(BitWriter writer, PotionType type) {
        writer.writeUnsignedInt(POTION_BITS, getCardTypeId(type));
    }

    public static ItemType readItemType(BitReader reader) {
        return ItemType.forId(reader.readUnsignedInt(ITEM_BITS));
    }

    public static void writeItemType(BitWriter writer, ItemType type) {
        writer.writeUnsignedInt(ITEM_BITS, getCardTypeId(type));
    }

    public static HeroType readHeroType(BitReader reader) {
        return HeroType.forId(reader.readUnsignedInt(HERO_BITS));
    }

    public static void writeHeroType(BitWriter writer, HeroType type) {
        writer.writeUnsignedInt(HERO_BITS, getCardTypeId(type));
    }

    public static Element readElement(BitReader reader) {
        return Element.forId(reader.readUnsignedInt(ELEMENT_BITS));
    }

    public static void writeElement(BitWriter writer, Element element) {
        writer.writeUnsignedInt(ELEMENT_BITS, element.id);
    }

    public static Difficulty readDifficulty(BitReader reader) {
        return Difficulty.forId(reader.readUnsignedInt(DIFFICULTY_BITS));
    }

    public static void writeDifficulty(BitWriter writer, Difficulty difficulty) {
        writer.writeUnsignedInt(DIFFICULTY_BITS, difficulty.id);
    }

    public static MapType readMapType(BitReader reader) {
        return MapType.forId(reader.readUnsignedInt(MAP_BITS));
    }

    public static void writeMapType(BitWriter writer, MapType difficulty) {
        writer.writeUnsignedInt(MAP_BITS, difficulty.id);
    }

    public static ActiveAbilityType readActiveAbilityType(BitReader reader) {
        return ActiveAbilityType.forId(reader.readUnsignedInt(ACTIVE_ABILITY_BITS));
    }

    public static void writeActiveAbilityType(BitWriter writer, ActiveAbilityType type) {
        writer.writeUnsignedInt(ACTIVE_ABILITY_BITS, type.id);
    }

    public static CardCategory readTowerPotionOrItemCardCategory(BitReader reader) {
        return CardCategory.forId(reader.readUnsignedInt2());
    }

    public static void writeTowerPotionOrItemCardCategory(BitWriter writer, CardCategory category) {
        writer.writeUnsignedInt2(category.id);
    }

    public static WizardPowerType readWizardPowerType(BitReader reader) {
        return WizardPowerType.forId(reader.readUnsignedInt(getWizardPowerBits()));
    }

    public static void writeWizardPowerType(BitWriter writer, WizardPowerType type) {
        writer.writeUnsignedInt(getWizardPowerBits(), type.id);
    }

    public static QuestType readQuestType(BitReader reader) {
        return QuestType.forId(reader.readUnsignedInt(getQuestBits()));
    }

    public static void writeQuestType(BitWriter writer, QuestType type) {
        writer.writeUnsignedInt(getQuestBits(), type.id);
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

    public static EnumSet<TowerType> readTowerTypes(BitReader reader) {
        EnumSet<TowerType> result = EnumSet.noneOf(TowerType.class);
        int size = reader.readUnsignedInt(TOWER_BITS);
        for (int i = 0; i < size; ++i) {
            result.add(readTowerType(reader));
        }
        return result;
    }

    public static void writeTowerTypes(BitWriter writer, EnumSet<TowerType> types) {
        if (types == null) {
            writer.writeUnsignedInt(TOWER_BITS, 0);
        } else {
            writer.writeUnsignedInt(TOWER_BITS, types.size());
            for (TowerType type : types) {
                writeTowerType(writer, type);
            }
        }
    }

    public static EnumSet<PotionType> readPotionTypes(BitReader reader) {
        EnumSet<PotionType> result = EnumSet.noneOf(PotionType.class);
        int size = reader.readUnsignedInt(POTION_BITS);
        for (int i = 0; i < size; ++i) {
            result.add(readPotionType(reader));
        }
        return result;
    }

    public static void writePotionTypes(BitWriter writer, EnumSet<PotionType> types) {
        if (types == null) {
            writer.writeUnsignedInt(POTION_BITS, 0);
        } else {
            writer.writeUnsignedInt(POTION_BITS, types.size());
            for (PotionType type : types) {
                writePotionType(writer, type);
            }
        }
    }

    public static EnumSet<ItemType> readItemTypes(BitReader reader) {
        EnumSet<ItemType> result = EnumSet.noneOf(ItemType.class);
        int size = reader.readUnsignedInt(ITEM_BITS);
        for (int i = 0; i < size; ++i) {
            result.add(readItemType(reader));
        }
        return result;
    }

    public static void writeItemTypes(BitWriter writer, EnumSet<ItemType> types) {
        if (types == null) {
            writer.writeUnsignedInt(ITEM_BITS, 0);
        } else {
            writer.writeUnsignedInt(ITEM_BITS, types.size());
            for (ItemType type : types) {
                writeItemType(writer, type);
            }
        }
    }

    public static EnumSet<HeroType> readHeroTypes(BitReader reader) {
        EnumSet<HeroType> result = EnumSet.noneOf(HeroType.class);
        int size = reader.readUnsignedInt(HERO_BITS);
        for (int i = 0; i < size; ++i) {
            result.add(readHeroType(reader));
        }
        return result;
    }

    public static void writeHeroTypes(BitWriter writer, EnumSet<HeroType> types) {
        if (types == null) {
            writer.writeUnsignedInt(HERO_BITS, 0);
        } else {
            writer.writeUnsignedInt(HERO_BITS, types.size());
            for (HeroType type : types) {
                writeHeroType(writer, type);
            }
        }
    }

    public static EnumSet<Element> readElements(BitReader reader) {
        EnumSet<Element> result = EnumSet.noneOf(Element.class);
        int size = reader.readUnsignedInt(ELEMENT_BITS);
        for (int i = 0; i < size; ++i) {
            result.add(readElement(reader));
        }
        return result;
    }

    public static void writeElements(BitWriter writer, EnumSet<Element> elements) {
        if (elements == null) {
            writer.writeUnsignedInt(ELEMENT_BITS, 0);
        } else {
            writer.writeUnsignedInt(ELEMENT_BITS, elements.size());
            for (Element element : elements) {
                writeElement(writer, element);
            }
        }
    }

    private static int getCardTypeId(CardType type) {
        if (type == null) {
            return 0;
        } else {
            return type.id();
        }
    }

    public static int getWizardPowerBits() {
        if (Sim.context().version > Sim.v11) {
            return 5;
        } else {
            return 4;
        }
    }

    public static int getQuestBits() {
        if (Sim.context().version >= Sim.vDoL) {
            return 6;
        } else {
            return 5;
        }
    }
}
