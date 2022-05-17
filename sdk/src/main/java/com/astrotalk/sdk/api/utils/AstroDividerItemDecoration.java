package com.astrotalk.sdk.api.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class AstroDividerItemDecoration extends RecyclerView.ItemDecoration {

    private final Drawable mDivider;
    private final DisplayMetrics displayMetrics;
    private final int padding;

    public AstroDividerItemDecoration(Context context, Drawable drawable, int paddingInDP) {
        this.padding = paddingInDP;
        mDivider = drawable;
        displayMetrics = context.getResources().getDisplayMetrics();
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft() + toPixels(padding, displayMetrics);
        int right = parent.getWidth() - parent.getPaddingRight() - toPixels(0, displayMetrics);

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    public static int toPixels(int dp, DisplayMetrics metrics) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }
}

