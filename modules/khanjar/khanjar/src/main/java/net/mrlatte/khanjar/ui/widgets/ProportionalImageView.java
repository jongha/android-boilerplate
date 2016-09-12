package net.mrlatte.khanjar.ui.widgets;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by jongha on 8/8/16.
 */
public class ProportionalImageView extends ImageView {
    private boolean mProportional;

    public ProportionalImageView(Context context) {
        super(context);

        initialize();
    }

    public ProportionalImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initialize();
    }

    public ProportionalImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        initialize();
    }

    public void setProportional(boolean proportional) {
        mProportional = proportional;
    }

    private void initialize() {
        mProportional = true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable drawable = getDrawable();
        if (mProportional && drawable != null) {
            int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
            int measuredHeight = MeasureSpec.getSize(heightMeasureSpec);

            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();

            if (width < measuredWidth) {
                double fector = (double) measuredWidth / width;
                width = measuredWidth;
                height *= fector;
            }

            if (height < measuredHeight) {
                double fector = (double) measuredHeight / height;
                width *= fector;
                height = measuredHeight;
            }

            setMeasuredDimension(width, height);

        } else {
            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        }
    }
}