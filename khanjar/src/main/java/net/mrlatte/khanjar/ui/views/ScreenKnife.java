package net.mrlatte.khanjar.ui.views;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by Jong-Ha Ahn on 10/1/15.
 * jongha.ahn@mrlatte.net
 */
public class ScreenKnife {
    public static float spToPx(Context context, float sp) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    public static int dpToPx(Context context, float dp) {
        float densityDpi = context.getResources().getDisplayMetrics().densityDpi;
        return (int) (dp * (densityDpi / 160f));
    }

    public static int pxToDp(Context context, float px) {
        float densityDpi = context.getResources().getDisplayMetrics().densityDpi;
        return (int) (px / (densityDpi / 160f));
    }

    public static int pxToSp(Context context, float px) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (px / scaledDensity);
    }

    public static int getScreenHeight(Context c) {
        WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
