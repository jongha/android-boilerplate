package net.mrlatte.khanjar.ui.notifications;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Jong-Ha Ahn on 9/25/15.
 */
public class NotificationKnife {
    public static void error(Context context, String message) {
        error(context, message, Toast.LENGTH_SHORT);
    }

    public static void error(Context context, String message, int duration) {
        Toast.makeText(context.getApplicationContext(), message, duration).show();
    }

    public static void alert(Context context, String message) {
        Toast.makeText(context.getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

    public static void notify(Context context, String message) {
        Toast toast = Toast.makeText(context.getApplicationContext(), message,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }
}
