package com.nalulabs.nalulib.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.nalulabs.lib.mvp.BaseActivity;
import com.nalulabs.lib.mvp.ErrorLoadingData;
import com.nalulabs.lib.recycle.BaseAdapter;
import com.nalulabs.lib.recycle.BaseHeader;
import com.nalulabs.lib.recycle.HeaderAdapter;
import com.nalulabs.nalulib.R;
import com.nalulabs.nalulib.databinding.ErrorLoadingWrapperBinding;
import com.nalulabs.nalulib.databinding.ExampleLayoutBinding;
import com.nalulabs.nalulib.databinding.Header2Binding;
import com.nalulabs.nalulib.databinding.HeaderBinding;
import com.nalulabs.nalulib.injection.ExampleApplication;

import javax.inject.Inject;

import it.codingjam.lifecyclebinder.BindLifeCycle;
import it.codingjam.lifecyclebinder.LifeCycleBinder;

public class ExampleActivity extends BaseActivity {

    private ExampleLayoutBinding binding;

    @BindLifeCycle
    @Inject
    ExamplePresenter presenter;

    @Override
    protected ViewGroup createErrorLoadingWrapper(ErrorLoadingData basePresenter) {
        ErrorLoadingWrapperBinding wrapper = ErrorLoadingWrapperBinding.inflate(LayoutInflater.from(this));
        wrapper.setPresenter(basePresenter);
        wrapper.executePendingBindings();

        return (ViewGroup) wrapper.getRoot();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ExampleApplication.get(this)
                .getComponent()
                .inject(this);

        binding = ExampleLayoutBinding.inflate(getLayoutInflater());
        binding.setPresenter(presenter);

        LifeCycleBinder.bind(this);

        setContentView(wrapLayout(presenter, binding.getRoot()));
    }

    public void createList() {

        BaseAdapter<BaseObject> adapter = new BaseAdapter<>(
                presenter.getModel().items,
                getLayoutInflater(),
                new BaseAdapter.Factory<>(R.layout.cell, CellViewHolder::new)
        );

        adapter.addItemType(
                (position, item) -> item instanceof Item2,
                new BaseAdapter.Factory<>(R.layout.cell2, Cell2ViewHolder::new)
        );

        BaseHeader<Integer, HeaderBinding> header = new BaseHeader<>(
                123, R.layout.header, HeaderViewHolder::new, getLayoutInflater());

        HeaderAdapter<Object> headerAdapter = new HeaderAdapter<>(adapter, -1, 0, header);

        BaseHeader<Integer, Header2Binding> footer = new BaseHeader<>(
                123, R.layout.header2, Header2ViewHolder::new, getLayoutInflater());

        HeaderAdapter<Object> footerAdapter = new HeaderAdapter<>(
                headerAdapter,
                -2,
                presenter.getModel().items.size() + 1,
                footer);
        binding.recyclerView.setAdapter(footerAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
