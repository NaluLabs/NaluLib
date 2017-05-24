package com.nalulabs.lib.recycle;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by jack on 10/04/17.
 */

public class BaseAdapter<T, B extends ViewDataBinding> extends RecyclerView.Adapter<BaseViewHolder<T>>
{

    private List<T> list;
    private LayoutInflater inflater;
    private int layoutId;
    private ViewHolderFactory<T, B> factory;

    public BaseAdapter(List<T> list, LayoutInflater inflater, int layoutId, ViewHolderFactory<T, B> factory)
    {
        this.list = list;
        this.inflater = inflater;
        this.layoutId = layoutId;
        this.factory = factory;
    }

    @Override
    public BaseViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType)
    {
        B binding = DataBindingUtil.inflate(inflater, layoutId, parent, false);
        return factory.newInstance(binding);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<T> holder, int position)
    {
        holder.viewHolderPosition.set(position);
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }
}
