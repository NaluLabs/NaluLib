package com.nalulabs.lib.recycle;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by jack on 09/05/17.
 */

public class BaseItemDecorator extends RecyclerView.ItemDecoration {

    private int firstTopMargin;
    private int lastBottomMargin;
    private int topMargin;
    private int bottomMargin;
    private int leftMargin;
    private int rightMargin;

    private int size;
    private boolean withHeader;
    private Context context;

    public BaseItemDecorator(Context context, int size, boolean withHeader) {

        this.context = context;
        this.withHeader = withHeader;
        this.size = withHeader ?
                size + 1 :
                size;

        firstTopMargin = 0;
        lastBottomMargin = 0;
        topMargin = 0;
        bottomMargin = 0;
        leftMargin = 0;
        rightMargin = 0;
    }

    public void setVerticalSpaces(
            int firstTopMargin,
            int lastBottomMargin,
            int topMargin,
            int bottomMargin) {
        this.firstTopMargin = firstTopMargin;
        this.lastBottomMargin = lastBottomMargin;
        this.topMargin = topMargin;
        this.bottomMargin = bottomMargin;
    }

    public void setVerticalSpacesDp(
            int firstTopMargin,
            int lastBottomMargin,
            int topMargin,
            int bottomMargin) {
        setVerticalSpaces(
                dpTopixel(firstTopMargin),
                dpTopixel(lastBottomMargin),
                dpTopixel(topMargin),
                dpTopixel(bottomMargin)
        );
    }

    public void setHorizontalSpaces(
            int leftMargin,
            int rightMargin) {
        this.leftMargin = leftMargin;
        this.rightMargin = rightMargin;
    }

    public void setHorizontalSpacesDp(
            int leftMargin,
            int rightMargin) {
        setHorizontalSpaces(
                dpTopixel(leftMargin),
                dpTopixel(rightMargin)
        );
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {

        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = firstTopMargin;
            outRect.bottom = bottomMargin;
        } else if (parent.getChildAdapterPosition(view) == size - 1) {
            outRect.top = topMargin;
            outRect.bottom = lastBottomMargin;
        } else {
            outRect.top = topMargin;
            outRect.bottom = bottomMargin;
        }

        outRect.left = leftMargin;
        outRect.right = rightMargin;
    }

    public int dpTopixel(int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        float pixel = dp * density;
        return Math.round(pixel);
    }
}
