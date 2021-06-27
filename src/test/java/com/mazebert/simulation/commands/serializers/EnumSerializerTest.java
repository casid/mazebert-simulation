package com.mazebert.simulation.commands.serializers;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.Difficulty;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.maps.MapType;
import com.mazebert.simulation.units.abilities.ActiveAbilityType;
import com.mazebert.simulation.units.heroes.HeroType;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.potions.PotionType;
import com.mazebert.simulation.units.quests.QuestType;
import com.mazebert.simulation.units.towers.TowerType;
import com.mazebert.simulation.units.wizards.WizardPowerType;
import org.junit.jupiter.api.Test;
import org.jusecase.bitpack.AbstractBitProtocol;
import org.jusecase.bitpack.BitProtocol;
import org.jusecase.bitpack.buffer.BufferBitReader;
import org.jusecase.bitpack.buffer.BufferBitWriter;

import java.nio.ByteBuffer;
import java.util.EnumSet;

import static org.assertj.core.api.Assertions.assertThat;

public class EnumSerializerTest {
    BitProtocol protocol = new AbstractBitProtocol();
    BufferBitWriter writer = new BufferBitWriter(protocol, ByteBuffer.allocateDirect(256));
    BufferBitReader reader;

    EnumSerializer enumSerializer = new EnumSerializer(Sim.version);

    @Test
    void towers() {
        for (TowerType value : TowerType.values()) {
            enumSerializer.writeTowerType(writer, value);
        }

        whenBufferIsFlushedAndRead();

        for (TowerType value : TowerType.values()) {
            assertThat(enumSerializer.readTowerType(reader)).isEqualTo(value);
        }
    }

    @Test
    void potions() {
        for (PotionType value : PotionType.values()) {
            enumSerializer.writePotionType(writer, value);
        }

        whenBufferIsFlushedAndRead();

        for (PotionType value : PotionType.values()) {
            assertThat(enumSerializer.readPotionType(reader)).isEqualTo(value);
        }
    }

    @Test
    void items() {
        for (ItemType value : ItemType.values()) {
            enumSerializer.writeItemType(writer, value);
        }

        whenBufferIsFlushedAndRead();

        for (ItemType value : ItemType.values()) {
            assertThat(enumSerializer.readItemType(reader)).isEqualTo(value);
        }
    }

    @Test
    void heroes() {
        for (HeroType value : HeroType.values()) {
            enumSerializer.writeHeroType(writer, value);
        }

        whenBufferIsFlushedAndRead();

        for (HeroType value : HeroType.values()) {
            assertThat(enumSerializer.readHeroType(reader)).isEqualTo(value);
        }
    }

    @Test
    void difficulty() {
        for (Difficulty value : Difficulty.values()) {
            enumSerializer.writeDifficulty(writer, value);
        }

        whenBufferIsFlushedAndRead();

        for (Difficulty value : Difficulty.values()) {
            assertThat(enumSerializer.readDifficulty(reader)).isEqualTo(value);
        }
    }

    @Test
    void map() {
        for (MapType value : MapType.values()) {
            enumSerializer.writeMapType(writer, value);
        }

        whenBufferIsFlushedAndRead();

        for (MapType value : MapType.values()) {
            assertThat(enumSerializer.readMapType(reader)).isEqualTo(value);
        }
    }

    @Test
    void activeAbility() {
        for (ActiveAbilityType value : ActiveAbilityType.values()) {
            enumSerializer.writeActiveAbilityType(writer, value);
        }

        whenBufferIsFlushedAndRead();

        for (ActiveAbilityType value : ActiveAbilityType.values()) {
            assertThat(enumSerializer.readActiveAbilityType(reader)).isEqualTo(value);
        }
    }

    @Test
    void cardCategory() {
        enumSerializer.writeTowerPotionOrItemCardCategory(writer, CardCategory.Tower);
        enumSerializer.writeTowerPotionOrItemCardCategory(writer, CardCategory.Potion);
        enumSerializer.writeTowerPotionOrItemCardCategory(writer, CardCategory.Item);


        whenBufferIsFlushedAndRead();

        assertThat(enumSerializer.readTowerPotionOrItemCardCategory(reader)).isEqualTo(CardCategory.Tower);
        assertThat(enumSerializer.readTowerPotionOrItemCardCategory(reader)).isEqualTo(CardCategory.Potion);
        assertThat(enumSerializer.readTowerPotionOrItemCardCategory(reader)).isEqualTo(CardCategory.Item);
    }

