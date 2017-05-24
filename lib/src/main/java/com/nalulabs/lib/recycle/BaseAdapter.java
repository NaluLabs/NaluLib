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
    private List<Pair<ItemTypeSelector<? super T>, Factory<? extends T, ?>>> factories = new ArrayList<>();

    private static final ItemTypeSelector<Object> ALWAYS_SELECTOR = new ItemTypeSelector<Object>() {
        @Override public boolean isOfType(int pos, Object item) {
            return true;
        }
    };

    public BaseAdapter(List<T> list, LayoutInflater inflater, int layoutId, ViewHolderFactory<T, ?> defaultFactory) {
        this(list, inflater, new Factory<>(layoutId, defaultFactory));
    }

    public BaseAdapter(List<T> list, LayoutInflater inflater, Factory<? extends T, ?> defaultFactory) {
        this.list = list;
        this.inflater = inflater;
        factories.add(Pair.<ItemTypeSelector<? super T>, Factory<? extends T, ?>>create(ALWAYS_SELECTOR, defaultFactory));
    }

    @Override public int getItemViewType(int position) {
        for (int i = 0; i < factories.size(); i++) {
            Pair<ItemTypeSelector<? super T>, Factory<? extends T, ?>> pair = factories.get(i);
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

    public void addItemType(ItemTypeSelector<? super T> selector, Factory<? extends T, ?> factory) {
        factories.add(factories.size() - 1, Pair.<ItemTypeSelector<? super T>, Factory<? extends T, ?>>create(selector, factory));
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
