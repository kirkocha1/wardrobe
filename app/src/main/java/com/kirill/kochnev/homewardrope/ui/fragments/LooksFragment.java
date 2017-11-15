package com.kirill.kochnev.homewardrope.ui.fragments;

import android.os.Bundle;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.db.models.Look;
import com.kirill.kochnev.homewardrope.enums.ViewMode;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.IPaginator;
import com.kirill.kochnev.homewardrope.mvp.presenters.look.LooksPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.ILooksView;
import com.kirill.kochnev.homewardrope.ui.activities.look.CreationLookActivity;
import com.kirill.kochnev.homewardrope.ui.activities.look.UpdateLookActivity;
import com.kirill.kochnev.homewardrope.ui.adapters.LooksAdapter;
import com.kirill.kochnev.homewardrope.ui.adapters.base.BaseDbAdapter;
import com.kirill.kochnev.homewardrope.ui.adapters.holders.LookHolder;
import com.kirill.kochnev.homewardrope.ui.fragments.base.BaseDbListFragment;

import java.util.HashSet;

import static com.kirill.kochnev.homewardrope.AppConstants.FRAGMENT_IS_EDIT;
import static com.kirill.kochnev.homewardrope.AppConstants.FRAGMENT_MODE;
import static com.kirill.kochnev.homewardrope.ui.activities.AddUpdateWardropeActivity.WARDROPE_ID;

/**
 * Created by kirill on 21.04.17.
 */

public class LooksFragment extends BaseDbListFragment<Look, LookHolder> implements ILooksView {

    public static LooksFragment newInstance(ViewMode mode, boolean isEdit, long wardropeId) {
        Bundle args = new Bundle();
        args.putInt(FRAGMENT_MODE, mode.getModeNum());
        args.putLong(WARDROPE_ID, wardropeId);
        args.putBoolean(FRAGMENT_IS_EDIT, isEdit);
        LooksFragment fragment = new LooksFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private ViewMode mode;

    @InjectPresenter
    LooksPresenter presenter;

    @ProvidePresenter
    LooksPresenter providePresenter() {
        long wardropeId = getArguments().getLong(WARDROPE_ID, AppConstants.DEFAULT_ID);
        boolean isEdit = getArguments().getBoolean(FRAGMENT_IS_EDIT);
        return new LooksPresenter(mode, isEdit, wardropeId);
    }

    @Override
    public BaseDbAdapter<Look, LookHolder> initAdapter() {
        return new LooksAdapter();
    }

    @Override
    public IPaginator getPresenter() {
        return presenter;
    }

    @Override
    public void onCreationStart() {
        super.onCreationStart();
        mode = ViewMode.getByNum(getArguments().getInt(FRAGMENT_MODE, AppConstants.DEFAULT_ID));
    }

    @Override
    public void onInitUi() {
        if (mode == ViewMode.LOOK_MODE) {
            setTitle(R.string.looks_title);
        }
        addBtn.setOnClickListener(v -> openUpdateActivity(CreationLookActivity.createIntent(getContext(), AppConstants.DEFAULT_ID, AppConstants.DEFAULT_ID)));
        addBtn.setActivated(mode == ViewMode.LOOK_MODE);
        addBtn.setVisibility(mode == ViewMode.LOOK_MODE ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setEditMode(boolean isEditMode) {
        adapter.clear();
        ((LooksAdapter) adapter).setEdit(isEditMode);
    }

    @Override
    public boolean isFullPart() {
        return mode == ViewMode.LOOK_MODE;
    }

    @Override
    public void markAdapterViews(HashSet<Long> set) {
        ((LooksAdapter) adapter).setIds(set);
    }

    @Override
    public void navigateToUpdateLookView(Long id) {
        openUpdateActivity(UpdateLookActivity.createIntent(getContext(), id));
    }
}
