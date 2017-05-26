package com.nalulabs.lib.recycle;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public class HeaderAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    private RecyclerView.Adapter delegate;

    private int headerViewType;
    private int headerPosition = -1;
    private BaseHeader header;

    public HeaderAdapter(RecyclerView.Adapter delegate, int headerViewType, int position, BaseHeader header)
    {
        this.delegate = delegate;
        this.headerViewType = headerViewType;
        this.headerPosition = position;
        this.header = header;
    }

    public HeaderAdapter(RecyclerView.Adapter delegate, int position, BaseHeader header)
    {
        this(delegate, 113344, position, header);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (viewType == headerViewType)
        {
            return header.getHeader(parent);
        } else
        {
            return delegate.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if (headerPosition == position)
        {
            ((BaseViewHolder) holder).viewHolderPosition.set(position);
            ((BaseViewHolder) holder).bind(header.binding);
        } else
        {
            delegate.onBindViewHolder(holder, getDelegatePosition(position));
        }
    }

    private int getDelegatePosition(int position) {
        return position < headerPosition ?
                        position :
                        position - 1;
    }

    @Override
    public int getItemCount()
    {
        return delegate.getItemCount() + 1;
    }

    @Override
    public int getItemViewType(int position)
    {
        return (position == headerPosition) ? headerViewType : delegate.getItemViewType(getDelegatePosition(position));
    }
}
