package com.kirill.kochnev.homewardrope.ui.fragments.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.MvpFragment;
import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.db.models.IHolderModel;
import com.kirill.kochnev.homewardrope.mvp.presenters.BaseDbListPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.interfaces.IPaginationView;
import com.kirill.kochnev.homewardrope.ui.adapters.DbListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kirill Kochnev on 24.02.17.
 */

public abstract class BaseDbListFragment<M extends IHolderModel> extends MvpFragment implements IPaginationView<M> {

    public static final String TAG = "BaseDbListFragment";

    @BindView(R.id.list_items)
    protected RecyclerView list;

    @BindView(R.id.add)
    protected FloatingActionButton addBtn;

    private LinearLayoutManager layoutManager;
    protected boolean isLoading = false;
    protected boolean isInit = false;

    protected DbListAdapter<M> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_db_list, container, false);
        ButterKnife.bind(this, view);
        initUi();
        return view;

    }

    private void initUi() {
        layoutManager = new GridLayoutManager(getContext(), 2);
        adapter = new DbListAdapter<>();
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapter);
        list.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                if (!isLoading && isInit) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0) {
                        isLoading = true;
                        getPresenter().loadMoreData(visibleItemCount + firstVisibleItemPosition);
                    }
                }
            }
        });
        addBtn.setOnClickListener(v -> Toast.makeText(getContext(), "ADD CLICKED", Toast.LENGTH_SHORT).show());
    }

    public abstract BaseDbListPresenter getPresenter();

}
