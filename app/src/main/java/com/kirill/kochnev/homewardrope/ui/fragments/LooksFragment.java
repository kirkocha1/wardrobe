package com.kirill.kochnev.homewardrope.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.WardrobeApplication;
import com.kirill.kochnev.homewardrope.db.models.Look;
import com.kirill.kochnev.homewardrope.di.components.LookComponent;
import com.kirill.kochnev.homewardrope.enums.ViewMode;
import com.kirill.kochnev.homewardrope.mvp.presenters.look.LooksPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.ILooksView;
import com.kirill.kochnev.homewardrope.ui.activities.look.CreationLookActivity;
import com.kirill.kochnev.homewardrope.ui.activities.look.UpdateLookActivity;
import com.kirill.kochnev.homewardrope.ui.adapters.LooksAdapter;
import com.kirill.kochnev.homewardrope.ui.adapters.holders.LookHolder;
import com.kirill.kochnev.homewardrope.ui.fragments.base.FragmentToolbarDelegate;
import com.kirill.kochnev.homewardrope.ui.fragments.base.ListDelegate;

import java.util.HashSet;
import java.util.List;

import static com.kirill.kochnev.homewardrope.AppConstants.FRAGMENT_IS_EDIT;
import static com.kirill.kochnev.homewardrope.AppConstants.FRAGMENT_MODE;
import static com.kirill.kochnev.homewardrope.ui.activities.AddUpdateWardrobeActivity.WARDROPE_ID;

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

    @Nullable
    private LooksAdapter adapter;

    @NonNull
    private ListDelegate<Look, LookHolder> delegate;

    @NonNull
    private FragmentToolbarDelegate fragmentToolbarDelegate = new FragmentToolbarDelegate();


    @InjectPresenter
    LooksPresenter presenter;

    @ProvidePresenter
    LooksPresenter providePresenter() {
        final long wardropeId = getArguments().getLong(WARDROPE_ID, AppConstants.DEFAULT_ID);
        final boolean isEdit = getArguments().getBoolean(FRAGMENT_IS_EDIT);
        final ViewMode mode = ViewMode.getByNum(getArguments().getInt(FRAGMENT_MODE, AppConstants.DEFAULT_ID));
        final LookComponent component = WardrobeApplication
                .getComponentHolder()
                .provideLookComponent(wardropeId, isEdit, mode);
        return component.provideLooksPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_looks, container, false);
        final ViewMode mode = ViewMode.getByNum(getArguments().getInt(FRAGMENT_MODE, AppConstants.DEFAULT_ID));
        adapter = new LooksAdapter();
        fragmentToolbarDelegate.init(view, mode, ViewMode.LOOK_MODE, R.string.looks_title);
        delegate = new ListDelegate<>(
                view,
                adapter,
                presenter,
                mode,
                ViewMode.LOOK_MODE,
                new GridLayoutManager(getContext(), 2),
                v -> startActivityForResult(
                        CreationLookActivity.createIntent(getContext(), AppConstants.DEFAULT_ID, AppConstants.DEFAULT_ID),
                        REQUEST_CODE
                )
        );
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        WardrobeApplication.getComponentHolder().clearLookComponent();
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
        startActivityForResult(UpdateLookActivity.createIntent(getContext(), id), REQUEST_CODE);
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
