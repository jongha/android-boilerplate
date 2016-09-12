package net.mrlatte.khanjar.ui.images;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by jongha on 11/1/15.
 */
public class BitmapKnife {
    public static Bitmap combine(Bitmap background, Bitmap source) {
        Bitmap result;
        int width = background.getWidth(), height = background.getHeight();
        result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas comboImage = new Canvas(result);
        comboImage.drawBitmap(background, 0f, 0f, null);
        comboImage.drawBitmap(source, 0f, 0f, null);

        return result;
    }
}
