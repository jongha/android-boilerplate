package net.mrlatte.khanjar.ui.fonts;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;

import java.lang.reflect.Field;
import java.util.Hashtable;

import static net.mrlatte.khanjar.io.logs.LogKnife.LOGE;
import static net.mrlatte.khanjar.io.logs.LogKnife.makeLogTag;

/**
 * Created by jongha on 8/31/15.
 */
public class FontKnife {
    private static final String TAG = makeLogTag(FontKnife.class);
    private static final Hashtable<String, Typeface> CACHE = new Hashtable<String, Typeface>();
    private static final String FONT_PATH = "fonts/OpenSans-Regular.ttf";

    private static Context sContext;
    private static FontKnife sInstance;

    private FontKnife(Context context) {
        sContext = context;
    }

    public static synchronized FontKnife initialize(Context context) {
        if (sInstance == null) {
            sInstance = new FontKnife(context.getApplicationContext());
            sInstance.setDefaultFont("SANS", FONT_PATH);
        }
        return sInstance;
    }

    public static Typeface getFont() {
        synchronized (CACHE) {
            if (!CACHE.containsKey(FONT_PATH)) {
                Typeface t = Typeface.createFromAsset(sContext.getAssets(), FONT_PATH);
                CACHE.put(FONT_PATH, t);
            }
            return CACHE.get(FONT_PATH);
        }
    }

    private void setDefaultFont(String staticTypefaceFieldName, String fontAssetName) {
        final Typeface regular = Typeface.createFromAsset(sContext.getAssets(),
                fontAssetName);
        replaceFont(staticTypefaceFieldName, regular);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void replaceFont(String staticTypefaceFieldName, final Typeface newTypeface) {
        try {
            final Field staticField = Typeface.class
                    .getDeclaredField(staticTypefaceFieldName);
            staticField.setAccessible(true);
            staticField.set(null, newTypeface);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            LOGE(TAG, e.toString());
        }
    }
}
