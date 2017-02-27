package com.kirill.kochnev.homewardrope.ui.fragments.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.MvpFragment;
import com.kirill.kochnev.homewardrope.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kirill Kochnev on 24.02.17.
 */

public class BaseDbListFragment extends MvpFragment {

    @BindView(R.id.list_items)
    protected RecyclerView list;

    @BindView(R.id.add)
    protected FloatingActionButton addBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_db_list, container, false);
        ButterKnife.bind(this, view);
        initUi();
        return view;

    }

    private void initUi() {
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        addBtn.setOnClickListener(v -> Toast.makeText(getContext(), "ADD CLICKED", Toast.LENGTH_SHORT).show());
    }
}
