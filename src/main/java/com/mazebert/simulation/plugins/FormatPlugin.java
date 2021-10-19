package com.mazebert.simulation.plugins;

import com.mazebert.java8.Function;
import com.mazebert.simulation.*;
import com.mazebert.simulation.units.Currency;
import com.mazebert.simulation.units.wizards.Wizard;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Locale;

public strictfp class FormatPlugin {
    private static final float EPSILON = 0.00000001f;

    private final NumberFormat noFractionFormat;
    private final NumberFormat oneFractionFormat;
    private final NumberFormat twoFractionFormat;
    private final NumberFormat percentFormat;

    public FormatPlugin() {
        noFractionFormat = DecimalFormat.getInstance(Locale.US);
        noFractionFormat.setRoundingMode(RoundingMode.HALF_UP);
        noFractionFormat.setMinimumFractionDigits(0);
        noFractionFormat.setMaximumFractionDigits(0);

        oneFractionFormat = DecimalFormat.getInstance(Locale.US);
        oneFractionFormat.setRoundingMode(RoundingMode.HALF_UP);
        oneFractionFormat.setMinimumFractionDigits(0);
        oneFractionFormat.setMaximumFractionDigits(1);

        twoFractionFormat = DecimalFormat.getInstance(Locale.US);
        twoFractionFormat.setRoundingMode(RoundingMode.HALF_UP);
        twoFractionFormat.setMinimumFractionDigits(0);
        twoFractionFormat.setMaximumFractionDigits(2);

        percentFormat = DecimalFormat.getInstance(Locale.US);
        percentFormat.setRoundingMode(RoundingMode.HALF_UP);
        percentFormat.setMinimumFractionDigits(0);
        percentFormat.setMaximumFractionDigits(4);
    }

    public String cooldown(float cooldown) {
        String result = seconds(cooldown);
        if (cooldown >= Balancing.MAX_COOLDOWN) {
            result += " (max)";
        } else if (cooldown <= Balancing.MIN_COOLDOWN) {
            result += " (min)";
        }
        return result;
    }

    public String seconds(float seconds) {
        if (seconds < 0.1f) {
            return twoFractionFormat.format(seconds) + "s";
        } else {
            return oneFractionFormat.format(seconds) + "s";
        }
    }

    public String time(float seconds) {
        if (seconds < 60.0f) {
            return noFractionFormat.format(seconds) + "s";
        }
        if (seconds < 3600.0f) {
            return noFractionFormat.format(seconds / 60.0f) + "m";
        }

        String result = noFractionFormat.format(seconds / 3600.0f) + ":";
        int minutes = (int) ((seconds % 3600.0f) / 60.0f);
        if (minutes < 10) {
            result += "0";
        }
        result += minutes;
        return result + "h";
    }

    public String damage(double damage) {
        return positiveNumber(damage);
    }

    public String damage(double damage, int multicrit) {
        String damageText = damage(damage);
        StringBuilder result = new StringBuilder(damageText.length() + multicrit);
        result.append(damageText);
        for (int i = 0; i < multicrit; ++i) {
            result.append('!');
        }

        return result.toString();
    }

    public String gold(long amount) {
        if (amount >= 0) {
            return positiveNumber(amount);
        } else {
            return "-" + positiveNumber(-amount);
        }
    }

    public String gold(long gold, Currency currency) {
        return gold(gold, currency, " ");
    }

    public String gold(long gold, Currency currency, String between) {
        if (gold == 1) {
            return gold(gold) + between + currency.singularLowercase;
        }
        return gold(gold) + between + currency.pluralLowercase;
    }

    public String goldGain(long amount) {
        if (amount >= 0) {
            return "+" + gold(amount);
        } else {
            return gold(amount);
        }
    }

    public String positiveNumber(double value) {
        if (value < 1000.0) {
            return Long.toString((long) value);
        } else if (value < 1000000.0) {
            return noFractionFormat.format(value);
        } else if (value < 1000000000.0) {
            return oneFractionFormat.format(0.000001 * value) + "M";
        } else if (value < 1000000000000.0) {
            return oneFractionFormat.format(0.000000001 * value) + "B";
        } else if (value < 1000000000000000.0) {
            return oneFractionFormat.format(0.000000000001 * value) + "T";
        } else {
            return noFractionFormat.format(0.000000000001 * value) + "T";
        }
    }

    public String percent(float value) {
        return percent(value, calculateRequiredDigits(value, 4) + 1);
    }

    public String percent(float value, int digits) {
        if (isZero(value)) {
            value = 0.0f;
        }
        percentFormat.setMaximumFractionDigits(digits);
        return percentFormat.format(value * 100.0f);
    }

    public boolean isZero(float value) {
        return value <= EPSILON && value >= -EPSILON;
    }

    private int calculateRequiredDigits(float value, @SuppressWarnings("SameParameterValue") int max) {
        int d;
        int f;
        for (d = 0, f = 100; d < max; ++d, f *= 10) {
            if ((int) (value * f) != 0) {
                break;
            }
        }
        return d;
    }

    public String percentWithSignAndUnit(float value) {
        if (value > 0) {
            return "+" + percent(value) + "%";
        } else if (value < 0) {
            return "-" + percent(-value) + "%";
        }

        return "0%";
    }

    public String experience(float value) {
        if (value < 0.1f) {
            return twoFractionFormat.format(value);
        } else {
            return oneFractionFormat.format(value);
        }
    }

    public String experienceWithSignAndUnit(float value) {
        if (value > 0) {
            return "+" + experience(value) + "XP";
        } else {
            return experience(value) + "XP";
        }
    }

    public String kills(long amount) {
        return positiveNumber(amount);
    }

    public String colored(String string, int color) {
        return "<c=#" + toHex(color) + ">" + string + "</c>";
    }

    public String prophecyTitle(String string) {
        return colored(string, 0xff0000);
    }

    public String prophecyDescription(String string) {
        return colored(string, 0xb00000);
    }

    private String toHex(int color) {
        String str = Integer.toString(color, 16);

        if (str.length() < 6) {
            StringBuilder hex = new StringBuilder(str);
            while (hex.length() < 6) {
                hex.insert(0, "0");
            }
            return hex.toString();
        }

        return str;
    }

    public String rarity(Rarity rarity) {
        return rarity(rarity, true);
    }

    public String rarity(Rarity rarity, boolean lowercase) {
        return colored(lowercase ? rarity.nameLowercase : rarity.name, rarity.color);
    }

    public String card(CardType<?> cardType) {
        Card card = cardType.instance();
        return colored(card.getName(), card.getRarity().color);
    }

    public String card(CardType<?> cardType, int maxLength) {
        Card card = cardType.instance();
        String name = card.getName();
        if (name.length() > maxLength) {
            name = name.substring(0, maxLength) + "..";
        }
        return colored(name, card.getRarity().color);
    }

    public String element(Element element) {
        return colored(element.getName(), element.color);
    }

    public String armorType(ArmorType armorType) {
        return colored(armorType.name(), armorType.color);
    }

    public String armorType(String content, ArmorType armorType) {
        return colored(content, armorType.color);
    }

    public String waveTypePlural(WaveType waveType) {
        switch (waveType) {
            case CultistOfDagon:
                return "Cultists of Dagon";
            case CultistOfYig:
                return "Cultists of Yig";
            case CultistOfCthulhu:
                return "Cultists of Cthulhu";
            case CultistOfAzathoth:
                return "Cultists of Azathoth";
        }
        return waveType.name();
    }

    public String norseWorld(Element element) {
        return colored(element.norseWorld, element.color);
    }

    public String distance(float distance) {
        if (distance > 0 && distance < 1) {
            return oneFractionFormat.format(distance) + " tiles";
        }
        if (distance == 1) {
            return "1 tile";
        }
        return noFractionFormat.format(distance) + " tiles";
    }

    public String playerName(Wizard wizard) {
        return colored(wizard.name, getPlayerNameColor(wizard.playerId));
    }

    public int getPlayerNameColor(int playerId) {
        switch (playerId) {
            case 1:
                return 0xff0000;
            case 2:
                return 0x0000ff;
            case 3:
                return 0x00ff00;
            case 4:
                return 0xff00ff;
        }

        return 0xffffff;
    }

    public <T> String listing(String prefix, Collection<T> data, Function<T, String> mapper, String suffix) {
        StringBuilder result = new StringBuilder(prefix);
        int i = 0;
        for (T item : data) {
            if (i > 0) {
                if (i == data.size() - 1) {
                    result.append(" and ");
                } else {
                    result.append(", ");
                }
            }

            result.append(mapper.apply(item));
            ++i;
        }
        result.append(suffix);
        return result.toString();
    }
}
