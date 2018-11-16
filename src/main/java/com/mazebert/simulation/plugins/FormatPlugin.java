package com.mazebert.simulation.plugins;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class FormatPlugin {
    private final NumberFormat noFractionFormat;
    private final NumberFormat oneFractionFormat;

    public FormatPlugin() {
        noFractionFormat = DecimalFormat.getInstance(Locale.US);
        noFractionFormat.setRoundingMode(RoundingMode.HALF_UP);
        noFractionFormat.setMinimumFractionDigits(0);
        noFractionFormat.setMaximumFractionDigits(0);

        oneFractionFormat = DecimalFormat.getInstance(Locale.US);
        oneFractionFormat.setRoundingMode(RoundingMode.HALF_UP);
        oneFractionFormat.setMinimumFractionDigits(0);
        oneFractionFormat.setMaximumFractionDigits(1);
    }

    public String cooldown(float cooldown) {
        return oneFractionFormat.format(cooldown) + "s";
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
        return "" + value;

        /* TODO test!
         * public static function formatPercentNumber(value:Number, digits:int = int.MIN_VALUE):String
         *                {
         * 			if (value == 0)
         *            {
         * 				return "0";
         *            }
         *
         * 			if (digits == int.MIN_VALUE)
         *            {
         * 				if (int(value * 100) != 0) digits = 0;
         * 				else if (int(value * 1000) != 0) digits = 1;
         * 				else if (int(value * 10000) != 0) digits = 2;
         * 				else if (int(value * 100000) != 0) digits = 3;
         * 				else if (int(value * 1000000) != 0) digits = 4;
         * 				else digits = 5;
         *            }
         *
         * 			return (value *= 100).toFixed(digits);
         *        }
         */
    }
}
