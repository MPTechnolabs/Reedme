package com.example.reedme.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.DefaultRetryPolicy;
import com.example.reedme.custom.SlidingTabLayout.TabColorizer;
import com.example.reedme.helper.Util;

class SlidingTabStrip extends LinearLayout {
    private static final byte DEFAULT_BOTTOM_BORDER_COLOR_ALPHA = (byte) 38;
    private static final int DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS = 1;
    private static final byte DEFAULT_DIVIDER_COLOR_ALPHA = (byte) 32;
    private static final float DEFAULT_DIVIDER_HEIGHT = 0.5f;
    private static final int DEFAULT_DIVIDER_THICKNESS_DIPS = 1;
    private static final int DEFAULT_SELECTED_INDICATOR_COLOR = -13388315;
    private static final int SELECTED_INDICATOR_THICKNESS_DIPS = 8;
    private final Paint mBottomBorderPaint;
    private final int mBottomBorderThickness;
    Context mContext;
    private TabColorizer mCustomTabColorizer;
    private final int mDefaultBottomBorderColor;
    private final SimpleTabColorizer mDefaultTabColorizer;
    private final float mDividerHeight;
    private final Paint mDividerPaint;
    private final Paint mSelectedIndicatorPaint;
    private final int mSelectedIndicatorThickness;
    private int mSelectedPosition;
    private float mSelectionOffset;
    TabColorizer tabColorizer;
    private static class SimpleTabColorizer implements TabColorizer {
        private int[] mDividerColors;
        private int[] mIndicatorColors;

        private SimpleTabColorizer() {
        }

        public final int getIndicatorColor(int position) {
            return this.mIndicatorColors[position % this.mIndicatorColors.length];
        }

        public final int getDividerColor(int position) {
            return this.mDividerColors[position % this.mDividerColors.length];
        }

        void setIndicatorColors(int... colors) {
            this.mIndicatorColors = colors;
        }

        void setDividerColors(int... colors) {
            this.mDividerColors = colors;
        }
    }

    SlidingTabStrip(Context context) {
        this(context, null);
    }

    SlidingTabStrip(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        this.mContext = context;
        float density = getResources().getDisplayMetrics().density;
        int themeForegroundColor = new TypedValue().data;
        this.mDefaultBottomBorderColor = setColorAlpha(themeForegroundColor, DEFAULT_BOTTOM_BORDER_COLOR_ALPHA);
        this.mDefaultTabColorizer = new SimpleTabColorizer();
        SimpleTabColorizer simpleTabColorizer = this.mDefaultTabColorizer;
        int[] iArr = new int[DEFAULT_DIVIDER_THICKNESS_DIPS];
        iArr[0] = DEFAULT_SELECTED_INDICATOR_COLOR;
        simpleTabColorizer.setIndicatorColors(iArr);
        simpleTabColorizer = this.mDefaultTabColorizer;
        iArr = new int[DEFAULT_DIVIDER_THICKNESS_DIPS];
        iArr[0] = setColorAlpha(themeForegroundColor, DEFAULT_DIVIDER_COLOR_ALPHA);
        simpleTabColorizer.setDividerColors(iArr);
        this.mBottomBorderThickness = (int) (DefaultRetryPolicy.DEFAULT_BACKOFF_MULT * density);
        this.mBottomBorderPaint = new Paint();
        this.mBottomBorderPaint.setColor(this.mDefaultBottomBorderColor);
        this.mSelectedIndicatorThickness = (int) (8.0f * density);
        this.mSelectedIndicatorPaint = new Paint();
        this.mDividerHeight = DEFAULT_DIVIDER_HEIGHT;
        this.mDividerPaint = new Paint();
        this.mDividerPaint.setStrokeWidth((float) ((int) (DefaultRetryPolicy.DEFAULT_BACKOFF_MULT * density)));
    }

    void setSelectedIndicatorColors(int... colors) {
        this.mCustomTabColorizer = null;
        this.mDefaultTabColorizer.setIndicatorColors(colors);
        invalidate();
    }

    void setDividerColors(int... colors) {
        this.mCustomTabColorizer = null;
        this.mDefaultTabColorizer.setDividerColors(colors);
        invalidate();
    }

    void onViewPagerPageChanged(int position, float positionOffset) {
        this.mSelectedPosition = position;
        this.mSelectionOffset = positionOffset;
        invalidate();
    }

    protected void onDraw(Canvas canvas) {
        int height = getHeight();
        int childCount = getChildCount();
        int dividerHeightPx = (int) (Math.min(Math.max(0.0f, this.mDividerHeight), DefaultRetryPolicy.DEFAULT_BACKOFF_MULT) * ((float) height));
        if (this.mCustomTabColorizer != null) {
             tabColorizer = this.mCustomTabColorizer;
        } else {
            Object tabColorizer2 = this.mDefaultTabColorizer;
        }
        if (childCount > 0) {
            View selectedTitle = getChildAt(this.mSelectedPosition);
            int left = selectedTitle.getLeft();
            int right = selectedTitle.getRight();
//            int color = tabColorizer.getIndicatorColor(this.mSelectedPosition);
            if (this.mSelectionOffset > 0.0f && this.mSelectedPosition < getChildCount() - 1) {
                int nextColor = tabColorizer.getIndicatorColor(this.mSelectedPosition + DEFAULT_DIVIDER_THICKNESS_DIPS);
               /* if (color != nextColor) {
                    color = blendColors(nextColor, color, this.mSelectionOffset);
                }*/
                View nextTitle = getChildAt(this.mSelectedPosition + DEFAULT_DIVIDER_THICKNESS_DIPS);
                left = (int) ((this.mSelectionOffset * ((float) nextTitle.getLeft())) + ((DefaultRetryPolicy.DEFAULT_BACKOFF_MULT - this.mSelectionOffset) * ((float) left)));
                right = (int) ((this.mSelectionOffset * ((float) nextTitle.getRight())) + ((DefaultRetryPolicy.DEFAULT_BACKOFF_MULT - this.mSelectionOffset) * ((float) right)));
            }
          //  this.mSelectedIndicatorPaint.setColor(color);
            Util.getInstance(this.mContext).drawTriangles(left, right, height, canvas);
        }
        canvas.drawRect(0.0f, (float) (height - this.mBottomBorderThickness), (float) getWidth(), (float) height, this.mBottomBorderPaint);
        int separatorTop = (height - dividerHeightPx) / 2;
        for (int i = 0; i < childCount - 1; i += DEFAULT_DIVIDER_THICKNESS_DIPS) {
            View child = getChildAt(i);
            this.mDividerPaint.setColor(tabColorizer.getDividerColor(i));
            canvas.drawLine((float) child.getRight(), (float) separatorTop, (float) child.getRight(), (float) (separatorTop + dividerHeightPx), this.mDividerPaint);
        }
    }

    private static int setColorAlpha(int color, byte alpha) {
        return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
    }
}
