package net.mrlatte.khanjar.ui.activities;

/**
 * Created by jongha on 8/7/16.
 */
public class ActivityKnife {
    public static String getCountableTitle(String title, int count) {
        return title + (count > 0 ? (" (" + count + ")") : "");
    }
}
