package com.mazebert.simulation.commands;

import com.mazebert.simulation.units.heroes.HeroType;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.potions.PotionType;
import com.mazebert.simulation.units.towers.TowerType;

import java.util.EnumSet;

public strictfp class InitPlayerCommand extends Command {
    public HeroType heroType;

    public EnumSet<HeroType> foilHeroes;
    public EnumSet<TowerType> foilTowers;
    public EnumSet<PotionType> foilPotions;
    public EnumSet<ItemType> foilItems;
}
