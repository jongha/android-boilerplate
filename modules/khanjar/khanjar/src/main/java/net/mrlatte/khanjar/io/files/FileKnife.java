package net.mrlatte.khanjar.io.files;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.OpenableColumns;

import net.mrlatte.khanjar.io.factories.BaseStorageDirectoryFactory;
import net.mrlatte.khanjar.io.factories.FroyoStorageDirectoryFactory;
import net.mrlatte.khanjar.io.factories.StorageDirectoryFactory;
import net.mrlatte.khanjar.objects.StringKnife;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import static android.graphics.Bitmap.CompressFormat;
import static net.mrlatte.khanjar.io.logs.LogKnife.makeLogTag;

/**
 * Created by jongha on 8/17/15.
 */
public class FileKnife {
    private static final String TAG = makeLogTag(FileKnife.class);
    private static final Object lock = new Object();
    private static final String SCHEME_FILE = "file";
    private static StorageDirectoryFactory sStorageDirFactory = null;

    public static File getPath(String basePath, String path) {
        return new File(getStorageDir(basePath), path);
    }

    public static long getSize(String path) {
        return new File(path).length();
    }

    public static long getSize(Context context, Uri uri) {
        if (uri.getScheme().equals(SCHEME_FILE)) {
            return new File(uri.getPath()).length();

        } else {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                try {
                    int nameIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
                    if (cursor.moveToFirst()) {
                        return cursor.getLong(nameIndex);
                    }

                } finally {
                    cursor.close();
                }
            }
        }

        return 0;
    }

    public static String getFileExtension(String fileName) {
        if (!StringKnife.isNullOrEmpty(fileName)) {
            String[] tokens = fileName.split("\\.(?=[^\\.]+$)");
            if (tokens.length > 1) {
                return "." + tokens[1].toLowerCase();
            }
        }

        return null;
    }

    public static String getFileNameFromUri(Context context, Uri uri) {
        if (uri.getScheme().equals(SCHEME_FILE)) {
            return new File(uri.getPath()).getName();

        } else {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                try {
                    int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (cursor.moveToFirst()) {
                        return cursor.getString(nameIndex);
                    }

                } finally {
                    cursor.close();
                }
            }
        }

        return "";
    }

    public static boolean exists(String path) {
        return new File(path).exists();
    }

    public static File joinPath(String directory, String path) {
        return new File(directory, path);
    }

    public static boolean deleteDirectory(File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (null != files) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);

                    } else {
                        file.delete();
                    }
                }
            }
        }
        return (directory.delete());
    }

    public static boolean deleteFile(String path) {
        return deleteDirectory(new File(path));
    }

    public static int write(InputStream input, OutputStream output, int bufferSize) throws IOException {
        byte[] buffer = new byte[bufferSize];
        int count = 0;
        int n = 0;

        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    public static int write(byte[] input, OutputStream output, int bufferSize) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input);
        byte[] buffer = new byte[bufferSize];
        int count = 0;
        int n = 0;

        while (-1 != (n = inputStream.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    public static boolean mkdirs(File directory) {
        return directory != null && (directory.exists() || directory.mkdirs());
    }

    public static void copy(File src, File dst) throws IOException {
        FileInputStream inStream = new FileInputStream(src);
        FileOutputStream outStream = new FileOutputStream(dst);
        FileChannel inChannel = inStream.getChannel();
        FileChannel outChannel = outStream.getChannel();
        inChannel.transferTo(0, inChannel.size(), outChannel);
        inStream.close();
        outStream.close();
    }

    public static File write(Context context, Uri input, File output, int bufferSize) {
        try {
            write(context.getContentResolver().openInputStream(input),
                    new FileOutputStream(output, false),
                    bufferSize);

        } catch (Exception ignored) {

        }

        return new File(output.getPath());
    }

    public static File getFile(Context context, Uri input, File temp, int bufferSize) {
        try {
            if (mkdirs(new File(temp.getParent()))) {
                if (input.getScheme().equals(SCHEME_FILE)) {
                    return new File(input.getPath());

                } else {
                    write(context.getContentResolver().openInputStream(input),
                            new FileOutputStream(temp, false),
                            bufferSize);
                }
            }

        } catch (Exception ignored) {

        }

        return new File(temp.getPath());
    }

    public static File get(String directory, String filename) {
        if (mkdirs(new File(directory))) {
            return new File(directory, filename);
        }

        return null;
    }

    public static File write(Bitmap input, File output, CompressFormat format, int quality) {
        FileOutputStream outputStream = null;
        try {
            if (mkdirs(new File(output.getParent()))) {
                outputStream = new FileOutputStream(output, false);
                input.compress(format, quality, outputStream);
            }

        } catch (Exception ignored) {

        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception ignored) {
            }
        }

        return new File(output.getPath());
    }

    public static String read(File input) {
        StringBuffer buffer = new StringBuffer("");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(input));
            try {
                String line = reader.readLine();
                while (line != null) {
                    buffer.append(line);
                    line = reader.readLine();
                }

            } finally {
                reader.close();
            }
        } catch (Exception ignored) {

        }

        return buffer.toString();
    }

    public static File getNextFile(String appName, String path, String prefix, String extension) {
        int index = -1;
        String date = new SimpleDateFormat("yyyyMMdd", Locale.US).format(new Date());
        String name;
        File file = null;

        synchronized (lock) {
            do {
                name = prefix + date + "-" + appName + String.format("%04d", ++index) + extension;
            } while (new File(path, name).exists());

            file = FileKnife.get(path, name);
            if (file != null) {
                try {
                    file.createNewFile();
                } catch (Exception ignored) {
                }
            }
        }

        return file;
    }

    public static File createTempFile(String path, String suffix) throws IOException {
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = Config.FILE_PREFIX + "_" + timeStamp + "_";

        String fileName = UUID.randomUUID().toString();
        File storageDir = new File(path);
        if (mkdirs(storageDir)) {
            return File.createTempFile(
                    fileName,       /* prefix */
                    suffix,         /* suffix */
                    storageDir      /* directory */
            );
        }

        return null;
    }

    public static String readableFileSize(long size) {
        if (size <= 0) return "0";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    public static File getStorageDir(String basePath) {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            if (sStorageDirFactory == null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
                    sStorageDirFactory = new BaseStorageDirectoryFactory();
                } else {
                    sStorageDirFactory = new FroyoStorageDirectoryFactory();
                }
            }

            storageDir = sStorageDirFactory.getStorageDir(basePath);
            if (!mkdirs(storageDir)) {
                return null;
            }
        }

        return storageDir;
    }
}
