package com.nalulabs.lib.mvp;

import android.databinding.Observable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by fabiocollini on 11/04/17.
 */

public class ErrorLoadingWrapper
{

    private Observable.OnPropertyChangedCallback loadingCallback;
    private Observable.OnPropertyChangedCallback errorCallback;
    private BasePresenter presenter;


    public View wrapLayout(final BasePresenter presenter, final View root, ViewGroup wrapperRoot)
    {
        this.presenter = presenter;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        wrapperRoot.addView(root, params);

        loadingCallback = new Observable.OnPropertyChangedCallback()
        {
            @Override
            public void onPropertyChanged(Observable observable, int i)
            {
                root.setVisibility(presenter.loading.get() ? View.GONE : View.VISIBLE);
            }
        };
        presenter.loading.addOnPropertyChangedCallback(loadingCallback);

        errorCallback = new Observable.OnPropertyChangedCallback()
        {
            @Override
            public void onPropertyChanged(Observable observable, int i)
            {
                root.setVisibility(presenter.getError().get() ? View.GONE : View.VISIBLE);
            }
        };
        presenter.getError().addOnPropertyChangedCallback(errorCallback);

        root.setVisibility(presenter.loading.get() || presenter.getError().get() ? View.GONE : View.VISIBLE);

        return wrapperRoot;
    }

    public void removeCallbacks()
    {
        presenter.loading.removeOnPropertyChangedCallback(loadingCallback);
        presenter.getError().removeOnPropertyChangedCallback(errorCallback);
    }
}
