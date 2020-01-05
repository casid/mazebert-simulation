package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.listeners.OnTowerReplacedListener;
import com.mazebert.simulation.stash.ItemStash;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.DamageAbility;
import com.mazebert.simulation.units.abilities.RandomGenderAbility;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.items.SnowGlobe;

import java.util.ArrayList;
import java.util.List;

public strictfp class SnowGlobeCreateItem extends Ability<Tower> implements OnTowerReplacedListener {
    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onTowerReplaced.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onTowerReplaced.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onTowerReplaced(Tower oldTower) {
        createItem(oldTower.getType());

        Sim.context().unitGateway.destroyTower(getUnit());
    }

    private void createItem(TowerType towerType) {
        ItemStash itemStash = getUnit().getWizard().itemStash;
        itemStash.add(ItemType.SnowGlobe, true);

        SnowGlobe item = (SnowGlobe) itemStash.get(ItemType.SnowGlobe).getCard();

        Tower tower = towerType.create();

        List<Ability<?>> abilities = new ArrayList<>();
        tower.forEachAbility(a -> {
            if (isSupportedAbility(a)) {
                tower.removeAbility(a);
                abilities.add(a);
                a.setOrigin(item); // For UI
            }
        });

        item.setAbilities(abilities.toArray(new Ability[0]));
        item.setTowerType(towerType);

        tower.dispose();
    }

    private boolean isSupportedAbility(Ability<?> ability) {
        if (ability instanceof AttackAbility) {
            return false;
        }

        if (ability instanceof DamageAbility) {
            return false;
        }

        if (ability instanceof RandomGenderAbility) {
            return false;
        }

        return true;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Capture (Battlecry)";
    }

    @Override
    public String getDescription() {
        return "Replace a " + format.rarity(Rarity.Common) + " tower to remove it from the field. A " + format.card(ItemType.SnowGlobe) + " item containing the captured tower is added to your hand, granting all abilities of the replaced tower.";
    }

    @Override
    public String getIconFile() {
        return "05_magic_globe_512";
    }
}
