package com.kirill.kochnev.homewardrope.ui.fragments.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.db.models.IDbModel;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.BaseDbListPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.base.IPaginationView;
import com.kirill.kochnev.homewardrope.ui.adapters.OnClick;
import com.kirill.kochnev.homewardrope.ui.adapters.base.BaseDbAdapter;
import com.kirill.kochnev.homewardrope.ui.adapters.base.BaseHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.kirill.kochnev.homewardrope.AppConstants.LIMIT;

/**
 * Created by Kirill Kochnev on 24.02.17.
 */

public abstract class BaseDbListFragment<M extends IDbModel, H extends BaseHolder<M>> extends BaseActionBarFragment implements IPaginationView<M>, OnClick<M> {

    public static final String TAG = "BaseDbListFragment";

    @BindView(R.id.list_items)
    protected RecyclerView list;

    @BindView(R.id.add)
    protected FloatingActionButton addBtn;

    @BindView(R.id.blank_image)
    protected ImageView blankImg;

    private LinearLayoutManager layoutManager;
    protected boolean isLoading = false;
    protected boolean isAllLoaded = false;
    protected BaseDbAdapter<M, H> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        onCreationStart();
        super.onCreate(savedInstanceState);
    }

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
        layoutManager = getLayoutManager();
        adapter = initAdapter();
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
                if (!isAllLoaded && !isLoading && isScrollDown) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                        isLoading = true;
                        getPresenter().loadMoreData(visibleItemCount + firstVisibleItemPosition);
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (addBtn.isActivated()) {
                    switch (newState) {
                        case RecyclerView.SCROLL_STATE_DRAGGING:
                            addBtn.setVisibility(View.GONE);
                            break;
                        case RecyclerView.SCROLL_STATE_IDLE:
                            addBtn.setVisibility(View.VISIBLE);
                            break;
                    }
                }
            }
        });
        onInitUi();
    }

    public LinearLayoutManager getLayoutManager() {
        return new GridLayoutManager(getContext(), 2);
    }

    @Override
    public void notifyListChanges(M model) {
        if (adapter != null) {
            adapter.onRemoveItem(model);
            if (adapter.getItemCount() == 0) {
                blankImg.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onLoadFinished(List<M> data) {
        blankImg.setVisibility(data == null || data.size() == 0 ? View.VISIBLE : View.GONE);
        list.post(() -> {
            isLoading = false;
            isAllLoaded = data != null && data.size() < LIMIT;
            adapter.addData(data);
        });
    }

    @Override
    public void dropData() {
        if (adapter != null) {
            adapter.clear();
        }
    }

    public void openUpdateActivity(Intent intent) {
        startActivity(intent);
    }

    public abstract BaseDbAdapter<M, H> initAdapter();

    public abstract BaseDbListPresenter getPresenter();

    public abstract void onInitUi();

    public void onCreationStart() {

    }
}
