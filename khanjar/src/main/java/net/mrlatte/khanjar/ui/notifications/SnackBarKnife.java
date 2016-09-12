package net.mrlatte.khanjar.ui.notifications;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by Jongha on 1/12/16.
 */
public class SnackBarKnife {
    public static void error(View view, String message, int duration) {
        Snackbar.make(view, message, duration).show();
    }

    public static void alert(View view, String message) {
        Snackbar.make(view, message,
                Snackbar.LENGTH_SHORT).show();
    }
}
