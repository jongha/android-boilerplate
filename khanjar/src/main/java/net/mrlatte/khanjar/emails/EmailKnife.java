package net.mrlatte.khanjar.emails;

import net.mrlatte.khanjar.objects.StringKnife;

/**
 * Created by jongha on 6/13/16.
 */
public class EmailKnife {
    public static boolean isValidEmail(String string) {
        return !StringKnife.isNullOrEmpty(string) && android.util.Patterns.EMAIL_ADDRESS.matcher(string).matches();
    }
}
