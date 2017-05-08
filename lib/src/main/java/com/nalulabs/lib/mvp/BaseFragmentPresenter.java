package com.nalulabs.lib.mvp;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Jack on 15/03/2017.
 */

public abstract class BaseFragmentPresenter<M extends BaseModel, F extends BaseFragment> extends BasePresenter<M, F>
{
    protected F fragment;

    @Override
    public void onCreate(F view, Bundle savedInstanceState, Intent intent, Bundle arguments)
    {
        super.onCreate(view, savedInstanceState, intent, arguments);
        this.fragment = view;
    }

    @Override
    protected String getString(int res)
    {
        return fragment.getResources().getString(res);
    }
}
