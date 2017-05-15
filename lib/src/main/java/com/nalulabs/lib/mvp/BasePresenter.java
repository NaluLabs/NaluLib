package com.nalulabs.lib.mvp;

import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Bundle;

import com.nalulabs.lib.R;
import com.nalulabs.lib.logs.LogUtils;

import org.parceler.Parcels;

import java.util.List;

import io.reactivex.exceptions.CompositeException;
import it.codingjam.lifecyclebinder.DefaultLifeCycleAware;
import timber.log.Timber;

/**
 * Created by fabio on 27/03/17.
 */

public abstract class BasePresenter<M extends BaseModel, V> extends DefaultLifeCycleAware<V>
{
    private static final String MODEL = "model";
    protected M model;

    public final ObservableBoolean loading = new ObservableBoolean();

    public M getModel()
    {
        return model;
    }

    public void setModel(M model)
    {
        this.model = model;
    }

    public ObservableBoolean getError()
    {
        return model.error;
    }

    public void showError(Throwable t)
    {
        loading.set(false);
        model.error.set(true);
        model.lastError = t.getClass();
        model.errorMessage.set(getErrorMessage(t));
        model.retryButtonMessage.set(getRetryButtonMessage(t));
        if (isExceptionToBeLogged(t))
        {
            Timber.e(t);
        }
    }

    private boolean isExceptionToBeLogged(Throwable t)
    {
        if (t instanceof ManagedException || LogUtils.isConnectionError(t))
        {
            return false;
        }
        if (t instanceof CompositeException)
        {
            return existsExceptionToBeLogged(((CompositeException) t).getExceptions());
        }
        return true;
    }

    private boolean existsExceptionToBeLogged(List<Throwable> exceptions)
    {
        for (Throwable t : exceptions)
        {
            if (isExceptionToBeLogged(t))
            {
                return true;
            }
        }
        return false;
    }

    protected String getErrorMessage(Throwable t)
    {
        if (t instanceof ManagedException)
        {
            ManagedException managedException = (ManagedException) t;
            int errorMessageRes = managedException.getErrorMessageRes();
            if (errorMessageRes > 0)
            {
                return getString(errorMessageRes);
            }
        }
        return getString(R.string.connection_error_message);
    }

    protected String getRetryButtonMessage(Throwable t)
    {
        if (t instanceof ManagedException)
        {
            ManagedException managedException = (ManagedException) t;
            int res = managedException.getRetryButtonText();
            if (res > 0)
            {
                return getString(res);
            }
        }
        return getString(R.string.retry);
    }

    public ObservableField<String> getRetryButtonMessage()
    {
        return model.retryButtonMessage;
    }

    public ObservableField<String> getErrorMessage()
    {
        return model.errorMessage;
    }

    protected abstract String getString(int res);

    public void showLoading()
    {
        loading.set(true);
    }

    public void hideLoading()
    {
        loading.set(false);
    }

    public final void retryFromUi()
    {
        model.error.set(false);
        showLoading();
        retry(model.lastError);
    }

    public void retry(Class<? extends Throwable> lastError)
    {
    }

    @Override
    public void onCreate(V view, Bundle savedInstanceState, Intent intent, Bundle arguments)
    {
        if (model == null)
        {
            if (savedInstanceState == null)
            {
                model = createModel(arguments);
            } else
            {
                model = Parcels.unwrap(savedInstanceState.getParcelable(MODEL));
            }
        }
    }

    @Override
    public void onSaveInstanceState(V view, Bundle bundle)
    {
        bundle.putParcelable(MODEL, Parcels.wrap(model));
    }

    protected abstract M createModel(Bundle arguments);
}
