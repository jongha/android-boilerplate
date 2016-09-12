package net.mrlatte.khanjar.ui.images;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.provider.OpenableColumns;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;

import static net.mrlatte.khanjar.io.logs.LogKnife.LOGD;
import static net.mrlatte.khanjar.io.logs.LogKnife.LOGE;
import static net.mrlatte.khanjar.io.logs.LogKnife.makeLogTag;

/**
 * Created by jongha on 10/24/15.
 */
public class ImageKnife {
    private static final String TAG = makeLogTag(ImageKnife.class);

    public static float getScaleFector(int width, int height, int maxWidth, int maxHeight, int minWidth, int minHeight) {
        float scaleFactor = Math.max(width / maxWidth, height / maxHeight);
        if (minWidth != 0 && minHeight != 0 && (width < minWidth || height < minHeight)) {
            if (width < minWidth && height > minHeight) {
                scaleFactor = width / minWidth;
            } else if (width > minWidth && height < minHeight) {
                scaleFactor = height / minHeight;
            } else {
                scaleFactor = Math.max(width / minWidth, height / minHeight);
            }
        }

        return Math.max(scaleFactor, 1);
    }

    public static byte[] compressBitmap(Bitmap bitmap) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        baos.close();

        return imageBytes;
    }

    public static String getFileNameFromUri(Context context, Uri uri) {
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null, null);

        String fileName = null;
        try {
            // moveToFirst() returns false if the cursor has 0 rows.  Very handy for
            // "if there's anything to look at, look at it" conditionals.
            if (cursor != null && cursor.moveToFirst()) {

                // Note it's called "Display Name".  This is
                // provider-specific, and might not necessarily be the file name.
                fileName = cursor.getString(
                        cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                LOGD(TAG, "FileKnife Name: " + fileName);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return fileName;
    }

    public static Size getSize(Context context, Uri uri) {
        Size size = new Size();
        try {
            ParcelFileDescriptor parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();

            BitmapFactory.Options options = new BitmapFactory.Options();
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                options.inPurgeable = true;
                options.inInputShareable = true;
            }

            options.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);

            size.width = bitmap.getWidth();
            size.height = bitmap.getHeight();
        } catch (Exception ignored) {
        }

        return size;
    }

    public static Bitmap getBitmapFromUri(Context context, Uri uri, SizeType requestedSize) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();

        BitmapFactory.Options options = new BitmapFactory.Options();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            options.inPurgeable = true;
            options.inInputShareable = true;
        }

        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);

        options.inJustDecodeBounds = false;
        int sampleSize = calculateInSampleSize(options, requestedSize.getValue(), requestedSize.getValue());
        options.inSampleSize = sampleSize;

        Bitmap bitmap = null;
        do {
            try {
                sampleSize *= 2;
                bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
            } catch (OutOfMemoryError e) {
                options.inSampleSize = sampleSize;
                LOGD(TAG, "Out of memory error: " + e.toString());
            }
        } while (bitmap == null && sampleSize <= 256);

        return bitmap;
    }

    public static int rotationForImage(Context context, Uri uri) {
        if (uri.getScheme().equals("content")) {
            String[] projection = {MediaStore.Images.ImageColumns.ORIENTATION};
            Cursor c = context.getContentResolver().query(uri, projection, null, null, null);
            if (c.moveToFirst()) {
                int value = c.getInt(0);
                c.close();
                return value;
            }
            c.close();
        } else if (uri.getScheme().equals("file")) {
            try {
                ExifInterface exif = new ExifInterface(uri.getPath());
                return (int) exifOrientationToDegrees(
                        exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                ExifInterface.ORIENTATION_NORMAL));
            } catch (IOException e) {
                LOGE(TAG, "Error checking exif", e);
            }
        }
        return 0;
    }

    private static float exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    private static int calculateInSampleSize(
            BitmapFactory.Options options, int requestedWidth, int requestedHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > requestedHeight || width > requestedWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > requestedHeight
                    && (halfWidth / inSampleSize) > requestedWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Size getScaledSize(int width, int height, int maxWidth, int maxHeight) {
        Size size = new Size();
        float fector = (width > height) ? ((float) maxWidth / width) : ((float) maxHeight / height);
        if (fector == 0) {
            fector = 1;
        }

        size.width = (int) (width * fector);
        size.height = (int) (height * fector);

        if (size.width == 0 || size.height == 0) {
            size.width = maxWidth;
            size.height = maxHeight;
        }

        return size;
    }

    public enum SizeType {
        SMALL(150), NORMAL(250), LARGE(500), XLARGE(1000);

        private final int value;

        SizeType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public static class Size {
        public int width;
        public int height;

        public Size() {
            this.width = 0;
            this.height = 0;
        }
    }
}
