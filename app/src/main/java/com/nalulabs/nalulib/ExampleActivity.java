package com.nalulabs.nalulib;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nalulabs.lib.recycle.BaseAdapter;
import com.nalulabs.lib.recycle.BaseHeader;
import com.nalulabs.lib.recycle.HeaderAdapter;
import com.nalulabs.nalulib.databinding.CellBinding;
import com.nalulabs.nalulib.databinding.HeaderBinding;

import java.util.Arrays;

public class ExampleActivity extends AppCompatActivity {

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView recycler = new RecyclerView(this);

        BaseAdapter<Item, CellBinding> adapter = new BaseAdapter<>(
                Arrays.asList(new Item("A"), new Item("B"), new Item("C")),
                getLayoutInflater(),
                R.layout.cell,
                CellViewHolder::new
        );

        BaseHeader<Integer, HeaderBinding> header = new BaseHeader<>(
                123, R.layout.header, HeaderViewHolder::new, getLayoutInflater());

        HeaderAdapter<Item, CellBinding> headerAdapter = new HeaderAdapter<>(adapter, 0, header);

        recycler.setAdapter(headerAdapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        setContentView(recycler);
    }
}
