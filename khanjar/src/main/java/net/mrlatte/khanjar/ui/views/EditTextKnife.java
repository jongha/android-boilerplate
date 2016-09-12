package net.mrlatte.khanjar.ui.views;

import android.widget.EditText;

/**
 * Created by Jong-Ha Ahn on 9/28/15.
 */
public class EditTextKnife {
    public static void setTextAndCursorEnd(EditText editText, String text) {
        editText.setText(text);
        placeCursorAtEnd(editText);
    }

    public static void placeCursorAtEnd(EditText editText) {
        editText.setSelection(editText.getText().length());
    }
}
