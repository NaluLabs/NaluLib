package com.nalulabs.lib.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Jack on 15/03/2017.
 */

public abstract class BaseActivityPresenter<M extends BaseModel, A extends BaseActivity> extends BasePresenter<M, A> {
    protected A activity;

    public void back() {
        if (activity != null) {
            activity.finish();
        }
    }

    @Override
    public void onCreate(@NonNull A view, @Nullable Bundle savedInstanceState, @NonNull Intent intent, @Nullable Bundle arguments) {
        this.activity = view;
        super.onCreate(view, savedInstanceState, intent, arguments);
    }

    @Override
    protected String getString(int res) {
        return activity.getString(res);
    }
}
