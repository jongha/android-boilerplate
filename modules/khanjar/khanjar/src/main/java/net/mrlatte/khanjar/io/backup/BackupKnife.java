package net.mrlatte.khanjar.io.backup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * Created by jongha on 10/16/15.
 */
public class BackupKnife {
    public static boolean backup(String src, String dst, int limit) {
        try {
            File currentDB = new File(src);     //File from database (source file).
            File backupDB = new File(dst);      //Destination file.
            File backupPath = new File(backupDB.getParent());       //Destination folder

            if (!backupPath.exists()) {
                if (!backupPath.mkdirs()) {
                    return false;
                }
            } else {
                if (backupDB.exists()) {
                    backupDB.delete();
                }
            }

            if (backupDB.createNewFile()) {
                FileChannel srcChannel = new FileInputStream(currentDB).getChannel();
                FileChannel dstChannel = new FileOutputStream(backupDB).getChannel();

                dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
                srcChannel.close();
                dstChannel.close();
            }

            File[] allFiles = backupPath.listFiles();
            File oldestFile = null;

            for (File f : allFiles) {

                if (allFiles.length >= limit) {
                    if (oldestFile == null || f.lastModified() < oldestFile.lastModified()) {
                        oldestFile = f;
                    }
                }
            }

            if (oldestFile != null && allFiles.length > limit) {
                oldestFile.delete();
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public static boolean restore(String src, String dst) {
        try {
            File currentDB = new File(src);
            File backupDB = new File(dst);

            if (currentDB.exists() && backupDB.exists()) {
                FileChannel srcChannel = new FileInputStream(currentDB).getChannel();
                FileChannel dstChannel = new FileOutputStream(backupDB).getChannel();

                dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
                srcChannel.close();
                dstChannel.close();

            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public static File getBackup(String directory) {
        File fileDirectory = new File(directory);
        File lastFile = null;

        if (fileDirectory.exists()) {
            for (File file : fileDirectory.listFiles()) {
                if (lastFile == null || lastFile.lastModified() < file.lastModified()) {
                    lastFile = file;
                }
            }
        }

        return lastFile;
    }

    public static boolean exists(String path) {
        return getBackup(path) != null;
    }
}
