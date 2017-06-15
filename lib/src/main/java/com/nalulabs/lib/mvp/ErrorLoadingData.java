package com.nalulabs.lib.mvp;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

public interface ErrorLoadingData {
    ObservableBoolean getLoading();

    ObservableBoolean getError();

    ObservableBoolean getConfirm();

    void retryFromUi();

    void confirmFromUi();

    ObservableField<String> getErrorMessage();

    ObservableField<String> getConfirmMessage();

    ObservableField<String> getConfirmButtonMessage();

    ObservableField<String> getRetryButtonMessage();
}
