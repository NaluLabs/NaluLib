package com.nalulabs.lib.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/**
 * Created by Jack on 15/02/2017.
 */

public abstract class BaseActivity extends AppCompatActivity
{

    private ErrorLoadingWrapper errorLoadingWrapper;

    protected View wrapLayout(BasePresenter presenter, View root)
    {
        errorLoadingWrapper = new ErrorLoadingWrapper();
        ViewGroup wrapperRoot = createErrorLoadingWrapper(presenter);
        return errorLoadingWrapper.wrapLayout(presenter, root, wrapperRoot);
    }

    protected abstract ViewGroup createErrorLoadingWrapper(BasePresenter presenter);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Timber.d("Create");
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Timber.d("Start");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Timber.d("Resume");
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Timber.d("Pause");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Timber.d("Stop");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Timber.d("Destroy");
        if (errorLoadingWrapper != null)
        {
            errorLoadingWrapper.removeCallbacks();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
