package net.mrlatte.khanjar.locations;

import android.graphics.Rect;
import android.view.View;

import java.text.DecimalFormat;

/**
 * Created by Jongha on 1/11/16.
 */
public class LocationKnife {
    public static String readableDistance(int distance) {
        if (distance <= 0) return "0m";
        final String[] units = new String[]{"m", "km"};
        return (distance < 1000) ? (new DecimalFormat("#,##0").format(distance) + units[0]) :
                (new DecimalFormat("#,##0.#").format(distance / 1000.0) + units[1]);
    }

    public static Rect locateView(View v) {
        int[] loc_int = new int[2];
        if (v == null) return null;
        try {
            v.getLocationOnScreen(loc_int);
        } catch (NullPointerException npe) {
            //Happens when the view doesn't exist on screen anymore.
            return null;
        }
        Rect location = new Rect();
        location.left = loc_int[0];
        location.top = loc_int[1];
        location.right = location.left + v.getWidth();
        location.bottom = location.top + v.getHeight();
        return location;
    }
}
