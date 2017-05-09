package com.nalulabs.lib.mvp;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by jack on 10/04/17.
 */

public abstract class BaseViewHolder<T1> extends RecyclerView.ViewHolder {

    public int position;

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bind(T1 data);
}
