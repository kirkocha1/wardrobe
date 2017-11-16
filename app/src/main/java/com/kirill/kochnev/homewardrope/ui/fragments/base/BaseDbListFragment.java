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
import android.widget.RelativeLayout;

import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.db.models.IDbModel;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.IPaginator;
import com.kirill.kochnev.homewardrope.mvp.views.base.IPaginationView;
import com.kirill.kochnev.homewardrope.ui.adapters.OnClick;
import com.kirill.kochnev.homewardrope.ui.adapters.base.BaseDbAdapter;
import com.kirill.kochnev.homewardrope.ui.adapters.base.BaseHolder;
import com.kirill.kochnev.homewardrope.utils.AnimationHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;
import static com.kirill.kochnev.homewardrope.AppConstants.LIMIT;

/**
 * Created by Kirill Kochnev on 24.02.17.
 */

public abstract class BaseDbListFragment<M extends IDbModel, H extends BaseHolder<M>> extends BaseActionBarFragment implements IPaginationView<M>, OnClick<M> {

    public static final String TAG = "BaseDbListFragment";
    public static final int REQUEST_CODE = 1;
    @BindView(R.id.list_items)
    protected RecyclerView list;

    @BindView(R.id.add)
    protected FloatingActionButton addBtn;

    @BindView(R.id.blank_image)
    protected RelativeLayout blankImg;

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
        if (isFullPart()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage(getString(R.string.delete_message))
                    .setPositiveButton(getString(R.string.yes), (dialog, which) -> {
                        getPresenter().onLongItemClick(model);
                        dialog.dismiss();
                    });
            builder.create().show();
        }
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
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                if (!isAllLoaded && !isLoading) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                        isLoading = true;
                        getPresenter().loadMoreData(adapter.getLastId());
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (addBtn.isActivated()) {
                    switch (newState) {
                        case RecyclerView.SCROLL_STATE_DRAGGING:
                            AnimationHelper.hideShowAnimation(getContext(), addBtn, true);
                            break;
                        case RecyclerView.SCROLL_STATE_IDLE:
                            AnimationHelper.hideShowAnimation(getContext(), addBtn, false);
                            break;
                    }
                }
            }
        });
    }

    public LinearLayoutManager getLayoutManager() {
        return new GridLayoutManager(getContext(), 2);
    }

    @Override
    public void deleteListItem(M model) {
        if (adapter != null) {
            adapter.onRemoveItem(model);
            if (adapter.getItemCount() == 0) {
                blankImg.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onLoadFinished(List<M> data) {
        boolean isBlank = adapter.getItemCount() == 0 && (data == null || data.size() == 0);
        blankImg.setVisibility(isBlank ? View.VISIBLE : View.GONE);
        list.post(() -> {
            isLoading = false;
            isAllLoaded = data == null || data.size() < LIMIT;
            adapter.addData(data);
        });
    }

    public void openUpdateActivity(Intent intent) {
        startActivityForResult(intent, REQUEST_CODE);
    }

    public abstract BaseDbAdapter<M, H> initAdapter();

    public abstract IPaginator getPresenter();

    public abstract boolean isFullPart();

    public void onCreationStart() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE && data != null) {
            getPresenter().addOrUpdateListItem(data.getLongExtra(AppConstants.ADD_UPDATED_ID, -1));
        }
    }

    @Override
    public void invalidateListItem(M model) {
        adapter.addData(model);
        blankImg.setVisibility(adapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

}
