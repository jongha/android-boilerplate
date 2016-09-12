package net.mrlatte.khanjar.objects;

/**
 * Created by jongha on 7/14/16.
 */
public class LongKnife {
    public static long tryParse(String value, int fallback) {
        try {
            return Long.parseLong(value);

        } catch (Exception e) {
        }

        return fallback;
    }
}
