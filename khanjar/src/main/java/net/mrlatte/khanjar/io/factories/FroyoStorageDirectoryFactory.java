package net.mrlatte.khanjar.io.factories;

import android.os.Environment;

import java.io.File;

/**
 * Created by jongha on 8/17/15.
 */
public class FroyoStorageDirectoryFactory extends StorageDirectoryFactory {
    @Override
    public File getStorageDir(String dirName) {
        return new File(
                Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES
                ),
                dirName
        );
    }
}