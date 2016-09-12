package net.mrlatte.khanjar.ui.views;

import android.support.annotation.MainThread;
import android.text.Spannable;
import android.text.style.BackgroundColorSpan;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static net.mrlatte.khanjar.io.logs.LogKnife.makeLogTag;

/**
 * Created by jongha on 8/3/16.
 */
public class TextViewHighlightKnife {
    private static final String TAG = makeLogTag(TextViewHighlightKnife.class);

    @MainThread
    public static void build(final TextView view,
                             final String[] keywords) {
        for (String keyword : keywords) {
            build(view, keyword);
        }
    }

    @MainThread
    public static void build(final TextView view,
                             final String keyword) {

        CharSequence text = view.getText();
        view.setText(text, TextView.BufferType.SPANNABLE);

        int position = 0;
        int keywordLength = keyword.length();

        List<Object[]> coordinates = new ArrayList<>();
        while (position >= 0) {
            position = text.toString().indexOf(keyword, position);
            if (position >= 0) {
                coordinates.add(new Object[]{position});
                position += keywordLength;
            }
        }

        if (coordinates.size() > 0) {
            view.setText(text);
            Spannable spannable = (Spannable) view.getText();
            for (final Object[] coordinate : coordinates) {
                int start = (int) coordinate[0];
                int end = start + keywordLength;

                if (spannable.length() >= end) {
                    spannable.setSpan(new BackgroundColorSpan(view.getHighlightColor()),
                            start,
                            end,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    );
                }
            }

            view.setText(spannable);
        }
    }
}
