package com.mazebert.simulation.units;

public strictfp enum Currency {
    Gold("Gold", "Gold", null, "card-gold-icon"),
    Cookie("Cookie", "Cookies", "stats-bar-cookies", "card-cookies-icon");

    Currency(String singular, String plural, String statsBarIcon, String towerCardIcon) {
        this.singular = singular;
        this.singularLowercase = singular.toLowerCase();
        this.plural = plural;
        this.pluralLowercase = plural.toLowerCase();
        this.statsBarIcon = statsBarIcon;
        this.towerCardIcon = towerCardIcon;
    }

    public final String singular;
    public final String singularLowercase;
    public final String plural;
    public final String pluralLowercase;
    public final String statsBarIcon;
    public final String towerCardIcon;
}
