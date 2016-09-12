package net.mrlatte.khanjar.devices;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import net.mrlatte.khanjar.objects.StringKnife;


/**
 * Created by jongha on 8/17/15.
 */
public class DevicePhoneNumberKnife {
    private static volatile DevicePhoneNumberKnife Instance = null;

    public static DevicePhoneNumberKnife getInstance() {
        DevicePhoneNumberKnife localInstance = Instance;
        if (localInstance == null) {
            synchronized (DevicePhoneNumberKnife.class) {
                localInstance = Instance;
                if (localInstance == null) {
                    Instance = localInstance = new DevicePhoneNumberKnife();
                }
            }
        }
        return localInstance;
    }

    public static String stripExceptNumbers(String str, boolean includePlus) {
        StringBuilder res = new StringBuilder(str);
        String phoneChars = "0123456789";
        if (includePlus) {
            phoneChars += "+";
        }
        for (int i = res.length() - 1; i >= 0; i--) {
            if (!phoneChars.contains(res.substring(i, i + 1))) {
                res.deleteCharAt(i);
            }
        }
        return res.toString();
    }

    public static String stripExceptNumbers(String str) {
        return stripExceptNumbers(str, false);
    }

    public static String getDefaultRegion(Context context) {
        String country = "";
        try {
            TelephonyManager telephonyManager =
                    (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);

            if (telephonyManager != null) {
                country = telephonyManager.getSimCountryIso().toUpperCase();
            }
        } catch (Exception e) {
        }

        return country.toUpperCase();
    }

    public static Phonenumber.PhoneNumber parse(Context context, String numberToParse) {
        return parse(context, numberToParse, getDefaultRegion(context));
    }

    public static Phonenumber.PhoneNumber parse(Context context, String numberToParse, String defaultRegion) {
        if (StringKnife.isNullOrEmpty(defaultRegion)) {
            defaultRegion = getDefaultRegion(context);
        }

        Phonenumber.PhoneNumber phoneNumber = null;
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

        try {
            phoneNumber = phoneUtil.parse(numberToParse, defaultRegion);

        } catch (NumberParseException e) {

        }

        return phoneNumber;
    }

    public static String format(Context context, String numberToParse) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber phoneNumber = parse(context, numberToParse);
        if (phoneNumber != null) {
            return phoneUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
        }

        return "";
    }

    public static Phonenumber.PhoneNumber getMyPhoneNumber(Context context) {
        return getMyPhoneNumber(context, getDefaultRegion(context));
    }

    public static Phonenumber.PhoneNumber getMyPhoneNumber(Context context, String defaultRegion) {
        try {
            TelephonyManager telephonyManager =
                    (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);

            return parse(context, telephonyManager.getLine1Number(), defaultRegion);

        } catch (Exception e) {
            return parse(context, "", "");
        }
    }
}