    @Test
    void wizardPowers() {
        for (WizardPowerType value : WizardPowerType.values()) {
            enumSerializer.writeWizardPowerType(writer, value);
        }

        whenBufferIsFlushedAndRead();

        for (WizardPowerType value : WizardPowerType.values()) {
            assertThat(enumSerializer.readWizardPowerType(reader)).isEqualTo(value);
        }
    }

    @Test
    void quests() {
        for (QuestType value : QuestType.values()) {
            enumSerializer.writeQuestType(writer, value);
        }

        whenBufferIsFlushedAndRead();

        for (QuestType value : QuestType.values()) {
            assertThat(enumSerializer.readQuestType(reader)).isEqualTo(value);
        }
    }

    @Test
    void towerSet_all() {
        EnumSet<TowerType> all = EnumSet.allOf(TowerType.class);
        enumSerializer.writeTowerTypes(writer, all);

        whenBufferIsFlushedAndRead();

        assertThat(enumSerializer.readTowerTypes(reader)).containsExactlyElementsOf(all);
    }

    @Test
    void towerSet_none() {
        EnumSet<TowerType> none = EnumSet.noneOf(TowerType.class);
        enumSerializer.writeTowerTypes(writer, none);

        whenBufferIsFlushedAndRead();

        assertThat(enumSerializer.readTowerTypes(reader)).containsExactlyElementsOf(none);
    }

    @Test
    void potionSet_all() {
        EnumSet<PotionType> all = EnumSet.allOf(PotionType.class);
        enumSerializer.writePotionTypes(writer, all);

        whenBufferIsFlushedAndRead();

        assertThat(enumSerializer.readPotionTypes(reader)).containsExactlyElementsOf(all);
    }

    @Test
    void potionSet_none() {
        EnumSet<PotionType> none = EnumSet.noneOf(PotionType.class);
        enumSerializer.writePotionTypes(writer, none);

        whenBufferIsFlushedAndRead();

        assertThat(enumSerializer.readPotionTypes(reader)).containsExactlyElementsOf(none);
    }

    @Test
    void itemSet_all() {
        EnumSet<ItemType> all = EnumSet.allOf(ItemType.class);
        enumSerializer.writeItemTypes(writer, all);

        whenBufferIsFlushedAndRead();

        assertThat(enumSerializer.readItemTypes(reader)).containsExactlyElementsOf(all);
    }

    @Test
    void itemSet_none() {
        EnumSet<ItemType> none = EnumSet.noneOf(ItemType.class);
        enumSerializer.writeItemTypes(writer, none);

        whenBufferIsFlushedAndRead();

        assertThat(enumSerializer.readItemTypes(reader)).containsExactlyElementsOf(none);
    }

    @Test
    void heroSet_all() {
        EnumSet<HeroType> all = EnumSet.allOf(HeroType.class);
        enumSerializer.writeHeroTypes(writer, all);

        whenBufferIsFlushedAndRead();

        assertThat(enumSerializer.readHeroTypes(reader)).containsExactlyElementsOf(all);
    }

    @Test
    void heroSet_none() {
        EnumSet<HeroType> none = EnumSet.noneOf(HeroType.class);
        enumSerializer.writeHeroTypes(writer, none);

        whenBufferIsFlushedAndRead();

        assertThat(enumSerializer.readHeroTypes(reader)).containsExactlyElementsOf(none);
    }

    @Test
    void elementSet_all() {
        EnumSet<Element> all = EnumSet.allOf(Element.class);
        enumSerializer.writeElements(writer, all);

        whenBufferIsFlushedAndRead();

        assertThat(enumSerializer.readElements(reader)).containsExactlyElementsOf(all);
    }

    @Test
    void elementSet_none() {
        EnumSet<Element> none = EnumSet.noneOf(Element.class);
        enumSerializer.writeElements(writer, none);

        whenBufferIsFlushedAndRead();

        assertThat(enumSerializer.readElements(reader)).containsExactlyElementsOf(none);
    }

    private void whenBufferIsFlushedAndRead() {
        writer.flush();
        writer.getBuffer().rewind();
        reader = new BufferBitReader(protocol, writer.getBuffer());
    }
}