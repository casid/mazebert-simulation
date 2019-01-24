package com.mazebert.simulation.commands.serializers;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.units.abilities.ActiveAbilityType;
import com.mazebert.simulation.units.heroes.HeroType;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.potions.PotionType;
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

    @Test
    void towers() {
        for (TowerType value : TowerType.values()) {
            EnumSerializer.writeTowerType(writer, value);
        }

        whenBufferIsFlushedAndRead();

        for (TowerType value : TowerType.values()) {
            assertThat(EnumSerializer.readTowerType(reader)).isEqualTo(value);
        }
    }

    @Test
    void potions() {
        for (PotionType value : PotionType.values()) {
            EnumSerializer.writePotionType(writer, value);
        }

        whenBufferIsFlushedAndRead();

        for (PotionType value : PotionType.values()) {
            assertThat(EnumSerializer.readPotionType(reader)).isEqualTo(value);
        }
    }

    @Test
    void items() {
        for (ItemType value : ItemType.values()) {
            EnumSerializer.writeItemType(writer, value);
        }

        whenBufferIsFlushedAndRead();

        for (ItemType value : ItemType.values()) {
            assertThat(EnumSerializer.readItemType(reader)).isEqualTo(value);
        }
    }

    @Test
    void heroes() {
        for (HeroType value : HeroType.values()) {
            EnumSerializer.writeHeroType(writer, value);
        }

        whenBufferIsFlushedAndRead();

        for (HeroType value : HeroType.values()) {
            assertThat(EnumSerializer.readHeroType(reader)).isEqualTo(value);
        }
    }

    @Test
    void activeAbility() {
        for (ActiveAbilityType value : ActiveAbilityType.values()) {
            EnumSerializer.writeActiveAbilityType(writer, value);
        }

        whenBufferIsFlushedAndRead();

        for (ActiveAbilityType value : ActiveAbilityType.values()) {
            assertThat(EnumSerializer.readActiveAbilityType(reader)).isEqualTo(value);
        }
    }

    @Test
    void cardCategory() {
        EnumSerializer.writeTowerPotionOrItemCardCategory(writer, CardCategory.Tower);
        EnumSerializer.writeTowerPotionOrItemCardCategory(writer, CardCategory.Potion);
        EnumSerializer.writeTowerPotionOrItemCardCategory(writer, CardCategory.Item);


        whenBufferIsFlushedAndRead();

        assertThat(EnumSerializer.readTowerPotionOrItemCardCategory(reader)).isEqualTo(CardCategory.Tower);
        assertThat(EnumSerializer.readTowerPotionOrItemCardCategory(reader)).isEqualTo(CardCategory.Potion);
        assertThat(EnumSerializer.readTowerPotionOrItemCardCategory(reader)).isEqualTo(CardCategory.Item);
    }

    @Test
    void wizardPowers() {
        for (WizardPowerType value : WizardPowerType.values()) {
            EnumSerializer.writeWizardPowerType(writer, value);
        }

        whenBufferIsFlushedAndRead();

        for (WizardPowerType value : WizardPowerType.values()) {
            assertThat(EnumSerializer.readWizardPowerType(reader)).isEqualTo(value);
        }
    }

    @Test
    void towerSet_all() {
        EnumSet<TowerType> all = EnumSet.allOf(TowerType.class);
        EnumSerializer.writeTowerTypes(writer, all);

        whenBufferIsFlushedAndRead();

        assertThat(EnumSerializer.readTowerTypes(reader)).containsExactlyElementsOf(all);
    }

    @Test
    void towerSet_none() {
        EnumSet<TowerType> none = EnumSet.noneOf(TowerType.class);
        EnumSerializer.writeTowerTypes(writer, none);

        whenBufferIsFlushedAndRead();

        assertThat(EnumSerializer.readTowerTypes(reader)).containsExactlyElementsOf(none);
    }

    @Test
    void potionSet_all() {
        EnumSet<PotionType> all = EnumSet.allOf(PotionType.class);
        EnumSerializer.writePotionTypes(writer, all);

        whenBufferIsFlushedAndRead();

        assertThat(EnumSerializer.readPotionTypes(reader)).containsExactlyElementsOf(all);
    }

    @Test
    void potionSet_none() {
        EnumSet<PotionType> none = EnumSet.noneOf(PotionType.class);
        EnumSerializer.writePotionTypes(writer, none);

        whenBufferIsFlushedAndRead();

        assertThat(EnumSerializer.readPotionTypes(reader)).containsExactlyElementsOf(none);
    }

    @Test
    void itemSet_all() {
        EnumSet<ItemType> all = EnumSet.allOf(ItemType.class);
        EnumSerializer.writeItemTypes(writer, all);

        whenBufferIsFlushedAndRead();

        assertThat(EnumSerializer.readItemTypes(reader)).containsExactlyElementsOf(all);
    }

    @Test
    void itemSet_none() {
        EnumSet<ItemType> none = EnumSet.noneOf(ItemType.class);
        EnumSerializer.writeItemTypes(writer, none);

        whenBufferIsFlushedAndRead();

        assertThat(EnumSerializer.readItemTypes(reader)).containsExactlyElementsOf(none);
    }

    @Test
    void heroSet_all() {
        EnumSet<HeroType> all = EnumSet.allOf(HeroType.class);
        EnumSerializer.writeHeroTypes(writer, all);

        whenBufferIsFlushedAndRead();

        assertThat(EnumSerializer.readHeroTypes(reader)).containsExactlyElementsOf(all);
    }

    @Test
    void heroSet_none() {
        EnumSet<HeroType> none = EnumSet.noneOf(HeroType.class);
        EnumSerializer.writeHeroTypes(writer, none);

        whenBufferIsFlushedAndRead();

        assertThat(EnumSerializer.readHeroTypes(reader)).containsExactlyElementsOf(none);
    }

    private void whenBufferIsFlushedAndRead() {
        writer.flush();
        writer.getBuffer().rewind();
        reader = new BufferBitReader(protocol, writer.getBuffer());
    }
}