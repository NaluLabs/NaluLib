package com.nalulabs.lib.mvp;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by jack on 10/04/17.
 */

public class BaseAdapter<T1, T2 extends ViewDataBinding> extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int ITEM = 0;
    private static final int HEADER = 1;

    private List<T1> list;
    private LayoutInflater inflater;
    private int layoutId;
    private ViewHolderFactory<BaseViewHolder<T1>, T2> factory;

    /**FIT OPTIONS*/
    private boolean fitHorizontal = false;

    /**HEADER OPTIONS*/
    private boolean showHeader = false;
    private int headerPosition = -1;
    private BaseHeader header;

    public BaseAdapter(List<T1> list, LayoutInflater inflater, int layoutId, ViewHolderFactory<BaseViewHolder<T1>, T2> factory) {
        this.list = list;
        this.inflater = inflater;
        this.layoutId = layoutId;
        this.factory = factory;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == ITEM) {
            T2 binding = DataBindingUtil.inflate(inflater, layoutId, parent, false);
            if (fitHorizontal) {
                int height = parent.getMeasuredHeight();
                int width = parent.getMeasuredWidth() / list.size();

                binding.getRoot().setLayoutParams(new RecyclerView.LayoutParams(width, height));
            }
            return factory.newInstance(binding);
        }else {
            return header.getHeader(parent);
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if(showHeader){
            if(headerPosition == position)
            {
                holder.position = position;
                holder.bind(header.binding);
            }else{

                int index = position < headerPosition ?
                        position :
                        position - 1;

                holder.position = index;
                holder.bind(list.get(index));
            }

        }else {
            holder.position = position;
            holder.bind(list.get(position));
        }

    }

    @Override
    public int getItemViewType(int position) {
        return (position == headerPosition) ? HEADER : ITEM;
    }

    @Override
    public int getItemCount() {
        return showHeader ? (list.size() + 1) : list.size();
    }

    public void fitHorizontal(){
        fitHorizontal = true;
    }

    public void setHeader(int position, BaseHeader header){
        this.showHeader = true;
        this.headerPosition = position;
        this.header = header;
    }
}
