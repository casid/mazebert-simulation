package com.mazebert.simulation.commands.serializers;

import com.mazebert.simulation.units.wizards.DeckMasterPower;
import com.mazebert.simulation.units.wizards.WizardPower;
import com.mazebert.simulation.units.wizards.WizardPowerType;
import org.jusecase.bitpack.BitReader;
import org.jusecase.bitpack.BitSerializer;
import org.jusecase.bitpack.BitWriter;

public strictfp class WizardPowerSerializer implements BitSerializer<WizardPower> {

    private final EnumSerializer enumSerializer;

    public WizardPowerSerializer(EnumSerializer enumSerializer) {
        this.enumSerializer = enumSerializer;
    }

    @Override
    public void serialize(BitWriter writer, WizardPower object) {
        enumSerializer.writeWizardPowerType(writer, object.getType());
        writer.writeUnsignedInt5(object.getSkillLevel());
        if (object instanceof DeckMasterPower) {
            enumSerializer.writeTowerType(writer, ((DeckMasterPower)object).getSelectedTower());
        }
    }

    @Override
    public WizardPower deserialize(BitReader reader) {
        WizardPowerType type = enumSerializer.readWizardPowerType(reader);
        WizardPower power = type.create();
        power.setSkillLevel(reader.readUnsignedInt5());

        if (power instanceof DeckMasterPower) {
            ((DeckMasterPower)power).setSelectedTower(enumSerializer.readTowerType(reader));
        }

        return power;
    }

    // Unused methods below ...

    @Override
    public WizardPower createObject() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deserialize(BitReader reader, WizardPower object) {
        throw new UnsupportedOperationException();
    }
}
