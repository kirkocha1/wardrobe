package com.kirill.kochnev.homewardrope.ui.fragments.base;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arellomobile.mvp.MvpFragment;
import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.db.models.IDbModel;
import com.kirill.kochnev.homewardrope.mvp.presenters.BaseDbListPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.interfaces.IPaginationView;
import com.kirill.kochnev.homewardrope.ui.adapters.DbListAdapter;
import com.kirill.kochnev.homewardrope.ui.adapters.OnClick;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kirill Kochnev on 24.02.17.
 */

public abstract class BaseDbListFragment<M extends IDbModel> extends MvpFragment implements IPaginationView<M>, OnClick<M> {

    public static final String TAG = "BaseDbListFragment";

    @BindView(R.id.list_items)
    protected RecyclerView list;

    @BindView(R.id.add)
    protected FloatingActionButton addBtn;

    @BindView(R.id.blank_image)
    protected ImageView blankImg;

    private GridLayoutManager layoutManager;
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

    @Override
    public void onLongClick(M model) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Вы уверенны, что хотите удалить?")
                .setPositiveButton("Да", (dialog, which) -> {
                    getPresenter().onLongItemClick(model);
                    dialog.dismiss();
                });
        builder.create().show();
    }

    @Override
    public void onClick(M model) {
        getPresenter().onItemClick(model);
    }

    private void initUi() {
        layoutManager = new GridLayoutManager(getContext(), 2);
        adapter = new DbListAdapter<>();
        adapter.setClickListner(this);
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapter);
        list.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                boolean isScrollDown = dy > 0;
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                if (!isLoading && isInit && isScrollDown) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition > 0) {
                        isLoading = true;
                        getPresenter().loadMoreData(visibleItemCount + firstVisibleItemPosition);
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        addBtn.setVisibility(View.GONE);
                        break;
                    case RecyclerView.SCROLL_STATE_IDLE:
                        addBtn.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
        onInitUi();
    }

    @Override
    public void notifyListChanges(M model) {
        if (adapter != null) {
            adapter.onRemoveItem(model);
        }
    }

    public abstract BaseDbListPresenter getPresenter();

    public abstract void onInitUi();
}
