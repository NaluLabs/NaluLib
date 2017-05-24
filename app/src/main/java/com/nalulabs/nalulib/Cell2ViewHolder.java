package com.nalulabs.nalulib;

import android.databinding.ObservableField;

import com.nalulabs.lib.recycle.BaseViewHolder;
import com.nalulabs.nalulib.databinding.Cell2Binding;

public class Cell2ViewHolder extends BaseViewHolder<Item2> {

    public final ObservableField<Item2> item = new ObservableField<>();
    private Cell2Binding binding;

    public Cell2ViewHolder(Cell2Binding binding) {
        super(binding.getRoot());
        this.binding = binding;
        binding.setViewHolder(this);
    }

    @Override public void bind(Item2 data) {
        item.set(data);
        binding.executePendingBindings();
    }
}
