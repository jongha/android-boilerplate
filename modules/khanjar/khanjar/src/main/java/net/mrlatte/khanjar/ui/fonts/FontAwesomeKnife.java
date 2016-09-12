package net.mrlatte.khanjar.ui.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by jongha on 6/23/16.
 */
public class FontAwesomeKnife {
    private static final String PATH = "fontawesome.ttf";

    public static Typeface getTypeface(Context context) {
        try {
            return Typeface.createFromAsset(context.getAssets(), PATH);
        } catch (Exception ignored) {
        }

        return null;
    }

    public static void setTypeface(View v) {
        setTypeface(v, 0);
    }

    public static void setTypeface(View v, int resId) {
        if (v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) v;
            for (int i = 0; i < vg.getChildCount(); i++) {
                View child = vg.getChildAt(i);
                setTypeface(child, resId);
            }

        } else if (v instanceof TextView) {
            TextView textView = (TextView) v;
            Typeface typeface = getTypeface(v.getContext());
            if (typeface != null) {
                textView.setTypeface(typeface);
            }

            if (resId != 0) {
                textView.setText(resId);
            }
        }
    }
}
