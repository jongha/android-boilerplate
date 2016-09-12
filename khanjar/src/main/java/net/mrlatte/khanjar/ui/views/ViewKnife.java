package net.mrlatte.khanjar.ui.views;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import net.mrlatte.khanjar.objects.StringKnife;

import java.text.Normalizer;

/**
 * Created by jongha on 10/13/15.
 */
public class ViewKnife {
    public static CharSequence highlight(String search, String originalText, int color) {
        if (StringKnife.isNullOrEmpty(search)) {
            return originalText;
        }

        // ignore case and accents
        // the same thing should have been done for the search text
        String normalizedText = Normalizer.normalize(originalText, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();

        int start = normalizedText.indexOf(search);
        if (start < 0) {
            // not found, nothing to to
            return originalText;
        } else {
            // highlight each appearance in the original text
            // while searching in normalized text
            Spannable highlighted = new SpannableString(originalText);
            while (start >= 0) {
                int spanStart = Math.min(start, originalText.length());
                int spanEnd = Math.min(start + search.length(), originalText.length());

                highlighted.setSpan(new ForegroundColorSpan(color), spanStart, spanEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                start = normalizedText.indexOf(search, spanEnd);
            }

            return highlighted;
        }
    }
}
