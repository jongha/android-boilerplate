package net.mrlatte.khanjar.ui.views;

import android.support.annotation.MainThread;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import net.mrlatte.khanjar.ui.interfaces.LinkSelectable;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.mrlatte.khanjar.io.logs.LogKnife.makeLogTag;

/**
 * Created by jongha on 8/3/16.
 */
public class TextViewLinkKnife {
    public static final Pattern PATTERN =
            Pattern.compile("\\b(https?|market)://(?!(data|about|javascript):)[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
    private static final String TAG = makeLogTag(TextViewLinkKnife.class);

    public static void setNormalizeLink(TextView view) {
        String message = view.getText().toString();
        view.setText(message.replaceAll("(?i)http://", "http://").replaceAll("(?i)https://", "https://"));
    }

    @MainThread
    public static void build(final TextView view,
                             final LinkSelectable linkSelectable) {

        CharSequence text = view.getText();
        view.setText(text, TextView.BufferType.SPANNABLE);

        int position = 0;
        List<Object[]> coordinates = new ArrayList<>();
        while (position >= 0) {
            Matcher matcher = PATTERN.matcher(text);
            position = matcher.find(position) ? matcher.start() : -1;

            if (position >= 0) {
                String url = matcher.group(0);
                coordinates.add(new Object[]{position, position + url.length(), url});
                position += url.length();
            }
        }

        if (coordinates.size() > 0) {
            view.setText(text);
            Spannable spannable = (Spannable) view.getText();

            for (final Object[] coordinate : coordinates) {
                if (linkSelectable != null) {
                    spannable.setSpan(new ClickableSpan() {
                                          @Override
                                          public void onClick(View widget) {
                                              String url = (String) coordinate[2];
                                              linkSelectable.onLink(url);
                                          }
                                      },
                            (int) coordinate[0],
                            (int) coordinate[1],
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    );

                } else {
                    spannable.setSpan(new ForegroundColorSpan(view.getLinkTextColors().getDefaultColor()),
                            (int) coordinate[0],
                            (int) coordinate[1],
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    );
                }
            }

            view.setText(spannable);

            if (linkSelectable != null) {
                view.setMovementMethod(LinkMovementMethod.getInstance());
            }
        }
    }
}
