package com.mazebert.simulation.units.items;

import com.mazebert.java8.Consumer;
import com.mazebert.simulation.Card;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.plugins.FormatPlugin;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.towers.Tower;

public strictfp abstract class Item implements Card {
    private Ability[] abilities;
    private boolean set;
    private final ItemType type;

    protected final FormatPlugin format = Sim.context().formatPlugin;

    public Item(Ability... abilities) {
        setAbilities(abilities);
        this.type = ItemType.forClass(getClass());
    }

    public void setAbilities(Ability... abilities) {
        this.abilities = abilities;
        this.set = containsAbility(ItemSetAbility.class);
    }

    public void onEquipped(Tower tower) {
        // Hook for item subclasses for cosmetic changes
    }

    public void onDropped(Tower tower) {
        // Hook for item subclasses for cosmetic changes
    }

    @Override
    public Rarity getDropRarity() {
        return getRarity();
    }

    @Override
    public boolean isDropable() {
        return true;
    }

    @Override
    public boolean isSet() {
        return set;
    }

    @Override
    public String getAuthor() {
        return "Andy";
    }

    public abstract String getIcon();

    @Override
    public void forEachAbility(Consumer<Ability> consumer) {
        for (Ability ability : abilities) {
            consumer.accept(ability);
        }
    }

    public boolean containsAbility(Class<? extends Ability> abilityClass) {
        return getAbility(abilityClass) != null;
    }

    @SuppressWarnings("unchecked")
    public <T extends Ability> T getAbility(Class<T> abilityClass) {
        for (Ability ability : abilities) {
            if (abilityClass.isAssignableFrom(ability.getClass())) {
                return (T)ability;
            }
        }
        return null;
    }

    @Override
    public ItemType getType() {
        return type;
    }

    public boolean isForbiddenToEquip(Tower tower) {
        return false;
    }

    public boolean isAllowedToReturnToInventory() {
        return true;
    }
}
