package com.nalulabs.nalulib;

import android.databinding.ObservableField;

import com.nalulabs.lib.recycle.BaseViewHolder;
import com.nalulabs.nalulib.databinding.CellBinding;

/**
 * Created by fabiocollini on 24/05/17.
 */

public class CellViewHolder extends BaseViewHolder<Item1> {

    public final ObservableField<Item1> item = new ObservableField<>();
    private CellBinding binding;

    public CellViewHolder(CellBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        binding.setViewHolder(this);
    }

    @Override public void bind(Item1 data) {
        item.set(data);
        binding.executePendingBindings();
    }
}
