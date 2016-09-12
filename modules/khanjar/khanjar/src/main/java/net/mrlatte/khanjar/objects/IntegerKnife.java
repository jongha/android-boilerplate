package net.mrlatte.khanjar.objects;

/**
 * Created by jongha on 7/14/16.
 */
public class IntegerKnife {
    public static int tryParse(String value, int fallback) {
        try {
            return Integer.parseInt(value);

        } catch (Exception e) {
        }

        return fallback;
    }
}
