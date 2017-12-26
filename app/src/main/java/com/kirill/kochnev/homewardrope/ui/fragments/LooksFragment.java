package com.kirill.kochnev.homewardrope.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.kirill.kochnev.homewardrope.ui.adapters.holders.LookHolder;
import com.kirill.kochnev.homewardrope.ui.fragments.base.ListDelegate;
import com.kirill.kochnev.homewardrope.ui.fragments.base.ToolbarDelegate;

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

    public static LooksFragment newInstance(@NonNull final ViewMode mode, boolean isEdit, long wardropeId) {
        final Bundle args = new Bundle();
        args.putInt(FRAGMENT_MODE, mode.getModeNum());
        args.putLong(WARDROPE_ID, wardropeId);
        args.putBoolean(FRAGMENT_IS_EDIT, isEdit);
        final LooksFragment fragment = new LooksFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.title)
    protected TextView title;

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    private ViewMode mode;

    @Nullable
    private LooksAdapter adapter;

    @Nullable
    private ListDelegate<Look, LookHolder> delegate;

    @NonNull
    private ToolbarDelegate toolbarDelegate = new ToolbarDelegate();


    @InjectPresenter
    LooksPresenter presenter;

    @ProvidePresenter
    LooksPresenter providePresenter() {
        final long wardropeId = getArguments().getLong(WARDROPE_ID, AppConstants.DEFAULT_ID);
        final boolean isEdit = getArguments().getBoolean(FRAGMENT_IS_EDIT);
        final LookComponent component = WardropeApplication
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
        final View view = inflater.inflate(R.layout.fragment_looks, container, false);
        ButterKnife.bind(this, view);
        adapter = new LooksAdapter();
        toolbarDelegate.init(view, mode, ViewMode.LOOK_MODE, R.string.looks_title);
        delegate = new ListDelegate<>(
                view,
                adapter,
                presenter,
                mode,
                ViewMode.LOOK_MODE,
                v -> openUpdateActivity(CreationLookActivity.createIntent(getContext(), AppConstants.DEFAULT_ID, AppConstants.DEFAULT_ID))
        );
        delegate.setLayoutManager(new GridLayoutManager(getContext(), 2));
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        WardropeApplication.getComponentHolder().clearLookComponent();
    }

    @Override
    public void setEditMode(boolean isEditMode) {
        if (adapter != null) {
            adapter.clear();
            adapter.setEdit(isEditMode);
        }
    }

    @Override
    public void markAdapterViews(HashSet<Long> set) {
        if (adapter != null) {
            adapter.setIds(set);
        }
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
