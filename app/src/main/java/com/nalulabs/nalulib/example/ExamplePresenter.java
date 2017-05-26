package com.nalulabs.nalulib.example;

import android.os.Bundle;

import com.nalulabs.lib.mvp.BaseActivityPresenter;
import com.nalulabs.nalulib.R;

import java.util.Arrays;

import javax.inject.Inject;

import it.codingjam.lifecyclebinder.BindLifeCycle;

/**
 * Created by jack on 26/05/17.
 */
@BindLifeCycle
public class ExamplePresenter extends BaseActivityPresenter<ExampleModel, ExampleActivity> {

    @Inject
    public ExamplePresenter() {
    }

    @Override
    protected ExampleModel createModel(Bundle arguments) {
        return new ExampleModel();
    }

    @Override
    public void onViewCreated(ExampleActivity view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            populateList();
        }

        activity.createList();
    }

    public void onConfirmClick() {
        showConfirm(R.string.app_name, R.string.retry);
    }

    public void onErrorClick() {
        showError(new NullPointerException());
    }

    public void populateList() {
        model.items = Arrays.asList(new Item1("A"), new Item2("B"), new Item1("C"), new Item1("C"), new Item1("D")
                , new Item1("E"), new Item1("F"), new Item1("G"), new Item1("H"), new Item2("I"));
    }
}
