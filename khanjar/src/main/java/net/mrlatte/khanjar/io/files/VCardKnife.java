package net.mrlatte.khanjar.io.files;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by jongha on 10/22/15.
 */
public class VCardKnife {
    public static void export(Context context, Cursor cursor, File file) {
        ArrayList<String> vCard = new ArrayList<>();
        String lookupKey = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
        Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_VCARD_URI, lookupKey);
        AssetFileDescriptor fd;
        try {
            fd = context.getContentResolver().openAssetFileDescriptor(uri, "r");
            if (fd != null) {
                FileInputStream in = fd.createInputStream();
                byte[] buffer = new byte[(int) fd.getDeclaredLength()];
                in.read(buffer);
                String vCardString = new String(buffer);
                vCard.add(vCardString);

                File storageDir = new File(file.getParent());
                if (!storageDir.exists()) {
                    storageDir.mkdirs();
                }

                FileOutputStream mFileOutputStream = new FileOutputStream(file, false);
                mFileOutputStream.write(vCardString.getBytes());
            }

        } catch (Exception ignored) {
        }
    }

    public static String getTel(File file) {
        String tel = "";
        InputStream input = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;

        try {
            input = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(input);
            reader = new BufferedReader(inputStreamReader);

            while ((tel = reader.readLine()) != null) {
                if (tel.startsWith("TEL;")) {
                    String[] tokens = tel.split(":");
                    if (tokens.length > 1) {
                        tel = tokens[1];
                        break;
                    }
                }
            }

        } catch (IOException ignored) {

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception ignored) {
                }
            }

            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (Exception ignored) {
                }
            }

            if (input != null) {
                try {
                    input.close();
                } catch (Exception ignored) {
                }
            }
        }

        return tel;
    }
}
