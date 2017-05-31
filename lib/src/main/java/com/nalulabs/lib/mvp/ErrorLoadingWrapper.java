package com.nalulabs.lib.mvp;

import android.databinding.Observable;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by fabiocollini on 11/04/17.
 */

public class ErrorLoadingWrapper {

    private Observable.OnPropertyChangedCallback loadingCallback;
    private Observable.OnPropertyChangedCallback errorCallback;
    private Observable.OnPropertyChangedCallback confirmCallback;
    private BasePresenter presenter;


    public View wrapLayout(final BasePresenter presenter, final View root, ViewGroup wrapperRoot) {
        final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        return wrapLayout(presenter, root, wrapperRoot, params);
    }

    @NonNull
    public View wrapLayout(final BasePresenter presenter, final View root, ViewGroup wrapperRoot, FrameLayout.LayoutParams params) {
        this.presenter = presenter;
        wrapperRoot.addView(root, params);

        loadingCallback = new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                root.setVisibility(presenter.loading.get() ? View.GONE : View.VISIBLE);
            }
        };
        presenter.loading.addOnPropertyChangedCallback(loadingCallback);

        errorCallback = new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                root.setVisibility(presenter.getError().get() ? View.GONE : View.VISIBLE);
            }
        };
        presenter.getError().addOnPropertyChangedCallback(errorCallback);

        confirmCallback = new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                root.setVisibility(presenter.getConfirm().get() ? View.GONE : View.VISIBLE);
            }
        };
        presenter.getConfirm().addOnPropertyChangedCallback(confirmCallback);

        root.setVisibility(presenter.loading.get() || presenter.getError().get() || presenter.getConfirm().get() ? View.GONE : View.VISIBLE);

        return wrapperRoot;
    }

    public void removeCallbacks() {
        presenter.loading.removeOnPropertyChangedCallback(loadingCallback);
        presenter.getError().removeOnPropertyChangedCallback(errorCallback);
        presenter.getConfirm().removeOnPropertyChangedCallback(confirmCallback);
    }
}
