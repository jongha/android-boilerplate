package net.mrlatte.khanjar.devices;

import android.content.Context;
import android.os.Build;
import android.os.PowerManager;

/**
 * Created by Jongha on 2/5/16.
 */
public class PowerKnife {
    public static boolean isDozing(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            return powerManager.isDeviceIdleMode() &&
                    !powerManager.isIgnoringBatteryOptimizations(context.getPackageName());
        } else {
            return false;
        }
    }

    public static boolean isWeakDazing(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            return !powerManager.isInteractive();

        } else {
            return false;
        }
    }
}
