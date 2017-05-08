package com.nalulabs.lib.mvp;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

/**
 * Created by Jack on 15/03/2017.
 */

public class BaseModel
{
    public ObservableBoolean error = new ObservableBoolean();

    public Class<? extends Throwable> lastError;

    public ObservableField<String> errorMessage = new ObservableField<>();

    public ObservableField<String> retryButtonMessage = new ObservableField<>();
}
