package com.nalulabs.lib.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import timber.log.Timber;


/**
 * Created by Jack on 15/02/2017.
 */

public abstract class BaseFragment extends Fragment
{
    private ErrorLoadingWrapper errorLoadingWrapper;

    protected View wrapLayout(ErrorLoadingData presenter, View root)
    {
        errorLoadingWrapper = new ErrorLoadingWrapper();
        ViewGroup wrapperLayout = createErrorLoadingWrapper(presenter);
        return errorLoadingWrapper.wrapLayout(presenter, root, wrapperLayout);
    }

    protected abstract ViewGroup createErrorLoadingWrapper(ErrorLoadingData presenter);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        Timber.d("CreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Timber.d("Create");
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Timber.d("Start");
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Timber.d("Resume");
    }

    @Override
    public void onPause()
    {
        super.onPause();
        Timber.d("Pause");
    }

    @Override
    public void onStop()
    {
        super.onStop();
        Timber.d("Stop");
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Timber.d("Destroy");
        if (errorLoadingWrapper != null)
        {
            errorLoadingWrapper.removeCallbacks();
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        Timber.d("Detach");
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        Timber.d("Attach");
    }
}
