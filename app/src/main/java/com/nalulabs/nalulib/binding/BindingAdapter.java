package com.nalulabs.nalulib.binding;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

/**
 * Created by jack on 26/05/17.
 */

public class BindingAdapter {

    @android.databinding.BindingAdapter("visibleOrGone")
    public static void bindVisibleOrGone(View v, boolean visible) {
        v.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @android.databinding.BindingAdapter("visibleOrInvisible")
    public static void bindVisibleOrInvisible(View v, boolean visible) {
        v.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    @android.databinding.BindingAdapter("loading")
    public static void bindLoading(ImageView v, boolean loading) {
        if (loading) {
            RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(800);
            rotate.setInterpolator(new LinearInterpolator());
            rotate.setRepeatCount(Animation.INFINITE);
            v.startAnimation(rotate);
        } else {
            v.setAnimation(null);
        }
    }
}
