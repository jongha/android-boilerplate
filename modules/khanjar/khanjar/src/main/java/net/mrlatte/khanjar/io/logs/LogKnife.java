package net.mrlatte.khanjar.io.logs;

import android.util.Log;

import net.mrlatte.khanjar.BuildConfig;
import net.mrlatte.khanjar.Config;
import net.mrlatte.khanjar.objects.StringKnife;

/**
 * Created by jongha on 8/11/15.
 */
public class LogKnife {
    private static final String LOG_PREFIX = Config.APP_NAME + "_";
    private static final int LOG_PREFIX_LENGTH = LOG_PREFIX.length();
    private static final int MAX_LOG_TAG_LENGTH = 23;

    public static String makeLogTag(String prefix, String tag) {
        prefix = StringKnife.isNullOrEmpty(prefix) ? LOG_PREFIX : prefix + "_";
        if (tag.length() > MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH) {
            return prefix
                    + tag.substring(0, MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH
                    - 1);
        }

        return prefix + tag;
    }

    public static String makeLogTag(String tag) {
        return makeLogTag(null, tag);
    }

    /**
     * Don't use this when obfuscating class names!
     */
    public static String makeLogTag(Class cls) {
        return makeLogTag(cls.getSimpleName());
    }

    /**
     * Don't use this when obfuscating class names!
     */
    public static String makeLogTag(String prefix, Class cls) {
        return makeLogTag(prefix, cls.getSimpleName());
    }

    public static void LOGD(final String tag, String... messages) {
        // noinspection PointlessBooleanExpression,ConstantConditions
        if (BuildConfig.DEBUG || Log.isLoggable(tag, Log.DEBUG)) {
            if (messages != null) {
                Log.d(tag, StringKnife.join(",", messages));
            }
        }
    }

    public static void LOGD(final String tag, String message) {
        // noinspection PointlessBooleanExpression,ConstantConditions
        if (BuildConfig.DEBUG || Log.isLoggable(tag, Log.DEBUG)) {
            if (message != null) {
                Log.d(tag, message);
            }
        }
    }

    public static void LOGD(final String tag, String message, Throwable cause) {
        // noinspection PointlessBooleanExpression,ConstantConditions
        if (BuildConfig.DEBUG || Log.isLoggable(tag, Log.DEBUG)) {
            if (message != null) {
                Log.d(tag, message, cause);
            }
        }
    }

    public static void LOGV(final String tag, String... messages) {
        if (BuildConfig.DEBUG && Log.isLoggable(tag, Log.VERBOSE)) {
            if (messages != null) {
                Log.v(tag, StringKnife.join(",", messages));
            }
        }
    }

    public static void LOGV(final String tag, String message) {
        // noinspection PointlessBooleanExpression,ConstantConditions
        if (BuildConfig.DEBUG && Log.isLoggable(tag, Log.VERBOSE)) {
            if (message != null) {
                Log.v(tag, message);
            }
        }
    }

    public static void LOGV(final String tag, String message, Throwable cause) {
        // noinspection PointlessBooleanExpression,ConstantConditions
        if (BuildConfig.DEBUG && Log.isLoggable(tag, Log.VERBOSE)) {
            if (message != null) {
                Log.v(tag, message, cause);
            }
        }
    }

    public static void LOGI(final String tag, String... messages) {
        if (messages != null) {
            Log.i(tag, StringKnife.join(",", messages));
        }
    }

    public static void LOGI(final String tag, String message) {
        if (message != null) {
            Log.i(tag, message);
        }
    }

    public static void LOGI(final String tag, String message, Throwable cause) {
        if (message != null) {
            Log.i(tag, message, cause);
        }
    }

    public static void LOGW(final String tag, String... messages) {
        if (messages != null) {
            Log.w(tag, StringKnife.join(",", messages));
        }
    }

    public static void LOGW(final String tag, String message) {
        Log.w(tag, message);
    }

    public static void LOGW(final String tag, String message, Throwable cause) {
        Log.w(tag, message, cause);
    }

    public static void LOGE(final String tag, String... messages) {
        if (messages != null) {
            Log.e(tag, StringKnife.join(",", messages));
        }
    }

    public static void LOGE(final String tag, String message) {
        Log.e(tag, message);
    }

    public static void LOGE(final String tag, String message, Throwable cause) {
        Log.e(tag, message, cause);
    }
}
