package net.mrlatte.khanjar.devices;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

/**
 * Created by jongha on 8/13/15.
 */
public class DeviceUserNameKnife {
    public static String getOwnerNameOfDevice(Context application) {
        // permission needed
        // <uses-permission android:name="android.permission.READ_PROFILE"/>
        // <uses-permission android:name="android.permission.READ_CONTACTS"/>
        String ownerName = "";
        Cursor cursor = application.getContentResolver().query(ContactsContract.Profile.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();

            try {
                int columnIndex = cursor.getColumnIndex("display_name");
                ownerName = cursor.getString(columnIndex);
            }catch(Exception e) {

            }

            cursor.close();
        }

        return ownerName;
    }
}
