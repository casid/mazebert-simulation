package com.mazebert.simulation.plugins;

import com.mazebert.simulation.Balancing;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public strictfp class FormatPlugin {
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
        String result;
        if (cooldown < 0.1f) {
            result = twoFractionFormat.format(cooldown) + "s";
        } else {
            result = oneFractionFormat.format(cooldown) + "s";
        }
        if (cooldown >= Balancing.MAX_COOLDOWN) {
            result += " (max)";
        } else if (cooldown <= Balancing.MIN_COOLDOWN) {
            result += " (min)";
        }
        return result;
    }

    public String damage(double damage) {
        return positiveNumber(damage);
    }

    public String money(double amount) {
        return positiveNumber(amount);
    }

    private String positiveNumber(double value) {
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
        return percent(value, calculateRequiredDigits(value, 5));
    }

    public String percent(float value, int digits) {
        percentFormat.setMaximumFractionDigits(digits);
        return percentFormat.format(value * 100.0f);
    }

    private int calculateRequiredDigits(float value, @SuppressWarnings("SameParameterValue") int max) {
        int d;
        int f;
        for (d = 0, f = 100; d < max; ++d, f *= 10) {
            if (((int) value * f) != 0) {
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
}
