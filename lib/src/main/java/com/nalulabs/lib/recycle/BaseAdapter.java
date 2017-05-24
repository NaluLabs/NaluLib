package com.nalulabs.lib.recycle;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jack on 10/04/17.
 */

public class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder<? extends T>> {

    private List<T> list;
    private final LayoutInflater inflater;
    private List<Pair<ItemTypeSelector<T>, Factory<? extends T, ?>>> factories = new ArrayList<>();

    public BaseAdapter(List<T> list, LayoutInflater inflater, int layoutId, ViewHolderFactory<T, ?> defaultFactory) {
        this(list, inflater, new Factory<>(layoutId, defaultFactory));
    }

    public BaseAdapter(List<T> list, LayoutInflater inflater, Factory<? extends T, ?> defaultFactory) {
        this.list = list;
        this.inflater = inflater;
        factories.add(Pair.create((pos, item) -> true, defaultFactory));
    }

    @Override public int getItemViewType(int position) {
        for (int i = 0; i < factories.size(); i++) {
            Pair<ItemTypeSelector<T>, Factory<? extends T, ?>> pair = factories.get(i);
            if (pair.first.isOfType(position, list.get(position))) {
                return i;
            }
        }

        throw new RuntimeException("Error defining default factory");
    }

    @Override
    public BaseViewHolder<? extends T> onCreateViewHolder(ViewGroup parent, int viewType) {
        return factories.get(viewType).second.create(inflater, parent);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<? extends T> holder, int position) {
        holder.viewHolderPosition.set(position);
        ((BaseViewHolder) holder).bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItemType(ItemTypeSelector<T> selector, Factory<? extends T, ?> factory) {
        factories.add(factories.size() - 1, Pair.create(selector, factory));
    }

    public interface ItemTypeSelector<T> {
        boolean isOfType(int position, T item);
    }

    public static class Factory<T, B extends ViewDataBinding> {
        private int layoutId;
        private ViewHolderFactory<T, B> factory;

        public Factory(int layoutId, ViewHolderFactory<T, B> factory) {
            this.layoutId = layoutId;
            this.factory = factory;
        }

        public BaseViewHolder<T> create(LayoutInflater inflater, ViewGroup parent) {
            B binding = DataBindingUtil.inflate(inflater, layoutId, parent, false);
            return factory.newInstance(binding);
        }
    }
}
