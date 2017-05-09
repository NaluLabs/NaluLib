package com.nalulabs.lib.recycle;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by jack on 09/05/17.
 */

public class BaseHeader<T1, T2 extends ViewDataBinding> {

    public int layoutId;
    public ViewHolderFactory<BaseViewHolder<T1>, T2> factory;
    public T1 binding;

    private LayoutInflater inflater;

    public BaseHeader(T1 binding, int layoutId, ViewHolderFactory<BaseViewHolder<T1>, T2> factory, LayoutInflater inflater) {
        this.layoutId = layoutId;
        this.factory = factory;
        this.inflater = inflater;
        this.binding = binding;
    }

    public BaseViewHolder getHeader(ViewGroup parent){
        T2 binding = DataBindingUtil.inflate(inflater, layoutId, parent, false);

        return factory.newInstance(binding);
    }
}
