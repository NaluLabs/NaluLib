package com.nalulabs.lib.mvp;

import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.nalulabs.lib.R;
import com.nalulabs.lib.logs.LogUtils;

import it.codingjam.lifecyclebinder.DefaultLifeCycleAware;
import timber.log.Timber;

/**
 * Created by fabio on 27/03/17.
 */

public abstract class BasePresenter<M extends BaseModel, V> extends DefaultLifeCycleAware<V> implements ErrorLoadingData {
    private static final String MODEL = "model";
    protected M model;

    public final ObservableBoolean loading = new ObservableBoolean();

    public M getModel() {
        return model;
    }

    public void setModel(M model) {
        this.model = model;
    }

    public ObservableBoolean getConfirm() {
        return model.confirm;
    }

    @Override public ObservableBoolean getLoading() {
        return loading;
    }

    public ObservableInt getConfirmRequestCode() {
        return model.confirmRequestCode;
    }

    public void showConfirm(int messageRes, int buttonRes) {
        loading.set(false);
        model.confirm.set(true);
        model.confirmMessage.set(getString(messageRes));
        model.confirmRequestCode.set(0);
        model.confirmButtonMessage.set(getString(buttonRes));
    }

    public void showConfirm(int messageRes, int buttonRes, int requestCode) {
        loading.set(false);
        model.confirm.set(true);
        model.confirmMessage.set(getString(messageRes));
        model.confirmRequestCode.set(requestCode);
        model.confirmButtonMessage.set(getString(buttonRes));
    }

    public ObservableBoolean getError() {
        return model.error;
    }

    public void showError(Throwable t) {
        loading.set(false);
        model.error.set(true);
        model.lastError = (Class<Throwable>) t.getClass();
        model.errorMessage.set(getErrorMessage(t));
        model.retryButtonMessage.set(getRetryButtonMessage(t));
        if (LogUtils.isExceptionToBeLogged(t)) {
            Timber.e(t);
        }
    }

    protected String getErrorMessage(Throwable t) {
        if (t instanceof ManagedException) {
            ManagedException managedException = (ManagedException) t;
            int errorMessageRes = managedException.getErrorMessageRes();
            if (errorMessageRes > 0) {
                return getString(errorMessageRes);
            } else if (managedException.getErrorMessage() != null &&
                    !managedException.getErrorMessage().isEmpty()) {
                return managedException.getErrorMessage();
            }
        }
        return getString(R.string.connection_error_message);
    }

    protected String getRetryButtonMessage(Throwable t) {
        if (t instanceof ManagedException) {
            ManagedException managedException = (ManagedException) t;
            int res = managedException.getRetryButtonText();
            if (res > 0) {
                return getString(res);
            }
        }
        return getString(R.string.retry);
    }

    public ObservableField<String> getRetryButtonMessage() {
        return model.retryButtonMessage;
    }

    public ObservableField<String> getErrorMessage() {
        return model.errorMessage;
    }

    public ObservableField<String> getConfirmButtonMessage() {
        return model.confirmButtonMessage;
    }

    public ObservableField<String> getConfirmMessage() {
        return model.confirmMessage;
    }

    protected abstract String getString(int res);

    public void showLoading() {
        loading.set(true);
    }

    public void hideLoading() {
        loading.set(false);
    }

    public final void retryFromUi() {
        model.error.set(false);
        retry(model.lastError);
    }

    public void retry(Class<? extends Throwable> lastError) {
    }

    public void confirmFromUi() {
        model.confirm.set(false);
        confirm(model.confirmRequestCode.get());
    }

    public void confirm(int requestId) {

    }

    @Override
    public void onCreate(@NonNull V view, @Nullable Bundle savedInstanceState, @NonNull Intent intent, @Nullable Bundle arguments) {
        if (model == null) {
            if (savedInstanceState == null) {
                model = createModel(arguments);
            } else {
                model = loadModelFromBundle(savedInstanceState, MODEL);
            }
        }
    }

    @Override
    public void onSaveInstanceState(V view, Bundle bundle) {
        saveModelToBundle(bundle, MODEL);
    }

    protected abstract M loadModelFromBundle(@NonNull Bundle savedInstanceState, @NonNull String key);

    protected abstract void saveModelToBundle(@NonNull Bundle bundle, @NonNull String key);

    protected abstract M createModel(@Nullable Bundle arguments);

    @Override public void onPause(@NonNull V view) {
        super.onPause(view);
    }

    @Override public void onStart(@NonNull V view) {
        super.onStart(view);
    }

    @Override public void onResume(@NonNull V view) {
        super.onResume(view);
    }

    @Override public void onStop(@NonNull V view) {
        super.onStop(view);
    }

    @Override public void onDestroy(@NonNull V view, boolean changingConfigurations) {
        super.onDestroy(view, changingConfigurations);
    }

    @Override public void onDestroyView(@NonNull V view) {
        super.onDestroyView(view);
    }
}
