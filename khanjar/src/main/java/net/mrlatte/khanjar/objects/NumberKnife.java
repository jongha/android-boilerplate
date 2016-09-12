package net.mrlatte.khanjar.objects;

import java.text.DecimalFormat;

/**
 * Created by Jongha on 2/3/16.
 */
public class NumberKnife {
    public static int parseInteger(String string, int defaultValue) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static String getCommaString(int value) {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        return formatter.format(value);
    }
}
