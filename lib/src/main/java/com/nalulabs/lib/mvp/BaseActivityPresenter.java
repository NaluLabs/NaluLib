package com.nalulabs.lib.mvp;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Jack on 15/03/2017.
 */

public abstract class BaseActivityPresenter<M extends BaseModel, A extends BaseActivity> extends BasePresenter<M, A>
{
    protected A activity;

    public void back()
    {
        if (activity != null)
        {
            activity.finish();
        }
    }

    @Override
    public void onCreate(A view, Bundle savedInstanceState, Intent intent, Bundle arguments)
    {
        super.onCreate(view, savedInstanceState, intent, arguments);
        this.activity = view;
    }

    @Override
    protected String getString(int res)
    {
        return activity.getString(res);
    }
}
