package net.mrlatte.khanjar.objects;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by jongha on 7/5/16.
 */
public class JSONKnife {
    /**
     * Return the value mapped by the given key, or {@code null} if not present or null.
     */
    public static String optString(JSONObject json, String key) {
        return json == null || json.isNull(key) ? null : json.optString(key, null);
    }

    public static Date optDate(JSONObject json, String key) {
        return json == null || json.isNull(key) ? null : new Date(json.optLong(key));
    }

    public static boolean optBoolean(JSONObject json, String key) {
        return optBoolean(json, key, false);
    }

    public static boolean optBoolean(JSONObject json, String key, boolean fallback) {
        if (json != null) {
            try {
                return json.getBoolean(key);

            } catch (JSONException e) {
                return json.optInt(key, fallback ? 1 : 0) != 0;
            }

        } else {
            return fallback;
        }
    }
}
