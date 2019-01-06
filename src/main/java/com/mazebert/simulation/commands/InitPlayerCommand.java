package com.mazebert.simulation.commands;

import com.mazebert.simulation.units.heroes.HeroType;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.potions.PotionType;
import com.mazebert.simulation.units.towers.TowerType;

import java.util.EnumSet;

public strictfp class InitPlayerCommand extends Command {
    public HeroType heroType;

    public EnumSet<HeroType> foilHeroes = EnumSet.noneOf(HeroType.class);
    public EnumSet<TowerType> foilTowers = EnumSet.noneOf(TowerType.class);
    public EnumSet<PotionType> foilPotions = EnumSet.noneOf(PotionType.class);
    public EnumSet<ItemType> foilItems = EnumSet.noneOf(ItemType.class);
}
