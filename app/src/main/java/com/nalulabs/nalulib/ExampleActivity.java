package com.nalulabs.nalulib;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nalulabs.lib.recycle.BaseAdapter;
import com.nalulabs.lib.recycle.BaseHeader;
import com.nalulabs.lib.recycle.HeaderAdapter;
import com.nalulabs.nalulib.databinding.Header2Binding;
import com.nalulabs.nalulib.databinding.HeaderBinding;

import java.util.Arrays;
import java.util.List;

public class ExampleActivity extends AppCompatActivity {

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView recycler = new RecyclerView(this);

        List<Object> items = Arrays.asList(new Item1("A"), new Item2("B"), new Item1("C"), new Item1("C"), new Item1("D")
                , new Item1("E"), new Item1("F"), new Item1("G"), new Item1("H"), new Item2("I"));
        BaseAdapter<Object> adapter = new BaseAdapter<>(
                items,
                getLayoutInflater(),
                new BaseAdapter.Factory<>(R.layout.cell, CellViewHolder::new)
        );

        adapter.addItemType(
                (position, item) -> item instanceof Item2,
                new BaseAdapter.Factory<>(R.layout.cell2, Cell2ViewHolder::new)
        );

        BaseHeader<Integer, HeaderBinding> header = new BaseHeader<>(
                123, R.layout.header, HeaderViewHolder::new, getLayoutInflater());

        HeaderAdapter<Object> headerAdapter = new HeaderAdapter<>(adapter, -1,0, header);

        BaseHeader<Integer, Header2Binding> footer = new BaseHeader<>(
                123, R.layout.header2, Header2ViewHolder::new, getLayoutInflater());

        HeaderAdapter<Object> footerAdapter = new HeaderAdapter<>(headerAdapter, -2, items.size() + 1, footer);

        recycler.setAdapter(footerAdapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        setContentView(recycler);
    }
}
