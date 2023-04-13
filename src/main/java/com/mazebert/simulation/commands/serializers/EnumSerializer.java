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
    private static final int ITEM_BITS = 7;
    private static final int ELEMENT_BITS = 3;
    private static final int DIFFICULTY_BITS = 3;
    private static final int MAP_BITS = 3;

    private final int version;

    public EnumSerializer(int version) {
        this.version = version;
    }

    public TowerType readTowerType(BitReader reader) {
        return TowerType.forId(reader.readUnsignedInt(getTowerBits()));
    }

    public void writeTowerType(BitWriter writer, TowerType type) {
        writer.writeUnsignedInt(getTowerBits(), getCardTypeId(type));
    }

    public PotionType readPotionType(BitReader reader) {
        return PotionType.forId(reader.readUnsignedInt(getPotionBits()));
    }

    public void writePotionType(BitWriter writer, PotionType type) {
        writer.writeUnsignedInt(getPotionBits(), getCardTypeId(type));
    }

    public ItemType readItemType(BitReader reader) {
        return ItemType.forId(reader.readUnsignedInt(ITEM_BITS));
    }

    public void writeItemType(BitWriter writer, ItemType type) {
        writer.writeUnsignedInt(ITEM_BITS, getCardTypeId(type));
    }

    public HeroType readHeroType(BitReader reader) {
        return HeroType.forId(reader.readUnsignedInt(getHeroBits()));
    }

    public void writeHeroType(BitWriter writer, HeroType type) {
        writer.writeUnsignedInt(getHeroBits(), getCardTypeId(type));
    }

    public Element readElement(BitReader reader) {
        return Element.forId(reader.readUnsignedInt(ELEMENT_BITS));
    }

    public void writeElement(BitWriter writer, Element element) {
        writer.writeUnsignedInt(ELEMENT_BITS, element.id);
    }

    public Difficulty readDifficulty(BitReader reader) {
        return Difficulty.forId(reader.readUnsignedInt(DIFFICULTY_BITS));
    }

    public void writeDifficulty(BitWriter writer, Difficulty difficulty) {
        writer.writeUnsignedInt(DIFFICULTY_BITS, difficulty.id);
    }

    public MapType readMapType(BitReader reader) {
        return MapType.forId(reader.readUnsignedInt(MAP_BITS));
    }

    public void writeMapType(BitWriter writer, MapType difficulty) {
        writer.writeUnsignedInt(MAP_BITS, difficulty.id);
    }

    public ActiveAbilityType readActiveAbilityType(BitReader reader) {
        return ActiveAbilityType.forId(reader.readUnsignedInt(getActiveAbilityBits()));
    }

    public void writeActiveAbilityType(BitWriter writer, ActiveAbilityType type) {
        writer.writeUnsignedInt(getActiveAbilityBits(), type.id);
    }

    public CardCategory readTowerPotionOrItemCardCategory(BitReader reader) {
        return CardCategory.forId(reader.readUnsignedInt2());
    }

    public void writeTowerPotionOrItemCardCategory(BitWriter writer, CardCategory category) {
        writer.writeUnsignedInt2(category.id);
    }

    public WizardPowerType readWizardPowerType(BitReader reader) {
        return WizardPowerType.forId(reader.readUnsignedInt(getWizardPowerBits()));
    }

    public void writeWizardPowerType(BitWriter writer, WizardPowerType type) {
        writer.writeUnsignedInt(getWizardPowerBits(), type.id);
    }

    public QuestType readQuestType(BitReader reader) {
        return QuestType.forId(reader.readUnsignedInt(getQuestBits()));
    }

    public void writeQuestType(BitWriter writer, QuestType type) {
        writer.writeUnsignedInt(getQuestBits(), type.id);
    }

    public CardType readCardType(BitReader reader, CardCategory category) {
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

    public void writeCardType(BitWriter writer, CardCategory category, CardType type) {
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

    public EnumSet<TowerType> readTowerTypes(BitReader reader) {
        EnumSet<TowerType> result = EnumSet.noneOf(TowerType.class);
        int size = reader.readUnsignedInt(getTowerBits());
        for (int i = 0; i < size; ++i) {
            result.add(readTowerType(reader));
        }
        return result;
    }

    public void writeTowerTypes(BitWriter writer, EnumSet<TowerType> types) {
        if (types == null) {
            writer.writeUnsignedInt(getTowerBits(), 0);
        } else {
            writer.writeUnsignedInt(getTowerBits(), types.size());
            for (TowerType type : types) {
                writeTowerType(writer, type);
            }
        }
    }

    public EnumSet<PotionType> readPotionTypes(BitReader reader) {
        EnumSet<PotionType> result = EnumSet.noneOf(PotionType.class);
        int size = reader.readUnsignedInt(getPotionBits());
        for (int i = 0; i < size; ++i) {
            result.add(readPotionType(reader));
        }
        return result;
    }

    public void writePotionTypes(BitWriter writer, EnumSet<PotionType> types) {
        if (types == null) {
            writer.writeUnsignedInt(getPotionBits(), 0);
        } else {
            writer.writeUnsignedInt(getPotionBits(), types.size());
            for (PotionType type : types) {
                writePotionType(writer, type);
            }
        }
    }

    public EnumSet<ItemType> readItemTypes(BitReader reader) {
        EnumSet<ItemType> result = EnumSet.noneOf(ItemType.class);
        int size = reader.readUnsignedInt(ITEM_BITS);
        for (int i = 0; i < size; ++i) {
            result.add(readItemType(reader));
        }
        return result;
    }

    public void writeItemTypes(BitWriter writer, EnumSet<ItemType> types) {
        if (types == null) {
            writer.writeUnsignedInt(ITEM_BITS, 0);
        } else {
            writer.writeUnsignedInt(ITEM_BITS, types.size());
            for (ItemType type : types) {
                writeItemType(writer, type);
            }
        }
    }

    public EnumSet<HeroType> readHeroTypes(BitReader reader) {
        EnumSet<HeroType> result = EnumSet.noneOf(HeroType.class);
        int size = reader.readUnsignedInt(getHeroBits());
        for (int i = 0; i < size; ++i) {
            result.add(readHeroType(reader));
        }
        return result;
    }

    public void writeHeroTypes(BitWriter writer, EnumSet<HeroType> types) {
        if (types == null) {
            writer.writeUnsignedInt(getHeroBits(), 0);
        } else {
            writer.writeUnsignedInt(getHeroBits(), types.size());
            for (HeroType type : types) {
                writeHeroType(writer, type);
            }
        }
    }

    public EnumSet<Element> readElements(BitReader reader) {
        EnumSet<Element> result = EnumSet.noneOf(Element.class);
        int size = reader.readUnsignedInt(ELEMENT_BITS);
        for (int i = 0; i < size; ++i) {
            result.add(readElement(reader));
        }
        return result;
    }

    public void writeElements(BitWriter writer, EnumSet<Element> elements) {
        if (elements == null) {
            writer.writeUnsignedInt(ELEMENT_BITS, 0);
        } else {
            writer.writeUnsignedInt(ELEMENT_BITS, elements.size());
            for (Element element : elements) {
                writeElement(writer, element);
            }
        }
    }

    private int getCardTypeId(CardType type) {
        if (type == null) {
            return 0;
        } else {
            return type.id();
        }
    }

    public int getWizardPowerBits() {
        if (version > Sim.v11) {
            return 5;
        } else {
            return 4;
        }
    }

    public int getQuestBits() {
        if (version >= Sim.vDoL) {
            return 6;
        } else {
            return 5;
        }
    }

    public int getHeroBits() {
        if (version >= Sim.vRoC) {
            return 5;
        } else {
            return 4;
        }
    }

    public int getTowerBits() {
        if (version >= Sim.vToC) {
            return 7;
        } else {
            return 6;
        }
    }

    public int getPotionBits() {
        if (version >= Sim.vRoCEnd) {
            return 6;
        } else {
            return 5;
        }
    }

    public int getActiveAbilityBits() {
        if (version >= Sim.vHalloween) {
            return 4;
        } else {
            return 3;
        }
    }
}
