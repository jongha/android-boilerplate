package net.mrlatte.khanjar.io.factories;

import java.io.File;

/**
 * Created by jongha on 8/17/15.
 */
public abstract class StorageDirectoryFactory {
    public abstract File getStorageDir(String dirName);
}
