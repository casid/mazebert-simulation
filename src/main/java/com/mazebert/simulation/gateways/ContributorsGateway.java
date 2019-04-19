package com.mazebert.simulation.gateways;

import com.mazebert.simulation.units.heroes.HeroType;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.potions.PotionType;
import com.mazebert.simulation.units.towers.TowerType;

import java.util.*;

@SuppressWarnings("unused") // Used by client/server
public strictfp class ContributorsGateway {
    public Set<String> getCardAuthors() {
        Set<String> result = new HashSet<>();

        for (TowerType type : TowerType.values()) {
            result.add(type.instance().getAuthor());
        }

        for (ItemType type : ItemType.values()) {
            result.add(type.instance().getAuthor());
        }

        for (PotionType type : PotionType.values()) {
            result.add(type.instance().getAuthor());
        }

        for (HeroType type : HeroType.values()) {
            result.add(type.instance().getAuthor());
        }

        result.remove("Andy");
        result.add("Kad");
        result.add("LHL");
        result.add("Bianca Assmann");
        result.add("Ian Pert"); // plink
        result.add("SnoreFox"); // Discord admin!

        return result;
    }

    public Set<String> getTesters() {
        Set<String> result = new HashSet<>();

        result.add("jrake");
        result.add("Kad");
        result.add("Kemu");
        result.add("ManuWins");
        result.add("Nillo");
        result.add("Solyom");
        result.add("Thomas Pircher");
        result.add("Sarore");
        result.add("Dragoneses");
        result.add("jhoijhoi");
        result.add("Warlemming");
        result.add("Vigi");

        return result;
    }

    public Set<String> getForumModerators() {
        Set<String> result = new HashSet<>();

        result.add("jhoijhoi");
        result.add("syotos");
        result.add("ManuWins");

        return result;
    }

    public List<String> getAllContributors() {
        Set<String> result = new HashSet<>();

        result.addAll(getCardAuthors());
        result.addAll(getTesters());
        result.addAll(getForumModerators());

        return sort(result);
    }

    private List<String> sort(Set<String> result) {
        List<String> sorted = new ArrayList<>(result);
        //noinspection Java8ListSort (Android compat)
        Collections.sort(sorted, String.CASE_INSENSITIVE_ORDER);

        return sorted;
    }

    public List<String> getCardAuthorsSorted() {
        return sort(getCardAuthors());
    }

    public List<String> getTestersSorted() {
        return sort(getTesters());
    }

    public List<String> getForumModeratorsSorted() {
        return sort(getForumModerators());
    }
}

