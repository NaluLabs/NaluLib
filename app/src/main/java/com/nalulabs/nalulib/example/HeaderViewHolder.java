package com.nalulabs.nalulib.example;

import com.nalulabs.lib.recycle.BaseViewHolder;
import com.nalulabs.nalulib.databinding.HeaderBinding;

/**
 * Created by fabiocollini on 24/05/17.
 */

public class HeaderViewHolder extends BaseViewHolder<Integer> {

    public HeaderViewHolder(HeaderBinding binding) {
        super(binding.getRoot());
    }

    @Override
    public void bind(Integer data) {

    }
}
