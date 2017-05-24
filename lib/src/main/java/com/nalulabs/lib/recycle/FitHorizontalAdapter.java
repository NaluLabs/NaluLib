package com.nalulabs.lib.recycle;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.ViewGroup;
import android.view.WindowManager;

public class FitHorizontalAdapter<T, B extends ViewDataBinding> extends RecyclerView.Adapter<BaseViewHolder<T>>
{
    private BaseAdapter<T, B> delegate;

    public FitHorizontalAdapter(BaseAdapter<T, B> delegate)
    {
        this.delegate = delegate;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        BaseViewHolder viewHolder = delegate.onCreateViewHolder(parent, viewType);

        /** PARENT SIZE **/
        int height = parent.getMeasuredHeight();
        int width = parent.getMeasuredWidth() / delegate.getItemCount();

        /** DISPLAY SIZE **/
        WindowManager wm = (WindowManager) parent.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int displayHeight = size.y;
        int displayWidth = size.x / delegate.getItemCount();

        viewHolder.itemView.setLayoutParams(new RecyclerView.LayoutParams(
                width > 0 ?
                        width :
                        displayWidth,
                height > 0 ?
                        height :
                        displayHeight));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position)
    {
        delegate.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount()
    {
        return delegate.getItemCount();
    }
}
