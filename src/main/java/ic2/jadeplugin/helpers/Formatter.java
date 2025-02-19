package ic2.jadeplugin.helpers;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Formatter {

    /**
     * Stolen from IC2Classic - The One Probe Module
     */

    public static final DecimalFormatSymbols US;
    public static final DecimalFormat THERMAL_GEN;
    public static final DecimalFormat SOLAR_TURBINE;
    public static final DecimalFormat NATURAL;
    public static final DecimalFormat DECIMAL;

    public static String formatNumber(double number, int digits) {
        return formatNumber(number, digits, false);
    }

    public static String formatInt(int number, int digits) {
        return formatInt(number, digits, false);
    }

    public static String formatInt(int number, int digits, boolean fixedLength) {
        return formatNumber((double) number, digits, fixedLength);
    }

    public static String formatNumber(double number, int digits, boolean fixedLength) {
        if (number < 1000) {
            return NATURAL.format(number); // use NATURAL format for small numbers
        }

        char[] suffixes = {'k', 'm', 'b', 't'};
        int index = -1;

        while (number >= 1000 && index < suffixes.length - 1) {
            number /= 1000.0;
            index++;
        }

        String suffix = index >= 0 ? String.valueOf(suffixes[index]) : "";
        int availableDigits = digits - suffix.length();

        // determine decimal format pattern dynamically
        String pattern = availableDigits > 1 ? "#,##0." + "#".repeat(availableDigits - 1) : "#,##0";
        DecimalFormat formatter = new DecimalFormat(pattern, new DecimalFormatSymbols(Locale.US));

        String formatted = formatter.format(number) + suffix;

        // fixed-length padding
        return fixedLength ? String.format("%" + digits + "s", formatted) : formatted;
    }



    static {
        US = new DecimalFormatSymbols(Locale.US);
        THERMAL_GEN = new DecimalFormat("#0.00", new DecimalFormatSymbols(Locale.US));
        SOLAR_TURBINE = new DecimalFormat("#00.00", new DecimalFormatSymbols(Locale.US));
        NATURAL = new DecimalFormat("###,##0", new DecimalFormatSymbols(Locale.US));
        DECIMAL = new DecimalFormat(".#########", new DecimalFormatSymbols(Locale.US));
    }
}
