package com.kirill.kochnev.homewardrobe.presentation.ui.fragments.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kirill.kochnev.homewardrobe.R;
import com.kirill.kochnev.homewardrobe.db.models.IDbModel;
import com.kirill.kochnev.homewardrobe.enums.ViewMode;
import com.kirill.kochnev.homewardrobe.presentation.presenters.base.IPaginator;
import com.kirill.kochnev.homewardrobe.presentation.ui.adapters.OnClick;
import com.kirill.kochnev.homewardrobe.presentation.ui.adapters.base.IListAdapterDelegate;
import com.kirill.kochnev.homewardrobe.utils.AnimationHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.kirill.kochnev.homewardrobe.AppConstants.LIMIT;

public class ListDelegate<M extends IDbModel> implements OnClick<M> {

    @BindView(R.id.list_items)
    RecyclerView list;

    @BindView(R.id.blank_image)
    View blankImg;

    @BindView(R.id.add)
    FloatingActionButton addBtn;

    private Context context;

    private @NonNull LinearLayoutManager layoutManager;
    private boolean isLoading = false;
    private boolean isAllLoaded = false;
    private @NonNull IListAdapterDelegate<M> adapter;
    private @NonNull IPaginator<M> paginator;
    private @NonNull ViewMode mode;
    private @NonNull ViewMode mainState;

    public ListDelegate(
            @NonNull final View view,
            @NonNull final IListAdapterDelegate<M> adapter,
            @NonNull final IPaginator<M> paginator,
            @NonNull final ViewMode mode,
            @NonNull final ViewMode mainState,
            @NonNull final View.OnClickListener addButtonListener
    ) {
        this(view, adapter, paginator, mode, mainState, new LinearLayoutManager(view.getContext()), addButtonListener);
    }

    public ListDelegate(
            @NonNull final View view,
            @NonNull final IListAdapterDelegate<M> adapter,
            @NonNull final IPaginator<M> paginator,
            @NonNull final ViewMode mode,
            @NonNull final ViewMode mainState,
            @NonNull final LinearLayoutManager layoutManager,
            @NonNull final View.OnClickListener addButtonListener
    ) {
        ButterKnife.bind(this, view);
        this.adapter = adapter;
        this.paginator = paginator;
        this.context = list.getContext();
        this.mode = mode;
        this.mainState = mainState;
        this.layoutManager = layoutManager;
        adjustList();
        adjustAddButton(addButtonListener);
    }

    private void adjustAddButton(View.OnClickListener listener) {
        addBtn.setOnClickListener(listener);
        addBtn.setActivated(mode == mainState);
        addBtn.setVisibility(mode == mainState ? View.VISIBLE : View.GONE);
    }

    private void adjustList() {
        adapter.setClickListener(this);
        list.setLayoutManager(layoutManager);
        list.setAdapter((RecyclerView.Adapter) adapter);
        list.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                if (!isAllLoaded && !isLoading) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                        isLoading = true;
                        paginator.loadMoreData(adapter.getLastId());
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (addBtn.isActivated()) {
                    switch (newState) {
                        case RecyclerView.SCROLL_STATE_DRAGGING:
                            AnimationHelper.hideShowAnimation(context, addBtn, true);
                            break;
                        case RecyclerView.SCROLL_STATE_IDLE:
                            AnimationHelper.hideShowAnimation(context, addBtn, false);
                            break;
                    }
                }
            }
        });

    }

    @Override
    public void onLongClick(M model) {
        if (mode.equals(mainState)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(context.getString(R.string.delete_message))
                    .setPositiveButton(context.getString(R.string.yes), (dialog, which) -> {
                        paginator.onLongItemClick(model);
                        dialog.dismiss();
                    });
            builder.create().show();
        }
    }

    @Override
    public void onClick(M model) {
        paginator.onItemClick(model);
    }


    public void onLoadFinished(List<M> data) {
        list.post(() -> {
            boolean isBlank = adapter.getItemCount() == 0 && (data == null || data.size() == 0);
            blankImg.setVisibility(isBlank ? View.VISIBLE : View.GONE);
            isLoading = false;
            isAllLoaded = data == null || data.size() < LIMIT;
            adapter.addData(data);
        });
    }


    public void invalidateListItem(M model) {
        adapter.addData(model);
        blankImg.setVisibility(adapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

    public void deleteListItem(M model) {
        adapter.onRemoveItem(model);
        if (adapter.getItemCount() == 0) {
            blankImg.setVisibility(View.VISIBLE);
        }
    }
}
