package com.nalulabs.lib.mvp;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;

/**
 * Created by Jack on 15/03/2017.
 */

public class BaseModel
{
    public ObservableBoolean error = new ObservableBoolean();
    public ObservableBoolean confirm = new ObservableBoolean();

    public Class<? extends Throwable> lastError;

    public ObservableField<String> errorMessage = new ObservableField<>();
    public ObservableField<String> confirmMessage = new ObservableField<>();

    public ObservableInt confirmRequestCode = new ObservableInt();

    public ObservableField<String> retryButtonMessage = new ObservableField<>();
    public ObservableField<String> confirmButtonMessage = new ObservableField<>();
}
