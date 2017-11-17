package com.kirill.kochnev.homewardrope.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.WardropeApplication;
import com.kirill.kochnev.homewardrope.db.models.Look;
import com.kirill.kochnev.homewardrope.di.components.LookComponent;
import com.kirill.kochnev.homewardrope.enums.ViewMode;
import com.kirill.kochnev.homewardrope.mvp.presenters.look.LooksPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.ILooksView;
import com.kirill.kochnev.homewardrope.ui.activities.look.CreationLookActivity;
import com.kirill.kochnev.homewardrope.ui.activities.look.UpdateLookActivity;
import com.kirill.kochnev.homewardrope.ui.adapters.LooksAdapter;
import com.kirill.kochnev.homewardrope.ui.adapters.base.BaseDbAdapter;
import com.kirill.kochnev.homewardrope.ui.adapters.holders.LookHolder;
import com.kirill.kochnev.homewardrope.ui.fragments.base.ListDelegate;

import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.kirill.kochnev.homewardrope.AppConstants.FRAGMENT_IS_EDIT;
import static com.kirill.kochnev.homewardrope.AppConstants.FRAGMENT_MODE;
import static com.kirill.kochnev.homewardrope.ui.activities.AddUpdateWardropeActivity.WARDROPE_ID;

/**
 * Created by kirill on 21.04.17.
 */

public class LooksFragment extends MvpAppCompatFragment implements ILooksView {

    public static final int REQUEST_CODE = 1;

    public static LooksFragment newInstance(@NonNull ViewMode mode, boolean isEdit, long wardropeId) {
        final Bundle args = new Bundle();
        args.putInt(FRAGMENT_MODE, mode.getModeNum());
        args.putLong(WARDROPE_ID, wardropeId);
        args.putBoolean(FRAGMENT_IS_EDIT, isEdit);
        final LooksFragment fragment = new LooksFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.list_items)
    protected RecyclerView list;

    @BindView(R.id.add)
    protected FloatingActionButton addBtn;

    @BindView(R.id.blank_image)
    protected RelativeLayout blankImg;

    @BindView(R.id.title)
    protected TextView title;

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    private ViewMode mode;

    @NonNull
    private BaseDbAdapter<Look, LookHolder> adapter;

    @NonNull
    private ListDelegate<Look, LookHolder> delegate;

    @InjectPresenter
    LooksPresenter presenter;

    @ProvidePresenter
    LooksPresenter providePresenter() {
        long wardropeId = getArguments().getLong(WARDROPE_ID, AppConstants.DEFAULT_ID);
        boolean isEdit = getArguments().getBoolean(FRAGMENT_IS_EDIT);
        LookComponent component = WardropeApplication
                .getComponentHolder()
                .provideLookComponent(wardropeId, isEdit, mode);
        return component.provideLooksPresenter();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mode = ViewMode.getByNum(getArguments().getInt(FRAGMENT_MODE, AppConstants.DEFAULT_ID));
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_looks, container, false);
        ButterKnife.bind(this, v);
        adapter = new LooksAdapter();
        delegate = new ListDelegate<>(list, adapter, addBtn, presenter, blankImg, mode, ViewMode.LOOK_MODE, new GridLayoutManager(getContext(), 2));
        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mode == ViewMode.LOOK_MODE) {
            title.setText(R.string.looks_title);
            toolbar.setVisibility(View.VISIBLE);
        }
        addBtn.setOnClickListener(v -> openUpdateActivity(CreationLookActivity.createIntent(getContext(), AppConstants.DEFAULT_ID, AppConstants.DEFAULT_ID)));
        addBtn.setActivated(mode == ViewMode.LOOK_MODE);
        addBtn.setVisibility(mode == ViewMode.LOOK_MODE ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        WardropeApplication.getComponentHolder().clearLookComponent();
    }

    @Override
    public void setEditMode(boolean isEditMode) {
        adapter.clear();
        ((LooksAdapter) adapter).setEdit(isEditMode);
    }

    @Override
    public void markAdapterViews(HashSet<Long> set) {
        ((LooksAdapter) adapter).setIds(set);
    }

    @Override
    public void navigateToUpdateLookView(Long id) {
        openUpdateActivity(UpdateLookActivity.createIntent(getContext(), id));
    }

    public void openUpdateActivity(Intent intent) {
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onLoadFinished(List<Look> data) {
        delegate.onLoadFinished(data);
    }

    @Override
    public void invalidateListItem(Look model) {
        delegate.invalidateListItem(model);
    }

    @Override
    public void deleteListItem(Look model) {
        delegate.deleteListItem(model);
    }
}
