package com.nalulabs.lib.mvp;

import android.databinding.ObservableBoolean;

public interface ErrorLoadingData {
    ObservableBoolean getLoading();

    ObservableBoolean getError();

    ObservableBoolean getConfirm();
}
