package com.nalulabs.lib.recycle;

import android.databinding.ViewDataBinding;

/**
 * Created by jack on 10/04/17.
 */

public interface ViewHolderFactory<T, B extends ViewDataBinding> {

    BaseViewHolder<T> newInstance(B binding);
}
